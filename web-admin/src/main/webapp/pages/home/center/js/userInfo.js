//提示消息
var tipsMsg = {
	tips_success: '', 
	tips_required: '不能为空',
	tips_mobile: '手机号码格式有误',
	tips_birthday: '日期格式有误',
	tips_nickname : '昵称格式为2-16位的中文或字母数字符号组合',
	tips_hasNickName: '该昵称已占用',
	tips_Length:"最多允许输入300字",
	tips_smaLength:"最多允许输入150字",
	tips_nationLength:"最多允许输入30字",
	tips_qq: 'QQ账号格式有误',
	tips_wx: '微信账号格式有误'
};

//提示消息框
function sendMsg(msg, flag, t){
	if(!flag){
		t.next('em').html(msg).addClass('fail').removeClass('sucess');
	}else{
		t.next('em').html(msg).addClass('sucess').removeClass('fail');
	}
}

//手机号码规则
var regexpPhone = /^1[3|4|5|7|8][0-9]\d{8}$/;  

//日期格式
var regexBirthday = /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/;

//qq号码格式
var regexQQ = /^[1-9][0-9]{4,13}$/;

//微信格式 
var regexWX = /^[a-zA-Z\d_]{5,30}$/;



//判断昵称是否已存在
function hasNickName(){
	var nickname = $("#nickname").val();
	var _success = true; 
	$.ajax({
		type : "POST",
		async: false,
		url : getFullUrl('./center/hasNickName'),
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
 * 可编辑
 * @returns
 */
function goSaveUserInfo(){
	//表单可编辑
	$("input").attr('disabled', false);
	$("select").attr('disabled', false);
	$("textarea").attr('disabled', false);
	$("#phone").attr('disabled', true);
	$("#email").attr('disabled', true);
	
	/**
	 * 性别：单选按钮处理
	 */
	var sex = $("#nickname").attr("sex");
	$("input[name='sex'][value='" + sex + "']").attr("checked", "checked");
	
	//显示性别单选按钮
	$("input[name='sex']").attr('disabled', false);
	$("input[name='sex']").show();
	$("input[name='sex']").next().show();
	//改变按钮名称
	$("#modify").html('保存');
}

/**
 * 保存资料
 * @returns
 */
function saveUserInfo(){
	
	//判断手机和邮箱是否均为空
	if($("#phone").val() == "" && $("#email").val() == ""){
		rongDialog({
			title : '请前往绑定手机或邮箱，绑定后可完善资料',
			type  :  true,
			time : 3000,
			url : getFullUrl('./center/safely')
		})
		return;
	}
	
	//验证用户信息不为空
	var fields = [
	    {field: $("#nickname").val(), el: $("#nickname")},
	    {field: $("#nation").val(), el: $("#nation")},
	    {field: $("#origo").val(), el: $("#origo")},
	    //{field: $("#qq").val(), el: $("#qq")},
	    //{field: $("#wx").val(), el: $("#wx")},
	    {field: $("#job").val(), el: $("#job")},
	    {field: $("#company").val(), el: $("#company")},
	    {field: $("#address").val(), el: $("#address")},
	    {field: $("#resume").val(), el: $("#resume")},
	    {field: $("#actbrief").val(), el: $("#actbrief")},
	    {field: $("#birthday").val(), el: $("#birthday")}
	    //{field: $("#phone").val(), el: $("#phone")}.parent()
	    
	];
	var isvalid = true;
	//错误信息的提示
	for(var i=0; i<fields.length; i++){
		if(fields[i].field == ""){
			sendMsg(tipsMsg['tips_required'], 0, fields[i].el);
			isvalid = false;
		}else{
			sendMsg(tipsMsg['tips_success'], 0, fields[i].el);
		}
	}
	
	//昵称是否已存在
	if($("#nickname").val() && !hasNickName()){
		isvalid = false;
		//return;
	}
	//验证昵称格式
	if($("#nickname").val() && $("#nickname").val().length < 2 || $("#nickname").val().length > 16){
		msg = tipsMsg['tips_nickname'];
		sendMsg(msg, 0, $("#nickname"));
		isvalid = false;
		//return;
	}
	//验证QQ格式
	if($("#qq").val() && !regexQQ.test($("#qq").val())){
		msg = tipsMsg['tips_qq'];
		sendMsg(msg, 0, $("#qq"));
		isvalid = false;
		//return;
	}
	//验证微信格式
	if($("#wx").val() && !regexWX.test($("#wx").val())){
		msg = tipsMsg['tips_wx'];
		sendMsg(msg, 0, $("#wx"));
		isvalid = false;
		//return;
	}
	//验证日期格式
	if($("#birthday").val() && !regexBirthday.test($("#birthday").val())) { 
		msg = tipsMsg['tips_birthday'];
		sendMsg(msg ,0 ,$("#birthday"));
		isvalid = false;
		//return;
	}
	
	//验证手机格式
	if($("#phone").val() && !regexpPhone.test($("#phone").val())){
		 msg = tipsMsg['tips_mobile'];
		 sendMsg(msg , 0, $("#phone"));
		 isvalid = false;
		 //return;
	}
	
	//民族字数限制
	if($("#nation").val() && $("#nation").val().length > 30){
		msg = tipsMsg['tips_nationLength'];
		sendMsg(msg , 0, $("#nation"));
		isvalid = false;
		//return;
	}
	
	//籍贯字数限制
	if($("#origo").val() && $("#origo").val().length > 30){
		msg = tipsMsg['tips_nationLength'];
		sendMsg(msg , 0, $("#origo"));
		isvalid = false;
		//return;
	}
	
	//工作单位字数限制
	if($("#company").val() && $("#company").val().length > 150){
		msg = tipsMsg['tips_smaLength'];
		sendMsg(msg , 0, $("#company"));
		isvalid = false;
		//return;
	}
	//通讯地址字数限制
	if($("#address").val() && $("#address").val().length > 150){
		msg = tipsMsg['tips_smaLength'];
		sendMsg(msg , 0, $("#address"));
		isvalid = false;
		//return;
	}
	
	//个人简历字数限制
	if($("#resume").val() && $("#resume").val().length > 300){
		msg = tipsMsg['tips_Length'];
		sendMsg(msg , 0, $("#resume"));
		isvalid = false;
		//return;
	}
	//从事文艺活动简介字数限制
	if($("#actbrief").val() && $("#actbrief").val().length > 300){
		msg = tipsMsg['tips_Length'];
		sendMsg(msg , 0, $("#actbrief"));
		isvalid = false;
		//return;
	}
	//验证信息错误：提示消息并调到错误处
	if(!isvalid){
		$(".formContainer em").each(function(){
			var txt = $(this).html();
			if(txt){
				$(this).siblings("[name]").focus();
				return false;
			}
		});
		return;
	}
   	//请求后台数据
	$.ajax({
		type : "POST",
		async: false,
		url : getFullUrl('./center/modifyUser'),
		data : $('#inputForm').serialize(),
		success : function(data) {
			$("em").html('');
			if (data.success == "0") {
				//alert($('#inputForm').serialize());
				//判断获取的前序路径是否为用户中心的路径
				var isCenter = preurl.indexOf("/center") > -1;
				if(!preurl || isCenter){
					rongDialog({
						title : '修改成功',
						type : true
					});
				}else{
					rongAlertDialog({
						title: "提示信息",
						desc : '保存成功',
						closeBtn : false,
						icoType : 1
					}, function(){
						window.location.href = preurl;
					});
				}
				//表单不可修改
				$("input").attr('disabled',true);
				$("select").attr('disabled',true);
				$("textarea").attr('disabled',true);
				//遍历单选按钮：查看是否选中并显示相应的性别文字信息
				$("input[name='sex']").each(function(i){
					  if($(this).is(':checked')){
						  $(this).hide();
						  $(this).next().show();	
					  }else{
						  $(this).hide();
						  $(this).next().hide();	
					  }
				});	
				//改变按钮名称
				$("#modify").html('修改');
			}
		}
	});
}

/**
 * 表单不可编辑
 * @returns
 */
function formNoMDF(){
	//进入页面显示信息，并且文本不可编辑
	$("input").attr('disabled',true);
	$("select").attr('disabled',true);
	$("textarea").attr('disabled',true);
	
	/**
	 * 性别：单选按钮处理
	 */
	var sex = $("#nickname").attr("sex");
	$("input[name='sex'][value='" + sex + "']").attr("checked", "checked");
	//只显示性别的文字信息
	$("input[name='sex']").each(function(i){
		  if($(this).is(':checked')){
			  $(this).hide();
			  $(this).next().show();	
		  }else{
			  $(this).hide();
			  $(this).next().hide();	
		  }
	});
	$("#modify").html('修改');
}

/**
 * 按钮click
 * @param e
 * @returns
 */
function buttonClick(e){
	e.preventDefault();
	if($(this).html() == '修改'){
		goSaveUserInfo();
	}else{
		saveUserInfo();
	}
}

/**
 * 页面加载完后处理
 */
$(function() {
	//日期组件
	$('.dateChange').on('click',
	        function() {
	            laydate({
	                elem: '#birthday'
	            });
	});
	/** 判断是否完善资料 */
	$.ajax({
		type : "POST",
		url : getFullUrl('./center/IsPerfect'),
		success : function(data){
			if(data.success == "1"){//完善资料
				formNoMDF();
			}else if(data.success == "2" || data.success == "0"){
				//未完善资料开始
				goSaveUserInfo();
			}
			$("#modify").on('click', buttonClick);
		}
	});
	
	//文本域失焦事件处理
	/** 个人简历验证 */
	$("#resume").on('blur',function(){
		var resume = $("#resume").val();
		var msg = "";
		if(resume == ""){
			msg = tipsMsg['tips_required'];
			sendMsg(msg, 0, $("#resume"));
			return;
		}
		//个人简历字数限制
		if($("#resume").val() && $("#resume").val().length > 300){
			msg = tipsMsg['tips_Length'];
			sendMsg(msg , 0, $("#resume"));
			return
		}
	})
	/** 从事文艺活动验证 */
	$("#actbrief").on('blur',function(){
		var actbrief = $("#actbrief").val();
		var msg = "";
		if(actbrief == ""){
			msg = tipsMsg['tips_required'];
			sendMsg(msg, 0, $("#actbrief"));
			return;
		}
		//从事文艺活动字数限制
		if($("#actbrief").val() && $("#actbrief").val().length > 300){
			msg = tipsMsg['tips_Length'];
			sendMsg(msg , 0, $("#actbrief"));
			return
		}
	})
		
	/**
	 * input的失焦事件处理
	 */
	/** 昵称验证 */
	$("#nickname").on('blur',function(){
		//获取昵称
		var nickname = $("#nickname").val();
		var msg = "";
		//判断昵称是否为空
		if(nickname == ""){
			msg = tipsMsg['tips_required'];
			sendMsg(msg, 0, $("#nickname"));
			return;
		}
		//昵称是否已存在
		if($("#nickname").val() && !hasNickName()){
			return;
		}
		//验证昵称格式
		if($("#nickname").val() && nickname.length < 2 || nickname.length > 16){
			msg = tipsMsg['tips_nickname'];
			sendMsg(msg, 0, $("#nickname"));
			//isvalid = false;
			return;
		}
		 msg = tipsMsg['tips_success'];
		 sendMsg(msg,1,$(this));
	});
	
	/*$("#birthday").on('blur',function(){
		//获取出生日期
		var birthday = $("#birthday").val();
		var msg = "";
		//判断出生日期是否为空
		if(birthday == ""){
			msg = tipsMsg['tips_required'];
			sendMsg(msg, 0, $("#birthday").parent());
			return;
		}
		//验证日期格式
		if($("#birthday").val() && $("#birthday").val() && !regexBirthday.test($("#birthday").val())) { 
			msg = tipsMsg['tips_birthday'];
			sendMsg(msg ,0 ,$("#birthday").parent());
			//isvalid = false;
			return;
		}
		msg = tipsMsg['tips_success'];
		sendMsg(msg, 1, $("#birthday").parent());
	})*/
	
	/** 民族验证 */
	$("#nation").on('blur',function(){
		//获取民族
		var nation = $("#nation").val();
		var msg = "";
		//判断民族是否为空
		if(nation == ""){
			msg = tipsMsg['tips_required'];
			sendMsg(msg, 0, $("#nation"));
			return;
		}
		//民族字数限制
		if($("#nation").val() && $("#nation").val().length > 30){
			msg = tipsMsg['tips_nationLength'];
			sendMsg(msg , 0, $("#nation"));
			//isvalid = false;
			return;
		}
		 msg = tipsMsg['tips_success'];
		 sendMsg(msg,1,$(this));
	});
	
	/** 籍贯验证 */
	$("#origo").on('blur',function(){
		//获取籍贯
		var origo = $("#origo").val();
		var msg = "";
		//判断籍贯是否为空
		if(origo == ""){
			msg = tipsMsg['tips_required'];
			sendMsg(msg, 0, $("#origo"));
			return;
		}
		//籍贯字数限制
		if($("#origo").val() && $("#origo").val().length > 30){
			msg = tipsMsg['tips_nationLength'];
			sendMsg(msg , 0, $("#origo"));
			//isvalid = false;
			return;
		}
		 msg = tipsMsg['tips_success'];
		 sendMsg(msg,1,$(this));
	});
	
	/** QQ验证 */
	$("#qq").on('blur',function(){
		var msg = "";
		//验证QQ格式
		if($("#qq").val() && !regexQQ.test($("#qq").val())){
			msg = tipsMsg['tips_qq'];
			sendMsg(msg, 0, $("#qq"));
			return;
		}
		 msg = tipsMsg['tips_success'];
		 sendMsg(msg,1,$(this));
	});
	
	/** 微信验证 */
	$("#wx").on('blur',function(){
		var msg = "";
		//验证微信格式
		if($("#wx").val() && !regexWX.test($("#wx").val())){
			msg = tipsMsg['tips_wx'];
			sendMsg(msg, 0, $("#wx"));
			return;
		}
		 msg = tipsMsg['tips_success'];
		 sendMsg(msg,1,$(this));
	});
	
	/** 工作单位验证 */
	$("#company").on('blur',function(){
		//获取工作单位
		var company = $("#company").val();
		var msg = "";
		//判断工作单位是否为空
		if(company == ""){
			msg = tipsMsg['tips_required'];
			sendMsg(msg, 0, $("#company"));
			return;
		}
		//工作单位字数限制
		if($("#company").val() && $("#company").val().length > 150){
			msg = tipsMsg['tips_smaLength'];
			sendMsg(msg , 0, $("#company"));
			//isvalid = false;
			return;
		}
		 msg = tipsMsg['tips_success'];
		 sendMsg(msg,1,$(this));
	});	
	
	/** 手机验证 */
	$("#phone").on('blur',function(){
		//获取手机号码
		var phone = $("#phone").val();
		var msg = "";
		//判断手机号码是否为空
		if(phone == ""){
			msg = tipsMsg['tips_required'];
			sendMsg(msg, 0, $("#phone"));
			return;
		}
		//验证手机格式
		if($("#phone").val() && !regexpPhone.test($("#phone").val())){
			msg = tipsMsg['tips_mobile'];
			sendMsg(msg , 0, $("#phone"));
			//isvalid = false;
			return;
		}
		msg = tipsMsg['tips_success'];
		sendMsg(msg,1,$(this));
	});	
	
	/** 通讯地址验证 */
	$("#address").on('blur',function(){
		//获取通讯地址
		var address = $("#address").val();
		var msg = "";
		//判断通讯地址是否为空
		if(address == ""){
			msg = tipsMsg['tips_required'];
			sendMsg(msg, 0, $("#address"));
			return;
		}
		//通讯地址字数限制
		if($("#address").val() && $("#address").val().length > 150){
			msg = tipsMsg['tips_smaLength'];
			sendMsg(msg , 0, $("#address"));
			//isvalid = false;
			return;
		}
		msg = tipsMsg['tips_success'];
		sendMsg(msg,1,$(this));
	});

	
});