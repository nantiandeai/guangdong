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


$(function(){
	$("#venueTool").css('visibility','visible');
})

var winform = new WhuiWinForm("#venue_edit",{height:250});


/**
 * 添加场馆
 */
function addVenue(index){
	if(typeof window.init_UE == 'undefined' || typeof window.init_UE1 == 'undefined'){
		 $.messager.progress();
		 function _fn(){
			 if(typeof window.init_UE == 'undefined' || typeof window.init_UE1 == 'undefined'){
				 window.setTimeout(function(){
					 _fn();
				 }, 200);
			 }else{
				 $.messager.progress('close');
				 editzyzz(index);
			 }
		 }
		 _fn();
		 return;
	 }
	var row = WHdg.getRowData(index);
	UE.getEditor('traeditor').setContent("");
	UE.getEditor('catalog').setContent("");
	winform.openWin();
	winform.setWinTitle("添加场馆");
	clear();
	//$("#venue_ff").form('clear');
	winform.getWinFooter().find("a:eq(0)").show();
	
	$(".img_div").remove();
	var frm = winform.getWinBody().find('form').form({
		url : getFullUrl('/admin/ven/save'),
		onSubmit : function(param){
			/* var phone = $("#vencontactnum").textbox('getText');
		    if(!(/^1(3|4|5|7|8)\d{9}$/.test(phone) || /^0\d{2,3}-?\d{7,8}$/.test(phone))){ 
		    	$.messager.alert('提示', '联系方式有误，请重填！');
		        winform.oneClick(function(){ frm.submit(); });
		        return false; 
		    }; */
			//设置富文本的内容
			//设施
			$('#venintroduce2').val(UE.getEditor('traeditor').getContent());
			//场馆
			$('#venintroduce1').val(UE.getEditor('catalog').getContent());
            var _is = $(this).form('enableValidation').form('validate');
            if (!_is) {
            	winform.oneClick(function(){ frm.submit(); });
			}else{
				$.messager.progress();
			}
            return _is;
        },
		success : function(data) {
			$.messager.progress('close');
			var Json = $.parseJSON(data);
			if(Json && Json.success == "0"){
				$('#venue').datagrid('reload');
				$.messager.alert('提示', '操作成功!');
				winform.closeWin();
			   }else{
				   $.messager.alert('提示', '操作失败!');
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

function clear(){
	$("#ventags").combobox("clear");
	$("#venkeys").combobox("clear");
	$("#venscope").combobox("clear");
}

/**
 * 修改场馆信息
 */
function editVenue(index) {
	if(typeof window.init_UE == 'undefined' || typeof window.init_UE1 == 'undefined'){
		 $.messager.progress();
		 function _fn(){
			 if(typeof window.init_UE == 'undefined' || typeof window.init_UE1 == 'undefined'){
				 window.setTimeout(function(){
					 _fn();
				 }, 200);
			 }else{
				 $.messager.progress('close');
				 editzyzz(index);
			 }
		 }
		 _fn();
		 return;
	 }
	var row = WHdg.getRowData(index);
	winform.openWin();
	winform.setWinTitle("编辑场馆信息");
	clear();
	$("#venue_ff").form('clear');
	winform.getWinFooter().find("a:eq(0)").show();
	_showImg(row);
	//显示富文本的值
	UE.getEditor('traeditor').setContent(row.venintroduce2);
	UE.getEditor('catalog').setContent(row.venintroduce1);
	var frm = winform.getWinBody().find('form').form({
		url : getFullUrl('/admin/ven/save'),
		onSubmit : function(param){
			/* var phone = $("#vencontactnum").textbox('getText');
		    if(!(/^1(3|4|5|7|8)\d{9}$/.test(phone) || /^0\d{2,3}-?\d{7,8}$/.test(phone))){ 
		    	$.messager.alert('提示', '联系方式有误，请重填！');
		        winform.oneClick(function(){ frm.submit(); });
		        return false; 
		    }; */
			//设置富文本的内容
			$('#venintroduce2').val(UE.getEditor('traeditor').getContent());
			$('#venintroduce1').val(UE.getEditor('catalog').getContent());
            var _is = $(this).form('enableValidation').form('validate');
            if (!_is) {
            	winform.oneClick(function(){ frm.submit(); });
			}else{
				$.messager.progress();
			}
            return _is;
        },
		success : function(data) {
			$.messager.progress('close');
			var Json = $.parseJSON(data);
			if(Json && Json.success == "0"){
				$('#venue').datagrid('reload');
				$.messager.alert('提示', '操作成功!');
				winform.closeWin();
			   }else{
				   $.messager.alert('提示', '操作失败!');
				   winform.oneClick(function(){ frm.submit(); });
			   }
		}
	});
	frm.form("clear");
	frm.form("load", row);
	winform.oneClick(function(){ frm.submit(); });
	/* winform.setFoolterBut({onClick:function(){
		frm.submit();
		
	}}); */
}
//处理编辑时显示图片
function _showImg(data){
	$(".img_div").remove();
	var imgDiv = '<div class="row img_div">'
		+'<div></div>'
		+'<div><img width="200" height="150"> </div> '
		+'</div>';
		
	if (data.venpic){
		var imgrow = $("[name$='venpic_up']").parents(".row");
		imgrow.after(imgDiv);
		imgrow.next(".img_div").find("div img").attr("src",getFullUrl(data.venpic));	
	}
	$(".img_div1").remove();
	var imgDiv1 = '<div class="row img_div1">'
		+'<div></div>'
		+'<div><img width="200" height="150"> </div> '
		+'</div>';
		
	if (data.venpic){
		var imgrow = $("[name$='venpic_up1']").parents(".row");
		imgrow.after(imgDiv1);
		imgrow.next(".img_div1").find("div img").attr("src",getFullUrl(data.venpic));	
	}
}
 
/**
 * 查看详情
 */
 function look(index){
	var row = WHdg.getRowData(index);
	winform.openWin();
	winform.setWinTitle("查看详情");
	_showImg(row);
	winform.getWinFooter().find("a:eq(0)").hide();
	//显示富文本的值
	UE.getEditor('traeditor').setContent(row.venintroduce2);
	UE.getEditor('catalog').setContent(row.venintroduce1);
	
	var frm = winform.getWinBody().find('form').form({
	});
	
	//格式化日期
	frm.form("load", row);
	$('#venue').datagrid('reload');
} 
 /**删除培训
 */
function delVenue(index){
	var row = WHdg.getRowData(index);
	var venid = row.venid;
	$.messager.confirm('确认对话框', '您确认要删除吗？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/ven/delven'),
				data: {venid : venid},
				success: function(data){
				//	alert(JSON.stringify(data));
					if(data.success == '0'){
						$('#venue').datagrid('reload');
					   }else{
						   $.messager.alert('提示', '操作失败。原因：'+data.errmsg, 'error');
					   }
				}
			});
		}
	});
}
 

