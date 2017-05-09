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
//弹出框初始
var winform = new WhuiWinForm("#sub_edit",{height:250});
 /**
  * 初始培训批次表格
  */
 $(function(){
 	//定义表格参数
	var options = {
			title: '文化馆管理',	
			url: getFullUrl('/admin/sub/findsub'),
			rownumbers:true,
			singleSelect:false,
			columns: [[
			{field:'ck',checkbox:true},
			
			{field:'subname',title:'子馆名称',width:80, sortable:true},
			{field:'subpic',title:'子馆列表页图片',width:80, sortable:true, formatter: imgFMT},
			{field:'subregtime',title:'登记时间',width:80, sortable:true, formatter: datetimeFMT},
			{field:'subcontact',title:'场馆联系人',width:80},
			{field:'subcontactemail',title:'子馆联系邮箱',width:80},
		//	
	        {field:'substate',title:'状态',width:80,formatter :traStateFMT},
	        {field:'opt', title:'操作', formatter: WHdg.optFMT, optDivId:'subOPT'}
			]]
	};

	//初始表格
	WHdg.init('sub', 'subTool', options);
	
	//初始弹出框
	winform.init();
	
	
 });
 
//处理编辑时显示图片
 function _showImg(data){
 	$(".img_div").remove();
 	
 	var imgDiv = '<div class="row img_div">'
 		+'<div></div>'
 		+'<div><img width="200" height="150"> </div> '
 		+'</div>';
 		
 	if (data.subpic){
 		var imgrow = $("[name$='subpic_up']").parents(".row");
 		imgrow.after(imgDiv);
 		imgrow.next(".img_div").find("div img").attr("src",getFullUrl(data.subpic));	
 	}
 	if (data.subbigpic){
 		var imgrow = $("[name$='subbigpic_up']").parents(".row");
 		imgrow.after(imgDiv);
 		imgrow.next(".img_div").find("div img").attr("src",getFullUrl(data.subbigpic));	
 	}
 	 
 }
