<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<% request.setAttribute("basePath", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath());%>
<% request.setAttribute("resourceid", request.getParameter("rsid")); %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <c:choose>
        <c:when test="${type eq 'edit'}">
            <c:set var="pageTitle" value="在线点播编辑列表"></c:set>
        </c:when>
        <c:when test="${type eq 'check'}">
            <c:set var="pageTitle" value="在线点播审核列表"></c:set>
        </c:when>
        <c:when test="${type eq 'publish'}">
            <c:set var="pageTitle" value="在线点播发布列表"></c:set>
        </c:when>
        <c:otherwise>
            <c:set var="pageTitle" value="在线点播"></c:set>
        </c:otherwise>
    </c:choose>

    <title>${pageTitle}</title>

    <%@include file="/pages/comm/admin/header.jsp"%>
</head>
<body>
<!-- 表格 -->
<table id="whgdg" title="${pageTitle}" class="easyui-datagrid" style="display: none"
       data-options="fit:true, striped:true, rownumbers:true, fitColumns:true, singleSelect:false, checkOnSelect:true, selectOnCheck:true, pagination:true, toolbar:'#whgdg-tb', url:'${basePath}/admin/drsc/srchPagging?type=${type}'">
    <thead>
    <tr>
        <th data-options="field:'drsctitle', width:100">资源标题</th>
        <th data-options="field:'drscarttyp', width:100, formatter:WhgComm.FMTArtType">艺术类型</th>
        <th data-options="field:'drscpic', width:100, formatter:WhgComm.FMTImg">资源图片</th>
        <th data-options="field:'drscfrom', width:100">资源来源</th>
        <th data-options="field:'drsccrttime', width:100, formatter:WhgComm.FMTDateTime">创建时间</th>
        <th data-options="field:'drscstate', width:100, formatter:traStateFMT" >状态</th>
        <th data-options="field:'_opt', width:400, formatter:WhgComm.FMTOpt,fixed:true, optDivId:'whgdg-opt'">操作</th>
    </tr>
    </thead>
</table>
<!-- 表格 END -->

<!-- 表格操作工具栏 -->
<div id="whgdg-tb" style="display: none;">

    <div class="whgdg-tb-srch">
        <input class="easyui-combobox" style="width: 200px;" name="drscarttyp" prompt='请选择艺术类型' data-options="valueField:'id', textField:'text', data:WhgComm.getArtType()"/>
        <input class="easyui-textbox" style="width: 200px;" name="drsctitle" prompt='请输入标题' data-options="validType:'length[1,30]'"/>
        <select class="easyui-combobox" name="teacherstate" prompt='请选择状态',>
            <option value="">全部</option>
            <option value="0">未审核</option>
            <option value="2">已审核</option>
            <option value="3">已发布</option>
        </select>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="WhgComm.search('#whgdg', '#whgdg-tb');">查 询</a>
    </div>
</div>
<!-- 表格操作工具栏-END -->

<!-- 操作按钮 -->
<div id="whgdg-opt" style="display:none" >
    <shiro:hasPermission name="${resourceid}:view">      <a href="javascript:void(0)" class="easyui-linkbutton" plain="true" method="doSee">查看</a></shiro:hasPermission>
    <shiro:hasPermission name="${resourceid}:edit">      <a href="javascript:void(0)" class="easyui-linkbutton" plain="true" validKey="drscstate" validVal="0,1" method="doUpd">编辑</a></shiro:hasPermission>
    <shiro:hasPermission name="${resourceid}:checkon">   <a href="javascript:void(0)" class="easyui-linkbutton" plain="true" validKey="drscstate" validVal="0,1" method="doCheck">审核</a></shiro:hasPermission>
    <shiro:hasPermission name="${resourceid}:back">      <a href="javascript:void(0)" class="easyui-linkbutton" plain="true" validKey="drscstate" validVal="2"   method="doUnCheck">打回</a></shiro:hasPermission>
    <shiro:hasPermission name="${resourceid}:publish">   <a href="javascript:void(0)" class="easyui-linkbutton" plain="true" validKey="drscstate" validVal="2"   method="doRelease">发布</a></shiro:hasPermission>
    <shiro:hasPermission name="${resourceid}:publishoff"><a href="javascript:void(0)" class="easyui-linkbutton" plain="true" validKey="drscstate" validVal="3"   method="doUnRelease">撤销发布</a></shiro:hasPermission>
    <shiro:hasPermission name="${resourceid}:del">       <a href="javascript:void(0)" class="easyui-linkbutton" plain="true" validKey="drscstate" validVal="0,1" method="doDel">删除</a></shiro:hasPermission>
