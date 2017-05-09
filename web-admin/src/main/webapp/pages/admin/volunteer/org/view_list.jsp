<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<% request.setAttribute("basePath", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath());%>
<% request.setAttribute("resourceid", request.getParameter("rsid")); %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>志愿优秀组织</title>
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
<table id="whgdg" title="志愿优秀组织" class="easyui-datagrid" style="display: none"
       data-options="fit:true, striped:true, rownumbers:true, fitColumns:true, singleSelect:false, checkOnSelect:true, selectOnCheck:true, pagination:true, toolbar:'#whgdg-tb', url:'${basePath}/admin/volun/findexce'">
    <thead>
    <tr>
        <th data-options="field:'ck', checkbox:true"></th>
        <th data-options="field:'zyfczzshorttitle',width:60">列表短标题</th>
        <th data-options="field:'zyfczzpic',width:60,formatter:WhgComm.FMTImg">列表图片</th>
        <th data-options="field:'zyfczzpnum',sortable:true,width:60">志愿者数量</th>
        <th data-options="field:'zyfczzanum',sortable:true,width:60">活动数量</th>
        <th data-options="field:'zyfczzscope',width:60">服务地区</th>
        <th data-options="field:'zyfczzfwtime',width:60">服务时间(小时)</th>
        <th data-options="field:'zyfczzstate', width:60,formatter:traStateFMT ">状态</th>
        <th data-options="field:'_opt',formatter:WhgComm.FMTOpt,optDivId:'whgdg-opt'">操作</th>
    </tr>
    </thead>
</table>
<!-- 表格 END -->

<!-- 表格操作工具栏 -->
<div id="whgdg-tb" style="display: none;">
    <div>
        <shiro:hasPermission name="${resourceid}:add"><a href="#" class="easyui-linkbutton" iconCls="icon-add" size='small' id="btn" onclick="addzyzz()">添加</a></shiro:hasPermission>
        <shiro:hasPermission name="${resourceid}:checkon"><a href="javascript:void(0)" class="easyui-linkbutton" size='small' onclick="allcheck()">批量审核</a></shiro:hasPermission>
        <shiro:hasPermission name="${resourceid}:checkoff"><a href="javascript:void(0)" class="easyui-linkbutton" size='small' onclick="allback()">批量打回</a></shiro:hasPermission>
        <shiro:hasPermission name="${resourceid}:publish"><a href="javascript:void(0)" class="easyui-linkbutton" size='small' onclick="allpublish()">批量发布</a></shiro:hasPermission>
        <shiro:hasPermission name="${resourceid}:publishoff"><a href="javascript:void(0)" class="easyui-linkbutton" size='small' onclick="allnoPublish()">批量取消发布</a></shiro:hasPermission>
    </div>
    <div style="padding-top: 5px">
        <%--状态:--%>
        <select class="easyui-combobox" name="zyfczzstate"style="width: 200px; height:28px"  >
            <option value="">请选择状态</option>
            <option value="0">未审核</option>
            <option value="2">已审核</option>
            <option value="3">已发布</option>
        </select>
        <a href="javascript:void(0)" class="easyui-linkbutton tb_search" iconCls="icon-search" onclick="WhgComm.search('#whgdg', '#whgdg-tb');">查询</a>

    </div>
</div>
<!-- 表格操作工具栏-END -->

