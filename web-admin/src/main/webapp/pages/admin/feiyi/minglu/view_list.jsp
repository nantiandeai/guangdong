<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<% request.setAttribute("basePath", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath());%>
<% request.setAttribute("resourceid", request.getParameter("rsid")); %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>名录项目管理</title>
    <link rel="stylesheet" href="${basePath}/static/admin/css/bootstrap.css"/>
    <link rel="stylesheet" href="${basePath}/static/admin/Font-Awesome/css/font-awesome.min.css"/>
    <link rel="stylesheet" href="${basePath}/static/admin/css/build.css"/>
    <!-- 富文本相关 -->
    <script type="text/javascript" charset="utf-8" src="${basePath}/static/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="${basePath}/static/ueditor/ueditor.all.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="${basePath}/static/ueditor/lang/zh-cn/zh-cn.js"></script>
    <!-- 富文本相关-END -->
    <%@include file="/pages/comm/admin/header.jsp"%>
    <script type="text/javascript" src="${basePath }/static/admin/js/common_img.js"></script>
    <!-- 图片上传相关 -->
    <script type="text/javascript" src="${basePath}/static/plupload/lib/plupload-2.1.2/js/plupload.full.min.js"></script>
    <script type="text/javascript" src="${basePath}/static/plupload/upload-img.js"></script>
    <!-- 图片上传相关-END -->
</head>
<body>
<!-- 表格 -->
<table id="whgdg" title="名录项目管理" class="easyui-datagrid" style="display: none"
       data-options="fit:true, striped:true, rownumbers:true, fitColumns:true, singleSelect:false, checkOnSelect:true, selectOnCheck:true, pagination:true, toolbar:'#whgdg-tb', url:'${basePath}/admin/feiyi/loadMinglu'">
    <thead>
    <tr>
        <th data-options="field:'mlprotailtitle', width:60">名录详情标题</th>
        <th data-options="field:'mlprosmpic', width:60, sortable:true,formatter:WhgComm.FMTImg">名录项目列表图</th>
        <th data-options="field:'mlprotype', width:60 ,formatter:function(val,row,index){ return WhgSysData.FMT(val, WhgSysData.getTypeData('8'));}">名录类型</th>
        <th data-options="field:'mlprolevel', width:60 ,formatter:function(val,row,index){ return WhgSysData.FMT(val, WhgSysData.getTypeData('10'));}">名录级别</th>
        <th data-options="field:'mlproitem', width:60,sortable:true,width:80,formatter:function(val,row,index){ return WhgSysData.FMT(val, WhgSysData.getTypeData('9'));}">名录批次</th>
        <th data-options="field:'mlprotime', width:60 ,formatter:datetimeFMT">名录项目修改时间</th>
        <th data-options="field:'mlprostate', width:60, sortable:true,formatter:WhgComm.FMTBizState">名录状态</th>
        <th data-options="field:'_opt', formatter:WhgComm.FMTOpt, optDivId:'whgdg-opt'">操作</th>
    </tr>
    </thead>
</table>
<!-- 表格 END -->

<!-- 表格操作工具栏 -->
<div id="whgdg-tb" style="display: none;">
    <div>
        <shiro:hasPermission name="${resourceid}:add"><a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add"  onclick="addminglu();" data-options="size:'small'">添加</a></shiro:hasPermission>
    </div>
    <div style="padding-top: 5px">
        <label>
            <input class="easyui-textbox" name="mlproshortitel" style="width:200px" data-options="prompt:'请输标题名称', validType:'length[1,32]'"/>
        </label>

        <label>
            <input class="easyui-combobox" name="mlprotype" style="width:200px"
                   data-options="editable:true, valueField:'id', textField:'text',prompt:'名录项目类型',data:WhgSysData.getTypeData('8')"/>
        </label>
        <label>
            <input class="easyui-combobox" name="mlproitem" style="width:200px"
                   data-options="editable:true, valueField:'id', textField:'text',prompt:'名录项目批次', data:WhgSysData.getTypeData('9')"/>
        </label>
        <label>
            <input class="easyui-combobox" name="mlprolevel" style="width:200px"
                   data-options="editable:true, valueField:'id', textField:'text', prompt:'名录项目级别',data:WhgSysData.getTypeData('10')"/>
        </label>
        <label>
            <input class="easyui-combobox" name="mlproqy" style="width:200px"
                   data-options="editable:true, valueField:'id', textField:'text',prompt:'名录项目区域', data:WhgSysData.getTypeData('6')"/>
        </label>
        <a href="javascript:void(0)" class="easyui-linkbutton tb_search" iconCls="icon-search" onclick="WhgComm.search('#whgdg', '#whgdg-tb');">查询</a>
    </div>
