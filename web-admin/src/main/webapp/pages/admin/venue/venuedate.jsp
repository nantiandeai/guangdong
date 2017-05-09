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

<script type="text/javascript" src="${basePath}/pages/admin/venue/venuedatebked.js"></script>
<script type="text/javascript">
var venid = "${venid}";

var vdb = new VenueDatebked();

$(function(){
	$("#vtTool").css('visibility','visible');

	vdb.init({
		grid : WHdg,
		basePath : "${basePath}"
	});
})

var winform = new WhuiWinForm("#vt_edit",{height:250});


/**
 * 添加场馆
 */
function addvt(){
	winform.openWin();
	winform.setWinTitle("添加日期");
	$("#vt_ff").form('clear');
	var now = dateFMT(new Date());
	var frm = winform.getWinBody().find('form').form({
		url : getFullUrl('/admin/ven/savedate'),
		onSubmit : function(param){
			param.venid = venid;
			var s = $('#vendsdate').datebox('getValue');
			var e = $('#vendedate').datebox('getValue');
			//时间比较
			if(s >= e){
				$.messager.alert("提示", "开始日期应小于结束日期!");
				winform.oneClick(function(){ frm.submit(); });
				return false;
			}
			if(s < now){
				
				$.messager.alert("提示", "开始日期应大于当前日期!");
				winform.oneClick(function(){ frm.submit(); });
				return false;
				
			}
            var _is = $(this).form('enableValidation').form('validate');
            if (!_is) {
            	winform.oneClick(function(){ frm.submit(); });
			}
            return _is;
        },
		success : function(data) {
			var Json = $.parseJSON(data);
			if(Json && Json.success == "0"){
				$('#vt').datagrid('reload');
				$.messager.alert('提示', ''+Json.msg);
				winform.closeWin();
			   }else{
				   $.messager.alert('提示', '操作失败！');
				   winform.oneClick(function(){ frm.submit(); });
			   }
		}
	});
	frm.form("clear");
	winform.oneClick(function(){ frm.submit(); });
}

/**
 * 是否可以修改
 */
function editvt(index){
	var row = WHdg.getRowData(index);
	var vendid = row.vendid;
	var now = new Date();
	$.ajax({
		type : "post",
		url : getFullUrl('/admin/ven/isEditDate'),
		data : {vendid : vendid},
		success :  function(data){
			if (data == 0 || data == "0") {
				winform.openWin();
				winform.setWinTitle("编辑时段信息");
				var frm = winform.getWinBody().find('form').form({
					url : getFullUrl('/admin/ven/savedate'),
					onSubmit : function(param){
						var s = $('#vendsdate').datebox('getValue');
						var e = $('#vendedate').datebox('getValue');
						//时间比较
						if(s >= e){
							$.messager.alert("提示", "开始日期应小于结束日期!");
							winform.oneClick(function(){ frm.submit(); });
							return false;
							
						}
						if(s < now){
							
							$.messager.alert("提示", "开始日期应大于当前日期!");
							winform.oneClick(function(){ frm.submit(); });
							return false;
							
						}
			            var _is = $(this).form('enableValidation').form('validate');
			            if (!_is) {
			            	winform.oneClick(function(){ frm.submit(); });
						}
			            return _is;
			        },
					success : function(data) {
						var Json = $.parseJSON(data);
						if(Json && Json.success == "0"){
							$('#vt').datagrid('reload');
							$.messager.alert('提示', '操作成功!');
							winform.closeWin();
						   }else{
							   $.messager.alert('提示', '操作失败!');
							   winform.oneClick(function(){ frm.submit(); });
						   }
					}
				});
				//格式化日期
				frm.form("clear");
				frm.form("load", row);
				winform.oneClick(function(){ frm.submit(); });
			}else{
				$.messager.alert("提示", "该时段在预定中已有记录，无法修改!");
			}
		}
	});
	
}
/**
 * 删除时进行判断，看在预定记录表中是否有记录，有则无法删除
 */
