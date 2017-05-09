<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<%@include file="/pages/comm/agdhead.jsp"%>
<title>广东省文化馆-馆务公开-组织机构</title>
<link href="${basePath }/static/assets/css/museum/museum.css" rel="stylesheet">
<script src="${basePath }/static/assets/js/museum/museum.js"></script>
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
                <li><a href="${basePath }/agdgwgk/index">省馆介绍</a></li>
                <li class="active"><a href="${basePath }/agdgwgk/jigou">组织机构</a></li>
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
    <div class="banner" style="background-image: url(${basePath }/static/assets/img/museum/zuzhijigou.jpg)">
        <div class="personnel-list">
            <ul>
            	<c:choose>
	        		<c:when test="${wh_zx_colinfo_p_list != null && fn:length(wh_zx_colinfo_p_list) > 0}">
	        			<c:forEach items="${wh_zx_colinfo_p_list }" var="wh_zx_colinfo" varStatus="s">
	        				<li class="clearfix">
			                    <div class="con-right">
			                        <div class="detail">
			                            <h2>${wh_zx_colinfo.clnftltle }</h2>
			                            <p>${wh_zx_colinfo.clnfintroduce }</p>
			                        </div>
			                    </div>
			                </li>
	        			</c:forEach>
	        		</c:when>
	        		<c:otherwise>
						<li class="none"></li>
			     <%--        <li class="clearfix">
		                    <div class="con-left">
		                        <div class="img">
		                            <img src="${basePath }/static/assets/img/img_demo/zuzhijigou1.jpg">
		                        </div>
		                    </div>
		                    <div class="con-right">
		                        <div class="detail">
		                            <h2>王惠君</h2>
		                            <p>广东省文化馆-馆长  主持全面工作，分管信息技术部。</p>
		                        </div>
		                    </div>
		                </li>
		                 --%>
	        		</c:otherwise>
	        	</c:choose>
            </ul>
            <ul class="pages">
            	<c:choose>
            		<c:when test="${wh_zx_colinfo_p_list != null && fn:length(wh_zx_colinfo_p_list) > 0}">
	        			<c:forEach items="${wh_zx_colinfo_p_list }" var="wh_zx_colinfo" varStatus="s">
	        				<li <c:if test="${s.first }"> class="active"</c:if> ><i></i></li>
	        			</c:forEach>
	        		</c:when>
	        		<c:otherwise>
		                <li class="active"><i></i></li>
		                <li><i></i></li>
		                <li><i></i></li>
	        		</c:otherwise>
	        	</c:choose>
            </ul>
        </div>
    </div>
</div>

<div class="public-information-container">
	<h1>广东省文化馆部门职能</h1>
	<ul>
		<c:choose>
          	<c:when test="${wh_zx_colinfo_org_list != null && fn:length(wh_zx_colinfo_org_list) > 0}">
       			<c:forEach items="${wh_zx_colinfo_org_list }" var="wh_zx_colinfo" varStatus="s">
       				<li>
			        	<i></i>
			        	<h2>${wh_zx_colinfo.clnftltle }：</h2>
			            <p>${wh_zx_colinfo.clnfintroduce }</p>
			        </li>
       			</c:forEach>
       		</c:when>
       		<c:otherwise>
                <li>
		        	<i></i>
		        	<h2>行政办公室：</h2>
		            <p>协助馆领导做好日常行政事务和管理工作。做好相关文件的上传下达；负责文秘、文印、公章管理、人事、综合档案等工作；负责馆财务和固定资产管理、政府采购工作；做好全馆卫生、计生、安全保卫、老干等管理工作；督促落实各项规章制度并做好相关制度的修订、完善工作；做好大型群众文化活动的组织、协调和后勤管理工作；协助做好党务、工会和团支部工作。</p>
		        </li>
		        <li>
		        	<i></i>
		        	<h2>培训辅导部：</h2>
		            <p>负责基层文艺骨干和群众文艺爱好者的培训辅导工作。承担上级交办的群众文化培训和辅导任务；组织开展全省性的群众文化艺术辅导和培训活动；组织策划面向社会公众的各类免费艺术培训班；为基层文化馆站培养群众文化业务骨干提供必要的帮助；负责省馆艺术考级工作的组织、实施。</p>
		        </li>
		        <li>
		        	<i></i>
		        	<h2>活动策划部：</h2>
		            <p>负责各项群众文化活动的组织、策划、实施工作。完成上级下达的群众文化活动任务；组织开展各类生动活泼、为群众喜闻乐见的文化活动；为基层文化馆站开展群众文化活动提供指导和帮助；为全省群众文化活动项目和人才的交流搭建良好平台；开展全省群众文化活动调查研究。</p>
		        </li>
		        <li>
		        	<i></i>
		        	<h2>信息技术部：</h2>
		            <p>负责群文活动信息收集、管理及本馆网站的建设、维护工作。负责各项群众文化活动的摄录工作；收集、整理、发布全省群众文化活动信息；设计、制作各项活动的背景板、宣传册及相关宣传资料；全馆办公设备、网络的管理和维护。</p>
		        </li>
		        <li>
		        	<i></i>
		        	<h2>创作调研部：</h2>
		            <p>负责全省群众文艺创作和调研工作。对全省群众文艺发展情况进行调研；为基层群众文艺研究和创作提供指导和帮助；负责广东省文化馆特聘创作员队伍的管理；负责全省美术、书法、摄影作品联展的组织和实施；完成本馆的创作计划和创作任务；负责《广东社会文化》的编辑和出版工作。</p>
		        </li>
		        <li>
		        	<i></i>
		        	<h2>广东省非物质文化遗产保护中心办公室：</h2>
		            <p>负责省非物质文化遗产保护中心的日常工作。执行全省非物质文化遗产保护的规划、计划和工作规范；组织实施和指导开展全省非物质文化遗产的普查、认定、申报、保护和交流传播工作。</p>
		        </li>
		        <li>
		        	<i></i>
		        	<h2>团队管理部：</h2>
		            <p>负责广东省文化志愿者队伍管理工作。承担广东省文化志愿者总队日常管理工作；组织文化志愿者开展各种文化志愿服务活动；为有需要的社会群众文化团体提供必要的辅导和帮助。</p>
		        </li>
       		</c:otherwise>
       	</c:choose>
    </ul>
</div>
<hr class="strongHr">
<!--主体结束-->


<!--公共主底部开始-->
<%@include file="/pages/comm/agdfooter.jsp"%>
<!--公共主底部结束-END-->
</body>
</html>