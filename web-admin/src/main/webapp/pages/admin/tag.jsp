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
/** 添加标签方法 */
function addTag(){
	editWinform.setWinTitle("添加页面");
	editWinform.openWin();
	$('#ff').form('clear');
	var url = getFullUrl('./admin/addTag');
	editWinform.setFoolterBut({
		onClick : function() {
			$('#ff').form('submit', {
				url : url,
				success : function(data) {
					$('#tagDG').datagrid('reload');
					$.messager.alert("提示", "操作成功");
					editWinform.closeWin();
				}
			});
		}
	})
}
/** 更新标签方法 */
function editTag(index){
	var row = WHdg.getRowData(index);
	var id = row.id;
	if (row){
		$('#ff').form('load', row);
		editWinform.setWinTitle("编辑页面");
		editWinform.openWin();
		var url = getFullUrl('./admin/updateTag');
		editWinform.setFoolterBut({
			onClick : function() {
				$('#ff').form('submit', {
					url : url,
					success : function(data) {
						$('#tagDG').datagrid('reload');
						$.messager.alert("提示", "操作成功");
						editWinform.closeWin();
					}
				});
			}
		})
	} 
}

/*
 * 删除标签 */
function destroyTag(index){
	var row = WHdg.getRowData(index);
	var id = row.id;
	$.messager.confirm('确认对话框', '您确认要删除吗？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: "${basePath}/admin/delTag",
				data: {id : id},
				success: function(msg){
					$('#tagDG').datagrid('reload');
				}
			});
		}
	});
}
/**
 * 初始表格
 */
 var editWinform = new WhuiWinForm("#edit_win");
$(function(){
	//定义表格参数
	var options = {
		title: '标签管理',			
		url: './admin/getAllTags',
		columns:[[    
			{field:'id',title:'标签ID',width:100},    
			{field:'name',title:'标签名称',width:100},    
			{field:'type',title:'标签类型',width:100,align:'right', formatter: tagFMT},
			{field:'idx',title:'标签序号',width:100,align:'right'},
			{field:'opt', title:'操作', formatter: WHdg.optFMT, optDivId:'tagOPT'}
		]]  
	};
	//初始表格
	WHdg.init('tagDG', 'tagTB',  options);
	editWinform.init();
});
</script>
</head>
<body class="easyui-layout">
		<div data-options="region:'center',title:'',iconCls:'icon-ok'">
			<table id="tagDG"></table>
			
			<div id="tagTB" style="height:auto">
				<div>
				        标签类型: 
				    <input class="easyui-combotree" name="type" data-options="valueField:'typid',textField:'typname',url:'${basePath}/comm/whtyp?type=6'"/>
				    <a href="#" class="easyui-linkbutton search" iconCls="icon-search">查询</a>
					<shiro:hasPermission name="${resourceid}:add"><a class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addTag()">添加</a></shiro:hasPermission>
				</div>
			</div>
			<!-- 操作按钮 -->
			<div id="tagOPT" class="none">
				<shiro:hasPermission name="${resourceid}:edit"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" method="editTag">编辑</a></shiro:hasPermission> 
				<shiro:hasPermission name="${resourceid}:del"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" method="destroyTag">删除</a></shiro:hasPermission>
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
					<div><label>标签类型:</label></div>
					<div>
						<select class="easyui-combotree" name="type" id="type" style="width:90%;height:35px"
						 data-options="required:true, valueField:'typid',textField:'typname',url:'${basePath}/comm/whtyp?type=6'">
						</select>
					</div>
				</div>
				<div class="row">
					<div><label>标签名称:</label></div>
					<div>
						<input class="easyui-textbox" name="name" style="width:90%;height:35px"
						data-options="required:true, validType:'length[0,10]'"/>
					</div>
				</div>
				<div class="row">
					<div><label>标签排序:</label></div>
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