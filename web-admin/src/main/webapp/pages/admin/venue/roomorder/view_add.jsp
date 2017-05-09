<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<% request.setAttribute("basePath", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath());%>
<% request.setAttribute("resourceid", request.getParameter("rsid")); %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>查看预订</title>

    <%@include file="/pages/comm/admin/header.jsp" %>
    <link rel="stylesheet" href="${basePath}/static/admin/css/bootstrap.css"/>
    <link rel="stylesheet" href="${basePath}/static/admin/Font-Awesome/css/font-awesome.min.css"/>
    <link rel="stylesheet" href="${basePath}/static/admin/css/build.css"/>

</head>
<body class="body_add">
<form id="whgff" method="post" class="whgff">
    <h2>查看预订</h2>
    <div class="whgff-row">
        <div class="whgff-row-label">活动室：</div>
        <div class="whgff-row-input">
            <input class="easyui-textbox" value="${room.title}" style="width:600px; height:32px"
                   data-options="readonly:true">
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label">订单号：</div>
        <div class="whgff-row-input">
            <input class="easyui-textbox" value="${order.orderid}" style="width:600px; height:32px"
                   data-options="readonly:true">
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label">预定人：</div>
        <div class="whgff-row-input">
            <input class="easyui-textbox" value="${order.ordercontact}" style="width:600px; height:32px"
                   data-options="readonly:true">
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label">联系手机：</div>
        <div class="whgff-row-input">
            <input class="easyui-textbox" value="${order.ordercontactphone}" style="width:600px; height:32px"
                   data-options="readonly:true">
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label">下单时间：</div>
        <div class="whgff-row-input">
            <input class="easyui-textbox" value="<fmt:formatDate value='${order.crtdate}' pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>" style="width:600px; height:32px"
                   data-options="readonly:true">
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label">预订时段：</div>
        <div class="whgff-row-input">
            <input class="easyui-textbox" value="<fmt:formatDate value='${order.timeday}' pattern="yyyy-MM-dd"></fmt:formatDate> <fmt:formatDate value='${order.timestart}' pattern="HH:mm"></fmt:formatDate> - <fmt:formatDate value='${order.timeend}' pattern="HH:mm"></fmt:formatDate>" style="width:600px; height:32px"
                   data-options="readonly:true">
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label">订单状态：</div>
        <div class="whgff-row-input">
            <input class="easyui-textbox js-state" value="${order.state}" style="width:600px; height:32px"
                   data-options="readonly:true">
        </div>
    </div>

    <c:if test="${order.state eq 2}">
        <div class="whgff-row">
            <div class="whgff-row-label">拒绝原因：</div>
            <div class="whgff-row-input">
                <input class="easyui-textbox" value="${order.ordersummary}" style="width:600px; height:150px"
                       data-options="readonly:true, multiline: true">
            </div>
        </div>
    </c:if>

    <div class="whgff-row">
        <div class="whgff-row-label">取票状态：</div>
        <div class="whgff-row-input">
            <input class="easyui-textbox" value="${order.ticketstatus eq 2? '已取票' : '未取票'}" style="width:600px; height:32px"
                   data-options="readonly:true">
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label">验票状态：</div>
        <div class="whgff-row-input">
            <input class="easyui-textbox" value="${order.ticketcheckstate eq 2? '已验票' : '未验票'}" style="width:600px; height:32px"
                   data-options="readonly:true">
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label">预订用途：</div>
        <div class="whgff-row-input">
            <input class="easyui-textbox js-purpose" style="width:600px; height:200px"
                   data-options="readonly:true, multiline: true">
            <textarea class="ref-purpose" style="display: none;">${order.purpose}</textarea>
        </div>
    </div>

</form>

<div class="whgff-but" style="width: 400px; margin:20px 0px 50px 350px">
    <a class="easyui-linkbutton whgff-but-clear">清 空</a>
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

        var state = $(".js-state").textbox("getValue");
        state = orderState(parseInt(state));
        $(".js-state").textbox("setValue", state);

        $(".js-purpose").textbox("setText", $(".ref-purpose").val());
    });

    function orderState(v){
        switch (v){
            case 0 : return '申请预定';
            case 1 : return '取消申请';
            case 2 : return '审核拒绝';
            case 3 : return '预定成功';
            default: return v;
        }
    }

</script>

</body>
</html>
