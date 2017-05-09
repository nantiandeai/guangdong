<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath(); request.setAttribute("basePath", basePath); %>
<%request.setAttribute("resourceid", request.getParameter("rsid"));%>
<%request.setAttribute("refid", request.getParameter("refid"));%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<base href="${basePath }/">
<%@include file="/pages/comm/header.jsp"%>
<script type="text/javascript" charset="utf-8" src="${basePath}/static/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${basePath}/static/ueditor/ueditor.all.min.js"></script>
<script type="text/javascript" charset="utf-8" src="${basePath}/static/ueditor/lang/zh-cn/zh-cn.js"></script>
<script>
/**
 * 状态
 */
function showload(val, rowData, index) {
	return rowData.upstate == '1' ? '启用' : '停用';
}
/**
 * 启动状态
 */
 function checktype(index){
	 var row = WHdg.getRowData(index);
	 var id = row.upid;
     $.messager.confirm('确认对话框', '确定要更改状态？', function(r) {
         if (r) {
        	 $.ajax({
        		type: "POST",
         		url : getFullUrl("/admin/shop/upstate"),
         		data : {upid:id, upstate:1},
         		success:function(data){
         			if (data=="success"){
//                         $.messager.alert("提示","操作成功");
                         $("#upDG").datagrid("reload");
                     }else{
                         $.messager.alert("失败了", "操作失败！");
                     }
         		}
         	})
         }
     })
 }
/**
 * 停用
 */
 function stoptype(index){
	 var row = WHdg.getRowData(index);
	 var id = row.upid;
     $.messager.confirm('确认对话框', '确定要更改状态？', function(r) {
         if (r) {
        	 $.ajax({
        		type: "POST",
         		url : getFullUrl("/admin/shop/upstate"),
         		data : {upid:id, upstate:0},
         		success:function(data){
         			if (data=="success"){
//                         $.messager.alert("提示","操作成功");
                         $("#upDG").datagrid("reload");
                     }else{
                         $.messager.alert("失败了", "操作失败！");
                     }
         		}
         	})
         }
     })
 }
/**
 * 添加
 */
 function addzysc(){
 	editWinform.setWinTitle("添加上传资源页面");
	editWinform.openWin();
	
	$('#ff').form({
		novalidate: true,
		url : getFullUrl('/admin/shop/adduploda'),
		onSubmit: function(param){
			 param.uptype ='${refid}';
			 param.upstate = 0;
			 var end=$(this).form('enableValidation').form('validate');
			 if (!end) {
				 editWinform.oneClick(function(){ $('#ff').submit(); });
			}
			return end;
		},
		success : function(data) {
			data = eval('('+data+')'); 
			if(data && data.success == '0'){
		  		 editWinform.closeWin();
//				 $.messager.alert("提示", "操作成功");
				 $('#upDG').datagrid('reload');  
			}else {
				 $.messager.alert('提示', '操作失败。原因：'+data.errmsg, 'error');
				 editWinform.oneClick(function(){ $('#ff').submit(); });
			}
		}
	});
	
	//清空表单的值
	$('#ff').form('clear');
	$('#uplink_up').filebox({required: true});
	
	editWinform.oneClick(function(){$('#ff').submit(); });
}
/**
 * 编辑
 */
 function updata(index){
	var row = WHdg.getRowData(index);
	if (row) {
		editWinform.setWinTitle("编辑上传资源页面");
		editWinform.openWin();

		var url = getFullUrl('/admin/shop/douploda');
		$('#ff').form({
			novalidate: true,
			url : url,
			onSubmit : function(param){
                param.upid = row.upid;
                param.uplink = row.uplink;
                var end=$(this).form('enableValidation').form('validate');
                if (!end) {
                	editWinform.oneClick(function(){ $('#ff').submit(); });
				}
                return $(this).form('enableValidation').form('validate');
            },
			success : function(data) {
				var json = $.parseJSON(data);
				if (json.success=='0'){
					$('#upDG').datagrid('reload');
//					$.messager.alert("提示", "操作成功");
					editWinform.closeWin();
				}else{
					$.messager.alert("失败了", json.msg);
					editWinform.oneClick(function(){ $('#ff').submit(); });
				}
			}
		});
		$('#ff').form('clear');
		
		$('#uplink_up').filebox({required: false});
		$('#ff').form('load', row);
		
		editWinform.oneClick(function(){ $('#ff').submit(); });
	}
	
}
/**
 * 删除
 */
 function delup(index){
	 var row = WHdg.getRowData(index);
		var id = row.upid;
		var link = row.uplink;
		$.messager.confirm('确认对话框', '您确认要删除吗？', function(r){
			if (r){
				$.ajax({
					type: "POST",
					url: getFullUrl('/admin/shop/deluploda'),
					data: {upid : id, uplink:link},
					success: function(msg){
//						$.messager.alert("提示", "操作成功");
						$('#upDG').datagrid('reload');
					}
				});
			}
		});
}

