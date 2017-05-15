<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<%@include file="/pages/comm/agdhead.jsp"%>
<title>${teacher.teachername }</title>
<link href="${basePath }/static/assets/css/train/teacher.css" rel="stylesheet">
</head>
<body>
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
                <li class="active"><a href="${basePath }/agdpxyz/teacher">培训师资</a></li>
                <li><a href="${basePath }/agdpxyz/trainlist">在线报名</a></li>
                <li class="last"><a href="${basePath }/agdpxyz/vod">在线点播</a></li>
                <%-- <li class="last"><a href="${basePath }/agdpxyz/resources">培训资源库</a></li> --%>
            </ul>
        </div>
    </div>
</div>
<!--公共主头部开始-END-->
 
<!--面包屑开始-->
<div class="public-crumbs">
    <span><a href="${basePath }/home">首页</a></span><span>></span>
    <span><a href="${basePath }/agdpxyz/index">培训驿站</a></span><span>></span>
    <span><a href="${basePath }/agdpxyz/teacher">培训师资</a></span><span>></span>
    <span>${teacher.teachername }</span>
</div>
<!--面包屑结束-->

<!--主体开始-->
<div class="main-info-bg main-info-no-padding main-info-bgColorW">
	<div class="main-info-container clearfix">
  		<div class="teacherCont clearfix">
	    	<div class="img">
	    		<c:choose>
	    			<c:when test="${teacher.teacherpic != null }">
			        	<img src="${imgServerAddr}${teacher.teacherpic}" width="160" height="160">
	    			</c:when><c:otherwise>
			        	<!-- <img src="${basePath }/static/assets/img/img_demo/trainTeacher1.jpg" width="160" height="160"> -->
	    			</c:otherwise>
	    		</c:choose>
	        </div>
	        <div class="info">
	        	<h1>${teacher.teachername }</h1>
				<c:if test="${not empty teacher.teachertype }">
	            	<p><i class="public-s-ico s-ico-5"></i>专长 : <span>${teacher.teachertype }</span></p>
				</c:if>
				<c:if test="${not empty teacher.teachercourse }">
					<p><i class="public-s-ico s-ico-6"></i>课程说明 : <span>${teacher.teachercourse }</span></p>
				</c:if>
	        </div>

	        <%-- <div class="fabulous">
				<!-- <div class="fabulous-btn"><span>500</span><i><a href="javascript:void(0);"></a></i></div> -->
	        	<!-- 动态包含点赞 -->
		        <jsp:include page="/pages/comm/agdfabulous.jsp" flush="true">     
				     <jsp:param name="reftype" value="2016112300000001"/> 
				     <jsp:param name="refid" value="${teacher.teacherid }"/> 
				</jsp:include>
				<!-- 动态包含点赞-END -->
	        </div> --%>
    	</div>
    	
    	<div class="public-left-main">
	    	<div class="public-info-step">
				<c:if test="${not empty teacher.teacherintroduce}">
					<h3><span>讲师简介</span></h3>
					<div class="info">${teacher.teacherintroduce} &nbsp;</div>
				</c:if>
				<c:if test="${not empty teacher.teacherexpdesc}">
					<h3><span>专长介绍</span></h3>
					<div class="info">${teacher.teacherexpdesc} &nbsp;</div>
				</c:if>
	            <%-- <h3><span>开班介绍</span></h3>
	            <div class="info">${teacher.teacherstartdesc} &nbsp;</div> --%>
	        </div>
	        <!-- 分享到微信和空间 -->
	        <div class="public-share">
	            <span class="btn qq"><a href="javascript:void(0);" class="fxqq"></a></span>
	            <span class="btn weixin"><a href="javascript:void(0)" class="fxweix"></a></span>
				<span class="btn weibo"><a href="javascript:void(0)" class="fxweibo" target="_blank"></a></span>
	            <span class="btn dianzan">
	              	<em>0</em>
	              	<a href="javascript:void(0)" class="dianzan" reftyp="2016101400000051" refid="${teacher.teacherid }" id="good"></a>
	            </span>
	       	</div> 
	        <!-- 动态包含评论 -->
	        <jsp:include page="/pages/comm/agdcomment.jsp" flush="true">     
			     <jsp:param name="reftype" value="3"/>
			     <jsp:param name="refid" value="${teacher.teacherid }"/> 
			</jsp:include>
			<!-- 动态包含评论-END -->
			
		</div>
		<div class="public-right-main">
			<c:if test="${not empty trainList}">
				<div class="public-other-notice">
					<h2>推荐课程</h2>
					<c:choose>
					   <c:when test="${trainList != null && fn:length(trainList) > 0 }">
						   <c:forEach items="${trainList }" var="row" varStatus="s">
								<div class="item clearfix">
									<div class="right-img">
										<a href="${basePath }/agdpxyz/traininfo?traid=${row.id }"><img src="${imgServerAddr}${row.trainimg}" width="130" height="90"></a>
									</div>
									<div class="right-detail">
										<a href="${basePath }/agdpxyz/traininfo?traid=${row.id }"><h3>${row.title}</h3></a>
										<p class="time"><fmt:formatDate value="${row.starttime}" pattern="yyyy-MM-dd" /></p>
									</div>
								</div>
						   </c:forEach>
					   </c:when>
					   <c:otherwise>
							<div class="public-no-message "></div>
					   </c:otherwise>
					</c:choose>
				</div>
			</c:if>

			<!--相关课程-->
			<c:if test="${not empty train}">
				<div class="public-other-notice" style="margin-top: 20px">
					<h2>相关课程</h2>
					<c:forEach items="${train }" var="row" varStatus="s">
						<div class="item clearfix">
							<div class="right-img">
								<a href="${basePath }/agdpxyz/traininfo?traid=${row.id }"><img src="${imgServerAddr}${row.trainimg}" width="130" height="90"></a>
							</div>
							<div class="right-detail">
								<a href="${basePath }/agdpxyz/traininfo?traid=${row.id }"><h3>${row.title}</h3></a>
								<p class="time"><fmt:formatDate value="${row.starttime}" pattern="yyyy-MM-dd" /></p>
							</div>
						</div>
					</c:forEach>
				</div>
			</c:if>
			<!--相关课程  END-->
		</div>
	</div>
</div>
<!--主体结束-->

<!--公共主底部开始-->
<%@include file="/pages/comm/agdfooter.jsp"%>
<!--公共主底部结束-END-->
</body>
</html>