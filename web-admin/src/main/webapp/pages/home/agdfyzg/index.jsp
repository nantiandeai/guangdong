<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<%@include file="/pages/comm/agdhead.jsp"%>
<title>广东省文化馆-非遗首页</title>
<link href="${basePath }/static/assets/css/intangibleheritage/mingluliebiao.css" rel="stylesheet">
<script src="${basePath }/static/assets/js/intangibleheritage/feiyizhongxin.js"></script>
<script type="text/javascript">
/**数据加载*/
function load(type) {
	$(".module2-con").empty();
	$(".module2-con").append('<div class="module2-content">'
		+'	<ul class="clearfix Dataurl"> </ul>'
		+'	</div>                             '
		+'	<div class="scrollbar">            '
		+'	<div class="handle"></div>         '
		+'	</div>');

	$.ajax({
		type: 'post',
		url: '${basePath }/agdfyzg/getminglu',
		data: {type:type},		
		success: function(data){
			if (!data) return;
			showData(data);

			if ($('.module2-content .Dataurl li').length){
				$('.module2-content').sly({
					itemNav: "smart",
					easing: "easeOutExpo",
					horizontal: 1,
					touchDragging: 1,
					dragContent: 1,
					scrollBy: 1,
					scrollBar:".scrollbar",
					cycleBy: 'items',
					cycleInterval: 1000,
					speed: 400
				})
			}
		}
	});
}

/**显示数据*/
function showData(data) {
	$(".Dataurl").empty();
	if(data.length >0){
		for (var i = 0; i < data.length; i++) {
			var row = data[i];
			var _html = '';
			var _a = '${basePath }/agdfyzg/mingluinfo?mlproid='+row.mlproid;
			var _img = '${imgServerAddr }/'+row.mlprosmpic;
		_html+='<li>  ';
	    _html+='    <a href="'+_a+'"> ';
	    _html+='      <div class="img"><img src="'+_img+'" width="380" height="240"/></div> ';
	    _html+='      <div class="detail">  ';
	    _html+='        <h2>'+row.mlproshortitel+'</h2>  ';
	    _html+='      </div> ';
	    _html+='    </a>';
        _html+='</li>';
        	$(".Dataurl").append(_html);
		}
		

	}
}  

