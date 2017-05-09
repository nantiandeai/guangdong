<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<%@include file="/pages/comm/agdhead.jsp"%>
<title>广东省文化馆-品牌活动</title>
<link href="${basePath }/static/assets/css/activity/activitylist.css" rel="stylesheet">
<script src="${basePath }/static/assets/js/activity/topicsList.js"></script>
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

//处理 活动活动类型查找
function setactType(active,type) {
	var val=$("#title").val();
	setParam({bratitle:val});
	setParam({actvtype:type});
	_active($(active).parent());
	loadData();
}

//处理 区域查找
function setactArea(active,type) {
	var val=$("#title").val();
	setParam({bratitle:val});
	setParam({actvarea:type});
	_active($(active).parent());
	loadData();
}

//处理 艺术类型查找
function setactArt(active,type) {
	var val=$("#title").val();
	setParam({bratitle:val});
	setParam({actvarttyp:type});
	_active($(active).parent());
	loadData();
}

//处理 时间查找
function setactDate(active,type) {
	var val=$("#title").val();
	setParam({bratitle:val});
	setParam({datemark:type});
	_active($(active).parent());
	loadData();
}

//处理 标题搜索
function setactTitle(){
	var val=$("#title").val();
	setParam({bratitle:val});
	loadData();
}

/**点更多进入 数据加载 */
function loadData(page, rows){
	_param.page = page || 1;
	_param.size = rows || 10;

	$.ajax({
		type: 'post',
		url: '${basePath }/agdwhhd/srchbrandList',
		data: _param,		
		success: function(data){
		    if(data && data.success == "1"){
                var actlist = data.rows;
                var total = data.total;
                showData(actlist);

                //加载分页工具栏
                genPagging('artPagging', _param.page, _param.size, total, loadData);
            }
		}
	});
}

/** 展示数据 */
function showData(data){
	$('.DataUrl').html('');
	$(".DataUrl").parent().find("div").remove();
	if(data.length > 0){    
		for(var i=0 ;i<data.length; i++){
			var row = data[i];
			var pic = row.picture? "${imgServerAddr }"+WhgComm.getImg300_200(row.picture)+"" : "";
			var _html = '';
			 _html+='<li>                                                                                                 '
			 _html+='    <a href="${basePath }/agdwhhd/brandinfo?braid='+row.id+'" target="_blank">               '
			 _html+='        <div class="img" style="background-image: url('+pic+')">                                     '
			 _html+='            <div class="mask"></div>                                                                 '
			 _html+='        </div>                                                                                       '
			 _html+='    </a>                                                                                             '
			 _html+='    <div class="detail">                                                                             '
			 _html+='        <a href="${basePath }/agdwhhd/brandinfo?braid='+row.id+'"><h2>'+row.name+' </h2></a>                            '
			 _html+='        <p>'+row.introduction+'</p>                                                                  '
			 _html+='        <a class="enter-zt" href="${basePath }/agdwhhd/brandinfo?braid='+row.id+'">进入专题</a>'
			 _html+='    </div>                                                                                           '
			 _html+='</li>                                                                                               '
	   	        
			$(".DataUrl").append(_html);
			}
	}else{
		$(".DataUrl").parent().append("<div class='public-no-message'></div>");
	}
}

$(function(){
	/** 回车事件 */
	$("body").keydown(function() {
		if (event.keyCode == "13"){
			 //setactTitle();
		}
	});
	
	$.extend(_param,{actvtype:"${actvtype}"});
	loadData();
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
				<%--<li><a href="${basePath }/agdwhhd/index">文化活动</a></li>--%>
				<li><a href="${basePath }/agdwhhd/activitylist">活动预约</a></li>
				 <li><a href="${basePath }/agdwhhd/notice">活动公告</a></li>
				<li><a href="${basePath }/agdwhhd/news">活动资讯</a></li>
				<li class="last active"><a href="${basePath }/agdwhhd/brandlist">品牌活动</a></li>
			</ul>
		</div>
	</div>
</div>
<!--公共主头部结束-END-->

<!--主体开始-->
<div id="content">
    <div class="categoryChange">
        <%--<div class="row clearfix">
            <div class="row clearfix">
                <div class="title">区域</div>
                <div class="adrList">
                    <span class="item active"><a href="javascript:void(0)" onClick="setactArea(this,'')">全部</a></span>
                    <c:forEach items="${qrtype}" var="item">
                         <span class="item"><a href="javascript:void(0)" onClick="setactArea(this,${item.typid})">${item.typname }</a></span>
                    </c:forEach>
                </div>
            </div>
            <div class="row clearfix">
                <div class="title">活动类型</div>
                <div class="adrList">
                    <span class="item active"><a href="javascript:void(0)" onClick="setactType(this,'')">全部</a></span>
                    <c:forEach items="${acttype}" var="item" varStatus="status">
                       <span class="item"> <a href="javascript:void(0)" onClick="setactType(this,${item.typid})">${item.typname }</a></span>
                    </c:forEach>
                </div>
            </div>
            <div class="row clearfix">
                <div class="title">艺术类型</div>
                <div class="adrList">
                    <span class="item active"><a href="javascript:void(0)" onClick="setactArt(this,'')">全部</a></span>
                    <c:forEach items="${ystype}" var="item">
                        <span class="item"><a href="javascript:void(0)" onClick="setactArt(this,${item.typid})">${item.typname }</a></span>
                    </c:forEach>
                </div>
            </div>
            <div class="row clearfix">
                <div class="title">排序</div>
                <div class="adrList adrList1">
                    <span class="item active"><a href="javascript:void(0)" onClick="setactDate(this,'')">智能排序</a></span>
                    <span class="item"><a href="javascript:void(0)" onClick="setactDate(this,'1')">即将开始</a></span>
                    <span class="item"><a href="javascript:void(0)" onClick="setactDate(this,'2')">正在进行</a></span>
                    <span class="item"><a href="javascript:void(0)" onClick="setactDate(this,'3')">已结束</a></span>
                </div>
                <div class="searchCont">
                    <input placeholder="搜点什么..." id="title">
                    <button id="search" type="submit" onClick="setactTitle()"></button>
                </div>
            </div>
        </div>--%>

        <div class="topicslist-list container">
            <div class="con">
                 <!--显示数据 -->
                <ul class="clearfix DataUrl"></ul>
            </div>

             <!--分页开始 -->
             <div class="green-black" id="artPagging">
             </div>
             <!--分页结束-->
        </div>
</div>
<!--主体结束-->

<!--公共主底部开始-->
<%@include file="/pages/comm/agdfooter.jsp"%>
<!--公共主底部结束-END-->
</body>
</html>