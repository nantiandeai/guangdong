<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>广东省文化馆</title>
    <%@include file="/pages/comm/comm_head.jsp"%>
    
    <link href="./static/assets/css/gypxpage/gypxpage.css" rel="stylesheet">
    <link href="./static/assets/css/gypxpage/gypxindex.css" rel="stylesheet">
    <script src="./static/assets/js/gypxpage/gypxpage.js"></script>
	<script src="./static/assets/js/public/sly.js"></script>
    <script src="./static/assets/js/gypxpage/gypxpageIndex.js"></script>

<script type="text/javascript">

var action = (function(){
	//公益培训类型缓存
	var gypxTypes = [{typid:"2016101400000029",typname:"文化惠民"},
	                 {typid:"2016101400000013",typname:"走进艺术"},
	                 {typid:"2016101400000026",typname:"名家讲堂"}];
	
	//加载公益培训类型
	function _loadTypes( fun ){
		if (gypxTypes.length == 0){
			$.getJSON("./gypx/loadTypes", function(data){
				gypxTypes = data;
				fun? fun.call(this) : "";
			})
		}else{
			fun? fun.call(this) :"";
		}
	}
	
	//艺术分类缓存
	var ysTypes = [];
	function _loadYsTypes( fun ){
		if (ysTypes.length == 0){
			$.getJSON("./gypx/loadYsTypes", function(data){
				ysTypes = data;
				fun? fun.call(this, ysTypes) : "";
			})
		}else{
			fun? fun.call(this, ysTypes) :"";
		}
	}
	_loadYsTypes();
	
	//加载首页培训集
	function _searchHp(){
		$.getJSON("./gypx/searchHpList", function(data){
			_pushGypxTypes(data);
		});
	}
	
	//加载培训分类块
	function _pushGypxTypes(data){
		var divjq = $(".gypxpage");
		var lastDataTemp = "";
		for(var i in gypxTypes){
			//处理两种不同的显示方式
			var temp = (i%2 == 0)? divjq.find(".data-temp:eq(0)"): divjq.find(".data-temp:eq(1)");
			var tempHTML = temp.prop("outerHTML");
			var dataTemp = $(tempHTML).removeClass("data-temp none");
			//设置分类标题
			dataTemp.find("div:eq(0) span").text(gypxTypes[i].typname);
			//处理分类数据
			_showGypxInfo(dataTemp, data, gypxTypes[i]);
			//查看更多
			dataTemp.find("div.more a").attr("href", "./gypx/list?pxtype="+gypxTypes[i].typid);
			
			divjq.append( dataTemp );
			lastDataTemp = dataTemp;
		}
		if (lastDataTemp){
			lastDataTemp.find("div.more").addClass("lastmore");
		}
	}
	
	//处理艺术分类导航显示
	function _showYsTypes(ystypes, tag, gypxtype){
		for (var i in ystypes){
			var type = ystypes[i];
			var ystemp = $('<a href="./gypx/list?pxtype='+gypxtype.typid+'&ystype='+type.typid+'"><span>'+type.typname+'</span></a>');
			tag.append(ystemp);
			tag.append('<span>/</span>');
		}
		tag.find("span:last").remove();
	}
	
	//处理培训分类块数据项显示
	function _showGypxInfo(temp, data, gypxtype){
		var yslabel = temp.find("div.label");
		if (yslabel.length){
			yslabel.empty();
			_loadYsTypes(function(ystypes){ _showYsTypes(ystypes, yslabel, gypxtype) });
		}
		
		var tag = temp.find("div[class$='content'] ul");
		var rowTemp = tag.find("li:eq(0)").prop("outerHTML");
		tag.empty();
		for(var i in data){
			var row = data[i];
			var rowtag = $(rowTemp);
			if (row.cfgentclazz == gypxtype.typid){
				rowtag.find("a").attr("href", "./gypx/detail/"+row.cfgshowid)
				rowtag.find(".img").css("background-image", "url("+row.cfgshowpic+")");
				rowtag.find("h2").text(row.cfgshowtitle);
				if (rowtag.find("span.time").length){
					rowtag.find("span.time").text( (new Date(row.cfgshowtime)).Format("yyyy-MM-dd") );
					rowtag.find("p").text( row.cfgshowintroduce );
				}else{
					rowtag.find("p").text( (new Date(row.cfgshowtime)).Format("yyyy-MM-dd") );
					rowtag.find("p").append( '<span class="address"></span>' );
				}
				tag.append(rowtag);
			}
		}
	}
	
	//首页培训加载入口
	function loadGypxHp(){
		_loadTypes( _searchHp );
	}
	
	return {
		loadGypxHp : loadGypxHp
	}
})();




