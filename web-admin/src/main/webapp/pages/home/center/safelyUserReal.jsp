<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge"> 
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>广东省文化馆-用户中心</title>
<%@include file="/pages/comm/comm_head.jsp"%>

<link href="${basePath }/static/assets/css/userCenter/userCenter.css" rel="stylesheet">
<link href="${basePath }/static/assets/js/plugins/dropify/css/dropify.min.css" rel="stylesheet">
<style>
    em.err{
        color:red;
    }
    em.success {
        color: black;
    }
</style>
<script src="${basePath }/static/assets/js/userCenter/safely-userReal.js"></script>
<script src="${basePath }/static/common/js/jquery.form.js"></script>

<script type="text/javascript">

var tool = (function(){
	//会话用户是否已实名
	var isrealname = "${sessionUser.isrealname}" ||0; 
	//会话用户ID
	var id = "${sessionUser.id}";
	//图片上传的处理URL
	var uploadUrl = "${basePath }/center/uploadIdCard";
	//提交处理URL
	var saveUrl = "${basePath }/center/saveInfo";
	
	//图片显示处理
	function reloadInfo(data){
		var idcardface = data.idcardface;
		var idcardback = data.idcardback;
		if (idcardface){ 
			if ($(".cardA span").find("img").size()==0){
				$(".cardA span").empty();
				$(".cardA span").append('<img width="310" height="160" />');
				$(".cardA").addClass("isUploaded");
			}
			$(".cardA span").find("img").attr("src", "${imgServerAddr}/"+idcardface);
		}
		if (idcardback){
			if ($(".cardB span").find("img").size()==0){
				$(".cardB span").empty();
				$(".cardB span").append('<img width="310" height="160" />');
				$(".cardB").addClass("isUploaded");
			}
			$(".cardB span").find("img").attr("src", "${imgServerAddr}/"+idcardback);
		}
	}
	
	//实名状态显示
	function showState(data){
		if (!data){
			data = new Object();
			data.name = "${sessionUser.name}";
			data.idcard = "${sessionUser.idcard}";
			data.idcardface = "${sessionUser.idcardface}";
			data.idcardback = "${sessionUser.idcardback}";
			data.isrealname = isrealname;
		}
		//如果相关的内容填写了，开启状态显示
		$(".rightPanel .chapter").removeClass("c-ok c-no");
		if (data.name && data.idcard && data.idcardface && data.idcardback){
			if (data.isrealname == 1){
				$(".rightPanel .chapter").addClass("c-ok");
			}
			if (data.isrealname == 2){
			    $('#checkMsg').show().find('div').text('${sessionUser.checkmsg}');
				$(".rightPanel .chapter").addClass("c-no");
			}
			if (data.isrealname == 3){
				$(".rightPanel .chapter").addClass("c-sh");
			}
		}
	}
	
	//验证错误信息显示
	function showError(event, name, value){ 
		var _em = $(event).siblings("em");
		var _title = $(event).parents("dd").siblings("dt").text();
		_em.empty();
		if (!value) {
			_em.text(_title+"不能为空");
            _em.removeClass('success').addClass('err');
			return false;
		}
		if (name == "name" && value.lenght > 25){
			_em.text(_title+"长度超过限制");
            _em.removeClass('success').addClass('err');
			return false;
		}
		if (name == "idcard" && !value.match(/^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|[X|x])$/)){
			_em.text(_title+"格式不正确");
            _em.removeClass('success').addClass('err');
			return false;
		}
        _em.removeClass('err').addClass('success');
		return true;
	}
	
	function _errorText(errormsg){
		if (errormsg == 1) return "已通过审核，不能修改";
		else if (errormsg == 2) return "请上传图片文件";
		else if (errormsg == 3) return "身份证号已被使用";
		else return "保存信息失败";
	}
	
	function submitInfo(){
		var args = new Object();
		var iserror = false;
		$(this).parents("div.rightPanel").find("input[name]").each(function(){
			var name = $(this).attr("name");
			var value = $(this).val();
			args[name] = value;
			if (!showError(this, name, value)){
				iserror = true;
			}
		})
		if (iserror) return;
		
		var cardimgsize = $(".clearfix a.card img", ".rightPanel").size();
		if (cardimgsize<2){
			rongDialog({ type : false, title : "请先完成身份证图片上传再保存", time : 3*1000 });
			return;
		}
		
		$.post(saveUrl, args, function(data){
			if (data.success){
				showState(data.msg);
				var user = data.msg;
				var gotoObj = new Object();
				if (user.name && user.idcard && user.idcardface && user.idcardback){
					gotoObj.url = "${basePath }/center/safely";
					gotoObj.title = "保存成功";
				}
				if (!user.idcardface || !user.idcardback){
					gotoObj.title = "保存成功，还需要完成身份证照片上传才能进行审核";
				}
				rongDialog($.extend({ type : true, title : "保存成功", time : 3*1000 },gotoObj));
			}else{
				rongDialog({ type : false, title : _errorText(data.msg), time : 3*1000 });
			}
		})
	}
	
	function uploadIdcard(){
		var tag_form = $(this).parents(".popup").find("form");

		tag_form.ajaxSubmit({
			url : uploadUrl,
			beforeSubmit: function(arr, form, options){
				return true;
			},
			success: function(data){
				showState(data.msg);
				if(data.success){
					reloadInfo(data.msg);
					closeDialog();
				}else{
					closeDialog();
					rongDialog({ type : false, title : _errorText(data.msg), time : 3*1000 });
				}
			},
			error: function(){
				rongDialog({ type : false, title : "上传文件失败，请检查文件是否超过5M", time : 3*1000 });
			}
		})
		
	}
	
	function init(){
		showState();
		
		if (isrealname==1){
//			rongDialog({ type : true, title : "您已完成了实名认证", time : 3*1000 });
			$(".cardA, .cardB").off("click");
			$(".leftGo").hide();
			
			var idcard = $("input[name='idcard']").val();
			idcard = idcard.replace(/^(.{3})(.{11})(.*)/, "$1***********$3");
			$("input[name='idcard']").val(idcard);
			$("input[name]").attr("readonly","readonly");
			return;
		}
		//注册上传和提交事件
        if(isrealname == 3){//待认证不显示按钮
            $(".leftGo").hide();
        }else{
            $(".leftGo").show();
            $(".leftGo").on("click", submitInfo);
            $(".upload_idcard").on("click", uploadIdcard);
        }
	}
	
	return {
		init: init
	}
})()

