<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath();
	request.setAttribute("basePath", basePath);
%>
<%request.setAttribute("resourceid", request.getParameter("rsid"));%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>分类管理</title>
<%@include file="/pages/comm/header.jsp"%>
<base href="${basePath}/">
<script>
/*
 * 下拉框事件函数
 */
function choiceType(record) {
	$('#typpid').combotree({
		url : '${basePath}/admin/inquire?type=' + record
	});
}
/**
 * 打开限制报名次数和广告状态
 */
function openadd(){
	var type =$('#ff_type').combobox('getValue');
	if (type==2) {
		$('#typbmcfg').parents('.row').show();
	}
}
/**
 * 打开图片配置说明
 */
function addpic(){
	var type =$('#ff_type').combobox('getValue');
	if (type == 12 || type == 21 ) {
		$('#typpic').parents('.row').show();
	}
}


/** 添加标签方法*/
function addType() {
	editWinform.setWinTitle("添加页面");
	editWinform.openWin();
	//清空表单的值
	$('#ff').form('clear');
	$('#typbmcfg').parents('.row').hide();
	$('#typpic').parents('.row').hide();
	//取值
	$('#ff_type').combobox('setValue', $('#type').combobox('getValue'));
	openadd();
	addpic();
	//设置默认值 combotree
	$('#typpid').combotree('setValue', '0');
	//$('#typstate').textbox('setValue', '0');
	$('#typidx').numberspinner('setValue', '1');
	$('#typbmcfg').combobox('setValue', '0');
	editWinform.setFoolterBut({
		onClick : function() {
			$('#ff').form('submit', {
				url : getFullUrl('./admin/typ/addtyp'),
				success : function(data) {
					var data = eval('(' + data + ')');
					//异常处理
					if(data && data.success == '0'){
						$('#tt').treegrid('reload');
						$.messager.alert("提示", "操作成功");
						editWinform.closeWin();
					}else{
						$.messager.alert('提示', '操作失败。原因：'+data.errmsg, 'error');
					}
				}
			});
		}
	})
}

/** 刷新功能*/
function renovateType() {
	$('#tt').treegrid('reload');
}

/** 折叠功能*/
function foldType() {
	$('#tt').treegrid('collapseAll');
}

/** 展开功能*/
function openType() {
	$('#tt').treegrid('expandAll');
}

/** 
 * 编辑
 */
function toEdit(index, typid) {	
	//得到选中项的id
	$('#tt').treegrid('select', typid);
	var rows = $('#tt').treegrid('getSelected');
	//打开div窗口
	editWinform.setWinTitle("编辑页面");
	editWinform.openWin();
	//获取选择的节点并返回它，得到行值。
	$('#ff').form('load', rows);
	$('#typbmcfg').parents('.row').hide();
	openadd();
	var url = './admin/typ/updtyp';
	//提交表单
    editWinform.setFoolterBut({
		onClick : function() { 
			$('#ff').form('submit', {
				url: getFullUrl('./admin/typ/updtyp'),
				onSubmit: function(param){    
					//alert(JSON.stringify(param))
			    },
				success: function(data) { 
					var data = eval('(' + data + ')');
					//异常处理
					if(data && data.success == '0'){
						$('#tt').treegrid('reload');
						$.messager.alert("提示", "操作成功");
						editWinform.closeWin();
					}else{
						$.messager.alert('提示', '操作失败。原因：'+data.errmsg, 'error');
					}
				}
			});
		}
	})
}


function showState(val, rowData, index) {
	return rowData.typstate == '1' ? '启用' : '关闭';
}

function mdyState(typid, state){
	$.messager.confirm('确认对话框', '确定要改变状态吗.', function(r) {
		if (r) {
			$.ajax({
				url : './admin/typ/gotyp',
				data : {typid : typid, typstate: state},
				type : "POST",
				success : function(data) {
					if (data.success) {
						$("#tt").treegrid("reload");
						$.messager.alert("提示", "操作成功");
					} else {
						$.messager.alert("提示", "error");
					}
				}
			});
		}
	});
}

/**
 *启用状态
 */
function tostate(index, typid){
	 mdyState(typid, '1');
}

