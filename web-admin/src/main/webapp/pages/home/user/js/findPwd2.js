/*---------------------------------------------------------------------
	* Filename:         findpwd2nd.js
	* Description:      findpwd
	* Version:          1.0.0 (2016-08-31)
	* Website:          hn.sun3d.com
	* Author:           JackyZeng (zrongs@vip.qq.com)
---------------------------------------------------------------------*/
//提示信息
var tipsMsg = {
	tips_success: '', 
	tips_required: '不能为空',
	tips_codeHide: '请输入验证码',
	tips_phoneCodeHide: '请输入手机验证码',
	tips_emailCodeHide: '请输入邮箱验证码',
	tips_errorCode:'输入的验证码有误,请重新输入',
	tips_moreCode:'验证码已超时，请重新获取验证码',
	tips_sendCodeFail:'发送验证码失败',
	tips_moreTime:'请于120秒后重新获取验证码',
	tips_moreSend:'一天最多发送十次验证码'
}; 

//提示消息框
function sendMsg(msg,flag,t){
	t.parent('dd').find('em').html('');
	if(!flag){
		t.parent('dd').find('em').html(msg).addClass('fail').removeClass('sucess');
	}else{
		t.parent('dd').find('em').html(msg).addClass('sucess').removeClass('fail');
	}
}

/**
 * 页面加载完后处理
 */
