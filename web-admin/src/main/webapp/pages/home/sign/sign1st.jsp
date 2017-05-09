<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<%@include file="/pages/comm/agdhead.jsp"%>
<title>广东省文化馆</title>
<script src="${basePath }/static/assets/js/plugins/tipso/tipso.js"></script>
<script src="${basePath }/static/assets/js/sign/sign.js"></script>
<link href="${basePath }/static/assets/css/sign/sign.css" rel="stylesheet">

<script type="text/javascript">
	var step1 = (function(){
		//活动培训标记参数
		var mark = "${mark}";
		var actbmcount = "${actbmcount}" || 1;
		
		//主ID标记
		var id = mark=="event"? "${event.actvitmid}" : "${train.traid}";
		//提交第一步的URL
		var step1submetUrl = mark=="event"? "${basePath}/sign/evt/sendStep1" : "${basePath}/sign/sendStep1";
		//转到2和3的URL
		var step2Url = mark=="event"? "/sign/evt/step2/" : "/sign/step2/";
		var step3Url = mark=="event"? "/sign/evt/step3/" : "/sign/step3/";
		
		//是否要上传资料
		var isUpload = mark=="event" ? "${event.actvisattach}" : "${train.traisattach}";
		
		//提交报名第一步
		function stepSubmit(enrtype){
			var data = new Object();
			if (mark == "event"){
				data.actvitmid = id;
				data.actbmtype = enrtype;
				data.actbmcount = actbmcount;
			}else{
				data.enrtraid = id;
				data.enrtype = enrtype;
			}
			$.post(step1submetUrl, data, postCall, "json");
		}
		
		function postCall(data){
			if (!data.success){
				showError(data.msg);
				return;
			}
			if (isUpload == 1){
				gotoUrl(step2Url+data.msg);//上传资料
			}else{
				gotoUrl(step3Url+data.msg);//完成
			}
		}
		
		function showError(msg){
			var tagname = mark=="event"? "活动" : "培训";
			var userbmurl = mark=="event"? "/center/activity" : "/center/curriculum";
			var error = new Object();
			switch (msg) {
				case "1" :  error.info = "指定的"+tagname+"已不存在"; break;
				case "2" :  error.info = "指定的"+tagname+"已取消了发布"; break;
				case "3" :  error.info = "指定的"+tagname+"不需要报名"; break;
				case "4" :  error.info = "报名时间未开始"; break;
				case "5" :  error.info = "报名时间已结束"; break;
				case "6" :  error.info = "当前"+tagname+"不允许个人报名"; break;
				case "7" :  error.info = "当前"+tagname+"不允许团队报名"; break;
				case "8" :  error.info = "当前"+tagname+"报名人数已达上限"; break;
				case "9" :  error.info = "当前"+tagname+"不允许已报过同类型"+tagname+"的重复报名"; break;
				case "13" :  error.info = "当前报名人数超过单次报名人数限制"; break;
				case "14" :  error.info = "当前"+tagname+"仅允许少儿段报名"; break;
				case "15" :  error.info = "当前"+tagname+"仅允许成人段报名"; break;
				case "16" :  error.info = "当前"+tagname+"仅允许老年段报名"; break;
				case "10" :  
					error.info = "当前"+tagname+"需要实名认证才可报名<br/><br/>马上实名认证";
					error.href = "/center/safely";
					break;
				case "11" :  
					error.info = "当前"+tagname+"需要完善资料才可报名<br/><br/>马上完善资料";
					error.href = "/center/userInfo";
					break;
				case "12" :  
					error.info = "已存在当前"+tagname+"的报名<br/></br>查看报名记录"; 
					error.href = userbmurl;
					break;
				default : error.info = "提交报名发生错误";
			}
			
			function clickOk(){
				if (error.href) gotoUrl(error.href);
			}
			rongAlertDialog({
				title: "提示信息",
				desc : error.info,
				closeBtn : error.href? false:true,
				icoType : 1
			}, clickOk);
		}
		
		function gotoUrl(href){ 
			var _href = getFullUrl(href);
			window.location = _href;
		}
		
		return {
			stepSubmit: stepSubmit
		}
	})()
	
	
	//页面加载后处理
	$(function(){
		//验证对象是否存在
		var mark = "${mark}";
		var info = (mark && mark=="event")? "${event.actvitmid}" : "${train.traid}";
		if (!info){
			rongDialog({
				type : false,
				title : "获取报名目标数据失败",
				time : 3*1000,
				url : "${basePath}"
			});
			return ;
		}
	})