<!-- 操作按钮 -->
<div id="whgdg-opt" style="display: none;">
    <shiro:hasPermission name="${resourceid}:view"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true"  method="look">查看详情</a></shiro:hasPermission>
    <shiro:hasPermission name="${resourceid}:edit"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="zyfczzstate" validVal="0" method="editzyzz">编辑</a></shiro:hasPermission>
    <shiro:hasPermission name="${resourceid}:checkon"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="zyfczzstate" validVal="0" method="check">审核</a></shiro:hasPermission>
    <shiro:hasPermission name="${resourceid}:checkoff"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="zyfczzstate" validVal="2" method="nocheck">打回</a></shiro:hasPermission>
    <shiro:hasPermission name="${resourceid}:publish"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="zyfczzstate" validVal="2" method="publish">发布</a></shiro:hasPermission>
    <shiro:hasPermission name="${resourceid}:publishoff"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="zyfczzstate" validVal="3" method="nopublish">取消发布</a></shiro:hasPermission>
    <shiro:hasPermission name="${resourceid}:edit"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="zyfczzstate" validVal="0" method="addzy">资源管理</a></shiro:hasPermission>
    <shiro:hasPermission name="${resourceid}:del"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="zyfczzstate" validVal="0" method="delzyzz">删除</a></shiro:hasPermission>
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
     * 添加志愿组织
     */
    function addzyzz() {
        WhgComm.editDialog('${basePath}/admin/volun/org/view/add');
    }

    /**
     * 编辑志愿组织
     * @param idx 行下标
     */
    function editzyzz(idx) {
        var curRow = $('#whgdg').datagrid('getRows')[idx];
        WhgComm.editDialog('${basePath}/admin/volun/org/view/edit?zyfczzid='+curRow.zyfczzid);
    }

    /**
     * 查看志愿组织
     * @param idx 行下标
     */
    function look(idx) {
        var curRow = $('#whgdg').datagrid('getRows')[idx];
        WhgComm.editDialog('${basePath}/admin/volun/org/view/edit?targetShow=1&zyfczzid='+curRow.zyfczzid);
    }

    /**
     * 资源管理
     * @param idx
     */
    function addzy(idx) {
        var row = $("#whgdg").datagrid("getRows")[idx];
        WhgComm.editDialog('${basePath}/admin/train/resource/view/list?reftype=8&id=' + row.zyfczzid);
    }

    /**审核*/
    function check(idx){
        var row = $("#whgdg").datagrid("getRows")[idx];
        var zyfczzid = row.zyfczzid;
        var title = "审核";
        docheck(title,zyfczzid,0,2);
    }

    /**发布*/
    function publish(idx){
        var row = $("#whgdg").datagrid("getRows")[idx];
        var zyfczzid = row.zyfczzid;
        var title = "发布";
        docheck(title,zyfczzid,2,3);
    }

    /**打回审核*/
    function nocheck(idx){
        var row = $("#whgdg").datagrid("getRows")[idx];
        var zyfczzid = row.zyfczzid;
        var title = "打回";
        docheck(title,zyfczzid,2,0);
    }

    /**取消发布*/
    function nopublish(idx){
        var row = $("#whgdg").datagrid("getRows")[idx];
        var zyfczzid = row.zyfczzid;
        var title = "取消发布";
        docheck(title,zyfczzid,3,2);
    }

    /** 审核事件提交处理 */
    function docheck(title,zyfczzid,fromstate,tostate){
        $.messager.confirm('确认对话框', '您确认将所选择的'+title+'吗？', function(r){
            if (r){
                $.ajax({
                    type: "POST",
                    url: getFullUrl('/admin/volun/checkexce'),
                    data: {zyfczzid : zyfczzid,fromstate:fromstate,tostate:tostate},
                    success: function(data){
                        if(data.success == 'success'){
                            $('#whgdg').datagrid('reload');
//						$.messager.alert('提示', '操作成功');
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
        var zyfczzids = _split = "";
        for (var i = 0; i<rows.length; i++){
            if (rows[i].zyfczzstate == 0) {
                zyfczzids += _split+rows[i].zyfczzid;
                _split = ",";
            }

        }
        if (!zyfczzids){
            $.messager.alert('提示', '没有匹配当前操作的选择记录');
            return false;
        }
        subcheck(zyfczzids,0,2);
    }

    /**批量发布*/
    function allpublish(){
        var rows={};
        rows = $("#whgdg").datagrid("getSelections");
        if (rows == "" || rows == null) {
            $.messager.alert('提示', '请选择要操作的数据！');
            return;
        }
        var zyfczzids = _split = "";
        for (var i = 0; i<rows.length; i++){
            if (rows[i].zyfczzstate == 2) {
                zyfczzids += _split+rows[i].zyfczzid;
                _split = ",";
            }

        }
        if (!zyfczzids){
            $.messager.alert('提示', '没有匹配当前操作的选择记录');
            return false;
        }
        subcheck(zyfczzids,2,3);
    }
    /**批量打回*/
    function allback(){
        var rows={};
        rows = $("#whgdg").datagrid("getSelections");
        if (rows == "" || rows == null) {
            $.messager.alert('提示', '请选择要操作的数据！');
            return;
        }
        var zyfczzids = _split = "";
        for (var i = 0; i<rows.length; i++){
            if (rows[i].zyfczzstate == 2) {
                zyfczzids += _split+rows[i].zyfczzid;
                _split = ",";
            }

        }
        if (!zyfczzids){
            $.messager.alert('提示', '没有匹配当前操作的选择记录');
            return false;
        }
        subcheck(zyfczzids,2,0);
    }
    /**批量取消发布*/
    function allnoPublish(){
        var rows={};
        rows = $("#whgdg").datagrid("getSelections");
        if (rows == "" || rows == null) {
            $.messager.alert('提示', '请选择要操作的数据！');
            return;
        }
        var zyfczzids = _split = "";
        for (var i = 0; i<rows.length; i++){
            if (rows[i].zyfczzstate == 3) {
                zyfczzids += _split+rows[i].zyfczzid;
                _split = ",";
            }

        }
        if (!zyfczzids){
            $.messager.alert('提示', '没有匹配当前操作的选择记录');
            return false;
        }
        subcheck(zyfczzids,3,2);
    }

    /**
     * 一键审核提交
     */
    function subcheck(zyfczzids,fromstate,tostate){
        $.messager.confirm('确认对话框', '您确认将所选择的通过审核吗？', function(r){
            if (r){
                $.ajax({
                    type: "POST",
                    url: getFullUrl('/admin/volun/Allstate'),
                    data: {zyfczzids : zyfczzids,fromstate: fromstate, tostate:tostate },
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
     * 删除
     * @param idx
     */
    function delzyzz(idx) {
        var curRow = $('#whgdg').datagrid('getRows')[idx];
        $.messager.confirm('提示', '您确定要删除此记录吗？', function(r){
            if (r){
                $.ajax({
                    type: "POST",
                    url: getFullUrl('/admin/volun/delexcll'),
                    data: {zyfczzid : curRow.zyfczzid},
                    success: function(Json){
                        if(Json && Json.success == '0'){
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