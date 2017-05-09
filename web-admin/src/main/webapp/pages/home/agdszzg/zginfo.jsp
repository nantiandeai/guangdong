<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="author" content="">
<%@include file="/pages/comm/agdhead.jsp"%>
<title>广东省文化馆-数字展览--${art.arttitle }</title>
<link href="${basePath }/static/assets/css/science/scienceDesc.css" rel="stylesheet">
<script src="${basePath }/static/assets/js/science/scienceDesc.js"></script>
<script src="${basePath }/static/assets/js/public/rong-dialog.js"></script>
<script src="${basePath }/static/assets/js/plugins/zoomin/zoomin.js"></script>
<script>
$(function(){
	//alert(new Date("${exh.exhstime}"));
	var stime = new Date("${exh.exhstime}");//new Date('<fmt:formatDate value="${exh.exhstime}" pattern="yyyy-MM-dd"/>');
	var etime = new Date("${exh.exhetime}");//new Date('<fmt:formatDate value="${exh.exhetime}" pattern="yyyy-MM-dd"/>');
	var dayNum = WHComm.d_getDay(stime, etime)+"";
	//alert(stime+" "+etime+" "+dayNum);
	$('#exhDays').html(dayNum+'天');
   	var a =	"${art.artkeys}";
   	var array = a.split(",");
	if (array.length > 0) {
		for (var i = 0; i < array.length; i++) {
   	 		$('.main-info-container >.public-left-main> .noticeInfo-main>.otherInfo >.key').append("<span>"+array[i]+"</span>");
   		}
	}
});
</script>
</head>
<body>
<!-- 头部开始 -->
<%@include file="/pages/comm/agdtopsmall.jsp"%>
<!-- 头部结束-END -->

<!--公共主头部开始-->
<div id="header-fix">
	<div class="header-nav-bg">
    	<div class="header-nav">
      		<div class="logo-small"> <a href="${basePath }/home"><img src="${basePath }/static/assets/img/public/logoSmall.png"></a> </div>
	      		<ul>
	        		<li class="active last"><a href="${basePath }/agdszzg/index">数字展厅</a></li>
	       			<!--  <li class="last"><a href="#">普通展厅</a></li> -->
	      		</ul>
     	</div>
  	</div>
</div>
<!--公共主头部结束--> 

<!--面包屑开始-->
<div class="public-crumbs">
	<span><a href="${basePath }/home">首页</a></span><span>></span>
	<span><a href="${basePath }/agdszzg/index">数字展厅</a></span><span>></span>
	<span><a href="${basePath }/agdszzg/zglist?id=${exh.exhid}">${exh.exhtitle }</a></span><span>></span>
	<span>${art.arttitle }</span>
</div>
<!--面包屑结束-->

<!--主体开始-->
<div class="main-info-bg main-info-bgColorW clearfix">
	<div class="main-info-container">
		<div class="public-left-main">
			<div class="noticeInfo-main">
				<h1>${art.arttitle }</h1>
				<div class="otherInfo">
					<span class="adr">作者 :<span>${art.artauthor}</span></span> 
					<span class="key">关键字:</span> 
					<span class="time">时间 :<span><fmt:formatDate value="${art.artcrttime}" pattern="yyyy.MM.dd"/></span></span>
				</div>
				<div class="info-main">
					<div class="topImg">
						<a href="javascript:void(0)" onClick="show_img(this,{url:'${imgServerAddr }/${art.artpic}',zoomin:'ture'})">
							<img src="${imgServerAddr }/${art.artpic }" width="800">
						</a>
					</div>
					<c:if test="${picList != null}">
					<div class="bottomImgList">
						<!--当数量超过4个的时候显示下面这个more的DIV-->
						<c:if test="${picList != null && fn:length(picList) > 4 }">
							<div class="more"></div>
						</c:if>
						<ul class="clearfix">
							<c:forEach items="${picList }" var="pic" varStatus="s">
								<li <c:if test="${ s.index > 0 &&  s.index%3 == 0 }"> class="last" </c:if> > 
									<a href="javascript:void(0)" onClick="show_img(this,{url:'${imgServerAddr }${pic.enturl }',zoomin:'ture'})"><img src="${imgServerAddr }${pic.enturl }" width="190"></a>
								</li>
							</c:forEach>
						</ul>
					</div>
					</c:if>
				</div>
				
				 <div class="page-cont">
	               	<c:if test="${preArt != null }"><span class="prev"><a href="${basePath }/agdszzg/zginfo?id=${preArt.artid}">&lt;&lt;上一篇作品</a></span></c:if>
        			<c:if test="${nextArt != null }"><span class="next"><a href="${basePath }/agdszzg/zginfo?id=${nextArt.artid}">下一篇作品 &gt;&gt;</a></span></c:if>
	            </div>
				
			<!--<div class="voteCont">
					这个是二期功能
					<a href="javascript:void(0)">投它一票</a>
				</div> -->
				
				<div class="public-info-step">
					<h3>
						<span>作品详情</span>
					</h3>
					<div class="info">
						${art.artcontent}
					</div>
					<h3>
						<span>关于作者</span>
					</h3>
					<div class="info">
						${art.artartistdesc}
					</div>
					
				<div class="public-share">
	                <span class="btn qq"><a href="javascript:void(0);" class="fxqq"></a></span>
	                <span class="btn weixin"><a href="javascript:void(0)" class="fxweix"></a></span>
					<span class="btn weibo"><a href="javascript:void(0)" class="fxweibo" target="_blank"></a></span>
	                <span class="btn dianzan">
	                	<em>0</em>
	                	<a href="javascript:void(0)" class="dianzan" reftyp="2016101400000054" refid="${art.artid }" id="good">
	                    </a>
	                </span>
            	</div> 
					
					<!-- 动态包含评论 -->
			        <%--<jsp:include page="/pages/comm/agdcomment.jsp" flush="true">     --%>
					     <%--<jsp:param name="reftype" value="2016101400000054"/> --%>
					     <%--<jsp:param name="refid" value="${art.artid }"/> --%>
					<%--</jsp:include>--%>
					<!-- 动态包含评论-END -->
				</div>
			</div>
		</div>
		<div class="public-right-main">
			<div class="public-other-notice">
				<h2>展览信息</h2>
				<p>展览天数<span id="exhDays">15天</span></p>
				<p>展览日期<span><fmt:formatDate value="${exh.exhstime}" pattern="yyyy-MM-dd"/> 至 <fmt:formatDate value="${exh.exhetime}" pattern="yyyy-MM-dd"/></span></p>
				<p>展览地址<span>${exh.exhaddress}</span></p>
			</div>
		</div>
	</div>
</div>
<!--主体结束--> 

<!--公共主底部开始-->
<%@include file="/pages/comm/agdfooter.jsp"%>
<!--公共主底部结束-END-->
</body>
</html>