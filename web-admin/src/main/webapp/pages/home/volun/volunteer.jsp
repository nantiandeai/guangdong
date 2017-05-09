<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath(); request.setAttribute("basePath", basePath); %>
<% String idx = request.getParameter("idx"); %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv=”X-UA-Compatible” content=”IE=edge,chrome=1″ />
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="keywords" content="">
    <meta name="author" content="">
    <title>广东省文化馆-个人文化展</title>
    <link href="${basePath }/static/assets/css/public/reset.css" rel="stylesheet">
    <link href="${basePath }/static/assets/css/publicity/publicityindex.css" rel="stylesheet">
    <!--[if lt IE 9] >
    <script src="${basePath }/static/assets/js/plugins/ie/IE9.js"></script>
    <! [endif]]-->
    <!-- core public JavaScript -->
	<script src="${basePath }/static/assets/js/public/jquery-1.11.0.min.js"></script>
	<script src="${basePath }/static/assets/js/public/jquery-migrate-1.0.0.js"></script>
	<script src="${basePath }/static/assets/js/public/comm.js"></script>
</head>
<body>

<!-- 公共头部开始 -->
<%@include file="/pages/comm/szheader.jsp"%>
<!-- 公共头部结束 -->

<!--广告开始-->
<div class="page-ad" style="background:url(${basePath }/static/assets/img/publicity/pub-banner.jpg) no-repeat 50% 50%"></div>
<!--广告结束-->

<!--主体开始-->
<div class="list-row-1 clearfix">
	<div class="center-title">
    	<h2>最新动态</h2>
        <p>RECENT NEWS</p>
        <hr>
    </div>
    <div class="left-cont">
    	<div class="public-title">志愿者新闻</div>
        <div class="img">
           <c:forEach items="${hot}" var="hots" varStatus="s">
           <c:if test="${s.first}">
        	<a href="${basePath }/zhiyuan/zyzinfo?clnfid=${hots.cfgshowid}"><img src="${basePath }/${hots.cfgshowpic}"></a>
           </c:if>
        	</c:forEach>
        </div>
        <div class="public-container">
        	<ul>
        	    <c:forEach items="${hot}" var="hots" varStatus="s">
        	     <c:if test="${!s.first and s.index < 5}">
            	<li><a href="${basePath }/zhiyuan/zyzinfo?clnfid=${hots.cfgshowid}">${hots.cfgshowtitle}</a></li>
            	</c:if>
				</c:forEach>
            </ul>
            <center><a href="http://dgvolunteer.dg.gov.cn/fw_sy?orgid=70" class="more-center">查看更多</a></center>
        </div>
    </div>
    <div class="right-cont">
    	<div class="r-row-1">
        	<div class="public-title">热门动态<a href="http://dgvolunteer.dg.gov.cn/fw_zxdt?orgid=70" class="more">MORE+</a></div>
             <div class="public-container">
                <ul>
                   	<c:forEach items="${rmdt}" var="hots" varStatus="s">
                   	   <c:if test="${s.index < 5 }">
                    	<li>
                    	<a href="${basePath }/zhiyuan/zyzinfo?clnfid=${hots.cfgshowid}">${hots.cfgshowtitle}</a><div class="timeCont"><span>
                    	<fmt:formatDate value='${hots.cfgshowtime}' pattern='MM/dd/'/></span><fmt:formatDate value='${hots.cfgshowtime}' pattern='yyyy'/></div>
                    	</li>
                    	</c:if>
	    		   </c:forEach>
	    		   
                </ul>
            </div>
        </div>
        <div class="r-row-2">
        	<div class="public-title">培训动态<a href="http://dgvolunteer.dg.gov.cn/fw_pxlb?type=1&orgid=70" class="more">MORE+</a></div>
            <div class="public-container">
                <ul>
                	<c:forEach items="${pxdt}" var="hots" varStatus="s">
                	  <c:if test="${s.index < 5 }">
	                   <li><a href="${basePath }/zhiyuan/zyzinfo?clnfid=${hots.cfgshowid}">${hots.cfgshowtitle}</a><div class="timeCont">
	                   <span> <fmt:formatDate value='${hots.cfgshowtime}' pattern='MM/dd/'/></span><fmt:formatDate value='${hots.cfgshowtime}' pattern='yyyy'/></div>
	                   </li>
                      </c:if>
				    </c:forEach>
                </ul>
            </div>
        </div>
    </div>
</div>
<div class="list-row-2 clearfix">
	<div class="center-title">
    	<h2>志愿者中心</h2>
        <p>VOLUNTEER CENTER</p>
        <hr>
    </div>
    <ul class="clearfix">
     <c:forEach items="${zyzx }" var="hots" varStatus="s">
     	<c:if test="${s.index < 9 }">
    	 <li>
        	<a href="${hots.cfgshowlink }" style="background:url(${basePath }/${hots.cfgshowpic}) no-repeat 50% 50%;">
            	<div class="txt">${hots.cfgshowtitle}</div>
            	<div class="mask"></div>
                <div class="bigMask"></div>
            </a>
         </li>
        </c:if>
     </c:forEach>
    </ul>
</div>

<!--底部开始-->
<%@include file="/pages/comm/szfooter.jsp"%>
<!--底部结束--> 
</body>
</html>