function delvt(index){
	var row = WHdg.getRowData(index);
	var vendid = row.vendid;
	$.ajax({
		type : "post",
		url : getFullUrl('/admin/ven/isEditDate'),
		data : {vendid : vendid},
		success :  function(data){
			
			if (data == 0 || data == "0") {
				$.messager.confirm('确认对话框', '您确认要删除吗？', function(r){
					if (r){
						$.ajax({
							type: "POST",
							url: getFullUrl('/admin/ven/delvenDate'),
							data : {vendid : vendid},
							success: function(data){
								//alert(JSON.stringify(data));
								if(data.success == 0){
									$('#vt').datagrid('reload');
								   }else{
									   $.messager.alert('提示', '操作失败', 'error');
								   }
							}
						});
					}
				});
			}else {
				$.messager.alert("提示", "该时段在预定中已有记录，无法删除!");
			}
		}
	});
}

 
/**启用*/
function checkvt(index){
	var row = WHdg.getRowData(index);
	var vendid = row.vendid;
	$.ajax({
		type : "post",
		url : getFullUrl('/admin/ven/isUseDate'),
		data : {vendid : vendid},
		success :  function(data){
			if (data > 0 || data != "0") {
				$.messager.confirm('确认对话框', '您确认启用所选的数据吗？', function(r){
					if (r){
						$.ajax({
							type: "POST",
							url: getFullUrl('/admin/ven/checkDate'),
							data: {vendid : vendid,fromstate: 0, tostate:1},
							success: function(data){
								if(data.success == '0'){
									$('#vt').datagrid('reload');
								   }else{
									   $.messager.alert('提示', '操作失败。原因：'+data.errmsg, 'error');
								   }
							}
						});
					}
				});
			}else {
				$.messager.alert("提示", "没有设置时段或时段未启用，不能启用！");
			}
		}
	});
}
/**停用*/
function nocheck(index){
	var row = WHdg.getRowData(index);
	var vendid = row.vendid;
	$.ajax({
		type : "post",
		url : getFullUrl('/admin/ven/isEditDate'),
		data : {vendid : vendid},
		success :  function(data){
			if (data == 0 || data == "0") {
				$.messager.confirm('确认对话框', '您确认启用所选的数据吗？', function(r){
					if (r){
						$.ajax({
							type: "POST",
							url: getFullUrl('/admin/ven/checkDate'),
							data: {vendid : vendid,fromstate: 1, tostate:0},
							success: function(data){
								if(data.success == '0'){
									$('#vt').datagrid('reload');
								   }else{
									   $.messager.alert('提示', '操作失败。原因：'+data.errmsg, 'error');
								   }
							}
						});
					}
				});
			}else {
				$.messager.alert("提示", "该时段在预定中已有记录，无法停用!");
			}
		}
	});
}
/**
 * 时段管理
 * @param index
 * @returns
 */
function shiduan(index){
	var rows = $('#vt').datagrid('getRows');
	var row = rows[index];//WHdg2.getRowData(index);
	var venid = row.vendid;
	//alert(venid);
	$('#shiduan iframe').attr('src', getFullUrl("/admin/ven/timepage?venid="+venid));
	$('#shiduan').dialog({    
	    title: '时段管理',  
	    modal:true,
	    maximizable: true,
	    maximized: true,
	    width: 400,
	    height: 200   
	}); 
}

 /**
  * 初始培训批次表格
  */
  
 $(function(){
 	//定义表格参数
	var options = {
			queryParams:{
				venid : venid
			},
			title: '场馆日期',	 
			url: getFullUrl('/admin/ven/finddate'),
			rownumbers:true,
			singleSelect:false,
			columns: [[
			{field:'ck',checkbox:true},
			{field:'venname',title:'场馆名称',width:80},
			{field:'vendsdate',title:'开始日期',width:80, formatter: dateFMT},
			{field:'vendedate',title:'结束日期',width:80,formatter: dateFMT},
			{field:'vendstate',title:'状态',width:80,  formatter: onOffFMT},
		//	
	        {field:'opt', title:'操作', formatter: WHdg.optFMT, optDivId:'vtOPT'}
			]]
	};

	//初始表格
	WHdg.init('vt', 'vtTool', options);
	
	//初始弹出框
	winform.init();

 });
 
 
	
</script>
</head>
<body>
		<!-- 培训管理页面 -->
		<table id="vt"></table>
		
		<!-- 查询栏 -->
		<div id="vtTool" class="grid-control-group" style="display: none;" >
			<div>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" size='small' id="btn" onclick="addvt()">添加</a>
			</div>
			
		</div>
		      
		<!-- 操作按钮 -->
		<div id="vtOPT" style="display:none">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" method="editvt">修改</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" method="delvt">删除</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="vendstate" validVal="0" method="checkvt">启用</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="vendstate" validVal="1" method="nocheck">停用</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="vendstate" validVal="0" method="shiduan">时段管理</a>
			<a href="javascript:void(0)" method="vdb.show">内定管理</a>
		</div> 
				
	 <!--  培训管理dialog -->
		 
		 <div id="vt_edit" class="none" style="display:none"  data-options=" fit:true" >
		    		
			 <form id="vt_ff" method="post" >
				<!-- 隐藏域  -->
		    	<input type="hidden" id="vendid" name="vendid" value="" />
				
				<div class="main">
		    		<div class="row">
		    			<div>开始日期:</div>
		    			<div>
		    				<input id="vendsdate" name="vendsdate" class="easyui-datebox"  data-options="editable:false, required:true"  style="height: 35px; width: 100%"></input>
		    			</div>
		    		</div>
		    		<div class="row">
		    			<div>结束日期:</div>
		    			<div>
		    				<input id="vendedate" name="vendedate" class="easyui-datebox"  data-options="editable:false, required:true"  style="height: 35px; width: 100%"></input>
		    			</div>
		    		</div>
				</div>
			</form>
		</div>	
		
		<!-- 时段dialog -->
		<div id="shiduan" style="display:none">
			<iframe  style="width:100%; height:100%;border:0px"></iframe>
		</div>
		
</body>
</html>