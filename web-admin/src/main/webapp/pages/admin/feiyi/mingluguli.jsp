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
    var params = {suorid:suorid};
    tb.find("[name]").each(function(){
    	var value = $(this).val();
    	var name = $(this).attr("name");
    	params[name] = value;
    });
    this.queryParams = this.queryParams ||{};
    $.extend(this.queryParams, params);

    datagrid.datagrid({
        url :'./admin/feiyi/loadMingluleavl',
        queryParams : this.queryParams
    });
}

//处理编辑时显示图片
function _showImg(data){
	$(".img_div").remove();
	var imgDiv = '<div class="row img_div">'
		+'<div></div>'
		+'<div><img width="200" height="150"> </div> '
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

/**增加名录*/
function addminglu(){
	var mingluid = $('.actvshorttitle').combogrid('getValue');
	if(!mingluid || mingluid == ""){
		return;
	}
	$.ajax({
		url : './admin/feiyi/addsuorpro',
		data : {
			spmlproid : mingluid,
			spsuorid:suorid
		},
		success : function(data) {
			if (data.success) {
				$('.actvshorttitle').combogrid('setValue','');
				$.messager.alert("提示", "增加成功！");
				$("#mingluDiv").datagrid("reload");
			} else {
				$.messager.alert("提示", "删除该记录失败！");
			}
		}
	})

}

/**删除名录项目记录*/
function removeActivity(idx) {
	var row = WHdg.getRowData(idx);
		$.messager.confirm('确认对话框', '您想要删除此记录吗？', function(r) {
			if (r) {
				$.ajax({
					url : './admin/feiyi/removeSuorpro',
					data : {
						spmlproid : row.mlproid
					},
					success : function(data) {
						if (data.success) {
							$("#mingluDiv").datagrid("reload");
							$.messager.alert("提示",data.msg );
							
						} else {
							$.messager.alert("提示", "删除该记录失败！");
						}
					}
				})
			}
		});
}

//传承人 名录 管理 传承人id
var suorid = "";
$(function(){
	 suorid = "${suorid}";
	var options = {
			title: '传承人相关名录',			
			url: getFullUrl('./admin/feiyi/loadMinglu'),
			pageSize:20,
			queryParams:{
				suorid:suorid
			},
			singleSelect:false,
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
	
	//查询下拉框中的活动管理数据表格
	var option = {
			//title: '活动管理',
			idField: 'mlproid',    
		    textField: 'mlproshortitel',
			pagination: true,
		    pageSize: 5,
		    pageList: [5,10,20,50],panelHeight:'auto',
		    queryParams:{
				suorid:suorid,
				mlprostate: 3
			},
		  	url: getFullUrl('./admin/feiyi/loadMingluleavl'),
			columns: [[
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
			]],
			toolbar: "#testdiv",
			onShowPanel: function(){
				 var g = $(this).combogrid("grid");
				 g.datagrid("reload");
			}
	};
	 //加载下拉框数据
	 $('.actvshorttitle').combogrid(option); 
	
	 //
	 $("#testdiv a.tb_search").on("click", function(){
		// var _data = $("#testdiv").find("[name]").serializeArray();
		 var params = {mlprostate: 3,suorid:suorid};
		 $("#testdiv").find("[name]").each(function(){
	    	var value = $(this).val();
			 if (!value) return true;
	    	var name = $(this).attr("name");
	    	params[name] = value;
	    });
	    /*this.queryParams = this.queryParams ||{};
	    $.extend(this.queryParams, params);*/
		 var grid = $('.actvshorttitle').combogrid("grid");
		 grid.datagrid({
			 //url: getFullUrl('./admin/feiyi/loadMinglu'),
			 url: getFullUrl('./admin/feiyi/loadMingluleavl'),
			 queryParams: params
		 });
	 })
	 
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
	<div id="mingluTool" style="display: none">
		<div style="margin: 5px 5px">
				名录项目:
				<input class="easyui-combogrid actvshorttitle"  name="actvshorttitle" style="width:600px;height:32px" data-options="editable:false" />
			<%-- <shiro:hasPermission name="${resourceid}:add"> --%>
				<a href="javascript:void(0)" class="easyui-linkbutton" 
					iconCls="icon-add"  onclick="addminglu();" data-options="size:'large'">添加
				</a>
			<%-- </shiro:hasPermission> --%>
		</div>
		<div >
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
	
	<div id="testdiv" style="display: none">
		<div>
			标题:
			<input class="easyui-textbox" name="mlproshortitel" style="width:150px" />
			名录项目级别:
			<input class="easyui-combobox" name="mlprolevel" style="width:150px" 
			data-options="editable:true, valueField:'typid',textField:'typname',url:'./comm/whtyp?type=16'"/>
			<a href="javascript:void(0)" class="easyui-linkbutton tb_search" iconCls="icon-search">查询</a>
		</div>
	</div>
	
	<!-- 操作按钮 -->
	<div id="mingluOPT" class="none" style="display: none">
	<a href="javascript:void(0)"  method="lookAll">查看详情</a>
		
	<a href="javascript:void(0)"  method="removeActivity">删除</a>
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
						<input name="mlproshortitel" class="easyui-textbox" data-options="validType:'length[0,30]',required:true" style="height: 35px; width: 90%"/>
					</div>
				</div>
				
				<div class="row">
					<div>
						<label>名录项目标题标题:</label>
					</div>
					<div>
						<input name="mlprotailtitle" class="easyui-textbox" data-options="validType:'length[0,30]',required:true" style="height: 35px; width: 90%"/>
					</div>
				</div>
				<div class="row">
					<div>
						<label>申报区域:</label>
					</div>
					<div>
					<input name="mlprosbaddr" class="easyui-textbox" data-options="validType:'length[0,64]',required:true" style="height: 35px; width: 90%"/>
					</div>
				</div>
				<div class="row">
					<div>
						<label>资源来源:</label>
					</div>
					<div>
					<input name="mlprosource" class="easyui-textbox" data-options="validType:'length[0,64]',required:true" style="height: 35px; width: 90%"/>
					</div>
				</div>
				<div class="row">
					<div>
						<label>关键字:</label>
					</div>
					<div>
						<input class="easyui-combobox" name="mlprokey" multiple="true" style="width:90%;height:32px;" 
						data-options=" valueField:'id',textField:'name',url:'${basePath}/comm/whkey?type=2016101400000022', multiple:true"/>
					</div>
				</div>
				
				<div class="row">
					<div>
						<label>名录列表图:</label>
					</div>
					<div>
						<input class="easyui-filebox" name="file_image" style="height:35px;width: 80%"/>
						<a id="btn_pic1" class="easyui-linkbutton" data-options="plain:true," iconCls="icon-clear" style="width:9%;height:32px;">清除</a>
					</div>
				</div>
				<div class="row">
					<div>
						<label>名录详情图:</label>
					</div>
					<div>
						<input class="easyui-filebox" name="file_imagesm" style="height:35px;width: 80%"/>
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