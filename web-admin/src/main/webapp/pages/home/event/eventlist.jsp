<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1″ />
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>广东省文化馆-活动列表</title>
<%@include file="/pages/comm/comm_head.jsp"%>
<!--<link href="${basePath }/static/assets/css/art/soloList.css" rel="stylesheet">-->
<!--<link href="${basePath }/static/assets/css/gypxpage/gypxpage.css" rel="stylesheet">-->
<script src="${basePath }/static/admin/js/common.js"></script>
<link href="${basePath }/static/assets/css/event/eventlist.css" rel="stylesheet">

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
	setParam({actvtype:type});
	_active($(active).parent());
	loadData();
}

//处理 区域查找
function setactArea(active,type) {
	setParam({actvarea:type});
	_active($(active).parent());
	loadData();
}

//处理 艺术类型查找
function setactArt(active,type) {
	setParam({actvarttyp:type});
	_active($(active).parent());
	loadData();
}

//处理 艺术类型查找
function setactDate(active,type) {
	setParam({sdate:type});
	_active($(active).parent());
	loadData();
}

//处理 标题搜索
function setactTitle(){
	var val=$("#title").val();
	setParam({actvshorttitle:val});
	loadData();
}

//处理 状态搜索
function setactState(active,type){
	setParam({actvsdate2:type});
	_active($(active).parent());
	loadData();
} 

