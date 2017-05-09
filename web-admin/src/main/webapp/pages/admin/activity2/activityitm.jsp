<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath(); request.setAttribute("basePath", basePath); %>
<%request.setAttribute("resourceid", request.getParameter("rsid"));%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>资源信息列表</title>
<base href="${basePath }/">
<%@include file="/pages/comm/header.jsp"%>
<script type="text/javascript">

var win=new WhuiWinForm("#frm",{height:'70%'});
//清除表单
function clearForm(frm){
	frm.form('disableValidation').form("clear");
}

/**增加活动场次*/
function addactitm() {
	win.openWin();
	win.setWinTitle("增加活动场次信息");
	var frm = win.getWinBody().find("form");
	clearForm(frm);
	frm.form({
		url:getFullUrl("./admin/activity/addoreditActivityitm"),
		onSubmit : function(param){
			//报名时间>结束时间
			var s = $('.actvitmsdate').datetimebox('getValue');
			var e = $('.actvitmedate').datetimebox('getValue');
			var ss = $('.actvbmsdate').datetimebox('getValue');
			var ee = $('.actvbmedate').datetimebox('getValue');
			
			var stime_ = new Date(s);
			var etime_ = new Date(e); 
			var stimebm_ = new Date(ss);
			var etimebm_ = new Date(ee); 
			
			if(ee != null && ss ==null ){
				 $.messager.alert("提示", "请填写活动报名开始时间");
				 win.oneClick(function(){ frm.submit(); });
				 return false;
			}
			if(s !=null && e != null){
			 if(stime_ >= etime_){
				 $.messager.alert("提示", "活动开始时间应小于活动结束时间!");
				 win.oneClick(function(){ frm.submit(); });
				 return false;
			 	}	
			}
			if(ss !=null && ee != null){
				 if(stimebm_ >= etimebm_){
					 $.messager.alert("提示", "活动报名开始时间应小于活动报名结束时间!");
					 win.oneClick(function(){ frm.submit(); });
					 return false;
				}	
			}
			
			if(s !=null && ss != null){
				if(ee == null){
					 $.messager.alert("提示", "请填写活动结束时间");
					 win.oneClick(function(){ frm.submit(); });
					 return false;
				}
				if(stime_<=etimebm_){
					 $.messager.alert("提示", "活动报名结束时间应小于活动开始时间!");
					 win.oneClick(function(){ frm.submit(); });
					 return false;
				}
			}
			//param.actvitmonemax = $(".actvitmonemax").val();
 			//param.actvitmupworks = $(".actvitmupworks").val();
			param.actvrefid=actvrefid;
			var sb = $(this).form('enableValidation').form('validate')
			if(sb == false){
				win.oneClick(function(){ frm.submit(); });
			}
		    return sb;	
		},
		success : function(data){
			 //var json = $.parseJSON(data);
			 data = eval('('+data+')');
			 if (data=="success"){
	            	$.messager.alert("提示", "新增成功！");
	            	win.closeWin();
	            	$("#ent_grid").datagrid("reload");
	            }else{
	            	$.messager.alert("提示", '新增失败！');
	            	win.oneClick(function(){ frm.submit(); });
	            }
		}
	});
	
	win.oneClick(function(){ frm.submit(); });
}


