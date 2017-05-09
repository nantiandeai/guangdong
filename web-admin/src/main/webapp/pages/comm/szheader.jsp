<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% 
String __nav = request.getParameter("nav"); request.setAttribute("__nav", __nav);
String sys_mode = request.getRequestURI(); 
sys_mode = sys_mode.substring(sys_mode.lastIndexOf("pages/")+6);
if("index.jsp".equals(sys_mode)){
	sys_mode = "";
}else if(sys_mode.endsWith("update.jsp")){
	sys_mode = sys_mode.substring(17);
	sys_mode = sys_mode.substring(0, sys_mode.lastIndexOf("/"));
}else if(sys_mode != null && sys_mode.startsWith("home")){
	sys_mode = sys_mode.substring(sys_mode.lastIndexOf("home/")+5);
	if(sys_mode.lastIndexOf("/") > -1){
		sys_mode = sys_mode.substring(0, sys_mode.lastIndexOf("/"));
	}
}
request.setAttribute("sys_mode", sys_mode); 
%>
<script>var __nav = '${sys_mode}'; var basePath = '${basePath}';</script>
<script src="${basePath }/pages/comm/agdtop.js"></script>
<div id="header">
	<div class="main clearfix">
		<!-- 广东logo -->
		<div class="logo"></div>
		
		<div class="rightMain">
			<div class="sysType clearfix">
				<!-- 首页注册登陆部分 -->
				<c:if test = "${empty sessionUser }">
				<div class="loginCont">
					<a href="${basePath }/login" class="btn">登录</a> 
					<a href="${basePath}/toregist" id="regist"  class="btn">注册</a>
				</div>
				</c:if>
				<c:if test = "${not empty sessionUser }">
					<div class="loginIn"> 
						<span class="userName"><i></i><a href="${basePath }/center/userCenter">${sessionUser.nickname}</a></span>
						<span class="quit"><a href="${basePath }/sessionExit">退出</a></span>
					</div>
				</c:if>
				
				<div class="h-line"></div> 
				
				<!-- 首页搜索框 -->
				<div class="searchCont">
					<input placeholder="搜点什么...">
					<button class="s-btn"></button>
				</div>
				
			</div>
			<ul class="nav clearfix" id="whgnav">
				<li><a href="${basePath }/">首页</a><span>/</span></li>
				<li><a href="${basePath }/agdgwgk/index">馆务公开</a><span>/</span></li>
				<li><a href="${basePath }/agdzxdt/index">资讯动态</a><span>/</span></li>
				<li><a href="${basePath }/agdwhhd/index">文化活动</a><span>/</span></li>
				<li><a href="${basePath }/agdcgfw/index">场馆服务</a><span>/</span></li>
				<li><a href="${basePath }/agdpxyz/index">培训驿站</a><span>/</span></li>
				<li><a href="${basePath }/agdszzy/index">数字资源</a><span>/</span></li>
				<li><a href="${basePath }/agdfyzg/index">非遗展馆</a><span>/</span></li>
				<li><a href="${basePath }/agdzyfw/index">志愿服务</a><span>/</span></li>
				<li><a href="${basePath }/agdwhlm/index">文化馆联盟</a></li>
			</ul>
		</div>
	</div>
</div>