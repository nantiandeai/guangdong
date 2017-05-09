<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<%@include file="/pages/comm/agdhead.jsp"%>
	<title>广东省文化馆</title>
	<link href="${basePath }/static/assets/css/index/index.css" rel="stylesheet">
	<script src="${basePath }/static/assets/js/index/index.js" type="text/javascript"></script>
	<script type="text/javascript">
        //是否登录标记 var loginState = 'isLogin';
        var loginState = '${sessionScope.sessionUser != null ? 'isLogin' : 'noLogin'}';
	</script>
</head>
<body>
<!--公共主头部开始-->
<%@include file="/pages/comm/agdtop.jsp"%>
<!--公共主头部结束-->

<!--轮播广告开始-->
<div class="banner-position">
	<div class="big-banner">
		<div class="banner-cont">
			<ul class="forceCentered">
				<c:choose>
					<c:when test="${lbtList != null && fn:length(lbtList) > 0}">
						<c:forEach items="${lbtList}" var="row" varStatus="s">
							<li style="background:url(${imgServerAddr }${row.picture})"><a href="${row.url != null && fn:startsWith(row.url, 'http') ? row.url : 'javascript:void(0)'}" title="${row.name}"></a></li>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<li style="background:url(${basePath }/static/assets/img/img_demo/bigBanner1.jpg)"><a href="javascript:void(0)" title="AAAAAAAAA"></a></li>
						<li style="background:url(${basePath }/static/assets/img/img_demo/bigBanner1.jpg)"><a href="javascript:void(0)" title="BBBBBBBB"></a></li>
						<li style="background:url(${basePath }/static/assets/img/img_demo/bigBanner1.jpg)"><a href="javascript:void(0)" title="CCCCCCCC"></a></li>
						<li style="background:url(${basePath }/static/assets/img/img_demo/bigBanner1.jpg)"><a href="javascript:void(0)" title="DDDDDDDDDD"></a></li>
					</c:otherwise>
				</c:choose>
			</ul>
			<div class="big-b big-b-prev"></div>
			<div class="big-b big-b-next"></div>
		</div>
	</div>
	<div class="page">
		<ul class="pages"></ul>
	</div>
</div>
<!--轮播广告结束-->

<!--资讯动态开始-->
<div class="public-index-bg margin-bo0px">
	<div class="public-index-cont">
		<h2>资讯动态<a href="${basePath}/agdzxdt/index">+更多</a><em></em></h2>
	</div>
	<div class="information">
		<div class="line line1"></div>
		<div class="line line2"></div>
		<div class="public-index-cont clearfix">
			<div class="left-wrapper">
				<div class="newsGroups">
					<div class="tab">
						<ul>
							<li class="active">公告</li>
							<li>工作动态</li>
							<li>基层直击</li>
							<li>热点聚焦</li>
						</ul>
					</div>
					<div class="detail on">
						<ul>
							<c:forEach items="${ggList}" var="row" varStatus="s">
							<li><i><fmt:formatDate value="${row.clnfcrttime}" pattern="yyyy-MM-dd"/></i><a href="${basePath}/agdzxdt/noticeinfo?id=${row.clnfid}">${row.clnftltle}</a></li>
							</c:forEach>
						</ul>
						<em><a href="${basePath}/agdzxdt/index">+更多</a></em>
					</div>
					<div class="detail">
						<ul>
							<c:forEach items="${dtList}" var="row" varStatus="s">
								<li><i><fmt:formatDate value="${row.clnfcrttime}" pattern="yyyy-MM-dd"/></i><a href="${basePath}/agdzxdt/workinginfo?id=${row.clnfid}">${row.clnftltle}</a></li>
							</c:forEach>
						</ul>
						<em><a href="${basePath}/agdzxdt/working">+更多</a></em>
					</div>
					<div class="detail">
						<ul>
							<c:forEach items="${jcList}" var="row" varStatus="s">
								<li><i><fmt:formatDate value="${row.clnfcrttime}" pattern="yyyy-MM-dd"/></i><a href="${basePath}/agdzxdt/unitsinfo?id=${row.clnfid}">${row.clnftltle}</a></li>
							</c:forEach>
						</ul>
						<em><a href="${basePath}/agdzxdt/units">+更多</a></em>
					</div>
					<div class="detail">
						<ul>
							<c:forEach items="${rdList}" var="row" varStatus="s">
								<li><i><fmt:formatDate value="${row.clnfcrttime}" pattern="yyyy-MM-dd"/></i><a href="${basePath}/agdzxdt/hotinfo?id=${row.clnfid}">${row.clnftltle}</a></li>
							</c:forEach>
						</ul>
						<em><a href="${basePath}/agdzxdt/hot">+更多</a></em>
					</div>

				</div>
			</div>
			<div class="right-wrapper">
				<div class="information-banner">
					<ul>
						<c:forEach items="${advList}" var="row" varStatus="s">
							<li>
								<img src="${imgServerAddr }${whg:getImg750_500(row.picture)}" width="600" height="320">
								<div class="msg">
									<div class="title">${row.name}</div>
									<span><fmt:formatDate value="${row.statemdfdate}" pattern="MM.dd"/></span>
								</div>
								<a href="${not empty row.url ? row.url : ''}"></a>
							</li>
						</c:forEach>
					</ul>
					<div class="info-arrow left-arrow"></div>
					<div class="info-arrow right-arrow"></div>
				</div>
			</div>
		</div>
	</div>