$(document).ready(function() {
	//判断是否有邮箱地址
	if($(".inputEmail").val()){
		$(".inputEcode").attr("readonly", true);//将邮箱验证码框设置为不允许更改
		$('.emailCode').fadeIn(0);//显示发送按钮
	}
	//判断是否有手机号码
	if($(".inputPhone").val()){
		$(".inputPcode").attr("readonly", true);//将手机验证码框设置为不允许更改
		$('.sendCodeP').fadeIn(0);//显示发送按钮
	}
	/**
	 * 验证手机验证码
	 */
	$(".inputPcode").on('keyup blur',function() {
		 var inputPcode = $(this).val();
		 var msg = "";
		 if(inputPcode == ""){
			 msg = tipsMsg['tips_phoneCodeHide'];
			 sendMsg(msg, 0, $(this));
			 return
		 }
		 //判断输入的手机验证码是否正确
		 if(!_dzl_validPhoneCode(inputPcode, $(this), sendMsg)){
			 return;
		 }
		 
		 //手机验证码验证成功
		 msg = tipsMsg['tips_success'];
		 sendMsg(msg, 1, $(this));
		 $(".inputPcode").attr("readonly", true);//验证成功时,将手机验证码框设置为不允许更改
		 $('.sendCodeP').fadeOut(0);//隐藏发送按钮
		 $('.phoneNum').fadeOut(0);//隐藏计时
	});
	
	/**
	 * 验证邮箱验证码
	 */
	$(".inputEcode").on('keyup blur',function() {
		 var inputEcode = $(this).val();
		 var msg = "";
		 if(inputEcode == ""){
			 msg = tipsMsg['tips_emailCodeHide'];
			 sendMsg(msg, 0, $(this));
			 return
		 }
		 //判断输入的邮箱验证码是否正确
		 if(!_dzl_validEmailCode(inputEcode, $(this), sendMsg)){
			 return;
		 }
		 //邮箱验证码验证成功
		 msg = tipsMsg['tips_success'];
		 sendMsg(msg, 1, $(this));
		 $(".inputEcode").attr("readonly", true);//验证成功时,将邮箱验证码框设置为不允许更改
		 $('.emailCode').fadeOut(0);//隐藏发送按钮
		 $('.emailNum').fadeOut(0);//隐藏计时
	});
	

	/*-------------------------------------------
			验证码动作
	--------------------------------------------*/
    var initTime = 60; //设置倒计时
    
    $('.emailCode').click(sendEmailCode);
    $('.sendCodeP').click(sendPhoneCode);
    $('.inputCode').keyup(useCode);

    //判断验证码是否正确，正确即可发送手机验证码
    function useCode() {
        var code = 'sbqq';
        if ($(this).val() == code) {
            $(this).parent().parent().hide();
            $('.phoneCode').fadeIn(0);
            numAdd();
        }
    }

    //手机验证码倒计时
    var countdown = initTime;
    function numAdd() {
        function loadTime() {
            if (countdown == 0) {
                countdown = initTime;
                $('.phoneNum').hide();
                $('.sendCodeP').fadeIn(0);
                return;
            }
            countdown--;
            $('.phoneNum span').html(countdown);
            setTimeout(function() {
                loadTime()
            },
            1000)
        }
        loadTime();
    }

    //Email验证码倒计时
    var countdownE = initTime;
    function numAddE() {
        function loadTime() {
            if (countdownE == 0) {
                countdownE = initTime;
                $('.emailNum').hide();
                $('.emailCode').fadeIn(0);
                return;
            }
            countdownE--;
            $('.emailNum span').html(countdownE);
            setTimeout(function() {
                loadTime()
            },
            1000)
        }
        loadTime();
    }

    /**
     * 发送Email验证码
     * @returns
     */
    function sendEmailCode() {
        $('.emailNum').fadeIn(0);
		$('.inputEcode').attr('readonly', false).focus();//点击按钮后,允许输入邮箱验证码
		//$('.inputEcode').focus();
        numAddE();
        
      //发送邮箱验证码
        _dzl_sendEmailCode();
    }

    /**
     * 发送手机验证码
     * @returns
     */
    function sendPhoneCode() {
        $(this).hide();	//	隐藏发送按钮
        $('.phoneNum').fadeIn(0);//显示读秒
		$('.inputPcode').attr('readonly', false).focus();//点击发送按钮后，允许输入手机验证码
        numAdd();
        
        //短信发送验证码处理:保存手机号码及短信验证码
        _dzl_sendPhoneCode();
    }
    
    /**
     * 通过邮箱修改密码
     */
    $("#email_go").click(function(){
    	//获取邮箱地址
    	var email = $(".inputEmail").val();
		//判断邮箱地址是否为空
		if(email == ""){
			msg = tipsMsg['tips_emailHide'];
			$('.emailCode').hide();
			sendMsg(msg, 0, $(".inputEmail"));
			return;
		}
		//获取邮箱验证码
		var inputCode = $(".inputEcode").val();
		//判断邮箱验证码是否为空
		if(inputCode == ""){
			msg = tipsMsg['tips_emailCodeHide'];
			sendMsg(msg, 0, $(".inputEcode"));
			return
		}
		
		 //判断输入的邮箱验证码是否正确
		if(!_dzl_validEmailCode(inputCode, $(this), sendMsg)){
			return;
		}
		$.ajax({
			type : "POST",
			url : getFullUrl('./user/saveFindPwd2'),
			data : {email : email, emailCode : inputCode},
			success : function(data){
				if(data.success == "0"){
					$('#findPWDForm').submit();
					//window.location.href = basePath+"/user/findPwd3?uid="+data.uid;
				}
			}
		 	
		});
    });
    
    /**
     * 通过手机修改密码
     */
    $("#phone_go").click(function(){
    	//获取手机号码
    	var phone = $(".inputPhone").val();
    	//判断手机号码是否为空
		if(phone == ""){
			msg = tipsMsg['tips_phoneHide'];
			$('.inputCode').attr('readonly', true).val('').addClass('readonly');
			sendMsg(msg, 0, $(".inputPcode"));
			return;
		}
		//获取手机验证码
		var msgcontent = $(".inputPcode").val();
		//判断手机验证码是否为空
		if(msgcontent == ""){
			 msg = tipsMsg['tips_phoneCodeHide'];
			 sendMsg(msg, 0, $(".inputPcode"));
			 return
		}
		 //判断输入的手机验证码是否正确
		if(!_dzl_validPhoneCode(msgcontent, $(this), sendMsg)){
			 return;
		}
		 //请求后台数据
		$.ajax({
			type : "POST",
			url : getFullUrl('./user/saveFindPwd2'),
			data : {msgcontent : msgcontent,phone : phone},
			success : function(data){
				if(data.success == "0"){
					//window.location.href = basePath+"/user/findPwd3?id="+data.uid;
					$('#findPWDForm').submit();
				}
			}
			 	
		});
    });
})

/**
 * 邮箱发送验证码处理：保存邮箱地址及邮箱验证码
 * @returns
 */
