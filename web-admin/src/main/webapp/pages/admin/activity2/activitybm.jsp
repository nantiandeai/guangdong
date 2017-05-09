<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()+ request.getContextPath();request.setAttribute("basePath", basePath);%>
<%request.setAttribute("resourceid", request.getParameter("rsid"));%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>活动报名管理</title>
<base href="${basePath }/">
<%@include file="/pages/comm/header.jsp"%>
<script type="text/javascript">
// 新建 活动审核表单
var winform = new WhuiWinForm("#act_ff",{height:350});

/** 验证审核 */
function validCheckFun(index){
	var row = WHdg.getRowData(index);
	if(row.actbmstate == 1 && row.actshstate == 0){
		return true;
	}
	return false;
}

/** 删除方法 */
function delActbm(index){
	var row = WHdg.getRowData(index);
	var actbmid = row.actbmid;
	//alert(JSON.stringify(row));
	$.messager.confirm('确认对话框', '您确认要删除吗？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/activity/delActbm'),
				data: {actbmid : actbmid},
				success: function(data){
					if(data.success){
						 $.messager.alert('提示', '操作成功！');
						$('#activityDiv').datagrid('reload');
						
					   }else{
						   $.messager.alert('提示', '操作失败。原因：'+data.msg, 'error');
					   }
				}
			});
		}
	});
}

//下载附件
function downfile(idx){
	var row = WHdg.getRowData(idx);
	if (!row.actbmfilepath){
		alert("not path");
		return;
	}
	var downURL =getFullUrl("/whtools/downFile?filePath="+row.actbmfilepath);
	window.location = downURL;
}

//判断是否能够进行审核
/* function _validFun(index){
	var row = WHdg.getRowData(index);
	return row.actbmisb != '0' && row.actshstate == '0' || row.actshstate == '0'&& row.actbmisa != '0' ;
} */

/**报名 工具栏 search 加载数据*/
function datagridLoad(){ 
	$("#actbmstatemsg").textbox('enable');
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
        url :'./admin/activity/loadActivitybm',
        queryParams : this.queryParams
    });
}

/**批量报名*/
function plBM(){
	var rows = $('#activityDiv').datagrid('getSelections');
	//未选中行
	if(rows.length<1){
		$.messager.alert('提示', '请选中要审核的记录！');
		return;
	}
	//不需要报名
	if(rows.actshstate == 2){
		$.messager.alert('提示', '活动报名含有不需要审核的！');
		return;
	}
	//判断 选中 是否 有已审核 或不需要审核
	for(var i=0;i<rows.length;i++){
		var count = rows[i].actbmcount;
		if(rows[i].actbmstate == 1 && rows[i].actshstate != 0 ){
			$.messager.alert('提示', '活动报名含有已审核或不需要审核！');
			return;
		}		
	}
	
	//判断 余票 是否 足够
/* 	for(var i=0;i<rows.length;i++){
	var count = rows[i].actbmcount;
		if(rows[i].actbmstate == 1){
			$.messager.alert('提示', '活动报名含有已审核！');
			return;
		}		
		for(var j=i+1; j<rows.length; j++ ){
			//有相同的报名活动场次
			if(rows[i].actvitmid == rows[j].actvitmid && rows[i].actid == rows[j].actid){
				count += rows[j].actbmcount; 
				}
			}
			//报名余票 < 报名数
			if(rows[i].actvitmbmcount < count){
				
				$.messager.alert('提示', '活动报名人数/票数 不足！');
				return;
			}
	} */
	
	var params="";
	var _params="";
	//获取报名id 数组
	for(var i=0;i<rows.length;i++){
		 params+=_params+rows[i].actbmid;
    	 _params=",";
	}

	//清空表单
	var frm=winform.getWinBody().find('form');
	//打开表单
	winform.openWin();
	winform.setWinTitle("批量审核报名人员");
	frm.form('clear');
	$("#actbmstatemsg").textbox('enable');
	frm.form({
		url : getFullUrl('/admin/activity/updateselectActivitybm'),
		onSubmit : function(param) {
			//审核失败信息
			var val = $("#actbmstatemsg").val();
			//param.actbmstatemsg = val;
			if(nnv == 0){
				if(val != null && val != ""){
					param.actbmstatemsg = val;
				}else{
					param.actbmstatemsg = "报名人数已满，审核不通过！";
				}
			}else{
				param.actbmstatemsg = " ";
			}
			//活动报名id
			param.params = params;
			var sb = $(this).form('enableValidation').form('validate');
			
			if(sb == false){
				winform.oneClick(function(){ frm.submit(); });
			}
			
			return sb;
		}, 
		success : function(data) {
			var Json = $.parseJSON(data);
	 			if(Json && Json.success == '0'){
	 			$.messager.alert('提示', Json.tishi);
				$('#activityDiv').datagrid('reload');
				winform.closeWin();
			} else {
				$.messager.alert('提示', data.msg);
				winform.oneClick(function(){ frm.submit(); });
			} 
		} 
	});
	winform.oneClick(function(){ frm.submit(); });
}