/**
 *关闭状态
 */
function dostate(index, typid){
	mdyState(typid, '0');
}

/**
 * 查询功能
 */
function typs() {
	var type = $('#type').combobox('getValue');
	$('#tt').treegrid('load', {
		type : type
	});
}
//删除操作
function delType(index, typid) {
	$('#tt').treegrid('select', typid);
	var rows = $('#tt').treegrid('getSelected');
	if (rows.children.length > 0) {
		$.messager.alert("提示", "选中的类型有子类型，请先删除子类型");
		return;
	}
	$.messager.confirm('确认对话框', '确定要删除选定的菜单记录吗.', function(r) {
		if (r) {
			$.ajax({
				url : './admin/typ/deltyp',
				data : {
					typid : rows.typid
				},
				type : "POST",
				success : function(data) {
					if (data.success) {
						$("#tt").treegrid("reload");
					} else {
						$.messager.alert("提示", "error");
					}
				}
			});
		}
	});
}
var editWinform = new WhuiWinForm("#edit_win");
//加载页面初始化loadFilter
$(function() {
	$('#tt').treegrid({
		url : '${basePath}/admin/inquire',
		idField : 'typid',
		treeField : 'typname',
		title : '分类管理',
		fit : true,
		fitColumns : true,
		toolbar : '#tb',
		queryParams : {type : '0'},
		columns : [[  
              {field : 'typid',title : '分类id', width:200},
              {field : 'typname',title : '分类名称', width:200},
              {field : 'typmemo',title : '分类说明', width:250},
              {field : 'typidx',title : '分类排序', width:100},
              {field : 'typstate',title : '分类状态', width:100, formatter : showState},
              {field : 'tags',title : '操作', width:300, formatter : WHdg.optFMT, align:'center', optDivId:'ttOPT'}
	    ]]
	});
	//初始化div页面
	editWinform.init();
});



