<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath(); request.setAttribute("basePath", basePath); %>
<%request.setAttribute("resourceid", request.getParameter("rsid"));%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>基本信息列表</title>
<%@include file="/pages/comm/header.jsp"%>
<script>
function showState(val, rowData, index){
	return rowData.systate == '1' ? '启用' : '关闭';
}
/**删除配置信息
 */
function delsys(index){
	var row = WHdg.getRowData(index);
	var sysid = row.sysid;
	$.messager.confirm('确认对话框', '您确认要删除吗？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/conf/delconfig'),
				data: {sysid : sysid},
				success: function(msg){
					$('#syspage').datagrid('reload');
					$.messager.alert("提示","删除成功");
				}
			});
		}
	});
}
/**
 * 添加配置信息
 */
 function addsys(){
	 editWinform.setWinTitle("添加页面");
	 editWinform.openWin();
	//清空表单的值
	$('#ff').form('clear');
	//设置默认值
	$('#systate').combobox('setValue', '0');
	$('#systype').combobox('setValue', '11');
	 editWinform.setFoolterBut({onClick:function(){
		 $('#ff').form('submit',{ 
			  url:getFullUrl('./admin/conf/addconfig'),
			  success: function(data){
				  var data = eval('(' + data + ')');
					//异常处理
					if(data && data.success == '0'){
						$('#syspage').datagrid('reload');
						$.messager.alert("提示", "操作成功");
						editWinform.closeWin();
					}else{
						$.messager.alert('提示', '操作失败。原因：'+data.errmsg, 'error');
					}
	 		    }    
		 });
	 }})
	
}
/**
 * 修改配置信息
 */
 function upsys(index){
    var row = WHdg.getRowData(index);
	if (row) {
		 editWinform.setWinTitle("编辑页面");
		 editWinform.openWin();
		 $('#ff').form('load', row);
		var url = getFullUrl('/admin/conf/upconfig');
		 editWinform.setFoolterBut({onClick:function(){
			 $('#ff').form('submit',{ 
				  url:url,
				  success: function(data){
					  var data = eval('(' + data + ')');
						//异常处理
						if(data && data.success == '0'){
							$('#syspage').datagrid('reload');
							$.messager.alert("提示", "操作成功");
							editWinform.closeWin();
						}else{
							$.messager.alert('提示', '操作失败。原因：'+data.errmsg, 'error');
						}
		 		  }    
			 });
		 }})
	}
	
}

var editWinform = new WhuiWinForm("#edit_win");
/** 页面装载完成后事件处理 */
$(function(){
	//定义表格参数
	var options = {
		title: '基础配置',
		url: getFullUrl('/admin/conf/seleconfig'),
		columns: [[    
			{field:'syskey',title:'配置项', sortable:true,width:100},
			{field:'sysval',title:'配置值', sortable:true,width:100},
			{field:'systate',title:'配置状态',align:'center',width:80,sortable:true,formatter:showState},
			//{field:'systype',title:'配置类型', sortable:true},
			{field:'sysmome',title:'配置说明',width:100, sortable:true},
			{field:'opt', title:'操作',width:80,align:'center', formatter: WHdg.optFMT, optDivId:'bnt'}
		]]
	};
	
	//初始表格
	WHdg.init('syspage', 'syspageTB', options);
	
	//初始化div页面
	editWinform.init();
});
</script>
</head>
<body>
	<!-- datagrid div -->
	<div id="syspage"></div>
	
	<!-- datagrid toolbar -->
	<div id="syspageTB" class="grid-control-group">
        <shiro:hasPermission name="${resourceid}:add"><a href="javascript:void(0)" class="easyui-linkbutton search" iconCls="icon-add" plain="true" onclick="addsys();">添加</a></shiro:hasPermission>
	</div>
	
	<!-- 操作按钮 -->
	<div id="bnt" class="none">
		<shiro:hasPermission name="${resourceid}:edit"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" method="upsys">编辑</a></shiro:hasPermission> 
		<shiro:hasPermission name="${resourceid}:del"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" method="delsys">删除</a></shiro:hasPermission>
	</div>
	
	<!-- 编辑DIV -->
	<div id="edit_win" class="none" data-options="maximized:true">
		<form method="post" id="ff">
		 <!-- 隐藏作用域 -->
	      <input type="hidden" name="sysid"/>
			<div class="main">
				<div class="row">
					<div><label>配置项:</label></div>
					<div>
						<input class="easyui-textbox" name="syskey" value="" style="width:90%;height:35px"
						data-options="required:true, validType:'length[0,30]'"/>
					</div>
				</div>
				<div class="row">
					<div><label>配置值:</label></div>
					<div>
						<input class="easyui-textbox" name="sysval" value="" style="width:90%;height:35px"
						data-options="required:true, validType:'length[0,60]'"/>
					</div>
				</div>
				<div class="row">
					<div><label>配置状态:</label></div>
					<div>
						<select class="easyui-combobox" name="systate" id="systate" style="width:90%;height:35px"
						data-options="editable:false, required:true, panelHeight:'auto'">
							<option value="1">启用</option>
							<option value="0">关闭</option>
						</select>
					</div>
				</div>
				<div class="row">
					<div><label>配置类型:</label></div>
					<div>
						<select class="easyui-combobox" name="systype" id="systype" style="width:90%;height:35px"
						data-options="editable:false, required:true, panelHeight:'auto'">
							<option value="11">默认类型</option>
						</select>
					</div>
				</div>
				<div class="row">
					<div><label>配置说明:</label></div>
					<div>
						<input class="easyui-textbox" name="sysmome" style="width:90%;height:120px"
						data-options="validType:'length[0,150]',multiline:true" />
					</div>
				</div>
			</div>
		</form>
	</div>
	
</body>
</html>