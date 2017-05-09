<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath(); request.setAttribute("basePath", basePath); %>
<%request.setAttribute("resourceid", request.getParameter("rsid"));%>
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
function showState(val, rowData, index) {
	return rowData.cfgadvstate == '1' ? '启用' : '停用';
}
/**
 * 添加广告信息
 */
function addcfga(){
	editWinform.setWinTitle("添加广告页面");
	editWinform.openWin();
	//清空表单的值
	$('#ff').form('clear');
	$('#cfgadvpic_up_img').removeAttr('src').parents('.row').hide();
	$('#cfgadvidx').numberspinner('setValue', '1');
	$('#cfgadvpagetype').combotree('setValue', $('#pageTree').tree('getSelected').typid);
	$('#cfgadvpagetype').combotree('readonly');
	$('#cfgadvpic_up').filebox({required: true});
	//提交表单
	editWinform.setFoolterBut({
		onClick : function() {
			$('#ff').form('submit', {
				url : getFullUrl('/admin/adve/addadves'),
				onSubmit: function(param){
					param.cfgadvstate = 0;
					param.cfgadvidx = 1;
	                return $(this).form('enableValidation').form('validate');
				},
				success : function(data) {
					data = eval('('+data+')');
					if(data && data.success == '0'){
						editWinform.closeWin();
	                	$('#advsDG').datagrid('reload');
						$.messager.alert("提示", "操作成功");
	                }else{
	                    $.messager.alert("失败了", json.msg);
	                }
				}
			});
		}
	});
	
}
/**
 * 编辑广告信息
 */
 function upcfga(index){
		var row = WHdg.getRowData(index);
		if (row) {
		//清空表单的值
		$('#ff').form('clear');
		editWinform.setWinTitle("编辑广告页面");
		$('#ff').form('load', row);
		//图片
		if (row.cfgadvpic && row.cfgadvpic != '' ){
        	$('#cfgadvpic_up_img').attr('src', getFullUrl(row.cfgadvpic)).parents('.row').show();
        }else{
        	$('#cfgadvpic_up_img').parents('.row').hide();
        }
		$('#cfgadvpic_up').filebox({required: false});
		editWinform.openWin();
		//提交表单
		editWinform.setFoolterBut({
			onClick : function() {
				$('#ff').form('submit', {
					url : getFullUrl('/admin/adve/upadves'),
					onSubmit: function(param){
						param.cfgadvid = row.cfgadvid;
						param.cfgadvpic = row.cfgadvpic;
		                return $(this).form('enableValidation').form('validate');
					},
					success : function(data) {
						data = eval('('+data+')');
						if(data && data.success == '0'){
							editWinform.closeWin();
		                	$('#advsDG').datagrid('reload');
							$.messager.alert("提示", "操作成功");
		                }else{
		                    $.messager.alert("失败了", json.msg);
		                }
					}
				});
			}
		});
	  }
  }
/**
 * 删除列表信息
 */
 function delcfga(index){
	 var row = WHdg.getRowData(index);
		var id = row.cfgadvid;
		var pic = row.cfgadvpic;
		$.messager.confirm('确认对话框', '您确认要删除吗？', function(r){
			if (r){
				$.ajax({
					type: "POST",
					url: getFullUrl('/admin/adve/deladves'),
					data: {cfgadvid : id, cfgadvpic:pic },
					success: function(msg){
						$.messager.alert("提示", "操作成功");
						$('#advsDG').datagrid('reload');
					}
				});
			}
		});
}
/**
 * 启动状态
 */
 function checktype(index){
	 var row = WHdg.getRowData(index);
	 var id = row.cfgadvid;
     $.messager.confirm('确认对话框', '确定要更改状态？', function(r) {
         if (r) {
        	 $.ajax({
        		type: "POST",
         		url : getFullUrl("./admin/adve/dotype"),
         		data : {cfgadvid:id, cfgadvstate:1},
         		success:function(data){
         			if (data=="success"){
                         $.messager.alert("提示","操作成功");
                         $("#advsDG").datagrid("reload");
                     }else{
                         $.messager.alert("失败了", "操作失败！");
                     }
         		}
         	})
         }
     })
 }
/**
 * 关闭状态
 */
 function stoptype(index){
	 var row = WHdg.getRowData(index);
	 var id = row.cfgadvid;
     $.messager.confirm('确认对话框', '确定要更改状态？', function(r) {
         if (r) {
        	 $.ajax({
        		type: "POST",
         		url : getFullUrl("./admin/adve/dotype"),
         		data : {cfgadvid:id, cfgadvstate:0},
         		success:function(data){
         			if (data=="success"){
                         $.messager.alert("提示","操作成功");
                         $("#advsDG").datagrid("reload");
                     }else{
                         $.messager.alert("失败了", "操作失败！");
                     }
         		}
         	})
         }
     })
 }
