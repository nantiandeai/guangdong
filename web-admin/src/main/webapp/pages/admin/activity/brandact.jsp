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
var winform = new WhuiWinForm("#brand_edit",{height:250});

/**
 * 初始品牌活动表格
 */
$(function(){
	//定义表格参数
	var options = {
			title: '品牌活动管理',	
			url: getFullUrl('/admin/brand/findBrand'),
			rownumbers:true,
			singleSelect:false,
			columns: [[
			{field:'ck',checkbox:true},
			{field:'bratitle',title:'品牌活动标题',width:80},
			{field:'brashorttitle',title:'品牌活动短标题',width:80},
			{field:'braintroduce',title:'品牌简介',width:80},
			{field:'brapic',title:'品牌图片',width:80, formatter:imgFMT},    
			{field:'brabigpic',title:'品牌详情背景图',width:80,formatter:imgFMT},
		//	{field:'bradetail',title:'现场介绍',width:80,},
	        {field:'brastate',title:'状态',width:80,formatter :traStateFMT},
	        {field:'opt', title:'操作', formatter: WHdg.optFMT, optDivId:'brandOPT'}
			]]
	};
	
	$("#brandTool").css('visibility','visible');
	//初始表格
	WHdg.init('brand', 'brandTool', options);
	
	//初始弹出框
	winform.init();
	//winform1.init();
	
	//初始富文本
	UE.getEditor('traeditor').ready(function(){
		window.init_UE = true;
	});
})

//处理编辑时显示图片
function _showImg(data){
	$(".img_div").remove();
	var imgDiv = '<div class="row img_div">'
		+'<div></div>'
		+'<div><img width="200" height="150"> </div> '
		+'</div>';
		
	if (data.brapic){
		var imgrow = $("[name$='brapic_up']").parents(".row");
		imgrow.after(imgDiv);
		imgrow.next(".img_div").find("div img").attr("src",getFullUrl(data.brapic));	
	}
	if (data.brabigpic){
		var imgrow = $("[name$='brabigpic_up']").parents(".row");
		imgrow.after(imgDiv);
		imgrow.next(".img_div").find("div img").attr("src",getFullUrl(data.brabigpic));	
	}
}
//处理编辑时显示图片
/* function _showImg1(data){
	$(".img_div1").remove();
	var imgDiv1 = '<div class="row img_div1">'
		+'<div></div>'
		+'<div><img width="200" height="150"> </div> '
		+'</div>';
		
	if (data.brapic){
		var imgrow = $("[name$='brapic_up1']").parents(".row");
		imgrow.after(imgDiv1);
		imgrow.next(".img_div1").find("div img").attr("src",getFullUrl(data.brapic));	
	}
	if (data.brabigpic){
		var imgrow = $("[name$='brabigpic_up1']").parents(".row");
		imgrow.after(imgDiv1);
		imgrow.next(".img_div1").find("div img").attr("src",getFullUrl(data.brabigpic));	
	}
} */

/**
 * 添加品牌活动
 */
