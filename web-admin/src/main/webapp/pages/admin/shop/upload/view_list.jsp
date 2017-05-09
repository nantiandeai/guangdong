<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% request.setAttribute("basePath", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath());%>
<% request.setAttribute("resourceid", request.getParameter("rsid")); %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>资料上传管理</title>
    <%@include file="/pages/comm/admin/header.jsp" %>
</head>
<body>
<!-- 表格 -->
<table id="whgdg" title="资料上传管理" class="easyui-datagrid" style="display: none"
       data-options="fit:true, striped:true, rownumbers:true, fitColumns:true, singleSelect:true, checkOnSelect:true, selectOnCheck:true, pagination:true, toolbar:'#whgdg-tb', url:'${basePath}/admin/shop/seletup?uptype=${refid}'">
    <thead>
    <tr>
        <th data-options="field:'upname', width:60">资源标题</th>
        <th data-options="field:'uplink', width:80">资源地址</th>
        <th data-options="field:'uptime', width:60,formatter:WhgComm.FMTDateTime">上传时间</th>
        <th data-options="field:'upstate', width:40, formatter:WhgComm.FMTState">状态</th>
        <th data-options="field:'_opt',width:80, formatter:WhgComm.FMTOpt, optDivId:'whgdg-opt'">操作</th>
    </tr>
    </thead>
</table>
<!-- 表格 END -->

<!-- 表格操作工具栏 -->
<div id="whgdg-tb" style="display: none;">
    <div class="whgd-gtb-btn">
        <shiro:hasPermission name="${resourceid}:add"><a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add"  onclick="addzysc();" data-options="size:'small'">添加</a></shiro:hasPermission>
        <a href="javascript:void(0)" class="easyui-linkbutton whgff-but-clear" iconCls="icon-undo" onclick="WhgComm.editDialogClose()" data-options="size:'small'">返 回</a>
    </div>
</div>
<!-- 表格操作工具栏-END -->

<!-- 操作按钮 -->
<div id="whgdg-opt" style="display: none;">
    <a href="javascript:void(0)" validKey="upstate" validVal="0" method="doEdit">编辑</a>
    <a href="javascript:void(0)" validKey="upstate" validVal="0" method="delup">删除</a>
    <a href="javascript:void(0)" validKey="upstate" validVal="0" method="checktype">启用</a>
    <a href="javascript:void(0)" validKey="upstate" validVal="1" method="stoptype">停用</a>
</div>
<!-- 操作按钮-END -->

<!-- 添加表单 -->
<div id="whgwin-add"></div>
<!-- 添加表单 END -->

<script>
    /**
     * 添加
     */
    function addzysc() {
        WhgComm.editDialog('${basePath}/admin/shop/uploads/view/add?refid='+${refid});
    }

    /**
     * 编辑
     * @param idx 行下标
     */
    function doEdit(idx) {
        var curRow = $('#whgdg').datagrid('getRows')[idx];
        WhgComm.editDialog('${basePath}/admin/shop/uploads/view/edit?upid='+curRow.upid);
    }
    /**
     * 删除
     */
    function delup(idx){
        var row = $('#whgdg').datagrid('getRows')[idx];
        var id = row.upid;
        var link = row.uplink;
        $.messager.confirm('确认对话框', '您确认要删除吗？', function(r){
            if (r){
                $.ajax({
                    type: "POST",
                    url: getFullUrl('/admin/shop/deluploda'),
                    data: {upid : id, uplink:link},
                    success: function(msg){
                        $('#whgdg').datagrid('reload');
                    }
                });
            }
        });
    }
    /**
     * 启动状态
     */
    function checktype(idx){
        var row = $('#whgdg').datagrid('getRows')[idx];
        var id = row.upid;
        $.messager.confirm('确认对话框', '确定要更改状态？', function(r) {
            if (r) {
                $.ajax({
                    type: "POST",
                    url : getFullUrl("/admin/shop/upstate"),
                    data : {upid:id, upstate:1},
                    success:function(data){
                        if (data=="success"){
                            $("#whgdg").datagrid("reload");
                        }else{
                            $.messager.alert("失败了", "操作失败！");
                        }
                    }
                })
            }
        })
    }
    /**
     * 停用
     */
    function stoptype(idx){
        var row = $('#whgdg').datagrid('getRows')[idx];
        var id = row.upid;
        $.messager.confirm('确认对话框', '确定要更改状态？', function(r) {
            if (r) {
                $.ajax({
                    type: "POST",
                    url : getFullUrl("/admin/shop/upstate"),
                    data : {upid:id, upstate:0},
                    success:function(data){
                        if (data=="success"){
                            $("#whgdg").datagrid("reload");
                        }else{
                            $.messager.alert("失败了", "操作失败！");
                        }
                    }
                })
            }
        })
    }
</script>
</body>
</html>
