<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<% request.setAttribute("basePath", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath());%>
<% request.setAttribute("resourceid", request.getParameter("rsid")); %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>短信模板管理</title>
    <%@include file="/pages/comm/admin/header.jsp" %>
</head>
<body>
<!-- 表格 -->
<table id="whgdg" title="短信模板管理" class="easyui-datagrid" style="display: none"
       data-options="fit:true, striped:true, rownumbers:true, fitColumns:true, singleSelect:true, checkOnSelect:true, selectOnCheck:true, pagination:true, toolbar:'#whgdg-tb', url:'${basePath}/admin/yunwei/smstmp/srchList4p'">
    <thead>
    <tr>
        <th data-options="field:'code', width:60">短信编码</th>
        <th data-options="field:'content', width:60">短信模板内容</th>
        <th data-options="field:'_opt', width:60, formatter:WhgComm.FMTOpt, optDivId:'whgdg-opt'">操作</th>
    </tr>
    </thead>
</table>
<!-- 表格 END -->

<!-- 表格操作工具栏 -->
<div id="whgdg-tb" style="display: none;">
    <div class="whgd-gtb-btn">
        <shiro:hasPermission name="${resourceid}:add"><a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-add"onclick="doAdd();">添加</a></shiro:hasPermission>
    </div>
    <div class="whgdg-tb-srch">
        <input class="easyui-textbox" style="width: 200px;" name="code" data-options="prompt:'请输入短信编码'" />
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="WhgComm.search('#whgdg', '#whgdg-tb');">查 询</a>
    </div>
</div>
<!-- 表格操作工具栏-END -->

<!-- 操作按钮 -->
<div id="whgdg-opt" style="display: none;">
    <shiro:hasPermission name="${resourceid}:edit"><a href="javascript:void(0)" class="easyui-linkbutton" plain="true" method="doEdit">编辑</a></shiro:hasPermission>
    <shiro:hasPermission name="${resourceid}:del"> <a href="javascript:void(0)" class="easyui-linkbutton" plain="true" method="doDel">删除</a></shiro:hasPermission>
</div>
<!-- 操作按钮-END -->

<!-- 添加表单 -->
<div id="whgwin-add"></div>
<%--<div id="whgwin-add-btn" style="text-align: center">--%>
<%--<div style="display: inline-block; margin: 0 auto">--%>
<%--<a href="#" class="easyui-linkbutton" iconCls="icon-save" onclick="submitForm()">保 存</a>--%>
<%--<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#whgwin-add').dialog('close')">关 闭</a>--%>
<%--</div>--%>
<%--</div>--%>
<!-- 添加表单 END -->

<script>
    /** 添加短信模板方法 */
    function doAdd() {
        WhgComm.editDialog('${basePath}/admin/yunwei/smstmp/view/add/');
    }
    /** 添加短信模板方法-END */

    /** 编辑短信模板方法 */
    function doEdit(idx) {
        var curtRow = $('#whgdg').datagrid('getRows')[idx];
        WhgComm.editDialog('${basePath}/admin/yunwei/smstmp/view/edit?id=' + curtRow.id);
    }
    /** 编辑短信模板方法-END */

    /**删除 */
    function doDel(idx) {
        var rows = $("#whgdg").datagrid('getRows');
        var row = rows[idx];
        var id = row.id;
        $.messager.confirm('确认对话框', '您确认要删除吗？', function (r) {
            if (r) {
                $.ajax({
                    type: "POST",
                    url: "${basePath}/admin/yunwei/smstmp/del",
                    data: {id: id},
                    success: function (data) {
                        //var Json = $.parseJSON(data);
                        if (data.success == "1") {
                            $('#whgdg').datagrid('reload');
                        } else {
                            $.messager.alert("提示", data.errorMsg);
                        }

                    }
                });
            }
        });
    }


</script>
</body>
</html>
