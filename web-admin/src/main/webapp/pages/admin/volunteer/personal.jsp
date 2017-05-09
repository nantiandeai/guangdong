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
var winform = new WhuiWinForm("#personal_edit",{height:250});

$(function(){
	$("#personalTool").css('visibility','visible');
})

/**
 * 初始化表格
 */
 $(function(){
	 var options = {
			 title : '先进个人管理',
			 url : getFullUrl('/admin/volun/findPerson'),
			 rownumbers:true,
			 singleSelect:false,
			 columns: [[
				{field:'ck',checkbox:true},
				{field:'zyfcgrshorttitle',title:'列表短标题',width:80, sortable:true},
				{field:'zyfcgrjoinact',title:'参与活动',width:80, sortable:true},
				{field:'zyfcgrpic',title:'列表图',width:80,formatter:imgFMT},  
				{field:'zyfcgrzc',title:'文艺专长',width:80, sortable:true},
		        {field:'opt', title:'操作', formatter: WHdg.optFMT, optDivId:'personalOPT'}
			]]
	 }
	 
	 //初始化表格
	 WHdg.init('personal', 'personalTool', options);
	 //初始弹出框
	 winform.init();
	 
	//清除文件框的值
	$('#btn_pic').bind('click', function(){  
		$("#zyfcgrpic_up").filebox('clear');
    });    
	$('#btn_pic2').bind('click', function(){  
		$("#zyfcgrbigpic_up").filebox('clear');
	});   
	 
 })
 
/** 添加先进人才 */
function addPersonal(){
	//弹出窗体
	winform.openWin();
	//设置标题
	winform.setWinTitle('添加先进人才');
	//显示提交按钮
	winform.getWinFooter().find("a:eq(0)").show();
	//定义表单并初始化
	var frm = winform.getWinBody().find('form').form({
		url : getFullUrl('/admin/volun/savePerson'),
		onSubmit : function(param){
			//表单验证
			var _is = $(this).form("enableValidation").form("validate");
			if(!_is){
				//验证不通过，阻止表单重复提交
				winform.oneClick(function(){ frm.submit(); });
			}else{
				$.messager.progress();
			}
			return _is;
		},
		success : function(data){
			$.messager.progress('close');
			var Json = $.parseJSON(data);		
			//alert(JSON.stringify(data));
			if(Json && Json.success == "0"){
				$("#personal").datagrid('reload');
				$.messager.alert('提示','操作成功');
				//关闭窗体
				winform.closeWin();
			}else{
				$.messager.alert('提示','操作失败');
				//验证不通过，防止表单重复提交
				winform.oneClick(function(){ frm.submit(); });
			}
		}
	})
	//清空表单
	frm.form('clear');
	//防止表单重复提交
	winform.oneClick(function(){ frm.submit(); });
}

/**修改先进人才*/
function editVolun(index){
	//获取所点击行的对象
	var row = WHdg.getRowData(index);
	//弹出窗体
	winform.openWin();
	//设置标题
	winform.setWinTitle("修改先进人才");
	//显示提交按钮
	winform.getWinFooter().find("a:eq(0)").show();
	//初始化表单
	var frm = winform.getWinBody().find('form').form({
		url : getFullUrl('/admin/volun/savePerson'),
		onSubmit : function(param){
			//表单验证
            var _is = $(this).form('enableValidation').form('validate');
            if (!_is) {
            	//表单验证不通过，阻止重复提交
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
				$('#personal').datagrid('reload');
				$.messager.alert('提示', '操作成功!');
				//关闭窗体
				winform.closeWin();
			   }else{
				   $.messager.alert('提示', '操作失败!');
				   //操作失败，阻止重复提交
				   winform.oneClick(function(){ frm.submit(); });
			   }
		}
	});
	//清空表单
	frm.form("clear");
	//格式化日期
	var _data = $.extend({}, row,
			{
		zyfcgrfwtime : datetimeFMT(row.zyfcgrfwtime),
		zyfcgrjrtime : datetimeFMT(row.zyfcgrjrtime),
	});
	//图片处理
	_showImg(row);
	//设置表单值
	frm.form('load',_data);
	//防止表单的重复提交
	winform.oneClick(function(){ frm.submit(); });
}

//处理编辑时显示图片
function _showImg(data){
	$(".img_div").remove();
	//document.getElementById('fileName').innerHTML = "";	
	
	var imgDiv = '<div class="row img_div">'
		+'<div></div>'
		+'<div><img width="200" height="150"> </div> '
		+'</div>';
		
	if (data.zyfcgrpic){
		var imgrow = $("[name$='zyfcgrpic_up']").parents(".row");
		imgrow.after(imgDiv);
		imgrow.next(".img_div").find("div img").attr("src",getFullUrl(data.zyfcgrpic));	
	}
	if (data.zyfcgrbigpic){
		var imgrow = $("[name$='zyfcgrbigpic_up']").parents(".row");
		imgrow.after(imgDiv);
		imgrow.next(".img_div").find("div img").attr("src",getFullUrl(data.zyfcgrbigpic));	
	}
	 
}

