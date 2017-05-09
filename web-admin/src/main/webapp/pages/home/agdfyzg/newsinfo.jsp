<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<%@include file="/pages/comm/agdhead.jsp"%>
<title>广东省文化馆-非遗展馆-新闻动态-${wh_zx_colinfo.clnftltle }</title>
<link href="${basePath }/static/assets/css/train/noticeInfo.css" rel="stylesheet">
<link href="${basePath }/static/assets/js/plugins/oiplayer-master/css/oiplayer.css" rel="stylesheet">
<script src="${basePath }/static/assets/js/plugins/oiplayer-master/plugins/flowplayer-3.2.6.min.js"></script>
<script src="${basePath }/static/assets/js/plugins/oiplayer-master/js/jquery.oiplayer.js"></script>
<script src="${basePath }/static/assets/js/public/public.js"></script>
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
</head>
<body class="oiplayer-example">
<!-- 公共头部开始 -->
<%@include file="/pages/comm/agdtopsmall.jsp"%>
<!-- 公共头部结束-END -->

<!--公共主头部开始-->
<div id="header-fix">
	<div class="header-nav-bg">
		<div class="header-nav">
			<div class="logo-small">
				<a href="${basePath }/home"><img src="${basePath }/static/assets/img/public/logoSmall.png"></a>
			</div>
			<ul>
				<li><a href="${basePath }/agdfyzg/index">非遗首页</a></li>
				<li><a href="${basePath }/agdfyzg/notice">最新公告</a></li>
				<li class="active"><a href="${basePath }/agdfyzg/news">新闻动态</a></li>
				<li><a href="${basePath }/agdfyzg/minglu">名录项目</a></li>
				<li><a href="${basePath }/agdfyzg/chuanchengren">传承人</a></li>
				<li><a href="${basePath }/agdfyzg/falvwenjian">法律文件</a></li>
				<li class="last"><a href="${basePath }/agdfyzg/shenbao">申报指南</a></li>
				<%-- <li class="last"><a href="${basePath }/agdfyzg/xxx">非遗资源</a></li> --%>
			</ul>
		</div>
	</div>
</div>
<!--公共主头部结束-END-->

<!--面包屑开始-->
<div class="public-crumbs">
    <span><a href="${basePath }/home">首页</a></span><span>></span>
    <span><a href="${basePath }/agdfyzg/index">非遗展馆</a></span><span>></span>
    <span><a href="${basePath }/agdfyzg/news">新闻动态</a></span><span>></span>
    <span>${wh_zx_colinfo.clnftltle }</span>
</div>
<!--面包屑结束-->

<!--主体开始-->
<div class="main-info-bg main-info-no-padding main-info-bgColorW">
	<div class="main-info-container clearfix">
		<!-- 资讯详情 -->
		<div class="public-left-main">
			<div class="noticeInfo-main">
				<h1>${wh_zx_colinfo.clnftltle }</h1>
				<div class="otherInfo">
					<span class="adr">来源 :<span>${wh_zx_colinfo.clnfsource }</span></span>
					<span class="key">关键字 :<span>${wh_zx_colinfo.clnfkey }</span></span> 
					<span class="time">时间 :<span><fmt:formatDate value="${wh_zx_colinfo.clnfcrttime}" pattern="yyyy.MM.dd" /></span></span>
				</div>
				<div class="info-main">${wh_zx_colinfo.clnfdetail }</div>
				
				<!-- 视频 -->
				<c:forEach items="${loadent }" var="loadents" varStatus="s">
				 <div class="video">
                	<div id="clientcaps"></div>
                	<c:if test="${not empty loadents.deourl}">
                    <video width="800" height="525" poster="${imgServerAddr}/${loadents.deourl}" controls  oncontextmenu="return false">
                    </c:if>
                    <c:if test="${empty loadents.deourl}">
                    <video width="800" height="525" poster="${basePath }/static/assets/img/public/vedioBg.jpg" controls  oncontextmenu="return false">
                    </c:if>
                    <c:set var="video_url" value="${loadents.enturl}"></c:set>
                    <c:if test="${fn:startsWith(loadents.enturl, 'upload')}">
                    	<c:set var="video_url" value="${basePath }/${loadents.enturl}"></c:set>
                    </c:if>
                    <source type="video/mp4" src="${video_url}" alt="szwhg" />
                    <%-- <source type="video/mp4" src="${fn:startsWith(loadents.enturl, 'upload') ? basePath+'/'+loadents.enturl : loadents.enturl}" /> --%>
                    </video>
                    <p>${loadents.entname }</p>
            	</div>
            	</c:forEach>
            	
            	<!-- 音频 -->
            	<c:forEach items="${loadclazz}" var="loadclazzs" varStatus="s">
            	<div class="video">
                	<div id="clientcaps2"></div>
                    <video width="800" height="525" poster="${basePath }/static/assets/img/public/vedioBg.jpg" controls  oncontextmenu="return false">
                      <source type="video/mp4" src="${basePath }/${loadclazzs.enturl}" />
                    </video>
                    <p>${loadclazzs.entname }</p>
            	</div>
            	</c:forEach>
            	
            	<!-- 图片 -->
	           	<c:forEach items="${loadwhe }" var="loadwhes" varStatus="s">
		           <div class="img-upload-list">
		           	<img src="${imgServerAddr }/${loadwhes.enturl}">
		               <p>${loadwhes.entname }</p>
		           </div>
	            </c:forEach>
	            
	            <!-- 下载 -->
	            <c:if test="${not empty loadlist}">
	            <div class="file-download-cont">
	            	<ul>
	            	   <c:forEach items="${loadlist}" var="loadlists" varStatus="s">
	                	<li>
	                    	<a href="${basePath }/whtools/downFile?filePath=${loadlists.uplink}"><i></i>${loadlists.upname}</a>
	                    </li>
	                      </c:forEach>
	                </ul>
	            </div>
				</c:if>
				
				<div class="next-notice">
					<a href="${basePath }/agdfyzg/newsinfo?id=${wh_zx_colinfo_next.clnfid}">下一篇<span>${wh_zx_colinfo_next.clnftltle}</span></a>
				</div>
				<!-- 分享 -->
				<div class="public-share">
	                <span class="btn qq"><a href="javascript:void(0);" class="fxqq"></a></span>
	                <span class="btn weixin"><a href="javascript:void(0)" class="fxweix"></a></span>
					<span class="btn weibo"><a href="javascript:void(0)" class="fxweibo" target="_blank"></a></span>
	            </div>
				
			</div>
		</div>
		<!-- 资讯详情-END -->
		
    	<!-- 相关推荐 -->
		<div class="public-right-main">
			<div class="public-other-notice">
				<h2>相关推荐</h2>
				<ul>
					<c:if test="${wh_zx_colinfo_ref != null && fn:length(wh_zx_colinfo_ref) > 0}">
						<c:forEach items="${wh_zx_colinfo_ref }" var="row" varStatus="s">
							<li><a href="${basePath }/agdfyzg/newsinfo?id=${row.clnfid}">${row.clnftltle }</a><p class="time"><fmt:formatDate value="${row.clnfcrttime}" pattern="yyyy-MM-dd" /></p></li>
						</c:forEach>
					</c:if>
				</ul>
			</div>
		</div>
		<!-- 相关推荐-END -->
	</div>
</div>
<!--主体结束-->

<!--公共主底部开始-->
<%@include file="/pages/comm/agdfooter.jsp"%>
<!--公共主底部结束-END-->
</body>
</html>