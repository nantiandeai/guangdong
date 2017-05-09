<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath();
	request.setAttribute("basePath", basePath);
%>
<%request.setAttribute("resourceid", request.getParameter("rsid"));%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>传承人管理</title>
<base href="${basePath }/">
<%@include file="/pages/comm/header.jsp"%>
<script type="text/javascript" charset="utf-8" src="${basePath}/static/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${basePath}/static/ueditor/ueditor.all.min.js"></script>
<script type="text/javascript" charset="utf-8" src="${basePath}/static/ueditor/lang/zh-cn/zh-cn.js"></script>


<script type="text/javascript">
var winform = new WhuiWinForm("#frm");

/**工具栏 search 加载数据*/
function datagridLoad(){ 
    var tb = $("#mingluTool");
    var datagrid=$("#mingluDiv");
    var params = {};
    tb.find("[name]").each(function(){
    	var value = $(this).val();
    	var name = $(this).attr("name");
    	params[name] = value;
    });
    this.queryParams = this.queryParams ||{};
    $.extend(this.queryParams, params);

    datagrid.datagrid({
        url :'./admin/feiyi/loadSuccessor',
        queryParams : this.queryParams
    });
}

//处理编辑时显示图片
function _showImg(data){
	$(".img_div").remove();
	var imgDiv = '<div class="row img_div">'
		+'<div></div>'
		+'<div><img id="img" width="200" height="150"> </div> '
		+'</div>';
	if(data.suorpic){
		var imgrow = $("[name$='image']").parents(".row");
		imgrow.after(imgDiv);
		$("#img").attr("src","${basePath }/"+data.suorpic);
	}
}

/*查找名录*/
function lookAll(idx) {
 	$("#Form .easyui-combobox").combobox({ disabled: true });  
	$("#Form .easyui-datetimebox").datetimebox({ disabled: true });
	$("#Form .easyui-textbox").textbox({ disabled: true });
	$("#Form .easyui-filebox").filebox("disable");
	UE.getEditor('traeditor').setDisabled('fullscreen');
	//UE.getEditor('traeditor1').setDisabled('fullscreen'); 
	$(".img_div").remove();
	winform.openWin();
	winform.setWinTitle("查找传承人信息");
	var row = WHdg.getRowData(idx);
	var frm = winform.getWinBody().find('form');
	
	frm.form("clear");
	
	_showImg(row);
	frm.form("load", row);
	UE.getEditor('traeditor').setContent(row.suorachv||"");
	//UE.getEditor('traeditor1').setContent(row.suorxus||"");
	winform.getWinFooter().find("a:eq(0)").hide();
} 

/**修改名录*/
function edit_minglu(idx) {
	$("#Form .easyui-combobox").combobox({ disabled: false });  
	$("#Form .easyui-datetimebox").datetimebox({ disabled: false });
	$("#Form .easyui-textbox").textbox({ disabled: false });
	$("#Form .easyui-filebox").filebox("enable");
	UE.getEditor('traeditor').setEnabled();
	//UE.getEditor('traeditor1').setEnabled();
	$(".img_div").remove();
	winform.openWin();
	winform.setWinTitle("修改传承人信息");
	var row = WHdg.getRowData(idx);
	winform.getWinFooter().find("a:eq(0)").show();
	//清空富文本
	 UE.getEditor('traeditor').setContent("");
	//UE.getEditor('traeditor1').setContent("");
	
	//显示富文本
/* 	UE.getEditor('traeditor').setContent(row.suorachv);
	UE.getEditor('traeditor1').setContent(row.suorxus); */
	var frm = winform.getWinBody().find('form').form({
		url : "./admin/feiyi/addOrEditsuccessor",
		onSubmit : function(param) {
			param.suorid = row.suorid;
			param.suorachv=UE.getEditor('traeditor').getContent();
			//param.suorxus=UE.getEditor('traeditor1').getContent();
			
			$("#traeditor").val(UE.getEditor('traeditor').getContent());
			//$("#traeditor1").val(UE.getEditor('traeditor1').getContent());
			var sb =$(this).form('enableValidation').form('validate');
			if(!sb){
				winform.oneClick(function(){ frm.submit(); });
			}
		    return sb;
		},
		success : function(data) {
			var Json = $.parseJSON(data);
			if (Json.success) {
				$.messager.alert('提示', '修改成功！');
				$("#mingluDiv").datagrid('reload');
				winform.closeWin();
			} else {
				$.messager.alert('提示', '修改失败,操作无效！');
				winform.oneClick(function(){ frm.submit(); });
			}
		}
	});
	
	frm.form("clear");
	_showImg(row);
	frm.form("load", row);
	//显示富文本
	UE.getEditor('traeditor').setContent(row.suorachv||"");
	//UE.getEditor('traeditor1').setContent(row.suorxus||"");
	winform.oneClick(function(){ frm.submit(); });
}

