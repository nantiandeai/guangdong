<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<%@include file="/pages/comm/agdhead.jsp"%>
<title>广东省文化馆-文化活动列表</title>
<link href="${basePath }/static/assets/css/activity/activitylist.css" rel="stylesheet">
<link href="${basePath }/static/assets/js/plugins/oiplayer-master/css/oiplayer.css" rel="stylesheet">

<link href="${basePath }/static/assets/css/field/fieldList.css" rel="stylesheet">
<script src="${basePath }/static/assets/js/field/fieldList.js"></script>

<script src="${basePath }/static/assets/js/activity/activitylist.js"></script>
<script src="${basePath }/static/assets/js/plugins/oiplayer-master/plugins/flowplayer-3.2.6.min.js"></script>
<script src="${basePath }/static/assets/js/plugins/oiplayer-master/js/jquery.oiplayer.js"></script>
<script type="text/javascript">
var _param={};

//获取查找的条件
function setParam(param){
	if (!$.isPlainObject(param)) return;
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
	setParam({actvshorttitle:val});
	setParam({etype:type});
	_active($(active).parent());
	loadData();
}

//处理 区域查找
function setactArea(active,type) {
	var val=$("#title").val();
	setParam({actvshorttitle:val});
	setParam({areaid:type});
	_active($(active).parent());
	loadData();
}

//处理 省馆/全省查找
function setactCultid(active, type){
    setParam({cultid:type});
    if (type){
        setParam({areaid:''});
        loadData();
    }else{
        $(".categoryChange .adrCont span.active a").click();
    }
}

//处理 艺术类型查找
function setactArt(active,type) {
	var val=$("#title").val();
	setParam({actvshorttitle:val});
	setParam({arttype:type});
	_active($(active).parent());
	loadData();
}

//处理 时间查找
function setactDate(active,type) {
	var val=$("#title").val();
	setParam({actvshorttitle:val});
	setParam({statemdfdate:type});
	_active($(active).parent());
	loadData();
}

//处理 标题搜索
function setactTitle(){
	var val=$("#title").val();
	setParam({actvshorttitle:val});
	loadData();
}