</div>
<!-- 表格操作工具栏-END -->

<!-- 操作按钮 -->
<div id="whgdg-opt" style="display: none;">
    <shiro:hasPermission name="${resourceid }:view"><a href="javascript:void(0)"  method="lookAll">查看</a></shiro:hasPermission>
    <shiro:hasPermission name="${resourceid }:edit"><a href="javascript:void(0)" validKey="mlprostate" validVal="1,4" method="edit_minglu">编辑</a></shiro:hasPermission>
    <shiro:hasPermission name="${resourceid}:publish"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="mlprostate" validVal="1,2,4" method="publish">发布</a></shiro:hasPermission>
    <shiro:hasPermission name="${resourceid}:publishoff"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="mlprostate" validVal="6" method="publishoff" >取消发布</a></shiro:hasPermission>
    <shiro:hasPermission name="${resourceid}:view"><a href="javascript:void(0)" class="easyui-linkbutton" validKey="mlprostate" validVal="1,4" method="resource">资源管理</a></shiro:hasPermission>
    <shiro:hasPermission name="${resourceid}:info"><a href="javascript:void(0)" class="easyui-linkbutton" plain="true" method="info">资讯公告管理</a></shiro:hasPermission>
    <shiro:hasPermission name="${resourceid}:publishoff"><a href="javascript:void(0)" class="easyui-linkbutton" plain="true" validFun="_recommend" method="recommend">推荐</a></shiro:hasPermission>
    <shiro:hasPermission name="${resourceid}:publishoff"><a href="javascript:void(0)" class="easyui-linkbutton" plain="true" validFun="_recommendoff" method="recommendoff">取消推荐</a></shiro:hasPermission>
    <shiro:hasPermission name="${resourceid }:del"><a href="javascript:void(0)" validKey="mlprostate" validVal="1,3,4"  method="del">删除</a></shiro:hasPermission>
</div>
<!-- 操作按钮-END -->

<!-- 添加表单 -->
<div id="frm" class="none" style="display: none" data-options="	maximized:true">

</div>
<div id="whgwin-add"></div>
<div id="whgwin-add-btn" style="text-align: center; display: none">xq
    <div style="display: inline-block; margin: 0 auto">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" id="whgwin-add-btn-save">保 存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#whgwin-add').dialog('close')">关 闭</a>
    </div>
</div>
<!-- 添加表单 END -->

<!-- 编辑表单 -->
<div id="whgwin-edit"></div>
<!-- 编辑表单 END -->

<div id="whgwin-view"></div>

