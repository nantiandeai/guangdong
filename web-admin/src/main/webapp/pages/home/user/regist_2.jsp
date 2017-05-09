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
<link href="${basePath }/static/assets/css/public/reset.css" rel="stylesheet">
<link href="${basePath }/static/assets/css/regist/regist.css" rel="stylesheet">
<!--[if lt IE 9] >
<script src="${basePath }/static/assets/js/plugins/ie/IE9.js"></script> 
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
<ul class="crumbs crumbs-2nd clearfix">
	<li class="step-1">1. 基本资料<em class="arrow"></em></li>
  	<li class="step-2">2. 完善资料<em class="arrow"></em></li>
  	<li class="step-3 last">3. 注册成功</li>
</ul>
<!-- 注册导航END -->

<div class="main">
	<div class="main-cont">
 		<div class="reLoad none"><a href="javascript:void(0)" onClick="history.go(0);">重新注册</a></div>
    	<div class="other-reg"></div>
    	
    	<!-- 注册信息 -->
		<dl class="clearfix">
			<dt class="float-left">昵称</dt>
			<dd class="float-left">
				<input class="in-txt" name="nickname" id="nickname" maxlength="20" placeholder="添加个性昵称"> <em></em>
				<em></em>
			</dd>
		</dl>
		<dl class="clearfix">
			<dt class="float-left">性别</dt>
			<dd class="float-left">
				<div class="radio radio-success">
					<input type="radio" value="1" name="radioSex" id="sex1" checked="checked">
					<label for="sex1">男</label> 
					<input type="radio" value="0" name="radioSex" id="sex0">
					<label for="sex0">女</label>
				</div>
				<em></em>
			</dd>
		</dl>
		<dl class="clearfix">
			<dt class="float-left">职业</dt>
			<dd class="float-left">
				<div class="box float-left">
					<select name="job" id="job">
						<option value="">请选择</option>
						<option value="政府机关">政府机关</option>
						<option value="互联网">互联网</option>
						<option value="电子商务">电子商务</option>
						<option value="信息传媒">信息传媒</option>
						<option value="通信">通信</option>
						<option value="金融">金融</option>
						<option value="学生">学生</option>
						<option value="其它">其它</option>
					</select>
				</div>
				<em></em>
			</dd>
		</dl>
		<dl class="clearfix">
			<dt class="float-left">出生日期</dt>
			<dd class="float-left">
				<div class="box float-left">
					<input class="in-txt dateChange" name="birthday" id="birthday" placeholder="请选择" required="required">
					<em></em>
				</div>
			</dd>
		</dl>
		<dl class="clearfix">
			<dt class="float-left">QQ号</dt>
			<dd class="float-left">
				<input class="in-txt" type="text" name="qq" id="qq" maxlength="20" data="${id}" placeholder="输入QQ号码"> 
				<em></em>
			</dd>
		</dl>
		<dl class="clearfix">
			<dt class="float-left">微信号</dt>
			<dd class="float-left">
				<input class="in-txt" type="txt" name="wx" id="wx" maxlength="20" placeholder="输入微信号"> 
				<em></em>
			</dd>
		</dl>
		<dl class="clearfix">
			<dt class="float-left">&nbsp;</dt>
			<dd class="float-left">
				<div class="goNext float-left" id="go_next">下一步</div>
			</dd>
		</dl>
		<!-- 注册信息END -->
		
	</div>
</div>
<div class="mask none"></div>
<!-- core public JavaScript --> 
<script src="${basePath }/static/assets/js/public/jquery-1.11.0.min.js"></script> 
<script src="${basePath }/static/assets/js/public/formUI.js"></script> 
<script src="${basePath }/static/assets/js/public/jquery-migrate-1.0.0.js"></script> 
<script src="${basePath }/static/assets/js/plugins/laydate.dev.min.js"></script>
<script src="${basePath }/static/assets/js/public/comm.js"></script>
<script src="${basePath }/pages/home/user/js/regist.js"></script>
</body>
</html>