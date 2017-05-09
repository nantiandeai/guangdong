<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<% request.setAttribute("basePath", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath());%>
<% request.setAttribute("resourceid", request.getParameter("rsid")); %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>会员管理</title>
    <%@include file="/pages/comm/admin/header.jsp"%>
    <script type="text/javascript" charset="utf-8" src="${basePath}/static/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="${basePath}/static/ueditor/ueditor.all.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="${basePath}/static/ueditor/lang/zh-cn/zh-cn.js"></script>
    <script type="text/javascript">
        /** 性别  */
        function getSexData() {
            return [{"id":"0", "text":"女"}, {"id":"1", "text":"男"}];
        }

        /** 是否实名 */
        function FMTRealname(val) {
            var _arr = [{"id":"0", "text":"待完善"}, {"id":"1", "text":"已认证"}, {"id":"2", "text":"认证失败"}, {"id":"3", "text":"待认证"}];
            for(var i=0; i<_arr.length; i++){
                if(_arr[i].id == val){
                    val = _arr[i].text;
                }
            }
            return val;
        }
    </script>
</head>
<body>

<!-- 表格 -->
<table id="whgdg" title="会员管理" class="easyui-datagrid" style="display: none"
       data-options="fit:true, striped:true, rownumbers:true, fitColumns:true, singleSelect:true, pagination:true, toolbar:'#whgdg-tb', url:'${basePath}/admin/user/info/srchList4p'">
    <thead>
    <tr>
        <th data-options="field:'nickname', width:150">昵称</th>
        <th data-options="field:'phone', width:150">手机</th>
        <th data-options="field:'email', width:150">邮箱</th>
        <th data-options="field:'name', width:150">姓名</th>
        <th data-options="field:'isrealname', width:150, formatter:FMTRealname">实名认证</th>
        <th data-options="field:'_opt', width:200, formatter:WhgComm.FMTOpt, optDivId:'whgdg-opt'">操作</th>
    </tr>
    </thead>
</table>
<!-- 表格 END -->

<!-- 表格操作工具栏 -->
<div id="whgdg-tb" style="display: none;">
    <div class="whgdg-tb-srch">
        <input class="easyui-textbox" name="name" style="width: 150px;"  data-options="prompt:'请输入姓名',width:100,validType:'length[1,30]'"/>
        <input class="easyui-textbox" name="nickname" style="width: 150px;"  data-options="prompt:'请输入昵称',width:100,validType:'length[1,30]'"/>
        <input class="easyui-textbox" name="phone" style="width: 150px;"   data-options="prompt:'请输入手机',width:100,validType:'length[1,30]'"/>
        <input class="easyui-textbox" name="email" style="width: 150px;"  data-options="prompt:'请输入邮箱',width:100,validType:'length[1,30]'"/>
        <select class="easyui-combobox" name="isrealname" data-options="prompt:'请选择状态',value:'',width:100">
            <option value="0">待完善</option>
            <option value="1">已认证</option>
            <option value="2">已打回</option>
            <option value="3">待认证</option>
        </select>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="WhgComm.search('#whgdg', '#whgdg-tb');">查 询</a>
    </div>
</div>
<!-- 表格操作工具栏-END -->

<!-- 操作按钮 -->
<div id="whgdg-opt" style="display: none;">
    <shiro:hasPermission name="${resourceid}:view"><a href="javascript:void(0)" class="easyui-linkbutton" plain="true" method="doSee">查看</a></shiro:hasPermission>
    <%--<shiro:hasPermission name="${resourceid}:edit"><a href="javascript:void(0)" class="easyui-linkbutton" plain="true" method="doEdit">编辑</a></shiro:hasPermission>--%>
    <shiro:hasPermission name="${resourceid}:edit"><a href="javascript:void(0)" class="easyui-linkbutton" plain="true" validKey="isrealname" validVal="3" method="doCheck">实名审核</a></shiro:hasPermission>
    <shiro:hasPermission name="${resourceid}:del"> <a href="javascript:void(0)" class="easyui-linkbutton" plain="true" method="doDel">删除</a></shiro:hasPermission>
</div>
<!-- 操作按钮-END -->

<!-- script -->
<script type="text/javascript">

    /**
     * 编辑信息
     * @param idx 行下标
     */
    function doEdit(idx) {
        //var curRow = $('#whgdg').datagrid('getRows')[idx];
        //WhgComm.editDialog('${basePath}/admin/user/info/view/edit?id='+curRow.id);
    }

    /**
     * 查看资料
     * @param idx 行下标
     */
    function doSee(idx) {
        var curRow = $('#whgdg').datagrid('getRows')[idx];
        WhgComm.editDialog('${basePath}/admin/user/info/view/edit?id='+curRow.id+"&onlyshow=1");
    }

    /**
     * 删除
     * @param idx
     */
    function doDel(idx) {
        var curRow = $('#whgdg').datagrid('getRows')[idx];
        $.messager.confirm('提示', '您确定要删除此记录吗？', function(r){
            if (r){
                $.ajax({
                    type: "POST",
                    cache: false,
                    url: getFullUrl('/admin/user/info/del'),
                    data: {ids : curRow.id},
                    success: function(Json){
                        if(Json && Json.success == '1'){
                            $('#whgdg').datagrid('reload');
                        } else {
                            $.messager.alert('提示', '操作失败:'+Json.errormsg+'！', 'error');
                        }
                    }
                });
            }
        });
    }

    /**
     * 审核实名认证
     * @param idx
     */
    function doCheck(idx) {
        var curRow = $('#whgdg').datagrid('getRows')[idx];
        WhgComm.editDialog('${basePath}/admin/user/info/view/auth?id='+curRow.id+"&onlyshow=1");
    }
</script>
<!-- script END -->
</body>
</html>