function _validFun(index){
	var row = WHdg.getRowData(index);
	return row.suorstate == 1 || row.suorstate == 0 ;
}

function _validFuncheck(index){
	var row = WHdg.getRowData(index);
	return row.suorstate == 1 || row.suorstate == 2 ;
}


/**增加名录*/
function addminglu(){
	$("#Form .easyui-combobox").combobox({ disabled: false });  
	$("#Form .easyui-datetimebox").datetimebox({ disabled: false });
	$("#Form .easyui-textbox").textbox({ disabled: false });
	$("#Form .easyui-filebox").filebox("enable");
	UE.getEditor('traeditor').setEnabled();
	//UE.getEditor('traeditor1').setEnabled();
	$(".img_div").remove();
	 winform.getWinFooter().find("a:eq(0)").show();
	winform.openWin();
	winform.setWinTitle("增加传承人信息");
	var frm = winform.getWinBody().find('form');
	//清空富文本
	UE.getEditor('traeditor').setContent("");
	//UE.getEditor('traeditor1').setContent("");
	

	frm.form({
		url : getFullUrl("./admin/feiyi/addOrEditsuccessor"),
		onSubmit : function(param) {
			//var param = frm.serialize();
			//富文本赋值
			//$("#traeditor").val(UE.getEditor('traeditor').getContent());
			//$("#traeditor1").val(UE.getEditor('traeditor1').getContent());
			
			param.suorachv = UE.getEditor('traeditor').getContent();
			//param.suorxus=UE.getEditor('traeditor1').getContent();
			var sb = $(this).form('enableValidation').form('validate');
			if(!sb){
				winform.oneClick(function(){ frm.submit(); });
			}
		    return sb;		
		},
		success : function(data) {
			var Json = $.parseJSON(data);
			if (Json.success) {
				$.messager.alert('提示', '增加成功！');
				$("#mingluDiv").datagrid('reload');
				winform.closeWin();
			} else {
				$.messager.alert('提示', '新增失败,操作无效！');
				winform.oneClick(function(){ frm.submit(); });
			}
		}
	});
	frm.form("clear");
	winform.oneClick(function(){ frm.submit(); });
}

/**删除名录项目记录*/
function removeActivity(idx) {
	var row = WHdg.getRowData(idx);
		$.messager.confirm('确认对话框', '您想要删除此记录吗？', function(r) {
			if (r) {
				$.ajax({
					url : './admin/feiyi/removeSuccessor',
					data : {
						suorid : row.suorid
					},
					success : function(data) {
						if (data.success) {
							$.messager.alert("提示", "删除成功！");
							$("#mingluDiv").datagrid("reload");
						} else {
							$.messager.alert("提示", "删除该记录失败！");
						}
					}
				})
			}
		});
}

var entWin = "";

function guli_minglu(idx) {
	var rows = $("#mingluDiv").datagrid("getRows");
	var row = rows[idx];
	
	function opw(){
		entWin.find("#entpageifm").attr("src", "./admin/feiyi/mingluguli?suorid="+row.suorid);
	}
	
	if (entWin){
		entWin.window({
			onOpen : opw
		});
		entWin.window("open");
	}else{
		entWin = $("<div></div>");
		entWin.window({
			collapsible : false,
			minimizable : false,
			maximizable : true,
			maximized : true,
			title : " ",
			modal : true,
			width : '70%',
			height : '60%',
			content : '<iframe id="entpageifm" width="100%" height="100%"  frameborder="0"></iframe>',
			onOpen : opw
		});
	}
}

