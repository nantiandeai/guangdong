<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
request.setAttribute("basePath", basePath);
%>
<%request.setAttribute("resourceid", request.getParameter("rsid"));%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<%@include file="/pages/comm/header.jsp"%>
<script type="text/javascript" src="${basePath }/static/common/js/jQuery.md5.js"></script>
<script type="text/javascript">
/** 表单对象 */
var winForm;

/** 页面加载完成后的事件处理 */
$(function() {
	//定义表格参数
	var options = {
		title: '管理员管理',			
		url: '${basePath }/admin/selectManage',
		//sortName: 'artid',
		//sortOrder: 'desc',
		rownumbers: true,
		singleSelect: true,
		//queryParams: {stateArray:"1"},
		columns: [[
            {field:"name", title: "登录名", width:150},
			{field:"status", title: "状态", width:100, sortable:true, formatter:onOffFMT},
			{field:'opt', title:'操作', width:200, formatter: WHdg.optFMT, optDivId:'userListOPT'}
		]]
	};
	
	//初始表格
	WHdg.init('userList', 'userListTB', options); 
	
	//add
	$('#btn_addzx').on('click.wxl', goAdd);
	
	//init winForm
	winForm = new WhuiWinForm("#form_div", {height:400,width:700});
	winForm.init();
});/** 页面加载完成后的事件处理-END */

/** 添加 */
function goAdd(){
	//弹出编辑表单
	winForm.setWinTitle("添加管理员");
	winForm.openWin();
	$(".easyui-textbox").textbox('readonly',false);
	$(".easyui-combobox").textbox('readonly',false);
	//表单初始化
	var frm = winForm.getWinBody().find('form').form({
		url : '${basePath}/admin/addManage',
		onSubmit : function(param){
			param.nickname = $('#mgrname').textbox('getValue');
			param.password = $.md5('123456');
			param.status = '1';
			var _validate =$(this).form('enableValidation').form('validate');
			if (!_validate) {
				winForm.oneClick(function(){ frm.submit(); });
			}
            return _validate;
		},success : function(data) {
			var Json = $.parseJSON(data);
 			if(Json && Json.success == '0'){
				$.messager.alert('提示', '操作成功!');
				$('#userList').datagrid('reload');
				winForm.closeWin();
			}else{
				if(Json.errfield != ''){
					$('#errfield').val(Json.errfield);
					$('#errfieldmsg').val(Json.errmsg);
					$('#form_mgr').form('validate');
					$('#errfield').val('');
				}
				$.messager.alert('提示', '操作失败:'+Json.errmsg, 'error');
				winForm.oneClick(function(){ frm.submit(); });
			}
		}
	});
	//清除表单值
	frm.form('clear');
		
	//表单提交按钮
	winForm.getWinFooter().find("a:eq(0)").show();
	winForm.oneClick(function(){ frm.submit(); });
}/** 添加-END */

/** 查看  */
function doSee(index){
	var row = WHdg.getRowData(index);
	//弹出编辑表单
	winForm.setWinTitle("添加管理员");
	winForm.openWin();
	
	$(".easyui-textbox").textbox('readonly');
	$(".easyui-combobox").textbox('readonly');
	//清除表单值
	$('#form_mgr').form('clear');
	//alert(JSON.stringify(row));
	
	//文化馆为总馆的处理
	var temp_venueid= "";
	if(row.venueid == ""){
		row.venueid = "0";
	}
	temp_venueid= row.venueid; 
	var row2 = $.extend({},row,{venueid: temp_venueid});
	/*for ( var i in row2) {
		b+=i+"=="+row2[i];
	} */
	//alert(b);
	$('#form_mgr').form('load', row2);
		
	//隐藏表单提交按钮
	winForm.getWinFooter().find("a:eq(0)").hide();
}

/** 编辑 */
function doUpd(index){
	var row = WHdg.getRowData(index);
	
	//弹出编辑表单
	winForm.setWinTitle("修改管理员");
	winForm.openWin();
	
	$(".easyui-textbox").textbox('readonly',false);
	$(".easyui-combobox").textbox('readonly',false);
	
	//初始化表单
	var frm = winForm.getWinBody().find('form').form({
		url : '${basePath}/admin/modifyManage',
		onSubmit : function(param){
			param.id = row.id;
			var _validate = $(this).form('enableValidation').form('validate');
			if (!_validate) {
				winForm.oneClick(function(){ frm.submit(); });
			}
            return _validate;
        },
		success : function(data) {
			var Json = $.parseJSON(data);
 			if(Json && Json.success == '0'){
				$.messager.alert('提示', '操作成功!');
				$('#userList').datagrid('reload');
				winForm.closeWin();
			}else{
				if(Json.errfield != ''){
					$('#errfield').val(Json.errfield);
					$('#errfieldmsg').val(Json.errmsg);
					$('#form_mgr').form('validate');
					$('#errfield').val('');
				}
				$.messager.alert('提示', '操作失败:'+Json.errmsg, 'error');
				winForm.oneClick(function(){ frm.submit(); });
			}
		}
	});
	//清除表单值
	$('#form_mgr').form('clear');
	//文化馆为总馆的处理
	var temp_venueid= "";
	if(row.venueid == ""){
		row.venueid = "0";
	}
	temp_venueid= row.venueid; 
	var row2 = $.extend({},row,{venueid: temp_venueid});
	$('#form_mgr').form('load', row2);
		
	//表单提交按钮
	winForm.getWinFooter().find("a:eq(0)").show();
	winForm.oneClick(function(){ frm.submit(); });
}

