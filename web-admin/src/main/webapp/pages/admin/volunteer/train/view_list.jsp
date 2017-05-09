<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<% request.setAttribute("basePath", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath());%>
<% request.setAttribute("resourceid", request.getParameter("rsid")); %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>志愿培训</title>
    <%@include file="/pages/comm/admin/header.jsp"%>
    
    <script type="text/javascript" charset="utf-8" src="${basePath}/static/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="${basePath}/static/ueditor/ueditor.all.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="${basePath}/static/ueditor/lang/zh-cn/zh-cn.js"></script>
</head>
<body>

<!-- 表格 -->
<table id="whgdg" title="志愿培训管理" class="easyui-datagrid" style="display: none"
       data-options="fit:true, striped:true, rownumbers:true, fitColumns:true, singleSelect:false, checkOnSelect:true, selectOnCheck:true, pagination:true, toolbar:'#whgdg-tb', url:'${basePath}/admin/volun/findVolun'">
    <thead>
    <tr>
    	<th data-options="field:'ck', checkbox:true"></th>
        <th data-options="field:'zypxtype', sortable: true,width:80,formatter:WhgComm.FMTTrainType,formatter:function(val,row,index){ return WhgSysData.FMT(val, WhgSysData.getTypeData('12'));}">分类</th>
        <th data-options="field:'zypxshorttitle', width:80,sortable: true">列表短标题</th>
        <th data-options="field:'zypxfrom', width:80,sortable: true">来源</th>
        <th data-options="field:'zypxpic', width:80,formatter:WhgComm.FMTImg">列表图</th>
        <th data-options="field:'zypxstate', width:60, formatter:traStateFMT">状态</th>
        <th data-options="field:'_opt', formatter:WhgComm.FMTOpt, optDivId:'whgdg-opt'">操作</th>
    </tr>
    </thead>
</table>
<!-- 表格 END -->

<!-- 表格操作工具栏 -->
<div id="whgdg-tb" style="display: none;">
    <div class="whgd-gtb-btn">
			<shiro:hasPermission name="${resourceid}:add"><a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" size='small' id="btn" onclick="doAdd()">添加</a></shiro:hasPermission>
			<shiro:hasPermission name="${resourceid}:checkon"><a href="javascript:void(0)" class="easyui-linkbutton" size='small' onclick="allcheck()">批量审核</a></shiro:hasPermission>
			<shiro:hasPermission name="${resourceid}:publish"><a href="javascript:void(0)" class="easyui-linkbutton" size='small' onclick="allpublish()">批量发布</a></shiro:hasPermission>
			<shiro:hasPermission name="${resourceid}:publishoff"><a href="javascript:void(0)" class="easyui-linkbutton" size='small' onclick="allnoPublish()">批量取消发布</a></shiro:hasPermission>
			<shiro:hasPermission name="${resourceid}:checkoff"><a href="javascript:void(0)" class="easyui-linkbutton" size='small' onclick="allback()">批量打回</a></shiro:hasPermission>
		</div>
    <div class="whgdg-tb-srch">
			<input class="easyui-combobox" name="zypxtype" style="width: 200px; height:28px" data-options="editable:true, valueField:'id', textField:'text',prompt:'请选择志愿培训类型', data:WhgSysData.getTypeData('12')"/>
			<input class="easyui-textbox" name="zypxtitle" style="width: 200px; height:28px" data-options="prompt:'请输入志愿培训标题',validType:'length[1,30]'"/>
			<select class="easyui-combobox" name="zypxstate" style="width: 200px; height:28px" data-options="editable:true,prompt:'请选择状态查询',validType:'length[1,30]'" >
				<option value="">请选择状态</option>
				<option value="1">待审核</option>
				<option value="2">已审核</option>
				<option value="3">已发布</option>
			</select>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="WhgComm.search('#whgdg', '#whgdg-tb');">查 询</a>
		</div>
</div>
<!-- 表格操作工具栏-END -->

<!-- 操作按钮 -->
<div id="whgdg-opt" style="display:none">
	<shiro:hasPermission name="${resourceid}:view"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true"  method="doSee">查看详情</a></shiro:hasPermission>
	<shiro:hasPermission name="${resourceid}:edit"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="zypxstate" validVal="1" method="doEdit">编辑</a></shiro:hasPermission>
		<shiro:hasPermission name="${resourceid}:checkon"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="zypxstate" validVal="1" method="check">审核</a></shiro:hasPermission>
	<shiro:hasPermission name="${resourceid}:checkoff"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="zypxstate" validVal="2" method="nocheck">打回</a></shiro:hasPermission>
	<shiro:hasPermission name="${resourceid}:publish"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="zypxstate" validVal="2" method="publish">发布</a></shiro:hasPermission>
	<shiro:hasPermission name="${resourceid}:publishoff"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="zypxstate" validVal="3" method="nopublish">取消发布</a></shiro:hasPermission>
	<shiro:hasPermission name="${resourceid}:del"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="zypxstate" validVal="1" method="delVolun">删除</a></shiro:hasPermission>
</div>
<!-- 操作按钮-END -->

