<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
<%@include file="/pages/comm/comm_head.jsp"%>
<link href="${basePath }/static/assets/css/art/recommend.css" rel="stylesheet">
<script>

/**
 * 加载艺术广场主内容区的元素:精品文化展/个人作品展/艺术团队
 */
function loadArtContent(page, rows){
	var page = page || 1;//分页加载第一页 
	var rows = rows || 12;//每页10条记录
	
	//URL
	var _url = getFullUrl('/art/srchjpwhzlist?id=${exh.exhid}');
	
	//艺术分类
	var arttyp = '';
	$('#artTypeNav > li > a').each(function(idx){
		if($(this).parent('li').hasClass('active')){
			arttyp = $(this).attr('arttyp');
		}
	});
	
	//搜索条件
	var condition = $("#artSearch input").val();
	
	//url
	if(arttyp != ''){
		_url += '&arttyp='+arttyp;
	}
	if(condition != ''){
		_url += '&title='+encodeURIComponent(condition);
	}
	
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
			
			//加载分页工具栏
			genPagging('artPagging', page, rows, total, loadArtContent);
		}
	});
}

/**
 * 生成列表数据
 * @param dataList
 * @returns
 */
function showList(dataRows){
	var _html = '';
	for(var i=0; i<dataRows.length; i++){
    	_html += '<li>';
	    _html += '	<a href="${basePath}/art/jpwhzdesc?id='+dataRows[i].artid+'" style="background-image:url('+getFullUrl(dataRows[i].artpic)+')">';
	    _html += '    	<div class="mask">';
	    _html += '        	<p class="title">'+dataRows[i].artshorttitle+'</p>';
	    _html += '            <p class="time">时间：<span>'+dateFMT(dataRows[i].artstime)+'</span></p>';
	    _html += '        </div>';
	    _html += '    </a>';
	    _html += '</li>';
	}
	$('#artContent').html(_html);
}

/** 页面装载完成后的事件处理 */
$(function(){
	//艺术分类
	$('#artTypeNav > li > a').each(function(idx){
		$(this).unbind('click.sz').bind('click.sz', function(e){
			e.preventDefault();
			
			var _class = $(this).parent('li').attr('class');
			if(_class != 'active'){
				$(this).parents('ul').find('li').removeAttr('class');
				$(this).parent('li').attr('class', 'active');
				
				//加载默认数据...
				loadArtContent();
			}
			
		});
	});//艺术分类结束
	
	//搜索
	$('#artSearch').unbind('click.sz').bind('click.sz', function(e){
		e.preventDefault();
		var _tVal = $(this).val();
		
	});
	
	//回车事件
	$("body").keydown(function() {
		var isFocus = $("#artSearch input").is(":focus");
		var _val = $("#artSearch input").val();
        if (event.keyCode == "13" && isFocus && _val != '') {
        	//加载默认数据...
        	loadArtContent();
        }
    });
	
	//默认查询个人的作品
	loadArtContent();
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
    <span><a href="${basePath }">首页</a></span><span>></span><span><a href="${basePath }/art/jpwhz">精品文化展</a></span><span>></span><span>${exh.exhtitle }</span>
</div>
<!-- 二级导航结束 -->

<!--主体开始-->
<div class="art-main clearfix">
	<div class="left-bar">
    	<h2><span>分类</span></h2>
        <ul id="artTypeNav">
        	
        	<li><i></i><a href="#" arttyp="">全部</a></li>
        	<c:forEach items="${artList }" var="art" varStatus="s">
	              	<li<c:if test="${s.last }"> class="last"</c:if>><i></i><a href="#" arttyp="${art.typid }">${art.typname }</a></li>
            </c:forEach>
        	
        </ul>
        <div class="search-cont">
        	<h3>搜索</h3>
            <div class="search-border" id="artSearch">
            	<input placeholder="输入关键字搜索...">
            </div>
        </div>
    </div>
    <div class="art-right-main">
    	<!-- 内容区 -->
    	<h1 class="recommend-title">${exh.exhtitle }</h1>
    	<ul class="recommend clearfix" id="artContent"></ul>
        <!-- 内容区结束 -->
        
        <!-- 内容分页 -->
        <div class="green-black" id="artPagging"></div>
        <!-- 内容分页结束 -->
        
    </div>
</div>
<!--主体结束-->

<!--底部开始-->
<%@include file="/pages/comm/szfooter.jsp"%>
<!--底部结束--> 

 
</body>
</html>