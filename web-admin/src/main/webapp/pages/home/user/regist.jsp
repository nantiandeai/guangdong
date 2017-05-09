<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setAttribute("basePath", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath());%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>广东省文化馆-会员注册</title>
	<link href="${basePath }/static/assets/css/public/reset.css" rel="stylesheet">
	<link href="${basePath }/static/assets/css/regist/regist.css" rel="stylesheet">
	<script src="${basePath }/static/assets/js/public/rong-dialog.js"></script>
	<!--[if lt IE 9] >
	<script src="${basePath }/static/assets/js/plugins/ie/IE9.js"></script>
	<! [endif]]-->
	<script>var basePath = '${basePath}';  var imgServerAddr = '${imgServerAddr}';</script>
</head>
<body>
<!--广东logo  -->
<a href="${basePath}/"><div class="regist-logo"></div></a>
<!--广东logoEND -->

<!-- 注册导航 -->
<ul class="crumbs crumbs-1st clearfix">
	<li class="step-1">1. 基本资料<em class="arrow"></em></li>
  	<li class="step-2">2. 完善资料<em class="arrow"></em></li>
  	<li class="step-3 last">3. 注册成功</li>
</ul>
<!-- 注册导航END -->

<!-- 主内容区 -->
<div class="main">
	<div class="main-cont">

		<div class="reLoad none"><a href="javascript:void(0)" onClick="history.go(0);">重新注册</a></div>
		<%--<div class="other-reg">或<a href="javascript:void(0)">邮箱注册</a></div>--%>

    	<dl class="phoneType clearfix">
      		<dt class="float-left">手机号</dt>
      		<dd class="float-left">
        		<input class="in-txt inputPhone" id="msgphone" name="msgphone" placeholder="输入11位手机号码" maxlength="11">     
        		<em></em>
      		</dd>
    	</dl>

    	<dl class="phoneCode clearfix" id="PhoneHide">
      		<dt class="float-left">手机验证码</dt>
      		<dd class="float-left">
        		<input class="in-txt inputPcode" readonly="readonly" id="msgcontent" name="msgcontent" placeholder="输入手机验证码" maxlength="6">
        		<em></em>
        		<div class="numAdd postCode sendCodeP none" id="getCode">发送验证码</div>
        		<em></em>
        		<div class="numAdd phoneNum none"><span></span>秒后可重新发送</div>
        		<em></em>
      		</dd>
    	</dl>

    	<dl class="mailType clearfix none">
      		<dt class="float-left">E-mail</dt>
      		<dd class="float-left">
        		<input class="in-txt inputEmail" id="emailaddr" name="emailaddr" placeholder="输入邮箱地址" maxlength="20">
        		<em></em>
      		</dd>
    	</dl>

    	<dl class="mailType clearfix none" id="codeHide">
      		<dt class="float-left">邮箱验证码</dt>
      		<dd class="float-left">
        		<input class="in-txt inputEcode" readonly="readonly" id="inputCode" placeholder="输入邮箱验证码" maxlength="6">
        		<em></em>
        		<div class="numAdd postCode emailCode none" id="sendCode">发送验证码</div>
        		<em></em>
        		<div class="numAdd emailNum none" id="reSend"><span></span>秒后可重新发送</div>
      		</dd>
    	</dl>

    	<dl class="clearfix">
      		<dt class="float-left">密码</dt>
      		<dd class="float-left">
        		<input class="in-txt password" id="password" type="password" placeholder="6-18位数字字母符号组合" maxlength="18">
        		<em></em>
      		</dd>
    	</dl>

    	<dl class="clearfix">
      		<dt class="float-left">确认密码</dt>
      		<dd class="float-left">
        		<input class="in-txt repassword" id="surePwd" type="password" placeholder="确认密码" maxlength="18">
        		<em></em>
      		</dd>
    	</dl>

    	<dl class="clearfix">
      		<dt class="float-left">&nbsp;</dt>
      		<dd class="float-left readPosition">
      			<div class="abBtn">认真阅读并同意《会员注册条款》</div>
      			<div class="float-left readClause">
        			<div class="readCont none">
            			<div class="closeRead"></div>
            			<h4>《会员注册条款》</h4>
               			<iframe src="./pages/home/user/readme.html"></iframe>
                		<div class="agree">
                			<input type="checkbox" class="ck_agree" checked="checked">我已阅读并接受《会员注册条款》
                		</div>
           			</div>
        		</div>
      		</dd>
    	</dl>

    	<dl class="clearfix">
      		<dt class="float-left">&nbsp;</dt>
      		<dd class="float-left">
        		<em><div class="msg" id="tipNext"></div></em>
        		<div class="goNext float-left" id="gonext">下一步</div>
      		</dd>
    	</dl>
   </div>
</div>
<!-- 主内容区END -->

<div class="mask none"></div>

<!-- core public JavaScript --> 
<script src="${basePath }/static/assets/js/public/jquery-1.11.0.min.js"></script> 
<script src="${basePath }/static/assets/js/public/formUI.js"></script> 
<script src="${basePath }/static/assets/js/public/jquery-migrate-1.0.0.js"></script> 
<script src="${basePath }/static/assets/js/public/comm.js"></script>
<script src="${basePath }/pages/home/user/js/regist.js"></script>
<script src="${basePath }/static/common/js/jQuery.md5.js" type="text/javascript"></script>
</body>
</html>