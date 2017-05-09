<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%  
Date nowDate = new Date(); nowDate.setTime(System.currentTimeMillis());
request.setAttribute("now", nowDate);
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>广东省文化馆-艺术广场</title>
<%@include file="/pages/comm/comm_head.jsp"%>
<link href="./static/assets/css/art/soloList.css" rel="stylesheet">
<link href="./static/assets/css/gypxpage/gypxpage.css" rel="stylesheet">

<script src="./pages/home/gypx/weektool.js"></script>

<script type="text/javascript">
var now = "<fmt:formatDate value='${now}' pattern='yyyy/MM/dd HH:mm:ss'/>";

var action = function(){ 
	//组合查询条件
	var _params = {};
	function _setParams(param){
		if (!$.isPlainObject(param)) return;
		$.extend(_params, param);
	}
	
	function setParams(event){
		var key = $(event).attr("key");
		var value = $(event).attr("val");
		var peg = $(event).attr("peg") || 0;
		
		var param = new Object();
		param[key] = value;
		_setParams(param);
		_active( peg? $(event).parent(): $(event));
		//selectData4Param();
	}
	
	//处理选择项样式
	function _active(active){
		if (!active || active.size()==0) return;
		active.addClass("active");
		active.siblings(".active").removeClass("active");
	}
	
	//处理显示的两种方式
	function setShowCls(event, clsidx){
		_active($(event));
		
		if(isNaN(clsidx)) return;
		var uls = $(".kecheng-list>ul");
		uls.hide();
		$(uls.get(clsidx)).show();
	}
	function searchvalue(event){
		var value = $(event).siblings("input").val();
		_setParams({searchvalue : value});
		selectData4Param();
	}
	
	//按条件加载数据
	function selectData4Param(){
		_params.page = _params.page || 1;
		_params.size = _params.size || 9;
		$.getJSON("./gypx/loadGypxList",_params, function(data){
			writeData2Page(data.rows);
			genPagging("page_nav", _params.page, _params.size, data.total, function(page, size){
				_params.page = page;
				_params.size = size;
				selectData4Param();
			});
		});
	}

	//处理数据显示模板，需在显示数据前处理掉
	var li_1; var li_2;
	function setRowTemp(tempsJQ){
		var temps = $(tempsJQ);
		if (temps.size()==2){
			li_1 = $(temps.get(0)).html();
			li_2 = $(temps.get(1)).html();
		}
	}
	//显示数据到页面
	function writeData2Page(data){
		var uls = $(".kecheng-list>ul");
		uls.empty();
		
		for(var i in data){
			var row = data[i];
			var li1 = $(li_1);
			var li2 = $(li_2);
			
			var trapic = row.trapic || "";
			var title = row.trashorttitle || "";
			var desc = row.traintroduce || "";
			var sdate = (new Date(row.trasdate)).Format("yyyy-MM-dd hh:mm:ss");
			var edate = (new Date(row.traedate)).Format("yyyy-MM-dd hh:mm:ss");
			var weekObj = WeekTool.getWeekNum(sdate, edate, now);
			var weektext = "进行至"+weekObj.nowWeekNum+"周,共"+weekObj.weekNum+"周";
			
			li1.find("h2").text( title );
			li1.find("p").text("课程简介:"+desc);
			li1.find("a").attr("href", './gypx/detail/'+row.traid+'');
			li1.find(".kecheng-logo").attr("style", 'background-image:url('+trapic+')');
			li1.find(".data_week").text(weektext);
			
			li2.find("p.author").text( title );
			li2.find("p.kechengjieshao").text("课程简介:"+desc);
			li2.find("a").attr("href", './gypx/detail/'+row.traid+'');
			li2.find(".img a").attr("style", 'background-image:url('+trapic+')');
			li2.find(".data_week").text(weektext);
			
			$(uls[0]).append(li1);
			$(uls[1]).append(li2);
		}
	}

	function inputKey(event){
		alert(event.keyCode)
	}
	
	return {
		setRowTemp: setRowTemp,
		setShowCls: setShowCls,
		searchvalue: searchvalue,
		inputKey:inputKey,
		setParams: setParams,
		selectData4Param: selectData4Param
	}
}();

$(function(){
	//装入数据项模板
	action.setRowTemp("#li_temp>div");
	
	$("body").delegate("a[key]", "click", function(){
		action.setParams(this);
		action.selectData4Param();
	});
	
	$(".data-keydown").keydown(function(event){
		if (event.keyCode==13){
			$(this).siblings("button").click();
		}
	});
	
	//有传入培训类型时的处理
	var pxtype = "${pxtype}";
	var ystypenow = "${ystype}";
	if (pxtype){
		action.setParams($("a[key='tratyp'][val='${pxtype}']") )
	}
	if (ystypenow){
		action.setParams($("a[key='traarttyp'][val='${ystype}']") )
	}
	action.selectData4Param();
})

</script>

</head>
<body>

<div id="li_temp" style="display: none">
<div>
<li>
	<a href="javascript:void(0)"><div class="kecheng-logo"></div></a>
	<div class="kecheng-detail">
		<h2>title</h2>
		<p>课程简介:desc</p>
		<div class="sk-shijian"><span class="rili"></span><span class="data_week">进行至第3周，共9周</span> <a href="javascript:void(0)"><span class="bofang"></span></a></div>
	</div>
