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
      <li class="active"><a href="${basePath }/center/curriculumExamine">审核中</a></li>
      <li><a href="${basePath }/center/curriculumReady">已报名</a></li>
    </ul>
    <div class="sysmsg">
       <div class="ad"></div>
       <p>暂无报名审核的课程</p>
    </div>
    <ul class="group clearfix">
      <li class="item">
        <div class="orderCont clearfix">
          <div class="orderTime">2016-09-29 18:40</div>
          <div class="orderNum">类型 : <span>文化惠民</span></div>
          <div class="orderType">审核中</div>
        </div>
        <div class="msgInfoCont">
          <h2><a href="#" target="_blank">广东省文化馆吉它培训中心寒假免费招生</a></h2>
          <div class="clearfix">
              <div class="timeOver">
                <div class="thisTime" style="width:30%"></div>
              </div>
              <div class="timeMsg">已进行<i>3</i>周，共<i>8</i>周</div>
          </div>
          <p>地址 :<span>广东省市文化馆艺术中心人民路0052号</span></p>
          <a class="orderKick js__p_orderKick_start" href="javascript:void(0)">取消报名</a>
        </div>
      </li>
      <li class="item">
        <div class="orderCont clearfix">
          <div class="orderTime">2016-09-29 18:40</div>
          <div class="orderNum">类型 : <span>直播</span></div>
          <div class="vedioIco"></div>
          <div class="orderType">审核中</div>
        </div>
        <div class="msgInfoCont">
          <h2><a href="#" target="_blank">专题讲座“用文字修复广东省的记忆——广东省市民社会生态与规则”</a></h2>
          <div class="clearfix">
              <div class="timeOver">
                <div class="thisTime" style="width:0%"></div>
              </div>
              <div class="timeMsg" id="i1"><i id="time_d"></i>天<i id="time_h"></i>小时<i id="time_m"></i>分 后开课</div>
          </div>
          <p>地址 :<span>广东省市文化馆艺术中心人民路0052号</span></p>
          <a class="orderKick js__p_orderKick_start" href="javascript:void(0)">取消报名</a>
        </div>
      </li>
      <li class="item err-border">
        <div class="orderCont clearfix">
          <div class="orderTime">2016-09-29 18:40</div>
          <div class="orderNum">类型 : <span>直播</span></div>
          <div class="vedioIco"></div>
          <div class="orderType error">
          	审核失败
            <div class="err-msg none"><i></i>不符合报名条件，上传的资料不真实</div>
          </div>
        </div>
        <div class="msgInfoCont">
          <h2><a href="#" target="_blank">dota2 WCG 中国赛区培训班</a></h2>
          <div class="clearfix">
              <div class="timeOver">
                <div class="thisTime" style="width:33%"></div>
              </div>
              <div class="timeMsg">已进行<i>1</i>周，共<i>3</i>周</div>
          </div>
          <p>地址 :<span>广东省市文化馆艺术中心人民路0052号</span></p>
          <a class="orderKick js__p_orderKick_start" href="javascript:void(0)">取消报名</a>
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

<!--弹出层开始-->
<div class="popup js__orderKick_popup js__slide_top clearfix"> <a href="#" class="p_close js__p_close" title="关闭"></a>
  <div class="p_content">
    <p>温馨提示</p>
  </div>
  <div class="p_main">
    <div class="p_ico p_ico_2"></div>
    <span>您确定取消报名吗？</span> 
  </div>
  <div class="p_btn goNext float-left"> <a href="javascript:void(0)">确定</a> </div>
  <div class="p_btn goBack float-left"> <a href="javascript:void(0)" class="js__p_close">取消</a> </div>
</div>
<!--弹出层结束--> 
<!-- core public JavaScript --> 
<script src="${basePath }/static/assets/js/public/jquery-1.11.0.min.js"></script> 
<script src="${basePath }/static/assets/js/public/jquery-migrate-1.0.0.js"></script> 
<script src="${basePath }/static/assets/js/plugins/tipso.js"></script> 
<script src="${basePath }/static/assets/js/userCenter/public.js"></script> 
<script src="${basePath }/static/assets/js/userCenter/curriculum.js"></script>
<script>
	show_time("#i1","2016/11/05 00:00:00");
</script>
</body>
</html>