//课程日历处理
function kcCalender(){
	
	var infoTEMP = '<div class="list-img">'
		+'        <a href="javascript:void(0)"><img src="" alt=""></a>'
		+'        <div class="txt">'
		+'            <h3>title</h3>'
		+'            <p>课程简介：text</p>'
		+'        </div>'
		+'    </div>';
	
	function pushPageInfo(row, tag){
		var title = row.trashorttitle||"";
		var img = row.trapic||"";
		var desc = row.traintroduce||"";
		var infoDiv = $(infoTEMP);
		infoDiv.find("img").attr("src", img);
		infoDiv.find("a").attr("href", './gypx/detail/'+row.traid+'');
		infoDiv.find(".txt>h3").text(title);
		infoDiv.find(".txt>p").text("课程简介："+desc);
		
		tag.append(infoDiv);
	}
	
	//处理点选日历列表中的标记日历
	function showKCday(data){
		var day = data.date;
		day = day.replace(/(\d{2})\D+(\d{2})\D+(\d{4})/, "$3$1$2");
		
		var callTag = $(".calendar-list-img");
		callTag.hide();
		$.post("./gypx/searchGypx4Day", {day:day}, function(data){
			if (data && data.length>0){
				callTag.find(".list-img").remove();
				
				for(var i in data){
					pushPageInfo(data[i], callTag);
				}
				callTag.show();
			}
		}, "json");
	}
	
	//标记天数到日历列表中，显示日历
	function showKCRLtag(days){
		activityDate.init(days);
		
		$('.today').html(showToday());
	    $('.date').sly({
	        itemNav: "smart",
	        easing: "easeOutExpo",
	        prevPage: ".arrow-left",
	        nextPage: ".arrow-right",
	        startAt: showDate(),
	        horizontal: 1,
	        touchDragging: 1,
	        dragContent: 1,
	        scrollBy: 1
	    });
	    
	    showActivityList(days, showKCday);
	}
	
	//加载课程按排当月的天数集合信息
	function kcrlPost(data){
		var days = new Array();
		for(var i in data){
			var tradate = data[i]["tradate"];
			tradate = tradate.replace(/(\d{4})(\d{2})(\d{2})/, "$2/$3/$1");
			days.push( tradate );
		}
		
		showKCRLtag(days);
	}
	
	$.post("./gypx/monthDateList", kcrlPost, "json");
}

$(function () {
	action.loadGypxHp();
	
	kcCalender();
	
	//activityDate.init(["10/18/2016","10/22/2016","10/02/2016"]);
})
</script>
</head>
<body>

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

<div class="calendar-list">
    <div class="container date">

    </div>
</div>
<div class="left-right">
    <div class="arrow-left arrow-box" >
        <code>&lt;</code>
    </div>
    <div class="arrow-right arrow-box">
        <code>&gt;</code>
    </div>
</div>

<!-- 第一个弹出层demo -->
<div class="calendar-list-img container open10182016 none">
    <div class="calendar-list-close">x</div>
</div>

<!--公益培训-->
<div class="gypxpage">
    <!--资讯动态-->
    <div class="module1">
        <div class="module1-title">
            <span>资讯动态</span>
        </div>
        <div class="module1-con clearfix">
            <div class="module1-con-left">
                <div class="page-banner">
                    <ul>
                    <c:forEach items="${gypz_zxlist }" var="item" varStatus="status" end="2">
                        <li>
                            <a href="./xuanchuan/xqinfo?clnfid=${item.cfgshowid }">
                                <div class="img" style="background-image: url(${item.cfgshowpic})">
                                    <div class="title">${item.cfgshowtitle }</div>
                                </div>
                            </a>
                        </li>
                    </c:forEach>
                    </ul>
                </div>
                <ul class="pages">
                 </ul>
            </div>
            <div class="module1-con-right">
                <ul>
                <c:forEach items="${gypz_zxlist }" var="item" varStatus="status" end="2">
                    <li class="clearfix">
                        <a href="./xuanchuan/xqinfo?clnfid=${item.cfgshowid }"><div class="img" style="background-image: url(${item.cfgshowpic})"></div></a>
                        <div class="detail">
                            <a href="./xuanchuan/xqinfo?clnfid=${item.cfgshowid }"><h2>${item.cfgshowtitle }</h2></a>
                            <p>${item.cfgshowintroduce }</p>
                            <span>时间：<fmt:formatDate value='${item.cfgshowtime}' pattern='yyyy/MM/dd'/></span>
                        </div>
                    </li>
                </c:forEach>
                </ul>
            </div>
        </div>
        <div class="more">
            <a href="./xuanchuan/whdt">查看更多</a>
        </div>
    </div>
    <!--文化惠民-->
    <div class="module1 module2 data-temp none">
        <div class="module1-title">
            <span>文化惠民</span>
        </div>
        <div class="label">
            <a href="javascript:void(0)"><span>音乐</span></a>
            <span>/</span>
            <a href="javascript:void(0)"><span>舞蹈</span></a>
        </div>
        <div class="module2-con clearfix">
            <div class="module2-content">
                <ul class="clearfix">
                    <li>
                        <a href="javascript:void(0)">
                            <div class="img" style="background-image: url(static/assets/img/gypxpage/gypxindex/img1.jpg)"></div>
                            <div class="detail">
                                <h2>文艺陪伴您 周末好心情--市文化馆举行多场文艺培训</h2>
                                <p>2015-04-24 <span class="address">广东省</span></p>
                            </div>
                        </a>
                    </li>
                </ul>
            </div>
        </div>
        <div class="more">
            <a href="javascript:void(0)">查看更多</a>
        </div>
    </div>
    <!--走进艺术-->
    <div class="module1 module3 data-temp none">
        <div class="module1-title">
            <span>走进艺术</span>
        </div>
        <div class="module3-con clearfix">
            <div class="module3-content">
                <ul class="clearfix">
                    <li>
                        <a href="javascript:void(0)"><div class="img" style="background-image: url(static/assets/img/gypxpage/gypxindex/img5.jpg)"></div></a>
                        <div class="detail">
                            <h2>戏剧界泰斗3天教会你幕后幕前那些事儿</h2>
                            <span class="time">2016-09-24</span>
                            <p>为了提升广东省戏剧的创演水平，开拓广东省戏剧编导演的专业视野，由广东省市文化广电新闻出版局主办，广东省市文化馆承办的办第七十期“文...</p>
                            <a class="lookdetail" href="javascript:void(0)">查看详情</a>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
        <div class="more">
            <a href="javascript:void(0)">查看更多</a>
        </div>
    </div>
</div>

<!--底部开始-->
<%@include file="/pages/comm/szfooter.jsp"%>
<!--底部结束-->

</body>
</html>
