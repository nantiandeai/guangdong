<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<%@include file="/pages/comm/agdhead.jsp"%>
<title>${zypx.zypxtitle}</title>
<link href="${basePath }/static/assets/css/public/reset.css" rel="stylesheet">
<link href="${basePath }/static/assets/css/train/vodDetail.css" rel="stylesheet">
<link href="${basePath }/static/assets/js/plugins/oiplayer-master/css/oiplayer.css" rel="stylesheet">
<script src="${basePath }/static/assets/js/plugins/oiplayer-master/plugins/flowplayer-3.2.6.min.js"></script>
<script src="${basePath }/static/assets/js/plugins/oiplayer-master/js/jquery.oiplayer.js"></script>
<script src="${basePath }/static/assets/js/train/vodDetail.js"></script>
<script type="text/javascript">
$(function(){
	var zypxkeys = {};
	zypxkeys = "${zypx.zypxkey}";
	$.ajax({
		type: "POST",
		url: "${basePath}/agdzyfw/getKey",
		data: {zypxkeys :zypxkeys},
		success: function(data){
			for (var i = 0; i < data.length; i++) {
				//alert(data[i].name);
				$("div.label").append('<span>'+data[i].name+'</span>');
			}
		}
	});
	
})

/* $(document).ready(function(e){
		 $('#calendar').eCalendar({
			events: [
				{title: '课程1', description: '<span>第一节 9:00-10:00</span> <span>第二节  10:00-11:00</span> <span>第三节  11:00-12:00</span> <span>第四节  11:00-12:00</span> <span>第五节  11:00-12:00</span>', datetime: new Date(2016, 11, 11)},
				{title: '课程2', description: '<span>第一节 9:00-10:00</span> <span>第二节  10:00-11:00</span> <span>第三节  11:00-12:00</span>', datetime: new Date(2016, 11, 25)},
				{title: '课程3', description: '<span>第一节 9:00-10:00</span> <span>第二节  10:00-11:00</span> <span>第三节  11:00-12:00</span>', datetime: new Date(2016, 12, 2)},
				{title: '课程3', description: '<span>第一节 9:00-10:00</span> <span>第二节  10:00-11:00</span> <span>第三节  11:00-12:00</span>', datetime: new Date(2016, 12, 5)},
				{title: '课程3', description: '<span>第一节 9:00-10:00</span> <span>第二节  10:00-11:00</span> <span>第三节  11:00-12:00</span>', datetime: new Date(2016, 12, 6)},
				{title: '课程3', description: '<span>第一节 9:00-10:00</span> <span>第二节  10:00-11:00</span> <span>第三节  11:00-12:00</span>', datetime: new Date(2016, 12, 13)},
				{title: '课程3', description: '<span>第一节 9:00-10:00</span> <span>第二节  10:00-11:00</span> <span>第三节  11:00-12:00</span>', datetime: new Date(2016, 12, 22)},
				{title: '课程3', description: '<span>第一节 9:00-10:00</span> <span>第二节  10:00-11:00</span> <span>第三节  11:00-12:00</span>', datetime: new Date(2017, 1, 5)}
			]
		});
	}); */


</script>

<!--[if lt IE 9] >
    <script src="${basePath }/static/assets/js/plugins/ie/IE9.js"></script>
<! [endif]]-->
</head>
<body class="oiplayer-example">
<!-- 公共头部开始 -->
<%@include file="/pages/comm/agdtopsmall.jsp"%>
<!-- 公共头部结束-END -->

<div id="header-fix">
    <div class="header-nav-bg">
        <div class="header-nav">
            <div class="logo-small">
                <a href="${basePath }/home"><img src="${basePath }/static/assets/img/public/logoSmall.png"></a>
            </div>
            <ul>
                <li><a href="${basePath }/agdzyfw/index">志愿服务</a></li>
                <li><a href="${basePath }/agdzyfw/news">志愿资讯</a></li>
                <li><a href="${basePath }/agdzyfw/huodong">志愿活动</a></li>
                <li class="active"><a href="${basePath }/agdzyfw/peixun">志愿培训</a></li>
                <li><a href="${basePath }/agdzyfw/xiangmu">风采展示</a></li>
                <li><a href="${basePath }/agdzyfw/tashan">他山之石</a></li>
                <li class="last"><a href="${basePath }/agdzyfw/zhengce">政策法规</a></li>
            </ul>
        </div>
    </div>
</div>
<!--公共主头部结束-->
<!--面包屑开始-->
<div class="public-crumbs">
    <span><a href="${basePath }/dgszwhg/index">首页</a></span><span>></span><span><a href="${basePath }/agdzyfw/index">志愿服务</a></span><span>></span><span><a href="${basePath }/agdzyfw/peixun">志愿培训</a></span><span>></span>
