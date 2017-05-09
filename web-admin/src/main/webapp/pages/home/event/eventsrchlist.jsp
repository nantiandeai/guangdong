<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath(); request.setAttribute("basePath", basePath); %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="keywords" content="">
    <meta name="author" content="">
    <title>广东省文化馆-品牌活动</title>
    <link href="${basePath }/static/assets/css/public/reset.css" rel="stylesheet">
    <link href="${basePath }/static/assets/css/event/eventindex.css" rel="stylesheet">
    <script src="${basePath }/static/assets/js/public/jquery-1.11.0.min.js"></script>
	<script src="${basePath }/static/assets/js/public/jquery-migrate-1.0.0.js"></script>
	<script src="${basePath }/static/admin/js/common.js"></script>
	<script src="${basePath }/static/assets/js/public/comm.js"></script>
	<script type="text/javascript">
	/**
	 * 展示数据
	 * @param dataList
	 * @returns
	 */
	 $(function(){
		 loadArtContent(1,9);
	 });
	 /**分页插件*/
		function loadArtContent(page, rows){
			var page = page || 1;//分页加载第一页 
			var rows = rows || 10;//每页10条记录
			var _url = getFullUrl('/event/pagelist?p=1');
			//请求服务器数据
			$.ajax({
				type: "POST",
				url: _url+'&page='+page+'&rows='+rows,
				success: function(data){
					var dataRows = data.rows;
					var total = data.total || 0;
					
					//加载页面数据
					if(total > 0){
						showList(dataRows);
					}else{
						showList([]);
					}
				//	alert(JSON.stringify(data));
					//加载分页工具栏
					genPagging('pagelist', page, rows, total, loadArtContent);
				}
			});
		}
	function showList(dataRows){
		var list = '';
		for(var i=0; i<dataRows.length; i++){
			list += '<li>';
			list += '	<div class="img">';
			list += '    	<div class="img-mask" href="" style="background-image:url()">';
 			list += '			<a href="'+getFullUrl('/event/pplist?braid='+dataRows[i].braid)+'">';
			list += '				<img src="'+getFullUrl(dataRows[i].brapic)+'"	/>';
			list += '        	</a>'; 
			list += '       </div>';
			list += '    </div>';
			list += '    <div class="detail">';
			list += '    	 <p class="author">'+dataRows[i].bratitle+'</p>';
			list += '        <p class="pageCount">'+dataRows[i].braintroduce+'</p>';
			list += '    </div>';
			list += '</li>';
		}
		$('#connet').html(list);
	}
	
	</script>
</head>
<body>
<!-- 公共头部开始 -->
<%@include file="/pages/comm/szheader.jsp"%>
<!-- 公共头部结束 -->

<!--广告开始-->
<div class="art-ad" style="background:url(${basePath }/static/assets/img/event/event-banner.jpg) no-repeat 50% 50%">
 <c:if test="${not empty archlistAdv}"> 
	<%-- <c:forEach items="${archlistAdv}" var="item" varStatus="s"> --%>
		<img src="${basePath }/${archlistAdv.cfgadvpic }" width="1263px" height="250px"/> 
	<%-- </c:forEach> --%>
 </c:if> 
</div>
<!--广告结束-->

<div class="public-crumbs">
    <span><a href="${basePath }/">首页</a></span><span>></span><span><a href="${basePath }/event/index">活动预约</a></span><span>></span><span>活动预约列表</span>
</div>
<!--主体开始-->
<div class="event-main clearfix">
    <div>
        <div class="event">
            <ul id="connet" class="eventlist clearfix">
            	<div class="img">
                	<div class="img-mask" href="javavoid(0)" style="background-image:url(${basePath }/static/assets/img/event/appt-1.jpg)"></div>
                </div>
            </ul>
        </div>
    </div>
    
	<!-- 分页 -->    
    <div id="pagelist" class="green-black"></div>
</div>
<!--主体结束-->
<!--底部开始-->
<%@include file="/pages/comm/szfooter.jsp"%>
<!--底部结束--> 
<!-- core public JavaScript -->

</body>
</html>
