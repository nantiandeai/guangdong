<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>广东省文化馆-找回密码</title>
<%@include file="/pages/comm/comm_head.jsp"%>

<link href="${basePath }/static/assets/css/regist/regist.css" rel="stylesheet">
<script type="text/javascript" src="${basePath }/static/common/js/jQuery.md5.js"></script>
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
<ul class="crumbs crumbs-3rd clearfix">
	<li class="step-1">1. 选择模式<em class="arrow"></em></li>
	<li class="step-2">2. 验证信息<em class="arrow"></em></li>
	<li class="step-3 last">3. 修改密码</li>
</ul>
<!-- 注册导航END -->

<div class="main">
	<div class="main-cont">
		<!-- 修改密码 -->
		<dl class="clearfix">
			<dt class="float-left">密码</dt>
			<dd class="float-left">
				<input class="in-txt password" type="password" placeholder="6-18位数字字母符号组合" maxlength="18"> <em></em>
			</dd>
		</dl>
		<dl class="clearfix">
			<dt class="float-left">确认密码</dt>
			<dd class="float-left">
				<input class="in-txt repassword" type="password" placeholder="确认密码" maxlength="18"> <em></em>
			</dd>
		</dl>
		<dl class="clearfix">
			<dt class="float-left">&nbsp;</dt>
			<dd class="float-left">
				<div class="goNext float-left" id="modifyPwd">修改密码</div>
			</dd>
		</dl>
		<!-- 修改密码END -->
  	</div>
  	
  	<!-- 修改成功 -->
  	<div class="reg-msg none">
   		<div class="msg success">
    		<h2><span></span>修改成功</h2>
        	<p>恭喜密码修改成功，请牢记您的登录密码</p>
        	<p><a href="#">返回首页</a></p>
    	</div>
    	<div class="msg err"></div>
	</div>
	<!-- 修改成功END -->
	
</div>

<div class="mask none"></div>

<!-- core public JavaScript --> 
<script src="${basePath }/static/assets/js/public/formUI.js"></script>
<script src="${basePath }/pages/home/user/js/findPwd3.js"></script>
<script src="${basePath }/static/assets/js/public/comm.js"></script>
</body>
</html>
