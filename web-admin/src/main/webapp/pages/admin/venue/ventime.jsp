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
var venid = "${venid}";

$(function(){
	$("#vtTool").css('visibility','visible');
})

var winform = new WhuiWinForm("#vt_edit",{height:250});


/**
 * 添加场馆
 */
function addvt(){
	winform.openWin();
	winform.setWinTitle("添加时段");
	$("#vt_ff").form('clear');
	
	var frm = winform.getWinBody().find('form').form({
		url : getFullUrl('/admin/ven/saveTime'),
		onSubmit : function(param){
			param.venid = venid;
			var s = $('#vtstime').timespinner('getValue');
			var e = $('#vtetime').timespinner('getValue');
			//时间比较
			if(s >= e){
				$.messager.alert("提示", "时段开始时间应小于时段结束时间!");
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
				   $.messager.alert('提示', ''+Json.msg);
				   winform.oneClick(function(){ frm.submit(); });
			   }
		}
	});
	frm.form("clear");
	winform.oneClick(function(){ frm.submit(); });
	/* winform.setFoolterBut({onClick:function(){
		frm.submit();
		
	}}); */
}

/**
 * 是否可以修改
 */
function editvt(index){
	var row = WHdg.getRowData(index);
	var vtid = row.vtid;
	$.ajax({
		type : "post",
		url : getFullUrl('/admin/ven/isEdit'),
		data : {vtid : vtid},
		success :  function(data){
			if (data == 0 || data == "0") {
				winform.openWin();
				winform.setWinTitle("编辑时段信息");
				var frm = winform.getWinBody().find('form').form({
					url : getFullUrl('/admin/ven/saveTime'),
					onSubmit : function(param){
						var s = $('#vtstime').timespinner('getValue');
						var e = $('#vtetime').timespinner('getValue');
						//时间比较
						if(s >= e){
							$.messager.alert("提示", "时段开始时间应小于时段结束时间!");
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
				/* var _data = $.extend({}, row,
						{
					vtstime : datetimeFMT(row.vtstime),
					vtetime : datetimeFMT(row.vtetime),
				}); */
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
	var vtid = row.vtid;
	$.ajax({
		type : "post",
		url : getFullUrl('/admin/ven/isEdit'),
		data : {vtid : vtid},
		success :  function(data){
			
			if (data == 0 || data == "0") {
				$.messager.confirm('确认对话框', '您确认要删除吗？', function(r){
					if (r){
						$.ajax({
							type: "POST",
							url: getFullUrl('/admin/ven/delvenTime'),
							data: {vtid : vtid},
							success: function(data){
							//	alert(JSON.stringify(data));
								if(data == 1){
									$('#vt').datagrid('reload');
								   }else{
									   $.messager.alert('提示', '操作失败。原因：'+data.errmsg, 'error');
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
	var vtid = row.vtid;
	$.messager.confirm('确认对话框', '您确认启用所选的数据吗？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/ven/checkTime'),
				data: {vtid : vtid,fromstate: 0, tostate:1},
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
}
/**停用*/
function nocheck(index){
	var row = WHdg.getRowData(index);
	var vtid = row.vtid;
	$.messager.confirm('确认对话框', '您确认启用所选的数据吗？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/ven/checkTime'),
				data: {vtid : vtid,fromstate: 1, tostate:0},
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
			/* title: '场馆时段', */	
			url: getFullUrl('/admin/ven/findtime'),
			rownumbers:true,
			singleSelect:false,
			columns: [[
			{field:'ck',checkbox:true},
			{field:'venname',title:'场馆名称',width:80},
			{field:'vtstime',title:'时段开始时间',width:80},
			{field:'vtetime',title:'时段结束时间',width:80},
			{field:'vtstate',title:'状态',width:80,  formatter: onOffFMT},
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
		<div id="vtTool" style="visibility: hidden;padding-top: 5px" >
			<div style="line-height: 32px;">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" size='large' id="btn" onclick="addvt()">添加</a>
			</div>
			<%-- <div style="padding-top: 5px">
				场馆类型:
				<input class="easyui-combobox" name="tratyp" data-options="valueField:'typid',textField:'typname',url:'${basePath}/comm/whtyp?type=2'"/>
				场馆区域: 
				<input class="easyui-combobox" name="traarttyp" data-options="valueField:'typid',textField:'typname',url:'${basePath}/comm/whtyp?type=0'"/>
			</div>	
			<div style="padding-top: 5px">
				培训标题:
				<input class="easyui-textbox" name="tratitle" data-options="validType:'length[1,30]'"/>
				开始时间:
				<input class="easyui-datebox" name="sdate_s" data-options=""/>到<input class="easyui-datebox" name="sdate_e" data-options=""/>
				<a href="#" class="easyui-linkbutton search" iconCls="icon-search">查询</a>
			</div> --%>
			
		</div>
		      
		<!-- 操作按钮 -->
		<div id="vtOPT" style="display:none">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" method="editvt">修改</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" method="delvt">删除</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="vtstate" validVal="0" method="checkvt">启用</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="vtstate" validVal="1" method="nocheck">停用</a>
		</div> 
				
	 <!--  培训管理dialog -->
		 
		 <div id="vt_edit" class="none" style="display:none"  data-options=" fit:true" >
		    		
			 <form id="vt_ff" method="post" enctype="multipart/form-data" >
				<!-- 隐藏域  -->
				<!-- <input type="hidden" id="venid" name="venid" value="" /> -->
		    	<input type="hidden" id="vtid" name="vtid" value="" />
				
				<div class="main">
		    		<div class="row">
		    			<div>时段开始时间:</div>
		    			<div>
		    				<input id="vtstime" name="vtstime" class="easyui-timespinner"  style="height: 35px; width: 100%" required="required" data-options="min:'00:00',showSeconds:false"></input>
		    			</div>
		    		</div>
		    		<div class="row">
		    			<div>时段结束时间:</div>
		    			<div>
		    				<input id="vtetime" name="vtetime" class="easyui-timespinner"  style="height: 35px; width: 100%" required="required" data-options="min:'00:00',showSeconds:false"></input>
		    			</div>
		    		</div>
				</div>
			</form>
		</div>	
		
</body>
</html>