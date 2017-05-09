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
var winform = new WhuiWinForm("#mage_edit",{height:250});
var winform1 = new WhuiWinForm("#mage_edit1",{height:250});


$(function(){
	var options = {
		title : "电子杂志",
		url: getFullUrl('/admin/magezine/selmage'),
		rownumbers:true,
		singleSelect:false,
		columns: [[
		{field:'ck',checkbox:true},
		{field:'magetitle',title:'电子杂志标题',width:80, sortable:true},
		{field:'magenum',title:'电子杂志刊号',width:80},
		{field:'magearttyp',title:'艺术类型',width:80, sortable:true, formatter: arttypFMT},
		{field:'magepic',title:'电子杂志图片',width:80, formatter:imgFMT},    
		   
        {field:'magestate',title:'状态',width:80,formatter :traStateFMT},
        {field:'opt', title:'操作', formatter: WHdg.optFMT, optDivId:'mageOPT'}
		]]
	}
	//初始表格
	WHdg.init('mage', 'mageTool', options);
	//初始弹出框
	winform.init();
	winform1.init();
	//显示出查询栏
	$("#mageTool").css('visibility','visible');
	//清除图片输入框的值
	$('#btn_pic').bind('click', function(){  
		$("#magepic_up").filebox('clear');
    });  
})

/**
 * 添加电子杂志
 */
