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
<title>名录项目管理</title>
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
        url :'./admin/feiyi/loadMinglu',
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
	if(data.mlprosmpic){
		var imgrow = $("[name$='image']").parents(".row");
		imgrow.after(imgDiv);
		imgrow.next(".img_div").find("div img").attr("src","${basePath }/"+data.mlprosmpic);
	}
	if(data.mlprobigpic){
		var imgrow = $("[name$='imagesm']").parents(".row");
		imgrow.after(imgDiv);
		imgrow.next(".img_div").find("div img").attr("src","${basePath }/"+data.mlprobigpic);
	}
}

/*查找名录*/
function lookAll(idx) {
	$("#Form .easyui-combobox").combobox({ disabled: true });  
	$("#Form .easyui-datetimebox").datetimebox({ disabled: true });
	$("#Form .easyui-textbox").textbox({ disabled: true });
	$("#Form .easyui-filebox").filebox("disable");
	UE.getEditor('traeditor').setDisabled('fullscreen');
	//清空富文本
	UE.getEditor('traeditor').setContent("");
	winform.openWin();
	winform.setWinTitle("查找名录信息");
	var row = WHdg.getRowData(idx);
	var frm = winform.getWinBody().find('form');
	
	UE.getEditor('traeditor').setContent(row.mlprodetail);

	frm.form("clear");
	_showImg(row);
	frm.form("load", row);
	winform.getWinFooter().find("a:eq(0)").hide();
}

