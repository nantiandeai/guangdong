<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath(); request.setAttribute("basePath", basePath); %>
<%request.setAttribute("resourceid", request.getParameter("rsid"));%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>培训师资管理</title>
<%@include file="/pages/comm/header.jsp"%>

<script>
var winform = new WhuiWinForm("#teacher_edit",{height:250});


/**
 * 添加培训老师
 */
function addTeacher(index){
	var row = WHdg.getRowData(index);
	winform.openWin();
	winform.setWinTitle("添加培训老师");
	clear();
	winform.getWinFooter().find("a:eq(0)").show();
	var frm = winform.getWinBody().find('form').form({
		url : getFullUrl('/admin/tea/save'),
		onSubmit : function(param){
            var _is = $(this).form('enableValidation').form('validate');
            if (!_is) {
            	winform.oneClick(function(){ frm.submit(); });
			}
            return _is;
        },
		success : function(data) {
			var Json = $.parseJSON(data);
			if(Json && Json.success == "0"){
				$('#teacherDG').datagrid('reload');
				$.messager.alert('提示', '操作成功!');
				winform.closeWin();
			   }else{
				   $.messager.alert('提示', '操作失败!');
				   winform.oneClick(function(){ frm.submit(); });
			   }
		}
	});
	frm.form("clear");
	_showImg(row);
	winform.oneClick(function(){ frm.submit(); });
 }
 
 
function clear(){
	$("#teachertype").combobox("clear");
	$("#teacherarttyp").combobox("clear");
}

/**
 * 修改培训老师
 */
function editTeacher(index) {
	var row = WHdg.getRowData(index);
	row.teacherregtime = datetimeFMT(row.teacherregtime);
	if (row.teacherstate != "1") {
		$.messager.alert("提示", "不能符合修改要求！");
		return;
	}
	//alert(JSON.stringify(row.enroldesc));
	winform.openWin();
	winform.setWinTitle("修改培训老师");
	clear();
	winform.getWinFooter().find("a:eq(0)").show();
	var frm = winform.getWinBody().find('form').form({
		url : getFullUrl('/admin/tea/save'),
		onSubmit : function(param) {
			var isSubmit = $(this).form('enableValidation').form('validate');
			if (!isSubmit) {
				winform.oneClick(function(){ frm.submit(); });
			}
			return isSubmit;
		},
		success : function(data) {
			//alert(JSON.stringify(data));
			var Json = $.parseJSON(data);
	 			if(Json && Json.success == '0'){
	 			$.messager.alert('提示', '操作成功！');
				$('#teacherDG').datagrid('reload');
				
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

/**删除培训老师
 */
function delTea(index){
	var row = WHdg.getRowData(index);
	var teacherid = row.teacherid;
	$.messager.confirm('确认对话框', '您确认要删除吗？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/tea/delteacher'),
				data: {teacherid : teacherid},
				success: function(data){
				//	alert(JSON.stringify(data));
					if(data.success == '0'){
						$('#teacherDG').datagrid('reload');
					   }else{
						   $.messager.alert('提示', '操作失败。原因：'+data.errmsg, 'error');
					   }
				}
			});
		}
	});
}

/**审核*/
function check(index){
	var row = WHdg.getRowData(index);
	var teacherid = row.teacherid;
	$.messager.confirm("确认信息", "确定要审核通过选中的项吗？", function(r){
		if (r){
			docheck(teacherid,1,2);
		}
	})


}
/**发布*/
function publish(index){
	var row = WHdg.getRowData(index);
	var teacherid = row.teacherid;
	$.messager.confirm("确认信息", "确定要发布选中的项吗？", function(r){
		if (r){
			docheck(teacherid,2,3);
		}
	})

}
/**打回审核*/
function nocheck(index){
	var row = WHdg.getRowData(index);
	var teacherid = row.teacherid;
	$.messager.confirm("确认信息", "确定要打回选中的项吗？", function(r){
		if (r){
			docheck(teacherid,2,1);
		}
	})

}
/**取消发布*/
function nopublish(index){
	var row = WHdg.getRowData(index);
	var teacherid = row.teacherid;
	$.messager.confirm("确认信息", "确定要取消发布选中的项吗？", function(r){
		if (r){
			docheck(teacherid,3,2);
		}
	})

}
/** 审核事件提交处理 */
function docheck(teacherid,fromstate,tostate){
	$.ajax({
		type: "POST",
		url: getFullUrl('/admin/tea/check'),
		data: {teacherid : teacherid,fromstate:fromstate,tostate:tostate},
		success: function(data){
			//alert(JSON.stringify(data));
			if(data.success == 'success'){
				$('#teacherDG').datagrid('reload');
				$.messager.alert('提示', ''+data.msg);
			   }else{
				   $.messager.alert('提示', '操作失败。原因：'+data.errmsg, 'error');
			   }
		}
	});
}
//处理编辑时显示图片
function _showImg(data){
	$(".img_div").remove();
		
	var imgDiv = '<div class="row img_div">'
		+'<div></div>'
		+'<div><img width="200" height="150"> </div> '
		+'</div>';
		
	if (data.teacherpic){
		var imgrow = $("[name$='teacherpic_up']").parents(".row");
		imgrow.after(imgDiv);
		var pic = WhgComm.getImgServerAddr()+data.teacherpic;
		//alert(data.teacherpic);
		imgrow.next(".img_div").find("div img").attr("src",pic);
	}
	
}

