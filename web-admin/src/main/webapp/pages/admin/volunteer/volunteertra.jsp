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
var winform = new WhuiWinForm("#volun_edit",{height:250});

$(function () {   
	$("#volunTool").css('visibility', 'visible');
});

 /**
  * 初始培训批次表格
  */
 $(function(){
 	//定义表格参数
	 var options = {
		 title: '志愿培训管理',
		 url: getFullUrl('/admin/volun/findVolun'),
		 rownumbers: true,
		 singleSelect: false,
		 columns: [[
			 {field: 'ck', checkbox: true},
			 {field: 'zypxtype', title: '分类', width: 80, sortable: true, formatter:function(val,row,index){ return WhgSysData.FMT(val, WhgSysData.getTypeData('12'));}},
			 {field: 'zypxshorttitle', title: '列表短标题', width: 80, sortable: true},
			 {field: 'zypxfrom', title: '来源', width: 80, sortable: true},
			 {field: 'zypxpic', title: '列表图', width: 80, formatter: imgFMT},
			 {field: 'zypxstate', title: '状态', width: 80, formatter: traStateFMT},
			 {field: 'opt', title: '操作', formatter: WHdg.optFMT, optDivId: 'volunOPT'}
		 ]]
	 };

	 //初始表格
	 WHdg.init('volun', 'volunTool', options);

	 //初始弹出框
	 winform.init();

	 //清除文件框的值
	 $('#btn_pic').bind('click', function () {
		 $("#zypxpic_up").filebox('clear');
	 });
	 $('#btn_pic1').bind('click', function () {
		 $("#zypxvideo_up").filebox('clear');
	 });
	
 });
 
/**添加志愿培训*/
function addVolun() {
	winform.openWin();
	winform.setWinTitle("添加志愿培训");
	$("#zypxkey").combobox("clear");
	$(".img_div").remove();
	winform.getWinFooter().find("a:eq(0)").show();
	var frm = winform.getWinBody().find('form').form({
		url: getFullUrl('/admin/volun/save'),
		onSubmit: function (param) {
			var _is = $(this).form('enableValidation').form('validate');
			if (!_is) {
				winform.oneClick(function () {
					frm.submit();
				});
			} else {
				$.messager.progress();
			}
			return _is;
		},
		success: function (data) {
			$.messager.progress('close');
			var Json = $.parseJSON(data);
			if (Json && Json.success == "0") {
				$('#volun').datagrid('reload');
				winform.closeWin();
			} else {
				$.messager.alert('提示', '操作失败!');
				winform.oneClick(function () {
					frm.submit();
				});
			}
		},
		error: function (error) {
			console.log(error);
		}
	});
	$("#zypxvideo").combobox("clear");
	frm.form("clear");
	winform.oneClick(function () {
		frm.submit();
	});

}
/**修改志愿培训*/
function editVolun(index){
	var row = WHdg.getRowData(index);
	winform.openWin();
	winform.setWinTitle("修改志愿培训");
//	$("#zypxkey").combobox("clear");
	$('#zypxkey').combobox('setValue', row.zypxkey);
	winform.getWinFooter().find("a:eq(0)").show();
	var frm = winform.getWinBody().find('form').form({
		url : getFullUrl('/admin/volun/save'),
		onSubmit : function(param){
            var _is = $(this).form('enableValidation').form('validate');
            if (!_is) {
            	winform.oneClick(function(){ frm.submit(); });
			}else{
				$.messager.progress();
			}
            return _is;
        },
		success : function(data) {
			$.messager.progress('close');
			var Json = $.parseJSON(data);
			if(Json && Json.success == "0"){
				$('#volun').datagrid('reload');
				winform.closeWin();
			   }else{
				   $.messager.alert('提示', '操作失败!');
				   winform.oneClick(function(){ frm.submit(); });
			   }
		}
	});
	$("#zypxvideo").combobox("clear");
	frm.form("clear");
	frm.form('load',row);
	_showImg(row);
//	$('#zypxkey').combobox({
//		onLoadSuccess: function (data) {
//			for (var i = 0; i < data.length; i++) {
//				if (data[i].addr == row.zypxkey) {
//					$('#zypxkey').combobox('setValue', row.zypxkey);
//				}
//			}
//		}
//	});
	winform.oneClick(function(){ frm.submit(); });
}