/**
 * 时段管理
 * @param index
 * @returns
 */
function shiduan(index){
	var rows = $('#venue').datagrid('getRows');
	var row = rows[index];//WHdg2.getRowData(index);
	var venid = row.venid;
	
	$('#shiduan iframe').attr('src', getFullUrl("/admin/ven/datepage?venid="+venid));
	$('#shiduan').dialog({    
	    title: '日期管理',  
	    modal:true,
	    maximizable: true,
	    maximized: true,
	    width: 400,
	    height: 200   
	}); 
}

/**
 * 预定管理
 * @param index
 * @returns
 */
/* function addzy(index){
	var rows = $('#venue').datagrid('getRows');
	var row = rows[index];//WHdg2.getRowData(index);
	var venid = row.venid;
	
	$('#destine iframe').attr('src', getFullUrl("/admin/ven/destine?vebvenid="+venid));
	$('#destine').dialog({    
	    title: '添加资源',  
	    modal:true,
	    maximizable: true,
	    maximized: true,
	    width: 400,
	    height: 200   
	}); 
} */


/**审核*/
function sendCheck(index){
	var row = WHdg.getRowData(index);
	var venid = row.venid;
	var fromstate = row.venstate;
	var vencanbk = row.vencanbk;
	$.messager.confirm('确认对话框', '您确认审核所选的数据吗？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/ven/check'),
				data: {vencanbk:vencanbk,venid : venid,fromstate: fromstate, tostate:2,_is:"yes"},
				success: function(data){
					if(data.success == '0'){
						$.messager.alert('提示', data.msg);
						$('#venue').datagrid('reload');
					   }else{
						   $.messager.alert('提示', '操作失败。原因：'+data.errmsg, 'error');
					   }
				}
			});
		}
	});
}
/**发布*/
function publish(index){
	var row = WHdg.getRowData(index);
	var venid= row.venid;
	var vencanbk = row.vencanbk;
	$.messager.confirm('确认对话框', '您确认发布所选的数据吗？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/ven/check'),
				data: {vencanbk:vencanbk,venid : venid,fromstate: 2, tostate:3},
				success: function(data){
					if(data.success == '0'){
						$.messager.alert('提示', data.msg);
						$('#venue').datagrid('reload');
					   }else{
						   $.messager.alert('提示', '操作失败。原因：'+data.errmsg, 'error');
					   }
				}
			});
		}
	});
}
/**打回审核*/
function nocheck(index){
	var row = WHdg.getRowData(index);
	var venid= row.venid;
	var vencanbk = row.vencanbk;
	$.messager.confirm('确认对话框', '您确认将所选的数据打回未审核吗？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/ven/check'),
				data: {vencanbk:vencanbk,venid : venid,fromstate: 2, tostate:1},
				success: function(data){
					if(data.success == '0'){
						$.messager.alert('提示', data.msg);
						$('#venue').datagrid('reload');
					   }else{
						   $.messager.alert('提示', '操作失败。原因：'+data.errmsg, 'error');
					   }
				}
			});
		}
	});
}
/**取消发布*/
function noPublish(index){
	var row = WHdg.getRowData(index);
	var venid= row.venid;
	var vencanbk = row.vencanbk;
	$.messager.confirm('确认对话框', '您确认将所选的数据取消发布吗？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/ven/check'),
				data: {vencanbk:vencanbk,venid : venid,fromstate: 3, tostate:2},
				success: function(data){
					if(data.success == '0'){
						$.messager.alert('提示', data.msg);
						$('#venue').datagrid('reload');
					   }else{
						   $.messager.alert('提示', '操作失败。原因：'+data.errmsg, 'error');
					   }
				}
			});
		}
	});
}
/** 判断是否能够进行审核*/
function _validFun(index){
	var row = WHdg.getRowData(index);
	return row.venstate == '0' || row.venstate == '1';
}