function _dzl_sendEmailCode(){
	var email=$(".inputEmail").val();
	$.ajax({
		type : "POST",
		async : false,
		url : getFullUrl('./user/sendEmail'),
		data : {email : email},
		success : function(data){
			if(data.success == "0"){
				$('.inputEcode').focus();
				return;
			}else if(data.success == "2"){
				msg = tipsMsg['tips_moreTime'];//提示120秒后重新获取
				$(".inputEcode").attr("readonly", true);//将验证码框设置为不允许更改
				$('.emailNum').fadeOut(0);//隐藏计时
				$('.emailCode').fadeOut(0);//隐藏按钮
				//$("#codeHide").hide();//隐藏验证码模块
				//$('.goNext').hide(); //隐藏确定按钮
				sendMsg(msg, 0, $('.emailCode'));
				return;
			}else if(data.success == "3"){
				msg = tipsMsg['tips_moreSend'];//提示一天最多能发10次验证码
				$("#codeHide").hide();//隐藏验证码模块
				$('.goNext').hide(); //隐藏确定按钮
				sendMsg(msg, 0, $('.inputEmail'));
	            return;
			}
		}
	});
}


/**
 * 判断邮箱验证码是否正确
 * @returns
 */
function _dzl_validEmailCode(emailcode, inputEl, sendMsg){
	var email = $(".inputEmail").val();
	var _succ = true;
	$.ajax({
		type: "POST",
		async: false,
		url: getFullUrl('./user/emailCode'),
		data: {email : email, emailcode: emailcode},
		success: function(data){
			if(data.success == "1"){
				msg = tipsMsg['tips_errorCode']; //提示邮箱验证码输入错误
				sendMsg(msg, 0, inputEl);
				_succ = false;
			}else if(data.success == "2"){
				msg = tipsMsg['tips_moreCode']; //提示验证码超时
				sendMsg(msg, 0, inputEl);
				$('.emailCode').fadeIn(0);
				_succ = false;
			}
		}
	});
	return _succ;
}

/**
 * 短信发送验证码处理:保存手机号码及短信验证码
 * @returns
 */
function _dzl_sendPhoneCode(){
		// 请求后台数据
	    var msgphone = $(".inputPhone").val();
		$.ajax({
			type:"POST",
			async: false,
			url:getFullUrl('./user/sendPhone'),
			data:{msgphone : msgphone},
			success:function(data){
				if(data.success == "0"){
					$('.inputPcode').focus();
					return;
				}else if(data.success == "2"){
					msg = tipsMsg['tips_moreTime'];//提示120秒后重新获取
					$(".inputPcode").attr("readonly", true);//将手机验证码框设置为不允许更改
					$('.phoneNum').fadeOut(0);//隐藏计时
					$('.sendCodeP').fadeOut(0);//隐藏按钮
					//$('.goNext').hide(); //隐藏确定按钮
					sendMsg(msg, 0, $('.sendCodeP'));
					return;
				}else if(data.success == "3"){
					msg = tipsMsg['tips_moreSend'];//提示一天最多能发10次验证码
					$("#PhoneHide").hide();//隐藏验证码模块
					$('.goNext').hide(); //隐藏确定按钮
					sendMsg(msg, 0, $('.inputPhone'));
					return;
				}
			}
		}); 	
}

/**
 * 判断手机验证码是否正确
 * @returns
 */
function _dzl_validPhoneCode(msgcontent,inputEl,sendMsg){
	var msgphone = $(".inputPhone").val();
	var _succ = true;
	$.ajax({
		type:"POST",
		async: false,
		url:getFullUrl('./user/phoneCode'),
		data:{msgcontent : msgcontent, msgphone : msgphone},
		success:function(data){
			if( data.success == "1" ){
				msg = tipsMsg['tips_errorCode'];  //提示手机验证码输入错误
				sendMsg(msg, 0, inputEl);
				_succ = false;
			}else if(data.success == "2"){
				msg = tipsMsg['tips_moreCode']; //提示验证码超时
				sendMsg(msg, 0, inputEl);
				$('.sendCodeP').fadeIn(0);//验证码超时,显示发送按钮
				_succ = false;
			}
		}
	});
	return _succ;
}