</li>
</div>
<div>
<li>
	<div class="img">
		<a href="javascript:void(0)" style="background-image:url(./static/assets/img/gypxpage/suolue-list-1.jpg)">
			<div class="mask"></div>
		</a>
	</div>
	<p class="author">title</p>
	<p class="kechengjieshao">课程简介:desc</p>
	<div class="sk-shijian"><span class="rili"></span><span class="data_week">进行至第3周，共9周</span> <a href="javascript:void(0)"><span class="bofang"></span></a></div>
</li>
</div>
</div>

<!-- 公共头部开始 -->
<%@include file="/pages/comm/szheader.jsp"%>
<!-- 公共头部结束 -->

<!--广告开始-->
<div class="art-ad" style="background:url(./static/assets/img/gypxpage/banner.jpg) no-repeat 50% 50%">
	<c:if test="${not empty gypx_adv.cfgadvpic }">
		<img alt="" src="${gypx_adv.cfgadvpic }" height="250">
	</c:if>
</div>
<!--广告结束-->
<!--主体开始-->
<div class="art-main clearfix Gypx-list">
	<div class="left-bar">
    	<h2><span>全部课程</span></h2>
        <ul>
        <li class="active"><i></i><a href="javascript:void(0)" key="traarttyp" val="" peg="1">全部</a></li>
        <c:forEach items="${ystypes }" var="item" varStatus="status">
        	<li ${status.last? "class='last'" : "" }>
        	<i></i>
        	<a href="javascript:void(0)" key="traarttyp" val="${item.typid}" peg="1">${item.typname }</a>
        	</li>
        </c:forEach>
        </ul>
        <div class="search-cont">
        	<h3>排列方式</h3>
            <div class="pailie">
                <div class="pailie-list active" onClick="action.setShowCls(this,1)">
                    <span class="active-1"></span>缩略图
                </div>
                <div class="pailie-slt" onClick="action.setShowCls(this,0)">
                    <span class="active-2"></span>列表式
                </div>
            </div>
        </div>
    </div>
    <div class="art-right-main">
        <div class="categoryChange">
            <div class="row clearfix">
            	<div class="title">类型</div>
            	<div class="adrList">
                	<span class="item active"><a href="javascript:void(0)" key="tratyp" val="" peg="1">不限</a></span>
                	
                	<c:forEach items="${pxtypes }" var="item" varStatus="status">
                	<span class="item">
                		<a href="javascript:void(0)" key="tratyp" val="${item.typid}" peg="1">${item.typname }</a>
               		</span>
                    </c:forEach>
                </div>
            </div>
            <div class="row border-none clearfix">
            	<div class="title">区域</div>
                <div class="adrList">
                	<span class="item active"><a href="javascript:void(0)" key="traarea" val="" peg="1">全部</a></span>
                	<c:forEach items="${qrtypes }" var="item" varStatus="status">
                    <span class="item">
                    	<a href="javascript:void(0)" key="traarea" val="${item.typid}" peg="1" >${item.typname }</a>
                    </span>
                	</c:forEach>
                </div>
            </div>
            <div class="type-row clearfix">
            	<span class="categoryName active"><a href="javascript:void(0)" key="datePart" val="0" peg="1">智能排序</a></span>
                <span class="categoryName"><a href="javascript:void(0)" key="datePart" val="1" peg="1">正在进行</a></span>
                <span class="categoryName"><a href="javascript:void(0)" key="datePart" val="2" peg="1">即将开始</a></span>
                <span class="categoryName last"><a href="javascript:void(0)" key="datePart" val="3" peg="1">已结束</a></span>
                <div class="searchCont">
                	<input placeholder="搜点什么..." class="data-keydown">
                    <button type="submit" onClick="action.searchvalue(this)"></button>
                </div>
            </div>
           
        </div>
        
        
        <div class="kecheng-list">
            <ul class="pailie-xs">
            
            </ul>
            <ul class="soloList clearfix suolue">

            </ul>
        </div>
        
        <div class="green-black" id="page_nav"> 
        </div>
    </div>
</div>
<!--主体结束-->
<!--底部开始-->
<%@include file="/pages/comm/szfooter.jsp"%>
<!--底部结束-->

<!--弹出层开始-->
<div class="popup js__orderKick_popup js__slide_top clearfix"> <a href="#" class="p_close js__p_close" title="关闭"></a>
  <div class="p_content">
    <p>温馨提示</p>
  </div>
  <div class="p_main">
    <div class="p_ico p_ico_2"></div>
    <span>您确定取消订单吗？</span> 
    <!--<p>取消后的订单将永久删除</p>--> 
  </div>
  <div class="p_btn goNext float-left"> <a href="javascript:void(0)">确定</a> </div>
  <div class="p_btn goBack float-left"> <a href="javascript:void(0)" class="js__p_close">取消</a> </div>
</div>
<!--弹出层结束--> 

<script src="./static/assets/js/gypxpage/gypxpage.js"></script>
</body>
</html>
