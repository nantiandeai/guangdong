<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<% request.setAttribute("now", new Date()); %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<%@include file="/pages/comm/agdhead.jsp"%>
<title>广东省文化馆-活动报名</title>
<link href="${basePath }/static/assets/css/activity/activityDetaiil.css" rel="stylesheet">
<link href="${basePath }/static/assets/js/plugins/oiplayer-master/css/oiplayer.css" rel="stylesheet">
<link href="${basePath }/static/assets/css/activity/baoming.css" rel="stylesheet">

<script src="${basePath }/static/assets/js/plugins/sidebar/stickySidebar.js"></script>
<script src="${basePath }/static/assets/js/activity/activityDetail.js"></script>
<script src="${basePath }/static/assets/js/plugins/oiplayer-master/plugins/flowplayer-3.2.6.min.js"></script>
<script src="${basePath }/static/assets/js/plugins/oiplayer-master/js/jquery.oiplayer.js"></script>
<script src="${basePath }/static/assets/js/activity/seatOrder.js"></script>
<script src="${basePath }/static/assets/js/plugins/laydate.dev.min.js"></script>
<script type="text/javascript">
	//验证码绑定事件	
	$(document).ready(function() {
		$("#yanzhen").click(function(){
			var src = $(this).attr("src");
			src = src.substring(0, src.indexOf('t=1'));
			$(this).attr("src", src+'t='+new Date().getTime());
		});
	})
	
	
	/* var staMap = '${statusMap}'; 
	var typeMap = '${mapType}'; */
	var seatMax = '${actdetail.seats}';
	var seatSize = 0;
	var seatSizeUser = 0;
	if(seatMax =='' || seatMax ==null){
		seatMax = 5;
	}
	var seatSize = 0;
	var totalSeatSize = 0 ; 
	//手机号码规则
	var Phones = /^1[34578][0-9]\d{8}$/;
	
	$(function(){
		var sellticket = "${actdetail.sellticket }";
		if(sellticket == 2){
			$("#seat_1").css("display","none");
			$("#seat").css("display","none");
			$("#seat-msg").css("display","none");
			$("#seat-on").css("display","none");
			$("#ticketCount").css("display","block");
		}
		if(sellticket == 3){
			$("#seat_1").css("display","none");
			$("#seat").css("display","block");
			$("#seat-msg").css("display","block");
			$("#seat-on").css("display","block");
			$("#ticketCount").css("display","none");
		}
	})
	
	/**
	 * 提交报名信息
	 * @returns
	 */
	function addActBM(){
		var _form = $("#bm");
		var actId = $("#actId").val();
		var selection = $("#seat-on-message").find("span.seat-list");
		var mySelectSeat = [];
		for(var i = 0;i < selection.length;i++){
			var item = selection[i];
			mySelectSeat.push($(item).html());
		}
		$("#seatStr").val(mySelectSeat);
		$.ajax({
	         url : '${basePath }/agdwhhd/checkActPublish',
	         data : {actId: actId},
	         dataType : "json",
	         success : function(data){
	             if (data.success == 1){
	            	 _form.submit();	
	             }else {
	                 rongDialog({ type : false, title : data.errormsg, time : 3*1000 });
                     $("#act_baoming_step2 a.submit").one("click", addActBM);
	             }
	         },
             error: function(){
                 $("#act_baoming_step2 a.submit").one("click", addActBM);
             }
	     });
			
	}
	
	/*
	 * 意见手机号码验证
	 */
	function checkPhone(){ 
		var _form = $("#bm");
	    var telPhone = _form.find("[name='orderphoneno']").val();
	    if(!Phones.test(telPhone)){ 
	    	rongDialog({ type : false, title : "手机号码有误，请重填", time : 3*1000 });
	        return false; 
	    } 
	    return true;
	}
	
	$(function () {
        $('.submit').on("click", 'a.submit-order.red', function(){
        	showDivStep2();
        })
    });
	
	/*
	 * 验证表单数据
	 */
	function showDivStep2(){ 
		var dateCont = $("#dateCont").val();
		var orderphoneno = $("#orderphoneno").val();
		var eventid = $("#eventid").val();
		var actId = $("#actId").val();
		var seatNum = $("#seats").val();
		var eventText = $("#eventid option:checked").text(); 
		var dateText = $("#dateCont option:checked").text();
		var ordername = $("#ordername").val();
		var seats = $("#seats").val();
		if (dateCont == '' ) {
			return rongDialog({ type : false, title : "操作失败,请选择活动日期!", time : 3*1000 });
		}
		if (orderphoneno == '') {
			return rongDialog({ type : false, title : "操作失败,请填写正确的手机号码!", time : 3*1000 });
		}
		if ( eventid == '') {
			return rongDialog({ type : false, title : "操作失败,请选择活动场次!", time : 3*1000 });
		}
		if ( ordername == '') {
			return rongDialog({ type : false, title : "操作失败,请输入订票人名称!", time : 3*1000 });
		}
		if(Number(seats) > Number(seatMax)){
			return rongDialog({ type : false, title : "本场次最多可选"+seatMax+"张票", time : 3*1000 });
		}
		var ticketNum_ = totalSeatSize - seatSize;
		if(ticketNum_ < Number(seatMax) && Number(seatNum) > 0){
			return rongDialog({ type : false, title : "本场次最多可选"+ticketNum_+"张票", time : 3*1000 });
		}
		
		/* alert($("input[type='checkboxOneInput']").is(':checked'));
		if($("input[type='checkboxOneInput']").is(':checked') == false){
			return rongDialog({ type : false, title : "请确认已阅读订票须知！", time : 3*1000 });
		} */
		var selection = $("#seat-on-message").find("span.seat-list");
		var mySelectSeat = [];
		for(var i = 0;i < selection.length;i++){
			var item = selection[i];
			mySelectSeat.push($(item).html());
		}
		if(mySelectSeat.length < 1 && seatNum < 1  ){
			return rongDialog({ type : false, title : "请选择座位或填写订票数量！", time : 3*1000 });
		}
		if(Number(seatSizeUser)+Number(seats) > Number(seatMax) ){
			return rongDialog({ type : false, title : "您最多可预定"+seatMax+"张票", time : 3*1000 });
		}
		if (!checkPhone()) return;
		$("#actDate").html(dateText +" "+ eventText);
		var ticketNum = mySelectSeat.length;
		if(mySelectSeat.length<1){
			ticketNum = seatNum;
		}
		$("#ticketNum").html(ticketNum);
		$("#telPhone").html(orderphoneno);
		$("#act_baoming_step2").show();
		$("#act_baoming_step1").hide();

        $("#act_baoming_step2 a.submit").off("click").one("click", addActBM);
	}
	
	/*
	 * 场次改变事件
	 */
	  function changePlay(){
		 var eventid = $("#eventid").val();
		 var actId = $("#actId").val();
		 $.ajax({
				type: 'post',
				url: '${basePath }/agdwhhd/changePlay?actId='+actId+'&eventId='+eventid,
				success: function(data){
					if (!data) return;
					$("#seat").empty(); 
					$('#seat-on-message').find("span.seat-list").remove();
					seatSize = data.seatSize;
					seatSizeUser = data.seatSizeUser||0; //当前用户订票数
					totalSeatSize = data.totalSeatSize; 
					$("#overNum").html(totalSeatSize - seatSize);
					$("#totalSeatNum").html(totalSeatSize);
					$("#ticketNum_").html(seatSizeUser+' / '+seatMax);
					var staMap =  data.statusMap;
					var typeMap = data.mapType; 
					//seatMax = seatMax - seatSizeUser;
					if(staMap !=''){
						$("#seat_1").css("display","block");
						var option = {
							        map     :    staMap,      //座位地图
							        mapType :    typeMap,  //座位号和状态
							        seatMax :    seatMax,         //票数
                                    seatSizeUser : seatSizeUser
							    }
						initSeat(option);
					}
				}
			});
	 } 
	
	/*
	 * 返回上一级
	 */
	 function returnBack(){
		 $("#act_baoming_step1").show();
		 $("#act_baoming_step2").hide();
	}
	

	 function changeDate(){ 
			var eventid = $("#dateCont").val();
			var actId = $("#actId").val();
	        $("#eventid").empty();  
	        $("#eventid").append('<option value="">--请选择--</option>');  
	          
	          
	        var url = '${basePath }/agdwhhd/changeSeat?actId='+actId+'&eventId='+eventid;
	        $.ajax({  
	           url: url,  
	           type: "POST",  
	           success : function(data, status){
	        	   if (!data) return;
	                   $.each(data.timePlayList||[],function(i,item){
	                       $("#eventid").append(" <option value='" + item.id + "'>" + item.playstime +"-"+ item.playetime + "</option>");  
	                   });  
	           },  
	            error : function() {    
	                 alert("error");  
	           }  
	        });  
	    }
