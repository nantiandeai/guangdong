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
var pic= '';
 function showState(val, rowData, index) {
	return rowData.cfgstate == '1' ? '启用' : '停用';
 }
 
 function typelist(val, rowData, index){
	 return WHTYP.sys_Whtyp('', val);  
 }
 
 /**
 *实体类型显示
 */
 function cfgtype(val, rowData, index){
	 return shitiFMT(val, rowData, index);
 }
 
 /**
 *实体分类显示
 */
 function cfgclass(val, rowData, index){
	 if(rowData.cfgenttype == '2016102800000001'){
		 return WHTYP.sys_Whtyp('zxcolumn', val);
	 }
	 return WHTYP.sys_Whtyp(rowData.cfgenttype, val);
 }
 
 /**
 *根据排序提示图片限制
 */
 function changshow(newVal, oldVal){
		//延时
		window.setTimeout(function(){
			//请求数据
		var	array = pic.split(",");
			if (array.length > newVal) {
				for (var i = 1; i < array.length; i++) {
					if (i == newVal) {
						
						$('#showid').textbox('setValue',array[i-1]);
					}
				}
			}else {
				$('#showid').textbox('setValue',array[array.length-1]);
			}
			
		},100);
	 
 }
 
 
 /**选择事件 读取数据 */
 function changewhcfg(newVal, oldVal){
	// 获取数据表格对象
	var g = $('#cfgshowid').combogrid('grid');
	
	// 获取选择的行
	var r = g.datagrid('getSelected');
	if (!r) {
		return;
	}
	
	//判断活动还是培训
	var cfgenttype = $('#cfgenttype').combotree('getValue');
	if (cfgenttype =='2016101400000051') {
		//培训
		r.trasdate = datetimeFMT(r.trasdate);
		$('#cfgshowtitle').textbox('setValue',r.trashorttitle);
		
		//图片
		$('#cfgshowpic_up').filebox('clear');
		$('#cfgshowpic').val(r.trapic);
		$('#cfgshowpic_up_img').attr('src', getFullUrl(r.trapic)).parents('.row').show();
		
		$('#cfgshowtime').datetimebox('setValue',r.trasdate);
		$('#cfgshowintroduce').textbox('setValue',r.traintroduce);
		$('#cfgshowlink').textbox('setValue','/gypx/detail/'+r.traid);
	}else if (cfgenttype =='2016101400000052') {
		//活动
		if (r.actvsdate) {
			r.actvsdate = datetimeFMT(r.actvsdate);
			$('#cfgshowtime').datetimebox('setValue', r.actvsdate);
		}else{
			$('#cfgshowtitle').textbox('clear');
		}
		var url = getFullUrl('/admin/adve/selecthd?actvid=')+r.actvid;
		$.ajax({  
			type : "POST",  
			url : url,   
			success : function(data) {
				if (data) {
					data.actvitmsdate = datetimeFMT(data.actvitmsdate);
					$('#cfgshowtime').datetimebox('setValue', data.actvitmsdate);
				}
			} 
		})  
		
		$('#cfgshowtitle').textbox('setValue',r.actvshorttitle);
		
		//图片
		$('#cfgshowpic_up').filebox('clear');
		$('#cfgshowpic').val( r.actvbigpic);
		$('#cfgshowpic_up_img').attr('src', getFullUrl(r.actvbigpic)).parents('.row').show();
		
		$('#cfgshowintroduce').textbox('setValue',r.actvintroduce);
		$('#cfgshowlink').textbox('setValue','/event/detail/'+r.actvid);
	}else if (cfgenttype == '2016103100000001'){
		//品牌
		$('#cfgshowtitle').textbox('setValue', r.bratitle);
		
		r.brasdate = datetimeFMT(r.brasdate);
		$('#cfgshowtime').datetimebox('setValue', r.brasdate);
		//图片
		$('#cfgshowpic_up').filebox('clear');
		$('#cfgshowpic').val( r.brapic);
		$('#cfgshowpic_up_img').attr('src', getFullUrl(r.brapic)).parents('.row').show();
		
		$('#cfgshowintroduce').textbox('setValue',r.braintroduce);
		$('#cfgshowlink').textbox('setValue','/event/pplist?braid='+r.braid);
		
	}else if (cfgenttype == '2016102800000001'){
		//资讯
		var _clnfcrttime = datetimeFMT(r.clnfcrttime);
		$('#cfgshowtitle').textbox('setValue', r.clnftltle);
		
		//图片
		$('#cfgshowpic_up').filebox('clear');
		$('#cfgshowpic').val( r.clnfpic);
		$('#cfgshowpic_up_img').attr('src', getFullUrl(r.clnfpic)).parents('.row').show();
		
		$('#cfgshowtime').datetimebox('setValue', _clnfcrttime);
		$('#cfgshowintroduce').textbox('setValue', r.clnfintroduce);
		$('#cfgshowlink').textbox('setValue','/xuanchuan/xqinfo?clnfid='+r.clnfid);
		
	}else if (cfgenttype == "2016112900000007") {
		//非遗名录
		var _mlprotime = datetimeFMT(r.mlprotime);
		$('#cfgshowtitle').textbox('setValue', r.mlproshortitel);
		//图片
		$('#cfgshowpic_up').filebox('clear');
		$('#cfgshowpic').val( r.mlprosmpic);
		$('#cfgshowpic_up_img').attr('src', getFullUrl(r.mlprosmpic)).parents('.row').show();
		
		$('#cfgshowtime').datetimebox('setValue', _mlprotime);
		
		$('#cfgshowlink').textbox('setValue','./admin/feiyi/loadMinglu?mlproid='+r.mlproid);
	}else if (cfgenttype == '2016112900000015') {
		//项目示范
		var _zyfcxmopttime = datetimeFMT(r.zyfcxmopttime);
		$('#cfgshowtitle').textbox('setValue', r.zyfcxmshorttitle);
		//图片
		$('#cfgshowpic_up').filebox('clear');
		$('#cfgshowpic').val( r.zyfcxmpic);
		$('#cfgshowpic_up_img').attr('src', getFullUrl(r.zyfcxmpic)).parents('.row').show();
		
		$('#cfgshowtime').datetimebox('setValue', _zyfcxmopttime);
		
		$('#cfgshowlink').textbox('setValue','agdzyfw/xiangmuinfo?zyfcxmid='+r.zyfcxmid);
	}else if (cfgenttype == '2016112800000004') {
		var _teacheropttime = datetimeFMT(r.teacheropttime);
		$('#cfgshowtitle').textbox('setValue', r.teachername);
		//图片
		$('#cfgshowpic_up').filebox('clear');
		$('#cfgshowpic').val( r.teacherpic);
		$('#cfgshowpic_up_img').attr('src', getFullUrl(r.teacherpic)).parents('.row').show();
		
		$('#cfgshowtime').datetimebox('setValue', _teacheropttime);
		$('#cfgshowintroduce').textbox('setValue', r.teacherintroduce);
		$('#cfgshowlink').textbox('setValue','dgszwhg/agdpxyz/teacherinfo?teacherid='+r.teacherid);
	}else if (cfgenttype == '2016112300000002') {
		//数字资源
		r.drscopttime = datetimeFMT(r.drscopttime);
		$('#cfgshowtitle').textbox('setValue',r.drsctitle);
		
		//图片
		$('#cfgshowpic_up').filebox('clear');
		$('#cfgshowpic').val(r.drscpic);
		$('#cfgshowpic_up_img').attr('src', getFullUrl(r.drscpic)).parents('.row').show();
		
		$('#cfgshowtime').datetimebox('setValue',r.drscopttime);
		$('#cfgshowintroduce').textbox('setValue',r.drscintro);
		$('#cfgshowlink').textbox('setValue','/gypx/detail/'+r.drscid);
	}
	
 }
 /**
  * 查看信息
  */
 function seeinfo(index){
	var row = WHdg.getRowData(index);
	row.cfgshowtime = datetimeFMT(row.cfgshowtime);
	var pic=row.cfgshowpic;
	if (row) {
		//清空表单的值
		$('#ff').form('clear');
		
		editWinform.setWinTitle("查看信息");
		editWinform.openWin();
		editWinform.getWinFooter().find("a:eq(0)").hide();
		
		//禁用
		$('#ff').find("input").attr("readonly",true);
		$('#cfgshowpic_up').filebox('disable');
		$('#cfgpagetype').combotree('readonly',true);
		$('#cfgenttype').combotree('readonly',true);
		$('#cfgentclazz').combotree('readonly',true);
		$('#cfgshowtime').datetimebox('readonly',true);
		$('#cfgshowidx').numberspinner('readonly',true);
		$('#cfgshowintroduce').textbox('readonly',true);
		
		//清理原来数据
		var _row = $.extend({}, row);
		_row.cfgentclazz = '';
		_row.cfgshowid = '';
		
		//重新设值加载数据
		$('#ff').form('load', _row);
		window.setTimeout(function(){
			$('#cfgentclazz').combotree('setValue', row.cfgentclazz);
			window.setTimeout(function(){
				$('#cfgshowid').combogrid('setValue', row.cfgshowid);
				$("#cfgshowid").combogrid("readonly", true);
				
				//图片
				if (pic && pic != '' ){
		        	$('#cfgshowpic_up_img').attr('src', getFullUrl(pic)).parents('.row').show();
		        }else{
		        	$('#cfgshowpic_up_img').parents('.row').hide();
		        }
				
				$('#cfgentclazz').combotree('setValue', row.cfgentclazz);
			},100);
		}, 100);
		
		
	}
		
		
 }
  
  
