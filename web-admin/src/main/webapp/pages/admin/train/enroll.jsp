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
<script>
var winform = new WhuiWinForm("#enrol_ff",{height:250});
var winform1 = new WhuiWinForm("#noenrol_ff",{height:250});
var send = new WhuiWinForm("#send_ff",{height:250});
/**下载文件*/
function downfile(idx){
	var row = WHdg.getRowData(idx);
	if (!row.enrfilepath){
		alert("not path");
		return;
	}
	var downURL =getFullUrl("/whtools/downFile?filePath="+row.enrfilepath);
	window.location = downURL;
}

/**发送面试通知*/
function sendTZ(index){
	var row = WHdg.getRowData(index);
	var enrtraid = row.enrtraid;
	var enruid = row.enruid;
	$.ajax({
		type: "POST",
		url: getFullUrl('/admin/train/isSendTZ'),
		data: {enrtraid : enrtraid,enruid : enruid},
		success: function(data){
			if(data.success == '0'){
				send.openWin();
				send.setWinTitle("发送面试通知");
				var frm = send.getWinBody().find('form').form({
					url : getFullUrl('/admin/train/sendFaceTZ'),
					onSubmit : function(param) {
						param.name = data.msg;
						param.phone = data.phone;
						return $(this).form('enableValidation').form('validate');
					},
					success : function(data) {
						var Json = $.parseJSON(data);
						//alert(JSON.stringify(data));
						if(Json.success == '0'){
							$.messager.alert('提示', '发送成功！');
							$('#enrollDG').datagrid('reload');
							send.closeWin();
						   }else{
							   $.messager.alert('提示', '操作失败。原因：'+data.msg, 'error');
						   }
					}
				});
				frm.form("clear");
				send.setFoolterBut({onClick:function(){
					frm.submit();
				}});
				
			   }else{
				   //如果没有电话号码，发送邮件通知
				   if (data.success == '2') {
					   send.openWin();
						send.setWinTitle("发送面试通知");
						var frm = send.getWinBody().find('form').form({
							url : getFullUrl('/admin/train/sendYJTZ'),
							onSubmit : function(param) {
								param.name = data.name;
								param.email = data.email;
								return $(this).form('enableValidation').form('validate');
							},
							success : function(data) {
								var Json = $.parseJSON(data);
								if(Json.success == '0'){
									$.messager.alert('提示', '发送成功！');
									$('#enrollDG').datagrid('reload');
									send.closeWin();
								   }else{
									   $.messager.alert('提示', '操作失败。原因：'+data.msg, 'error');
								   }
							}
						});
						frm.form("clear");
						send.setFoolterBut({onClick:function(){
							frm.submit();
						}}); 
					}else{
					$.messager.alert('提示', '用户没有填写电话或邮箱，不需要发送');
					}
				   
			   }
		}
	});
}

/** 删除方法 */
function delEnrol(index){
	var row = WHdg.getRowData(index);
	var enrid = row.enrid;
	//alert(JSON.stringify(row));
	$.messager.confirm('确认对话框', '您确认要删除吗？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/train/delEnroll'),
				data: {enrid : enrid},
				success: function(data){
					if(data.success == '0'){
						 $.messager.alert('提示', '操作成功！');
						$('#enrollDG').datagrid('reload');
						
					   }else{
						   $.messager.alert('提示', '操作失败。原因：'+data.errmsg, 'error');
					   }
				}
			});
		}
	});
}

/**
 * 审核报名
 */
