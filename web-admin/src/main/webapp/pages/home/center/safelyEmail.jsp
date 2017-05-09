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
<link href="${basePath }/static/assets/css/regist/regist.css" rel="stylesheet">
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
        	<div class="tt">绑定邮箱</div>
            <div class="goBack"><a href="${basePath }/center/safely"></a></div>
        </div>
        <dl class="mailType clearfix none" id="reEmail">
	      <dt class="float-left">绑定邮箱</dt>
	      <dd class="float-left">
	        <input class="in-txt inputEmail3" id="emailaddr3" value="${email}" placeholder="输入邮箱地址" maxlength="120" readonly>
	        <em></em>
	      </dd>
	    </dl>
        <dl class="mailType clearfix none" id="preEmail">
	      <dt class="float-left">原邮箱账号</dt>
	      <dd class="float-left">
	        <input class="in-txt inputEmail2" id="preEmailAddr" placeholder="输入邮箱地址" maxlength="120">
	        <em></em>
	      </dd>
	    </dl>
		<dl class="mailType clearfix">
	      <dt class="float-left">E-mail</dt>
	      <dd class="float-left">
	        <input class="in-txt inputEmail" id="emailaddr" placeholder="输入邮箱地址" maxlength="120">
	        <em></em>
	      </dd>
	    </dl>
	    <dl class="mailType clearfix" id="codeHide">
	      <dt class="float-left">验证码</dt>
	      <dd class="float-left">
	        <input class="in-txt inputEcode" id ="inputCode" placeholder="输入邮箱验证码" maxlength="6">
	        <em></em>
	        <div class="numAdd postCode emailCode none">发送验证码</div>
	        <div class="numAdd emailNum none"><span></span>秒后可重新发送</div>
	      </dd>
	    </dl>
	      <dl class="clearfix">
	        <dt class="float-left">&nbsp;</dt>
	        <dd class="float-left">
	          <div class="goNext float-left"><a href="javascript:void(0)" id="sure">确 定</a></div>
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
<script src="${basePath }/pages/home/center/js/safelyEmail.js"></script>
</body>
</html>