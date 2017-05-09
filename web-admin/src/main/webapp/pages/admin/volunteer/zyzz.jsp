<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath(); request.setAttribute("basePath", basePath); %>
<%request.setAttribute("resourceid", request.getParameter("rsid"));%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>优秀组织</title>
<%@include file="/pages/comm/header.jsp"%>
<script type="text/javascript" charset="utf-8" src="${basePath}/static/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${basePath}/static/ueditor/ueditor.all.min.js"></script>
<script type="text/javascript" charset="utf-8" src="${basePath}/static/ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript">
var winform = new WhuiWinForm("#zyzz_edit",{height:250});

$(function(){
	$("#zyzzTd").css('visibility','visible');
})

/**审核*/
function check(index){
	var row = WHdg.getRowData(index);
	var zyfczzid = row.zyfczzid;
	var title = "审核";
	docheck(title,zyfczzid,0,2);
}

/**发布*/
function publish(index){
	var row = WHdg.getRowData(index);
	var zyfczzid = row.zyfczzid;
	var title = "发布";
	docheck(title,zyfczzid,2,3);
}

/**打回审核*/
function nocheck(index){
	var row = WHdg.getRowData(index);
	var zyfczzid = row.zyfczzid;
	var title = "打回";
	docheck(title,zyfczzid,2,0);
}

/**取消发布*/
function nopublish(index){
	var row = WHdg.getRowData(index);
	var zyfczzid = row.zyfczzid;
	var title = "取消发布";
	docheck(title,zyfczzid,3,2);
}

