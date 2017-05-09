<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${childList.clnftltle}</title>
    <%@include file="/pages/comm/comm_head.jsp"%>
    <%@include file="/pages/comm/comm_video.jsp"%>
    <link href="${basePath }/static/assets/css/publicity/publicityindex.css" rel="stylesheet">
</head>
<body>

<!-- 公共头部开始 -->
<%@include file="/pages/comm/szheader.jsp"%>
<!-- 公共头部结束 -->

<!--广告开始-->
<div class="library-ad" style="background:url(${basePath }/static/assets/img/publicity/pub-banner.jpg) no-repeat 50% 50%;"></div>
<!--广告结束-->

<div class="public-crumbs">
    <span><a href="${basePath}">首页</a></span>
    <span>></span><span>馆务公开</span>
    <span>></span><span><a href="${basePath }/xuanchuan/${column}?idx=${columnIdx}">${columnName}</a></span>
    <span>></span><span>${childList.clnftltle}</span>
</div>
<script type="text/javascript">

</script>
<!--主体开始-->
<div class="page-content clearfix">
    <div class="page-content-left">
        <h1>${childList.clnftltle}</h1>
        <div class="detail">时间：<span class="time"><fmt:formatDate value="${childList.clnfcrttime}" pattern="yyyy-MM-dd"/></span><span class="zuozhe">来源：${childList.clnfsource}</span>作者：<span class="count">${childList.clnfauthor}</span></div>
        <div class="text">
         <c:if test="${not empty childList.clnfbigpic and childList.clnfbigpic ne ''  }">
            <img src="${basePath }/${childList.clnfbigpic}">
         </c:if>
            <p>${childList.clnfdetail}</p>
        </div>
        
        <div class="download">
        <c:forEach items="${loadwhe }" var="loadwhes" varStatus="s">
            <div class="dow">
                <h2><i class="h-line"></i>${loadwhes.entname}：</h2>
                <div class="img-list">
                    <ul>
                        <li>
                            <div class="img">
                             <c:if test="${not empty loadwhes.enturl and loadwhes.enturl ne ''  }">
                                <img src="${basePath }/${loadwhes.enturl}">
                             </c:if> 
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
         </c:forEach>
        </div>
       <c:forEach items="${loadent }" var="loadwhes" varStatus="s">
        <c:if test="${not empty loadwhes and loadwhes ne '' and s.first }">
         <div class="download" id="shiping">
            <div class="video">
                <h2><i class="h-line"></i>视频播放：</h2>
                <div class="video-list">
                    <ul class="clearfix">
                     <c:forEach items="${loadent }" var="loadwhes" varStatus="s">
                        <li>
                            <a href="javascript:void(0)">
                                <div class="img" style="background-image: url(${basePath }/${loadwhes.deourl})">
                                    <div class="mask">
                                    <a href="javascript:void(0)" class="opt_videoshow" vsurl="${loadwhes.enturl}">
                                        <img src="${basePath }/static/assets/img/publicity/vedioBg.png">
                                    </div>
                                </div>
                            </a>
                            <div class="detail">
                                <a href="javascript:void(0)">${loadwhes.entname}</a>
                            </div>
                        </li>
                      </c:forEach> 
                    </ul>
                </div>
            </div>
        </div>
        </c:if>
        </c:forEach>
        <c:forEach items="${loadclazz }" var="loadwhes" varStatus="s">
        <c:if test="${not empty loadwhes and loadwhes ne '' and s.first }">
        <div class="download" id="yinping">
            <div class="dow">
                <h2><i class="h-line"></i>音频播放：</h2>
                <div class="dow-list">
                    <ul>
                      <c:forEach items="${loadclazz }" var="loadwhes" varStatus="s">
                        <li>
                            <div class="title clearfix">
                                <a href="javascript:void(0)"><h3>${loadwhes.entname}</h3></a>
                                <a class="yinyue opt_videoshow" vsurl="${loadwhes.enturl}" href="javascript:void(0)"><i></i>点击播放</a>
                            </div>
                        </li>
                       </c:forEach>  
                    </ul>
                </div>
            </div>
        </div>
        </c:if>
        </c:forEach>
        
        <c:forEach items="${loadlist}" var="loadlists" varStatus="s">
           <c:if test="${not empty loadlists and loadlists ne '' and s.first}">
        <div class="download" id="xiazai">
            <div class="dow">
                <h2><i class="h-line"></i>附件下载：</h2>
                <div class="dow-list">
                    <ul>
                    <c:forEach items="${loadlist}" var="loadlists" varStatus="s">
                        <li>
                            <div class="title clearfix">
                                <a href="javascript:void(0)"><h3>${loadlists.upname}</h3></a><a class="download" href="./whtools/downFile?filePath=${loadlists.uplink}">点击下载</a>
                            </div>
                        </li>
                    </c:forEach> 
                    </ul>
                </div>
            </div>
        </div>
        	</c:if>
       </c:forEach>
    </div>
    <div class="scgx clearfix">
        <div class="zuozhe">
            <a href="javascript:void(0);" class="shoucang" reftyp="2016102800000001" refid="${childList.clnfid }" id="collection"><span></span>收藏</a>
            <a href="javascript:void(0);" class="dianzan" reftyp="2016102800000001" refid="${childList.clnfid }" id="good"><span></span>点赞</a>
        </div>
        <div class="fenx">
            <span>分享:</span>
            <a href="javascript:void(0);" class="fxweibo"></a>
            <a href="javascript:void(0);" class="fxweix"></a>
            <a href="javascript:void(0);" class="fxqq"></a>
        </div>
    </div>
</div>
<!--主体结束-->

<!--底部开始-->
<%@include file="/pages/comm/szfooter.jsp"%>
<!--底部结束--> 

 <script src="${basePath }/static/assets/js/libraryService/libraryList.js"></script>
 <script src="${basePath }/static/assets/js/public/jquery-1.11.0.min.js"></script>
 <script src="${basePath }/static/assets/js/public/jquery-migrate-1.0.0.js"></script>
</body>
</html>