<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath(); request.setAttribute("basePath", basePath); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<%@include file="/pages/comm/header.jsp"%>
<script>
/** 查询方法 */
function searchZx(){
	
}

/** 修改方法 */
function updFun(id){
	window.location.href = '${basePath}/admin/train/kecheng?traitmid='+id+'&type=';
}

/** 删除方法 */
function delFun(id){
	$.messager.confirm('确认对话框', '您确认要执行删除操作吗？', function(r){
		if (r){
/* 			$.ajax({
			   type: "POST",
			   url: "${basePath}/admin/delzx",
			   data: "id="+id,
			   success: function(msg){
				   $('#dg').datagrid('reload');
			   }
			}); */
		}
	});


}

/** 表格操作栏 */
function optFun(val, rowData, index){
	return '<a href="#" onclick="updFun(\''+rowData.id+'\');">课程设置</a> <a href="#" onclick="delFun(\''+rowData.id+'\');">删除</a>';
}

$(function(){
	//初始表格
	$('#dg').datagrid({    
	    url:'${basePath}/admin/train/sreachhis',  
	    title: '培训回收站',
	    fit: true,
	    rownumbers: true,
	    //fitColumns: true,
	    singleSelect: true,
	    toolbar: '#tb',
	    pagination: true,
	    pageSize: 20,
	    pageList: [10,20,50],
	    columns:[[    
	        {field:'traid',title:'traid',width:100},    
			{field:'traitmid',title:'traitmid',width:100},    
			{field:'enrid',title:'enrid',width:100,align:'right'},
			{field:'sdate',title:'sdate',width:100,align:'right'},
			{field:'state', title:'操作', width:100, align:'center', formatter:optFun}
	    ]]    
	}); 
});
</script>
</head>
<body>
		<table id="dg"></table>
		<div id="tb" style="height:auto">
			<div>
				分类: <select id="cc" class="easyui-combotree" style="width:200px;" data-options="url:'${basePath}/admin/findzxtyp'"></select>  
				Date From: <input class="easyui-datebox" style="width:80px">
				To: <input class="easyui-datebox" style="width:80px">
				Language: 
				<select class="easyui-combobox" panelHeight="auto" style="width:100px">
					<option value="java">Java</option>
					<option value="c">C</option>
					<option value="basic">Basic</option>
					<option value="perl">Perl</option>
					<option value="python">Python</option>
				</select>
				<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="searchZx();">查询</a>
			</div>
		</div>
</body>
</html>