/**修改名录*/
function edit_minglu(idx) {
	$("#Form .easyui-combobox").combobox({ disabled: false });  
	$("#Form .easyui-datetimebox").datetimebox({ disabled: false });
	$("#Form .easyui-textbox").textbox({ disabled: false });
	$("#Form .easyui-filebox").filebox("enable");
	 UE.getEditor('traeditor').setEnabled();
	 winform.getWinFooter().find("a:eq(0)").show();
	var row = WHdg.getRowData(idx);
	winform.openWin();
	winform.setWinTitle("修改名录信息");
	//显示富文本
	UE.getEditor('traeditor').setContent(row.mlprodetail);
	
	var frm = winform.getWinBody().find('form').form({
		url : "./admin/feiyi/addOrEditminglu",
		onSubmit : function(param) {
			param.mlproid = row.mlproid;
			param.mlprodetail=UE.getEditor('traeditor').getContent();
			$("#traeditor").val(UE.getEditor('traeditor').getContent());
		    return $(this).form('enableValidation').form('validate');
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
	winform.oneClick(function(){ frm.submit(); });
}
/**增加名录*/
function addminglu(){
	$("#Form .easyui-combobox").combobox({ disabled: false });  
	$("#Form .easyui-datetimebox").datetimebox({ disabled: false });
	$("#Form .easyui-textbox").textbox({ disabled: false });
	$("#Form .easyui-filebox").filebox("enable");
	 UE.getEditor('traeditor').setEnabled();
	 winform.getWinFooter().find("a:eq(0)").show();
	$(".img_div").remove();
	winform.openWin();
	winform.setWinTitle("增加名录信息");
	var frm = winform.getWinBody().find('form');
	//清空富文本
	UE.getEditor('traeditor').setContent("");
	frm.form({
		url : getFullUrl("./admin/feiyi/addOrEditminglu"),
		onSubmit : function(param) {
			
			$("#traeditor").val(UE.getEditor('traeditor').getContent());
			param.mlprodetail=UE.getEditor('traeditor').getContent();
		    return $(this).form('enableValidation').form('validate');		
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
					url : './admin/feiyi/removeMinglu',
					data : {
						mlproid : row.mlproid
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
	    	if(data.fromstate != row[i].mlprostate){
	    		$.messager.alert('提示', '选中的有不符合/重复'+title+'的记录！');
	    		return;
	    	}
	  }
	 $.messager.confirm("提示", "确定要"+title+"所选吗？", function(r) {
			if (!r)return;
	 var params = _params="";
    for(var i=0; i<row.length; i++){ 
   	 params+=_params+row[i].mlproid;
   	 _params=",";
    }
    
    var data2=$.extend(data,{mlproid:row.mlproid,params :params});
	$.ajax({
		url : getFullUrl("./admin/feiyi/pleditState"),
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
	var mlproid= row.mlproid;
	var data=$.extend({},data,{mlproid:mlproid,suorid:suorid});
	$.messager.confirm('确认对话框', title, function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/feiyi/editState'),
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

/** 判断是否能够进行审核*/
function _validFun(index){
	var row = WHdg.getRowData(index);
	return row.mlprostate == '0' || row.mlprostate == '1';
}

/** 资源管理*/
var entWin = '';
function addEnt(idx){
	var rows = $("#mingluDiv").datagrid("getRows");
	var row = rows[idx];
	
	function opw(){
		var reftype = "2016101400000052";
		entWin.find("#entpageifm").attr("src", "./admin/ent/entsrc?entsuorpro="+row.mlproid+"&reftype="+reftype+"&canEdit=true");
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

var suorid = "";
$(function(){
	suorid = "${suorid}";
	var options = {
			title: '名录项目管理',			
			url: getFullUrl('./admin/feiyi/loadMinglu'),
			pageSize:20,
			singleSelect:false,
			queryParams:{
				suorid:suorid
			},
			columns: [[
				{ field: 'SH',checkbox:'true',width:30},
				{field:'mlproid',title:'名录标识', sortable:true,width:100},
				{field:'mlproshortitel',title:'名录列表标题', width:150},
				{field:'mlprotailtitle',title:'名录详情标题',width:60},
				{field:'mlprotype',title:'名录级别',width:100,formatter:function(value,row,index){ return WHTYP.sys_Whtyp("18",value)}},
				{field:'mlprolevel',title:'名录级别',width:100,formatter:function(value,row,index){ return WHTYP.sys_Whtyp("16",value)}},
				{field:'mlproitem',title:'名录批次', sortable:true,width:80,formatter:function(value,row,index){ return WHTYP.sys_Whtyp("17",value) }},
				{field:'mlprosbaddr',title:'申报区域', sortable:true, width:150},
				{field:'mlprosource',title:'资料来源', width:60},
				{field:'mlprosmpic',title:'名录项目列表图', sortable:true,width:80,formatter:imgFMT},
				{field:'mlprobigpic',title:'名录项目详情图', sortable:true,width:80,formatter:imgFMT},
				{field:'mlprotime',title:'名录项目修改时间',  width:90,formatter:datetimeFMT},  
				{field:'mlprostate',title:'名录状态',width:50, sortable:true,formatter:traStateFMT},
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
			<shiro:hasPermission name="${resourceid}:add"><a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add"  onclick="addminglu();" data-options="size:'small'">添加</a></shiro:hasPermission>
		<%-- 	<shiro:hasPermission name="${resourceid }:checkgo">
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
			标题:
			<input class="easyui-textbox" name="mlproshortitel" style="width:150px" />
			名录项目类型:
			<input class="easyui-combobox" name="mlprotype" style="width:150px" 
			data-options="editable:true, valueField:'typid',textField:'typname',url:'./comm/whtyp?type=18'"/>
			名录项目批次:
			<input class="easyui-combobox" name="mlproitem" style="width:150px" 
			data-options="editable:true, valueField:'typid',textField:'typname',url:'./comm/whtyp?type=17'"/>
			
			名录项目级别:
			<input class="easyui-combobox" name="mlprolevel" style="width:150px" 
			data-options="editable:true, valueField:'typid',textField:'typname',url:'./comm/whtyp?type=16'"/>
			名录项目区域:
			<input class="easyui-combobox" name="mlproqy" style="width:150px" 
			data-options="editable:true, valueField:'typid',textField:'typname',url:'./comm/whtyp?type=8'"/>
			申报区域:
			<input class="easyui-textbox" name="mlprosbaddr" style="width:150px" />
			<a href="javascript:void(0)" class="easyui-linkbutton tb_search" iconCls="icon-search">查询</a>
		</div>
	</div>
	
	<!-- 操作按钮 -->
	<div id="mingluOPT" class="none" style="display: none">
		<shiro:hasPermission name="${resourceid }:view">
		<a href="javascript:void(0)"  method="lookAll">查看</a>
		</shiro:hasPermission>
		
		 <shiro:hasPermission name="${resourceid }:del"> 
			<a href="javascript:void(0)" validKey="mlprostate" validVal="1"  method="removeActivity">删除</a> 
		 </shiro:hasPermission>
		
		<shiro:hasPermission name="${resourceid }:edit">
			<a href="javascript:void(0)" validKey="mlprostate" validVal="1" method="edit_minglu">修改</a> 
		</shiro:hasPermission>
		<shiro:hasPermission name="${resourceid}:view"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="mlprostate" validVal="1" method="addEnt">资源管理</a></shiro:hasPermission>
		<%-- <shiro:hasPermission name="${resourceid}:checkon"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="mlprostate" validVal="0" method="sendCheck">送审</a></shiro:hasPermission> --%>
		<shiro:hasPermission name="${resourceid}:checkon"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="mlprostate" validVal="1" method="Check">审核</a></shiro:hasPermission>
		<shiro:hasPermission name="${resourceid}:checkoff"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="mlprostate" validVal="2" method="nocheck">打回审核</a></shiro:hasPermission>
		<shiro:hasPermission name="${resourceid}:publish"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="mlprostate" validVal="2" method="publish">发布</a></shiro:hasPermission>
		<shiro:hasPermission name="${resourceid}:publishoff"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="mlprostate" validVal="3" method="nopublish" >取消发布</a></shiro:hasPermission>
	</div>
	
	<!--活动操作 表单 -->	
<div id="frm" class="none" style="display: none" data-options="	maximized:true">
		<form method="post" enctype="multipart/form-data" id="Form">
			<div class="main" id="mobandaor">
			   <div class="row">
					<div>
						<label>名录项目类型:</label>
					</div>
					<div>
						<input class="easyui-combobox " name="mlprotype" style="height:35px;width:90%" 
							data-options="editable:false, valueField:'typid',textField:'typname',
							url:'./comm/whtyp?type=18',required:true"/>
					</div>
				</div>
				<div class="row">
					<div>
						<label>名录项目批次:</label>
					</div>
					<div>
						<input class="easyui-combobox" name="mlproitem" style="height:35px;width: 90%" 
						data-options="editable:false,required:true, valueField:'typid',textField:'typname',url:'./comm/whtyp?type=17'"/>
					</div>
				</div>
				<div class="row">
					<div>
						<label>名录项目级别:</label>
					</div>
					<div>
						<input class="easyui-combobox" name="mlprolevel" style="height:35px;width: 90%" 
						data-options="editable:false,required:true, valueField:'typid',textField:'typname',url:'./comm/whtyp?type=16'"/>
					</div>
				</div>
				<div class="row">
					<div>
						<label>名录项目区域:</label>
					</div>
					<div>
						<input class="easyui-combobox" name="mlproqy" style="height:35px;width: 90%" 
						data-options="editable:false,required:true, valueField:'typid',textField:'typname',url:'./comm/whtyp?type=8'"/>
					</div>
				</div>
				
				<div class="row">
					<div>
						<label>名录项目列表标题:</label>
					</div>
					<div>
						<input name="mlproshortitel" class="easyui-textbox" data-options="validType:'length[0,60]',required:true" style="height: 35px; width: 90%"/>
					</div>
				</div>
				
				<div class="row">
					<div>
						<label>名录项目详情标题:</label>
					</div>
					<div>
						<input name="mlprotailtitle" class="easyui-textbox" data-options="validType:'length[0,60]',required:true" style="height: 35px; width: 90%"/>
					</div>
				</div>
				<div class="row">
					<div>
						<label>申报区域:</label>
					</div>
					<div>
					<input name="mlprosbaddr" class="easyui-textbox" data-options="validType:'length[0,60]',required:true" style="height: 35px; width: 90%"/>
					</div>
				</div>
				<div class="row">
					<div>
						<label>资源来源:</label>
					</div>
					<div>
					<input name="mlprosource" class="easyui-textbox" data-options="validType:'length[0,20]',required:true" style="height: 35px; width: 90%"/>
					</div>
				</div>
				<div class="row">
					<div>
						<label>关键字:</label>
					</div>
					<div>
						<input class="easyui-combobox" name="mlprokey" multiple="true" style="width:90%;height:32px;" 
						data-options=" valueField:'id',textField:'name',missingMessage:'请用英文逗号分隔',required:true,url:'${basePath}/comm/whkey?type=2016120800000001', multiple:true"/>
					</div>
				</div>
				
				<div class="row">
					<div>
						<label>名录列表图:</label>
					</div>
					<div>
						<input class="easyui-filebox" name="file_image" style="height:35px;width: 80%" prompt='图片尺寸:380x240'/>
						<a id="btn_pic1" class="easyui-linkbutton" data-options="plain:true," iconCls="icon-clear" style="width:9%;height:32px;">清除</a>
					</div>
				</div>
				<div class="row">
					<div>
						<label>名录详情图:</label>
					</div>
					<div>
						<input class="easyui-filebox" name="file_imagesm" style="height:35px;width: 80%" prompt='内容图片800x533（此图片的宽度只要不超过800像素都行）'/>
						<a id="btn_pic2" class="easyui-linkbutton" data-options="plain:true," iconCls="icon-clear" style="width:9%;height:32px;">清除</a>
					</div>
				</div>
				<div class="row">
					<div>
						<label>名录项目详情:</label>
					</div>
					<div>
						<div style="width:90%">
							<script id="traeditor" type="text/plain" style="width:100%; height:300px;"></script>
						</div>
					</div>
				</div>
				
				<div class="cls"></div>
				</div>
		</form>
</div>		
</body>
</html>