<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath();
	request.setAttribute("basePath", basePath);
%>
<%request.setAttribute("resourceid", request.getParameter("rsid"));%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<%@include file="/pages/comm/header.jsp"%>
<base href="${basePath}/">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
/** 新建表单 --查看用户详情  */
var winform = new WhuiWinForm("#frm");

/** 新建表单 --设置内部员工*/
var innerFrm = new WhuiWinForm("#innerFrm",{width:500,height:400});

/** 新建表单 --设置普通员工*/
var normalFrm = new WhuiWinForm("#normalFrm",{width:500,height:400});

var tool = (function(){
	//设置为内部员工
	function setInner(idx){
		//获取选中行的值
		var row = WHdg.getRowData(idx);
		
		//弹出表单
		innerFrm.openWin();
		innerFrm.setWinTitle("设置内部员工");
		
		//显示提交按钮
		innerFrm.getWinFooter().find("a:eq(0)").show();
		//清空表单值
		$("#innerFrm").form('clear');
		
		//初始化表单
		var frm = innerFrm.getWinBody().find('form').form({
			url : getFullUrl('./admin/setInner'),
			onSubmit : function(param){
				param.id = row.id;
				//表单验证
				var _validate = $(this).form('enableValidation').form('validate');
				//验证不通过，阻止表单重复提交
				if(!_validate){
					innerFrm.oneClick(function(){frm.submit();});
				}
				return _validate;
			},
			success : function(data){
				var Json = $.parseJSON(data);
				//alert(JSON.stringify(data));
				if(Json && Json.success == "0"){
					$.messager.alert('提示','操作成功');
					//关闭窗体
					innerFrm.closeWin();
				}else{
					$.messager.alert('提示','操作失败');
					//操作失败，阻止表单重复提交
					innerFrm.oneClick(function(){frm.submit();})
				}
			}
		});
		
		//设置表单值
		frm.form('load',row);
		//阻止表单重复提交
		innerFrm.oneClick(function(){frm.submit();})
	}
	
	//设置为普通员工
	function setNormal(idx){
		//获取选中行的值
		var row = WHdg.getRowData(idx);
		//弹出表单
		normalFrm.openWin();
		normalFrm.setWinTitle("设置普通员工");
		$("#normalDiv").hide();
		//显示提交按钮
		normalFrm.getWinFooter().find("a:eq(0)").show();
		//清空表单值
		$("#normalFrm").form('clear');
		//初始化表单
		var frm = normalFrm.getWinBody().find('form').form({
			url : getFullUrl('./admin/setInner'),
			onSubmit : function(param){
				param.id = row.id;
				//表单验证
				var _validate = $(this).form('enableValidation').form('validate');
				//验证不通过，阻止表单重复提交
				if(!_validate){
					normalFrm.oneClick(function(){frm.submit();});
				}
				return _validate;
			},
			success : function(data){
				var Json = $.parseJSON(data);
				//alert(JSON.stringify(data));
				if(Json && Json.success == "0"){
					$.messager.alert('提示','操作成功');
					//关闭窗体
					normalFrm.closeWin();
				}else{
					$.messager.alert('提示','操作失败');
					//操作失败，阻止表单重复提交
					normalFrm.oneClick(function(){frm.submit();})
				}
			}
		});
		
		//设置表单值
		frm.form('load',row);
		//阻止表单重复提交
		normalFrm.oneClick(function(){frm.submit();})

	}
	
	//查看详情
	function lookUser(idx){
		$("input").attr("readonly", "readonly");
		$("#Form .easyui-combobox").combobox({ disabled: true }); 
		$("#Form .easyui-datetimebox").datetimebox({ disabled: true });
		$("#Form .easyui-textbox").textbox({ disabled: true });
		$("#Form .easyui-filebox").filebox("disable");
		var row = WHdg.getRowData(idx);
		var frm = winform.getWinBody().find('form');
		winform.openWin();
		winform.setWinTitle("查看用户详情");
		//格式化日期
		var _data = $.extend({}, row,{
			birthday : datetimeFMT(row.birthday),
		});
		frm.form("load", _data);
		_showImg(_data);
		winform.getWinFooter().find("a:eq(0)").hide();
	}
	
	//查看详情显示图片
	function _showImg(data){
		$(".img_div").remove();
		$(".img_div2").remove();
		var imgDiv = '<div class="row img_div">'
			+'<div></div>'
			+'<div><img width="200" height="150"> </div> '
			+'</div>';
		var imgDiv2 = '<div class="row img_div2">'
			+'<div></div>'
			+'<div><img width="200" height="150"> </div> '
			+'</div>';
		
		if (data.idcardface){ 
			var imgrow = $("[name$='idcardface']").parents(".row");
			imgrow.after(imgDiv);
			imgrow.next(".img_div").find("div img").attr("src",data.idcardface);	
		}
		if (data.idcardback){
			var imgrow = $("[name$='idcardback']").parents(".row");
			//$(".file_image").filebox('setText',data.actvpic);
			imgrow.after(imgDiv2);
			imgrow.next(".img_div2").find("div img").attr("src",data.idcardback);	
		}
	}
	
	//删除用户信息
	function removeUser(idx) {
		var rows = $("#table_grid").datagrid("getRows");
        var row = rows[idx];
        var id = row.id;
		$.messager.confirm('确认对话框', '准备删除' + id + '', function(r) {
			if (r) {
				$.ajax({
					url : getFullUrl( './admin/deleteUser'),
					data : { id : id },
					type : "POST",
					success : function(data) {
						if (data.success) {
							$(".easyui-datagrid").datagrid("reload");
						} else {
							$.messager.alert("提示", "error");
						}
					}
				})
			}
		});
	}
	//工具栏根据条件查询
	function datagridLoad(){ 
	    var tb = $("#tb");
	    var datagrid=$("#table_grid");
	    var params = {};
	    tb.find("[name]").each(function(){
	    	var value = $(this).val();
	    	var name = $(this).attr("name");
	    	params[name] = value;
	    });
	    this.queryParams = this.queryParams ||{};
	    $.extend(this.queryParams, params);

	    datagrid.datagrid({
	        url :'./admin/loadUser',
	        queryParams : this.queryParams
	    });
	}
	//实名审核点亮处理
	function showCheck(idx){
		var rows = $("#table_grid").datagrid("getRows");
        var row = rows[idx];
        
        var name= row.name;
        var idcard = row.idcard;
        var idcardface = row.idcardface;
        var idcardback = row.idcardback;
        var isrealname = row.isrealname;
        
        return (name && idcard && idcardface && idcardback && (isrealname && isrealname==3));
	}
	
	var realWin = new WhuiWinForm("#real_win",{width:650,height:'65%'});
   // var isInitRealWin = false;
    function initRealWin(){
        //if (!isInitRealWin){
        	realWin.init();
        //}
        //isInitRealWin = true;
    }
    
    //发送实名审核处理
    function sendChedkReal(id, isrealname, checkmsg){
    	$.post(getFullUrl( './admin/checkUserReal'), {id:id, isrealname:isrealname, checkmsg:checkmsg}, function(data){
    		if (data.success){
    			$.messager.alert("提示", "操作成功");
    			$('#table_grid').datagrid('reload');
    			realWin.closeWin();
    		}else{
    			$.messager.alert("提示", "提交处理出错");
    		}
    	}, "JSON");
    }
	
	//实名审核
	function checkUserReal(idx){
		initRealWin();
		realWin.setWinTitle("实名信息");
		realWin.openWin();
		
		var rows = $("#table_grid").datagrid("getRows");
        var row = rows[idx];
        
        var winbody = realWin.getWinBody();
        winbody.find(".data_name").textbox("setValue",row.name);
        winbody.find(".data_idcard").textbox("setValue",row.idcard);
        if (row.checkmsg){
        	winbody.find(".data_checkmsg").parents(".row").show();
        	winbody.find(".data_checkmsg").textbox("setValue",row.checkmsg);
        }else{
        	winbody.find(".data_checkmsg").parents(".row").hide();
        }
        winbody.find(".idcardface").attr("src",row.idcardface);
        winbody.find(".idcardback").attr("src",row.idcardback);
        
        //realWin.oneClick(function(e){ alert(e.data.test)}, {test:"aabbcc"});
        realWin.setFoolterBut({
        	text :"通过实名认证",
			iconCls:"icon-ok",
			width: 120,
        	onClick:function(){ sendChedkReal(row.id, 1, "");}
        },0);
        realWin.setFoolterBut({
        	text :"打回实名认证",
			iconCls:"icon-no",
			width: 120,
        	onClick:function(){
           		$.messager.prompt("打回认证", "请输入打回的消息，点击取消可跳过输入", function(m){
           			var checkmsg = "";
           			if (m){
           				checkmsg = m;
           			}
           			if (checkmsg.length>50){
           				$.messager.alert("提示", "输入的信息过长，已取消发送", 'error');
           				return;
           			}
           			sendChedkReal(row.id, 2, checkmsg);
           		})
	        }
        },1);
        
        //没有权限对按钮处理
        if(!checkon){ realWin.getWinFooter().find("a:eq(0)").hide() }
        if(!checkoff){ realWin.getWinFooter().find("a:eq(1)").hide() }
	}
	
	//没有审核和取消审核的权限时的处理
	var checkon=1, checkoff=1;
	function notCheckon(){
		checkon = 0;
	}
	function notCheckoff(){
		checkoff = 0;
	}
	//检查如果审核和取消审核实名都没权限时，列表中按钮不显示
	function checkRowBut(){
		if (!checkon && !checkoff){
			$(".checkRowBut").hide();
		}
	}
	
	//实名审核列显示状态值转换
	function realState(v,r,i){
		switch (v) {
			case 1: return "已认证";
			case 2: return "已打回";
			case 3: return "待认证";
			default : return "待完善";
		}
	}
	
	return {
		removeUser : removeUser,
		checkUserReal : checkUserReal,
		showCheck : showCheck,
		realState : realState,
		notCheckon : notCheckon,
		notCheckoff : notCheckoff,
		checkRowBut: checkRowBut,
		datagridLoad : datagridLoad,
		lookUser : lookUser,
		setInner : setInner,
		setNormal : setNormal
	}
})()
	
	
$(function(){
	var options = {
           title : "用户列表",
           url : getFullUrl('./admin/selectUser'),
           toolbar : "#tb",
	   	   rownumbers:true,
	   	   singleSelect: true,
           columns : [[
			 { field : 'phone', title : '手机号码', width : 100 }, 
			 { field : 'email', title : '邮箱地址', width : 100 }, 
			 { field : 'openid', title : 'QQ标识', width : 100 },
			 { field : 'wid', title : '微博标识', width : 100 },
			 { field : 'wxopenid', title : '微信标识', width : 100 },
             { field : 'name', title : '姓名', width : 90 }, 
             //{ field : 'nation', title : '民族', width : 60 }, 
             //{ field : 'origo', title : '籍贯', width : 60 }, 
             //{ field : 'sex', title : '性别', width : 40, formatter : function(v, r, i) { return v == 1 ? "男" : "女"; } }, 
			 //{ field : 'password', title : '密码', width : 100 }, 
			 { field : 'nickname', title : '昵称', width : 90 }, 
			 //{ field : 'job', title : '职业', width : 80 }, 
			 { field : 'company', title : '工作单位', width : 90 }, 
			 { field : 'birthday', title : '出生日期', width : 100, formatter:datetimeFMT}, 
			 //{ field : 'address', title : '通讯地址', width : 90 }, 
			 //{ field : 'qq', title : 'qq账号', width : 100 }, 
			 //{ field : 'wx', title : '微信账号', width : 100 }, 
			 { field : 'isrealname', title : '实名认证', width : 60, formatter:tool.realState }, 
			 //{ field : 'lastdate', title : '最新时间', width : 80 }, 
			 { field : 'operate', title : '操作', width : 465, fixed:true, formatter: WHdg.optFMT,optDivId:'row_opt' } 
		 	]]
    }
	
	//查看详情表单的初始化
	winform.init();
	//内部员工表单的初始化
	innerFrm.init();
	//外部员工的初始化
	normalFrm.init();
	WHdg.init('table_grid', 'tb', options);
	
	//无实名审核和审核处消时的列钮不显示
	tool.checkRowBut();
	
 	//search 点击查找用户信息
	var tbSearch = $("#tb").find(".searchBar").off("click");
  		 tbSearch.on("click", function () {
       	 tool.datagridLoad();
    });
  	
  	//显示设置内部员工
	$("#isnormal").combobox({
    	onChange:function(nv,ov){
    		if(nv == 1){
    			$("#normalDiv").show();
    		}else{
	    		$("#normalDiv").hide();
    		}
    	}
    })
    //显示设置普通员工
	$("#inneris").combobox({
    	onChange:function(nv,ov){
    		if(nv == 1){
    			$("#innerDiv").show();
    		}else{
	    		$("#innerDiv").hide();
    		}
    	}
    })
})

