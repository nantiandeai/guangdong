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
 * 上传资料
 */
function addzy(index){
	var rows = $('#infDG').datagrid('getRows');
	var row = rows[index];//WHdg2.getRowData(index);
	var id = row.clnfid;
	WhgComm.editDialog('${basePath}/admin/shop/uploads?rsid=${resourceid}&refid='+id);
}
/**
 * 添加资源
 * @param index
 * @returns
 */
//function addzl(index){
//	var rows = $('#infDG').datagrid('getRows');
//	var row = rows[index];//WHdg2.getRowData(index);
//
//	$('#addzl iframe').attr('src', getFullUrl('/admin/ent/entsrc?reftype=2016102800000001&refid='+row.clnfid+"&canEdit=true"));
//	$('#addzl').dialog({
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
function addzl(idx) {
	var row = $("#infDG").datagrid("getRows")[idx];
	WhgComm.editDialog('${basePath}/admin/train/resource/view/list?reftype=11&id=' + row.clnfid);
}

//TODO qxk
var tool = (function(){
	//begin 通用的变量声明
	var _tree = "#pageTree", //树JQ
		_grid = "#infDG",	//表格JQ
		_gridtb = "infTB",	//表格工具JQ
		_editwin = "#edit_win", //编辑界面JQ
		_options = {	//表格配置对象
			title: '栏目内容管理',			
			sortName: 'clnfidx',
			sortOrder: 'asc',
			rownumbers:true,
			singleSelect:false,
			columns:[[
				{field:'ck',checkbox:true},
				{field:'clnftype',title:'所属栏目',width:100,formatter :shopType},     
				{field:'clnftltle',title:'标题',width:100}, 
//				{field:'img',title:'图片',width:100,formatter:showImg},
				{field:'clnfstata',title:'状态',width:100,sortable:true,formatter: traStateFMT},
//				{field:'clnfidx',title:'排序',width:50},
				{field:'opt', title:'操作',formatter: WHdg.optFMT, optDivId:'infOPT'}
			]]  
		};	
		
	//end 通用的变量声明
	
	//begin 缓存数据变量声明
	var nowColid,	//当前相关的栏目ID
		nowColidTitle,
		editWinform,	//editWin对象引用
		//formRows,	//表单项初始dom缓存
	  	cfgColids = {
			//只要 标题、详情图、内容 相关的栏目集
			ids_0 : ['2016111900000006'],
			ids_1 : ['2016111900000010'],
			ids_2 : ['2016111900000011'],
			ids_3 : ['2016111900000007','2016111900000008'],
            ids_4 : ['2016111900000012','2016111900000018',
                '2016111900000020','2016111900000021',
                '2016112200000004','2016112200000005','2016112200000006','2016112200000007',
                '2016112200000008','2016112200000009','2016112200000010','2016112200000011','2016112200000012',
                '2016112400000001','2016112400000002'],
			ids_5 : ['2016111900000014','2016111900000015', '2016111900000016','2016111900000017']
		},
		cfgRefFields = {
			ids_0 : ["clnftltle", "colinfobigpic_up", "clnfdetail_edit","clnfcrttime"],
			ids_1 : ["clnftltle", "clnfintroduce","clnfidx","clnfcrttime"],
			ids_2 : ["clnftltle", "clnfintroduce","clnfidx","clnfcrttime"],
			ids_3 : ["clnftltle", "clnfintroduce","clnfauthor","colinfobigpic_up","clnfsource","clnfdetail_edit","clnfkey","clnfcrttime"],
			ids_4 : ["clnftltle", "clnfintroduce","clnfauthor","clnfsource","clnfdetail_edit","clnfkey","clnfcrttime"],
			ids_5 : ["clnftltle", "clnfintroduce","clnfauthor","clnfsource","clnfdetail_edit","clnfkey","clnfcrttime"],
			ids_default : [
					"clnftltle", //栏目内容标题
					"clnfsource", //栏目内容来源
					"clnfauthor", //作者
					"colinfopic_up", //列表图片
					"colinfobigpic_up", //详情图片
					"clnfintroduce",  //栏目内容简介
					"clnfdetail_edit", //栏目内容详细介绍   
					"clnfkey", // 关键字
					"clnfidx", //排序
					"clnfcrttime"//创立时间
	               ]
		},
		nowIdsKey,	//当前idsKey
		venueid	//当前会话管理员分馆标记
	//end 缓存数据变量声明
	
	//begin 格式化转换
	function showImg(v, r, i){
		var img = r.clnfpic || r.clnfbigpic || "";
		if (!img) return "";
		return imgFMT(img, r, i);
	}
	function shopType(val, rowData, index){
		return $(_tree).tree('find', val).text;
	}
	//end 格式化转换
	
	//处理查看的只读
	function _readOnlySetter(){
		//fields = cfgRefFields[nowIdsKey] || [];
		var showRows = $(_editwin).find(".main>.row:visible");
		showRows.each(function(){
			if (!$(this).find("[name]").length) return true;
			var name = $(this).find("[name]").attr("name");
			if ("colinfopic_up" == name){
				$(this).hide();
				//$("#colinfopic_up_img").parents('.row').show();
				return true;
			}
			if ("colinfobigpic_up" == name){
				$(this).hide();
				//$("#colinfobigpic_up_img").parents('.row').show();
				return true;
			}
			_inputEasyuiCls($(this), 'r');
			/* $(this).find("input.easyui-textbox").textbox("readonly");
			$(this).find("input.easyui-combobox").combobox("readonly"); */
			
		})
		$("#colinfopic_up_img,#colinfobigpic_up_img").each(function(){
			$(this).attr("src", "");
			$(this).parents(".row").hide();
		})
	}
	
	function initFormItem(idsKey){
		var fields = [];
		if (!idsKey){ idsKey='ids_default' }
		fields = cfgRefFields[idsKey] || [];
		nowIdsKey = idsKey;
		//console.log(idsKey+"==>"+JSON.stringify(fields));
		//var _main = $(_editwin).find(".main").empty();
		//_main.html(formRows);
		$(_editwin).find(".main>.row").each(function(){
			//if (!$(this).find("[name]").length) return true;
			//var name = $(this).find("[name]").attr("name");
			var _ipt = $(this).find("[name],[comboname]");
			var name = _ipt.attr("name") || _ipt.attr("comboname");
			if (typeof name == 'undefined') return true;
			//console.log(name)
			if ($.inArray(name, fields)==-1){
				$(this).hide();
				_inputEasyuiCls($(this), 'h');
				/* $(this).find("input.easyui-textbox").textbox("disable");
				$(this).find("input.easyui-combobox").combobox("disable"); */
			}else{
				$(this).show();
				_inputEasyuiCls($(this), 's');
				/* $(this).find("input.easyui-textbox").textbox("enable");
				$(this).find("input.easyui-textbox").textbox("readonly", false);
				$(this).find("input.easyui-combobox").combobox("enable");
				$(this).find("input.easyui-combobox").combobox("readonly", false); */
			}
		})
		$("#colinfopic_up").filebox('clear');
		$("#colinfobigpic_up").filebox('clear');
		
		$("#colinfopic_up_img,#colinfobigpic_up_img").each(function(){
			$(this).attr("src", "");
			$(this).parents(".row").hide();
		})
	}
	
	function _inputEasyuiCls(drow, shr){
		var _easyui = drow.find("input[class*='easyui-']");
		if (!_easyui.length) return;
		_easyui.each(function(){
			var _cls = $(this).attr("class");
			_cls = _cls.substring(_cls.indexOf("easyui-"));
			_cls = _cls.split(" ")[0];
			
			//console.log("test==>>"+_cls);
			_easyuiClsOpt($(this), _cls, shr);
		})
	}
	function _easyuiClsOpt(eui, cls, shr){
		switch (cls) {
		case "easyui-textbox":
			if (shr == "s") {
				eui.textbox("enable");
				eui.textbox("readonly", false);
			}
			if (shr == "h") eui.textbox("disable");
			if (shr == "r") eui.textbox("readonly");
			break;
		case "easyui-combobox":
			eui.combobox("clear");
			if (shr == "s") {
				eui.combobox("enable");
				eui.combobox("readonly", false);
			}
			if (shr == "h") eui.combobox("disable");
			if (shr == "r") eui.combobox("readonly");
			break;
		 case "easyui-numberspinner":
			 if (shr == "s") {
					eui.numberspinner("enable");
					eui.numberspinner("readonly", false);
				}
				if (shr == "h") eui.numberspinner("disable");
				if (shr == "r") eui.numberspinner("readonly");
			break; 
		default:
			break;
		}
	}
	
	//处理不同的需求字段时编辑面板内容
	function initEditPanel(){
		var idsKey = "";
		for(var k in cfgColids){
			var arridx = $.inArray(nowColid, cfgColids[k]);
			if (arridx != -1){
				idsKey = k;
				break;
			}
		}
		initFormItem(idsKey);
	}
	
	//初始化列表面板
	function initGridPanel(node){
		//缓存相关的栏目ID
		nowColid = node.colid;
		nowColidTitle = node.coltitle;
		//初始表格
		var grid_id = _grid.replace("#", '');
		WHdg.init(grid_id, _gridtb, _options);
		$(_grid).datagrid({
			url: getFullUrl('/admin/shop/seleinfo'),
			onBeforeLoad: function(param){
				param.clnftype = nowColid;
				if (!param.clnftype) return false;
			}
		});
		//处理表单
		initEditPanel();
	}
	
	//初始树型面板
	function initTreePanel(){
		$(_tree).tree({    
		    url: '${basePath}/admin/shop/selecol' ,
		    onLoadSuccess: function(node, data){
		    	//默认选中省馆介绍
		    	var node = $(_tree).tree('find', '2016111900000006');
				if(node){
					$(_tree).tree('select', node.target);
				}
		    },
		    onSelect: function(node){
		    	initGridPanel(node);
		    },
		    onBeforeSelect: function(node){
		    	//是否是一级节点
		    	var isLeaf = node.colpid != '0';
		    	//如果是一级栏目，不能操作资讯
		    	if(!isLeaf || node.colid == '2016111900000005'){
		    		$('#infTB > div > a.easyui-linkbutton').linkbutton('disable');
		    	}else{
		    		$('#infTB > div > a.easyui-linkbutton').linkbutton('enable');
		    	}
		    	//隐藏不需要的上传和资源的栏目
		    	if (node.colid == '2016111900000006' || node.colid == '2016111900000010' || node.colid == '2016111900000011') {
		    		$('#infOPT >.uploadzy').hide();
		    		$('#infOPT >.uploadFile').hide();
				}else {
					$('#infOPT >.uploadzy').show();
		    		$('#infOPT >.uploadFile').show();
				}
		    	
		    }
		});
	}
	
	//万能的初始化方法
	function init(){
		UE.getEditor('traeditor');
		
		editWinform = new WhuiWinForm(_editwin);
		editWinform.init();
		
		$('#btn_pic').bind('click', function(){  
			$("#colinfopic_up").filebox('clear');
	    });
		$('#btn_pic1').bind('click', function(){  
			$("#colinfobigpic_up").filebox('clear');
	    });
		//formRows = $(_editwin).find(".main").html();
		initTreePanel();
		//alert("$ {user.venueid}")
		//venueid = "$ { user.venueid}" || '';
	}
	
	//查看
	function seeinfo(idx){
		var row = WHdg.getRowData(idx);
		row.clnfcrttime = datetimeFMT(row.clnfcrttime);
		editWinform.setWinTitle("查看 "+nowColidTitle+" 栏目内容");
		editWinform.openWin();
		//$("#clnfkey").combobox("clear");
		$("#colinfopic_up").filebox('clear');
		$("#colinfobigpic_up").filebox('clear');
		editWinform.getWinFooter().find("a:eq(0)").hide();
		_readOnlySetter();
		
		var showObj = new Object();
		for(var k in row){
			if (k && row[k]){
				showObj[k] = row[k];
			}
		}
		editWinform.getWinBody().find("form").form("load", showObj);
		if (row.clnfpic){
			$("#colinfopic_up_img").attr('src', getFullUrl(row.clnfpic)).parents('.row').show();
		}
		if (row.clnfbigpic){
			$("#colinfobigpic_up_img").attr('src', getFullUrl(row.clnfbigpic)).parents('.row').show();
		}
		if ($.inArray("clnfdetail_edit", cfgRefFields[nowIdsKey])>=0){
			UE.getEditor('traeditor').setContent(row.clnfdetail||"" );
			UE.getEditor('traeditor').setDisabled('fullscreen');
		}
	}
	
	//添加处理
	function addinfo(){
		initFormItem(nowIdsKey);
		editWinform.setWinTitle("添加 "+nowColidTitle+" 栏目内容");
		editWinform.openWin();
		//$("#clnfkey").combobox("clear");
		editWinform.getWinFooter().find("a:eq(0)").show();
		
		var _frm = editWinform.getWinBody().find("form").form({
			url : getFullUrl('/admin/shop/addmusinfo'),
			onSubmit: function(param){
				param.clnfstata = 0;
            	param.clnfghp = 0;
            	param.clnfidx = 1;
            	param.clnftype = nowColid;	//当前的栏目ID
            	param.clnvenueid = venueid;	//所属文化馆标识
				if ($.inArray("clnfdetail_edit", cfgRefFields[nowIdsKey])>=0){
					//设置富文本的值
					param.clnfdetail = UE.getEditor('traeditor').getContent();
				}
				
                var isvalid = $(this).form('enableValidation').form('validate');
                if (!isvalid){
                	$.messager.alert("提示", "请检查输入项");
                	editWinform.oneClick(function(){ _frm.submit(); });
                }
                return isvalid;
			},
			success : function(data) {
				var json = $.parseJSON(data);
                if (json.success=='0'){
                	$(_grid).datagrid('reload');
					$.messager.alert("提示", "操作成功");
					editWinform.closeWin();
                }else{
                    $.messager.alert("失败了", json.msg);
                    editWinform.oneClick(function(){ _frm.submit(); });
                }
			}
		});
		_frm.form("clear");
		$("#colinfopic_up_img, #colinfobigpic_up_img").parents('.row').hide();
		if ($.inArray("clnfdetail_edit", cfgRefFields[nowIdsKey])>=0){
			UE.getEditor('traeditor').setContent("");
			UE.getEditor('traeditor').setEnabled();
		}
		editWinform.oneClick(function(){ _frm.submit(); });
	}
	
	//编辑处理
	function uptro(idx){
		var row = WHdg.getRowData(idx);
		row.clnfcrttime = datetimeFMT(row.clnfcrttime);
		initFormItem(nowIdsKey);
		editWinform.setWinTitle("编辑 "+nowColidTitle+" 栏目内容");
		editWinform.openWin();
		editWinform.getWinFooter().find("a:eq(0)").show();
		
		var _frm = editWinform.getWinBody().find("form").form({
			url : getFullUrl('./admin/shop/upminfo'),
			onSubmit : function(param){
                param.clnfid = row.clnfid;
                if ($.inArray("clnfdetail_edit", cfgRefFields[nowIdsKey])>=0){
					//设置富文本的值
					param.clnfdetail = UE.getEditor('traeditor').getContent();
				}
                var isvalid = $(this).form('enableValidation').form('validate');
                if (!isvalid){
                	$.messager.alert("提示", "请检查输入项");
                	editWinform.oneClick(function(){ _frm.submit(); });
                }
                return isvalid;
            },
			success : function(data) {
				var json = $.parseJSON(data);
				if (json.success=='0'){
					$(_grid).datagrid('reload');
					$.messager.alert("提示", "操作成功");
					editWinform.closeWin();
				}else{
					$.messager.alert("失败了", json.msg);
					editWinform.oneClick(function(){ _frm.submit(); });
				}
			}
		});
		
		$("#colinfopic_up_img, #colinfobigpic_up_img").parents('.row').hide();
		if (row.clnfpic){
			$("#colinfopic_up_img").attr('src', getFullUrl(row.clnfpic)).parents('.row').show();
		}
		if (row.clnfbigpic){
			$("#colinfobigpic_up_img").attr('src', getFullUrl(row.clnfbigpic)).parents('.row').show();
		}
		//$("#clnfkey").combobox("clear");
		_frm.form("clear");
		//console.log(JSON.stringify(row));
		if (!row.clnfkey) row.clnfkey = '';
		_frm.form("load", row);
		if ($.inArray("clnfdetail_edit", cfgRefFields[nowIdsKey])>=0){
			UE.getEditor('traeditor').setContent(row.clnfdetail||"" );
			UE.getEditor('traeditor').setEnabled();
		}
		
		editWinform.oneClick(function(){ _frm.submit(); });		
	}
	
	//处理删除
	function delinfo(idx){
		var row = WHdg.getRowData(idx);
		var id = row.clnfid;
		var pic = row.clnfpic;
		var bigpic = row.clnfbigpic;
		$.messager.confirm('确认对话框', '您确认要删除吗？', function(r){
			if (r){
				$.ajax({
					type: "POST",
					url: getFullUrl('/admin/shop/delinfo'),
					data: {clnfid : id, clnfpic:pic,clnfbigpic : bigpic},
					success: function(msg){
						$.messager.alert("提示", "操作成功");
						$(_grid).datagrid('reload');
					}
				});
			}
		});
	}
	
	//修改审核发布状态
	function _oneSetState(id, state){
		$.ajax({
    		type: "POST",
     		url : getFullUrl("./admin/shop/checkinfo"),
     		data : {clnfid:id, clnfstata:state},
     		success:function(data){
     			if (data=="success"){
//                     $.messager.alert("提示","操作成功");
                     $(_grid).datagrid("reload");
                 }else{
                     $.messager.alert("失败了", "操作失败！");
                 }
     		}
     	})
	}
	
	//处理审核
	function checkinfo(idx){
		var row = WHdg.getRowData(idx);
		 var id = row.clnfid;
	     $.messager.confirm('确认对话框', '确定要通过审核吗？', function(r) {
	         if (r) {
	        	 _oneSetState(id, 2);
	         }
	     })
	}
	//取消审核
	function uncheckinfo(idx){
		var row = WHdg.getRowData(idx);
	 	 var id = row.clnfid;
	      $.messager.confirm('确认对话框', '确定要打回重审吗？', function(r) {
	          if (r) {
	        	  _oneSetState(id, 0);
	          }
	      })
	}
	//发布
	function ucheckinfo(idx){
		var row = WHdg.getRowData(idx);
	  	 var id = row.clnfid;
	       $.messager.confirm('确认对话框', '确定要发布吗？', function(r) {
	           if (r) {
	        	   _oneSetState(id, 3);
	           }
	       })
	}
	//取消发布
	function ncheckinfo(idx){
		var row = WHdg.getRowData(idx);
	   	 var id = row.clnfid;
        $.messager.confirm('确认对话框', '确定要取消发布？', function(r) {
            if (r) {
            	_oneSetState(id, 2);
            }
        })
	}
	
	//批量处理时取选项值
	function _getSelectIds(oldstart){
		var rows={};
		rows = $(_grid).datagrid("getSelections");
		if (rows == "" || rows == null) {
			$.messager.alert('提示', '请选择要操作的记录');
			return false;
		}
		var clnfids = _split = "";//id1,id2,id3
		for (var i = 0; i<rows.length; i++){
			if (rows[i].clnfstata == oldstart){
				clnfids += _split+rows[i].clnfid;
				_split = ",";
			}
		}
		if (!clnfids){
			$.messager.alert('提示', '没有匹配当前操作的选择记录');
			return false;
		}
		return clnfids;
	}
	//批量处理时发送处理
	function _batchSend(data){
		$.ajax({
			type: "POST",
			url: getFullUrl('./admin/shop/checkeinfos'),
			data: data,
			success: function(data){
			   if(data.success == '0'){
					$(_grid).datagrid('reload');
			   }else{
				   $.messager.alert('提示', '操作失败。原因：'+data.errmsg, 'error');
			   }
			}
		});
	}
	
	//处理批量审核
	function doallup(){
		var clnfids = _getSelectIds(0);
		if (!clnfids){ return; }
		$.messager.confirm('确认对话框', '您确认将所选择的批量审核吗？', function(r){
			if (r){
				var data = {clnfid : clnfids,fromstate:0, tostate:2};
				_batchSend(data);
			}
		});
	}
	//批量取消审核
	function doupall(){
		var clnfids = _getSelectIds(2);
		if (!clnfids){ return; }
		$.messager.confirm('确认对话框', '您确认将所选择的批量取消审核吗？', function(r){
			if (r){
				var data = {clnfid : clnfids,fromstate:2, tostate:0};
				_batchSend(data);
			}
		});
	}
	//批量发布
	function toallup(){
		var clnfids = _getSelectIds(2);
		if (!clnfids){ return; }
		$.messager.confirm('确认对话框', '您确认将所选择的进行批量发布吗？', function(r){
			if (r){
				var data = {clnfid : clnfids,fromstate:2, tostate:3};
				_batchSend(data);
			}
		});
	}
	//批量取消发布
	function toupall(){
		var clnfids = _getSelectIds(3);
		if (!clnfids){ return; }
		$.messager.confirm('确认对话框', '您确认将所选择的进行批量取消发布吗？', function(r){
			if (r){
				var data = {clnfid : clnfids,fromstate:3, tostate:2};
				_batchSend(data);
			}
		});
	}
	
	return {
		init: init,
		addinfo: addinfo,
		uptro: uptro,
		delinfo: delinfo,
		checkinfo: checkinfo,
		uncheckinfo: uncheckinfo,
		ucheckinfo: ucheckinfo,
		ncheckinfo: ncheckinfo,
		doallup: doallup,
		doupall: doupall,
		toallup: toallup,
		toupall: toupall,
		seeinfo: seeinfo
	}
})()

