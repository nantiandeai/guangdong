<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath(); request.setAttribute("basePath", basePath); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>培训审核列表</title>
<%@include file="/pages/comm/header.jsp"%>
<script>
/** 审核事件处理 */
function docheck(idx){
	//alert(JSON.stringify(WHdg.getRowData(idx)));
	var row = WHdg.getRowData(idx);
	var traitmid = row.traitmid;
	var state = row.state;
	$.messager.confirm('确认对话框', '确定通过审核？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/train/checkTraitm'),
				data: {traitmid : traitmid,state : state},
				success: function(msg){
				$('#checklistDG').datagrid('reload');
				}
			});
		}
	}); 
}

/** 打回事件处理 */
function doback(idx){
	//alert(JSON.stringify(WHdg.getRowData(idx)));
	var row = WHdg.getRowData(idx);
	var traitmid = row.traitmid;
	var state = row.state;
	$.messager.confirm('确认对话框', '确定打回待审核状态？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/train/goBack'),
				data: {traitmid : traitmid,state : state},
				success: function(msg){
				$('#checklistDG').datagrid('reload');
				}
			});
		}
	}); 
}

/** 页面装载完成后事件处理 */
$(function(){
	//定义表格参数
	var options = {
		title: '培训审核',			
		url: getFullUrl('/admin/train/sreachAllTrain'),
		queryParams: {stateArray:"1"},
		columns: [[
			//{field:'traid',title:'培训标识', sortable:true},    
			{field:'traitmid',title:'培训标识', sortable:true},    
			{field:'tratitle',title:'标题', sortable:true},
			{field:'tratyp',title:'培训类型', sortable:true, formatter: tratypFMT},
			{field:'arttyp',title:'艺术类型', sortable:true, formatter: arttypFMT},
			{field:'agelevel',title:'适合年龄', sortable:true, formatter: agelevelFMT},
			{field:'tralevel',title:'课程级别', sortable:true, formatter: tralevelFMT},
			{field:'sdate',title:'开始日期', sortable:true, formatter: dateFMT},
			{field:'edate',title:'结束日期', sortable:true, formatter: dateFMT},
			
			{field:'teacher',title:'授课老师', sortable:true},
			{field:'teacherid',title:'授课老师标识', sortable:true},    
			{field:'isrealname',title:'必须实名', sortable:true, formatter:yesNoFMT},    
			{field:'isfulldata',title:'必须完善资料', sortable:true, formatter:yesNoFMT},
			{field:'isonlyone',title:'1人1项限制', sortable:true, formatter:yesNoFMT},    
			{field:'islogincomment',title:'须登录点评', sortable:true, formatter:yesNoFMT},
			{field:'isattach',title:'需上传附件', sortable:true, formatter:yesNoFMT},
			
			{field:'isenrol',title:'需要报名', sortable:true, formatter:yesNoFMT},
			{field:'isenrolqr',title:'报名审核', sortable:true, formatter:yesNoFMT},
			{field:'enrollimit',title:'报名人数限制', sortable:true},
			{field:'enrolstime',title:'报名开始日期', sortable:true, formatter: dateFMT},
			{field:'enroletime',title:'报名结束日期', sortable:true, formatter: dateFMT},
			{field:'isnotic',title:'是否需要发送面试通知', sortable:true, formatter:yesNoFMT},
			{field:'state',title:'状态', sortable:true, formatter :traStateFMT},
			{field:'opt', title:'操作', formatter: WHdg.optFMT, optDivId:'checklistOPT'}
		]]
	};
	
	//初始表格
	WHdg.init('checklistDG', 'checklistTB', options);
});
</script>
</head>
<body>
	<!-- datagrid div -->
	<div id="checklistDG"></div>
	
	<!-- datagrid toolbar -->
	<div id="checklistTB">
		<div>
			培训类型:
			<input class="easyui-combobox" name="tratyp" data-options="valueField:'typid',textField:'typname',url:'${basePath}/comm/whtyp?type=2'"/>
			艺术类型: 
			<input class="easyui-combobox" name="arttyp" data-options="valueField:'typid',textField:'typname',url:'${basePath}/comm/whtyp?type=0'"/>
			适合年龄: 
			<input class="easyui-combobox" name="agelevel" data-options="valueField:'typid',textField:'typname',url:'${basePath}/comm/whtyp?type=3'"/>
			课程级别: 
			<input class="easyui-combobox" name="tralevel" data-options="valueField:'typid',textField:'typname',url:'${basePath}/comm/whtyp?type=4'"/>
			<br>
			培训标题:
			<input class="easyui-textbox" name="title" data-options="validType:'length[1,30]'"/>
			开始时间:
			<input class="easyui-datebox" name="sdate_s" data-options=""/>到<input class="easyui-datebox" name="sdate_e" data-options=""/>
			<a href="#" class="easyui-linkbutton search" iconCls="icon-search">查询</a>
		</div>
	</div>
	
	<!-- 操作按钮 -->
	<div id="checklistOPT" class="none">
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" method="docheck">审核</a> 
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" method="doback">打回</a>
	</div>
</body>
</html>