/**点更多进入 数据加载 */
function loadData(){
	_param.page = _param.page || 1;
	_param.rows = _param.rows || 9;
	//alert("${actvtype}");
	$.ajax({
		type: 'post',
		url: '${basePath }/event/eventList',
		data:_param,		
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
	$('#dataUL').html('');
	if(data.length > 0){
		for(var i=0 ;i<data.length; i++){
			var row = data[i];
			var pic = row.actvbigpic? "${basePath }/"+row.actvbigpic+"" : "";
			var _html = '';
			//if(row.actvitmsdate != null){
			_html+=' <li>                                                                 '
	        _html+=' <div class="img">                                                    '
	        _html+='     <a href="${basePath }/event/detail?actvid='+row.actvid+'"        '
	        _html+='     style="background-image:url('+pic+')">   						  '
	        _html+='         <div class="mask"></div>                                     '
	        _html+='     </a>                                                             '
	        _html+=' </div>                                                               '
	        _html+=' <p class="author">'+row.actvshorttitle+'</p>                         '
	        _html+=' <p class="kechengjieshao">活动简介:'+row.actvintroduce+'</p>            '
	        if(row.actvitmsdate=="undefined"||row.actvitmsdate==null){
	        _html+=' <div class="sk-shijian">活动时间：暂无</div>  						  	  '	
	        }else{
	        _html+=' <div class="sk-shijian">活动时间：'+dateFMT(row.actvitmsdate)+'</div>   '
	        }
	        _html+='</li> 																  ';	
	        
			$('#dataUL').append(_html);
			//}
		}
	}
}

$(function(){
	$.extend(_param,{actvtype:"${actvtype}"});
	loadData();
});

</script>
</head>
<body>

<!-- 公共头部开始 -->
<%@include file="/pages/comm/szheader.jsp"%>
<!-- 公共头部结束 -->

<!--广告开始-->
<div class="art-ad" style="background:url(${basePath}/static/assets/img/event/event-banner.jpg) no-repeat 50% 50%">
	<c:if test="${not empty advlist}">
		<img src="${basePath }/${advlist.cfgadvpic}" height="250" width="100%"/>
	</c:if>
</div>

<!--广告结束-->

<div class="public-crumbs">
    <span><a href="${basePath }/">首页</a></span><span>></span><span><a href="${basePath }/event/index">活动预定</a></span><span>></span><span>活动列表</span>
</div>
<!--主体开始-->
<div class="art-main clearfix">
    <div class="art-left-main">
        <div class="categoryChange">
            <div class="sousuo">搜索条件
                <div class="searchCont">
                    <input placeholder="搜点什么..." id="title">
                    <button type="submit" onClick="setactTitle()"></button>
                </div>
            </div>
            
            <!--活动类型 开始 -->
             <div class="row clearfix">
                <div class="title">活动类型</div>
                <div class="adrList">
                	<span class="item"><a href="javascript:void(0)" onClick="setactType(this,'')">不限</a></span>
                 <c:forEach items="${acttype}" var="item" varStatus="status">
                    <span class="item ${actvtype eq item.typid? 'active':'' }">
                    <a href="javascript:void(0)" onClick="setactType(this,${item.typid})">${item.typname }</a>
                    </span>
                 </c:forEach>
                </div>
            </div>
             <!--活动类型 结束 -->
            
             <!--区域类型 开始 -->
            <div class="row clearfix">
                <div class="title">区域</div>
                <div class="adrList">
                <span class="item active"><a href="javascript:void(0)" onClick="setactArea(this,'')">全部</a></span>
                <c:forEach items="${qrtype}" var="item">
                <span class="item">
                <a href="javascript:void(0)" onClick="setactArea(this,${item.typid})"> ${item.typname}</a>
              	</span>
                </c:forEach>
                </div>
            </div>
             <!--区域类型 结束 -->
             
              <!--艺术类型 开始-->
            <div class="row clearfix">
                <div class="title">艺术类型</div>
                <div class="adrList">
                 <span class="item active"><a href="javascript:void(0)" onClick="setactArt(this,'')">不限</a></span>
                 <c:forEach items="${ystype}" var="item">
                    <span class="item "><a href="javascript:void(0)" onClick="setactArt(this,${item.typid})">${item.typname }</a></span>
                 </c:forEach>
                </div>
            </div>
             <!--艺术类型 结束 -->
            
            <!--活动开始时间 开始-->
            <div class="row clearfix">
                <div class="title">时间</div>
                <div class="adrList">
                    <span class="item active"><a href="javascript:void(0)" onClick="setactDate(this,'')">不限</a></span>
                    <span class="item"><a href="javascript:void(0)" onClick="setactDate(this,11)">一周内</a></span>
                    <span class="item"><a href="javascript:void(0)" onClick="setactDate(this,1)">一月内</a></span>
                    <span class="item"><a href="javascript:void(0)" onClick="setactDate(this,3)">三月内</a></span>
                    <span class="item"><a href="javascript:void(0)" onClick="setactDate(this,12)">12个月内</a></span>
                </div>
            </div>
            <!--活动结束时间 结束-->
            
            <div class="row clearfix">
                <div class="title">状态</div>
                <div class="adrList">
                    <span class="item active"><a href="javascript:void(0)" onClick="setactState(this,'')">不限</a></span>
                    <span class="item"><a href="javascript:void(0)" onClick="setactState(this,0)">正在进行</a></span>
                    <span class="item"><a href="javascript:void(0)" onClick="setactState(this,1)">即将开始</a></span>
                    <span class="item"><a href="javascript:void(0)" onClick="setactState(this,2)">已结束</a></span>
                </div>
            </div>
        </div>
        <div class="kecheng-list">
        
        	<!-- 数据 加载  开始-->
            <ul class="soloList clearfix suolue" id="dataUL">
            </ul>
            <!-- 数据 加载 结束 -->
            
        </div>
        
        <!--分页开始 -->
        <div class="green-black" id="artPagging">
         </div>
         <!--分页结束-->
         
    </div>
    
    <!--推荐 活动 开始 -->
    <div class="art-right">
        <h3>今日推荐活动</h3>
        <ul>
            <li>
                <div class="event-list">
                    <div class="img" style="background: url(${basePath }/static/assets/img/gypxpage/banner.jpg)"></div>
                    <p>超现实艺术摄影交流大赛</p>
                    <p>活动时间：2016-10-20</p>
                </div>
            </li>
        </ul>
    </div>
    <!--推荐 活动 结束 -->
</div>
<!--主体结束-->

<!--底部开始-->
<%@include file="/pages/comm/szfooter.jsp"%>
<!--底部结束--> 
<!-- core public JavaScript -->
<script src="${basePath }/static/assets/js/plugins/tipso.js"></script>
<script src="${basePath }/static/assets/js/userCenter/public.js"></script>
<script src="${basePath }/static/assets/js/gypxpage/gypxpage.js"></script>
</body>
</html>