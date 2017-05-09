<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath(); request.setAttribute("basePath", basePath); %>
<%request.setAttribute("resourceid", request.getParameter("rsid"));%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>采集数据管理</title>
<base href="${basePath }/">
<%@include file="/pages/comm/header.jsp"%>
<script>
function showState(val, rowData, index) {
	return rowData.fromstate == '1' ? '有效' : '无效';
}

/**
 * 审核
 */
function mdyState(fromid, state){
	$.messager.confirm('确认对话框', '确定要改变状态吗.', function(r) {
		if (r) {
			$.ajax({
				url : './admin/adve/uptype',
				data : {fromid : fromid, fromstate: state},
				type : "POST",
				success : function(data) {
					if (data && data.success == "0") {
						$("#operDG").datagrid("reload");
						$.messager.alert("提示", "操作成功");
					} else {
						$.messager.alert("提示", "操作失败");
					}
				}
			});
		}
	});
}

/**
 *有效状态
 */
function tostate(index, fromid){
	 mdyState(fromid, '1');
}

/**
 *无效状态
 */
function dostate(index, fromid){
	mdyState(fromid, '0');
}
/**
 * 查看艺术团信息
 */
 function seeinfo(index){
    var row = WHdg.getRowData(index);
	//清空表单的值
	$('#ff').form('clear');
	$('#ff').form('load', row);
	editWinform.setWinTitle("查看艺术展页面");
	editWinform.openWin();
	var url = getFullUrl('./admin/adve/upoper');
	editWinform.getWinFooter().find("a:eq(0)").hide();
		
 }
	
/**
 * 编辑采集信息
 */
 function upexh(index){
    var row = WHdg.getRowData(index);
	if (row){
		//清空表单的值
		$('#ff').form('clear');
		editWinform.getWinFooter().find("a:eq(0)").show();
		var url = getFullUrl('./admin/adve/upoper');
		editWinform.openWin();
		//初始
		$('#ff').form({
			novalidate: true,
			url : url,
			onSubmit: function(param){    
			    param.fromid = row.fromid;    
		         return $(this).form('enableValidation').form('validate');
			 },    
			success : function(data) {
				data = eval('('+data+')');
				if(data && data.success == '0'){
					editWinform.closeWin();
					$.messager.alert("提示", "操作成功");
					$('#operDG').datagrid('reload');
				 }else{
					 $.messager.alert('提示', '操作失败。原因：'+data.errmsg, 'error');
				  }
				
			}
		});
		
		$('#ff').form('load', row);
		$('#exhpic_up').filebox({required: false});
		
		editWinform.setFoolterBut({
			onClick : function() {
				$('#ff').form('submit');
			}
		});
	} 
}
/**
 * 添加采集信息
 */
function addexh(){
	editWinform.setWinTitle("添加采集数据页面");
	editWinform.openWin();
	editWinform.getWinFooter().find("a:eq(0)").show();
	//初始
	$('#ff').form({
		novalidate: true,
		url : getFullUrl('/admin/adve/addoper'),
		onSubmit: function(param){
			 param.fromstate = '0';
			 return $(this).form('enableValidation').form('validate');
		 },    
		 success : function(data) {
			 data = eval('('+data+')');
			 if(data && data.success == '0'){
				 editWinform.closeWin();
				 $.messager.alert("提示", "操作成功");
				 $('#operDG').datagrid('reload');
			 }else{
				 $.messager.alert('提示', '操作失败。原因：'+data.errmsg, 'error');
			 }
		}
	});
	//清空表单的值
	$('#ff').form('clear');
	//提交表单
	editWinform.setFoolterBut({
		onClick : function() {
			$('#ff').form('submit');
		}
	});
}
 
/**
 * 根据主键id删除
 */
 function deltroupe(index){
	var row = WHdg.getRowData(index);
	var id = row.fromid;
	$.messager.confirm('确认对话框', '您确认要删除吗？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/adve/deloper'),
				data: {fromid : id},
				success: function(data){
					if(data && data.success == '0'){
						$.messager.alert("提示", "操作成功");
						$('#operDG').datagrid('reload');
					 }else{
						 $.messager.alert('提示', '操作失败。原因：'+data.errmsg, 'error');
					 }
					
				}
			});
		}
	});
}
/**
 * 初始表格
 */
 var editWinform = new WhuiWinForm("#oper_win");
