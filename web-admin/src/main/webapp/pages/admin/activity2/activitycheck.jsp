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
<title>活动审核</title>
<base href="${basePath }/">
<%@include file="/pages/comm/header.jsp"%>
<script type="text/javascript" charset="utf-8" src="${basePath}/static/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${basePath}/static/ueditor/ueditor.all.min.js"></script>
<script type="text/javascript" charset="utf-8" src="${basePath}/static/ueditor/lang/zh-cn/zh-cn.js"></script>
</head>
<script type="text/javascript">
var winform = new WhuiWinForm("#frm");

//批量 审核
function plSS(data) {
	 var row = $('#activityDiv').datagrid('getSelections');  
	 if(row.length<1){
		 $.messager.alert('提示', '请选中要审核的记录！');
		 return;
	 }
	$.messager.confirm("提示", "确定要审核所选吗？", function(r) {
	if (!r)return;
	 var params = _params="";
    for(var i=0; i<row.length; i++){  
   	 params+=_params+row[i].actvid;
   	 _params=",";
    }
	$.ajax({
		url : getFullUrl("./admin/activity/editactitmstate"),
		data :{
			actvid : row.actvid,
			params :params,
			fromstate:1,
			tostate:2
		},
		success : function(data) {
			if (data.success=="success") {
				$.messager.alert("提示", data.msg);
				$("#activityDiv").datagrid("reload");
			} else {
				$.messager.alert("提示", "操作失败!");
			}
		}
	}) 
	})
}

/**打回 审核 */
function dhSS() {
	 var row = $('#activityDiv').datagrid('getSelections');  
	 if(row.length<1){
		 $.messager.alert('提示', '请选中要打回的记录！');
		 return;
	 }
	$.messager.confirm("提示", "确定要打回所选吗？", function(r) {
	if (!r)return;
	 var params = _params="";
    for(var i=0; i<row.length; i++){  
   	 params+=_params+row[i].actvid;
   	 _params=",";
    }
	$.ajax({
		url : getFullUrl("./admin/activity/editactitmstate"),
		data :{
			actvid : row.actvid,
			params :params,
			fromstate:1,
			tostate:0
		},
		success : function(data) {
			if (data.success=="success") {
				$.messager.alert("提示",data.msg);
				$("#activityDiv").datagrid("reload");
			} else {
				$.messager.alert("提示", "操作失败!");
			}
		}
	}) 
	})
}

/**工具栏 search 加载数据*/
function datagridLoad(){ 
    var tb = $("#activityTool");
    var datagrid=$("#activityDiv");
    var params = {};
    tb.find("[name]").each(function(){
    	var value = $(this).val();
    	var name = $(this).attr("name");
    	params[name] = value;
    });
    this.queryParams = this.queryParams ||{};
    $.extend(this.queryParams, params);

    datagrid.datagrid({
        url :'./admin/activity/loadActivity?actvstate=1',
        queryParams : this.queryParams
    });
}

//处理编辑时显示图片
function _showImg(data){
	$(".img_div").remove();
	$('#fileoneName').html("");	
	$('#fileteamName').html("");	
	var imgDiv = '<div class="row img_div">'
		+'<div></div>'
		+'<div><img width="200" height="150"> </div> '
		+'</div>';
	
	if (data.actvbigpic){
		var imgrow = $("[name$='imagesm']").parents(".row");
		imgrow.after(imgDiv);
		imgrow.next(".img_div").find("div img").attr("src",data.actvbigpic);	
	}
	if (data.actvpic){
		var imgrow = $("[name$='image']").parents(".row");
		imgrow.after(imgDiv);
		imgrow.next(".img_div").find("div img").attr("src",data.actvpic);	
	}
	if(data.actvpersonfile){
 		$('#fileoneName').html("<span style='color:Blue'>" +data.actvpersonfile+ "</span>");	
	}
	if(data.actvteamfile){
		$('#fileteamName').html("<span style='color:Blue'>" +data.actvteamfile+ "</span>");	
	} 
}

/**修改活动 状态*/
function lookActivity(data) {
			$.ajax({
				url : getFullUrl("./admin/activity/editactitmstate"),
				data :data,
				success : function(data) {
					if (data.success == "success") {
						$.messager.alert("提示",data.msg);
						$("#activityDiv").datagrid("reload");
					} else {
						$.messager.alert("提示", "操作失败!");
					}
				}
			})
}

