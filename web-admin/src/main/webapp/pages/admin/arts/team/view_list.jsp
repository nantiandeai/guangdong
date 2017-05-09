<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<% request.setAttribute("basePath", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath());%>
<% request.setAttribute("resourceid", request.getParameter("rsid")); %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>馆办团队管理</title>
    <%@include file="/pages/comm/admin/header.jsp"%>
    
    <script type="text/javascript" charset="utf-8" src="${basePath}/static/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="${basePath}/static/ueditor/ueditor.all.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="${basePath}/static/ueditor/lang/zh-cn/zh-cn.js"></script>
</head>
<body>

<!-- 表格 -->
<table id="whgdg" title="馆办团队管理" class="easyui-datagrid" style="display: none"
       data-options="fit:true, striped:true, rownumbers:true, fitColumns:true, singleSelect:false, checkOnSelect:true, selectOnCheck:true, pagination:true, toolbar:'#whgdg-tb', url:'${basePath}/admin/arts/seletroupe'">
    <thead>
    <tr>
    	<th data-options="field:'ck', checkbox:true"></th>
        <th data-options="field:'troupearttyp', sortable: true,width:80,formatter: WhgComm.FMTArtType">艺术类型</th>
        <th data-options="field:'troupename', width:80">团队名称</th>
        <th data-options="field:'troupepic', width:80,formatter:WhgComm.FMTImg">图片</th>
        <th data-options="field:'troupestate', width:60, formatter:traStateFMT">状态</th>
        <th data-options="field:'_opt', formatter:WhgComm.FMTOpt, optDivId:'whgdg-opt'">操作</th>
    </tr>
    </thead>
</table>
<!-- 表格 END -->

<!-- 表格操作工具栏 -->
<div id="whgdg-tb" style="display: none;">
    <div class="whgd-gtb-btn">
			<shiro:hasPermission name="${resourceid}:add"><a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="addtroupe();">添加</a></shiro:hasPermission>
			<shiro:hasPermission name="${resourceid}:checkon"><a href="javascript:void(0)" class="easyui-linkbutton"  onclick="checkAllTrou();">批量审核</a></shiro:hasPermission> 
			<shiro:hasPermission name="${resourceid}:checkoff"><a href="javascript:void(0)"  class="easyui-linkbutton"  onclick="allTrou();" >批量取消审核</a></shiro:hasPermission>
			<shiro:hasPermission name="${resourceid}:publish"><a href="javascript:void(0)" class="easyui-linkbutton"  onclick="goAllTrou();" >批量发布</a></shiro:hasPermission>
			<shiro:hasPermission name="${resourceid}:publishoff"><a href="javascript:void(0)" class="easyui-linkbutton"  onclick="goTrou();" >批量取消发布</a></shiro:hasPermission>
		</div>
    <div class="whgdg-tb-srch">
			<input class="easyui-combobox" name="troupearttyp" style="width: 200px; height:28px" data-options="prompt:'请选择艺术类型', valueField:'id',textField:'text',data:WhgComm.getArtType()"/>
			<input class="easyui-textbox" name="troupename" style="width: 200px; height:28px" data-options="prompt:'请输入团队名称', validType:'length[1,30]'"/>
			<select class="easyui-combobox radio" name="troupestate" style="width: 200px; height:28px" data-options="prompt:'请选择状态'">
				<option value="">全部</option>
				<option value="0">未审核</option>
				<option value="2">已审核</option>
				<option value="3">已发布</option>
			</select>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="WhgComm.search('#whgdg', '#whgdg-tb');">查 询</a>
		</div>
</div>
<!-- 表格操作工具栏-END -->

<!-- 操作按钮 -->
<div id="whgdg-opt" style="display:none">
		<shiro:hasPermission name="${resourceid}:view"><a href="javascript:void(0)" class="easyui-linkbutton" method="seetro">资源管理</a></shiro:hasPermission>
	    <shiro:hasPermission name="${resourceid}:view"><a href="javascript:void(0)" class="easyui-linkbutton" validKey="troupestate" validVal="0,1,2,3" method="doSee">查看</a></shiro:hasPermission> 
		<shiro:hasPermission name="${resourceid}:edit"><a href="javascript:void(0)" class="easyui-linkbutton" validKey="troupestate" validVal="0,1" method="uptro">编辑</a></shiro:hasPermission> 
		<shiro:hasPermission name="${resourceid}:edit"><a href="javascript:void(0)" class="easyui-linkbutton" validKey="troupestate" validVal="0,1" method="troupeUser">成员管理</a></shiro:hasPermission>
		<shiro:hasPermission name="${resourceid}:del"><a href="javascript:void(0)" class="easyui-linkbutton" validKey="troupestate" validVal="0,1" method="deltroupe">删除</a></shiro:hasPermission>
		<shiro:hasPermission name="${resourceid}:checkon"><a href="javascript:void(0)" class="easyui-linkbutton" validKey="troupestate" validVal="0,1" method="checkTrou">审核</a></shiro:hasPermission>
        <shiro:hasPermission name="${resourceid}:checkoff"><a href="javascript:void(0)" class="easyui-linkbutton" validKey="troupestate" validVal="2" method="uncheckTrou">取消审核</a></shiro:hasPermission>
        <shiro:hasPermission name="${resourceid}:publish"><a href="javascript:void(0)" class="easyui-linkbutton" validKey="troupestate" validVal="2" method="ucheckTrou">发布</a></shiro:hasPermission>
        <shiro:hasPermission name="${resourceid}:publishoff"><a href="javascript:void(0)" class="easyui-linkbutton" validKey="troupestate" validVal="3" method="ncheckTrou">取消发布</a></shiro:hasPermission>
        <shiro:hasPermission name="${resourceid}:order"><a href="javascript:void(0)" class="easyui-linkbutton" validKey="troupestate" validVal="3" method="goindex">排序</a></shiro:hasPermission>
	</div>
