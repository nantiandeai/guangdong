<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath();
	request.setAttribute("basePath", basePath);
%>
<%request.setAttribute("resourceid", request.getParameter("rsid"));%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>活动管理</title>
<base href="${basePath }/">
<%@include file="/pages/comm/header.jsp"%>
<script type="text/javascript" charset="utf-8" src="${basePath}/static/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${basePath}/static/ueditor/ueditor.all.min.js"></script>
<script type="text/javascript" charset="utf-8" src="${basePath}/static/ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript">
/** new 表单*/
var winform = new WhuiWinForm("#frm");
var combox=function(){
	/** 开关 与报名相关的表单*/
		/** 开关 与报名相关的表单*/
	function isup() {
		if($(".actvisattach").combobox("getValue") == "0"){
		//$(".temp_url").filebox({  required:false   });
		//$(".one_url").filebox({  required:false   });
		$(".temp_url").filebox('readonly');
		$(".one_url").filebox('readonly');
		
		}else{
			if($(".actvcanperson").combobox('getValue') == '1'){//不需要个人传
				$(".one_url").filebox({  required:true  });
			}
			if($(".actvcanteam").combobox('getValue') == '1'){
				$(".temp_url").filebox({  required:true  });
			}
			 /* if(count == -1 && count1 == -1){
			    //$(".temp_url").filebox({  required:true   });
				//$(".one_url").filebox({  required:true  });
				
				$(".actvcanteam").combobox('setValue','1');
				$(".actvcanperson").combobox('setValue','1'); 
			} */
			$(".temp_url").filebox('readonly',false);
			$(".one_url").filebox('readonly',false); 
		
		}
	}
	
	var count=0;
	var count1=0;
	
	//允许修改个人报名
	function isone4edit(){
		isone(110);
	}
	
	function isone(isedit) {
		var val = $(".actvisattach").combobox('getValue');
		if(val == "1"){//需要传
			if($(".actvcanperson").combobox('getValue') == '0'){//不需要个人传
				count = -1;
				$(".one_url").filebox({  required:false   });
			}else{//需要个人传
				count = 1;
				if(isedit == 110){
					var fileoneName = $("#fileoneName").html();
					if(fileoneName !=null && fileoneName != ""){
						$(".one_url").filebox({  required: false  });
					}else{
						$(".one_url").filebox({  required: true  });
					}
				}else{
					$(".one_url").filebox({  required: true  });
				}
			}
			
			//个人 团队 二者 必选一
			if(count==-1 && count1==-1){//
				//$(".actvcanteam").combobox('unselect');
				$(".temp_url").filebox({  required:true   });
				$(".one_url").filebox({  required:true  });
				
				$(".actvcanteam").combobox('setValue','1');
				$(".actvcanperson").combobox('setValue','1');
			} 
		} else{//不需要传
			$(".temp_url").filebox({  required:false   });
			$(".one_url").filebox({  required:false  });
		} 
	}
	
	//允许修改团队报名
	function isteamedit(){
		isteam(110);
	}
	
	//允许团队报名
	function isteam(isedit){
		var val=$(".actvisattach").combobox('getValue');
		if(val=="1"){
			if($(".actvcanteam").combobox('getValue') == '0'){
				count1=-1;
				$(".temp_url").filebox({  required:false   });
				//$(".temp_url").filebox('disable');
			}else{
				count1=1;
				if(isedit == 110){
					var fileteamName = $("#fileteamName").html();
					if(fileteamName !=null && fileteamName != ""){
						$(".temp_url").filebox({  required: false  });
					}else{
						$(".temp_url").filebox({  required: true  });
					}
				}else{
				$(".temp_url").filebox({  required:true  });
				}
			}
		} else{//不需要传
			$(".temp_url").filebox({  required:false   });
			$(".one_url").filebox({  required:false  });
		} 
		 if(count == -1 && count1 == -1){
			//$(".actvcanteam").combobox('unselect');
		 	$(".temp_url").filebox({  required:true   });
			$(".one_url").filebox({  required:true  });
			
			$(".actvcanteam").combobox('setValue','1');
			$(".actvcanperson").combobox('setValue','1'); 
		} 
  }
	/** 开关 与修改报名相关的表单*/
	function isbmedit(){
		isbm(110);
	}
	
	/** 开关 与报名相关的表单*/
	function isbm(isedit) { 
		//$.messager.alert('a',"bbb");
		//var jqstr = ".actvenroldesc, .actvenroldesc,.actvenrollimit,.actvenroletime,.actvenrolstime,.actvisenrolqr,.actvisrealname";
		//var stime = $(".actvenrolstime").datebox("getValue");
		//var etime = $(".actvenroletime").datebox("getValue");
		if($(".actvisenrol").combobox('getValue')=='0'){
			if($(".actvisyp").combobox('getValue') && $(".actvisyp").combobox('getValue') == 0){
				$("#actvsdate").show();
				$(".actvsdate").datetimebox({required:true});
				$("#actvedate").show();
				$(".actvedate").datetimebox({required:true});
			}
			
			//if(isedit == 110) return;
			//$(".actvisyp").combobox('setValue','1');
			$(".actvisrealname").combobox('readonly');
			$(".actvisrealname").combobox('setValue','0');
			
			$(".actvisenrolqr").combobox('readonly');
			$(".actvisenrolqr").combobox('setValue','0');
			
			//$(".actvenrolstime").datebox('readonly');
			//$(".actvenroletime").datebox('readonly');
		
			$(".actvenrollimit").textbox('readonly');
			$(".actvenrollimit").textbox('setValue','0');
			
			$(".actvenroldesc").textbox('readonly'); 
			$(".actvenroldesc").textbox('setValue',''); 
			//$(jqstr).textbox('readonly');
			
			//$(".actvenrolstime").datebox({  required:false   });  
			//$(".actvenroletime").datebox({ required:false });  
			//$(".actvenrolstime").datetimebox("setValue", stime);  
			//$(".actvenroletime").datetimebox("setValue", etime);  
			
			 $(".actvisfulldata").combobox('readonly');
			 $(".actvisfulldata").combobox('setValue','0');
			 
			 $(".actvisattach").combobox('readonly');
			 $(".actvisattach").combobox('setValue','0');
			 
			 $(".actvcanperson").combobox('readonly');
			 $(".actvcanperson").combobox('setValue','0');
			 
			 $(".actvcanteam").combobox('readonly');
			 $(".actvcanteam").combobox('setValue','0');
			 
			$(".temp_url").filebox('disable');
			$(".one_url").filebox('disable');
			}else if($(".actvisenrol").combobox('getValue')==1){
			$("#actvsdate").hide();
			$(".actvsdate").datetimebox({required:false});
			$("#actvedate").hide();
			$(".actvedate").datetimebox({required:false});	
				
			//if(isedit == 110) return;
			$(".actvisyp").combobox('setValue','0');
			$(".actvisrealname").combobox('readonly',false);
			$(".actvisrealname").combobox('setValue','');
			
			$(".actvisenrolqr").combobox('readonly',false);
			$(".actvisenrolqr").combobox('setValue','');
			
			//$(".actvenrolstime").datebox('readonly',false);
			//$(".actvenroletime").datebox('readonly',false);
			
			$(".actvenrollimit").textbox('readonly',false);
			$(".actvenrollimit").textbox('setValue','');
			
			$(".actvenroldesc").textbox('readonly',false);
			$(".actvenroldesc").textbox('setValue',''); 
			//$(jqstr).textbox('readonly',false);
			
			//$(".actvenrolstime").datebox({  required:true   });  
			//$(".actvenroletime").datebox({ required:true   });  
			
			 $(".actvisfulldata").combobox('readonly',false);
			 $(".actvisfulldata").combobox('setValue','');
			 
			 $(".actvisattach").combobox('readonly',false);
			 $(".actvisattach").combobox('setValue','');
			 
			 $(".actvcanperson").combobox('readonly',false);
			 $(".actvcanperson").combobox('setValue','');
			 
			 $(".actvcanteam").combobox('readonly',false);
			 $(".actvcanteam").combobox('setValue','');
			 
			 $(".temp_url").filebox('enable');
			 $(".one_url").filebox('enable');
		}
	}
	//订票
	function isyp(){
		//	var val=$(".actvisenrol").combobox('getValue');
		if($(".actvisyp").combobox('getValue')=='1'){
			$(".actvisenrol").combobox('setValue','0');
			$("#actvsdate").hide();
			$(".actvsdate").datetimebox({required:false});
			$("#actvedate").hide();
			$(".actvedate").datetimebox({required:false});
		}else if($(".actvisyp").combobox('getValue')=='0'){
	  		 //$(".actvisenrol").combobox('setValue','1');
			if($(".actvisenrol").combobox('getValue') && $(".actvisenrol").combobox('getValue') == 0){
				$("#actvsdate").show();
				$(".actvsdate").datetimebox({required:true});
				$("#actvedate").show();
				$(".actvedate").datetimebox({required:true});
			}
		}
	} 
	
	return{
		isyp:isyp,
		isteamedit:isteamedit,	
		isup:isup,
		isbm:isbm,
		isone:isone,
		isone4edit:isone4edit,
		isteam:isteam
	};
}();

