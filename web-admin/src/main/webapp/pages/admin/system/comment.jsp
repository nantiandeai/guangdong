<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<title>活动模板编辑</title>
<%@include file="/pages/comm/header.jsp"%>
<base href="${basePath }/">
<title>评论后台</title>
<script type="text/javascript">
//new 弹出窗体
var Win = new WhuiWinForm("#win",{height:'65%'});
var lookwin=new WhuiWinForm("#lookwin",{height:'65%'});

function _validFun(idx) {
	var row = WHdg.getRowData(idx);
	if(row.rmstate == 0){
		return true;
	}else{
		return false;
	}
}

function _validFun2(idx) {
	var row = WHdg.getRowData(idx);
	if(row.rmstate == 1 && row.ishf == 0){
		return true;
	}else{
		return false;
	}
}

/**
 * g格式化 评论状态
 */
function stateFMT(val, row, index) {
	return val == "0" ? '待审核' : val == "1" ? "审核通过" : "审核拒绝";
}

var winform = new WhuiWinForm("#idx_win",{width:400,height:300});
//工具栏 search 加载数据
function datagridLoad(){ 
    var tb = $("#tb");
    var datagrid=$("#dis_grid");
    var params = {};
    tb.find("[name]").each(function(){
    	var value = $(this).val();
    	var name = $(this).attr("name");
    	params[name] = value;
    });
    this.queryParams = this.queryParams ||{};
    $.extend(this.queryParams, params);

    datagrid.datagrid({
        url :'./admin/act/loaddisList',
        queryParams : this.queryParams
    });
}

//删除此条活动记录
function removediscuss(idx) {
		var msg="";
		var row = WHdg.getRowData(idx);
		if(row.ishf=="0"){
			msg="您想要删除此评论信息吗?";
		}else{
			msg="您想要删除此评论与回复吗?";
		}
		$.messager.confirm('确认对话框', msg, function(r) {
			if (r) {
				$.ajax({
					url : './admin/dis/removediscuss',
					data : {
						id : row.rmid,
						rmtyp:row.rmtyp
					},
					success : function(data) {
						if (data.success) {
							$.messager.alert("提示", "删除成功！");
							$("#dis_grid").datagrid("reload");
						} else {
							$.messager.alert("提示", "删除该记录失败"+data.msg+"!");
						}
					}
				})
			}
		});
}

/**查看评论回复信息 */
function lookPLHF(idx) {
	$("#rmtitle").html('');
	$("#plmsg").html('');
	$("#rmdate").html('');
	$("#hftype").html('');
	$("#hfmsg").html('');
	$("#hfdate").html('');
	
	var rows = $("#dis_grid").datagrid("getRows");
	var row = rows[idx];
	lookwin.openWin();
	lookwin.setWinTitle("查看评论回复信息");
	$("#hfdate").html('');
	$("#hfmsg").html('');
	$("#hfuser").html('');
	$.ajax({
		url : getFullUrl("./admin/dis/loadhfList"),
		data : {
			rmrefid: row.rmid,
		},
		success : function(data) {
			if(data != "nodata"){
			$("#hfdate").html("回复时间:"+datetimeFMT(data.rmdate));
			$("#hfmsg").html("管理员("+data.nickname+")回复:"+data.rmcontent);
			$("#hftype").html("回复类型:"+(data.rmpltype == 0?"内部回复":"公开回复"));
			}
		}
	})
	$("#rmtitle").html("标题:"+row.rmtitle);
	$("#plmsg").html("用户("+row.nickname+")评论:"+row.rmcontent);
	$("#rmdate").html("评论时间:"+datetimeFMT(row.rmdate));
}

function lookPL(idx) {
	$("#rmtitle").html('');
	$("#plmsg").html('');
	$("#rmdate").html('');
	$("#hftype").html('');
	$("#hfmsg").html('');
	$("#hfdate").html('');
	var rows = $("#dis_grid").datagrid("getRows");
	var row = rows[idx];
	lookwin.openWin();
	lookwin.setWinTitle("查看评论信息");
	$("#plmsg").html("<font color='#FF8040'>评论人:</font>"+row.nickname);
	$("#rmdate").html("<font color='#FF8040'>评论标题:</font>"+row.rmtitle);
	$("#hftype").html("<font color='#FF8040'>评论内容:</font>"+row.rmcontent);
	$("#hfmsg").html("<font color='#FF8040'>评论时间:</font>"+datetimeFMT(row.rmdate));
	//$("#hfdate").html("<font color='#FF8040'>评论类型:</font>"+shitiFMT(row.rmrefid));
}

