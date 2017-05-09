<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath();
	request.setAttribute("basePath", basePath);
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge"> 
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="keywords" content="">
<meta name="author" content="">
<title>广东省文化馆-注册</title>
<base href="${basePath}/"/>
<link href="./static/assets/css/public/reset.css" rel="stylesheet">
<link href="./static/assets/css/regist/regist.css" rel="stylesheet">
<!--[if lt IE 9] >
<script src="./static/assets/js/plugins/ie/IE9.js"></script> 
<! [endif]]-->
<script>
var basePath = '${basePath}';
</script>
</head>
<body> 

<!--  广东logo-->
<a href="${basePath}/home"><div class="regist-logo"></div></a>
<!-- 广东logoEND -->

<!-- 注册导航 -->
<ul class="crumbs crumbs-3rd clearfix">
	<li class="step-1">1. 基本资料<em class="arrow"></em></li>
  	<li class="step-2">2. 完善资料<em class="arrow"></em></li>
  	<li class="step-3 last">3. 注册成功</li>
</ul>
<!-- 注册导航END -->

<!-- 注册成功 -->
<div class="main">
	<div class="reg-msg">
    	<div class="msg success">
    		<h2><span></span>注册成功</h2>
        	<p>恭喜您成为广东省文化馆注册会员</p>
        	<p><a href="#">返回首页</a></p>
    	</div>
    	<div class="msg err"></div>
  	</div>
</div>
<!-- 注册成功END -->

<div class="mask none"></div>

<!-- core public JavaScript --> 
<script src="./static/assets/js/public/jquery-1.11.0.min.js"></script> 
<script src="./static/assets/js/public/formUI.js"></script> 
<script src="./static/assets/js/public/jquery-migrate-1.0.0.js"></script> 
<script src="${basePath }/static/assets/js/public/comm.js"></script>
<script src="./static/assets/js/regist/regist.js"></script>

</body>
</html>