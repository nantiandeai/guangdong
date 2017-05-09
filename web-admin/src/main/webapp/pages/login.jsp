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
<meta http-equiv="X-UA-Compatible" content="IE=edge"> 
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
<script type="text/javascript" src="http://qzonestyle.gtimg.cn/qzone/openapi/qc_loader.js" data-appid="101360064" data-redirecturi="http://120.77.10.118:8082/szwhgsg/user/afterloginPage" charset="utf-8" >
</script>
<script>
var basePath = '${basePath}';
var preurl = '${preurl}';

function wb_open(){
	var url = "${basePath }/user/authorizeWb";
	$.post(url, function(data){
		if (data.success){
			window.location="${basePath}";
		}
	}, "json")
}
</script>
</head>
<body>

<!--广东logo -->
<a href="${basePath}/home"><div class="regist-logo"></div></a>
<!--广东logo END-->

<div class="main clearfix" style="height:500px;">
	<!-- 用户登录信息 -->
	<div class="login-cont">
  		<div class="form-cont">
    		<h1>用户登录/USER LOGIN</h1>
        	<div class="input-row id-bg">
        		<input class="login-input" id="userName" name="userName" value="" placeholder="手机号">
            	<div class="msg" id="tipName"><!-- 手机号或邮箱账号不存在 --></div>
        	</div>
        	<div class="input-row pwd-bg">
        		<input type="password" id="password" class="login-input" value="" placeholder="请输入密码">
        		<div class="msg" id="tipPwd"><!--手机号或邮箱账号不存在 --></div>
        	</div>
        	<div class="find-pwd">
        		<a href="${basePath }/user/tofindPwd">忘记密码</a>
        	</div>
        	<button type="submit" id="login" class="login-btn goNext">登录</button>
        	<div class="regist-btn">
        		没有账号？<a href="${basePath }/toregist">立即注册</a>
        	</div>
		</div>
	</div>
	<!--用户登录信息END  -->
	<div class="line-middle">
		<em>OR</em>
  	</div>
  	
  	<!-- 三方登录 -->
	<div class="other-cont">
  		<div class="third-party">
    		<h2>使用以下账号直接登录</h2>
        	<ul class="clearfix"><!-- ${basePath }/user/authorizeWb -->
        		<%--<li class="ico-1"><a href="${basePath }/user/authorizeWb" target="none"><em></em><span>新浪微博</span></a></li>--%>
            	<!--  <li class="ico-2"><a href="${basePath }/user/authorizePage"><em></em><span>QQ登录</span></a></li>-->
            	<li class="ico-3"><a href="${basePath }/user/authorizeWx"><em></em><span>微信登录</span></a></li>
        	</ul>
    	</div>
	</div>
	<!-- 三方登录END -->
	
</div>
<!-- core public JavaScript --> 
<script src="${basePath }/static/assets/js/public/jquery-1.11.0.min.js"></script> 
<script src="${basePath }/static/assets/js/public/jquery-migrate-1.0.0.js"></script> 
<script src="${basePath }/static/assets/js/public/rong-dialog.js"></script>
<script src="${basePath }/pages/home/user/js/login.js"></script>
<script src="${basePath }/static/assets/js/public/comm.js"></script>
<script src="${basePath }/static/common/js/jQuery.md5.js" type="text/javascript"></script>
</body>
</html>