<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<%@include file="/pages/comm/agdhead.jsp"%>
<title>广东省文化馆-非遗中心-传承人-${suorDetail.suorname}</title>
<link href="${basePath }/static/assets/css/intangibleheritage/mingluliebiao.css" rel="stylesheet">
<link href="${basePath }/static/assets/js/plugins/oiplayer-master/css/oiplayer.css" rel="stylesheet">

<!-- core public JavaScript -->
<script src="${basePath }/static/assets/js/intangibleheritage/chuanchengrenxiangqing.js"></script>
<script src="${basePath }/static/assets/js/plugins/oiplayer-master/plugins/flowplayer-3.2.6.min.js" type="text/javascript"></script>
<script src="${basePath }/static/assets/js/plugins/oiplayer-master/js/jquery.oiplayer.js"></script>
<script src="${basePath }/static/assets/js/plugins/roll/jquery.sly.min.js"></script>
<script src="${basePath }/static/assets/js/intangibleheritage/chuanchengrenxiangqing.js"></script>

<script type="text/javascript">

/**页面加载*/
$(function() {
	$(".source li:eq(0)").addClass("active");
	var _html = $(".source li:eq(0)").html();
	$(".sourceinfo").children("div:eq(0)").addClass("on");
	
	if($(".source li").size() < 1 ){
		$(".source").remove();
	}
})

</script>
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
				<li><a href="${basePath }/agdfyzg/index">非遗首页</a></li>
				<li><a href="${basePath }/agdfyzg/notice">最新公告</a></li>
				<li><a href="${basePath }/agdfyzg/news">新闻动态</a></li>
				<li><a href="${basePath }/agdfyzg/minglu">名录项目</a></li>
				<li class="active"><a href="${basePath }/agdfyzg/chuanchengren">传承人</a></li>
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
    <span><a href="${basePath }/home">首页</a></span><span>></span><span><a href="${basePath }/agdfyzg/index">非遗中心</a></span><span>></span><span><a href="${basePath }/agdfyzg/chuanchengren">传承人</a></span><span>></span><span>${suorDetail.suorname }</span>
</div>
<!--面包屑结束-->

