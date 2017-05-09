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
<script >
//
var winform = new WhuiWinForm("#tu_ff",{height:450});


/**
 * 添加团员
 */
function addUser(){
	winform.openWin();
	winform.setWinTitle("添加团员");
	var frm = winform.getWinBody().find('form').form({
		url : getFullUrl('/admin/arts/saveUser'),
		onSubmit : function(param){
			var _is = $(this).form('enableValidation').form('validate');
            param.tutroupeid = '${param.troupeid}';
			if (!_is) {
				winform.oneClick(function(){ frm.submit(); });
			}
            return _is;
        },
		success : function(data) {
			var Json = $.parseJSON(data);
			if(Json && Json.success == "0"){
				$('#tu').datagrid('reload');
				//$.messager.alert('提示', '操作成功!');
				winform.closeWin();
			}else{
				$.messager.alert('提示', '操作失败!');
				winform.oneClick(function(){ frm.submit(); });
			}
		}
	});
	frm.form("clear");
	$('#sta').combobox('setValue', '1');
	winform.oneClick(function(){ frm.submit(); });
	/* winform.setFoolterBut({onClick:function(){
		frm.submit();
	}}); */
}

/**
 * 修改团员
 */
function editUser(index){
	
	var row = WHdg.getRowData(index);
	var tuid = row.tuid;
	winform.openWin();
	winform.setWinTitle("编辑团员信息");
	_showImg(row);
	$("form").form('load',row);
	var frm = winform.getWinBody().find('form').form({
		url : getFullUrl('/admin/arts/saveUser'),
		onSubmit : function(param) {
            param.tutroupeid = '${param.troupeid}';
			var _is = $(this).form('enableValidation').form('validate');
			if (!_is) {
				winform.oneClick(function(){ frm.submit(); });
			}
			return _is;
		},
		success : function(data) {
			//alert(JSON.stringify(data));
			var Json = $.parseJSON(data);
			if(Json && Json.success == "0"){
	 			//$.messager.alert('提示', '操作成功！');
				$('#tu').datagrid('reload');
				winform.closeWin();
			} else {
				$.messager.alert('提示', '修改失败,操作无效！');
				winform.oneClick(function(){ frm.submit(); });
			}
		}
	});
	winform.oneClick(function(){ frm.submit(); });
	/* winform.setFoolterBut({onClick:function(){
		frm.submit();
	}}); */
}
/**
 * 图片处理
 */
function _showImg(data){
	$(".img_div").remove();
	
	var imgDiv = '<div class="row img_div">'
		+'<div></div>'
		+'<div><img width="200" height="150"> </div> '
		+'</div>';
	
	if (data.tupic){
		var imgrow = $("[name$='tupic_up']").parents(".row");
		imgrow.after(imgDiv);
		imgrow.next(".img_div").find("div img").attr("src", getFullUrl("/whgstatic"+data.tupic));	
	}
	
}
/**
 * 删除团员
 */
function delUser(index){
	var row = WHdg.getRowData(index);
	var tuid = row.tuid;
	$.messager.confirm('确认对话框', '您确认要删除吗？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/arts/delUser'),
				data: {tuid : tuid},
				success: function(data){
					if(data.success == '0'){
						$('#tu').datagrid('reload');
					   }else{
						   $.messager.alert('提示', '操作失败!');
					   }
				}
			});
		}
	});
}
function yxFMT(val, rowData, index){
	return val == "1" ? '有效' : '无效';
}
//页面加载
$(function(){
	//定义表格参数
	var options = {
		title: '馆办团队成员管理',
		url: getFullUrl('/admin/arts/findTroupeUser?troupeid=${param.troupeid}'),
		sortName: 'tuid',
		sortOrder: 'desc',
		pageSize:20,
		columns: [[
		//	{field:'tuid',title:'团队成员标识', width:100},
			{field:'tuname',title:'成员姓名', width:100},
			{field:'tupic',title:'成员照片',width:100, formatter:imgFMT},
			{field:'troupename',title:'艺术团',width:100},
			{field:'tustate',title:'状态',width:100, formatter:yxFMT},
			{field:'opt', title:'操作',formatter:WHdg.optFMT, optDivId:'tu_opt'}
		]]
	};
	//初始表格
	WHdg.init('tu', 'tra_tb', options);
	
	winform.init();
	//$('#sta').combobox('setValue', 'ww');
})
</script>
</head>
<body >
		<!-- 团队成员管理表格 -->
		<table id="tu"></table>
		
		<!-- 培训模板表格操作按钮 -->
		<div id="tra_tb" style="height:auto">
			<div>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" onclick="WhgComm.editDialogClose()">返回</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="addUser()">添加</a>
			</div>
		</div>
		<!-- 操作按钮 -->
		<div id="tu_opt" class="none">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" method="editUser">修改</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" method="delUser">删除</a>
		</div>
		
		<!-- 团队成员管理编辑  -->
		<div id="tu_ff" class="none" style="display: none">
			<form method="post" enctype="multipart/form-data">
				<input type="hidden" id="tuid" name="tuid" value="" />
				<input type="hidden" id="tupic" name="tupic" value="" />
				<div class="main">
		    		<div class="row">
		    			<div>成员姓名:</div>
		    			<div>
		    				<input class="easyui-textbox" id="tuname" name="tuname" style="height: 35px; width: 90%" data-options="validType:'length[1,10]'">
		    			</div>
		    		</div>
		    		<div class="row">
		    			<div>成员照片:</div>
			    			<div>
			    				<input id="tupic_up"  class="easyui-filebox" name="tupic_up" style="width:50%;height:32px;" data-options="validType:'isIMG[\'tupic_up\']', buttonText:'选择图片', accept:'image/jpeg,image/peg, image/png'"/>&nbsp;(尺寸：100*100)
			    			</div>
		    		</div>
		    		<div class="row">
		    			<div>成员介绍:</div>
		    			<div>
			    			<input class="easyui-textbox" data-options="validType:'length[0,80]',multiline:true" id="tudesc" name="tudesc" style="width: 90%; height: 100px">
		    			</div>
		    		</div>
		    		
		    		
		    		<div class="row">
		    			<div>状态:</div>
		    			<div>
		    				<select id="sta" class="easyui-combobox"
								name="tustate" style="height: 35px; width: 90%"
								data-options="editable:false,required:true">
								<option value="1">有效</option>
								<option value="0">无效</option>
							</select>	
		    			</div>
		    		</div>
				</div>
			</form>
		</div>	 
		
</body>
</html>