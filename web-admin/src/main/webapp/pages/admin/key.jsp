<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath(); request.setAttribute("basePath", basePath); %>
<%request.setAttribute("resourceid", request.getParameter("rsid"));%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<base href="${basePath }/">
<%@include file="/pages/comm/header.jsp"%>
<script>
/**
 * 返回按钮
 * @returns
 */
function back(){
 	$("#edit").dialog('close');
}
/**
 * 添加培训
 */
function addKey(){
	editWinform.setWinTitle("添加页面");
	editWinform.openWin();
	$('#ff').form('clear');
	var url = getFullUrl('/admin/addKey');
	editWinform.setFoolterBut({
		onClick : function() {
			$('#ff').form('submit', {
				url : url,
				success : function(data) {
					$('#keyDG').datagrid('reload');
					$.messager.alert("提示", "操作成功");
					editWinform.closeWin();
				}
			});
		}
	})
}
 
 /**
  * 修改
  */
function editKey(index){
	 var row = WHdg.getRowData(index);
	 var id = row.id;
	//打开div窗口
	 editWinform.setWinTitle("编辑页面");
	 editWinform.openWin();
	 if (row){
		$('#ff').form('load', row);
		var url = getFullUrl('/admin/updateKey');
		 editWinform.setFoolterBut({
				onClick : function() {
					$('#ff').form('submit', {
						url :url,
						success : function(data) {
							$('#keyDG').datagrid('reload');
							$.messager.alert("提示", "操作成功");
							editWinform.closeWin();
						}
					});
				}
		   })
	} 
}

/*
 * 删除关键字 */
function destroyKey(index){
	var row = WHdg.getRowData(index);
	var id = row.id;
	$.messager.confirm('确认对话框', '您确认要删除吗？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: "${basePath}/admin/delKey",
				data: {id : id},
				success: function(msg){
					$('#keyDG').datagrid('reload');
				}
			});
		}
	});
}
var editWinform = new WhuiWinForm("#edit_win");
$(function(){
	//定义表格参数
	var options = {
		title: '关键字管理',			
		url: './admin/getAllKeys',
		columns: [[
			{field:'id',title:'关键字ID',width:100},    
	        {field:'name',title:'关键字名称',width:100},    
	        {field:'type',title:'关键字类型',width:100,align:'right', formatter: keyFMT},
	        {field:'idx',title:'关键字序号',width:100,align:'right'},
			{field:'opt', title:'操作', formatter: WHdg.optFMT, optDivId: 'keyOPT'}
		]]
	};
	//初始表格
	WHdg.init('keyDG', 'keyTB', options);
	editWinform.init();
});

</script>
</head>
<body class="easyui-layout">
		<div data-options="region:'center',title:'',iconCls:'icon-ok'">
			<table id="keyDG"></table>
			
			<div id="keyTB" style="height:auto">
			   <div>
			        <shiro:hasPermission name="${resourceid}:add"><a class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addKey()">添加</a></shiro:hasPermission>
			   </div>
			   <div>
				     关键字类型: 
				    <input class="easyui-combotree" name="type" data-options="valueField:'typid',textField:'typname',url:'${basePath}/comm/whtyp?type=7'"/>
				    <a href="javascript:void(0)" class="easyui-linkbutton search" iconCls="icon-search">查询</a>
			   </div>
			</div>
			<!-- 操作按钮 -->
			<div id="keyOPT" class="none">
				<shiro:hasPermission name="${resourceid}:edit"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" method="editKey">编辑</a></shiro:hasPermission> 
				<shiro:hasPermission name="${resourceid}:del"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" method="destroyKey">删除</a></shiro:hasPermission>
			</div>
		</div>
		
		 <!-- div弹出层 --> 
		 <!-- 编辑DIV -->
	<div id="edit_win" class="none">
		<form method="post" id="ff">
		 <!-- 隐藏作用域 -->
	      <input type="hidden" name="id"/>
			<div class="main">
				<div class="row">
					<div><label>关键字类型:</label></div>
					<div>
						<select class="easyui-combotree" name="type" id="type" style="width:90%;height:35px"
						 data-options="required:true, valueField:'typid',textField:'typname',url:'${basePath}/comm/whtyp?type=7'">
						</select>
					</div>
				</div>
				<div class="row">
					<div><label>关键字名称:</label></div>
					<div>
						<input class="easyui-textbox" name="name" style="width:90%;height:35px"
						data-options="required:true, validType:'length[0,10]'"/>
					</div>
				</div>
				<div class="row">
					<div><label>关键字排序:</label></div>
					<div>
						<div>
						<input class="easyui-numberspinner" name="idx" id="idx" value="" style="width:90%;height:35px"
						data-options="increment:1, required:true,min:1,max:999"/>
					</div>
					</div>
				</div>
			</div>
		</form>
	</div>
</body>
</html>