</div>
<!--资讯动态结束-->

<!--文化活动开始-->
<div class="public-index-bg">
	<div class="public-index-cont">
		<h2>文化活动<a href="${basePath}/agdwhhd/activitylist">+更多</a><em></em></h2>
	</div>
	<div class="public-index-cont">
		<div class="activity-list">
			<ul class="clearfix">
				<c:forEach items="${actList}" var="row" varStatus="s">
					<li>
						<div class="img">
							<img src="${imgServerAddr }${whg:getImg300_200(row.imgurl)}" width="297" height="225">
						</div>
						<div class="detail">
							<h4>${row.name}</h4>
							<p>时间：<span><fmt:formatDate value="${row.starttime}" pattern="yyyy-MM-dd"/></span></p>
							<p>地点：<span>${row.address}</span></p>
						</div>
						<a href="${basePath}/agdwhhd/activityinfo?actvid=${row.id}" class="goDetail"></a>
					</li>
				</c:forEach>
			</ul>
		</div>
	</div>
</div>
<!--文化活动结束-->

<!--培训驿站开始-->
<div class="public-index-bg public-index-color">
	<div class="public-index-cont clearfix">
		<h2>培训驿站<a href="${basePath}/agdpxyz/trainlist">+更多</a><em></em></h2>
		<div class="train-cont">
			<div class="train-cont-main">
				<ul class="forceCentered">
					<c:forEach items="${traList}" var="row" varStatus="s">
					<li>
						<div class="info">
							<h3>${row.title}<em></em></h3>
							<p class="clearfix"><span class="tt">开课时间：</span><span class="desc"><fmt:formatDate value="${traMap[row.id].starttime}" pattern="yyyy年MM月dd日"/> </span></p>
							<p class="clearfix"><span class="tt">培训地点：</span><span class="desc">${row.address}</span></p>
							<p class="clearfix"><span class="tt">联系方式：</span><span class="desc">${row.phone} </span></p>
							<p class="clearfix"><span class="tt">课程描述：</span><span class="desc">${row.coursedesc} </span></p>
							<a href="${basePath}/agdpxyz/traininfo?traid=${row.id}">了解详情<img src="${basePath }/static/assets/img/index/ar.gif"> </a>
						</div>
						<div class="img">
							<a href="${basePath}/agdpxyz/traininfo?traid=${row.id}">
								<img src="${imgServerAddr }${whg:getImg750_500(row.trainimg)}" style="width: 550px; height: 400px;">
							</a>
						</div>
					</li>
					</c:forEach>
				</ul>
			</div>
			<ul class="t-nav">

			</ul>
			<script>
                $('.train-cont-main').sly({
                    itemNav: "basic",
                    easing: "easeOutExpo",
                    pagesBar: ".t-nav",
                    pageBuilder: function (dom) {
                        var _text = $('.train-cont-main .forceCentered > li:eq('+dom+') .info h3').text();
                        var _text2 = _text.length > 10 ? _text.substring(0, 10) + '.' : _text;

                        return "<li class='active' title='"+_text+"'>"+_text2+"<i></i>"
                    },
                    horizontal: 1,
                    touchDragging: 1
                });
			</script>
		</div>
	</div>
