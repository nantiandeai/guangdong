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
<script>
/**
 * 批量审核
 */
function doalltrou(){
	var rows={};
	rows = $("#exhDG").datagrid("getSelections");
	if (rows == "" || rows == null) {
		$.messager.alert('提示', '操作失败,没有选择要操作的记录');
		return;
	}
	var exhids = _split = "";//id1,id2,id3
	for (var i = 0; i<rows.length; i++){
		exhids += _split+rows[i].exhid;
		_split = ",";
	}
	//alert(JSON.stringify(exhids));
	$.messager.confirm('确认对话框', '您确认将所选择的进行发布吗？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/arts/checkexi'),
				data: {exhid : exhids,fromstate:0, tostate:2},
				success: function(data){
					if(data.success == '0'){
						$('#exhDG').datagrid('reload');
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
function toalltrou(){
	var rows={};
	rows = $("#exhDG").datagrid("getSelections");
	if (rows == "" || rows == null) {
		$.messager.alert('提示', '操作失败,没有选择要操作的记录');
		return;
	}
	var exhids = _split = "";//id1,id2,id3
	for (var i = 0; i<rows.length; i++){
		exhids += _split+rows[i].exhid;
		_split = ",";
	}
	//alert(JSON.stringify(exhids));
	$.messager.confirm('确认对话框', '您确认将所选择的进行发布吗？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/arts/checkexi'),
				data: {exhid : exhids,fromstate:2, tostate:3},
				success: function(data){
					if(data.success == '0'){
						$('#exhDG').datagrid('reload');
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
function dotrou(){
	var rows={};
	rows = $("#exhDG").datagrid("getSelections");
	if (rows == "" || rows == null) {
		$.messager.alert('提示', '操作失败,没有选择要操作的记录');
		return;
	}
	var exhids = _split = "";//id1,id2,id3
	for (var i = 0; i<rows.length; i++){
		exhids += _split+rows[i].exhid;
		_split = ",";
	}
	//alert(JSON.stringify(exhids));
	$.messager.confirm('确认对话框', '您确认将所选择的进行发布吗？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/arts/checkexi'),
				data: {exhid : exhids,fromstate:2, tostate:0},
				success: function(data){
					if(data.success == '0'){
						$('#exhDG').datagrid('reload');
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
function totrou(){
	var rows={};
	rows = $("#exhDG").datagrid("getSelections");
	if (rows == "" || rows == null) {
		$.messager.alert('提示', '操作失败,没有选择要操作的记录');
		return;
	}
	var exhids = _split = "";//id1,id2,id3
	for (var i = 0; i<rows.length; i++){
		exhids += _split+rows[i].exhid;
		_split = ",";
	}
	//alert(JSON.stringify(exhids));
	$.messager.confirm('确认对话框', '您确认将所选择的进行发布吗？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/arts/checkexi'),
				data: {exhid : exhids,fromstate:3, tostate:2},
				success: function(data){
					if(data.success == '0'){
						$('#exhDG').datagrid('reload');
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
	 var id = row.exhid;
     $.messager.confirm('确认对话框', '确定要通过审核？', function(r) {
         if (r) {
        	 $.ajax({
        		type: "POST",
         		url : getFullUrl("./admin/arts/checkexhib"),
         		data : {exhid:id, exhstate:2},
         		success:function(data){
         			if (data=="success"){
                         $.messager.alert("提示","操作成功");
                         $("#exhDG").datagrid("reload");
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
 	 var id = row.exhid;
 	 if (row.exhstate==2) {
      $.messager.confirm('确认对话框', '确定要取消审核？', function(r) {
          if (r) {
         	 $.ajax({
         		type: "POST",
          		url : getFullUrl("./admin/arts/checkexhib"),
          		data : {exhid:id, exhstate:0},
          		success:function(data){
          			if (data=="success"){
                          $.messager.alert("提示","操作成功");
                          $("#exhDG").datagrid("reload");
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
  	 var id = row.exhid;
  	 if (row.exhstate==2) {
       $.messager.confirm('确认对话框', '确定要发布？', function(r) {
           if (r) {
          	 $.ajax({
          		type: "POST",
           		url : getFullUrl("./admin/arts/checkexhib"),
           		data : {exhid:id, exhstate:3},
           		success:function(data){
           			if (data=="success"){
                           $.messager.alert("提示","操作成功");
                           $("#exhDG").datagrid("reload");
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
   	 var id = row.exhid;
   	 //if (row.exhstate==3 && row.exhghp==0) {
        $.messager.confirm('确认对话框', '确定要取消发布？', function(r) {
            if (r) {
           	 $.ajax({
           		type: "POST",
            		url : getFullUrl("./admin/arts/checkexhib"),
            		data : {exhid:id,exhstate:2},
            		success:function(data){
            			if (data=="success"){
                            $.messager.alert("提示","操作成功");
                            $("#exhDG").datagrid("reload");
                        }else{
                        	 $.messager.alert("提示", "操作失败！");
                        }
            		}
            	})
            }
        })
   	 //}else {
   		// $.messager.alert("提示", "上首页标识为否才能取消发布！");
	//}
   }
/**
 * 查看艺术团信息
 */
 function seeinfo(index){
    var row = WHdg.getRowData(index);
	//清空表单的值
	$('#ff').form('clear');
	row.exhstime = datetimeFMT(row.exhstime);
	row.exhetime = datetimeFMT(row.exhetime);
	
	$('#ff').form('load', row);
	editWinform.setWinTitle("查看艺术展页面");
	$('#exhpic_up_img').attr('src', getFullUrl(row.exhpic)).parents('.row').show();
	
	$('#ff').find("input").attr("readonly","true");
	$('#exharttyp').combotree('readonly',true);
	$('#exhstime').datetimebox('readonly',true);
	$('#exhetime').datetimebox('readonly',true);
	$('#exhidx').numberspinner('readonly',true);
	$('#exhdesc').textbox('readonly',true);
	
	editWinform.openWin();
	var url = getFullUrl('./admin/arts/upexhib');
	editWinform.getWinFooter().find("a:eq(0)").hide();
		
	}
/**
 * 编辑艺术展信息
 */
 function upexh(index){
    var row = WHdg.getRowData(index);
	if (row){
		//清空表单的值
		$('#ff').form('clear');
		row.exhstime = datetimeFMT(row.exhstime);
		row.exhetime = datetimeFMT(row.exhetime);
		editWinform.setWinTitle("编辑艺术展页面");
		$('#exhpic_up_img').attr('src', getFullUrl(row.exhpic)).parents('.row').show();
		$('#ff').find("input").attr("readonly",false);
		$('#exharttyp').combotree('readonly',false);
		$('#exhstime').datetimebox('readonly',false);
		$('#exhetime').datetimebox('readonly',false);
		$('#exhidx').numberspinner('readonly',false);
		$('#exhdesc').textbox('readonly',false);
		editWinform.getWinFooter().find("a:eq(0)").show();
		var url = getFullUrl('./admin/arts/upexhib');
		editWinform.openWin();
		//初始
		$('#ff').form({
			novalidate: true,
			url : url,
			onSubmit: function(param){    
			    param.exhid = row.exhid;    
			    //param.exhpic = row.exhpic;
			    param.exhghp = row.exhghp;
			    param.exhstate = row.exhstate; 
			    param.exhidx = 1;
		        //得到当前的输入值
				 var a = $('#exhstime').datetimebox('getValue');
				 var b = $('#exhetime').datetimebox('getValue');
				 //转换时间格式
				 var d_a = new Date(a); 
				 var d_b = new Date(b);
				 //时间比较
				 if(d_a >= d_b){
					 $.messager.alert("提示", "时间设置错误,开始时间必须小于结束时间");
					 return false;
				 }
		         return $(this).form('enableValidation').form('validate');
			 },    
			success : function(data) {
				data = eval('('+data+')');
				if(data && data.success == '0'){
					editWinform.closeWin();
					$.messager.alert("提示", "操作成功");
					$('#exhDG').datagrid('reload');
				 }else{
					 $.messager.alert('提示', '操作失败。原因：'+data.errmsg, 'error');
				  }
				
			}
		});
		
		$('#ff').form('load', row);
		
		editWinform.setFoolterBut({
			onClick : function() {
				$('#ff').form('submit');
			}
		});
	} 
}
/**
 * 添加艺术团队信息
 */
function addexh(){
	editWinform.setWinTitle("添加艺术展信息页面");
	editWinform.openWin();
	
	//设置默认值 
	$('#exhidx').numberspinner('setValue', '1');
    var a =	$('#exhstime').datetimebox('getValue');
	var b = $('#exhetime').datetimebox('getValue');
	
	//日期比较(yyyy-mm-dd)
	$('#ff').find("input").attr("readonly",false);
	$('#exharttyp').combotree('readonly',false);
	$('#exhstime').datetimebox('readonly',false);
	$('#exhetime').datetimebox('readonly',false);
	$('#exhidx').numberspinner('readonly',false);
	$('#exhdesc').textbox('readonly',false);
	
	editWinform.getWinFooter().find("a:eq(0)").show();
	//$('#exhstate').val(row.exhstate);
	$('#exhpic_up_img').removeAttr('src').parents('.row').hide();
	//初始
	$('#ff').form({
		novalidate: true,
		url : getFullUrl('/admin/arts/addexhib'),
		onSubmit: function(param){
			 param.exhghp = '0';
			 param.exhstate = '0';
			 param.exhidx = 1;
			 //得到当前的输入值
			 var a = $('#exhstime').datetimebox('getValue');
			 var b = $('#exhetime').datetimebox('getValue');
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
		 success : function(data) {
 			 data = eval('('+data+')');
			 if(data && data.success == '0'){
				 editWinform.closeWin();
				 $.messager.alert("提示", "操作成功");
				 $('#exhDG').datagrid('reload');
			 }else{
				 $.messager.alert('提示', '操作失败。原因：'+data.errmsg, 'error');
			 }
		}
	});
	//清空表单的值
	$('#ff').form('clear');
	
	//提交表单
	editWinform.setFoolterBut({
		onClick : function() {
			$('#ff').form('submit');
		}
	});
}
 
/**
 * 根据主键id删除
 */
 function deltroupe(index){
	var row = WHdg.getRowData(index);
	var id = row.exhid;
	var pic = row.exhpic;
	$.messager.confirm('确认对话框', '您确认要删除吗？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/arts/delexhib'),
				data: {exhid : id, exhpic:pic},
				success: function(data){
					if(data && data.success == '0'){
						$.messager.alert("提示", "操作成功");
						$('#exhDG').datagrid('reload');
					 }else{
						 $.messager.alert('提示', '操作失败。原因：'+data.errmsg, 'error');
					 }
					
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
		title: '艺术展览管理',			
		url:getFullUrl('/admin/arts/selexhib'),
		sortName: 'exhid',
		sortOrder: 'desc',
		rownumbers:true,
		singleSelect:false,
		//fitColumns: true,
		columns:[[
			{field:'ck', checkbox:true},
			{field:'exhid', width:120, title:'艺术展标识',sortable:true},    
            {field:'exharttyp', width:60,title:'艺术类型', formatter: arttypFMT},
			{field:'exhtitle', width:180, title:'艺术展标题',sortable:true},
			{field:'exhtype', width:100,title:'艺术展类型',sortable:true},
			{field:'exhpic', width:100,title:'艺术展图片', formatter:imgFMT},
			{field:'exhstime', width:130,title:'开始时间',sortable:true,formatter :datetimeFMT},
			{field:'exhetime', width:130,title:'结束时间',sortable:true,formatter :datetimeFMT},
			{field:'exhidx', width:50,title:'排序',sortable:true},
			{field:'exhstate', width:60,title:'状态',sortable:true,formatter : traStateFMT},
			{field:'opt', title:'操作', formatter: WHdg.optFMT, optDivId:'exhOPT'}
		]]  
	};
	//初始表格
	WHdg.init('exhDG', 'exhTB',  options);
	editWinform.init();
	
	//上传图片处理
	$('#btn_pic1').on('click', function(e){
		e.preventDefault();
		openImgCut('art', 300, 190, 'exhpic');
	});
});
</script>
</head>
<body class="easyui-layout">
		<div data-options="region:'center',title:'',iconCls:'icon-ok'">
			<table id="exhDG"></table>
			
			<div id="exhTB" class="grid-control-group">
			     <div>
			     <shiro:hasPermission name="${resourceid}:add">
			        <a class="easyui-linkbutton" iconCls="icon-add" plain="false" onclick="addexh();">添加</a>
			     </shiro:hasPermission>  
			     <shiro:hasPermission name="${resourceid}:checkon">
					<a href="javascript:void(0)" class="easyui-linkbutton search" plain="false" onclick="doalltrou();">批量审核</a>
				 </shiro:hasPermission>
				 <shiro:hasPermission name="${resourceid}:checkoff">
					<a href="javascript:void(0)" class="easyui-linkbutton search" plain="false" onclick="dotrou();" >批量取消审核</a>
				 </shiro:hasPermission>
				 <shiro:hasPermission name="${resourceid}:publish">
					<a href="javascript:void(0)" class="easyui-linkbutton search" plain="false" onclick="toalltrou();" >批量发布</a>
				 </shiro:hasPermission>
				 <shiro:hasPermission name="${resourceid}:publishoff">
					<a href="javascript:void(0)" class="easyui-linkbutton search" plain="false" onclick="totrou();" >批量取消发布</a>
				 </shiro:hasPermission>	
			     </div>
				<div  style="padding-top: 5px">
				            艺术类型: 
				    <input class="easyui-combobox" name="exharttyp" data-options="valueField:'typid',textField:'typname',url:'${basePath}/comm/whtyp?type=0'"/>
					艺术展标题:
					<input class="easyui-textbox" name="exhtitle" data-options="validType:'length[1,30]'"/>
					状态:
					 <select class="easyui-combobox radio" name="exhstate">
					 				<option value="">全部</option>
			    					<option value="0">未审核</option>
			    					<option value="2">已审核</option>
			    					<option value="3">已发布</option>
					 </select>
					<a href="#" class="easyui-linkbutton search" iconCls="icon-search">查询</a>
				</div>
			</div>
			<!-- 操作按钮 -->
			<div id="exhOPT" class="none" style="display:none">
			    <shiro:hasPermission name="${resourceid}:view"><a href="javascript:void(0)" validKey="exhstate" validVal="0,1,2,3" method="seeinfo">查看</a></shiro:hasPermission>
			    <shiro:hasPermission name="${resourceid}:edit"><a href="javascript:void(0)" validKey="exhstate" validVal="0,1" method="upexh">编辑</a></shiro:hasPermission>
			    <shiro:hasPermission name="${resourceid}:del"><a href="javascript:void(0)" validKey="exhstate" validVal="0,1" method="deltroupe">删除</a></shiro:hasPermission>
			    <shiro:hasPermission name="${resourceid}:checkon"><a href="javascript:void(0)" validKey="exhstate" validVal="0,1" method="checkTrou">审核</a></shiro:hasPermission>
			    <shiro:hasPermission name="${resourceid}:checkoff"><a href="javascript:void(0)" validKey="exhstate" validVal="2" method="uncheckTrou">取消审核</a></shiro:hasPermission>
			    <shiro:hasPermission name="${resourceid}:publish"><a href="javascript:void(0)" validKey="exhstate" validVal="2" method="ucheckTrou">发布</a></shiro:hasPermission>
			    <shiro:hasPermission name="${resourceid}:publishoff"><a href="javascript:void(0)" validKey="exhstate" validVal="3" method="ncheckTrou">取消发布</a></shiro:hasPermission>
			    <%-- <shiro:hasPermission name="${resourceid}:order"><a href="javascript:void(0)" validKey="exhstate" validVal="3" method="goindex">排序</a> </shiro:hasPermission> --%>
			</div>
		</div>
		 <!-- div弹出层 --> 
		 <!-- 编辑DIV -->
	<div id="edit_win" class="none" data-options="maximized:true"style="display:none" >
		<form method="post" id="ff" enctype="multipart/form-data">
			<div class="main">
			     <div class="row">
					<div><label>艺术类型:</label></div>
					<div>
						<input class="easyui-combotree" name="exharttyp" id="exharttyp" style="width:90%;height:35px"
						 data-options="required:true, valueField:'typid',textField:'typname',url:'${basePath}/comm/whtyp?type=0'">
						</input>
					</div>
				</div>
				<div class="row">
					<div><label>艺术展标题:</label></div>
					<div>
						<input class="easyui-textbox" name="exhtitle" style="width:90%;height:35px" data-options="required:true, validType:'length[0,60]'"/>
					</div>
				</div>
				<div class="row">
					<div><label>艺术展类型:</label></div>
					<div>
						<input class="easyui-textbox" name="exhtype" style="width:90%;height:35px" data-options="required:true, validType:'length[0,30]'"/>
					</div>
				</div>
				<div class="row">
					<div><label>艺术展图片:</label></div>
					<div>
						<!-- 
						<input class="easyui-textbox" id="exhpic" name="exhpic" style="width:80%;height:35px" data-options="required:true, validType:'length[0,128]'" />
						 -->
						<a id="btn_pic1" class="easyui-linkbutton" style="height:32px;">选择图片</a>
						<input type="hidden" name="exhpic" id="exhpic"/>
						<!-- <input class="easyui-filebox" id="exhpic_up" name="exhpic_up" style="width:90%;height:35px" data-options="required: true, validType:'isIMG[\'exhpic_up\', 1024]', buttonText:'选择封面', accept:'image/jpeg, image/png',prompt:'建议:300*190'"/> -->
					</div>
				</div>
				<div class="row">
					<div><label></label></div>
					<div>
						<img id="exhpic_up_img" style="height:150px;"/>
					</div>
				</div>
				<div class="row">
					<div><label>开始时间:</label></div>
					<div>
						<input id="exhstime" name="exhstime" type="text" style="width:90%;height:32px;" class="easyui-datetimebox" data-options="editable:false, required:true" />
					</div>
				</div>
				<div class="row">
					<div><label>结束时间:</label></div>
					<div>
						<input id="exhetime" name="exhetime" type="text" style="width:90%;height:32px;" class="easyui-datetimebox" data-options="editable:false, required:true" />
					</div>
				</div>
				<!-- <div class="row">
					<div><label>上首页排序:</label></div>
					<div>
						<div>
						<input class="easyui-numberspinner" name="exhidx" id="exhidx" value="" style="width:90%;height:35px"
						data-options="increment:1, required:true,min:1,max:999"/>
					    </div>
				    </div>
			   </div> -->
				<div class="row">
					<div><label>艺术团简介:</label></div>
					<div>
						 <input class="easyui-textbox" name=exhdesc id="exhdesc" style="width:90%; height:120px" data-options="validType:'length[0,400]',multiline:true"/>
					</div>
				</div>
		  </div>
	  </form>
   </div>

</body>
</html>

