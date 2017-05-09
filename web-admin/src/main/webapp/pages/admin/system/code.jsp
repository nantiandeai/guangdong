<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath();
	request.setAttribute("basePath", basePath);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/pages/comm/header.jsp"%>
<base href="${basePath}/">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	var tool = (function () {
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
				url :'./admin/loadMsg',
				queryParams : this.queryParams
			});
		}
		return{
			datagridLoad : datagridLoad
		}
	})()
	/**表格操作栏 */
	function optFun(val, rowData, index) {
		return '<a href="javascript:void(0)" onclick="removeCode(\''
				+ rowData.id + '\');">删除</a>';
	}
	$(function() {
		//对表格的处理
		var tableGrid = $(".easyui-datagrid");
		tableGrid.datagrid({
			url : getFullUrl('./admin/selectCode'),
			columns : [ [ {
				field : 'msgphone',
				title : '手机号码',
				width : 100
			}, {
				field : 'msgtime',
				title : '发送时间',
				width : 100,
				formatter: function(v){
					if (!v) return '';
					return new Date(v).Format('yyyy-MM-dd hh:mm:ss')
				}
			}, {
				field : 'msgcontent',
				title : '手机验证码',
				width : 100
			}, {
				field : 'emailaddr',
				title : '邮箱地址',
				width : 100
			}, {
				field : 'emailcode',
				title : '邮箱验证码',
				width : 100
			}, {
				field : 'emailState',
				title : '邮箱状态',
				width : 100
			}, {
				field : 'operate',
				title : '操作',
				width : 100,
				formatter : optFun
			} ] ],
			singleSelect : true,
			fitColumns : true,
			pagination : true,
			fit : true,
			rownumbers : true,
			toolbar : "#tb",
			title: '验证码管理'
		});

		//search 点击查找用户信息
		var tbSearch = $("#tb").find(".searchBar").off("click");
		tbSearch.on("click", function () {
			tool.datagridLoad();
		});
		//分页页面控制
		$(".easyui-datagrid").datagrid('getPager').pagination({
			pageList : [ 10, 20, 30, 40, 50, 100 ],
			pageSize : 30
		});

	});

	/**
	 * 删除短信
	 */
	function removeCode(id) {
		$.messager.confirm('确认对话框', '准备删除' + id + '', function(r) {
			if (r) {
				$.ajax({
					url : getFullUrl('./admin/deleteCode'),
					data : {
						id : id
					},
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
</script>
</head>
<body>
	<div id="tb" class="grid-control-group">
		手机：
		<input class="easyui-textbox" name="msgphone" data-options="width:100,validType:'length[1,30]'"/>
		邮箱：
		<input class="easyui-textbox" name="emailaddr" data-options="width:100,validType:'length[1,30]'"/>
		开始时间：
		<input class="easyui-datebox" name="msgtime_s" data-options=""/>到<input class="easyui-datebox" name="msgtime_e" data-options=""/>
		<a href="javascript:void(0)" class="easyui-linkbutton searchBar" plain="true"
			iconCls="icon-search">查询</a>
	</div>

	<table class="easyui-datagrid" id="table_grid"></table>
</body>
</html>