/**
 * 查看详情
 */
function look(index){
	var row = WHdg.getRowData(index);
	row.teacherregtime = datetimeFMT(row.teacherregtime);
	winform.openWin();
	winform.setWinTitle("查看详情");
	winform.getWinFooter().find("a:eq(0)").hide();
	_showImg(row);
	var frm = winform.getWinBody().find('form').form({
	});
	frm.form("clear");
	frm.form("load", row);
	$('#teacherDG').datagrid('reload');
}


/**批量审核*/
function allcheck(){
	var rows={};
	rows = $("#teacherDG").datagrid("getSelections");
	if (rows == "" || rows == null) {
		$.messager.alert('提示', '请选择要操作的数据！');
		return;
	}
	var teacherids = _split = "";
	for (var i = 0; i<rows.length; i++){
		teacherids += _split+rows[i].teacherid;
		_split = ",";
	}
	$.messager.confirm("确认信息", "确定要审核通过选中的项吗？", function(r){
		if (r){
			subcheck(teacherids,1,2);
		}
	})


}

/**批量发布*/
function allpublish(){
	var rows={};
	rows = $("#teacherDG").datagrid("getSelections");
	if (rows == "" || rows == null) {
		$.messager.alert('提示', '请选择要操作的数据！');
		return;
	}
	var teacherids = _split = "";
	for (var i = 0; i<rows.length; i++){
		teacherids += _split+rows[i].teacherid;
		_split = ",";
	}
	$.messager.confirm("确认信息", "确定要发布选中的项吗？", function(r){
		if (r){
			subcheck(teacherids,2,3);
		}
	})

}
/**批量打回*/
function allback(){
	var rows={};
	rows = $("#teacherDG").datagrid("getSelections");
	if (rows == "" || rows == null) {
		$.messager.alert('提示', '请选择要操作的数据！');
		return;
	}
	var teacherids = _split = "";
	for (var i = 0; i<rows.length; i++){
		teacherids += _split+rows[i].teacherid;
		_split = ",";
	}
	$.messager.confirm("确认信息", "确定要打回选中的项吗？", function(r){
		if (r){
			subcheck(teacherids,2,1);
		}
	})

}
/**批量取消发布*/
function allnoPublish(){
	var rows={};
	rows = $("#teacherDG").datagrid("getSelections");
	if (rows == "" || rows == null) {
		$.messager.alert('提示', '请选择要操作的数据！');
		return;
	}
	var teacherids = _split = "";
	for (var i = 0; i<rows.length; i++){
		teacherids += _split+rows[i].teacherid;
		_split = ",";
	}
	$.messager.confirm("确认信息", "确定要取消发布选中的项吗？", function(r){
		if (r){
			subcheck(teacherids,3,2);
		}
	})

}
/**
 * 一键审核提交
 */
