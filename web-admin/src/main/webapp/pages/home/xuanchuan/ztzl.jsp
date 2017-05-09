<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath(); request.setAttribute("basePath", basePath); %>
<% String idx = request.getParameter("idx"); %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
    <link href="${basePath }/static/assets/css/public/reset.css" rel="stylesheet">
    <link href="${basePath }/static/assets/css/publicity/publicityindex.css" rel="stylesheet">
    <!--[if lt IE 9] >
    <script src="${basePath }/static/assets/js/plugins/ie/IE9.js"></script>
    <! [endif]]-->
    <!-- core public JavaScript -->
	<script src="${basePath }/static/assets/js/public/jquery-1.11.0.min.js"></script>
	<script src="${basePath }/static/assets/js/public/jquery-migrate-1.0.0.js"></script>
	<script src="${basePath }/static/assets/js/public/comm.js"></script>
	<script>var columnIdx = '<%=idx%>' == 'null' ? '0' : '<%=idx%>';</script>
	<script src="${basePath }/pages/home/xuanchuan/ztzl.js"></script>
</head>
<body>

<!-- 公共头部开始 -->
<%@include file="/pages/comm/szheader.jsp"%>
<!-- 公共头部结束 -->

<!--广告开始-->
<div class="page-ad" style="background:url(${basePath }/static/assets/img/publicity/pub-banner.jpg) no-repeat 50% 50%"></div>
<!--广告结束-->

<!--主体开始-->
<div class="page-main clearfix">
	<div class="page-content clearfix">
       	<!-- 左边 -->
        <div class="page-left">
            <div class="title title1"><span>宣传栏</span></div>
            <ul>
            	<li url="${basePath }/xuanchuan/whdt"><div class="title title2"><span><i></i>文化动态</span></div></li>
                <li url="${basePath }/xuanchuan/gwgk"><div class="title title3"><span><i></i>馆务概况</span></div></li>
                <li url="${basePath }/xuanchuan/bszn"><div class="title title4"><span><i></i>办事指南</span></div></li>
                <li url="${basePath }/xuanchuan/ztzl"  class="active"><div class="title title5"><span><i></i>专题专栏</span></div></li>
                <li url="${basePath }/xuanchuan/lxwm"><div class="title title6"><span><i></i>联系我们</span></div></li>
            </ul>
        </div>
        <!-- 左边 END -->
            
        <!-- 主体 -->
        <div class="page-right">
            <div class="page1">
            	<!-- 子栏目 -->
                <div class="page1-title">
                    <ul> <c:forEach items="${childList }" var="child" varStatus="s">
	        	   		<li<c:if test="${s.first }"> class="active"</c:if> colid="${child.colid }">${child.coltitle }</li>     	
		            </c:forEach>
                    </ul>
                </div>
                    <div class="page-con page1-con on">
                        <div class="top">
							<ul class="clearfix">
								<c:forEach items="${cfg}" var="cfgs" varStatus="s">
									<c:if test="${s.index < 3}">
										<li>
											<a href="javascript:void(0)">
												<div class="img" style="background:url(${basePath }/${cfgs.cfgshowpic})"></div>
											</a>
											<a href="javascript:void(0)">
												<div class="detail">
													<h4>${cfgs.cfgshowtitle}</h4>
													<p>${cfgs.cfgshowintroduce}</p>
												</div>
											</a> 
											<!-- <a href="javascript:void(0)" class="more">查看更多></a> -->
										</li>
									</c:if>
								</c:forEach>
							</ul>
							</div>
                        <div class="bottom">
                            <div class="list">
                                <ul> </ul>
                            </div>
                        </div>
                    </div>

                </div>
                
    	<!-- 主体 -END-->
	</div>
</div>
<!--主体结束-->

<!--底部开始-->
<%@include file="/pages/comm/szfooter.jsp"%>
<!--底部结束--> 

</body>
</html>