/**审核*/
function Check(index){
	var data={fromstate:1,tostate:2};
	var title="您确认审核所选吗？";
	editState(index,data,title);
}
/**发布*/
function publish(index){
	var data={fromstate:2,tostate:3};
	var title="您确认要发布所选吗？";
	editState(index,data,title);
}
/**取消发布*/
function nopublish(index){
	var data={fromstate:3,tostate:2};
	var title="您确认要取消发布所选吗？";
	editState(index,data,title);
}
/**打回审核*/
function nocheck(index){
	var data={fromstate:2,tostate:1};
	var title="您确认要打回审核吗？";
	editState(index,data,title);
}
/**送审*/
function sendCheck(index){
	var data={fromstate:0,tostate:1};
	var title="您确认送审核所选吗？";
	editState(index,data,title);
}

function plSS(){
	var data={fromstate:0,tostate:1};
	var title="送审";
	pleditState(data,title);
}

function plSH(){
	var data={fromstate:1,tostate:2};
	var title="审核";
	pleditState(data,title);
}
function dhSS(){
	var data={fromstate:2,tostate:1};
	var title="打回审核";
	pleditState(data,title);
}

function plFP(){
	var data={fromstate:2,tostate:3};
	var title="发布";
	pleditState(data,title);
}

function qxFP(){
	var data={fromstate:3,tostate:2};
	var title="取消发布";
	pleditState(data,title);
}


//批量 送审
function pleditState(data,title) {
	 var row = $('#mingluDiv').datagrid('getSelections');  
	 if(row.length<1){
		 $.messager.alert('提示', '请选中要'+title+'的记录！');
		 return;
	 }
	  for(var i=0; i<row.length; i++){ 
	    	if(data.fromstate != row[i].suorstate){
	    		$.messager.alert('提示', '选中的有不符合/重复'+title+'的记录！');
	    		return;
	    	}
	  }
	 $.messager.confirm("提示", "确定要"+title+"所选吗？", function(r) {
			if (!r)return;
	 var params = _params="";
    for(var i=0; i<row.length; i++){ 
   	 params+=_params+row[i].suorid;
   	 _params=",";
    }
    
    var data2=$.extend(data,{suorid:row.suorid,params :params});
	$.ajax({
		url : getFullUrl("./admin/feiyi/pleditStatesuor"),
		data : data2,
		success : function(data) {
			if (data.success=="success") {
				$.messager.alert("提示", data.msg);
				$("#mingluDiv").datagrid("reload");
			}else {
				$.messager.alert("提示", "要求不服,不能提审!");
			}
		}
	})   
})
} 

/**改变状态*/
function editState(index,data,title){
	var row = WHdg.getRowData(index);
	var suorid= row.suorid;
	var data=$.extend({},data,{suorid:suorid});
	$.messager.confirm('确认对话框', title, function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/feiyi/editStatesuor'),
				data: data,
				success: function(data){
					if(data.success == '0'){
						$.messager.alert('提示', data.msg);
						$('#mingluDiv').datagrid('reload');
					   }else{
						   $.messager.alert('提示', '操作失败。原因：'+data.errmsg, 'error');
					   }
				}
			});
		}
	});
}

/** 资源管理*/
var entWin = '';
function addEnt(idx){
	var rows = $("#mingluDiv").datagrid("getRows");
	var row = rows[idx];
	
	function opw(){
		var reftype = "2016101400000052";
		entWin.find("#entpageifm").attr("src", "./admin/ent/entsrc?entsuorpro="+row.suorid+"&reftype="+reftype+"&canEdit=true");
	}
	
	if (entWin){
		entWin.window({
			onOpen : opw
		});
		entWin.window("open");
	}else{
		entWin = $("<div></div>");
		entWin.window({
			collapsible : false,
			minimizable : false,
			maximizable : true,
			maximized : true,
			title : " ",
			modal : true,
			width : '70%',
			height : '60%',
			content : '<iframe id="entpageifm" width="100%" height="100%"  frameborder="0"></iframe>',
			onOpen : opw
		});
	}
}