/**修改活动场次*/
function eidtactitm(idx) {
	var rows = $("#ent_grid").datagrid("getRows");
	var row = rows[idx];
	win.openWin();
	win.setWinTitle("修改活动场次信息");
	var frm = win.getWinBody().find("form");
	clearForm(frm);
	// $(".actvitmsdate").datetimebox("readonly",false);
	// $(".actvitmedate").datetimebox("readonly",false);
	frm.form({
		url:getFullUrl("./admin/activity/addoreditActivityitm"),
		onSubmit : function(param){
			//开始时间>结束时间
			var s = $('.actvitmsdate').datetimebox('getValue');
			var e = $('.actvitmedate').datetimebox('getValue');
			//报名开始时间>报名结束时间
			var ss = $('.actvbmsdate').datetimebox('getValue');
			var ee = $('.actvbmedate').datetimebox('getValue');
			
			var stime_ = new Date(s);
			var etime_ = new Date(e); 
			var stimebm_ = new Date(ss);
			var etimebm_ = new Date(ee); 
			
			if(ee != null && ss == null ){
				 $.messager.alert("提示", "请填写活动报名开始时间");
				 win.oneClick(function(){ frm.submit(); });
				 return false;
			}
			if(s !=null && e != null){
			 if(stime_ >= etime_){
				 $.messager.alert("提示", "活动开始时间应小于活动结束时间!");
				 win.oneClick(function(){ frm.submit(); });
				 return false;
			 	}	
			}
			if(ss !=null && ee != null){
				 if(stimebm_ >= etimebm_){
					 $.messager.alert("提示", "活动报名开始时间应小于活动报名结束时间!");
					 win.oneClick(function(){ frm.submit(); });
					 return false;
				}	
			}
			
			if(s !=null && ss != null){
				if(ee == null){
					 $.messager.alert("提示", "请填写活动结束时间");
					 win.oneClick(function(){ frm.submit(); });
					 return false;
				}
				if(stime_<=etimebm_){
					 $.messager.alert("提示", "活动报名结束时间应小于活动开始时间!");
					 win.oneClick(function(){ frm.submit(); });
					 return false;
				}
			}

			param.actvitmid=row.actvitmid;
			param.actvrefid=actvrefid;
			var sb = $(this).form('enableValidation').form('validate')
			if(sb == false){
				win.oneClick(function(){ frm.submit(); });
			}
		    return sb;	
		},
		success : function(data){
		 if (data=="success"){
            	$.messager.alert("提示", "修改成功！");
            	win.closeWin();
            	$("#ent_grid").datagrid("reload");
            }else{
            	$.messager.alert("提示", '修改失败！');
            	win.oneClick(function(){ frm.submit(); });
            }
		}
	});
	var _data = $.extend({}, row,
		{
			actvitmsdate : datetimeFMT(row.actvitmsdate),
			actvitmedate : datetimeFMT(row.actvitmedate),
			actvbmsdate  : datetimeFMT(row.actvbmsdate),
			actvbmedate  : datetimeFMT(row.actvbmedate)
		});
	frm.form("load", _data);
	 
	win.oneClick(function(){ frm.submit(); });
}


function removeactitm(idx){
	var row = WHdg.getRowData(idx);
	$.messager.confirm('确认对话框', '您想要删除此记录吗？', function(r) {
		if (r) {
			$.ajax({
				url : './admin/activity/removeactivity',
				data : {
					id : row.actvitmid
				},
				success : function(data) {
					if (data.success) {
						$.messager.alert("提示", "删除成功！");
						$("#ent_grid").datagrid("reload");
					} else {
						$.messager.alert("提示", "删除该记录失败！");
					}
				}
			})
		}
	});
}

function stateFMT(val){
	return val=="0" ? "无效" : "有效";
} 

function peopleFMT(val){
	return val=="0" ? "不限" : val;
} 

