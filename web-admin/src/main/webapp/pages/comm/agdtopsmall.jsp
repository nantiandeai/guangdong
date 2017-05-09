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
<script type="text/javascript">
	$.ajax({
		type : "POST",
		url : '${basePath}/center/alertHeader',
		success : function(data){
			if(data.success == "0"){
				if (data.msgCount && data.msgCount>0){
					$(".comm_alert").html(data.msgCount);
					$(".comm_alert").addClass("userMsg");
				}else{
					$(".comm_alert").html("");
					$(".comm_alert").removeClass("userMsg");
				}
			}
		}
	});

	$(function () {
		$("#header-sm").on("click", ".searchContLt .searchBtn", function(event){
			var _input = $(this).siblings('input:first');
			if (_input && _input.val()){
				window.location = "${basePath}/search?srchkey="+ encodeURIComponent(_input.val());
			}
		})

		$("#header-sm").on("keydown", ".searchContLt input:first", function(event){
			if (event.keyCode==13){
				$(this).siblings(".searchBtn").click();
			}
		});
	})
</script>
<div id="header-sm">
	<div class="header-nav-top-bg">
		<div class="header-nav">
			<ul id="whgnav">
                <li><a href="${basePath }/">首页</a></li>
				<li><a href="${basePath }/agdgwgk/index">馆务公开</a></li>
				<li><a href="${basePath }/agdzxdt/index">资讯动态</a></li>
				<li><a href="${basePath }/agdwhhd/index">文化活动</a></li>
				<li><a href="${basePath }/agdcgfw/index">场馆服务</a></li>
				<li><a href="${basePath }/agdpxyz/index">培训驿站</a></li>
				<li><a href="${basePath }/agdszzy/index">数字资源</a></li>
				<li><a href="${basePath }/agdszzg/index">数字展馆</a></li>
				<li><a href="${basePath }/agdfyzg/index">非遗中心</a></li>
				<li><a href="${basePath }/agdzyfw/index">志愿服务</a></li>
				<li><a href="${basePath }/agdwhlm/index">文化馆联盟</a></li>
			</ul>
			<div class="loginIn">
				 <span class="userName"><i></i><a href="${basePath }/center/userInfo">
					 <c:choose>
					 	<c:when test="${not empty sessionUser}" >
					 		${sessionUser.nickname }
					 		<span class="comm_alert"></span>
					 	</c:when>
					 	<c:otherwise><a href="${basePath }/login" class="btn">登录</a></c:otherwise>
					 </c:choose>
				</a></span> 
				<span><a href="${basePath }/sessionExit">
				<c:choose>
					 	<c:when test="${not empty sessionUser}" >
					 		<span class="quit"><a href="${basePath }/sessionExit">退出</a></span>
					 	</c:when>
					 	<c:otherwise><a href="${basePath}/toregist" id="regist" class="btn">注册</a></c:otherwise>
				</c:choose>
				</a></span> 
			</div>
			<div class="searchContLt">
				<input name="srchkey" class="searchInput" placeholder="搜您喜欢的...">
				<a href="javascript:void(0)" class="searchBtn"></a>
			</div>
		</div>
	</div>
</div>