/**点更多进入 数据加载 */
function loadData(page, rows){
	_param.page = page || 1;
	_param.rows = rows || 9;

	$.ajax({
		type: 'post',
		url: '${basePath }/agdwhhd/activityload',
		data: _param,		
		success: function(data){
			//
			if (!data) return;
			var actlist = data.actlist;
			var total = data.total;
			showData(actlist);
			
			//加载分页工具栏
			genPagging('artPagging', _param.page, _param.rows, total, loadData);
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
			var pic ="${imgServerAddr}"+WhgComm.getImg750_500(row.imgurl);
			var _html = '';
			_html+='<li>                                                                                 '
	        _html+='<a href="${basePath }/agdwhhd/activityinfo?actvid='+row.id+'">                   '
	        _html+='    <div class="img">  '
	        _html+='    <img src="'+pic+'" width="380" height="240"/>  '
	        _html+='        <div class="mask"></div>                                                     '
	        _html+='    </div>                                                                           '
	        _html+='</a>                                                                                 '
		    _html+='    <div class="detail">                                                             '
		   if(row.name == null || row.name == "undefined" || row.name == ""){
			_html+='        <h2>----</h2>     	                                 '
		   }else{
			_html+='        <h2>'+row.name+'</h2>  										     '
		   }
		   if(row.address == null || row.address == "undefined" || row.address == "" ){
		     _html+='        <p>----</p>  		'
		    }
		    else{
		   _html+='        <p style="white-space:nowrap;text-overflow :ellipsis; ">地址 :'+row.address+'</p>   												 '
		   }
			if(row.starttime == null || row.starttime == "undefined" || row.starttime == "" ){
				_html+='        <p>----</p>  		'
			}
			else{
				_html+='        <p class="time">时间 :<span>'+WhgComm.FMTDate(row.starttime)+'</span>&nbsp;&nbsp;至&nbsp;&nbsp;<span>'+WhgComm.FMTDate(row.endtime)+'</span></p>   												 '
			}
		    _html+='    </div>                                                                           '
	        _html+='</li>																				 '
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
			 setactTitle();
		}
	});
	
	$.extend(_param,{actvtype:"${actvtype}"});
	//loadData();
    $(".masterRow li.active a").click();
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
				<li class="active"><a href="${basePath }/agdwhhd/activitylist">活动预约</a></li>
				 <li><a href="${basePath }/agdwhhd/notice">活动公告</a></li>
				<li><a href="${basePath }/agdwhhd/news">活动资讯</a></li>
				<li class="last"><a href="${basePath }/agdwhhd/brandlist">品牌活动</a></li>
			</ul>
		</div>
	</div>
</div>
<!--公共主头部结束-END-->

<!--主体开始-->
<div id="content">
    <div class="categoryChange">
        <div class="masterRow">
            <ul class="clearfix">
                <li class="active closeAdr"><a href="javascript:void(0)" onclick="setactCultid(this, 'TOP')" pname="cultid" pvalue="TOP">省馆</a></li>
                <li class="showAdr"><a href="javascript:void(0)" onclick="setactCultid(this, '')" pname="cultid" pvalue="">全省</a></li>
            </ul>
        </div>

        <div class="row clearfix adrCont none">
            <div class="title">区域</div>
            <div class="adrList qrtype">
                <span class="item active"><a href="javascript:void(0)" onClick="setactArea(this,'')">全部</a></span>
                <c:if test="${qrtype !=null}">
	            	<c:forEach items="${qrtype}" var="item">
	               		<span class="item"><a href="javascript:void(0)"  onClick="setactArea(this,${item.id})">${item.name}</a></span>
	            	</c:forEach>
                </c:if>
            </div>
        </div>
        
        <div class="row clearfix">
            <div class="title">活动类型</div>
            <div class="adrList">
                <span class="item ${empty actvtype ?'active':''}"><a href="javascript:void(0)" onClick="setactType(this,'')">全部</a></span>
           	  	<c:if test="${acttype != null}">
            		<c:forEach items="${acttype}" var="item">
             	 		<span class="item ${actvtype eq item.id? 'active':'' }"><a href="javascript:void(0)" onClick="setactType(this,${item.id})">${item.name}</a></span>
             		</c:forEach>
            	</c:if>
            </div>
        </div>
        
        <div class="row clearfix">
            <div class="title">艺术类型</div>
            <div class="adrList">
                <span class="item active"><a href="javascript:void(0)" onClick="setactArt(this,'')">全部</a></span>
                <c:if test="${ystype !=null}">
	                <c:forEach items="${ystype}" var="item"> 
	               		 <span class="item"><a href="javascript:void(0)" onClick="setactArt(this,${item.id})">${item.name}</a></span>
	             	</c:forEach>
             	</c:if>
            </div>
        </div>
        
        <div class="row clearfix">
            <div class="title">排序</div>
            <div class="adrList adrList1">
                <span class="item active"><a href="javascript:void(0)" onClick="setactDate(this,'')">智能排序</a></span>
                <span class="item"><a href="javascript:void(0)" onClick="setactDate(this,'1')">即将开始</a></span>
                <span class="item"><a href="javascript:void(0)" onClick="setactDate(this,'2')">正在进行</a></span>
                <span class="item"><a href="javascript:void(0)" onClick="setactDate(this,'3')">已结束</a></span>
                <span class="item"><a href="javascript:void(0)" onClick="setactDate(this,'4')">活动推荐</a></span>
            </div>
            <div class="searchCont">
                <input  id="title" placeholder="搜点什么...">
                <button type="submit" onClick="setactTitle()"></button>
            </div>
        </div>
    </div>
    
    <div class="active-list container">
        <div class="con">
        
        	<!--list 数据加载 start -->
            <ul class="clearfix DataUrl"></ul>
            <!-- list 数据加载  end  -->
            
        </div>
    </div>
    
    <!--分页开始 -->
        <div class="green-black" id="artPagging" style="margin-bottom: 40px"></div>
   	<!--分页结束-->
</div>
<!--主体结束-->

<!--公共主底部开始-->
<%@include file="/pages/comm/agdfooter.jsp"%>
<!--公共主底部结束-END-->
</body>
</html>