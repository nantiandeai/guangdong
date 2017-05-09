<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<%@include file="/pages/comm/agdhead.jsp"%>
<title>广东省文化馆-非遗中心-传承人列表</title>
<link href="${basePath }/static/assets/css/intangibleheritage/mingluliebiao.css" rel="stylesheet">
<script src="${basePath }/static/assets/js/intangibleheritage/mingluliebiao.js"></script>
<script src="${basePath }/static/assets/js/train/teach.js"></script>

<script type="text/javascript">
var _param={};

//获取查找的条件
function setParam(param){
	if (!$.isPlainObject) return;
	//合并并覆盖 数据
	$.extend(_param,param);
}

//处理选择项样式
function _active(active){
	if (!active || active.size()==0) return;
	active.addClass("active");
	//siblings 遍历同级其它类型
	active.siblings(".active").removeClass("active");
}

//处理 名录类bie查找
function setmlType(active,type) {
	setParam({suortype:type});
	_active($(active).parent());
	loadData();
}

//处理 区域查找
function setactArea(active,type) {
	setParam({suorqy:type});
	_active($(active).parent());
	loadData();
}

//处理 批次查找
function setpici(active,type) {
	setParam({suoritem:type});
	_active($(active).parent());
	loadData();
}

//处理 级别查找
function settLevel(active,type) {
	setParam({suorlevel:type});
	_active($(active).parent());
	loadData();
}

//处理 标题搜索
function setactTitle(){
	var val=$("#title").val();
	setParam({suorname:val});
	loadData();
}

/**点更多进入 数据加载 */
function loadData(page, rows){
	_param.page = page || 1;
	_param.rows = rows || 9;

	$.ajax({
		type: 'post',
		url: '${basePath }/agdfyzg/dataloadsuccessor',
		data: _param,		
		success: function(data){
			//
			if (!data) return;
			var rows = data.rows;
			var total = data.total;
			showData(rows);
			
			//加载分页工具栏
			genPagging('artPagging', _param.page, _param.rows, total, loadData);
			$("#testParams").val(JSON.stringify(_param));
		}
	});
}

/** 展示数据 */
function showData(data){
	$('.DataUrl').html('');
	$(".DataUrl").parent().find("div.public-no-message").remove();
	if(data.length > 0){
		for(var i=0 ;i<data.length; i++){
			var html = '';
			var row = data[i];
	html+='	   	<li>                                                                                                                       		 '
    html+='   		<div class="img">                                                                                                            '
    html+='       		<a href="${basePath }/agdfyzg/chuanchengreninfo?suorid='+row.suorid+'"><img src="${imgServerAddr}/'+row.suorpic+'" width="136" height="136" onerror="showDefaultIMG(this, \'${basePath }/static/assets/img/img_demo/1.jpg\')"></a>  '
    html+='      	</div>                                                                                                                       '
    html+='       	<div class="info">                                                                                                           '
    html+='       		<a href="${basePath }/agdfyzg/chuanchengreninfo?suorid='+row.suorid+'">                                                  '
    html+='       			<h2>'+row.suorname +'</h2>                                                                                           '
    html+='           		<p>                                                                                                                  '
    html+='               		项目名称 :<span>'+(row.mlproshortitel||"-") +'</span>                                                                      '
    html+='           		</p>                                                                                                                 '
    html+='           		<p>                                                                                                                  '
    html+='           			简介 :<span>'+row.suorjs +'</span> 																				 '
    html+='           		</p>                                                                                                                 '
    html+='          	</a>                                                                                                                	 '
    html+='         <div class="arrow"></div>                                                                                            		 '
    html+='       </div>                                                                                                                   		 '
    html+='   	</li>                                                                                       					              	 '
        	$('.DataUrl').append(html);                                                                                            
		}                                                                                                                                  
	}else{                                                                                                                                 
		$(".DataUrl").parent().append("<div class='public-no-message'></div>");                                                                                                                                   
	}                                                                                                                                      
}

$(function(){
	/** 回车事件 */
	$("body").keydown(function() {
		if (event.keyCode == "13"){
			 setactTitle();
		}
	});
	
	var _p = $("#testParams").val();
	
	if (_p){
		var pm = $.parseJSON(_p);
		
		//console.info("test=>>",pm)
		_param = pm;
		
		loadData(pm.page, pm.rows);
		recParamItem(pm);
		return;
	}
	
	//$.extend(_param,{actvtype:"${actvtype}"});
	loadData();
})