/**
 * 初始表格
 */
 var editWinform = new WhuiWinForm("#edit_win");
$(function(){
	//定义表格参数
	var options = {
		title:"资料上传管理",
		url:getFullUrl('/admin/shop/seletup?uptype=${refid}'),
		sortName: 'upid',
		sortOrder: 'desc',
		fitColumns:true,
		columns:[[ 
//			{field:'upid',title:'资源标识',width:100,sortable:true},
			{field:'upname',width:100,title:'资源标题'},
			//{field:'uptype',title:'资源类型'},
			{field:'uplink',width:100,title:'资源地址'},
			{field:'uptime',width:100,title:'上传时间',sortable:true,formatter :datetimeFMT},
			{field:'upstate',width:100,title:'状态',sortable:true,formatter :showload},
			{field:'opt', width:250,title:'操作', formatter: WHdg.optFMT, optDivId:'upOPT'}
		]]  
	};
	
	//初始表格
	WHdg.init('upDG', 'upTB',  options);
	editWinform.init();
	//清除文件框的值
	$('#btn_link').bind('click', function(){  
		$("#uplink_up").filebox('clear');
    });
});
</script>
</head>
<body class="easyui-layout">
		<div style="display:none" data-options="region:'center',title:'',iconCls:'icon-ok'">
			<div id="upDG"></div>
			
			<div id="upTB" style="height:auto" style="display:none; padding-top:5px">
			    <div>
					<shiro:hasPermission name="${resourceid}:add"><a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add"  onclick="addzysc();" data-options="size:'small'">添加</a></shiro:hasPermission>
					<a href="javascript:void(0)" class="easyui-linkbutton whgff-but-clear" iconCls="icon-undo" onclick="WhgComm.editDialogClose()" data-options="size:'small'">返 回</a>
				</div>
			</div>
			<!-- 操作按钮 -->
			<div id="upOPT" class="none" style="display:none">
				<a href="javascript:void(0)" validKey="upstate" validVal="0" method="updata">编辑</a>
				<a href="javascript:void(0)" validKey="upstate" validVal="0" method="delup">删除</a>
				<a href="javascript:void(0)" validKey="upstate" validVal="0" method="checktype">启用</a>
            	<a href="javascript:void(0)" validKey="upstate" validVal="1" method="stoptype">停用</a>
			</div>
		</div>
		 <!-- div弹出层 --> 
	<div id="edit_win" class="none" data-options="maximized:true" style="display:none">
		<form method="post" id="ff" enctype="multipart/form-data">
		 <!-- 隐藏作用域 -->
			<div class="main">
			   	<div class="row">
					<div><label>资源标题:</label></div>
					<div>
						<input class="easyui-textbox" name="upname" id="upname" style="width:90%;height:35px"
						data-options="required:true, validType:'length[1,60]'"/>
					</div>
				</div>
				<div class="row">
					<div><label>资源地址:</label></div>
					<div>
						<input class="easyui-filebox" name="uplink_up" id="uplink_up" style="width:80%;height:35px" data-options="required: true, validType:'isFile[\'uplink_up\', 20485760]', buttonText:'选择文件'"/>
						<a id="btn_link" class="easyui-linkbutton clearfilebox" data-options="plain:true," iconCls="icon-clear" style="width:10%;height:32px;">清除</a>
					</div>
				</div>
		  </div>
	  </form>
   </div>
</body>
</html>

