<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath(); request.setAttribute("basePath", basePath); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<%@include file="/pages/comm/header.jsp"%>
<script type="text/javascript">
var winform = new WhuiWinForm("#brand_dlg",{height:250});
/**
 * 初始品牌活动表格
 */
 var braid="${braid}";
$(function(){
	//定义表格参数
	var options = {
			queryParams:{
				bracid:braid,
			},
			title: '活动管理',	
			url: getFullUrl('/admin/brand/findActBrand'),
			columns: [[
			{field:'actvtitle',title:'活动标题',width:80},
			{field:'bracsdate',title:'活动开始日期',width:80,formatter :dateFMT},
			{field:'bracstime',title:'活动开始时间',width:80,formatter :datetimeFMT},
			{field:'bractelephone',title:'联系电话',width:80},    
			{field:'bracaddr',title:'活动地址',width:80},
	        {field:'bracstate',title:'状态',width:80,formatter :youxiaoFMT},
	        {field:'opt', title:'操作', formatter: WHdg.optFMT, optDivId:'brandOPT_act'}
			]]
	};
	
	$("#brandTool_act").css('visibility','visible');
	//初始表格
	WHdg.init('brand_act', 'brandTool_act', options);
	
	//初始弹出框
	winform.init();
	
})


/**
 * 添加活动
 */
function addBrand(){
	winform.openWin();
	winform.setWinTitle("添加活动");
	
	var frm = winform.getWinBody().find('form').form({
		url : getFullUrl('/admin/brand/savebrand'),
		onSubmit : function(param){
			var phone = $("#bractelephone").textbox('getText');
		    /* if(!(/^1(3|4|5|7|8)\d{9}$/.test(phone) || /^0\d{2,3}-?\d{7,8}$/.test(phone))){ 
		        $.messager.alert('提示', '联系方式有误，请重填！');
		        winform.oneClick(function(){ frm.submit(); });
		        return false; 
		    }; */
			param.braid = braid;
            var _is = $(this).form('enableValidation').form('validate');
            if (!_is) {
            	winform.oneClick(function(){ frm.submit(); });
			}
            return _is;
        },
		success : function(data) {
			var Json = $.parseJSON(data);
			if(Json && Json.success == "0"){
				$('#brand_act').datagrid('reload');
				$.messager.alert('提示', '操作成功!');
				winform.closeWin();
			   }else{
				   $.messager.alert('提示', '操作失败!');
				   winform.oneClick(function(){ frm.submit(); });
			   }
		}
	});
	frm.form("clear");
	winform.oneClick(function(){ frm.submit(); });
	/* winform.setFoolterBut({onClick:function(){
		frm.submit();
	}}); */
}
/** 修改*/
function editBrand(index){
	var row =WHdg.getRowData(index); 
	//winform.init();
	winform.openWin();
	winform.setWinTitle("修改活动");
	//alert(JSON.stringify(row.bracedate));
	var frm = winform.getWinBody().find('form').form({
		url : getFullUrl('/admin/brand/savebrand'),
		onSubmit : function(param) {
		    var _is = $(this).form('enableValidation').form('validate');
		    if (!_is) {
		    	 winform.oneClick(function(){ frm.submit(); });
			}
			return _is;
		},
		success : function(data) {
			//alert(JSON.stringify(data));
			var Json = $.parseJSON(data);
	 			if(Json && Json.success == '0'){
	 			$.messager.alert('提示', '操作成功！');
				$('#brand_act').datagrid('reload');
				winform.closeWin();
			} else {
				$.messager.alert('提示', '修改失败,操作无效！');
				winform.oneClick(function(){ frm.submit(); });
			}
		}
	});
	//格式化日期
	var _data = $.extend({}, row,
			{
		bracsdate : datetimeFMT(row.bracsdate),
		bracstime : datetimeFMT(row.bracstime),
		bracedate : datetimeFMT(row.bracedate),
	});
	frm.form("clear");
	frm.form("load", _data);
	winform.oneClick(function(){ frm.submit(); });
	/* winform.setFoolterBut({onClick:function(){
		frm.submit();
	}}); */
}

