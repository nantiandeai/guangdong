<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<% request.setAttribute("basePath", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath());%>
<% request.setAttribute("resourceid", request.getParameter("rsid")); %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>先进个人</title>
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
<table id="whgdg" title="先进个人管理" class="easyui-datagrid" style="display: none"
       data-options="fit:true, striped:true, rownumbers:true, fitColumns:true, singleSelect:false, checkOnSelect:true, selectOnCheck:true, pagination:true, toolbar:'#whgdg-tb', url:'${basePath}/admin/volun/findPerson'">
    <thead>
    <tr>
        <th data-options="field:'ck', checkbox:true"></th>
        <th data-options="field:'zyfcgrshorttitle',width:60">列表短标题</th>
        <th data-options="field:'zyfcgrjoinact',width:60">参与活动</th>
        <th data-options="field:'zyfcgrpic',width:60,formatter:WhgComm.FMTImg">列表图片</th>
        <th data-options="field:'zyfcgrzc',width:60">文艺专长</th>
        <th data-options="field:'zyfcgrstate', width:60,formatter:traStateFMT ">状态</th>
        <th data-options="field:'_opt',formatter:WhgComm.FMTOpt,optDivId:'personalOPT'">操作</th>
    </tr>
    </thead>
</table>
<!-- 表格 END -->