/* var count =0; */
function checkEnrol(index){
	var rows = WHdg.getRowData(index);
	var enrid = rows.enrid;
	var enrtraid = rows.enrtraid;
	//var tratitle = rows.tratitle;
	var name = rows.nickname;
	var enruid = rows.enruid;
	$.ajax({
		type : "post",
		url : getFullUrl('/admin/train/enrollcount'),
		data : {enrtraid : enrtraid},
		success :  function(data){
			if (data >= rows.traenrollimit) {
				$.messager.alert('提示', '报名人数已满！');
				return;
			}else{
				winform.openWin();
				winform.setWinTitle("审核报名人员");
				//判断是否隐藏审核失败原因输入框
				$("#state").combobox({
					onChange:function ys(){
						if ($("#state").combobox('getValue') != "3") {
							$("#enrstatemsg").textbox('disable');
							$("#enrstatemsg").textbox('setValue',"");
						}else{
							$("#enrstatemsg").textbox('enable');
							$("#enrstatemsg").textbox('setValue',"报名人数已满，审核不通过！");
						}
					}
				});
				var frm = winform.getWinBody().find('form').form({
					url : getFullUrl('/admin/train/checkEnroll'),
					onSubmit : function(param) {
						param.enrid = enrid;
						param.enrtraid = enrtraid;
						param.name = name;
						param.enruid = enruid;
						return $(this).form('enableValidation').form('validate');
					},
					success : function(data) {
						var Json = $.parseJSON(data);
				 			if(Json && Json.success == '0'){
				 			$.messager.alert('提示', '操作成功！');
							$('#enrollDG').datagrid('reload');
							winform.closeWin();
						} else {
							$.messager.alert('提示', '操作无效！');
						}
					}
				});
				frm.form("clear");
				//frm.form("load", rows);
				winform.setFoolterBut({onClick:function(){
					frm.submit();
				}});
				
				
			}
		}
	});
}

/**
 * 判断是否能够进行审核
 */
function _validFun(index){
	var row = WHdg.getRowData(index);
	//alert(row.enrisa+'--'+row.enrisb);
	return row.enrstepstate == '1' && row.enrstate == '0';
}
/**
 * 判断是否能够进行发送面试通知
 */
function _validFun1(index){
	var row = WHdg.getRowData(index);
	//alert(row.enrstate+""+row.traisnotic);
	return (row.enrstate == '1' || row.enrstate == '2') && row.traisnotic == '1';
}

/**
 * 判断是否能够进行批量不审核
 */
function allCheck(index){
	var rows = {}
	var rows = $("#enrollDG").datagrid("getSelections");
	if (rows == "" || rows == null) {
		$.messager.alert('提示', '请选择要操作的数据！');
		return;
	}
	var enrids = _split = "";
	for (var i = 0; i<rows.length; i++){
		enrids += _split+rows[i].enrid;
		_split = ",";
	}
	
	var enruid = _split = "";
	for (var i = 0; i<rows.length; i++){
		enruid += _split+rows[i].enrid;
		_split = ",";
	}
	
	winform1.openWin();
	winform1.setWinTitle("审核培训报名记录");
	var frm = winform1.getWinBody().find('form').form({
		url : getFullUrl('/admin/train/checkAll'),
		onSubmit : function(param) {
			param.enrids = enrids;
			
			param.fromstate = 0;
			param.tostate = 3;
			var _is = $(this).form('enableValidation').form('validate');
			if (!_is) {
				winform1.oneClick(function(){ frm.submit(); });
			}
			return _is;
		},
		success : function(data){
			var Json = $.parseJSON(data);
 			if(Json && Json.success == '0'){
				$.messager.alert('提示', '操作成功！');
				$('#enrollDG').datagrid('reload');
				winform1.closeWin();
			}else {
				$.messager.alert('提示', '操作失败！');
				winform1.oneClick(function(){ frm.submit(); });
			}
			
		}
		
	});
	winform1.oneClick(function(){ frm.submit(); });
	
}