function subcheck(teacherids,fromstate,tostate){
	$.ajax({
		type: "POST",
		url: getFullUrl('/admin/tea/checkAll'),
		data: {teacherids : teacherids,fromstate: fromstate, tostate:tostate },
		success: function(data){
			if(data.success=="success"){
				$.messager.alert("提示", data.msg);
				$('#teacherDG').datagrid('reload');
			}else{
				$.messager.alert('提示', '操作失败。原因：'+data.errmsg, 'error');
			}
		}
	});

}
/**选择列表图片*/
function chooseImg(){
	openImgCut("train",160,160,"teacherpic_up");
}
/** 页面装载完成后事件处理 */
$(function(){
	//定义表格参数
	var options = {
		title: '培训师资管理',
		url: getFullUrl('/admin/tea/findTeacher'),
		rownumbers:true,
		singleSelect:false,
		columns: [[
			{field:'ck',checkbox:true},
			{field:'teachername',title:'老师名称',width:80, sortable:true},
			{field:'teacherpic',title:'老师图片',width:80, formatter:imgFMT},
			{field:'teachertype',title:'专长类型',width:80, formatter:WhgComm.FMTTEAType},
			{field:'teacherarea',title:'区域',width:80, formatter:WhgComm.FMTAreaType},

			/*{field:'teacherarttyp',title:'艺术类型',width:80, formatter:WhgComm.FMTArtType},*/
			
			{field:'teacherstate',title:'状态',width:80, sortable:true, formatter :traStateFMT},
			{field:'opt', title:'操作', formatter: WHdg.optFMT, optDivId:'teacherOPT'}
		]]
	};


	//初始表格
	
	$("#teacherTB").css('visibility','visible');

	WHdg.init('teacherDG', 'teacherTB', options);
	//初始弹出框
	winform.init();
	
	//清除文件框的值
	$('#btn_pic').bind('click', function(){  
		$("#teacherpic_up").filebox('clear');
    });
});

