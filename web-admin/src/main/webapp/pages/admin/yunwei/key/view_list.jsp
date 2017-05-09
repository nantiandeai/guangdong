<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% request.setAttribute("basePath", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath());%>
<% request.setAttribute("resourceid", request.getParameter("rsid")); %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>关键字管理</title>
    <%@include file="/pages/comm/admin/header.jsp"%>
    <c:choose>
        <c:when test="${classify == 1}">
            <c:set var="pageTitle" value="场馆关键字配置"></c:set>
        </c:when>
        <c:when test="${classify == 2}">
            <c:set var="pageTitle" value="活动室关键字配置"></c:set>
        </c:when>
        <c:when test="${classify == 3}">
            <c:set var="pageTitle" value="活动关键字配置"></c:set>
        </c:when>
        <c:when test="${classify == 4}">
            <c:set var="pageTitle" value="培训关键字配置"></c:set>
        </c:when>
        <c:when test="${classify == 5}">
            <c:set var="pageTitle" value="资讯关键字配置"></c:set>
        </c:when>
        <c:otherwise>
            <c:set var="pageTitle" value="关键字配置"></c:set>
        </c:otherwise>
    </c:choose>
    <title>${pageTitle}</title>
</head>
<body>
<!-- 表格 -->
<table id="whgdg" title="${pageTitle}" class="easyui-datagrid" style="display: none"
       data-options="fit:true, striped:true, rownumbers:true, fitColumns:true, singleSelect:false, checkOnSelect:true, selectOnCheck:true, pagination:true, toolbar:'#whgdg-tb', url:'${basePath}/admin/yunwei/key/srchList4p?type=${classify}'">
    <thead>
    <tr>
        <th data-options="field:'name', width:160">关键字名称</th>
        <th data-options="field:'idx', width:160">排序值</th>
        <th data-options="field:'_opt', width:160, formatter:WhgComm.FMTOpt, optDivId:'whgdg-opt'">操作</th>
    </tr>
    </thead>
</table>
<!-- 表格 END -->

<!-- 表格操作工具栏 -->
<div id="whgdg-tb" style="display: none;">
    <div class="whgd-gtb-btn">
        <shiro:hasPermission name="${resourceid}:add"><a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="add();">添加</a></shiro:hasPermission>
    </div>
    <div class="whgdg-tb-srch">
        <input class="easyui-textbox" style="width: 200px;" name="name" data-options="prompt:'请输入关键字名称', validType:'length[1,32]'" />
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="WhgComm.search('#whgdg', '#whgdg-tb');">查 询</a>
    </div>
</div>
<!-- 表格操作工具栏-END -->

<!-- 操作按钮 -->
<div id="whgdg-opt" style="display: none;">
    <shiro:hasPermission name="${resourceid}:edit"><a href="javascript:void(0)" class="easyui-linkbutton" plain="true" method="edit">编辑</a></shiro:hasPermission>
    <%--<shiro:hasPermission name="${resourceid}:off"> <a href="javascript:void(0)" class="easyui-linkbutton" plain="true" method="sort">排序</a></shiro:hasPermission>--%>
    <shiro:hasPermission name="${resourceid}:del"> <a href="javascript:void(0)" class="easyui-linkbutton" plain="true" method="del">删除</a></shiro:hasPermission>
</div>
<!-- 操作按钮-END -->

<!-- 添加表单 -->
<div id="whgwin-add" style="display: none">
    <form id="whgff" class="whgff" method="post">
        <input type="hidden" name="id"/>
        <div class="whgff-row">
            <div class="whgff-row-label" style="width: 25%"><i>*</i>名称：</div>
            <div class="whgff-row-input" style="width: 75%"><input class="easyui-textbox" name="name" style="width:90%; height:32px" data-options="required:true,validType:'length[0,20]'"></div>
        </div>

        <div class="whgff-row">
            <div class="whgff-row-label" style="width: 25%"><i>*</i>排序值：</div>
            <div class="whgff-row-input" style="width: 75%"><input class="easyui-numberspinner" name="idx" style="width:90%; height:32px" data-options="required:true,validType:'length[0,10]'"></div>
        </div>
    </form>
