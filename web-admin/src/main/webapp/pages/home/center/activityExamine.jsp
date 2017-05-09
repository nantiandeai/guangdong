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
      <li><a href="${basePath }/center/activity">报名中</a></li>
      <li class="active"><a href="${basePath }/center/activity-examine">审核中</a></li>
      <li><a href="${basePath }/center/activity-history">已报名</a></li>
    </ul>
    <div class="sysmsg">
      <div class="ad"></div>
      <p>暂无您正在参与的活动报名审核信息</p>
    </div>
    <ul class="group clearfix">
      <li class="item">
        <div class="orderCont clearfix">
          <div class="orderTime">2016-09-29 18:40</div>
          <div class="orderNum">活动号 : <span>2016102200000117</span></div>
          <div class="orderType">审核中</div>
        </div>
        <div class="msgInfoCont">
          <h2><a href="#" target="_blank">人口流动 健康同行 主题活动暨文艺演出</a></h2>
          <p>活动时间 :<span>2016/10/30  上午10点</span></p>
          <p>地址 :<span>广东省市文化馆艺术中心人民路0052号</span></p>
          <a class="orderKick js__p_orderKick_start" href="javascript:void(0)">取消报名</a>
        </div>
      </li>
      <li class="item">
        <div class="orderCont clearfix">
          <div class="orderTime">2016-09-29 18:40</div>
          <div class="orderNum">活动号 : <span>2016102200022937</span></div>
          <div class="orderType">审核中</div>
        </div>
        <div class="msgInfoCont">
          <h2><a href="#" target="_blank">人口流动 健康同行 主题活动暨文艺演出</a></h2>
          <p>活动时间 :<span>2016/10/30  上午10点</span></p>
          <p>地址 :<span>广东省市文化馆艺术中心人民路0052号</span></p>
          <a class="orderKick js__p_orderKick_start" href="javascript:void(0)">取消报名</a>
        </div>
      </li>
      <li class="item err-border">
        <div class="orderCont clearfix">
          <div class="orderTime">2016-09-29 18:40</div>
          <div class="orderNum">活动号 : <span>2016102200001111</span></div>
          <div class="orderType error">
          	审核失败
            <div class="err-msg none"><i></i>不符合报名条件，信息不全</div>
          </div>
        </div>
        <div class="msgInfoCont">
          <h2><a href="#" target="_blank">人口流动 健康同行 主题活动暨文艺演出</a></h2>
          <p>活动时间 :<span>2016/10/30  上午10点</span></p>
          <p>地址 :<span>广东省市文化馆艺术中心人民路0052号</span></p>
          <a class="orderKick js__p_orderKick_start" href="javascript:void(0)">取消报名</a>
        </div>
      </li>
      <li class="item err-border">
        <div class="orderCont clearfix">
          <div class="orderTime">2016-09-29 18:40</div>
          <div class="orderNum">活动号 : <span>2016102200001111</span></div>
          <div class="orderType error">
          	审核失败
            <div class="err-msg none"><i></i>活动已开始，报名已截止</div>
          </div>
        </div>
        <div class="msgInfoCont">
          <h2><a href="#" target="_blank">改稿十多次后的收获 |《红高粱》创作谈</a></h2>
          <p>活动时间 :<span>2016/10/23  上午09点</span></p>
          <p>地址 :<span>广东省市文化馆艺术中心人民路0052号</span></p>
          <a class="orderKick js__p_orderKick_start" href="javascript:void(0)">取消报名</a>
        </div>
      </li>
    </ul>
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
    <!--<p>取消后的订单将永久删除</p>--> 
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
<script src="${basePath }/static/assets/js/userCenter/activity.js"></script>
</body>
</html>