</script>
</head>
<body>
	<!-- datagrid div -->
	<div id="teacherDG"></div>
	
	<!-- datagrid toolbar -->
	<div id="teacherTB" class="grid-control-group" style="display: none;">
		<div>
			<div>
				<shiro:hasPermission name="${resourceid}:add"><a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" size='small' onclick="addTeacher()">添加</a></shiro:hasPermission>
				<shiro:hasPermission name="${resourceid}:checkon"><a href="javascript:void(0)" class="easyui-linkbutton"  size='small' onclick="allcheck()">批量审核</a></shiro:hasPermission>
				<shiro:hasPermission name="${resourceid}:checkoff"><a href="javascript:void(0)" class="easyui-linkbutton"  size='small' onclick="allback()">批量打回</a></shiro:hasPermission>
				<shiro:hasPermission name="${resourceid}:publish"><a href="javascript:void(0)" class="easyui-linkbutton" size='small' onclick="allpublish()">批量发布</a></shiro:hasPermission>
			<shiro:hasPermission name="${resourceid}:publishoff"><a href="javascript:void(0)" class="easyui-linkbutton" size='small' onclick="allnoPublish()">批量取消发布</a></shiro:hasPermission>
			</div>
		</div>
		<div style="padding-top: 5px">
			艺术类型: 
			<input class="easyui-combobox" name="teacherarttyp" data-options="valueField:'id', textField:'text', data:WhgComm.getArtType()"/>
			区域：
			<input class="easyui-combobox" name="teacherarea" data-options="valueField:'id', textField:'text', data:WhgComm.getAreaType()"/>
			老师名称:
			<input class="easyui-textbox" name="teachername" data-options="validType:'length[1,30]'"/>
			状态:
			<select class="easyui-combobox" name="teacherstate" >
				<option value="">全部</option>
				<option value="1">未审核</option>
				<option value="2">已审核</option>
				<option value="3">已发布</option>
			</select>
			<a href="javascript:void(0)" class="easyui-linkbutton search" iconCls="icon-search">查询</a>
		</div>
	</div>
	
	<!-- 操作按钮 -->
	<div id="teacherOPT" style="display:none" >
		<shiro:hasPermission name="${resourceid}:edit"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="teacherstate" validVal="1" method="editTeacher">修改</a></shiro:hasPermission> 
		<shiro:hasPermission name="${resourceid}:del"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="teacherstate" validVal="1" method="delTea">删除</a></shiro:hasPermission>
		<shiro:hasPermission name="${resourceid}:view"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" method="look">查看详情</a></shiro:hasPermission>
		<shiro:hasPermission name="${resourceid}:checkon"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="teacherstate" validVal="1" method="check">审核</a></shiro:hasPermission>
		<shiro:hasPermission name="${resourceid}:checkoff"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="teacherstate" validVal="2" method="nocheck">打回</a></shiro:hasPermission>
		<shiro:hasPermission name="${resourceid}:publish"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="teacherstate" validVal="2" method="publish">发布</a></shiro:hasPermission> 
		<shiro:hasPermission name="${resourceid}:publishoff"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="teacherstate" validVal="3" method="nopublish">取消发布</a></shiro:hasPermission>
	</div>
	
	 <div id="teacher_edit" class="none" style="display:none"  data-options=" fit:true" >
		    		
			 <form id="ff" method="post" enctype="multipart/form-data" >
				<!-- 隐藏域  -->
				<input type="hidden" id="teacherid" name="teacherid" value="" />
		  		<input type="hidden" id="teacherpic" name="teacherpic" value=""/>

				<div class="main">

		    		<div class="row">
		    			<div>老师名称:</div>
		    			<div>
							<input id="teachername" class="easyui-textbox" name="teachername" style="width:100%;height:35px;"  data-options="required:true, validType:'length[1,30]'"/>
		    			</div>
		    		 </div>
					 <div class="row">
		    			<div>专长类型:</div>
		    			<div>
							<select class="easyui-combobox" id="teachertype" name="teachertype" panelHeight="auto" limitToList="true" style="width:100%;height:35px;" data-options="editable:false,valueField:'text', textField:'text', data:WhgComm.getTEAType()"></select>
		    				<%--<input class="easyui-combobox" id="teachertype" name="teachertype" data-options="editable:false,multiple:true, required:true, valueField:'typname',textField:'typname',url:'${basePath}/comm/whtyp?type=22'" style="width:100%;height:35px;">--%>
		    			</div>
		    		</div> 
		    		
		    		<div class="row">
		    			<div>艺术类型:</div>
		    			<div>
							<select class="easyui-combobox" id="teacherarttyp" name="teacherarttyp" panelHeight="auto" limitToList="true" style="width:100%;height:35px;" data-options="multiple:true,required:true,editable:false,valueField:'id', textField:'text', data:WhgComm.getArtType()"></select>
		    				<%--<input id="teacherarttyp" class="easyui-combobox" name="teacherarttyp" style="width:100%;height:35px;" data-options="editable:false,multiple:true, required:true, valueField:'typname',textField:'typname',data:WhgComm.getArtType()"/>--%>
		    			</div>
		    		</div>

		    		<div class="row">
		    			<div>老师所属区域:</div>
		    			<div>
		    				<%--<input id="teacherarea" class="easyui-combobox" name="teacherarea" style="width:100%;height:35px"
									data-options="panelHeight:'auto', editable:false, required:true, valueField:'typid',textField:'typname',url:'${basePath}/comm/whtyp?type=8'"/>--%>
								<select class="easyui-combobox" name="teacherarea" panelHeight="auto" limitToList="true" style="width:100%;height:35px;" data-options="required:true,editable:false,valueField:'id', textField:'text', data:WhgComm.getAreaType()"></select>
		    			</div>
		    		</div>

		    		<div class="row">
		    			<div>老师图片:</div>
		    			<div>
		    				<input id="teacherpic_up"  class="easyui-filebox" name="teacherpic_up" style="width:85%;height:35px;" data-options="validType:'isIMG[\'teacherpic_up\']', buttonText:'选择图片', accept:'image/jpeg,image/peg, image/png'">
							<a id="btn_pic" class="easyui-linkbutton" data-options="plain:true," iconCls="icon-clear" style="width:9%;height:32px;">清除</a>
		    			</div>
		    		 </div>
		    		 
		    		<div class="row">
		    			<div>老师课程说明：</div>
		    			<div>
		    				<input class="easyui-textbox" id="teachercourse" name="teachercourse" data-options="validType:'length[0,60]',multiline:true " style="width:100%;height:35px;">
		    			</div>
		    		</div>
		    		<div class="row">
		    			<div>注册日期:</div>
		    			<div><input id="teacherregtime" name="teacherregtime" type="text" class="easyui-datetimebox" data-options=" required:true"  style="height: 35px; width: 100%"/></div>
		    		</div>
		    		<div class="row">
		    			<div>培训老师简介：</div>
		    			<div>
		    				<input class="easyui-textbox" id="teacherintroduce" name="teacherintroduce" data-options="validType:'length[0,400]',multiline:true " style="width:100%;height:80px;">
		    			</div>
		    		</div>
		    		<div class="row">
		    			<div>专长介绍：</div>
		    			<div>
		    				<input class="easyui-textbox" id="teacherexpdesc" name="teacherexpdesc" data-options="validType:'length[0,400]',multiline:true " style="width:100%;height:80px;">
		    			</div>
		    		</div>
		    		<!-- <div class="row">
		    			<div>开课介绍：</div>
		    			<div>
		    				<input class="easyui-textbox" id="teacherstartdesc" name="teacherstartdesc" data-options="validType:'length[0,200]',multiline:true " style="width:100%;height:80px;">
		    			</div>
		    		</div> -->
				</div>
			</form>
		</div>	
	
	
	
	
</body>
</html>