/**删除先进个人*/
function delVolun(index){
	//获取选中行对象
	var row = WHdg.getRowData(index);
	var zyfcgrid = row.zyfcgrid;
	$.messager.confirm('确认对话框', '您确认要删除吗？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/volun/delPerson'),
				data: {zyfcgrid : zyfcgrid},
				success: function(data){
				//	alert(JSON.stringify(data));
					if(data.success == '0'){
						$('#personal').datagrid('reload');
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
	//获取选中行的对象
	var row = WHdg.getRowData(index);
	//弹出窗体
	winform.openWin();
	//设置标题
	winform.setWinTitle("查看详情");
	//隐藏提交按钮
	winform.getWinFooter().find("a:eq(0)").hide();
	//初始化表单
	var frm = winform.getWinBody().find('form').form({
	});
	//清除表单
	frm.form("clear");
	//图片处理
	_showImg(row);
	//设置表单值
	var _data = $.extend({}, row,
			{
		zyfcgrfwtime : datetimeFMT(row.zyfcgrfwtime),
		zyfcgrjrtime : datetimeFMT(row.zyfcgrjrtime),
	});
	frm.form("load", _data);
	
	$('#personal').datagrid('reload');
}


/**审核*/
function check(index){
	var row = WHdg.getRowData(index);
	var zyfcgrid = row.zyfcgrid;
	//审核处理
	var title = "审核";
	docheck(title,zyfcgrid,1,2);
}
/**发布*/
function publish(index){
	var row = WHdg.getRowData(index);
	var zyfcgrid = row.zyfcgrid;
	var title = "发布";
	docheck(title,zyfcgrid,2,3);
}
/**打回审核*/
function nocheck(index){
	var row = WHdg.getRowData(index);
	var zyfcgrid = row.zyfcgrid;
	var title = "打回";
	docheck(title,zyfcgrid,2,1);
}
/**取消发布*/
function nopublish(index){
	var row = WHdg.getRowData(index);
	var zyfcgrid = row.zyfcgrid;
	var title = "取消发布";
	docheck(title,zyfcgrid,3,2);
}

/** 审核事件提交处理 */
function docheck(title,zyfcgrid,fromstate,tostate){
	$.messager.confirm('确认对话框', '您确认将所选择的'+title+'吗？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/volun/checkPerson'),
				data: {zyfcgrid : zyfcgrid,fromstate:fromstate,tostate:tostate},
				success: function(data){
					//alert(JSON.stringify(data));
					if(data.success == 'success'){
						$('#personal').datagrid('reload');
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
	//获取选中框的行
	var rows={};
	rows = $("#personal").datagrid("getSelections");
	if (rows == "" || rows == null) {
		$.messager.alert('提示', '请选择要操作的数据！');
		return;
	}
	//拼装id
	var zyfcgrids = _split = "";
	for (var i = 0; i<rows.length; i++){
		zyfcgrids += _split+rows[i].zyfcgrid;
		_split = ",";
	}
	//一键审核
	subcheck(zyfcgrids,1,2);
}

/**批量发布*/
function allpublish(){
	//获取选中框的行
	var rows={};
	rows = $("#personal").datagrid("getSelections");
	if (rows == "" || rows == null) {
		$.messager.alert('提示', '请选择要操作的数据！');
		return;
	}
	//拼装id
	var zyfcgrids = _split = "";
	for (var i = 0; i<rows.length; i++){
		zyfcgrids += _split+rows[i].zyfcgrid;
		_split = ",";
	}
	//一键审核
	subcheck(zyfcgrids,2,3);
}
/**批量打回*/
function allback(){
	//获取选中框的行
	var rows={};
	rows = $("#personal").datagrid("getSelections");
	if (rows == "" || rows == null) {
		$.messager.alert('提示', '请选择要操作的数据！');
		return;
	}
	var zyfcgrids = _split = "";
	for (var i = 0; i<rows.length; i++){
		zyfcgrids += _split+rows[i].zyfcgrid;
		_split = ",";
	}
	//一键审核
	subcheck(zyfcgrids,2,1);
}
/**批量取消发布*/
function allnoPublish(){
	//获取选中框的行
	var rows={};
	rows = $("#personal").datagrid("getSelections");
	if (rows == "" || rows == null) {
		$.messager.alert('提示', '请选择要操作的数据！');
		return;
	}
	//拼装id
	var zyfcgrids = _split = "";
	for (var i = 0; i<rows.length; i++){
		zyfcgrids += _split+rows[i].zyfcgrid;
		_split = ",";
	}
	//一键审核
	subcheck(zyfcgrids,3,2);
}

/**
 * 一键审核提交 
 */
function subcheck(zyfcgrids,fromstate,tostate){
	$.messager.confirm('确认对话框', '您确认将所选择的通过审核吗？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/volun/checkAllPer'),
				data: {zyfcgrids : zyfcgrids,fromstate: fromstate, tostate:tostate },
				success: function(data){
					if(data.success=="success"){
						$.messager.alert("提示", data.msg);
						$('#personal').datagrid('reload');
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
//	var rows = $('#personal').datagrid('getRows');
//	var row = rows[index];//WHdg2.getRowData(index);
//	var zyfcgrid = row.zyfcgrid;
//
//	$('#addzy iframe').attr('src', getFullUrl("/admin/ent/entsrc?reftype=2016112900000002&refid="+zyfcgrid+"&canEdit=true"));
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
	var row = $("#personal").datagrid("getRows")[idx];
	WhgComm.editDialog('${basePath}/admin/train/resource/view/list?reftype=10&id=' + row.zyfcgrid);
}
</script>
</head>
<body>
		<!-- 先进个人管理页面 -->
		<table id="personal"></table>
		
		<!-- 查询栏 -->
		<div id="personalTool" class="grid-control-group" style="display: none;" >
			<div style="line-height: 32px;">
				<shiro:hasPermission name="${resourceid}:add"><a href="#" class="easyui-linkbutton" iconCls="icon-add" size='small' id="btn" onclick="addPersonal()">添加</a></shiro:hasPermission>
				<shiro:hasPermission name="${resourceid}:checkgo"><a href="javascript:void(0)" class="easyui-linkbutton" size='small' onclick="allcheck()">批量审核</a></shiro:hasPermission> 
				<shiro:hasPermission name="${resourceid}:publish"><a href="javascript:void(0)" class="easyui-linkbutton" size='small' onclick="allpublish()">批量发布</a></shiro:hasPermission> 
				<shiro:hasPermission name="${resourceid}:publishoff"><a href="javascript:void(0)" class="easyui-linkbutton" size='small' onclick="allnoPublish()">批量取消发布</a></shiro:hasPermission> 
				<shiro:hasPermission name="${resourceid}:checkoff"><a href="javascript:void(0)" class="easyui-linkbutton" size='small' onclick="allback()">批量打回</a></shiro:hasPermission>
			</div>
			<div style="padding-top: 5px">
				<%--先进个人标题:--%>
				<input class="easyui-textbox" name="zyfcgrtitle" style="width: 200px; height:28px"  data-options="validType:'length[1,30]',prompt:'请输入标题'"/>
				<%--状态:--%>
				<select class="easyui-combobox" name="zyfcgrstate" style="width: 200px; height:28px" >
					<option value="">请选择状态</option>
					<option value="1">待审核</option>
					<option value="2">已审核</option>
					<option value="3">已发布</option>
				</select>
				<a href="javascript:void(0)" class="easyui-linkbutton search" iconCls="icon-search">查询</a>
			</div>
			
		</div>
		      
		<!-- 操作按钮 -->
		<div id="personalOPT" style="display:none">
			<shiro:hasPermission name="${resourceid}:edit"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="zyfcgrstate" validVal="1" method="addzy">资源管理</a></shiro:hasPermission> 
			<shiro:hasPermission name="${resourceid}:edit"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="zyfcgrstate" validVal="1" method="editVolun">修改</a></shiro:hasPermission> 
			<shiro:hasPermission name="${resourceid}:del"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="zyfcgrstate" validVal="1" method="delVolun">删除</a></shiro:hasPermission>
			<shiro:hasPermission name="${resourceid}:view"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true"  method="look">查看详情</a></shiro:hasPermission>
			<shiro:hasPermission name="${resourceid}:checkon"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="zyfcgrstate" validVal="1" method="check">审核</a></shiro:hasPermission>
			<shiro:hasPermission name="${resourceid}:checkoff"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="zyfcgrstate" validVal="2" method="nocheck">打回</a></shiro:hasPermission>
			<shiro:hasPermission name="${resourceid}:publish"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="zyfcgrstate" validVal="2" method="publish">发布</a></shiro:hasPermission> 
			<shiro:hasPermission name="${resourceid}:publishoff"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="zyfcgrstate" validVal="3" method="nopublish">撤消发布</a></shiro:hasPermission>
		</div> 
		
		<!--  先进个人dialog -->
		<div id="personal_edit" class="none" style="display:none"  data-options=" fit:true" >
		    		
			 <form id="ff" method="post" enctype="multipart/form-data" >
				<!-- 隐藏域  -->
				<input type="hidden" id="zyfcgrid" name="zyfcgrid" value="" />
		  		<input type="hidden" id="zyfcgrpic" name="zyfcgrpic" value=""/>
				<input type="hidden" id="zyfcgrbigpic" name="zyfcgrbigpic" value=""/>
				
				
				<div class="main">
		    		<div class="row">
		    			<div>详情标题:</div>
			    		<div>
							<input id="zyfcgrtitle" class="easyui-textbox" name="zyfcgrtitle" style="width:100%;height:32px;" data-options="required:true, validType:'length[1,60]'"/>
			    		</div>
		    		</div>
		    		<div class="row">
		    			<div>列表短标题:</div>
			    		<div>
							<input id="zyfcgrshorttitle" class="easyui-textbox" name="zyfcgrshorttitle" style="width:100%;height:32px;" data-options="required:true, validType:'length[1,60]'"/>
			    		</div>
		    		</div>
		    		<div class="row">
		    			<div>文艺专长:</div>
			    		<div>
							<input id="zyfcgrzc" class="easyui-textbox" name="zyfcgrzc" style="width:100%;height:32px;" data-options="required:false"/>
			    		</div>
		    		</div>
		    		<div class="row">
		    			<div>工作单位:</div>
			    		<div>
							<input id="zyfcgrworkaddr" class="easyui-textbox" name="zyfcgrworkaddr" style="width:100%;height:32px;" data-options="required:false"/>
			    		</div>
		    		</div>
		    		<div class="row">
		    			<div>参与活动:</div>
			    		<div>
							<input id="zyfcgrjoinact" class="easyui-textbox" name="zyfcgrjoinact" style="width:100%;height:32px;" data-options="required:false"/>
			    		</div>
		    		</div>
		    		<div class="row">
		    			<div>加入时间:</div>
			    		<div>
			    			<input id="zyfcgrjrtime" name="zyfcgrjrtime" type="text" class="easyui-datetimebox" data-options="editable:false, required:false"  style="height: 35px; width: 100%"></input>
			    		</div>
		    		</div>
		    		<div class="row">
		    			<div>服务地区:</div>
			    		<div>
							<input id="zyfcgrscope" class="easyui-textbox" name="zyfcgrscope" style="width:100%;height:32px;" data-options="required:true"/>
			    		</div>
		    		</div>
		    		<div class="row">
		    			<div>服务时间:</div>
			    		<div>
							<input id="zyfcgrfwtime" name="zyfcgrfwtime" type="text" class="easyui-datetimebox" data-options="editable:false, required:true"  style="height: 35px; width: 100%"></input>
			    		</div>
		    		</div>
		    		<div class="row">
		    			<div>服务时长:</div>
			    		<div>
			    			<input id="zyfwhen"  name="zyfwhen" class="easyui-numberspinner" style=" height: 35px; width: 100% " data-options="min:1,max:100000000,editable:true,required:false">  
			    		</div>
		    		</div>
		    		
		    		<div class="row">
		    			<div>事迹材料:</div>
			    		<div>
							<input id="zyfcgrcontent" class="easyui-textbox" name="zyfcgrcontent" style="width:100%;height:32px;" data-options="required:false"/>
			    		</div>
		    		</div>
		    		<div class="row">
		    			<div>列表图片:</div>
		    			<div>
		    				<!-- <input id="zyfcgrpic_up"  class="easyui-textbox" name="zyfcgrpic_up" style="width:90%;height:32px;" data-options="required:true">
		    				<a href="javascript:void(0)" class="easyui-linkbutton" style="width:9%;height:32px;" data-options="iconCls:'icon-add'" onclick="chooseImg()">选择图片</a> -->
							<input id="zyfcgrpic_up"  class="easyui-filebox" name="zyfcgrpic_up" style="width:90%;height:32px;" data-options="validType:'isIMG[\'zyfcgrpic_up\']', buttonText:'选择图片', accept:'image/jpeg,image/peg, image/png',prompt:'图片尺寸:380x240'">
							<a id="btn_pic" class="easyui-linkbutton" data-options="plain:true," iconCls="icon-clear" style="width:9%;height:32px;">清除</a>
		    			</div>
		    		</div>
		    		<div class="row">
		    			<div>详情图片:</div>
		    			<div>
							<input id="zyfcgrbigpic_up"  class="easyui-filebox" name="zyfcgrbigpic_up" style="width:90%;height:32px;" data-options="validType:'isIMG[\'zyfcgrbigpic_up\']', buttonText:'选择图片', accept:'image/jpeg,image/peg, image/png',prompt:'图片尺寸:400x250'">
							<a id="btn_pic2" class="easyui-linkbutton" data-options="plain:true," iconCls="icon-clear" style="width:9%;height:32px;">清除</a>
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