$(function(){
	//初始表格
	var options = {   
	    url:'${basePath}/admin/train/sreachenroll',  
	    title: '报名列表',
	    fit: true,
	    pagination: true,
	    pageSize: 10,
	    pageList: [10,20,50],
	    rownumbers:true,
		singleSelect:false,
	    columns:[[
			{field:'ck',checkbox:true},
		//	{field:'enrid',title:'报名标识',width:100},    
			{field:'tratitle',title:'培训名称',width:100},    
			{field:'nickname',title:'用户昵称',width:100,align:'right'},
			{field:'enrtype',title:'团队或个人',width:100,align:'right',formatter:typeFMT},
			{field:'enrisa',title:'是否完善资料', formatter:enrolllistFMT, width:100,align:'right'},
			{field:'enrisb',title:'是否已上传附件', formatter:enrolllistFMT, width:100,align:'right'},
			{field:'enrtime',title:'报名时间', formatter :datetimeFMT, width:100,align:'right'},
			{field:'enrstate',title:'状态', formatter:eStateFMT,width:100,align:'right'},
		//	{field:'enrstepstate',title:'报名是否完成', formatter:yesNoFMT,width:100,align:'right'},
			{field:'opt', title:'操作', formatter: WHdg.optFMT, optDivId:'enrollOPT'}
	    ]]    
	};
	//初始表格
	WHdg.init('enrollDG', 'enrollTool', options);
	//初始弹出框
	winform.init();
	winform1.init();
	send.init();
	
	//查询框中的培训管理数据表格
	var option = {
			//title: '培训管理',	
		    delay: 800,    
		    mode: 'remote',
			url: getFullUrl('/admin/trainManage/selectTrain'),
			idField: 'traid',    
		    textField: 'tratitle',
			queryParams: {stateArray:"3"},
			pagination: true,
		    pageSize: 10,
		    pageList: [10,20,50],
			columns: [[
			{field:'tratitle',title:'课程标题',width:80, sortable:true},
			{field:'tratyp',title:'培训类型',width:80, sortable:true, formatter: tratypFMT},
			{field:'traarttyp',title:'艺术类型',width:80, sortable:true, formatter: arttypFMT},
			{field:'traagelevel',title:'适合年龄',width:80, sortable:true, formatter:agelevelFMT},    
			{field:'tralevel',title:'课程级别',width:80, sortable:true, formatter:tralevelFMT},
			{field:'trasdate',title:'开始日期',width:200,formatter :datetimeFMT,sortable:true},
			{field:'traedate',title:'结束日期',width:200,formatter :datetimeFMT, sortable:true},
			{field:'trateacher',title:'授课老师名称',width:80},
	        {field:'trastate',title:'状态',width:80,formatter :traStateFMT}
			]],
			toolbar: "#testdiv"
	};
	 //
	 $('#enrtraid').combogrid(option); 
	 
	 $("#testdiv a.search").on("click", function(){
		 var _data = $("#testdiv").find("[name]").serializeArray();
		 var params = new Object();
		 for(var k in _data){
			 var ent = _data[k];
			 if (ent.value)
			 params[ent.name] = ent.value;
		 }
		 params.stateArray = "3";
		 
		 var grid = $('#enrtraid').combogrid("grid");
		 grid.datagrid({
			 url: getFullUrl('/admin/trainManage/selectTrain'),
			 queryParams: params
		 });
	 })
	
});




