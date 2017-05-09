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
    <script src="${basePath }/static/assets/js/activity/activityDetail.js"></script>

</head>
<body class="oiplayer-example">

<!-- 公共头部开始 -->
<%@include file="/pages/comm/agdtopsmall.jsp"%>
<!-- 公共头部结束-END -->

<!--主体开始-->
<div class="main-info-bg bg-color">
    <ul class="crumbs crumbs-2 clearfix">
        <li class="step-1">1. 填写基本信息<em class="arrow"></em></li>
        <li class="step-2">2. 预订人信息<em class="arrow"></em></li>
        <li class="step-3">3. 确认订单<em class="arrow"></em></li>
        <li class="step-4 last">4. 提交审核<em class="arrow"></em></li>
    </ul>
    <div class="container-wrapper">
        <div class="order-msg">
            <span class="msg-title">场地信息</span>
        </div>
        <div class="order-content clearfix">
            <div class="order-img">
                <img src="${imgServerAddr}${whg:getImg300_200(room.imgurl)}" width="249" height="170">
            </div>
            <div class="order-detail">
                <h1>${room.title}</h1>
                <p><i class="iconfont icon-dibiao"></i>地址：<span>${ven.address} ${room.location}</span></p>
            </div>
        </div>
    </div>
    <div class="dialog-signUP-content clearfix">
        <div class="order-msg">
            <span class="msg-title">预订人信息</span>
        </div>
        <form id="frm_roomorder1" action="${basePath}/agdcgfw/roomOrder2" method="post" onsubmit="return formCheck()">
            <input type="hidden" name="roomtimeid" value="${roomtime.id}">
            <div class="form-list">
                <p class="clearfix">为了能顺利预订，请确保您的手机号码无误</p>
                <p class="clearfix"><span class="hd">预定人：</span>
                    <%--<span>${sessionScope[WhConstance.SESS_USER_KEY].nickname}</span>--%>
                    <input type="text" name="username" value="${not empty username?username : (sessionScope[WhConstance.SESS_USER_KEY].isrealname eq 1? sessionScope[WhConstance.SESS_USER_KEY].name: sessionScope[WhConstance.SESS_USER_KEY].nickname)}" placeholder="请输入预定人">
                    <span class="color"></span>
                </p>
                <p class="clearfix"><span class="hd">手机号码：</span>
                    <input type="text" name="userphone" value="${not empty userphone?userphone : sessionScope[WhConstance.SESS_USER_KEY].phone}" placeholder="请输入手机号码" readonly="true">
                    <span class="color"></span>
                </p>
                <p class="clearfix">
                    <span class="hd">预定场次：</span>
                    <span class="text"><fmt:formatDate value="${roomtime.timeday}" pattern="yyyy-MM-dd"></fmt:formatDate> <fmt:formatDate value="${roomtime.timestart}" pattern="HH:mm"></fmt:formatDate>-<fmt:formatDate value="${roomtime.timeend}" pattern="HH:mm"></fmt:formatDate></span>
                </p>
                <p class="clearfix"><span class="hd">预订用途：</span>
                    <textarea class="purpose" maxlength="200" name="purpose" placeholder="请输入预订用途">${purpose}</textarea>
                    <span class="color"></span>
                </p>
            </div>
        </form>
        <div class="explain">
            <div class="explain-desc">
                <h3>预订须知</h3>
                <p>1、预定订单提交审核之后，请及时与场地管理人员联系；</p>
                <p>2、预订成功后，开始当日请提前入场，避免拥堵；</p>
                <%--<p>2、如需退订，请在预订时段开始前办理相关手续；</p>--%>
                <%--<p>3、如累计超过2次预订却没有到场者，将取消本年度预订资格；</p>--%>
                <p>3、如遇非人为可控因素或重大天气等影响，导致预订无法进行，举办方有权利延后或取消预订，并以短信和站内信形式告知预订人办理相关手续；</p>
                <p>4、最终解释权归举办方。</p>
            </div>
        </div>
    </div>
    <div class="submit clearfix">
        <div class="jf-checbox" id="checkbox1">
            <div class="checbox1">
                <input type="checkbox" value="1" id="checkboxOneInput">
                <label for="checkboxOneInput"><i class="selectx"></i></label>
            </div>
            <a href="javascript:void(0)">我已阅读并接受预订须知相关条款</a>
        </div>
        <a href="javascript:void(0)" id="submit-order" class="submit-order">提交订单</a>
    </div>
</div>
<!--主体结束-->

<script>
    $(function () {
        $('.submit').on("click", 'a.submit-order.red', function(){
            $('#frm_roomorder1').submit();
        })
    });

    function formCheck() {
        var userphome = $('#frm_roomorder1').find("input[name='userphone']");
        var username = $('#frm_roomorder1').find("input[name='username']");
        var userpurpose = $('#frm_roomorder1').find("textarea[name='purpose']");

        userphome.next("span").text('');
        username.next("span").text('');
        userpurpose.next("span").text('');

        var viald = true;
        var focusTarget = null;

        var name = username.val();
        name = $.trim(name);
        if (!name){
            username.next("span").text("* 预定人为必填");
            focusTarget = focusTarget? focusTarget: username;
            viald = false;
        }
        if (name.length>20){
            username.next("span").text("* 预定人长度不能大于20个字符");
            focusTarget = focusTarget? focusTarget: username;
            viald = false;
        }

        var phome = userphome.val();
        phome = $.trim(phome);
        if (!phome){
            userphome.next("span").text("* 手机号码为必填");
            focusTarget = focusTarget? focusTarget: userphome;
            viald = false;
        }
        if (!/^1[3-8]+\d{9}$/.test(phome)){
            userphome.next("span").text("* 请输入11位数字的手机号码");
            focusTarget = focusTarget? focusTarget: userphome;
            viald = false;
        }

        var purpose = userpurpose.val();
        if (purpose.length>200){
            userpurpose.next("span").text("* 预定用途长度不能大于200个字符");
            focusTarget = focusTarget? focusTarget: userpurpose;
            viald = false;
        }


        if (!viald){
            focusTarget.focus();
        }else{
            userphome.val(phome);
            username.val(name);
        }
        return viald;
    }
</script>

<!--公共主底部开始-->
<%@include file="/pages/comm/agdfooter.jsp"%>
<!--公共主底部结束-END-->

</body>
</html>
