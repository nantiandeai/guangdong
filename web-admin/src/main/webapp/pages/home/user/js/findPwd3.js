/*---------------------------------------------------------------------
	* Filename:         findpwd3rd.js
	* Description:      findpwd
	* Version:          1.0.0 (2016-11-07)
	* Website:          hn.sun3d.com
	* Author:           JackyZeng (zrongs@vip.qq.com)
---------------------------------------------------------------------*/
//提示信息
var tipsMsg = {
	tips_success: '', 
	tips_required: '不能为空',
	tips_pwd: '密码必须为6-18位数字字母或符号',
	tips_pwdequal: '两次密码不一致',
	tips_pwdHide: '请输入登录密码'
};

//提示消息框
function sendMsg(msg,flag,t){
	if(!flag){
		t.next('em').html(msg).addClass('fail').removeClass('sucess');
	}else{
		t.next('em').html(msg).addClass('sucess').removeClass('fail');
	}
}

/**
 * 页面加载完后处理
 */
$(document).ready(function() {
	 
	//正则表达式
	//var regexpPassword=/^(?![^A-Za-z]+$)(?![^0-9]+$)[z\x21-x7e]{6,18}$/;  //密码为字母或数字或符号
	var regexpPassword=/^(?![A-Z]+$)(?![a-z]+$)(?!\d+$)(?![\W_]+$)\S{6,18}$/;  //密码为字母或数字或符号

	/*-------------------------------------------
			注册账号校验
	--------------------------------------------*/
	
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
	 * 修改密码
	 */
	$("#modifyPwd").click(function(){
		//alert("modify");
		//获取密码
		var password = $(".password").val();
		 //判断密码是否为空
		 if(password == ""){
			 msg = tipsMsg['tips_pwdHide'];
			 sendMsg(msg,0,$(this));
			 return
		 }
		 //判断密码格式是否正确
		 if(!regexpPassword.test(password)){
			 msg = tipsMsg['tips_pwd'];
			 sendMsg(msg,0,$(this));
			 return
		 }
		//获取确认密码
		var repassword = $(".repassword").val();
		//判断确认密码是否为空
		 if(repassword == ""){
			 msg = tipsMsg['tips_pwdHide'];
			 sendMsg(msg,0,$(this));
			 return
		 }
		 //判断两次密码是否一致
		 if(repassword != password){
			 msg = tipsMsg['tips_pwdequal'];
			 sendMsg(msg,0,$(this));
			 return
		 }
		//对密码加密
		var pwdMd5 = $.md5(password);
		
		 //请求后台数据
		 $.ajax({
			 type : "POST",
			 url : getFullUrl('./user/findPwd'),
			 data : {pwdMd5 : pwdMd5},
			 success : function(data){
				// alert(data.success);
				 if(data.success == "0"){
					 rongDialog({
							title : '修改密码成功',
							type  :  true
						})
					 $(".main .clearfix").hide();
					 $(".reg-msg").show();
				 }else{
					 alert("修改失败");
				 }
				 
			 }
		 });
		
	});
	
})