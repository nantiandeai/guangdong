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
<script type="text/javascript" charset="utf-8" src="${basePath}/static/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${basePath}/static/ueditor/ueditor.all.min.js"></script>
<script type="text/javascript" charset="utf-8" src="${basePath}/static/ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript">
var winform = new WhuiWinForm("#page_edit",{height:250});
var pagemageid = "${pagemageid}";

$(function(){
	var options = {
		queryParams:{
			pagemageid : pagemageid
		},
		title : "电子杂志页码",
		url: getFullUrl('/admin/magezine/pageinfo'),
		rownumbers:true,
		singleSelect:false,
		columns: [[
		{field:'ck',checkbox:true},
		{field:'magetitle',title:'电子杂志',width:80},
		{field:'pagetitle',title:'页码标题',width:80},
		{field:'pagepic',title:'页码图片',width:80, formatter:imgFMT},    
		{field:'pageidx',title:'页码排序',width:80},
		   
        {field:'pagestate',title:'状态',width:80,formatter :onOffFMT},
        {field:'opt', title:'操作', formatter: WHdg.optFMT, optDivId:'pageOPT'}
		]]
	}
	//初始表格
	WHdg.init('page', 'pageTool', options);
	//初始弹出框
	winform.init();
	//显示出查询栏
	$("#pageTool").css('visibility','visible');
	//清除图片输入框的值
	$('#btn_pic').bind('click', function(){  
		$("#pagepic_up").filebox('clear');
    });  
})

/**
 * 添加电子杂志
 */