//处理编辑时显示图片
function _showImg(data){
	$(".img_div").remove();
	$(".img_div2").remove();
	$('#fileoneName').html("");	
	$('#fileteamName').html("");	
	var imgDiv = '<div class="row img_div">'
		+'<div></div>'
		+'<div><img width="200" height="150"> </div> '
		+'</div>';
	var imgDiv2 = '<div class="row img_div2">'
		+'<div></div>'
		+'<div><img width="200" height="150"> </div> '
		+'</div>';
	
	if (data.actvbigpic){
		var imgrow = $("[name$='imagesm']").parents(".row");
		imgrow.after(imgDiv);
		//$(".file_imagesm").filebox('setText',data.actvbigpic);
		imgrow.next(".img_div").find("div img").attr("src",data.actvbigpic);	
	}
	if (data.actvpic){
		var imgrow = $("[name$='image']").parents(".row");
		//$(".file_image").filebox('setText',data.actvpic);
		imgrow.after(imgDiv2);
		imgrow.next(".img_div2").find("div img").attr("src",data.actvpic);	
	}
	if(data.actvpersonfile){
 		$('#fileoneName').html("<span style='color:Blue'>" +data.actvpersonfile+ "</span>");	
	}
	if(data.actvteamfile){
		$('#fileteamName').html("<span style='color:Blue'>" +data.actvteamfile+ "</span>");	
	} 
}