/**
 * 添加广告信息
 */
  function addcfgs(){
	//设置编辑表单标题并打开
	editWinform.setWinTitle("添加页面配置列表");
	editWinform.openWin();
	$('#ff').form('clear');
	//清空表单的值
	$('#cfgshowpic_up_img').removeAttr('src').parents('.row').hide();
	$('#cfgshowidx').numberspinner('setValue', '1');
	$('#cfgpagetype').combotree('setValue', $('#pageTree').tree('getSelected').typid);
	
	
	$('#cfgpagetype').combotree('readonly');
	
	//启用编辑状态
	$('#ff').find("input").attr("readonly",false);
	$('#cfgshowpic_up').filebox('clear');
	$('#cfgshowpic_up').filebox('enable');
	$('#cfgenttype').combotree('readonly',false);
	$('#cfgentclazz').combotree('readonly',false);
	$("#cfgshowid").combogrid("readonly", false);
	$('#cfgshowtime').datetimebox('readonly',false);
	$('#cfgshowidx').numberspinner('readonly',false);
	$('#cfgshowintroduce').textbox('readonly',false);
	$('#showid').textbox('readonly',true);
	//显示提交按钮
	editWinform.getWinFooter().find("a:eq(0)").show();
	$('#ff').form({
		novalidate: true,
		url : getFullUrl('/admin/adve/addcgflist'),
		onSubmit: function(param){
			param.cfgstate = 0;
            return $(this).form('enableValidation').form('validate');
		},
		success : function(data) {
		   data = eval('('+data+')');
		  //alert(data.success);
			if(data && data.success == '0'){
				editWinform.closeWin();
            	$('#cfgsDG').datagrid('reload');
				$.messager.alert("提示", "操作成功");
            }else{
                $.messager.alert("失败了", "操作不成功");
            }
		}
	});
	//提交表单
	editWinform.setFoolterBut({
		onClick : function() {
			$('#ff').form('submit');
		}
	});
 }
 
