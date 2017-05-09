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
<link href="${basePath }/static/assets/css/art/soloList.css" rel="stylesheet">
<link href="${basePath }/static/assets/css/art/pershow.css" rel="stylesheet">
<script>

/**
 * 加载艺术广场主内容区的元素:精品文化展/个人作品展/艺术团队
 */
function loadArtContent(page, rows){
	var page = page || 1;//分页加载第一页 
	var rows = rows || 12;//每页10条记录
	
	//URL
	var _url = getFullUrl('/art/srchgrzpzlist?id=${artist.artistid}');
	
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
	    _html += '    <div class="img">';
	    _html += '        <a href="${basePath}/art/grzpzdesc?id='+dataRows[i].artid+'" style="background-image:url('+getFullUrl(dataRows[i].artpic)+')">';
	    _html += '            <div class="mask"></div>';
	    _html += '        </a>';
	    _html += '    </div>';
	    _html += '    <p class="pageCount">'+dataRows[0].artshorttitle+'</p>';
	    _html += '</li>';
	}
	$('#artContent').html(_html);
}

/** 页面装载完成后的事件处理 */
$(function(){
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
	<span><a href="${basePath }">首页</a></span><span>></span><span><a href="${basePath }/art/grzpz">个人文化展</a></span><span>></span><span>${artist.artistname }</span>
</div>
<!-- 二级导航结束 -->



<!--主体开始-->
<div class="art-main clearfix">
    <div class="personal-detail">
        <div class="personal-left">
            <img src="${basePath }/${artist.artistpic }" alt="" style="width:285px;height:344px;">
        </div>
        <div class="personal-right">
            <div class="personal">
                <span class="per-name">${artist.artistname }</span>
                <span class="per-job">${artist.artisttype }</span>
                <span class="job-size">作品数：<label>${artistList.size() }</label>件</span>
            </div>
            <div class="per-ir">
                <p>${artist.artistdesc }</p>
            </div>
        </div>
    </div>

    <div class="per-zuopin-detail">
        <h3>全部作品 （<label for="">${artistList.size() }</label>）</h3>
        <ul class="per-zuopin-list clearfix" id="artContent"></ul>
        
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