<!-- 操作按钮-END -->

<!-- script -->
<script type="text/javascript">

/**
 * 添加
 */
function addtroupe() {
    WhgComm.editDialog('${basePath}/admin/arts/view/add');
}

/**
 * 编辑信息
 * @param idx 行下标
 */
function uptro(idx) {
    var curRow = $('#whgdg').datagrid('getRows')[idx];
    WhgComm.editDialog('${basePath}/admin/arts/view/edit?id='+curRow.troupeid);
}

/**
 * 查看资料
 * @param idx 行下标
 */
function doSee(idx) {
    var curRow = $('#whgdg').datagrid('getRows')[idx];
    WhgComm.editDialog('${basePath}/admin/arts/view/edit?id='+curRow.troupeid+"&onlyshow=1");
}

/**
 * 资源管理
 * @param idx
 */
function seetro(idx) {
	var row = $("#whgdg").datagrid("getRows")[idx];
	WhgComm.editDialog('${basePath}/admin/train/resource/view/list?reftype=12&id=' + row.troupeid);
}

/**
 * 成员管理
 *
 * */
function troupeUser(idx){
	var row = $("#whgdg").datagrid("getRows")[idx];
    WhgComm.editDialog('${basePath}/admin/arts/troupeUser?troupeid='+row.troupeid);
}

/**
 * 批量审核
 */
function checkAllTrou(){
	var rows={};
	rows = $("#whgdg").datagrid("getSelections");
	if (rows == "" || rows == null) {
		$.messager.alert('提示', '操作失败,没有选择要操作的记录');
		return;
	}
	var troupeids = _split = "";//id1,id2,id3
	for (var i = 0; i<rows.length; i++){
		troupeids += _split+rows[i].troupeid;
		_split = ",";
	}
	$.messager.confirm('确认对话框', '您确认将所选择的进行审核吗？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/arts/gocheckTrou'),
				data: {troupeid : troupeids,fromstate:0, tostate:2},
				success: function(data){
					if(data.success == '0'){
						$('#whgdg').datagrid('reload');
					   }else{
						   $.messager.alert('提示', '操作失败。原因：'+data.errmsg, 'error');
					   }
				}
			});
		}
	});
}

/**
 * 批量取消审核
 */
function allTrou(){
	var rows={};
	rows = $("#whgdg").datagrid("getSelections");
	if (rows == "" || rows == null) {
		$.messager.alert('提示', '操作失败,没有选择要操作的记录');
		return;
	}
	var troupeids = _split = "";//id1,id2,id3
	for (var i = 0; i<rows.length; i++){
		troupeids += _split+rows[i].troupeid;
		_split = ",";
	}
		$.messager.confirm('确认对话框', '您确认将所选择的取消审核吗？', function(r){
			if (r){
				$.ajax({
					type: "POST",
					url: getFullUrl('/admin/arts/gocheckTrou'),
					data: {troupeid : troupeids,fromstate:2, tostate:0},
					success: function(data){
						if(data.success == '0'){
							$('#whgdg').datagrid('reload');
						   }else{
							   $.messager.alert('提示', '操作失败。原因：'+data.errmsg, 'error');
						   }
					}
				});
			}
		});
}

/**
 * 批量发布
 */
function goAllTrou(){
	var rows={};
	rows = $("#whgdg").datagrid("getSelections");
	if (rows == "" || rows == null) {
		$.messager.alert('提示', '操作失败,没有选择要操作的记录');
		return;
	}
	var troupeids = _split = "";//id1,id2,id3
	for (var i = 0; i<rows.length; i++){
		troupeids += _split+rows[i].troupeid;
		_split = ",";
	}
	$.messager.confirm('确认对话框', '您确认将所选择的批量发布吗？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/arts/gocheckTrou'),
				data: {troupeid : troupeids,fromstate:2, tostate:3},
				success: function(data){
					if(data.success == '0'){
						$('#whgdg').datagrid('reload');
					   }else{
						   $.messager.alert('提示', '操作失败。原因：'+data.errmsg, 'error');
					   }
				}
			});
		}
	});
}