$(function(){
	var options = {
			title: '传承人管理',			
			url: getFullUrl('./admin/feiyi/loadSuccessor'),
			pageSize:20,
			singleSelect:false,
			columns: [[
				{ field: 'SH',checkbox:'true',width:30},
				{field:'suorid',title:'传承人标识', sortable:true,width:100},
				{field:'suorname',title:'传承人姓名', width:150},
				{field:'suorqy',title:'传承人区域',width:100,formatter:function(value,row,index){ return WHTYP.sys_Whtyp("8",value)}},
				{field:'suorlevel',title:'传承人级别',width:100,formatter:function(value,row,index){ return WHTYP.sys_Whtyp("16",value)}},
				{field:'suoritem',title:'传承人批次', sortable:true,width:80,formatter:function(value,row,index){ return WHTYP.sys_Whtyp("17",value) }},
				{field:'suortype',title:'传承人类别', sortable:true, width:150,formatter:function(value,row,index){ return WHTYP.sys_Whtyp("18",value) }},
				{field:'suorpic',title:'传承人图片',width:60,formatter:imgFMT},
				{field:'suorstate',title:'传承人状态', width:60,formatter:traStateFMT},
				{field:'opt', title:'操作',formatter:WHdg.optFMT, optDivId:'mingluOPT'}
			]]
		};
	
	//初始表格
	WHdg.init('mingluDiv', 'mingluTool', options);
	//初始化表单
	winform.init();
	
	//实例化编辑器
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
	UE.getEditor('traeditor');  
	//UE.getEditor('traeditor1');  
	 var tbSearch = $("#mingluTool").find(".tb_search").off("click");
	    tbSearch.on("click", function () {
	        datagridLoad();
	    });
})


</script>

