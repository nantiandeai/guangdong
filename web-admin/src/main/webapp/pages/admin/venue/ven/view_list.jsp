<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<% request.setAttribute("basePath", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath());%>
<% request.setAttribute("resourceid", request.getParameter("rsid")); %>
<% request.setAttribute("resourceopts", request.getParameter("opts")); %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <%@include file="/pages/comm/admin/header.jsp"%>

    <c:choose>
        <c:when test="${type eq 'edit'}">
            <c:set var="pageTitle" value="编辑场馆信息"></c:set>
        </c:when>
        <c:when test="${type eq 'check'}">
            <c:set var="pageTitle" value="审核场馆信息"></c:set>
        </c:when>
        <c:when test="${type eq 'publish'}">
            <c:set var="pageTitle" value="发布场馆信息"></c:set>
        </c:when>
        <c:when test="${type eq 'del'}">
            <c:set var="pageTitle" value="回收站"></c:set>
        </c:when>
        <c:otherwise>
            <c:set var="pageTitle" value="场馆信息"></c:set>
        </c:otherwise>
    </c:choose>

    <title>${pageTitle}</title>

    <script src="${basePath}/static/admin/js/whgmodule-gridopts.js"></script>
</head>
<body>
    <table id="whgdg" class="easyui-datagrid" title="${pageTitle}" style="display: none;"
           toolbar="#tb" pagination=true pageSize=30 pageList="[10,20,30,50,100,200]" loadMsg="数据加载中，请稍候。。。"
           data-options="fit:true, striped:true, rownumbers:true, fitColumns:false, singleSelect:false, checkOnSelect:true, selectOnCheck:true,
           url:'${basePath}/admin/venue/srchList4p?__pageType=${type}'">
        <thead>
            <tr>
                <th data-options="<%--width: 30,--%> checkbox: true, field:'checkbox' ">全选</th>
                <th data-options="<%--width:100,--%> sortable:false, field:'title' ">名称</th>
                <th data-options="<%--width:100,--%> sortable:false, field:'area',formatter:WhgComm.FMTAreaType ">区域</th>
                <th data-options="<%--width:100,--%> sortable:false, field:'address' ">地址</th>
                <th data-options="<%--width:100,--%> sortable: true, field:'etype',formatter:WhgComm.FMTVenueType ">分类</th>
                <th data-options="<%--width:100,--%> sortable:false, field:'contacts' ">联系人</th>
                <th data-options="<%--width:100,--%> sortable:false, field:'phone' ">联系电话</th>
                <%--<th data-options="width:100, sortable: true, field:'level' ">星级</th>--%>
                <%--<th data-options="width:100, sortable: true, field:'datebuild', formatter:WhgComm.FMTDate ">建馆时间</th>--%>
                <th data-options="<%--width:100,--%> sortable: true, field:'state', formatter:WhgComm.FMTBizState ">状态</th>
                <th data-options="<%--width:120,--%> sortable: true, field:'statemdfdate', formatter:WhgComm.FMTDateTime ">状态变更时间</th>
                <th data-options="<%--width:'${type eq 'publish'?'430':'300'}', fixed:true,--%> field:'id', formatter:WhgComm.FMTOpt, optDivId:'whgdg-opt'">操作</th>
            </tr>
        </thead>
    </table>

    <div id="tb" style="display: none;">
        <input class="easyui-textbox" name="title" prompt="请输入场馆名称" data-options="width:120">
        <select class="easyui-combobox" name="state" prompt="请选择状态" panelHeight="auto" limitToList="true"
            data-options="width:120, value:'', valueField:'id', textField:'text', data:WhgComm.getBizState()">
        </select>
        <c:if test="${type eq 'publish'}">
            <select class="easyui-combobox" name="recommend" prompt="是否推荐" panelHeight="auto" limitToList="true"
                    data-options="width:120, value:'', valueField:'id', textField:'text', data:[{id:'1',text:'推荐'},{id:'0',text:'非推荐'}]">
            </select>
        </c:if>

        <a class="easyui-linkbutton" iconCls="icon-search" onclick="WhgComm.search('#whgdg', '#tb');">查 询</a>
    </div>


    <!-- 操作按钮 -->
    <div id="whgdg-opt" style="display: none;">

        <shiro:hasPermission name="${resourceid}:view"><a plain="true" method="whgListTool.view">查看</a></shiro:hasPermission>
        <shiro:hasPermission name="${resourceid}:edit"><a plain="true" method="whgListTool.edit" validKey="state" validVal="1,9,2,4,5">编辑</a></shiro:hasPermission>
        <shiro:hasPermission name="${resourceid}:checkgo"><a plain="true" method="whgListTool.checkgo" validKey="state" validVal="1,5">送审</a></shiro:hasPermission>

        <shiro:hasPermission name="${resourceid}:checkon"><a plain="true" method="whgListTool.checkon" validKey="state" validVal="9">审核通过</a></shiro:hasPermission>
        <shiro:hasPermission name="${resourceid}:publish"><a plain="true" method="whgListTool.publish" validKey="state" validVal="2,4">发布</a></shiro:hasPermission>
        <shiro:hasPermission name="${resourceid}:publishoff"><a plain="true" method="whgListTool.publishoff" validKey="state" validVal="6">撤销发布</a></shiro:hasPermission>

        <%--<shiro:hasPermission name="${resourceid}:checkoff"><a plain="true" method="whgListTool.checkoff" validKey="state" validVal="9">审核打回</a></shiro:hasPermission>--%>
        <shiro:hasPermission name="${resourceid}:back"><a plain="true" method="whgListTool.back" validKey="state" validVal="9,2">打回</a></shiro:hasPermission>

        <shiro:hasPermission name="${resourceid}:edit"><a plain="true" method="whgListTool.resource">资源管理</a></shiro:hasPermission>

        <shiro:hasPermission name="${resourceid}:recommend"><a plain="true" method="whgListTool.recommend" validFun="whgListTool.recommendVfun">推荐</a></shiro:hasPermission>
        <shiro:hasPermission name="${resourceid}:recommendoff"><a plain="true" method="whgListTool.recommendoff" validFun="whgListTool.recommendOffVfun">取消推荐</a></shiro:hasPermission>

        <shiro:hasPermission name="${resourceid}:undel"><a plain="true" method="whgListTool.undel" validKey="delstate" validVal="1">还原</a></shiro:hasPermission>
        <c:choose>
            <c:when test="${type eq 'del'}">
                <shiro:hasPermission name="${resourceid}:del"><a plain="true" method="whgListTool.del" validKey="delstate" validVal="1">删除</a></shiro:hasPermission>
            </c:when>
            <c:otherwise>
                <shiro:hasPermission name="${resourceid}:del"><a plain="true" method="whgListTool.del" validKey="state" validVal="1,9,2,4,5">回收</a></shiro:hasPermission>
            </c:otherwise>
        </c:choose>

    </div>
    <!-- 操作按钮-END -->

    <!-- 编辑表单 -->
    <div id="whgwin-edit"></div>
    <!-- 编辑表单 END -->

<script>

    var whgListTool = new Gridopts();

    Gridopts.prototype.resource = function (idx) {
        var row = this.__getGridRow(idx);
        WhgComm.editDialog('${basePath}/admin/train/resource/view/list?reftype=3&id=' + row.id);
    }

</script>
</body>
</html>
