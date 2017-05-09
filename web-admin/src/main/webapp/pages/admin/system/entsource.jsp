<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath(); request.setAttribute("basePath", basePath); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>资源信息列表</title>
<base href="${basePath }/">
<%@include file="/pages/comm/header.jsp"%>
<script type="text/javascript" src="${basePath}/static/ueditor/ueditor.config.js"></script>
<script type="text/javascript" src="${basePath}/static/ueditor/ueditor.all.min.js"></script>
<script type="text/javascript" src="${basePath}/static/ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript">

var action = function(){
	var editWin = new WhuiWinForm("#edit_win");
	var isInitEditWin = false;

	function initEditWin(){
		if (!isInitEditWin){
			editWin.init();
		}
		isInitEditWin = true;
		editWin.getWin().window({
			onOpen: function () {
				tooVideo(false);
			}
		})
	}

	//切换视频或资源
	function tooVideo(isVideo){
		if (isVideo){
			editWin.getWin().find("#video_enturl").parents(".row").show();
			editWin.getWin().find("#video_enturl").combobox({
				readonly : false,
				required : true
			});
			editWin.getWin().find("#file_enturl").parents(".row").hide();
			editWin.getWin().find("#file_enturl").filebox({
				readonly : true,
				required : false
			});
		}else{
			editWin.getWin().find("#video_enturl").parents(".row").hide();
			editWin.getWin().find("#video_enturl").combobox({
				readonly : true,
				required : false
			});
			editWin.getWin().find("#file_enturl").parents(".row").show();
			editWin.getWin().find("#file_enturl").filebox({
				readonly : false,
				required : true
			});
		}
	}

	//工具栏 search 加载数据
	function datagridLoad(){ 
	    var tb = $("#tb");
	    var datagrid=$("#ent_grid");
	    var params = {};
	    tb.find("[name]").each(function(){
	    	var value = $(this).val();
	    	var name = $(this).attr("name");
	    	params[name] = value;
	    });
	    this.queryParams = this.queryParams ||{};
	    $.extend(this.queryParams, params);
	    datagrid.datagrid({
	        url :'./admin/ent/loadSrcList',
	        queryParams : this.queryParams
	    });
	}

	function setFormData(frm, row){
		frm.form("load", row);
		$(".enttype").textbox('disable');
		$(".enttimes").textbox('disable');
		$("#file_deourl").filebox("disable");
		$(".file_enturl").filebox("disable");
		var imgDiv = '<div class="row img_div">'
				+'<div></div>'
				+'<div><img width="200" height="150"> </div> '
				+'</div>';
		if(!(row.enttype=='2016101400000056')){
			if (row.enturl){
				var imgrow = $("[name$='enturl']:visible").parents(".row");
				imgrow.after(imgDiv);
				imgrow.next(".img_div").find("div img").attr("src",row.enturl);
			}
		}else{
			frm.find("#video_enturl").combobox({
				onLoadSuccess : function (data) {
					$(this).combobox("setValue", row.enturl);
				}
			});
		}
		if (row.deourl&&row.enttype=='2016101400000056'){
			imgrow = $("[name$='deourl']").parents(".row");
			imgrow.after(imgDiv);
			imgrow.next(".img_div").find("div img").attr("src",row.deourl);	
		}
	}

	function clearForm(frm){
		frm.find("#video_enturl").combobox("clear");
		frm.form('disableValidation').form("clear");
		$(".img_div").remove();
	}
	function addEnt(){
		$(".enttype").textbox('enable');
		$(".enttimes").textbox('enable');
		$("#file_deourl").filebox('enable');
		$(".file_enturl").filebox("enable");
		initEditWin();
		if(reftype=="2016101400000052"){
			editWin.setWinTitle("添加活动资源");
			}
			else if(reftype=="2016101400000051"){
				editWin.setWinTitle("添加培训资源");
			}else if(reftype=="2016101400000053"){
				editWin.setWinTitle("添加场馆资源");
			}else if(reftype=="2016103100000001"){
				editWin.setWinTitle("添加品牌活动资源");
			}else{
				editWin.setWinTitle("添加咨询资源");
			}
		editWin.openWin();
		//$(".reftye").val("${reftype}");
		var frm = editWin.getWinBody().find("form");
		clearForm(frm);
		
		//清空富文本
		UE.getEditor('traeditor').setContent("");
		frm.form({
			url:getFullUrl("./admin/ent/addOrEditEntInfo"),
			onSubmit : function(param){
				param.refid=refid;
				param.reftype=reftype;
				param.entsuorpro=entsuorpro;
				//$("#traeditor").val(UE.getEditor('traeditor').getContent());
				param.entcontent=UE.getEditor('traeditor').getContent();
				var sb = $(this).form('enableValidation').form('validate');
				if(!sb){
					editWin.oneClick(function(){ frm.submit(); });
				}
			    return sb;	
			},
			success : function(data){
			var json = $.parseJSON(data);
			 if (json.success){
                	$.messager.alert("提示", "操作成功");
                	editWin.closeWin();
                	$("#ent_grid").datagrid("reload");
                	editWin.oneClick(function(){ frm.submit(); });
                }else{
                	$.messager.alert("失败了", json.msg);
                }
			}
		});

		editWin.oneClick(function(){ frm.submit(); });
	}

	function editOpt(idx){
		var rows = $("#ent_grid").datagrid("getRows");
		var row = rows[idx];
		initEditWin();
		if(reftype=="2016101400000052"){
		editWin.setWinTitle("编辑活动资源");
		}
		else if(reftype=="2016101400000051"){
			editWin.setWinTitle("编辑培训资源");
		}else if(reftype=="2016101400000053"){
			editWin.setWinTitle("编辑场馆资源");
		}else if(reftype=="2016103100000001"){
			editWin.setWinTitle("添加品牌活动资源");
		}else{
			editWin.setWinTitle("编辑咨询资源");
		}
		editWin.openWin();

		var frm = editWin.getWinBody().find("form");
		clearForm(frm);
		//显示富文本
		UE.getEditor('traeditor').addListener("ready", function () {
	        // editor准备好之后才可以使用
	        //alert(1);
	        UE.getEditor('traeditor').setContent(row.entcontent||"");

        });
		UE.getEditor('traeditor').setContent(row.entcontent || "");
	
		setFormData(frm, row);
		
		frm.form({
			url:"./admin/ent/addOrEditEntInfo",
			onSubmit : function(param){
				param.entid = row.entid;
				
				param.entcontent=UE.getEditor('traeditor').getContent();
				//$("#traeditor").val(UE.getEditor('traeditor').getContent());
				var sb = $(this).form('enableValidation').form('validate');
				if(!sb){
					editWin.oneClick(function(){ frm.submit(); });
				}
			    return sb;	
			},
			success : function(data){
				var json = $.parseJSON(data);
				if (json.success){
					$.messager.alert("提示","编辑资源成功");
					$("#ent_grid").datagrid("reload");
					editWin.closeWin();
				}else{
					$.messager.alert("失败了", json.msg);
					editWin.oneClick(function(){ frm.submit(); });
				}
			}
		});
		
		editWin.oneClick(function(){ frm.submit(); });
	}

	function removeOpt(idx){
		var row = WHdg.getRowData(idx);
			$.messager.confirm('确认对话框', '您想要删除此记录吗？', function(r) {
				if (r) {
					$.ajax({
						url : './admin/ent/entRomv',
						data : {
							id : row.entid
						},
						success : function(data) {
							if (data.success) {
								$.messager.alert("提示", "删除成功！");
								$("#ent_grid").datagrid("reload");
							} else {
								$.messager.alert("提示", "删除该记录失败！");
							}
						}
					})
				}
			});
	}
	
	///资源类型判断是否隐藏封面图片
	function xsimg(nv,ov) {
		var deourl=$("#file_deourl");
		var enttimes=$(".enttimes");
		var file_enturl = $("#file_enturl");
		if(nv=='2016101400000056'){
			tooVideo(true);
			/*file_enturl.filebox({
				validType:'isVideo[\'file_enturl\']',				
			});
			deourl.filebox("enable")
			enttimes.textbox('enable')*/
		}else if(nv=='2016101400000055'){
			tooVideo(false);
			file_enturl.filebox({
				validType:'isIMG[\'file_enturl\']',				
			});
			enttimes.textbox('disable')
			deourl.filebox("disable")
		}else{
			tooVideo(false);
			file_enturl.filebox({
				validType:'isAudio[\'file_enturl\']',				
			});
			enttimes.textbox('enable')
			deourl.filebox("disable")
		}
	}
	return {
		addEnt:addEnt,
		editOpt:editOpt,
		removeOpt:removeOpt,
		xsimg:xsimg,
		datagridLoad:datagridLoad
	}
}()