/**历史培训*/
function oldtra(index){
	var row = WHdg.getRowData(index);
	var uid = row.id;
	$('#win').window('open');
	$('#win').panel({title:"历史培训"});
	$('#olddg').datagrid({   
		 queryParams: {
		 	uid: uid
		 },
		 url : getFullUrl('/admin/findOldTra'),
		 fit : true,
		 fitColumns:true,
		 rownumbers:true,
		 pagination : true,
		
		 columns : [[ 
			{field:'tratitle',width:100,title:'培训名称'},    
			{field:'trasdate',width:100,title:'培训开始日期',formatter:datetimeFMT},    
			{field:'traedate',width:100,title:'培训结束日期',formatter:datetimeFMT},    
			{field:'enrtype',width:80,title:'团队或个人',formatter:typeFMT},
			{field:'enrisa',width:50,title:'是否完善资料', formatter:enrolllistFMT},
			{field:'enrisb',width:50,title:'是否已上传附件', formatter:enrolllistFMT},
			{field:'enrtime',width:100,title:'报名时间', formatter :datetimeFMT},
		 ]]
	});
}
/**历史活动*/
function oldact(index){
	var row = WHdg.getRowData(index);
	var uid = row.id;
	$('#win').window('open');
	$('#win').panel({title:"历史活动"});
	$('#olddg').datagrid({
		queryParams: {
		 	uid: uid,
		},
        url : getFullUrl('/admin/selOldAct'),
        fit : true,
        fitColumns:true,
        pagination : true,
	    rownumbers:true,
        columns : [[ 
			{field:'actvshorttitle',width:100,title:'活动名称',width:100},    
			/* {field:'actvitmsdate',title:'活动开始时间',width:100,align:'right',formatter:dateFMT},
			{field:'actvitmedate',title:'活动结束时间',width:100,align:'right',formatter:dateFMT}, */
			{field:'actbmtype',title:'团队或个人',width:80,align:'right',formatter:typeFMT},
			{field:'actbmisa',title:'是否完善资料',width:50, formatter:enrolllistFMT, width:100},
			{field:'actbmisb',title:'是否已上传附件',width:50, formatter:enrolllistFMT, width:100},
			{field:'ismoney',title:'是否需要收费',width:50,width:100,formatter:yesNoFMT},
			{field:'actbmtime',title:'报名时间',width:100, formatter :datetimeFMT, width:100},
		]]
	});
}
/**历史场馆*/
function oldven(index){
	var row = WHdg.getRowData(index);
	var uid = row.id;
	$('#win').window('open');
	$('#win').panel({title:"历史场馆"});
	$('#olddg').datagrid({ 
		queryParams: {uid: uid},
		url : getFullUrl('/admin/selOldVen'),
		fit : true,
		fitColumns:true,
		rownumbers:true,
		pagination : true,
		columns : [[ 
			{field:'venname',width:100,title:'场馆名称'},
			{field:'vencontact',width:100,title:'场馆联系人'},
			{field:'vencontactnum',width:100,title:'场馆联系方式'},
			{field:'vebday',width:100,title:'预定时间', formatter: dateFMT},
			{field:'vebstime',width:100,title:'预定时段开始时间'},
			{field:'vebetime',width:100,title:'预定时段结束时间'},
		]]
	 });
}
	