function addBrand(){
	if(typeof window.init_UE == 'undefined'){
		 $.messager.progress();
		 function _fn(){
			 if(typeof window.init_UE == 'undefined'){
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
	UE.getEditor('traeditor').setContent("");
	winform.openWin();
	winform.setWinTitle("添加品牌活动");
	winform.getWinFooter().find("a:eq(0)").show();
	$(".img_div").remove();
	$("#ff").form('clear');
	var frm = winform.getWinBody().find('form').form({
		url : getFullUrl('/admin/brand/save'),
		onSubmit : function(param){
			//设置富文本的内容
			$('#bradetail').val(UE.getEditor('traeditor').getContent());
			
            var _is = $(this).form('enableValidation').form('validate');
            if (!_is) {
            	winform.oneClick(function(){ frm.submit(); });
			}
            return _is;
        },
		success : function(data) {
			var Json = $.parseJSON(data);
			if(Json && Json.success == "0"){
				$('#brand').datagrid('reload');
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
}
/** 修改*/
function editBrand(index){
	if(typeof window.init_UE == 'undefined'){
		 $.messager.progress();
		 function _fn(){
			 if(typeof window.init_UE == 'undefined'){
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
	//alert(JSON.stringify(row.enroldesc));
	winform.openWin();
	winform.setWinTitle("修改品牌活动");
	winform.getWinFooter().find("a:eq(0)").show();
	//显示富文本的值
	UE.getEditor('traeditor').setContent(row.bradetail);
	_showImg(row);
	var frm = winform.getWinBody().find('form').form({
		url : getFullUrl('/admin/brand/save'),
		onSubmit : function(param) {
			//设置富文本的内容
			$('#bradetail').val(UE.getEditor('traeditor').getContent());
			var _is = $(this).form('enableValidation').form('validate');
			if (!_is) {
				winform.oneClick(function(){ frm.submit(); });
			}
			return _is;
		},
		success : function(data) {
			var Json = $.parseJSON(data);
	 			if(Json && Json.success == '0'){
	 			$.messager.alert('提示', '操作成功！');
				$('#brand').datagrid('reload');
				winform.closeWin();
			} else {
				$.messager.alert('提示', '修改失败,操作无效！');
				winform.oneClick(function(){ frm.submit(); });
			}
		}
	});
	//格式化日期
	var _data = $.extend({}, row,
			{
		brasdate : datetimeFMT(row.brasdate),
		braedate : datetimeFMT(row.braedate),
	});
	frm.form("clear");
	frm.form("load", _data);
	winform.oneClick(function(){ frm.submit(); });
	UE.getEditor('traeditor');
}

/**删除品牌*/
function delBrand(index){
	var row = WHdg.getRowData(index);
	var braid = row.braid;
	$.messager.confirm('确认对话框', '您确认要删除吗？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/brand/delBrand'),
				data: {braid : braid},
				success: function(data){
				//	alert(JSON.stringify(data));
					if(data.success == '0'){
						$('#brand').datagrid('reload');
					   }else{
						   $.messager.alert('提示', '操作失败。原因：'+data.errmsg, 'error');
					   }
				}
			});
		}
	});
}

/**查看详情*/
function look(index){
	var row = WHdg.getRowData(index);
	winform.openWin();
	winform.setWinTitle("查看详情");
	winform.getWinFooter().find("a:eq(0)").hide();
	//显示富文本的值
	UE.getEditor('traeditor').setContent(row.bradetail);
	$('#look_ff').find("input").attr("readonly","true");
	_showImg(row);
	var frm = winform.getWinBody().find('form').form({
	});
	frm.form("clear");
	//格式化日期
	var _data = $.extend({}, row,
			{
		brasdate : datetimeFMT(row.brasdate),
		braedate : datetimeFMT(row.braedate),
	});
	frm.form("load", _data);
	$('#brand').datagrid('reload');
}
/**审核*/
function sendCheck(index){
	var row = WHdg.getRowData(index);
	var braid= row.braid;
	var brastate = row.brastate;
	$.messager.confirm('确认对话框', '您确认审核所选的数据吗？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/brand/check'),
				data: {braid : braid,brastate: brastate},
				success: function(data){
					if(data.success == '0'){
						$('#brand').datagrid('reload');
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
	var braid= row.braid;
	var brastate = row.brastate;
	$.messager.confirm('确认对话框', '您确认发布所选的数据吗？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/brand/check'),
				data: {braid : braid,brastate: brastate},
				success: function(data){
					if(data.success == '0'){
						$('#brand').datagrid('reload');
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
	var braid= row.braid;
	var brastate = row.brastate;
	$.messager.confirm('确认对话框', '您确认将所选的数据打回未审核吗？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/brand/back'),
				data: {braid : braid,brastate: brastate},
				success: function(data){
					if(data.success == '0'){
						$('#brand').datagrid('reload');
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
	var braid= row.braid;
	var brastate = row.brastate;
	$.messager.confirm('确认对话框', '您确认将所选的数据取消发布吗？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/brand/back'),
				data: {braid : braid,brastate: brastate},
				success: function(data){
					if(data.success == '0'){
						
						$('#brand').datagrid('reload');
						$.messager.alert('提示', ''+data.msg);
					   }else{
						   $.messager.alert('提示', '操作失败。原因：'+data.msg, 'error');
					   }
				}
			});
		}
	});
}
/** 判断是否能够进行审核*/
function _validFun(index){
	var row = WHdg.getRowData(index);
	return row.brastate == '0' || row.brastate == '1';
}
/**
 * 一键审核
 */
function checkAll(){
	var rows={};
	rows = $("#brand").datagrid("getSelections");
	if (rows == "" || rows == null) {
		$.messager.alert('提示', '请选择要操作的数据！');
		return;
	}
	var braids = _split = "";//id1,id2,id3
	for (var i = 0; i<rows.length; i++){
		braids += _split+rows[i].braid;
		_split = ",";
	}
	//trastate = rows[0].trastate;
	$.messager.confirm('确认对话框', '您确认将所选择的进行审核吗？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/brand/checkBrand'),
				data: {braid : braids,fromstate: 0, tostate:2},
				success: function(data){
					if(data.success == '0'){
						$('#brand').datagrid('reload');
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
	rows = $("#brand").datagrid("getSelections");
	if (rows == "" || rows == null) {
		$.messager.alert('提示', '请选择要操作的数据！');
		return;
	}
	var braids = _split = "";//id1,id2,id3
	for (var i = 0; i<rows.length; i++){
		braids += _split+rows[i].braid;
		_split = ",";
	}
	//trastate = rows[0].trastate;
	$.messager.confirm('确认对话框', '您确认将所选择的进行发布吗？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/brand/checkBrand'),
				data: {braid : braids,fromstate: 2, tostate:3},
				success: function(data){
					if(data.success == '0'){
						$('#brand').datagrid('reload');
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
function noAllPublish(){
	var rows={};
	rows = $("#brand").datagrid("getSelections");
	if (rows == "" || rows == null) {
		$.messager.alert('提示', '请选择要操作的数据！');
		return;
	}
	var braids = _split = "";//id1,id2,id3
	for (var i = 0; i<rows.length; i++){
		braids += _split+rows[i].braid;
		_split = ",";
	}
	//trastate = rows[0].trastate;
	$.messager.confirm('确认对话框', '您确认将所选择的取消发布吗？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/brand/checkBrand'),
				data: {braid : braids,fromstate: 3, tostate:2},
				success: function(data){
					if(data.success == '0'){
						$('#brand').datagrid('reload');
						$.messager.alert('提示', ''+data.msg);
					   }else{
						   $.messager.alert('提示', '操作失败。原因：'+data.msg, 'error');
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
	rows = $("#brand").datagrid("getSelections");
	if (rows == "" || rows == null) {
		$.messager.alert('提示', '请选择要操作的数据！');
		return;
	}
	var braids = _split = "";//id1,id2,id3
	for (var i = 0; i<rows.length; i++){
		braids += _split+rows[i].braid;
		_split = ",";
	}
	//trastate = rows[0].trastate;
	$.messager.confirm('确认对话框', '您确认将所选择的打回审核吗？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/brand/checkBrand'),
				data: {braid : braids,fromstate: 2, tostate:1},
				success: function(data){
					if(data.success == '0'){
						$('#brand').datagrid('reload');
					   }else{
						   $.messager.alert('提示', '操作失败。原因：'+data.errmsg, 'error');
					   }
				}
			});
		}
	});
}
/**
 * 资源管理
 * @param index
 * @returns
 */
function addzy(index){
	var rows = $('#brand').datagrid('getRows');
	var row = rows[index];//WHdg2.getRowData(index);
	var braid = row.braid;
	
	$('#addzy iframe').attr('src', getFullUrl("/admin/ent/entsrc?reftype=2016103100000001&refid="+braid+"&canEdit=true"));
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
 * 活动管理
 */
function addact(index){
	var rows = $('#brand').datagrid('getRows');
	var row = rows[index];//WHdg2.getRowData(index);
	var braid = row.braid;
	
	$('#addact iframe').attr('src', getFullUrl("/admin/brand/showBrandAct?braid="+braid));
	$('#addact').dialog({    
	    title: '活动管理',  
	    modal:true,
	    maximizable: true,
	    maximized: true,
	    width: 400,
	    height: 200   
	}); 
}

</script>
</head>
<body>
	<!-- 培训管理页面 -->
	<table id="brand"></table>
	
	<!-- 查询栏 -->
	<div id="brandTool" class="grid-control-group" style="display: none;" >
		<div>
			<shiro:hasPermission name="${resourceid}:add"><a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" size='small' id="btn" onclick="addBrand()">添加</a></shiro:hasPermission>
			<shiro:hasPermission name="${resourceid}:checkon"><a href="javascript:void(0)" class="easyui-linkbutton" size='small' onclick="checkAll()">批量审核</a></shiro:hasPermission>
			<shiro:hasPermission name="${resourceid}:checkoff"><a href="javascript:void(0)" class="easyui-linkbutton" size='small' onclick="goallCheck()">批量打回</a></shiro:hasPermission> 
			<shiro:hasPermission name="${resourceid}:publish"><a href="javascript:void(0)" class="easyui-linkbutton" size='small' onclick="publishAll()">批量发布</a></shiro:hasPermission> 
			<shiro:hasPermission name="${resourceid}:publishoff"><a href="javascript:void(0)" class="easyui-linkbutton" size='small' onclick="noAllPublish()">批量取消发布</a></shiro:hasPermission> 
			
		</div>
		<div style="padding-top: 5px">
			品牌活动:
			<input class="easyui-textbox" name="bratitle" data-options="validType:'length[1,30]'"/>
			状态:
			<select class="easyui-combobox" name="brastate" >
				<option value="">全部</option>
				<option value="0">未审核</option>
				<option value="2">已审核</option>
				<option value="3">已发布</option>
			</select>
			<a href="javascript:void(0)" class="easyui-linkbutton search" iconCls="icon-search">查询</a>
		</div>
		
	</div>
		      
	<!-- 操作按钮 -->
	<div id="brandOPT" style="display:none">
		<shiro:hasPermission name="${resourceid}:edit"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validFun="_validFun" method="editBrand">修改</a></shiro:hasPermission> 
		<shiro:hasPermission name="${resourceid}:del"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" method="delBrand">删除</a></shiro:hasPermission>
		<shiro:hasPermission name="${resourceid}:checkon"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validFun="_validFun" method="sendCheck">审核</a></shiro:hasPermission>
		<shiro:hasPermission name="${resourceid}:checkoff"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="brastate" validVal="2" method="nocheck">打回审核</a></shiro:hasPermission>
		<shiro:hasPermission name="${resourceid}:publish"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="brastate" validVal="2" method="publish">发布</a></shiro:hasPermission>
		<shiro:hasPermission name="${resourceid}:publishoff"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="brastate" validVal="3" method="noPublish">取消发布</a></shiro:hasPermission>
		<shiro:hasPermission name="${resourceid}:view"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true"  method="look">查看详情</a></shiro:hasPermission>
		<shiro:hasPermission name="${resourceid}:edit">
		<shiro:hasPermission name="${resourceid}:view"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="brastate" validVal="1" method="addact">活动管理</a></shiro:hasPermission>   
		<shiro:hasPermission name="${resourceid}:view"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="brastate" validVal="1" method="addzy">资源管理</a></shiro:hasPermission>   
		</shiro:hasPermission>
	</div> 
				
	 <!-- 品牌活动dialog -->
		 
	 <div id="brand_edit" class="none" style="display:none"  data-options=" fit:true" >
		 <form id="brand_ff" method="post" enctype="multipart/form-data" >
		 	<input type="hidden" id="braid" name="braid" value="">
		 	<input type="hidden" id="bradetail" name="bradetail" value="">
		 	<input type="hidden" id="brapic" name="brapic" value="">
		 	<input type="hidden" id="brabigpic" name="brabigpic" value="">
	    		<div class="row">
	    			<div>品牌活动标题:</div>
	    			<div>
						<input id="bratitle" class="easyui-textbox" name="bratitle" style="width:80%;height:32px;" data-options="required:true,validType:'length[1,20]'"/>
	    			</div>
	    		 </div>
	    		<div class="row">
	    			<div>短标题:</div>
	    			<div>
	    				<input id="brashorttitle" class="easyui-textbox" name="brashorttitle" style="width:80%;height:32px;" data-options="required:true,validType:'length[1,30]'"/>
	    			</div>
	    		</div>
	    		 <div class="row">
	    			<div>品牌图片:</div>
		    		<div>
						<input id="brapic_up" class="easyui-filebox" name="brapic_up" style="width:80%;height:32px;" data-options="editable:false,validType:'isIMG[\'brapic_up\']', buttonText:'选择图片', accept:'image/jpeg,image/peg, image/png',prompt:'图片尺寸:300x190'"/>
		    		</div>
	    		 </div>
	    		 <div class="row">
	    			<div>品牌详情背景图:</div>
		    		<div>
						<input id="brabigpic_up" class="easyui-filebox" name="brabigpic_up" style="width:80%;height:32px;" data-options="editable:false,validType:'isIMG[\'brabigpic_up\']', buttonText:'选择图片', accept:'image/jpeg,image/peg, image/png',prompt:'图片尺寸:1903x2410'"/>
		    		</div>
	    		 </div>
	    		 <div class="row">
		    		<div>品牌活动开始时间:</div>
	    			<div>
	    				<input id="brasdate" name="brasdate" type="text" data-options="editable:false,required:true" class="easyui-datetimebox"  style="height: 32px; width: 80%"></input>
	    			</div>
		    	</div>
	    		<div class="row">
	    			<div>品牌活动结束时间:</div>
	    			<div>
	    				<input id="braedate" name="braedate" type="text" data-options="editable:false,required:true" class="easyui-datetimebox"  style="height: 32px; width: 80%"></input>
	    			</div>
	    		</div>
	    		 <div class="row">
	    			<div>品牌简介:</div>
	    			<div>
	    				<input id="braintroduce" class="easyui-textbox" name="braintroduce" style="width:80%;height:60px;" data-options="multiline:true,validType:'length[1,400]'"/>
	    			</div>
	    		</div>
	    		<div class="row">
	    			<div>品牌活动的现场介绍:</div>
	    			<div>
	    				<script id="traeditor" type="text/plain" style="width:90%; height:300px;"></script>
	    			</div>
	    		</div>
	    		
			</div>
		</form>
	</div>
	<!-- 查看详情 -->	
	<!--  <div id="look" class="none" style="display:none"  data-options=" fit:true" >
		 <form id="look_ff" method="post" enctype="multipart/form-data" >
		 	<input type="hidden" id="braid" name="braid" value="">
		 	<input type="hidden" id="bradetail" name="bradetail" value="">
		 	<input type="hidden" id="brapic" name="brapic" value="">
		 	<input type="hidden" id="brabigpic" name="brabigpic" value="">
	    		<div class="row">
	    			<div>品牌活动标题:</div>
	    			<div>
						<input id="bratitle1" class="easyui-textbox" name="bratitle" style="width:80%;height:32px;" data-options="required:true,validType:'length[1,30]'"/>
	    			</div>
	    		 </div>
	    		<div class="row">
	    			<div>短标题:</div>
	    			<div>
	    				<input id="brashorttitle1" class="easyui-textbox" name="brashorttitle" style="width:80%;height:32px;" data-options="required:true,validType:'length[1,30]'"/>
	    			</div>
	    		</div>
	    		 <div class="row">
	    			<div>品牌图片:</div>
		    		<div>
						<input id="brapic_up1" class="easyui-filebox" name="brapic_up1" style="width:80%;height:32px;" data-options="editable:false"/>
		    		</div>
	    		 </div>
	    		 <div class="row">
	    			<div>品牌详情背景图:</div>
		    		<div>
						<input id="brabigpic_up1" class="easyui-filebox" name="brabigpic_up1" style="width:80%;height:32px;" data-options=""/>
		    		</div>
	    		 </div>
	    		 <div class="row">
		    		<div>品牌活动开始时间:</div>
	    			<div>
	    				<input id="brasdate1" name="brasdate" type="text" data-options="editable:false,required:true" class="easyui-datetimebox"  style="height: 35px; width: 100%"></input>
	    			</div>
		    	</div>
	    		<div class="row">
	    			<div>品牌活动结束时间:</div>
	    			<div>
	    				<input id="braedate1" name="braedate" type="text" data-options="editable:false,required:true" class="easyui-datetimebox"  style="height: 35px; width: 100%"></input>
	    			</div>
	    		</div>
	    		 <div class="row">
	    			<div>品牌简介:</div>
	    			<div>
	    				<input id="braintroduce1" class="easyui-textbox" name="braintroduce" style="width:80%;height:60px;" data-options="multiline:true,validType:'length[1,200]'"/>
	    			</div>
	    		</div>
	    		<div class="row">
	    			<div>品牌活动的现场介绍:</div>
	    			<div>
	    				<script id="traeditor1" type="text/plain" style="width:90%; height:300px;"></script>
	    			</div>
	    		</div>
	    		
			</div>
		</form>
	</div>	 -->
		
		
	<!--资源管理dialog  -->
	<div id="addzy" style="display:none">
		<iframe  style="width:100%; height:100%"></iframe>
	</div>
		
	<!--活动管理dialog  -->
	<div id="addact" style="display:none">
		<iframe  style="width:100%; height:100%"></iframe>
	</div>
</body>
</html>