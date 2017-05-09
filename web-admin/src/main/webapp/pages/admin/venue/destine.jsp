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
var winform = new WhuiWinForm("#des_ff",{height:250});
var winform1 = new WhuiWinForm("#no_ff",{height:250});

$(function(){
	$("#vebTool").css('visibility','visible');
})




/**删除培训
 */
function delveb(index){
	var row = WHdg.getRowData(index);
	var vebid = row.vebid;
	$.messager.confirm('确认对话框', '您确认要删除吗？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/ven/deldes'),
				data: {vebid : vebid},
				success: function(data){
				//	alert(JSON.stringify(data));
					if(data.success == '0'){
						$('#veb').datagrid('reload');
					   }else{
						   $.messager.alert('提示', '操作失败。原因：'+data.errmsg, 'error');
					   }
				}
			});
		}
	});
}
/**审核失败原因框显示与隐藏*/
function can(){
	if ($("#vebstate").combobox('getValue') == "1") {
		$("#vebcheckmsg").textbox('disable');
		$("#vebcheckmsg").textbox('setValue',"");
	}else{
		$("#vebcheckmsg").textbox('enable');
		$("#vebcheckmsg").textbox('setValue',"该时段已经被预定，审核不通过！");
	}
}

/**
 * 是否能够审核通过
 */
function checkveb(index){
	var row = WHdg.getRowData(index);
	var vebvenid = row.vebvenid;
	var vebday = row.vebday;
	var vebstime = row.vebstime;
	var vebetime = row.vebetime;
	var vebid = row.vebid;
	var venname = row.venname;
	var name = row.nickname;
	var vebuid = row.vebuid;
	$("#vebstate").combobox({
		onChange:can
	});
	
	$.ajax({
		type : "post",
		url : getFullUrl('/admin/ven/isCanCheck'),
		data : {vebid:vebid,vebvenid:vebvenid,vebday:vebday,vebstime:vebstime,vebetime:vebetime},
		success :  function(data){
			if (data != 0 || data != "0") {
				$.messager.alert("提示","该时段已经被预定，不能通过审核！");
				$('#veb').datagrid('reload');
				return ;
			}else{
				winform.openWin();
				winform.setWinTitle("审核预定记录");
				
				var frm = winform.getWinBody().find('form').form({
					url : getFullUrl('/admin/ven/checkdes'),
					onSubmit : function(param) {
						param.vebid = vebid;
						param.venname = venname;
						param.name = name;
						param.vebuid = vebuid;
						return $(this).form('enableValidation').form('validate');
					},
					success : function(data) {
						var Json = $.parseJSON(data);
				 			if(Json && Json.success == '0'){
				 			$.messager.alert('提示', '操作成功！');
							$('#veb').datagrid('reload');
							winform.closeWin();
						} else {
							$.messager.alert('提示', '操作失败！');
						}
					}
				});
				frm.form("clear");
				winform.setFoolterBut({onClick:function(){
					frm.submit();
				}});
			}
		}
	});
}
 
 /**批量不通过*/
function nocheck(){
	var rows={};
	rows = $("#veb").datagrid("getSelections");
	if (rows == "" || rows == null) {
		$.messager.alert('提示', '请选择要操作的数据！');
		return;
	}
	var vebids = _split = "";//id1,id2,id3
	for (var i = 0; i<rows.length; i++){
		vebids += _split+rows[i].vebid;
		_split = ",";
	}
	winform1.openWin();
	winform1.setWinTitle("审核预定记录");
	var frm = winform1.getWinBody().find('form').form({
		url : getFullUrl('/admin/ven/allcheckdes'),
		onSubmit : function(param) {
			param.vebids = vebids;
			param.fromstate = 0;
			param.tostate = 2;
			return $(this).form('enableValidation').form('validate');
		},
		success : function(data){
			//alert(JSON.stringify(data));
			var Json = $.parseJSON(data);
 			if(Json && Json.success == '0'){
				$.messager.alert('提示', '操作成功！');
				$('#veb').datagrid('reload');
				winform1.closeWin();
			}else {
				$.messager.alert('提示', '操作失败！');
			}
			
		}
		
	});
	winform1.setFoolterBut({onClick:function(){
		frm.submit();
	}});
	
 }

 /**
  * 初始培训批次表格
  */
  /**如果是内部员工，在后面显示描述信息*/
 function _innerFmt(v, r, i){
	 var txt = yesNoFMT(v,r,i);
	 if (v){
		 if(r.innerdesc){
		 	txt += "("+(r.innerdesc||"")+")";
		 }
	 }
	 return txt;
 }
  
 $(function(){
 	//定义表格参数
	var options = {
			title: '场馆预定',	
			url: getFullUrl('/admin/ven/finddestine'),
			rownumbers:true,
			singleSelect:false,
			columns: [[
			{field:'ck',checkbox:true},
			{field:'vebid',title:'预定号',width:80, sortable:true},
			{field:'venname',title:'场馆名称',width:80},
			{field:'nickname',title:'预定用户',width:80 , formatter:function(v, r, i){
				if (r.vebuid == 'admin') return "Administrator";
				else return v;
			}},
			{field:'isinner',title:'是否是内部员工',width:80,formatter: _innerFmt},
			
			{field:'vebday',title:'预定时间',width:80, formatter: dateFMT},
			{field:'vebstime',title:'预定时段开始时间',width:80},
			{field:'vebetime',title:'预定时段结束时间',width:80},
			{field:'vebstate',title:'状态',width:80,  formatter: desStateFMT},
		//	
	        {field:'opt', title:'操作', formatter: WHdg.optFMT, optDivId:'vebOPT'}
			]]
	};

	//初始表格
	WHdg.init('veb', 'vebTool', options);
	

	winform.init();
	winform1.init();
 });
 
 
	