$(function(){
	//定义表格参数
	var options = {
		title: '采集数据管理',			
		url:getFullUrl('/admin/adve/seleoper'),
		sortName: 'fromid',
		sortOrder: 'desc',
		//rownumbers:true,
		//singleSelect:false,
		columns:[[
			//{field:'fromid',title:'采集标识',sortable:true},    
            {field:'fromname',title:'采集标题',width:120},
			{field:'fromfetchtype',title:'采集类型',width:80},
			{field:'fromlisturl',title:'采集URL',width:120},
			{field:'fromlistvalinitval',title:'初始值',width:60},
			{field:'fromlistvaladdval',title:'递增值',width:60},
			{field:'fromlistitemmatch',title:'列表元素',width:120},
			{field:'fromitemidmatch',title:'详细地址',width:120},
			//{field:'frominfoaddrmatch',title:'元素主键',width:120},
			{field:'fromstate',title:'状态',sortable:true,formatter : showState,width:60},
			{field:'opt', title:'操作', formatter: WHdg.optFMT, optDivId:'operOPT'}
		]]  
	};
	//初始表格
	WHdg.init('operDG', 'operTB',  options);
	editWinform.init();
});
</script>
</head>
<body class="easyui-layout">
		<div data-options="region:'center',title:'',iconCls:'icon-ok'">
			<table id="operDG"></table>
			
			<div id="operTB" style="height:auto" style="display:none ,padding-top: 10px" >
			     <div>
			     <shiro:hasPermission name="${resourceid}:add">
			        <a class="easyui-linkbutton" iconCls="icon-add" size="large" plain="true" onclick="addexh();">添加</a>
			     </shiro:hasPermission>  
			     </div>
				<div  style="padding-top: 5px">
					采集标题:
					<input class="easyui-textbox" name="fromname" data-options="validType:'length[1,60]'"/>
					状态:
					 <select class="easyui-combobox radio" name="fromstate">
					 				<option value="">全部</option>
			    					<option value="0">无效</option>
			    					<option value="1">有效</option>
					 </select>
					<a href="#" class="easyui-linkbutton search" iconCls="icon-search">查询</a>
				</div>
			</div>
			<!-- 操作按钮 -->
			<div id="operOPT" class="none" style="display:none">
			    <shiro:hasPermission name="${resourceid}:view"><a href="javascript:void(0)" validKey="fromstate" validVal="0,1" method="seeinfo">查看</a></shiro:hasPermission>
			    <shiro:hasPermission name="${resourceid}:edit"><a href="javascript:void(0)" validKey="fromstate" validVal="0" method="upexh">编辑</a></shiro:hasPermission>
			    <shiro:hasPermission name="${resourceid}:del"><a href="javascript:void(0)" validKey="fromstate" validVal="0" method="deltroupe">删除</a></shiro:hasPermission>
			    <a href="javascript:void(0)" validKey="fromstate" validVal="0" method="">采集</a>
				<shiro:hasPermission name="${resourceid}:on">
				<a href="javascript:void(0);" class="easyui-linkbutton" validKey="fromstate" validVal="0" data-options="plain:true" method="tostate" prop="fromid">有效</a>
				</shiro:hasPermission>
			
				<shiro:hasPermission name="${resourceid}:off">
				<a href="javascript:void(0);" class="easyui-linkbutton" validKey="fromstate" validVal="1" data-options="plain:true" method="dostate" prop="fromid">无效</a>
				</shiro:hasPermission>
				
			</div>
		</div>
		 <!-- div弹出层 --> 
		 <!-- 编辑DIV -->
	<div id="oper_win" class="none" data-options="maximized:true"style="display:none" >
		<form method="post" id="ff" enctype="multipart/form-data">
			<div class="main">
			     <div class="row">
					<div><label>采集标题:</label></div>
					<div>
						<input class="easyui-textbox" name="fromname" id="fromname" style="width:90%;height:35px" data-options="required:true,validType:'length[0,60]' ">
						</input>
					</div>
				</div>
				<div class="row">
					<div><label>采集数据类型:</label></div>
					<div>
						<input class="easyui-textbox" name="fromfetchtype" style="width:90%;height:35px" data-options="required:true, validType:'length[0,30]'"/>
					</div>
				</div>
				<div class="row">
					<div><label>采集数据列表页URL:</label></div>
					<div>
						<input class="easyui-textbox" name="fromlisturl" style="width:90%;height:35px" data-options="required:true, validType:'length[0,256]'"/>
					</div>
				</div>
				<div class="row">
					<div><label>采集数据URL初始值:</label></div>
					<div>
						<input class="easyui-textbox" name="fromlistvalinitval" style="width:90%;height:35px" data-options="required:true, validType:'length[0,16]'"/>
					</div>
				</div>
				<div class="row">
					<div><label>采集数据URL递增值:</label></div>
					<div>
						<input class="easyui-textbox" name="fromlistvaladdval" style="width:90%;height:35px" data-options="required:true, validType:'length[0,16]'"/>
					</div>
				</div>
				<div class="row">
					<div><label>采集数据列表页元素:</label></div>
					<div>
						<input class="easyui-textbox" name="fromlistitemmatch" style="width:90%;height:35px" data-options="required:true, validType:'length[0,256]'"/>
					</div>
				</div>
				<div class="row">
					<div><label>采集数据详细页元素:</label></div>
					<div>
						<input class="easyui-textbox" name="frominfoaddrmatch" style="width:90%;height:35px" data-options="required:true, validType:'length[0,256]'"/>
					</div>
				</div>
				<div class="row">
					<div><label>采集数据主键:</label></div>
					<div>
						<div>
							<input class="easyui-textbox" name="fromitemidmatch" style="width:90%;height:35px" data-options="required:true, validType:'length[0,256]'"/>
					    </div>
				    </div>
			   </div>
		  </div>
	  </form>
   </div>
</body>
</html>

