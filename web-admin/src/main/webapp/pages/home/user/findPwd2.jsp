<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
request.setAttribute("basePath", basePath);
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="keywords" content="">
<meta name="author" content="">
<title>广东省文化馆-找回密码</title>
<base href = "${basePath}/" />
<link href="${basePath }/static/assets/css/public/reset.css" rel="stylesheet">
<link href="${basePath }/static/assets/css/regist/regist.css" rel="stylesheet">
<script src="${basePath }/static/assets/js/public/jquery-1.11.0.min.js"></script> 
<script src="${basePath }/static/assets/js/public/jquery-migrate-1.0.0.js"></script>
<script src="${basePath }/static/assets/js/public/comm.js"></script>
<!--[if lt IE 9] >
<script src="${basePath }/static/assets/js/plugins/ie/IE9.js"></script> 
<! [endif]]-->
<script>
var basePath = '${basePath}';
</script>
</head>
<body>

<!-- 广东logo -->
<a href="${basePath}/home"><div class="regist-logo"></div></a>
<!-- 广东logoEND -->

<!-- 注册导航 -->
<ul class="crumbs crumbs-2nd clearfix">
	<li class="step-1">1. 选择模式<em class="arrow"></em></li>
	<li class="step-2">2. 验证信息<em class="arrow"></em></li>
	<li class="step-3 last">3. 修改密码</li>
</ul>
<!-- 注册导航END -->


<div class="main">
	<div class="main-cont">
		<form id="findPWDForm" name="findPWDForm" action="${basePath }/user/findPwd3" method="post">
		
		<!-- 选择找回密码方式 -->
		<div class="typeChange">
			<c:if test="${email != null }" >
				<div class="change mobileFind locationP none">
					<h2>通过手机找回密码</h2>
					<p>Retrieve password with mobile</p>
				</div>
				<div class="change emailFind locationE">
					<h2>通过邮箱找回密码</h2>
					<p>Retrieve password with E-mail</p>
				</div>
			</c:if>
			<c:if test="${phone != null }" >
				<div class="change mobileFind locationP">
					<h2>通过手机找回密码</h2>
					<p>Retrieve password with mobile</p>
				</div>
				<div class="change emailFind locationE none">
					<h2>通过邮箱找回密码</h2>
					<p>Retrieve password with E-mail</p>
				</div>
			</c:if>
		</div>
		<!--选择找回密码方式END  -->
		
		<!--手机找回密码信息验证开始-->
		<c:if test="${not empty phone}" >
			<div class="typeMain">
				<dl class="phoneType clearfix">
					<dt class="float-left">手机号</dt>
					<dd class="float-left">
						<input class="in-txt inputPhone locking" placeholder="输入11位手机号码" value="${phone}" maxlength="11" name="phone" readonly> <em></em>
					</dd>
				</dl>
				<dl class="phoneCode clearfix " id="PhoneHide">
					<dt class="float-left">手机验证码</dt>
					<dd class="float-left">
						<input class="in-txt inputPcode" placeholder="输入手机验证码" maxlength="6"> <em></em>
						<div class="numAdd phoneNum none">
							<span></span>秒后可重新发送
						</div>
						<div class="numAdd postCode sendCodeP">发送验证码</div>
					</dd>
				</dl>
				<dl class="clearfix">
					<dt class="float-left">&nbsp;</dt>
					<dd class="float-left">
						<div class="goNext float-left" id="phone_go">下一步</div>
					</dd>
				</dl>
			</div>
		</c:if>
		<!--手机找回密码信息验证结束-->
		
		<!--邮箱找回密码信息验证开始-->
		<c:if test="${email != null }" >
			<div class="typeMain">
				<dl class="mailType clearfix">
					<dt class="float-left">E-mail</dt>
					<dd class="float-left">
						<input class="in-txt inputEmail locking" name="email" placeholder="输入邮箱地址" readonly value="${email}" maxlength="20"> <em></em>
						<em></em>
					</dd>
				</dl>
				<dl class="mailType clearfix" id="codeHide">
					<dt class="float-left">验证码</dt>
					<dd class="float-left">
						<input class="in-txt inputEcode" name="emailCode" placeholder="输入邮箱验证码" maxlength="6"> 
						<em></em>
						<div class="numAdd postCode emailCode">发送验证码</div>
						<em></em>
						<div class="numAdd emailNum none"><span></span>秒后可重新发送</div>
					</dd>
				</dl>
				<dl class="clearfix">
					<dt class="float-left">&nbsp;</dt>
					<dd class="float-left">
						<div class="goNext float-left" id="email_go">下一步</div>
					</dd>
				</dl>
			</div>
		</c:if>
		<!--邮箱找回密码信息验证结束-->
		
		</form>
	</div>
</div>
<!-- core public JavaScript -->
 
<script src="${basePath }/pages/home/user/js/findPwd2.js"></script>
</body>
</html>
