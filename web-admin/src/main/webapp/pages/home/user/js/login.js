/*---------------------------------------------------------------------
	* Filename:         login.js
	* Description:      login
	* Version:          1.0.1 (2016-10-20)
	* Website:          hn.sun3d.com
	* Author:           JackyZeng (zrongs@vip.qq.com)
---------------------------------------------------------------------*/


/** 登录点击时的处理逻辑 */
function toLogin(){
	var userName = $("#userName").val();
	var pwd = $("#password").val();
	
	//登录名不能为空
	if(userName == ''){
		$("#tipName").html("<font color='red'>登录名不能为空</font>");
	}else{
		$("#tipName").html("");
	}
	
	//密码不能为空
	if(pwd == ''){
		$("#tipPwd").html("<font color='red'>登录密码不能为空</font>");
	}else{
		$("#tipPwd").html("");
	}
	
	//对密码加密
	var password = $.md5(pwd);
	
	//后台登录验证
	$.ajax({
		type: "POST",
		url: getFullUrl('./loginpage'),
		data: {userName:userName,password:password},
		success: function(data){
			if(data.success=="success"){
				rongLoading({
					title : '登录成功',
					clear : false
				});
				//判断是否有前序路径
				if(preurl != ""){
					window.location.href = preurl;
				}else{
					window.location.href = basePath+'/home';
				}
			}else{
			   $("#tipPwd").html("<font color='red'>用户名或密码不正确</font>"); 
			} 
		}
	});
	return false;
}

$(document).ready(function() {
	$('.input-row').click(function(){
		$(this).addClass('active');
	});
	
	$('.input-row').focusout(function(){
		$(this).removeClass('active');
	});
	
	/**
	 * 验证用户名是否为空以及用户登录方式
	 */
	 $("#userName").on('blur',function(){
		 var userName=$("#userName").val()
		 if(userName==""){
		   $("#tipName").html("<font color='red'>用户名不能为空</font>");
		 }/*else if(userName.match(/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/)
				 ||userName.match(/^1[3|4|5|7|8][0-9]\d{8}$/)){
		   //alert("login success");
		 }*/
	 });
	 
	 /**
	  * 验证密码不为空
	  */
	  $("#password").on('blur',function(){
		 var password=$("#password").val()
		 if(password==""){
		  $("#tipPwd").html("<font color='red'>密码不能为空</font>");
		 }
	 }); 
	 
	 /**
	  * 用户登录
	  */
	 $("#login").on('click.wxl', toLogin);
	 
	 
	/** 回车事件 */
	$("body").keydown(function() {
		if (event.keyCode == "13"){
			var userName = $("#userName").val();
			var pwd = $("#password").val();
			
			if(userName == ''){
				$("#userName").focus();
			}else if(pwd == ''){
				$("#password").focus();
			}
			if(userName != '' && pwd != ''){
				toLogin();
			}
		}
	});
})