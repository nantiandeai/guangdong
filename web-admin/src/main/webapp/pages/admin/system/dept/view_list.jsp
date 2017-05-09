<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<% request.setAttribute("basePath", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath());%>
<% request.setAttribute("resourceid", request.getParameter("rsid")); %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>部门管理</title>
    <%@include file="/pages/comm/admin/header.jsp"%>
    <script type="text/javascript" charset="utf-8" src="${basePath}/static/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="${basePath}/static/ueditor/ueditor.all.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="${basePath}/static/ueditor/lang/zh-cn/zh-cn.js"></script>
</head>
<body>
<!-- 表格 -->
<table id="whgdg" title="部门管理" class="easyui-treegrid" style="display: none"
       data-options="fit:true, rownumbers:true, fitColumns:true, singleSelect:true, loadFilter:myLoadFilter, idField:'id', treeField:'name', toolbar:'#whgdg-tb', url:'${basePath}/admin/system/dept/srchList'">
    <thead>
    <tr>
        <th data-options="field:'name', width:100">名称</th>
        <th data-options="field:'code', width:50">编码</th>
        <th data-options="field:'crtdate', width:50, formatter:WhgComm.FMTDateTime">创建时间</th>
        <th data-options="field:'_opt', width:150, formatter:WhgComm.FMTTreeGridOpt, optDivId:'whgdg-opt'">操作</th>
    </tr>
    </thead>
</table>
<!-- 表格 END -->

<!-- 表格操作工具栏 -->
<div id="whgdg-tb" style="display: none;">
    <div class="whgd-gtb-srch">
        <shiro:hasPermission name="${resourceid}:add"><a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="doAdd()">添加部门</a></shiro:hasPermission>
        <shiro:hasPermission name="${resourceid}:view"><a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" onclick="$('#whgdg').treegrid('reload')">刷 新</a></shiro:hasPermission>
    </div>
</div>
<!-- 表格操作工具栏-END -->

<!-- 操作按钮 -->
<div id="whgdg-opt" style="display: none;">
    <shiro:hasPermission name="${resourceid}:add"> <a href="javascript:void(0)" class="easyui-linkbutton" plain="true" validKey="_state" validVal="1" method="doAddChild">添加子部门</a></shiro:hasPermission>
    <shiro:hasPermission name="${resourceid}:edit"><a href="javascript:void(0)" class="easyui-linkbutton" plain="true" validKey="_state" validVal="1" method="doEdit">编辑</a></shiro:hasPermission>
    <shiro:hasPermission name="${resourceid}:del"> <a href="javascript:void(0)" class="easyui-linkbutton" plain="true" validFun="validDel" method="doDel">删除</a></shiro:hasPermission>
</div>
<!-- 操作按钮-END -->

<!-- 添加编辑表单 -->
<div id="whgwin">
    <form id="whgff" class="whgff" method="post">
        <input type="hidden" name="id" value="" >
        <input type="hidden" name="pid" value="" >
        <div class="whgff-row">
            <div class="whgff-row-label" style="width: 25%"><i>*</i>名称：</div>
            <div class="whgff-row-input" style="width: 75%"><input class="easyui-textbox" id="whgff-row-input-name" name="name" style="width:250px; height:32px" data-options="prompt:'请输入部门名称,3到30字字符', required:true, validType:'length[3,30]'"></div>
        </div>
    </form>
</div>
<div id="whgwin-btn" style="text-align: center; display: none">
    <div style="display: inline-block; margin: 0 auto">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" id="whgwin-btn-save">保 存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#whgwin').dialog('close')">关 闭</a>
    </div>
</div>
<!-- 添加编辑表单 END -->

<!-- script -->
<script type="text/javascript">
    /**
     * treeGrid数据中增加_parentId和_state属性
     */
    function myLoadFilter(data, parentId) {
        var rows = data.rows;
        for(var i=0; i<rows.length; i++){
            if(rows[i].pid){
                rows[i]._parentId = rows[i].pid;
            }
            rows[i]._state = rows[i].state;
        }
        return data;
    }

    /**
     * 初始表单
     */
    function __initForm(action){
        $('#whgff').form({
            novalidate: true,
            url : action,
            onSubmit : function(param) {
                var _valid = $(this).form('enableValidation').form('validate')
                if(_valid){
                    $.messager.progress();
                }else{
                    //失败时再注册提交事件
                    $('#whgwin-btn-save').off('click').one('click', function () { $('#whgff').submit(); });
                }
                return _valid;
            },
            success : function(data) {
                $.messager.progress('close');
                var Json = eval('('+data+')');
                if(Json && Json.success == '1'){
                    $('#whgdg').treegrid('reload');
                    $('#whgwin').dialog('close');
                } else {
                    $.messager.alert('提示', '操作失败:'+Json.errormsg+'！', 'error');
                    $('#whgwin-btn-save').off('click').one('click', function () { $('#whgff').submit(); });
                }
            }
        });
    }

    /**
     * 添加部门
     */
    function doAdd() {
        __initForm(getFullUrl('/admin/system/dept/add'));
        $('#whgff').form('clear');
        $('#whgwin-btn-save').off('click').one('click', function () { $('#whgff').submit(); });
        $('#whgwin').dialog({
            title: '部门管理-添加部门',
            modal: true,
            width: 420,
            height: 150,
            buttons: '#whgwin-btn'
        });
    }

    /**
     * 添加子部门
     */
    function doAddChild(pid) {
        var node = $('#whgdg').treegrid('find', pid);
        __initForm(getFullUrl('/admin/system/dept/add'));
        $('#whgff').form('clear');
        $('#whgff input[name="pid"]').val(pid);
        $('#whgwin-btn-save').off('click').one('click', function () { $('#whgff').submit(); });
        $('#whgwin').dialog({
            title: '部门管理-添加【'+node.name+'】的子部门',
            modal: true,
            width: 420,
            height: 150,
            buttons: '#whgwin-btn'
        });
    }

    /**
     * 是否禁用删除按钮
     */
    function validDel(id) {
        var node = $('#whgdg').treegrid('find', id);
        if(typeof node.children == 'undefined'){
            return true;
        }
        return false;
    }

    /**
     * 编辑信息
     * @param idx 行下标
     */
    function doEdit(id) {
        var node = $('#whgdg').treegrid('find', id);
        __initForm(getFullUrl('/admin/system/dept/edit'));
        $('#whgff').form('clear');
        $('#whgff input[name="id"]').val(id);
        $('#whgff input[name="pid"]').val(node.pid || '');
        $('#whgff-row-input-name').textbox('setValue', node.name);
        $('#whgwin-btn-save').off('click').one('click', function () { $('#whgff').submit(); });
        $('#whgwin').dialog({
            title: '部门管理-编辑部门资料',
            modal: true,
            width: 420,
            height: 150,
            buttons: '#whgwin-btn'
        });
    }

    /**
     * 删除
     * @param idx
     */
    function doDel(id) {
        $.messager.confirm('提示', '您确定要删除此记录吗？', function(r){
            if (r){
                $.ajax({
                    type: "POST",
                    url: getFullUrl('/admin/system/dept/del'),
                    data: {ids : id},
                    cache: false,
                    success: function(Json){
                        if(Json && Json.success == '1'){
                            $('#whgdg').treegrid('reload');
                        } else {
                            $.messager.alert('提示', '操作失败:'+Json.errormsg+'！', 'error');
                        }
                    }
                });
            }
        });
    }
</script>
<!-- script END -->
</body>
</html>