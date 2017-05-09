/*---------------------------------------------------------------------
	* Filename:         findpwd1st.js
	* Description:      findpwd
	* Version:          1.0.0 (2016-08-31)
	* Website:          hn.sun3d.com
	* Author:           JackyZeng (zrongs@vip.qq.com)
---------------------------------------------------------------------*/
//提示信息
 var tipsMsg = {
	  tips_success: '', 
	  tips_required: '不能为空',
	  tips_email: '邮箱地址格式有误',
	  tips_mobile: '手机号码格式有误',
	  tips_phoneHide: '请输入手机号码',
	  tips_emailHide: '请输入邮箱地址',
	  tips_phoneCodeHide: '请输入手机验证码',
	  tips_emailCodeHide: '请输入邮箱验证码',
	  tips_phoneHas:'该手机未注册,请重新输入',
	  tips_emailHas:'该邮箱未注册,请重新输入'
};

//提示消息框
function sendMsg(msg,flag,t){
	if(!flag){
		t.next('em').html(msg).addClass('fail').removeClass('success');
	}else{
	t.next('em').html(msg).addClass('success').removeClass('fail');
	}
}

/**
 * 页面加载完后处理
 */
$(document).ready(function() {
	
	var actionTime = 400;
	$('.mobileFind a').click(function(){
		$t = $('.mobileFind');
		$(this).fadeOut(actionTime);
		$t.siblings().fadeOut(actionTime);
		setTimeout(function(){
			$t.animate({left:'92px',top:'-10px'},actionTime/2);
			$('.help').fadeOut(actionTime);
			//$('.reLoad').fadeIn(actionTime);
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
	
	
	//正则表达式
	var regexpPhone = /^1[3|4|5|7|8][0-9]\d{8}$/;  //手机号码规则
	var regexpEmail =  /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/; //邮箱规则
	
	//验证手机号
	$(".inputPhone").on('keyup blur',function() {
		//获取手机号码
		 var phoneNumber = $(this).val();
		 var msg = "";
		 var t = $(".inputPhone");
		 //判断手机号码是否为空
		 if(phoneNumber == ""){
			 msg = tipsMsg['tips_phoneHide'];
			 sendMsg(msg,0,$(this));
			 t.parent().parent().next('dl').hide(0);
			 return
		 }
		 //判断手机格式是否正确
		 if(!regexpPhone.test(phoneNumber)){
			 msg = tipsMsg['tips_mobile'];
			 sendMsg(msg,0,$(this));
			 t.parent().parent().next('dl').hide(0);
			 return
		 }
		 
		//验证手机是否已注册
		 if(!validPhoneAddr(phoneNumber, $(this), sendMsg)){
			 return;
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
		 //判断手机是否为空
		 if(inputEmail == ""){
			 msg = tipsMsg['tips_emailHide'];
			 sendMsg(msg,0,$(this));
			 t.parent().parent().next('dl').hide(0);
			 return
		 }
		 //验证手机格式是否正确
		 if(!regexpEmail.test(inputEmail)){
			 msg = tipsMsg['tips_email'];
			 sendMsg(msg,0,$(this));
			 t.parent().parent().next('dl').hide(0);
			 return
		 }
		//验证邮箱是否已注册	已注册：go 未注册：stop
		 if(!validEmailAddr(inputEmail, $(this), sendMsg)){
			 return;
		 }
		 msg = tipsMsg['tips_success'];
		 sendMsg(msg,1,$(this));
		 t.parent().parent().next('dl').fadeIn(0);
	});

	/**
	 * 通过手机修改密码
	 */
	$("#phoneFind").click(function(){
		$(".inputEmail").attr("disabled","disabled");
		//alert(123);
		//获取手机号码
		$("#go_phone").click(function(){
			var phone = $(".inputPhone").val();
			//alert(phone);
			//判断手机号码是否为空
			if(phone == ''){
				msg = tipsMsg['tips_phoneHide'];
				$('.inputCode').attr('readonly', true).val('').addClass('readonly');
				sendMsg(msg, 0, $(".inputPhone"));
				return;
			}
			//验证手机是否已注册
			 if(!validPhoneAddr(phone, $(this), sendMsg)){
				 return;
			 }
			//请求后台数据
			$.ajax({
				type : "POST",
				url : getFullUrl('./user/selectFindPwd'),
				data : {phone : phone},
				success : function(data){
					if(data.success == "0"){
						//window.location.href = basePath+"/user/findPwd2?id="+data.id;
						$('#findPWDForm #id').val(data.id);
						
						$('#findPWDForm').submit();
					}else{
						alert("查找失败");
					}
				}
			})
		});
	});
	
	/**
	 * 通过邮箱修改密码
	 */
	$("#emailFind").click(function(){
		$(".inputPhone").attr("disabled","disabled");
		//获取邮箱地址
		$("#go_email").click(function(){
			var email = $(".inputEmail").val();
			//判断邮箱地址是否为空
			if(email == ''){
				msg = tipsMsg['tips_emailCodeHide'];
				sendMsg(msg, 0, $(".inputEmail"));
				return
			}
			//验证邮箱是否已注册	已注册：go 未注册：stop
			if(!validEmailAddr(email, $(this), sendMsg)){
				 return;
			}
			
			//请求后台数据
			$.ajax({
				type : "POST",
				url : getFullUrl('./user/selectFindPwd'),
				data : {email : email},
				success : function(data){
					if(data.success == "0"){
						$('#findPWDForm #id').val(data.id);
						$('#findPWDForm').submit();
						//window.location.href = basePath+"/user/findPwd2?id="+data.id;
					}else{
						alert("查找失败");
					}
				}
			})
		});
	});


	//默认使用手机找回密码
	$('.mobileFind a').trigger('click');
});

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
		data:{phone : phone},
		success:function(data){
			if(data.success == "1"){
				msg = tipsMsg['tips_phoneHas'];//提示手机未注册
				sendMsg(msg, 0, inputEl);
				_success = false;
			}
		}
	});
	return _success;
}
/**
 * 验证邮箱是否已注册	已注册：go 未注册：stop
 * @returns
 */
function validEmailAddr(email, inputEl, sendMsg){
	var _success = true; 
	$.ajax({
		type:"POST",
		async: false,
		url:getFullUrl('./user/isEmail'),
		data:{email : email},
		success:function(data){
			if(data.success == "1"){
				msg = tipsMsg['tips_emailHas'];//提示邮箱未注册
				sendMsg(msg, 0, inputEl);
				_success = false;
			}
		}
	});
	return _success;
}