/**
 * 编辑广告信息
 */
 function upwhcfg(index){
	var row = WHdg.getRowData(index);
	
	row.cfgshowtime = datetimeFMT(row.cfgshowtime);
	var pic=row.cfgshowpic;
	if (row) {
		
		//清空表单的值
		editWinform.openWin();
		editWinform.setWinTitle("编辑页面配置列表");
		
		//启用
		$('#ff').find("input").attr("readonly",false);
		$('#cfgshowpic_up').filebox('enable');
		$('#cfgenttype').combotree('readonly',false);
		$('#cfgentclazz').combotree('readonly',false);
		$('#cfgshowtime').datetimebox('readonly',false);
		$('#cfgshowidx').numberspinner('readonly',false);
		$('#cfgshowintroduce').textbox('readonly',false);
		$('#showid').textbox('readonly',true);
		editWinform.getWinFooter().find("a:eq(0)").show();
		
		//
		$('#ff').form('clear');
		
		
		//1.cfgenttype
		$('#cfgenttype').combotree('setValue', row.cfgenttype);
		
		//2.cfgentclazz
		window.setTimeout(function(){
			$('#cfgentclazz').combotree('setValue', row.cfgentclazz);
 			window.setTimeout(function(){
			 	$('#cfgshowid').combogrid('setValue', row.cfgshowid);
				$('#cfgshowid').combogrid('readonly', false);
				
				//图片
				if (pic){
		        	$('#cfgshowpic_up_img').attr('src', getFullUrl(pic)).parents('.row').show();
		        }else{
		        	$('#cfgshowpic_up_img').removeAttr('src');
		        	$('#cfgshowpic_up_img').parents('.row').hide();
		        }
				$('#cfgentclazz').combotree('setValue', row.cfgentclazz);
				
				//
				var _row2 = $.extend({}, row);
				delete _row2.cfgenttype;
				delete _row2.cfgentclazz;
				delete _row2.cfgshowid;
				$('#ff').form('load', _row2);
				
				//
				$('#cfgshowpic_up').filebox({required: false});
			}, 200);
		}, 200);
		
		//
		$('#ff').form({
			novalidate: true,
			url : getFullUrl('/admin/adve/upcgf'),
			onSubmit: function(param){
				param.cfgid = row.cfgid;
				//param.cfgshowpic = pic;
                return $(this).form('enableValidation').form('validate');
			},
			success : function(data) {
				data = eval('('+data+')');
				if(data && data.success == '0'){
					editWinform.closeWin();
                	$('#cfgsDG').datagrid('reload');
					$.messager.alert("提示", "操作成功");
                }else{
                    $.messager.alert("失败了", "操作不成功");
                }
			}
		});
		
		//提交表单
		editWinform.setFoolterBut({
			onClick : function() {
				$('#ff').form('submit');
			}
		});
	}
}
  