<!-- 表格操作工具栏 -->
<div id="whgdg-tb" style="display: none;">
    <div style="line-height: 32px;">
        <shiro:hasPermission name="${resourceid}:add"><a href="#" class="easyui-linkbutton" iconCls="icon-add" size='small' id="btn" onclick="addPersonal()">添加</a></shiro:hasPermission>
        <shiro:hasPermission name="${resourceid}:checkgo"><a href="javascript:void(0)" class="easyui-linkbutton" size='small' onclick="allcheck()">批量审核</a></shiro:hasPermission>
        <shiro:hasPermission name="${resourceid}:publish"><a href="javascript:void(0)" class="easyui-linkbutton" size='small' onclick="allpublish()">批量发布</a></shiro:hasPermission>
        <shiro:hasPermission name="${resourceid}:publishoff"><a href="javascript:void(0)" class="easyui-linkbutton" size='small' onclick="allnoPublish()">批量取消发布</a></shiro:hasPermission>
        <shiro:hasPermission name="${resourceid}:checkoff"><a href="javascript:void(0)" class="easyui-linkbutton" size='small' onclick="allback()">批量打回</a></shiro:hasPermission>
    </div>
    <div style="padding-top: 5px">
        <%--先进个人标题:--%>
        <input class="easyui-textbox" name="zyfcgrtitle" style="width: 200px; height:28px"  data-options="validType:'length[1,30]',prompt:'请输入标题'"/>
        <%--状态:--%>
        <select class="easyui-combobox" name="zyfcgrstate" style="width: 200px; height:28px" >
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
<div id="personalOPT" style="display:none">
    <shiro:hasPermission name="${resourceid}:view"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true"  method="look">查看详情</a></shiro:hasPermission>
    <shiro:hasPermission name="${resourceid}:edit"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="zyfcgrstate" validVal="0" method="editVolun">编辑</a></shiro:hasPermission>
    <shiro:hasPermission name="${resourceid}:checkon"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="zyfcgrstate" validVal="0" method="check">审核</a></shiro:hasPermission>
    <shiro:hasPermission name="${resourceid}:checkoff"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="zyfcgrstate" validVal="2" method="nocheck">打回</a></shiro:hasPermission>
    <shiro:hasPermission name="${resourceid}:publish"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="zyfcgrstate" validVal="2" method="publish">发布</a></shiro:hasPermission>
    <shiro:hasPermission name="${resourceid}:publishoff"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="zyfcgrstate" validVal="3" method="nopublish">撤消发布</a></shiro:hasPermission>
    <shiro:hasPermission name="${resourceid}:edit"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="zyfcgrstate" validVal="0" method="addzy">资源管理</a></shiro:hasPermission>
    <shiro:hasPermission name="${resourceid}:del"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="zyfcgrstate" validVal="0" method="delVolun">删除</a></shiro:hasPermission>
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
     * 添加先进个人
     */
    function addPersonal() {
        WhgComm.editDialog('${basePath}/admin/volun/person/view/add');
    }

    /**
     * 编辑先进个人
     * @param idx 行下标
     */
    function editVolun(idx) {
        var curRow = $('#whgdg').datagrid('getRows')[idx];
        WhgComm.editDialog('${basePath}/admin/volun/person/view/edit?zyfcgrid='+curRow.zyfcgrid);
    }

    /**
     * 查看
     * @param idx 行下标
     */
    function look(idx) {
        var curRow = $('#whgdg').datagrid('getRows')[idx];
        WhgComm.editDialog('${basePath}/admin/volun/person/view/edit?targetShow=1&zyfcgrid='+curRow.zyfcgrid);
    }

    /**
     * 资源管理
     * @param idx
     */
    function addzy(idx) {
        var row = $("#whgdg").datagrid("getRows")[idx];
        WhgComm.editDialog('${basePath}/admin/train/resource/view/list?reftype=10&id=' + row.zyfcgrid);
    }

    /**审核*/
    function check(idx){
        var row = $("#whgdg").datagrid("getRows")[idx];
        var zyfcgrid = row.zyfcgrid;
        var title = "审核";
        docheck(title,zyfcgrid,0,2);
    }

    /**发布*/
    function publish(idx){
        var row = $("#whgdg").datagrid("getRows")[idx];
        var zyfcgrid = row.zyfcgrid;
        var title = "发布";
        docheck(title,zyfcgrid,2,3);
    }

    /**打回审核*/
    function nocheck(idx){
        var row = $("#whgdg").datagrid("getRows")[idx];
        var zyfcgrid = row.zyfcgrid;
        var title = "打回";
        docheck(title,zyfcgrid,2,0);
    }

    /**取消发布*/
    function nopublish(idx){
        var row = $("#whgdg").datagrid("getRows")[idx];
        var zyfcgrid = row.zyfcgrid;
        var title = "取消发布";
        docheck(title,zyfcgrid,3,2);
    }

    /** 审核事件提交处理 */
    function docheck(title,zyfcgrid,fromstate,tostate){
        $.messager.confirm('确认对话框', '您确认将所选择的'+title+'吗？', function(r){
            if (r){
                $.ajax({
                    type: "POST",
                    url: getFullUrl('/admin/volun/checkPerson'),
                    data: {zyfcgrid : zyfcgrid,fromstate:fromstate,tostate:tostate},
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
        //获取选中框的行
        var rows={};
        rows = $("#personal").datagrid("getSelections");
        if (rows == "" || rows == null) {
            $.messager.alert('提示', '请选择要操作的数据！');
            return;
        }
        //拼装id
        var zyfcgrids = _split = "";
        for (var i = 0; i<rows.length; i++){
            zyfcgrids += _split+rows[i].zyfcgrid;
            _split = ",";
        }
        //一键审核
        subcheck(zyfcgrids,1,2);
    }

    /**批量发布*/
    function allpublish(){
        //获取选中框的行
        var rows={};
        rows = $("#personal").datagrid("getSelections");
        if (rows == "" || rows == null) {
            $.messager.alert('提示', '请选择要操作的数据！');
            return;
        }
        //拼装id
        var zyfcgrids = _split = "";
        for (var i = 0; i<rows.length; i++){
            zyfcgrids += _split+rows[i].zyfcgrid;
            _split = ",";
        }
        //一键审核
        subcheck(zyfcgrids,2,3);
    }
    /**批量打回*/
    function allback(){
        //获取选中框的行
        var rows={};
        rows = $("#personal").datagrid("getSelections");
        if (rows == "" || rows == null) {
            $.messager.alert('提示', '请选择要操作的数据！');
            return;
        }
        var zyfcgrids = _split = "";
        for (var i = 0; i<rows.length; i++){
            zyfcgrids += _split+rows[i].zyfcgrid;
            _split = ",";
        }
        //一键审核
        subcheck(zyfcgrids,2,1);
    }
    /**批量取消发布*/
    function allnoPublish(){
        //获取选中框的行
        var rows={};
        rows = $("#personal").datagrid("getSelections");
        if (rows == "" || rows == null) {
            $.messager.alert('提示', '请选择要操作的数据！');
            return;
        }
        //拼装id
        var zyfcgrids = _split = "";
        for (var i = 0; i<rows.length; i++){
            zyfcgrids += _split+rows[i].zyfcgrid;
            _split = ",";
        }
        //一键审核
        subcheck(zyfcgrids,3,2);
    }

    /**
     * 一键审核提交
     */
    function subcheck(zyfcgrids,fromstate,tostate){
        $.messager.confirm('确认对话框', '您确认将所选择的通过审核吗？', function(r){
            if (r){
                $.ajax({
                    type: "POST",
                    url: getFullUrl('/admin/volun/checkAllPer'),
                    data: {zyfcgrids : zyfcgrids,fromstate: fromstate, tostate:tostate },
                    success: function(data){
                        if(data.success=="success"){
                            $.messager.alert("提示", data.msg);
                            $('#personal').datagrid('reload');
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
    function delVolun(idx) {
        var curRow = $('#whgdg').datagrid('getRows')[idx];
        $.messager.confirm('提示', '您确定要删除此记录吗？', function(r){
            if (r){
                $.ajax({
                    type: "POST",
                    url: getFullUrl('/admin/volun/delPerson'),
                    data: {zyfcgrid : curRow.zyfcgrid},
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