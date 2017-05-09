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
var winform = new WhuiWinForm("#zyzz_edit",{height:250});

$(function(){
	$("#zyxmTd").css('visibility','visible');
})

/**审核*/
function check(index){
	var row = WHdg.getRowData(index);
	var zyfcxmid = row.zyfcxmid;
	var title = "审核";
	docheck(title,zyfcxmid,0,2);
}

/**发布*/
function publish(index){
	var row = WHdg.getRowData(index);
	var zyfcxmid = row.zyfcxmid;
	var title = "发布";
	docheck(title,zyfcxmid,2,3);
}

/**打回审核*/
function nocheck(index){
	var row = WHdg.getRowData(index);
	var zyfcxmid = row.zyfcxmid;
	var title = "打回";
	docheck(title,zyfcxmid,2,0);
}

/**取消发布*/
function nopublish(index){
	var row = WHdg.getRowData(index);
	var zyfcxmid = row.zyfcxmid;
	var title = "取消发布";
	docheck(title,zyfcxmid,3,2);
}

/** 审核事件提交处理 */
function docheck(title,zyfcxmid,fromstate,tostate){
	$.messager.confirm('确认对话框', '您确认将所选择的'+title+'吗？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/volun/checkproj'),
				data: {zyfcxmid : zyfcxmid,fromstate:fromstate,tostate:tostate},
				success: function(data){
					if(data.success == 'success'){
						$('#zyxmDg').datagrid('reload');
//						$.messager.alert('提示', '操作成功');
					   }else{
						   $.messager.alert('提示', '操作失败。原因：'+data.errmsg, 'error');
					   }
				}
			});
		}
	});
}

/**批量审核*/
function allcheck(){
	var rows={};
	rows = $("#zyxmDg").datagrid("getSelections");
	if (rows == "" || rows == null) {
		$.messager.alert('提示', '请选择要操作的数据！');
		return;
	}
	var zyfcxmids = _split = "";
	for (var i = 0; i<rows.length; i++){
		if (rows[i].zyfcxmstate == 0) {
			zyfcxmids += _split+rows[i].zyfcxmid;
			_split = ",";
		}
		
	}
	if (!zyfcxmids){
		$.messager.alert('提示', '没有匹配当前操作的选择记录');
		return false;
	}
	subcheck(zyfcxmids,0,2);
}

/**批量发布*/
function allpublish(){
	var rows={};
	rows = $("#zyxmDg").datagrid("getSelections");
	if (rows == "" || rows == null) {
		$.messager.alert('提示', '请选择要操作的数据！');
		return;
	}
	var zyfcxmids = _split = "";
	for (var i = 0; i<rows.length; i++){
		if (rows[i].zyfcxmstate == 2) {
			zyfcxmids += _split+rows[i].zyfcxmid;
			_split = ",";
		}
		
	}
	if (!zyfcxmids){
		$.messager.alert('提示', '没有匹配当前操作的选择记录');
		return false;
	}
	subcheck(zyfcxmids,2,3);
}
/**批量打回*/
function allback(){
	var rows={};
	rows = $("#zyxmDg").datagrid("getSelections");
	if (rows == "" || rows == null) {
		$.messager.alert('提示', '请选择要操作的数据！');
		return;
	}
	var zyfcxmids = _split = "";
	for (var i = 0; i<rows.length; i++){
		if (rows[i].zyfcxmstate == 2) {
			zyfcxmids += _split+rows[i].zyfcxmid;
			_split = ",";
		}
		
	}
	if (!zyfcxmids){
		$.messager.alert('提示', '没有匹配当前操作的选择记录');
		return false;
	}
	subcheck(zyfcxmids,2,0);
}
/**批量取消发布*/
function allnoPublish(){
	var rows={};
	rows = $("#zyxmDg").datagrid("getSelections");
	if (rows == "" || rows == null) {
		$.messager.alert('提示', '请选择要操作的数据！');
		return;
	}
	var zyfcxmids = _split = "";
	for (var i = 0; i<rows.length; i++){
		if (rows[i].zyfcxmstate == 3) {
			zyfcxmids += _split+rows[i].zyfcxmid;
			_split = ",";
		}
		
	}
	if (!zyfcxmids){
		$.messager.alert('提示', '没有匹配当前操作的选择记录');
		return false;
	}
	subcheck(zyfcxmids,3,2);
}