//处理编辑时显示图片
function _showImg(data){
	$(".img_div").remove();
	//document.getElementById('fileName').innerHTML = "";	
	
	var imgDiv = '<div class="row img_div">'
		+'<div></div>'
		+'<div><img width="200" height="150"> </div> '
		+'</div>';
		
	if (data.zypxpic){
		var imgrow = $("[name$='zypxpic_up']").parents(".row");
		imgrow.after(imgDiv);
		imgrow.next(".img_div").find("div img").attr("src",getFullUrl(data.zypxpic));	
	}
	/* if (data.zypxvideo) {
		document.getElementById('fileName').innerHTML = "<span style='color:Blue'>" +data.zypxvideo+ "</span>";
	} */
	 
}
/**查看详情*/
function look(index){
	var row = WHdg.getRowData(index);
	winform.openWin();
	winform.setWinTitle("查看详情");
	winform.getWinFooter().find("a:eq(0)").hide();

	var frm = winform.getWinBody().find('form').form({
	});
	$("#zypxkey").combobox("clear");
	$('#zypxkey').combobox({onLoadSuccess : function(data){
		for (var i = 0; i < data.length; i++) {
			if (data[i].addr == row.zypxkey) {
				$('#zypxkey').combobox('setValue',row.zypxkey);
			}
		}
	}});
	frm.form("clear");
	frm.form("load", row);
	_showImg(row);
	$('#volun').datagrid('reload');

}

/**删除志愿培训*/
function delVolun(index){
	var row = WHdg.getRowData(index);
	var zypxid = row.zypxid;
	$.messager.confirm('确认对话框', '您确认要删除吗？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/volun/delpx'),
				data: {zypxid : zypxid},
				success: function(data){
				//	alert(JSON.stringify(data));
					if(data.success == '0'){
						$('#volun').datagrid('reload');
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
	var zypxid = row.zypxid;
	var title = "审核";
	docheck(title,zypxid,1,2);
}
/**发布*/
function publish(index){
	var row = WHdg.getRowData(index);
	var zypxid = row.zypxid;
	var title = "发布";
	docheck(title,zypxid,2,3);
}
/**打回审核*/
function nocheck(index){
	var row = WHdg.getRowData(index);
	var zypxid = row.zypxid;
	var title = "打回";
	docheck(title,zypxid,2,1);
}
/**取消发布*/
function nopublish(index){
	var row = WHdg.getRowData(index);
	var zypxid = row.zypxid;
	var title = "取消发布";
	docheck(title,zypxid,3,2);
}
/** 审核事件提交处理 */
function docheck(title,zypxid,fromstate,tostate){
	$.messager.confirm('确认对话框', '您确认将所选择的'+title+'吗？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/volun/check'),
				data: {zypxid : zypxid,fromstate:fromstate,tostate:tostate},
				success: function(data){
					//alert(JSON.stringify(data));
					if(data.success == 'success'){
						$('#volun').datagrid('reload');
						//$.messager.alert('提示', ''+data.msg);
					   }else{
						   $.messager.alert('提示', '操作失败。原因：'+data.errmsg, 'error');
					   }
				}
			});
		}
	});
}

/**批量审核*/
function allcheck(){
	var rows={};
	rows = $("#volun").datagrid("getSelections");
	if (rows == "" || rows == null) {
		$.messager.alert('提示', '请选择要操作的数据！');
		return;
	}
	var zypxids ="", _split = "";
	for (var i = 0; i<rows.length; i++){
		zypxids += _split+rows[i].zypxid;
		_split = ",";
	}
	subcheck(zypxids,1,2);
}

/**批量发布*/
function allpublish(){
	var rows={};
	rows = $("#volun").datagrid("getSelections");
	if (rows == "" || rows == null) {
		$.messager.alert('提示', '请选择要操作的数据！');
		return;
	}
	var zypxids ="", _split = "";
	for (var i = 0; i<rows.length; i++){
		zypxids += _split+rows[i].zypxid;
		_split = ",";
	}
	subcheck(zypxids,2,3);
}
/**批量打回*/
function allback(){
	var rows={};
	rows = $("#volun").datagrid("getSelections");
	if (rows == "" || rows == null) {
		$.messager.alert('提示', '请选择要操作的数据！');
		return;
	}
	var zypxids ="", _split = "";
	for (var i = 0; i<rows.length; i++){
		zypxids += _split+rows[i].zypxid;
		_split = ",";
	}
	subcheck(zypxids,2,1);
}
/**批量取消发布*/
function allnoPublish(){
	var rows={};
	rows = $("#volun").datagrid("getSelections");
	if (rows == "" || rows == null) {
		$.messager.alert('提示', '请选择要操作的数据！');
		return;
	}
	var zypxids ="", _split = "";
	for (var i = 0; i<rows.length; i++){
		zypxids += _split+rows[i].zypxid;
		_split = ",";
	}
	subcheck(zypxids,3,2);
}
/**
 * 一键审核提交
 */
