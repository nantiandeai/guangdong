<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath(); request.setAttribute("basePath", basePath); %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge"> 
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="keywords" content="">
<meta name="author" content="">
<title>广东省文化馆-用户中心</title>
<link href="${basePath }/static/assets/css/public/reset.css" rel="stylesheet">
<link href="${basePath }/static/assets/css/userCenter/userCenter.css" rel="stylesheet">
<script src="${basePath }/static/assets/js/public/jquery-1.11.0.min.js"></script> 
<script src="${basePath }/static/assets/js/public/comm.js"></script>
<script src="${basePath }/static/assets/js/public/jquery-migrate-1.0.0.js"></script> 
<!--[if lt IE 9] >
<script src="${basePath }/static/assets/js/plugins/ie/IE9.js"></script> 
<! [endif]]-->
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
        <div class="crumbs clearfix">
        	<div class="tt">绑定手机</div>
            <div class="goBack"><a href="${basePath }/center/safely"></a></div>
        </div>
	   	<dl class="phoneType clearfix none" id="rePhone">
	      <dt class="float-left">绑定手机</dt>
	      <dd class="float-left">
	        <input class="in-txt inputPhone3" value="${phone}" placeholder="输入11位手机号码" maxlength="11" readonly>
	        <em></em>
	      </dd>
	    </dl>
	    <dl class="phoneType clearfix none" id="prePhone">
	      <dt class="float-left">原手机</dt>
	      <dd class="float-left">
	        <input class="in-txt inputPhone2" id="preMsgPhone" placeholder="输入11位手机号码" maxlength="11">
	        <em></em>
	      </dd>
	    </dl>
		<dl class="phoneType clearfix">
	      <dt class="float-left">手机号</dt>
	      <dd class="float-left">
	        <!-- <span class="inputPhone_span none">&nbsp;</span> -->
	        <input class="in-txt inputPhone" id="msgphone" placeholder="输入11位手机号码" maxlength="11">
	        <em></em>
	      </dd>
	    </dl>
	    <dl class="phoneCode clearfix" id="PhoneHide">
	      <dt class="float-left">手机验证码</dt>
	      <dd class="float-left">
	        <!-- <span class="inputPcode_span none">&nbsp;</span> -->
	        <input class="in-txt inputPcode" placeholder="输入手机验证码" maxlength="6">
	        <em></em>
	        <div class="numAdd phoneNum none"><span></span>秒后可重新发送</div>
	        <em></em>
	        <div class="numAdd postCode sendCodeP none">发送验证码</div>
	        <em></em>
	      </dd>
	    </dl>
	    <dl class="clearfix">
	      <dt class="float-left">&nbsp;</dt>
	      <dd class="float-left">
	        <div class="goNext float-left" id="sure">确 定</div>
	      </dd>
	    </dl>
    </div>
</div>

<!--底部开始-->
<%@include file="/pages/comm/agdfooter.jsp"%>
<!--底部结束-->

<div class="md-overlay"></div>

<!-- core public JavaScript --> 
<script src="${basePath }/static/assets/js/plugins/tipso/tipso.js"></script>
<script src="${basePath }/static/assets/js/public/rong-dialog.js"></script>
<script src="${basePath }/static/assets/js/userCenter/public.js"></script>
<script src="${basePath }/pages/home/center/js/safelyPhone.js"></script>
</body>
</html>