/** 审核事件提交处理 */
function docheck(title,zyfczzid,fromstate,tostate){
	$.messager.confirm('确认对话框', '您确认将所选择的'+title+'吗？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/volun/checkexce'),
				data: {zyfczzid : zyfczzid,fromstate:fromstate,tostate:tostate},
				success: function(data){
					if(data.success == 'success'){
						$('#zyzzDg').datagrid('reload');
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
	rows = $("#zyzzDg").datagrid("getSelections");
	if (rows == "" || rows == null) {
		$.messager.alert('提示', '请选择要操作的数据！');
		return;
	}
	var zyfczzids = _split = "";
	for (var i = 0; i<rows.length; i++){
		if (rows[i].zyfczzstate == 0) {
			zyfczzids += _split+rows[i].zyfczzid;
			_split = ",";
		}
		
	}
	if (!zyfczzids){
		$.messager.alert('提示', '没有匹配当前操作的选择记录');
		return false;
	}
	subcheck(zyfczzids,0,2);
}

/**批量发布*/
function allpublish(){
	var rows={};
	rows = $("#zyzzDg").datagrid("getSelections");
	if (rows == "" || rows == null) {
		$.messager.alert('提示', '请选择要操作的数据！');
		return;
	}
	var zyfczzids = _split = "";
	for (var i = 0; i<rows.length; i++){
		if (rows[i].zyfczzstate == 2) {
			zyfczzids += _split+rows[i].zyfczzid;
			_split = ",";
		}
		
	}
	if (!zyfczzids){
		$.messager.alert('提示', '没有匹配当前操作的选择记录');
		return false;
	}
	subcheck(zyfczzids,2,3);
}
/**批量打回*/
function allback(){
	var rows={};
	rows = $("#zyzzDg").datagrid("getSelections");
	if (rows == "" || rows == null) {
		$.messager.alert('提示', '请选择要操作的数据！');
		return;
	}
	var zyfczzids = _split = "";
	for (var i = 0; i<rows.length; i++){
		if (rows[i].zyfczzstate == 2) {
			zyfczzids += _split+rows[i].zyfczzid;
			_split = ",";
		}
		
	}
	if (!zyfczzids){
		$.messager.alert('提示', '没有匹配当前操作的选择记录');
		return false;
	}
	subcheck(zyfczzids,2,0);
}
/**批量取消发布*/
function allnoPublish(){
	var rows={};
	rows = $("#zyzzDg").datagrid("getSelections");
	if (rows == "" || rows == null) {
		$.messager.alert('提示', '请选择要操作的数据！');
		return;
	}
	var zyfczzids = _split = "";
	for (var i = 0; i<rows.length; i++){
		if (rows[i].zyfczzstate == 3) {
			zyfczzids += _split+rows[i].zyfczzid;
			_split = ",";
		}
		
	}
	if (!zyfczzids){
		$.messager.alert('提示', '没有匹配当前操作的选择记录');
		return false;
	}
	subcheck(zyfczzids,3,2);
}

/**
 * 一键审核提交
 */
function subcheck(zyfczzids,fromstate,tostate){
	$.messager.confirm('确认对话框', '您确认将所选择的通过审核吗？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/volun/Allstate'),
				data: {zyfczzids : zyfczzids,fromstate: fromstate, tostate:tostate },
				success: function(data){
					if(data.success=="success"){
						$.messager.alert("提示", '操作成功');
						$('#zyzzDg').datagrid('reload');
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
 	//row.zyfczzfwtime = datetimeFMT(row.zyfczzfwtime);
	frm.form("load", row);
	_showImg(row);
	$('#zyzzDg').datagrid('reload');
}



/**
 * 添加优秀组织
 */
 function addzyzz(){
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
	winform.setWinTitle("添加优秀组织");
	//显示提交按钮
	winform.getWinFooter().find("a:eq(0)").show();
	//定义表单
	var frm = winform.getWinBody().find('form').form({
		url : getFullUrl('/admin/volun/addexce'),
		onSubmit : function(param){
			param.zyfczzstate = 0
			//设置富文本的内容
			$('#zyfczzcontent').val(UE.getEditor('traeditor').getContent());
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
				$('#zyzzDg').datagrid('reload');
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
	//删除展示图片层
 	$(".img_div").remove();
	//清空富文本
	UE.getEditor('traeditor').setContent("");
	//提交事件一次有效
	winform.oneClick(function(){ frm.submit(); });
	
}

/**
 * 编辑优秀组织
 */
 function editzyzz(index){
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
 	winform.setWinTitle("修改优秀组织");
 	//显示提交按钮
 	winform.getWinFooter().find("a:eq(0)").show();
 	//定义表单
 	var frm = winform.getWinBody().find('form').form({
 		url : getFullUrl('/admin/volun/upexce'),
 		onSubmit : function(param){
 			//得到当前修改的id和图片路径
 			param.zyfczzid = row.zyfczzid;
 			param.zyfczzpic = row.zyfczzpic;
 			param.zyfczzbigpic = row.zyfczzbigpic;
 			//设置富文本的内容
 			$('#zyfczzcontent').val(UE.getEditor('traeditor').getContent());
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
 				$('#zyzzDg').datagrid('reload');
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
 	//row.zyfczzfwtime = datetimeFMT(row.zyfczzfwtime);
 	//读取表单数据
 	frm.form('load',row);
 	//处理编辑时显示图片
 	_showImg(row);
 	//显示富文本的值
 	UE.getEditor('traeditor').setContent(row.zyfczzcontent);
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
 	if (data.zyfczzpic){
 		var imgrow = $("[name$='zyfczzpic_up']").parents(".row");
 		imgrow.after(imgDiv);
 		imgrow.next(".img_div").find("div img").attr("src",getFullUrl(data.zyfczzpic));	
 	}
 	//判断图片不为空显示	
 	if (data.zyfczzbigpic){
 		var imgrow = $("[name$='zyfczzbigpic_up']").parents(".row");
 		imgrow.after(imgDiv);
 		imgrow.next(".img_div").find("div img").attr("src",getFullUrl(data.zyfczzbigpic));	
 	}
 }
 
 /**
  * 删除优秀组织
  */
 function delzyzz(index){
	var row = WHdg.getRowData(index);
	var zyfczzid = row.zyfczzid;
	var zyfczzpic = row.zyfczzpic;
	var	zyfczzbigpic = row.zyfczzbigpic;
	$.messager.confirm('确认对话框', '您确认要删除吗？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/volun/delexcll'),
				data: {zyfczzid : zyfczzid,zyfczzpic:zyfczzpic,zyfczzbigpic:zyfczzbigpic},
				success: function(data){
					if(data.success == '0'){
						$('#zyzzDg').datagrid('reload');
					   }else{
						   $.messager.alert('提示', '操作失败。原因：'+data.errmsg, 'error');
					   }
				}
			});
		}
	});
}
 
 /**
  * 初始优秀组织表格
  */
 $(function(){
 	//定义表格参数
	var options = {
			title: '志愿优秀组织',	
			url: getFullUrl('/admin/volun/findexce'),
			rownumbers:true,
			singleSelect:false,
			columns: [[
			{field:'ck',checkbox:true},
			//{field:'zyfczzid',title:'主键标识',width:80, sortable:true},
//			{field:'zyfczztitle',width:120,title:'详情标题'},
			{field:'zyfczzshorttitle',width:100,title:'列表短标题', sortable:true},
			{field:'zyfczzpic',width:80,title:'列表图片',formatter:imgFMT},  
//			{field:'zyfczzbigpic',width:80,title:'详情图片',formatter:imgFMT},
			{field:'zyfczzpnum',width:80,title:'志愿者数量',sortable:true}, 
			{field:'zyfczzanum',width:80,title:'活动数量',sortable:true},
			{field:'zyfczzscope',width:120,title:'服务地区'},
//	        {field:'zyfczzjoinact',width:80,title:'参与活动'},
	        {field:'zyfczzfwtime',width:120,title:'服务时间',sortable:true,formatter:datetimeFMT},
	        //{field:'zyfczzcontent',title:'组织介绍'},
	       	//{field:'zyfczzopttime',title:'修改状态时间',width:80,formatter:datetimeFMT},
	        {field:'zyfczzstate',width:80,title:'状态',formatter :traStateFMT},
	        //{field:'zyfvenueid',title:'所属文化馆标识'},
	        {field:'opt', title:'操作', formatter: WHdg.optFMT, optDivId:'zyzzOPT'}
			]]
	};

	//初始表格
	WHdg.init('zyzzDg', 'zyzzTd', options);
	
	//初始弹出框
	winform.init();
	
	//初始富文本
	UE.getEditor('traeditor').ready(function(){
		window.init_UE = true;
	});
	
	//清除文件框的值
	$('#btn_pic').bind('click', function(){  
		$("#zyfczzpic_up").filebox('clear');
    });    
	$('#btn_pic1').bind('click', function(){  
		$("#zyfczzbigpic_up").filebox('clear');
	});   
	
 })
 


/**
 * 添加资源
 * @param index
 * @returns
 */
//function addzy(index){
//	var rows = $('#zyzzDg').datagrid('getRows');
//	var row = rows[index];//WHdg2.getRowData(index);
//	var zyfczzid = row.zyfczzid;
//
//	$('#addzy iframe').attr('src', getFullUrl("/admin/ent/entsrc?reftype=2017010400000001&refid="+zyfczzid+"&canEdit=true"));
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
	var row = $("#zyzzDg").datagrid("getRows")[idx];
	WhgComm.editDialog('${basePath}/admin/train/resource/view/list?reftype=8&id=' + row.zyfczzid);
}
</script>
</head>
<body>
	<!-- 志愿组织页面 -->
	<table id="zyzzDg"></table>
	
	<!-- 查询栏 -->
	<div id="zyzzTd" class="grid-control-group" style="display: none;" >
		<div style="line-height: 32px;">
			<shiro:hasPermission name="${resourceid}:add"><a href="#" class="easyui-linkbutton" iconCls="icon-add" size='small' id="btn" onclick="addzyzz()">添加</a></shiro:hasPermission>
			<shiro:hasPermission name="${resourceid}:checkon"><a href="javascript:void(0)" class="easyui-linkbutton" size='small' onclick="allcheck()">批量审核</a></shiro:hasPermission>
			<shiro:hasPermission name="${resourceid}:checkoff"><a href="javascript:void(0)" class="easyui-linkbutton" size='small' onclick="allback()">批量打回</a></shiro:hasPermission> 
			<shiro:hasPermission name="${resourceid}:publish"><a href="javascript:void(0)" class="easyui-linkbutton" size='small' onclick="allpublish()">批量发布</a></shiro:hasPermission> 
			<shiro:hasPermission name="${resourceid}:publishoff"><a href="javascript:void(0)" class="easyui-linkbutton" size='small' onclick="allnoPublish()">批量取消发布</a></shiro:hasPermission> 
			
		</div>
		<div style="padding-top: 5px">
			<%--状态:--%>
			<select class="easyui-combobox" name="zyfczzstate"style="width: 200px; height:28px"  >
				<option value="">请选择状态</option>
				<option value="0">未审核</option>
				<option value="2">已审核</option>
				<option value="3">已发布</option>
			</select>
			<a href="javascript:void(0)" class="easyui-linkbutton search" iconCls="icon-search">查询</a>
		</div>
		
	</div>
	      
	<!-- 操作按钮 -->
	<div id="zyzzOPT" style="display:none">
		<shiro:hasPermission name="${resourceid}:view"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true"  method="look">查看详情</a></shiro:hasPermission>
		<shiro:hasPermission name="${resourceid}:edit"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="zyfczzstate" validVal="0" method="editzyzz">修改</a></shiro:hasPermission> 
		<shiro:hasPermission name="${resourceid}:del"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="zyfczzstate" validVal="0" method="delzyzz">删除</a></shiro:hasPermission>
		<shiro:hasPermission name="${resourceid}:checkon"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="zyfczzstate" validVal="0" method="check">审核</a></shiro:hasPermission>
		<shiro:hasPermission name="${resourceid}:checkoff"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="zyfczzstate" validVal="2" method="nocheck">打回</a></shiro:hasPermission>
		<shiro:hasPermission name="${resourceid}:publish"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="zyfczzstate" validVal="2" method="publish">发布</a></shiro:hasPermission> 
		<shiro:hasPermission name="${resourceid}:publishoff"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="zyfczzstate" validVal="3" method="nopublish">取消</a></shiro:hasPermission>
		<shiro:hasPermission name="${resourceid}:edit"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="zyfczzstate" validVal="0" method="addzy">资源管理</a></shiro:hasPermission>  
	</div> 
			
 <!--  志愿组织dialog -->
	 
	 <div id="zyzz_edit" class="none" style="display:none"  data-options=" fit:true" >
	    		
		 <form id="ff" method="post" enctype="multipart/form-data" >
			<!-- 隐藏域  -->
	  		<!-- <input type="hidden" id="zyfczzpic" name="zyhdpic" value=""/>
	  		<input type="hidden" id="zyfczzbigpic" name="zyhdbigpic" value=""/> -->
			<input type="hidden" id="zyfczzcontent" name="zyfczzcontent" value=""/>
			<div class="main">
	    		<div class="row">
	    			<div>详情标题:</div>
		    		<div>
						<input id="zyfczztitle" class="easyui-textbox" name="zyfczztitle" style="width:100%;height:32px;" data-options="required:true, validType:'length[1,60]'"/>
		    		</div>
	    		</div>
	    		<div class="row">
	    			<div>列表短标题:</div>
		    		<div>
						<input id="zyfczzshorttitle" class="easyui-textbox" name="zyfczzshorttitle" style="width:100%;height:32px;" data-options="required:true, validType:'length[1,60]'"/>
		    		</div>
	    		</div>
	    		<div class="row">
	    			<div>服务地区:</div>
		    		<div>
						<input id="zyfczzscope" class="easyui-textbox" name="zyfczzscope" style="width:100%;height:32px;" data-options="required:true, validType:'length[1,30]'"/>
		    		</div>
	    		</div>
	    		<div class="row">
	    			<div>参与活动:</div>
		    		<div>
						<input id="zyfczzjoinact" class="easyui-textbox" name="zyfczzjoinact" style="width:100%;height:32px;" data-options=" validType:'length[1,30]'"/>
		    		</div>
	    		</div>
	    		<div class="row">
	    			<div>列表页图:</div>
	    			<div>
						<input id="zyfczzpic_up"  class="easyui-filebox" name="zyfczzpic_up" style="width:90%;height:32px;" data-options="validType:'isIMG[\'zyfczzpic_up\']', buttonText:'选择图片', accept:'image/jpeg,image/peg, image/png',prompt:'图片尺寸:380x240'">
						<a id="btn_pic" class="easyui-linkbutton" data-options="plain:true," iconCls="icon-clear" style="width:9%;height:32px;">清除</a>
	    			</div>
	    		</div>
	    		<div class="row">
	    			<div>详情页图:</div>
	    			<div>
						<input id="zyfczzbigpic_up"  class="easyui-filebox" name="zyfczzbigpic_up" style="width:90%;height:32px;" data-options="validType:'isIMG[\'zyfczzbigpic_up\']', buttonText:'选择图片', accept:'image/jpeg,image/peg, image/png',prompt:'图片尺寸:400x250'">
						<a id="btn_pic1" class="easyui-linkbutton" data-options="plain:true," iconCls="icon-clear" style="width:9%;height:32px;">清除</a>
	    			</div>
	    		</div>
	    		
	    		<div class="row">
	    			<div>服务时间:</div>
	    			<div><input id="zyfczzfwtime" name="zyfczzfwtime" type="text" class="easyui-numberspinner" data-options="min:1,max:1000000000"  style="height: 35px; width: 100%"></div>
	    		</div>
	    		<div class="row">
	    			<div>志愿者数量:</div>
		    		<div>
		    			<input id="zyfczzpnum" class="easyui-numberspinner" name="zyfczzpnum" style="width:100%;height:32px;" data-options="min:1,max:1000000000"/>
		    		</div>
	    		</div>
	    		
	    		<div class="row">
	    			<div>活动数量:</div>
		    		<div>
						<input id="zyfczzanum" class="easyui-numberspinner" name="zyfczzanum" style="width:100%;height:32px;" data-options="min:1,max:1000000000"/>
		    		</div>
	    		</div>
	    		
	    		<div class="row">
	    			<div>组织介绍:</div>
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