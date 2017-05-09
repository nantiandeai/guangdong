<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<%@include file="/pages/comm/agdhead.jsp"%>
<%@include file="/pages/comm/comm_video.jsp"%>
<title>广东省文化馆-馆务公开-馆办团队-团队介绍</title>
<link href="${basePath }/static/assets/css/museum/museum.css" rel="stylesheet">
<script src="${basePath }/static/assets/js/museum/museum.js"></script>
</head>
<!-- 公共头部开始 -->
<%@include file="/pages/comm/agdtopsmall.jsp"%>
<!-- 公共头部结束-END -->

<!-- 二级栏目 -->
<div id="header-fix">
    <div class="header-nav-bg">
        <div class="header-nav">
            <div class="logo-small">
                <a href="${basePath }/home"><img src="${basePath }/static/assets/img/public/logoSmall.png"></a>
            </div>
            <ul>
                <li><a href="${basePath }/agdgwgk/index">省馆介绍</a></li>
                <li><a href="${basePath }/agdgwgk/jigou">组织机构</a></li>
                <li><a href="${basePath }/agdgwgk/fagui">政策法规</a></li>
                <li><a href="${basePath }/agdgwgk/zhinan">业务指南</a></li>
                <li class="active"><a href="${basePath }/agdgwgk/tuandui">馆办团队</a></li>
                <li class="last"><a href="${basePath }/agdgwgk/fankui">意见反馈</a></li>
            </ul>
        </div>
    </div>
</div>
<!-- 二级栏目-END -->

<!--面包屑开始-->
<div class="public-crumbs">
    <span><a href="${basePath }/home">首页</a></span><span>></span>
    <span><a href="${basePath }/agdgwgk/index">馆务公开</a></span><span>></span>
    <span><a href="${basePath }/agdgwgk/tuandui">馆办团队</a></span><span>></span>
    <span>${troupe.troupename }</span>
</div>
<!--面包屑结束-->

<!--主体开始-->
<div class="main-info-bg main-info-no-padding main-info-bgColorW">
    <div class="main-info-container">
        <div class="team">
        
        	<!-- 团队介绍 -->
            <div class="team-pre clearfix">
                <h3><span>团队介绍</span></h3>
                <div class="img">
                    <img src="${imgServerAddr }/${troupe.troupepic}" width="320" height="200">
                </div>
                <div class="detail">
                    <h2>${troupe.troupename }&nbsp;</h2>
                    <p>团队地址：<span>${troupe.troupeaddr }&nbsp;</span></p>
                    <p>联系方式：<span>${troupe.troupecontact }&nbsp;</span></p>
                    <p>${troupe.troupedesc } &nbsp;</p>
                </div>
            </div>
            <!-- 团队介绍-END -->
            
            <!-- 团队荣誉 -->
            <c:if test="${troupe_ry_list != null && fn:length(troupe_ry_list) > 0}">
            <div class="team-pre clearfix">
                <h3><span>团队荣誉</span></h3>
                <div class="list">
                	<ul>
                		<c:forEach items="${troupe_ry_list }" var="row" varStatus="s">
                			<c:if test="${s.index < 10 }">
	                			<li><span>0${s.index }.</span>${row.rydesc }&nbsp;</li>
                			</c:if>
                			<c:if test="${s.index > 9 }">
	                			<li><span>${s.index }.</span>${row.rydesc }&nbsp;</li>
                			</c:if>
                		</c:forEach>
                	</ul>
                </div>
            </div>
            </c:if>
            <!-- 团队荣誉-END -->
            
            <!-- 团队详细介绍 -->
            <div class="team-pre clearfix">
                <h3><span>团队详细介绍</span></h3>
                <p> ${troupe.troupecontent } &nbsp;</p>
            </div>
            <!-- 团队详细介绍-END -->
            
            <!-- 团队成员 -->
            <div class="team-pre clearfix ${empty troupe_user_list? 'none' : ''}">
                <h3>
                    <span>团队成员</span>
                    <div class="pre-right"><i class="kx-arrow kx-arrow-left"><em></em><span></span></i></div>
                    <div class="pre-left"><i class="kx-arrow kx-arrow-right"><em></em><span></span></i></div>
                </h3>
                <div class="members-list">
                    <ul class="clearfix">
                        <c:if test="${empty troupe_user_list}">
                            <li class="none"></li>
                        </c:if>
                    	<c:if test="${troupe_user_list != null && fn:length(troupe_user_list) > 0}">
	                		<c:forEach items="${troupe_user_list }" var="row" varStatus="s">
		                        <li>
		                            <div class="logo"><i><img src="${imgServerAddr }/${row.tupic }" width="100" height="100"></i></div>
		                            <div class="name"><span>${row.tuname} &nbsp;</span></div>
		                            <div class="detail-text"><p> ${row.tudesc }&nbsp;</p></div>
		                        </li>
	                		</c:forEach>
	                	</c:if>
                    </ul>
                </div>
            </div>
            <!-- 团队成员-END -->
            
            <!-- 团队图片 -->
            <c:if test="${troupe_pic_list != null && fn:length(troupe_pic_list) > 0}">
            <div class="team-pre clearfix">
                <h3>
                	<span>团队图片</span>
                	<div class="pre-right1"><i class="kx-arrow kx-arrow-left"><em></em><span></span></i></div>
                	<div class="pre-left1"><i class="kx-arrow kx-arrow-right"><em></em><span></span></i></div>
                </h3>
                <div class="img-list">
                    <ul class="clearfix">
                		<c:forEach items="${troupe_pic_list }" var="row" varStatus="s">
	                        <li>
	                            <a href="javascript:void(0)">
	                            	<c:if test="${row.enturl != null }">
		                                <div class="img" style="background-image: url(${imgServerAddr }/${row.enturl })">
		                                    <div class="mask"></div>
		                                </div>
	                            	</c:if>
	                            	<c:if test="${row.enturl == null }">
		                                <div class="img" style="background-image: url(${basePath }/static/assets/img/img_demo/tuandui-img1.jpg)">
		                                    <div class="mask"></div>
		                                </div>
	                            	</c:if>
	                            </a>
	                        </li>
                		</c:forEach>
                    </ul>
                </div>
            </div>
	        </c:if>
            <!-- 团队图片-END -->
            
            <!-- 团队视频 -->
            <c:if test="${troupe_video_list != null && fn:length(troupe_video_list) > 0}">
            <div class="team-pre clearfix">
                <h3>
                	<span>团队视频</span>
                	<div class="pre-right2"><i class="kx-arrow kx-arrow-left"><em></em><span></span></i></div>
                    <div class="pre-left2"><i class="kx-arrow kx-arrow-right"><em></em><span></span></i></div>
                </h3>
                <div class="video-list">
                    <ul class="clearfix">
                    	<c:forEach items="${troupe_video_list }" var="row" varStatus="s">
	                        <li>
	                            <a href="javascript:void(0)" class="opt_videoshow" vsurl="${basePath }/ ${row.enturl } ">
	                                <div class="img" style="background-image: url(${imgServerAddr }/${row.deourl })">
	                                    <div class="mask">
	                                        <img src="${basePath }/static/assets/img/public/vedioBg.png">
	                                    </div>
	                                </div>
	                            </a>
	                        </li>
                    	</c:forEach>
                    </ul>
                </div>
            </div>
            </c:if>
			<!-- 团队视频-END -->
        </div>
    </div>
</div>
<!--主体结束-->

<!--公共主底部开始-->
<%@include file="/pages/comm/agdfooter.jsp"%>
<!--公共主底部结束-END-->
</body>
</html>