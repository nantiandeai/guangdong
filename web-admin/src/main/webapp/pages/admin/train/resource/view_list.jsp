<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<% request.setAttribute("basePath", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath());%>
<% request.setAttribute("resourceid", request.getParameter("rsid")); %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>资源管理</title>
    <%@include file="/pages/comm/admin/header.jsp" %>
</head>
<body>
<!-- 表格 -->
<table id="whgdg" title="资源管理" class="easyui-datagrid" style="display: none"
       data-options="
            fit:true,
            striped:true,
            rownumbers:true,
            fitColumns:true,
            singleSelect:true,
            checkOnSelect:true,
            selectOnCheck:true,
            pagination:true,
            toolbar:'#whgdg-tb', url:'${basePath}/admin/train/resource/srchList4p?id=${id}&reftype=${reftype}'">
    <thead>
    <tr>
        <%--<th data-options="field:'entid', width:60">资源ID</th>--%>
        <th data-options="field:'entname', width:60">资源名</th>
        <%--<th data-options="field:'enttype', width:60">资源类型</th>--%>
        <th data-options="field:'enttype',width:60,formatter:function(val) {return ['图片','视频','音频'][parseInt(val)-1]}">资源类型</th>
        <%--<th data-options="field:'reftype', width:60">实体类型</th>--%>
        <th data-options="field:'reftype',width:60,formatter:function(val) {return ['培训','活动','场馆','场馆活动室','名录','传承人','志愿活动','优秀组织','项目示范','先进个人','资讯公告','馆办团队','文化品牌','数字展览作品'][parseInt(val)-1]}">实体类型</th>
        <%--<th data-options="field:'refid', width:60">实体ID</th>--%>
        <th data-options="field:'enturl', width:60,formatter:FMTErul">资源地址(图片)</th>
        <th data-options="field:'enttimes', width:60">视频/音频时长</th>
        <th data-options="field:'deourl', width:60, formatter:WhgComm.FMTImg">视频封面</th>
        <th data-options="field:'_opt', width:60, formatter:WhgComm.FMTOpt, optDivId:'whgdg-opt'">操作</th>
    </tr>
    </thead>
</table>
<!-- 表格 END -->

<!-- 表格操作工具栏 -->
<div id="whgdg-tb" style="display: none;">
    <div class="whgd-gtb-btn">
        <a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-add"onclick="doAdd();">添加资源</a>
        <a href="javascript:void(0)" class="easyui-linkbutton whgff-but-clear" iconCls="icon-undo" onclick="WhgComm.editDialogClose()">返 回</a>
    </div>
    <div class="whgdg-tb-srch">
        <input class="easyui-textbox" style="width: 200px; height:26px" name="entname" data-options="prompt:'资源名称'" />
        <select id="cc" class="easyui-combobox" name="enttype" panelHeight="auto" style="width:200px; height:26px" data-options="prompt:'请选择资源类型', value:''">
            <option value="1">图片</option>
            <option value="2">视频</option>
            <option value="3">音频</option>
        </select>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="WhgComm.search('#whgdg', '#whgdg-tb');">查 询</a>
    </div>
</div>
<!-- 表格操作工具栏-END -->

<!-- 操作按钮 -->
<div id="whgdg-opt" style="display: none;">
    <a href="javascript:void(0)" class="easyui-linkbutton" plain="true" method="doEdit">编辑</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" plain="true" method="doDel">删除</a>
</div>
<!-- 操作按钮-END -->

<!-- 添加表单 -->
<div id="whgwin-add"></div>
<%--<div id="whgwin-add-btn" style="text-align: center">--%>
<%--<div style="display: inline-block; margin: 0 auto">--%>
<%--<a href="#" class="easyui-linkbutton" iconCls="icon-save" onclick="submitForm()">保 存</a>--%>
<%--<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#whgwin-add').dialog('close')">关 闭</a>--%>
<%--</div>--%>
<%--</div>--%>
<!-- 添加表单 END -->

<script>
    /** 格式化资源地址*/
    function FMTErul(val, data, idx) {
        var enttype = data.enttype;
        if("1" == enttype){
            return WhgComm.FMTImg(val);
        }else if("2" == enttype){
            return val;//return WhgComm.FMTImg(data.deourl);
        }
        return "";
    }

    /** 添加资源方法 */
    function doAdd() {
        WhgComm.editDialog('${basePath}/admin/train/resource/view/add/?id=${id}&reftype=${reftype}' );
    }
    <%--console.log(${reftype});--%>
    /** 添加资源方法-END */

    /** 编辑资源方法 */
    function doEdit(idx) {
        var curtRow = $('#whgdg').datagrid('getRows')[idx];
        WhgComm.editDialog('${basePath}/admin/train/resource/view/edit?entid=' + curtRow.entid);
    }
    /** 编辑资源方法-END */

    /**删除 */
    function doDel(idx) {
        var rows = $("#whgdg").datagrid('getRows');
        var row = rows[idx];
        var entid = row.entid;
        $.messager.confirm('确认对话框', '您确认要删除吗？', function (r) {
            if (r) {
                $.ajax({
                    type: "POST",
                    url: "${basePath}/admin/train/resource/del",
                    data: {entid: entid},
                    success: function (data) {
                        //var Json = $.parseJSON(data);
                        if (data.success == "1") {
                            $('#whgdg').datagrid('reload');
                        } else {
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
