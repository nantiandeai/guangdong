<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv=”X-UA-Compatible” content=”IE=edge,chrome=1″ />
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="keywords" content="">
<meta name="author" content="">
<title>${art.arttitle }</title>
<%@include file="/pages/comm/comm_head.jsp"%>
<link href="${basePath }/static/assets/css/art/soloList.css" rel="stylesheet">
<link href="${basePath }/static/assets/css/art/soloArt.css" rel="stylesheet">
<script>
$(function(){
	//点评不生效
	$('.submit-dianp, .wxldisabled').bind('click.wxl', function(e){
		e.preventDefault();
	});
	
	//计算展览天数
	var time1 = Date.parse('<fmt:formatDate value="${art.artstime}" pattern="MM-dd-yyyy"/>');
	var time2 = Date.parse('<fmt:formatDate value="${art.artetime}" pattern="MM-dd-yyyy"/>');
	var dayCount = (Math.abs(time2 - time1)) / 86400000;
	$('.days').html(dayCount);
});
</script>
</head>
<body>
<!-- 公共头部开始 -->
<%@include file="/pages/comm/szheader.jsp"%>
<!-- 公共头部结束 -->

<!--广告开始-->
<div class="art-ad" style="background:url(${basePath }/static/assets/img/art/banner.jpg) no-repeat 50% 50%"></div>
<!--广告结束-->

<!-- 二级导航 -->
<div class="public-crumbs">
  	 <span><a href="${basePath }">首页</a></span>
  	 <span>></span>
  	 <span><a href="${basePath }/art/grzpz">个人文化展</a></span>
  	 <span>></span>
  	 <span><a href="${basePath }/art/grzpzlist?id=${artist.artistid}">${artist.artistname }</a></span>
  	 <span>></span>
  	 <span>${art.arttitle }</span>
</div>
<!-- 二级导航结束 -->