var editWinform = new WhuiWinForm("#edit_win");
$(function(){
	//定义表格参数
	var options = {
		//title: '广告配置管理',			
		//url:getFullUrl('/admin/adve/seleadve'),
		sortName: 'cfgadvid',
		sortOrder: 'desc',
		columns:[[    
			//{field:'cfgadvid',title:'页面广告标识',width:150, align:'center',sortable:true},
			//{field:'cfgadvpagetype',title:'页面类型',align:'center'},
			{field:'cfgadvpic',title:'广告图',width:200, formatter:imgFMT},
			{field:'cfgadvidx',title:'排序',width:150, sortable:true},
			{field:'cfgadvstate',title:'状态', width:150, sortable:true, formatter: showState},
			{field:'opt', title:'操作', align:'center', width:150,formatter: WHdg.optFMT, optDivId:'advOPT'}
		]]  
	};
	//初始表格
	editWinform.init();
	WHdg.init('advsDG', 'advTB',  options);
	//清除文件框的值
	$('#btn_pic').bind('click', function(){  
		$("#cfgadvpic_up").filebox('clear');
    });
	//得到类型id
	$('#pageTree').tree({    
	    url: '${basePath}/comm/whtyp?type=21' ,
	    onLoadSuccess: function(node, data){
			var nodeList = $('#pageTree').tree('getRoots');
			if(nodeList.length > 0){
				$('#pageTree').tree('select', nodeList[0].target);
				$('#advsDG').datagrid( {
					url: getFullUrl('/admin/adve/seleadve'),
					queryParams: {'cfgadvpagetype': nodeList[0].typid}
				});
			}
	    },
	    onSelect: function(node){
	    	$('#advsDG').datagrid( {
				url: getFullUrl('/admin/adve/seleadve'),
				queryParams: {'cfgadvpagetype': node.typid}
			});
	    }
	  /*   loadFilter: function(data, parent){
			var newData = [];
			if($.isArray(data)){
				for(var i=0; i<data.length; i++){
					data[i].children = [];
					newData.push(data[i]);
		    	}
			}
			return newData;
		} */
	});
	
});
</script>
</head>

<body class="easyui-layout">
	
    <div id='advDG' data-options="region:'west',title:'页面',split:true" style="width:200px;">
    	<ul id="pageTree"></ul>  
    </div>   
    
    
    <div data-options="region:'center',title:'列表显示'" style="padding:5px;background:#eee;">
    	<div id="advsDG"></div>
    
    	<div id="advTB" style="height:auto">
		    <div>
		        <shiro:hasPermission name="${resourceid}:add"><a class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addcfga();">添加</a></shiro:hasPermission>
		    </div>
		</div>
		<!-- 操作按钮 -->
		<div id="advOPT" class="none">
			<shiro:hasPermission name="${resourceid}:edit"><a href="javascript:void(0)" validKey="cfgadvstate" validVal="0,1" method="upcfga">编辑</a></shiro:hasPermission> 
			<shiro:hasPermission name="${resourceid}:del"><a href="javascript:void(0)" validKey="cfgadvstate" validVal="0" method="delcfga">删除</a></shiro:hasPermission>
			<shiro:hasPermission name="${resourceid}:on"><a href="javascript:void(0)" validKey="cfgadvstate" validVal="0" method="checktype">启用</a></shiro:hasPermission>
            <shiro:hasPermission name="${resourceid}:off"><a href="javascript:void(0)" validKey="cfgadvstate" validVal="1" method="stoptype">停用</a></shiro:hasPermission>
		</div>
    </div>
    <div id="edit_win" class="none" style="display:none;" data-options="maximized:true">
		<form method="post" id="ff" enctype="multipart/form-data">
		 <!-- 隐藏作用域 -->
			<div class="main">
				<div class="row">
					<div><label>页面类型:</label></div>
					<div>
						<select class="easyui-combotree" name="cfgadvpagetype" id="cfgadvpagetype" style="width:90%;height:35px"
						 data-options="editable:true, panelHeight:'auto', url:'${basePath}/comm/whtyp?type=21'">
						</select>
					</div>
				</div>
					<div class="row">
					<div><label></label></div>
					<div>
						<img id="cfgadvpic_up_img" style="height:150px;"/>
					</div>
				</div>
				<div class="row">
					<div><label>广告图:</label></div>
					<div>
						<input class="easyui-filebox" name="cfgadvpic_up" id="cfgadvpic_up" style="width:81%;height:35px" data-options="required: true, validType:'isIMG[\'cfgadvpic_up\', 1024]', buttonText:'选择封面', accept:'image/jpeg, image/png', prompt:'建议使用1920x450'" />
						<a id="btn_pic" class="easyui-linkbutton" data-options="plain:true," iconCls="icon-clear" style="width:9%;height:32px;">清除</a>
					</div>
				</div>
				
				<div class="row">
					<div><label>连接地址:</label></div>
					<div>
						<input class="easyui-textbox" name="cfgadvlink" id="cfgadvlink" style="width:90%;height:35px"
						data-options="validType:'length[0,128]'"/>
					</div>
				</div>
			
				<div class="row">
					<div><label>排序:</label></div>
					<div>
						<div>
						<input class="easyui-numberspinner" name="cfgadvidx" id="cfgadvidx" style="width:90%;height:35px"
						data-options="increment:1, required:true,min:1,max:999"/>
					</div>
					</div>
				</div>
			</div>
		</form>
	</div>
</body>
</html>

