<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>广东省文化馆-个人文化展</title>
<%@include file="/pages/comm/comm_head.jsp"%>
<link href="${basePath }/static/assets/css/art/ezine.css" rel="stylesheet">
<script src="${basePath }/static/assets/js/public/sly.js"></script>
<script type="text/javascript">
$(function(){
	//点击电子杂志动态更新页码信息
 	$('.dzzz').on('click.wjq', function(e){
 		e.preventDefault();
 		_loadData($(this).attr('zzid'));
 	})
 	_doSly();
 	//首次进入展示第一本杂志的页码信息
 	/* 
 	list += '<li style="background-image: url('+getFullUrl(data[i].pagepic)+')">';
	list += '</li>'; */
});

//通过ajax动态通过ID获取数据
function _loadData(zzid){
	var _url = getFullUrl('/art/showPage');
	//请求服务器数据
	$.ajax({
		type: "POST",
		url: _url,
		data : {pagemageid : zzid},
		success: function(data){
			var list = '';
	   		for(var i=0; i<data.length; i++){
	   			
	   			list += '<li style="background-image: url('+getFullUrl(data[i].pagepic)+')">';
	   			list += '</li>';
	   		}
	   		$('.page > ul').html(list);
			_doSly();
		}
	});
	
}

//滚动页码的插件
function _doSly(){	
	 $('.page').sly({
        itemNav: "smart",
        easing: "easeOutExpo",
        prevPage: ".prev",
        nextPage: ".next",
        pagesBar: ".pages",
        horizontal: 1,
        touchDragging: 1,
        dragContent: 1,
        scrollBy: 1
    });
    $('.shuji').sly({
        itemNav: "smart",
        easing: "easeOutExpo",
        prevPage: ".pre-left",
        nextPage: ".pre-right",
        horizontal: 1,
        touchDragging: 1,
        dragContent: 1,
        scrollBy: 1
    });
    
    $(".mulu").click(function(){
        $(".pages").slideDown();
        $(".pages").click(function(){
            $(this).slideUp();
        })
    });
}
</script>
</head>
<body>

<!-- 公共头部开始 -->
<%@include file="/pages/comm/szheader.jsp"%>
<!-- 公共头部结束 -->

<!--广告开始-->
<div class="art-ad" style="background:url(${basePath }/static/assets/img/art/banner.jpg) no-repeat 50% 50%">
<c:if test="${not empty listAdv}"> 
	<img src="${basePath }/${listAdv.cfgadvpic }" width="1263px" height="250px"/> 
</c:if> 
</div>
<!--广告结束-->

<div class="public-crumbs">
    <span><a href="${basePath }">首页</a></span><span>></span>
    <span><a href="${basePath }/art/jpwhz">艺术广场</a></span><span>></span>
    <span><a href="${basePath }/art/dzzz">电子杂志</a></span><span>></span>
    <span>电子杂志详情页</span>
    <!-- <div class="search-border">
        <input placeholder="输入关键字搜索...">
    </div> -->
</div>

<!--主体开始-->
<div class="art-main clearfix">
    <div class="art-main-list">
        <div class="page">
            <ul>
            	<c:forEach items="${mageidx }" var="item">
            		<li style="background-image: url(${basePath}/${item.pagepic })"></li>
            	</c:forEach>
            </ul>
            <div class="pre">
                <div href="javascript:void(0)" class="prev"><i></i></div>
                <div href="javascript:void(0)" class="next"><i></i></div>
            </div>
        </div>
        <ul class="pages">
        </ul>
        <div class="enter">
            <a href="javascript:void(0)" class="mulu active">目录</a>
            <!-- <a href="javascript:void(0)" class="fenxiang">分享</a> -->
        </div>
    </div>

    <div class="art-main-list1 clearfix">
    
        <div class="pre-left">
            <i></i>
        </div>
        
        <div class="shuji clearfix">
            <ul>
                <c:forEach items="${mage }" var="item">
	                <li class="active" style="background-image: url(${basePath }/${item.magepic })">
	                    <a href="javascript:void(0)" class="dzzz" zzid="${item.mageid }">
	                        <div class="detail">
	                            <h2>${item.magetitle }</h2>
	                            <span>${item.magenum }</span>
	                        </div>
	                    </a>
	                </li>
	            </c:forEach>
            </ul>
        </div>
        
        <div class="pre-right">
            <i></i>
        </div>
    </div>
</div>
<!--主体结束-->

<!--底部开始-->
<%@include file="/pages/comm/szfooter.jsp"%>
<!--底部结束--> 
<!-- core public JavaScript -->


</body>
</html>
