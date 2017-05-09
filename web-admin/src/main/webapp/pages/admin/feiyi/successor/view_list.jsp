<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<% request.setAttribute("basePath", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath());%>
<% request.setAttribute("resourceid", request.getParameter("rsid")); %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>传承人管理</title>
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
<table id="whgdg" title="传承人管理" class="easyui-datagrid" style="display: none"
       data-options="fit:true, striped:true, rownumbers:true, fitColumns:true, singleSelect:false, checkOnSelect:true, selectOnCheck:true, pagination:true, toolbar:'#whgdg-tb', url:'${basePath}/admin/feiyi/loadSuccessor'">
    <thead>
    <tr>
        <th data-options="field:'suorname', width:60">传承人姓名</th>
        <th data-options="field:'suorpic', width:60, sortable:true,formatter:WhgComm.FMTImg">传承人图片</th>
        <th data-options="field:'suorqy', width:60 ,formatter:function(val,row,index){ return WhgSysData.FMT(val, WhgSysData.getTypeData('6'));}">传承人区域</th>
        <th data-options="field:'suorlevel', width:60 ,formatter:function(val,row,index){ return WhgSysData.FMT(val, WhgSysData.getTypeData('10'));}">传承人级别</th>
        <th data-options="field:'suoritem', width:60 ,formatter:function(val,row,index){ return WhgSysData.FMT(val, WhgSysData.getTypeData('9'));}">传承人批次</th>
        <th data-options="field:'suortype', width:60,sortable:true,width:80,formatter:function(val,row,index){ return WhgSysData.FMT(val, WhgSysData.getTypeData('8'));}">传承人类别</th>
        <th data-options="field:'suorstate', width:60, sortable:true,formatter:WhgComm.FMTBizState">传承人状态</th>
        <th data-options="field:'_opt', formatter:WhgComm.FMTOpt, optDivId:'whgdg-opt'">操作</th>
    </tr>
    </thead>
</table>
<!-- 表格 END -->

<!-- 表格操作工具栏 -->
<div id="whgdg-tb" style="display: none;">
    <div>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add"  onclick="addminglu();" data-options="size:'small'">添加</a>
        <a href="javascript:void(0)" class="easyui-linkbutton whgff-but-clear" iconCls="icon-undo" onclick="WhgComm.editDialogClose()">返 回</a>
    </div>
    <div style="padding-top: 5px">
        <input type="hidden" name="mlproid" value="${mlproid}">
        <label>
            <input class="easyui-textbox" name="suorname" style="width:200px" data-options="prompt:'传承人姓名', validType:'length[1,32]'"/>
        </label>

        <label>
            <input class="easyui-combobox" name="suortype" style="width:200px"
                   data-options="editable:true, valueField:'id', textField:'text',prompt:'传承人类型',data:WhgSysData.getTypeData('8')"/>
        </label>
        <label>
            <input class="easyui-combobox" name="suoritem" style="width:200px"
                   data-options="editable:true, valueField:'id', textField:'text',prompt:'传承人批次', data:WhgSysData.getTypeData('9')"/>
        </label>
        <label>
            <input class="easyui-combobox" name="suorlevel" style="width:200px"
                   data-options="editable:true, valueField:'id', textField:'text', prompt:'传承人级别',data:WhgSysData.getTypeData('10')"/>
        </label>
        <label>
            <input class="easyui-combobox" name="suorqy" style="width:200px"
                   data-options="editable:true, valueField:'id', textField:'text',prompt:'传承人区域', data:WhgSysData.getTypeData('6')"/>
        </label>
        <a href="javascript:void(0)" class="easyui-linkbutton tb_search" iconCls="icon-search" onclick="WhgComm.search('#whgdg', '#whgdg-tb');">查询</a>
    </div>
</div>
<!-- 表格操作工具栏-END -->

<!-- 操作按钮 -->
<%--<div id="whgdg-opt" style="display: none;">--%>
    <%--<a href="javascript:void(0)"  method="lookAll">查看</a>--%>
    <%--<a href="javascript:void(0)" validKey="suorstate" validVal="1,4" method="doEdit">编辑</a>--%>
    <%--<a href="javascript:void(0)" validKey="suorstate" validVal="1,3,4"  method="del">删除</a>--%>
    <%--<a href="javascript:void(0)" class="easyui-linkbutton"  method="resource">资源管理</a>--%>
    <%--<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="suorstate" validVal="1,2,4" method="publish">发布</a>--%>
    <%--<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="suorstate" validVal="6" method="publishoff" >取消发布</a>--%>