function recParamItem(prm){
	for (var k in prm){
		if (k=='page' || k=='rows') continue;
		
		if (k=='suorname'){
			$('#title').val(prm[k]);
			continue;
		}
		
		var v = prm[k];
		$("[kl='"+k+"'][vl='"+v+"']").parents("span").siblings(".active").removeClass("active");
		$("[kl='"+k+"'][vl='"+v+"']").parents("span").addClass("active");
	}
	
	
}

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
				<li><a href="${basePath }/agdfyzg/index">非遗首页</a></li>
				<li><a href="${basePath }/agdfyzg/notice">最新公告</a></li>
				<li><a href="${basePath }/agdfyzg/news">新闻动态</a></li>
				<li><a href="${basePath }/agdfyzg/minglu">名录项目</a></li>
				<li class="active"><a href="${basePath }/agdfyzg/chuanchengren">传承人</a></li>
				<li><a href="${basePath }/agdfyzg/falvwenjian">法律文件</a></li>
				<li class="last"><a href="${basePath }/agdfyzg/shenbao">申报指南</a></li>
				<%-- <li class="last"><a href="${basePath }/agdfyzg/xxx">非遗资源</a></li> --%>
			</ul>
		</div>
	</div>
</div>
<!--公共主头部结束-END-->

<!--主体开始-->
<!--列表 搜索条件 start -->
<div class="main-info-bg main-info-no-padding main-info-bgColorW">
	<div class="main-info-container">
		<div class="categoryChange">
	        <div class="row clearfix">
	           <div class="title">区域</div>
	           <div class="adrList">
	                <span class="item active"><a href="javascript:void(0)" kl="suorqy" vl="" onClick="setactArea(this,'')">全部</a>
	                </span>
	                <c:forEach items="${qrtype}" var="item">
		               	<span class="item"><a href="javascript:void(0)" kl="suorqy" vl="${item.id}"  onClick="setactArea(this,${item.id})">${item.name}</a></span>
		            </c:forEach>
	            </div>
	        </div>
	        
	        <div class="row clearfix">
	            <div class="title">级别</div>
	            <div class="adrList">
	                <span class="item active"><a href="javascript:void(0)" kl="suorlevel" vl="" onClick="settLevel(this,'')">全部</a>
	                </span>
	                <c:forEach items="${leveltype}" var="item">
		               	<span class="item"><a href="javascript:void(0)" kl="suorlevel" vl="${item.id}"  onClick="settLevel(this,${item.id})">${item.name}</a></span>
		            </c:forEach>
	            </div>
	        </div>
	        
	        <div class="row clearfix">
	            <div class="title">批次</div>
	            <div class="adrList">
	                <span class="item active"><a href="javascript:void(0)" kl="suoritem" vl="" onClick="setpici(this,'')">全部</a>
	                </span>
	                <c:forEach items="${pici}" var="item">
		               	<span class="item"><a href="javascript:void(0)" kl="suoritem" vl="${item.id}"  onClick="setpici(this,${item.id})">${item.name}</a></span>
		            </c:forEach>
	            </div>
	        </div>
	        
	        <div class="row clearfix">
	            <div class="title">类别</div>
	            <div class="adrList">
	                <span class="item active"><a href="javascript:void(0)" kl="suortype" vl="" onClick="setmlType(this,'')">全部</a>
	                </span>
	                <c:forEach items="${ttype}" var="item">
		               	<span class="item"><a href="javascript:void(0)" kl="suortype" vl="${item.id}" onClick="setmlType(this,${item.id})">${item.name}</a></span>
		            </c:forEach>
	            </div>
	        </div>
	        <div class="row clearfix">
	            <div class="title"></div>
	            <div class="adrList">
	                <div class="searchCont">
	                    <input placeholder="搜点什么..." id="title">
	                    <button type="submit" onClick="setactTitle()"></button>
	                    <input type="hidden" id="testParams" />
	                </div>
	            </div>
	        </div>
		</div>
		<!--列表 搜索条件 end-->
		
	    <div class="teacherList">
	       <!--数据显示 start -->
	    	<ul class="DataUrl"> </ul>
	    	<!--数据显示 end -->
	    	
	    	<!--分页开始 -->
			<div class="green-black" id="artPagging"></div>
		 	<!--分页结束-->
	    </div>
	    
	   
	 	
		</div>
</div>
<!--主体结束-->

<!--公共主底部开始-->
<%@include file="/pages/comm/agdfooter.jsp"%>
<!--公共主底部结束-END-->
</body>
</html>