/*---------------------------------------------------------------------
	* Filename:         findpwd1st.js
	* Description:      findpwd
	* Version:          1.0.0 (2016-08-31)
	* Website:          hn.sun3d.com
	* Author:           JackyZeng (zrongs@vip.qq.com)
---------------------------------------------------------------------*/


$(document).ready(function() {
	var actionTime = 400;
	$('.mobileFind a').click(function(){
		$t = $('.mobileFind');
		$(this).fadeOut(actionTime);
		$t.siblings().fadeOut(actionTime);
		setTimeout(function(){
			$t.animate({left:'92px',top:'-10px'},actionTime/2);
			$('.help').fadeOut(actionTime);
			$('.reLoad').fadeIn(actionTime);
		},actionTime);
		setTimeout(function(){
			$('.phone').fadeIn(actionTime).find('input').focus();
		},actionTime*2);
	});
	
	$('.emailFind a').click(function(){
		$t = $('.emailFind');
		$(this).fadeOut(actionTime);
		$t.siblings().fadeOut(actionTime);
		setTimeout(function(){
			$t.animate({left:'78px'},actionTime);
			$('.help').fadeOut(actionTime);
			$('.reLoad').fadeIn(actionTime);
		},actionTime);
		setTimeout(function(){
			$('.mail').fadeIn(actionTime).find('input').focus();
		},actionTime*2);
		
	});
	
	//提示信息
	 var tipsMsg = {
      tips_success: '', 
      tips_required: '不能为空',
      tips_email: '邮箱地址格式有误',
      tips_mobile: '手机号码格式有误',
	  tips_phoneHide: '请输入手机号码',
	  tips_emailHide: '请输入邮箱地址',
	  tips_phoneCodeHide: '请输入手机验证码',
	  tips_emailCodeHide: '请输入邮箱验证码'
    };
	
	//正则表达式
	var regexpPhone = /^1[3|4|5|7|8][0-9]\d{8}$/;  //手机号码规则
	var regexpEmail =  /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/; //邮箱规则
	
	//验证手机号
	$(".inputPhone").on('keyup blur',function() {
		 var phoneNumber = $(this).val();
		 var msg = "";
		 var t = $(".inputPhone");
		 if(phoneNumber == ""){
			 msg = tipsMsg['tips_phoneHide'];
			 sendMsg(msg,0,$(this));
			 t.parent().parent().next('dl').hide(0);
			 return
		 }
		 if(!regexpPhone.test(phoneNumber)){
			 msg = tipsMsg['tips_mobile'];
			 sendMsg(msg,0,$(this));
			 t.parent().parent().next('dl').hide(0);
			 return
		 }
		 msg = tipsMsg['tips_success'];
		 sendMsg(msg,1,$(this));
		 t.parent().parent().next('dl').fadeIn(0);
	});
	
	//验证邮箱地址
	$(".inputEmail").on('keyup blur',function() {
		 var inputEmail = $(this).val();
		 var msg = "";
		 var t = $(".inputEmail");
		 if(inputEmail == ""){
			 msg = tipsMsg['tips_emailHide'];
			 sendMsg(msg,0,$(this));
			 t.parent().parent().next('dl').hide(0);
			 return
		 }
		 if(!regexpEmail.test(inputEmail)){
			 msg = tipsMsg['tips_email'];
			 sendMsg(msg,0,$(this));
			 t.parent().parent().next('dl').hide(0);
			 return
		 }
		 msg = tipsMsg['tips_success'];
		 sendMsg(msg,1,$(this));
		 t.parent().parent().next('dl').fadeIn(0);
	});
	
	//提示消息框
	function sendMsg(msg,flag,t){
		if(!flag){
			t.next('label').html(msg).addClass('fail').removeClass('sucess');
		}else{
			t.next('label').html(msg).addClass('sucess').removeClass('fail');
		}
	}
})