/**
 * 删除列表信息
 */
 function delcfgs(index){
	var row = WHdg.getRowData(index);
	var id = row.cfgid;
	var pic = row.cfgshowpic;
	$.messager.confirm('确认对话框', '您确认要删除吗？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/adve/delcgf'),
				data: {cfgid : id, cfgshowpic:pic },
				success: function(msg){
					$.messager.alert("提示", "操作成功");
					$('#cfgsDG').datagrid('reload');
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
	 var id = row.cfgid;
     $.messager.confirm('确认对话框', '确定要更改状态？', function(r) {
         if (r) {
        	 $.ajax({
        		type: "POST",
         		url : getFullUrl("./admin/adve/docfg"),
         		data : {cfgid:id, cfgstate:1},
         		success:function(data){
         			if (data=="success"){
                         $.messager.alert("提示","操作成功");
                         $("#cfgsDG").datagrid("reload");
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
	 var id = row.cfgid;
     $.messager.confirm('确认对话框', '确定要更改状态？', function(r) {
         if (r) {
        	 $.ajax({
        		type: "POST",
         		url : getFullUrl("./admin/adve/docfg"),
         		data : {cfgid:id, cfgstate:0},
         		success:function(data){
         			if (data=="success"){
                         $.messager.alert("提示","操作成功");
                         $("#cfgsDG").datagrid("reload");
                     }else{
                         $.messager.alert("失败了", "操作失败！");
                     }
         		}
         	})
         }
     })
 }
 
/** 选择实体类型后的处理 */
function changeAtrtyp(newVal, oldVal){
	 $('#cfgentclazz').combotree('clear');
	 var type = '';
	 if(newVal == '2016101400000051'){
		 //培训
		 type = '2';
		 $('#cfgentclazz').combotree('enable');
		 $('#cfgentclazz').combotree('loadData', WHTYP.sys_Whtyp_Data(type));
		 combogrid_px();
	 }else if(newVal == '2016101400000052'){
		 //活动
		 type = '1';
		 $('#cfgentclazz').combotree('enable');
		 $('#cfgentclazz').combotree('loadData', WHTYP.sys_Whtyp_Data(type));
		 combogrid_hd();
	 }else if(newVal == '2016103100000001'){
		 //精品活动-暂时没有分类
		 type = '';
		 $('#cfgentclazz').combotree('disable');
		 combogrid_ppHD();
		 $('#cfgshowid').combogrid({
			url: getFullUrl('/admin/adve/srchEntList'),
			queryParams: {type:"2"}
		 });
	 }else if(newVal == '2016102800000001'){
		 //馆务资讯
		 $('#cfgentclazz').combotree('enable');
		 $('#cfgentclazz').combotree('loadData', WHTYP.sys_Whtyp_Data('zxcolumn'));
		 combogrid_zx();
	 }else if (newVal == '2016112900000007') {
		 //非遗名录
		 type = '';
		 $('#cfgentclazz').combotree('disable');
		 combogrid_ml();
		 $('#cfgshowid').combogrid({
				url: getFullUrl('./admin/feiyi/loadMinglu')
		 });
	}else if (newVal == '2016112900000015') {
		 //项目展示
		 type = '';
		 $('#cfgentclazz').combotree('disable');
		 combogrid_xm();
		 $('#cfgshowid').combogrid({
				url: getFullUrl('/admin/volun/findpro')
		 });
	}else if (newVal == '2016112800000004') {
		 //项目展示
		 type = '';
		 $('#cfgentclazz').combotree('disable');
		 combogrid_ls();
		 $('#cfgshowid').combogrid({
				url: getFullUrl('/admin/tea/findTeacher')
		 });

	}else if (newVal == '2016120500000001') {
		 //广告展示
		 type = '';
		 $('#cfgentclazz').combotree('disable');
		 $('#cfgshowid').combogrid('disable');
		 
	}else if (newVal == '2016112300000002') {
		 //数字资源
		 type = '';
		 $('#cfgentclazz').combotree('disable');
		 combogrid_szzy();
		 $('#cfgshowid').combogrid({
				url: getFullUrl('/admin/drsc/srchPagging')
		 });
	}
}

/** 根据选择类型显示查询信息 */
function changeClazz(newVal, oldVal){
	var cfgenttype = $('#cfgenttype').combotree('getValue');
	
	if(cfgenttype == '2016101400000051'){
		 //培训
		$('#cfgshowid').combogrid({
			url: getFullUrl('/admin/adve/srchEntList'),
			queryParams: {type: "0", clazz: newVal}
		}); 
	 }else if(cfgenttype == '2016101400000052'){
		 //活动
 		 $('#cfgshowid').combogrid({
			url: getFullUrl('/admin/adve/srchEntList'),
			queryParams: {type:"1", clazz: newVal}
		 });
	 }else if(cfgenttype == '2016103100000001'){
		//精品活动-暂时没有分类
 		 /* $('#cfgshowid').combogrid({
			url: getFullUrl('/admin/activity/selectAct'),
			queryParams: {actvstate:"3", actvtype:newVal}
		 }); */
	 }else if(cfgenttype == '2016102800000001'){
		//馆务资讯
 		$('#cfgshowid').combogrid({
			url: getFullUrl('/admin/adve/srchEntList'),
			queryParams: {type:"3", clazz: newVal}
		 });
	 }else if (cfgenttype == '2016112900000007') {
		 //非遗名录
	
	}else if (cfgenttype == '2016112900000015') {
		//项目示范
	}else if (cfgenttype == '2016112800000004') {
		//培训老师
	}else if (cfgenttype == '2016112300000002') {
		//数字资源
		
	}
}

//培训
function combogrid_px(){
	var _div = $('#cfgshowid').parent('div');
	$('#cfgshowid').combogrid('destroy');
	_div.append($('<input id="cfgshowid" name="cfgshowid" style="width:100%; height:35px;" /> '));
	
	var options = {
	    mode: 'remote',
		idField: 'traid',    
	    textField: 'tratitle',
		queryParams: {trastate:"3"},
		rownumbers: true,
		fitColumns: true,
		singleSelect: true, 
		url: false,
		columns: [[
			{field:'tratitle',title:'课程标题',width:80, sortable:true},
			{field:'tratyp',title:'培训类型',width:80, sortable:true, formatter: tratypFMT},
			{field:'traarttyp',title:'艺术类型',width:80, sortable:true, formatter: arttypFMT},
			{field:'tralevel',title:'课程级别',width:80, sortable:true, formatter:tralevelFMT},
			{field:'trapic',title:'列表图',width:80, sortable:true, formatter:imgFMT},
			{field:'trasdate',title:'开始日期',width:200,formatter :datetimeFMT,sortable:true},
			{field:'trateacher',title:'授课老师名称',width:80},
	        {field:'trastate',title:'状态',width:80,formatter :traStateFMT}
		]],
		onChange: changewhcfg
	};
	
	 //初始化
	 var _width = $('#cfgenttype').width() - (1084-716);
	 $('#cfgshowid').css({width: _width+'px'});
	 $('#cfgshowid').combogrid(options); 
}

//活动
function combogrid_hd(){
	var _div = $('#cfgshowid').parent('div');
	$('#cfgshowid').combogrid('destroy');
	_div.append($('<input id="cfgshowid" name="cfgshowid" style="width:100%; height:35px;" /> '));
	
	var options = {
	    mode: 'remote',
		idField: 'actvid',    
	    textField: 'actvtitle',
		queryParams: {actvstate:"3"},
		rownumbers: true,
		fitColumns: true,
		singleSelect: true,
		url: false,
		columns: [[
			{field:'actvid',title:'活动标识',width:100},
			{field:'actvtype',title:'活动类型',width:100,formatter:acttypFMT},
			{field:'actvarttyp',title:'艺术类型', sortable:true, width:150,formatter:arttypFMT},
			{field:'actvshorttitle',title:'列表标题', sortable:true, width:150},
			{field:'actvtitle',title:'详情标题',width:60, sortable:true},
			{field:'actvbigpic',title:'活动小图', sortable:true,width:150, formatter:imgFMT},
			{field:'actvstate',title:'状态',width:60, sortable:true,formatter:traStateFMT}
		]],
		onChange: changewhcfg
	};
	
	//初始化
	 var _width = $('#cfgenttype').width() - (1084-716);
	 $('#cfgshowid').css({width: _width+'px'});
	 $('#cfgshowid').combogrid(options); 
}


/** 品牌活动 */
function combogrid_ppHD(){
	var _div = $('#cfgshowid').parent('div');
	$('#cfgshowid').combogrid('destroy');
	_div.append($('<input id="cfgshowid" name="cfgshowid" style="width:100%; height:35px;" /> '));
	
	var options = {
		idField: 'braid',    
	    textField: 'bratitle',
		rownumbers: true,
		fitColumns: true,
		singleSelect: true,
		url: false,
		columns: [[
			{field:'bratitle', title:'标题', width:200, sortable: true},
			{field:'brapic', title:'图片', width:250, formatter: imgFMT},
			{field:'brastate', title:'状态', width:150, formatter: traStateFMT}
		]],
		onChange: changewhcfg
	};
	
	//初始化
	var _width = $('#cfgenttype').width() - (1084-716);
	$('#cfgshowid').css({width: _width+'px'});
	$('#cfgshowid').combogrid(options); 
}

/** 馆务资讯 */ 
function combogrid_zx(){
	var _div = $('#cfgshowid').parent('div');
	$('#cfgshowid').combogrid('destroy');
	_div.append($('<input id="cfgshowid" name="cfgshowid" style="width:100%; height:35px;"/> '));
	
	var options = {
		idField: 'clnfid',    
	    textField: 'clnftltle',
		rownumbers: true,
		fitColumns: true,
		singleSelect: true,
		url: false,
		columns: [[
			{field:'clnftltle', title:'标题', width:250, sortable: true},
			{field:'clnfpic', title:'图片', width:200, formatter: imgFMT},
			{field:'clnfcrttime', title:'时间', width:150, sortable:true, formatter: datetimeFMT},
			{field:'clnfstata', title:'状态', width:150, formatter: traStateFMT}
		]],
		onChange: changewhcfg
	};
	
	//初始化
	var _width = $('#cfgenttype').width() - (1084-716);
	$('#cfgshowid').css({width: _width+'px'});
	$('#cfgshowid').combogrid(options); 
}
/** 非遗名录 */ 
function combogrid_ml(){
	var _div = $('#cfgshowid').parent('div');
	$('#cfgshowid').combogrid('destroy');
	_div.append($('<input id="cfgshowid" name="cfgshowid" style="width:100%; height:35px;"/> '));
	
	var options = {
		idField: 'mlproid',    
	    textField: 'mlproshortitel',
		rownumbers: true,
		fitColumns: true,
		singleSelect: true,
		//url: false,
		pagination: true,
		queryParams: {mlprostate:"3"},
		columns: [[
		{field:'mlproshortitel',title:'名录列表标题', width:150},
		{field:'mlprosmpic',title:'名录项目列表图', sortable:true,width:80,formatter:imgFMT},
		{field:'mlprotime',title:'名录项目修改时间',  width:90,formatter:datetimeFMT},
		{field:'mlprostate',title:'名录状态',width:50, sortable:true,formatter:traStateFMT},
		]],
		onChange: changewhcfg
	};
	
	//初始化
	var _width = $('#cfgenttype').width() - (1084-716);
	$('#cfgshowid').css({width: _width+'px'});
	$('#cfgshowid').combogrid(options); 
}

/** 项目展示 */ 
function combogrid_xm(){
	var _div = $('#cfgshowid').parent('div');
	$('#cfgshowid').combogrid('destroy');
	_div.append($('<input id="cfgshowid" name="cfgshowid" style="width:100%; height:35px;"/> '));
	var options = {
		idField: 'zyfcxmid',    
	    textField: 'zyfcxmshorttitle',
	    queryParams: {zyfcxmstate : "3"},
		rownumbers: true,
		fitColumns: true,
		singleSelect: true,
		pagination: true,
		columns: [[
		{field:'zyfcxmtitle',title:'详情标题'},
		{field:'zyfcxmshorttitle',title:'列表短标题', sortable:true},
		{field:'zyfcxmpic',title:'列表图片',formatter:imgFMT},  
        {field:'zyfcxmssdw',title:'实施单位'},
       	{field:'zyfcxmopttime',title:'修改状态时间',width:80,formatter:datetimeFMT}
		]],
		onChange: changewhcfg
	};
	//初始化
	var _width = $('#cfgenttype').width() - (1084-716);
	$('#cfgshowid').css({width: _width+'px'});
	$('#cfgshowid').combogrid(options); 
}

/** 培训老师 */ 
function combogrid_ls(){
	var _div = $('#cfgshowid').parent('div');
	$('#cfgshowid').combogrid('destroy');
	_div.append($('<input id="cfgshowid" name="cfgshowid" style="width:100%; height:35px;"/> '));
	var options = {
		idField: 'teacherid',    
	    textField: 'teachername',
	    queryParams: {teacherstate : "3"},
		rownumbers: true,
		fitColumns: true,
		singleSelect: true,
		pagination: true,
		columns: [[
		{field:'teachername',title:'老师名称',width:80, sortable:true},
			{field:'teacherpic',title:'老师图片',width:80, formatter:imgFMT},
			{field:'teachertype',title:'专长类型',width:80,formatter: arttypFMT},
			{field:'teacherarttyp',title:'艺术类型',width:80, formatter: arttypFMT},
			]],
		onChange: changewhcfg
	};
	//初始化
	var _width = $('#cfgenttype').width() - (1084-716);
	$('#cfgshowid').css({width: _width+'px'});
	$('#cfgshowid').combogrid(options); 
}

/** 数字资源 */ 
function combogrid_szzy(){
	var _div = $('#cfgshowid').parent('div');
	$('#cfgshowid').combogrid('destroy');
	_div.append($('<input id="cfgshowid" name="cfgshowid" style="width:100%; height:35px;"/> '));
	var options = {
		idField: 'drscid',    
	    textField: 'drsctitle',
	    queryParams: {drscstate : "3"},
		rownumbers: true,
		fitColumns: true,
		singleSelect: true,
		pagination: true,
		columns: [[
        {field:"drscarttyp", title: "艺术类型",width:80, formatter:zxtypeFMT},
        {field:"drsctyp", title: "资源分类",width:80, formatter:zxtypeFMT},
		{field:"drsctitle", title: "资源标题",width:80, sortable:true},
		{field:"drsccrttime", title: "创建时间",width:150, sortable:true, formatter: datetimeFMT},
		]],
		onChange: changewhcfg
	};
	//初始化
	var _width = $('#cfgenttype').width() - (1084-716);
	$('#cfgshowid').css({width: _width+'px'});
	$('#cfgshowid').combogrid(options); 
}


/** 选择类型后的处理 */
function cfgenttypeFilter(q, row){
	//培训 活动 品牌 资讯
	//2016102800000001 
	//2016103100000001
	return row.typid == '2016101400000051' || row.typid == '2016101400000052' || row.typid == '2016102800000001' || row.typid == '2016103100000001' ||row.typid == '2016112900000007' || row.typid == '2016112900000015' || row.typid =='2016112800000004'
			|| row.typid =='2016120500000001' || row.typid =='2016112300000002'}
 
var editWinform = new WhuiWinForm("#edit_win");
$(function(){
	//定义表格参数
	var options = {
		//title: '配置管理',			
		//url:getFullUrl('/admin/adve/seleadve'),
		sortName: 'cfgid',
		sortOrder: 'desc',
		fitColumns:true,
		columns:[[    
			//{field:'cfgid',title:'配置标识',align:'center',sortable:true},
			{field:'cfgpagetype',title:'页面类型',align:'center',formatter:typelist},
			{field:'cfgenttype', title:'实体类型', width: 100, formatter:cfgtype, sortable:true},
			{field:'cfgentclazz', title:'实体类型分类', width: 150, sortable:true, formatter: cfgclass},
			{field:'cfgshowtitle', title:'显示的标题', width: 250, sortable:true},
			{field:'cfgshowpic', title:'显示图片', width: 200, formatter:imgFMT},
			//{field:'cfgshowtime', title:'显示的时间', width: 150, sortable:true, formatter: datetimeFMT},
			//{field:'cfgshowlink',title:'连接地址',align:'center',sortable:true},
			{field:'cfgshowidx',title:'排序', width: 100,sortable:true},
			{field:'cfgstate',title:'状态', width: 100, sortable:true, formatter:showState},
			//{field:'cfgshowintroduce',title:'简介',align:'center'},
			{field:'opt', title:'操作', align:'center', width: 350, formatter: WHdg.optFMT, optDivId:'cfgOPT'}
		]]  
	};
	//初始表格
	editWinform.init();
	WHdg.init('cfgsDG', 'cfgTB',  options);
	//清除文件框的值
	$('#btn_pic').bind('click', function(){  
		$("#cfgshowpic_up").filebox('clear');
    });
	//得到类型id
	$('#pageTree').tree({    
	    //url: '${basePath}/comm/whtyp?type=12' ,
	    data: WHTYP.sys_Whtyp_Data('12'),
	    onLoadSuccess: function(node, data){
			var nodeList = $('#pageTree').tree('getRoots');
			if(nodeList.length > 0){
				$('#pageTree').tree('select', nodeList[0].target);
				/* $('#cfgsDG').datagrid({
					url: getFullUrl('/admin/adve/seletcgf'),
					queryParams: {'cfgpagetype': nodeList[0].typid}
				}); */
			}
	    },
	    onSelect: function(node){
	    	 pic = node.typpic;
	    	$('#cfgsDG').datagrid( {
				url: getFullUrl('/admin/adve/seletcgf'),
				queryParams: {'cfgpagetype': node.typid}
			});
	    },
	    onBeforeSelect: function(node){
	    	//如果是一级栏目下列id，不能操作资讯
	    	if(node.typid == "2016111400000001" || node.typid == "2016111400000003"){
	    		$('#cfgTB > div > a.easyui-linkbutton').linkbutton('disable');
	    		$('#cfgTB > div > input.easyui-combotree').combotree('disable');
	    	}else{
	    		$('#cfgTB > div > a.easyui-linkbutton').linkbutton('enable');
	    		$('#cfgTB > div > input.easyui-combotree').combotree('enable');
	    	}
	    }
	});
	
	//初始化
	$('#cfgshowid').combogrid({delay: 500, mode: 'remote',});
});
</script>
</head>


<body class="easyui-layout">
	
    <div data-options="region:'west',title:'页面',split:true" style="width:200px;">
    	<ul id="pageTree"></ul>  
    </div>   
    
    
    <div data-options="region:'center',title:'列表显示'" style="padding:5px;background:#eee;">
    	<div id="cfgsDG"></div>
    
    	<div id="cfgTB" style="height:auto" style="display:none">
		    <div>
			            类型:<input class="easyui-combotree" name="cfgenttype" data-options="valueField:'typid', textField:'typname', data:WHTYP.sys_Whtyp_Data('9')" />
				<a href="#" class="easyui-linkbutton search" iconCls="icon-search">查询</a>
				<shiro:hasPermission name="${resourceid}:add"><a class="easyui-linkbutton" iconCls="icon-add" plain="true" size="large" onclick="addcfgs();">添加</a></shiro:hasPermission>
		    </div>
		</div>
		
		<!-- 操作按钮 -->
		<div id="cfgOPT" class="none" style="display:none">
			<shiro:hasPermission name="${resourceid}:view"><a href="javascript:void(0)" validKey="cfgstate" validVal="0,1" method="seeinfo">查看</a></shiro:hasPermission> 
			<shiro:hasPermission name="${resourceid}:edit"><a href="javascript:void(0)" validKey="cfgstate" validVal="0" method="upwhcfg">编辑</a></shiro:hasPermission> 
			<shiro:hasPermission name="${resourceid}:del"><a href="javascript:void(0)" validKey="cfgstate" validVal="0" method="delcfgs">删除</a></shiro:hasPermission>
			<shiro:hasPermission name="${resourceid}:on"><a href="javascript:void(0)" validKey="cfgstate" validVal="0" method="checktype">启用</a></shiro:hasPermission>
            <shiro:hasPermission name="${resourceid}:off"><a href="javascript:void(0)" validKey="cfgstate" validVal="1" method="stoptype">停用</a></shiro:hasPermission>
		</div>
    </div>
    
    <div id="edit_win" class="none" style="display:none;" data-options="maximized:true">
		<form method="post" id="ff" enctype="multipart/form-data">
		 <!-- 隐藏作用域 -->
		   <input type="hidden" id="cfgshowpic" name="cfgshowpic" value=""/>
			<div class="main">
				<div class="row">
					<div><label>页面类型:</label></div>
					<div>
						<select class="easyui-combotree" name="cfgpagetype" id="cfgpagetype" style="width:100%;height:35px"
						 data-options="editable:true, panelHeight:'auto', data:WHTYP.sys_Whtyp_Data('12')">
						</select>
					</div>
				</div>
				<div class="row">
					<div><label>实体类型:</label></div>
					<div>
						<select class="easyui-combotree" name="cfgenttype" id="cfgenttype" style="width:100%;height:35px"
						 data-options="required:true, editable:true, panelHeight:'auto', filter:cfgenttypeFilter, data:WHTYP.sys_Whtyp_Data('9'),onChange:changeAtrtyp">
						</select>
					</div>
				</div>
				<div class="row">
					<div><label>实体分类:</label></div>
					<div>
						<select class="easyui-combotree" name="cfgentclazz" id="cfgentclazz" value="" style="width:100%;height:35px"
						 data-options="required:true,editable:true, panelHeight:'auto', onChange:changeClazz">
						</select>
					</div>
				</div>
				<div class="row">
					<div><label></label></div>
					<div>
						<div style="width: 100%">
						  <input class="easyui-combogrid" id="cfgshowid" name="cfgshowid" style="width:100%; height:35px;" data-options="required:true,onChange:changewhcfg" />
						</div>
					</div>
				</div>
				<div class="row">
					<div><label>标题:</label></div>
					<div>
						<div style="width: 100%">
							<input class="easyui-textbox" name="cfgshowtitle" id="cfgshowtitle" style="width:100%;height:35px;"
							data-options="required:true, validType:'length[0,60]'"/>
							
						</div>
					</div>
				</div>
				<div class="row">
					<div><label></label></div>
					<div>
						<img id="cfgshowpic_up_img" style="height:150px;"/>
					</div>
				</div>
				<div class="row">
					<div><label>图片:</label></div>
					<div>
						<input class="easyui-filebox" name="cfgshowpic_up" id="cfgshowpic_up" style="width:41%;height:35px" data-options="validType:'isIMG[\'cfgshowpic_up\', 1024]', buttonText:'选择封面', accept:'image/jpeg, image/png'"/>
						<a id="btn_pic" class="easyui-linkbutton" iconCls="icon-clear" style="width:9%;height:32px;">清除</a>
						<input class="easyui-textbox" id="showid" style="width:49%;height:35px;"/>
					</div>
				</div>
				<div class="row">
					<div><label>连接地址:</label></div>
					<div>
						<input class="easyui-textbox" name="cfgshowlink" id="cfgshowlink" style="width:100%;height:35px"
						data-options="validType:'length[0,128]'"/>
					</div>
				</div>
				<div class="row">
					<div><label>时间:</label></div>
					<div>
						<input id="cfgshowtime" name="cfgshowtime" type="text" class="easyui-datetimebox" required="required">
					</div>
				</div>			
				<div class="row">
					<div><label>简介：</label></div>
					<div>
						<input name="cfgshowintroduce" id="cfgshowintroduce" class="easyui-textbox" style="width:100%;height:100px;" data-options="multiline:true, validType:'length[0,400]'"/>
					</div>
				</div>
				<div class="row">
					<div><label>排序:</label></div>
					<div>
						<div>
						<input class="easyui-numberspinner" name="cfgshowidx" id="cfgshowidx" value="" style="width:100%;height:35px"
						data-options="increment:1, required:true,min:1,max:999,onChange:changshow" />
					    </div>
					</div>
				</div>
			</div>
		</form>
	</div>
</body>
</html>