</div>
<!--面包屑结束-->
<!--主体开始-->
<div class="main-info-bg main-info-no-padding">
    <div class="main-info-container clearfix">
        <div class="public-left-main">
        	<div class="public-info-step">
	            <div class="video-detail">
	                <h1>${zypx.zypxtitle}</h1>
	                <div class="detail clearfix">
	                    <%--<div class="time">时长：<span>${zypx.zypxvideolen}</span></div>--%>
	                    <div class="source">来源：<span>${zypx.zypxfrom}</span></div>
	                    <div class="label">关键字：<span>${zypx.zypxkey} </span></div>
	                </div>
	                <div class="video">
	                	<div id="clientcaps"></div>
	                    <video width="800" height="525" poster="${imgServerAddr }/${not empty zypx.zypxpic?zypx.zypxpic:'static/assets/img/public/vedioBg.jpg'}" controls  oncontextmenu="return false">
	                    	<c:set var="video_url" value="${zypx.zypxvideo}"></c:set>
		                    <c:if test="${fn:startsWith(zypx.zypxvideo, 'upload')}">
		                    	<c:set var="video_url" value="${basePath }/${zypx.zypxvideo}"></c:set>
		                    </c:if>
		                    <source type="video/mp4" src="${video_url}" alt="szwhg" />
	                      <%-- <source type="video/mp4" src="${zypx.zypxvideo}" /> --%>
	                    </video>
	                </div>
	            </div>
	            <h3><span>视频简介</span></h3>
	            <div class="info">
	            	${zypx.zypxcontent}
	            </div>
	            <!-- 分享 -->
	            <div class="public-share">
	                <span class="btn qq"><a href="javascript:void(0);" class="fxqq"></a></span>
	                <span class="btn weixin"><a href="javascript:void(0)" class="fxweix"></a></span>
					<span class="btn weibo"><a href="javascript:void(0)" class="fxweibo" target="_blank"></a></span>
	                <span class="btn dianzan">
	                	<em>0</em>
	                	<a href="javascript:void(0)" class="dianzan" reftyp="2016120900000003" refid="${zypx.zypxid}" id="good">
	                    </a>
	                </span>
            	</div> 
            </div>
           <!-- 动态包含评论 -->
        <jsp:include page="/pages/comm/agdcomment.jsp" flush="true">     
		     <jsp:param name="reftype" value="5"/>
		     <jsp:param name="refid" value="${zypx.zypxid}"/> 
		</jsp:include>
		<!-- 动态包含评论-END -->
    	</div>
	        <div class="public-right-main">
		    	<div class="public-other-notice">
		        	<h2>视频推荐</h2>
		        	<c:choose>
					   <c:when test="${not empty shipin }">  
						   <c:forEach items="${shipin }" var="item">
					            <div class="item clearfix">
				                    <div class="right-img">
				                        <a href="${basePath }/agdzyfw/peixuninfo?zypxid=${item.zypxid}"><img src="${imgServerAddr }/${item.zypxpic}" width="130" height="90"></a>
				                    </div>
				                    <div class="right-detail">
				                        <a href="${basePath }/agdzyfw/peixuninfo?zypxid=${item.zypxid}"><h3>${item.zypxshorttitle}</h3></a>
				                        <p class="time"><fmt:formatDate value="${item.zypxopttime}" pattern="yyyy-MM-dd "/></p>
				                    </div>
			                	</div>
				            </c:forEach>      
					   </c:when>
					   <c:otherwise> 
					   		<div class="public-no-message "></div>
					   </c:otherwise>
					</c:choose>
		        	
		        	<%-- //
		        	<c:if test="${not empty shipin }">
		        		<c:forEach items="${shipin }" var="item">
				            <div class="item clearfix">
		                    <div class="right-img">
		                        <a href="${basePath }/agdzyfw/peixuninfo?zypxid=${item.zypxid}"><img src="${basePath }/static/assets/${item.zypxpic}" width="130" height="90"></a>
		                    </div>
		                    <div class="right-detail">
		                        <a href="${basePath }/agdzyfw/peixuninfo?zypxid=${item.zypxid}"><h3>${item.zypxshorttitle}</h3></a>
		                        <p class="time"><fmt:formatDate value="${item.zypxopttime}" pattern="yyyy-MM-dd "/></p>
		                    </div>
		                </div>
			            </c:forEach>
		            </c:if> --%>
		        </div>
	    	</div>
	</div>
</div>

<!--主体结束-->
<!--公共主底部开始-->
<%@include file="/pages/comm/agdfooter.jsp"%>
<!--公共主底部结束-END-->
<script>
    	$(document).ready(function(e) {
			$('body.oiplayer-example').oiplayer({
				server : 'http://www.openimages.eu',
				jar: '${basePath }/static/assets/js/plugins/oiplayer-master/plugins/cortado-ovt-stripped-0.6.0.jar',
				flash: '${basePath }/static/assets/js/plugins/oiplayer-master/plugins/flowplayer-3.2.7.swf',
				controls: 'top',
				log: 'info'
				 /* msie (or java) has issues with just a dir */
			});
		});
    </script>
<a class="to-top"></a>
</body>
</html>