function addPage(){
	winform.openWin();
	winform.setWinTitle("添加电子杂志");
	$("#page_ff").form("clear");
	$(".img_div").remove();
	
	var frm = winform.getWinBody().find('form').form({
		url : getFullUrl('/admin/magezine/savePage'),
		onSubmit : function(param){
			param.pagemageid = pagemageid;
			//验证是否通过
            var _is = $(this).form('enableValidation').form('validate');
			if (!_is) {
				winform.oneClick(function(){ frm.submit(); });
			}
            return _is;
        },
		success : function(data) {
			var Json = $.parseJSON(data);
			if(Json && Json.success == "0"){
				$('#page').datagrid('reload');
				$.messager.alert('提示', '操作成功!');
				winform.closeWin();
			   }else{
				   $.messager.alert('提示', '操作失败!');
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
 * 修改电子杂志
 */
function editPage(index){
	var row = WHdg.getRowData(index);
	winform.openWin();
	winform.setWinTitle("修改电子杂志");
	$("#page_ff").form("clear");
	var frm = winform.getWinBody().find('form').form({
		url : getFullUrl('/admin/magezine/savePage'),
		onSubmit : function(param){
			//验证是否通过
            var _is = $(this).form('enableValidation').form('validate');
			if (!_is) {
				winform.oneClick(function(){ frm.submit(); });
			}
            return _is;
        },
		success : function(data) {
			var Json = $.parseJSON(data);
			if(Json && Json.success == "0"){
				$('#page').datagrid('reload');
				$.messager.alert('提示', '操作成功!');
				winform.closeWin();
			   }else{
				   $.messager.alert('提示', '操作失败!');
				   winform.oneClick(function(){ frm.submit(); });
			   }
		}
	});
	_showImg(row);
	frm.form('load',row);
	winform.oneClick(function(){ frm.submit(); });
	/* winform.setFoolterBut({onClick:function(){
		frm.submit();
		
	}}); */
}
/**
 * 删除电子杂志
 */
function delPage(index){
	var row = WHdg.getRowData(index);
	var pageid = row.pageid;
	$.messager.confirm('确认对话框','您确定删除这条记录吗？',function(r){
		if (r) {
			$.ajax({
				type : "post",
				url : getFullUrl('/admin/magezine/delPage'),
				data : {pageid : pageid},
				success : function(data){
					if (data.success == '0') {
						$("#page").datagrid('reload');
					}else{
						$.messager.alert('提示', '操作失败。原因：'+data.errmsg, 'error');
					}
				}
			})
		}
		
	})
}

 

//处理编辑时显示图片
function _showImg(data){
	$(".img_div").remove();
	var imgDiv = '<div class="row img_div">'
		+'<div></div>'
		+'<div><img width="200" height="150"> </div> '
		+'</div>';
		
	if (data.pagepic){
		var imgrow = $("[name$='pagepic_up']").parents(".row");
		imgrow.after(imgDiv);
		imgrow.next(".img_div").find("div img").attr("src",getFullUrl(data.pagepic));	
	}
}

/**启用*/
function sendCheck(index){
	var row = WHdg.getRowData(index);
	var pageid = row.pageid;
	$.messager.confirm('确认对话框', '您确认启用所选的数据吗？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/magezine/checkPage'),
				data: {pageid : pageid,fromstate: 0, tostate:1},
				success: function(data){
					if(data.success == '0'){
						$('#page').datagrid('reload');
					   }else{
						   $.messager.alert('提示', '操作失败。原因：'+data.errmsg, 'error');
					   }
				}
			});
		}
	});
}
/**停用*/
function nocheck(index){
	var row = WHdg.getRowData(index);
	var pageid = row.pageid;
	$.messager.confirm('确认对话框', '您确认启用所选的数据吗？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/magezine/checkPage'),
				data: {pageid : pageid,fromstate: 1, tostate:0},
				success: function(data){
					if(data.success == '0'){
						$('#page').datagrid('reload');
					   }else{
						   $.messager.alert('提示', '操作失败。原因：'+data.errmsg, 'error');
					   }
				}
			});
		}
	});
}
</script>
</head>
<body>
		<!-- 电子杂志管理页面 -->
		<table id="page"></table>
		
		<!-- 查询栏 -->
		<div id="pageTool" style="visibility: hidden;padding-top: 5px" >
			<div style="line-height: 32px;">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" size='large' id="btn" onclick="addPage()">添加</a>
			</div>
			<div style="padding-top: 5px">
				页码标题:
				<input class="easyui-textbox" name="pagetitle" data-options="validType:'length[1,30]'"/>
				状态:
				<select class="easyui-combobox" name="pagestate" data-options="editable:false">
					<option value="">全部</option>
					<option value="0">启用</option>
					<option value="1">停用</option>
				</select>
				<a href="javascript:void(0)" class="easyui-linkbutton search" iconCls="icon-search">查询</a>
			</div>
		</div>
		      
		<!-- 操作按钮 -->
		<div id="pageOPT" style="display:none">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" method="editPage">修改</a> 
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" method="delPage">删除</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="pagestate" validVal="0" method="sendCheck">启用</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="pagestate" validVal="1" method="nocheck">停用</a>
		</div> 
				
		
		
		<!-- 电子杂志管理 -->
		 <div id="page_edit" class="none" style="display:none"  data-options=" fit:true" >
			 <form id="page_ff" method="post" enctype="multipart/form-data" >
				<!-- 隐藏域  -->
				<input type="hidden" id="pageid" name="pageid" value="" />
				<!-- <input type="hidden" id="pagemageid" name="pagemageid" value="" /> -->
				<input type="hidden" id="pagepic" name="pagepic" value="" />
				
				<div class="main">
					<div class="row">
		    			<div>页码标题:</div>
		    			<div>
		    				<input id="pagetitle" class="easyui-textbox" name="pagetitle" style="width:100%;height:32px;" data-options=" required:true"/>
		    			</div>
		    		</div>
		    		<div class="row">
		    			<div>页码序号:</div>
		    			<div>
		    				<input id="pageidx" class="easyui-numberspinner" name="pageidx" style="width:100%;height:32px;" required="required" data-options="min:1,max:1000,editable:false">  
		    			</div>
		    		</div>
		    		<div class="row">
		    			<div>页码图片:</div>
		    			<div>
							<input id="pagepic_up" class="easyui-filebox" name="pagepic_up" style="width:89%;height:32px;" data-options="validType:'isIMG[\'pagepic_up\']', buttonText:'选择图片', accept:'image/jpeg,image/peg, image/png'"/>
		    				<a id="btn_pic" class="easyui-linkbutton" data-options="plain:true," iconCls="icon-clear" style="width:9%;height:32px;">清除</a>
		    			</div>
		    		 </div>
		    		<div class="row">
		    			<div>页码简介:</div>
		    			<div>
							<input id="pagedesc" class="easyui-textbox" name="pagedesc" style="width:100%;height:60px;" data-options="multiline:true, validType:'length[1,50]'"/>
		    			</div>
		    		 </div>
				</div>
			</form>
		</div>	
		
		
</body>
</html>