/**查看 活动info*/
function lookAll(idx){
	$(".img_div2").remove();
	$(".img_div").remove();
	//$("input").attr("readonly", "readonly");
		//对form表单禁用
	$("#Form .easyui-combobox").combobox({ disabled: true });  
	$("#Form .easyui-datetimebox").datetimebox({ disabled: true });
	$("#Form .easyui-textbox").textbox({ disabled: true });
	$("#Form .easyui-filebox").filebox("disable"); 
	$("#btn_pic1").hide();
	$("#btn_pic2").hide();
	$("#btn_pic3").hide();
	$("#btn_pic4").hide();
	UE.getEditor('traeditor').setDisabled('fullscreen');
	$(".img_div").remove();
	var row = WHdg.getRowData(idx);
	var frm = winform.getWinBody().find('form');
	winform.openWin();
	winform.setWinTitle("查看活动模板信息");
	//显示富文本
	UE.getEditor('traeditor').setContent(row.actvdetail);
	//格式化日期
	var _data = $.extend({}, row,
	 	{
		actvsdate : datetimeFMT(row.actvsdate),
		actvedate : datetimeFMT(row.actvedate),
	//	actvenrolstime : new Date(row.actvenrolstime).Format("yyyy-MM-dd hh:mm:ss"),
	//	actvenroletime : new Date(row.actvenroletime).Format("yyyy-MM-dd hh:mm:ss")
	    });
		frm.form("clear");
		
		//是否需要报名下拉框
	     $(".actvisenrol").combobox({
	   		onChange:function(){}
	    }); 
	 	//是否需要上传附件
	  	 $(".actvisattach").combobox({
	   		onChange:function(){}
	    }); 
	 	//绑定是否需要个人报名
		$(".actvcanperson").combobox({
	   		onChange:function(){}
	    });
		//绑定是否需要团队报名
	 	$(".actvcanteam").combobox({
	   		onChange:function(){}
	    });  
		
	 	if(row.actvisenrol == 0 && row.actvisyp == 0){
			$("#actvsdate").show();
			$(".actvsdate").datetimebox({required:true});
			$("#actvedate").show();
			$(".actvedate").datetimebox({required:true});
		}else{
			$("#actvsdate").hide();
			$(".actvsdate").datetimebox({required:false});
			$("#actvedate").hide();
			$(".actvedate").datetimebox({required:false});
		}
		
		_showImg(_data);
		frm.form("load", _data);
		winform.getWinFooter().find("a:eq(0)").hide();
}