/**
 * 一键审核
 */
function checkAll(){
	var rows={};
	rows = $("#venue").datagrid("getSelections");
	if (rows == "" || rows == null) {
		$.messager.alert('提示', '请选择要操作的数据！');
		return;
	}
	var venids = _split = "";//id1,id2,id3
	for (var i = 0; i<rows.length; i++){
		venids += _split+rows[i].venid;
		_split = ",";
	}
	//trastate = rows[0].trastate;
	$.messager.confirm('确认对话框', '您确认将所选择的进行审核吗？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/ven/allcheck'),
				data: {venid : venids,fromstate: 1, tostate:2,_is:"yes"},
				success: function(data){
					if(data.success == '0'){
						$.messager.alert('提示', data.msg);
						$('#venue').datagrid('reload');
					   }else{
						   $.messager.alert('提示', '操作失败。原因：'+data.errmsg, 'error');
					   }
				}
			});
		}
	});
}
/**
 * 一键发布
 */
function publishAll(){
	var rows={};
	rows = $("#venue").datagrid("getSelections");
	if (rows == "" || rows == null) {
		$.messager.alert('提示', '请选择要操作的数据！');
		return;
	}
	var venids = _split = "";//id1,id2,id3
	for (var i = 0; i<rows.length; i++){
		venids += _split+rows[i].venid;
		_split = ",";
	}
	//trastate = rows[0].trastate;
	$.messager.confirm('确认对话框', '您确认将所选择的进行发布吗？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/ven/allcheck'),
				data: {venid : venids,fromstate: 2, tostate:3},
				success: function(data){
					if(data.success == '0'){
						$.messager.alert('提示', data.msg);
						$('#venue').datagrid('reload');
					   }else{
						   $.messager.alert('提示', '操作失败。原因：'+data.errmsg, 'error');
					   }
				}
			});
		}
	});
}
/**
 * 一键取消
 */
function noPublishAll(){
	var rows={};
	rows = $("#venue").datagrid("getSelections");
	if (rows == "" || rows == null) {
		$.messager.alert('提示', '请选择要操作的数据！');
		return;
	}
	var venids = _split = "";//id1,id2,id3
	for (var i = 0; i<rows.length; i++){
		venids += _split+rows[i].venid;
		_split = ",";
	}
	//trastate = rows[0].trastate;
	$.messager.confirm('确认对话框', '您确认将所选择的取消发布吗？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/ven/allcheck'),
				data: {venid : venids,fromstate: 3, tostate:2},
				success: function(data){
					if(data.success == '0'){
						$.messager.alert('提示', data.msg);
						$('#venue').datagrid('reload');
					   }else{
						   $.messager.alert('提示', '操作失败。原因：'+data.errmsg, 'error');
					   }
				}
			});
		}
	});
}
/**
 * 一键打回审核
 */