</script>

</head>
<body class="oiplayer-example">
<!-- 公共头部开始 -->
<%@include file="/pages/comm/agdtopsmall.jsp"%>
<!-- 公共头部结束-END -->

<!--公共主头部开始-->
<div id="header-fix">
	<div class="header-nav-bg">
		<div class="header-nav">
			<div class="logo-small">
				<a href="${basePath }/index"><img src="${basePath }/static/assets/img/public/logoSmall.png"></a>
			</div>
			<ul>
				<li class="active"><a href="${basePath }/agdwhhd/activitylist">活动预约</a></li>
				<li><a href="${basePath }/agdwhhd/notice">活动公告</a></li> 
				<li><a href="${basePath }/agdwhhd/news">活动资讯</a></li>
				<li class="last"><a href="${basePath }/agdwhhd/brandlist">品牌活动</a></li>
			</ul>
		</div>
	</div>
</div>
<!--公共主头部结束-END-->

<!--主体开始 第一步-->
<div class="main-info-bg bg-color" id="act_baoming_step1">
    <ul class="crumbs crumbs-3 clearfix">
        <li class="step-1">1. 填写基本信息<em class="arrow"></em></li>
        <li class="step-2">2. 选择座位<em class="arrow"></em></li>
        <li class="step-3">3. 填写取票信息<em class="arrow"></em></li>
        <li class="step-4">4. 确认订单信息<em class="arrow"></em></li>
        <li class="step-5 last">5. 完成报名<em class="arrow"></em></li>
    </ul>
    <div class="container-wrapper">
        <div class="order-msg">
            <span class="msg-title">完成报名</span>
        </div>
        <div class="order-content clearfix">
            <div class="order-img">
                <img src="${imgServerAddr}${whg:getImg300_200(actdetail.imgurl)}" width="249" height="170">
            </div>
            <div class="order-detail">
                <h1>${actdetail.name }</h1>
                <p><i class="iconfont icon-shijian"></i>时间：<span><fmt:formatDate value="${actdetail.starttime}" pattern="yyyy-MM-dd"/></span></p>
                <p><i class="iconfont icon-dianhua"></i>电话：<span>${actdetail.telphone }</span></p>
                <p><i class="iconfont icon-dibiao"></i>地址：<span>${actdetail.address }</span></p>
            </div>
        </div>
    </div>
	<form method="post" id="bm" action="${basePath }/agdwhhd/saveActBmInfo" >
    <div class="dialog-signUP-content clearfix">
    <input type="hidden" name="actId" id="actId" value="${actdetail.id }">
     <input type="hidden" name="seatStr" id="seatStr">
        <div class="order-msg">
            <span class="msg-title">选择座位</span>
        </div>
        <div class="dialog-signUP-con">
            <div class="desc desc1">
                <div class="detail">
                    <table class="tab1" width="100%">
                        <tbody>
                        <tr>
                            <td class="w500">
                                 <span class="date_name fl">选择日期</span>
                                <select  class="cate fl" name="dateCont" id="dateCont" style="width:170px" onchange="changeDate()">
                                <option value="">--请选择--</option>
                                <c:forEach items="${timeDateList}" var="item">
                                	<option value="${item.id }"><fmt:formatDate value="${item.playdate }" pattern="yyyy-MM-dd"/></option>
                                </c:forEach>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td class="clearfix" >
                                <span class="date_name fl">选择场次</span> 
                                <select  class="cate fl" name="eventid" id="eventid" style="width:170px" onchange="changePlay()" >
                                 <%-- <c:forEach items="${playList }" var="item">
                                	<option value="${item.id }">${item.playstime }-${item.playetime }</option>
                                </c:forEach>  --%>
                                </select>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                    <p>剩余票数/总票数：<span><span class="active" id="overNum"></span>/<span id="totalSeatNum"></span></span></p>
                    <p>您已预定：<span><span class="active" id="ticketNum_"></span></span></p>
                    <div id="ticketCount">
	                    <p>
	                                                            我要订
	                        <input class="ticket" type="number" id="seats" name="seats" value="0" min="0" max="${actdetail.seats}"></input>
	                        <span>张票</span>
	                    </p>
                    </div>
                </div>
				<div class="seat-position">
					<div class="seat" id="seat_1">
						<!--座位渲染-->
						<div class="seat-container" id="seat"></div>
					</div>
				</div>
                <div class="seat-msg" id="seat-msg" >
                    <div class="seat-img">
                        <span><span class="kex"></span>可选</span>
                        <span><span class="bkx"></span>不可选</span>
                        <span><span class="yx"></span>已选</span>
                    </div>
                </div>
                <div class="seat-on" id="seat-on" >
                    <div class="seat-on-message clearfix" id="seat-on-message">
                        <span class="tt">已选座位</span>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="dialog-signUP-content clearfix">
        <div class="order-msg">
            <span class="msg-title">取票信息</span>
        </div>
        
            <div class="form-list">
                <p>为了能顺利取票，请确保您的手机号码无误</p>
                <p><span>手机号码：</span><input type="text" value="${sessionUser.phone }" maxlength="11" id="orderphoneno" name="orderphoneno" readonly="readonly"><span class="color">*必填项</span></p>
                <p><span>姓名：</span><input type="text" value="${sessionUser.name }" placeholder="请输入订票人名称" maxlength="30" id="ordername" name="ordername"><span class="color">*必填项</span></p>
                <%-- <p><span>验证码：</span><input type="text" placeholder="验证码" class="yz-code" name="yanzhen"><img  id="yanzhen" src="${basePath }/authImage?t=1" width="88" height="34"></p> --%>
            </div>
        <div class="explain">
            <div class="explain-desc">
                <h3>订票须知</h3>
                <p>1、订票成功后，活动开始当日请提前入场，避免拥堵；</p>
               <!--  <p>2、如需退票，请在活动开始前办理相关手续；</p>
                <p>3、每场活动票数有限，退票后再次预订可能遇到票已售完，无法订票的情况，由用户自行负责；</p>-->
                <p>2、如累计超过2次订票却没有到场参加活动者，将取消本年度购票资格；</p> 
                <p>3、如遇非人为可控因素或重大天气等影响，导致活动无法举办，举办方有权利延后或取消活动，并以短信和站内信形式告知订票人办理相关手续；</p>
                <p>4、活动最终解释权归举办方。</p>
            </div>
        </div>
    </div>
    <div class="submit clearfix">
        <div class="jf-checbox" id="checkbox1">
            <div class="checbox1">
                <input type="checkbox" value="1" id="checkboxOneInput">
                <label for="checkboxOneInput"><i class="selectx"></i></label>
            </div>
            <a href="javascript:void(0)">我已阅读并接受订票须知相关条款</a>
        </div>
        <a href="javascript:void(0)" class="submit-order" id="submit-order" >提交订单</a>
    </div>
    
    </form>