/**工具栏 search 加载数据*/
function datagridLoad(){ 
    var tb = $("#activityTool");
    var datagrid=$("#activityDiv");
    var params = {};
    tb.find("[name]").each(function(){
    	var value = $(this).val();
    	var name = $(this).attr("name");
    	params[name] = value;
    });
    this.queryParams = this.queryParams ||{};
    $.extend(this.queryParams, params);

    datagrid.datagrid({
        url :'./admin/activity/loadActivitytpl',
        queryParams : this.queryParams
    });
}

/**激活 表单 */
function activateForm() {
	$("input").attr("readonly", false);
	//对form里面的下拉框禁用
	$("#Form .easyui-combobox").combobox({ disabled: false });  
	$("#Form .easyui-datetimebox").datetimebox({ disabled: false });
	$("#Form .easyui-textbox").textbox({ disabled: false });
	$("#Form .easyui-filebox").filebox("enable");
	$("#btn_pic1").show();
	$("#btn_pic2").show();
	$("#btn_pic3").show();
	$("#btn_pic4").show();
	winform.getWinFooter().find("a:eq(0)").show();
	 UE.getEditor('traeditor').setEnabled();
}

/**修改模板活动*/
function edit_activity(idx) {
	activateForm();
	$(".img_div2").remove();
	$(".img_div").remove();
	var row = WHdg.getRowData(idx);
	winform.openWin();
	winform.setWinTitle("修改活动模板信息");
	
	if(row.actvisenrol == 0 && row.actvisyp == 0){
		$("#actvsdate").show();
		$(".actvsdate").datetimebox({required:true});
		$("#actvedate").show();
		$(".actvedate").datetimebox({required:true});
	}else{
		$("#actvsdate").hide();
		$(".actvsdate").datetimebox({required:false});
		$("#actvedate").hide();
		$(".actvedate").datetimebox({required:false});
	}
	
	//init form
	var frm = winform.getWinBody().find('form').form({
		url : "./admin/activity/addOrEditActivitytpl",
		onSubmit : function(param) {
			
			param.actvtplid = row.actvtplid;
			param.actvdetail=UE.getEditor('traeditor').getContent();
			$("#traeditor").val(UE.getEditor('traeditor').getContent());
			var sb = $(this).form('enableValidation').form('validate');
			if(!sb){
				winform.oneClick(function(){ frm.submit(); });
			}
		    return sb;
		},
		success : function(data) {
			var Json = $.parseJSON(data);
			if (Json.success) {
				$.messager.alert('提示', '修改成功！');
				$("#activityDiv").datagrid('reload');
				winform.closeWin();
			} else {
				$.messager.alert('提示', '修改失败,操作无效！');
				winform.oneClick(function(){ frm.submit(); });
			}
		}
	});
	
	//clear form
	frm.form("clear");
	
	/** */
	//是否需要订票下拉框
	$(".actvisyp").combobox({
   		onChange:combox.isyp
    });
	
	//是否需要报名下拉框
   	$(".actvisenrol").combobox({
   		onChange:combox.isbm
    });
 	//是否需要上传附件
  	 $(".actvisattach").combobox({
   		onChange:combox.isup
    }); 
 	//绑定是否需要个人报名
	$(".actvcanperson").combobox({
   		onChange:combox.isone4edit
    });
	//绑定是否需要团队报名
 	$(".actvcanteam").combobox({
   		onChange:combox.isteamedit
    }); 
	
	
	//set data to form	
	//格式化日期
	var _data = $.extend({}, row, {
		actvsdate : datetimeFMT(row.actvsdate),
		actvedate :datetimeFMT(row.actvedate),
	//	actvenrolstime : new Date(row.actvenrolstime).Format("yyyy-MM-dd hh:mm:ss"),
	//	actvenroletime : new Date(row.actvenroletime).Format("yyyy-MM-dd hh:mm:ss")
	});
	
	var _data2=$.extend({},_data);
	
	
	_showImg(_data);
	//显示富文本
	UE.getEditor('traeditor').setContent(row.actvdetail);
	
	if(_data2.actvisenrol == 1){
		//赋值验证框
		frm.form("load", {
			actvisenrol: _data2.actvisenrol,
		 	actvisattach: _data2.actvisattach,
			actvcanperson: _data2.actvcanperson,
			actvcanteam: _data2.actvcanteam
		});
		
		delete _data2.actvisenrol,
		delete _data2.actvisattach,
		delete _data2.actvcanperson,
		delete _data2.actvcanteam
		window.setTimeout(function(){
			frm.form("load", _data2);
		}, 100);
	}else{
		frm.form("load",_data2);
	}	
	$(".temp_url").filebox({  required:false   });
	$(".one_url").filebox({  required:false   });
	 
	winform.oneClick(function(){ frm.submit(); });
}

