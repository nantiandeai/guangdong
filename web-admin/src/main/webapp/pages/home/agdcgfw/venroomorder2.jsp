<%@ page import="com.creatoo.hn.utils.WhConstance" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    Object user = request.getSession().getAttribute(WhConstance.SESS_USER_KEY);
    if (user!=null){
        request.setAttribute("isSessUser", true);
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <%@include file="/pages/comm/agdhead.jsp"%>
    <title>场馆服务-${room.title }</title>
    <link href="${basePath }/static/assets/css/field/fieldOrder.css" rel="stylesheet">
    <link href="${basePath }/static/assets/js/plugins/oiplayer-master/css/oiplayer.css" rel="stylesheet">

    <script src="${basePath }/static/assets/js/plugins/oiplayer-master/plugins/flowplayer-3.2.6.min.js"></script>
    <script src="${basePath }/static/assets/js/plugins/oiplayer-master/js/jquery.oiplayer.js"></script>

    <script src="${basePath }/static/assets/js/field/fieldOrder.js"></script>

</head>
<body class="oiplayer-example">

<!-- 公共头部开始 -->
<%@include file="/pages/comm/agdtopsmall.jsp"%>
<!-- 公共头部结束-END -->

<div class="main-info-bg bg-color">
    <ul class="crumbs crumbs-3 clearfix">
        <li class="step-1">1. 填写基本信息<em class="arrow"></em></li>
        <li class="step-2">2. 预订人信息<em class="arrow"></em></li>
        <li class="step-3">3. 确认订单<em class="arrow"></em></li>
        <li class="step-4 last">4. 提交审核<em class="arrow"></em></li>
    </ul>
    <div class="container-wrapper">
        <div class="order-msg">
            <span class="msg-title">确认订单信息</span>
            <%--<a href="${basePath}/agdcgfw/roomOrder1?roomtimeid=${roomtime.id}&userphone=${userphone}&username=${username}&purpose=${purpose}" class="return">返回上一级修改信息</a>--%>
            <a href="javascript:void(0)" class="return">返回上一级修改信息</a>
        </div>
        <div class="order-content clearfix">
            <div class="order-img">
                <img src="${imgServerAddr}${whg:getImg300_200(room.imgurl)}" width="249" height="170">
            </div>
            <div class="order-detail">
                <h1>${room.title}</h1>
                <p><i class="iconfont icon-dibiao"></i>地址：<span>${ven.address} ${room.location}</span></p>
                <p><i class="iconfont icon-ren"></i>预订人：<span>${username}</span></p>
                <p><i class="iconfont icon-dianhua"></i>联系方式：<span>${userphone}</span></p>
                <p><i class="iconfont icon-quanminyuedongchangguanleixing"></i>预定场次：<span><fmt:formatDate value="${roomtime.timeday}" pattern="yyyy-MM-dd"></fmt:formatDate> <fmt:formatDate value="${roomtime.timestart}" pattern="HH:mm"></fmt:formatDate>-<fmt:formatDate value="${roomtime.timeend}" pattern="HH:mm"></fmt:formatDate></span></p>
            </div>
        </div>
        <a href="javascript:void(0)" class="submit">确认订单</a>
    </div>
</div>

<div style="display: none">
    <form id="gotoform" method="post">
        <input type="hidden" name="roomtimeid" value="${roomtime.id}">
        <input type="hidden" name="userphone" value="${userphone}">
        <input type="hidden" name="username" value="${username}">
        <textarea name="purpose" style="display: none">${purpose}</textarea>
    </form>
</div>



<script>
    $(function () {
        $("a.submit").one("click", okSubmit);

        function okSubmit() {
            var paramarr = $("#gotoform [name]").serializeArray();
            var data = {};
            for (var i in paramarr){
                data[paramarr[i].name] = paramarr[i].value;
            }

            $.ajax({
                url: "${basePath}/agdcgfw/saveRoomOrder",
                /*data: {roomtimeid:'${roomtime.id}', userphone: '${userphone}', username:'${username}', purpose: '${purpose}'},*/
                data : data,
                type: 'post',
                dataType: 'json',
                success: function(data){
                    if (data.success){
                        /*window.location.href = "${basePath}/agdcgfw/roomOrder3?roomtimeid=${roomtime.id}&userphone=${userphone}&username=${username}&purpose=${purpose}"*/
                        $("#gotoform").attr("action", "${basePath}/agdcgfw/roomOrder3");
                        $("#gotoform").submit();
                    }else{
                        rongAlertDialog({ title: '错误信息', desc: data.errmsg, closeBtn: true, closeIco:false, icoType:3});
                        $("a.submit").one("click", okSubmit);
                    }
                },
                error: function(){
                    $("a.submit").one("click", okSubmit);
                }
            })
        }

        $("a.return").on("click", function(){
            $("#gotoform").attr("action", "${basePath}/agdcgfw/roomOrder1");
            $("#gotoform").submit();
        });
    })
</script>

<!--公共主底部开始-->
<%@include file="/pages/comm/agdfooter.jsp"%>
<!--公共主底部结束-END-->

</body>
</html>