/**审核报名*/
function checkactbm(index){
	$("#actbmstatemsg").textbox('enable');
	var rows = WHdg.getRowData(index);
	var actbmid = rows.actbmid;
	//不需要报名
	if(rows.actshstate == 2){
		$.messager.alert('提示', '活动不需要审核！');
		return;
	}
	//清空表单
	winform.getWinBody().find('form').form('clear');
	//判断是否隐藏审核失败原因输入框
	winform.openWin();
	winform.setWinTitle("审核报名人员");
	var frm = winform.getWinBody().find('form').form({
		url : getFullUrl('/admin/activity/updateActivitybm'),
		onSubmit : function(param) {
			param.actid = rows.actid;
			//活动报名id
			param.actbmid = actbmid;
			//报名/订票数
			param.actbmcount=rows.actbmcount;
			//活动场次id
			param.actvitmid=rows.actvitmid;
			//审核失败信息
			var val = $("#actbmstatemsg").val();
			//param.actbmstatemsg = val;
			//用户id
			param.actbmuid = rows.actbmuid;
			
			if(nnv == 0){
				if(val != null && val != ""){
					param.actbmstatemsg = val;
				}else{
					param.actbmstatemsg = "报名人数已满，审核不通过！";
				}
			}else{
				param.actbmstatemsg = " ";
			}
			
			//余票/余人
			//param.actvitmbmcount=rows.actvitmbmcount;
			var sb = $(this).form('enableValidation').form('validate');
			
			if(sb == false){
				winform.oneClick(function(){ frm.submit(); });
			}
			
			return sb;
		},
		success : function(data) {
			var Json = $.parseJSON(data);
	 			if(Json && Json.success == '0'){
	 			$.messager.alert('提示',Json.tishi);
				$('#activityDiv').datagrid('reload');
				winform.closeWin();
			} else {
				$.messager.alert('提示', data.msg);
				winform.oneClick(function(){ frm.submit(); });
			} 
		}
	});
	frm.form("clear");
	//frm.form("load", rows);
	winform.oneClick(function(){ frm.submit(); });
}

var nnv = "";
/** 页面 加载*/
$(function(){
	var options = {
			title: '活动报名管理',
			url: getFullUrl('./admin/activity/loadActivitybm'),
			singleSelect:false,
			columns: [[
		   	{field:'sh',title:'',checkbox:'true',width:20,},   
			{field:'actvshorttitle',title:'活动名称',width:100},    
			{field:'nickname',title:'用户昵称',width:100,align:'right'},
			{field:'actvitmsdate',title:'活动开始时间',width:100,align:'right',formatter:dateFMT},
			{field:'actvitmedate',title:'活动结束时间',width:100,align:'right',formatter:dateFMT},
			{field:'actbmtype',title:'团队或个人',width:100,align:'right',formatter:typeFMT},
			{field:'actbmisa',title:'是否完善资料', formatter:enrolllistFMT, width:100,align:'right'},
			{field:'actbmisb',title:'是否已上传附件', formatter:enrolllistFMT, width:100,align:'right'},
			{field:'ismoney',title:'是否需要收费',width:100,formatter:yesNoFMT},
			{field:'actvitmdpcount',title:'活动报名/预票最大数', width:100,align:'right'},
			{field:'actbmcount',title:'个人活动报名/预票数量', width:100,align:'right'},
			{field:'actbmtime',title:'报名时间', formatter :datetimeFMT, width:100,align:'right'},
			{field:'actshstate',title:'状态', formatter:eStateFMT,width:100,align:'right'},
			{field:'actbmstate',title:'报名步骤是否完成', formatter:yesNoFMT,width:100,align:'right'},
			{field:'opt', title:'操作', formatter: WHdg.optFMT, optDivId:'activityOPT'}
			]]
		};
	//初始表格
	WHdg.init('activityDiv', 'activityTool', options);
	
	//查询下拉框中的活动管理数据表格
	var option = {
			//title: '活动管理',
			idField: 'actvid',    
		    textField: 'actvshorttitle',
			pagination: true,
		    pageSize: 10,
		    pageList: [10,20,50],
		    queryParams:{actvstate:3},
		  	url: getFullUrl('./admin/activity/loadActivity'),
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
				{field:'actvstate',title:'状态',width:60, sortable:true,formatter:traStateFMT},
			]],
			toolbar: "#testdiv"
	};
	 //加载下拉框数据
	 $('.actvshorttitle').combogrid(option); 
	 
	 //
	 $("#testdiv a.search").on("click", function(){
		// var _data = $("#testdiv").find("[name]").serializeArray();
		 var params = {"actvstate":3};
		 $("#testdiv").find("[name]").each(function(){
	    	var value = $(this).val();
	    	var name = $(this).attr("name");
	    	params[name] = value;
	    });
	    this.queryParams = this.queryParams ||{};
	    $.extend(this.queryParams, params);
		 var grid = $('.actvshorttitle').combogrid("grid");
		 grid.datagrid({
			 url: getFullUrl('./admin/activity/loadActivity'),
			 queryParams: this.queryParams
		 });
	 })
	
	//search 点击查找
   var tbSearch = $("#activityTool").find(".tb_search").off("click");
   tbSearch.on("click", function () {
       datagridLoad();
   });
   //初始化表单
   winform.init();
   
	$("#actbmstate").combobox({
		onChange:function ys(nv,ov){
			nnv = nv;
			if (nv == 1) {
				$("#actbmstatemsg").textbox('disable');
				$("#actbmstatemsg").textbox('setValue','');
			}else{
				$("#actbmstatemsg").textbox('enable');
			}
		}
	});
})
</script>
</head>
<body>
<!-- 活动数据表格 工具栏-->
<div id="testdiv" style="display: none">
	活动类型:
	<input class="easyui-combobox" name="actvtype" data-options="valueField:'typid',textField:'typname',url:'${basePath}/comm/whtyp?type=1'"/>
	艺术类型: 
	<input class="easyui-combobox" name="actvarttyp" data-options="valueField:'typid',textField:'typname',url:'${basePath}/comm/whtyp?type=0'"/>
	<a id="search" class="easyui-linkbutton search" iconCls="icon-search">查询</a>
