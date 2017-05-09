/**
 * 提示消息
 */
var tipsMsg = {
	tips_success: '', 
	tips_required: '不能为空',
	tips_email: '邮箱地址格式有误',
	tips_emailHide: '请输入邮箱地址',
	tips_emailCodeHide: '请输入邮箱验证码',
    tips_emailHas:'该邮箱已绑定',
    tips_errorCode:'输入的验证码有误,请重新输入',
    tips_sendCodeFail:'发送验证码失败',
    tips_moreCode:'验证码已超时，请重新获取验证码',
    tips_errorPreEmail:'原始邮箱不匹配',
    tips_moreTime:'请于120秒后重新获取验证码',
    tips_moreSend:'一天最多发送十次验证码'
}

//正则表达式
var regexpEmail =  /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/; //邮箱规则

//提示消息框
function sendMsg(msg,flag,t){
	t.parent('dd').find('em').html('');
	if(!flag){
		t.next('em').html(msg).addClass('fail').removeClass('sucess');
	}else{
		t.next('em').html(msg).addClass('sucess').removeClass('fail');
	}
}

/**
 * 页面加载完处理
 */
$(function(){
	 $(".inputEcode").attr("readonly", true);//将邮箱验证码框设置为不允许更改

	//判断是否有绑定邮箱文字
	if($(".tt").length>0){
		$.ajax({
			type : "POST",
			url : getFullUrl('./center/hasSessEmail'),
			success : function(data){
				if(data.success == "1"){
					//已绑定邮箱
					$(".tt").html('修改邮箱');
					$("#reEmail").show();
					$("#preEmail").show();
					
					$(".inputEmail").attr("readonly", true);
					/**
					 * 验证原始邮箱
					 */
					$("#preEmailAddr").on('keyup blur',function(){
						var preEmailAddr = $(this).val();
						var msg = "";
						//判断邮箱地址是否为空
						if(preEmailAddr == ""){
							msg = tipsMsg['tips_emailHide'];
							sendMsg(msg,0,$(this));
							return;
						}
						// 判断原邮箱是否匹配
						if(!_validPreEmail(preEmailAddr,$(this),sendMsg)){
							 return;
						 }
						 // 验证成功
						 msg = tipsMsg['tips_success'];
						 sendMsg(msg,1,$(this));
						 $(".inputEmail2").attr("readonly", true);
						 $(".inputEmail").attr("readonly", false);
					})
					
					/**
					 * 验证邮箱
					 */
					$(".inputEmail").on('keyup blur',function() {
						 var inputEmail = $(this).val();
						 var msg = "";
						 //判断邮箱是否为空
						 if(inputEmail == ""){
							 msg = tipsMsg['tips_emailHide'];
							 $('.emailCode').hide();//邮箱地址为空时,隐藏发送按钮
							 sendMsg(msg,0,$(this));
							 return;
						 }
						 //判断邮箱格式是否正确
						 if(!regexpEmail.test(inputEmail)){
							 msg = tipsMsg['tips_email'];
							 $('.emailCode').hide();//邮箱验证码格式不正确时,隐藏发送按钮
							 sendMsg(msg,0,$(this));
							 return
						 }
						 //验证邮箱是否已注册
						 if(!validEmailAddr(inputEmail, $(this), sendMsg)){
							 return;
						 }
						 //邮箱验证成功
						 msg = tipsMsg['tips_success'];
						 //$(".inputEmail").attr("readonly", true);
						 $('.emailCode').fadeIn(0);//手机验证成功时,显示发送按钮
						// $(".inputEcode").attr("readonly", true);//将邮箱验证码框设置为不允许更改
						 sendMsg(msg,1,$(this));

					});
					
					/**
					 * 验证邮箱验证码
					 */
					$(".inputEcode").on('keyup blur',function() {
						 var inputEcode = $(this).val();
						 var msg = "";
						 //判断邮箱验证码是否为空
						 if(inputEcode == ""){
							 msg = tipsMsg['tips_emailCodeHide'];
							 sendMsg(msg,0,$(this));
							 return;
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
					
					/**
					 * 确认按钮
					 */
					$("#sure").click(function(){
				    	var preEmailAddr = $("#preEmailAddr").val();
				    	//判断原始邮箱地址是否为空
						if(preEmailAddr == ""){
							msg = tipsMsg['tips_emailHide'];
							sendMsg(msg,0,$(this));
							return;
						}
				    	//获取邮箱地址
						var email = $("#emailaddr").val();
						//判断邮箱地址是否为空
						if(email == ''){
							msg = tipsMsg['tips_emailHide'];
							$('.emailCode').hide();
							sendMsg(msg, 0, $("#emailaddr"));
							return;
						}
						//获取邮箱验证码
						var inputCode = $("#inputCode").val();
						//判断邮箱验证码是否为空
						if(inputCode == ''){
							msg = tipsMsg['tips_emailCodeHide'];
							sendMsg(msg, 0, $("#inputCode"));
							return
						}
						//判断输入的邮箱验证码是否正确
						 if(!_dzl_validEmailCode(inputCode, $(this), sendMsg)){
							 return;
						 }
						$.ajax({
							type : "POST",
							url : getFullUrl('./center/modifyEmail'),
							data : {email : email},
							success : function(data){
								//alert(data.success);
								if(data.success == "0"){
									rongDialog({
										title : '绑定邮箱成功',
										type  :  true
									})
									window.location.href = basePath+"/center/safely";
								}else{
									rongDialog({
										title : '绑定邮箱失败',
										type  :  false
									})
								}
							}
						})
				    })
					
				}else if(data.success == "2"){
					//未绑定邮箱
					/**
					 * 验证邮箱
					 */
					$(".inputEmail").on('keyup blur',function() {
						 var inputEmail = $(this).val();
						 var msg = "";
						 //判断邮箱是否为空
						 if(inputEmail == ""){
							 msg = tipsMsg['tips_emailHide'];
							 $('.emailCode').hide();//邮箱地址为空时,隐藏发送按钮
							 sendMsg(msg,0,$(this));
							 return;
						 }
						 //判断邮箱格式是否正确
						 if(!regexpEmail.test(inputEmail)){
							 msg = tipsMsg['tips_email'];
							 $('.emailCode').hide();//邮箱验证码格式不正确时,隐藏发送按钮
							 sendMsg(msg,0,$(this));
							 return
						 }
						 //验证邮箱是否已注册
						 if(!validEmailAddr(inputEmail, $(this), sendMsg)){
							 return;
						 }
						 //邮箱验证成功
						 msg = tipsMsg['tips_success'];
						 $('.emailCode').fadeIn(0);//邮箱验证成功时,显示发送按钮
						 //$(".inputEmail").attr("readonly", true);
						 sendMsg(msg,1,$(this));
					});
					
					/**
					 * 验证邮箱验证码
					 */
					$(".inputEcode").on('keyup blur',function() {
						 var inputEcode = $(this).val();
						 var msg = "";
						 //判断邮箱验证码是否为空
						 if(inputEcode == ""){
							 msg = tipsMsg['tips_emailCodeHide'];
							 sendMsg(msg,0,$(this));
							 return;
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
					
					$("#sure").click(function(){
				    	//获取邮箱地址
						var email = $("#emailaddr").val();
						//判断邮箱地址是否为空
						if(email == ''){
							msg = tipsMsg['tips_emailHide'];
							$('.emailCode').hide();
							sendMsg(msg, 0, $("#emailaddr"));
							return;
						}
						//获取邮箱验证码
						var inputCode = $("#inputCode").val();
						//判断邮箱验证码是否为空
						if(inputCode == ''){
							msg = tipsMsg['tips_emailCodeHide'];
							sendMsg(msg, 0, $("#inputCode"));
							return
						}
						//判断输入的邮箱验证码是否正确
						 if(!_dzl_validEmailCode(inputCode, $(this), sendMsg)){
							 return;
						 }
						$.ajax({
							type : "POST",
							url : getFullUrl('./center/modifyEmail'),
							data : {email : email},
							success : function(data){
								//alert(data.success);
								if(data.success == "0"){
									rongDialog({
										title : '绑定邮箱成功',
										type  :  true
									})
									window.location.href = basePath+"/center/safely";
								}else{
									rongDialog({
										title : '绑定邮箱失败',
										type  :  false
									})
								}
							}
						})
				    })
				}
			}
		})
	}
	
	 //Email验证码倒计时
    var initTime = 60; //设置倒计时
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
     * Email验证码处理
     * @returns
     */
    function sendEmailCode() {
        $('.other-reg').html('');
        $('.emailNum').fadeIn(0);
        $('.inputEmail').attr('readonly', true).addClass('locking');
		$('.reLoad').fadeIn(0);//点击发送按钮后，去除重新注册
		$('.inputEcode').attr('readonly', false).focus();//点击按钮后,允许输入邮箱验证码
		
        numAddE();
        
        //发送邮箱验证码
        _dzl_sendEmailCode(sendMsg);
    }
    $('.emailCode').click(sendEmailCode);

    /**
	 * 判断原始邮箱是否匹配
	 */
	function _validPreEmail(preEmailAddr,inputEl,sendMsg){
		var _success = true;
		// 请求后台数据
		$.ajax({
			type:"POST",
			async:false,
			url:getFullUrl('./center/isPreEmail'),
			data:{preEmailAddr : preEmailAddr},
			success:function(data){
				if(data.success == "2"){
					msg=tipsMsg['tips_errorPreEmail'],
					sendMsg(msg, 0, inputEl),
					_success=false;
 				}
			}
		});
		return _success;
	}
    
    /**
     * 验证邮箱是否已注册
     * @returns
     */
    function validEmailAddr(email, inputEl, sendMsg){
    	var _success = true; 
    	$.ajax({
    		type:"POST",
    		async: false,
    		url:getFullUrl('./user/isEmail'),
    		data:{email:email},
    		success:function(data){
    			if(data.success == "2"){
    				msg = tipsMsg['tips_emailHas'];//提示邮箱已注册
    				$('.emailCode').hide();
    				sendMsg(msg, 0, inputEl);
    				_success = false;
    			}
    		}
    	});
    	return _success;
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
    		data: {email : email,emailcode: emailcode},
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
     * 邮箱发送验证码处理：保存邮箱地址及邮箱验证码
     * @returns
     */
    function _dzl_sendEmailCode(sendMsg){
    	var email=$("#emailaddr").val();
    	$.ajax({
    		type:"POST",
    		async: false,
    		url:getFullUrl('./user/sendEmail'),
    		data:{email:email},
    		success:function(data){
    			if(data.success == "0"){
    				$('.inputEcode').focus();
    				return;
    			}else if(data.success == "2"){
    				msg = tipsMsg['tips_moreTime'];//提示120秒后重新获取
    				//$("#codeHide").hide();//隐藏验证码模块
    				$(".inputEcode").attr("readonly", true);//将邮箱验证码框设置为不允许更改
    				$('.emailNum').fadeOut(0);//隐藏计时
    				$('.emailCode').fadeOut(0);//隐藏发送按钮
    				//$('.goNext').hide(); //隐藏确定按钮
    				sendMsg(msg, 0, $('.emailCode'));
    				return;
    			}else if(data.success == "3"){
    				msg = tipsMsg['tips_moreSend'];//提示一天最多能发10次验证码
    				$("#codeHide").hide();//隐藏验证码模块
    				$('.goNext').hide(); //隐藏确定按钮
    				sendMsg(msg, 0, $('#emailaddr'));
    	            return;
    			}
    		}
    	});
   }
    
});