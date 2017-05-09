/**
* 提示信息
*/
var tipsMsg={
	tips_success:'',
	tips_mobile: '手机号码格式有误',
	tips_phoneHide: '请输入手机号码',
	tips_codeHide:'请输入手机验证码',
	tips_phoneHas:'该手机已绑定',
    tips_errorCode:'输入的验证码有误,请重新输入',
    tips_sendCodeFail:'发送验证码失败',
    tips_moreCode:'验证码已超时，请重新获取验证码',
    tips_errorPrePhone:'原始手机不匹配',
    tips_moreTime:'请于120秒后重新获取验证码',
    tips_moreSend:'一天最多发送十次验证码'
};
//正则表达式:手机号码规则
var regexpPhone = /^1[3|4|5|7|8][0-9]\d{8}$/; 

/**
 * 提示消息框
 * @param msg
 * @param flag
 * @param t
 * @returns
 */
function sendMsg(msg,flag,t){
	t.parent('dd').find('em').html('');
	//alert(t.next('em') +'---2---'+msg);
	if(!flag){
		t.next('em').html(msg).addClass('fail').removeClass('sucess');
	}else{
		t.next('em').html(msg).addClass('sucess').removeClass('fail');
	}
}

/**
 * 页面加载完后处理
 */
$(document).ready(function(e){
	 $(".inputPcode").attr("readonly", true);//将手机验证码框设置为不允许更改
	 
	//判断是否有绑定手机文字
	if($(".tt").length>0){
		$.ajax({
			type : "POST",
			url : getFullUrl('./center/hasSessPhone'),
			success : function(data){
				if(data.success == "1"){
					//已绑定手机
					$(".tt").html('修改手机');
					$("#rePhone").show();
					$("#prePhone").show();
					
					 $(".inputPhone").attr("readonly", true);
					/**
					 * 验证原始手机
					 */
					$("#preMsgPhone").on('keyup blur',function(){
						var preMsgPhone = $(this).val();
						var msg = "";
						//判断原始手机是否为空
						if(preMsgPhone == ""){
							msg = tipsMsg['tips_phoneHide'];
							sendMsg(msg,0,$(this));
							return;
						}
						// 判断原手机是否匹配
						if(!_validPrePhone(preMsgPhone,$(this),sendMsg)){
							 return;
						 }
						 // 验证成功
						 msg = tipsMsg['tips_success'];
						 sendMsg(msg,1,$(this));
						 $(".inputPhone2").attr("readonly", true);
						 $(".inputPcode2").attr("readonly", true);//将手机验证码框设置为不允许更改
						 $('.sendCodeP2').fadeIn(0);//手机验证成功时,显示发送按钮
						 $(".inputPhone").attr("readonly", false);
					})
					
					/**
				    * 验证手机号码
				    */
					$("#msgphone").on('keyup blur',function() {
						//获取手机号码
						 var phone = $(this).val();
						 var msg = "";
						 //判断手机号码是否为空
						 if(phone == ""){
							 msg = tipsMsg['tips_phoneHide'];
							 //手机号码为空时，不允许输入手机验证码
							 //$(".inputPcode").attr("readonly", true);
							 sendMsg(msg,0,$(this));
							 return
						 }
						 //判断手机号码是否格式错误
						 if(!regexpPhone.test(phone)){
							 msg = tipsMsg['tips_mobile'];
							 //手机验证码格式错误，不允许输入手机验证码
							 //$('.inputPCode').attr('readonly', true);
							 sendMsg(msg,0,$(this));
							 return
						 }
						 
						//验证手机是否已注册
						 if(!validPhoneAddr(phone, $(this), sendMsg)){
							 return;
						 }
						 
						 //验证成功后
						 msg = tipsMsg['tips_success'];
						 $(".inputPhone").attr("readonly", true);
						 //$(".inputPcode").attr("readonly", true);//将手机验证码框设置为不允许更改
						 $('.sendCodeP').fadeIn(0);//手机验证成功时,显示发送按钮
						 sendMsg(msg,1,$(this));
					});
					
					/**
					 * 验证手机验证码
					 */
					$(".inputPcode").on('keyup blur',function(){
						//获取手机验证码
						var inputPcode=$(".inputPcode").val();
						var msg="";
						//判断手机验证码是否为空
						if(inputPcode == ""){
							 msg = tipsMsg['tips_codeHide'];
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
						
					})
					/**
					 * 确认后绑定手机
					 */
					$("#sure").click(function(){
						var preMsgPhone = $("#preMsgPhone").val();
						var msg = "";
						//判断原始手机是否为空
						if(preMsgPhone == ""){
							msg = tipsMsg['tips_phoneHide'];
							sendMsg(msg,0,$(this));
							return;
						}
						//获取手机号码
						var phone=$("#msgphone").val();
						//判断手机号码是否为空
						if(phone == ""){
							 msg = tipsMsg['tips_phoneHide'];
							 sendMsg(msg,0,$("#msgphone"));
							 return
						 }
						 //获取手机验证码
						var inputCode=$(".inputPcode").val();
						//判断手机验证码是否为空
						if(inputCode == ""){
							 msg = tipsMsg['tips_codeHide'];
							 sendMsg(msg,0,$(".inputPcode"));
							 return
						}
						//判断输入的手机验证码是否正确
						 if(!_dzl_validPhoneCode(inputCode, $(this), sendMsg)){
							 return;
						 }
						
						//请求后台数据
						$.ajax({
							type : "POST",
							url : getFullUrl('./center/modifyPhone'),
							data : {phone : phone},
							success : function(data){
								if(data.success == "0"){
									rongDialog({
										title : '绑定手机成功',
										type  :  true
									})
									window.location.href = basePath+"/center/safely";
								}else{
									rongDialog({
										title : '绑定失败失败',
										type  :  false
									})
								}
							}
						});
					});
				}else if(data.success == "2"){
					//未绑定邮箱
					/**
				    * 验证手机号码
				    */
					$("#msgphone").on('keyup blur',function() {
						//获取手机号码
						 var phone = $(this).val();
						 var msg = "";
						 //判断手机号码是否为空
						 if(phone == ""){
							 msg = tipsMsg['tips_phoneHide'];
							 //手机号码为空时，不允许输入手机验证码
							 //$(".inputPcode").attr('readonly',true).val('').addClass('readonly');
							 sendMsg(msg,0,$(this));
							 return
						 }
						 //判断手机号码是否格式错误
						 if(!regexpPhone.test(phone)){
							 msg = tipsMsg['tips_mobile'];
							 //手机验证码格式错误，不允许输入手机验证码
							 //$('.inputPCode').attr('readonly', true).val('');
							 sendMsg(msg,0,$(this));
							 return
						 }
						 
						//验证手机是否已注册
						 if(!validPhoneAddr(phone, $(this), sendMsg)){
							 return;
						 }
						 
						 //验证成功后
						 msg = tipsMsg['tips_success'];
						 $(".inputPhone").attr("readonly", true);//将手机框设置为不允许更改
						 $('.sendCodeP').fadeIn(0);//手机验证成功时,显示发送按钮
						 sendMsg(msg,1,$(this));
					});
					
					/**
					 * 验证手机验证码
					 */
					$(".inputPcode").on('keyup blur',function(){
						//获取手机验证码
						var inputPcode=$(".inputPcode").val();
						var msg="";
						//判断手机验证码是否为空
						if(inputPcode == ""){
							 msg = tipsMsg['tips_codeHide'];
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
						// $(".inputPcode").attr("readonly", true);//验证成功时,将手机验证码框设置为不允许更改
						 $('.sendCodeP').fadeOut(0);//隐藏发送按钮
						 $('.phoneNum').fadeOut(0);//隐藏计时
						
					})
						
					/**
					 * 确认后绑定手机
					 */
					$("#sure").click(function(){
						var msg = "";
						//获取手机号码
						var phone=$("#msgphone").val();
						//判断手机号码是否为空
						if(phone == ""){
							 msg = tipsMsg['tips_phoneHide'];
							 sendMsg(msg,0,$("#msgphone"));
							 return
						}
						//获取手机验证码
						var inputCode=$(".inputPcode").val();
						//判断手机验证码是否为空
						if(inputCode == ""){
							 msg = tipsMsg['tips_codeHide'];
							 sendMsg(msg,0,$(".inputPcode"));
							 return
						}
						//判断输入的手机验证码是否正确
						 if(!_dzl_validPhoneCode(inputCode, $(this), sendMsg)){
							 return;
						 }
						
						//请求后台数据
						$.ajax({
							type : "POST",
							url : getFullUrl('./center/modifyPhone'),
							data : {phone : phone},
							success : function(data){
								if(data.success == "0"){
									rongDialog({
										title : '绑定手机成功',
										type  :  true
									})
									window.location.href = basePath+"/center/safely";
								}else{
									rongDialog({
										title : '绑定失败失败',
										type  :  false
									})
								}
							}
						});
					});
				}
			}
		})
	}
	
    /**
     *验证码计时控制 
     */
	var initTime = 60; //设置倒计时

	$('.sendCodeP').click(sendPhoneCode);
	
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
    
   /**
    * 发送手机验证码
    * @returns
    */
    function sendPhoneCode() {
        $('.phoneNum').fadeIn(0);
        $('.inputPhone').attr('readonly', true).addClass('locking');
		$('.inputPcode').attr('readonly', false).focus();//点击发送按钮后，允许输入手机验证码
		$('.sendCodeP').fadeOut(0);
        numAdd();
        
        //短信发送验证码处理:保存手机号码及短信验证码
        _dzl_sendPhoneCode(sendMsg);
    }
    
    /**
	 * 判断原始手机是否匹配
	 */
	function _validPrePhone(preMsgPhone,inputEl,sendMsg){
		var _success = true;
		// 请求后台数据
		$.ajax({
			type:"POST",
			async:false,
			url:getFullUrl('./center/isPrePhone'),
			data:{preMsgPhone : preMsgPhone},
			success:function(data){
				if(data.success == "2"){
					msg=tipsMsg['tips_errorPrePhone'],
					sendMsg(msg, 0, inputEl),
					_success=false;
 				}
			}
		});
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
			data:{phone:phone},
			success:function(data){
				if(data.success == "2"){
					msg = tipsMsg['tips_phoneHas'];//提示手机已绑定
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
	function _dzl_sendPhoneCode(sendMsg){
		// 请求后台数据
	    var msgphone = $("#msgphone").val();
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
					//$('.goNext').hide();
					//$("#PhoneHide").hide();//隐藏验证码模块
					sendMsg(msg, 0, $('.sendCodeP'));
					return;
				}else if(data.success == "3"){
					msg = tipsMsg['tips_moreSend'];//提示一天最多能发10次验证码
					$("#PhoneHide").hide();//隐藏验证码模块
					$('.goNext').hide(); 
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
		var msgphone = $("#msgphone").val();
		var _succ = true;
		$.ajax({
			type:"POST",
			async: false,
			url:getFullUrl('./user/phoneCode'),
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
	
});