</script>
</head>
<body>
		<!-- 培训管理页面 -->
		<table id="veb"></table>
		
		<!-- 查询栏 -->
		<div id="vebTool" class="grid-control-group" style="display: none;" >
			<div>
				<shiro:hasPermission name="${resourceid}:checkon"><a href="javascript:void(0)" class="easyui-linkbutton" size='small' onclick="nocheck()">批量不通过</a></shiro:hasPermission>
			</div>
			<div style="padding-top: 5px">  
				场馆名称:
				<input class="easyui-combobox" name="venname" style="height: 32px; width: 150px" data-options="valueField:'venname',textField:'venname',url:'${basePath}/admin/ven/findName'"/>
				预定日期：
				<input name="vebday" class="easyui-datebox" style="height: 32px; width: 150px" data-options="" ></input>
				预定时段：
				<input name="vebstime" class="easyui-timespinner" style="height: 32px; width: 150px" data-options="min:'00:00',showSeconds:false"></input>
				到
				<input name="vebetime" class="easyui-timespinner" style="height: 32px; width: 150px" data-options="min:'00:00',showSeconds:false"></input>
				<br/>
				预定用户:
				<input class="easyui-combobox" name="nickname" style="height: 32px; width: 150px" data-options="valueField:'nickname',textField:'nickname',url:'${basePath}/admin/ven/findUser'"/>
				状态：
				<select class="easyui-combobox" name="vebstate" style="height: 32px; width: 80px" data-options="editable:false">
					<option value="">全部</option>
					<option value="0">未审核</option>
					<option value="1">通过</option>
					<option value="2">未通过</option>
				</select>
				<a href="javascript:void(0)" class="easyui-linkbutton search" iconCls="icon-search">查询</a>
			</div> 
			
		</div>
		      
		<!-- 操作按钮 -->
		<div id="vebOPT" style="display:none">
			<!-- <a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" method="delveb">删除</a> -->
			<shiro:hasPermission name="${resourceid}:checkon"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="vebstate" validVal="0" method="checkveb">审核</a></shiro:hasPermission>
		</div> 
			
			
		<!--  培训报名编辑dialog -->
		 <div id="des_ff" class="none" style="display: none" >
			<form method="post">
				<div class="main">
		    		 <div class="row">
		    			<div>审核状态:</div>
			    		<div>
							<select id="vebstate" class="easyui-combobox"
								name="vebstate" style="height: 35px; width: 90%"
								data-options="editable:false,required:true">
								<option value="1">通过</option>
								<option value="2">不通过</option>
							</select>
			    		</div>
		    		 </div>
		    		 <div class="row">
		    			<div>审核不通过原因:</div>
			    		<div>
			    			<input class="easyui-textbox" id="vebcheckmsg" name="vebcheckmsg" data-options="validType:'length[0,200]',multiline:true " style="width:90%;height:35px;">
			    		</div>
		    		 </div>
		    		 <%-- <div class="row">
		    			<div>关联培训:</div>
			    		<div>
							<input style="width:90%;height:32px;" class="easyui-combobox" name="vebtrainid" data-options="valueField:'traid', textField:'tratitle', url:'${basePath}/admin/ven/findTra'"/>
			    		</div>
		    		 </div>
		    		 <div class="row">
		    			<div>关联活动:</div>
			    		<div>
							<input style="width:90%;height:32px;" class="easyui-combobox" name="vebactivid" data-options="valueField:'actvid', textField:'actvtitle', url:'${basePath}/admin/ven/findAct'"/>
			    		</div>
		    		 </div> --%>
				</div>
			</form>
		</div>	
		
		
		<!-- 批量不通过 -->	
		 <div id="no_ff" class="none" style="display: none" >
			<form method="post">
				<div class="main">
		    		 <div class="row">
		    			<div>审核不通过原因:</div>
			    		<div>
			    			<input class="easyui-textbox" name="vebcheckmsg" value='该时段已经被预定，审核不通过！' data-options="validType:'length[0,200]',multiline:true" style="width:90%;height:35px;">
			    		</div>
		    		 </div>
				</div>
			</form>
		</div>		
	
		
</body>
</html>