function goallCheck(){
	var rows={};
	rows = $("#venue").datagrid("getSelections");
	if (rows == "" || rows == null) {
		$.messager.alert('提示', '请选择要操作的数据！');
		return;
	}
	var venids = _split = "";//id1,id2,id3
	for (var i = 0; i<rows.length; i++){
		venids += _split+rows[i].venid;
		_split = ",";
	}
	//trastate = rows[0].trastate;
	$.messager.confirm('确认对话框', '您确认将所选择的打回审核吗？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/ven/allcheck'),
				data: {venid : venids,fromstate: 2, tostate:1},
				success: function(data){
					if(data.success == '0'){
						$('#venue').datagrid('reload');
					   }else{
						   $.messager.alert('提示', '操作失败。原因：'+data.errmsg, 'error');
					   }
				}
			});
		}
	});
}
/**
 * 添加资源
 * @param index
 * @returns
 */
function addZY(index){
	var rows = $('#venue').datagrid('getRows');
	var row = rows[index];//WHdg2.getRowData(index);
	var venid = row.venid;
	
	$('#addzy iframe').attr('src', getFullUrl("/admin/ent/entsrc?reftype=2016101400000053&refid="+venid+"&canEdit=true"));
	$('#addzy').dialog({    
	    title: '添加资源',  
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
			title: '场馆管理',	
			url: getFullUrl('/admin/ven/findvenue'),
			rownumbers:true,
			singleSelect:false,
			columns: [[
			{field:'ck',checkbox:true},
			{field:'venname',title:'场馆名称',width:80, sortable:true},
			{field:'venarea',title:'场馆区域',width:80, sortable:true, formatter: areaFMT},
			{field:'ventype',title:'场馆类型',width:80, sortable:true, formatter: tratypFMT},
			{field:'vencanbk',title:'是否可预订',width:80, sortable:true, formatter: ydFMT},
			{field:'venpic',title:'场馆图片',width:80, sortable:true, formatter: imgFMT},
		//	
			{field:'vencontact',title:'场馆联系人',width:80, sortable:true},    
			{field:'vencontactnum',title:'联系方式',width:80, sortable:true},
	        {field:'venstate',title:'状态',width:80,formatter :traStateFMT},
	        {field:'opt', title:'操作', formatter: WHdg.optFMT, optDivId:'venueOPT'}
			]]
	};

	//初始表格
	WHdg.init('venue', 'venueTool', options);
	
	//初始弹出框
	winform.init();
	
	//初始富文本
	UE.getEditor('traeditor').ready(function(){
		window.init_UE = true;
	});
	UE.getEditor('catalog').ready(function(){
		window.init_UE1 = true;
	});
	

 });
 
 
	