<%--</div>--%>
<div id="whgdg-opt" style="display: none;">
    <shiro:hasPermission name="${resourceid }:view"><a href="javascript:void(0)"  method="lookAll">查看</a></shiro:hasPermission>
    <shiro:hasPermission name="${resourceid }:edit"><a href="javascript:void(0)" validKey="suorstate" validVal="1,4" method="doEdit">编辑</a></shiro:hasPermission>
    <shiro:hasPermission name="${resourceid}:publish"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="suorstate" validVal="1,2,4" method="publish">发布</a></shiro:hasPermission>
    <shiro:hasPermission name="${resourceid}:publishoff"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="suorstate" validVal="6" method="publishoff" >取消发布</a></shiro:hasPermission>
    <shiro:hasPermission name="${resourceid}:view"><a href="javascript:void(0)" class="easyui-linkbutton"  validKey="suorstate" validVal="1,4" method="resource">资源管理</a></shiro:hasPermission>
    <shiro:hasPermission name="${resourceid}:publishoff"><a href="javascript:void(0)" class="easyui-linkbutton" plain="true" validFun="_recommend" method="recommend">推荐</a></shiro:hasPermission>
    <shiro:hasPermission name="${resourceid}:publishoff"><a href="javascript:void(0)" class="easyui-linkbutton" plain="true" validFun="_recommendoff" method="recommendoff">取消推荐</a></shiro:hasPermission>
    <shiro:hasPermission name="${resourceid }:del"><a href="javascript:void(0)" validKey="suorstate" validVal="1,3,4"  method="del">删除</a></shiro:hasPermission>
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
     * 添加传承人
     */
    function addminglu() {
        WhgComm.editDialog('${basePath}/admin/feiyi/successor/view/add');
    }

    /**
     * 编辑传承人
     * @param idx 行下标
     */
    function doEdit(idx) {
        var curRow = $('#whgdg').datagrid('getRows')[idx];
        WhgComm.editDialog('${basePath}/admin/feiyi/successor/view/edit?suorid='+curRow.suorid+'&mlproid='+'${mlproid}');
    }

    /**
     * 查看传承人
     * @param idx 行下标
     */
    function lookAll(idx) {
        var curRow = $('#whgdg').datagrid('getRows')[idx];
        WhgComm.editDialog('${basePath}/admin/feiyi/successor/view/edit?targetShow=1&suorid='+curRow.suorid+'&mlproid='+'${mlproid}');
    }

    /**
     * 资源管理
     * @param idx
     */
    function resource(idx) {
        var row = $("#whgdg").datagrid("getRows")[idx];
        WhgComm.editDialog('${basePath}/admin/train/resource/view/list?reftype=6&id=' + row.suorid);
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
                __updStateSend(row.suorid, "1,2,4", 6);
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
                __updStateSend(row.suorid, 6, 4);
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
            url: getFullUrl("/admin/feiyi/sucupdstate"),
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
     *推荐状态验证
     */
    function _recommendoff(idx){
        var row = $("#whgdg").datagrid("getRows")[idx];
        return row.suorstate == 6 && row.recommend == 1;
    }

    function _recommend(idx){
        var row = $("#whgdg").datagrid("getRows")[idx];
        return row.suorstate == 6 && row.recommend == 0;
    }
    /**
     * 推荐
     * @param idx
     */
    function recommend(idx){
        var row = $("#whgdg").datagrid("getRows")[idx];
        $.messager.confirm("确认信息", "确定要推荐选中的项吗？", function(r){
            if (r){
                __updrecommend(row.suorid, 0, 1);
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
                __updrecommend(row.suorid, 1, 0);
            }
        })
    }
    /**
     * 推荐提交
     */
    function __updrecommend(ids, formrecoms, torecom) {
        $.messager.progress();
        var params = {ids: ids, formrecoms: formrecoms, torecom: torecom};
        $.post('${basePath}/admin/feiyi/updaterecommend', params, function(data){
            $("#whgdg").datagrid('reload');
            if (!data.success || data.success != "1"){
                $.messager.alert("错误", data.errormsg||'操作失败', 'error');
            }
            $.messager.progress('close');
        }, 'json');
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
                    url: getFullUrl('/admin/feiyi/removeSuccessor'),
                    data: {suorid : curRow.suorid},
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

</script>
<!-- script END -->
</body>
</html>