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
<title>栏目设置</title>
<%@include file="/pages/comm/header.jsp"%>
<base href="${basePath}/">
<script>
/** 状态 */
function showState(val, rowData, index) {
	return rowData.colstate == '1' ? '启用' : '关闭';
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
//删除操作
function delType(index,colid) {
	$('#tt').treegrid('select', colid);
	var rows = $('#tt').treegrid('getSelected');
	if (rows.children.length > 0) {
		$.messager.alert("提示", "选中的类型有子类型，请先删除子类型");
		return;
	}
	$.messager.confirm('确认对话框', '确定要删除选定的菜单记录吗.', function(r) {
		if (r) {
			$.ajax({
				url : '${basePath}/admin/shop/delmus',
				data : {
					colid : rows.colid,colpic:rows.colpic
				},
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
/** 添加方法*/
function addmuse() {
	//清空表单的值
	$('#ff').form('clear');
	editWinform.setWinTitle("添加栏目页面");
	editWinform.openWin();
	$('#colpid').combotree('setValue', '0');
	$('#colstate').combobox('setValue', '0')
	$('#muspic_up_img').removeAttr('src').parents('.row').hide();
	editWinform.setFoolterBut({
		onClick : function() {
			$('#ff').form('submit', {
				url : '${basePath}/admin/shop/addmus',
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
/** 
 * 编辑
 */
function toEdit(index,colid) {
	//得到选中项的id
	$('#tt').treegrid('select', colid);
    var row= $('#tt').treegrid('getSelected');
	if (row.colpic && row.colpic != '' ){
    	$('#muspic_up_img').attr('src', getFullUrl(row.colpic)).parents('.row').show();
    }else{
    	$('#muspic_up_img').parents('.row').hide();
    }
	
	//打开div窗口
	editWinform.setWinTitle("编辑栏目页面");
	editWinform.openWin();
	//获取选择的节点并返回它，得到行值。
	$('#ff').form('load', row);

	var url = '${basePath}/admin/shop/upmus';
	//提交表单
    editWinform.setFoolterBut({
		onClick : function() {
			$('#ff').form('submit', {
				url :url,
				onSubmit : function(param){
	                param.colid = row.colid;
	                return $(this).form('enableValidation').form('validate');
	            },
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
var editWinform = new WhuiWinForm("#edit_win");
//加载页面初始化
$(function() {
	$('#tt').treegrid({
		url : '${basePath}/admin/shop/selmus',
		idField : 'colid',
		treeField : 'coltitle',
		title : '栏目设置管理',
		toolbar : '#tb',
		fitColumns:true,
		columns : [[  {field : 'colid',title : '栏目标识',width:125},
		              {field : 'coltitle',title : '栏目标题',width:250},
		              //{field : 'colpid',title : '上级类型',width:125},
		              {field : 'colidx',title : '排序',width:80,align:'center'},
		              {field : 'colstate',title : '状态',width:80,formatter : showState},
		              //{field : 'colpic',title : '栏目图片',width:80,formatter:imgFMT},
		              {field : 'tags',title : '操作',width:100,formatter : WHdg.optFMT, align:'center', optDivId:'ttOPT'}
		             
	    ]]
	});
	//初始化div页面
	editWinform.init();
	$('#btn_pic').bind('click', function(){  
		$("#muspic_up").filebox('clear');
    });
});
</script>
</head>
<body>
	<div id="tb">
			<shiro:hasPermission name="${resourceid}:add"><a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-add" plain="true" size="large" onclick="addmuse()">添加</a></shiro:hasPermission> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-reload" plain="true" size="large" onclick="renovateType()">刷新</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-undo" plain="true" size="large" onclick="foldType()">折叠</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-redo" plain="true" size="large" onclick="openType()">展开</a>
	</div>
	<table id="tt"></table>
	<!-- 操作按钮 -->
	<div id="ttOPT" class style="display:none" >
		<shiro:hasPermission name="${resourceid}:edit">
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true" method="toEdit" prop="colid">编辑</a>
		</shiro:hasPermission>

		<shiro:hasPermission name="${resourceid}:del">
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true" method="delType" prop="colid">删除</a>
		</shiro:hasPermission>
	</div>
	
	<!-- 编辑DIV -->
	<div id="edit_win" class="none" style="display:none;" data-options="maximized:true">
		<form method="post" id="ff" enctype="multipart/form-data">
		 <!-- 隐藏作用域 -->
			<div class="main">
				<div class="row">
					<div><label>栏目标题:</label></div>
					<div>
						<input class="easyui-textbox" name="coltitle" style="width:90%;height:35px"
						data-options="required:true, validType:'length[0,60]'"/>
					</div>
				</div>
				<div class="row">
					<div><label>上级栏目:</label></div>
					<div>
						<input class="easyui-combotree" name="colpid" id="colpid" style="width:90%;height:35px"  data-options=" required:true, url:'${basePath}/admin/shop/selmus'" />
					</div>
				</div>
				<div class="row">
					<div><label>栏目排序:</label></div>
					<div>
						<div>
						<input class="easyui-numberspinner" name="colidx" id="colidx" value="" style="width:90%;height:35px"
						data-options="increment:1, required:true,min:1,max:999"/>
					</div>
					</div>
				</div>
				<div class="row">
					<div><label>栏目状态:</label></div>
					<div>
						<select class="easyui-combobox" name="colstate" id="colstate" style="width:90%;height:35px"
						data-options="editable:false, required:true, panelHeight:'auto'">
							<option value="0">关闭</option>
							<option value="1">启用</option>
						</select>
					</div>
				</div>
				<div class="row">
					<div><label>栏目图片:</label></div>
					<div>
						<input class="easyui-filebox" name="muspic_up" id="muspic_up" style="width:81%;height:35px"/>
						<a id="btn_pic" class="easyui-linkbutton" data-options="plain:true," iconCls="icon-clear" style="width:9%;height:32px;">清除</a>
					</div>
				</div>
				<div class="row">
					<div><label></label></div>
					<div>
						<img id="muspic_up_img" style="height:150px;"/>
					</div>
				</div>
			</div>
		</form>
	</div>
</body>
</html>