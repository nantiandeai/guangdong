<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath();
	request.setAttribute("basePath", basePath);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge"> 
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
<!-- 广东logo END-->

<!-- 注册导航 -->
<ul class="crumbs crumbs-1st clearfix">
	<li class="step-1">1. 选择模式<em class="arrow"></em></li>
  	<li class="step-2">2. 验证信息<em class="arrow"></em></li>
  	<li class="step-3 last">3. 修改密码</li>
</ul>
<!-- 注册导航 END-->

<div class="main">
	<div class="main-cont">
  		<div class="reLoad none"><a href="javascript:void(0)" onClick="history.go(0);">其它方式找回密码</a></div>
    	
    	<!-- 找回密码方式 -->
    	<div class="typeChange">
    		<div class="change mobileFind">
        		<h2>通过手机找回密码</h2>
            	<p>Retrieve password with mobile</p>
            	<a href="javascript:void(0)" id="phoneFind"></a>
        	</div>
        	<div class="change emailFind" style="display: none">
        		<h2>通过邮箱找回密码</h2>
            	<p>Retrieve password with E-mail</p>
        		<a href="javascript:void(0)" id="emailFind"></a>
        	</div>
    	</div>
    	<!-- 找回密码方式END -->
    	
    	<!-- 找回密码 -->
    	<div class="typeMain">
    		<form id="findPWDForm" name="findPWDForm" action="${basePath }/user/findPwd2" method="post">
    			<div class="help">请选择您需要的密码找回方式</div>
        		<dl class="phoneType clearfix phone none">
          			<dt class="float-left">手机号</dt>
          			<dd class="float-left">
           				<input class="in-txt inputPhone" placeholder="输入11位手机号码" name="phone" maxlength="11">
            			<em></em>
          			</dd>
        		</dl>
        		<dl class="clearfix next none">
          			<dt class="float-left">&nbsp;</dt>
          			<dd class="float-left">
            			<div class="goNext float-left" id="go_phone">下一步</div>
          			</dd>
        		</dl>
        		<dl class="mailType clearfix  mail none">
          			<dt class="float-left">E-mail</dt>
          			<dd class="float-left">
            			<input class="in-txt inputEmail" name="email" maxlength="20" placeholder="输入邮箱地址">
            			<em></em>
          			</dd>
        		</dl>
    			<dl class="clearfix next none">
          			<dt class="float-left">&nbsp;</dt>
          			<dd class="float-left">
            			<div class="goNext float-left" id="go_email">下一步</div>
          			</dd>
        		</dl>
        	</form>
		</div>
		<!-- 找回密码END -->
	</div>
</div>

<!-- core public JavaScript --> 
<script src="${basePath }/pages/home/user/js/findPwd.js"></script>
</body>
</html>