$(function(){
	tool.init();
})
</script>

</head>
<body>

<!-- 公共头部开始 -->
<%@include file="/pages/comm/agdtop.jsp"%>
<!-- 公共头部结束 -->

<!-- 公共绑定开始 -->
<%@include file="/pages/comm/comm_center.jsp"%>
<!-- 公共绑定结束 -->

<div class="main clearfix">
 	<div class="leftPanel">
    	<ul>
        	<!--用户中心导航开始-->
			<%@include file="/pages/comm/ucnav.jsp"%>
			<!--用户中心导航结束-->
        </ul>
    </div>
    <div class="rightPanel">
    	<div class="chapter "></div>
        <div class="crumbs clearfix">
        	<div class="tt">实名认证</div>
            <div class="goBack"><a href="${basePath }/center/safely"></a></div>
        </div>
        <dl class="clearfix">
          <dt class="float-left">姓名</dt>
          <dd class="float-left">
            <input name="name" value="${sessionUser.name }" class="in-txt" placeholder="真实姓名">
            <em class="err"></em>
          </dd>
        </dl>
        <dl class="clearfix">
          <dt class="float-left">身份证号</dt>
          <dd class="float-left">
            <input name="idcard" value="${sessionUser.idcard }" class="in-txt" placeholder="请输入18位正确的身份证号码"/>
            <em class="err"></em>
          </dd>
        </dl>
        <dl class="clearfix">
        	<dt class="float-left">&nbsp;</dt>
            <dd class="float-left">
            <c:choose>
				<c:when test="${not empty sessionUser.idcardface }">
            	<a href="javascript:void(0)" class="card cardA isUploaded"><span>
            		<img alt="" src="${imgServerAddr }/${sessionUser.idcardface}" width="310" height="160" />
            	</span></a>
				</c:when>
				<c:otherwise>
            	<a href="javascript:void(0)" class="card cardA js__p_cardA_start"><span>点击上传（身份证正面）</span></a>
				</c:otherwise>            
            </c:choose>
            </dd>
        </dl>
        <dl class="clearfix">
        	<dt class="float-left">&nbsp;</dt>
            <dd class="float-left">
            <c:choose>
				<c:when test="${not empty sessionUser.idcardback }">
            	<a href="javascript:void(0)" class="card cardB isUploaded"><span>
            		<img alt="" src="${imgServerAddr }/${sessionUser.idcardback}" width="310" height="160" />
            	</span></a>
				</c:when>
				<c:otherwise>
            	<a href="javascript:void(0)" class="card cardB js__p_cardB_start"><span>点击上传（身份证反面）</span></a>
				</c:otherwise>            
            </c:choose>
            </dd>
        </dl>


        <dl class="clearfix none" id="checkMsg">
            <dt class="float-left">审核失败原因：</dt>
            <dd class="float-left">
                <div style="color: red"> </div>
            </dd>
        </dl>

        <dl class="clearfix">
          <dt class="float-left">&nbsp;</dt>
          <dd class="float-left">
            <div class="goNext leftGo float-left">提交审核</div>
          </dd>
        </dl>
    </div>
