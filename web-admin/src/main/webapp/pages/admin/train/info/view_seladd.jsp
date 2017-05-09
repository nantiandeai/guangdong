<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<% request.setAttribute("basePath", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath());%>
<% request.setAttribute("resourceid", request.getParameter("rsid")); %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>资讯公告管理</title>
    <%@include file="/pages/comm/admin/header.jsp"%>
</head>
<body>
<!-- 表格 -->
<table id="whgdg" title="资讯公告管理" class="easyui-datagrid" style="display: none"
       data-options="fit:true, striped:true, rownumbers:true, fitColumns:true, singleSelect:false, checkOnSelect:true, selectOnCheck:true, pagination:true, toolbar:'#whgdg-tb', url:'${basePath}/admin/info/srchcolinfoList4p?entity=1&entityid=${entityid}'">
    <thead>
    <tr>
        <th data-options="width: 30, checkbox: true, field:'checkbox' ">全选</th>
        <th data-options="field:'clnftltle', width:100">标题</th>
        <th data-options="field:'clnfcrttime', width:100, formatter:WhgComm.FMTDateTime">创建时间</th>
    </tr>
    </thead>
</table>
<!-- 表格 END -->

<!-- 表格操作工具栏 -->
<div id="whgdg-tb" style="display: none;">
    <div class="whgdg-tb-srch">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="add()">提交</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" onclick="WhgComm.editDialogClose();">返回</a>
    </div>
</div>
<!-- 表格操作工具栏-END -->




<script>
    function add(idx) {
        var entityid = ${entityid};
        var rows = $("#whgdg").datagrid("getSelections");
        var ids = _split = "";//id1,id2,id3
        for (var i = 0; i<rows.length; i++){
            ids += _split+rows[i].clnfid;
            _split = ",";
        }
        $.messager.confirm("确认信息", "确认要将所选择的资讯公告添加到", function(r){
            if (r){
                $.messager.progress();
                $.post('${basePath}/admin/info/seladd', {ids: ids,entityid:entityid}, function(data){
                    $("#whgdg").datagrid('reload');
                    if (!data.success || data.success != "1"){
                        $.messager.alert("错误", data.errormsg||'操作失败', 'error');
                    }
                    $.messager.progress('close');
                }, 'json');
            }
        })
    }


</script>
</body>
</html>
