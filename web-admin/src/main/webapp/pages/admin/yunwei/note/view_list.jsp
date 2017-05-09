<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<% request.setAttribute("basePath", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath());%>
<% request.setAttribute("resourceid", request.getParameter("rsid")); %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>操作日志管理</title>
    <%@include file="/pages/comm/admin/header.jsp"%>
    <script type="text/javascript" charset="utf-8" src="${basePath}/static/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="${basePath}/static/ueditor/ueditor.all.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="${basePath}/static/ueditor/lang/zh-cn/zh-cn.js"></script>
    <script type="text/javascript">
        /**
         * 格式化操作对象
         */
        function FMT_OPT_TYPE(val){
            return WhgSysData.FMT(val, DATA_OPT_TYPE());
        }

        /**
         * 操作对象列表数据
         * @returns {*}
         * @constructor
         */
        function DATA_OPT_TYPE(){
            return WhgSysData.getStateData("EnumOptType");
        }
    </script>
</head>
<body>

<!-- 表格 -->
<table id="whgdg" title="操作日志管理" class="easyui-datagrid" style="display: none"
       data-options="fit:true, striped:true, rownumbers:true, fitColumns:true, singleSelect:true, pagination:true, toolbar:'#whgdg-tb', url:'${basePath}/admin/yunwei/note/srchList4p'">
    <thead>
    <tr>
        <th data-options="field:'adminaccount', width:160">操作人</th>
        <th data-options="field:'opttype', width:160, formatter:FMT_OPT_TYPE">操作对象</th>
        <th data-options="field:'optdesc', width:160">操作说明</th>
        <th data-options="field:'opttime', width:160, formatter:WhgComm.FMTDateTime">操作时间</th>
        <th data-options="field:'optargs', width:160, formatter:WhgComm.FMTOpt, optDivId:'whgdg-opt'">操作数据</th>
    </tr>
    </thead>
</table>
<!-- 表格 END -->

<!-- 表格操作工具栏 -->
<div id="whgdg-tb" style="display: none;">
    <div class="whgd-gtb-btn"></div>
    <div class="whgdg-tb-srch">
        <input class="easyui-textbox" style="width: 200px;" name="adminaccount" data-options="prompt:'请输入操作人(管理员账号)进行查询', validType:'length[1,32]'" />
        <input class="easyui-textbox" style="width: 200px;" name="optdesc" data-options="prompt:'请输入操作说明进行查询', validType:'length[1,32]'" />
        <input class="easyui-textbox" style="width: 200px;" name="optargs" data-options="prompt:'请输入操作参数进行查询', validType:'length[1,32]'" />
        <input class="easyui-combobox" style="width: 200px;" name="opttype" data-options="prompt:'请操作对象', value:'', valueField:'id', textField:'text', data:DATA_OPT_TYPE()"/>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="WhgComm.search('#whgdg', '#whgdg-tb');">查 询</a>
    </div>
</div>
<!-- 表格操作工具栏-END -->

<!-- 操作按钮 -->
<div id="whgdg-opt" style="display: none;">
    <a href="javascript:void(0)" class="easyui-linkbutton" plain="true" method="doSee">查看</a>
</div>
<!-- 操作按钮-END -->

<!-- 查看表单 -->
<div id="whgwin-add" style="display: none">
    <table id="optargs" class="easyui-propertygrid" data-options="fit:true,columns:[[
              {field:'name',title:'参数',width:20,sortable:true},
              {field:'value',title:'值',width:80,sortable:true}
           ]],showGroup:false,scrollbarSize:0, border:false"></table>
</div>
<div id="whgwin-add-btn" style="text-align: center; display: none">
    <div style="display: inline-block; margin: 0 auto">
        <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#whgwin-add').dialog('close')">关 闭</a>
    </div>
</div>
<!-- 添加表单 END -->

<!-- script -->
<script type="text/javascript">
    /**
     * 查看资料
     * @param idx 行下标
     */
    function doSee(idx) {
        var curRow = $('#whgdg').datagrid('getRows')[idx];
        var optargsData = [];
        var optargs = $.parseJSON(curRow.optargs);
        var idx = 0;
        $.each(optargs, function(i, val){
            optargsData.push({"name":i, "value":val, "group":"note"});
            idx++;
        });
        $('#optargs').propertygrid("loadData", {"total":idx, "rows":optargsData});

        var _wh = $(window).height();
        var _ww = $(window).width();
        _ww = _ww > 850 ? 850 : (_ww-40);
        _wh = _wh > 650 ? 650 : (_wh-40);
        $('#whgwin-add').dialog({
            title: '操作数据',
            cache: false,
            modal: true,
            width: _ww,
            height: _wh,
            //maximizable: true,
            //resizable: false,
            buttons: '#whgwin-add-btn',
        });
    }
</script>
<!-- script END -->
</body>
</html>