function addMage(){
	winform.openWin();
	winform.setWinTitle("添加电子杂志");
	$("#mage_ff").form("clear");
	$(".img_div").remove();
	$(".img_div1").remove();
	
	var frm = winform.getWinBody().find('form').form({
		url : getFullUrl('/admin/magezine/save'),
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
				$('#mage').datagrid('reload');
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
function editMage(index){
	var row = WHdg.getRowData(index);
	winform.openWin();
	winform.setWinTitle("修改电子杂志");
	$("#mage_ff").form("clear");
	var frm = winform.getWinBody().find('form').form({
		url : getFullUrl('/admin/magezine/save'),
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
				$('#mage').datagrid('reload');
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
function delmage(index){
	var row = WHdg.getRowData(index);
	var mageid = row.mageid;
	$.messager.confirm('确认对话框','您确定删除这条记录吗？',function(r){
		if (r) {
			$.ajax({
				type : "post",
				url : getFullUrl('/admin/magezine/delmage'),
				data : {mageid : mageid},
				success : function(data){
					if (data.success == '0') {
						$("#mage").datagrid('reload');
					}else{
						$.messager.alert('提示', '操作失败。原因：'+data.errmsg, 'error');
					}
				}
			})
		}
		
	})
}

/**查看详情*/
function look(index){
	var row = WHdg.getRowData(index);
	winform1.openWin();
	winform1.setWinTitle("查看详情");
	winform1.getWinFooter().find("a:eq(0)").hide();
	_showImg1(row);
	$("#mage_ff1").form("clear");
	$("#mage_ff1").form("load", row);
	$('#mage_ff1').find("input").attr("readonly","true");
	$('#mage').datagrid('reload');
}
 

//处理编辑时显示图片
function _showImg(data){
	$(".img_div").remove();
	$(".img_div1").remove();
	var imgDiv = '<div class="row img_div">'
		+'<div></div>'
		+'<div><img width="200" height="150"> </div> '
		+'</div>';
		
	if (data.magepic){
		var imgrow = $("[name$='magepic_up']").parents(".row");
		imgrow.after(imgDiv);
		imgrow.next(".img_div").find("div img").attr("src",getFullUrl(data.magepic));	
	}
}
//处理编辑时显示图片
function _showImg1(data){
	$(".img_div").remove();
	$(".img_div1").remove();
	var imgDiv1 = '<div class="row img_div1">'
		+'<div></div>'
		+'<div><img width="200" height="150"> </div> '
		+'</div>';
		
	if (data.magepic){
		var imgrow = $("[name$='magepic_up']").parents(".row");
		imgrow.after(imgDiv1);
		imgrow.next(".img_div1").find("div img").attr("src",getFullUrl(data.magepic));	
	}
}

/**审核*/
function sendCheck(index){
	var row = WHdg.getRowData(index);
	var mageid = row.mageid;
	var fromstate = row.magestate;
	$.messager.confirm('确认对话框', '您确认审核所选的数据吗？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/magezine/check'),
				data: {mageid : mageid,fromstate: fromstate, tostate:2},
				success: function(data){
					if(data.success == '0'){
						$('#mage').datagrid('reload');
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
	var mageid= row.mageid;
	$.messager.confirm('确认对话框', '您确认发布所选的数据吗？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/magezine/check'),
				data: {mageid : mageid,fromstate: 2, tostate:3},
				success: function(data){
					if(data.success == '0'){
						$('#mage').datagrid('reload');
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
	var mageid= row.mageid;
	$.messager.confirm('确认对话框', '您确认将所选的数据打回未审核吗？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/magezine/check'),
				data: {mageid : mageid,fromstate: 2, tostate:1},
				success: function(data){
					if(data.success == '0'){
						$('#mage').datagrid('reload');
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
	var mageid= row.mageid;
	$.messager.confirm('确认对话框', '您确认将所选的数据取消发布吗？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/magezine/check'),
				data: {mageid : mageid,fromstate: 3, tostate:2},
				success: function(data){
					if(data.success == '0'){
						$('#mage').datagrid('reload');
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
	return row.magestate == '0' || row.magestate == '1';
}

/**
 * 一键审核
 */
function checkAll(){
	var rows={};
	rows = $("#mage").datagrid("getSelections");
	if (rows == "" || rows == null) {
		$.messager.alert('提示', '请选择要操作的数据！');
		return;
	}
	var mageids = _split = "";//id1,id2,id3
	for (var i = 0; i<rows.length; i++){
		mageids += _split+rows[i].mageid;
		_split = ",";
	}
	//trastate = rows[0].trastate;
	$.messager.confirm('确认对话框', '您确认将所选择的进行审核吗？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/magezine/allcheck'),
				data: {mageid : mageids,fromstate: 0, tostate:2},
				success: function(data){
					if(data.success == '0'){
						$('#mage').datagrid('reload');
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
	rows = $("#mage").datagrid("getSelections");
	if (rows == "" || rows == null) {
		$.messager.alert('提示', '请选择要操作的数据！');
		return;
	}
	var mageids = _split = "";//id1,id2,id3
	for (var i = 0; i<rows.length; i++){
		mageids += _split+rows[i].mageid;
		_split = ",";
	}
	//trastate = rows[0].trastate;
	$.messager.confirm('确认对话框', '您确认将所选择的进行发布吗？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/magezine/allcheck'),
				data: {mageid : mageids,fromstate: 2, tostate:3},
				success: function(data){
					if(data.success == '0'){
						$('#mage').datagrid('reload');
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
	rows = $("#mage").datagrid("getSelections");
	if (rows == "" || rows == null) {
		$.messager.alert('提示', '请选择要操作的数据！');
		return;
	}
	var mageids = _split = "";//id1,id2,id3
	for (var i = 0; i<rows.length; i++){
		mageids += _split+rows[i].mageid;
		_split = ",";
	}
	//trastate = rows[0].trastate;
	$.messager.confirm('确认对话框', '您确认将所选择的取消发布吗？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/magezine/allcheck'),
				data: {mageid : mageids,fromstate: 3, tostate:2},
				success: function(data){
					if(data.success == '0'){
						$('#mage').datagrid('reload');
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
	rows = $("#mage").datagrid("getSelections");
	if (rows == "" || rows == null) {
		$.messager.alert('提示', '请选择要操作的数据！');
		return;
	}
	var mageids = _split = "";//id1,id2,id3
	for (var i = 0; i<rows.length; i++){
		mageids += _split+rows[i].mageid;
		_split = ",";
	}
	//trastate = rows[0].trastate;
	$.messager.confirm('确认对话框', '您确认将所选择的打回审核吗？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/magezine/allcheck'),
				data: {mageid : mageids,fromstate: 2, tostate:1},
				success: function(data){
					if(data.success == '0'){
						$('#mage').datagrid('reload');
					   }else{
						   $.messager.alert('提示', '操作失败。原因：'+data.errmsg, 'error');
					   }
				}
			});
		}
	});
}

/**
 * 页码管理
 */
function addpage(index){
	var rows = $('#mage').datagrid('getRows');
	var row = rows[index];//WHdg2.getRowData(index);
	var mageid = row.mageid;
	
	$('#addpage iframe').attr('src', getFullUrl("/admin/magezine/magezinepage?pagemageid="+mageid));
	$('#addpage').dialog({    
	    title: '页码管理',  
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
		<!-- 电子杂志管理页面 -->
		<table id="mage"></table>
		
		<!-- 查询栏 -->
		<div id="mageTool" class="grid-control-group" style="display: none;">
			<div>
				<shiro:hasPermission name="${resourceid}:add"><a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" id="btn" onclick="addMage()">添加</a></shiro:hasPermission>
				<shiro:hasPermission name="${resourceid}:checkon"><a href="javascript:void(0)" class="easyui-linkbutton" onclick="checkAll()">批量审核</a></shiro:hasPermission>
				<shiro:hasPermission name="${resourceid}:checkoff"><a href="javascript:void(0)" class="easyui-linkbutton" onclick="goallCheck()">批量打回</a></shiro:hasPermission>
				<shiro:hasPermission name="${resourceid}:publish"><a href="javascript:void(0)" class="easyui-linkbutton" onclick="publishAll()">批量发布</a></shiro:hasPermission>
				<shiro:hasPermission name="${resourceid}:publishoff"><a href="javascript:void(0)" class="easyui-linkbutton" onclick="noPublishAll()">批量取消发布</a></shiro:hasPermission>
			</div>
			<div style="padding-top: 5px">
				艺术类型: 
				<input class="easyui-combobox" name="magearttyp" data-options="valueField:'typid',textField:'typname',url:'${basePath}/comm/whtyp?type=0'"/>
				杂志标题:
				<input class="easyui-textbox" name="magetitle" data-options="validType:'length[1,30]'"/>
				状态:
				<select class="easyui-combobox" name="magestate" data-options="editable:false">
					<option value="">全部</option>
					<option value="0">初始</option>
					<option value="1">未审核</option>
					<option value="2">已审核</option>
					<option value="3">已发布</option>
				</select>
				<a href="javascript:void(0)" class="easyui-linkbutton search" iconCls="icon-search">查询</a>
			</div>
		</div>
		      
		<!-- 操作按钮 -->
		<div id="mageOPT" style="display:none">
			<shiro:hasPermission name="${resourceid}:edit"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validFun="_validFun" method="editMage">修改</a></shiro:hasPermission> 
			<shiro:hasPermission name="${resourceid}:del"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" method="delmage">删除</a></shiro:hasPermission>
			<shiro:hasPermission name="${resourceid}:checkon"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validFun="_validFun" method="sendCheck">审核</a></shiro:hasPermission>
			<shiro:hasPermission name="${resourceid}:checkoff"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="magestate" validVal="2" method="nocheck">打回审核</a></shiro:hasPermission>
			<shiro:hasPermission name="${resourceid}:publish"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="magestate" validVal="2" method="publish">发布</a></shiro:hasPermission>
			<shiro:hasPermission name="${resourceid}:publishoff"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="magestate" validVal="3" method="noPublish">取消发布</a></shiro:hasPermission>
			<shiro:hasPermission name="${resourceid}:view"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true"  method="look">查看详情</a></shiro:hasPermission>
			<shiro:hasPermission name="${resourceid}:view"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" method="addpage">页码管理</a></shiro:hasPermission>
		</div> 
				
	
		    		
		    		
		
		<!--电子杂志页码管理dialog  -->
		<div id="addpage" style="display:none">
			<iframe  style="width:100%; height:100%"></iframe>
		</div>
		
		
		<!-- 电子杂志管理 -->
		 <div id="mage_edit" class="none" style="display:none"  data-options=" fit:true" >
			 <form id="mage_ff" method="post" enctype="multipart/form-data" >
				<!-- 隐藏域  -->
				<input type="hidden" id="mageid" name="mageid" value="" />
				<input type="hidden" id="magepic" name="magepic" value="" />
				
				<div class="main">
					<div class="row">
		    			<div>艺术类型:</div>
		    			<div>
		    				<input id="magearttyp" class="easyui-combobox" name="magearttyp" style="width:100%;height:32px;" data-options="editable:false, required:true, valueField:'typid',textField:'typname',url:'${basePath}/comm/whtyp?type=0'"/>
		    			</div>
		    		</div>
					<div class="row">
		    			<div>标题:</div>
		    			<div>
		    				<input id="magetitle" class="easyui-textbox" name="magetitle" style="width:100%;height:32px;" data-options=" required:true"/>
		    			</div>
		    		</div>
		    		<div class="row">
		    			<div>杂志刊号:</div>
		    			<div>
		    				<input id="magenum" class="easyui-textbox" name="magenum" style="width:100%;height:32px;" data-options=" required:true"/>
		    			</div>
		    		</div>
		    		<div class="row">
		    			<div>封面图片:</div>
		    			<div>
							<input id="magepic_up" class="easyui-filebox" name="magepic_up" style="width:89%;height:32px;" data-options="validType:'isIMG[\'magepic_up\']', buttonText:'选择图片', accept:'image/jpeg,image/peg, image/png'"/>
		    				<a id="btn_pic" class="easyui-linkbutton" data-options="plain:true," iconCls="icon-clear" style="width:9%;height:32px;">清除</a>
		    			</div>
		    		 </div>
		    		<div class="row">
		    			<div>简介:</div>
		    			<div>
							<input id="magedesc" class="easyui-textbox" name="magedesc" style="width:100%;height:60px;" data-options="multiline:true, validType:'length[1,50]'"/>
		    			</div>
		    		 </div>
				</div>
			</form>
		</div>	
		
		
		
		<!-- 电子杂志查看详情 -->
		 <div id="mage_edit1" class="none" style="display:none"  data-options=" fit:true" >
			 <form id="mage_ff1" method="post" enctype="multipart/form-data" >
				<!-- 隐藏域  -->
				<input type="hidden" id="mageid1" name="mageid" value="" />
				<input type="hidden" id="magepic1" name="magepic" value="" />
				
				<div class="main">
					<div class="row">
		    			<div>艺术类型:</div>
		    			<div>
		    				<input id="magearttyp1" class="easyui-combobox" name="magearttyp" style="width:100%;height:32px;" data-options="editable:false, required:true, valueField:'typid',textField:'typname',url:'${basePath}/comm/whtyp?type=0'"/>
		    			</div>
		    		</div>
					<div class="row">
		    			<div>标题:</div>
		    			<div>
		    				<input id="magetitle1" class="easyui-textbox" name="magetitle" style="width:100%;height:32px;" data-options=" required:true"/>
		    			</div>
		    		</div>
		    		<div class="row">
		    			<div>杂志刊号:</div>
		    			<div>
		    				<input id="magenum1" class="easyui-textbox" name="magenum" style="width:100%;height:32px;" data-options=" required:true"/>
		    			</div>
		    		</div>
		    		<div class="row">
		    			<div>封面图片:</div>
		    			<div>
							<input id="magepic_up1" class="easyui-filebox" name="magepic_up" style="width:89%;height:32px;"/>
		    				<a id="btn_pic" class="easyui-linkbutton" data-options="plain:true," iconCls="icon-clear" style="width:9%;height:32px;">清除</a>
		    			</div>
		    		 </div>
		    		<div class="row">
		    			<div>简介:</div>
		    			<div>
							<input id="magedesc1" class="easyui-textbox" name="magedesc" style="width:100%;height:60px;" data-options="multiline:true, validType:'length[1,50]'"/>
		    			</div>
		    		 </div>
				</div>
			</form>
		</div>	
		
		
</body>
</html>