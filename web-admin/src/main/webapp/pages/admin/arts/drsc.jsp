<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath(); request.setAttribute("basePath", basePath); %>
<%request.setAttribute("resourceid", request.getParameter("rsid"));%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<%@include file="/pages/comm/header.jsp"%>
<!-- UEditor -->
<script type="text/javascript" charset="utf-8" src="${basePath}/static/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${basePath}/static/ueditor/ueditor.all.min.js"></script>
<script type="text/javascript" charset="utf-8" src="${basePath}/static/ueditor/lang/zh-cn/zh-cn.js"></script>
<script>
//添加编辑查看的表单
var winform = new WhuiWinForm("#drscWin", {height:400});

/** 打开添加数字资源表单 */
function _goAdd(){
	//打开表单
	winform.setWinTitle("添加数字资源");
	winform.openWin();
	winform.getWinFooter().find("a:eq(0)").show();
	
	//启用表单域
	$('#drscarttyp').combobox('readonly', false).combobox('clear');
	$('#drsctyp').combobox('readonly', false).combobox('clear');
	$('#drsctitle').textbox('readonly', false).textbox('clear');
	$('#drscintro').textbox('readonly', false).textbox('clear');
	$('#drscfrom').textbox('readonly', false).textbox('clear');
	$('#drscpic_up').filebox('readonly', false).filebox('enable').filebox('clear');
	$('#drscpic_up_img').removeAttr('src').parents('.row').hide();
	$('#drscpath').combobox('readonly', false).combobox('enable').combobox('clear');
	
	//初始与清除表单
	$('#ff').form({
		novalidate: true,
		url : getFullUrl('/admin/drsc/add'),
		onSubmit : function(param) {
			param.drscstate = 0;
			var _valid = $(this).form('enableValidation').form('validate')
			if(_valid){
				$.messager.progress();
			}
			return _valid;
		},
		success : function(data) {
			$.messager.progress('close');
			
			//var Json = $.parseJSON(data);
			var Json = eval('('+data+')');
	 		if(Json && Json.success == '0'){
	 			$.messager.alert('提示', '操作成功！', 'info');
				$('#zxDG').datagrid('reload');
				winform.closeWin();
			} else {
				$.messager.alert('提示', '操作失败！', 'error');
			}
		}
	});
	
	//注册提交事件
	winform.setFoolterBut({onClick:function(){
		$('#ff').submit();
	}});
	
}/** 打开添加数字资源表单-END */

/** 查看数字资源 */
function doSee(index){
	//要查看的资源
	var row = WHdg.getRowData(index);
	
	//打开表单
	winform.setWinTitle("查看数字资源信息");
	winform.openWin();
	winform.getWinFooter().find("a:eq(0)").hide();

	//启用表单域
	$('#drscarttyp').combobox('readonly', true).combobox('setValue', row.drscarttyp);
	$('#drsctyp').combobox('readonly', true).combobox('setValue', row.drsctyp);
	$('#drsctitle').textbox('readonly', true).textbox('setValue', row.drsctitle);
	$('#drscintro').textbox('readonly', true).textbox('setValue', row.drscintro);
	$('#drscfrom').textbox('readonly', true).textbox('setValue', row.drscfrom);
	$('#drscpic_up').filebox('clear'); $('#drscpic_up').filebox('disable');

	$('#drscpic_up_img').attr('src', WhgComm.getImgServerAddr()+row.drscpic).parents('.row').show();
	//
	$('#drscpath').combobox('clear'); $('#drscpath').combobox('disable');
	$('#drscpath').combobox({onLoadSuccess : function(data){
		for (var i = 0; i < data.length; i++) {
			if (data[i].addr == row.drscpath) {
				$('#drscpath').combobox('setValue',row.drscpath);
			}
		}
	}});
	//$('#drscpath').combobox('setValue',row.drscpath);
	
	//注册提交事件
	winform.setFoolterBut({onClick:function(){ }});
	
}/** 查看数字资源-END */

