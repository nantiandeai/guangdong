<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
    
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
request.setAttribute("basePath", basePath);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>管理员登录</title>
<link href="${basePath }/static/admin/css/admin.css" rel="stylesheet">
<script type="text/javascript" src="${basePath }/static/admin/easyui/jquery.min.js" ></script>
<script type="text/javascript" src="${basePath }/static/common/js/jQuery.md5.js"></script>

<script type="text/javascript">

	function befSubmit(){
		var pwd = $("input[name='password']").val();
		var pwdmd5 = $.md5(pwd);
		$("input[name='password']").val(pwdmd5);
		
		return true;
	}
	
	$(function(){
        var topURI = window.parent.location;
        var nowURI = window.location;
        if (topURI != nowURI){
            window.parent.location.reload();
        }
    });
</script>
</head>
<body class="loginBg">
<h1>&nbsp;</h1>
<div class="login">
    <form action="${basePath }/admin/logindo" method="post" onsubmit="return befSubmit()">
        <div>
            <input type="text" name="name"  placeholder="User ID"/>
        </div>
        <div>
            <input type="password" name="password"  placeholder="Password"/>
        </div>
        <div>
            <c:if test="${not empty msg}">
                <p class="msg" style="color: #fff;">${msg}</p>
            </c:if>
            <button type="submit">登 录</button>
        </div>
    </form>
</div>
</body>
</html>