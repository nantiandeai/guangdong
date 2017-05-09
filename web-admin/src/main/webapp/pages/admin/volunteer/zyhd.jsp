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
var winform = new WhuiWinForm("#zyhd_edit",{height:250});

$(function(){
	$("#zyhdTool").css('visibility','visible');
})

 /**
  * 初始培训批次表格
  */
 $(function(){
 	//定义表格参数
	var options = {
			title: '志愿活动管理',	
			url: getFullUrl('/admin/zyhd/findzyhd'),
			rownumbers:true,
			singleSelect:false,
			columns: [[
			{field:'ck',checkbox:true},
			{field:'zyhdarea',title:'志愿活动区域',width:80, sortable:true,formatter:function(val,row,index){ return WhgSysData.FMT(val, WhgSysData.getTypeData('6'));}},
			{field:'zyhdtype',title:'类型',width:80, sortable:true,formatter:function(val,row,index){ return WhgSysData.FMT(val, WhgSysData.getTypeData('13'));}},
			{field:'zyhdtitle',title:'详情标题',width:80, sortable:true},
			{field:'zyhdscope',title:'服务地区',width:80, sortable:true},
			{field:'zyhdpic',title:'列表图',width:80,formatter:imgFMT},  
			{field:'zyhdbigpic',title:'详情图',width:80,formatter:imgFMT},  
			{field:'zyhdsdate',title:'活动开始时间',width:80,formatter:datetimeFMT},  
			
	        {field:'zyhdstate',title:'状态',width:80,formatter :traStateFMT},
	        {field:'opt', title:'操作', formatter: WHdg.optFMT, optDivId:'zyhdOPT'}
			]]
	};

	//初始表格
	WHdg.init('zyhd', 'zyhdTool', options);
	
	//初始弹出框
	winform.init();
	
	//初始富文本
	UE.getEditor('traeditor').ready(function(){
		window.init_UE = true;
	});
	
	//清除文件框的值
	$('#btn_pic').bind('click', function(){  
		$("#zyhdpic_up").filebox('clear');
    });    
	$('#btn_pic1').bind('click', function(){  
		$("#zyhdbigpic_up").filebox('clear');
	});   
	
 })
 
