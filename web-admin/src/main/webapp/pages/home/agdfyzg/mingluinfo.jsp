<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<%@include file="/pages/comm/agdhead.jsp"%>
<%@include file="/pages/comm/comm_video.jsp"%>
<title>广东省文化馆-非遗中心-${proDetail.mlprotailtitle}</title>
<link href="${basePath }/static/assets/css/train/noticeInfo.css" rel="stylesheet">
<link href="${basePath }/static/assets/css/intangibleheritage/mingluliebiao.css" rel="stylesheet">
<link href="${basePath }/static/assets/js/plugins/oiplayer-master/css/oiplayer.css" rel="stylesheet">
<script src="${basePath }/static/assets/js/plugins/oiplayer-master/plugins/flowplayer-3.2.6.min.js" type="text/javascript"></script>
<script src="${basePath }/static/assets/js/plugins/oiplayer-master/js/jquery.oiplayer.js"></script>
<script src="${basePath }/static/assets/js/intangibleheritage/mingluliebiao.js"></script>

<script type="text/javascript">
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
				<li class="active"><a href="${basePath }/agdfyzg/minglu">名录项目</a></li>
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
    <span><a href="${basePath }/home">首页</a></span><span>></span><span><a href="${basePath }/agdfyzg/index">非遗中心</a></span><span>></span><span><a href="${basePath }/agdfyzg/minglu">名录项目</a></span><span>></span><span>${proDetail.mlprotailtitle}</span>