</div>

<!--底部开始-->
<%@include file="/pages/comm/agdfooter.jsp"%>
<!--底部结束-->

<!--弹出层开始-->
<div class="popup js__cardA_popup js__slide_top clearfix"> <a href="javascript:void(0)" class="p_close js__p_close" title="关闭"></a>
  <div class="p_content">
    <p>上传身份证正面</p>
  </div>
  <div class="p_main">
  	<div style="width:400px; margin:0px auto; padding-top:30px;">
    <form id="UploadForm1" method="post" enctype="multipart/form-data"> 
    		<input type="hidden" name="filemake" value="idcardface"/>
	       <input type="file" name="file" id="input-file-now-custom-1" class="dropify" data-height="160" data-max-file-size="5M"/>
    　　</form>
    </div>
  </div>
  <div class="p_btn goNext float-left"> <a href="javascript:void(0)" class="upload_idcard">上传</a> </div>
  <div class="p_btn goBack float-left"> <a href="javascript:void(0)" class="js__p_close">取消</a> </div>
</div>
<div class="popup js__cardB_popup js__slide_top clearfix"> <a href="javascript:void(0)" class="p_close js__p_close" title="关闭"></a>
  <div class="p_content">
    <p>上传身份证反面</p>
  </div>
  <div class="p_main">
  	<div style="width:400px; margin:0px auto; padding-top:30px;">
     <form id="UploadForm2" method="post" enctype="multipart/form-data">
     		<input type="hidden" name="filemake" value="idcardback"/>
	       <input type="file" name="file" id="input-file-now-custom-2" class="dropify" data-height="160" data-max-file-size="5M"/> 　　
    　</form> 
    </div>
  </div>
  <div class="p_btn goNext float-left"> <a href="javascript:void(0)" class="upload_idcard">上传</a> </div>
  <div class="p_btn goBack float-left"> <a href="javascript:void(0)" class="js__p_close">取消</a> </div>
</div>
<!--弹出层结束--> 

<!-- core public JavaScript --> 

<script src="${basePath }/static/assets/js/plugins/tipso/tipso.js"></script>
<script src="${basePath }/static/assets/js/plugins/dropify/dropify.min.js"></script>
<script src="${basePath }/static/assets/js/public/rong-dialog.js"></script>
<script src="${basePath }/static/assets/js/userCenter/public.js"></script>

</body>
</html>

