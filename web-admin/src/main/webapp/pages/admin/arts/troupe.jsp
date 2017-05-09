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
/**
 * 添加资源
 */
// function addzy(index){
//	var rows = $('#troDG').datagrid('getRows');
//	var row = rows[index];//WHdg2.getRowData(index);
//
//	$('#addzy iframe').attr('src', getFullUrl('/admin/ent/entsrc?reftype=2016102400000001&refid='+row.troupeid+"&canEdit=true"));
//	$('#addzy').dialog({
//	    title: '资源管理',
//	    modal:true,
//	    maximizable: true,
//	    maximized: true,
//	    width: 400,
//	    height: 200
//	});
//}
/**
 * 资源管理
 * @param idx
 */
function addzy(idx) {
	var row = $("#troDG").datagrid("getRows")[idx];
	WhgComm.editDialog('${basePath}/admin/train/resource/view/list?reftype=12&id=' + row.troupeid);
}

/**
 * 批量审核
 */
 function checkAllTrou(){
	var rows={};
	rows = $("#troDG").datagrid("getSelections");
	if (rows == "" || rows == null) {
		$.messager.alert('提示', '操作失败,没有选择要操作的记录');
		return;
	}
	var troupeids = _split = "";//id1,id2,id3
	for (var i = 0; i<rows.length; i++){
		troupeids += _split+rows[i].troupeid;
		_split = ",";
	}
	//alert(JSON.stringify(artistids));
	$.messager.confirm('确认对话框', '您确认将所选择的进行发布吗？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/arts/gocheckTrou'),
				data: {troupeid : troupeids,fromstate:0, tostate:2},
				success: function(data){
					if(data.success == '0'){
						$('#troDG').datagrid('reload');
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
	rows = $("#troDG").datagrid("getSelections");
	if (rows == "" || rows == null) {
		$.messager.alert('提示', '操作失败,没有选择要操作的记录');
		return;
	}
	var troupeids = _split = "";//id1,id2,id3
	for (var i = 0; i<rows.length; i++){
		troupeids += _split+rows[i].troupeid;
		_split = ",";
	}
	//alert(JSON.stringify(artistids));
	$.messager.confirm('确认对话框', '您确认将所选择的进行发布吗？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/arts/gocheckTrou'),
				data: {troupeid : troupeids,fromstate:2, tostate:0},
				success: function(data){
					if(data.success == '0'){
						$('#troDG').datagrid('reload');
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
	rows = $("#troDG").datagrid("getSelections");
	if (rows == "" || rows == null) {
		$.messager.alert('提示', '操作失败,没有选择要操作的记录');
		return;
	}
	var troupeids = _split = "";//id1,id2,id3
	for (var i = 0; i<rows.length; i++){
		troupeids += _split+rows[i].troupeid;
		_split = ",";
	}
	//alert(JSON.stringify(artistids));
	$.messager.confirm('确认对话框', '您确认将所选择的进行发布吗？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/arts/gocheckTrou'),
				data: {troupeid : troupeids,fromstate:2, tostate:3},
				success: function(data){
					if(data.success == '0'){
						$('#troDG').datagrid('reload');
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
	rows = $("#troDG").datagrid("getSelections");
	if (rows == "" || rows == null) {
		$.messager.alert('提示', '操作失败,没有选择要操作的记录');
		return;
	}
	var troupeids = _split = "";//id1,id2,id3
	for (var i = 0; i<rows.length; i++){
		troupeids += _split+rows[i].troupeid;
		_split = ",";
	}
	//alert(JSON.stringify(artistids));
	$.messager.confirm('确认对话框', '您确认将所选择的进行发布吗？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/arts/gocheckTrou'),
				data: {troupeid : troupeids,fromstate:3, tostate:2},
				success: function(data){
					if(data.success == '0'){
						$('#troDG').datagrid('reload');
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
	 var id = row.troupeid;
     $.messager.confirm('确认对话框', '确定要通过审核？', function(r) {
         if (r) {
        	 $.ajax({
        		type: "POST",
         		url : getFullUrl("./admin/arts/checktroupe"),
         		data : {troupeid:id, troupestate:2},
         		success:function(data){
         			if (data=="success"){
                         //$.messager.alert("提示","操作成功");
                         $("#troDG").datagrid("reload");
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
 	 var id = row.troupeid;
 	 if (row.troupestate==2) {
      $.messager.confirm('确认对话框', '确定要取消审核？', function(r) {
          if (r) {
         	 $.ajax({
         		type: "POST",
          		url : getFullUrl("./admin/arts/checktroupe"),
          		data : {troupeid:id, troupestate:0},
          		success:function(data){
          			if (data=="success"){
                          //$.messager.alert("提示","操作成功");
                          $("#troDG").datagrid("reload");
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
 	 var id = row.troupeid;
 	 if (row.troupestate==2) {
      $.messager.confirm('确认对话框', '确定要发布？', function(r) {
          if (r) {
         	 $.ajax({
         		type: "POST",
          		url : getFullUrl("./admin/arts/checktroupe"),
          		data : {troupeid:id,troupestate:3},
          		success:function(data){
          			if (data=="success"){
                          //$.messager.alert("提示","操作成功");
                          $("#troDG").datagrid("reload");
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
	var id = row.troupeid;
	$.messager.confirm('确认对话框', '确定要取消发布？', function(r) {
	    if (r) {
	   	 $.ajax({
	   		type: "POST",
	    		url : getFullUrl("./admin/arts/checktroupe"),
	    		data : {troupeid:id, troupestate:2},
	    		success:function(data){
	    			if (data=="success"){
	                    //$.messager.alert("提示","操作成功");
	                    $("#troDG").datagrid("reload");
	                }else{
	                	 $.messager.alert("提示", "操作失败！");
	                }
	    		}
	    	})
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

/**
 * 查看艺术团队信息
 */
 function seetro(index){
	var row = WHdg.getRowData(index);
	var id = row.troupeid;
	if (row){
		$('#ff').form('clear');
		row.trouperegtime = datetimeFMT(row.trouperegtime);
		row.troupeopttime = datetimeFMT(row.troupeopttime);
		row.troupetag = row.troupetag || '';
		row.troupekey = row.troupekey || '';
		//图片
		if (row.troupepic && row.troupepic != '' ){
        	if($('#troupepic_up_img').size() > 0) $('#troupepic_up_img').attr('src', getFullUrl(row.troupepic)).parents('.row').show();
        }else{
        	if($('#troupepic_up_img').size() > 0) $('#troupepic_up_img').parents('.row').hide();
        }
		if (row.troupebigpic && row.troupebigpic != '' ){
			if($('#troupebigpic_up_img').size() > 0) $('#troupebigpic_up_img').attr('src', getFullUrl(row.troupebigpic)).parents('.row').show();
        }else{
        	if($('#troupebigpic_up_img').size() > 0) $('#troupebigpic_up_img').parents('.row').hide();
        }

		$('#ff').find("input").attr("readonly",true);
		if($('#troupepic_up').size() > 0) $('#troupepic_up').filebox('disable');
		if($('#troupebigpic_up').size() > 0) $('#troupebigpic_up').filebox('disable');
		if($('#troupearttyp').size() > 0) $('#troupearttyp').combobox('readonly',true);
		if($('#trouperegtime').size() > 0) $('#trouperegtime').datetimebox('readonly',true);
		if($('#troupetype').size() > 0) $('#troupetype').textbox('readonly',true);
		if($('#troupernum').size() > 0) $('#troupernum').numberspinner('readonly',true);
		if($('#troupemain').size() > 0) $('#troupemain').textbox('readonly',true);
		if($('#troupedesc').size() > 0) $('#troupedesc').textbox('readonly',true);
		if($('#troupetag').size() > 0) $('#troupetag').combobox('readonly',true);
		if($('#troupekey').size() > 0) $('#troupekey').combobox('readonly',true);
		
		UE.getEditor('traeditor').setDisabled('fullscreen');
		editWinform.setWinTitle("查看团队信息");
		editWinform.openWin();
		$('#ff').form('load', row);
		UE.getEditor('traeditor').setContent(row.troupecontent);
		var url = getFullUrl('./admin/arts/uptroupe');
		editWinform.getWinFooter().find("a:eq(0)").hide();
	} 
}

/**
 * 成员管理
 *
 * */
function troupeUser(index){
    var row = WHdg.getRowData(index);
    var id = row.troupeid;
    WhgComm.editDialog('${basePath}/admin/arts/troupeUser?troupeid='+id);
}

/**
 * 编辑艺术团队信息
 */
 function uptro(index){
	var row = WHdg.getRowData(index);
	var id = row.troupeid;
	if (row){
		//显示编辑表单
		editWinform.setWinTitle("编辑团队信息");
		editWinform.openWin();
		
		//特殊数据处理
		row.trouperegtime = datetimeFMT(row.trouperegtime);
		row.troupeopttime = datetimeFMT(row.troupeopttime);
		row.troupetag = row.troupetag || '';
		row.troupekey = row.troupekey || '';
		
		//图片
		if (row.troupepic && row.troupepic != '' ){
			if($('#troupepic_up_img').size() > 0) $('#troupepic_up_img').attr('src', getFullUrl(row.troupepic)).parents('.row').show();
        }else{
        	if($('#troupepic_up_img').size() > 0) $('#troupepic_up_img').parents('.row').hide();
        }
		if (row.troupebigpic && row.troupebigpic != '' ){
			if($('#troupebigpic_up_img').size() > 0) $('#troupebigpic_up_img').attr('src', getFullUrl(row.troupebigpic)).parents('.row').show();
        }else{
        	if($('#troupebigpic_up_img').size() > 0) $('#troupebigpic_up_img').parents('.row').hide();
        }
		$('#ff').find("input").attr("readonly",false);
		if($('#troupepic_up').size() > 0) $('#troupepic_up').filebox('enable');
		if($('#troupebigpic_up').size() > 0) $('#troupebigpic_up').filebox('enable');
		if($('#troupearttyp').size() > 0) $('#troupearttyp').combobox('readonly',false);
		if($('#trouperegtime').size() > 0) $('#trouperegtime').datetimebox('readonly',false);
		if($('#troupetype').size() > 0) $('#troupetype').textbox('readonly',false);
		if($('#troupernum').size() > 0) $('#troupernum').numberspinner('readonly',false);
		if($('#troupemain').size() > 0) $('#troupemain').textbox('readonly',false);
		if($('#troupedesc').size() > 0) $('#troupedesc').textbox('readonly',false);
		if($('#troupetag').size() > 0) $('#troupetag').combobox('readonly',false);
		if($('#troupekey').size() > 0) $('#troupekey').combobox('readonly',false);
		
		//初始表单
		$('#ff').form({
			novalidate: true,
			url : getFullUrl('./admin/arts/uptroupe'),
			onSubmit : function(param){
				var _isOK = true;
                param.troupeid = row.troupeid;
                param.troupepic = row.troupepic;
                param.troupebigpic=row.troupebigpic;
            	//得到当前的输入值
				 var a = $('#trouperegtime').datetimebox('getValue');
				 //转换时间格式
				 var d_a = new Date(a);
				 var myDate=new Date();
				 //时间比较
				 if(d_a >= myDate.getTime()){
					 $.messager.alert("提示", "时间设置错误,成立时间必须小于当前时间");
					 _isOK = false;
				 }else{
					 _isOK = $(this).form('enableValidation').form('validate');
				 }
				 if(!_isOK){
					 editWinform.getWinFooter().find("a:eq(0)").one('click', function(){
						$('#ff').form('submit');
					 });
				 }
				//设置富文本的值
				 $('#troupecontent').val(UE.getEditor('traeditor').getContent());
                return $(this).form('enableValidation').form('validate');
            },
			success : function(data) {
				var json = $.parseJSON(data);
				if (json.success=='0'){
					$('#troDG').datagrid('reload');
					editWinform.closeWin();
					$.messager.alert("提示", "操作成功");
				}else{
					$.messager.alert("失败了", json.msg);
					editWinform.getWinFooter().find("a:eq(0)").one('click', function(){
						$('#ff').form('submit');
					});
				}
			}
		});
		
		$('#ff').form('clear');
		$('#ff').form('load', row);
		
		UE.getEditor('traeditor').setEnabled('fullscreen');
		UE.getEditor('traeditor').setContent(row.troupecontent);
		
		if($('#troupepic_up').size() > 0) $('#troupepic_up').filebox({required: false});
		if($('#troupebigpic_up').size() > 0) $('#troupebigpic_up').filebox({required: false});
		
		editWinform.getWinFooter().find("a:eq(0)").show();
		editWinform.getWinFooter().find("a:eq(0)").one('click', function(){
			$('#ff').form('submit');
		});
	} 
}

/**
 * 添加艺术团队信息
 */
function addtroupe(){
	//1. 显示添加表单并设置好标题
	editWinform.setWinTitle("添加团队");
	editWinform.openWin();
	
	//2. 隐藏图片
	if($('#troupepic_up_img').size() > 0) $('#troupepic_up_img').removeAttr('src').parents('.row').hide();
	if($('#troupebigpic_up_img').size() > 0) $('#troupebigpic_up_img').removeAttr('src').parents('.row').hide();
	
	//3. 所有表单元素可编辑
	$('#ff').find("input").attr("readonly", false);
	
	//4. 其它表单元素可编辑
	if($('#troupepic_up').size() > 0) $('#troupepic_up').filebox('enable');
	if($('#troupebigpic_up').size() > 0) $('#troupebigpic_up').filebox('enable');
	if($('#troupearttyp').size() > 0) $('#troupearttyp').combobox('readonly',false);
	if($('#trouperegtime').size() > 0) $('#trouperegtime').datetimebox('readonly',false);
	if($('#troupetype').size() > 0) $('#troupetype').textbox('readonly',false);
	if($('#troupernum').size() > 0) $('#troupernum').numberspinner('readonly',false);
	if($('#troupemain').size() > 0) $('#troupemain').textbox('readonly',false);
	if($('#troupedesc').size() > 0) $('#troupedesc').textbox('readonly',false);
	if($('#troupetag').size() > 0) $('#troupetag').combobox('readonly',false);
	if($('#troupekey').size() > 0) $('#troupekey').combobox('readonly',false);
	if($('#troupetag').size() > 0) $('#troupetag').combobox('clear');
	if($('#troupekey').size() > 0) $('#troupekey').combobox('clear');
	
	//5. 清空富文本值
	UE.getEditor('traeditor').setEnabled('fullscreen');
	UE.getEditor('traeditor').setContent("");
	
	//6. 定义表单 
	$('#ff').form({
		novalidate: true,
		url: getFullUrl('/admin/arts/addtroupe'),
		onSubmit: function(param){
			param.troupestate = 0;
           	param.troupeidx = 1;
           	param.troupeghp = 0;
           	param.troupeuid = 0;
           	
           	//得到当前的输入值
			var a = $('#trouperegtime').datetimebox('getValue');
			//转换时间格式
			var d_a = new Date(a); 
			//当前时间	
			var myDate = new Date();
			//时间比较
			var _isOK = true;
			if(d_a >= myDate.getTime()){
				$.messager.alert("提示", "时间设置错误,成立时间必须小于当前时间");
				_isOK = false;
			}else{
				_isOK = $(this).form('enableValidation').form('validate');
			}
			
			if( !_isOK ){
				editWinform.getWinFooter().find("a:eq(0)").one('click', function(){
	        		$('#ff').form('submit');
	        	});
			}else{
				//设置富文本的值
				$('#troupecontent').val(UE.getEditor('traeditor').getContent());
			}
			
            return _isOK;
		},
		success : function(data) {
			var json = $.parseJSON(data);
            if (json.success){
               	$('#troDG').datagrid('reload');
				$.messager.alert("提示", "操作成功");
				editWinform.closeWin();
            }else{
            	$.messager.alert("失败了", json.msg);
            }
            editWinform.getWinFooter().find("a:eq(0)").one('click', function(){
            	editWinform.getWinFooter().find("a:eq(0)").off('click');
        		$('#ff').form('submit');
        	});
		}
	});
	
	//7. 清空表单的值
	$('#ff').form('clear');
	
	//8. 图片必填
	if($('#troupepic_up').size() > 0) $('#troupepic_up').filebox({required: true});
	if($('#troupebigpic_up').size() > 0) $('#troupebigpic_up').filebox({required: true});
	if($('#troupernum').size() > 0) $('#troupernum').numberspinner('setValue', 10);
	
	//8. 显示提交按钮
	editWinform.getWinFooter().find("a:eq(0)").show();
	
	//9. 给提交按钮绑定提交事件，只执行一次，防止重复提交
	editWinform.getWinFooter().find("a:eq(0)").one('click', function(){
		$('#ff').form('submit');
	});
}
 
/**
 * 根据主键id删除
 */
 function deltroupe(index){
	 var row = WHdg.getRowData(index);
		var id = row.troupeid;
		var pic = row.troupepic;
		var bigpic = row.troupebigpic;
		$.messager.confirm('确认对话框', '您确认要删除吗？', function(r){
			if (r){
				$.ajax({
					type: "POST",
					url: getFullUrl('/admin/arts/deltroupe'),
					data: {troupeid : id, troupepic:pic,troupebigpic : bigpic},
					success: function(msg){
						$.messager.alert("提示", "操作成功");
						$('#troDG').datagrid('reload');
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
		title: '馆办团队管理',
		url: getFullUrl('/admin/arts/seletroupe'),
		sortName: 'troupeid',
		sortOrder: 'desc',
		rownumbers: true,
		singleSelect: false,
		columns:[[ 
			{field:'ck', width:50, checkbox:true},    
			//{field:'troupeid',title:'团队标识', width:120, sortable:true},
			//{field:'troupeuid',title:'艺术团所属注册用户标识'},    
			{field:'troupearttyp',title:'艺术类型', width:100, formatter: WhgComm.FMTArtType},
			{field:'troupename',title:'团队名称', width:150},
			{field:'troupepic',title:'图片', width:80, formatter:imgFMT},
			//{field:'troupetype',title:'艺术团类型', width:150,sortable:true},    
			//{field:'troupernum',title:'成员人数', width:100,sortable:true},    
			//{field:'trouperegtime',title:'成立时间', width:150, sortable:true,formatter :datetimeFMT},
			//{field:'troupeghp',title:'上首页标识',sortable:true,formatter: yesNoFMT},
			//{field:'troupeidx',title:'上首页排序',sortable:true},
			{field:'troupestate',title:'状态',sortable:true, width:80, formatter : traStateFMT},
			//{field:'troupedesc',title:'艺术团简介'},
			//{field:'troupemain',title:'代表作品'},
			{field:'opt', title:'操作', width:200, formatter: WHdg.optFMT, optDivId:'troOPT'}
		]]  
	};
	
	//初始表格
	WHdg.init('troDG', 'troTB',  options);
	editWinform.init();
	
	//初始富文本
	UE.getEditor('traeditor');
});
</script>
</head>
<body>
	<!-- 表格 -->
	<div id="troDG"></div>
	<!-- 表格-END -->
	
	<!-- 表格工具栏 -->
	<div id="troTB" style="height:auto; display:none; padding-top:5px">
		<div>
			<shiro:hasPermission name="${resourceid}:add">       <a href="javascript:void(0)" size="large" class="easyui-linkbutton" plain="true" iconCls="icon-add" onclick="addtroupe();">添加</a></shiro:hasPermission>  
			<shiro:hasPermission name="${resourceid}:checkon">   <a href="javascript:void(0)" size="large" class="easyui-linkbutton" plain="true" onclick="checkAllTrou();">批量审核</a></shiro:hasPermission> 
			<shiro:hasPermission name="${resourceid}:checkoff">  <a href="javascript:void(0)" size="large" class="easyui-linkbutton" plain="true" onclick="allTrou();" >批量取消审核</a></shiro:hasPermission>
			<shiro:hasPermission name="${resourceid}:publish">   <a href="javascript:void(0)" size="large" class="easyui-linkbutton" plain="true" onclick="goAllTrou();" >批量发布</a></shiro:hasPermission>
			<shiro:hasPermission name="${resourceid}:publishoff"><a href="javascript:void(0)" size="large" class="easyui-linkbutton" plain="true" onclick="goTrou();" >批量取消发布</a></shiro:hasPermission>
	    </div>
		<div style="padding-top:5px">
			<input class="easyui-combobox" name="troupearttyp" style="width: 200px; height:28px" data-options="prompt:'请选择艺术类型', valueField:'id',textField:'text',data:WhgComm.getArtType()"/>

			<input class="easyui-textbox" name="troupename" style="width: 200px; height:28px" data-options="prompt:'请输入团队名称', validType:'length[1,30]'"/>

			<select class="easyui-combobox radio" name="troupestate" style="width: 200px; height:28px" data-options="prompt:'请选择状态'">
				<option value="">全部</option>
				<option value="0">未审核</option>
				<option value="2">已审核</option>
				<option value="3">已发布</option>
			</select>
			<a href="#" class="easyui-linkbutton search" iconCls="icon-search">查询</a>
		</div>
	</div>
	<!-- 表格工具栏-END -->
	
	<!-- 操作按钮 -->
	<div id="troOPT" class="none" style="display:none">
	     <shiro:hasPermission name="${resourceid}:view">      <a href="javascript:void(0)" class="easyui-linkbutton" method="addzy">资源管理</a></shiro:hasPermission>
	    <shiro:hasPermission name="${resourceid}:view">      <a href="javascript:void(0)" class="easyui-linkbutton" validKey="troupestate" validVal="0,1,2,3" method="seetro">查看</a></shiro:hasPermission> 
		<shiro:hasPermission name="${resourceid}:edit">      <a href="javascript:void(0)" class="easyui-linkbutton" validKey="troupestate" validVal="0,1" method="uptro">编辑</a></shiro:hasPermission> 
		<shiro:hasPermission name="${resourceid}:edit">      <a href="javascript:void(0)" class="easyui-linkbutton" validKey="troupestate" validVal="0,1" method="troupeUser">成员管理</a></shiro:hasPermission>
		<shiro:hasPermission name="${resourceid}:del">       <a href="javascript:void(0)" class="easyui-linkbutton" validKey="troupestate" validVal="0,1" method="deltroupe">删除</a></shiro:hasPermission>
		<shiro:hasPermission name="${resourceid}:checkon">   <a href="javascript:void(0)" class="easyui-linkbutton" validKey="troupestate" validVal="0,1" method="checkTrou">审核</a></shiro:hasPermission>
        <shiro:hasPermission name="${resourceid}:checkoff">  <a href="javascript:void(0)" class="easyui-linkbutton" validKey="troupestate" validVal="2" method="uncheckTrou">取消审核</a></shiro:hasPermission>
        <shiro:hasPermission name="${resourceid}:publish">   <a href="javascript:void(0)" class="easyui-linkbutton" validKey="troupestate" validVal="2" method="ucheckTrou">发布</a></shiro:hasPermission>
        <shiro:hasPermission name="${resourceid}:publishoff"><a href="javascript:void(0)" class="easyui-linkbutton" validKey="troupestate" validVal="3" method="ncheckTrou">取消发布</a></shiro:hasPermission>
        <shiro:hasPermission name="${resourceid}:order">     <a href="javascript:void(0)" class="easyui-linkbutton" validKey="troupestate" validVal="3" method="goindex">排序</a></shiro:hasPermission>
	</div>
		
	<!--添加资源dialog  -->
    <div id="addzy" style="display:none;">
	     <iframe  style="width:100%; height:100%"></iframe>
    </div>
	    
	<!-- div弹出层 --> 
	<div id="edit_win" data-options="maximized:true" style="display:none">
		<form method="post" id="ff" enctype="multipart/form-data">
		 <!-- 隐藏作用域 -->
		 	<input type="hidden" id="troupecontent" name="troupecontent" value="" />
			<div class="main">
			
				<div class="row">
					<div><label>艺术类型:</label></div>
					<div>
						<input class="easyui-combobox" name="troupearttyp" id="troupearttyp" style="width:90%; height:35px" data-options="editable:false, required:true, valueField:'id',textField:'text',data:WhgComm.getArtType()"/>
					</div>
				</div>
			   	<!-- <div class="row">
					<div><label>艺术团类型:</label></div>
					<div>
						<input class="easyui-textbox" name="troupetype" id="troupetype" value="gd" style="width:90%;height:35px" data-options="required:true, validType:'length[0,30]'"/>
					</div>
				</div> -->
				<div class="row">
					<div><label>团队名称:</label></div>
					<div>
						<input class="easyui-textbox" name="troupename" style="width:90%;height:35px" data-options="required:true, validType:'length[0,60]'"/>
					</div>
				</div>
				<%-- <div class="row">
		    		<div><label>标签:</label></div>
		    		<div>
						<input id="troupetag" class="easyui-combobox" name="troupetag" multiple="true" style="width:90%;height:32px;" data-options=" valueField:'id',textField:'name',url:'${basePath}/comm/whtag?type=2016102600000002', multiple:true"/>
		    		</div>
		    	</div>
		    	<div class="row">
		    		<div><label>关键字:</label></div>
			    	<div>
						<input id="troupekey" class="easyui-combobox" name="troupekey" multiple="true" style="width:90%;height:32px;" data-options=" valueField:'id',textField:'name',url:'${basePath}/comm/whkey?type=2016102600000001', multiple:true"/>
			    	</div>
		    	</div> --%>
				<div class="row">
					<div><label>图片:</label></div>
					<div>
						<input class="easyui-filebox" id="troupepic_up" name="troupepic_up" style="width:90%;height:35px" data-options="editable:false, required: true, validType:'isIMG[\'troupepic_up\', 1024]', buttonText:'选择图片', accept:'image/jpeg, image/png' ,prompt:'建议:320*200'"/>
					</div>
				</div>
				<div class="row">
					<div><label></label></div>
					<div>
						<img id="troupepic_up_img" style="height:150px;"/>
					</div>
				</div>
				<!-- <div class="row">
					<div><label>详情页图片:</label></div>
					<div>
						<input class="easyui-filebox" id="troupebigpic_up" name="troupebigpic_up" style="width:90%;height:35px" data-options="required: true, validType:'isIMG[\'troupebigpic_up\', 1024]', buttonText:'选择封面', accept:'image/jpeg, image/png'"/>
					</div>
				</div>
				<div class="row">
					<div><label></label></div>
					<div>
						<img id="troupebigpic_up_img" style="height:150px;"/>
					</div>
				</div> -->
				<div class="row">
					<div><label>成立时间:</label></div>
					<div>
						<input id="trouperegtime" name="trouperegtime" type="text" class="easyui-datetimebox" style="width:90%;height:35px" data-options="editable:false, required:true">
					</div>
				</div>
				<div class="row">
					<div><label>成员数:</label></div>
					<div>
						<input class="easyui-numberspinner" name="troupernum" id="troupernum" style="width:90%;height:35px" data-options="increment:1, required:true, min:2, max:999"/>
					</div>
				</div>
				<!-- <div class="row">
					<div><label>代表作品:</label></div>
					<div>
						<input class="easyui-textbox" name="troupemain" id="troupemain" style="width:90%;height:35px" data-options="required:true, validType:'length[0,60]'"/>
					</div>
				</div> -->
				<div class="row">
					<div><label>联系地址:</label></div>
					<div>
						<input class="easyui-textbox" name="troupeaddr" id="troupeaddr" style="width:90%; height:35px" data-options="required: true, validType:'length[0,150]'" />
					</div>
				</div>
				<div class="row">
					<div><label>联系方式:</label></div>
					<div>
						<input class="easyui-textbox" name="troupecontact" id="troupecontact" style="width:90%; height:35px" data-options="required: true, validType:'isPhone'" />
					</div>
				</div>
				<div class="row">
					<div><label>简介:</label></div>
					<div>
						<input class="easyui-textbox" name="troupedesc" id="troupedesc" style="width:90%; height:120px" data-options="validType:'length[0,400]', multiline:true" />
					</div>
				</div>
				<div class="row">
					<div><label>详细介绍:</label></div>
					<div>
						<div style="width:90%">
							<script id="traeditor" type="text/plain" style="width:100%; height:300px;"></script>
						</div>
					</div>
				</div>
			   
		  </div>
	  </form>
   </div>
   
</body>
</html>