</div>
<!--面包屑结束-->
<!--主体开始-->
<div class="main-info-bg main-info-no-padding main-info-bgColorW">
  <div class="main-info-container clearfix">
    <div class="public-left-main">
    	<div class="noticeInfo-main">
        	<h1>${proDetail.mlprotailtitle}</h1>
            <div class="otherInfo">
            	<span class="adr">申报地区 :<span>${proDetail.mlprosbaddr}</span></span>
                <span class="adr">来源 :<span>${proDetail.mlprosource}</span></span>
            	<span class="key">关键字 :
	            	<c:if test="${keylist != null}">
		            	<c:forEach items="${keylist}" var="item">
		            		<span>${item.name}</span>
		            	</c:forEach>
            		</c:if>
            	<span class="time">时间 :<span><fmt:formatDate value='${proDetail.mlprotime}' pattern='yyyy.MM.dd'/></span></span>
            </div>
            <div class="info-main">
                <p> ${proDetail.mlprodetail} </p>
                <c:if test="${proDetail.mlprobigpic != null}">
                	<img src="${imgServerAddr}/${proDetail.mlprobigpic}">
                </c:if>
            </div>
            <div class="public-share">
                <span class="btn qq"><a href="javascript:void(0)" class="fxqq"></a></span>
                <span class="btn weixin"><a href="javascript:void(0)" target="_blank" class="fxweix"></a></span>
                <span class="btn weibo"><a href="javascript:void(0)" target="_blank" class="fxweibo"></a></span>
                <span class="btn dianzan">
                	<em>129</em>
                	<a href="javascript:void(0)" class="dianzan" reftyp="2016112900000007" refid="${proDetail.mlproid }" id="good"></a>
                </span>
            </div> 
             <c:if test="${not empty suor}">
	            <div class="chuanchen">
	                <h3><span>项目传承人</span></h3>
	                <div class="chuanchen-list">
	                    <ul class="clearfix">
	                    	<c:forEach items="${suor}" var="item">
	                        <li>
	                            <div class="img"><a href="${basePath }/agdfyzg/chuanchengreninfo?suorid=${item.suorid}"><img src="${imgServerAddr}/${item.suorpic}" width="136" height="136" onerror="showDefaultIMG(this, '${basePath }/static/assets/img/img_demo/1.jpg')"></a></div>
	                            <div class="name"><a href="${basePath }/agdfyzg/chuanchengreninfo?suorid=${item.suorid}">${item.suorname}</a></div>
	                        </li>
	                    	</c:forEach>
	                    </ul>
	                </div>
	            </div>
            </c:if>
        	<div class="site clearfix sourceinfo">
                <ul class="tab clearfix source">
                	 <c:if test="${not empty tup}">
                      <li class="active">项目图片</li>
                      </c:if>
                       <c:if test="${not empty spin}">
                      <li>项目视频</li>
                      </c:if>
                     <c:if test="${not empty ypin}">
                      <li>项目音频</li>
                     </c:if>
					<c:if test="${not empty doc}">
						<li>项目文档</li>
					</c:if>
                  </ul>
                <c:if test="${not empty tup}">
                  <div class="list1">
                      <div class="demo-list list-video clearfix">
                         <c:forEach items="${tup}" var="item" varStatus="stut">
                          <a ${stut.count%3 == 0?'class="last"':'' } href="javascript:void(0)" onClick="show_img(this,{url:'${imgServerAddr}/${item.enturl}'})" class="${stut.count%3 == 0 ?'last':''}">
                              <div class="img1">
                                  <img src="${imgServerAddr}/${item.enturl}" onerror="showDefaultIMG(this, '${basePath }/static/assets/img/img_demo/1.jpg')" width="252" height="170">
                                  <span>${item.entname}</span>
                              </div>
                          </a>
                         </c:forEach>
                      </div>
                  </div>
                </c:if>

				<c:if test="${not empty spin}">
                 	 <div class="list1">
                      <div class="demo-list list-video clearfix">
                          <c:forEach items="${spin}" var="item" varStatus="stut">
	                          <a ${stut.count%3 == 0?'class="last"':'' } href="javascript:void(0)" onClick="show_vedio(this,{url:'${basePath}/${item.enturl}'})" class="${stut.count%3 == 0 ?'last':''}">
	                              <div class="mask"></div>
	                              <div class="video1">
	                                  <img src="${imgServerAddr}/${item.deourl}" width="252" height="170" onerror="showDefaultIMG(this, '${basePath }/static/assets/img/img_demo/1.jpg')">
	                                  <span>${item.entname}</span>
	                              </div>
	                          </a>
                          </c:forEach>
                      </div>
                  </div>
                 </c:if>
				 <c:if test="${not empty ypin}">
                  <div class="list1">
                      <div class="demo-list list-mp3 clearfix">
                          <c:forEach items="${ypin}" var="item" varStatus="stut">
	                          <a ${stut.count%3 == 0?'class="last"':'' } href="javascript:void(0)" onClick="show_vedio(this,{url:'${basePath}/${item.enturl}'})" class="${stut.count%3 == 0 ?'last':''}">
	                          	  <div class="mask"></div>
	                              <div class="mp31">
	                                  <span>${item.entname}</span>
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
            <!--<div class="next-notice">-->
            	<!--<a href="#">下一篇<span>广东省文化馆中心</span></a>-->
            <!--</div>-->
        </div>
    </div>
      <div class="public-right-main">
          <div class="public-other-notice">
              <h2>名录推荐</h2>
              <c:choose>
                 <c:when test="${not empty tjminglu}">
                  	<c:forEach items="${tjminglu}" var="item">
	              		<div class="item clearfix">
		                  	<div class="right-img">
		                       <a href="${basePath }/agdfyzg/mingluinfo?mlproid=${item.mlproid}"><img src="${imgServerAddr}/${item.mlprosmpic}" width="130" height="90" onerror="showDefaultIMG(this, '${basePath }/static/assets/img/img_demo/1.jpg')"></a>
		                  	</div>
		                  	<div class="right-detail">
		                      	<a href="${basePath }/agdfyzg/mingluinfo?mlproid=${item.mlproid}"><h3>${item.mlproshortitel}</h3></a>
		                      	<p class="time"><fmt:formatDate value="${item.mlprotime}" pattern="yyyy-MM-dd"/></p>
	                  		</div>
	              		</div>
             		</c:forEach>
                  </c:when>
                  <c:otherwise>
                  	<div class='public-no-message'></div>
                  </c:otherwise>
              </c:choose>
          </div>
		  <!-- 相关资讯-->
		  <c:if test="${not empty info}">
			  <div class="public-other-notice" style="margin-top: 20px">
				  <h2>相关资讯</h2>
				  <ul>
					  <c:forEach items="${info }" var="row" varStatus="s">
						  <li>
							  <a href="${basePath }/agdfyzg/newsinfo?id=${row.clnfid}">${row.clnftltle }</a>
							  <p class="time"><fmt:formatDate value="${row.clnfcrttime}" pattern="yyyy-MM-dd" /></p>
						  </li>
					  </c:forEach>
				  </ul>
			  </div>
		  </c:if>
	  </div>
  </div>
</div>
<!--主体结束-->

<!--公共主底部开始-->
<%@include file="/pages/comm/agdfooter.jsp"%>
<!--公共主底部结束-END-->
</body>
</html>