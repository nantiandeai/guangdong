/**
 * 提示信息
 */
 var tipsMsg={
	  tips_success: '',
	  tips_pwd: '密码必须为6-18位数字字母或符号',
	  tips_pwdequal: '两次密码不一致',
	  tips_pwdPre: '请输入原始密码',
	  tips_pwdnew: '请输入新密码',
	  tips_pwdSure: '请确认新密码',
	  tips_errorPwd:'原始密码不匹配'
};
 
var regexpPassword=/^(?![^A-Za-z]+$)(?![^0-9]+$)[z\x21-x7e]{6,18}$/;  //密码为字母或数字或符号
// 提示消息框
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
$(document).ready(function(e) {
	if($(".tt").length>0){
		$.ajax({
			type : "POST",
			url : getFullUrl('./center/hasSessPwd'),
			success : function(data){
				if(data.success == "1"){
					//已设置登录密码
					$(".tt").html('修改密码');
					$("#showPwd").show();
					/**
					 * 验证原始密码
					 */
					$("#prePwd").on('keyup blur',function() {
						 // 获取原始密码
						 var prePwd = $(this).val();
						 // 对密码加密
						 var password=$.md5(prePwd);
						 var msg = "";
						 // 判断原始密码是否为空
						 if(prePwd == ""){
							 msg = tipsMsg['tips_pwdPre'];
							 sendMsg(msg,0,$(this));
							 return
						 }
						 // 判断原始验证码是否匹配
						 if(!_validPrePwd(password,$(this),sendMsg)){
							 return;
						 }
						 
						 // 验证成功
						 msg = tipsMsg['tips_success'];
						 sendMsg(msg,1,$(this));
					});
					
					/**
					 * 验证新密码
					 */
					$("#newPwd").on('keyup blur',function() {
						 var newPwd = $(this).val();
						 var msg = "";
						 // 判断新密码是否为空
						 if(newPwd == ""){
							 msg = tipsMsg['tips_pwdnew'];
							 sendMsg(msg,0,$(this));
							 return
						 }
						 //判断新密码格式是否正确
						 if(!regexpPassword.test(newPwd)){
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
					$('#surePwd').on('blur',function() {
						 var surePwd = $(this).val();
						 var newPwd = $('#newPwd').val();
						 var msg = "";
						 // 判断确认密码是否为空
						 if(surePwd == ""){
							 msg = tipsMsg['tips_pwdSure'];
							 sendMsg(msg,0,$(this));
							 return
						 }
						 // 判断两次密码是否一致
						 if(surePwd != newPwd){
							 msg = tipsMsg['tips_pwdequal'];
							 sendMsg(msg,0,$(this));
							 return
						 }
						 msg = tipsMsg['tips_success'];
						 sendMsg(msg,1,$(this));
					});
					
					/**
					 * 确认后修改密码
					 */
					$("#sure").click(function(){
						// 获取原始密码
						var prePwd=$("#prePwd").val();
						// 判断判断原始密码是否为空
						if(prePwd == ""){
							msg = tipsMsg['tips_pwdPre'],
							sendMsg(msg, 0 ,$("#prePwd"));
							return
						}
						// 获取新密码
						var newPwd=$("#newPwd").val();
						// 判断新密码是否为空
						if(newPwd == ""){
							msg = tipsMsg['tips_pwdnew'];
							sendMsg(msg, 0, $("#newPwd"));
							return
						}
						//判断密码格式是否正确
						if(!regexpPassword.test(newPwd)){
							msg = tipsMsg['tips_pwd'];
							sendMsg(msg,0,$(this));
							return;
						 }
						// 获取确认密码
						var surePwd=$("#surePwd").val();
						//判断确认密码是否为空
						if(surePwd == ""){
							msg = tipsMsg['tips_pwdSure']
							sendMsg(msg, 0, $("#surePwd"));
							return
						}
						//判断两次密码是否一致
						if(newPwd != surePwd){
							msg = tipsMsg['tips_pwdequal'];
							sendMsg(msg,0,$(this));
							return
						}
						
						// 对密码加密
						var newPwdMd5 = $.md5(newPwd);
						// 请求后台数据
						$.ajax({
							type : "POST",
							url : getFullUrl('./center/modifyPwd'),
							data : {newPwdMd5 : newPwdMd5},
							success : function(data){
								if(data.success == "0"){
									rongDialog({
										title : '修改密码成功',
										type  :  true
									})
									window.location.href = basePath+"/center/safely";
								}else{
									rongDialog({
										title : '设置失败',
										type  :  false
									})	
								}
							}
						});
					});
					
				}else if(data.success == "2"){
					//未设置密码
					/**
					 * 验证新密码
					 */
					$("#newPwd").on('keyup blur',function() {
						 var newPwd = $(this).val();
						 var msg = "";
						 // 判断新密码是否为空
						 if(newPwd == ""){
							 msg = tipsMsg['tips_pwdnew'];
							 sendMsg(msg,0,$(this));
							 return
						 }
						 //判断新密码格式是否正确
						 if(!regexpPassword.test(newPwd)){
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
					$('#surePwd').on('blur',function() {
						 var surePwd = $(this).val();
						 var newPwd = $('#newPwd').val();
						 var msg = "";
						 // 判断确认密码是否为空
						 if(surePwd == ""){
							 msg = tipsMsg['tips_pwdSure'];
							 sendMsg(msg,0,$(this));
							 return
						 }
						 // 判断两次密码是否一致
						 if(surePwd != newPwd){
							 msg = tipsMsg['tips_pwdequal'];
							 sendMsg(msg,0,$(this));
							 return
						 }
						 msg = tipsMsg['tips_success'];
						 sendMsg(msg,1,$(this));
					});
					/**
					 * 确认后修改密码
					 */
					$("#sure").click(function(){
						// 获取新密码
						var newPwd=$("#newPwd").val();
						// 判断新密码是否为空
						if(newPwd == ""){
							msg = tipsMsg['tips_pwdnew'];
							sendMsg(msg, 0, $("#newPwd"));
							return
						}
						//判断密码格式是否正确
						if(!regexpPassword.test(newPwd)){
							msg = tipsMsg['tips_pwd'];
							sendMsg(msg,0,$(this));
							return;
						 }
						// 获取确认密码
						var surePwd=$("#surePwd").val();
						//判断确认密码是否为空
						if(surePwd == ""){
							msg = tipsMsg['tips_pwdSure']
							sendMsg(msg, 0, $("#surePwd"));
							return
						}
						//判断两次密码是否一致
						if(newPwd != surePwd){
							msg = tipsMsg['tips_pwdequal'];
							sendMsg(msg,0,$(this));
							return
						}
						
						// 对密码加密
						var newPwdMd5 = $.md5(newPwd);
						// 请求后台数据
						$.ajax({
							type : "POST",
							url : getFullUrl('./center/modifyPwd'),
							data : {newPwdMd5 : newPwdMd5},
							success : function(data){
								if(data.success == "0"){
									rongDialog({
										title : '修改密码成功',
										type  :  true
									})
									window.location.href = basePath+"/center/safely";
								}else{
									rongDialog({
										title : '设置失败',
										type  :  false
									})	
								}
							}
						});
					});
				}
				
			}
		});			
	}
	
	/**
	 * 判断原始密码是否匹配
	 */
	function _validPrePwd(password,inputEl,sendMsg){
		var _success = true;
		// 请求后台数据
		$.ajax({
			type:"POST",
			async:false,
			url:getFullUrl('./center/isPwd'),
			data:{password:password},
			success:function(data){
				if(data != "success"){
					msg=tipsMsg['tips_errorPwd'],
					sendMsg(msg, 0, inputEl),
					_success=false;
 				}
			}
		});
		return _success;
	}

})