<!--主体开始-->
<div class="art-main clearfix">
    <div class="detail">
        <div class="ystd-left">
            <img src="${basePath }/${art.artpic2}" alt="" style="width:335px;">
        </div>
        <div class="ystd-right">
            <h1>${art.arttitle }</h1>
            <p>${art.artnote }</p>
            <div class="kcjd">
                <div class="ys-label">标签：
                	<c:forEach items="${tags }" var="tag" varStatus="s">
		              	<span>${tag.name }</span>
	            	</c:forEach>
                    <p>关键字：<c:forEach items="${keys }" var="key" varStatus="s">${key.name }<c:if test="${!s.last }">&nbsp;</c:if></c:forEach></p>
                </div>

                <div class="kksj">
                    <span>发布时间：<fmt:formatDate value="${art.artcrttime}" pattern="yyyy/MM/dd HH:mm:ss"/></span>
                    <div class="scgx">
                        <a href="" class="dianzan wxldisabled"  reftyp="2016101400000054" refid="${art.artid }" id="collection" ><span></span>点赞</a>
                		<a href="" class="shoucang wxldisabled" reftyp="2016101400000054" refid="${art.artid }" id="good"><span></span>收藏</a>
                        <div class="fenx">
                            <span>分享:</span>
                            <a href="" class="fxweibo wxldisabled"></a>
                            <a href="" class="fxweix wxldisabled"></a>
                            <a href="" class="fxqq wxldisabled"></a>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </div>

    <div class="zuopin-detail">
    	<c:if test="${art.artcontent != null }">
        <h1>作品详情</h1>
        
        <!-- 详情图 -->
        <!-- <img src="${basePath }/${art.artpic2}" style="width:1138px;height:359px;"> -->
        <!-- 详情图结束 -->
        
        <!-- 详情 -->
        <div>${art.artcontent }</div>
        <!-- 详情结束 -->
        </c:if>
        
        <!-- 艺术家介绍 -->
        <c:if test="${art.artartistdesc != null }">
        <hr style="color: #dbdbdb;margin: 15px 0 0px 0;">
        <div class="guanyu-ysj">
            <h2><span></span>关于艺术家</h2>
            <div>${art.artartistdesc }</div>
        </div>
        </c:if>
        <!-- 艺术家介绍结束 -->
        
        <!-- 图片资源 -->
        <c:if test="${picList != null }">
        <hr style="color: #dbdbdb;margin: 15px 0 0px 0;">
        <div class="guanyu-ysj clearfix">
            <h2><span></span>其他作品</h2>
            <div class="running running-1">
            <ul class="smart">
            	<c:forEach items="${picList }" var="pic" varStatus="s">
	                <li><img src="${basePath }/${pic.enturl}" alt="${pic.entname}" style="width:266px;height:210px;"></li>
            	</c:forEach>
            </ul>
            </div>
            <div class="arrow-left arrow-box prev-a" >
                <b class="left"><i class="left-arrow1"></i><i class="left-arrow2"></i></b>
            </div>
            <div class="arrow-right arrow-box next-a">
                <b class="right"><i class="right-arrow1"></i><i class="right-arrow2"></i></b>
            </div>
        </div>
        </c:if>
        <!-- 图片资源结束 -->
        
        <!-- 视频资源 -->
        <c:if test="${videoList != null }">
        <hr>
        <div class="guanyu-ysj clearfix">
            <h2><span></span>视频欣赏</h2>
            <div class="running running-2">
            <ul class="smart">
            
                <c:forEach items="${videoList }" var="video" varStatus="s">
	                <li>
	                    <a href="javascript:void(0);" class="js__p_vedio_start"><img style="width:266px; height:162px;" src="${basePath }/${video.deourl}" addr="${basePath }/${video.enturl}" alt="${video.entname }"></a>
	                    <p>${video.entname }</p>
	                </li>
            	</c:forEach>
            	
            </ul>
            </div>
            <div class="arrow-left arrow-box prev-b" >
                <b class="left"><i class="left-arrow1"></i><i class="left-arrow2"></i></b>
            </div>
            <div class="arrow-right arrow-box next-b">
                <b class="right"><i class="right-arrow1"></i><i class="right-arrow2"></i></b>
            </div>
        </div>
        </c:if>
        <!-- 视频资源结束 -->
        
        <!-- 音频资源 -->
        <c:if test="${audioList != null }">
        <hr>
        <div class="guanyu-ysj">
            <h2><span></span>音频欣赏</h2>
        </div>
        <div class="teacher-ypxs-list">
        	<c:forEach items="${audioList }" var="audio" varStatus="s">
            <ul>
                <li>
                    <span class="yy-logo"></span>${audio.entname }
                </li>
                <li>
                    <span class="yy-time">时长：${audio.enttimes }</span>
                </li>
                <li>
                    <a href="">点击试听<span class="yy-bofang"></span></a>
                </li>
            </ul>
            </c:forEach>
        </div>
        </c:if>
        <!-- 音频资源结束 -->
        
    </div>
    
    <!-- 评论开始 -->
    <div class="zuopin-detail pinglun">
        <h2><span class="kcdianping"></span>评论</h2>
        <div class="dianp-list">
            <ul id="pinglunNRC" reftyp="2016101400000054" refid="${art.artid }">
            
                <li>
                    <div class="xylogo"> <span></span> </div>
                    <div class="dp-content">
                        <div class="xyname"> </div>
                        <div class="pl-neirong"> </div>
                        <div class="pl-shijian">
                            <span>10小时</span>
                            <div class="huifu">
                                <a href=""> <span class="xingxi"></span>回复 </a>
                                <a href=""> <span class="xx"></span>25 </a>
                            </div>
                        </div>
                    </div>
                </li>
                
            </ul>
        </div>
        <div class="input-dianp">
            <textarea placeholder="对此课程进行点评。。。"></textarea>
            <div class="dianp-xuanx clearfix">
            	<!-- 
                <a href="" class="wxldisabled"><span></span>表情</a>
                <label class="wxldisabled"><input type="checkbox">同步到新浪微博</label>
                 -->
                <a href="" class="submit-dianp a-button">评  论</a>
            </div>
        </div>
    </div>
    <!-- 评论结束 -->
    
</div>

</div>
<!--主体结束-->

<!--底部开始-->
<%@include file="/pages/comm/szfooter.jsp"%>
<!--底部结束-->

<!--弹出层开始-->
<div class="popup js__vedio_popup js__slide_top clearfix"> <a href="#" class="p_close js__p_close" title="关闭"></a>
  <div class="p_content">
    <p>广东省文化馆与红星美凯龙联手举行艺术大展</p>
  </div>
  <div class="p_main">
    <embed src='http://player.youku.com/player.php/sid/XMTc2NjU2MzA2OA==/v.swf' allowFullScreen='true' quality='high' width='480' height='400' align='middle' allowScriptAccess='always' type='application/x-shockwave-flash'></embed>
  </div>
</div>
<!--弹出层结束-->

<!-- core public JavaScript --> 
<script src="${basePath }/static/assets/js/public/sly.js"></script> 
<script src="${basePath }/static/assets/js/art/soloArt.js"></script> 
</body>
</html>