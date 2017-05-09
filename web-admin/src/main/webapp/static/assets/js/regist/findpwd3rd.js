/*---------------------------------------------------------------------
	* Filename:         findpwd3rd.js
	* Description:      findpwd
	* Version:          1.0.0 (2016-11-07)
	* Website:          hn.sun3d.com
	* Author:           JackyZeng (zrongs@vip.qq.com)
---------------------------------------------------------------------*/


$(document).ready(function() {
	 //提示信息
	 var tipsMsg = {
      tips_success: '', 
      tips_required: '不能为空',
	  tips_pwd: '密码必须为6-18位数字字母或符号',
      tips_pwdequal: '两次密码不一致',
	  tips_pwdHide: '请输入登录密码',
    };
	//正则表达式
	var regexpPassword=/^[\@A-Za-z0-9\!\#\$\%\^\&\*\.\~]{6,18}$/; //密码为字母或数字或符号
	
	/*-------------------------------------------
			注册账号校验
	--------------------------------------------*/
	
	//验证密码格式
	$(".password").on('keyup blur',function() {
		 var password = $(this).val();
		 var msg = "";
		 if(password == ""){
			 msg = tipsMsg['tips_pwdHide'];
			 sendMsg(msg,0,$(this));
			 return
		 }
		 if(!regexpPassword.test(password)){
			 msg = tipsMsg['tips_pwd'];
			 sendMsg(msg,0,$(this));
			 return
		 }
		 msg = tipsMsg['tips_success'];
		 sendMsg(msg,1,$(this));
	});
	
	//验证确认密码
	$('.repassword').on('blur',function() {
		 var repassword = $(this).val();
		 var password = $('.password').val();
		 var msg = "";
		 if(repassword != password){
			 msg = tipsMsg['tips_pwdequal'];
			 sendMsg(msg,0,$(this));
			 return
		 }
		 msg = tipsMsg['tips_success'];
		 sendMsg(msg,1,$(this));
	});
	
	//提示消息框
	function sendMsg(msg,flag,t){
		if(!flag){
			t.next('em').html(msg).addClass('fail').removeClass('sucess');
		}else{
			t.next('em').html(msg).addClass('sucess').removeClass('fail');
		}
	}
})