<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<% request.setAttribute("basePath", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath());%>
<% request.setAttribute("resourceid", request.getParameter("rsid")); %>
<%
    request.setAttribute("nowSys", new java.util.Date());
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>查看活动预订</title>

    <%@include file="/pages/comm/admin/header.jsp" %>
    <link rel="stylesheet" href="${basePath}/static/admin/css/bootstrap.css"/>
    <link rel="stylesheet" href="${basePath}/static/admin/Font-Awesome/css/font-awesome.min.css"/>
    <link rel="stylesheet" href="${basePath}/static/admin/css/build.css"/>

</head>
<body class="body_add">
<form id="whgff" method="post" class="whgff">

    <h2>查看活动预订</h2>
    <div class="whgff-row">
        <div class="whgff-row-label">活动名：</div>
        <div class="whgff-row-input">
            <input class="easyui-textbox" value="${act.name}" style="width:600px; height:32px"
                   data-options="readonly:true">
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label">活动开始时间：</div>
        <div class="whgff-row-input">
            <input class="easyui-textbox" value="<fmt:formatDate value='${act.starttime}' pattern="yyyy-MM-dd"></fmt:formatDate>" style="width:600px; height:32px"
                   data-options="readonly:true">
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label">活动结束时间：</div>
        <div class="whgff-row-input">
            <input class="easyui-textbox" value="<fmt:formatDate value='${act.endtime}' pattern="yyyy-MM-dd"></fmt:formatDate>" style="width:600px; height:32px"
                   data-options="readonly:true">
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label">预定活动时间：</div>
        <div class="whgff-row-input">
            <input class="easyui-textbox" value="<fmt:formatDate value='${time.playdate}' pattern="yyyy-MM-dd"></fmt:formatDate>" style="width:600px; height:32px"
                   data-options="readonly:true">
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label">预订时段：</div>
        <div class="whgff-row-input">
            <input class="easyui-textbox" value="${time.playstime} - ${time.playetime}" style="width:600px; height:32px"
                   data-options="readonly:true">
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label">订单编号：</div>
        <div class="whgff-row-input">
            <input class="easyui-textbox" value="${order.ordernumber}" style="width:600px; height:32px"
                   data-options="readonly:true">
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label">预订人姓名：</div>
        <div class="whgff-row-input">
            <input class="easyui-textbox" value="${order.ordername}" style="width:600px; height:32px"
                   data-options="readonly:true">
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label">预定人手机号：</div>
        <div class="whgff-row-input">
            <input class="easyui-textbox" value="${order.orderphoneno}" style="width:600px; height:32px"
                   data-options="readonly:true">
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label">短信发送状态：</div>
        <div class="whgff-row-input">
            <input class="easyui-textbox js-state" stateFn="getSmsSendStatus" stateVal="${order.ordersmsstate}" value="${order.ordersmsstate}" style="width:600px; height:32px"
                   data-options="readonly:true">
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label">生成订单时间：</div>
        <div class="whgff-row-input">
            <input class="easyui-textbox" value="<fmt:formatDate value='${order.ordercreatetime}' pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>" style="width:600px; height:32px"
                   data-options="readonly:true">
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label">取票状态：</div>
        <div class="whgff-row-input">
            <input class="easyui-textbox js-state" stateFn="setTicketsStatus" stateVal="${order.ticketstatus}" value="${order.ticketstatus}" style="width:600px; height:32px"
                   data-options="readonly:true">
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label">票务状态：</div>
        <div class="whgff-row-input">
            <input class="easyui-textbox js-purpose" style="width:600px; height:200px"
                   data-options="readonly:true, multiline: true">
            <textarea class="ref-purpose" style="display: none;"><c:forEach items="${ticketList}" var="item" varStatus="vs"><c:if test="${vs.index gt 0}">, </c:if> ${item.seatcode} (${item.ticketstatus eq 1? '已验票':'未验票'})</c:forEach>
            </textarea>
        </div>
    </div>

</form>

<div class="whgff-but" style="width: 400px; margin:20px 0px 50px 350px">
    <a class="easyui-linkbutton whgff-but-clear"></a>
</div>

<script>

    $(function(){
        var buts = $("div.whgff-but");
        //处理返回
        buts.find("a.whgff-but-clear").linkbutton({
            text: '返 回',
            iconCls: 'icon-undo',
            onClick: function(){
                window.parent.$('#whgdg').datagrid('reload');
                WhgComm.editDialogClose();
            }
        });

        $(".js-state").each(function(){
            var stateFn = $(this).attr("stateFn");
            var state = $(this).attr("stateVal");
            var fmtvalue = window[stateFn].call(null, state);
            $(this).textbox("setValue", fmtvalue);
        });

        $(".js-purpose").textbox("setText", $(".ref-purpose").val());
    });

    function getSmsSendStatus(ordersmsstate) {
        ordersmsstate = Number(ordersmsstate);
        if(1 == ordersmsstate){
            return "未发送";
        }else if(2 == ordersmsstate){
            return "发送成功";
        }else if(3 == ordersmsstate){
            return "发送失败";
        }else {
            return "未知";
        }
    }

    function setTicketsStatus(ticketstatus) {
        var nowSys = '${nowSys}';
        ticketstatus = Number(ticketstatus);
        if(1 == ticketstatus){
            var playdate = '${time.playdate}';
            var playetime = '${time.playetime}';

            var now = new Date( Date.parse(nowSys) );
            var play = new Date( Date.parse(playdate) );
            var ptime = /(\d{2}):(\d{2})/.exec( playetime );
            if (ptime){
                var hh = String(ptime[1]);
                var mm = String(ptime[2]);
                play.setHours(Number(hh));
                play.setMinutes(Number(mm));
            }

            return play>now?"未取票" : "已过期";
        }else if(2 == ticketstatus){
            return "已取票";
        }else if(3 == ticketstatus){
            return "已取消";
        }else {
            return "未知";
        }
    }

</script>

</body>
</html>
