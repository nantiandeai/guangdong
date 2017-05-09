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
/**
 * 删除
 */
 function delfeed(index){
	var row = WHdg.getRowData(index);
	var id = row.feedid;
	$.messager.confirm('确认对话框', '您确认要删除吗？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/shop/delfeed'),
				data: {feedid : id},
				success: function(data){
					if(data && data.success == '0'){
						//$.messager.alert("提示", "操作成功");
						$('#feedDG').datagrid('reload');
					 }else{
						 $.messager.alert('提示', '操作失败。原因：'+data.errmsg, 'error');
					 }
					
				}
			});
		}
	});
	
}
/**
 * 查看
 */
 function seleback(index){
	 var row = WHdg.getRowData(index);
	//清空表单的值
	$('#ff').form('clear');
	$('#ff').form('load', row);
	editWinform.setWinTitle("查看意见反馈页面");
	editWinform.openWin();
	var url = getFullUrl('/admin/shop/upfeed');
	editWinform.getWinFooter().find("a:eq(0)").hide();
}
var editWinform = new WhuiWinForm("#edit_win");
/** 页面装载完成后事件处理 */
$(function(){
	//定义表格参数
	var options = {
		title: '意见咨询页面',
		url:getFullUrl('/admin/shop/selefeed'),
		sortName: 'feedid',
		sortOrder: 'desc',
		fitColumns:true,
		columns:[[ 
			{field:'feedid',title:'咨询意见标识',sortable:true,width:80},    
			{field:'feedname',title:'姓名',width:80},
			{field:'feedcom',title:'单位/地址',width:120},
			{field:'feedphone',title:'电话号码',width:120},
			{field:'feedmail',title:'邮箱',width:120},
			//{field:'feeddesc',title:'咨询意见详情'},
			{field:'opt', title:'操作', formatter: WHdg.optFMT, optDivId:'feedOPT'}
		]]  
	};
	
	//初始表格
	WHdg.init('feedDG', 'feedTB', options);
	
	//初始化div页面
	editWinform.init();
});
</script>
</head>
<body>
	<!-- datagrid div -->
	<div id="feedDG"></div>
	
	<!-- datagrid toolbar -->
	<div id="feedTB">
		<div>
		<input class="easyui-textbox" name="feedphone" data-options="prompt:'请输入手机号码查询', validType:'length[1,30]'"/>
		<input class="easyui-textbox" name="feedmail" data-options="prompt:'请输入邮箱查询', validType:'length[1,30]'"/>
		<a href="#" class="easyui-linkbutton search" iconCls="icon-search">查询</a>
		</div>
	</div>
	
	<!-- 操作按钮 -->
	<div id="feedOPT" class="none" style="display:none">
		<shiro:hasPermission name="${resourceid}:view"><a href="javascript:void(0)" method="seleback">查看</a></shiro:hasPermission>
		<shiro:hasPermission name="${resourceid}:del"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" method="delfeed">删除</a></shiro:hasPermission>
	</div>
   <!-- div弹出层 --> 
	<div id="edit_win" class="none" data-options="maximized:true" style="display:none">
		<form method="post" id="ff" enctype="multipart/form-data">
		 <!-- 隐藏作用域 -->
			<div class="main">
			   	<div class="row">
					<div><label>姓名:</label></div>
					<div>
						<input class="easyui-textbox" name="feedname" id="feedname" style="width:90%;height:35px" data-options="readonly:true"/>
					</div>
				</div>
				<div class="row">
					<div><label>单位/地址:</label></div>
					<div>
						<input class="easyui-textbox" name="feedcom" id="feedcom" style="width:90%;height:35px" data-options="readonly:true"/>
					</div>
				</div>
				<div class="row">
					<div><label>电话号码:</label></div>
					<div>
						<input class="easyui-textbox" name="feedphone" id="feedphone" style="width:90%;height:35px" data-options="readonly:true"/>
					</div>
				</div>
				<div class="row">
					<div><label>邮箱:</label></div>
					<div>
						<input class="easyui-textbox" name="feedmail" id="feedmail" style="width:90%;height:35px" data-options="readonly:true"/>
					</div>
				</div>
				<div class="row">
					<div><label>意见咨询详情:</label></div>
					<div>
					 <input class="easyui-textbox" name="feeddesc" id="feeddesc" style="width:90%; height:120px" data-options="readonly:true ,multiline:true"/>
					</div>
				</div>
			
		  </div>
	  </form>
   </div>
</body>
</html>