/**
 * 一键审核提交
 */
function subcheck(zyfcxmids,fromstate,tostate){
	$.messager.confirm('确认对话框', '您确认将所选择的通过审核吗？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/volun/Allproj'),
				data: {zyfcxmids : zyfcxmids,fromstate: fromstate, tostate:tostate },
				success: function(data){
					if(data.success=="success"){
						$.messager.alert("提示", '操作成功');
						$('#zyxmDg').datagrid('reload');
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
	
	var frm = winform.getWinBody().find('form').form({
	});
	//显示富文本的值
	UE.getEditor('traeditor').setContent(row.zyfczzcontent);
	frm.form("clear");
	//格式化日期
		//格式化日期
 	row.zyfczzfwtime = datetimeFMT(row.zyfczzfwtime);
	frm.form("load", row);
	_showImg(row);
	$('#zyxmDg').datagrid('reload');
}



/**
 * 添加项目示范
 */
 function addzyxm(){
	//设置延时如果文本框没加载完就不打开编辑页面
	 if(typeof window.init_UE == 'undefined'){
		 $.messager.progress();
		 function _fn(){
			 if(typeof window.init_UE == 'undefined'){
				 window.setTimeout(function(){
					 _fn();
				 }, 200);
			 }else{
				 $.messager.progress('close');
				 addzyzz();
			 }
		 }
		 _fn();
		 return;
	 }
	//打开页面设置页面名称
	winform.openWin();
	winform.setWinTitle("添加项目示范");
	//显示提交按钮
	winform.getWinFooter().find("a:eq(0)").show();
	//定义表单
	var frm = winform.getWinBody().find('form').form({
		url : getFullUrl('/admin/volun/addpro'),
		onSubmit : function(param){
			param.zyfcxmstate = 0
			//设置富文本的内容
			$('#zyfcxmcontent').val(UE.getEditor('traeditor').getContent());
			//表单验证
            var _is = $(this).form('enableValidation').form('validate');
			//验证不通过 注册一次提交事件
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
				$('#zyxmDg').datagrid('reload');
				$.messager.alert('提示', '操作成功!');
				winform.closeWin();
			   }else{
				   $.messager.alert('提示', '操作失败!');
				   //注册一次提交事件
				   winform.oneClick(function(){ frm.submit(); });
			   }
		}
	});
	//清空表单
	frm.form("clear");
	//删除图片展示层
 	$(".img_div").remove();
	//清空富文本
	UE.getEditor('traeditor').setContent("");
	//提交事件一次有效
	winform.oneClick(function(){ frm.submit(); });
	
}

/**
 * 编辑项目示范
 */
 function editzyxm(index){
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
	//得到要求编辑的数据
 	var row = WHdg.getRowData(index);
	//打开编辑页面设置页面名字 
 	winform.openWin();
 	winform.setWinTitle("修改项目示范");
 	//显示提交按钮
 	winform.getWinFooter().find("a:eq(0)").show();
 	//定义表单
 	var frm = winform.getWinBody().find('form').form({
 		url : getFullUrl('/admin/volun/upproj'),
 		onSubmit : function(param){
 			//得到当前修改的id和图片路径
 			param.zyfcxmid = row.zyfcxmid;
 			param.zyfcxmpic = row.zyfcxmpic;
 			param.zyfcxmbigpic = row.zyfcxmbigpic;
 			//设置富文本的内容
 			$('#zyfcxmcontent').val(UE.getEditor('traeditor').getContent());
 			//表单验证
             var _is = $(this).form('enableValidation').form('validate');
             //验证不通过 注册一次提交事件
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
 				$('#zyxmDg').datagrid('reload');
 				$.messager.alert('提示', '操作成功!');
 				winform.closeWin();
 			   }else{
 				   $.messager.alert('提示', '操作失败!');
 				   winform.oneClick(function(){ frm.submit(); });
 			   }
 		}
 	});
 	frm.form("clear");
 	//格式化日期
 	row.zyfcxmsstime = datetimeFMT(row.zyfcxmsstime);
 	//读取表单数据
 	frm.form('load',row);
 	//处理编辑时显示图片
 	_showImg(row);
 	//显示富文本的值
 	UE.getEditor('traeditor').setContent(row.zyfcxmcontent);
 	winform.oneClick(function(){ frm.submit(); });
 }
 