</script>
</head>
<body>
		<!-- 培训管理页面 -->
		<table id="venue"></table>
		
		<!-- 查询栏 -->
		<div id="venueTool" class="grid-control-group" style="display: none;" >
			<div>
				<shiro:hasPermission name="${resourceid}:add"><a href="#" class="easyui-linkbutton" iconCls="icon-add" size='small' id="btn" onclick="addVenue()">添加</a></shiro:hasPermission>
				<shiro:hasPermission name="${resourceid}:checkon"><a href="javascript:void(0)" class="easyui-linkbutton" size='small' onclick="checkAll()">批量审核</a></shiro:hasPermission>
				<shiro:hasPermission name="${resourceid}:checkoff"><a href="javascript:void(0)" class="easyui-linkbutton" size='small' onclick="goallCheck()">批量打回</a></shiro:hasPermission> 
				<shiro:hasPermission name="${resourceid}:publish"><a href="javascript:void(0)" class="easyui-linkbutton" size='small' onclick="publishAll()">批量发布</a></shiro:hasPermission> 
				<shiro:hasPermission name="${resourceid}:publishoff"><a href="javascript:void(0)" class="easyui-linkbutton" size='small' onclick="noPublishAll()">批量取消发布</a></shiro:hasPermission>
			</div>
			<div style="padding-top: 5px">
				场馆类型:
				<input class="easyui-combobox" name="ventype" data-options="valueField:'typid',textField:'typname',url:'${basePath}/comm/whtyp?type=15'"/>
				场馆区域: 
				<input  class="easyui-combobox" name="venarea" data-options="panelHeight:'auto', valueField:'typid',textField:'typname',url:'${basePath}/comm/whtyp?type=8'"/>
				场馆名称:
				<input  class="easyui-textbox" name="venname" data-options=""/>
				状态：
				<select class="easyui-combobox" name="venstate" data-options="panelHeight:'auto'">
					<option value="">全部</option>
					<option value="1">未审核</option>
					<option value="2">已审核</option>
					<option value="3">已发布</option>
				</select>
				<a href="javascript:void(0)" class="easyui-linkbutton search" iconCls="icon-search">查询</a>
			</div>
			
		</div>
		      
		<!-- 操作按钮 -->
		<div id="venueOPT" style="display:none">
			<shiro:hasPermission name="${resourceid}:edit"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="venstate" validVal="1" method="editVenue">修改</a></shiro:hasPermission> 
			<shiro:hasPermission name="${resourceid}:del"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="venstate" validVal="1" method="delVenue">删除</a></shiro:hasPermission>
			<shiro:hasPermission name="${resourceid}:view"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true"  method="look">查看详情</a></shiro:hasPermission>
			<shiro:hasPermission name="${resourceid}:checkon"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validFun="_validFun" method="sendCheck">审核</a></shiro:hasPermission>
			<shiro:hasPermission name="${resourceid}:checkoff"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="venstate" validVal="2" method="nocheck">打回审核</a></shiro:hasPermission>
			<shiro:hasPermission name="${resourceid}:publish"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="venstate" validVal="2" method="publish">发布</a></shiro:hasPermission>
			<shiro:hasPermission name="${resourceid}:publishoff"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="venstate" validVal="3" method="noPublish">取消发布</a></shiro:hasPermission>
			<shiro:hasPermission name="${resourceid}:edit"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" method="shiduan">日期管理</a></shiro:hasPermission>
			<shiro:hasPermission name="${resourceid}:edit"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="venstate" validVal="1" method="addZY">资源管理</a></shiro:hasPermission>
		</div>
				
	 <!--  培训管理dialog -->
		 
		 <div id="venue_edit" class="none" style="display:none"  data-options=" fit:true" >
		    		
			 <form id="venue_ff" method="post" enctype="multipart/form-data" >
				<!-- 隐藏域  -->
				<input type="hidden" id="venid" name="venid" value="" />
		    	<input type="hidden" id="venintroduce1" name="venintroduce1" value="" />
		    	<input type="hidden" id="venintroduce2" name="venintroduce2" value="" />
				<input type="hidden" id="venpic" name="venpic" value="" />
				
				<div class="main">
					<div class="row">
		    			<div>场馆名称:</div>
		    			<div>
		    				<input id="venname" class="easyui-textbox" name="venname" style="width:100%;height:32px;" data-options="validType:'length[0,60]',required:true"/>
		    			</div>
		    		</div>
		    		<div class="row">
		    			<div>场馆区域:</div>
		    			<div>
		    				<input id="venarea" class="easyui-combobox" name="venarea" style="width:100%;height:32px" 
									data-options="panelHeight:'auto', editable:false, required:true, valueField:'typid',textField:'typname',url:'${basePath}/comm/whtyp?type=8'"/>
		    			</div>
		    		</div>
		    		<div class="row">
		    			<div>场馆类型:</div>
		    			<div>
		    				<input id="ventype" class="easyui-combobox" name="ventype" style="width:100%;height:32px;" data-options="editable:false, required:true, valueField:'typid',textField:'typname',url:'${basePath}/comm/whtyp?type=15'"/>
		    			</div>
		    		</div>
		    		<div class="row">
		    			<div>关键字:</div>
			    		<div>
							<input id="venkeys" class="easyui-combobox" name="venkeys" multiple="true" style="width:100%;height:32px;" data-options="required:'true',missingMessage:'请用英文逗号分隔',panelHeight:'auto', valueField:'name',textField:'name',url:'${basePath}/comm/whkey?type=2016101400000048', multiple:true"/>
			    		</div>
		    		 </div>
		    		<div class="row">
		    			<div>标签:</div>
			    		<div>
							<input id="ventags" class="easyui-combobox" name="ventags" multiple="true" style="width:100%;height:32px;" data-options="required:'true',missingMessage:'请用英文逗号分隔',panelHeight:'auto', valueField:'name',textField:'name',url:'${basePath}/comm/whtag?type=2016120800000002', multiple:true"/>
			    		</div>
		    		 </div>
		    		<div class="row">
		    			<div>场馆图片:</div>
		    			<div>
							<input id="venpic_up"  class="easyui-filebox" name="venpic_up" style="width:90%;height:32px;" data-options="validType:'isIMG[\'venpic_up\']', buttonText:'选择图片', accept:'image/jpeg,image/peg, image/png',prompt:'图片尺寸:420x285'">
							<a id="btn_pic" class="easyui-linkbutton" data-options="plain:true," iconCls="icon-clear" style="width:9%;height:32px;">清除</a>
		    			</div>
		    		 </div>
		    		<div class="row">
		    			<div>能否预定:</div>
		    			<div>
		    				<select class="easyui-combobox radio" name="vencanbk" data-options="panelHeight:'auto',editable:false,required:true" style="width:100%;height:32px;">
			    					<option value="1">可预订</option>
			    					<option value="0">不可预定</option>
							</select>
		    			</div>
		    		 </div>
		    		<div class="row">
		    			<div>是否收费:</div>
		    			<div>
		    				<select class="easyui-combobox radio" name="ismoney" data-options="panelHeight:'auto',editable:false,required:true" style="width:100%;height:32px;">
			    					<option value="1">是</option>
			    					<option value="0">否</option>
							</select>
		    			</div>
		    		 </div>
		    		<div class="row">
		    			<div>场馆联系方式:</div>
		    			<div>
							<input id="vencontactnum" class="easyui-textbox" name="vencontactnum" style="width:100%;height:32px;" data-options="validType:'isPhone[\'vencontactnum\']'"/>
		    			</div>
		    		 </div>
		    		<div class="row">
		    			<div>场馆联系人:</div>
		    			<div>
		    				<input id="vencontact" class="easyui-textbox" name="vencontact" style="width:100%;height:32px;" data-options="required:true,validType:'length[0,10]',"/>
		    			</div>
		    		 </div>
		    		 <div class="row">
		    			<div>场馆用途：</div>
			    		<div>
			    			<input id="venscope" class="easyui-combobox" name="venscope" style="width:100%;height:32px;" data-options="panelHeight:'auto',editable:false, valueField:'id',textField:'name',url:'${basePath}/comm/whtag?type=2016101400000047', multiple:true"/>
			    		</div>
		    		 </div>
		    		 <div class="row">
		    			<div>场馆地址:</div>
		    			<div>
		    				<input id="venaddr" class="easyui-textbox" name="venaddr" style="width:100%;height:32px;" data-options="required:true"/>
		    			</div>
		    		</div>
		    		<div class="row">
		    			<div>场馆简介:</div>
		    			<div>
		    				<input id="venintro" class="easyui-textbox" name="venintro" style="width:100%;height:50px;" data-options="required:true,validType:'length[0,400]',multiline:true"/>
		    			</div>
		    		</div>
		    		<div class="row">
		    			<div>场馆申请条件:</div>
		    			<div>
		    				<input id="vencondition" name="vencondition" class="easyui-textbox" data-options="validType:'length[0,500]',multiline:true" style="height: 80px; width: 100%"></input>
		    			</div>
		    		</div>
		    		<div class="row">
		    			<div>场馆描述：</div>
			    		<div>
							<script id="catalog" type="text/plain" style="width:100%; height:300px;"></script>
			    		</div>
		    		</div>
		    		<div class="row">
		    			<div>设施描述:</div>
		    			<div>
		    				<script id="traeditor" type="text/plain" style="width:100%; height:300px;"></script>
		    			</div>
		    		</div>
				</div>
			</form>
		</div>	
		
		<!--管理资源dialog  -->
		<div id="addzy" style="display:none">
			<iframe  style="width:100%; height:100%;border:0px"></iframe>
		</div>
		
		
		
		<!--培训资源dialog  -->
		<div id="destine" style="display:none">
			<iframe  style="width:100%; height:100%"></iframe>
		</div>
		
		<!-- 课程表dialog -->
		<div id="shiduan" style="display:none">
			<iframe  style="width:100%; height:100%;border:0px"></iframe>
		</div>
		
		
</body>
</html>