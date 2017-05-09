<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<%@include file="/pages/comm/agdhead.jsp"%>
	<title>${train.title}</title>
	<link href="${basePath}/static/assets/css/train/registrationInfo.css" rel="stylesheet">
	<script src="${basePath}/static/assets/js/train/registrationInfo.js"></script>

	<script src="${basePath }/static/common/js/whg.maps.js"></script>

	<!--[if lt IE 9] >
    <script src="../../assets/js/plugins/ie/IE9.js"></script>
    <! [endif]]-->
	<script type="text/javascript">
        var basePath = "${basePath}";
		function loadArtContent(page, rows){
			var page = page || 1;//分页加载第一页
			var rows = rows || 10;//每页10条记录
			var _url = '${basePath}/agdpxyz/course?traid=${train.id}';
			$.ajax({
				type: "POST",
				url: _url+'&page='+page+'&rows='+rows,
				success: function(data){

					var dataRows = data.rows;
					var total = data.total || 0;
					//加载页面数据
					if(total > 0){
						showList(dataRows);
					}else{
						showList([]);
					}
					//加载分页工具栏
					genPagging('whgPagging', page, rows, total, loadArtContent);
				}
			});
		}

		/** 加载数据 */
		function showList(dataRows){
			var $table = $(".table table");
			$table.empty();
			for(var i=0; i<dataRows.length; i++){
				var list = '';
				list += '<tr>';
				list += '	<td class="not-conform">'+(i+1)+'</td>';
				list += '	<td class="not-conform">'+datetimeFMT(dataRows[i].starttime)+'</td>';
				list += '	<td class="not-conform">'+datetimeFMT(dataRows[i].endtime)+'</td>';
				list += '</tr>';
				$table.append(list);
			}

		}
		$(function () {

			loadArtContent();
		})

	</script>
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
				<%--<li><a href="${basePath }/agdpxyz/index">培训驿站</a></li>--%>
				<li><a href="${basePath }/agdpxyz/notice">培训公告</a></li>
				<li><a href="${basePath }/agdpxyz/news">培训资讯</a></li>
				<li><a href="${basePath }/agdpxyz/teacher">培训师资</a></li>
				<li class="active"><a href="${basePath }/agdpxyz/trainlist">在线报名</a></li>
				<li class="last"><a href="${basePath }/agdpxyz/vod">在线点播</a></li>
				<%-- <li class="last"><a href="${basePath }/agdpxyz/resources">培训资源库</a></li> --%>
			</ul>
		</div>
	</div>
</div>

<!--面包屑开始-->
<div class="public-crumbs">
	<span><a href="${basePath }/home">首页</a></span><span>></span><span><a href="${basePath }/agdpxyz/index">培训驿站</a></span><span>></span><span><a href="${basePath }/agdpxyz/trainlist">在线报名</a></span><span>></span><span>${train.title}</span>
</div>
<!--面包屑结束-->