<!-- script -->
<script type="text/javascript">
    /**
     *推荐状态验证
     */
    function _recommendoff(idx){
        var row = $("#whgdg").datagrid("getRows")[idx];
        return row.mlprostate == 6 && row.recommend == 1;
    }
    function _recommend(idx){
        var row = $("#whgdg").datagrid("getRows")[idx];
        return row.mlprostate == 6 && row.recommend == 0;
    }
    /**
     * 添加名录
     */
    function addminglu() {
        WhgComm.editDialog('${basePath}/admin/feiyi/minglu/view/add');
    }

    /**
     * 编辑名录
     * @param idx 行下标
     */
    function edit_minglu(idx) {
        var curRow = $('#whgdg').datagrid('getRows')[idx];
        WhgComm.editDialog('${basePath}/admin/feiyi/minglu/view/edit?mlproid='+curRow.mlproid);
    }

    /**
     * 查看名录
     * @param idx 行下标
     */
    function lookAll(idx) {
        var curRow = $('#whgdg').datagrid('getRows')[idx];
        WhgComm.editDialog('${basePath}/admin/feiyi/minglu/view/edit?targetShow=1&mlproid='+curRow.mlproid+"&onlyshow=1");
    }

    /**
     * 资源管理
     * @param idx
     */
    function resource(idx) {
        var row = $("#whgdg").datagrid("getRows")[idx];
        WhgComm.editDialog('${basePath}/admin/train/resource/view/list?reftype=5&id=' + row.mlproid);
    }

    /**
     * 推荐
     * @param idx
     */
    function recommend(idx){
        var row = $("#whgdg").datagrid("getRows")[idx];
        $.messager.confirm("确认信息", "确定要推荐选中的项吗？", function(r){
            if (r){
                __updrecommend(row.mlproid, 0, 1);
            }
        })
    }
    /**
     * 取消推荐
     * @param idx
     */
    function recommendoff(idx){
        var row = $("#whgdg").datagrid("getRows")[idx];
        $.messager.confirm("确认信息", "确定要取消推荐选中的项吗？", function(r){
            if (r){
                __updrecommend(row.mlproid, 1, 0);
            }
        })
    }
    /**
     * 推荐提交
     */
    function __updrecommend(ids, formrecoms, torecom) {
        $.messager.progress();
        var params = {ids: ids, formrecoms: formrecoms, torecom: torecom};
        $.post('${basePath}/admin/feiyi/updrecommend', params, function(data){
            $("#whgdg").datagrid('reload');
            if (!data.success || data.success != "1"){
                $.messager.alert("错误", data.errormsg||'操作失败', 'error');
            }
            $.messager.progress('close');
        }, 'json');
    }

    /**
     * 批量发布
     */
    function plFP(){
        var data={fromstate:2,tostate:3};
        var title="发布";
        pleditState(data,title);
    }

    /**
     * 批量取消发布
     */
    function qxFP(){

        var data={fromstate:3,tostate:2};
        var title="取消发布";
        pleditState(data,title);
    }

    /**
     * 发布 [1,2,4]->6
     * @param idx
     */
    function publish(idx){
        var row = $("#whgdg").datagrid("getRows")[idx];
        $.messager.confirm("确认信息", "确定要发布选中的项吗？", function(r){
            if (r){
                __updStateSend(row.mlproid, "1,2,4", 6);
            }
        })
    }

    /**
     * 取消发布 [6]->4
     * @param idx
     */
    function publishoff(idx){
        var row = $("#whgdg").datagrid("getRows")[idx];
        $.messager.confirm("确认信息", "确定要取消发布选中的项吗？", function(r){
            if (r){
                __updStateSend(row.mlproid, 6, 4);
            }
        })
    }

    /**
     * 发布和取消发布时修改状态
     * @param ids
     * @param formstates
     * @param tostate
     * @private
     */
    function __updStateSend(ids, formstates, tostate){
        $.ajax({
            url: getFullUrl("/admin/feiyi/updstate"),
            data: {ids: ids, formstates: formstates, tostate: tostate},
            success: function (data) {
                if(data && data.success == '1'){
                    $('#whgdg').datagrid('reload');
                } else {
                    $.messager.alert('提示', '操作失败:'+data.errormsg+'！', 'error');
                }
            }
        });
    }

    /**
     * 删除
     * @param idx
     */
    function del(idx) {
        var curRow = $('#whgdg').datagrid('getRows')[idx];
        $.messager.confirm('提示', '您确定要删除此记录吗？', function(r){
            if (r){
                $.ajax({
                    type: "POST",
                    url: getFullUrl('/admin/feiyi/removeMinglu'),
                    data: {mlproid : curRow.mlproid},
                    success: function(Json){
                        if(Json && Json.success == '1'){
                            //$.messager.alert('提示', '操作成功！', 'info');
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
     * 资讯公告
     * @param idx
     */
    function info(idx) {
        var row = $("#whgdg").datagrid("getRows")[idx];
        WhgComm.editDialog('${basePath}/admin/info/view/list?entityid='+row.mlproid+'&entity=3');
    }

</script>
<!-- script END -->
</body>
</html>