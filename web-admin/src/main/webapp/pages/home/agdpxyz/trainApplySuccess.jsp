<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <%@include file="/pages/comm/agdhead.jsp"%>
    <title>广东省文化馆-培训驿站-在线报名-${train.title}</title>
    <meta charset="utf-8">
    <meta http-equiv=”X-UA-Compatible” content=”IE=edge,chrome=1″ />
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="keywords" content="">
    <meta name="author" content="">
    <link href="${basePath}/static/assets/css/public/reset.css" rel="stylesheet">
    <link href="${basePath}/static/assets/css/activity/activityDetaiil.css" rel="stylesheet">
    <link href="${basePath}/static/assets/js/plugins/oiplayer-master/css/oiplayer.css" rel="stylesheet">
    <link href="${basePath}/static/assets/css/train/train.css" rel="stylesheet">
    <!-- core public JavaScript -->
    <script src="${basePath}/static/assets/js/public/jquery-1.11.0.min.js"></script>
    <script src="${basePath}/static/assets/js/public/jquery-migrate-1.0.0.js"></script>
    <script src="${basePath}/static/assets/js/plugins/oiplayer-master/plugins/flowplayer-3.2.6.min.js" type="text/javascript"></script>
    <script src="${basePath}/static/assets/js/plugins/oiplayer-master/js/jquery.oiplayer.js"></script>
    <script src="${basePath}/static/assets/js/public/rong-dialog.js"></script>
    <script src="${basePath}/static/assets/js/plugins/roll/jquery.sly.min.js"></script>
    <script src="${basePath}/static/assets/js/plugins/sidebar/stickySidebar.js"></script>
    <script src="${basePath}/static/assets/js/public/WdatePicker.js"></script>
    <script src="${basePath}/static/assets/js/activity/activityDetail.js"></script>
    <script src="${basePath}/static/assets/js/public/public.js"></script>
    <script type="text/javascript">
    </script>
</head>
<body class="oiplayer-example">
<!--公共主头部开始-->
<%@include file="/pages/comm/agdtopsmall.jsp"%>
<!--公共主头部结束-->
<div class="main-info-bg bg-color">
    <ul class="crumbs crumbs-2 clearfix">
        <li class="step-1">1. 填写培训报名资料<em class="arrow"></em></li>
        <li class="step-2 last">2. 等待管理员审核<em class="arrow"></em></li>
    </ul>
    <div class="main train-main container-wrapper">
        <div class="order-msg">
            <span class="msg-title">完成报名</span>
            <a href="${basePath}/center/curriculum" class="return">查看培训</a>
        </div>
        <div class="order-content clearfix">
            <div class="complete-order">
                <img src="${basePath}/static/assets/img/activity/complete-order.png" width="61" height="61">
            </div>
            <div class="compltet-order-msg">
                <c:if test="${train.isbasicclass == 2}">
                    <p>恭喜您，${train.title}</p>
                    <span>已报名成功 ！</span>
                </c:if>
                <c:if test="${train.isbasicclass != 2}">
                    <p>恭喜您，${train.title}</p>
                    <span>报名申请已提交成功，请耐心等待审核 ！</span>
                </c:if>
            </div>
        </div>
    </div>
    <a href="${basePath}/agdpxyz/traininfo?traid=${train.id}" class="submit">返回培训详情</a>
</div>

<!--公共主底部开始-->
<%@include file="/pages/comm/agdfooter.jsp"%>
<!--公共主底部结束-END-->
<a class="to-top"></a>
<a class="to-top"></a>
</body>
</html>