</div>
<div id="whgwin-add-btn" style="text-align: center; display: none">
    <div style="display: inline-block; margin: 0 auto">
        <a href="#" class="easyui-linkbutton" iconCls="icon-save" id="btn" >保 存</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#whgwin-add').dialog('close')">关 闭</a>
    </div>
</div>
<!-- 添加表单 END -->



<script>

    /** 添加关键字 */
    function add(){
        $('#whgwin-add').dialog({
            title: '关键字管理-添加关键字',
            cache: false,
            modal: true,
            width: '400',
            height: '200',
            maximizable: true,
            resizable: true,
           // href: '${basePath}/admin/system/cult/view/add',
            buttons: '#whgwin-add-btn',
            onOpen: function () {
                //init Form
                $('#whgff').form({
                    url : '${basePath}/admin/yunwei/key/add?type=${classify}',
                    onSubmit : function(param) {
                        var isValid = $(this).form('enableValidation').form('validate');
                        if(isValid){
                            $.messager.progress();
                        }else{
                            $("#btn").off("click").one("click",function () { $('#whgff').submit(); });
                        }
                        return isValid
                    },
                    success : function(data) {
                        $.messager.progress('close');
                        var Json = $.parseJSON(data);
                        if(Json.success == "1"){
                            $('#whgdg').datagrid('reload');
                          //  $.messager.alert("提示", "操作成功");
                            $('#whgwin-add').dialog('close');
                        }else{
                            $.messager.alert("提示", data.errorMsg);
                        }

                    }
                });
                $('#whgff').form("clear");
                $("#btn").off("click").one("click",function () { $('#whgff').submit(); });

            }
        });
    }

    /** 更新关键字方法 */
    function edit(idx){
        $('#whgwin-add').dialog({
            title: '关键字管理-编辑关键字',
            cache: false,
            modal: true,
            width: '400',
            height: '200',
            maximizable: true,
            resizable: true,
            // href: '${basePath}/admin/system/cult/view/add',
            buttons: '#whgwin-add-btn',
            onOpen : function () {
                var rows = $("#whgdg").datagrid('getRows');
                var row = rows[idx];
                var id = row.id;
                if (row){
                    $('#whgff').form('load', row);
                    $('#whgff').form({
                        url : '${basePath}/admin/yunwei/key/edit',
                        onSubmit : function(param) {
                            var isValid = $(this).form('enableValidation').form('validate');
                            if(isValid){
                                $.messager.progress();
                            }else{
                                $("#btn").off("click").one("click",function () { $('#whgff').submit(); });
                            }
                            return isValid
                        },
                        success : function(data) {
                            $.messager.progress('close');
                            var Json = $.parseJSON(data);
                            if(Json.success == "1"){
                                $('#whgdg').datagrid('reload');
                              //  $.messager.alert("提示", "操作成功");
                                $('#whgwin-add').dialog('close');
                            }else{
                                $.messager.alert("提示", data.errorMsg);
                            }

                        }
                    });

                }
                $("#btn").off("click").on("click",function () {$('#whgff').submit();});
            }
        });
    }

    /*
     * 删除关键字 */
    function del(idx){
        var rows = $("#whgdg").datagrid('getRows');
        var row = rows[idx];
        var id = row.id;
        $.messager.confirm('确认对话框', '您确认要删除吗？', function(r){
            if (r){
                $.ajax({
                    type: "POST",
                    url: "${basePath}/admin/yunwei/key/del",
                    data: {id : id},
                    success: function(data){
                        //var Json = $.parseJSON(data);
                        if(data.success == "1"){
                            $('#whgdg').datagrid('reload');
                            $.messager.alert("提示", "操作成功");
                        }else{
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