/**添加子馆信息*/
function addsub(){
	winform.openWin();
	winform.setWinTitle("添加子馆");
	winform.getWinFooter().find("a:eq(0)").show();
	$("#sub_ff").form('clear');
	//_showImg(row);
	
	var frm = winform.getWinBody().find('form').form({
		url : getFullUrl('/admin/sub/save'),
		onSubmit : function(param){
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
				$('#sub').datagrid('reload');
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

/**修改子馆信息*/
function editsub(index){
	var row = WHdg.getRowData(index);
	winform.openWin();
	winform.setWinTitle("修改子馆");
	winform.getWinFooter().find("a:eq(0)").show();
	var frm = winform.getWinBody().find('form').form({
	url : getFullUrl("/admin/sub/save"),
	onSubmit : function(param){
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
			$('#sub').datagrid('reload');
			$.messager.alert('提示', '操作成功!');
			winform.closeWin();
		   }else{
			   $.messager.alert('提示', '操作失败!');
			   winform.oneClick(function(){ frm.submit(); });
		   }
	}
	});
	frm.form("clear");
	_showImg(row);
	//格式化日期
	var _data = $.extend({}, row,{
		subregtime : datetimeFMT(row.subregtime),
	});
	frm.form('load',_data);
	//alert(JSON.stringify(row));
	winform.oneClick(function(){ frm.submit(); });
}


/**查看子馆信息*/
function look(index){
	var row = WHdg.getRowData(index);
	winform.openWin();
	winform.setWinTitle("查看详情");
	winform.getWinFooter().find("a:eq(0)").hide();
	var frm = winform.getWinBody().find('form').form({
	});
	frm.form("clear");
	_showImg(row);
	//格式化日期
	var _data = $.extend({}, row,{
		subregtime : datetimeFMT(row.subregtime),
	});
	frm.form('load',_data);
	winform.oneClick(function(){ frm.submit(); });
}

/**删除志愿培训*/
function delsub(index){
	var row = WHdg.getRowData(index);
	var subid = row.subid;
	
	$.messager.confirm('确认对话框', '您确认要删除吗？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/sub/delsub'),
				data: {subid : subid},
				success: function(data){
				//	alert(JSON.stringify(data));
					if(data.success == '0'){
						$('#sub').datagrid('reload');
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
	frm.form("clear");
	_showImg(row);
	//格式化日期
	var _data = $.extend({}, row,{
		subregtime : datetimeFMT(row.subregtime),
	});
	frm.form('load',_data);
	$('#sub').datagrid('reload');
}

/**审核*/
function check(index){
	var row = WHdg.getRowData(index);
	var subid = row.subid;
	//alert(subid);
	docheck(subid,1,2);
}
/**发布*/
function publish(index){
	var row = WHdg.getRowData(index);
	var subid = row.subid;
	docheck(subid,2,3);
}
/**打回审核*/
function nocheck(index){
	var row = WHdg.getRowData(index);
	var subid = row.subid;
	docheck(subid,2,1);
}
/**取消发布*/
function nopublish(index){
	var row = WHdg.getRowData(index);
	var subid = row.subid;
	docheck(subid,3,2);
}
/** 审核事件提交处理 */
function docheck(subid,fromstate,tostate){
	$.messager.confirm('确认对话框', '您确认将所选择的审核或发布吗？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/sub/check'),
				data: {subid : subid,fromstate:fromstate,tostate:tostate},
				success: function(data){
					//alert(JSON.stringify(data));
					if(data.success == 'success'){
						$('#sub').datagrid('reload');
						$.messager.alert('提示', ''+data.msg);
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
	rows = $("#sub").datagrid("getSelections");
	if (rows == "" || rows == null) {
		$.messager.alert('提示', '请选择要操作的数据！');
		return;
	}
	var subids = _split = "";
	for (var i = 0; i<rows.length; i++){
		subids += _split+rows[i].subid;
		_split = ",";
	}
	subcheck(subids,1,2);
}

/**批量发布*/
function allpublish(){
	var rows={};
	rows = $("#sub").datagrid("getSelections");
	if (rows == "" || rows == null) {
		$.messager.alert('提示', '请选择要操作的数据！');
		return;
	}
	var subids = _split = "";
	for (var i = 0; i<rows.length; i++){
		subids += _split+rows[i].subid;
		_split = ",";
	}
	subcheck(subids,2,3);
}
/**批量打回*/
function allback(){
	var rows={};
	rows = $("#sub").datagrid("getSelections");
	if (rows == "" || rows == null) {
		$.messager.alert('提示', '请选择要操作的数据！');
		return;
	}
	var subids = _split = "";
	for (var i = 0; i<rows.length; i++){
		subids += _split+rows[i].subid;
		_split = ",";
	}
	subcheck(subids,2,1);
}
/**批量取消发布*/
function allnoPublish(){
	var rows={};
	rows = $("#sub").datagrid("getSelections");
	if (rows == "" || rows == null) {
		$.messager.alert('提示', '请选择要操作的数据！');
		return;
	}
	var subids = _split = "";
	for (var i = 0; i<rows.length; i++){
		subids += _split+rows[i].subid;
		_split = ",";
	}
	subcheck(subids,3,2);
}
/**
 * 一键审核提交
 */
function subcheck(subids,fromstate,tostate){
	$.messager.confirm('确认对话框', '您确认将所选择的通过审核吗？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/sub/checkAll'),
				data: {subids : subids,fromstate: fromstate, tostate:tostate },
				success: function(data){
					if(data.success=="success"){
						$.messager.alert("提示", data.msg);
						$('#sub').datagrid('reload');
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
		<!-- 培训管理页面 -->
		<table id="sub"></table>
		
		<!-- 查询栏 -->
		<div id="subTool" class="grid-control-group" style="display:none" >
			<div>
				<shiro:hasPermission name="${resourceid}:add"><a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" size='small' id="btn" onclick="addsub()">添加</a></shiro:hasPermission>
				<shiro:hasPermission name="${resourceid}:checkon"><a href="javascript:void(0)" class="easyui-linkbutton" size='small' onclick="allcheck()">批量审核</a></shiro:hasPermission>
				<shiro:hasPermission name="${resourceid}:checkoff"><a href="javascript:void(0)" class="easyui-linkbutton" size='small' onclick="allback()">批量打回</a></shiro:hasPermission> 
				<shiro:hasPermission name="${resourceid}:publish"><a href="javascript:void(0)" class="easyui-linkbutton" size='small' onclick="allpublish()">批量发布</a></shiro:hasPermission> 
				<shiro:hasPermission name="${resourceid}:publishoff"><a href="javascript:void(0)" class="easyui-linkbutton" size='small' onclick="allnoPublish()">批量取消发布</a></shiro:hasPermission>
			</div>
			<div style="padding-top: 5px">
				场馆类型:
				<input class="easyui-combobox" name="ventype" data-options="valueField:'typid',textField:'typname',url:'${basePath}/comm/whtyp?type=15'"/>
				场馆区域: 
				<input  class="easyui-combobox" name="venarea" data-options="panelHeight:'auto',valueField:'typid',textField:'typname',url:'${basePath}/comm/whtyp?type=8'"/>
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
		<div id="subOPT" style="display:none">
			<shiro:hasPermission name="${resourceid}:edit"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="substate" validVal="1" method="editsub">修改</a></shiro:hasPermission> 
			<shiro:hasPermission name="${resourceid}:del"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="substate" validVal="1" method="delsub">删除</a></shiro:hasPermission>
			<shiro:hasPermission name="${resourceid}:view"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true"  method="look">查看详情</a></shiro:hasPermission>
			<shiro:hasPermission name="${resourceid}:checkon"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="substate" validVal="1" method="check">审核</a></shiro:hasPermission>
			<shiro:hasPermission name="${resourceid}:checkoff"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="substate" validVal="2" method="nocheck">打回审核</a></shiro:hasPermission>
			<shiro:hasPermission name="${resourceid}:publish"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="substate" validVal="2" method="publish">发布</a></shiro:hasPermission>
			<shiro:hasPermission name="${resourceid}:publishoff"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="substate" validVal="3" method="nopublish">取消发布</a></shiro:hasPermission>
		</div> 
				
	 <!--  培训管理dialog -->
		 
		 <div id="sub_edit" class="none" style="display:none"  data-options=" fit:true" >
		    		
			 <form id="sub_ff" method="post" enctype="multipart/form-data" >
				<!-- 隐藏域  -->
				<input type="hidden" id="subid" name="subid" value="" />
				
				<div class="main">
					<div class="row">
		    			<div>子馆名称:</div>
		    			<div>
		    				<input id="subname" class="easyui-textbox" name="subname" style="width:100%;height:32px;" data-options="validType:'length[0,50]',required:true"/>
		    			</div>
		    		</div>
		    		<div class="row">
		    			<div>子馆列表页图片:</div>
		    			<div>
		    				<!-- <input id="subpic_up"  class="easyui-textbox" name="subpic_up" style="width:90%;height:32px;" data-options="editable:false, required:true">
		    				<a href="javascript:void(0)" class="easyui-linkbutton" style="width:9%;height:32px;" data-options="iconCls:'icon-add'">选择图片</a> -->
							<input id="subpic_up"  class="easyui-filebox" name="subpic_up" style="width:90%;height:32px;" data-options="validType:'isIMG[\'subpic_up\']', buttonText:'选择图片', accept:'image/jpeg,image/peg, image/png'">
							<a id="btn_pic" class="easyui-linkbutton" data-options="plain:true," iconCls="icon-clear" style="width:9%;height:32px;">清除</a> 
		    			</div>
		    		 </div>
		    		<div class="row">
		    			<div>子馆详情页图片:</div>
		    			<div>
		    				<!-- <input id="subbigpic_up"  class="easyui-textbox" name="subbigpic_up" style="width:90%;height:32px;" data-options="editable:false, required:true">
		    				<a href="javascript:void(0)" class="easyui-linkbutton" style="width:9%;height:32px;" data-options="iconCls:'icon-add'">选择图片</a> -->
							<input id="subbigpic_up"  class="easyui-filebox" name="subbigpic_up" style="width:90%;height:32px;" data-options="validType:'isIMG[\'subbigpic_up\']', buttonText:'选择图片', accept:'image/jpeg,image/peg, image/png'">
							<a id="btn_bigpic" class="easyui-linkbutton" data-options="plain:true," iconCls="icon-clear" style="width:9%;height:32px;">清除</a> 
		    			</div>
		    		 </div>
		    		<div class="row">
		    			<div>子馆简介:</div>
		    			<div>
		    				<input id="subintroduce" class="easyui-textbox" name="subintroduce" style="width:100%;height:50px;" data-options="validType:'length[0,400]',multiline:true"/>
		    			</div>
		    		</div>
		    		<%-- <div class="row">
		    			<div>父馆标识:</div>
		    			<div>
		    				<input id="subpid" class="easyui-combobox" name="subpid" style="width:100%;height:32px;" data-options="editable:false, required:true, valueField:'typid',textField:'typname',url:'${basePath}/comm/whtyp?type=15'"/>
		    			</div>
		    		</div> --%>
		    		
		    		<div class="row">
		    			<div>登记时间:</div>
		    			<div>
		    				<input id="subregtime" name="subregtime" class="easyui-datetimebox" data-options="editable:false, required:true"  style="height: 35px; width: 100%"></input>
		    			</div>
		    		 </div>
		    		<div class="row">
		    			<div>子馆联系方式:</div>
		    			<div>
							<input id="subcontactnum" class="easyui-textbox" name="subcontactnum" style="width:100%;height:32px;" data-options="validType:'isPhone[\'subcontactnum\']'"/>
		    			</div>
		    		 </div>
		    		<div class="row">
		    			<div>场馆联系人:</div>
		    			<div>
		    				<input id="subcontact" class="easyui-textbox" name="subcontact" style="width:100%;height:32px;" data-options="required:true"/>
		    			</div>
		    		 </div>
		    		<div class="row">
		    			<div>子馆联系邮箱:</div>
		    			<div>
		    				<input id="subcontactemail" class="easyui-textbox" name="subcontactemail" style="width:100%;height:32px;" data-options="validType:'isEmail[\'subcontactemail\']'"/>
		    			</div>
		    		 </div>
				</div>
			</form>
		</div>	
		
		
		
		
</body>
</html>