</div>
<!--主体结束-->

<!--主体开始(第二部)-->
<div class="main-info-bg bg-color" id="act_baoming_step2" style="display: none">
   <ul class="crumbs crumbs-4 clearfix">
        <li class="step-1">1. 填写基本信息<em class="arrow"></em></li>
        <li class="step-2">2. 选择座位<em class="arrow"></em></li>
        <li class="step-3">3. 填写取票信息<em class="arrow"></em></li>
        <li class="step-4">4. 确认订单信息<em class="arrow"></em></li>
        <li class="step-5 last">5. 完成报名<em class="arrow"></em></li>
    </ul>
    <div class="container-wrapper">
        <div class="order-msg">
            <span class="msg-title">确认订单信息</span>
            <a href="javascript:void(0)" class="return" onclick="returnBack()">返回上一级修改信息</a>
        </div>
        <div class="order-content clearfix">
            <div class="order-img">
                <img src="${imgServerAddr}${actdetail.imgurl}" id="img" width="249" height="170">
            </div>
            <div class="order-detail">
                <h1 id="actName">${actdetail.name }</h1>
                <p><i class="iconfont icon-shijian"></i>时间：<span id="actDate"> </span></p>
                <p><i class="iconfont icon-dibiao"></i>地址：<span id="addr">${actdetail.address }</span></p>
                <p><i class="iconfont icon-yqf-menpiao"></i>订票数：<span id="ticketNum"></span></p>
                <p><i class="iconfont icon-dianhua"></i>联系方式：<span id="telPhone"></span></p>
            </div>
        </div>
        <a href="javascript:void(0)" class="submit" >确认订单</a>
    </div>
</div>
<!--主体结束-->

<!--公共主底部开始-->
<%@include file="/pages/comm/agdfooter.jsp"%>
<!--公共主底部结束-END-->
</body>
</html>