</div>

<!-- 报名数据表格 -->
<table id="activityDiv"></table>

<!-- 报名工具条 -->
<div id="activityTool" class="grid-control-group" style="display: none">
	<shiro:hasPermission name="${resourceid}:checkon">
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="plBM();" data-options="size:'small'">批量审核 </a>
	</shiro:hasPermission>
		
	<div style="padding-top: 5px">
		活动标题: 
		<input class="easyui-combogrid actvshorttitle"  name="actvshorttitle" style="width:600px;height:32px" />  
		状态：
		<select class="easyui-combobox" name=actshstate data-options="value:''" style="width:100px;height:32px">
			<option value="0">未审核</option>
			<option value="1">已审核</option>
			<option value="2">不需要</option>
			<option value="3">未通过</option>
		</select>
		步骤是否完成:
		<select class="easyui-combobox" name=actbmstate data-options="value:''" style="width:100px;height:32px">
			<option value="0">否</option>
			<option value="1">是</option>
		</select>
		<a href="javascript:void(0)" class="easyui-linkbutton tb_search" iconCls="icon-search">查询</a>
	</div>
</div>

<!-- 操作栏 -->
<div id="activityOPT" class="none" style="display: none">
	<shiro:hasPermission name="${resourceid}:view">
		<a class="easyui-linkbutton" validKey="actbmisb" validVal="1" method="downfile">查看附件</a>
	</shiro:hasPermission>
	
	<shiro:hasPermission name="${resourceid}:checkon">
		<a id="btn" class="easyui-linkbutton" data-options="plain:true" validFun="validCheckFun" method="checkactbm">审核</a>
	</shiro:hasPermission>
	
	<%-- <shiro:hasPermission name="${resourceid}:del">
		<a class="easyui-linkbutton" data-options="plain:true" validKey="actshstate" validVal="3" method="delActbm">删除</a>
	</shiro:hasPermission> --%>
	
	<!--  培训报名编辑dialog -->
	<div id="act_ff" class="none" style="display: none" >
		<form method="post">
			<div class="main">
	    		 <div class="row">
	    			<div>审核状态:</div>
		    		<div>
						<select id="actbmstate" class="easyui-combobox"
							name="actbmstate" style="height: 35px; width: 90%"
							data-options="editable:false,required:true,value:''">
							<option value="1">通过</option>
							<option value="0">不通过</option>
						<!-- <option value="2">不需要</option> -->	
						</select>
		    		</div>
	    		 </div>
	    		 <div class="row">
	    			<div>审核失败原因:</div>
		    		<div>
						<input id="actbmstatemsg" class="easyui-textbox" id="actbmstatemsg" name="actshmsg" data-options="validType:'length[0,200]',multiline:true,prompt:'报名人数已满，审核不通过！' " style="width:90%;height:150px;">
		    		</div>
	    		 </div>
			</div>
		</form>
	</div>	
</div> 

</body>
</html>