function myimgFMT(val, rowData, index){
	if(rowData.enttype=="2016101400000055"){
	if(val && val != ''){
		return '<img src="'+getFullUrl(val)+'" style="height:50px;"/>';
	}else{
		return val;
	}
	}else{
		return val;
	}
}

//定义变量接收refid 和 reftype
var refid="";
var reftype="";
var entsuorpro = "";
$(function () {
	//实例化编辑器
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
	UE.getEditor('traeditor');
	
	var canEdit ="${canEdit}";
	if(canEdit=="false"){
		$("#checklistOPT").find("a").css("display","none");
	}
	
	refid="${refid}";
	reftype="${reftype}";
	entsuorpro="${entsuorpro}";
	var options = {
		title : "资源列表",
		url : "./admin/ent/loadSrcList",
		//toolbar : "#tb",
		queryParams:{
			reftype:reftype,
			refid:refid,
			entsuorpro:entsuorpro
		},
		columns : [[
			{field:'entid',title:'资源ID',width:100},
			{field:'entname',title:'资源名',width:100},
			{field:'entauthor',title:'作者',width:100},
			{field:'entkey',title:'关键字',width:100},
			{field:'enttype',title:'资源类型',sortable:true,width:80,formatter: entFMT},
			{field:'reftype',title:'实体类型',sortable:true,width:80,formatter: shitiFMT},
			{field:'refid',title:'实体id',width:100},
			{field:'enturl',title:'资源地址',width:100,formatter:myimgFMT},
			{field:'enttimes',title:'视频/音频时长',width:80,sortable:true},
			{field:'deourl',title:'视频封面',width:100,formatter:imgFMT},
			{field:'opt',title:'操作', formatter: WHdg.optFMT, optDivId:'checklistOPT'}
		]]
	}

	WHdg.init('ent_grid', canEdit=="true" ? 'tb': '', options);

	$("#tb a:first").linkbutton({
		iconCls:'icon-add',
		text : '添加资源',
		onClick: function(){ action.addEnt() }
	})
	
	//enttype资源类型是是视频 显示img
	$(".enttype").combobox({
		onChange:action.xsimg
	});
	
	//search 点击查找
	 var tbSearch = $("#ent_grid").find("#tb").off("click");
    tbSearch.on("click", function () {
    	action.datagridLoad
    });
});