/**管理员 回复评论*/
function adminHF(idx) {
	var rows = $("#dis_grid").datagrid("getRows");
	var row = rows[idx];
	Win.openWin();
	Win.setWinTitle("编辑回复信息");
	var frm = $("#win").find("form");
	$("#title").html(row.rmtitle);
	$("#msg").html(row.rmcontent);
	$("#rmcontent").textbox("setValue",'');
	//setFormData(frm, row);
	frm.form({
		url:"./admin/dis/addCommentHF",
		onSubmit : function(param){
			param.rmid = row.rmid;
			param.rmuid = row.rmuid;
			var val = $("#rmhftype").combobox('getValue');
			param.rmpltype=val;
		    var sb = $(this).form('enableValidation').form('validate');
		    if(sb == false){
		    	Win.oneClick(function(){ frm.submit(); });
			}
		    return sb;
		},
		success : function(data){
			var json = $.parseJSON(data);
			if (json.success){
				$.messager.alert("提示","回复评论成功!");
				$("#dis_grid").datagrid("reload");
				Win.closeWin();
			}else{
				$.messager.alert("失败了","回复评论失败!");
				Win.oneClick(function(){ frm.submit(); });
			} 
		}
	});
	Win.oneClick(function(){ frm.submit(); });
}

/**审核评论*/
function adminCheck(index){
	
	var rows = WHdg.getRowData(index);
	var rmid = rows.rmid;
	//判断是否隐藏审核失败原因输入框
	winform.openWin();
	winform.setWinTitle("审核评论信息");
	var frm = winform.getWinBody().find('form').form({
		url : getFullUrl('/admin/dis/contentEdit'),
		onSubmit : function(param) {
		//	你的评论信息不规范,不予通过!
			param.rmid=rmid;
			param.rmstate =  $("#ischeck").combobox('getValue');
			if(nnv == 2){
				if($("#shrmcontent").textbox('getValue') == "" || $("#shrmcontent").textbox('getValue')  == null){
					param.rmcontent2 = "你的评论信息不规范,不予通过!";
				}else{
					param.rmcontent2 =  $("#shrmcontent").textbox('getValue');
				}
			}
			var sb = $(this).form('enableValidation').form('validate');
			
			if(sb == false){
				winform.oneClick(function(){ frm.submit(); });
			}
			
			return sb;
		},
		success : function(data) {
			var Json = $.parseJSON(data);
	 			if(Json.success){
	 			$.messager.alert('提示',"审核成功!");
				$('#dis_grid').datagrid('reload');
				winform.closeWin();
			} else {
				$.messager.alert('提示',"审核失败!");
				winform.oneClick(function(){ frm.submit(); });
			} 
		}
	});
	frm.form("clear");
	//frm.form("load", rows);
	winform.oneClick(function(){ frm.submit(); });
}

/**
 * 评论类型的格式化
 * @param val 单元格的值
 * @param rowData 表格行数据
 * @param index 表格行索引
 * @returns 评论类型的格式化
 */
function pltypeFMT(val, rowData, index){
	return val == "1" ? '回复' : '评论';
}
/**
 * 评论状态的格式化
 * @param val 单元格的值
 * @param rowData 表格行数据
 * @param index 表格行索引
 * @returns 评论状态的格式化
 */
/* function plstateFMT(val, rowData, index){
	return val == "1" ? '有效' : '失效';
}
 */
var nnv = "";
$(function () {
	var options = {
		title : "评论列表",
		url : "./admin/dis/selecthdComment",
		toolbar : "#tb",
		columns : [[
			{field:'rmid',title:'评论ID',width:100,sortable:true},
			{field:'nickname',title:'评论人',width:100,sortable:true},
			{field:'rmtitle',title:'标题',width:100},
			{field:'rmdate',title:'评论时间',sortable:true,width:80,formatter:dateFMT},
			{field:'rmreftyp',title:'评论关联类型',width:50,formatter:shitiFMT},
			{field:'rmtyp',title:'评论类型',width:50,sortable:true,formatter:pltypeFMT},
			{field:'rmstate',title:'评论状态',width:60,sortable:true,formatter:stateFMT},
			{field:'ishf',title:'回复数',width:60},
			{field:'opt',title:'操作', formatter: WHdg.optFMT, optDivId:'checklistOPT'}
		]]
	}

	WHdg.init('dis_grid', 'tb', options);

	
	//search 点击查找
	 var tbSearch = $("#dis_grid").find("#tb").off("click");
    tbSearch.on("click", function () {
    	datagridLoad();
    });
    $("#pltype").combobox("clear");
    
    //窗体初始化
    Win.init();
    lookwin.init();
    winform.init();
    lookwin.getWinFooter().find("a:eq(0)").hide();
    
    $("#ischeck").combobox({
    	onChange:function(nv,ov){
    		nnv = nv;
    		if(nv == 1){
	    		$("#shmsg").hide();
    		}else{
	    		$("#shmsg").show();
    		}
    	}
    })
    
    
});