</script>

</head>
<body>

<!-- 公共头部开始 -->
<%@include file="/pages/comm/agdtop.jsp"%>
<!-- 公共头部结束 -->

<c:choose>
	<c:when test="${mark eq 'event' }">
		<h1 class="sign-title">《${event.actvtitle }》活动报名</h1>
	</c:when>
	
	<c:otherwise>
		<h1 class="sign-title">《${train.tratitle }》培训报名</h1>
	</c:otherwise>
</c:choose>

<c:choose>
	<c:when test="${train.traisattach eq 1 or event.actvisattach eq 1}">
	<ul class="crumbs crumbs-1st clearfix">
	  <li class="step-1">1. 选择报名方式<em class="arrow"></em></li>
	  <li class="step-2">2. 上传报名资料<em class="arrow"></em></li>
	  <li class="step-3 last">3. 报名完成</li>
	</ul>
	</c:when>
	
	<c:otherwise>
	<ul class="crumbs2 crumbs2-1st clearfix">
	  <li class="step-1">1. 选择报名方式<em class="arrow"></em></li>
	  <li class="step-2 last">2. 报名完成</li>
	</ul>
	</c:otherwise>
</c:choose>

<div class="main">
<c:choose>
	<c:when test="${(train.tracanperson eq 1 and train.tracanteam eq 1) or(event.actvcanperson eq 1 and event.actvcanteam eq 1) }">
	<!-- 有个人也有团队报名 -->
	<div class="main-cont">
	  	<div class="reLoad none"><a href="javascript:void(0)" onClick="history.go(0);">返回</a></div>
	    <div class="typeChange">
	    	<div class="change individual">
	        	<h2>个人用户${mark eq 'event'? "活动":"培训" }报名</h2>
	            <p>User training registration</p>
	            <a href="javascript:void(0)"></a>
	        </div>
	        <div class="change teamReg">
	        	<h2>团队用户${mark eq 'event'? "活动":"培训" }报名</h2>
	            <p>Team user training</p>
	        	<a href="javascript:void(0)"></a>
	        </div>
	    </div>
	    <div class="typeMain">
	        <div class="download-cont">
	        	<a href="javascript:void(0)" class="goNext next1 none" onClick="step1.stepSubmit(0)">下一步</a>
	        	<a href="javascript:void(0)" class="goNext next2 none" onClick="step1.stepSubmit(1)">下一步</a>
	        </div>
	    </div>
	  </div>
	</c:when>
	
	<c:when test="${(train.tracanperson eq 1 and train.tracanteam ne 1) or(event.actvcanperson eq 1 and event.actvcanteam ne 1) }">
	<!-- 个人报名开，团队报名关 -->
	<div class="main-cont">
	    <div class="typeChange">
	    	<div class="change2 individual2">
	        	<h2>个人用户${mark eq 'event'? "活动":"培训" }报名</h2>
	            <p>User training registration</p>
	            <a href="javascript:void(0)"></a>
	        </div>
	    </div>
	    <div class="typeMain">
	        <div class="download-cont">
	        	<a href="javascript:void(0)" class="goNext next1" onClick="step1.stepSubmit(0)">下一步</a>
	        </div>
	    </div>
	  </div>
	</c:when>
	
	<c:when test="${(train.tracanperson ne 1 and train.tracanteam eq 1) or(event.actvcanperson ne 1 and event.actvcanteam eq 1) }">
	<!-- 个人报名关，团队报名开 -->
	<div class="main-cont">
	    <div class="typeChange">
	    	<div class="change2 teamReg2">
	        	<h2>团队用户${mark eq 'event'? "活动":"培训" }报名</h2>
	            <p>Team user training</p>
	        	<a href="javascript:void(0)"></a>
	        </div>
	    </div>
	    <div class="typeMain">
	        <div class="download-cont">
	        	<a href="javascript:void(0)" class="goNext next1" onClick="step1.stepSubmit(1)">下一步</a>
	        </div>
	    </div>
	  </div>
	</c:when>
</c:choose>
  
</div>

<!--底部开始-->
<%@include file="/pages/comm/agdfooter.jsp"%>
<!--底部结束-->

</body>
</html>
