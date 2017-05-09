<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>广东省文化馆-活动详情</title>
<%@include file="/pages/comm/comm_head.jsp"%>

<link href="${basePath }/static/assets/css/art/soloList.css" rel="stylesheet">
<link href="${basePath }/static/assets/css/event/appt.css" rel="stylesheet">
<script type="text/javascript">

function bmClick(event){
	event.preventDefault();
	var itmid = event.data.itmid;
	alert(itmid);
	if (!itmid){
		rongDialog({ type : false, title : "请选择要报名的时段", time : 3*1000 });
		return;
	}
	window.location = "${basePath }/sign/evt/step1/"+itmid;
}

$(function(){
	var msg="";
	$(".a-button").removeClass("overPay");
	var actvisyp="${actdetail.actvisyp}";
	var actvisenrol="${actdetail.actvisenrol}";
	if(actvisenrol == "1"&& actvisyp != "1"){
		//如果是报名 
		$(".a-button").html("报名");	
			msg="剩余报名人数:";
		}else{
		//订票
		$(".a-button").html("预票");
			msg="剩余票数:";
		}
	$(".sj a").unbind("click").bind("click",function(){
		$(this).addClass("active");
		$(this).siblings(".active").removeClass("active");
		$("#ypcount").html(msg+$(this).attr("data"));
		
		//人数 and ID
		var count = $(this).attr("data");
		var itmid = $(this).attr("itmid");
		var bmedate=$(this).attr("bmedate");
		var bmsdate=$(this).attr("bmsdate");
	//	alert("bmsdate=="+new Date(bmedate)+"--new date"+new Date());
		//
		var but = $(".a-button");
		but.off("click");
		if (count == 0 || new Date(bmedate)<new Date() || new Date(bmsdate)>new Date()){
			but.addClass("overPay");
		}else{
			but.removeClass("overPay");
			but.on("click", {itmid:itmid}, bmClick);
		}
	})
	
	$(".sj a:first").click();
	
	
	/* $(".a-button").on("click", function(event){
		event.preventDefault();
		var itmid = $(this).data("itmid");
		if (!itmid){
			rongDialog({ type : false, title : "请选择要报名的时段", time : 3*1000 });
			return;
		}
		window.location = "${basePath }/sign/evt/step1/"+itmid;
	}) */
})
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
    <span><a href="${basePath }/">首页</a></span><span>></span><span><a href="${basePath }/event/index">活动预定</a></span><span>></span><span>${actdetail.actvtitle}</span>
</div>

<!--主体开始-->
<div class="art-main clearfix">
    <div class="yishutuandui-detail">
        <div class="yishutuandui-left">
            <img src="${basePath }/${actdetail.actvbigpic}" alt="">
        </div>
        <div class="yishutuandui-right">
            <h1>
                <span>${actdetail.actvtitle}</span>
                <a href="javascript:void(0)" class="dianzan" reftyp="2016101400000052" refid="${actdetail.actvid }" id="good"><span></span>点赞</a>
                <a href="javascript:void(0)" class="shoucang" reftyp="2016101400000052" refid="${actdetail.actvid }" id="collection"><span></span>收藏</a>
            </h1>
            <div class="event-time clearfix">
           <%--      <p>开始时间：
               			<span> <fmt:formatDate value='${actdetail.actvsdate}' pattern='yyyy-MM-dd'/></span>
                </p> --%>
                <p>活动场馆：<span>${actdetail.actvaddress}</span></p>
                <p>活动地址：<span>${actdetail.actvaddress }</span></p>
                <div class="time clearfix">
                    <div class="wz">活动时间:</div>
                    <div class="sj">
                    	<c:forEach items="${actvitm}" var="item">
                        <a href="javascript:void(0)" bmedate="${item.actvbmedate}" data="${item.actvitmbmcount}" 
                        itmid="${item.actvitmid }" bmsdate="${item.actvbmsdate}">
                        	<span> 
                        		活动:<fmt:formatDate value='${item.actvitmsdate}' pattern='yyyy-MM-dd'/>&nbsp;-
                        		<fmt:formatDate value='${item.actvitmedate}' pattern='yyyy-MM-dd'/>;&nbsp;&nbsp;
                        		报名|订票:<fmt:formatDate value='${item.actvbmsdate}' pattern='yyyy-MM-dd HH:mm'/>
                        	</span>
                        </a>
                    	</c:forEach>
                    </div>
                </div>
            </div>
            <div class="kcjd">
                <div class="ystd-label">
                    标签：
                    <c:forEach items="${taglist}" var="item">
                    <span>${item.name}</span>
                    </c:forEach>
                </div>
                <div class="kksj">
                    <a href="javascript:void(0)" class="sqrt a-button overPay">报名/预票</a>
                    <span id="ypcount"></span>
                    <div class="scgx" style="margin:10px 10px 0 0;">
                        <div class="fenx">
                            <span>分享:</span>
                            <a href="" class="fxweibo"></a>
                            <a href="" class="fxweix"></a>
                            <a href="" class="fxqq"></a>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </div>

    <div class="zuopin-detail">
        <div class="detail-list">
            <h3>详情介绍</h3>
            <img src="${basePath }/${actdetail.actvpic}" width="100%" height="360px">
            <p>
            	${actdetail.actvdetail}
            </p>
        </div>
      <if test="${not empty wqAct}">  
        <hr style="color: #dbdbdb;margin: 15px 0;">
        <div class="guanyu-ysj clearfix">
            <h2>往期回顾</h2>
            <div class="running running-1">
                <ul class="smart">
                <c:forEach items="${wqAct}" var="wq">
                 <li>
                	 <a href="${basePath}/event/detail?actvid=${wq.actvid}">
                 		<img src="${basePath }/${wq.actvbigpic}" width="266" height="210">
                 		<h3>${wq.actvshorttitle}</h3>
                 	</a>
                 </li>
                </c:forEach>
                </ul>
            </div>
            <div class="arrow-left arrow-box prev-a" >
                <b class="left"><i class="left-arrow1"></i><i class="left-arrow2"></i></b>
            </div>
            <div class="arrow-right arrow-box next-a">
                <b class="right"><i class="right-arrow1"></i><i class="right-arrow2"></i></b>
            </div>
        </div>
     </if>
</div>

</div>
<!--主体结束-->

<!--底部开始-->
<%@include file="/pages/comm/szfooter.jsp"%>
<!--底部结束--> 

<!-- core public JavaScript -->
<script src="${basePath }/static/assets/js/public/sly.js"></script>
<script src="${basePath }/static/assets/js/event/event.js"></script>
</body>
</html>