<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<%@include file="/pages/comm/agdhead.jsp"%>
<title>广东省文化馆-志愿服务</title>
<link href="${basePath }/static/assets/css/volunteer/zhiyuanfuwuIndex.css" rel="stylesheet">
<script src="${basePath }/static/assets/js/volunteer/zhiyuanfuwuIndex.js"></script>
<script type="text/javascript">
$(function(){
	$("#more").on('click',function(){
		var msg=$('.tab .active').html();
		if(msg == "志愿公告"){
			$(this).attr('href','${basePath}/agdzyfw/notice');
		}else{
			$(this).attr('href','${basePath}/agdzyfw/news');
		}
	})
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
            <div class="logo-small"> <a href="${basePath }/home"><img src="${basePath }/static/assets/img/public/logoSmall.png"></a> </div>
            <ul>
                <li  class="active"><a href="${basePath }/agdzyfw/index">志愿服务</a></li>
		        <li><a href="${basePath }/agdzyfw/news">志愿资讯</a></li>
		        <li><a href="${basePath }/agdzyfw/huodong">志愿活动</a></li>
		        <li><a href="${basePath }/agdzyfw/peixun">志愿培训</a></li>
		        <li><a href="${basePath }/agdzyfw/xiangmu">风采展示</a></li>
		        <li><a href="${basePath }/agdzyfw/tashan">他山之石</a></li>
		        <li class="last"><a href="${basePath }/agdzyfw/zhengce">政策法规</a></li>
            </ul>
        </div>
    </div>
</div>
<!--公共主头部结束-END-->

<!--广告开始-->
<%-- <c:choose>
   <c:when test="${not empty adv }">  
   		<c:forEach items="${adv }" var="item" end="0">
   			<div class="public_ad" style="background:url(${basePath }/${item.cfgadvpic }) no-repeat 50% 50%;"></div>  
   		</c:forEach>    
   </c:when>
   <c:otherwise> 
     	<div class="public_ad" style="background:url(${basePath }/static/assets/img/volunteer/zhiyuan-banner.jpg) no-repeat 50% 50%;"></div> 
   </c:otherwise>
</c:choose> --%>

<div class="main-info-bg zhiyuanBg" style="background:url('${basePath }/${not empty adv ? adv.cfgadvpic : 'static/assets/img/volunteer/zhiyuan-banner-2.jpg' }') no-repeat 50% 50%;">
    <div class="main-info-container">
        <div class="zhiyuan clearfix">
            <div class="number">
                <div class="num">
                    <span>${time}</span>
                    <p>服务时间</p>
                </div>
                <div class="num">
                    <span>${person}</span>
                    <p>志愿者注册人数</p>
                </div>
                <div class="num">
                    <span>${team}</span>
                    <p>志愿者组织</p>
                </div>
            </div>
            <div class="detail">
                <p>志愿服务包含着深刻的互助精神，它提倡“互相帮助、助人自助”。</p>
                <p>进步精神是志愿服务精神的重要组成部分，志愿者通过参与志愿服务，使自己的能力得到提高，同时促进了社会的进步。</p>
            </div>
            <a href="http://www.gdzyz.cn/index?districtId=402800e245df56eb0145dfd7d6bb0043"><span class="enter">加入志愿者</span></a>
        </div>
    </div>
</div>
<!--广告结束-->

<!--主体开始-->

<!-- 最新动态  -->
<div class="main-info-bg bg-color">
    <div class="main-info-container main-info-title">
        <center><h2><span class="line-l"></span><span class="tt">最新动态</span><span class="line-r"></span></h2></center>
        
        <div class="news-cont clearfix">
			<div class="img">
				<ul class="basic">
			    	<c:choose>
						<c:when test="${not empty advList }">
			   				<c:forEach items="${advList }" var="row" end="2">
					        	<li>
					                <div class="mask">
					            		<%--<a href="${basePath}/agdzyfw/newsinfo?id=${item.cfgshowid}">${not empty item.cfgshowtitle ? item.cfgshowtitle : '未配置'}</a>--%>
                                        <a href="${row.url}">${row.name }</a>
                                    </div>
                                        <a href="${row.url}"><img src="${imgServerAddr}${row.picture}" style="width: 480px; height: 370px;" onerror="showDefaultIMG(this, '${basePath }/static/assets/img/img_demo/activityindex1.jpg')"></a>
                                    <%--<a href="${basePath}/agdzyfw/newsinfo?id=${item.cfgshowid}"><img src="${basePath }/${not empty item.cfgshowpic ? item.cfgshowpic : '/static/assets/img/img_demo/activityindex1.jpg' }"></a>--%>
					        	</li>
			       			</c:forEach> 
			       		</c:when>
						<c:otherwise>
					           	<li>
					               <div class="mask">
					                   <a href="javascript:void(0)">2016年度中国歌剧舞剧的院舞2</a>
					               </div>
					               <a href="javascript:void(0)"><img src="${basePath }/static/assets/img/img_demo/activityindex1.jpg"></a>
					       		</li>
			       		</c:otherwise>
					</c:choose>
				</ul>
		         	<div class="btn-top btn-top-prev"></div>
		         	<div class="btn-top btn-top-next"></div>
			</div>
        	
            <div class="newsGroups">
                <div class="tab">
                    <ul>
                        <!-- <li class="active">志愿公告</li> -->
                        <li>志愿资讯</li>
                    </ul>
                </div>
               <%--  <div class="detail on">
                    <ul>
                    	<c:if test="${not empty zygg}">
	                    	<c:forEach items="${zygg }" var="item" end="5">
	                    		<li><i></i><a href="${basePath }/agdzyfw/noticeinfo?id=${item.clnfid}">${item.clnftltle }</a><span><fmt:formatDate value="${item.clnfcrttime}" pattern="yyyy-MM-dd "/></span></li>
	                    	</c:forEach>
                    	</c:if>
                    </ul>
                </div> --%>
                <div class="detail on">
                    <ul>																	
	                    <c:if test="${not empty zyzx}">
		                	<c:forEach items="${zyzx }" var="item" end="5">
	                        	<li><i></i><a href="${basePath }/agdzyfw/newsinfo?id=${item.clnfid}">${item.clnftltle }</a><span><fmt:formatDate value="${item.clnfcrttime}" pattern="yyyy-MM-dd "/></span></li>
	                    	</c:forEach>
	                    </c:if>
                    </ul>
                </div>
                <div class="more">
                    <a class="public-more" id="more" href="javascript:void(0)">MORE</a>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- 最新动态 END -->

<!-- 风采展示 -->
<div class="main-info-bg">
    <div class="main-info-container main-info-title">
        <div class="fengcai">
            <div class="fengcai-row fengcai-row1">
                <div class="content content1">
                    <div class="mask">
                        <a href="${fengcai[0].url}">${not empty fengcai[0]?fengcai[0].name :'2016年度志愿活动舞剧的院舞'}</a>
                    </div>
                    <a href="${fengcai[0].url}"><img src="${imgServerAddr }/${not empty fengcai[0]?fengcai[0].picture :'static/assets/img/img_demo/zhiyuan1.jpg'}"></a>
                </div>
                <div class="content content2">
                    <div class="mask">
                        <a href="${fengcai[3].url}">${not empty fengcai[3]?fengcai[3].name :'2016年度志愿活动舞剧的院舞'}</a>
                    </div>
                    <a href="${fengcai[3].url}"><img src="${imgServerAddr }/${not empty fengcai[3]?fengcai[3].picture :'static/assets/img/img_demo/zhiyuan1.jpg'}"></a>
                </div>
            </div>
            <div class="fengcai-row fengcai-row2">
                <div class="content content1">
                    <div class="mask">
                        <a href="${fengcai[1].url}">${not empty fengcai[1]?fengcai[1].name :'2016年度志愿活动舞剧的院舞'}</a>
                    </div>
                    <a href="${fengcai[1].url}"><img src="${imgServerAddr }/${not empty fengcai[1]?fengcai[1].picture :'static/assets/img/img_demo/zhiyuan1.jpg'}"></a>
                </div>
                <div class="content content2">
                    <div class="mask">
                        <a href="${fengcai[2].url}">${not empty fengcai[2]?fengcai[2].name :'2016年度志愿活动舞剧的院舞'}</a>
                    </div>
                    <a href="${fengcai[2].url}"><img src="${imgServerAddr }/${not empty fengcai[2]?fengcai[2].picture :'static/assets/img/img_demo/zhiyuan1.jpg'}"></a>
                </div>
            </div>
            <div class="fengcai-row fengcai-row3">
                <div class="content content2">
                    <div class="mask">
                        <a href="${fengcai[4].url}">${not empty fengcai[4]?fengcai[4].name :'2016年度志愿活动舞剧的院舞'}</a>
                    </div>
                    <a href="${fengcai[4].url}"><img src="${imgServerAddr }/${not empty fengcai[4]?fengcai[4].picture :'static/assets/img/img_demo/zhiyuan1.jpg'}"></a>
                </div>
                <div class="content content1">
                    <div class="infocon">
                        <h3>风采展示</h3>
                        <div class="label">
                            <a href="${basePath }/agdzyfw/xiangmu">项目示范</a>
                            <a href="${basePath }/agdzyfw/geren">先进个人</a>
                            <a href="${basePath }/agdzyfw/youxiuzuzhi">优秀组织</a>
                            <a href="${basePath }/agdzyfw/xiangmu">更多</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- 风采展示 END -->
<!--主体结束-->

<!--公共主底部开始-->
<%@include file="/pages/comm/agdfooter.jsp"%>
<!--公共主底部结束-END-->
</body>
</html>