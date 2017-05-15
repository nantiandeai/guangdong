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
       data-options="fit:true, striped:true, rownumbers:true, fitColumns:true, singleSelect:false, checkOnSelect:true, selectOnCheck:true, pagination:true, toolbar:'#whgdg-tb', url:'${basePath}/admin/info/srchList4p?entityid=${entityid}'">
    <thead>
    <tr>
        <th data-options="field:'clnftltle', width:100">标题</th>
        <th data-options="field:'clnfcrttime', width:100, formatter:WhgComm.FMTDateTime">创建时间</th>
        <th data-options="width:100, sortable:false, field:'clnfstata', formatter:traStateFMT ">状态</th>
        <th data-options="field:'_opt', width:300, formatter:WhgComm.FMTOpt,fixed:true, optDivId:'whgdg-opt'">操作</th>
    </tr>
    </thead>
</table>
<!-- 表格 END -->

<!-- 表格操作工具栏 -->
<div id="whgdg-tb" style="display: none;">
    <div class="whgdg-tb-srch">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="add()">添加</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="seladd()">选择添加</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" onclick="WhgComm.editDialogClose();">返回</a>
    </div>
</div>
<!-- 表格操作工具栏-END -->

<!-- 操作按钮 -->
<div id="whgdg-opt" style="display: none;">
    <a href="javascript:void(0)" class="easyui-linkbutton" plain="true" method="view">查看</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" plain="true" method="edit">编辑</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" plain="true" method="del">删除</a>
</div>
<!-- 操作按钮-END -->

<!-- 选择添加表格 -->
<div id="whgwin-add" style="display: none">
    <!-- 表格 -->
    <table id="whgdg-add" title="" class="easyui-datagrid" style="display: none"
           data-options="fit:true, striped:true, rownumbers:true, fitColumns:true, singleSelect:false, checkOnSelect:true, selectOnCheck:true, pagination:true, url:'${basePath}/admin/info/srchcolinfoList4p?entity=${entity}&entityid=${entityid}'">
        <thead>
        <tr>
            <th data-options="width: 30, checkbox: true, field:'checkbox' ">全选</th>
            <th data-options="field:'clnftltle', width:100">标题</th>
            <th data-options="field:'clnfcrttime', width:100, formatter:WhgComm.FMTDateTime">创建时间</th>
            <th data-options="width:100, sortable:false, field:'clnfstata', formatter:traStateFMT ">状态</th>
        </tr>
        </thead>
    </table>
    <!-- 表格 END -->
</div>
<!-- 表格操作工具栏 -->
<div id="whgwin-add-btn" style="display: none;">
    <div class="whgdg-tb-srch">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" id="btn">提交</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#whgwin-add').dialog('close')">关 闭</a>
    </div>
</div>
<!-- 表格操作工具栏-END -->


<script>

/**
 * 添加
 */
function add(){ window.location.href = '${basePath}/admin/info/view/add?entityid=${entityid}&entity=${entity}'; }

/**
 * 选择添加
 */
function seladd(){
    $('#whgwin-add').dialog({
        title: '选择添加资讯公告',
        cache: false,
        modal: true,
        width: '1000',
        height: '500',
        maximizable: true,
        resizable: true,
        buttons: '#whgwin-add-btn',
        onOpen: function () {
            //window.location.href = '${basePath}/admin/info/view/seladd?entityid=${entityid}';
            var entityid = ${entityid};

            $("#btn").off("click").one("click",function () {
                var rows = $("#whgdg-add").datagrid("getSelections");

                var ids = _split = "";//id1,id2,id3
                for (var i = 0; i<rows.length; i++){
                    ids += _split+rows[i].clnfid;
                    _split = ",";
                }
                $.messager.confirm("确认信息", "确认要添加所选择的资讯信息吗？", function(r){
                    if (r){
                        $.messager.progress();
                        $.post('${basePath}/admin/info/seladd', {ids: ids,entityid:entityid}, function(data){
                            $("#whgdg-add").datagrid('reload');
                            $('#whgwin-add').dialog('close')
                            if (!data.success || data.success != "1"){
                                $.messager.alert("错误", data.errormsg||'操作失败', 'error');
                            }
                            $.messager.progress('close');
                        }, 'json');
                    }
                })
            });



        }
    })

}

/**
 * 编辑
 * @param idx
 */
function edit(idx){
    var row = $("#whgdg").datagrid("getRows")[idx];
    WhgComm.editDialog('${basePath}/admin/info/view/edit?id='+row.id+'&entityid=${entityid}&entity=${entity}&clnfid='+row.clnfid);
}

/**
 * 查看
 * @param idx
 */
function view(idx){
    var row = $("#whgdg").datagrid("getRows")[idx];
    WhgComm.editDialog('${basePath}/admin/info/view/edit?targetShow=1&id='+row.id+'&entityid=${entityid}&entity=${entity}');
}


/**
 * 删除
 * @param idx
 */
function del(idx){
    var row = $("#whgdg").datagrid("getRows")[idx];
    var confireStr = '确定要删除选中的项吗？'
    $.messager.confirm("确认信息", confireStr, function(r){
        if (r){
            $.messager.progress();
            $.post('${basePath}/admin/info/del', {id: row.id}, function(data){
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