</div>
<!-- 操作按钮-END -->


<script>

    /**
     * 编辑
     * @param idx
     */
    function doUpd(idx){
        var row = $("#whgdg").datagrid("getRows")[idx];
        WhgComm.editDialog('${basePath}/admin/drsc/index/add?id='+row.drscid);
    }

    /**
     * 查看
     * @param idx
     */
    function doSee(idx){
        var row = $("#whgdg").datagrid("getRows")[idx];
        WhgComm.editDialog('${basePath}/admin/drsc/index/add?targetShow=1&id='+row.drscid);
    }


    /** 删除数字资源 */
    function doDel(idx){
        var row = $("#whgdg").datagrid("getRows")[idx];
        $.messager.confirm('确认对话框', '确定要删除此数字资源吗？', function(r){
            if (r){
                $.ajax({
                    type: "POST",
                    url: getFullUrl('/admin/drsc/del'),
                    data: {id : row.drscid},
                    success: function(Json){
                        if(Json && Json.success == '0'){
                            $.messager.alert('提示', '操作成功！', 'info');
                            $('#whgdg').datagrid('reload');
                            winform.closeWin();
                        } else {
                            $.messager.alert('提示', '操作失败:'+Json.errmsg+'！', 'error');
                        }
                    }
                });
            }
        });
    }/** 删除数字资源-END */


    /** 修改数字资源状态 */
    function _updState(fromState, toState, drscid){
        var drscids = '';
        if(drscid){//单个操作
            drscids = drscid;
        }else{//批量操作
            rows = $("#zxDG").datagrid("getChecked");
            if(rows.length < 1){
                $.messager.alert('警告', '请选择要操作的数据！', 'warning');return;
            }
            var _spt = '';
            for(var i=0; i<rows.length; i++){
                drscids += _spt+rows[i].drscid;
                _spt = ',';
            }
        }
        $.ajax({
            type: "POST",
            url: getFullUrl('/admin/drsc/updState'),
            data: {ids : drscids, fromState: fromState, toState: toState},
            success: function(Json){
                if(Json && Json.success == '0'){
                    $.messager.alert('提示', '操作成功！', 'info');
                    $('#whgdg').datagrid('reload');
                    winform.closeWin();
                } else {
                    $.messager.alert('提示', '操作失败:'+Json.errmsg+'！', 'error');
                }
            }
        });
    }/** 修改数字资源状态-END */

    /** 审批数字资源 */
    function doCheck(index){
        var row = $("#whgdg").datagrid("getRows")[index];
        _updState(0, 2, row.drscid);
    }/** 审批数字资源-END */

    /** 取消审批数字资源 */
    function doUnCheck(index){
        var row = $("#whgdg").datagrid("getRows")[index];
        _updState(2, 0, row.drscid);
    }/** 取消审批数字资源-END */

    /** 发布数字资源 */
    function doRelease(index){
        var row = $("#whgdg").datagrid("getRows")[index];
        _updState(2, 3, row.drscid);
    }/** 发布数字资源-END */

    /** 取消发布数字资源 */
    function doUnRelease(index){
        var row = $("#whgdg").datagrid("getRows")[index];
        _updState(3, 2, row.drscid);
    }/** 取消发布数字资源-END */

</script>
</body>
</html>