<!--主体开始-->
<div class="main-info-bg main-info-no-padding main-info-bgColorW">
	<div class="main-info-container clearfix">
		<!--详情上部 start-->
		<div class="renwu-detail">
			<div class="img">
            	<img src="${imgServerAddr}/${suorDetail.suorpic}"  width="160" height="160" onerror="showDefaultIMG(this, '${basePath }/static/assets/img/img_demo/1.jpg')">
			</div>
          	<div class="detail">
              <div class="name">${suorDetail.suorname }</div>
              <div class="address"><i class="public-s-ico s-ico-10"></i>区域：<span>${suorDetail.name }</span></div>
              <div class="demo">
              <i class="public-s-ico s-ico-12"></i>传承项目：
	              <c:if test="${not empty minglu}">
		              <c:forEach items="${minglu}" var="item">
		                  <span class="xiangmu">${item.mlproshortitel }<span class="jibie">(${item.name })</span></span>
		              </c:forEach>
	              </c:if>
				  <c:if test="${empty minglu}">-</c:if>
              </div>
          	</div>
		</div>
		<!--详情上部 end-->
		
		<div class="con-left clearfix">
			<div class="public-info-step">
			
				<!-- 传承人详情 start -->
				<h3> <span>传承人简介</span></h3>
				<div class="info">${suorDetail.suorjs}</div>
				<h3><span>艺术成就</span></h3>
				<div class="info"> ${suorDetail.suorachv}</div>
				<div class="public-share">
					<span class="btn qq">
						<a href="javascript:void(0);" class="fxqq"></a>
					</span>
					<span class="btn weixin">
						<a href="javascript:void(0)" target="_blank" class="fxweix"></a>
					</span>
					<span class="btn weibo">
						<a href="javascript:void(0)" target="_blank" class="fxweibo"></a>
					</span>
					<span class="btn dianzan">
						<em>129</em>
						<a href="javascript:void(0)" class="dianzan" reftyp="2016112900000007" refid="${suorDetail.suorid }" id="good"></a>
					</span>
				</div>
				 
				<!-- 传承人项目 start -->
              	<h3 ${empty minglu2 ? 'class="none"':''}><span>传承人项目</span>
                	<div class="pre-right1">
	                    <i class="kx-arrow kx-arrow-left">
	                        <em></em>
	                        <span></span>
	                    </i>
                  	</div>
	                <div class="pre-left1">
                    	<i class="kx-arrow kx-arrow-right">
	                        <em></em>
	                        <span></span>
                    	</i>
	                </div>
             	</h3> 
	            <c:if test="${not empty minglu2}">
	                <div class="img-list">
	                	<ul class="clearfix">
		                   		<c:forEach items="${minglu2}" var="item">
		                     		 <li>
	                        	 	 	<a href="${basePath }/agdfyzg/mingluinfo?mlproid=${item.mlproid}">
			                               <div class="img">
											   <img src="${imgServerAddr}/${item.mlprosmpic}" width="253" height="168">
			                                  <div class="mask"></div>
			                               </div>
			                               <div class="name">  ${item.mlproshortitel}</div>
			                          	</a>
				                     </li>
		                   		</c:forEach>
	                  	</ul>
	              	</div>
                </c:if>
				
				<!--传承人资源 start -->
              	<div class="site clearfix sourceinfo">
                	<ul class="tab clearfix source">
		                <c:if test="${not empty tup}">
		                	<li>传承人图片</li>
		                </c:if>
		                <c:if test="${not empty spin}">
		                	<li>传承人视频</li>
		                </c:if>
		                <c:if test="${not empty ypin}">
		                	<li>传承人音频</li>
		                </c:if>
						<c:if test="${not empty doc}">
							<li>传承人文档</li>
						</c:if>
                	</ul>
                  
	                <c:if test="${not empty tup}">
	                	<div class="list1 on">
	                 		<div class="demo-list list-video clearfix">
	                        	<c:forEach items="${tup}" var="item" varStatus="stut">
		                          	<a ${stut.count%3 == 0?'class="last"':'' } href="javascript:void(0)" onClick="show_img(this,{url:'${imgServerAddr}/${item.enturl}'})" class="${stut.count%3 == 0 ?'last':''}">
			                            <div class="img1">
			                           		<img src="${imgServerAddr}/${item.enturl}" onerror="showDefaultIMG(this, '${basePath }/static/assets/img/img_demo/1.jpg')">
			                            	<span>${item.entname }</span>
			                            </div>
		                          	</a>
	                        	</c:forEach>
	                    	</div>
	                	</div>
	                </c:if>   
	                
	               	<c:if test="${not empty spin}">
	                	<div class="list1">
	                    	<div class="demo-list list-video clearfix">
	                      		<c:forEach items="${spin }" var="item" varStatus="stut">
	                          		<a ${stut.count%3 == 0?'class="last"':'' } href="javascript:void(0)" onClick="show_vedio(this,{url:'${basePath}/${item.enturl}'})" class="${stut.count%3 == 0 ?'last':''}">
	                              		<div class="mask"></div>
	                              		<div class="video1">
		                                  	<img src="${imgServerAddr}/${item.deourl}" width="252" height="170" onerror="showDefaultIMG(this, '${basePath }/static/assets/img/img_demo/1.jpg')">
		                                  	<span>${item.entname }</span>
	                             		</div>
	                          		</a>
	                        	</c:forEach>
	                    	</div>
	                	</div>
	                </c:if>
	                
	                 <c:if test="${not empty ypin}">
	                 	<div class="list1">
	                    	<div class="demo-list list-mp3 clearfix">
		                        <c:forEach items="${ypin }" var="item" varStatus="stut">
		                          <a ${stut.count%3 == 0?'class="last"':'' } href="javascript:void(0)" onClick="show_vedio(this,{url:'${basePath}/${item.enturl}'})" class="${stut.count%3 == 0 ?'last':''}">
		                          	  <div class="mask"></div>
		                              <div class="mp31">
		                                  <span>${item.entname }</span>
		                              </div>
		                          </a>
								</c:forEach>
	                    	</div>
	                	</div>
					</c:if>
					<!-- 下载 -->
					<c:if test="${not empty doc }">
						<div class="list1">
							<div class="file-download-cont">
								<ul>
									<c:forEach items="${doc}" var="loadlists" varStatus="s">
										<li>
											<a href="${basePath }/whtools/downFile?filePath=${loadlists.enturl}"><i></i>${loadlists.entname}</a>
										</li>
									</c:forEach>
								</ul>
							</div>
						</div>
					</c:if>
					<!-- 下载 -->
            	</div>
			</div>
		</div>
		
		<!-- 传承人推荐 start -->
      	<div class="public-right-main">
        	<div class="public-info-step">
            	<h3><span>传承人推荐</span></h3>
            	<div class="tuijian">
                	<c:choose>
                 		<c:when test="${not empty tjsuor}">
                  			<ul>
		                  		<c:forEach items="${tjsuor}" var="item">
		                        	<li>
		                      	    	<div class="img"><a href="${basePath }/agdfyzg/chuanchengreninfo?suorid=${item.suorid}"><img src="${imgServerAddr }/${whg:getImg300_200(item.suorpic) }" width="45" height="45" onerror="showDefaultIMG(this, '${basePath }/static/assets/img/img_demo/1.jpg')"></a></div>
		                           		<a href="${basePath }/agdfyzg/chuanchengreninfo?suorid=${item.suorid}"><h4>${item.suorname }</h4></a>
		                            	<p>${item.mlproshortitel }</p>
		                      		</li>
		                    	</c:forEach>
                			</ul>
                  		</c:when>
                  		<c:otherwise>
                  			<div class='public-no-message'></div>
                  		</c:otherwise>
              		</c:choose>
            	</div>
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