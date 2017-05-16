<%@ page import="com.creatoo.hn.model.WhgYwiWhpp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<%@include file="/pages/comm/agdhead.jsp"%>
	<title>广东省文化馆-文化品牌</title>
	<link href="${basePath }/static/assets/css/activity/specialDetail.css" rel="stylesheet">
	<script src="${basePath }/static/assets/js/activity/specialDetail.js"></script>
</head>
<body class="oiplayer-example">

<!--公共主头部开始-->
<div id="header">
	<div class="main clearfix">
		<div class="gatalogMain">
			<div class="return">
				<a href="${basePath}/agdwhhd/brandlist" class="btn">
					<i class="kx-arrow kx-arrow-right">
						<em></em>
						<span></span>
					</i>
					返回文化品牌列表
				</a>
			</div>
		</div>
	</div>
</div>
<!--公共主头部结束-->

<!--主体开始-->
<c:choose>
	<c:when test="${brand.norepeat !=null && brand.norepeat == 0}">
		<c:set var="whgpp_norepeat" value="no-repeat" />
	</c:when>
	<c:otherwise>
		<c:set var="whgpp_norepeat" value="repeat" />
	</c:otherwise>
</c:choose>
<c:choose>
	<c:when test="${brand.bgpicture !=null && brand.bgcolour != null}">
		<div class="special-bg" style="background:url(${imgServerAddr}${brand.bgpicture}) center top ${whgpp_norepeat} ${brand.bgcolour};">
	</c:when>
	<c:when test="${brand.bgpicture !=null}">
		<div class="special-bg" style="background:url(${imgServerAddr}${brand.bgpicture}) center top ${whgpp_norepeat};">
	</c:when>
	<c:otherwise>
		<div class="special-bg" style="background:url(${basePath }/static/assets/img/activity/special-bg.jpg) center top ${whgpp_norepeat};">
	</c:otherwise>
</c:choose>
	<div class="special-main">
		<div class="banner">
			<h1>${brand.shortname}</h1>
			<div>${brand.introduction}</div>
		</div>
		<div class="groups">
			<ul class="clearfix">
				<c:forEach items="${actList}" var="row" varStatus="s">
				<li>
					<a href="${basePath}/agdwhhd/activityinfo?actvid=${row.id}">
						<img width="255" height="148" src="${imgServerAddr}${row.imgurl}">
						<h2>${row.name}</h2>
						<p>时间：<span><fmt:formatDate value="${row.starttime}" pattern="yyyy-MM-dd"/>至<fmt:formatDate value="${row.endtime}" pattern="yyyy-MM-dd"/></span></p>
						<p>地址：<span>${row.address}</span></p>
					</a>
				</li>
				</c:forEach>
			</ul>
		</div>

		<!--品牌培训-->
		<div class="groups">
			<ul class="clearfix">
				<c:forEach items="${traList}" var="row" varStatus="s">
					<li>
						<a href="${basePath}/agdpxyz/traininfo?traid=${row.id}">
							<img width="255" height="148" src="${imgServerAddr}${row.trainimg}">
							<h2>${row.title}</h2>
							<p>时间：<span><fmt:formatDate value="${row.starttime}" pattern="yyyy-MM-dd"/>至<fmt:formatDate value="${row.endtime}" pattern="yyyy-MM-dd"/></span></p>
							<p>地址：<span>${row.address}</span></p>
						</a>
					</li>
				</c:forEach>
			</ul>
		</div> <!--品牌培训 END-->



		<!-- 图片视频资源 -->
		<c:if test="${ !((imgList == null || fn:length(imgList) < 1) && (vedioList ==null || fn:length(vedioList) < 1) && (audioList ==null || fn:length(audioList) < 1)) }">
		<div class="site special_file clearfix">
			<ul class="tab clearfix">
				<c:if test="${imgList != null && fn:length(imgList) > 0}">    <li class="active">活动图片</li> </c:if>
				<c:if test="${vedioList != null && fn:length(vedioList) > 0}"><li class="">活动视频</li></c:if>
				<c:if test="${audioList != null && fn:length(audioList) > 0}"><li class="">活动音频</li></c:if>
			</ul>
			<c:if test="${imgList != null && fn:length(imgList) > 0}">
			<div class="list1">
				<div class="demo-list list-video clearfix">
					<c:choose>
						<c:when test="${imgList !=null && fn:length(imgList) > 0}">
							<c:forEach items="${imgList}" var="row" varStatus="s">
								<a href="javascript:void(0)" onclick="show_img(this,{url:'${imgServerAddr }${row.enturl }'})">
									<div class="img1">
										<img src="${imgServerAddr }${whg:getImg300_200(row.enturl) }" width="252" height="170">
										<span>${row.entname}</span>
									</div>
								</a>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<div class="special_msg">暂无更新活动图片</div>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
			</c:if>

			<c:if test="${vedioList != null && fn:length(vedioList) > 0}">
			<div class="list1">
				<div class="demo-list list-video clearfix">
					<c:choose>
						<c:when test="${vedioList !=null && fn:length(vedioList) > 0}">
							<c:forEach items="${vedioList}" var="row" varStatus="s">
								<a href="javascript:void(0)" onclick="show_vedio(this,{url:'${row.enturl}'})">
									<div class="mask"></div>
									<div class="video1">
										<img src="${imgServerAddr }${whg:getImg300_200(row.deourl) }" width="252" height="150">
										<span>${row.entname}</span>
									</div>
								</a>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<div class="special_msg">暂无更新活动视频</div>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
			</c:if>

			<c:if test="${audioList != null && fn:length(audioList) > 0}">
			<div class="list1">
				<div class="demo-list list-mp3 clearfix">
					<c:choose>
						<c:when test="${audioList !=null && fn:length(audioList) > 0}">
							<c:forEach items="${audioList}" var="row" varStatus="s">
								<a href="javascript:void(0)" onclick="show_vedio(this,{url:'${row.enturl }'})">
									<div class="mask"></div>
									<div class="mp31">
										<span>${row.entname}</span>
									</div>
								</a>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<div class="special_msg">暂无更新活动音频</div>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
			</c:if>
		</div>
		</c:if>
		<!-- 图片视频资源-END -->

		<div class="backrun"><a href="${basePath}/agdwhhd/brandlist">返回文化品牌列表</a></div>
	</div>
</div>
<!--主体结束-->

<!--公共主底部开始-->
<%@include file="/pages/comm/agdfooter.jsp"%>
<!--公共主底部结束-END-->

<script type="text/javascript">
$('div.list1:eq(0)').addClass("on");
</script>
</body>
</html>