</div>
<!--培训驿站结束-->
<!--非遗中心开始-->
<div class="public-index-bg">
	<div class="public-index-cont">
		<h2>非遗中心<a href="${basePath}/agdfyzg/index">+更多</a><em></em></h2>
		<div class="ibhi-cont">
			<ul>
				<li>
					<a href="${basePath}/agdfyzg/minglu?mlprotype=2017041200000001"></a>
					<i class="icon ico-1"></i>
					<p>民间文学</p>
				</li>
				<li>
					<a href="${basePath}/agdfyzg/minglu?mlprotype=2017041200000002"></a>
					<i class="icon ico-2"></i>
					<p>传统音乐</p>
				</li>
				<li>
					<a href="${basePath}/agdfyzg/minglu?mlprotype=2017041200000003"></a>
					<i class="icon ico-3"></i>
					<p>传统舞蹈</p>
				</li>
				<li>
					<a href="${basePath}/agdfyzg/minglu?mlprotype=2017041200000004"></a>
					<i class="icon ico-4"></i>
					<p>传统戏曲</p>
				</li>
				<li>
					<a href="${basePath}/agdfyzg/minglu?mlprotype=2017041200000005"></a>
					<i class="icon ico-5"></i>
					<p>曲艺</p>
				</li>
			</ul>
			<ul class="last">
				<li>
					<a href="${basePath}/agdfyzg/minglu?mlprotype=2017041200000006"></a>
					<i class="icon ico-6"></i>
					<p>传统体育、游艺与杂技</p>
				</li>
				<li>
					<a href="${basePath}/agdfyzg/minglu?mlprotype=2017041200000007"></a>
					<i class="icon ico-7"></i>
					<p>传统美术</p>
				</li>
				<li>
					<a href="${basePath}/agdfyzg/minglu?mlprotype=2017041200000008"></a>
					<i class="icon ico-8"></i>
					<p>传统技艺</p>
				</li>
				<li>
					<a href="${basePath}/agdfyzg/minglu?mlprotype=2017041200000009"></a>
					<i class="icon ico-9"></i>
					<p>传统医药</p>
				</li>
				<li>
					<a href="${basePath}/agdfyzg/minglu?mlprotype=2017041200000010"></a>
					<i class="icon ico-10"></i>
					<p>民俗</p>
				</li>
			</ul>
		</div>
	</div>
</div>
<!--非遗中心结束-->

<!--文化馆联盟开始-->
<div class="public-index-bg public-index-color">
	<div class="public-index-cont">
		<h2>文化馆联盟<a href="${basePath}/agdwhlm/index">+更多</a><em></em></h2>
		<div class="union-cont">
			<div class="forceCentered clearfix" style="width:10000px;">

				<c:if test="${cultList != null && fn:length(cultList) > 0}">
					<c:forEach items="${cultList}" var="row" varStatus="s">
						<c:choose>
							<c:when test="${not empty row.siteurl}"><c:set var="whgurl" value="${row.siteurl}"/></c:when>
							<c:otherwise><c:set var="whgurl" value="${basePath }/agdwhlm/info/${row.id}"/></c:otherwise>
						</c:choose>
						<c:if test="${s.first}"><ul></c:if>
						<c:if test="${s.index%3 == 0 && !s.first }"></ul><ul></c:if>
						<li>
							<div class="info">
								<h3><a href="${whgurl}">${row.name}</a></h3>
								<p>${row.address}</p>
								<a href="${whgurl}" class="show">了解详情</a>
							</div>
							<div class="img">
								<a href="${whgurl}"><img src="${imgServerAddr }${whg:getImg750_500(row.picture)}"></a>
							</div>
						</li>
						<c:if test="${s.last}"></ul></c:if>
					</c:forEach>
				</c:if>

			</div>
			<div class="u-nav"></div>
		</div>
	</div>
</div>
<!--文化馆联盟结束-->

<!--公共主底部开始-->
<%@include file="/pages/comm/agdfooter.jsp"%>
<!--公共主底部结束-END-->
</body>
</html>