function setDisabled() {
    UE.getEditor('traeditor').setDisabled('fullscreen');
    //disableBtn("enable");
}

function setEnabled() {
    UE.getEditor('traeditor').setEnabled();
    //enableBtn();
}


/**查看 活动info*/
function lookAll(idx){
	$("input").attr("readonly", "readonly");
	//对form里面的下拉框禁用
	$("#Form .easyui-combobox").combobox({ disabled: true });  
	$("#Form .easyui-datetimebox").datetimebox({ disabled: true });
	$("#Form .easyui-textbox").textbox({ disabled: true });
	$("#Form .easyui-filebox").filebox("disable");
	setDisabled();
	var row = WHdg.getRowData(idx);
	if(row.actvisenrol == 0 && row.actvisyp == 0){
		$("#actvsdate").show();
		$("#actvedate").show();
	}else{
		$("#actvsdate").hide();
		$("#actvedate").hide();
	}
	var frm = winform.getWinBody().find('form');
	winform.openWin();
	winform.setWinTitle("查看活动信息");
	//显示富文本
	UE.getEditor('traeditor').setContent(row.actvdetail);

	//格式化日期
	var _data = $.extend({}, row,
	 	{
		actvsdate : new Date(row.actvsdate).Format("yyyy-MM-dd hh:mm:ss"),
		actvedate : new Date(row.actvedate).Format("yyyy-MM-dd hh:mm:ss"),
		//actvenrolstime : new Date(row.actvenrolstime).Format("yyyy-MM-dd hh:mm:ss"),
		//actvenroletime : new Date(row.actvenroletime).Format("yyyy-MM-dd hh:mm:ss")
	    });
		frm.form("clear");
		_showImg(_data);
		frm.form("load", _data);
		winform.getWinFooter().find("a:eq(0)").hide();
}


/**打回*/
function callback(idx){
	var row = WHdg.getRowData(idx);
	var data={actvid : row.actvid,fromstate:1,tostate : 0};
	$.messager.confirm("提示", "确定要打回吗？", function(r) {
		if (!r)return;
		lookActivity(data);
	})
}
/**审核*/
function goalong(idx){
	var row = WHdg.getRowData(idx);
	var data={actvid : row.actvid,fromstate:1,tostate :2};
	$.messager.confirm("提示", "确定要审核吗？", function(r) {
		if (!r)return;
		lookActivity(data);
	})
}
var entWin = '';
/**活动场次管理 */
function activityitm(idx) {
	var rows = $("#activityDiv").datagrid("getRows");
	var row = rows[idx];
	function opw(){
		entWin.find("#entpageifm").attr("src", "./admin/activity/activityitm?actvrefid="+row.actvid+"&canEdit=false");
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
			title : "活动场次管理",
			modal : true,
			width : '70%',
			height : '60%',
			content : '<iframe id="entpageifm" width="100%" height="100%"  frameborder="0"></iframe>',
			onOpen : opw
		});
	}
}