//TODO qxk
$(function(){
	tool.init();

	editWinform_.init();
})

var editWinform_ = new WhuiWinForm("#edit_win_",{width:500,height:250});

/**
 * 上首页
 */
function goindex(index){
	var row = WHdg.getRowData(index);
	editWinform_.openWin();
	editWinform_.setWinTitle("设置排序");
	var frm = $('#ff_').form({
		url : getFullUrl("/admin/shop/goinfo"),
		onSubmit : function(param) {
			param.clnfid = row.clnfid;
			return $(this).form('enableValidation').form('validate');
		},
		success : function(data) {
			data = eval('('+data+')');
			if(data && data.success == "0"){
				$('#infDG').datagrid('reload');
				$.messager.alert('提示', '操作成功!');
				editWinform_.closeWin();
			}else{
				$.messager.alert('提示', '操作失败!');
			}
		}
	});

	frm.form("load", row);
	editWinform_.setFoolterBut({onClick:function(){
		frm.submit();
	}});
}


</script>
</head>
<body class="easyui-layout">
	<div id='infDGS' data-options="region:'west',title:'栏目页面',split:true" style="width:200px;">
		<ul id="pageTree"></ul>  
	</div> 
         
	<div style="display:none" data-options="region:'center',title:'',iconCls:'icon-ok'">
		<div id="infDG"></div>
		
		<div id="infTB" style="height:auto" style="display:none">
		    <div>
		        <shiro:hasPermission name="${resourceid}:add"><a class="easyui-linkbutton" iconCls="icon-add" size="large" plain="true" onclick="tool.addinfo();">添加</a></shiro:hasPermission>
		        <shiro:hasPermission name="${resourceid}:checkon">
				<a href="javascript:void(0)" class="easyui-linkbutton" size="large" plain="true"  onclick="tool.doallup();">批量审核</a>
				</shiro:hasPermission> 
				<shiro:hasPermission name="${resourceid}:checkoff">
				<a href="javascript:void(0)" class="easyui-linkbutton" size="large" plain="true" onclick="tool.doupall();" >批量取消审核</a>
				</shiro:hasPermission>
				<shiro:hasPermission name="${resourceid}:publish">
				<a href="javascript:void(0)" class="easyui-linkbutton" size="large" plain="true" onclick="tool.toallup();" >批量发布</a>
				</shiro:hasPermission>
				<shiro:hasPermission name="${resourceid}:publishoff">
				<a href="javascript:void(0)" class="easyui-linkbutton" size="large" plain="true" onclick="tool.toupall();" >批量取消发布</a>
				</shiro:hasPermission>	
		    </div>
			<div>
			            <%-- 栏目:
			    <input class="easyui-combotree" name="clnftype" data-options="url:'${basePath}/admin/shop/selmus'" /> --%>
				<%--栏目内容标题:--%>
				<%--<input class="easyui-textbox" name="clnftltle" data-options="validType:'length[1,30]',prompt:'请输入栏目内容标题'"/>--%>
				<input class="easyui-textbox" style="width: 200px; height:28px" name="clnftltle" data-options="prompt:'请输入栏目内容标题'" />
				<a href="javascript:void(0)" class="easyui-linkbutton search" iconCls="icon-search">查询</a>
				
			</div>
		</div>
		<!-- 操作按钮 -->
		<div id="infOPT" class="none" style="display:none">
			<shiro:hasPermission name="${resourceid}:view"><a href="javascript:void(0)" class="easyui-linkbutton uploadzy" data-options="plain:true" method="addzl">资源管理</a></shiro:hasPermission>
		    <shiro:hasPermission name="${resourceid}:view"><a href="javascript:void(0)" class="easyui-linkbutton uploadFile" data-options="plain:true" method="addzy">上传资料</a></shiro:hasPermission>
		    <shiro:hasPermission name="${resourceid}:view"><a href="javascript:void(0)" validKey="clnfstata" validVal="0,1,2,3" method="tool.seeinfo">查看</a></shiro:hasPermission> 
			<shiro:hasPermission name="${resourceid}:edit"><a href="javascript:void(0)" validKey="clnfstata" validVal="0,1" method="tool.uptro">编辑</a></shiro:hasPermission> 
			<shiro:hasPermission name="${resourceid}:del"><a href="javascript:void(0)" validKey="clnfstata" validVal="0,1" method="tool.delinfo">删除</a></shiro:hasPermission>
			<shiro:hasPermission name="${resourceid}:checkon"><a href="javascript:void(0)" validKey="clnfstata" validVal="0,1" method="tool.checkinfo">审核</a></shiro:hasPermission>
            <shiro:hasPermission name="${resourceid}:checkoff"><a href="javascript:void(0)" validKey="clnfstata" validVal="2" method="tool.uncheckinfo">取消审核</a></shiro:hasPermission>
            <shiro:hasPermission name="${resourceid}:publish"><a href="javascript:void(0)" validKey="clnfstata" validVal="2" method="tool.ucheckinfo">发布</a></shiro:hasPermission>
            <shiro:hasPermission name="${resourceid}:publishoff"><a href="javascript:void(0)" validKey="clnfstata" validVal="3" method="tool.ncheckinfo">取消发布</a></shiro:hasPermission>
            <shiro:hasPermission name="${resourceid}:order"><a href="javascript:void(0)" validKey="clnfstata" validVal="3" method="goindex">排序</a></shiro:hasPermission>
		</div>
	</div>
		
		<!--添加资源dialog  -->
    <div id="addzy">
	     <iframe  style="width:100%; height:100%"></iframe>
    </div>
    <div id="addzl">
	     <iframe  style="width:100%; height:100%"></iframe>
    </div>
	    
		 <!-- div弹出层 --> 
	<div id="edit_win" class="none" data-options="maximized:true" style="display:none">
		<form method="post" id="ff" enctype="multipart/form-data">
		 <!-- 隐藏作用域 -->
		 	<!-- <input type="hidden" id="clnfdetail" name="clnfdetail" value="" /> -->
			<div class="main">
			    <%-- <div class="row">
					<div><label>栏目:</label></div>
					<div>
					<select class="easyui-combotree" name="clnftype" id="clnftype" style="width:90%;height:35px"  
					data-options="editable:true, required:true, url:'${basePath}/admin/shop/selmus'" >
					</select>
					</div>
				</div> --%>
				<div class="row">
					<div><label>标题:</label></div>
					<div>
						<input class="easyui-textbox" name="clnftltle" id='clnftltle' style="width:90%;height:35px"
						data-options="required:true, validType:'length[0,60]'"/>
					</div>
				</div>
				
			   	<div class="row">
					<div><label>来源:</label></div>
					<div>
						<input class="easyui-textbox" name="clnfsource" id="clnfsource" style="width:90%;height:35px"
						data-options="required:true, validType:'length[0,60]'"/>
					</div>
				</div>
				<div class="row">
					<div><label>作者:</label></div>
					<div>
						<input class="easyui-textbox" name="clnfauthor" id="clnfauthor" style="width:90%;height:35px"
						data-options="required:true, validType:'length[0,60]'"/>
					</div>
				</div>
				<div class="row">
					<div><label>关键字:</label></div>
					<div>
						<input id="clnfkey" class="easyui-combobox" name="clnfkey" multiple="true" style="width:90%;height:32px;" data-options="required:true,missingMessage:'请用英文逗号分隔', panelHeight:'auto',editable:true, valueField:'text',textField:'text', data: WhgComm.getZxKey(), multiple:true"/>
					</div>
				</div>
				
				<div class="row">
					<div><label>列表图片:</label></div>
					<div>
						<input class="easyui-filebox" name="colinfopic_up" id="colinfopic_up" data-options="buttonText:'选择图片'" style="width:81%;height:35px"/>
						<a id="btn_pic" class="easyui-linkbutton" data-options="plain:true," iconCls="icon-clear" style="width:9%;height:32px;">清除</a>
					</div>
				</div>
				<div class="row" style="display: none">
					<div><label></label></div>
					<div>
						<img id="colinfopic_up_img" style="height:150px;"/>
					</div>
				</div>
				<div class="row">
					<div><label>详情图片:</label></div>
					<div>
						<input class="easyui-filebox" name="colinfobigpic_up" id="colinfobigpic_up" data-options="prompt:'建议:1920x335 省馆介绍：1200x455'" style="width:81%;height:35px"/>
						<a id="btn_pic1" class="easyui-linkbutton" data-options="plain:true," iconCls="icon-clear" style="width:9%;height:32px;">清除</a>
					</div>
				</div>
				<div class="row" style="display: none">
					<div><label></label></div>
					<div>
						<img id="colinfobigpic_up_img" style="height:150px;"/>
					</div>
				</div>
				
				<div class="row">
					<div><label>创立时间：</label></div>
					<div>
						<input id="clnfcrttime" name="clnfcrttime" class="easyui-datetimebox" style="width:90%;height:32px;" data-options="required:true">
					</div>
				</div>
				
				<div class="row">
					<div><label>排序</label></div>
					<div>
						<input class="easyui-numberspinner" id="clnfidx" name="clnfidx" style="width:90%;height:35px" data-options="increment:1, required:true,min:1,max:999,editable:true"/>
					</div>
			   </div>
			   
				<div class="row">
					<div><label>简介:</label></div>
					<div>
						<input class="easyui-textbox" name="clnfintroduce" id="clnfintroduce" style="width:90%; height:120px" 
						data-options="validType:'length[0,200]',multiline:true"/>
					</div>
				</div>
				
				<div class="row">
					<div><label>详细介绍:</label></div>
					<div>
						<div style="width:90%" name="clnfdetail_edit">
							<script id="traeditor" type="text/plain" style="width:100%; height:300px;"></script>
						</div>
					</div>
				</div>
			   
		  </div>
	  </form>
   </div>
   <!-- 上首页编辑层  -->
   <div id="edit_win_" class="none">
		<form method="post" id="ff_" enctype="multipart/form-data">
		
		 <input type="hidden" id="clnfghp" name="clnfghp" value="" />
			<div class="main">
				<div class="row">
					<div><label>排序</label></div>
					<div>
						<input class="easyui-numberspinner" id="troupeidx" name="clnfidx" style="width:90%;height:35px" data-options="increment:1, required:true,min:1,max:999,editable:true"/>
					</div>
				</div>
		    </div>
	    </form>
   </div>
</body>
</html>