</head>
<body>
	<!--数据表格 -->
	<div id="mingluDiv"> </div>
	
	<!--工具条 -->
	<div id="mingluTool" class="grid-control-group" style="display: none">
		<div>
			<shiro:hasPermission name="${resourceid}:add">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add"  onclick="addminglu();" data-options="size:'small'">添加 </a>
			</shiro:hasPermission>
			<%-- <shiro:hasPermission name="${resourceid }:checkgo">
				<a href="javascript:void(0)" class="easyui-linkbutton" onclick="plSS();" data-options="size:'small'">送审所选 </a>
			</shiro:hasPermission> --%>
			<shiro:hasPermission name="${resourceid }:checkon">
				<a href="javascript:void(0)" class="easyui-linkbutton" onclick="plSH();" data-options="size:'small'">审核所选</a>
			</shiro:hasPermission>
				<shiro:hasPermission name="${resourceid }:checkoff">
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="dhSS();" data-options="size:'small'">打回所选</a>
				</shiro:hasPermission>
			<shiro:hasPermission name="${resourceid }:publish">
				<a href="javascript:void(0)" class="easyui-linkbutton" onclick="plFP();" data-options="size:'small'">发布所选</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="${resourceid }:publishoff">
				<a href="javascript:void(0)" class="easyui-linkbutton" onclick="qxFP();" data-options="size:'small'">取消发布</a>
			</shiro:hasPermission>
		</div>
		<div style="padding-top: 5px">
			传承人姓名:
			<input class="easyui-textbox" name="suorname" style="width:150px" />
			传承人类型:
			<input class="easyui-combobox" name="suortype" style="width:150px" 
			data-options="editable:true, valueField:'typid',textField:'typname',url:'./comm/whtyp?type=18'"/>
			传承人批次:
			<input class="easyui-combobox" name="suoritem" style="width:150px" 
			data-options="editable:true, valueField:'typid',textField:'typname',url:'./comm/whtyp?type=17'"/>
			
			传承人级别:
			<input class="easyui-combobox" name="suorlevel" style="width:150px" 
			data-options="editable:true, valueField:'typid',textField:'typname',url:'./comm/whtyp?type=16'"/>
			传承人区域:
			<input class="easyui-combobox" name="suorqy" style="width:150px" 
			data-options="editable:true, valueField:'typid',textField:'typname',url:'./comm/whtyp?type=8'"/>
			<a href="javascript:void(0)" class="easyui-linkbutton tb_search" iconCls="icon-search">查询</a>
		</div>
	</div>
	
	<!-- 操作按钮 -->
	<div id="mingluOPT" class="none" style="display: none">
		<shiro:hasPermission name="${resourceid }:view">
		<a href="javascript:void(0)"  method="lookAll">查看</a>
		</shiro:hasPermission>
		
		<shiro:hasPermission name="${resourceid }:del">
			<a href="javascript:void(0)"  method="removeActivity">删除</a> 
		</shiro:hasPermission> 
		
		<%-- <shiro:hasPermission name="${resourceid }:edit"> --%>
			<a href="javascript:void(0)"  method="edit_minglu" >修改</a> 
		<%-- </shiro:hasPermission> --%>
		<shiro:hasPermission name="${resourceid}:add"><a href="javascript:void(0)" validFun="_validFun" method="guli_minglu">名录项目管理</a></shiro:hasPermission> 
		<shiro:hasPermission name="${resourceid}:view"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validFun="_validFun" method="addEnt">资源管理</a></shiro:hasPermission>
		<%-- <shiro:hasPermission name="${resourceid}:checkon"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="suorstate" validVal="0" method="sendCheck">送审</a></shiro:hasPermission> --%>
		<shiro:hasPermission name="${resourceid}:checkon"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="suorstate" validVal="1" method="Check">审核</a></shiro:hasPermission>
		<shiro:hasPermission name="${resourceid}:checkoff"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="suorstate" validVal="2" method="nocheck">打回审核</a></shiro:hasPermission>
		<shiro:hasPermission name="${resourceid}:publish"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="suorstate" validVal="2" method="publish">发布</a></shiro:hasPermission>
		<shiro:hasPermission name="${resourceid}:publishoff"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="suorstate" validVal="3" method="nopublish" >取消发布</a></shiro:hasPermission>
	</div>
	
	<!--活动操作 表单 -->	
	<div id="frm" class="none" style="display: none" data-options="	maximized:true">
		<form method="post" enctype="multipart/form-data" id="Form">
			<div class="main" id="mobandaor">
			  <div class="row">
					<div>
						<label>传承人姓名:</label>
					</div>
					<div>
						<input name="suorname" class="easyui-textbox" data-options="validType:'length[0,20]',required:true" style="height: 35px; width: 90%"/>
					</div>
				</div>
			   <div class="row">
					<div>
						<label>传承人类型:</label>
					</div>
					<div>
						<input class="easyui-combobox " name="suortype" style="height:35px;width:90%" 
							data-options="editable:false, valueField:'typid',textField:'typname',
							url:'./comm/whtyp?type=18',required:true"/>
					</div>
				</div>
				<div class="row">
					<div>
						<label>传承人批次:</label>
					</div>
					<div>
						<input class="easyui-combobox" name="suoritem" style="height:35px;width: 90%" 
						data-options="editable:false,required:true, valueField:'typid',textField:'typname',url:'./comm/whtyp?type=17'"/>
					</div>
				</div>
				<div class="row">
					<div>
						<label>传承人级别:</label>
					</div>
					<div>
						<input class="easyui-combobox" name="suorlevel" style="height:35px;width: 90%" 
						data-options="editable:false,required:true, valueField:'typid',textField:'typname',url:'./comm/whtyp?type=16'"/>
					</div>
				</div>
				<div class="row">
					<div>
						<label>传承人区域:</label>
					</div>
					<div>
						<input class="easyui-combobox" name="suorqy" style="height:35px;width: 90%" 
						data-options="editable:false,required:true, valueField:'typid',textField:'typname',url:'./comm/whtyp?type=8'"/>
					</div>
				</div>
			
				<div class="row">
					<div>
						<label>传承人图片:</label>
					</div>
					<div>
						<input class="easyui-filebox" name="file_image" style="height:35px;width: 80%" prompt='图片尺寸:136x136'/>
						<a id="btn_pic2" class="easyui-linkbutton" data-options="plain:true," iconCls="icon-clear" style="width:9%;height:32px;">清除</a>
					</div>
				</div>
				<div class="row">
					<div>
						<label>传承人介绍:</label>
					</div>
					<div>
						<input class="easyui-textbox" data-options="multiline:true,validType:'length[0,400]'"
						name="suorjs" style="width: 90%; height: 180px"/>
					</div>
				</div>
				<div class="row">
					<div>
						<label>传承人成就:</label>
					</div>
					<div>
						<div style="width:90%">
							<script id="traeditor" type="text/plain" style="width:100%; height:300px;"></script>
						</div>
					</div>
				</div>
				
			<!-- 	<div class="row">
					<div>
						<label>传承人叙史:</label>
					</div>
					<div>
						<div style="width:90%">
							<script id="traeditor1" type="text/plain" style="width:100%; height:300px;"></script>
						</div>
					</div>
				</div> -->
				<div class="cls"></div>
				</div>
		</form>
</div>		
</body>
</html>