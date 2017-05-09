<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath(); 
request.setAttribute("basePath", basePath);
%>

<meta name="description" content="${page_desc }">
<meta name="keywords" content="${page_key }">
<meta name="author" content="${page_author }">
<base href="${basePath }/">
<link href="./static/assets/css/public/reset.css" rel="stylesheet">
<!--[if lt IE 9] >
    <script src="${basePath }/static/assets/js/plugins/ie/IE9.js"></script>
<! [endif]]-->
<script src="./static/assets/js/public/jquery-1.11.0.min.js"></script>
<script src="./static/assets/js/public/jquery-migrate-1.0.0.js"></script>
<script src="./static/assets/js/public/rong-dialog.js"></script>

<script src="./static/assets/js/public/comm.js"></script>