/**页面加载*/
$(function(){
	$("li.minglu[typid]").bind('click',function(){
		load($(this).attr("typid"));
	})
	if ($("li.minglu[typid].active:eq(0)").length){
		$("li.minglu[typid].active:eq(0)").click();
	} else {
		$("li.minglu[typid]:eq(0)").click();
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
				<li class="active"><a href="${basePath }/agdfyzg/index">非遗首页</a></li>
				<li><a href="${basePath }/agdfyzg/notice">最新公告</a></li>
				<li><a href="${basePath }/agdfyzg/news">新闻动态</a></li>
				<li><a href="${basePath }/agdfyzg/minglu">名录项目</a></li>
				<li><a href="${basePath }/agdfyzg/chuanchengren">传承人</a></li>
				<li><a href="${basePath }/agdfyzg/falvwenjian">法律文件</a></li>
				<li class="last"><a href="${basePath }/agdfyzg/shenbao">申报指南</a></li>
				<%-- <li class="last"><a href="${basePath }/agdfyzg/xxx">非遗资源</a></li> --%>
			</ul>
		</div>
	</div>
</div>
<!--公共主头部结束-END-->
 
<!--广告开始-->
<c:if test="${empty listAdv}">
	<div class="public_ad" style="background:url(${basePath }/static/assets/img/intangibleheritage/feiyibanner.jpg) no-repeat 50% 50%;"></div> 
</c:if>
<c:if test="${not empty listAdv}">
	<c:forEach items="${listAdv}" var="item" begin="0" end="0">
		<a href="${item.url !=null ?item.url:'javascript:void(0)'}"><div class="public_ad" style="background:url(${imgServerAddr}/${item.picture}) no-repeat 50% 50%;"></div></a>
	</c:forEach>
</c:if>
<!--广告结束--> 

<!--主体开始-->
<!--最新动态 start-->
<div class="main-info-bg main-info-bgColorW">
	<div class="main-info-container main-info-title">
    	<center>
     	 	<h2>
     	 		<span class="line-l"></span><span class="tt">最新动态</span><span class="line-r"></span>
     	 	</h2>
    	</center>
    	<div class="news-cont clearfix">
      		<div class="img">
		       	<ul class="basic">
					<c:if test="${advList != null && fn:length(advList) > 0}">
						<c:forEach items="${advList}" var="row" varStatus="s">
							<li>
								<div class="mask">
									<a href="${row.url}">${row.name }</a>
								</div>
								<a href="${row.url}"><img src="${imgServerAddr}${row.picture}" style="width: 480px; height: 370px;" onerror="showDefaultIMG(this, '${basePath }/static/assets/img/img_demo/1.jpg')"></a>
							</li>
						</c:forEach>
					</c:if>
		      		<%--<c:if test="${not empty listzx}">
			       		 <c:forEach items="${listzx}" var="item">
			               	<li>
			                    <div class="mask">
			                        <a href="${basePath}/agdfyzg/newsinfo?id=${item.cfgshowid}">${item.cfgshowtitle }</a>
			                    </div>
			                    <a href="${basePath}/agdfyzg/newsinfo?id=${item.cfgshowid}"><img src="${basePath }/${item.cfgshowpic }" onerror="showDefaultIMG(this, '${basePath }/static/assets/img/img_demo/1.jpg')"></a>
			             	</li>
			       		 </c:forEach>
		       		</c:if>--%>
		       		<c:if test="${empty advList || fn:length(advList) < 1}">
		       		 	<li>
		                      <div class="mask">
		                           <a href="javascript:void(0)">2016年度中国歌剧舞剧的院舞</a>
		                      </div>
		                      <a href="javascript:void(0)"><img src="${basePath}/assets/img/img_demo/activityindex1.jpg" onerror="showDefaultIMG(this, '${basePath }/static/assets/img/img_demo/1.jpg')"></a>
		               	</li>
		       		</c:if>
		        </ul> 
	        	<div class="btn-top btn-top-prev"></div>
	        	<div class="btn-top btn-top-next"></div>
        	</div>
      			
		    <div class="newsGroups">
		        <ul class="nav tab_list clearfix">
		           <li class="active">新闻动态</li>
		           <li>最新公告</li>
		        </ul>
		        <!-- 最新动态 start -->  
		        <div class="tab_content newsList active">
		        	<ul>
			        	<c:if test="${listnews != null}">
					        <c:forEach items="${listnews }" var="item">
					            <li><i></i><a href="${basePath}/agdfyzg/newsinfo?id=${item.clnfid}">${item.clnftltle}</a><em><fmt:formatDate value="${item.clnfcrttime}" pattern="yyyy-MM-dd"/> </em></li>
					        </c:forEach>
				    	</c:if>
		       		</ul>
		        	<div class="more"> <a href="${basePath}/agdfyzg/news" class="public-more">MORE</a> </div>
		    	</div>
		        
		        <!-- 最新公告 start -->
	            <div class="tab_content newsList">
	         	   <ul>
			          	<c:if test="${listgg != null}">
				          	<c:forEach items="${listgg }" var="item">
				           		 <li><i></i><a href="${basePath }/agdfyzg/noticeinfo?id=${item.clnfid}">${item.clnftltle}</a><em><fmt:formatDate value="${item.clnfcrttime}" pattern="yyyy-MM-dd"/> </em></li>
				          	</c:forEach>
			          	</c:if>
	          	   </ul>
		           <div class="more"> <a href="${basePath}/agdfyzg/notice" class="public-more">MORE</a> </div>
		        </div>
		        
			</div>
    	</div>
	</div>
</div>
<!--最新动态 end-->

<!--名录项目  start-->
<div class="main-info-bg">
	<div class="main-info-container main-info-title">
	    <center>
	    	<h2><span class="line-l"></span><span class="tt">名录项目</span><span class="line-r"></span></h2>
	    </center>
	    
	    <!--名录项目  类型 start-->
	    <div class="label clearfix">
	        <ul class="clearfix minglutype">
		        <c:if test="${ttype != null}">
			       	<c:forEach items="${ttype }" var="item"  varStatus="idx">
			            <li class="minglu ${item.id == '2017041200000002' ?'active':''}" typid=${item.id }>
			                <a href="javascript:void(0)"><span>${item.name }</span></a>
							<c:if test="${not idx.last}">
								<i></i>
							</c:if>
			            </li>
			        </c:forEach>
		         </c:if>
		         
		         <c:if test="${empty ttype || fn:length(ttype)<1}">
			         <li class="minglu" typid=''>
			             <a href="javascript:void(0)"><span>全部</span></a>
			             <i></i>
				    </li>
		         </c:if>
	        </ul>
	    </div>
    
    	<!--名录项目图片 start-->
	    <div class="module2-con clearfix">
	        <div class="module2-content">
	            <!-- 显示名录项目 开始-->
	            <ul class="clearfix Dataurl"> </ul>
	            <!-- 显示名录项目 结束-->
	        </div>
	        <div class="scrollbar">
	            <div class="handle"></div>
	        </div>
	    </div>
    	<center>
    		<div class="more"> <a href="${basePath }/agdfyzg/minglu" class="public-more">MORE</a></div>
    	</center>
	</div>
</div>
<!--名录项目  end-->

<!--广告开始-->
	<div class="public_ad" style="height:240px;background:url(${basePath }/static/assets/img/intangibleheritage/feiyiindex.jpg) no-repeat 50% 50%;"></div>
<!--广告结束-->

<!--主体结束--> 

<!--公共主底部开始-->
<%@include file="/pages/comm/agdfooter.jsp"%>
<!--公共主底部结束-END-->
</body>
</html>