/**添加活动模板*/
 function addActivity(){
	 activateForm();
	 $("#fileteamName").html("");
	 $("#fileoneName") .html("");
	 $(".img_div2").remove();
	 $(".img_div").remove();
	var frm = winform.getWinBody().find('form');
	winform.openWin();
	winform.setWinTitle("添加活动模板信息"); 
	
	$("#actvsdate").hide();
	$(".actvsdate").datetimebox({required:false});
	$("#actvedate").hide();
	$(".actvedate").datetimebox({required:false});
	//清空富文本
	UE.getEditor('traeditor').setContent("");
	frm.form({
		url : "./admin/activity/addOrEditActivitytpl",
		onSubmit : function(param) {
			
			param.actvdetail=UE.getEditor('traeditor').getContent();
			$("#traeditor").val(UE.getEditor('traeditor').getContent());
			var sb = $(this).form('enableValidation').form('validate');
			if(!sb){
				winform.oneClick(function(){ frm.submit(); });
			}
			
		    return sb;
		},
		success : function(data) {
			var Json = $.parseJSON(data);
			if (Json.success) {
				$.messager.alert('提示', '增加成功！');
				$("#activityDiv").datagrid('reload');
				winform.closeWin();
			} else {
				$.messager.alert('提示', '新增失败,操作无效！');
				winform.oneClick(function(){ frm.submit(); });
			}
		}
	});
	$(".temp_url").filebox({  required:false   });
	$(".one_url").filebox({  required:false   });
	frm.form("clear");
	//是否需要报名下拉框
    $(".actvisenrol").combobox({
  		onChange:combox.isbm
   }); 
	//是否需要上传附件
 	$(".actvisattach").combobox({
  		onChange:combox.isup
   }); 
	//绑定是否需要个人报名
	$(".actvcanperson").combobox({
  		onChange:combox.isone
   });
	
	//绑定是否需要团队报名
	$(".actvcanteam").combobox({
  		onChange:combox.isteam
   });  
	
	//绑定是否需要团队报名
	$(".actvisyp").combobox({
  		onChange:combox.isyp
    });  

	winform.oneClick(function(){ frm.submit(); });
}

/**删除活动模板记录*/
function removeActivity(idx) {
	var row = WHdg.getRowData(idx);
		$.messager.confirm('确认对话框', '您想要删除此记录吗？', function(r) {
			if (r) {
				$.ajax({
					url : './admin/activity/removeActivitytpl',
					data : {
						actvtplid : row.actvtplid
					},
					success : function(data) {
						if (data.success) {
							$.messager.alert("提示", "删除成功！");
							$("#activityDiv").datagrid("reload");
						} else {
							$.messager.alert("提示", "删除该记录失败！");
						}
					}
				})
			}
		});
}

/**页面加载 */
$(function(){
	var options = {
			title: '活动模板管理',			
			url: getFullUrl('./admin/activity/loadActivitytpl'),
			pageSize:20,
			columns: [[
				{field:'actvshorttitle',title:'列表标题', sortable:true, width:150},
				{field:'actvtitle',title:'详情标题',width:60, sortable:true},
				{field:'actvtype',title:'活动类型',width:100,formatter:acttypFMT},
				{field:'actvarttyp',title:'艺术类型', sortable:true,width:150,formatter:arttypFMT},
				{field:'actvaddress',title:'详细地址', sortable:true, width:150},
				{field:'actvhost',title:'主办方', width:60},
				{field:'actvagelevel',title:'适合年龄段',  width:60,formatter:agelevelFMT},  
				{field:'actvpic',title:'活动详情图', sortable:true,width:80,formatter:imgFMT},
				{field:'actvbigpic',title:'活动列表图', sortable:true,width:80,formatter:imgFMT},
				{field:'opt', title:'操作',formatter:WHdg.optFMT, optDivId:'activityOPT'}
			]]
		};
	//初始表格
	WHdg.init('activityDiv', 'activityTool', options);
	//表单初始化
	winform.init();
	//实例化编辑器
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
	UE.getEditor('traeditor');  
	
	//search 点击查找
	 var tbSearch = $("#activityTool").find(".tb_search").off("click");
    tbSearch.on("click", function () {
        datagridLoad();
    });
  	//清除文件框的值
	$('#btn_pic1').bind('click', function(){  
		$(".file_image").filebox('clear');
		$("#actvpic").val(' ');
		$(".img_div2").remove();
    });
	$('#btn_pic2').bind('click', function(){  
		$(".file_imagesm").filebox('clear');
		$("#actvbigpic").val(' ');
		$(".img_div").remove();
    });
	$('#btn_pic3').bind('click', function(){  
		$(".one_url").filebox('clear');
    });
	$('#btn_pic4').bind('click', function(){  
		$(".temp_url").filebox('clear');
    });
})
</script>
</head>
<body>
<!-- datagrid div -->
<div id="activityDiv"></div>