<!--主体开始-->
<div class="main-info-bg main-info-no-padding main-info-bgColorW">
	<div class="main-info-container clearfix">
		<div class="registrationInfo">
			<div class="img">
				<c:choose>
					<c:when test="${not empty train.trainimg}">
						<img src="${imgServerAddr}${whg:getImg750_500(train.trainimg)}" width="480" height="315">
					</c:when>
					<c:otherwise>
						<img src="${basePath }/static/assets/img/img_demo/trainTeacherCase2.jpg" width="480" height="315">
					</c:otherwise>
				</c:choose>
			</div>
			<div class="info">
				<div class="public-fav"><a id="collection" class="shoucang" reftype="${empty enumtypetrain?"5":enumtypetrain}" refid="${train.id}" href="javascript:void(0)"></a></div>
				<h1>${train.title}</h1>
				<c:if test="${not empty teacher}">
					<p class="name clearfix"><i class="public-s-ico s-ico-15"></i><span class="desc">讲师 :</span><span class="tt"><c:forEach items="${teacher}" var="item"><span>${item} </span>&nbsp;</c:forEach></span></p>
				</c:if>
				<p class="seat clearfix"><i class="public-s-ico s-ico-16"></i><span class="desc">人数 :</span><span class="tt"><span class="num">${(not empty count) ? count : 0 }</span>${train.isshowmaxnumber == 0 ?"":"/"}${train.isshowmaxnumber == 0 ? "" :train.maxnumber}</span>
				</p>
				<c:if test="${not empty train.phone and train.phone != ''}">
					<p class="tel clearfix"><i class="public-s-ico s-ico-17"></i><span class="desc">电话 :</span><span class="tt">${train.phone}</span></p>
				</c:if>
				<p class="adr clearfix"><i class="public-s-ico s-ico-10"></i><span class="desc">地址 :</span><span class="tt">${train.address}</span></p>
				<p class="adr clearfix"><i class="public-s-ico s-ico-30"></i><span class="desc">报名周期 :</span><span class="tt"><span><fmt:formatDate value="${train.enrollstarttime}" pattern="yyyy-MM-dd HH:mm:ss"/></span>&nbsp;&nbsp;&nbsp;至&nbsp;&nbsp;&nbsp;<span><fmt:formatDate value="${train.enrollendtime}" pattern="yyyy-MM-dd HH:mm:ss"/></span></span></p>
				<p class="adr clearfix"><i class="public-s-ico s-ico-31"></i><span class="desc">培训周期 :</span><span class="tt"><fmt:formatDate value="${train.starttime}" pattern="yyyy-MM-dd"/>&nbsp;&nbsp;&nbsp;至&nbsp;&nbsp;&nbsp;<fmt:formatDate value="${train.endtime}" pattern="yyyy-MM-dd"/></span></p>
				<c:if test="${not empty age1 and not empty age2}">
				<p class="adr clearfix"><i class="public-s-ico s-ico-1"></i><span class="desc">年龄段 :</span><span class="tt"><span>${age1}</span>&nbsp;&nbsp;&nbsp;至&nbsp;&nbsp;&nbsp;<span>${age2}</span>&nbsp;&nbsp;岁</span></p>
				</c:if>
				<div class="goNext">
					<!--<a href="javascript:void(0)" class="open">直接前往</a>-->

					<div class="goNext">
						<c:choose>
							<c:when test="${(train.enrollstarttime gt now)}">
								<a href="javascript:void(0)" class="open">报名未开始</a>
							</c:when>
							<c:when test="${(train.enrollendtime lt now)}">
								<a href="javascript:void(0)" class="open">报名已结束</a>
							</c:when>
							<c:when test="${(count ge train.maxnumber) }">
								<a href="javascript:void(0)" class="open">报名名额已满</a>
							</c:when>
							<c:otherwise>
								<a id="showbm" href="javascript:void(0)" trainId="${train.id}">立即报名</a>
								<a id="hiddenbm" style="display: none" class="open" href="javascript:void(0)">立即报名</a>
							</c:otherwise>
						</c:choose>
					</div>

				</div>
			</div>
		</div>
		<div class="public-left-main">
			<div class="public-info-step">
				<h3><span>查看课程表</span></h3>
				<div class="table">
					<table>
						<tbody>
							<tr>
								<th>序列</th>
								<th>开课时间</th>
								<th>结束时间</th>
							</tr>
						</tbody>
					</table>
					<!-- 分页栏 -->
					<div class="green-black" id="whgPagging"></div>
					<!-- 分页栏-END -->
				</div>

				<!--这里的表格有分页，10条一页-->
			</div>
			<div class="public-info-step">
				<c:if test="${not empty train.coursedesc}">
					<h3><span>课程简介</span></h3>
					<div class="info">
						${train.coursedesc}
					</div>
				</c:if>
				<c:if test="${not empty train.outline}">
					<h3><span>课程内容</span></h3>
					<div class="info">
						${train.outline}
					</div>
				</c:if>
				<c:if test="${not empty train.teacherdesc}">
					<h3><span>老师介绍</span></h3>
					<div class="info">
							${train.teacherdesc}
					</div>
				</c:if>
			</div>

			<div class="site clearfix">
				<ul class="tab clearfix">
					<c:if test="${not empty pic }">
						<li class="${not empty pic ? 'active' : '' }">培训图片</li>
					</c:if>
					<c:if test="${not empty video }">
						<li class="${not empty pic ? '' : 'active' }">培训视频</li>
					</c:if>
					<c:if test="${not empty audio }">
						<li class="${not empty pic || not empty video ? '' : 'active' }">培训音频</li>
					</c:if>
				</ul>
				<!-- 图片 -->
				<c:if test="${not empty pic }">
					<div class="list1 on">
						<div class="demo-list list-video clearfix">
							<c:forEach items="${pic }" var="item" varStatus="s">
								<a ${s.count%3 == 0?'class="last"':'' } href="javascript:void(0)" onClick="show_img(this,{url:'${imgServerAddr}${item.enturl}'})">
									<div class="img1">
										<img src="${imgServerAddr}${whg:getImg300_200(item.enturl)}" width="252" height="170">
										<span>${item.entname }</span>
									</div>
								</a>
							</c:forEach>
						</div>
					</div>
				</c:if>
				<!-- 图片  END -->

				<!-- 视频 -->
				<c:if test="${not empty video }">
					<div class="list1 ${not empty pic ? '':'on'}">
						<div class="demo-list list-video clearfix">
							<c:forEach items="${video }" var="item" varStatus="s">
								<a ${s.count%3 == 0?'class="last"':'' } href="javascript:void(0)" onClick="show_vedio(this,{url:'${item.enturl}'})">
									<div class="mask"></div>
									<div class="video1">
										<img src="${imgServerAddr}${whg:getImg300_200(item.deourl) }" width="252" height="150">
										<span>${item.entname }</span>
									</div>
								</a>
							</c:forEach>
						</div>
					</div>
				</c:if>
				<!-- 视频 END -->

				<!-- 音频 -->
				<c:if test="${not empty audio }">
					<div class="list1 ${not empty pic || not empty video ? '':'on'}">
						<div class="demo-list list-mp3 clearfix">
							<c:forEach items="${audio }" var="item" varStatus="s">
								<a ${s.count%3 == 0?'class="last"':'' } href="javascript:void(0)" onClick="show_vedio(this,{url:'${item.enturl}'})">
									<div class="mask"></div>
									<div class="mp31">
										<span>${item.entname }</span>
									</div>
								</a>
							</c:forEach>
						</div>
					</div>
				</c:if>
				<!-- 音频 END -->
			</div>
			<%--<div class="public-share">
                <span class="btn qq"><a href="http://sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_onekey?"
										target="_blank"></a></span>
				<span class="btn weixin"><a href="javascript:void(0)" target="_blank"></a></span>
				<span class="btn weibo"><a href="javascript:void(0)" target="_blank"></a></span>
                <span class="btn dianzan">
                	<em>0</em>
                	<a href="javascript:void(0)" class="dianzan" reftyp="2016101400000051" refid="${train.id }" id="good"></a>
                    </a>
                </span>
			</div>--%>

			<div class="public-share">
				<span class="btn qq"><a href="javascript:void(0);" class="fxqq"></a></span>
				<span class="btn weixin"><a a href="javascript:void(0);" class="fxweix"></a></span>
				<span class="btn weibo"><a href="javascript:void(0)" class="fxweibo" target="_blank"></a></span>
				<span class="btn dianzan">
					<em>0</em>
					<a href="javascript:void(0)" class="dianzan" reftyp="${empty enumtypetrain?"5":enumtypetrain}" refid="${train.id }" id="good"></a>
				</span>
			</div>
			<!-- 动态包含评论 -->
			<jsp:include page="/pages/comm/agdcomment.jsp" flush="true">
				<jsp:param name="reftype" value="2"/>
				<jsp:param name="refid" value="${train.id}"/>
			</jsp:include>
			<!-- 动态包含评论-END -->

		</div>

		<div class="public-right-main">
			<div class="public-other-notice margin-bottom padding-bottom-0">
				<div class="map" id="maps_tra_target" style="width: 300px; height: 222px;">
				</div>
				<script>
					$(function () {
						WhgMap.showMap('maps_tra_target', '${train.address}', '${train.longitude}', '${train.latitude}')
					})
				</script>
			</div>
			<c:if test="${not empty kecheng }">
				<div class="public-other-notice">
					<h2>推荐课程</h2>
					<c:choose>
						<c:when test="${not empty kecheng }">
							<c:forEach items="${kecheng }" var="item">
								<div class="item clearfix">
									<div class="right-img">
										<a href="${basePath }/agdpxyz/traininfo?traid=${item.id}"><img src="${imgServerAddr}${whg:getImg300_200(item.trainimg)}" width="130" height="90"></a>
									</div>
									<div class="right-detail">
										<a href="${basePath }/agdpxyz/traininfo?traid=${item.id}"><h3>${item.title }</h3></a>
										<p class="time"><fmt:formatDate value="${item.starttime }" pattern="yyyy-MM-dd "/></p>
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
		</div>
	</div>
</div>
<!--主体结束-->

<!--公共主底部开始-->
<%@include file="/pages/comm/agdfooter.jsp"%>
<!--公共主底部结束-END-->

<a class="to-top"></a>
</body>
</html>