/*---------------------------------------------------------------------
	* Filename:         regist.js
	* Description:      regist
	* Version:          1.0.1 (2016-09-27)
	* Website:          hn.sun3d.com
	* Author:           JackyZeng (zrongs@vip.qq.com)
---------------------------------------------------------------------*/
//提示信息
var tipsMsg = {
	tips_success: '', 
	tips_required: '不能为空',
	tips_email: '邮箱地址格式有误',
	tips_mobile: '手机号码格式有误',
	tips_pwd: '密码必须为6-18位数字字母或符号，不能是连续的数字或者字母',
	tips_pwdequal: '两次密码不一致',
	tips_phoneHide: '请输入手机号码',
	tips_emailHide: '请输入邮箱地址',
	tips_pwdHide: '请输入登录密码',
	tips_codeHide: '请输入验证码',
	tips_phoneCodeHide: '请输入手机验证码',
	tips_emailCodeHide: '请输入邮箱验证码',
	tips_phoneHas:'该手机已注册,请重新输入',
    tips_emailHas:'该邮箱已注册,请重新输入',
	tips_hasNickName: '该昵称已占用',
    tips_errorCode:'输入的验证码有误,请重新输入',
    tips_moreCode:'验证码已超时，请重新获取验证码',
    tips_sendCodeFail:'发送验证码失败',
    tips_moreTime:'请于120秒后重新获取验证码',
    tips_birthday: '日期格式有误',
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


//正则表达式
var regexpPhone = /^1[3|4|5|7|8][0-9]\d{8}$/;  //手机号码规则
var regexpEmail =  /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/; //邮箱规则
var regexpPassword= /^(?![^A-Za-z]+$)(?![^0-9]+$)[\x21-\x7e]{6,18}$/;  //密码为字母或数字或符号
var regexBirthday = /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/;	//日期格式

/**
 * 页面加载完处理
 */
$(document).ready(function() {
	$('select:not(.ignore)').niceSelect();      
	//日期组件
	$('.dateChange').on('click',
	    function() {
	        laydate({
	            elem: '#birthday'
	        });
	});

	/*-------------------------------------------
			注册账号校验
	--------------------------------------------*/
	//注册账号事件校验
	
	/**
	 * 验证手机号
	 */
	$(".inputPhone").on('keyup blur',function() {
		 var phoneNumber = $(this).val();
		 var msg = "";
		 //判断手机号是否为空
		 if(phoneNumber == ""){
			 msg = tipsMsg['tips_phoneHide'];
			 //手机号码为空时,不允许输入手机验证码
			 $('.inputPcode').attr('readonly', true);
			 sendMsg(msg,0,$(this));
			 return
		 }
		 //判断手机格式是否正确
		 if(!regexpPhone.test(phoneNumber)){
			 msg = tipsMsg['tips_mobile'];
			 //手机格式不正确时，不允许输入手机验证码
			 $('.inputPcode').attr('readonly', true);
			 sendMsg(msg,0,$(this));
			 return
		 }
		 //验证手机是否已注册
		 if(!validPhoneAddr(phoneNumber, $(this), sendMsg)){
			 $('.inputPcode').attr('readonly', true);
			 return;
		 }
		 //手机验证成功
		 msg = tipsMsg['tips_success'];
         $('.inputPhone').attr('readonly', true).addClass('locking');
		 $('.sendCodeP').fadeIn(0);//手机验证成功时,显示发送按钮
		 sendMsg(msg,1,$(this));
	});
	
	/**
	 * 验证手机验证码
	 */
	$(".inputPcode").on('keyup blur',function() {
		 var inputPcode = $(this).val();
		 var msg = "";
		 //判断手机验证码是否为空
		 if(inputPcode == ""){
			 msg = tipsMsg['tips_phoneCodeHide'];
			 sendMsg(msg,0,$(this));
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
	 * 验证邮箱
	 */
	$(".inputEmail").on('keyup blur',function() {
		 var inputEmail = $(this).val();
		 var msg = "";
		 //判断邮箱是否为空
		 if(inputEmail == ""){
			 msg = tipsMsg['tips_emailHide'];
			 $(".inputEcode").attr("readonly", true);
			 $('.emailCode').hide();//邮箱地址为空时,隐藏发送按钮
			 sendMsg(msg,0,$(this));
			 return;
		 }
		 //判断邮箱格式是否正确
		 if(!regexpEmail.test(inputEmail)){
			 msg = tipsMsg['tips_email'];
			 $(".inputEcode").attr("readonly", true);
			 $('.emailCode').hide();//邮箱验证码格式不正确时,隐藏发送按钮
			 sendMsg(msg,0,$(this));
			 return
		 }
		 //验证邮箱是否已注册
		 if(!validEmailAddr(inputEmail, $(this), sendMsg)){
			 $(".inputEcode").attr("readonly", true);
			 return;
		 }
		 //邮箱验证成功
		 msg = tipsMsg['tips_success'];
		 $('.emailCode').fadeIn(0);//邮箱验证成功时,显示发送按钮
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
	 * 验证密码
	 */
	$(".password").on('keyup blur',function() {
		 var password = $(this).val();
		 var msg = "";
		 //判断密码是否为空
		 if(password == ""){
			 msg = tipsMsg['tips_pwdHide'];
			 sendMsg(msg,0,$(this));
			 return
		 }
		 //判断密码格式是否正确
		// console.log(regexpPassword+">"+password+"test==>>"+(regexpPassword.test(password)) )
		 if(!regexpPassword.test(password)){ 
			 msg = tipsMsg['tips_pwd'];
			 sendMsg(msg,0,$(this));
			 return
		 }
		 msg = tipsMsg['tips_success'];
		 sendMsg(msg,1,$(this));
	});
	
	/**
	 * 验证确认密码
	 */
	$('.repassword').on('blur',function() {
		 var repassword = $(this).val();
		 var password = $('.password').val();
		 var msg = "";
		 //判断两次密码是否一致
		 if(repassword != password){
			 msg = tipsMsg['tips_pwdequal'];
			 sendMsg(msg,0,$(this));
			 return
		 }
		 msg = tipsMsg['tips_success'];
		 sendMsg(msg,1,$(this));
	});
	
	/**
	 * 会员注册条款展开
	 */
	$('.abBtn').on(
		'click',
		function(){
			$(this).fadeOut(300,function(){
				$('.mask').fadeIn(300);
				$(this).next('div').stop(1,0).animate({left:"-100px",bottom:"0px",height:"451px",width:"800px"},function(){
					$(this).find('.readCont').fadeIn(500);
				});
			});
		}
	);
	
	/**
	 * 会员注册条款关闭
	 */
	$('.ck_agree,.closeRead').on(
		'click',
		function(){
			$('.abBtn').hide(300,function(){
				$('.readCont').hide(0,function(){
					$('.readClause').stop(1,0).animate({left:"0px",bottom:"0px",height:"40px",width:"311px"});
					setTimeout(function(){
						$('.abBtn').fadeIn(300);	
					},400);
				});
			});
			$('.mask').fadeOut(300);
		}
	);
	
	
	/*-------------------------------------------
			验证码动作
	--------------------------------------------*/
    var initTime = 150; //设置倒计时
    $('.other-reg a').toggle(function() {
        $('.main-cont input').val('');
        $(this).html('手机注册');
        $('.phoneType,.phoneCode').hide();
        $('.mailType').fadeIn(0);
        $('.inputCode').attr('readonly', true);
        $('.inputEmail').attr('readonly', false).removeClass('locking');
        countdown = 0;
        countdownE = initTime;
    },
    function() {
        $('.main-cont input').val('');
        $(this).html('邮箱注册');
        $('.mailType,.emailNum,.emailCode').hide();
        $('.phoneType,.phoneCode').fadeIn(0);
        $('.inputPhone').attr('readonly', false).removeClass('locking');
        countdown = initTime;
        countdownE = 0;
    });
    
    //点击发送按钮
    $('.emailCode').click(sendEmailCode);
    $('.sendCodeP').click(sendPhoneCode);
  
    //手机验证码倒计时
    var countdown = initTime;
    function numAdd() {
        $('.other-reg').html('');
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

    /**
     * 手机验证码处理
     * @returns
     */
    function sendPhoneCode() {
    	$('.other-reg').html('');
        $('.phoneNum').fadeIn(0);
        $('.inputPhone').attr('readonly', true).addClass('locking');
		$('.reLoad').fadeIn(0);//点击发送按钮后，去除重新注册
		$('.inputPcode').attr('readonly', false).focus();//点击发送按钮后，允许输入手机验证码
        //$(this).hide();
        numAdd();
        
       //短信发送验证码处理:保存手机号码及短信验证码
        _dzl_sendPhoneCode();
    }
    
	/**
	 * 注册信息的保存:第一步
	 */
    _dzl_regist1_submit();
    
   /**
	 * 注册信息的保存：第二步
	 */
    _dzl_regist2_submit();

});

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
		dataType : 'json',
		data:{email : email},
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

//判断昵称是否已存在
function hasNickName(){
	var nickname = $("#nickname").val();
	var _success = true; 
	$.ajax({
		type : "POST",
		async: false,
		url : getFullUrl('./user/hasNickName'),
		dataType : 'json',
		data : {nickname : nickname},
		success : function(data){
			if(data.success == "1"){
				msg = tipsMsg['tips_hasNickName'];//提示昵称已存在
				sendMsg(msg, 0, $("#nickname"));
				_success = false;
			}
		} 
	})
	return _success;
}

/**
 * 验证手机是否已注册
 * @returns
 */
function validPhoneAddr(phone,inputEl,sendMsg){
	var _success = true; 
	  $.ajax({
		type:"POST",
		async: false,
		url:getFullUrl('./user/isPhone'),
		  dataType : 'json',
		data:{phone : phone},
		success:function(data){
			if(data.success == "2"){
				msg = tipsMsg['tips_phoneHas'];//提示手机已注册
				$('.inputCode').hide();
				sendMsg(msg, 0, inputEl);
				_success = false;
			}
		}
	});
	return _success;
}


/**
 * 短信发送验证码处理:保存手机号码及短信验证码
 * @returns
 */
function _dzl_sendPhoneCode(){
		// 请求后台数据
	    var msgphone = $("#msgphone").val();
		$.ajax({
			type:"POST",
			async: false,
			url:getFullUrl('./user/sendPhone'),
			dataType : 'json',
			data:{msgphone:msgphone},
			success:function(data){
				if(data.success == "0"){
					$('.inputPcode').focus();
					return;
				}else if(data.success == "2"){
					msg = tipsMsg['tips_moreTime'];//提示120秒后重新获取
					$(".inputPcode").attr("readonly", true);//将手机验证码框设置为不允许更改
					$('.phoneNum').fadeOut(0);//隐藏计时
					$('.sendCodeP').fadeOut(0);//隐藏按钮
					//$("#PhoneHide").hide();//隐藏验证码模块
					sendMsg(msg, 0, $('.sendCodeP'));
					return;
				}else if(data.success == "3"){
					msg = tipsMsg['tips_moreSend'];//提示一天最多能发10次验证码
					$("#PhoneHide").hide();//隐藏验证码模块
					sendMsg(msg, 0, $('.inputPhone'));
					return;
				}
			}
		}); 	
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
		dataType : 'json',
		data:{email:email},
		success:function(data){
			if(data.success == "0"){
				$('.inputEcode').focus();
				return;
			}else if(data.success == "2"){
				msg = tipsMsg['tips_moreTime'];//提示120秒后重新获取
				$(".inputEcode").attr("readonly", true);//将邮箱验证码框设置为不允许更改
				$('.emailNum').fadeOut(0);//隐藏计时
				$('.emailCode').fadeOut(0);//隐藏发送按钮
				//$("#codeHide").hide();//隐藏验证码模块
				sendMsg(msg, 0, $('.emailCode'));
				return;
			}else if(data.success == "3"){
				msg = tipsMsg['tips_moreSend'];//提示一天最多能发10次验证码
				//$(".inputEcode").attr("readonly", true);//将邮箱验证码框设置为不允许更改
				//$('.emailNum').fadeOut(0);//隐藏计时
				//$('.emailCode').fadeOut(0);//隐藏发送按钮
				$("#codeHide").hide();//隐藏验证码模块
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
		dataType : 'json',
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
		dataType : 'json',
		data:{msgphone : msgphone, msgcontent : msgcontent},
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

/**
 * 注册用户信息第一步
 * @returns
 */
function _dzl_regist1_submit(){
	$("#gonext").click(function(e){
		//判断用户是选择邮箱登录还是手机登录
		var isPhone = $('dl.phoneType').css('display') == 'block';
		  
		if(isPhone){
			//获取手机地址
			var msgphone = $("#msgphone").val();
			//判断手机号码是否为空
			if(msgphone == ''){
				msg = tipsMsg['tips_phoneHide'];
				sendMsg(msg, 0, $("#msgphone"));
				return;
			}
			//获取手机验证码
			var msgcontent = $("#msgcontent").val();
			//判断手机验证码是否为空
			if(msgcontent == ''){
				 msg = tipsMsg['tips_phoneCodeHide'];
				 sendMsg(msg, 0, $("#msgcontent"));
				 return
			}
			 //判断输入的手机验证码是否正确
			 if(!_dzl_validPhoneCode(msgcontent, $(this), sendMsg)){
				 return;
			 }
		}else{
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
			
        }
		
		//获取密码及确认密码
		var pwd = $("#password").val();
		//alert(pwd);
		var surePwd = $("#surePwd").val();
		//判断密码是否为空
		if(pwd == ''){
			msg = tipsMsg['tips_pwdHide'];
			sendMsg(msg, 0, $("#password"));
			return;
		}
		//判断确认密码是否为空
		if(surePwd == ''){
			msg = tipsMsg['tips_pwdequal'];
			sendMsg(msg, 0, $("#surePwd"));
			return
		}
		//判断密码格式是否正确
		 if(!regexpPassword.test(pwd)){
			 msg = tipsMsg['tips_pwd'];
			 sendMsg(msg,0,$(this));
			 return;
		 }
		//判断两次密码是否一致
		if(pwd != surePwd){
			return;
		}
		//对密码进行加密
		var pwdmd5 = $.md5(pwd);
		//alert(pwdmd5);
		
		//是否勾选同意条款
		var IsCheck = $('.ck_agree').is(':checked');
		//判断是否勾选同意条款
		if(!IsCheck){
			$("#tipNext").html("<font color='red'>请认真阅读并同意合同条款</font>");
			return;
		}else{
			$("#tipNext").html("");
		}
		//请求后台数据
		$.ajax({
   		     type: "POST",
			 url: getFullUrl('./user/saveRegist'),
   		     data: {phone: msgphone, password: pwdmd5, email: email},
			 dataType : 'json',
   		     success: function(data){
	   			 if(data.success == "0"){
	   			    //alert(data.id);
	   				window.location.href = basePath+"/toregist_2?id="+data.id;
	   			 }else{
					 rongDialog({
						 type : false,
						 title : "提交注册失败",
						 time : 3*1000
					 });
				 }
   		     }
   	 	});
	 });
}

/**
 * 注册用户信息第二步
 * @returns
 */
function _dzl_regist2_submit(){
	$("#go_next").click(function(){
		  //获取用户昵称
		  var nickname = $("#nickname").val();
		  var sex = $("input[name='radioSex']:checked").val();
		  var job = $("#job option:selected").val();
		  var birthday = $("#birthday").val();
		  var qq = $("#qq").val();
		  var id = $("#qq").attr("data");
		  var wx = $("#wx").val();
		  
		  //验证日期格式
		  if($("#birthday").val() && !regexBirthday.test($("#birthday").val())) { 
			  msg = tipsMsg['tips_birthday'];
			  sendMsg(msg ,0 ,$("#birthday"));
			  return;
		  }
		  
		  //昵称是否已存在
		  if(!hasNickName()){
			  return;
		  }
		  //submit
		  $.ajax({
			  type : "POST",
			  async : false,
			  url : getFullUrl('./user/saveRegist2'),
			  dataType : 'json',
			  data:{ 
					  id : id,
					  nickname : nickname,
					  sex :sex,
					  job : job,
					  birthday : birthday,
					  qq : qq,
					  wx : wx
			  },
		      success : function(data){
		    	  //清空错误信息
		    	  $("em").html('');
				  if(data.success == "0"){
					  window.location.href = basePath+"/toregist_3";
				  }else{
					  var fieldMap = {
							  "nickname": $("#nickname"),
							  "sex": $("input[name='radioSex']").parent('div'),
							  "job": $('#job').parent('div'),
							  "birthday": $("#birthday").parent('div'),
							  "qq": $("#qq"),
							  "wx": $("#wx")
					  };
					  var err_filed = data.errfield;
					  var errmsg = data.errMsg; 
					  sendMsg(errmsg, 0, fieldMap[err_filed]);
					  return;
				  }
			  }
		  });
		  
	   });
}