/**删除品牌*/
function delBrand(index){
	var row = WHdg.getRowData(index);
	var bracid = row.bracid;
	$.messager.confirm('确认对话框', '您确认要删除吗？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/brand/delActBrand'),
				data: {bracid : bracid},
				success: function(data){
				//	alert(JSON.stringify(data));
					if(data.success == '0'){
						$('#brand_act').datagrid('reload');
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
	var row = WHdg.getRowData(index);
	winform.openWin();
	winform.setWinTitle("查看详情");
	winform.getWinFooter().find("a:eq(0)").hide();
	setDisabled();
	//显示富文本的值
	UE.getEditor('traeditor').setContent(row.bradetail);
	$('#brand_ff').find("input").attr("readonly","true");
	_showImg(row);
	var frm = winform.getWinBody().find('form').form({
	});
	frm.form("clear");
	frm.form("load", row);
	$('#brapic_up').filebox('disable');
	$('#brabigpic_up').filebox('disable');
	$('#braintroduce').textbox('disable');
	$('#brand').datagrid('reload');
}

/**
 * 导入模板
 * @returns
 */
function addAct(){
	//
	var bracid = $('#bracactid').combobox('getValue');
	//
	if (bracid == "") {
		$.messager.alert('提示','请选择活动！');
		return;
	}
	$.ajax({
		url: getFullUrl('/admin/brand/getAct'),
		type: 'post',
		data: {"actvid": bracid},
		success: function(data){
			findTime(data.actvid);
			//alert(JSON.stringify(dateFMT(data.actvsdate)));
			$('#brand_act_ff').form('load',{
				bracaddr : data.actvaddress|| "",
				bracactid : data.actvid,
				bractelephone : data.actvphone,
				/* bracsdate : dateFMT(data.actvsdate) || "",
				bracstime :datetimeFMT(data.actvsdate),
				bracedate :datetimeFMT(data.actvedate), */
				//
				
				
			});
		}
	})
}

function findTime(data){
	$.ajax({
		url : getFullUrl('/admin/brand/getActTime'),
		type : 'post',
		data : {"actvrefid" : data},
		success : function(data){
			//alert(JSON.stringify(data));
			$('#brand_act_ff').form('load',{
				bracsdate : dateFMT(data.actvitmsdate),
				bracstime : datetimeFMT(data.actvitmsdate),
				bracedate : datetimeFMT(data.actvitmedate),
				
			});
		}
	})
}
</script>
</head>
<body>
	<!-- 培训管理页面 -->
	<table id="brand_act"></table>
	
	<!-- 查询栏 -->
	<div id="brandTool_act" style="visibility: hidden;padding-top: 5px" >
		<div style="line-height: 32px;">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" size='large' id="btn" onclick="addBrand()">添加</a>
		</div>
		<div style="padding-top: 5px">
		艺术类型:
		<input class="easyui-combobox" name="actvarttyp" style="width:150px" 
		data-options="editable:true, valueField:'typid',textField:'typname',url:'${basePath}/comm/whtyp?type=0'"/>
		活动类型:
		<input class="easyui-combobox" name="actvtype" style="width:150px" 
		data-options="editable:true, valueField:'typid',textField:'typname',url:'${basePath}/comm/whtyp?type=1'"/>
			<a href="#" class="easyui-linkbutton search" iconCls="icon-search">查询</a>
		</div>
		
	</div>
		      
	<!-- 操作按钮 -->
	<div id="brandOPT_act" style="display:none">
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" method="editBrand">修改</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" method="delBrand">删除</a>
	</div> 
				
	 <!-- 品牌活动dialog -->
		 
	 <div id="brand_dlg" class="none" style="display:none"  data-options=" fit:true" >
		 <form id="brand_act_ff" method="post" enctype="multipart/form-data">
		 	<input type="hidden" id="bracid" name="bracid" value="">   
		 	<!-- <input type="hidden" id="braid" name="braid" value="">    --> 
		 	<div class="main">
	    		<div class="row">
	    			<div>选择活动:</div>
	    			<div>
	    				<input style="width:70%;height:32px;" class="easyui-combobox" name="bracactid" id="bracactid" data-options="valueField:'actvid',editable:false,textField:'actvtitle', url:'${basePath}/admin/brand/getActTitle'"/>
	    				<a class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addAct()">导入活动</a>
	    			</div>
	    		</div>
	    		<div class="row">
	    			<div>活动开始日期:</div>
	    			<div>
	    				<input id="bracsdate" type="text" name="bracsdate" style="width:80%;height:32px;" class="easyui-datebox" required="required"></input> 
	    			</div>
	    		</div>
	    		 <div class="row">
	    			<div>活动开始时间:</div>
		    		<div>
						<input id="bracstime" name="bracstime" class="easyui-datetimebox"  style="width:80%;height:32px;" required="required" data-options="" />
		    		</div>
	    		 </div>
	    		 <div class="row">
	    			<div>活动结束时间:</div>
		    		<div>
						<input id="bracedate" name="bracedate" class="easyui-datetimebox"  style="width:80%;height:32px;" required="required" data-options="" />
		    		</div>
	    		 </div>
	    		 <div class="row">
	    			<div>联系电话:</div>
		    		<div>
						<input id="bractelephone" class="easyui-textbox" name="bractelephone" style="width:80%;height:32px;" data-options="validType:'isPhone[\'bractelephone\']'"/>
		    		</div>
	    		 </div>
	    		 <div class="row">
	    			<div>活动地址:</div>
	    			<div>
	    				<input id="bracaddr" class="easyui-textbox" name="bracaddr" style="width:80%;height:32px;" data-options="multiline:true,validType:'length[1,50]'"/>
	    			</div>
	    		</div>
	    	</div>
		</form>
	</div>	
		
</body>
</html>