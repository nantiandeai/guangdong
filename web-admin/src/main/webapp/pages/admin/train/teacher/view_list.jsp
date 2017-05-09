<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<% request.setAttribute("basePath", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath());%>
<% request.setAttribute("resourceid", request.getParameter("rsid")); %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <c:choose>
        <c:when test="${type eq 'edit'}">
            <c:set var="pageTitle" value="培训师资编辑列表"></c:set>
        </c:when>
        <c:when test="${type eq 'check'}">
            <c:set var="pageTitle" value="培训师资审核列表"></c:set>
        </c:when>
        <c:when test="${type eq 'publish'}">
            <c:set var="pageTitle" value="培训师资发布列表"></c:set>
        </c:when>
        <c:otherwise>
            <c:set var="pageTitle" value="培训师资"></c:set>
        </c:otherwise>
    </c:choose>

    <title>${pageTitle}</title>

    <%@include file="/pages/comm/admin/header.jsp"%>
</head>
<body>
<!-- 表格 -->
<table id="whgdg" title="${pageTitle}" class="easyui-datagrid" style="display: none"
       data-options="fit:true, striped:true, rownumbers:true, fitColumns:true, singleSelect:false, checkOnSelect:true, selectOnCheck:true, pagination:true, toolbar:'#whgdg-tb', url:'${basePath}/admin/train/tea/srchList4p?type=${type}'">
    <thead>
    <tr>
        <th data-options="field:'teachername', width:100">老师名称</th>
        <th data-options="field:'teacherpic', width:100, formatter:WhgComm.FMTImg">老师照片</th>
        <th data-options="field:'teachertype', width:100, formatter:WhgComm.FMTTEAType">专长类型</th>
        <th data-options="field:'teacherarea', width:100, formatter:WhgComm.FMTAreaType">区域</th>
        <th data-options="field:'teacherstate', width:100, formatter:traStateFMT" >状态</th>
        <th data-options="field:'_opt', width:400, formatter:WhgComm.FMTOpt,fixed:true, optDivId:'whgdg-opt'">操作</th>
    </tr>
    </thead>
</table>
<!-- 表格 END -->

<!-- 表格操作工具栏 -->
<div id="whgdg-tb" style="display: none;">
    <div class="whgdg-tb-srch">
        <input class="easyui-combobox" style="width: 200px;" name="teacherarttyp" prompt='请选择艺术类型' data-options="valueField:'id', textField:'text', data:WhgComm.getArtType()"/>
        <input class="easyui-combobox" style="width: 200px;" name="teacherarea" prompt='请选择区域' data-options="valueField:'id', textField:'text', data:WhgComm.getAreaType()"/>
        <input class="easyui-textbox" style="width: 200px;" name="teachername" prompt='请选择老师名称' data-options="validType:'length[1,30]'"/>
        <select class="easyui-combobox" name="teacherstate" prompt='请选择状态',>
            <option value="">全部</option>
            <option value="0">已编辑</option>
            <option value="1">待审核</option>
            <option value="2">已审核</option>
            <option value="3">已发布</option>
        </select>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="WhgComm.search('#whgdg', '#whgdg-tb');">查 询</a>
    </div>
</div>
<!-- 表格操作工具栏-END -->

<!-- 操作按钮 -->
<div id="whgdg-opt" style="display:none" >
    <shiro:hasPermission name="${resourceid}:view"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" method="view">查看</a></shiro:hasPermission>
    <shiro:hasPermission name="${resourceid}:edit"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="teacherstate" validVal="1,2" method="edit">编辑</a></shiro:hasPermission>
    <shiro:hasPermission name="${resourceid}:checkon"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="teacherstate" validVal="1" method="checkon">审核</a></shiro:hasPermission>
    <shiro:hasPermission name="${resourceid}:back"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="teacherstate" validVal="2" method="checkoff">打回</a></shiro:hasPermission>
    <shiro:hasPermission name="${resourceid}:publish"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="teacherstate" validVal="2" method="publish">发布</a></shiro:hasPermission>
    <shiro:hasPermission name="${resourceid}:publishoff"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="teacherstate" validVal="3" method="publishoff">撤销发布</a></shiro:hasPermission>
    <shiro:hasPermission name="${resourceid}:del"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validFun="_del" method="del">删除</a></shiro:hasPermission>
</div>
<!-- 操作按钮-END -->


<script>
function _del(idx) {
    var row = $("#whgdg").datagrid("getRows")[idx];
    return row.teacherstate != 3;
}


/**
 * 编辑
 * @param idx
 */
function edit(idx){
    var row = $("#whgdg").datagrid("getRows")[idx];
    WhgComm.editDialog('${basePath}/admin/train/tea/view/add?id='+row.teacherid);
}

/**
 * 查看
 * @param idx
 */
function view(idx){
    var row = $("#whgdg").datagrid("getRows")[idx];
    WhgComm.editDialog('${basePath}/admin/train/tea/view/add?targetShow=1&id='+row.teacherid);
}


/**
 * 删除
 * @param idx
 */
function del(idx){
    var row = $("#whgdg").datagrid("getRows")[idx];
    $.messager.confirm("确认信息", '此操作将会永久性删除数据！', function(r){
        if (r){
            $.messager.progress();
            $.post('${basePath}/admin/train/tea/del', {teacherid: row.teacherid}, function(data){
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
 * 发布
 * @param idx
 */
function publish(idx){
    var row = $("#whgdg").datagrid("getRows")[idx];
    $.messager.confirm("确认信息", "确定要发布选中的项吗？", function(r){
        if (r){
            __updStateSend(row.teacherid, 2, 3);
        }
    })
}

/**
 * 取消发布
 * @param idx
 */
function publishoff(idx){
    var row = $("#whgdg").datagrid("getRows")[idx];
    $.messager.confirm("确认信息", "确定要取消发布选中的项吗？", function(r){
        if (r){
            __updStateSend(row.teacherid, 3, 2);
        }
    })
}


/**
 * 审通过
 * @param idx
 */
function checkon(idx){
    var row = $("#whgdg").datagrid("getRows")[idx];
    $.messager.confirm("确认信息", "确定要审核选中的项吗？", function(r){
        if (r){
            __updStateSend(row.teacherid, 1, 2);
        }
    })
}

/**
 * 打回
 * @param idx
 */
function checkoff(idx){
    var row = $("#whgdg").datagrid("getRows")[idx];

    $.messager.confirm("确认信息", "确定要打回选中的项吗？", function(r){
        if (r){
            __updStateSend(row.teacherid, 2, 1);
        }
    })
}

/**
 * 修改状态提交
 * @param ids
 * @param formstates
 * @param tostate
 * @private
 */
function __updStateSend(ids, formstates, tostate){
    $.messager.progress();
    var params = {ids: ids, formstates: formstates, tostate: tostate};
    $.post('${basePath}/admin/train/tea/updstate', params, function(data){
        $("#whgdg").datagrid('reload');
        if (!data.success || data.success != "1"){
            $.messager.alert("错误", data.errormsg||'操作失败', 'error');
        }
        $.messager.progress('close');
    }, 'json');
}

</script>
</body>
</html>
