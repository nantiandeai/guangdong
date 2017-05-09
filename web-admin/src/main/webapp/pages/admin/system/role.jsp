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
	<script type="text/javascript">
		/** 表单对象 */
		var roleForm;

		/** 页面加载完成后的事件处理 */
		$(function() {
			//定义表格参数
			var options = {
				title: '角色管理',
				url: '${basePath }/admin/loadRoleList',
				//sortName: 'artid',
				//sortOrder: 'desc',
				rownumbers: true,
				singleSelect: true,
				//queryParams: {stateArray:"1"},
				columns: [[
					{field:"name", title: "角色", width:150},
					{field:"state", title: "状态", width:100, sortable:true, formatter:onOffFMT},
					{field:'opt', title:'操作', width:200, formatter: WHdg.optFMT, optDivId:'zxOPT'}
				]]
			};

			//初始表格
			WHdg.init('roelList', 'zxTB', options);

			//add
			$('#btn_addzx').on('click.wxl', goAdd);

			//init roleForm
			roleForm = new WhuiWinForm("#role_form_div", {height:600});
			roleForm.init();
		});/** 页面加载完成后的事件处理-END */

		/** 角色不能重复验证 */
		$.extend($.fn.validatebox.defaults.rules, {
			rolenameValid: {
				validator: function(value, param){
					$.fn.validatebox.defaults.rules.rolenameValid.message = $('#errfieldmsg').val();
					return $('#errfield').val() == '';
				},
				message: ''
			}
		});


		/** 删除角色 */
		function doDel(index) {
			var row = WHdg.getRowData(index);
			$.messager.confirm('确认对话框', '您想要删除此记录吗？', function(r) {
				if (r) {
					$.ajax({
						url : '${basePath}/admin/delRole',
						data : {id : row.id},
						success : function(data) {
							if (data.success == '0') {
								$("#roelList").datagrid("reload");
							} else {
								$.messager.alert("提示", "操作失败："+data.errmsg, 'error');
							}
						}
					})
				}
			});

		};

		/** 启用角色 */
		function doOn(index) {
			var row = WHdg.getRowData(index);
			updataState(row.id, '1');
		};


		/** 停用角色 */
		function doOff(index) {
			var row = WHdg.getRowData(index);
			updataState(row.id, '0');

		};

		/** 修改角色状态 */
		function updataState(id, state){
			$.ajax({
				url : '${basePath}/admin/updRoleState',
				data : {id : id, state: state},
				success : function(data) {
					if (data.success == '0') {
						$("#roelList").datagrid("reload");
					} else {
						$.messager.alert("提示", "操作失败："+data.errmsg, 'error');
					}
				}
			});
		}

		/** 根据角色标识取权限  */
		function srchRolePMS(id){
			var opts;
			$.ajax({
				url : '${basePath}/admin/srchRolePMS',
				data : {id : id},
				async: false,
				success : function(data) {
					opts = data;
				}
			});
			return opts;
		}


		/** 打开查看表单 */
		function doSee(index){
			var row = WHdg.getRowData(index);

			//弹出编辑表单
			roleForm.setWinTitle("查看角色信息");
			roleForm.openWin();

			//初始权限编辑表格
			$('#tdg_menu').treegrid({
				url: '${basePath}/admin/loadMenus?type=off',
				idField: 'id',
				treeField: 'text',
				fitColumns: false,
				singleSelect: true,
				columns:[[
					{field:'text', title:'权限资源', width:150},
					{field:'opts', title:'支持操作', formatter: pmsFMT}
				]]
			});

			window.setTimeout(function(){
				//全选与取消
				$('#chioceAll').off('click.wxl').hide();
				$('#unchioceAll').off('click.wxl').hide();

				//清除表单值
				$('#role_form').form('clear');
				chioceAll(false);

				//设置角色信息和权限信息
				$('#roleid').val(row.id);
				$('#rolename').textbox('setValue', row.name);
				$('#rolestate').val(row.state);
				var opts = srchRolePMS(row.id);
				if(opts){
					parsePMS2Form(row.id, opts);
				}

				//表单提交按钮
				roleForm.getWinFooter().find("a:eq(0)").hide();

				//
				pmsOnChange();
			}, 200);

		}

		/** 打开编辑表单 */
		function doUpd(index){
			var row = WHdg.getRowData(index);

			//弹出编辑表单
			roleForm.setWinTitle("编辑角色");
			roleForm.openWin();

			//初始权限编辑表格
			$('#tdg_menu').treegrid({
				url: '${basePath}/admin/loadMenus?type=off',
				idField: 'id',
				treeField: 'text',
				fitColumns: false,
				singleSelect: true,
				columns:[[
					{field:'text', title:'权限资源', width:150},
					{field:'opts', title:'支持操作', formatter: pmsFMT}
				]]
			});
			window.setTimeout(function(){
				//全选与取消
				$('#chioceAll').show().off('click.wxl').on('click.wxl', function(){
					chioceAll(true);
				});
				$('#unchioceAll').show().off('click.wxl').on('click.wxl', function(){
					chioceAll(false);
				});

				//清除表单值
				$('#role_form').form({
					url : '${basePath}/admin/updateRole',
					onSubmit : function(param){
						param.id = $('#roleid').val();
						param.name = $('#rolename').textbox('getValue');
						param.state = $('#rolestate').val();

						var isOK = $(this).form('enableValidation').form('validate');
						if(!isOK){
							roleForm.oneClick(function(){ $('#role_form').submit(); });
						}else{
							$.messager.progress();
						}

						return isOK;
					},
					success : function(data) {
						$.messager.progress('close');

						var Json = $.parseJSON(data);
						if(Json && Json.success == '0'){
							$.messager.alert('提示', '操作成功!');
							$('#roelList').datagrid('reload');
							roleForm.closeWin();
						}else{
							if(Json.errfield != ''){
								$('#errfield').val(Json.errfield);
								$('#errfieldmsg').val(Json.errmsg);
								$('#role_form').form('validate');
								$('#errfield').val('');
							}
							$.messager.alert('提示', '操作失败:'+Json.errmsg, 'error');
						}
					}
				});
				$('#role_form').form('clear');
				chioceAll(false);

				//设置角色信息和权限信息
				$('#roleid').val(row.id);
				$('#rolename').textbox('setValue', row.name);
				$('#rolestate').val(row.state);
				var opts = srchRolePMS(row.id);
				if(opts){
					parsePMS2Form(row.id, opts);
				}

				//表单提交按钮
				roleForm.getWinFooter().find("a:eq(0)").show();
				roleForm.oneClick(function(){ $('#role_form').submit(); });

				//改变color
				pmsOnChange();
			}, 200);
		}

		/** 打开编辑表单 */
		function goAdd(){
			//弹出编辑表单
			roleForm.setWinTitle("添加角色");
			roleForm.openWin();

			//初始权限编辑表格
			$('#tdg_menu').treegrid({
				url: '${basePath}/admin/loadMenus?type=off',
				idField: 'id',
				treeField: 'text',
				fitColumns: false,
				singleSelect: true,
				columns:[[
					{field:'text', title:'权限资源', width:150},
					{field:'opts', title:'支持操作', formatter: pmsFMT}
				]]
			});

			window.setTimeout(function(){
				//全选与取消
				$('#chioceAll').show().off('click.wxl').on('click.wxl', function(){
					chioceAll(true);
				});
				$('#unchioceAll').show().off('click.wxl').on('click.wxl', function(){
					chioceAll(false);
				});

				//清除表单值
				$('#role_form').form({
					url : '${basePath}/admin/addRole',
					onSubmit : function(param){

						param.name = $('#rolename').textbox('getValue');
						param.state = '1';

						var isOK = $(this).form('enableValidation').form('validate');
						if(!isOK){
							roleForm.oneClick(function(){ $('#role_form').submit(); });
						}else{
							$.messager.progress();
						}

						return isOK;
					},
					success : function(data) {
						$.messager.progress('close');

						var Json = $.parseJSON(data);
						if(Json && Json.success == '0'){
							$.messager.alert('提示', '操作成功!');
							$('#roelList').datagrid('reload');
							roleForm.closeWin();
						}else{
							if(Json.errfield != ''){
								$('#errfield').val(Json.errfield);
								$('#errfieldmsg').val(Json.errmsg);
								$('#role_form').form('validate');
								$('#errfield').val('');
							}
							$.messager.alert('提示', '操作失败:'+Json.errmsg, 'error');
							roleForm.oneClick(function(){ $('#role_form').submit(); });
						}
					}
				});
				$('#role_form').form('clear');
				chioceAll(false);

				//表单提交按钮
				roleForm.getWinFooter().find("a:eq(0)").show();
				roleForm.oneClick(function(){
					$('#role_form').submit();
				});

				//改变color
				pmsOnChange();
			}, 200);
		}

		/** 根据pms字符串设置权限选中情况 */
		function parsePMS2Form(id, val){
			if(val){
				var pmsArr = val;
				if(pmsArr.length > 0){
					for(var i=0; i<pmsArr.length; i++){
						var pms = pmsArr[i];
						var pmsid = pms.rpmstr;
						pmsid = pmsid.replace(':', '-');
						$("input[type='checkbox'][id='"+pmsid+"']").prop("checked", true);
					}
				}
			}
		}


		/** 格式化权限 操作 */
		function pmsFMT(val, data, idx){
			var html = '';
			if(val){
				var pmsArr = $.parseJSON(val);
				if(pmsArr.length > 0){
					html += '<label><input type="checkbox" name="pmsAll" value="0" id="pmsAll-'+data.id+'" onchange="chiocePMS(\''+data.id+'\');" />全选</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
					for(var i=0; i<pmsArr.length; i++){
						var pms = pmsArr[i];
						var pmsid = pms.id;
						var pmstext = pms.text;

						html += '<label><input type="checkbox" rcid="'+data.id+'" name="pms" id="'+data.id+'-'+pmsid+'" value="'+data.id+':'+pmsid+'" />'+pmstext+'</label>&nbsp;&nbsp;';
					}
				}

			}
			return html;
		}

		/** 单个资源全选 */
		function chiocePMS(rcid){
			if( $('#pmsAll-'+rcid).prop("checked") ){
				$('#pmsAll-'+rcid).parents('td').find("input[type='checkbox'][id^='"+rcid+"']").prop("checked", true);
			}else{
				$('#pmsAll-'+rcid).parents('td').find("input[type='checkbox'][id^='"+rcid+"']").prop("checked", false);
			}
			$("input[type='checkbox'][name!='pmsAll']:checked").parent('label').css('color', 'red');
			$("input[type='checkbox'][name!='pmsAll']").not(':checked').parent('label').css('color', '');
		}

		/** 所有资源全选与取消 */
		function chioceAll(checked){
			if(checked){
				$("input[type='checkbox'][name='pms']").prop('checked', true);
				$("input[type='checkbox'][name='pmsAll']").prop('checked', true);
			}else{
				$("input[type='checkbox'][name='pms']").prop('checked', false);
				$("input[type='checkbox'][name='pmsAll']").prop('checked', false);
			}
			$("input[type='checkbox'][name!='pmsAll']:checked").parent('label').css('color', 'red');
			$("input[type='checkbox'][name!='pmsAll']").not(':checked').parent('label').css('color', '');
		}


		function pmsOnChange(){
			$("input[type='checkbox'][name!='pmsAll']:checked").parent('label').css('color', 'red');
			$("input[type='checkbox'][name!='pmsAll']").change(function(){

				if( $(this).prop('checked') ){
					$(this).parent('label').css('color', 'red');
				}else{
					$(this).parent('label').css('color', '');
				}
			});
		}

	</script>
