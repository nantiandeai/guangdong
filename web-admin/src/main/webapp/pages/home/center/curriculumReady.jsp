<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath(); request.setAttribute("basePath", basePath); %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge"> 
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="keywords" content="">
<meta name="author" content="">
<title>广东省文化馆-用户中心</title>
<link href="${basePath }/static/assets/css/public/reset.css" rel="stylesheet">
<link href="${basePath }/static/assets/css/userCenter/userCenter.css" rel="stylesheet">
<!--[if lt IE 9] >
<script src="${basePath }/static/assets/js/plugins/ie/IE9.js"></script> 
<! [endif]]-->
</head>
<body>

<!-- 公共头部开始 -->
<%@include file="/pages/comm/agdtop.jsp"%>
<!-- 公共头部结束 -->

<!-- 公共绑定开始 -->
<%@include file="/pages/comm/comm_center.jsp"%>
<!-- 公共绑定结束 -->

<div class="main clearfix">
  <div class="leftPanel">
    <ul>
       <!--用户中心导航开始-->
		<%@include file="/pages/comm/ucnav.jsp"%>
		<!--用户中心导航结束-->
    </ul>
  </div>
  <div class="rightPanel">
    <ul class="commBtn clearfix">
      <li><a href="${basePath }/center/curriculum">报名中</a></li>
      <li><a href="${basePath }/center/curriculumExamine">审核中</a></li>
      <li class="active"><a href="${basePath }/center/curriculumReady">已报名</a></li>
    </ul>
    <div class="sysmsg">
       <div class="ad"></div>
       <p>暂无已报名的课程</p>
    </div>
    <ul class="group clearfix">
      <li class="item sus-border">
        <div class="orderCont clearfix">
          <div class="orderTime">2016-09-29 18:40</div>
          <div class="orderNum">类型 : <span>文化惠民</span></div>
        </div>
        <div class="msgInfoCont">
          <h2><a href="#" target="_blank">SC2 WCG 中国赛区培训</a></h2>
          <div class="clearfix">
              <div class="timeOver">
                <div class="thisTime" style="width:30%"></div>
              </div>
              <div class="timeMsg">已进行<i>3</i>周，共<i>8</i>周</div>
          </div>
          <p>地址 :<span>广东省市文化馆艺术中心人民路0052号</span></p>
        </div>
      </li>
      <li class="item sus-border">
        <div class="orderCont clearfix">
          <div class="orderTime">2016-09-29 18:40</div>
          <div class="orderNum">类型 : <span>直播</span></div>
          <div class="vedioIco"></div>
        </div>
        <div class="msgInfoCont">
          <h2><a href="#" target="_blank">LOL WCG 中国赛区培训班</a></h2>
          <div class="clearfix">
              <div class="timeOver">
                <div class="thisTime" style="width:33%"></div>
              </div>
              <div class="timeMsg">已进行<i>1</i>周，共<i>3</i>周</div>
          </div>
          <p>地址 :<span>广东省市文化馆艺术中心人民路0052号</span></p>
        </div>
      </li>
      <li class="item sus-border">
        <div class="orderCont clearfix">
          <div class="orderTime">2016-09-29 18:40</div>
          <div class="orderNum">类型 : <span>直播</span></div>
          <div class="vedioIco"></div>
        </div>
        <div class="msgInfoCont">
          <h2><a href="#" target="_blank">风暴英雄 WCG 中国赛区培训班</a></h2>
          <div class="clearfix">
              <div class="timeOver">
                <div class="thisTime" style="width:0%"></div>
              </div>
              <div class="timeMsg" id="i4"><i id="time_d"></i>天<i id="time_h"></i>小时<i id="time_m"></i>分 后开课</div>
          </div>
          <p>地址 :<span>广东省市文化馆艺术中心人民路0052号</span></p>
        </div>
      </li>
      <li class="item">
        <div class="orderCont clearfix">
          <div class="orderTime">2016-09-29 18:40</div>
          <div class="orderNum">类型 : <span>直播</span></div>
          <div class="vedioIco"></div>
        </div>
        <div class="msgInfoCont">
          <div class="m-end"></div>
          <h2><a href="#" target="_blank">风暴英雄 WCG 中国赛区培训班</a></h2>
          <div class="clearfix">
              <div class="timeOver">
                <div class="thisTime endTime" style="width:100%"></div>
              </div>
              <div class="timeMsg">课程已结束</div>
          </div>
          <p>地址 :<span>广东省市文化馆艺术中心人民路0052号</span></p>
        </div>
      </li>
    </ul>
    <div class="green-black"> <span class="disabled">&lt; Prev</span> <span class="current">1</span> <a href="#?page=2">2</a> <a href="#?page=3">3</a> <a href="#?page=4">4</a> <a href="#?page=5">5</a> <a href="#?page=6">6</a> <a href="#?page=7">7</a>... <a href="#?page=199">199</a> <a href="#?page=200">200</a> <a href="#?page=2">Next 
      &gt; </a></div>
  </div>
</div>

<!--底部开始-->
<%@include file="/pages/comm/agdfooter.jsp"%>
<!--底部结束-->

<!-- core public JavaScript --> 
<script src="${basePath }/static/assets/js/public/jquery-1.11.0.min.js"></script> 
<script src="${basePath }/static/assets/js/public/jquery-migrate-1.0.0.js"></script> 
<script src="${basePath }/static/assets/js/plugins/tipso.js"></script> 
<script src="${basePath }/static/assets/js/userCenter/public.js"></script>
<script src="${basePath }/static/assets/js/userCenter/curriculumReady.js"></script>
<script>
	show_time("#i4","2016/11/11 00:00:00");
</script>
</body>
</html>
