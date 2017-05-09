<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>广东省文化馆-场馆预定</title>
<%@include file="/pages/comm/comm_head.jsp"%>
<link href="${basePath }/static/assets/css/venue/venueList.css" rel="stylesheet">

<script src="${basePath }/static/assets/js/venue/venueList.js"></script> 
<script type="text/javascript">
/**分页插件*/
function loadArtContent(page, rows){
	var page = page || 1;//分页加载第一页 
	var rows = rows || 10;//每页10条记录
	var _url = getFullUrl('/venue/venlist?p=1');
	
	//将要带进去查询的条件设置好
	var areaid = $('.categoryChange > .row:eq(0) > .adrList > span.active > a').attr("areaid");
	var typid = $('.categoryChange > .row:eq(1) > .adrList > span.active > a').attr("typid");
	var state = $('.categoryChange > .type-row > .categoryName.active > a').attr("state");
	var title = $('#title').val();
	var _param = {};
	if(areaid){ _param.venarea = areaid; }
	if(typid){ _param.ventype = typid; }
	if(state){ _param.vencanbk = state; }
	if(title){ _param.venname = title; }
	
	//请求服务器数据
	$.ajax({
		type: "POST",
		url: _url+'&page='+page+'&rows='+rows,
		data: _param,
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
			genPagging('paging', page, rows, total, loadArtContent);
		}
	});
}
 
/** 加载数据 */
function showList(dataRows){
	var rows = {};
	
	var list = '';
	for(var i=0; i<dataRows.length; i++){
		var rows = {};
		rows = dataRows[i].venscope.split(",");
		list += '<li class="item clearfix">';
		list += '	<div class="img">';
		list += '    	<a href="${basePath}/venue/order/'+dataRows[i].venid+'"><img src="${basePath }/'+dataRows[i].venpic+'" width="280" height="190"></a>';
		list += '	</div>';
		list += '	<div class="info">';
		list += '       <h2><a href="#">'+dataRows[i].venname+'</a></h2>'; 
		list += '   	<div class="info-main">';
		list += '    		<div class="row-1">';
		list += '    			<p class="type">';
		for (var j = 0; j < rows.length; j++) {
		list += '					<span class="d">'+rows[j]+'</span>'
		}
		list += '				</p>'
		list += '    	 		<p class="adr">地址 :<span>'+(dataRows[i].venaddr || '')+'</span><a href="#">[查看地图]</a></p>';
		list += '        		<p class="cate">类型 :<span>'+(WHTYP.sys_Whtyp('', dataRows[i].ventype) || '')+'</span></p>';
		list += '    			<p class="desc">描述 :<span>'+(dataRows[i].venintro || '') +'</span></p>';
		list += '			</div>';
		list += '		</div>';
		list += '	</div>';
		list += '</li>';
	}
	$('#connet').html(list);
}
/** 给导航栏添加样式和点击事件*/
$(function(){
	//init conditions class
	$('.categoryChange > .row:eq(0) > .adrList > span:eq(0)').addClass('active').siblings('span').removeClass('active');
	$('.categoryChange > .row:eq(1) > .adrList > span:eq(0)').addClass('active').siblings('span').removeClass('active');
	$('.categoryChange > .type-row > .categoryName:eq(0)').addClass('active').siblings('span').removeClass('active');
	
	//init type event
	$('.categoryChange > .row:eq(0) > .adrList > span > a').on('click', function(e){
		e.preventDefault();
		$(this).parent('span').addClass('active').siblings('span').removeClass('active');
		loadArtContent();
	});
	$('.categoryChange > .row:eq(1) > .adrList > span > a').on('click', function(e){
		e.preventDefault();
		$(this).parent('span').addClass('active').siblings('span').removeClass('active');
		loadArtContent();
	});
	$('.categoryChange > .type-row > .categoryName > a').on('click', function(e){
		e.preventDefault();
		$(this).parent('span').addClass('active').siblings('span').removeClass('active');
		loadArtContent();
	});
	$("#btn_sub").on('click', function(e){
		e.preventDefault();
		loadArtContent();
	});
	 
	//回车事件
	$("body").keydown(function() {
		var isFocus = $("#title").is(":focus");
		var _val = $("#title").val();
		if (event.keyCode == "13" && isFocus) {
			//加载默认数据...
			loadArtContent();
		}
	 });

	 //load Data
	 loadArtContent();
});
</script>
</head>
<body>

<!-- 公共头部开始 -->
<%@include file="/pages/comm/szheader.jsp"%>
<!-- 公共头部结束 -->

<!--广告开始-->
<div class="venue-ad" style="background:url(${basePath }/static/assets/img/venue/bg.jpg) no-repeat 50% 50% #ddd">
	<c:if test="${not empty venlist}">
		<img src="${basePath }/${venlist.cfgadvpic}" height="250" width="100%"/>
	</c:if>
</div>
<!--广告结束-->

<!--主体开始-->
<div class="venue-main">
	<div class="categoryChange">
            
            <div class="row clearfix">
            	<div class="title">区域</div>
                <div class="adrList">
                	<span class="item"><a href="javascript:void(0)" areaid="">全部</a></span>
                	<c:forEach items="${venArea }" var="item">
                		<span class="item active"><a href="javascript:void(0)" areaid="${item.typid}">${item.typname}</a></span>
                	</c:forEach>
                </div>
            </div>
            
            <div class="row border-none clearfix">
            	<div class="title">类型</div>
            	<div class="adrList">
                	<span class="item active"><a href="javascript:void(0)" typid="">不限</a></span>
                   	<c:forEach items="${ventype }" var="item">
                		<span class="item active"><a href="javascript:void(0)" typid="${item.typid}">${item.typname}</a></span>
                	</c:forEach>
                </div>
            </div>
            
            <div class="type-row clearfix">
            	<span class="categoryName"><a href="javascript:void(0)" state="">全部</a></span>
                <span class="categoryName last"><a href="javascript:void(0)" state="1">可预定</a></span>
                <!-- 搜索  -->
                <div class="searchCont">
                	<input placeholder="搜点什么..." id="title">
                    <button type="submit" id="btn_sub"></button>
                </div>
            </div>
        </div>
</div>
<div class="main clearfix" >
    <div class="venue-main-left">
    
    	<!-- 内容展示区域 -->
    	<ul class="group" id="connet">
    	
    		<!-- <li class="item clearfix">
            	<div class="err-msg">
                	<i></i>
                    <p>
                        <span>抱歉，没有找到符合条件的结果</span><br>
                       	 您可以选择其它条件重新尝试
                    </p>
                </div>
            </li> -->
    		
        </ul>
       
       <!-- 分页 --> 
        <div class="green-black" id="paging"></div> 
        
    </div>
</div>
<!--主体结束-->

<!--底部开始-->
<%@include file="/pages/comm/szfooter.jsp"%>
<!--底部结束-->

<!-- core public JavaScript --> 

</body>
</html>
