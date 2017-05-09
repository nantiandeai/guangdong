<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<% request.setAttribute("basePath", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath());%>
<% request.setAttribute("resourceid", request.getParameter("rsid")); %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>项目示范</title>
    <%@include file="/pages/comm/admin/header.jsp"%>
    <script type="text/javascript" charset="utf-8" src="${basePath}/static/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="${basePath}/static/ueditor/ueditor.all.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="${basePath}/static/ueditor/lang/zh-cn/zh-cn.js"></script>
</head>
<body>

<!-- 表格 -->
<table id="whgdg" title="项目示范" class="easyui-datagrid" style="display: none"
       data-options="fit:true, striped:true, rownumbers:true, fitColumns:true, singleSelect:false, checkOnSelect:true, selectOnCheck:true, pagination:true, toolbar:'#whgdg-tb', url:'${basePath}/admin/zyhd/findzyhd'">
    <thead>
    <tr>
        <th data-options="field:'ck', checkbox:true"></th>
        <th data-options="field:'zyhdtitle',sortable:true,width:120">标题</th>
        <th data-options="field:'zyhdpic',width:100,formatter:WhgComm.FMTImg">列表图</th>
        <th data-options="field:'zyhdarea',sortable:true,width:50,formatter:FMTArea">志愿活动区域</th>
        <th data-options="field:'zyhdtype',sortable:true, width:50,formatter:FMTType">类型</th>
        <th data-options="field:'zyhdscope',sortable:true, width:50">服务地区</th>
        <%--<th data-options="field:'zyhdsdate', width:100, formatter:WhgComm.FMTDateTime">活动开始时间</th>--%>
        <th data-options="field:'zyhdstate', width:50, formatter:traStateFMT">状态</th>
        <th data-options="field:'_opt', width:200, formatter:WhgComm.FMTOpt, optDivId:'whgdg-opt'">操作</th>
    </tr>
    </thead>
</table>
<!-- 表格 END -->

<!-- 表格操作工具栏 -->
<div id="whgdg-tb" style="display: none;">
    <div class="whgd-gtb-btn">
        <shiro:hasPermission name="${resourceid}:add"><a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" size='small' id="btn" onclick="doAdd()">添加</a></shiro:hasPermission>
        <shiro:hasPermission name="${resourceid}:checkon"><a href="javascript:void(0)" class="easyui-linkbutton" onclick="allcheck()">批量审核</a></shiro:hasPermission>
        <shiro:hasPermission name="${resourceid}:checkoff"><a href="javascript:void(0)" class="easyui-linkbutton" onclick="allback()">批量打回</a></shiro:hasPermission>
        <shiro:hasPermission name="${resourceid}:publish"><a href="javascript:void(0)" class="easyui-linkbutton" onclick="allpublish()">批量发布</a></shiro:hasPermission>
        <shiro:hasPermission name="${resourceid}:publishoff"><a href="javascript:void(0)" class="easyui-linkbutton" onclick="allnoPublish()">批量取消发布</a></shiro:hasPermission>
    </div>
    <div class="whgdg-tb-srch">
        <input class="easyui-combobox" name="zyhdarea" style="width: 200px; height:28px" data-options="editable:true, valueField:'id', textField:'text',prompt:'请选择区域', data:WhgSysData.getTypeData('6')"/>
        <input class="easyui-combobox" name="zyhdtype" style="width: 200px; height:28px" data-options="editable:true, valueField:'id', textField:'text',prompt:'志愿活动类型', data:WhgSysData.getTypeData('13')"/>
        <select class="easyui-combobox" name="zyhdstate" style="width: 200px; height:28px" data-options="prompt:'请选输入活动标题', value:''">
            <option value="1">待审核</option>
            <option value="2">已审核</option>
            <option value="3">已发布</option>
        </select>
        <input class="easyui-textbox" name="zyhdtitle" style="width: 200px; height:28px" data-options="validType:'length[1,30]',prompt:'请选输入活动标题'"/>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="WhgComm.search('#whgdg', '#whgdg-tb');">查 询</a>
    </div>
</div>
<!-- 表格操作工具栏-END -->

