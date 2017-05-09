<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% request.setAttribute("basePath", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath());%>
<% request.setAttribute("resourceid", request.getParameter("rsid")); %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>评论管理</title>
    <%@include file="/pages/comm/admin/header.jsp"%>
    <c:choose>
        <c:when test="${type == 1}">
            <c:set var="pageTitle" value="活动评论管理"></c:set>
        </c:when>
        <c:when test="${type == 2}">
            <c:set var="pageTitle" value="培训评论管理"></c:set>
        </c:when>
        <c:when test="${type == 3}">
            <c:set var="pageTitle" value="培训师资评论管理"></c:set>
        </c:when>
        <c:when test="${type == 4}">
            <c:set var="pageTitle" value="培训点播评论管理"></c:set>
        </c:when>
        <c:when test="${type == 5}">
            <c:set var="pageTitle" value="志愿培训评论管理"></c:set>
        </c:when>
        <c:when test="${type == 6}">
            <c:set var="pageTitle" value="场馆评论管理"></c:set>
        </c:when>
        <c:when test="${type == 7}">
            <c:set var="pageTitle" value="场馆活动室评论管理"></c:set>
        </c:when>
        <c:otherwise>
            <c:set var="pageTitle" value="评论管理"></c:set>
        </c:otherwise>
    </c:choose>
    <title>${pageTitle}</title>
</head>
<body>
<!-- 表格 -->
<table id="whgdg" title="${pageTitle}" class="easyui-datagrid" style="display: none"
       data-options="fit:true, striped:true, rownumbers:true, fitColumns:true, singleSelect:false, checkOnSelect:true, selectOnCheck:true, pagination:true, toolbar:'#whgdg-tb', url:'${basePath}/admin/yunwei/comment/srchList4p?rmreftyp=${type}'">
    <thead>
    <tr>
        <th data-options="field:'rmtitle', width:220">标题</th>
        <th data-options="field:'nickname', width:120">评论人</th>
        <th data-options="field:'rmdate', width:160,formatter:WhgComm.FMTDateTime">评论时间</th>
        <th data-options="field:'rmstate', width:160,formatter:FMTState">评论状态</th>
        <th data-options="field:'ishf', width:100">回复数</th>
        <th data-options="field:'_opt', width:220, formatter:WhgComm.FMTOpt, optDivId:'whgdg-opt'">操作</th>
    </tr>
    </thead>
</table>
<!-- 表格 END -->

<!-- 表格操作工具栏 -->
<div id="whgdg-tb" style="display: none;">
    <div class="whgd-gtb-btn">

    </div>
    <div class="whgdg-tb-srch">
        <input class="easyui-textbox" style="width: 200px;" name="rmcontent" data-options="prompt:'请输入评论信息', validType:'length[1,32]'" />
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="WhgComm.search('#whgdg', '#whgdg-tb');">查 询</a>
    </div>
</div>
<!-- 表格操作工具栏-END -->

<!-- 操作按钮 -->
<div id="whgdg-opt" style="display: none;">
    <shiro:hasPermission name="${resourceid}:add"><a href="javascript:void(0)" class="easyui-linkbutton"  plain="true" method="viewComment">查看评论</a></shiro:hasPermission>
    <shiro:hasPermission name="${resourceid}:add"><a href="javascript:void(0)" class="easyui-linkbutton"  plain="true" validFun="ishf" method="viewReply">查看回复</a></shiro:hasPermission>
    <shiro:hasPermission name="${resourceid}:add"><a href="javascript:void(0)" class="easyui-linkbutton"  plain="true" method="toReply" validKey="rmstate" validVal="1">回复</a></shiro:hasPermission>
    <shiro:hasPermission name="${resourceid}:add"><a href="javascript:void(0)" class="easyui-linkbutton"  plain="true" method="del">删除</a></shiro:hasPermission>
    <shiro:hasPermission name="${resourceid}:add"><a href="javascript:void(0)" class="easyui-linkbutton"  plain="true" method="toAudit">审核</a></shiro:hasPermission>
</div>
<!-- 操作按钮-END -->
<script>

    /**
     * 是否显示查看回复按钮
     */
    function ishf(idx) {
        var curRow = $('#whgdg').datagrid('getRows')[idx];
        if(curRow.ishf > 0){
            return true;
        }
        return false;
    }
    /**
     * 查看评论
     */
    function viewComment(idx) {
        var curRow = $('#whgdg').datagrid('getRows')[idx];
        WhgComm.editDialog('${basePath}/admin/yunwei/comment/viewComment?id='+curRow.rmid);
    }

    /**
     * 查看回复
     * @param idx
     */
    function viewReply(idx) {
        var curRow = $('#whgdg').datagrid('getRows')[idx];
        WhgComm.editDialog('${basePath}/admin/yunwei/comment/viewReply?id='+curRow.rmid);
    }

    /**
     * 回复
     * @param val
     * @returns {*}
     * @constructor
     */
    function toReply(idx) {
        var curRow = $('#whgdg').datagrid('getRows')[idx];
        WhgComm.editDialog('${basePath}/admin/yunwei/comment/toReply?id='+curRow.rmid);
    }

    /**
     * 删除
     * @param idx
     */
    function del(idx){
        var curRow = $("#whgdg").datagrid("getRows")[idx];
        var confireStr = '确定要删除该条评论吗？';
        $.messager.confirm("确认信息", confireStr, function(r){
            if (r){
                $.messager.progress();
                $.post('${basePath}/admin/yunwei/comment/del', {id: curRow.rmid}, function(data){
                    $("#whgdg").datagrid('reload');
                    if (!data.success || data.success != "1"){
                        $.messager.alert("错误", data.errormsg||'操作失败', 'error');
                    }
                    $.messager.progress('close');
                }, 'json');
            }
        })
    }
    function toAudit(idx){
        var curRow = $("#whgdg").datagrid("getRows")[idx];
        WhgComm.editDialog('${basePath}/admin/yunwei/comment/toAudit?id='+curRow.rmid);
    }
    //状态转换
    function FMTState(val)  {
        if(val == 0){
            return "待审核";
        }else if(val == 1){
            return "审核通过";
        }else{
            return "审核不通过";
        }
    }
</script>
</body>
</html>
