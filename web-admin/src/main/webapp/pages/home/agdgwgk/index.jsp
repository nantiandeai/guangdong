<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<%@include file="/pages/comm/agdhead.jsp"%>
<title>广东省文化馆-馆务公开-省馆介绍</title>
<link href="${basePath }/static/assets/css/museum/museum.css" rel="stylesheet">
</head>

<!-- 公共头部开始 -->
<%@include file="/pages/comm/agdtopsmall.jsp"%>
<!-- 公共头部结束-END -->

<!-- 二级栏目 -->
<div id="header-fix">
    <div class="header-nav-bg">
        <div class="header-nav">
            <div class="logo-small">
                <a href="${basePath }/home"><img src="${basePath }/static/assets/img/public/logoSmall.png"></a>
            </div>
            <ul>
                <li class="active"><a href="${basePath }/agdgwgk/index">省馆介绍</a></li>
                <li><a href="${basePath }/agdgwgk/jigou">组织机构</a></li>
                <li><a href="${basePath }/agdgwgk/fagui">政策法规</a></li>
                <li><a href="${basePath }/agdgwgk/zhinan">业务指南</a></li>
                <li><a href="${basePath }/agdgwgk/tuandui">馆办团队</a></li>
                <li class="last"><a href="${basePath }/agdgwgk/fankui">意见反馈</a></li>
            </ul>
        </div>
    </div>
</div>
<!-- 二级栏目-END -->

<!--主体开始-->
<div class="main-info-bg main-info-no-padding main-info-bgColorW">
    <div class="main-info-container">
    	<!-- 广东省文化馆的主图 -->
        <div class="page-banner">
        	<c:choose>
        		<c:when test="${not empty wh_zx_colinfo and not empty wh_zx_colinfo.clnfbigpic}">
        			<img src="${imgServerAddr }/${wh_zx_colinfo.clnfbigpic }" width="1200" height="455">
        		</c:when>
        		<c:otherwise>
		            <img src="${basePath }/static/assets/img/img_demo/shengguanjieshao-banner.jpg">
        		</c:otherwise>
        	</c:choose>
        </div>
        <!-- 广东省文化馆的主图-END -->
        
        <div class="page-detail">
        
        	<c:choose>
        		<c:when test="${not empty wh_zx_colinfo and not empty wh_zx_colinfo.clnftltle}">
        			<h1>${wh_zx_colinfo.clnftltle }</h1>
        		</c:when>
        		<c:otherwise>
		            <h1>广东省文化馆简介</h1>
        		</c:otherwise>
        	</c:choose>
            
            <!-- 广东省文化馆的详情介绍 -->
            <c:choose>
        		<c:when test="${not empty wh_zx_colinfo and not empty wh_zx_colinfo.clnfdetail}">
        			<p>${wh_zx_colinfo.clnfdetail}</p>
        		</c:when>
        		<c:otherwise>
		            <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;广东省文化馆（原名广东省群众艺术馆）成立于1956年，是广东省人民政府设立的专门从事辅导、指导、研究群众文化艺术活动和培训业务骨干，为广大人民群众提供公共文化服务的公益一类事业单位。2005年12月经省编办批准，加挂广东省非物质文化遗产保护中心牌子。核定事业编制50名。<br><br>
		            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;主要职能包括：组织开展具有导向性、示范性的群众文化艺术活动；辅导农村、社区、企业等开展群众文化活动，辅导、培训辖区内文化馆、站业余干部及文艺活动业务骨干；组织、指导、研究群众性文艺创作活动；组织开展群众文艺理论研究，搜集、整理、保护民族民间文化艺术遗产；执行全省非物质文化遗产保护的规划、计划和工作规范，组织实施全省非物质文化遗产的普查、认定、申报、保护和交流传播工作；指导基层文化馆工作及群众业余文艺团队建设，为基层文化馆提供文化资源和文化服务；推进文化馆数字化、信息化平台建设，建立信息资源共享机制，开展数字文化信息服务；开展对外文化交流；完成上级交办的其他工作等。馆内设活动策划部、培训辅导部、创作调研部、信息技术部、团队管理部、省非物质文化遗产保护中心办公室、行政办公室等七个部室。<br><br>
		
		                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;构建现代公共文化服务体系，是十八届三中全会关于文化领域改革的一项重要任务。积极投身现代公共文化服务体系建设是广东省文化馆义不容辞的责任。在中央大力推动文化事业大繁荣、大发展的新形势下，广东省文化馆将在省委、省政府和省文化厅的领导下，紧紧围绕党和政府的中心工作，坚持“面向现代化、面向世界、面向未来”，“民族的、科学的、大众的”社会主义文化发展方向，继续完善和创新现代公共文化服务，对内提高素质，对外树立形象，进一步发挥好省馆的龙头示范作用，为我省经济发展和社会进步，为团结全省广大群众齐心协力实现“伟大中国梦”提供一份精神动力和智力支持。
		            </p>
        		</c:otherwise>
        	</c:choose>
        	<!-- 广东省文化馆的详情介绍 -END-->
        </div>
    </div>
</div>
<!--主体结束-->

<!--公共主底部开始-->
<%@include file="/pages/comm/agdfooter.jsp"%>
<!--公共主底部结束-END-->
</body>
</html>