</script>
</head>
<body>
	<shiro:lacksPermission name="${resourceid}:checkon"><script type="text/javascript"> tool.notCheckon() </script></shiro:lacksPermission>
	<shiro:lacksPermission name="${resourceid}:checkoff"><script type="text/javascript"> tool.notCheckoff() </script></shiro:lacksPermission>
	
	<!-- 工具栏 -->
	<div id="tb" class="grid-control-group">
		<div>
			<input class="easyui-textbox" name="name"  data-options="prompt:'请输入姓名',width:100,validType:'length[1,30]'"/>
			<input class="easyui-textbox" name="nickname"  data-options="prompt:'请输入昵称',width:100,validType:'length[1,30]'"/>
			<input class="easyui-textbox" name="origo"  data-options="prompt:'请输入籍贯',width:100,validType:'length[1,30]'"/>
			<input class="easyui-textbox" name="phone"   data-options="prompt:'请输入手机',width:100,validType:'length[1,30]'"/>
			<input class="easyui-textbox" name="email"  data-options="prompt:'请输入邮箱',width:100,validType:'length[1,30]'"/>
			<!-- <input id="traedate" name="sex" type="text" class="easyui-datetimebox" data-options="editable:false" style="height: 35px; width: 16%"></input> -->
			<select class="easyui-combobox" name="isrealname" data-options="prompt:'请选择状态',value:'',width:100">
				<option value="0">待完善</option>
				<option value="1">已认证</option>
				<option value="2">已打回</option>
				<option value="3">待认证</option>
			</select>
			<a href="javascript:void(0)" class="easyui-linkbutton searchBar" iconCls="icon-search">查询</a>
		</div>
	</div>
	
	<!-- 数据表格 -->
	<table class="easyui-datagrid" id="table_grid"></table>
	
	<!-- 操作栏 -->
	<div id="row_opt" style="display: none">
		<a href="javascript:void(0)" class="checkRowBut" validFun="tool.showCheck" method="tool.checkUserReal">实名审核</a>
		<shiro:hasPermission name="${resourceid}:del"><a href="javascript:void(0)" method="tool.removeUser">删除</a></shiro:hasPermission>
		<shiro:hasPermission name="${resourceid}:view"><a href="javascript:void(0)" method="tool.lookUser">查看详情</a></shiro:hasPermission>
		<a href="javascript:void(0)" validKey="isinner" validVal="1"  method="tool.setInner">内部账户</a>
		<a href="javascript:void(0)" validKey="isinner" validVal="0" method="tool.setNormal">普通账户</a>
		<a href="javascript:void(0)" method="oldact">历史活动</a>
		<a href="javascript:void(0)" method="oldtra">历史培训</a>
		<a href="javascript:void(0)" method="oldven">历史场馆</a>
		
	</div>
	
	<!-- 设置内部员工 -->
	<div id="innerFrm" class="none" style="display: none">
		<form method="post" enctype="multipart/form-data" id="innerForm">
		<div id="innerDiv">
			<label>内部员工描述:</label>
			<input class="easyui-textbox innerdesc" data-options="multiline:true" name="innerdesc" style="width: 90%; height: 80px"/>
		</div>
		<div>
			<label>是否置为普通账户:</label>
		</div>
		<div>
			<select id="inneris" class="easyui-combobox isinner" name="isinner" style="height: 35px; width: 90%">
				<option value="0">是</option>
				<option value="1">否</option>
			</select>
		</div>
		</form>
	</div>
	
	<!-- 设置普通账户 -->
	<div id="normalFrm" class="none" style="display: none">
		<form method="post" enctype="multipart/form-data">
		<div>
			<label>是否置为内部账户:</label>
		</div>
		<div>
			<select id="isnormal" class="easyui-combobox isinner" name="isinner" style="height: 35px; width: 90%" data-options="editable:false">
				<option value="0">否</option>
				<option value="1">是</option>
			</select>
		</div>
		<div id="normalDiv">
			<label>内部员工描述:</label>
			<input class="easyui-textbox innerdesc" data-options="multiline:true" name="innerdesc" style="width: 90%; height: 80px"/>
		</div>
		</form>
	</div>
	
	
	<!--查看详情操作-->
	<div id="frm" class="none" style="display: none" data-options="maximized:true">
		<form method="post" enctype="multipart/form-data" id="Form">
		<input type="hidden" id="cardface" name="idcardface"/>
		<input type="hidden" id="cardback" name="idcardback"/>
			<div class="main" id="mobandaor">
				<div class="row">
					<div>
						<label>姓名:</label>
					</div>
					<div>
						<input name="name" class="easyui-textbox name" data-options="validType:'length[0,30]',required:true" style="height: 35px; width: 90%"/>
					</div>
				</div>
				<div class="row">
					<div>
						<label>昵称:</label>
					</div>
					<div>
						<input name="nickname" class="easyui-textbox" data-options="validType:'length[0,64]'" style="height: 35px; width: 90%"/>
					</div>
				</div>
				<div class="row">
						<div>
							<label>性别:</label>
						</div>
						<div>
							<select class="easyui-combobox sex" name="sex" style="height: 35px; width: 90%" data-options="editable:false">
								<option value="0">女</option>
								<option value="1">男</option>
							</select>
						</div>
				</div>
				<div class="row">
					<div>
						<label>电话号码:</label>
					</div>
					<div>
						<input name="phone" class="easyui-textbox" data-options="validType:'length[0,64]'" style="height: 35px; width: 90%"/>
					</div>
				</div>
				<div class="row">
					<div>
						<label>电子邮件:</label>
					</div>
					<div>
						<input name="email" class="easyui-textbox" data-options="validType:'length[0,64]'" style="height: 35px; width: 90%"/>
					</div>
				</div>
				<div class="row">
					<div>
						<label>职业:</label>
					</div>
					<div>
						<input name="job" class="easyui-textbox" data-options="validType:'length[0,64]'" style="height: 35px; width: 90%"/>
					</div>
				</div>
				<div class="row">
					<div>
						<label>qq账号:</label>
					</div>
					<div>
						<input name="qq" class="easyui-textbox" data-options="validType:'length[0,64]'" style="height: 35px; width: 90%"/>
					</div>
				</div>
				<div class="row">
					<div>
						<label>微信账号:</label>
					</div>
					<div>
						<input name="wx" class="easyui-textbox" data-options="validType:'length[0,64]'" style="height: 35px; width: 90%"/>
					</div>
				</div>
				<div class="row">
					<div>
						<label>民族:</label>
					</div>
					<div>
						<input name="nation" class="easyui-textbox" data-options="validType:'length[0,64]'" style="height: 35px; width: 90%"/>
					</div>
				</div>
				<div class="row">
					<div>
						<label>籍贯:</label>
					</div>
					<div>
						<input name="origo" class="easyui-textbox" data-options="validType:'length[0,64]'" style="height: 35px; width: 90%"/>
					</div>
				</div>
				<div class="row">
					<div>
						<label>工作单位:</label>
					</div>
					<div>
						<input name="company" class="easyui-textbox" data-options="validType:'length[0,64]'" style="height: 35px; width: 90%"/>
					</div>
				</div>
				<div class="row">
					<div>  
						<label>通讯地址:</label>
					</div>
					<div>
						<input class="easyui-textbox" name="address" data-options="validType:'length[0,128]',required:true" style="height:35px;width: 90%" required=true/>
					</div>
				</div>
				<div class="row">
					<div>
						<label>出生日期:</label>
					</div>
					<div>
						<input name="birthday" id="birthday" class="easyui-textbox" style="height: 35px; width: 90%" />
					</div>
				</div>
				<div class="row">
					<div>
						<label>身份证号码:</label>
					</div>
					<div>
						<input name="idcard" class="easyui-textbox" data-options="validType:'length[0,64]'" style="height: 35px; width: 90%"/>
					</div>
				</div>
				<div class="row">
					<div>
						<label>身份证正面:</label>
					</div>
					<div>
						<input class="easyui-filebox file_idcardface" name="idcardface" style="height:35px;width: 90%"/>
					</div>
				</div>
				<div class="row">
					<div>
						<label>身份证反面:</label>
					</div>
					<div>
						<input class="easyui-filebox file_idcardback" name="idcardback" style="height:35px;width: 90%"/>
					</div>
				</div>
				<div class="row">
						<div>
							<label>是否实名:</label>
						</div>
						<div>
							<select class="easyui-combobox isrealname" name="isrealname" style="height: 35px; width: 90%" data-options="editable:false">
								<option value="0">否</option>
								<option value="1">是</option>
							</select>
						</div>
				</div>
				<div class="row">
						<div>
							<label>是否完善资料:</label>
						</div>
						<div>
							<select class="easyui-combobox isperfect" name="isperfect" style="height: 35px; width: 90%" data-options="editable:false">
								<option value="0">否</option>
								<option value="1">是</option>
							</select>
						</div>
				</div>
				<div class="row">
						<div>
							<label>是否为内部员工:</label>
						</div>
						<div>
							<select class="easyui-combobox isinner" name="isinner" style="height: 35px; width: 90%" data-options="editable:false">
								<option value="0">否</option>
								<option value="1">是</option>
							</select>
						</div>
				</div>
				<div class="row">
					<div>
						<label>个人简介:</label>
					</div>
					<div>
						<input class="easyui-textbox" data-options="multiline:true,validType:'length[0,128]'" name="resume" style="width: 90%; height: 180px"/>
					</div>
				</div>
				<div class="row">
					<div>
						<label>从事文艺活动简介:</label>
					</div>
					<div>
						<div style="width:90%">
							<input class="easyui-textbox actbrief" data-options="multiline:true" name="actbrief" style="width: 90%; height: 180px"/>
						</div>
					</div>
				</div>
				<div class="row">
					<div>
						<label>实名审核被打回的消息:</label>
					</div>
					<div>
						<input class="easyui-textbox checkmsg" data-options="multiline:true" name="checkmsg" style="width: 90%; height: 80px"/>
					</div>
				</div>
				
				<div class="cls"></div>
				</div>
		</form>
	</div>	
	
	<!-- 用户实名 -->
	<div id="real_win" style="display:none"  >
		<div class="main">
        	<div class="row">
        		<div>姓名：</div>
        		<div><input class="easyui-textbox data_name" data-options="readonly:true" style="width:310px; height:35px"></div>
        	</div>
        	<div class="row">
        		<div>身份证号：</div>
        		<div><input class="easyui-textbox data_idcard" data-options="readonly:true" style="width:310px; height:35px"></div>
        	</div>
        	<div class="row">
        		<div>身份证正面：</div>
        		<div><img class="idcardface" width="310" height="160" /></div>
        	</div>
        	<div class="row">
        		<div>身份证反面：</div>
        		<div><img class="idcardback" width="310" height="160" /></div>
        	</div>
        </div>
	</div>
	
	
	<!-- 历史培训 -->
	<div id="win" class="easyui-window" closed="true" fit="true"  data-options="iconCls:'icon-save',modal:true"> 
		<table id="olddg" ></table>
	</div>
	
</body>
</html>