function subcheck(zypxids,fromstate,tostate){
	$.messager.confirm('确认对话框', '您确认将所选择的通过审核吗？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/volun/checkAll'),
				data: {zypxids : zypxids,fromstate: fromstate, tostate:tostate },
				success: function(data){
					if(data.success=="success"){
						//$.messager.alert("提示", data.msg);
						$('#volun').datagrid('reload');
					}else{
						$.messager.alert('提示', '操作失败。原因：'+data.errmsg, 'error');
					}
				}
			});
		}
	});
}
</script>
</head>
<body>
	<!-- 培训管理页面 -->
	<table id="volun"></table>
	<!-- 查询栏 -->
	<div id="volunTool" class="grid-control-group" style="display: none;" >
		<div style="line-height: 32px;">
			<shiro:hasPermission name="${resourceid}:add"><a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" size='small' id="btn" onclick="addVolun()">添加</a></shiro:hasPermission>
			<shiro:hasPermission name="${resourceid}:checkgo"><a href="javascript:void(0)" class="easyui-linkbutton" size='small' onclick="allcheck()">批量审核</a></shiro:hasPermission>
			<shiro:hasPermission name="${resourceid}:publish"><a href="javascript:void(0)" class="easyui-linkbutton" size='small' onclick="allpublish()">批量发布</a></shiro:hasPermission>
			<shiro:hasPermission name="${resourceid}:publishoff"><a href="javascript:void(0)" class="easyui-linkbutton" size='small' onclick="allnoPublish()">批量取消发布</a></shiro:hasPermission>
			<shiro:hasPermission name="${resourceid}:checkoff"><a href="javascript:void(0)" class="easyui-linkbutton" size='small' onclick="allback()">批量打回</a></shiro:hasPermission>
		</div>
		<div style="padding-top: 5px">

			<input class="easyui-combobox" name="zypxtype" style="width: 200px; height:28px" data-options="editable:true, valueField:'id', textField:'text',prompt:'请选择志愿培训类型', data:WhgSysData.getTypeData('12')"/>

			<input class="easyui-textbox" name="zypxtitle" style="width: 200px; height:28px" data-options="prompt:'请输入志愿培训标题',validType:'length[1,30]'"/>

			<select class="easyui-combobox" name="zypxstate" style="width: 200px; height:28px" data-options="editable:true,prompt:'请选择状态查询',validType:'length[1,30]'" >
				<option value="">请选择状态</option>
				<option value="1">待审核</option>
				<option value="2">已审核</option>
				<option value="3">已发布</option>
			</select>
			<a href="javascript:void(0)" class="easyui-linkbutton search" iconCls="icon-search">查询</a>
		</div>
	</div>

	<!-- 操作按钮 -->
	<div id="volunOPT" style="display:none">
		<shiro:hasPermission name="${resourceid}:edit"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="zypxstate" validVal="1" method="editVolun">编辑</a></shiro:hasPermission>
		<shiro:hasPermission name="${resourceid}:del"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="zypxstate" validVal="1" method="delVolun">删除</a></shiro:hasPermission>
		<shiro:hasPermission name="${resourceid}:view"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true"  method="look">查看详情</a></shiro:hasPermission>
		<shiro:hasPermission name="${resourceid}:checkon"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="zypxstate" validVal="1" method="check">审核</a></shiro:hasPermission>
		<shiro:hasPermission name="${resourceid}:checkoff"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="zypxstate" validVal="2" method="nocheck">打回</a></shiro:hasPermission>
		<shiro:hasPermission name="${resourceid}:publish"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="zypxstate" validVal="2" method="publish">发布</a></shiro:hasPermission>
		<shiro:hasPermission name="${resourceid}:publishoff"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" validKey="zypxstate" validVal="3" method="nopublish">取消</a></shiro:hasPermission>
	</div>
				
	 <!--  培训管理dialog -->
	 <div id="volun_edit" class="none" style="display:none"  data-options=" fit:true" >
		 <form id="ff" method="post" enctype="multipart/form-data" >
			<!-- 隐藏域  -->
			<input type="hidden" id="zypxid" name="zypxid" value="" />
			<input type="hidden" id="zypxpic" name="zypxpic" value=""/>
			<!-- <input type="hidden" id="zypxvideo" name="zypxvideo" value=""/> -->

			<div class="main">
				<div class="row">
					<div>培训类型:</div>
					<div>
						<input id="zypxtype" class="easyui-combobox" name="zypxtype" style="width:100%;height:32px;" data-options="editable:false, valueField:'id',textField:'text',data:WhgSysData.getTypeData('12'),required:true"/>
					</div>
				</div>
				<div class="row">
					<div>关键字:</div>
					<div>
						<input id="zypxkey" class="easyui-combobox" name="zypxkey" multiple="true" style="width:100%;height:32px;" data-options="panelHeight:'auto',editable:false,valueField:'text',textField:'text',data: WhgComm.getTrainKey(), multiple:true"/>
					</div>
				</div>
				<div class="row">
					<div>详情标题:</div>
					<div>
						<input id="zypxtitle" class="easyui-textbox" name="zypxtitle" style="width:100%;height:32px;" data-options="required:true, validType:'length[1,60]'"/>
					</div>
				</div>
				<div class="row">
					<div>列表短标题:</div>
					<div>
						<input id="zypxshorttitle" class="easyui-textbox" name="zypxshorttitle" style="width:100%;height:32px;" data-options="required:true, validType:'length[1,60]'"/>
					</div>
				</div>
				<div class="row">
					<div>来源:</div>
					<div>
						<input id="zypxfrom" class="easyui-textbox" name="zypxfrom" style="width:100%;height:32px;" data-options="required:true"/>
					</div>
				</div>
				<div class="row">
					<div>列表页图:</div>
					<div>
						<input id="zypxpic_up"  class="easyui-filebox" name="zypxpic_up" style="width:90%;height:32px;" data-options="validType:'isIMG[\'zypxpic_up\']', buttonText:'选择图片', accept:'image/jpeg,image/peg, image/png',prompt:'图片尺寸:380x240'">
						<a id="btn_pic" class="easyui-linkbutton" data-options="plain:true," iconCls="icon-clear" style="width:9%;height:32px;">清除</a>
					</div>
				</div>

				<div class="row">
					<div>视频地址：</div>
					<div>
						<input class="easyui-combobox" id="zypxvideo" name="zypxvideo" style="width:100%;height:32px;" data-options="editable:false, required:true, valueField:'addr', textField:'key', url:'${basePath}/admin/video/srchPagging'"/>
						<!-- <input id="zypxvideo_up" class="easyui-filebox" name="zypxvideo_up" editable="true" style="width:90%;height:32px;" data-options="validType:'isVideo[\'zypxvideo_up\']', buttonText:'选择视频', accept:'video/mp4,video/ogg, video/webm'">
						<a id="btn_pic1" class="easyui-linkbutton" data-options="plain:true," iconCls="icon-clear" style="width:9%;height:32px;">清除</a>  -->
					</div>
				</div>
				<!-- <div class="row">
					<div></div>
					<div>
						<label id="fileName" /></a>
					</div>
				 </div> -->
				<div class="row">
					<div>视频时长:</div>
					<div>
						<input class="easyui-numberspinner" id="zypxvideolen" name="zypxvideolen" data-options="validType:'length[0,10]',multiline:true " style="width:100%;height:32px;">
					</div>
				</div>

				<div class="row">
					<div>视频简介：</div>
					<div>
						<input class="easyui-textbox" id="zypxcontent" name="zypxcontent" data-options="validType:'length[0,200]',multiline:true " style="width:100%;height:150px;">
					</div>
				</div>

			</div>
		</form>
	</div>
	<!--培训资源dialog  -->
	<div id="addzy" style="display:none">
		<iframe  style="width:100%; height:100%"></iframe>
	</div>
</body>
</html>