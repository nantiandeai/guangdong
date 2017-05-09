<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<%@include file="/pages/comm/agdhead.jsp"%>
<%@include file="/pages/comm/comm_video.jsp"%>
<title>广东省文化馆-培训驿站-在线点播</title>
<link href="${basePath }/static/assets/css/train/vod.css" rel="stylesheet">
<script type="text/javascript">
/**分页插件*/
function loadContent(page, rows){
	var page = page || 1;//分页加载第一页 
	var rows = rows || 9;//每页10条记录
	var _url = '${basePath}/agdpxyz/vodlist?p=1';
	
	//将要带进去查询的条件设置好
	var artid = $('.categoryChange > .row:eq(0) > .adrList > span.active > a').attr("artid");
	
	var drscopttime = $('.categoryChange > .row:eq(1) > .adrList > span.active > a').attr("time");
	
	var title = $('#title').val();
	
	var _param = {};
	
	if(artid){ _param.drscarttyp = artid; }
	if(drscopttime){ _param.drscopttime = drscopttime; }
	if(title){ _param.drsctitle = title; } 
	
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
			genPagging('paging', page, rows, total, loadContent);
		}
	});
}


 
/** 加载数据 */

function showList(dataRows){
	if (!dataRows.length){
		$('#connet').siblings(".public-no-message").show();
	}else{
		$('#connet').siblings(".public-no-message").hide();
	}
	var list = '';
	
	for(var i=0; i<dataRows.length; i++){
		/* var intro = dataRows[i].drscintro;
		var _intro = intro.substring(0,40); */
		list += '<li>';
		list += '	<a href="${basePath }/agdpxyz/vodinfo?drscid='+dataRows[i].drscid+'">';
		list += '    	<div class="img"> ';
		if (dataRows[i].drscpic != "") {
			list += '			<img src="'+WhgComm.getImgServerAddr()+''+dataRows[i].drscpic+'" width="380" height="240"/>';
		}else{
			list += '			<img src="${basePath }/static/assets/img/img_demo/activi-img1.jpg" width="380" height="240"/>';
		}
		list += '			<div class="mask"></div>';
		list += '		</div>';
		list += '    </a>'; 
		list += '    <div class="detail">';
		list += '    	<h2>'+dataRows[i].drsctitle+'</h2>';
		list += '    	<p>'+(dataRows[i].drscintro || '&nbsp;')+'</p>';
		list += '	 </div>'
		list += '</li>';
	}
	
	$('#connet').html(list);
}
/** 给导航栏添加样式和点击事件*/
$(function(){
	$('.categoryChange > .row:eq(0) > .adrList > span:eq(0)').addClass('active').siblings('span').removeClass('active');
	$('.categoryChange > .row:eq(1) > .adrList > span:eq(0)').addClass('active').siblings('span').removeClass('active');
	
	//类型
	$('.categoryChange > .row:eq(0) > .adrList > span > a').on('click', function(e){
		e.preventDefault();
		$(this).parent('span').addClass('active').siblings('span').removeClass('active');
		loadContent();
	});
	//排序
	$('.categoryChange > .row:eq(1) > .adrList > span > a').on('click', function(e){
		e.preventDefault();
		$(this).parent('span').addClass('active').siblings('span').removeClass('active');
		loadContent();
	});

	//搜索的点击
	$("#btn_sub").on('click', function(e){
		e.preventDefault();
		loadContent();
	});
	 
	//搜索回车事件
	$("body").keydown(function() {
		var isFocus = $("#title").is(":focus");
		var _val = $("#title").val();
		if (event.keyCode == "13" && isFocus) {
			//加载默认数据...
			loadContent();
		}
	 }); 

	 //加载数据
	 loadContent();
	 
	 
});

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
            	<%--<li><a href="${basePath }/agdpxyz/index">培训驿站</a></li>--%>
            	<li><a href="${basePath }/agdpxyz/notice">培训公告</a></li>
                <li><a href="${basePath }/agdpxyz/news">培训资讯</a></li>
                <li><a href="${basePath }/agdpxyz/teacher">培训师资</a></li>
                <li><a href="${basePath }/agdpxyz/trainlist">在线报名</a></li>
                <li class="active last"><a href="${basePath }/agdpxyz/vod">在线点播</a></li>
                <%-- <li class="last"><a href="${basePath }/agdpxyz/resources">培训资源库</a></li> --%>
            </ul>
        </div>
    </div>
</div>
<!--公共主头部开始-END-->

<!--主体开始-->
<div class="main-info-bg main-info-no-padding main-info-bgColorW">
    <div class="main-info-container">
        <div class="categoryChange">
        
            <div class="row clearfix">
                <div class="title">类型</div>
                <div class="adrList">
                    <span class="item active"><a href="javascript:void(0)">全部</a></span>
                  <c:if test="${not empty ystypes }"> 
                    <c:forEach items="${ystypes }" var="item">
                		<span class="item active"><a href="javascript:void(0)" artid="${item.id}">${item.name}</a></span>
                	</c:forEach>
                  </c:if>  
                    
                </div>
            </div>
            
            <div class="row clearfix">
            
                <div class="title">排序</div>
                <div class="adrList adrList1">
                    <span class="item active"><a href="javascript:void(0)" time="">智能排序</a></span>
                    <!-- <span class="item"><a href="javascript:void(0)">即将开始</a></span>
                    <span class="item"><a href="javascript:void(0)">即将结束</a></span> -->
                    <span class="item"><a href="javascript:void(0)" time="1">最新发布</a></span>
                </div>
                
                <div class="searchCont">
                    <input placeholder="搜点什么..." id="title">
                    <button type="submit" id="btn_sub"></button>
                </div>
                
            </div>
        </div>
        <div class="active-list container">
            <div class="con">
            
            	<div class="public-no-message none"></div>
            <!-- 内容展示 -->
            
                <ul class="clearfix" id="connet">
                </ul>
                <!-- 分页 -->
                <div class="green-black" id="paging"> </div>
            </div>
        </div>
    </div>
</div>
<!--主体结束-->

<!--公共主底部开始-->
<%@include file="/pages/comm/agdfooter.jsp"%>
<!--公共主底部结束-END-->
</body>
</html>