</script>
</head>
<body>
	<table id="tt"></table>

	<div id="tb" class="grid-control-group">
		类别: <select class="easyui-combobox" panelHeight="auto" style="width: 100px" id="type">
			<option value="0">艺术分类</option>
			<option value="1">活动分类</option>
			<option value="2">培训分类</option>
			<option value="3">年龄</option>
			<option value="4">课程级别</option>
			<option value="5">资讯分类</option>
			<option value="6">标签分类</option>
			<option value="7">关键字分类</option>
			<option value="8">区域</option>
			<option value="9">实体分类</option><!-- 培训/活动 -->
			<option value="10">资源分类</option><!-- 图片/视频/音频 -->
			<option value="11">基础配置</option>
			<option value="12">页面信息配置</option>
			<option value="13">馆务公开</option>
			<option value="14">数字资源分类</option>
			<option value="15">场馆分类</option>
			<option value="16">非遗级别</option>
			<option value="17">非遗批次</option>
			<option value="18">非遗类型</option>
			<option value="19">志愿培训类型</option>
			<option value="20">志愿活动类型</option>
			<!-- 注意20 不知道什么分类 -->
			<option value="21">广告页面配置</option>
			<option value="22">培训老师专长</option>
			<!-- 按钮 -->
		</select> 
		    <a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-search" onclick="typs();">查询</a> 
		   <shiro:hasPermission name="${resourceid}:add"><a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-add" plain="true" onclick="addType()">添加</a></shiro:hasPermission> 
		    <a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-reload" plain="true" onclick="renovateType()">刷新</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-undo" plain="true" onclick="foldType()">折叠</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-redo" plain="true" onclick="openType()">展开</a>
	</div>
	
	<!-- 操作按钮 -->
	<div id="ttOPT" class style="display:none" >
		<shiro:hasPermission name="${resourceid}:edit">
		<a href="javascript:void(0);" class="easyui-linkbutton" validKey="typstate" validVal="0" data-options="plain:true" method="toEdit" prop="typid">编辑</a>
		</shiro:hasPermission>

		<shiro:hasPermission name="${resourceid}:del">
		<a href="javascript:void(0);" class="easyui-linkbutton" validKey="typstate" validVal="0" data-options="plain:true" method="delType" prop="typid">删除</a>
		</shiro:hasPermission>
	
		<shiro:hasPermission name="${resourceid}:on">
		<a href="javascript:void(0);" class="easyui-linkbutton" validKey="typstate" validVal="0" data-options="plain:true" method="tostate" prop="typid">启用</a>
		</shiro:hasPermission>
	
		<shiro:hasPermission name="${resourceid}:off">
		<a href="javascript:void(0);" class="easyui-linkbutton" validKey="typstate" validVal="1" data-options="plain:true" method="dostate" prop="typid">关闭</a>
		</shiro:hasPermission>
		
	</div>
	
	<!-- 编辑DIV -->
	<div id="edit_win" class="none" style="display:none;" data-options="maximized:true">
		<form method="post" id="ff">
		 <!-- 隐藏作用域 -->
	      <input type="hidden" name="typid"/>
	      <input type="hidden" id="typstate" name="typstate" value="0"/>
			<div class="main">
				<div class="row">
					<div><label>分类类别:</label></div>
					<div>
						<select class="easyui-combobox" name="type" id="ff_type" style="width:90%;height:35px"
						data-options="editable:false, required:true, panelHeight:'auto',onChange:choiceType">
							<option value="0">艺术分类</option>
							<option value="1">活动分类</option>
							<option value="2">培训分类</option>
							<option value="3">年龄</option>
							<option value="4">课程级别</option>
							<option value="5">资讯分类</option>
							<option value="6">标签分类</option>
							<option value="7">关键字分类</option>
							<option value="8">区域</option>
							<option value="9">实体分类</option><!-- 培训/活动 -->
							<option value="10">资源分类</option><!-- 图片/视频/音频 -->
							<option value="11">基础配置</option>
							<option value="12">页面信息配置</option>
							<option value="13">馆务公开</option>
							<option value="14">数字资源分类</option>
							<option value="15">场馆分类</option>
							<option value="16">非遗级别</option>
							<option value="17">非遗批次</option>
							<option value="18">非遗类型</option>
							<option value="19">志愿培训类型</option>
							<option value="20">志愿活动类型</option>
							<!-- 注意20 不知道什么分类 -->
							<option value="21">广告页面配置</option>
							<option value="22">培训老师专长</option>
					   </select>
					</div>
				</div>
				<div class="row">
					<div><label>上级分类:</label></div>
					<div>
						<select class="easyui-combotree" name="typpid" id="typpid" style="width:90%;height:35px"
						 data-options="editable:true, panelHeight:'auto', url:'${basePath}/admin/inquire?type=${type}'">
						</select>
					</div>
				</div>
				<div class="row">
					<div><label>分类名称:</label></div>
					<div>
						<input class="easyui-textbox" name="typname" style="width:90%;height:35px"
						data-options="required:true, validType:'length[0,60]'"/>
					</div>
				</div>
				<div class="row">
					<div><label>报名限制:</label></div>
					<div>
						<div>
						<select class="easyui-combobox" name="typbmcfg" id="typbmcfg" style="width:90%;height:35px"
						data-options="editable:false, required:true, panelHeight:'auto'">
							<option value="0">不限制</option>
							<option value="1">1人只能报一次</option>
					   </select>
					</div>
					</div>
				</div>
				<div class="row">
					<div><label>分类排序:</label></div>
					<div>
						<div>
						<input class="easyui-numberspinner" name="typidx" id="typidx" value="" style="width:90%;height:35px"
						data-options="increment:1, required:true,min:1,max:999"/>
					</div>
					</div>
				</div>
				<div class="row">
					<div><label>分类说明:</label></div>
					<div>
						<input class="easyui-textbox" name="typmemo" style="width:90%;height:120px" 
						data-options="validType:'length[0,400]',multiline:true"/>
					</div>
				</div>
				
				<div class="row">
					<div><label>图片限制:</label></div>
					<div>
						<input class="easyui-textbox" name="typpic" id="typpic" style="width:90%;height:120px" 
						data-options="validType:'length[0,400]',multiline:true"/>
					</div>
				</div>
				
			</div>
		</form>
	</div>
</body>
</html>