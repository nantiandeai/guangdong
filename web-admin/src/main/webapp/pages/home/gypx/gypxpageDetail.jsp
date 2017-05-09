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
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${px.tratitle }</title>
    
    <%@include file="/pages/comm/comm_head.jsp"%>
    <%@include file="/pages/comm/comm_video.jsp"%>
    
    <link href="static/assets/css/gypxpage/gypxpage.css" rel="stylesheet">
	<script src="pages/home/gypx/weektool.js"></script>
	<script src="static/assets/js/plugins/jquery.e-calendar.js"></script>
	
<script type="text/javascript">

function baomingOpt(){
	var enroletime = "<fmt:formatDate value='${px.traenroletime}' pattern='yyyy/MM/dd HH:mm:ss'/>";
	//alert(enroletime)
	//show_time("2016/11/11 00:00:00");
	show_time(enroletime);
}

function baomingCount(){
	var traid = "${px.traid}";
	var traisenrolqr = "${px.traisenrolqr}";
	$.post("./sign/ajaxGetBaomingCount", {traid:traid, traisenrolqr:traisenrolqr}, function(data){
		$(".bm-over").html('<span></span> 已报名人数：'+data+'人');
	})
}

function weekOpt(){
	var sdate = "<fmt:formatDate value='${px.trasdate}' pattern='yyyy/MM/dd HH:mm:ss'/>";
	var edate = "<fmt:formatDate value='${px.traedate}' pattern='yyyy/MM/dd HH:mm:ss'/>";
	var now = "<fmt:formatDate value='${now}' pattern='yyyy/MM/dd HH:mm:ss'/>";
	var weekObj = WeekTool.getWeekNum(sdate, edate, now);
	$(".week_now").html(weekObj.nowWeekNum+'/'+weekObj.weekNum+"周");
	$(".loadtiao>div").attr("style", "width:"+weekObj.per+"%");
	$(".kcsm label").text(weekObj.weekNum+"周");
}


var times = (function(){
	var traitmid = "${px.traid}";
	var fmtarr = ["零","一","二","三","四","五","六","七","八","九","十","拾","百","千","万","亿"];
	
	function fmt(idx){
		idx+=1;
		if (idx<=10) return fmtarr[idx];
		var idxarr = String(idx).split('');
		if (idxarr.length>5) return idx;
		idxarr.reverse();
		var fv = "";
		for(var i in idxarr){
			var _v = idxarr[i]>0?fmtarr[idxarr[i]]:"";
			if (i>0){
				var x = Math.floor(i%6);
				var ix = 10+x;
				_v += _v ? fmtarr[ix] : "";
				if (!_v && idxarr[i-1]!=0){
					_v += fmtarr[idxarr[i]];
				}
			}
			fv = _v+fv;	
		}
		return fv;
	}
	
	function _desc(desc, btm, etm){
		desc = desc || "";
		var test = desc.match(/\<span\>/g);
		var idx = test?test.length:0;
		var fv = fmt(idx);
		desc+='<span>第'+fv+'节 '+btm+'-'+etm+'</span>';
		return desc;
	}
	
	function gotoTime(data){
		var argments = new Object();
		argments.events = new Array();
		
		for(var i=0; i<data.length; i++){
			var ent = data.splice(i, 1)[0];
			i--;
			var item = new Object();
			item.title = ent.dtitle;
			var _date = ent.tradate.replace(/(\d{4})(\d{2})(\d{2})/, "$1,$2,$3");
			var dateArr = _date.split(",");
			item.datetime = new Date();
			item.datetime.setFullYear(parseInt(dateArr[0]),parseInt(dateArr[1]),parseInt(dateArr[2]));
			item.description = _desc(item.description, ent.stime, ent.etime);
			for(var j=0; j<data.length; j++){
				var ent2 = data[j];
				if (ent2.tradate == ent.tradate && ent2.dtitle == ent.dtitle){
					data.splice(j,1);
					j--;
					item.description = _desc(item.description, ent2.stime, ent2.etime);
				}
			}
			argments.events.push(item);
		}
		//alert(JSON.stringify(argments));
		$('#calendar').eCalendar(argments);
		/* $('#calendar').eCalendar({
		    events: [
		    	{title: '课程1', description: '<span>第一节 9:00-10:00</span> <span>第二节  10:00-11:00</span> <span>第三节  11:00-12:00</span> <span>第四节  11:00-12:00</span> <span>第五节  11:00-12:00</span>', datetime: new Date(2016, 11, 11)},
		    	{title: '课程1', description: '<span>第一节 9:00-10:00</span> <span>第二节  10:00-11:00</span> <span>第三节  11:00-12:00</span> <span>第四节  11:00-12:00</span> <span>第五节  11:00-12:00</span>', datetime: new Date(2016, 11, 11)},
		        {title: '课程2', description: '<span>第一节 9:00-10:00</span> <span>第二节  10:00-11:00</span> <span>第三节  11:00-12:00</span>', datetime: new Date(2016, 11, 25)},
		        {title: '课程3', description: '<span>第一节 9:00-10:00</span> <span>第二节  10:00-11:00</span> <span>第三节  11:00-12:00</span>', datetime: new Date(2016, 12, 2)},
				{title: '课程3', description: '<span>第一节 9:00-10:00</span> <span>第二节  10:00-11:00</span> <span>第三节  11:00-12:00</span>', datetime: new Date(2016, 12, 5)},
				{title: '课程3', description: '<span>第一节 9:00-10:00</span> <span>第二节  10:00-11:00</span> <span>第三节  11:00-12:00</span>', datetime: new Date(2016, 12, 6)},
				{title: '课程3', description: '<span>第一节 9:00-10:00</span> <span>第二节  10:00-11:00</span> <span>第三节  11:00-12:00</span>', datetime: new Date(2016, 12, 13)},
				{title: '课程3', description: '<span>第一节 9:00-10:00</span> <span>第二节  10:00-11:00</span> <span>第三节  11:00-12:00</span>', datetime: new Date(2016, 12, 22)},
				{title: '课程3', description: '<span>第一节 9:00-10:00</span> <span>第二节  10:00-11:00</span> <span>第三节  11:00-12:00</span>', datetime: new Date(2017, 1, 5)}
		    ]
		}); */
	}
	
	function send(){
		$.post("./gypx/searchGypxTimes", {traitmid:traitmid}, function(data){
			if(!data || data.length==0){
				//$(".openCurriculum").parent().hide();
				//return;
			}
			gotoTime(data);
		}, "json")
	}
	
	return {
		send : send
	}
})();