var canEdit="";
var actvrefid="";
var actvisenrol="";
var actvisyp = "";
$(function(){
	canEdit = "${canEdit}";
   	actvrefid = "${actvrefid}";
  	actvisenrol = "${actvisenrol}";
  	actvisyp = "${actvisyp}";
	if(actvisenrol == 0){
  		$(".actvbmsdate").textbox('disable');
  		$(".actvbmedate").textbox('disable');
  		$(".actvbmsdate").textbox( {required:false} );
  		$(".actvbmedate").textbox( {required:false} );
  	}else{
  		$(".actvbmsdate").textbox('enable');
  		$(".actvbmedate").textbox('enable');
  		$(".actvbmsdate").textbox( {required:true} );
  		$(".actvbmedate").textbox( {required:true} );
  	} 
  	
	//不需要 报名 不需要订票
	if(actvisyp == 0 && actvisenrol == 0){
		$(".actvitmonemax").combobox( {required:false} );
		$(".actvitmdpcount").textbox( {required:false} );
	}else{
		$(".actvitmonemax").combobox( {required:true} );
		$(".actvitmdpcount").textbox( {required:true} );
	}
	
	
	if(canEdit=="false"){
		$("#checklistOPT").find("a").css("display","none");
	} 
	
	var options = {
		title : "活动场次列表",
		url : "./admin/activity/activityitmList",
		//toolbar : "#tb",
		queryParams:{
			actvrefid:actvrefid
		},
		columns : [[
			{field:'actvitmid',title:'活动场次标识',width:100},
			{field:'actvitmsdate',title:'活动开始时间',sortable:true,width:80,formatter: datetimeFMT},
			{field:'actvitmedate',title:'活动结束时间',sortable:true,width:80,formatter: datetimeFMT},
			{field:'actvbmsdate',title:'活动报名开始时间',sortable:true,width:80,formatter: datetimeFMT},
			{field:'actvbmedate',title:'活动报名结束时间',sortable:true,width:80,formatter: datetimeFMT},
			{field:'actvitmdpcount',title:'活动订票/报名限制',width:100,sortable:true,formatter:peopleFMT},
			{field:'actvitmonemax',title:'个人订票/报名限制',width:100,sortable:true},
			{field:'actvitmupworks',title:'活动是否上传作品',width:100,formatter:yesNoFMT},
			{field:'ismoney',title:'是否需要收费',width:100,formatter:yesNoFMT},
			{field:'actvitmstate',title:'活动状态',width:100,formatter:stateFMT},
			{field:'opt',title:'操作', formatter: WHdg.optFMT, optDivId:'checklistOPT'}
		]]
	}
	//初始化数据表格
	WHdg.init('ent_grid', canEdit=="true" ? 'tb' : '', options);
	//表单初始化
	win.init();
})
</script>
</head>
<body>
		<!-- 数据表格 -->
		<table id="ent_grid" class="easyui-datagrid"></table>
		
		<!--表格操作  -->
		<div id="checklistOPT" style="display: none;">
		<%-- <shiro:hasPermission name="${resourceid }:edit"> --%>
			<a href="javascript:void(0)"  method="eidtactitm">编辑</a>
		<%-- </shiro:hasPermission> --%>
			
		<%-- <shiro:hasPermission name="${resourceid }:del">	 --%>
			<a href="javascript:void(0)"   method="removeactitm">删除</a>
		<%-- </shiro:hasPermission>	--%>
		
		</div>
		<!-- 工具条 -->
		<div id="tb" style="display: none; padding:5px">
			<div>
				<%-- <shiro:hasPermission name="${resourceid}:add"> --%>
					<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-add"  onclick="addactitm();">添加</a>
				<%-- </shiro:hasPermission> --%>
			</div>
		</div>
		<!-- 编辑表单 -->
		<div id="frm" style="display:none">
			<form method="post" enctype="multipart/form-data">
				<div class="main">
					<div class="row">
							<div>
								<label>活动开始时间</label>
							</div>
							<div>
								<input name="actvitmsdate" class="easyui-datetimebox actvitmsdate"
								style="height: 35px; width: 90%" data-options="required:true" />
							</div>
					</div>
					<div class="row">
						<div>
							<label>活动结束时间:</label>
						</div>
						<div>
							<input name="actvitmedate" class="easyui-datetimebox actvitmedate"
								style="height: 35px; width: 90%" data-options="required:true"/>
						</div>
					</div>
					<div class="row">
							<div>
								<label>活动报名开始时间</label>
							</div>
							<div>
								<input name="actvbmsdate" class="easyui-datetimebox actvbmsdate"
								style="height: 35px; width: 90%"/>
							</div>
					</div>
					<div class="row">
							<div>
								<label>活动报名结束时间</label>
							</div>
							<div>
								<input name="actvbmedate" class="easyui-datetimebox actvbmedate"
								style="height: 35px; width: 90%" />
							</div>
					</div>
					
					<div class="row">
						<div>
							<label>报名/预票数限制:</label>
						</div>
						<div>
							<select class="easyui-combobox actvitmdpcount" name="actvitmdpcount" required=true style="width:90%;height: 35px;" editable=true>   
							    <option value="50">50</option>
							    <option value="100">100</option>   
							    <option value="500">500</option>   
							    <option value="0">不限制</option>   
								
							</select> 
						</div>
					</div>
					<div class="row">
						<div>
							<label>单人报名/票数限制:</label>
						</div>
						<div>
							<select class="easyui-combobox actvitmonemax" name="actvitmonemax" required=true style="width:90%;height: 35px;" editable=false>   
							    <option value="1">1</option>
							    <option value="2">2</option>   
							    <option value="3">3</option>   
							    <option value="4">4</option>   
							    <option value="5">5</option>   
							    <option value="6">6</option>   
							    <option value="7">7</option>   
							    <option value="8">8</option>   
							    <option value="9">9</option>  
							</select> 
						</div>
					</div>
					
					<div class="row">
						<div>
							<label>是否收费:</label>
						</div>
						<div>
							<select class="easyui-combobox ismoney" name="ismoney" style="height: 35px; width: 90%" 
								data-options="editable:false,required:true">
								<option value="0">否</option>
								<option value="1">是</option>
							</select>
						</div>
					</div>
					
					<div class="row">
						<div>
							<label>是否上传作品:</label>
						</div>
						<div>
							<select class="easyui-combobox actvitmupworks" name="actvitmupworks" style="height: 35px; width: 90%" 
								data-options="editable:false,required:true">
								<option value="0">否</option>
								<option value="1">是</option>
							</select>
						</div>
					</div>
					
					
				</div>
			</form>
		</div>
</body>
</html>