<!-- script -->
<script type="text/javascript">

/**
 * 添加志愿培训
 */
function doAdd() {
    WhgComm.editDialog('${basePath}/admin/volun/view/add');
}

/**
 * 编辑信息
 * @param idx 行下标
 */
function doEdit(idx) {
    var curRow = $('#whgdg').datagrid('getRows')[idx];
    WhgComm.editDialog('${basePath}/admin/volun/view/edit?id='+curRow.zypxid);
}

/**
 * 查看资料
 * @param idx 行下标
 */
function doSee(idx) {
    var curRow = $('#whgdg').datagrid('getRows')[idx];
    WhgComm.editDialog('${basePath}/admin/volun/view/edit?id='+curRow.zypxid+"&onlyshow=1");
}

/**
 * 一键审核提交
 */
function subcheck(zypxids,fromstate,tostate,title){
	$.messager.confirm('确认对话框', '您确认将所选择的'+title+'吗？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/volun/checkAll'),
				data: {zypxids : zypxids,fromstate: fromstate, tostate:tostate },
				success: function(data){
					if(data.success=="success"){
						//$.messager.alert("提示", data.msg);
						$('#whgdg').datagrid('reload');
					}else{
						$.messager.alert('提示', '操作失败。原因：'+data.errmsg, 'error');
					}
				}
			});
		}
	});
}

/**批量取消发布*/
function allnoPublish(){
	var rows={};
	var title = "取消发布";
	rows = $("#whgdg").datagrid("getSelections");
	if (rows == "" || rows == null) {
		$.messager.alert('提示', '请选择要操作的数据！');
		return;
	}
	var zypxids ="", _split = "";
	for (var i = 0; i<rows.length; i++){
		zypxids += _split+rows[i].zypxid;
		_split = ",";
	}
	subcheck(zypxids,3,2);
}

/**批量打回*/
function allback(){
	var rows={};
	var title = "批量打回";
	rows = $("#whgdg").datagrid("getSelections");
	if (rows == "" || rows == null) {
		$.messager.alert('提示', '请选择要操作的数据！');
		return;
	}
	var zypxids ="", _split = "";
	for (var i = 0; i<rows.length; i++){
		zypxids += _split+rows[i].zypxid;
		_split = ",";
	}
	subcheck(zypxids,2,1,title);
}

/**批量审核*/
function allcheck(){
	var rows={};
	var title = "审核通过";
	rows = $("#whgdg").datagrid("getSelections");
	if (rows == "" || rows == null) {
		$.messager.alert('提示', '请选择要操作的数据！');
		return;
	}
	var zypxids ="", _split = "";
	for (var i = 0; i<rows.length; i++){
		zypxids += _split+rows[i].zypxid;
		_split = ",";
	}
	subcheck(zypxids,1,2,title);
}

/**批量发布*/
function allpublish(){
	var rows={};
	var title = "批量发布";
	rows = $("#whgdg").datagrid("getSelections");
	if (rows == "" || rows == null) {
		$.messager.alert('提示', '请选择要操作的数据！');
		return;
	}
	var zypxids ="", _split = "";
	for (var i = 0; i<rows.length; i++){
		zypxids += _split+rows[i].zypxid;
		_split = ",";
	}
	subcheck(zypxids,2,3,title);
}
/**删除志愿培训*/
function delVolun(idx){
	var curRow = $('#whgdg').datagrid('getRows')[idx];
	var zypxid = curRow.zypxid;
	$.messager.confirm('确认对话框', '您确认要删除吗？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/volun/delpx'),
				data: {zypxid : zypxid},
				success: function(data){
					if(data.success == '1'){
						$('#whgdg').datagrid('reload');
					   }else{
						   $.messager.alert('提示', '操作失败。原因：'+data.errmsg, 'error');
					   }
				}
			});
		}
	});
	
}

/**审核*/
function check(idx){
	var curRow = $('#whgdg').datagrid('getRows')[idx];
	var zypxid = curRow.zypxid;
	var title = "审核";
	docheck(title,zypxid,1,2);
}
/**发布*/
function publish(idx){
	var curRow = $('#whgdg').datagrid('getRows')[idx];
	var zypxid = curRow.zypxid;
	var title = "发布";
	docheck(title,zypxid,2,3);
}
/**打回审核*/
function nocheck(idx){
	var curRow = $('#whgdg').datagrid('getRows')[idx];
	var zypxid = curRow.zypxid;
	var title = "打回";
	docheck(title,zypxid,2,1);
}
/**取消发布*/
function nopublish(idx){
	var curRow = $('#whgdg').datagrid('getRows')[idx];
	var zypxid = curRow.zypxid;
	var title = "取消发布";
	docheck(title,zypxid,3,2);
}
/** 审核事件提交处理 */
function docheck(title,zypxid,fromstate,tostate){
	$.messager.confirm('确认对话框', '您确认将所选择的'+title+'吗？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/volun/check'),
				data: {zypxid : zypxid,fromstate:fromstate,tostate:tostate},
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

</script>
<!-- script END -->
</body>
</html>