/** 编辑数字资源 */
function doUpd(index){
	//要查看的资源
	var row = WHdg.getRowData(index);
	
	//打开表单
	winform.setWinTitle("编辑数字资源");
	winform.openWin();
	winform.getWinFooter().find("a:eq(0)").show();
	
	//初始与清除表单
	$('#ff').form({
		novalidate: true,
		url : getFullUrl('/admin/drsc/edit'),
		onSubmit : function(param) {
			return $(this).form('enableValidation').form('validate');
		},
		success : function(data) {
			//var Json = $.parseJSON(data);
			var Json = eval('('+data+')');
	 		if(Json && Json.success == '0'){
	 			$.messager.alert('提示', '操作成功！', 'info');
				$('#zxDG').datagrid('reload');
				winform.closeWin();
			} else {
				$.messager.alert('提示', '操作失败！', 'error');
			}
		}
	});
	
	//启用表单域
	$('#drscid').val(row.drscid);
	$('#drscarttyp').combobox('readonly', false).combobox('setValue', row.drscarttyp);
	$('#drsctyp').combobox('readonly', false).combobox('setValue', row.drsctyp);
	$('#drsctitle').textbox('readonly', false).textbox('setValue', row.drsctitle);
	$('#drscintro').textbox('readonly', false).textbox('setValue', row.drscintro);
	$('#drscfrom').textbox('readonly', false).textbox('setValue', row.drscfrom);
	
	$('#drscpic_up').filebox({'required': false});
	$('#drscpic_up').filebox('readonly', false).filebox('enable').filebox('clear');
	$('#drscpic_up_img').attr('src', WhgComm.getImgServerAddr()+row.drscpic).parents('.row').show();
	//
	$('#drscpath').combobox({'required': false});	
	
	$('#drscpath').combobox('readonly', false).combobox('enable').combobox('clear');
	//$('#drscpath').combobox('setValue',row.drscpath);
	$('#drscpath').combobox({onLoadSuccess : function(data){
		for (var i = 0; i < data.length; i++) {
			if (data[i].addr == row.drscpath) {
				$('#drscpath').combobox('setValue',row.drscpath);
			}
		}
	}});
	
	
	//注册提交事件
	winform.setFoolterBut({onClick:function(){
		$('#ff').submit();
	}});
}/** 编辑数字资源-END */

/** 删除数字资源 */
function doDel(index){
	var row = WHdg.getRowData(index);
	$.messager.confirm('确认对话框', '确定要删除此数字资源吗？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/drsc/del'),
				data: {id : row.drscid},
				success: function(Json){
					if(Json && Json.success == '0'){
			 			$.messager.alert('提示', '操作成功！', 'info');
						$('#zxDG').datagrid('reload');
						winform.closeWin();
					} else {
						$.messager.alert('提示', '操作失败:'+Json.errmsg+'！', 'error');
					}
				}
			});
		}
	});
}/** 删除数字资源-END */

/** 修改数字资源状态 */
function _updState(fromState, toState, drscid){
	var drscids = ''; 
	if(drscid){//单个操作
		drscids = drscid;
	}else{//批量操作
		rows = $("#zxDG").datagrid("getChecked");
		if(rows.length < 1){
			$.messager.alert('警告', '请选择要操作的数据！', 'warning');return;
		}
		var _spt = '';
		for(var i=0; i<rows.length; i++){
			drscids += _spt+rows[i].drscid;
			_spt = ',';
		}
	}
	$.ajax({
		type: "POST",
		url: getFullUrl('/admin/drsc/updState'),
		data: {ids : drscids, fromState: fromState, toState: toState},
		success: function(Json){
			if(Json && Json.success == '0'){
	 			$.messager.alert('提示', '操作成功！', 'info');
				$('#zxDG').datagrid('reload');
				winform.closeWin();
			} else {
				$.messager.alert('提示', '操作失败:'+Json.errmsg+'！', 'error');
			}
		}
	});
}/** 修改数字资源状态-END */

/** 审批数字资源 */
function doCheck(index){
	var row = WHdg.getRowData(index);
	_updState(0, 2, row.drscid);
}/** 审批数字资源-END */

/** 取消审批数字资源 */
function doUnCheck(index){
	var row = WHdg.getRowData(index);
	_updState(2, 0, row.drscid);
}/** 取消审批数字资源-END */

/** 发布数字资源 */
function doRelease(index){
	var row = WHdg.getRowData(index);
	_updState(2, 3, row.drscid);
}/** 发布数字资源-END */

/** 取消发布数字资源 */
function doUnRelease(index){
	var row = WHdg.getRowData(index);
	_updState(3, 2, row.drscid);
}/** 取消发布数字资源-END */