//处理编辑时显示图片
 function _showImg(data){
	//删除
 	$(".img_div").remove();
 	//创建显示层
 	var imgDiv = '<div class="row img_div">'
 		+'<div></div>'
 		+'<div><img width="200" height="150"> </div> '
 		+'</div>';
 	//判断图片不为空显示	
 	if (data.zyfcxmpic){ 
 		var imgrow = $("[name$='zyfcxmpic_up']").parents(".row");
 		imgrow.after(imgDiv);
 		imgrow.next(".img_div").find("div img").attr("src",getFullUrl(data.zyfcxmpic));	
 	}
 	//判断图片不为空显示	
 	if (data.zyfcxmbigpic){
 		var imgrow = $("[name$='zyfcxmbigpic_up']").parents(".row");
 		imgrow.after(imgDiv);
 		imgrow.next(".img_div").find("div img").attr("src",getFullUrl(data.zyfcxmbigpic));	
 	}
 }
 
 /**
  * 删除优秀组织
  */
 function delzyzz(index){
	var row = WHdg.getRowData(index);
	var zyfcxmid = row.zyfcxmid;
	var zyfcxmpic = row.zyfcxmpic;
	var	zyfcxmbigpic = row.zyfcxmbigpic;
	$.messager.confirm('确认对话框', '您确认要删除吗？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/volun/delproj'),
				data: {zyfcxmid : zyfcxmid,zyfcxmpic:zyfcxmpic,zyfcxmbigpic:zyfcxmbigpic},
				success: function(data){
					if(data.success == '0'){
						$('#zyxmDg').datagrid('reload');
					   }else{
						   $.messager.alert('提示', '操作失败。原因：'+data.errmsg, 'error');
					   }
				}
			});
		}
	});
}
 
 /**
  * 初始项目示范表格
  */
 $(function(){
 	//定义表格参数
	var options = {
			title: '项目示范',	
			url: getFullUrl('/admin/volun/findpro'),
			rownumbers:true,
			singleSelect:false,
			columns: [[
			{field:'ck',checkbox:true},
			//{field:'zyfcxmid',title:'主键标识', sortable:true},
//			{field:'zyfcxmtitle',width:100,title:'详情标题'},
			{field:'zyfcxmshorttitle',width:100,title:'列表短标题', sortable:true},
			{field:'zyfcxmpic',width:100,title:'列表图片',formatter:imgFMT},
//			{field:'zyfcxmbigpic',width:100,title:'详情图片',formatter:imgFMT},
			{field:'zyfcxmpnum',width:100,title:'志愿者数量',sortable:true},
			{field:'zyfcxmanum',width:100,title:'服务人数量',sortable:true},
			//{field:'zyfcxmscope',title:'服务地区'},
	        {field:'zyfcxmssdw',width:100,title:'实施单位'},
	        {field:'zyfcxmsstime',width:100,title:'实施时间',sortable:true,formatter:datetimeFMT},
	        //{field:'zyfcxmcontent',title:'项目实施内容'},
	       	//{field:'zyfcxmopttime',title:'修改状态时间',width:80,formatter:datetimeFMT},
	        {field:'zyfcxmstate',width:100,title:'状态',formatter :traStateFMT},
	        //{field:'zyfcxvenueid',title:'所属文化馆标识'},
	        {field:'opt', title:'操作', formatter: WHdg.optFMT, optDivId:'zyxmOPT'}

			]]
	};

	//初始表格
	WHdg.init('zyxmDg', 'zyxmTd', options);
	
	//初始弹出框
	winform.init();
	
	//初始富文本
	UE.getEditor('traeditor').ready(function(){
		window.init_UE = true;
	});
	
	//清除文件框的值
	$('#btn_pic').bind('click', function(){  
		$("#zyfcxmpic_up").filebox('clear');
    });    
	$('#btn_pic1').bind('click', function(){  
		$("#zyfcxmbigpic_up").filebox('clear');
	});   
	
 })
 