<!-- 操作按钮 -->
<div id="whgdg-opt" style="display: none;">
    <shiro:hasPermission name="${resourceid}:view"><a href="javascript:void(0)" class="easyui-linkbutton" plain="true"  method="doSee">查看详情</a></shiro:hasPermission>
    <shiro:hasPermission name="${resourceid}:edit"><a href="javascript:void(0)" class="easyui-linkbutton" plain="true" validKey="zyhdstate" validVal="1" method="doEdit">修改</a></shiro:hasPermission>
    <shiro:hasPermission name="${resourceid}:del"><a href="javascript:void(0)" class="easyui-linkbutton" plain="true" validKey="zyhdstate" validVal="1" method="doDel">删除</a></shiro:hasPermission>
    <shiro:hasPermission name="${resourceid}:checkon"><a href="javascript:void(0)" class="easyui-linkbutton" plain="true" validKey="zyhdstate" validVal="1" method="check">审核</a></shiro:hasPermission>
    <shiro:hasPermission name="${resourceid}:checkoff"><a href="javascript:void(0)" class="easyui-linkbutton" plain="true" validKey="zyhdstate" validVal="2" method="nocheck">打回</a></shiro:hasPermission>
    <shiro:hasPermission name="${resourceid}:publish"><a href="javascript:void(0)" class="easyui-linkbutton" plain="true" validKey="zyhdstate" validVal="2" method="publish">发布</a></shiro:hasPermission>
    <shiro:hasPermission name="${resourceid}:publishoff"><a href="javascript:void(0)" class="easyui-linkbutton" plain="true" validKey="zyhdstate" validVal="3" method="nopublish">取消</a></shiro:hasPermission>
    <shiro:hasPermission name="${resourceid}:edit"><a href="javascript:void(0)" class="easyui-linkbutton" plain="true" method="addzy">资源管理</a></shiro:hasPermission>
</div>
<!-- 操作按钮-END -->