</script>

</head>
<body>

<table id="ent_grid" class="easyui-datagrid"></table>

<div id="checklistOPT" style="display: none;">
	<a href="javascript:void(0)"  method="action.editOpt">编辑</a>
	<a href="javascript:void(0)"  method="action.removeOpt">删除</a>
</div>
<!-- 工具条 -->
<div id="tb" style="display: none;padding:5px">
	<div>
		<a href="javascript:void(0)">添加</a>
	</div>
	<div style="margin-top:5px">
		资源类型:
		<input class="easyui-combobox" name="enttype" style="width:150px" 
			data-options="editable:true, valueField:'typid',textField:'typname',url:'./comm/whtyp?type=10'"/>
		<a href="javascript:void(0)" class="easyui-linkbutton search" iconCls="icon-search">查询</a>
	</div>
</div>


<!-- 编辑表单 -->
<div id="edit_win" style="display:none" data-options="	maximized:true">
	<form method="post" enctype="multipart/form-data">
		<div class="main">
			<div class="row">
					<div>
					<label>资源类型:</label>
					</div>
					<div>
						<input class="easyui-combobox enttype" name="enttype" style="width:90%; height:35px"
							   data-options="required:true,editable:false, valueField:'typid',textField:'typname',url:'./comm/whtyp?type=10'"/>
					</div>
			</div>
			
			<div class="row">
				<div>
				<label>作者:</label>
				</div>
				<div>
					<input class="easyui-textbox" name="entauthor" style="width:90%; height:35px"/>
				</div>
			</div>
			<div class="row">
				<div>
				<label>关键字:</label>
				</div>
				<div>
					<input class="easyui-textbox" name="entkey" style="width:90%; height:35px"/>
				</div>
			</div>
			
			<div class="row">
				<div>
				<label>地点:</label>
				</div>
				<div>
					<input class="easyui-textbox" name="entaddress" style="width:90%; height:35px"/>
				</div>
			</div>
			<div class="row">
				<div>
				<label>资源名称:</label>
				</div>
				<div>
					<input class="easyui-textbox" name="entname" style="width:90%; height:35px"
						   data-options="required:true,validType:'length[0,60]'"/>
				</div>
			</div>
			
			<div class="row">
				<div>
				<label>资源地址:</label>
				</div>
				<div>
					<input class="easyui-filebox file_enturl" name="file_enturl" id="file_enturl" style="height:35px;width:90%" prompt='图片尺寸：252x170'/>
				</div>
			</div>
			<div class="row">
				<div>
					<label>资源地址:</label>
				</div>
				<div>
					<input class="easyui-combobox" name="video_enturl" id="video_enturl" style="height:35px;width:90%" data-options="editable:false, valueField:'addr', textField:'key', url:'${basePath}/admin/video/srchPagging'"/>
				</div>
			</div>
			<div class="row">
				<div>
				<label>视频/音频时长:</label>
				</div>
				<div>
					<input class="easyui-textbox enttimes" name="enttimes" style="width:90%; height:35px"/>
				</div>
			</div>
			<div class="row">
				<div>
				<label>封面图片地址:</label>
				</div>
				<div>
					<input class="easyui-filebox" id="file_deourl" name="file_deourl" style="height:35px;width:90%" prompt='图片尺寸：252x170'/>
				</div>
			</div>

			<div class="row">
					<div>
						<label>资源内容:</label>
					</div>
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