</script>
</head>
<body>
		<div id="testdiv" style="display: none">
		培训类型:
		<input class="easyui-combobox" name="tratyp" data-options="editable:false,valueField:'typid',textField:'typname',url:'${basePath}/comm/whtyp?type=2'"/>
		艺术类型: 
		<input class="easyui-combobox" name="traarttyp" data-options="editable:false,valueField:'typid',textField:'typname',url:'${basePath}/comm/whtyp?type=0'"/>
		<a id="search" class="easyui-linkbutton search" iconCls="icon-search">查询</a>
		</div>


		<table id="enrollDG"></table>
		
		<div id="enrollTool" class="grid-control-group" style="display: none;">
			<div>
				<shiro:hasPermission name="${resourceid}:checkon"><a href="javascript:void(0)" class="easyui-linkbutton" size='small' onclick="allCheck()">批量不通过</a></shiro:hasPermission>
			</div>
			<div>
				培训标题: <!-- <input class="easyui-combobox" name="traid" data-options="valueField:'traid',textField:'tratitle'" url='${basePath}/admin/trainManage/getAllTitle'/>   -->  
				<input class="easyui-combogrid" id="enrtraid" name="traid" style="width:600px;height:32px" data-options="editable:false"/>  
				状态：
				<select class="easyui-combobox" name="enrstate" >
					<option value="">选择状态</option>
					<option value="0">未审核</option>
					<option value="1">已审核</option>
					<option value="2">不需要</option>
					<option value="3">未通过</option>
				</select>
				
				<a href="javascript:void(0)" class="easyui-linkbutton search" iconCls="icon-search">查询</a>
			</div>
		</div>
		
		<div id="enrollOPT" style="display: none;">
			<shiro:hasPermission name="${resourceid}:view"><a class="easyui-linkbutton" validKey="enrisb" validVal="1" method="downfile">查看附件</a></shiro:hasPermission>
			<shiro:hasPermission name="${resourceid}:checkon"><a id="btn" class="easyui-linkbutton" data-options="plain:true" validFun="_validFun" method="checkEnrol">审核</a></shiro:hasPermission> 
			<shiro:hasPermission name="${resourceid}:view"><a id="btn" class="easyui-linkbutton" data-options="plain:true" validFun="_validFun1" method="sendTZ">发送面试通知</a></shiro:hasPermission> 
			<%-- <shiro:hasPermission name="${resourceid}:del"><a class="easyui-linkbutton" data-options="plain:true" validKey="enrstepstate" validVal="0" method="delEnrol">删除</a></shiro:hasPermission>  --%>
		</div> 
		
		<!--  培训报名编辑dialog -->
		 <div id="enrol_ff" class="none" style="display: none" >
			<form method="post">
				<div class="main">
		    		 <div class="row">
		    			<div>审核状态:</div>
			    		<div>
							<select id="state" class="easyui-combobox"
								name="state" style="height: 35px; width: 90%"
								data-options="editable:false,required:true">
								<option value="1">通过</option>
								<option value="3">不通过</option>
							<!-- <option value="2">不需要</option> -->	
							</select>
			    		</div>
		    		 </div>
		    		 <div class="row">
		    			<div>审核失败原因:</div>
			    		<div>
							<input class="easyui-textbox" id="enrstatemsg" name="enrstatemsg" data-options="validType:'length[0,200]',multiline:true, " style="width:90%;height:50px;">
			    		</div>
		    		 </div>
		    		 
				</div>
			</form>
		</div>	
		
		
		<!--  培训报名审核不通过 -->
		 <div id="noenrol_ff" class="none" style="display: none" >
			<form method="post">
				<div class="main">
		    		 <div class="row">
		    			<div>审核失败原因:</div>
			    		<div>
							<input class="easyui-textbox" name="enrstatemsg" value="报名人数已满，审核不通过！" data-options="validType:'length[0,50]',multiline:true, " style="width:90%;height:50px;">
			    		</div>
		    		 </div>
		    		 
				</div>
			</form>
		</div>	
		
		
		<!--  发送短信通知 -->
		 <div id="send_ff" class="none" style="display: none" >
			<form method="post">
				<div class="main">
		    		 <div class="row">
		    			<div>面试时间:</div>
			    		<div>
							<input name="date" class="easyui-datetimebox" data-options="editable:false, required:true"  style="height: 35px; width: 90%"></input>
			    		</div>
		    		 </div>
		    		 <div class="row">
		    			<div>面试地址:</div>
			    		<div>
							<input class="easyui-textbox" name="msg" data-options="required:true,validType:'length[0,50]',multiline:true, " style="width:90%;height:50px;"></input>
			    		</div>
		    		 </div>
		    		 
				</div>
			</form>
		</div>	
</body>
</html>