<!-- script -->
<script type="text/javascript">
    function FMTArea(val) {
        return WhgSysData.FMT(val, WhgSysData.getTypeData('6'));
    }

    function FMTType(val) {
        return WhgSysData.FMT(val, WhgSysData.getTypeData('13'));
    }

    /**
     * 添加
     */
    function doAdd() {
        WhgComm.editDialog('${basePath}/admin/zyhd/view/add');
    }

    /**
     * 编辑
     * @param idx 行下标
     */
    function doEdit(idx) {
        var curRow = $('#whgdg').datagrid('getRows')[idx];
        WhgComm.editDialog('${basePath}/admin/zyhd/view/edit?id='+curRow.zyhdid+"&onlyshow=0");
    }

    /**
     * 查看
     * @param idx 行下标
     */
    function doSee(idx) {
        var curRow = $('#whgdg').datagrid('getRows')[idx];
        WhgComm.editDialog('${basePath}/admin/zyhd/view/edit?id='+curRow.zyhdid+"&onlyshow=1");
    }

    /**
     * 删除
     * @param idx
     */
    function doDel(idx) {
        var curRow = $('#whgdg').datagrid('getRows')[idx];
        var zyhdid = curRow.zyhdid;
        $.messager.confirm('提示', '您确定要删除此记录吗？', function(r){
            if (r){
                $.ajax({
                    type: "POST",
                    cache: false,
                    url: getFullUrl('/admin/zyhd/delhd'),
                    data: {zyhdid : zyhdid},
                    success: function(Json){
                        if(Json && Json.success == '0'){
                            $('#whgdg').datagrid('reload');
                        } else {
                            $.messager.alert('提示', '操作失败:'+Json.errmsg+'！', 'error');
                        }
                    }
                });
            }
        });
    }

    /**审核*/
    function check(idx){
        var row = $('#whgdg').datagrid('getRows')[idx];
        var zyhdid = row.zyhdid;
        var title  = '审核';
        docheck(title,zyhdid,1,2);
    }

    /**发布*/
    function publish(idx){
        var row = $('#whgdg').datagrid('getRows')[idx];
        var zyhdid = row.zyhdid;
        var title  = '发布';
        docheck(title,zyhdid,2,3);
    }

    /**打回审核*/
    function nocheck(idx){
        var row = $('#whgdg').datagrid('getRows')[idx];
        var zyhdid = row.zyhdid;
        var title  = '打回';
        docheck(title,zyhdid,2,1);
    }

    /**取消发布*/
    function nopublish(idx){
        var row = $('#whgdg').datagrid('getRows')[idx];
        var zyhdid = row.zyhdid;
        var title  = '取消发布';
        docheck(title,zyhdid,3,2);
    }

    /** 审核事件提交处理 */
    function docheck(title,zyhdid,fromstate,tostate){
        $.messager.confirm('确认对话框', '您确认将所选择的'+title+'吗？', function(r){
            if (r){
                $.ajax({
                    type: "POST",
                    url: getFullUrl('/admin/zyhd/check'),
                    data: {zyhdid : zyhdid,fromstate:fromstate,tostate:tostate},
                    success: function(data){
                        if(data.success == 'success'){
                            $('#whgdg').datagrid('reload');
                        }else{
                            $.messager.alert('提示', '操作失败。原因：'+data.errmsg, 'error');
                        }
                    }
                });
            }
        });
    }

    /**批量审核*/
    function allcheck(){
        var rows={};
        rows = $("#whgdg").datagrid("getSelections");
        if (rows == "" || rows == null) {
            $.messager.alert('提示', '请选择要操作的数据！');
            return;
        }
        var zyhdids = _split = "";
        for (var i = 0; i<rows.length; i++){
            zyhdids += _split+rows[i].zyhdid;
            _split = ",";
        }

        /*$.ajax({
            type : "post",
            url : getFullUrl('/admin/zyhd/canCheckAll'),
            data : {zyhdids : zyhdids},
            success :  function(data){
                if (data.success == 0 || data.success == null) {
                    $.messager.alert('提示', '没有添加资源，不能通过审核！');
                    return;
                }else{
                    subcheck(zyhdids,1,2);
                }
            }
        });*/
        subcheck(zyhdids,1,2);
    }

    /**批量发布*/
    function allpublish(){
        var rows={};
        rows = $("#whgdg").datagrid("getSelections");
        if (rows == "" || rows == null) {
            $.messager.alert('提示', '请选择要操作的数据！');
            return;
        }
        var zyhdids = _split = "";
        for (var i = 0; i<rows.length; i++){
            zyhdids += _split+rows[i].zyhdid;
            _split = ",";
        }
        subcheck(zyhdids,2,3);
    }

    /**批量打回*/
    function allback(){
        var rows={};
        rows = $("#whgdg").datagrid("getSelections");
        if (rows == "" || rows == null) {
            $.messager.alert('提示', '请选择要操作的数据！');
            return;
        }
        var zyhdids = _split = "";
        for (var i = 0; i<rows.length; i++){
            zyhdids += _split+rows[i].zyhdid;
            _split = ",";
        }
        subcheck(zyhdids,2,1);
    }

    /**批量取消发布*/
    function allnoPublish(){
        var rows={};
        rows = $("#whgdg").datagrid("getSelections");
        if (rows == "" || rows == null) {
            $.messager.alert('提示', '请选择要操作的数据！');
            return;
        }
        var zyhdids = _split = "";
        for (var i = 0; i<rows.length; i++){
            zyhdids += _split+rows[i].zyhdid;
            _split = ",";
        }
        subcheck(zyhdids,3,2);
    }

    /**
     * 一键审核提交
     */
    function subcheck(zyhdids,fromstate,tostate){
        $.messager.confirm('确认对话框', '您确认将所选择的通过审核吗？', function(r){
            if (r){
                $.ajax({
                    type: "POST",
                    url: getFullUrl('/admin/zyhd/checkAll'),
                    data: {zyhdids : zyhdids,fromstate: fromstate, tostate:tostate },
                    success: function(data){
                        if(data.success=="success"){
                            $('#whgdg').datagrid('reload');
                        }else{
                            $.messager.alert('提示', '操作失败。原因：'+data.errmsg, 'error');
                        }
                    }
                });
            }
        });
    }

    /**
     * 资源管理
     * @param idx
     */
    function addzy(idx) {
        var row = $("#whgdg").datagrid("getRows")[idx];
        WhgComm.editDialog('${basePath}/admin/train/resource/view/list?reftype=7&id=' + row.zyhdid);
    }
</script>
<!-- script END -->
</body>
</html>