</script>
</head>
<body>
<table id="dis_grid" class="easyui-datagrid"></table>

<div id="checklistOPT" style="display: none;">
	<shiro:hasPermission name="${resourceid }:view">
		<a href="javascript:void(0)" method="lookPL">查看评论</a>
	</shiro:hasPermission>
	
	<shiro:hasPermission name="${resourceid }:view">
		<a href="javascript:void(0)" validKey="ishf" validVal="1" method="lookPLHF">查看回复</a>
	</shiro:hasPermission>
	
	<shiro:hasPermission name="${resourceid }:add">
		<a href="javascript:void(0)" validFun="_validFun2" method="adminHF">回复</a>
	</shiro:hasPermission>
	
	<shiro:hasPermission name="${resourceid }:del">
		<a href="javascript:void(0)" method="removediscuss">删除</a>
	</shiro:hasPermission>
	
	<%-- <shiro:hasPermission name="${resourceid }:checkon">  --%>
		<a href="javascript:void(0)" validFun="_validFun" method="adminCheck">审核</a>
	 <%-- </shiro:hasPermission>  --%>
	
</div>
<!-- 工具条 -->
<div id="tb" class="grid-control-group">
    评论关联类型:
    <input class="easyui-combobox" name="rmreftyp" style="width:100px"
           data-options="editable:true, valueField:'typid',textField:'typname',url:'./comm/whtyp?type=9'"/>
    评论信息:
    <input class="easyui-textbox" name="rmcontent" />
    <a href="javascript:void(0)" class="easyui-linkbutton search" iconCls="icon-search">查询</a>
</div>

<!--评论回复表单操作-->
	<div id="win" class="none" style="display: none">
			<form method="post">
				<div class="main">
					<div class="row">
						<div style="font-size: 24px;font-style:inherit;color:blue">
							标题:
						</div>
						<div id="title" style="font-size: 24px;font-style:inherit;">
						</div>
					</div>
					
					<div class="row">
						<div >
							评论信息:
						</div>
						<div id="msg">
						</div>
					</div>
					<div class="row">
						<div >
							回复类型:
						</div>
						<select id="rmhftype" class="easyui-combobox" name="dept" style="width:500px;"  required="true" data-options="editable:false">   
						    <option value="1">公开回复</option>   
						    <option value="2">内部回复</option>   
						</select>  
					</div>
					
					<div class="row">
						<div>
							<label>回复信息:</label>
						</div>
						<div>
							<input name="rmcontent" id="rmcontent" class="easyui-textbox"
								style="width:500px;height:180px" data-options="multiline:true,prompt:'请输入您的回复信息'"/>
						</div>
					</div>
					<div class="cls"></div>
				</div>
			</form>
		</div>
	<!--回复表单查看操作-->
	<div id="lookwin" class="none" style="display: none">
			
				<div class="main">
					<div class="row">
						<div></div>
						<div id="rmtitle" style="font-size: 24px;font-style:inherit;">
						</div>
					</div>
					
					<div class="row">
					<div></div>
						<div id="plmsg" style="font-size: 16px;font-style:inherit;">
						</div>
					</div>
					<div class="row">
					<div></div>
						<div id="rmdate" style="font-size: 16px;font-style:inherit;">
						</div>
					</div>
					
					<div class="row">
						<div></div>
						<div id="hftype" style="font-size: 16px;font-style:inherit;">
						</div> 
					</div> 
					<div class="row">
						<div></div>
						<div id="hfmsg" style="font-size: 16px;font-style:inherit;">
						</div>
					</div>
					<div class="row">
						<div></div>
						<div id="hfdate" style="font-size: 16px;font-style:inherit;">
						</div>
					</div>
					
				</div>
		</div>
		
<!-- 审核 -->
<div id="idx_win" style="display: none">
	<form method="post">
		<center>
			<div>
				<label style="font-size: 18px;font-family: inherit;">提示信息：</label><label style="font-size: 18px;font-family: inherit;">请选择你要操作的类型,提交!!!</label>
			</div>
		</center>	
		<br/>
		<div>
			是否通过审核:<select id="ischeck" class="easyui-combobox" name="dept" style="width:300px;"  required="true" data-options="editable:false">   
			    <option value="1">通过</option>   
			    <option value="2">不通过</option>   
			</select>  
		</div>
		<br/>
		<div id="shmsg">
			<label>回&nbsp;&nbsp;复&nbsp;&nbsp;信&nbsp;&nbsp;息:</label><input name="rmcontent" id="shrmcontent" class="easyui-textbox"
			style="width:300px;height:100px" data-options="multiline:true,prompt:'你的评论信息不规范,不予通过!'"/>
		</div>
	</form>
</div>
</body>
</html>