<!-- datagrid toolbar -->
<div id="activityTool" class="grid-control-group" style="display: none">
	 <shiro:hasPermission name="${resourceid}:add">
	<div style="padding-bottom: 5px">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-add"  onclick="addActivity();" data-options="size:'small'">添加
		</a>
	</div>
	</shiro:hasPermission>
	<div>
		标题:
		<input class="easyui-textbox" name="actvtitle" style="width:150px" />
		艺术类型:
		<input class="easyui-combobox" name="actvarttyp" style="width:150px" 
		data-options="editable:true, valueField:'typid',textField:'typname',url:'./comm/whtyp?type=0'"/>
		活动类型:
		<input class="easyui-combobox" name="actvtype" style="width:150px" 
		data-options="editable:true, valueField:'typid',textField:'typname',url:'./comm/whtyp?type=1'"/>
		<!-- 开始时间:
		<input class="easyui-datebox" name="actvsdate" data-options=""/>
		到
		<input class="easyui-datebox" name="actvedate" data-options=""/> -->
		<a href="javascript:void(0)" class="easyui-linkbutton tb_search" iconCls="icon-search">查询</a>
	</div>
</div>
	
	<!-- 操作按钮 -->
	<div id="activityOPT" class="none" style="display: none">
	<a href="javascript:void(0)"  method="lookAll">查看</a>
	<shiro:hasPermission name="${resourceid}:del"><a href="javascript:void(0)"  method="removeActivity">删除</a> </shiro:hasPermission> 
	<shiro:hasPermission name="${resourceid}:edit"><a href="javascript:void(0)"  method="edit_activity">修改</a> </shiro:hasPermission> 
	</div>