/**添加志愿培训*/
function addzyhd(){
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
	winform.openWin();
	winform.setWinTitle("添加志愿活动");
	$(".img_div").remove();
	winform.getWinFooter().find("a:eq(0)").show();
	var frm = winform.getWinBody().find('form').form({
		url : getFullUrl('/admin/zyhd/save'),
		onSubmit : function(param){
			//设置富文本的内容
			$('#zyhdcontent').val(UE.getEditor('traeditor').getContent());
			
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
				$('#zyhd').datagrid('reload');
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


/**修改志愿培训*/
function editzyhd(index){
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
	winform.openWin();
	winform.setWinTitle("修改志愿活动");
	winform.getWinFooter().find("a:eq(0)").show();
	//alert(JSON.stringify(row));
	var frm = winform.getWinBody().find('form').form({
		url : getFullUrl('/admin/zyhd/save'),
		onSubmit : function(param){
			//设置富文本的内容
			$('#zyhdcontent').val(UE.getEditor('traeditor').getContent());
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
				$('#zyhd').datagrid('reload');
				winform.closeWin();
			   }else{
				   $.messager.alert('提示', '操作失败!');
				   winform.oneClick(function(){ frm.submit(); });
			   }
		}
	});
	frm.form("clear");
	
	//格式化日期
	var _data = $.extend({}, row,
			{
		zyhdsdate : datetimeFMT(row.zyhdsdate),
		zyhdedate : datetimeFMT(row.zyhdedate),
	});
	frm.form('load',_data);
	_showImg(row);
	//显示富文本的值
	UE.getEditor('traeditor').setContent(row.zyhdcontent);
	winform.oneClick(function(){ frm.submit(); });
}

//处理编辑时显示图片
function _showImg(data){
	$(".img_div").remove();
	
	var imgDiv = '<div class="row img_div">'
		+'<div></div>'
		+'<div><img width="200" height="150"> </div> '
		+'</div>';
		
	if (data.zyhdpic){
		var imgrow = $("[name$='zyhdpic_up']").parents(".row");
		imgrow.after(imgDiv);
		imgrow.next(".img_div").find("div img").attr("src",getFullUrl(data.zyhdpic));	
	}
	if (data.zyhdbigpic){
		var imgrow = $("[name$='zyhdbigpic_up']").parents(".row");
		imgrow.after(imgDiv);
		imgrow.next(".img_div").find("div img").attr("src",getFullUrl(data.zyhdbigpic));	
	}
	
	 
}
/**删除志愿培训*/
function delzyhd(index){
	var row = WHdg.getRowData(index);
	var zyhdid = row.zyhdid;
	$.messager.confirm('确认对话框', '您确认要删除吗？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/zyhd/delhd'),
				data: {zyhdid : zyhdid},
				success: function(data){
				//	alert(JSON.stringify(data));
					if(data.success == '0'){
						$('#zyhd').datagrid('reload');
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
	UE.getEditor('traeditor').setContent(row.zyhdcontent);
	frm.form("clear");
	//格式化日期
	var _data = $.extend({}, row,
			{
		zyhdsdate : datetimeFMT(row.zyhdsdate),
		zyhdedate : datetimeFMT(row.zyhdedate),
	});
	frm.form("load", _data);
	_showImg(row);
	$('#zyhd').datagrid('reload');
}

/**审核*/
function check(index){
	var row = WHdg.getRowData(index);
	var zyhdid = row.zyhdid;
	var title  = '审核';
	docheck(title,zyhdid,1,2);
	/* $.ajax({
		type : "post",
		url : getFullUrl('/admin/zyhd/canCheck'),
		data : {zyhdid : zyhdid},
		success :  function(data){
			if (data.success == 0 || data.success == null) {
				$.messager.alert('提示', '没有添加资源，不能通过审核！');
				return;
			}else{
				docheck(zyhdid,1,2);
			}
			
		}
	}); */
}
/**发布*/
function publish(index){
	var row = WHdg.getRowData(index);
	var zyhdid = row.zyhdid;
	var title  = '发布';
	docheck(title,zyhdid,2,3);
}
/**打回审核*/
function nocheck(index){
	var row = WHdg.getRowData(index);
	var zyhdid = row.zyhdid;
	var title  = '打回';
	docheck(title,zyhdid,2,1);
}
/**取消发布*/
function nopublish(index){
	var row = WHdg.getRowData(index);
	var zyhdid = row.zyhdid;
	var title  = '取消发布';
	docheck(title,zyhdid,3,2);
}
/** 审核事件提交处理 */
function docheck(title,zyhdid,fromstate,tostate){
	$.messager.confirm('确认对话框', '您确认将所选择的'+title+'吗？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/zyhd/check'),
				data: {zyhdid : zyhdid,fromstate:fromstate,tostate:tostate},
				success: function(data){
					//alert(JSON.stringify(data));
					if(data.success == 'success'){
						$('#zyhd').datagrid('reload');
//						$.messager.alert('提示', ''+data.msg);
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
	rows = $("#zyhd").datagrid("getSelections");
	if (rows == "" || rows == null) {
		$.messager.alert('提示', '请选择要操作的数据！');
		return;
	}
	var zyhdids = _split = "";
	for (var i = 0; i<rows.length; i++){
		zyhdids += _split+rows[i].zyhdid;
		_split = ",";
	}
	
	$.ajax({
		type : "post",
		url : getFullUrl('/admin/zyhd/canCheckAll'),
		data : {zyhdids : zyhdids},
		success :  function(data){
			if (data.success == 0 || data.success == null) {
				$.messager.alert('提示', '没有添加资源，不能通过审核！');
				return;
			}else{
				subcheck(zyhdids,1,2);
			}
		}
	});
}

/**批量发布*/
function allpublish(){
	var rows={};
	rows = $("#zyhd").datagrid("getSelections");
	if (rows == "" || rows == null) {
		$.messager.alert('提示', '请选择要操作的数据！');
		return;
	}
	var zyhdids = _split = "";
	for (var i = 0; i<rows.length; i++){
		zyhdids += _split+rows[i].zyhdid;
		_split = ",";
	}
	subcheck(zyhdids,2,3);
}
/**批量打回*/
function allback(){
	var rows={};
	rows = $("#zyhd").datagrid("getSelections");
	if (rows == "" || rows == null) {
		$.messager.alert('提示', '请选择要操作的数据！');
		return;
	}
	var zyhdids = _split = "";
	for (var i = 0; i<rows.length; i++){
		zyhdids += _split+rows[i].zyhdid;
		_split = ",";
	}
	subcheck(zyhdids,2,1);
}
/**批量取消发布*/
function allnoPublish(){
	var rows={};
	rows = $("#zyhd").datagrid("getSelections");
	if (rows == "" || rows == null) {
		$.messager.alert('提示', '请选择要操作的数据！');
		return;
	}
	var zyhdids = _split = "";
	for (var i = 0; i<rows.length; i++){
		zyhdids += _split+rows[i].zyhdid;
		_split = ",";
	}
	subcheck(zyhdids,3,2);
}
/**
 * 一键审核提交
 */
function subcheck(zyhdids,fromstate,tostate){
	$.messager.confirm('确认对话框', '您确认将所选择的通过审核吗？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/zyhd/checkAll'),
				data: {zyhdids : zyhdids,fromstate: fromstate, tostate:tostate },
				success: function(data){
					if(data.success=="success"){
						$.messager.alert("提示", data.msg);
						$('#zyhd').datagrid('reload');
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
//function addzy(index){
//	var rows = $('#zyhd').datagrid('getRows');
//	var row = rows[index];//WHdg2.getRowData(index);
//	var zyhdid = row.zyhdid;
//
//	$('#addzy iframe').attr('src', getFullUrl("/admin/ent/entsrc?reftype=2016112800000002&refid="+zyhdid+"&canEdit=true"));
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
	var row = $("#zyhd").datagrid("getRows")[idx];
	WhgComm.editDialog('${basePath}/admin/train/resource/view/list?reftype=7&id=' + row.zyhdid);
}

</script>
</head>
<body>
		
		
		<!-- 培训管理页面 -->
		<table id="zyhd"></table>
		
		<!-- 查询栏 -->
		<div id="zyhdTool" class="grid-control-group" style="display: none;" >
			<div style="line-height: 32px;">
			<shiro:hasPermission name="${resourceid}:add"><a href="#" class="easyui-linkbutton" iconCls="icon-add" size='small' id="btn" onclick="addzyhd()">添加</a></shiro:hasPermission>
			<shiro:hasPermission name="${resourceid}:checkgo"><a href="javascript:void(0)" class="easyui-linkbutton" size='small' onclick="allcheck()">批量审核</a></shiro:hasPermission> 
			<shiro:hasPermission name="${resourceid}:publish"><a href="javascript:void(0)" class="easyui-linkbutton" size='small' onclick="allpublish()">批量发布</a></shiro:hasPermission> 
			<shiro:hasPermission name="${resourceid}:publishoff"><a href="javascript:void(0)" class="easyui-linkbutton" size='small' onclick="allnoPublish()">批量取消发布</a></shiro:hasPermission> 
			<shiro:hasPermission name="${resourceid}:checkoff"><a href="javascript:void(0)" class="easyui-linkbutton" size='small' onclick="allback()">批量打回</a></shiro:hasPermission>
			</div>
			<div style="padding-top: 5px">

				<input class="easyui-combobox" name="zyhdarea" style="width: 200px; height:28px" data-options="editable:true, valueField:'id', textField:'text',prompt:'请选择区域', data:WhgSysData.getTypeData('6')"/>

				<input class="easyui-combobox" name="zyhdtype" style="width: 200px; height:28px" data-options="editable:true, valueField:'id', textField:'text',prompt:'志愿活动类型', data:WhgSysData.getTypeData('13')"/>

				<input class="easyui-textbox" name="zyhdtitle" style="width: 200px; height:28px" data-options="validType:'length[1,30]',prompt:'请选输入活动标题'"/>

				<select class="easyui-combobox" name="zyhdstate" style="width: 200px; height:28px" >
					<option value="">请选择状态</option>
					<option value="1">待审核</option>
					<option value="2">已审核</option>
					<option value="3">已发布</option>
				</select>
				<a href="javascript:void(0)" class="easyui-linkbutton search" iconCls="icon-search">查询</a>
			</div>
			
		</div>
		      
		<!-- 操作按钮 -->
		<div id="zyhdOPT" style="display:none">
			<shiro:hasPermission name="${resourceid}:edit"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="zyhdstate" validVal="1" method="editzyhd">修改</a></shiro:hasPermission> 
			<shiro:hasPermission name="${resourceid}:del"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="zyhdstate" validVal="1" method="delzyhd">删除</a></shiro:hasPermission>
			<shiro:hasPermission name="${resourceid}:view"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true"  method="look">查看详情</a></shiro:hasPermission>
			<shiro:hasPermission name="${resourceid}:checkon"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="zyhdstate" validVal="1" method="check">审核</a></shiro:hasPermission>
			<shiro:hasPermission name="${resourceid}:checkoff"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="zyhdstate" validVal="2" method="nocheck">打回</a></shiro:hasPermission>
			<shiro:hasPermission name="${resourceid}:publish"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="zyhdstate" validVal="2" method="publish">发布</a></shiro:hasPermission> 
			<shiro:hasPermission name="${resourceid}:publishoff"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="zyhdstate" validVal="3" method="nopublish">取消</a></shiro:hasPermission>
			<shiro:hasPermission name="${resourceid}:edit"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" method="addzy">资源管理</a></shiro:hasPermission>  
		</div> 
				
	 <!--  培训管理dialog -->
		 
		 <div id="zyhd_edit" class="none" style="display:none"  data-options=" fit:true" >
		    		
			 <form id="ff" method="post" enctype="multipart/form-data" >
				<!-- 隐藏域  -->
				<input type="hidden" id="zyhdid" name="zyhdid" value="" />
		  		<input type="hidden" id="zyhdpic" name="zyhdpic" value=""/>
		  		<input type="hidden" id="zyhdbigpic" name="zyhdbigpic" value=""/>
				<input type="hidden" id="zyhdcontent" name="zyhdcontent" value=""/>
				
				
				<div class="main">
					<div class="row">
		    			<div>志愿活动区域:</div>
		    			<div>
		    				<input id="zyhdarea" class="easyui-combobox" name="zyhdarea" style="width:100%;height:32px;" data-options="editable:false,required:true, valueField:'id',textField:'text', data:WhgSysData.getTypeData('6')"/>
		    			</div>
		    		</div>
					<div class="row">
		    			<div>志愿活动分类:</div>
		    			<div>
		    				<input id="zyhdtype" class="easyui-combobox" name="zyhdtype" style="width:100%;height:32px;" data-options="editable:false, valueField:'id',textField:'text',data:WhgSysData.getTypeData('13'),required:true"/>
		    			</div>
		    		</div>
		    		<div class="row">
		    			<div>详情标题:</div>
			    		<div>
							<input id="zyhdtitle" class="easyui-textbox" name="zyhdtitle" style="width:100%;height:32px;" data-options="required:true, validType:'length[1,60]'"/>
			    		</div>
		    		</div>
		    		<div class="row">
		    			<div>列表短标题:</div>
			    		<div>
							<input id="zyhdshorttitle" class="easyui-textbox" name="zyhdshorttitle" style="width:100%;height:32px;" data-options="required:true, validType:'length[1,60]'"/>
			    		</div>
		    		</div>
		    		<div class="row">
		    			<div>服务地区:</div>
			    		<div>
							<input id="zyhdscope" class="easyui-textbox" name="zyhdscope" style="width:100%;height:32px;" data-options="required:true,validType:'length[1,30]'"/>
			    		</div>
		    		</div>
		    		<div class="row">
		    			<div>发起文化馆:</div>
			    		<div>
							<input id="zyhdstart" class="easyui-textbox" name="zyhdstart" style="width:100%;height:32px;" data-options="required:true,validType:'length[1,30]'"/>
			    		</div>
		    		</div>
		    		<div class="row">
		    			<div>列表页图:</div>
		    			<div>
							<input id="zyhdpic_up"  class="easyui-filebox" name="zyhdpic_up" style="width:90%;height:32px;" data-options="validType:'isIMG[\'zyhdpic_up\']', buttonText:'选择图片', accept:'image/jpeg,image/peg, image/png',prompt:'图片尺寸:380x240'">
							<a id="btn_pic" class="easyui-linkbutton" data-options="plain:true," iconCls="icon-clear" style="width:9%;height:32px;">清除</a>
		    			</div>
		    		</div>
		    		<div class="row">
		    			<div>详情页图:</div>
		    			<div>
							<input id="zyhdbigpic_up"  class="easyui-filebox" name="zyhdbigpic_up" style="width:90%;height:32px;" data-options="validType:'isIMG[\'zyhdbigpic_up\']', buttonText:'选择图片', accept:'image/jpeg,image/peg, image/png',prompt:'图片尺寸:400x250'">
							<a id="btn_pic1" class="easyui-linkbutton" data-options="plain:true," iconCls="icon-clear" style="width:9%;height:32px;">清除</a>
		    			</div>
		    		</div>
		    		<div class="row">
		    			<div>活动地址:</div>
			    		<div>
							<input id="zyhdaddr" class="easyui-textbox" name="zyhdaddr" style="width:100%;height:32px;" data-options="required:true,validType:'length[1,30]'"/>
			    		</div>
		    		</div>
		    		
		    		<div class="row">
		    			<div>报名开始日期:</div>
		    			<div><input id="zyhdsdate" name="zyhdsdate" type="text" class="easyui-datetimebox" data-options="editable:false, required:true"  style="height: 35px; width: 100%"></div>
		    		</div>
		    		<div class="row">
		    			<div>报名结束日期:</div>
		    			<div><input id="zyhdedate" name="zyhdedate" type="text" class="easyui-datetimebox" data-options="editable:false, required:true" style="height: 35px; width: 100%"></div>
		    		</div>
		    		
		    		<!-- <div class="row">
		    			<div>详请内容：</div>
		    			<div>
		    				<input class="easyui-textbox" id="zyhdcontent" name="zyhdcontent" data-options="validType:'length[0,500]',multiline:true " style="width:100%;height:50px;">
		    			</div>
		    		</div> -->
		    		<div class="row">
		    			<div>详请内容:</div>
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