<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<script type="text/javascript">
	//查询
/*	$.ajax({
		type : "POST",
		url : '$ {basePath}/center/alertHeader',
		dataType: 'json',
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
	});*/

	$(function(){
	    //全局搜索
		$("#header").on("click", ".search-login .icon-search", function(event){
			var _input = $(this).siblings('input:first');
			if (_input && _input.val()){
				window.location = "${basePath}/search?srchkey="+encodeURIComponent(_input.val());
			}
		})
		$("#header").on("keydown", ".search input:first", function(event){
			if (event.keyCode==13){
				$(this).siblings(".icon-search").click();
			}
		});

		//登录状态处理
        $.ajax({
            type : "GET",
            url : '${basePath}/home/validLogin',
            dataType: 'json',
			cache: false,
            success : function(res){
                var data = res.data;
                if(res.success == "1"){
                    if(data.login == "true"){
                        $('#header .login').hide();
                        $('#header .logon').show();
                        $('#header .logon .userName a').text(data.username);
					}else{
						$('#header .login').show();
						$('#header .logon').hide();
					}
                }
            }
        });
	})
</script>
<script src="${basePath }/pages/comm/agdtop.js"></script>
<div id="header">
	<div class="main-header clearfix">
		<a href="${basePath}/home">
			<div class="logo">
			</div>
		</a>
		<div class="search-login">
			<!-- 未登录时显示 -->
			<div class="login" style="display: none;">
				<a href="${basePath }/login" class="login">登录</a>
				<a href="${basePath}/toregist" class="register">注册</a>
			</div>
			<!-- 未登录时显示END -->

			<!-- 已登录时显示 -->
			<div class="logon" style="display: none;">
				<span class="userName">
					Hi,&nbsp;<a href="${basePath }/center/userInfo"></a>&nbsp;您好！
				</span>
				<a href="${basePath }/sessionExit" class="esc">退出</a>
			</div>
			<!-- 已登录时显示END -->

			<span class="line"></span>
			<div class="search">
				<input type="text" name="srchkey" placeholder="搜索所需的内容">
				<a href="#" class="icon-search"></a>
			</div>
		</div>

	</div>

	<div class="header-nav clearfix">
		<div class="header-nav-content">
			<ul id="whgnav">
				<li><a href="${basePath }/">首&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;页</a></li>
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
		</div>
	</div>
</div>