<!--活动模板操作 表单 -->	
<div id="frm" class="none" style="display: none" data-options="	maximized:true">
		<form method="post" enctype="multipart/form-data" id="Form">
			<input type="hidden" id="actvpic" name="actvpic"/>
			<input type="hidden" id="actvbigpic" name="actvbigpic"/>
			<div class="main">
				<div class="row">
					<div>
						<label>活动类型:</label>
					</div>
					<div>
						<input class="easyui-combobox " name="actvtype" style="height:35px;width:90%" 
							data-options="editable:false, valueField:'typid',textField:'typname',
							url:'./comm/whtyp?type=1',required:true"/>
					</div>
				</div>
				<div class="row">
					<div>
						<label>艺术分类:</label>
					</div>
					<div>
						<input class="easyui-combobox" name="actvarttyp" style="height:35px;width: 90%" 
						data-options="editable:false,required:true, valueField:'typid',textField:'typname',url:'./comm/whtyp?type=0'"/>
					</div>
				</div>
				<div class="row">
					<div>
						<label>列表标题:</label>
					</div>
					<div>
						<input name="actvshorttitle" class="easyui-textbox" data-options="validType:'length[0,60]',required:true" style="height: 35px; width: 90%"/>
					</div>
				</div>
				<div class="row">
					<div>
						<label>详情标题:</label>
					</div>
					<div>
					<input name="actvtitle" class="easyui-textbox" data-options="validType:'length[0,60]',required:true" style="height: 35px; width: 90%"/>
					</div>
				</div>
				<div class="row">
					<div>
						<label>详情页主图:</label>
					</div>
					<div>
						<input class="easyui-filebox file_image" name="file_image" id="file_image" style="height:35px;width: 80%" data-options="validType:'isIMG[\'file_image\']', buttonText:'选择图片', accept:'image/jpeg,image/peg, image/png',prompt:'图片尺寸:380x240'"/>
						<a id="btn_pic1" class="easyui-linkbutton" data-options="plain:true," iconCls="icon-clear" style="width:9%;height:32px;">清除</a>
					</div>
				</div>
				<div class="row">
					<div>
						<label>列表图:</label>
					</div>
					<div>
						<input class="easyui-filebox file_imagesm" name="file_imagesm" id="file_imagesm" style="height:35px;width: 80%" data-options="validType:'isIMG[\'file_imagesm\']', buttonText:'选择图片', accept:'image/jpeg,image/peg, image/png',prompt:'图片尺寸:380x240'"/>
						<a id="btn_pic2" class="easyui-linkbutton" data-options="plain:true," iconCls="icon-clear" style="width:9%;height:32px;">清除</a>
					</div>
				</div>
				<div class="row">
					<div>
						<label>联系人电话:</label>
					</div>
					<div>
						<input class="easyui-textbox" name="actvphone" data-options="validType:'isPhone[\'actvphone\']'" style="height:35px;width: 90%" />	
					</div>
				</div>
				<div class="row">
					<div>
						<label>必须登陆点评:</label>
					</div>
					<div>
						<select class="easyui-combobox combobox_isenroldata" name="actvislogincomment"  style="height:32px;width: 90%" 
							data-options="editable:false,required:true">
							<option value="1">是</option>
		    				<option value="0">否</option>
						</select>
					</div>
				</div>
				<div class="row">
					<div>
						<label>标签:</label>
					</div>
					<div>
						<input id="tratags" class="easyui-combobox" name="actvtags" multiple="true" style="width:90%;height:32px;" 
						data-options="editable:false,valueField:'id',textField:'name',url:'${basePath}/comm/whtag?type=2016101400000019', multiple:true"/>
					</div>
				</div>
				<div class="row">
					<div>
						<label>关键字:</label>
					</div>
					<div>
						<input id="tratags2" class="easyui-combobox" name="actvkeys" multiple="true" style="width:90%;height:32px;"
						data-options="valueField:'id',textField:'name',missingMessage:'请用英文逗号分隔',required:true,url:'${basePath}/comm/whkey?type=2016101400000022', multiple:true"/>
					</div>
				</div>
				<div class="row">
					<div>
						<label>区域:</label>
					</div>
					<div>
					<input class="easyui-combobox" name="actvarea" style="height:35px;width: 90%" 
						data-options="editable:false, valueField:'typid',textField:'typname',url:'./comm/whtyp?type=8',required:true"/> 
					</div>
				</div>
				<div class="row">
					<div>
						<label>详细地址:</label>
					</div>
					<div>
						<input class="easyui-textbox" name="actvaddress" data-options="validType:'length[0,128]',required:true" style="height:35px;width: 90%" required=true/>
					</div>
				</div>
				<div class="row">
					<div>
						<label>活动主办方:</label>
					</div>
					<div>
						<input class="easyui-textbox" name="actvhost" data-options="validType:'length[0,75]'" style="height:35px;width: 90%"/>
					</div>
				</div>
				<div class="row">
					<div>
						<label>适合年龄段:</label>
					</div>
					<div>
						<input class="easyui-combobox" name="actvagelevel" style="height:32px;width: 90%" 
						data-options="editable:false, valueField:'typid',textField:'typname',url:'./comm/whtyp?type=3'"/>
					</div>
				</div>
				<div class="row">
					<div>
						<label>是否需要订票:</label>
					</div>
					<div>
						<select class="easyui-combobox actvisyp" name="actvisyp"  style="height:32px;width: 90%" 
							data-options="editable:false,required:true,value:''">
							<option value="1">是</option>
		    				<option value="0">否</option>
						</select>
					</div>
				</div>
				<div class="row">
					<div>
						<label>是否需要报名:</label>
					</div>
					<div>
						<select class="easyui-combobox actvisenrol"
							name="actvisenrol" style="height: 35px; width: 90%"
							data-options="editable:false,required:true,value:''">
							<option value="0">否</option>
							<option value="1">是</option>
						</select>
					</div>
				</div>
				<div class="row" id="actvsdate">
						<div>
							<label>活动开始时间</label>
						</div>
						<div>
							<input name="actvsdate" class="easyui-datetimebox actvsdate"
							style="height: 35px; width: 90%"/>
						</div>
				</div>
				<div class="row" id="actvedate">
						<div>
							<label>活动开始时间</label>
						</div>
						<div>
							<input name="actvedate" class="easyui-datetimebox actvedate"
							style="height: 35px; width: 90%"/>
						</div>
				</div>
				<div class="row">
					<div>
						<label>报名是否需要审核:</label>
					</div>
					<div>
						<select class="easyui-combobox actvisenrolqr"
							name="actvisenrolqr" style="height: 35px; width: 90%"
							data-options="editable:false,required:true,value:''">
							<option value="0">否</option>
							<option value="1">是</option>
						</select>
					</div>
				</div>
				<div class="row">
					<div>
						<label>报名介绍:</label>
					</div>
					<div>
						<input class="easyui-textbox actvenroldesc" data-options="multiline:true"
						name="actvenroldesc" style="width: 90%; height: 180px"/>
					</div>
				</div>
				<div class="row">
					<div>
						<label>必须实名报名:</label>
					</div>
					<div>
						<select class="easyui-combobox actvisrealname" name="actvisrealname"
							style="height: 35px; width: 90%"
							data-options="editable:false,required:true">
							<option value="0">否</option>
							<option value="1">是</option>
						</select>
					</div>
				</div>
				<div class="row">
					<div>
						<label>是否需要完善资料:</label>
					</div>
					<div>
						<select class="easyui-combobox actvisfulldata" name="actvisfulldata"
							style="height: 35px; width: 90%"
							data-options="editable:false,required:true">
							<option value="0">否</option>
							<option value="1">是</option>
						</select>
					</div>
				</div>
				<div class="row">
					<div>
						<label>是否需要上传附件:</label>
					</div>
					<div>
						<select class="easyui-combobox actvisattach" name="actvisattach"
							style="height: 35px; width: 90%"
							data-options="editable:false,required:true,value:''">
							<option value="0">否</option>
							<option value="1">是</option>
						</select>
					</div>
				</div>
				<div class="row">
					<div>
						<label>允许个人报名:</label>
					</div>
					<div>
						<select class="easyui-combobox actvcanperson" name="actvcanperson"
							style="height: 35px; width: 90%"
							data-options="editable:false,required:true,value:''">
							<option value="0">否</option>
							<option value="1">是</option>
						</select>
					</div>
				</div>
				<div class="row">
					<div>
						<label>个人附件路径:</label>
					</div>
					<div>
						<input name="one_url" id="one_url" class="easyui-filebox one_url" style="height: 35px; width: 80%" data-options="validType:'isFile[\'one_url\']', buttonText:'选择文件', accept:'file/doc,file/docx, file/xls,file/xlsx,file/zip'"/>
						<a id="btn_pic3" class="easyui-linkbutton" data-options="plain:true," iconCls="icon-clear" style="width:9%;height:32px;">清除</a>
					</div>
				</div>
				<div class="row">
	    			<div></div>
		    		<div>
		    			<label id="fileoneName" /></a>
		    		</div>
	    		 </div>
				<div class="row">
					<div>
						<label>允许团队报名:</label>
					</div>
					<div>
						<select class="easyui-combobox actvcanteam" name="actvcanteam" style="height: 35px; width: 90%" data-options="editable:false,required:true,value:''">
							<option value="0">否</option>
							<option value="1">是</option>
						</select>
					</div>
				</div>
				<div class="row">
					<div>
						<label>团队附件路径:</label>
					</div>
					<div>
						<input name="temp_url" id="temp_url" class="easyui-filebox temp_url" style="height: 35px; width: 80%" data-options="validType:'isFile[\'temp_url\']', buttonText:'选择文件', accept:'file/doc,file/docx, file/xls,file/xlsx,file/zip'"/>
						<a id="btn_pic4" class="easyui-linkbutton" data-options="plain:true," iconCls="icon-clear" style="width:9%;height:32px;">清除</a>
					</div>
				</div>
				<div class="row">
	    			<div></div>
		    		<div>
		    			<label id="fileteamName" /></a>
		    		</div>
	    		 </div>
				<div class="row">
					<div>
						<label>活动介绍:</label>
					</div>
					<div>
						<input class="easyui-textbox" data-options="multiline:true,validType:'length[0,400]'" name="actvintroduce" style="width: 90%; height: 180px"/>
					</div>
				</div>
				<div class="row">
					<div>
						<label>活动详情:</label>
					</div>
					<div>
						<div style="width:90%">
							<script id="traeditor" type="text/plain" style="width:100%; height:300px;"></script>
						</div>
					</div>
				</div>
				
				<div class="cls"></div>
				</div>
		</form>
</div>	
	
</body>
</html>