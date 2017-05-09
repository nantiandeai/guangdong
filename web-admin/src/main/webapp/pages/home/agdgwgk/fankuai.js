/**
 * Created by dong on 2016/11/1.
 */
$(document).ready(function() {
	//验证码绑定事件	
	$("#yanzhen").click(function(){
		var src = $(this).attr("src");
		src = src.substring(0, src.indexOf('t=1'));
		$(this).attr("src", src+'t='+new Date().getTime());
	});
	upreverse();
})

//手机号码规则
var Phones = /^1[34578][0-9]\d{8}$/;
//邮箱规则
var Emails =  /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/; 
//中文输入规则
var Chinese = /^[\u2E80-\u9FFF]+$/;
/**
 * 意见
 * @returns
 */
function addyj(){
	var _form = $("#ff");
	var txt = $("#feeddesc").val();
	if (txt == '') {
		return rongDialog({ type : false, title : "操作失败,请输入意见反馈信息!", time : 3*1000 });;
	}
	if (!checkchinese()) return;
	if (!checkPhone()) return;
	if (!checkeamil()) return;
	var url = getFullUrl('/xuanchuan/addfeed');
	var data = _form.serialize();
	$.ajax({  
		type : "POST",  
		url : url,  
		data : data,  
		success : function(data) {  
			if (data.success) {
				rongDialog({ type : true, title : "操作成功", time : 3*1000 });
				_form.get(0).reset();
				$("#yanzhen").click();
			}else {
				rongDialog({ type : false, title : "操作失败:"+data.msg+"!", time : 3*1000 });
			}
		} 
	
	})  
}
/*
 * 意见手机号码验证
 */
function checkPhone(){ 
	var _form = $("#ff");
    var phone = _form.find("[name='feedphone']").val();
    if(!Phones.test(phone)){ 
    	rongDialog({ type : false, title : "手机号码有误，请重填", time : 3*1000 });
        return false; 
    } 
    return true;
}
/*
 * 意见邮箱验证
 */
function checkeamil(){ 
	var _form = $("#ff");
    var eamil = _form.find("[name='feedmail']").val();
    if(!Emails.test(eamil)){ 
    	rongDialog({ type : false, title : "邮箱不正确，请重填", time : 3*1000 });
        return false; 
    } 
    return true;
}
/*
 * 意见输入名字中文
 */
function checkchinese(){
	var _form = $("#ff");
    var chi = _form.find("[name='feedname']").val();
    if(!Chinese.test(chi)){ 
    	rongDialog({ type : false, title : "请输入中文姓名", time : 3*1000 });
        return false; 
    } 
    return true;
}

/**
 *反显 
 */
function upreverse(){
	var url = getFullUrl('/agdgwgk/upfeed');
	$.ajax({  
		type : "POST",  
		url : url,   
		success : function(data) {
			if (data) {
			$("#ff").find("[name='feedname']").val(data.name);
			$("#ff").find("[name='feedmail']").val(data.email);
			$("#ff").find("[name='feedphone']").val(data.phone);
			$("#ff").find("[name='feedcom']").val(data.job);
			}
		} 
	
	})  
}

