<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath();
	request.setAttribute("basePath", basePath);
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv=”X-UA-Compatible” content=”IE=edge,chrome=1″ />
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="keywords" content="">
<meta name="author" content="">
<title>广东省文化馆-用户中心</title>
<base href="${basePath}/" />
<link href="./static/assets/css/public/reset.css" rel="stylesheet">
<link href="./static/assets/css/userCenter/userCenter.css"
	rel="stylesheet">
<!--[if lt IE 9] >
<script src="./static/assets/js/plugins/ie/IE9.js"></script> 
<! [endif]]-->
</head>
<body>
<!-- 公共头部开始 -->
<%@include file="/pages/comm/agdtop.jsp"%>
<!-- 公共头部结束 -->

<div style="height: 100px"></div>

<!-- 公共绑定开始 -->
<%@include file="/pages/comm/comm_center.jsp"%>
<!-- 公共绑定结束 -->

	<div class="main clearfix">
		<div class="leftPanel">
			<ul>
				<li><a href="./userCenter"><em class="ico i-9"></em>账户概况</a></li>
				<li><a href="./userInfo"><em class="ico i-1"></em>基本信息</a></li>
				<li><a href="./order"><em class="ico i-2"></em>我的订单</a></li>
				<li><a href="./activity"><em class="ico i-6"></em>我的活动</a></li>
				<li><a href="./curriculum"><em class="ico i-3"></em>我的课程</a></li>
				<li><a href="./favorite"><em class="ico i-8"></em>我的收藏</a></li>
				<li><a href="./comment"><em class="ico i-7"></em>我的点评</a></li>
				<li class="active"><a href="./message"><em
						class="ico i-4"></em>我的消息</a></li>
				<li><a href="./safely"><em class="ico i-5"></em>安全设置</a></li>
			</ul>
		</div>
		<div class="rightPanel">
			<ul class="commBtn clearfix">
				<li class="active">我的消息</li>
				<a href="javascript:void(0)" class="btn js__p_orderKickAll_start">一键清空</a>
			</ul>
			<!--<div class="sysmsg">
                <div class="ad"></div>
                <p>暂无消息</p>
            </div>-->
			<ul class="group clearfix">
				<li class="item">
					<div class="orderCont clearfix">
						<div class="orderTime">2016-09-29 18:40</div>
					</div>
					<div class="msgInfoCont">
						<h2>【系统通知】您成功报名 "临港四镇文化交流演出（迎国庆）"</h2>
						<p class="info">活动地址：万祥镇万祥路101号，活动时间：2016-10-08
							14:00-15:30，请记得准时参加哦！</p>
						<a class="orderKick js__p_orderKick_start"
							href="javascript:void(0)">删除</a>
					</div>
				</li>
				<li class="item">
					<div class="orderCont clearfix">
						<div class="orderTime">2016-09-13 17:45</div>
					</div>
					<div class="msgInfoCont">
						<h2>【系统通知】恭喜您成为广东省文化馆注册会员</h2>
						<p class="info">会员请自觉遵守《全国人大常委会关于维护互联网安全的决定》、《互联网信息服务管理办法》、《互联网电子公告服务管理规定》及中华人民共和国其他各项有关法律法规
						</p>
						<a class="orderKick js__p_orderKick_start"
							href="javascript:void(0)">删除</a>
					</div>
				</li>
				<li class="item">
					<div class="orderCont clearfix">
						<div class="orderTime">2016-09-13 17:45</div>
					</div>
					<div class="msgInfoCont">
						<h2>【系统通知】恭喜您成为广东省文化馆注册会员</h2>
						<p class="info">会员请自觉遵守《全国人大常委会关于维护互联网安全的决定》、《互联网信息服务管理办法》、《互联网电子公告服务管理规定》及中华人民共和国其他各项有关法律法规会员请自觉遵守《全国人大常委会关于维护互联网安全的决定》、《互联网信息服务管理办法》、《互联网电子公告服务管理规定》及中华人民共和国其他各项有关法律法规会员请自觉遵守《全国人大常委会关于维护互联网安全的决定》、《互联网信息服务管理办法》、《互联网电子公告服务管理规定》及中华人民共和国其他各项有关法律法规
						</p>
						<a class="orderKick js__p_orderKick_start"
							href="javascript:void(0)">删除</a>
					</div>
				</li>
			</ul>
			<div class="green-black">
				<span class="disabled">&lt; Prev</span> <span class="current">1</span>
				<a href="#?page=2">2</a> <a href="#?page=3">3</a> <a href="#?page=4">4</a>
				<a href="#?page=5">5</a> <a href="#?page=6">6</a> <a href="#?page=7">7</a>...
				<a href="#?page=199">199</a> <a href="#?page=200">200</a> <a
					href="#?page=2">Next &gt; </a>
			</div>
		</div>
	</div>
	</div>
	<!--弹出层开始-->
	<div class="popup js__orderKick_popup js__slide_top clearfix">
		<a href="#" class="p_close js__p_close" title="关闭"></a>
		<div class="p_content">
			<p>温馨提示</p>
		</div>
		<div class="p_main">
			<div class="p_ico p_ico_2"></div>
			<span>您确定删除？</span>
		</div>
		<div class="p_btn goNext float-left">
			<a href="javascript:void(0)">确定</a>
		</div>
		<div class="p_btn goBack float-left">
			<a href="javascript:void(0)" class="js__p_close">取消</a>
		</div>
	</div>
	<div class="popup js__orderKickAll_popup js__slide_top clearfix">
		<a href="#" class="p_close js__p_close" title="关闭"></a>
		<div class="p_content">
			<p>温馨提示</p>
		</div>
		<div class="p_main">
			<div class="p_ico p_ico_1"></div>
			<span>您确定删除全部消息？</span>
		</div>
		<div class="p_btn goNext float-left">
			<a href="javascript:void(0)">确定</a>
		</div>
		<div class="p_btn goBack float-left">
			<a href="javascript:void(0)" class="js__p_close">取消</a>
		</div>
	</div>
<!--底部开始-->
<%@include file="/pages/comm/agdfooter.jsp"%>
<!--底部结束-->
	<!--弹出层结束-->
	<!-- core public JavaScript -->
	<script src="./static/assets/js/public/jquery-1.11.0.min.js"></script>
	<script src="./static/assets/js/plugins/tipso.js"></script>
	<script src="./static/assets/js/public/jquery-migrate-1.0.0.js"></script>
	<script src="./static/assets/js/userCenter/public.js"></script>
	<script src="./static/assets/js/userCenter/message.js"></script>
</body>
</html>
