<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<% request.setAttribute("basePath", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath());%>
<% request.setAttribute("resourceid", request.getParameter("rsid")); %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="/pages/comm/admin/header.jsp"%>
    <meta charset="UTF-8">
    <title>课程管理</title>

</head>
<body>
<!-- 表格 -->
<table id="whgdg" title="${pageTitle}" class="easyui-datagrid" style="display: none"
       data-options="fit:true, striped:true, rownumbers:true, fitColumns:true, singleSelect:false, checkOnSelect:true, selectOnCheck:true, pagination:true, toolbar:'#whgdg-tb', url:'${basePath}/admin/train/course/srchList4p?id=${id}'">
    <thead>
    <tr>
        <th data-options="field:'starttime', width:160, formatter:WhgComm.FMTDateTime">课程开始时间</th>
        <th data-options="field:'endtime', width:160, formatter:WhgComm.FMTDateTime">课程结束时间</th>
        <th data-options="field:'state', width:160, formatter:WhgComm.FMTState">状态</th>
        <th data-options="field:'_opt', width:160, formatter:WhgComm.FMTOpt, optDivId:'whgdg-opt'">操作</th>
    </tr>
    </thead>
</table>
<!-- 表格 END -->

<!-- 表格操作工具栏 -->
<div id="whgdg-tb" style="display: none;">

    <div class="whgdg-tb-srch">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" onclick="WhgComm.editDialogClose();">返回</a>
    </div>
</div>
<!-- 表格操作工具栏-END -->

<!-- 操作按钮 -->
<div id="whgdg-opt" style="display: none;">
    <a href="javascript:void(0)" class="easyui-linkbutton" validKey="state" validVal="1" plain="true" method="edit">编辑</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" validKey="state" validVal="1" plain="true" method="publishoff">取消</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" validKey="state" validVal="0" plain="true" method="publish">启用</a>
</div>
<!-- 操作按钮-END -->

<!-- 添加表单 -->
<div id="whgwin-add" style="display: none">
    <form id="whgff" class="whgff" method="post">
        <input type="hidden" name="id"/>
        <div class="whgff-row">
            <div class="whgff-row-label" style="width: 25%"><i>*</i>开始时间：</div>
            <div class="whgff-row-input" style="width: 75%"><input class="easyui-datetimebox" name="starttime" style="width:90%; height:32px" data-options="required:true"></div>
        </div>

        <div class="whgff-row">
            <div class="whgff-row-label" style="width: 25%"><i>*</i>结束时间：</div>
            <div class="whgff-row-input" style="width: 75%"><input class="easyui-datetimebox" name="endtime" style="width:90%; height:32px" data-options="required:true"></div>
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

    /**
     * 编辑
     * @param idx
     */
    function edit(idx){
        $('#whgwin-add').dialog({
            title: '编辑课程',
            cache: false,
            modal: true,
            width: '400',
            height: '200',
            maximizable: true,
            resizable: true,
            buttons: '#whgwin-add-btn',
            onOpen : function () {
                var _trastarttime = WhgComm.FMTDateTime(${starttime});
                var _traendtime = WhgComm.FMTDateTime(${endtime}+" 23:59:59");


                var rows = $("#whgdg").datagrid('getRows');
                var row = rows[idx];
                var id = row.id;
                var traid = row.traid;
                //格式化日期
                var _data = $.extend({}, row,
                        {
                            starttime : WhgComm.FMTDateTime(row.starttime),
                            endtime : WhgComm.FMTDateTime(row.endtime),
                        });
                if (row){
                    $('#whgff').form('load', _data);
                    $('#whgff').form({

                        url : '${basePath}/admin/train/course/edit',
                        onSubmit : function(param) {
                            param.traid = traid;
                            var isValid = $(this).form('enableValidation').form('validate');
                            var starttime = $("#whgff").find("input[name='starttime']").val();
                            var endtime = $("#whgff").find("input[name='endtime']").val();

                            if((starttime > endtime)){
                                $.messager.alert("提示", "课程开始时间应早于结束时间！");
                                isValid = false;
                            }
                            if(starttime < _trastarttime || endtime > _traendtime){
                                $.messager.alert("提示", "课程时间应在培训周期内！");
                                isValid = false;
                            }
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
                                $.messager.alert("提示", data.errormsg);
                            }
                        }
                    });
                }
                $("#btn").off("click").on("click",function () {$('#whgff').submit();});
            }
        });
    }

    /**取消*/
    function publishoff(idx) {
        var row = $("#whgdg").datagrid("getRows")[idx];
        $.messager.confirm("确认信息", "确定要取消选中的项吗？", function(r){
            if (r){
                $.messager.progress();
                var params = {ids: row.id, formstates: 1, tostate: 0};
                $.post('${basePath}/admin/train/course/updstate', params, function(data){
                    $("#whgdg").datagrid('reload');
                    if (!data.success || data.success != "1"){
                        $.messager.alert("错误", data.errormsg||'操作失败', 'error');
                    }
                    $.messager.progress('close');
                }, 'json');
            }
        })
    }
    /**启用*/
    function publish(idx) {
        var row = $("#whgdg").datagrid("getRows")[idx];
        $.messager.confirm("确认信息", "确定要启用选中的项吗？", function(r){
            if (r){
                $.messager.progress();
                var params = {ids: row.id, formstates: 0, tostate: 1};
                $.post('${basePath}/admin/train/course/updstate', params, function(data){
                    $("#whgdg").datagrid('reload');
                    if (!data.success || data.success != "1"){
                        $.messager.alert("错误", data.errormsg||'操作失败', 'error');
                    }
                    $.messager.progress('close');
                }, 'json');
            }
        })
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
                $.post('${basePath}/admin/train/course/del', {id: row.id}, function(data){
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
