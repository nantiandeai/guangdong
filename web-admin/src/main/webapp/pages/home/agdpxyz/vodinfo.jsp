<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<%@include file="/pages/comm/agdhead.jsp"%>
<title>${whDrsc.drsctitle }</title>
<link href="${basePath }/static/assets/css/train/vodDetail.css" rel="stylesheet">
<link href="${basePath }/static/assets/js/plugins/oiplayer-master/css/oiplayer.css" rel="stylesheet">
<script src="${basePath }/static/assets/js/plugins/oiplayer-master/plugins/flowplayer-3.2.6.min.js"></script>
<script src="${basePath }/static/assets/js/plugins/oiplayer-master/js/jquery.oiplayer.js"></script>
<script src="${basePath }/static/assets/js/train/vodDetail.js"></script>
<script type="text/javascript">
$(function(){
	var zypxkeys = {};
	zypxkeys = "${whDrsc.drsckey}".split(",");
	//key = zypxkeys.split(",");
	for (var i = 0; i < zypxkeys.length; i++) {
		key = zypxkeys[i];
		//alert(data[i].name);
		$("div.label").append('<span>'+key+'</span>');
	}
})
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
            	<%--<li><a href="${basePath }/agdpxyz/index">培训驿站</a></li>--%>
            	<li><a href="${basePath }/agdpxyz/notice">培训公告</a></li>
                <li><a href="${basePath }/agdpxyz/news">培训资讯</a></li>
                <li><a href="${basePath }/agdpxyz/teacher">培训师资</a></li>
                <li><a href="${basePath }/agdpxyz/trainlist">在线报名</a></li>
                <li class="active last"><a href="${basePath }/agdpxyz/vod">在线点播</a></li>
                <%-- <li class="last"><a href="${basePath }/agdpxyz/resources">培训资源库</a></li> --%>
            </ul>
        </div>
    </div>
</div>
<!--公共主头部开始-END-->

<!--面包屑开始-->
<div class="public-crumbs">
    <span><a href="${basePath }/dgszwhg/home">首页</a></span><span>></span><span><a href="${basePath }/agdpxyz/index">培训驿站</a></span><span>></span><span><a href="${basePath }/agdpxyz/vod">在线点播</a></span><span>></span><span>公益培训 ${whDrsc.drsctitle}</span>
</div>
<!--面包屑结束-->
<!--主体开始-->
<div class="main-info-bg main-info-no-padding">
    <div class="main-info-container clearfix">
        <div class="public-left-main">
        	<div class="public-info-step">
	            <div class="video-detail">
	                <h1>${whDrsc.drsctitle }</h1>
	                <div class="detail clearfix">
	                    <div class="time">时长：<span>${whDrsc.drsctime }</span></div>
	                    <div class="source">来源：<span>${whDrsc.drscfrom}</span></div>
	                </div>
	                <div class="video1">
	                	<div id="clientcaps"></div>
	                    <video width="800" height="525" poster="${imgServerAddr}${whDrsc.drscpic}" controls  oncontextmenu="return false">
	                      <%-- <source type="video/mp4" src="${basePath }/${whDrsc.drscpath}" /> --%>
	                      
	                      <!-- <source type="video/mp4" src="http://szwhg-gds.oss-cn-shenzhen.aliyuncs.com/%5B%E9%AC%BCC%E7%81%AF%E4%B9%8BJJ%E5%8F%A4%E5%9F%8E%5D%E7%AC%AC10%E9%9B%86_bd.mp4" />
	                     -->
	                     <%-- <source type="video/mp4" src="${whDrsc.drscpath}" alt="szwhg" /> --%>
	                     <c:set var="video_url" value="${whDrsc.drscpath}"></c:set>
	                     <c:if test="${fn:startsWith(whDrsc.drscpath, 'upload')}">
	                     	<c:set var="video_url" value="${basePath }/${whDrsc.drscpath}"></c:set>
	                     </c:if>
	                     <source type="video/mp4" src="${video_url}" alt="szwhg" />
	                     
	                    </video>
	                </div>
	            </div>
	            
	            <h3><span>视频简介</span></h3>
	            <div class="info">
	            	${whDrsc.drscintro}
	            </div>
            </div>
            
            <!-- 分享到微信和空间 -->
	        <div class="public-share">
	            <span class="btn qq"><a href="javascript:void(0);" class="fxqq"></a></span>
                <span class="btn weixin"><a href="javascript:void(0)" class="fxweix"></a></span>
				<span class="btn weibo"><a href="javascript:void(0)" class="fxweibo" target="_blank"></a></span>
                <span class="btn dianzan">
                	<em>0</em>
                	<a href="javascript:void(0)" class="dianzan" reftyp="2016101400000051" refid="${whDrsc.drscid}" id="good">
                    </a>
                </span>
	       	</div> 
            
	        <!-- 动态包含评论 -->
	        <jsp:include page="/pages/comm/agdcomment.jsp" flush="true">     
			     <jsp:param name="reftype" value="4"/>
			     <jsp:param name="refid" value="${whDrsc.drscid }"/> 
			</jsp:include>
			<!-- 动态包含评论-END -->
		
        </div>
        
        <div class="public-right-main">
	    	<div class="public-other-notice">
	        	<h2>推荐视频</h2>
	        	<%-- <c:if test="${not empty kecheng }">
		        	<c:forEach items="${kecheng }" var="item" end="2">
			        	<div class="item clearfix">
			                <div class="right-img">
			                    <a href="${basePath }/agdpxyz/vodinfo?drscid=${item.drscid}"><img src="${basePath }/${item.drscpic}" width="130" height="90"></a>
			                </div>
			                <div class="right-detail">
			                    <a href="${basePath }/agdpxyz/vodinfo?drscid=${item.drscid}"><h3>${item.drsctitle}</h3></a>
			                    <p class="time"><fmt:formatDate value="${item.drsccrttime}" pattern="yyyy-MM-dd "/></p>
			                </div>
		            	</div>
		        	</c:forEach>
	        	</c:if>
	        	// --%>
	        	<c:choose>
				   <c:when test="${not empty kecheng }">  
					   <c:forEach items="${kecheng }" var="item" end="2">
			        		<div class="item clearfix">
				                <div class="right-img">
				                    <a href="${basePath }/agdpxyz/vodinfo?drscid=${item.drscid}"><img src="${imgServerAddr}${item.drscpic}" width="130" height="90"></a>
				                </div>
				                <div class="right-detail">
				                    <a href="${basePath }/agdpxyz/vodinfo?drscid=${item.drscid}"><h3>${item.drsctitle}</h3></a>
				                    <p class="time"><fmt:formatDate value="${item.drsccrttime}" pattern="yyyy-MM-dd "/></p>
				                </div>
		            		</div>
		        		</c:forEach>      
				   </c:when>
				   <c:otherwise> 
				   		<!-- <div class="public-no-message "></div> -->
				   </c:otherwise>
				</c:choose>
	        	
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
</body>
</html>