$(function(){
	var traid = "${px.traid}";
	if (!traid){
		rongDialog({
			type : false,
			title : "详情数据已不存在",
			time : 3*1000,
			url : "${basePath}"
		});
		return ;
	}
	
	var isenrol = "${px.traisenrol}";
	if (isenrol == 1){
		baomingOpt();
		baomingCount();
	}
	weekOpt();
	
	times.send();
})
</script>
</head>
<body class="oiplayer-example">

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
<div class="public-crumbs">
    <span><a href="${basePath }/">首页</a></span><span>></span><span><a href="./gypx/index">公益培训</a></span><span>></span><span>${px.tratitle }</span>
</div>
    <div>
        <div class="container kcxq">
            <div class="xq-left">
                <img src="${px.trabigpic }" />
            </div>
            <div class="xq-right">
                <h1>${px.tratitle }</h1>
                <div class="kcsm">
                    <div><span></span>课程时长：<label>周</label></div><!--|
                     <div>内容类型：视频 文档 随堂测验 富文本 讨论</div> -->
                </div>
                <div class="skdz">
                    <div><span></span>上课地址：<label>${px.traaddress }</label></div>
                </div>
                <div class="kcjd">
                <%-- <c:if test="${px.trasdate lt now and px.traedate gt now }"> --%>
                    <div>课程已进行至<span class="week_now">周</span></div>
                    <div class="loadtiao"><div style="width:0%"></div></div>
                <%-- </c:if> --%>
                    <div class="kksj clearfix">
                    	<span class="open">开课时间 :<span> <fmt:formatDate value="${px.trasdate}" pattern="yyyy-MM-dd HH:mm"/> </span></span>
                        <span class="close">结束时间 :<span> <fmt:formatDate value="${px.traedate}" pattern="yyyy-MM-dd HH:mm"/> </span></span>
                 	</div>
                </div>
                <hr style="margin: 15px 0">
                <div class="baoming">
	                <c:if test="${px.traisenrol ne 1}">
	                	<div class="go-now">
	                        <a href="javascript:void(0);" class="a-button">直接前往</a>
	                    </div>
	                </c:if>
	                <c:if test="${px.traisenrol eq 1}">
	                    <div class="bm-button">
		                    <c:choose>
		                    	<c:when test="${(px.traenrolstime gt now)}">
		                    		<a href="javascript:void(0)" class="a-button">报名未开始</a>
		                    	</c:when>
		                    	<c:when test="${(px.traenroletime lt now)}">
		                    		<a href="javascript:void(0)" class="a-button">报名已结束</a>
		                    	</c:when>
		                    	<c:otherwise>
		                    		<a href="./sign/step1/${px.traid }" class="a-button">立即报名</a>
		                    	</c:otherwise>
		                    </c:choose>
	                    </div>
                    </c:if>
                    <div class="bm-button open-kcb">
                        <a href="javascript:void(0);" class="a-button openCurriculum">查看课程表</a>
                    </div>
                    <c:if test="${px.traisenrol eq 1}">
	                	<c:if test="${(px.traenrolstime lt now) and (px.traenroletime gt now) }">
		                    <div class="bm-time"> 报名倒计时：
		                        <p><span id="time_d"></span>天<span id="time_h"></span>小时<span id="time_m"></span>分</p>
		                    </div>
	                	</c:if>
	                    <div class="bm-over">
	                        <span></span>  已报名人数： 人
	                    </div>
                    </c:if>
                
                </div>
            </div>
        </div>
        <div class="kedetail-box">
            <div class="kcdetail container">
                <div class="page-left">
                    <div class="kcdetail-left">
                    	<div class="kc-xq">
	                        <h2><span></span>课程详情介绍</h2>
	                        <p class="txt-cont">${px.tradetail }</p>
                    	</div>
                    	
                    	<div class="teacher-detail">
	                    	<h2><span></span>课程大纲</h2>
	                        <div>
	                        	${px.tracatalog }
	                        </div>
                    	</div>

						<c:if test="${not empty vos }">
                        <div class="teacher-spxs">
                            <h3><span></span>视频欣赏</h3>
                            <ul class="clearfix">
                            <c:forEach	items="${vos }" var="item">
                            	<li>
                            		<div class="img">
                            			<a href="javascript:void(0)" class="js__p_vedio_start opt_videoshow" vsurl="${item.enturl}">
                            				<img src="${item.deourl }">
                            			</a>
                            		</div>
                            		<h4>${item.entname }</h4>
                                </li>
                            </c:forEach>
                            </ul>
                        </div>
						</c:if>
                        
                        <c:if test="${not empty mps }">
                        <div class="teacher-ypxs">
                            <h3><span></span>音频欣赏</h3>
                            <div class="teacher-ypxs-list">
                            <c:forEach items="${mps }" var="item">
                            	<ul>
                                    <li class="yy-ming">
                                        <span class="yy-logo"></span>${item.entname }
                                    </li>
                                    <li class="yy-sj">
                                        <span class="yy-time">时长：${item.enttimes }</span>
                                    </li>
                                    <li class="yy-anniu">
                                        <a href="javascript:void(0);" class="opt_videoshow" vsurl="${item.enturl}">点击试听<span class="yy-bofang"></span></a>
                                    </li>
                                </ul>
                            </c:forEach>
                            </div>

                        </div>
                        </c:if>

                        <div class="ke-yaoqiu">
                        	<c:if test="${px.traisenrol eq 1}">
                        	<h2 class="kc-baoming"><span></span>课程报名要求</h2>
                            <h3><span></span>课程报名条件</h3>
                            <div>
                            	${px.traenroldesc }
                            </div>
                            </c:if>
                        </div>
                        <div class="scgx">
                            <a href="javascript:void(0);" class="shoucang" reftyp="2016101400000051" refid="${px.traid }" id="collection"><span></span>收藏</a>
                            <a href="javascript:void(0);" class="dianzan" reftyp="2016101400000051" refid="${px.traid }" id="good"><span></span>点赞</a>
                            <div class="fenx">
                                <span>分享:</span>
                                <a href="javascript:void(0);" class="fxweibo"></a>
                                <a href="javascript:void(0);" class="fxweix"></a>
                                <a href="javascript:void(0);" class="fxqq"></a>
                            </div>
                        </div>
                    </div>
                    
                    <div class="kcdetail-left">
                    <div class="kc-dp">
                        <h2><span class="kcdianping"></span>课程点评</h2>
                        <div class="dianp-list">
	                        <ul id="pinglunNRC" reftyp="2016101400000051" refid="${px.traid }">  </ul>
                        </div>
                        <div class="input-dianp clearfix">
                            <textarea placeholder ="对此课程进行点评。。。"></textarea>
                            <div class="dianp-xuanx clearfix">
                                <!-- <a href="javascript:void(0);"><span></span>表情</a>
                                <input type="checkbox">同步到新浪微博
                                <a href="javascript:void(0);" class="submit-dianp a-button">点 评</a> -->
                                <a href="javascript:void(0)" class="submit-dianp a-button">评  论</a>
                            </div>
                        </div>
                    </div>
                    </div>
                </div>
                <div class="page-right">
                    <div class="kcdetail-right">
                    	<h2 class="kc-teacher"><span></span>课程老师介绍</h2>
                        <!-- <span class="teacher-logo"></span> -->
                        <div class="teacher-detail-sm">
                        	<h3><span>${px.trateacher }</span><label></label></h3>
                        	<p>${px.trateacherdesc }</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
<!--底部开始-->
<%@include file="/pages/comm/szfooter.jsp"%>
<!--底部结束-->

<!--弹出层开始-->
<div class="popup js__cardA_popup js__slide_top clearfix" style="width:1200px;"> <a href="javascript:void(0);" class="p_close js__p_close" title="关闭"></a>
  <div class="p_content">
    <p>课程表</p>
  </div>
  <div class="p_main" style="margin:20px auto; padding:0px;">
  	<div style="margin:0px auto; padding-top:0px;">
    	<div id="calendar" class="clearfix"></div>
    </div>
  </div>
</div>
<!--弹出层结束-->

<script src="./static/assets/js/gypxpage/gypxpageDetail.js"></script>

</body>
</html>
