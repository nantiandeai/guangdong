$(document).ready(function(e) {
	
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
	
	/*-------------------------------------------
			注册账号校验
	--------------------------------------------*/
	//注册账号事件校验
	
	//验证手机号
	$(".inputPhone").on('keyup blur',function() {
		 var phoneNumber = $(this).val();
		 var msg = "";
		 if(phoneNumber == ""){
			 msg = tipsMsg['tips_phoneHide'];
			 $('.inputCode').attr('readonly', true).val('').addClass('readonly');
			 sendMsg(msg,0,$(this));
			 return
		 }
		 if(!regexpPhone.test(phoneNumber)){
			 msg = tipsMsg['tips_mobile'];
			 $('.inputCode').attr('readonly', true).val('');
			 sendMsg(msg,0,$(this));
			 return
		 }
		 msg = tipsMsg['tips_success'];
		 $('.inputCode').attr('readonly', false).removeClass('readonly');
		 sendMsg(msg,1,$(this));
	});
	
	//验证邮箱地址
	$(".inputEmail").on('keyup blur',function() {
		 var inputEmail = $(this).val();
		 var msg = "";
		 if(inputEmail == ""){
			 msg = tipsMsg['tips_emailHide'];
			 $('.emailCode').hide();
			 sendMsg(msg,0,$(this));
			 return
		 }
		 if(!regexpEmail.test(inputEmail)){
			 msg = tipsMsg['tips_email'];
			 $('.emailCode').hide();
			 sendMsg(msg,0,$(this));
			 return
		 }
		 msg = tipsMsg['tips_success'];
		 $('.emailCode').fadeIn(0);
		 sendMsg(msg,1,$(this));
	});
	
	//验证验证码
	$(".inputCode").on('keyup blur',function() {
		 var inputCode = $(this).val();
		 var msg = "";
		 if(inputCode == ""){
			 msg = tipsMsg['tips_codeHide'];
			 sendMsg(msg,0,$(this));
			 return
		 }
		 msg = tipsMsg['tips_success'];
		 sendMsg(msg,1,$(this));
	});
	
	//验证手机验证码
	$(".inputPcode").on('keyup blur',function() {
		 var inputPcode = $(this).val();
		 var msg = "";
		 if(inputPcode == ""){
			 msg = tipsMsg['tips_phoneCodeHide'];
			 sendMsg(msg,0,$(this));
			 return
		 }
		 msg = tipsMsg['tips_success'];
		 sendMsg(msg,1,$(this));
	});
	
	//验证邮箱验证码
	$(".inputEcode").on('keyup blur',function() {
		 var inputEcode = $(this).val();
		 var msg = "";
		 if(inputEcode == ""){
			 msg = tipsMsg['tips_emailCodeHide'];
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
            $('.inputPhone').attr('readonly', true).addClass('locking');
			$('.reLoad').fadeIn(0);
            numAdd();
        }
    }

    //手机验证码倒计时
    var countdown = initTime;
    function numAdd() {
        $('.other-reg').html('');
        function loadTime() {
            if (countdown == 0) {
                countdown = initTime;
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

    //发送Email验证码
    function sendEmailCode() {
        $('.other-reg').html('');
        $('.emailNum').fadeIn(0);
        $('.inputEmail').attr('readonly', true).addClass('locking');
		$('.reLoad').fadeIn(0);
		$('.inputEcode').focus();
        numAddE();
    }

    //发送手机验证码
    function sendPhoneCode() {
        $(this).hide();
        numAdd();
    }
})