/** 停用 */
function doOff(index){
	var row = WHdg.getRowData(index);
	$.ajax({
		url : '${basePath}/admin/updMgrState',
		data : {id : row.id, state: '0'},
		success : function(data) {
			if (data.success == '0') {
				$("#userList").datagrid("reload");
			} else {
				$.messager.alert("提示", "操作失败："+data.errmsg, 'error');
				winForm.oneClick(function(){ frm.submit(); });
			}
		}
	});
}

/** 启用 */
function doOn(index){
	var row = WHdg.getRowData(index);
	$.ajax({
		url : '${basePath}/admin/updMgrState',
		data : {id : row.id, state: '1'},
		success : function(data) {
			if (data.success == '0') {
				$("#userList").datagrid("reload");
			} else {
				$.messager.alert("提示", "操作失败："+data.errmsg, 'error');
				winForm.oneClick(function(){frm.submit(); });
			}
		}
	});
}

/** 删除 */
function doDel(index){
	var row = WHdg.getRowData(index);
	$.messager.confirm('确认对话框', '您想要删除此记录吗？', function(r) {
		if (r) {
 			$.ajax({
				url : '${basePath}/admin/deleteManage',
				data : {id : row.id},
				success : function(data) {
					if (data.success == '0') {
						$("#userList").datagrid("reload");
					} else {
						$.messager.alert("提示", "操作失败："+data.errmsg, 'error');
						winForm.oneClick(function(){frm.submit(); });
					}
				}
			})
		}
	});
}
/**
 * 密码重置
 */
 function resets(index){
	var row = WHdg.getRowData(index);
	password = $.md5('123456');
	alert(password);
	$.messager.confirm('确认对话框', '您想要重置密码吗？', function(r) {
		if (r) {
 			$.ajax({
				url : '${basePath}/admin/resetpwd',
				data : {id : row.id, password : password},
				success : function(data) {
					if (data.success == '0') {
						$("#userList").datagrid("reload");
					} else {
						$.messager.alert("提示", "操作失败："+data.errmsg, 'error');
						winForm.oneClick(function(){frm.submit(); });
					}
				}
			})
		}
	});
}
 
 
</script>

</head>
<body>
	<!-- 表格 -->
	<div id="userList"></div>
	
	<!-- 表格toolbar -->
	<shiro:hasPermission name="${resourceid}:add">  
	<div id="userListTB" class="grid-control-group">
		<a class="easyui-linkbutton" plain="true" iconCls="icon-add" id="btn_addzx">添加</a>
	</div>
	</shiro:hasPermission>

	<!-- 操作按钮 -->
	<div id="userListOPT" style="display: none;">
		<shiro:hasPermission name="${resourceid}:view"><a href="javascript:void(0)" method="doSee">查看</a></shiro:hasPermission>
		<shiro:hasPermission name="${resourceid}:edit"><a href="javascript:void(0)" method="doUpd">修改</a></shiro:hasPermission>
		<shiro:hasPermission name="${resourceid}:off"><a href="javascript:void(0)" validKey="status" validVal="1" method="doOff">停用</a> </shiro:hasPermission>
		<shiro:hasPermission name="${resourceid}:on"><a href="javascript:void(0)" validKey="status" validVal="0" method="doOn">启用</a> </shiro:hasPermission>
		<shiro:hasPermission name="${resourceid}:del"><a href="javascript:void(0)" validKey="status" validVal="0" method="doDel">删除</a></shiro:hasPermission>
		<shiro:hasPermission name="${resourceid}:reset"><a href="javascript:void(0)" validKey="status" validVal="0" method="resets">密码重置</a></shiro:hasPermission>
	</div>
	
	<!-- 角色编辑 -->
	<div id="form_div" style="display: none;" >
		<form id="form_mgr" method="post">
			<div class="main">
				<div class="row">
	    			<div>登录名称:</div>
	    			<div>
	    				<input class="easyui-textbox" id="mgrname" name="name" data-options="required:true, validType:['length[4,20]']" style="width:300px;height:32px;"/>
	    			</div>
	    		</div>
		    	<div class="row">
	    			<div>电话:</div>
	    			<div>
	    				<input class="easyui-textbox" id="mgrphone" name="phone" data-options="required:true, validType:['length[11,20]']" style="width:300px;height:32px;"/>
	    			</div>
	    		</div>	
	    		<div class="row">
	    			<div>邮箱:</div>
	    			<div>
	    				<input class="easyui-textbox" id="mgremail" name="email" data-options="required:true, validType:['email']" style="width:300px;height:32px;"/>
	    			</div>
	    		</div>
	    		<div class="row">
	    			<div>角色:</div>
	    			<div>
	    				<input class="easyui-combobox" id="mgrroleid" name="roleid" data-options="required:true, editable:false, valueField:'id', textField:'name', url:'${basePath }//admin/srchRoles'" style="width:300px;height:32px;"/>
	    			</div>
	    		</div>
	    		<div class="row">
	    			<div>文化馆:</div>
	    			<div>
	    				<input class="easyui-combobox" id="mgrvenueid" name="venueid" data-options="required:true, editable:false, valueField:'subid', textField:'subname', url:'${basePath }/admin/refSubvenue'" style="width:300px;height:32px;"/>
	    			</div>
	    		</div>
			</div>
		</form>
	</div>
	<!-- 角色编辑-END -->
	
</body>
</html>