/**
 * 批量取消发布
 */
function goTrou(){
	var rows={};
	rows = $("#whgdg").datagrid("getSelections");
	if (rows == "" || rows == null) {
		$.messager.alert('提示', '操作失败,没有选择要操作的记录');
		return;
	}
	var troupeids = _split = "";//id1,id2,id3
	for (var i = 0; i<rows.length; i++){
		troupeids += _split+rows[i].troupeid;
		_split = ",";
	}
	$.messager.confirm('确认对话框', '您确认将所选择的批量取消发布吗？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/arts/gocheckTrou'),
				data: {troupeid : troupeids,fromstate:3, tostate:2},
				success: function(data){
					if(data.success == '0'){
						$('#whgdg').datagrid('reload');
					   }else{
						   $.messager.alert('提示', '操作失败。原因：'+data.errmsg, 'error');
					   }
				}
			});
		}
	});
}
/**
 *审核状态
 */
function checkTrou(idx){
 var curRow = $('#whgdg').datagrid('getRows')[idx];
	 var id = curRow.troupeid;
     $.messager.confirm('确认对话框', '确定要通过审核？', function(r) {
         if (r) {
        	 $.ajax({
        		type: "POST",
         		url : getFullUrl("./admin/arts/checktroupe"),
         		data : {troupeid:id, troupestate:2},
         		success:function(data){
         			if (data=="success"){
                         //$.messager.alert("提示","操作成功");
                         $("#whgdg").datagrid("reload");
                     }else{
                         $.messager.alert("失败了", "操作失败！");
                     }
         		}
         	})
         }
     })
}
 
/**
 *取消审核状态
 */
function uncheckTrou(idx){
	var curRow = $('#whgdg').datagrid('getRows')[idx];
	var id = curRow.troupeid;
	 if (curRow.troupestate==2) {
     $.messager.confirm('确认对话框', '确定要取消审核？', function(r) {
         if (r) {
        	 $.ajax({
        		type: "POST",
         		url : getFullUrl("./admin/arts/checktroupe"),
         		data : {troupeid:id, troupestate:0},
         		success:function(data){
         			if (data=="success"){
                         //$.messager.alert("提示","操作成功");
                         $("#whgdg").datagrid("reload");
                     }else{
                         $.messager.alert("提示", "操作失败！");
                     }
         		}
         	})
         }
     })
	 }else {
		 $.messager.alert("提示", "操作失败！");
	}
}
  
/**
 *发布状态
 */
function ucheckTrou(idx){
 	var curRow = $('#whgdg').datagrid('getRows')[idx];
	var id = curRow.troupeid;
  	if (curRow.troupestate==2) {
      $.messager.confirm('确认对话框', '确定要发布？', function(r) {
          if (r) {
         	 $.ajax({
         		type: "POST",
          		url : getFullUrl("./admin/arts/checktroupe"),
          		data : {troupeid:id,troupestate:3},
          		success:function(data){
          			if (data=="success"){
                          //$.messager.alert("提示","操作成功");
                          $("#whgdg").datagrid("reload");
                      }else{
                   	   $.messager.alert("提示", "操作失败！");
                      }
          		}
          	})
          }
      })
 	 }else {
 		 $.messager.alert("提示", "操作失败！");
	}
}
  
/**
 *取消发布状态
 */
function ncheckTrou(idx){
	var curRow = $('#whgdg').datagrid('getRows')[idx];
	var id = curRow.troupeid;
 	$.messager.confirm('确认对话框', '确定要取消发布？', function(r) {
 	    if (r) {
 	   	 $.ajax({
 	   		type: "POST",
 	    		url : getFullUrl("./admin/arts/checktroupe"),
 	    		data : {troupeid:id, troupestate:2},
 	    		success:function(data){
 	    			if (data=="success"){
 	                    //$.messager.alert("提示","操作成功");
 	                    $("#whgdg").datagrid("reload");
 	                }else{
 	                	 $.messager.alert("提示", "操作失败！");
 	                }
 	    		}
 	    	})
 	    }
 	});
}


/**
 * 根据主键id删除
 */
 function deltroupe(idx){
	var curRow = $('#whgdg').datagrid('getRows')[idx];
	var id = curRow.troupeid;
	$.messager.confirm('确认对话框', '您确认要删除吗？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/arts/deltroupe'),
				data: {troupeid : id},
				success: function(msg){
					$('#whgdg').datagrid('reload');
				}
			});
		}
	});
}
     
/**
 * 上首页
 */
function showhp(val,rowData,index){
	if(rowData.troupeghp==0){  
        return "否";  
    }else if(rowData.troupeghp==1){ 
    	return "是";  
    }
}

</script>
<!-- script END -->
</body>
</html>