/**
 * 添加资源
 * @param index
 * @returns
 */
//function addzy(index){
//	var rows = $('#zyxmDg').datagrid('getRows');
//	var row = rows[index];//WHdg2.getRowData(index);
//	var zyfcxmid = row.zyfcxmid;
//
//	$('#addzy iframe').attr('src', getFullUrl("/admin/ent/entsrc?reftype=2016112900000015&refid="+zyfcxmid+"&canEdit=true"));
//	$('#addzy').dialog({
//	    title: '添加资源',
//	    modal:true,
//	    maximizable: true,
//	    maximized: true,
//	    width: 400,
//	    height: 200
//	});
//}

/**
 * 资源管理
 * @param idx
 */
function addzy(idx) {
	var row = $("#zyxmDg").datagrid("getRows")[idx];
	WhgComm.editDialog('${basePath}/admin/train/resource/view/list?reftype=9&id=' + row.zyfcxmid);
}
</script>
</head>
<body>
	<!-- 项目展示页面 -->
	<table id="zyxmDg"></table>
	
	<!-- 查询栏 -->
	<div id="zyxmTd" class="grid-control-group" style="display: none;" >
		<div style="line-height: 32px;">
			<shiro:hasPermission name="${resourceid}:add"><a href="#" class="easyui-linkbutton" iconCls="icon-add" size='small' id="btn" onclick="addzyxm();">添加</a></shiro:hasPermission>
			<shiro:hasPermission name="${resourceid}:checkon"><a href="javascript:void(0)" class="easyui-linkbutton" size='small' onclick="allcheck()">批量审核</a></shiro:hasPermission>
			<shiro:hasPermission name="${resourceid}:checkoff"><a href="javascript:void(0)" class="easyui-linkbutton" size='small' onclick="allback()">批量打回</a></shiro:hasPermission> 
			<shiro:hasPermission name="${resourceid}:publish"><a href="javascript:void(0)" class="easyui-linkbutton" size='small' onclick="allpublish()">批量发布</a></shiro:hasPermission> 
			<shiro:hasPermission name="${resourceid}:publishoff"><a href="javascript:void(0)" class="easyui-linkbutton" size='small' onclick="allnoPublish()">批量取消发布</a></shiro:hasPermission> 
			
		</div>
		<div style="padding-top: 5px">
			<%--状态:--%>
			<select class="easyui-combobox" name="zyfcxmstate" style="width: 200px; height:28px" >
				<option value="">请选择状态</option>
				<option value="0">未审核</option>
				<option value="2">已审核</option>
				<option value="3">已发布</option>
			</select>
			<a href="javascript:void(0)" class="easyui-linkbutton search" iconCls="icon-search">查询</a>
		</div>
		
	</div>
	      
	<!-- 操作按钮 -->
	<div id="zyxmOPT" style="display:none">
		<shiro:hasPermission name="${resourceid}:view"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true"  method="look">查看详情</a></shiro:hasPermission>
		<shiro:hasPermission name="${resourceid}:edit"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="zyfcxmstate" validVal="0" method="editzyxm">修改</a></shiro:hasPermission>
		<shiro:hasPermission name="${resourceid}:del"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="zyfcxmstate" validVal="0" method="delzyzz">删除</a></shiro:hasPermission>
		<shiro:hasPermission name="${resourceid}:checkon"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="zyfcxmstate" validVal="0" method="check">审核</a></shiro:hasPermission>
		<shiro:hasPermission name="${resourceid}:checkoff"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="zyfcxmstate" validVal="2" method="nocheck">打回</a></shiro:hasPermission>
		<shiro:hasPermission name="${resourceid}:publish"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="zyfcxmstate" validVal="2" method="publish">发布</a></shiro:hasPermission> 
		<shiro:hasPermission name="${resourceid}:publishoff"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="zyfcxmstate" validVal="3" method="nopublish">取消</a></shiro:hasPermission>
		<shiro:hasPermission name="${resourceid}:edit"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="zyfcxmstate" validVal="0" method="addzy">资源管理</a></shiro:hasPermission>

	</div> 
			
 <!--  项目展示dialog -->
	 
	 <div id="zyzz_edit" class="none" style="display:none"  data-options=" fit:true" >
	    		
		 <form id="ff" method="post" enctype="multipart/form-data" >
			<!-- 隐藏域  -->
	  		<!-- <input type="hidden" id="zyfcxmpic" name="zyfcxmpic" value=""/>
	  		<input type="hidden" id="zyfcxmbigpic" name="zyfcxmbigpic" value=""/> -->
			<input type="hidden" id="zyfcxmcontent" name="zyfcxmcontent" value=""/> 
			<div class="main">
	    		<div class="row">
	    			<div>详情标题:</div>
		    		<div>
						<input id="zyfcxmtitle" class="easyui-textbox" name="zyfcxmtitle" style="width:100%;height:32px;" data-options="required:true, validType:'length[1,60]'"/>
		    		</div>
	    		</div>
	    		<div class="row">
	    			<div>列表短标题:</div>
		    		<div>
						<input id="zyfcxmshorttitle" class="easyui-textbox" name="zyfcxmshorttitle" style="width:100%;height:32px;" data-options="required:true, validType:'length[1,60]'"/>
		    		</div>
	    		</div>
	    		<div class="row">
	    			<div>服务地区:</div>
		    		<div>
						<input id="zyfcxmscope" class="easyui-textbox" name="zyfcxmscope" style="width:100%;height:32px;" data-options="required:true, validType:'length[1,30]'"/>
		    		</div>
	    		</div>
	    		<div class="row">
	    			<div>实施单位:</div>
		    		<div>
						<input id="zyfcxmssdw" class="easyui-textbox" name="zyfcxmssdw" style="width:100%;height:32px;" data-options="required:true, validType:'length[1,30]'"/>
		    		</div>
	    		</div>
	    		<div class="row">
	    			<div>列表页图:</div>
	    			<div>
						<input id="zyfcxmpic_up"  class="easyui-filebox" name="zyfcxmpic_up" style="width:90%;height:32px;" data-options="validType:'isIMG[\'zyfcxmpic_up\']', buttonText:'选择图片', accept:'image/jpeg,image/peg, image/png',prompt:'图片尺寸:380x240'">
						<a id="btn_pic" class="easyui-linkbutton" data-options="plain:true," iconCls="icon-clear" style="width:9%;height:32px;">清除</a>
	    			</div>
	    		</div>
	    		<div class="row">
	    			<div>详情页图:</div>
	    			<div>
						<input id="zyfcxmbigpic_up"  class="easyui-filebox" name="zyfcxmbigpic_up" style="width:90%;height:32px;" data-options="validType:'isIMG[\'zyfcxmbigpic_up\']', buttonText:'选择图片', accept:'image/jpeg,image/peg, image/png',prompt:'图片尺寸:400x250'">
						<a id="btn_pic1" class="easyui-linkbutton" data-options="plain:true," iconCls="icon-clear" style="width:9%;height:32px;">清除</a>
	    			</div>
	    		</div>
	    		
	    		<div class="row">
	    			<div>实施时间:</div>
	    			<div><input id="zyfcxmsstime" name="zyfcxmsstime" type="text" class="easyui-datetimebox" data-options="editable:false, required:true"  style="height: 35px; width: 100%"></input></div>
	    		</div>
	    		<div class="row">
	    			<div>志愿者数量:</div>
		    		<div>
		    			<input id="zyfcxmpnum" class="easyui-numberspinner" name="zyfcxmpnum" style="width:100%;height:32px;" />
		    		</div>
	    		</div>
	    		
	    		<div class="row">
	    			<div>服务人数量:</div>
		    		<div>
						<input id="zyfcxmanum" class="easyui-numberspinner" name="zyfcxmanum" style="width:100%;height:32px;" />
		    		</div>
	    		</div>
	    		
	    		<div class="row">
	    			<div>项目实施内容:</div>
	    			<div>
	    				<script id="traeditor" type="text/plain" style="width:100%; height:300px;"></script>
	    			</div>
	    		</div>
	    		
			</div>
		</form>
	</div>	
	<!--培训资源dialog  -->
	<div id="addzy" style="display:none">
		<iframe  style="width:100%; height:100%"></iframe>
	</div>
</body>
</html>