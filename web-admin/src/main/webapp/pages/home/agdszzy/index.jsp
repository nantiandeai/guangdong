<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
    <%@include file="/pages/comm/agdhead.jsp"%>
<title>广东省文化馆-数字资源</title>
<link href="${basePath }/static/assets/css/resource/resourceCatalog.css" rel="stylesheet">
</head>
<body>
<!-- 头部开始 -->
<%@include file="/pages/comm/agdtopsmall.jsp"%>
<!-- 头部结束-END -->

<!--主体开始-->
<div id="content">
    <div class="relogo">
        <img src="${basePath }/static/assets/img/resource/logo.png" alt="数字资源" title="数字资源">
    </div>
    <div class="logo-list">
        <ul>
            <li class="li1 clearfix">
                <div class="list list1">
                    <a href="http://183.63.187.41/2016zyys/lncj.html" target="_blank">
                        <div class="l-logo logo1">
                            <i>
                            	<img src="${basePath}/static/assets/img/resource/1.png">
                            </i>
                            <span>岭南春节习俗</span>
                        </div>
                    </a>
                </div>
                <div class="list list2">
                    <a href="http://219.135.173.196:18080" target="_blank">
                        <div class="l-logo logo2">
                            <i>
                            	<img src="${basePath}/static/assets/img/resource/2.png">
                            </i>
                            <span>广东稀有剧种</span>
                        </div>
                    </a>
                </div>
                <div class="list list3">
                    <a href="http://183.63.187.41/2016zyys/lnwd.html" target="_blank">
                        <div class="l-logo logo3">
                            <i>
                            	<img src="${basePath}/static/assets/img/resource/3.png">
                            </i>
                            <span>岭南传统舞蹈</span>
                        </div>
                    </a>
                </div>
            </li>
            <li class="li2 clearfix">
                <div class="list list1">
                    <a href="http://183.63.187.57:8999" target="_blank">
                        <div class="l-logo logo4">
                            <i>
                            	<img src="${basePath}/static/assets/img/resource/4.png">
                            </i>
                            <span>广东雷州石狗</span>
                        </div>
                    </a>
                </div>
                <div class="list list2">
                    <a href="http://www.kuke.com " target="_blank">
                        <div class="l-logo logo5">
                            <i>
                            	<img src="${basePath}/static/assets/img/resource/5.png">
                            </i>
                            <span>库客数字音乐图书馆</span>
                        </div>
                    </a>
                </div>
                <div class="list list3">
                    <a href="http://www.gdwhmap.com/fwz" target="_blank">
                        <div class="l-logo logo6">
                            <i>
                            	<img src="${basePath}/static/assets/img/resource/6.png">
                            </i>
                            <span>非遗地图</span>
                        </div>
                    </a>
                </div>
            </li>
            <%--<li class="li3">
                <div class="list list1">
                    <a href="#">
                        <div class="l-logo logo7">
                            <i></i>
                            <span>曲艺</span>
                        </div>
                    </a>
                </div>
                <div class="list list2">
                    <a href="#">
                        <div class="l-logo logo8">
                            <i></i>
                            <span>名俗</span>
                        </div>
                    </a>
                </div>
                <div class="list list3">
                    <a href="#">
                        <div class="l-logo logo9">
                            <i></i>
                            <span>培训视频</span>
                        </div>
                    </a>
                </div>
            </li>--%>
        </ul>
    </div>
</div>
<!--主体结束-->

<!--公共主底部开始-->
<%@include file="/pages/comm/agdfooter.jsp"%>
<!--公共主底部结束-END-->
</body>
</html>