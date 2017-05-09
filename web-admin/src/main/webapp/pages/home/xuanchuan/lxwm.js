/**
 * Created by dong on 2016/11/1.
 */
$(document).ready(function(e) {
    $(".page-left ul li").click(function () {
        $(this).addClass("active").siblings().removeClass('active');
        //$(".list1").eq($(".tab li").index(this)).addClass("on").siblings().removeClass('on');
    })
    $(".page1 .page1-title ul li").click(function () {
        $(this).addClass("active").siblings().removeClass('active');
        $(".page-con").eq($(".page1 .page1-title ul li").index(this)).addClass("on").siblings().removeClass('on');
    })
    $('.page-right > .page1').show();
    
    //绑定事件跳转页面
	$(".page-left ul li").click(function(){
		var url = $(this).attr("url");
		window.location.href = url;
	});
	
	//验证码绑定事件	
	$("#yanzhen").click(function(){
		var src = $(this).attr("src");
		src = src.substring(0, src.indexOf('t=1'));
		$(this).attr("src", src+'t='+new Date().getTime());
	});
	//验证码绑定事件	
	$("#yanzhen1").click(function(){
		var src = $(this).attr("src");
		src = src.substring(0, src.indexOf('t=1'));
		$(this).attr("src", src+'t='+new Date().getTime());
	});
})

//手机号码规则
var Phones = /^1[34578][0-9]\d{8}$/;
//邮箱规则
var Emails =  /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/; 
//中文输入规则
var Chinese = /^[\u2E80-\u9FFF]+$/;///^[\u4e00-\u9fa5]+$/;

/**
 * 意见
 * @returns
 */
function addyj(){
	var _form = $("#ff");
	var input=_form.find("input").val();
	if (input=='') {
		return rongDialog({ type : false, title : "操作失败,不能有空值!", time : 3*1000 });;
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
/**
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
 * 网上咨询
 * @returns
 */
function addzx(){
	var form = $("#ff_");
	var input=form.find("input").val();
	if (input=='') {
		return rongDialog({ type : false, title : "操作失败,不能有空值!", time : 3*1000 });;
	}
	if (!checkchinese_()) return;
	if (!checkPhone_()) return;
	if (!checkeamil_()) return;
	
	var url = getFullUrl('/xuanchuan/addfeed');
	var data = $("#ff_").serialize();
	$.ajax({  
		type : "POST",  
		url : url,  
		data : data,  
		success : function(data) {  
			if (data.success) {
				rongDialog({ type : true, title : "操作成功", time : 3*1000 });
				 $("#ff_").get(0).reset();
			} else {
				rongDialog({ type : false, title : "操作失败:"+data.msg+"!", time : 3*1000 });
			}
		} 
	
	})  
   
}
/*
 * 咨询手机号码验证
 */
function checkPhone_(){ 
	var _form = $("#ff_");
    var phone = _form.find("[name='feedphone']").val();
    if(!Phones.test(phone)){ 
    	rongDialog({ type : false, title : "手机号码有误，请重填", time : 3*1000 });
        return false; 
    } 
    return true;
}
/*
 * 咨询邮箱验证
 */
function checkeamil_(){ 
	var _form = $("#ff_");
    var eamil = _form.find("[name='feedmail']").val();
    if(!Emails.test(eamil)){ 
    	rongDialog({ type : false, title : "邮箱不正确，请重填", time : 3*1000 });
        return false; 
    } 
    return true;
}

/**
 * 输入名字中文
 */
function checkchinese_(){
	var _form = $("#ff_");
    var chi = _form.find("[name='feedname']").val();
    if(!Chinese.test(chi)){ 
    	rongDialog({ type : false, title : "请输入中文姓名", time : 3*1000 });
        return false; 
    } 
    return true;
}