</head>
<body>
<!-- 表格 -->
<div id="roelList"></div>

<!-- datagrid toolbar -->
<div id="zxTB" class="grid-control-group">
    <shiro:hasPermission name="${resourceid}:add"><a href="javascript:void(0)" class="easyui-linkbutton" plain="true" iconCls="icon-add" id="btn_addzx">添加</a></shiro:hasPermission>
</div>

<!-- 操作按钮 -->
<div id="zxOPT" style="display: none;">
	<shiro:hasPermission name="${resourceid}:view"><a href="javascript:void(0)" method="doSee">查看</a></shiro:hasPermission>
	<shiro:hasPermission name="${resourceid}:edit"><a href="javascript:void(0)" method="doUpd">修改</a></shiro:hasPermission>
	<shiro:hasPermission name="${resourceid}:on"><a href="javascript:void(0)" validKey="state" validVal="1" method="doOff">停用</a> </shiro:hasPermission>
	<shiro:hasPermission name="${resourceid}:off"><a href="javascript:void(0)" validKey="state" validVal="0" method="doOn">启用</a> </shiro:hasPermission>
	<shiro:hasPermission name="${resourceid}:del"><a href="javascript:void(0)" validKey="state" validVal="0" method="doDel">删除</a></shiro:hasPermission>
</div>

<!-- 角色编辑 -->
<div id="role_form_div" style="display: none" data-options="fit:true">
	<form id="role_form" method="post">
		<input type="hidden" id="roleid" name="roleid" />
		<input type="hidden" id="rolestate" name="rolestate" />
		<input type="hidden" id="errfield" name="errfield" />
		<input type="hidden" id="errfieldmsg" name="errfieldmsg" />
		<div class="main">
			<div class="row">
				<div>角色名称:</div>
				<div>
					<input class="easyui-textbox" id="rolename" name="rolename" data-options="required:true, validType:['length[4,20]', 'rolenameValid']" style="width:80%;height:32px;"/>
				</div>
			</div>
			<div class="row">
				<div>角色权限:</div>
				<div>
					<table id="tdg_menu" style="width:80%; height:400px"></table>
					<a href="javascript:void(0)" class="easyui-linkbutton" style="width:100px;height:32px;" id="chioceAll" >全&nbsp;&nbsp;选</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" style="width:100px;height:32px;" id="unchioceAll">取&nbsp;&nbsp;消</a>
				</div>
			</div>
		</div>
	</form>
</div>
<!-- 角色编辑-END -->
</body>
</html>