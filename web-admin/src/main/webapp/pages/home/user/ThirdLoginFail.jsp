<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath();
	request.setAttribute("basePath", basePath);
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv=”X-UA-Compatible” content=”IE=edge,chrome=1″ />
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="keywords" content="">
<meta name="author" content="">
<base href="${basePath}/" />
<title>广东省文化馆-登录</title>
<link href="${basePath }/static/assets/css/public/reset.css" rel="stylesheet">
<link href="${basePath }/static/assets/css/regist/login.css" rel="stylesheet">
<!--[if lt IE 9] >
<script src="${basePath }/static/assets/js/plugins/ie/IE9.js"></script> 
<! [endif]]-->
<script type="text/javascript" src="http://qzonestyle.gtimg.cn/qzone/openapi/qc_loader.js" data-appid="101360064" data-redirecturi="http://127.0.0.1:8080/szwhg/user/afterloginPage" charset="utf-8" >
</script>
<script>
var basePath = '${basePath}';
var preurl = '${preurl}';
</script>
</head>
<body>
<!-- 公共头部-->
<%@include file="/pages/comm/agdtop.jsp"%>
<!-- 公共头部 END-->

<div class="main">
	<div class="main-cont">
    <dl class="clearfix">
			<dt class="float-left">&nbsp;</dt>
			<dd class="float-left">
				<h2><span></span>登录失败，${failMsg }</h2>
				<p><a href="${basePath }/login">返回登录页面</a></p>
			</dd>
		</dl>
  </div>
</div>

<!--底部-->
<%@include file="/pages/comm/agdfooter.jsp"%>
<!--底部END-->
</body>