/** 页面加载完成后事件处理 */
$(function(){
	//定义表格参数
	var options = {
		title: '在线点播管理',
		url: getFullUrl('/admin/drsc/srchPagging'),
		sortName: 'drscid',
		sortOrder: 'desc',
		rownumbers: true,
		singleSelect: false,
		fitColumns: true,
		columns: [[
			{field:'ck',checkbox:true},
            {field:"drscarttyp", title: "艺术类型",width:80, formatter:WhgComm.FMTArtType},
            /*{field:"drsctyp", title: "资源分类",width:80, formatter:zxtypeFMT},*/
			{field:"drsctitle", title: "资源标题",width:80, sortable:true},
			{field:"drscfrom", title: "资源来源",width:150},
			{field:"drsccrttime", title: "创建时间",width:150, sortable:true, formatter: datetimeFMT},
			{field:"drscpic", title: "资源图片",width:80,formatter:imgFMT},
			{field:"drscstate", title: "状态", width:50, sortable:true, formatter: traStateFMT},
			{field:'opt', title:'操作',width:350, formatter: WHdg.optFMT, optDivId:'zxOPT'}
		]]
	};
	
	//初始表格
	WHdg.init('zxDG', 'zxTB', options); 
	
	//注册添加事件
	$('#btn_add').on('click.wxl', _goAdd);
	$('#btn_batch_updstate0_2').on('click.wxl', function(e){e.preventDefault();_updState(0,2);});
	$('#btn_batch_updstate2_0').on('click.wxl', function(e){e.preventDefault();_updState(2,0);});
	$('#btn_batch_updstate2_3').on('click.wxl', function(e){e.preventDefault();_updState(2,3);});
	$('#btn_batch_updstate3_2').on('click.wxl', function(e){e.preventDefault();_updState(3,2);});
	
	
	//初始表单
	winform.init();
	
	//清除文件
	$('.clearfilebox').on('click.wxl', function(e){
		e.preventDefault();
		$(this).siblings('input').filebox('clear');
	});
});
</script>
</head>
<body>

	<!-- 表格 -->
	<div id="zxDG"></div>
	
	<!-- 表格操作工具栏 -->
	<div id="zxTB" class="grid-control-group" style="display: none;">
		<div>
			<shiro:hasPermission name="${resourceid}:add">       <a size="small" class="easyui-linkbutton" plain="false" iconCls="icon-add"  id="btn_add" >添加</a></shiro:hasPermission>
			<shiro:hasPermission name="${resourceid}:checkon">   <a size="small" class="easyui-linkbutton" plain="false" iconCls="icon-redo" id="btn_batch_updstate0_2" >批量审核</a></shiro:hasPermission>
			<shiro:hasPermission name="${resourceid}:checkoff">  <a size="small" class="easyui-linkbutton" plain="false" iconCls="icon-undo" id="btn_batch_updstate2_0" >批量取消审核</a></shiro:hasPermission>
			<shiro:hasPermission name="${resourceid}:publish">   <a size="small" class="easyui-linkbutton" plain="false" iconCls="icon-redo" id="btn_batch_updstate2_3" >批量发布</a></shiro:hasPermission>
			<shiro:hasPermission name="${resourceid}:publishoff"><a size="small" class="easyui-linkbutton" plain="false" iconCls="icon-undo" id="btn_batch_updstate3_2" >批量取消发布</a></shiro:hasPermission>
		</div>
		
		<div style="padding-top: 5px">
			艺术分类:
			<input class="easyui-combobox" name="drscarttyp" data-options="valueField:'id', textField:'text', data:WhgComm.getArtType()"/>
			列表标题:
			<input class="easyui-textbox"  name="drsctitle"  data-options="validType:'length[1,30]'"/>
			状态:
			<select class="easyui-combobox" name="drscstate">
					<option value="">全部</option>
 					<option value="0">未审核</option>
 					<option value="2">已审核</option>
 					<option value="3">已发布</option>
			</select>
			<a href="javascript:void(0)" class="easyui-linkbutton search" iconCls="icon-search">查询</a>
		</div>
	</div>
	<!-- 表格操作工具栏-END -->
	
	<!-- 操作按钮 -->
	<div id="zxOPT" style="display: none;">
		<shiro:hasPermission name="${resourceid}:view">      <a href="javascript:void(0)" class="easyui-linkbutton" plain="true" method="doSee">查看</a></shiro:hasPermission>
		<shiro:hasPermission name="${resourceid}:edit">      <a href="javascript:void(0)" class="easyui-linkbutton" plain="true" validKey="drscstate" validVal="0,1" method="doUpd">修改</a></shiro:hasPermission> 
		<shiro:hasPermission name="${resourceid}:del">       <a href="javascript:void(0)" class="easyui-linkbutton" plain="true" validKey="drscstate" validVal="0,1" method="doDel">删除</a></shiro:hasPermission>
		<shiro:hasPermission name="${resourceid}:checkon">   <a href="javascript:void(0)" class="easyui-linkbutton" plain="true" validKey="drscstate" validVal="0,1" method="doCheck">审核</a></shiro:hasPermission>
        <shiro:hasPermission name="${resourceid}:checkoff">  <a href="javascript:void(0)" class="easyui-linkbutton" plain="true" validKey="drscstate" validVal="2"   method="doUnCheck">取消审核</a></shiro:hasPermission>
        <shiro:hasPermission name="${resourceid}:publish">   <a href="javascript:void(0)" class="easyui-linkbutton" plain="true" validKey="drscstate" validVal="2"   method="doRelease">发布</a></shiro:hasPermission>
        <shiro:hasPermission name="${resourceid}:publishoff"><a href="javascript:void(0)" class="easyui-linkbutton" plain="true" validKey="drscstate" validVal="3"   method="doUnRelease">取消发布</a></shiro:hasPermission>
	</div><!-- 操作按钮-END -->
	
	
	<!-- 编辑查看添加表单 -->
	<div id="drscWin" style="display: none" fit="true">
		 <form id="ff" method="post" enctype="multipart/form-data">
			 <input type="hidden" id="drscid" name="drscid">
			<div class="main">
	    		<div class="row">
	    			<div>艺术类型:</div>
	    			<div>
						<select class="easyui-combobox" id="drscarttyp" name="drscarttyp" panelHeight="auto" limitToList="true" style="width:90%;height:32px;" data-options="required:true,editable:false,valueField:'id', textField:'text', data:WhgComm.getArtType()"></select>
	    				<%--<input class="easyui-combobox" id="drscarttyp" name="drscarttyp" style="width:90%;height:32px;" data-options="editable:false, required:true, valueField:'typid', textField:'typname', url:'${basePath}/comm/whtyp?type=0'"/>--%>
	    			</div>
	    		</div>
				<div class="row">
	    			<div>资源分类:</div>
	    			<div>
	    				<input class="easyui-combobox" id="drsctyp" name="drsctyp" style="width:90%;height:32px;" data-options="editable:false, required:false, valueField:'id', textField:'text', data:WhgComm.getZYFLType()"/>
	    			</div>
	    		</div>
	    		<div class="row">
	    			<div>资源标题:</div>
	    			<div>
	    				<input class="easyui-textbox" id="drsctitle" name="drsctitle" style="width:90%;height:32px;" data-options="required:true, validType:'length[1,60]'"/>
	    			</div>
	    		</div>
	    		<div class="row">
	    			<div>资源时长:</div>
	    			<div>
	    				<input class="easyui-numberspinner" id="zypxvideolen" name="drsctime" data-options="validType:'length[0,10]',multiline:true " style="width:90%;height:32px;">
					</div>
		    	</div>
		    	<%--<div class="row">
	    			<div>关键字:</div>
		    		<div>
						<input id="drsckey" class="easyui-combobox" name="drsckey" multiple="true" style="width:90%;height:32px;" data-options="panelHeight:'auto', valueField:'name',textField:'name',url:'${basePath}/comm/whkey?type=2016121500000008', multiple:true"/>
		    		</div>
		    	</div>--%>
	    		<div class="row">
	    			<div>资源简介:</div>
	    			<div>
	    				<input class="easyui-textbox" id="drscintro" name="drscintro" style="width:90%;height:80px;" data-options="multiline:true,required:true, validType:'length[1,200]'"/>
	    			</div>
	    		</div>
	    		<div class="row">
	    			<div>资源来源:</div>
	    			<div>
	    				<input class="easyui-textbox" id="drscfrom" name="drscfrom" style="width:90%;height:32px;" data-options="required:true, validType:'length[1,15]'"/>
	    			</div>
	    		</div>
	    		<div class="row" style="display: none;">
	    			<div></div>
	    			<div>
	    				<img id="drscpic_up_img" height="150" />
	    			</div>
	    		</div>
	    		<div class="row">
	    			<div>封面图片:</div>
	    			<div>
	    				<input class="easyui-filebox" id="drscpic_up" name="drscpic_up" style="width:80%;height:32px;" data-options="required: true, validType:'isIMG[\'drscpic_up\']', buttonText:'选择封面', accept:'image/jpeg, image/png'">
	    				<a class="easyui-linkbutton clearfilebox" data-options="plain:true," iconCls="icon-clear" style="width:10%;height:32px;">清除</a>
	    			</div>
	    		</div>
	    		<div class="row">
	    			<div>数字资源:</div>
	    			<div>
	    				<!-- 
	    				<input  class="easyui-filebox" id="drscpath_up" name="drscpath_up" style="width:80%;height:32px;" data-options="required: false, validType:'isVideo[\'drscpath_up\']', buttonText:'选择视频'">
	    				<a class="easyui-linkbutton clearfilebox" data-options="plain:true," iconCls="icon-clear" style="width:10%;height:32px;">清除</a>
	    				 -->
	    				<input class="easyui-combobox" id="drscpath" name="drscpath" style="width:90%;height:32px;" data-options="editable:false, required:true, valueField:'addr', textField:'key', url:'${basePath}/admin/video/srchPagging'"/>
	    			</div>
	    		</div>
			</div>
		</form>
	</div>
	<!-- 编辑查看添加表单-END -->
		
</body>
</html>