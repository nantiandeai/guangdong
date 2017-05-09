<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<%@include file="/pages/comm/agdhead.jsp"%>
<title>广东省文化馆-志愿服务-志愿活动-${zyhd.zyhdtitle}</title>
<link href="${basePath }/static/assets/css/volunteer/huodongxiangqing.css" rel="stylesheet">
<link href="${basePath }/static/assets/js/plugins/oiplayer-master/css/oiplayer.css" rel="stylesheet">
<script src="${basePath }/static/assets/js/plugins/oiplayer-master/plugins/flowplayer-3.2.6.min.js"></script>
<script src="${basePath }/static/assets/js/plugins/oiplayer-master/js/jquery.oiplayer.js"></script>
<script type="text/javascript">
$(document).ready(function(e) {

    $(".tab li").click(function() {
        $(this).addClass("active").siblings().removeClass('active');
        $(".list1").eq($(".tab li").index(this)).addClass("on").siblings().removeClass('on');
    })
    $(".tab li").eq(0).trigger("click");
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
            <div class="logo-small"> <a href="${basePath}/home"><img src="${basePath }/static/assets/img/public/logoSmall.png"></a> </div>
            <ul>
                <li><a href="${basePath }/agdzyfw/index">志愿服务</a></li>
                <li><a href="${basePath }/agdzyfw/news">志愿资讯</a></li>
                <li class="active"><a href="${basePath }/agdzyfw/huodong">志愿活动</a></li>
                <li><a href="${basePath }/agdzyfw/peixun">志愿培训</a></li>
                <li><a href="${basePath }/agdzyfw/xiangmu">风采展示</a></li>
                <li><a href="${basePath }/agdzyfw/tashan">他山之石</a></li>
                <li class="last"><a href="${basePath }/agdzyfw/zhengce">政策法规</a></li>
            </ul>
        </div>
    </div>
</div>
<!--公共主头部结束-END-->

<!--面包屑开始-->
<div class="public-crumbs">
    <span><a href="${basePath }/dgszwhg/index">首页</a></span><span>></span><span><a href="${basePath }/agdzyfw/index">志愿服务</a></span><span>></span><span><a href="${basePath }/agdzyfw/huodong">志愿活动</a></span><span>></span><span>${zyhd.zyhdtitle}</span>
</div>
<!--面包屑结束-->
<!--主体开始-->
<div class="special-bg">
    <div class="special-main">
        <div class="special-head">
            <div class="special-head-left" style="background:url(${imgServerAddr }/${zyhd.zyhdbigpic}"></div>
            <div class="special-head-right clearfix">
                <div class="head-father">
                    <div class="head-con on">
                        <h1 style="margin: 36px 0 10px 0;">${zyhd.zyhdtitle}</h1>
                        <div class="detail">
                            <div class="time"><i class="public-s-ico s-ico-14"></i>发起：<span>${zyhd.zyhdstart}</span></div>
                            <div class="time1"><i class="public-s-ico s-ico-10"></i>地址：<span>${zyhd.zyhdaddr}</span></div>
                            <div><i class="public-s-ico s-ico-8"></i>时间：<span><fmt:formatDate value="${zyhd.zyhdsdate}" pattern="yyyy-MM-dd "/></span></div>
                        </div>
                        <a class="lookdetail" href="http://www.gdzyz.cn/" target="_blank">了解详情</a>
                    </div>
                </div>
            </div>
        </div>
        <div class="special-content clearfix">
            <div class="con-left clearfix">
                <div class="site">
                    <div class="site-head"><span>现场直击</span></div>
                    <div class="detail">
                        <p>
							${zyhd.zyhdcontent}                        
                        </p>
                    </div>
                </div>
                <div class="public-share">
	                <span class="btn qq"><a href="javascript:void(0);" class="fxqq"></a></span>
	                <span class="btn weixin"><a href="javascript:void(0)" class="fxweix"></a></span>
					<span class="btn weibo"><a href="javascript:void(0)" class="fxweibo" target="_blank"></a></span>
	                <span class="btn dianzan">
	                	<em>0</em>
	                	<a href="javascript:void(0)" class="dianzan" reftyp="2016120900000003" refid="${zyhd.zyhdid}" id="good">
	                    </a>
	                </span>
            	</div> 
            	<!-- 资源展示  --> 
                <div class="site clearfix">
                  <ul class="tab clearfix">
                  <c:if test="${not empty pic}">
                      <li class="${not empty pic ? 'active' : '' }">项目图片</li>
                  </c:if>
                  <c:if test="${not empty vido}">
                      <li class="${not empty pic ? '' : 'active' }">项目视频</li>
                  </c:if>
                  <c:if test="${not empty musci}">
                      <li class="${not empty pic || not empty vido ? '' : 'active' }">项目音频</li>
                  </c:if>
                  </ul>
                  
                 <c:if test="${not empty pic}">
                  <div class="list1 on">
                      <div class="demo-list list-video clearfix">
	                      <c:forEach items="${pic}" var="pics" varStatus="s">
	                          <a ${s.count%3 == 0?'class="last"':'' } href="javascript:void(0)" onClick="show_img(this,{url:'${imgServerAddr }/${not empty pics.enturl ? pics.enturl : 'static/assets/img/img_demo/works-1.jpg'}' })">
	                              <div class="img1">
	                                  <img src="${imgServerAddr }/${not empty pics.enturl ? pics.enturl : 'static/assets/img/img_demo/works-1.jpg'}">
	                                  <span>${pics.entname}</span>
	                              </div>
	                          </a>
	                      </c:forEach>
                      </div>
                  </div>
                 </c:if> 
                 
                 <c:if test="${not empty vido}">
                 <div class="list1">
                      <div class="demo-list list-video clearfix">
                     
                      	<c:forEach items="${vido}" var="vidos" varStatus="s">
                          <a ${s.count%3 == 0?'class="last"':'' } href="javascript:void(0)" onClick="show_vedio(this,{url:'${basePath }/${vidos.enturl}',basePath:'${basePath }'})">
                              <div class="mask"></div>
                              <div class="video1">
                                  <img src="${imgServerAddr }/${not empty vidos.deourl ? vidos.deourl : 'static/assets/img/public/vedioBg.jpg'}">
                                  <span>${vidos.entname}</span>
                              </div>
                          </a>
                          </c:forEach>
                         
                      </div>
                  </div>
                 </c:if>
                 
                  <c:if test="${not empty musci}">
                  <div class="list1">
                    <div class="demo-list list-mp3 clearfix">
                    <c:forEach items="${musci}" var="muscis" varStatus="s">
                        <a ${s.count%3 == 0?'class="last"':'' } href="javascript:void(0)" onClick="show_vedio(this,{url:'${basePath }/${muscis.enturl}',basePath:'${basePath }'})">
                        	  <div class="mask"></div>
                            <div class="mp31">
                                <span>${muscis.entname}</span>
                            </div>
                        </a>
                     </c:forEach> 
                    </div>
                </div>
               </c:if> 
              </div>
              <!-- 资源展示 - END --> 
                <%-- <div class="site clearfix">
                  <ul class="tab clearfix">
                  <c:if test="${not empty pic}">
                      <li class="active">活动图片</li>
                  </c:if>
                  </ul>
                  <div class="list1 on">
                      <div class="demo-list list-video clearfix">
	                      <c:forEach items="${pic }" var="item">
		                      <c:if test="${not empty pic}">
		                          <a href="javascript:void(0)" onClick="show_img(this,{url:'${basePath }/${item.enturl }'})">
		                              <div class="img1">
		                                  <img src="${basePath }/${item.enturl }">
		                                  <span>${item.entname }</span>
		                              </div>
		                          </a>
		                       </c:if>
	                       </c:forEach>
                      </div>
                  </div>
              </div> --%>
            </div>
            <div class="public-right-main">
                <div class="public-other-notice">
                    <h2>推荐活动</h2>
                    <c:choose>
					   <c:when test="${not empty kecheng }">  
						   <c:forEach items="${kecheng}" var="item">
			                    <div class="item clearfix">
			                        <div class="right-img">
			                            <a href="${basePath }/agdzyfw/huodonginfo?zyhdid=${item.zyhdid}"><img src="${imgServerAddr }/${item.zyhdpic}" width="130" height="90"></a>
			                        </div>
			                        <div class="right-detail">
			                            <a href="${basePath }/agdzyfw/huodonginfo?zyhdid=${item.zyhdid}"><h3>${item.zyhdtitle}</h3></a>
			                            <p class="time"><fmt:formatDate value="${item.zyhdsdate}" pattern="yyyy-MM-dd "/></p>
			                        </div>
			                    </div>
		                	</c:forEach>      
					   </c:when>
					   <c:otherwise> 
					   		<div class="public-no-message "></div>
					   </c:otherwise>
					</c:choose>
                    
                    <%-- //
                    <c:if test="${not empty kecheng }">
	                    <c:forEach items="${kecheng}" var="item">
		                    <div class="item clearfix">
		                        <div class="right-img">
		                            <a href="${basePath }/agdzyfw/huodonginfo?zyhdid=${item.zyhdid}"><img src="${basePath }/${item.zyhdpic}" width="130" height="90"></a> 
		                        </div>
		                        <div class="right-detail">
		                            <a href="${basePath }/agdzyfw/huodonginfo?zyhdid=${item.zyhdid}"><h3>${item.zyhdtitle}</h3></a>
		                            <p class="time"><fmt:formatDate value="${item.zyhdsdate}" pattern="yyyy-MM-dd "/></p>
		                        </div>
		                    </div>
		                </c:forEach>
                    </c:if> --%>
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