<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% request.setAttribute("basePath", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath());%>
<% request.setAttribute("resourceid", request.getParameter("rsid")); %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>轮播图管理</title>
    <%@include file="/pages/comm/admin/header.jsp" %>
    <c:choose>
        <c:when test="${clazz == 1}">
            <c:set var="pageTitle" value="PC首页轮播图配置"></c:set>
        </c:when>
        <c:when test="${clazz == 2}">
            <c:set var="pageTitle" value="APP首页轮播图配置"></c:set>
        </c:when>
        <c:when test="${clazz == 3}">
            <c:set var="pageTitle" value="PC首页广告图配置"></c:set>
        </c:when>
        <c:when test="${clazz == 4}">
            <c:set var="pageTitle" value="APP首页广告图图配置"></c:set>
        </c:when>
        <c:when test="${clazz == 5}">
            <c:set var="pageTitle" value="PC培训首页轮播图配置"></c:set>
        </c:when>
        <c:when test="${clazz == 6}">
            <c:set var="pageTitle" value="PC志愿服务首页风采展示"></c:set>
        </c:when>
        <c:when test="${clazz == 7}">
            <c:set var="pageTitle" value="PC非遗首页广告图配置"></c:set>
        </c:when>
        <c:when test="${clazz == 8}">
            <c:set var="pageTitle" value="PC志愿服务首页广告图配置"></c:set>
        </c:when>
        <c:when test="${clazz == 9}">
            <c:set var="pageTitle" value="PC非遗首页轮播图配置"></c:set>
        </c:when>
        <c:otherwise>
            <c:set var="pageTitle" value="轮播图配置"></c:set>
        </c:otherwise>
    </c:choose>
    <title>${pageTitle}</title>
</head>
<body>
<!-- 表格 -->
<table id="whgdg" title="${pageTitle}" class="easyui-datagrid" style="display: none"
       data-options="fit:true, striped:true, rownumbers:true, fitColumns:true, singleSelect:true, checkOnSelect:true, selectOnCheck:true, pagination:false, toolbar:'#whgdg-tb', url:'${basePath}/admin/yunwei/lbt/srchList?type=${clazz}'">
    <thead>
    <tr>
        <th data-options="field:'name', width:60">标题</th>
        <th data-options="field:'picture', width:60 , formatter:WhgComm.FMTImg">图片</th>
        <th data-options="field:'url', width:60">URL</th>
        <th data-options="field:'state', width:60, formatter:WhgComm.FMTState">状态</th>
        <th data-options="field:'_opt', width:60, formatter:WhgComm.FMTOpt, optDivId:'whgdg-opt'">操作</th>
    </tr>
    </thead>
</table>
<!-- 表格 END -->

<!-- 表格操作工具栏 -->
<div id="whgdg-tb" style="display: none;">
    <div class="whgd-gtb-btn">
        <shiro:hasPermission name="${resourceid}:add"><a href="javascript:void(0)" class="easyui-linkbutton"
                                                         iconCls="icon-add"
                                                         onclick="doAdd();">添加</a></shiro:hasPermission>
    </div>
</div>
<!-- 表格操作工具栏-END -->

<!-- 操作按钮 -->
<div id="whgdg-opt" style="display: none;">
    <shiro:hasPermission name="${resourceid}:edit"><a href="javascript:void(0)" class="easyui-linkbutton" plain="true" validKey="state" validVal="1"
                                                      method="doEdit">编辑</a></shiro:hasPermission>
    <shiro:hasPermission name="${resourceid}:off"> <a href="javascript:void(0)" class="easyui-linkbutton" plain="true"
                                                      validKey="state" validVal="1"
                                                      method="doOff">停用</a></shiro:hasPermission>
    <shiro:hasPermission name="${resourceid}:on"> <a href="javascript:void(0)" class="easyui-linkbutton" plain="true"
                                                     validKey="state" validVal="0"
                                                     method="doOn">启用</a></shiro:hasPermission>
    <shiro:hasPermission name="${resourceid}:del"> <a href="javascript:void(0)" class="easyui-linkbutton" plain="true"
                                                      validKey="state" validVal="0"
                                                      method="doDel">删除</a></shiro:hasPermission>

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
    /** 添加图片方法 */
    function doAdd() {
        WhgComm.editDialog('${basePath}/admin/yunwei/lbt/view/add/${clazz}');
    }
    /** 添加图片方法-END */

    /** 编辑图片方法 */
    function doEdit(idx) {
        var curtRow = $('#whgdg').datagrid('getRows')[idx];
        WhgComm.editDialog('${basePath}/admin/yunwei/lbt/view/edit/${clazz}?id=' + curtRow.id);
    }
    /** 编辑图片方法-END */

    /**删除 */
    function doDel(idx) {
        var rows = $("#whgdg").datagrid('getRows');
        var row = rows[idx];
        var id = row.id;
        $.messager.confirm('确认对话框', '您确认要删除吗？', function (r) {
            if (r) {
                $.ajax({
                    type: "POST",
                    url: "${basePath}/admin/yunwei/lbt/del",
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

    /** 启用角色 */
    function doOn(idx) {
        var curtRow = $('#whgdg').datagrid('getRows')[idx];
        updataState(curtRow.id, '1');
    }
    ;


    /** 停用角色 */
    function doOff(idx) {
        var curtRow = $('#whgdg').datagrid('getRows')[idx];
        updataState(curtRow.id, '0');

    }
    ;

    /** 修改角色状态 */
    function updataState(id, state) {
        $.ajax({
            url: "${basePath}/admin/yunwei/lbt/updstate",
            data: {id: id, state: state},
            success: function (data) {
                //var Json = $.parseJSON(data);
                if (data.success == "1") {
                    $('#whgdg').datagrid('reload');
//                    $.messager.alert("提示", "操作成功");
                } else {
                    $.messager.alert("提示", data.data.errorMsg);
                }

            }

        });
    }
</script>
</body>
</html>
