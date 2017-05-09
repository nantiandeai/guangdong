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
<!-- UEditor -->
<script type="text/javascript" charset="utf-8" src="${basePath}/static/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${basePath}/static/ueditor/ueditor.all.min.js"></script>
<script type="text/javascript" charset="utf-8" src="${basePath}/static/ueditor/lang/zh-cn/zh-cn.js"></script>
<script>
/** 编辑添加艺术作品弹出框 */
var winform = false;
var editWinform_ = false;
/**
 * 批量审核
 */
 function artAllcheck(){
	var rows={};
	rows = $("#zxDG").datagrid("getSelections");
	if (rows == "" || rows == null) {
		$.messager.alert('提示', '操作失败,没有选择要操作的记录');
		return;
	}
	var artids = _split = "";//id1,id2,id3
	for (var i = 0; i<rows.length; i++){
		artids += _split+rows[i].artid;
		_split = ",";
	}
	//alert(JSON.stringify(artistids));
	$.messager.confirm('确认对话框', '您确认将所选择的进行发布吗？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/arts/goAllwha'),
				data: {artid : artids,fromstate:0, tostate:2},
				success: function(data){
					if(data.success == '0'){
						$('#zxDG').datagrid('reload');
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
  function artcheck(){
 	var rows={};
 	rows = $("#zxDG").datagrid("getSelections");
 	if (rows == "" || rows == null) {
 		$.messager.alert('提示', '操作失败,没有选择要操作的记录');
 		return;
 	}
 	var artids = _split = "";//id1,id2,id3
 	for (var i = 0; i<rows.length; i++){
 		artids += _split+rows[i].artid;
 		_split = ",";
 	}
 	//alert(JSON.stringify(artistids));
 	$.messager.confirm('确认对话框', '您确认将所选择的进行发布吗？', function(r){
 		if (r){
 			$.ajax({
 				type: "POST",
 				url: getFullUrl('/admin/arts/goAllwha'),
 				data: {artid : artids,fromstate:2, tostate:0},
 				success: function(data){
 					if(data.success == '0'){
 						$('#zxDG').datagrid('reload');
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
 function artGocheck(){
	var rows={};
	rows = $("#zxDG").datagrid("getSelections");
	if (rows == "" || rows == null) {
		$.messager.alert('提示', '操作失败,没有选择要操作的记录');
		return;
	}
	var artids = _split = "";//id1,id2,id3
	for (var i = 0; i<rows.length; i++){
		artids += _split+rows[i].artid;
		_split = ",";
	}
	//alert(JSON.stringify(artistids));
	$.messager.confirm('确认对话框', '您确认将所选择的进行发布吗？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/arts/goAllwha'),
				data: {artid : artids,fromstate:2, tostate:3},
				success: function(data){
					if(data.success == '0'){
						$('#zxDG').datagrid('reload');
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
function Gocheck(){
	var rows={};
	rows = $("#zxDG").datagrid("getSelections");
	if (rows == "" || rows == null) {
		$.messager.alert('提示', '操作失败,没有选择要操作的记录');
		return;
	}
	var artids = _split = "";//id1,id2,id3
	for (var i = 0; i<rows.length; i++){
		artids += _split+rows[i].artid;
		_split = ",";
	}
	//alert(JSON.stringify(artistids));
	$.messager.confirm('确认对话框', '您确认将所选择的进行发布吗？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/arts/goAllwha'),
				data: {artid : artids,fromstate:3, tostate:2},
				success: function(data){
					if(data.success == '0'){
						$('#zxDG').datagrid('reload');
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
 function checkTrou(index){
	 var row = WHdg.getRowData(index);
	 var id = row.artid;
     $.messager.confirm('确认对话框', '确定要通过审核？', function(r) {
         if (r) {
        	 $.ajax({
        		type: "POST",
         		url : getFullUrl("./admin/arts/checkType"),
         		data : {artid:id, artstate:2},
         		success:function(data){
         			if (data=="success"){
                         $.messager.alert("提示","操作成功");
                         $("#zxDG").datagrid("reload");
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
  function uncheckTrou(index){
 	 var row = WHdg.getRowData(index);
 	 var id = row.artid;
 	 if (row.artstate==2) {
      $.messager.confirm('确认对话框', '确定要取消审核？', function(r) {
          if (r) {
         	 $.ajax({
         		type: "POST",
          		url : getFullUrl("./admin/arts/checkType"),
          		data : {artid:id, artstate:0},
          		success:function(data){
          			if (data=="success"){
                          $.messager.alert("提示","操作成功");
                          $("#zxDG").datagrid("reload");
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
   function ucheckTrou(index){
  	 var row = WHdg.getRowData(index);
  	 var id = row.artid;
  	 if (row.artstate==2) {
       $.messager.confirm('确认对话框', '确定要发布？', function(r) {
           if (r) {
          	 $.ajax({
          		type: "POST",
           		url : getFullUrl("./admin/arts/checkType"),
           		data : {artid:id, artstate:3},
           		success:function(data){
           			if (data=="success"){
                           $.messager.alert("提示","操作成功");
                           $("#zxDG").datagrid("reload");
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
  function ncheckTrou(index){
   	 var row = WHdg.getRowData(index);
   	 var id = row.artid;
   	 //if (row.artstate==3 && row.artghp==0) {
        $.messager.confirm('确认对话框', '确定要取消发布？', function(r) {
            if (r) {
           	 $.ajax({
           		type: "POST",
            		url : getFullUrl("./admin/arts/checkType"),
            		data : {artid:id,artstate:2},
            		success:function(data){
            			if (data=="success"){
                            $.messager.alert("提示","操作成功");
                            $("#zxDG").datagrid("reload");
                        }else{
                        	 $.messager.alert("提示", "操作失败！");
                        }
            		}
            	})
            }
        })
   	 //}else {
   		//$.messager.alert("提示", "上首页标识为否才能取消发布！");
	//}
   }
   
/** 编辑艺术作品时选择作品类型后的处理 */
function changeAtrtyp(newVal, oldVal){
	$('#arttypid').combobox('reload', '${basePath}/admin/arts/findType?type='+newVal);
}
function changeAtrtyp2(newVal, oldVal){
	$('#arttypid2').combobox('reload', '${basePath}/admin/arts/findType?type='+newVal);
}

/** 添加艺术作品 */
function doAdd(e){
	e.preventDefault();
	winform.setWinTitle("添加艺术作品");
	
	//清除表单
	$('#arttags').combobox('clear');
	$('#artkeys').combobox('clear');
	$('#artForm').form('clear');
	$('#artstate').val('0');
	$('#artghp').val('0');
	$('#artpic_up_img').removeAttr('src').parents('.row').hide();
	$('#artpic1_up_img').removeAttr('src').parents('.row').hide();
	$('#artpic2_up_img').removeAttr('src').parents('.row').hide();
	UE.getEditor('art_editor').setContent('');
	UE.getEditor('art_artartistdesc').setContent('');
	
    $('#artForm').find("input").attr("readonly",false);
	
	$('#artpic_up').filebox('enable');
	$('#artpic2_up').filebox('enable');
	
	$('#arttyp').combobox('readonly',false);
	$('#arttypid').combobox('readonly',false);
	
	$('#artstime').datetimebox('readonly',false);
	$('#artetime').datetimebox('readonly',false);
	 
	$('#arttags').combobox('readonly',false);
	$('#artkeys').combobox('readonly',false);
	
	UE.getEditor('art_editor').setEnabled('fullscreen');
	UE.getEditor('art_artartistdesc').setEnabled('fullscreen');
	
	winform.getWinFooter().find("a:eq(0)").show();
	winform.openWin();
}


/**修改艺术作品*/
function doUpd(idx){
	var row = WHdg.getRowData(idx);
	winform.setWinTitle("修改艺术作品");
	//打开表单
	winform.openWin();
	
	//重置表单数据
	$('#arttags').combobox('clear');
	$('#artkeys').combobox('clear');
	$('#artForm').form('clear');
	
	row.artstime = datetimeFMT(row.artstime);
	row.artetime = datetimeFMT(row.artetime);
	$('#artForm').form('load', row);
	
	//图片处理
	$('#artpic').val(row.artpic);
	$('#artpic1').val(row.artpic1);
	$('#artpic2').val(row.artpic2);
	$('#artstate').val(row.artstate);
	$('#artghp').val(row.artghp)
	$('#artpic_up_img').attr('src', getFullUrl(row.artpic)).parents('.row').show();
	$('#artpic1_up_img').attr('src', getFullUrl(row.artpic1)).parents('.row').show();
	$('#artpic2_up_img').attr('src', getFullUrl(row.artpic2)).parents('.row').show();
	
	$('#artForm').find("input").attr("readonly",false);
	
	$('#artpic_up').filebox('enable');
	$('#artpic2_up').filebox('enable');
	
	$('#arttyp').combobox('readonly',false);
	$('#arttypid').combobox('readonly',false);
	
	$('#artstime').datetimebox('readonly',false);
	$('#artetime').datetimebox('readonly',false);
	 
	$('#arttags').combobox('readonly',false);
	$('#artkeys').combobox('readonly',false);
	
	UE.getEditor('art_editor').setEnabled('fullscreen');
	UE.getEditor('art_artartistdesc').setEnabled('fullscreen');
	
	winform.getWinFooter().find("a:eq(0)").show();
	
	//需要AJAX取数富文本数据
	UE.getEditor('art_editor').setContent(row.artcontent || '');
	UE.getEditor('art_artartistdesc').setContent(row.artartistdesc || "");//(row.artartistdesc);
}
/**
 * 查看艺术作品
 */
 function see(idx){
		var row = WHdg.getRowData(idx);
		winform.setWinTitle("查看艺术作品");
		//打开表单
		winform.openWin();
		
		//重置表单数据
		$('#artForm').form('clear');
		
		row.artstime = datetimeFMT(row.artstime);
		row.artetime = datetimeFMT(row.artetime);
		$('#artForm').form('load', row);
		
		//图片处理
		$('#artpic').val(row.artpic);
		$('#artpic2').val(row.artpic2);
		$('#artstate').val(row.artstate);
		$('#artghp').val(row.artghp)
		$('#artpic_up_img').attr('src', getFullUrl(row.artpic)).parents('.row').show();
		$('#artpic2_up_img').attr('src', getFullUrl(row.artpic2)).parents('.row').show();
		//需要AJAX取数富文本数据
		UE.getEditor('art_editor').setContent(row.artcontent || '');
		UE.getEditor('art_artartistdesc').setContent(row.artartistdesc || "");//(row.artartistdesc);
		
		$('#artForm').find("input").attr("readonly",true);
		
		$('#artpic_up').filebox('disable');
		$('#artpic2_up').filebox('disable');
		
		$('#arttyp').combobox('readonly',true);
		$('#arttypid').combobox('readonly',true);
		
		$('#artstime').datetimebox('readonly',true);
		$('#artetime').datetimebox('readonly',true);
		 
		$('#arttags').combobox('readonly',true);
		$('#artkeys').combobox('readonly',true);
		
		UE.getEditor('art_editor').setDisabled('fullscreen');
		UE.getEditor('art_artartistdesc').setDisabled('fullscreen');
		
		winform.getWinFooter().find("a:eq(0)").hide();
	}
/** 删除艺术作品 */
function doDel(idx){
	var row = WHdg.getRowData(idx);
	$.messager.confirm('确认对话框', '您确认要删除吗？', function(r){
		if (r){
			$.ajax({
			   type: "POST",
			   url: "${basePath}/admin/arts/delWhzp",
			   data: {id : row.artid},
			   success: function(data){
				   if(data && data.success == '0'){
					   $('#zxDG').datagrid('reload');
				   }else{
					   $.messager.alert('提示', '操作失败。原因：'+data.errmsg, 'error');
				   }
			   }
			});
		}
	});
}

/** 提交表单-添加艺术作品 */
function doSubmitArt(){
	$("#artForm").form("submit", {
		url: '${basePath}/admin/arts/saveWhzp',
		onSubmit: function(){
			//param.artstate = '0';
			//设置富文本的内容
			$('#artcontent').val(UE.getEditor('art_editor').getContent());
			$('#artartistdesc').val(UE.getEditor('art_artartistdesc').getContent());
			 //得到当前的输入值
			 var a = $('#artstime').datetimebox('getValue');
			 var b = $('#artetime').datetimebox('getValue');
			 //转换时间格式
			 var d_a = new Date(a); 
			 var d_b = new Date(b); 
			 //时间比较
			 if(d_a >= d_b){
				 $.messager.alert("提示", "时间设置错误,开始时间必须小于结束时间");
				 return flase;
			 }
			return $(this).form('enableValidation').form('validate');
		},
		success: function(data){
			var data = eval('(' + data + ')');
			if(data && data.success == '0'){
				winform.closeWin();
				$.messager.alert('提示', '操作成功。');
				$('#zxDG').datagrid('reload');
		   	}else{
			   	$.messager.alert('提示', '操作失败。原因：'+data.errmsg, 'error');
		   	}
		}
	});
}
/**
 * 上首页
 */
 function goindex(index){
		var row = WHdg.getRowData(index);
		editWinform_.openWin();
	    editWinform_.setWinTitle("设置上首页");
	    $('#artghp').combobox({
			onChange: function(newVal, oldVal){
				if(newVal == '0'){
					$('#artidx').numberspinner('enable');
				}else {
					$('#artidx').numberspinner('disable');
				}
			}
		});
	    var frm = $('#ff_').form({
			url : getFullUrl("/admin/arts/gowha"),
			onSubmit : function(param) {
				param.artid = row.artid;
				return $(this).form('enableValidation').form('validate');
			},
			success : function(data) {
				   data = eval('('+data+')');
				   if(data && data.success == "0"){ 
					$('#zxDG').datagrid('reload');
					$.messager.alert('提示', '操作成功!');
					editWinform_.closeWin();
				   }else{
					   $.messager.alert('提示', '操作失败!');
				   }
			}
		});
	    editWinform_.setFoolterBut({onClick:function(){
			frm.submit();
		}});
} 

/** 页面加载完成后事件处理 */
$(function(){
	//定义表格参数
	var options = {
		title: '艺术作品管理',			
		url: getFullUrl('/admin/arts/sreachAllWhzp'),
		sortName: 'artid',
		sortOrder: 'desc',
		rownumbers:true,
		singleSelect:false,
		fitColumns:true,
		queryParams: {stateArray:"1"},
		columns: [[
			{field:'ck',checkbox:true},
            {field:"arttyp", title: "艺术作品类型",width:80,formatter:zxtypeFMT},
			{field:"artshorttitle", title: "列表标题",width:80, sortable:true},
			//{field:"arttypid", title: "类型标识", sortable:true},
			{field:"artstime", title: "开始时间",width:150,sortable:true,formatter :datetimeFMT},
			{field:"artetime", title: "结束时间",width:150, sortable:true,formatter :datetimeFMT},
			//{field:"arttags", title: "标签", sortable:true},
			//{field:"artkeys", title: "关键字", sortable:true},
			//{field:"artauthor", title: "作者",width:80,sortable:true},
			//{field:"arteditor", title: "小编",width:80,sortable:true},
			//{field:"artcrttime", title: "创建时间", sortable:true,formatter :datetimeFMT},
			//{field:"arttags", title: "标签", sortable:true},},
			//{field:"artfrom", title: "来源",width:125,sortable:true},
			{field:"artpic", title: "列表图",width:80,formatter:imgFMT},
			//{field:"artpic1", title: "首页图",formatter:imgFMT},
			//{field:"artpic2", title: "详情图",width:80,formatter:imgFMT},
			{field:"artstate", title: "状态",width:50,sortable:true, formatter:traStateFMT},
			//{field:"artghp", title: "上首页",sortable:true, formatter:yesNoFMT},
			//{field:"artidx", title: "上首页排序",sortable:true},
			{field:'opt', title:'操作',width:350, formatter: WHdg.optFMT, optDivId:'zxOPT'}
		]]
	};
	
	//初始表格
	WHdg.init('zxDG', 'zxTB', options); 
	//初始添加艺术作品的编辑表单
	winform = new WhuiWinForm("#art_win", {maximized:true});
	editWinform_ = new WhuiWinForm("#edit_win_",{width:500,height:250});
	editWinform_.init();
	winform.init();
	winform.setFormStyleTemp({lableWidth : '100px',});
	winform.setFoolterBut({onClick:function(){
		doSubmitArt();
	}});
	
	//添加艺术作品事件
	$('#btn_addzx').bind('click', doAdd);
	
	//实例化编辑器
    UE.getEditor('art_editor');
    UE.getEditor('art_artartistdesc');
});


/**
 * 添加资源
 * @param index
 * @returns
 */
function addzy(index){
	var rows = $('#zxDG').datagrid('getRows');
	var row = rows[index];//WHdg2.getRowData(index);
	
	$('#addzy iframe').attr('src', getFullUrl('/admin/ent/entsrc?reftype=2016101400000054&refid='+row.artid+"&canEdit=true"));
	$('#addzy').dialog({    
	    title: '资源管理',  
	    modal:true,
	    maximizable: true,
	    maximized: true,
	    width: 400,
	    height: 200   
	}); 
}
</script>
</head>
<body>

	<!-- datagrid div -->
	<div id="zxDG"></div>
	
	<!-- datagrid toolbar -->
	<div id="zxTB" class="grid-control-group" style="display: none;">
		<div>
			<shiro:hasPermission name="${resourceid}:add"><a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" id="btn_addzx">添加</a></shiro:hasPermission>
			<shiro:hasPermission name="${resourceid}:checkon"><a href="javascript:void(0)" class="easyui-linkbutton search"  onclick="artAllcheck();">批量审核</a></shiro:hasPermission>
			<shiro:hasPermission name="${resourceid}:checkoff"> <a href="javascript:void(0)" class="easyui-linkbutton search" onclick="artcheck();" >批量取消审核</a></shiro:hasPermission>
			<shiro:hasPermission name="${resourceid}:publish"><a href="javascript:void(0)" class="easyui-linkbutton search" onclick="artGocheck();" >批量发布</a></shiro:hasPermission>
			<shiro:hasPermission name="${resourceid}:publishoff"><a href="javascript:void(0)" class="easyui-linkbutton search" onclick="Gocheck();" >批量取消发布</a></shiro:hasPermission>
		</div>
		
		<div style="padding-top: 5px">
			艺术作品类型:
			<input class="easyui-combobox" name="arttyp" data-options="valueField:'typid',textField:'typname',url:'${basePath}/comm/whtyp?type=5',onChange:changeAtrtyp2"/>
			<input id="arttypid2" name="arttypid" class="easyui-combobox" data-options="valueField:'typid',textField:'typname'"/>
			列表标题:
			<input class="easyui-textbox" name="artshorttitle" data-options="validType:'length[1,30]'"/>
			状态:
			<select class="easyui-combobox radio" name="artstate">
							<option value="">全部</option>
	    					<option value="0">未审核</option>
	    					<option value="2">已审核</option>
	    					<option value="3">已发布</option>
			</select>
			<a href="#" class="easyui-linkbutton search" iconCls="icon-search">查询</a>
		</div>
	</div>
	
	<!-- 操作按钮 -->
	<div id="zxOPT" style="display: none;">
		<shiro:hasPermission name="${resourceid}:view"><a href="javascript:void(0)" validKey="artstate" validVal="0,1" class="easyui-linkbutton" data-options="plain:true" method="addzy">资源管理</a></shiro:hasPermission>
		<shiro:hasPermission name="${resourceid}:view"><a href="javascript:void(0)" validKey="artstate" validVal="0,1,2,3" method="see">查看</a></shiro:hasPermission>
		<shiro:hasPermission name="${resourceid}:edit"><a href="javascript:void(0)" validKey="artstate" validVal="0,1" method="doUpd">修改</a></shiro:hasPermission> 
		<shiro:hasPermission name="${resourceid}:del"><a href="javascript:void(0)"validKey="artstate" validVal="0,1" method="doDel">删除</a></shiro:hasPermission>
		<shiro:hasPermission name="${resourceid}:checkon"><a href="javascript:void(0)" validKey="artstate" validVal="0,1" method="checkTrou">审核</a></shiro:hasPermission>
        <shiro:hasPermission name="${resourceid}:checkoff"><a href="javascript:void(0)" validKey="artstate" validVal="2" method="uncheckTrou">取消审核</a></shiro:hasPermission>
        <shiro:hasPermission name="${resourceid}:publish"><a href="javascript:void(0)" validKey="artstate" validVal="2" method="ucheckTrou">发布</a></shiro:hasPermission>
        <shiro:hasPermission name="${resourceid}:publishoff"><a href="javascript:void(0)" validKey="artstate" validVal="3" method="ncheckTrou">取消发布</a></shiro:hasPermission>
	</div>	
	
	<!--添加资源dialog  -->
	<div id="addzy">
		<iframe  style="width:100%; height:100%"></iframe>
	</div>
	
	
	<!-- 添加/编辑艺术作品对话框 -->
	<div id="art_win" style="display: none;" data-options="maximized:true">
		<form id="artForm" method="post" enctype="multipart/form-data">
			<input type="hidden" id="artstate" name="artstate" value="0"/>
			<input type="hidden" id="artghp" name="artghp" value=""/>
			<input type="hidden" id="artid" name="artid" value=""/>
			<input type="hidden" id="artpic" name="artpic" value=""/>
			<input type="hidden" id="artpic1" name="artpic1" value=""/>
			<input type="hidden" id="artpic2" name="artpic2" value=""/>
			<input type="hidden" id="artcontent" name="artcontent" value=""/>
			<input type="hidden" id="artartistdesc" name="artartistdesc" value=""/>
			<div class="main">
				<div class="row">
					<div><label>艺术类型：</label></div>
					<div>
						<input id="arttyp" name="arttyp" class="easyui-combobox" style="width:50%;height:32px;" data-options="required:true, valueField:'typid',textField:'typname',url:'${basePath}/comm/whtyp?type=5', onChange:changeAtrtyp"/>
						<input id="arttypid" name="arttypid" class="easyui-combobox" style="width:49.9%;height:32px;" data-options="required:true, valueField:'typid',textField:'typname',url:'${basePath}/comm/whtyp?type=2'"/>
					</div>
				</div>
				<div class="row">
					<div><label>详情标题：</label></div>
					<div>
						<input name="arttitle" class="easyui-textbox" style="width:100%;height:35px" data-options="required:true, validType:'length[1,60]'" />
					</div>
				</div>
				
				<div class="row">
					<div><label>列表标题：</label></div>
					<div>
						<input name="artshorttitle" class="easyui-textbox" style="width:100%;height:35px" data-options="required:true, validType:'length[1,30]'" />
					</div>
				</div>
				
				<div class="row">
					<div><label></label></div>
					<div>
						<img id="artpic_up_img" style="height:150px;"/>
					</div>
				</div>
				
				<div class="row">
					<div><label>列表图：</label></div>
					<div>
						<input class="easyui-filebox" name="artpic_up" id="artpic_up" style="width:100%;height:32px;">
					</div>
				</div>
				
				<div class="row">
					<div><label></label></div>
					<div>
						<img id="artpic2_up_img" style="height:150px;"/>
					</div>
				</div>
				
				<div class="row">
					<div><label>详情图：</label></div>
					<div>
						<input class="easyui-filebox" name="artpic2_up" id="artpic2_up" style="width:100%;height:32px;">
					</div>
				</div>
				<div class="row">
					<div><label>标签：</label></div>
					<div>
						<input name="arttags" id="arttags" class="easyui-combobox" style="width:100%;height:32px;" data-options="editable:false,required:true, valueField:'name',textField:'name',url:'${basePath}/comm/whtag?type=2016102100000001',multiple:true"/>
					</div>
				</div> 
				
				<div class="row">
					<div><label>关键字：</label></div>
					<div>
						<input name="artkeys" id="artkeys" class="easyui-combobox" style="width:100%;height:32px;" data-options="required:true, valueField:'name',textField:'name',url:'${basePath}/comm/whkey?type=2016101700000007',multiple:true"/>
					</div>
				</div>
				
				<div class="row">
					<div><label>开始时间：</label></div>
					<div>
						<input id="artstime" name="artstime" class="easyui-datetimebox" style="width:100%;height:32px;" data-options="required:true"></input>  
					</div>
				</div>
				
				<div class="row">
					<div><label>结束时间：</label></div>
					<div>
						<input id="artetime" name="artetime" class="easyui-datetimebox" style="width:100%;height:32px;" data-options="required:true"></input>  
					</div>
				</div>
				
				<div class="row">
					<div><label>地址：</label></div>
					<div>
						<input name="artaddr" class="easyui-textbox" style="width:100%;height:32px;" data-options="required:true, validType:'length[1,100]'"/>
					</div>
				</div>
				
				<div class="row">
					<div><label>作者：</label></div>
					<div>
						<input name="artauthor" class="easyui-textbox" style="width:100%;height:32px;" data-options="required:true, validType:'length[1,20]'"/>
					</div>
				</div>
				<!-- 
				<div class="row">
					<div><label>小编：</label></div>
					<div>
						<input name="arteditor" class="easyui-textbox" style="width:100%;height:32px;" data-options="required:true, validType:'length[1,20]'"/>
					</div>
				</div> -->
				<!-- 
				<div class="row">
					<div><label>来源：</label></div>
					<div>
						<input name="artfrom" class="easyui-textbox" style="width:100%;height:32px;" data-options="validType:'length[1,30]'"/>
					</div>
				</div> -->
				
				<div class="row">
					<div><label>备注：</label></div>
					<div>
						<input name="artnote" class="easyui-textbox" style="width:100%;height:100px;" data-options="multiline:true, validType:'length[1,128]'"/>
					</div>
				</div>
				<div class="row">
					<div><label>艺术家详情：</label></div>
					<div>
						<script id="art_artartistdesc" type="text/plain" style="width:100%; height:300px;"
						data-options="validType:'length[0,250]',multiline:true"/></script>
					</div>
				</div>
				<div class="row">
					<div><label>内容详情：</label></div>
					<div>
						<script id="art_editor" type="text/plain" style="width:100%; height:300px;"
						data-options="validType:'length[0,400]',multiline:true"/></script>
					</div>
				</div>
			</div>
		</form>
	</div>
	 <!-- 上首页编辑层 -->
   <div id="edit_win_" class="none">
		<form method="post" id="ff_" enctype="multipart/form-data">
		<input type="hidden" name="artghp"/>
		 <!-- 隐藏作用域 -->
			<div class="main">
				<div class="row">
					<div><label>上首页排序</label></div>
					<div>
						<input class="easyui-numberspinner" id="artidx" name="artidx" style="width:90%;height:35px" data-options="increment:1, required:true,min:1,max:999,editable:true"/>
					</div>
				</div>
		    </div>
	    </form>
   </div>
		
</body>
</html>