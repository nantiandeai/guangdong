<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% String idx = request.getParameter("idx"); %>
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
    <title>广东省文化馆-个人文化展</title>
    <%@include file="/pages/comm/comm_head.jsp"%>
    <%@include file="/pages/comm/comm_video.jsp"%>
    <link href="${basePath }/static/assets/css/libraryService/libraryList.css" rel="stylesheet">
	<script src="${basePath }/static/assets/js/libraryService/libraryList.js"></script>
</head>
<body>

<!-- 公共头部开始 -->
<%@include file="/pages/comm/szheader.jsp"%>
<!-- 公共头部结束 -->

<!--广告开始-->
<div class="page-ad" style="background:url(${basePath }/static/assets/img/publicity/pub-banner.jpg) no-repeat 50% 50%"></div>
<!--广告结束-->
<div class="public-crumbs">
		 <span><a href="#">首页</a></span><span>></span><span><a href="${basePath}/zhiyuan/zyz">志愿服务机构</a></span><span>></span><span>${zyinfo.clnftltle}</span>
</div>
<!--主体开始-->
<div class="page-content clearfix">
    <div class="page-content-left">
        <h1>${zyinfo.clnftltle}</h1>
        <div class="detail">时间：<span class="time"> <fmt:formatDate value='${zyinfo.clnfcrttime}' pattern='yyyy-MM-dd'/></span><span class="zuozhe">作者：${zyinfo.clnfauthor}</span>浏览量：<span class="count">${zyinfo.clnfbrowse}</span></div>
        <div class="text">
          <c:if test="${not empty zyinfo.clnfbigpic and  zyinfo.clnfbigpic ne  ''}">
            <img src="${basePath}/${zyinfo.clnfbigpic}">
          </c:if>  
          ${zyinfo.clnfdetail}
        </div>
        <div class="scgx clearfix">
            <div class="zuozhe">文：${zyinfo.clnfsource}</div>
            <div class="fenx">
                <span>分享:</span>
                <a href="javascript:void(0);" class="fxweibo"></a>
                <a href="javascript:void(0);" class="fxweix"></a>
                <a href="javascript:void(0);" class="fxqq"></a>
            </div>
        </div>
        <div class="fanye">
           <c:if test="${not empty bid and  bid ne  ''}">
            <div class="pageup">上一篇：<a href="${basePath }/zhiyuan/zyzinfo?clnfid=${bid}">${btitle }</a></div>
           </c:if>
             <c:if test="${not empty lid and  lid ne  ''}">
            <div class="pagedown">下一篇：<a href="${basePath }/zhiyuan/zyzinfo?clnfid=${lid}">${ltitle}</a></div>
             </c:if>
        </div>
    </div>
    <div class="page-content-right">
        <div class="hot">
            <div class="head"><i class="h-line"></i>热门动态</div>
            <div class="content">
                <ul>
                <c:forEach items="${rmdt }" var="hots" varStatus="s">
        	         <c:if test="${s.index < 5}">
                    <li class="clearfix">
                        <a href="${basePath }/zhiyuan/zyzinfo?clnfid=${hots.cfgshowid}">
                            <div class="img" style="background-image: url(${basePath }/${hots.cfgshowpic})"></div>
                            <div class="detail">
                                <h2>${hots.cfgshowtitle}</h2>
                                <p>时间：<fmt:formatDate value='${hots.cfgshowtime}' pattern='yyyy-MM-dd'/></p>
                            </div>
                        </a>
                    </li>
                    </c:if>
                    </c:forEach>
                </ul>
            </div>
            <div class="lookmore">
                <a href="http://dgvolunteer.dg.gov.cn/fw_zxdt?orgid=70">查看更多></a>
            </div>
        </div>
        <div class="train">
            <div class="head"><i class="h-line"></i>培训动态</div>
            <div class="content">
                <ul>
                 <c:forEach items="${pxdt }" var="hots" varStatus="s">
        	         <c:if test="${s.index < 5}">
                    <li class="clearfix">
                        <a href="${basePath }/zhiyuan/zyzinfo?clnfid=${hots.cfgshowid}">
                            <div class="detail">
                                <h2><i></i>${hots.cfgshowtitle}</h2>
                                <p>时间：<fmt:formatDate value='${hots.cfgshowtime}' pattern='yyyy-MM-dd'/></p>
                            </div>
                        </a>
                    </li>
                    </c:if>
                    </c:forEach>
                </ul>
            </div>
            <div class="lookmore">
                <a href="http://dgvolunteer.dg.gov.cn/fw_pxlb?type=1&orgid=70">查看更多></a>
            </div>
        </div>
    </div>
</div>
<!--主体结束-->

<!--底部开始-->
<%@include file="/pages/comm/szfooter.jsp"%>
<!--底部结束--> 
</body>
</html>