/** 资源管理*/
function addEnt(idx){
	var rows = $("#activityDiv").datagrid("getRows");
	var row = rows[idx];
	
	function opw(){
		var reftype = "2016101400000052";
		entWin.find("#entpageifm").attr("src", "./admin/ent/entsrc?refid="+row.actvid+"&reftype="+reftype+"&canEdit=false");
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

/**页面加载 */
$(function(){
	//实例化编辑器
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
	UE.getEditor('traeditor');  
	
	var options = {
			title: '活动管理',			
			url: getFullUrl('./admin/activity/loadActivity?actvstate=1'),
			pageSize:20,
			singleSelect:false,
			columns: [[
				{ field: 'SH',checkbox:'true',width:30},
				{field:'actvid',title:'活动标识', sortable:true, width:150},
				{field:'actvshorttitle',title:'列表标题', sortable:true, width:150},
				{field:'actvtitle',title:'详情标题',width:60, sortable:true},
				{field:'actvtype',title:'活动类型',width:100,formatter:acttypFMT},
				{field:'actvarttyp',title:'艺术类型', sortable:true,width:150,formatter:arttypFMT},
				{field:'actvaddress',title:'详细地址', sortable:true, width:150},
				{field:'actvhost',title:'主办方', width:60},
				{field:'actvagelevel',title:'适合年龄段',  width:60,formatter:agelevelFMT},  
				{field:'actvpic',title:'活动详情图', sortable:true,width:80,formatter:imgFMT},
				{field:'actvbigpic',title:'活动列表图', sortable:true,width:80,formatter:imgFMT},
			/* 	{field:'actvsdate',title:'活动开始时间', sortable:true,width:150,formatter:datetimeFMT},
				{field:'actvedate',title:'活动结束时间', sortable:true,width:150,formatter:datetimeFMT}, */
				{field:'actvstate',title:'状态',width:60, sortable:true,formatter:traStateFMT},
				{field:'opt', title:'操作',formatter:WHdg.optFMT, optDivId:'activityOPT'}
			]]
		};
	//初始表格
	WHdg.init('activityDiv', 'activityTool', options);
	// 初始化 窗体
	winform.init();
	
	//search 点击查找
	 var tbSearch = $("#activityTool").find(".tb_search").off("click");
     tbSearch.on("click", function () {
     	  datagridLoad();
     });
})
</script>
<body>
<!-- datagrid div -->
	<div id="activityDiv"></div>
	
	<!-- datagrid toolbar -->
	<div id="activityTool" class="grid-control-group" style="display: none">
		<div style="padding-bottom: 5px;">
		<shiro:hasPermission name="${resourceid }:checkon">
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="plSS();" data-options="size:'small'">审核所选</a>
		</shiro:hasPermission>
		<shiro:hasPermission name="${resourceid }:checkoff">
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="dhSS();" data-options="size:'small'">打回所选</a>
		</shiro:hasPermission>
		</div>
		<div>
		详情标题:
		<input class="easyui-textbox" name="actvtitle" style="width:150px" />
		艺术类型:
		<input class="easyui-combobox" name="actvarttyp" style="width:150px" 
		data-options="editable:true, valueField:'typid',textField:'typname',url:'./comm/whtyp?type=0'"/>
		活动类型:
		<input class="easyui-combobox" name="actvtype" style="width:150px" 
		data-options="editable:true, valueField:'typid',textField:'typname',url:'./comm/whtyp?type=1'"/>
		<!-- 开始时间:
		<input class="easyui-datebox" name="actvsdate" data-options=""/>
		到
		<input class="easyui-datebox" name="actvedate" data-options=""/> -->
		<a href="javascript:void(0)" class="easyui-linkbutton tb_search" iconCls="icon-search">查询</a>
		</div>
	</div>
	
	<!-- 操作按钮 -->
	<div id="activityOPT" class="none" style="display: none">
	
	<shiro:hasPermission name="${resourceid }:view">
		<a href="javascript:void(0)" method="lookAll">查看</a> 
	</shiro:hasPermission>
	
	<shiro:hasPermission name="${resourceid }:view">
		<a href="javascript:void(0)" class="easyui-linkbutton" method="activityitm">活动场次</a>
	</shiro:hasPermission> 
	
	<shiro:hasPermission name="${resourceid }:view">
		<a href="javascript:void(0)" method="addEnt">管理资源</a>  
	</shiro:hasPermission> 
	
	<shiro:hasPermission name="${resourceid }:checkon">
		<a href="javascript:void(0)"  method="callback">打回</a>
	</shiro:hasPermission> 
	
	<shiro:hasPermission name="${resourceid }:checkoff">
		<a href="javascript:void(0)" method="goalong">审核</a>
	</shiro:hasPermission>
	
	</div>
	
	<!--活动操作 表单 -->	
<div id="frm" class="none" style="display: none" data-options="	maximized:true">
		<form method="post" enctype="multipart/form-data" id="Form">
			<div class="main">
				<div class="row">
					<div>
						<label>活动类型:</label>
					</div>
					<div>
						<input class="easyui-combobox " name="actvtype" style="height:35px;width:90%" 
							data-options="editable:false, valueField:'typid',textField:'typname',
							url:'./comm/whtyp?type=1',required:true"/>
					</div>
				</div>
				<div class="row">
					<div>
						<label>艺术分类:</label>
					</div>
					<div>
						<input class="easyui-combobox" name="actvarttyp" style="height:35px;width: 90%" 
						data-options="editable:false,required:true, valueField:'typid',textField:'typname',url:'./comm/whtyp?type=0'"/>
					</div>
				</div>
				<div class="row">
					<div>
						<label>列表标题:</label>
					</div>
					<div>
						<input name="actvshorttitle" class="easyui-textbox"
						style="height: 35px; width: 90%"/>
					</div>
				</div>
				<div class="row">
					<div>
						<label>详情标题:</label>
					</div>
					<div>
					<input name="actvtitle" class="easyui-textbox"
						style="height: 35px; width: 90%"/>
					</div>
				</div>
				<div class="row">
					<div>
						<label>活动图片详情页主图:</label>
					</div>
					<div>
						<input class="easyui-filebox" name="file_image" style="height:35px;width: 90%"/>
					</div>
				</div>
				<div class="row">
					<div>
						<label>列表展示小图:</label>
					</div>
					<div>
						<input class="easyui-filebox" name="file_imagesm" style="height:35px;width: 90%"/>
					</div>
				</div>
				<div class="row">
					<div>
						<label>联系人电话:</label>
					</div>
					<div>
						<input class="easyui-textbox" name="actvphone" data-options="validType:'length[0,11]',required:true" style="height:35px;width: 90%" required=true/>	
					</div>
				</div>
				<div class="row">
					<div>
						<label>必须登陆点评:</label>
					</div>
					<div>
						<select class="easyui-combobox combobox_isenroldata" name="actvislogincomment"  style="height:32px;width: 90%" 
							data-options="editable:false">
							<option value="1">是</option>
		    				<option value="0">否</option>
						</select>
					</div>
				</div>
				<div class="row">
					<div>
						<label>是否需要订票:</label>
					</div>
					<div>
						<select class="easyui-combobox actvisyp" name="actvisyp"  style="height:32px;width: 90%" 
							data-options="editable:false">
							<option value="1">是</option>
		    				<option value="0">否</option>
						</select>
					</div>
				</div>
				<div class="row">
					<div>
						<label>标签:</label>
					</div>
					<div>
						<input id="tratags" class="easyui-combobox" name="actvtags" multiple="true" style="width:90%;height:32px;" 
						data-options=" valueField:'id',textField:'name',url:'${basePath}/comm/whtag?type=2016101400000019', multiple:true"/>
					</div>
				</div>
				<div class="row">
					<div>
						<label>关键字:</label>
					</div>
					<div>
						<input id="tratags" class="easyui-combobox" name="actvkeys" multiple="true" style="width:90%;height:32px;" 
						data-options=" valueField:'id',textField:'name',url:'${basePath}/comm/whkey?type=2016101400000022', multiple:true"/>
					</div>
				</div>
				<div class="row">
					<div>
						<label>区域:</label>
					</div>
					<div>
					<input class="easyui-combobox" name="actvarea" style="height:35px;width: 90%" 
						data-options="editable:false, valueField:'typid',textField:'typname',url:'./comm/whtyp?type=8'"/> 
					</div>
				</div>
				<div class="row">
					<div>
						<label>详细地址:</label>
					</div>
					<div>
						<input class="easyui-textbox" name="actvaddress" style="height:35px;width: 90%" required=true/>
					</div>
				</div>
				<div class="row">
					<div>
						<label>活动主办方:</label>
					</div>
					<div>
						<input class="easyui-textbox" name="actvhost" style="height:35px;width: 90%"/>
					</div>
				</div>
				<div class="row">
					<div>
						<label>适合年龄段:</label>
					</div>
					<div>
						<input class="easyui-combobox" name="actvagelevel" style="height:32px;width: 90%" 
						data-options="editable:false,required:true, valueField:'typid',textField:'typname',url:'./comm/whtyp?type=3'"/>
					</div>
				</div>
				<div class="row">
					<div>
						<label>是否需要报名:</label>
					</div>
					<div>
						<select class="easyui-combobox actvisenrol"
							name="actvisenrol" style="height: 35px; width: 90%"
							data-options="editable:false">
							<option value="0">否</option>
							<option value="1">是</option>
						</select>
					</div>
				</div>
				<div class="row" id="actvsdate">
						<div>
							<label>活动开始时间</label>
						</div>
						<div>
							<input name="actvsdate" class="easyui-datetimebox actvsdate"
							style="height: 35px; width: 90%"/>
						</div>
				</div>
				<div class="row" id="actvedate">
						<div>
							<label>活动结束时间</label>
						</div>
						<div>
							<input name="actvedate" class="easyui-datetimebox actvedate"
							style="height: 35px; width: 90%"/>
						</div>
				</div>
				<div class="row">
					<div>
						<label>报名最大人数:</label>
					</div>
					<div>
						<input name="actvenrollimit" class="easyui-textbox peoplecount"
							style="height: 35px; width: 90%" data-options="validType:'number'"/>
					</div>
				</div>
				<div class="row">
					<div>
						<label>报名是否需要审核:</label>
					</div>
					<div>
						<select class="easyui-combobox"
							name="actvisenrolqr" style="height: 35px; width: 90%"
							data-options="editable:false">
							<option value="0">否</option>
							<option value="1">是</option>
						</select>
					</div>
				</div>
				<div class="row">
					<div>
						<label>报名介绍:</label>
					</div>
					<div>
						<input class="easyui-textbox" data-options="multiline:true"
						name="actvenroldesc" style="width: 90%; height: 180px"/>
					</div>
				</div>
				<div class="row">
					<div>
						<label>必须实名报名:</label>
					</div>
					<div>
						<select class="easyui-combobox" name="actvisrealname"
							style="height: 35px; width: 90%"
							data-options="editable:false,required:true">
							<option value="0">否</option>
							<option value="1">是</option>
						</select>
					</div>
				</div>
				<div class="row">
					<div>
						<label>是否需要完善资料:</label>
					</div>
					<div>
						<select class="easyui-combobox" name="actvisfulldata"
							style="height: 35px; width: 90%"
							data-options="editable:false,required:true">
							<option value="0">否</option>
							<option value="1">是</option>
						</select>
					</div>
				</div>
				<div class="row">
					<div>
						<label>是否需要上传附件:</label>
					</div>
					<div>
						<select class="easyui-combobox" name="actvisattach"
							style="height: 35px; width: 90%"
							data-options="editable:false,required:true">
							<option value="0">否</option>
							<option value="1">是</option>
						</select>
					</div>
				</div>
				<div class="row">
					<div>
						<label>允许个人报名:</label>
					</div>
					<div>
						<select class="easyui-combobox" name="actvcanperson"
							style="height: 35px; width: 90%"
							data-options="editable:false,required:true">
							<option value="0">否</option>
							<option value="1">是</option>
						</select>
					</div>
				</div>
				<div class="row">
					<div>
						<label>个人附件路径:</label>
					</div>
					<div>
						<input name="one_url" class="easyui-filebox"
						style="height: 35px; width: 90%"/>
					</div>
				</div>
				<div class="row">
	    			<div></div>
		    		<div>
		    			<label id="fileoneName" /></a>
		    		</div>
	    		 </div>
				
				<div class="row">
					<div>
						<label>允许团队报名:</label>
					</div>
					<div>
						<select class="easyui-combobox" name="actvcanteam" style="height: 35px; width: 90%" data-options="editable:false,required:true">
							<option value="0">否</option>
							<option value="1">是</option>
						</select>
					</div>
				</div>
				<div class="row">
					<div>
						<label>团队附件路径:</label>
					</div>
					<div>
						<input name="temp_url" class="easyui-filebox"
						style="height: 35px; width: 90%"/>
					</div>
				</div>
				<div class="row">
	    			<div></div>
		    		<div>
		    			<label id="fileteamName" /></a>
		    		</div>
	    		 </div>
				
				<div class="row">
					<div>
						<label>活动介绍:</label>
					</div>
					<div>
						<input class="easyui-textbox" data-options="multiline:true" name="actvintroduce" style="width: 90%; height: 180px"/>
					</div>
				</div>
				<div class="row">
					<div>
						<label>活动详情:</label>
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