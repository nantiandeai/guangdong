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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<base href="${basePath }/">
<%@include file="/pages/comm/header.jsp"%>
<script>
	function go_back() {
		window.location.href = './admin/selAllTag';
	}
	/*
	 * 添加和修改
	 */
	$(function() {
		var id = "${id}";
		var url='./admin/addTag';
		
		if (id == "") {

			$("#btn").on("click", function() {

				var name = $("#cc").combobox('getText');
				var type = $("#cc1").combobox('getText');
				var idx = $("#idx").textbox('getValue');
				$.ajax({
					type : "post",
					url : "./admin/addTag",
					data : {
						name : name,
						type : type,
						idx : idx
					},
					success : function(data) {
						if (data.success) {
							go_back();
						} else {

						}
					}
				})
			})
		} else {
			$("#btn").on("click", function() {

				var name = $("#cc").combobox('getText');
				var type = $("#cc1").combobox('getText');
				var idx = $("#idx").textbox('getValue');
				$.ajax({
					type : "post",
					url : "./admin/updateTag",
					data : {
						id : id,
						name : name,
						type : type,
						idx : idx
					},
					success : function(data) {
						if (data.success) {
							go_back();
						} else {

						}
					}
				})
			})
		}
	})
	
	
	/*
 * 添加和修改
 */
$(function() {
	
	
	//修改
	var id = "${id}";
	var url = './admin/addTag';
	if(id != ''){
		url = './admin/updateTag';
	}
	
	//添加
	$('#btn_ok').bind('click', function(){
		$('#ff').form('submit', {    
		    url: url,
		    success:function(data){
		    		$('#btn_ok').linkbutton('disable');
			    	 $.messager.alert("提示","操作成功"); 
		    	} 
		});
	});
	})
</script>
</head>
<style type="text/css">
.easyui-combobox, .easyui-filebox, .easyui-textbox,.easyui-combotree,.easyui-numberbox {
	width: 60%;
}

.radio {
	width: 30%;
}

table {
	margin-top: 5px;
}

table tbody tr td {
	padding: 2px;
}
</style>
</head>
<body>

	<div class="easyui-panel" title="${title }" data-options="fit:true">
		<div style="width: 70%; margin: 0 auto;">
			<form id="ff" method="post" enctype="multipart/form-data"
				action="${basePath}/admin/traintpl/savetratpl">
				<input type="hidden" id="traid" name="traid" value="" /> <input
					type="hidden" id="teacherid" name="teacherid" value="123" /> <input
					type="hidden" id="tradesc" name="tradesc" value="" />
				<table cellpadding="5">
					<tbody>
						<tr>
							<td style="width: 100px;">标签类型:</td>
							<td><select id="tagid" class="easyui-combotree" name="id"
								style="height: 32px;"
								data-options="url:'${basePath}/admin/inquire?type=${type}',required:true,validType:'length[0,7]'">
								</select></td>
							
						</tr>
						<tr>
							<td>标签名字:</td>
							<td>
							<input name="name" class="easyui-textbox" value="${whtag.name }" style="height: 32px;" type="text" data-options="required:true, validType:'length[0,10]'" />
							</td>
						</tr>
						<tr>
							<td>标签排序:</td>
							<td><input class="easyui-numberbox" name="agelevel" value="${whtag.idx}"
						       style="height: 32px;"data-options="required:true,validType:'length[0,7]'" />
							</td>
						</tr>
					</tbody>
				</table>
				<div style="text-align: center; padding: 5px">
					<a href="#" class="easyui-linkbutton"
						style="width: 100px; height: 32px" iconCls="icon-ok"
						onclick="submitForm();">提交保存</a> <a href="#"
						class="easyui-linkbutton" style="width: 100px; height: 32px"
						onclick="history.go(-1);">返回</a>
				</div>
			</form>
		</div>

	</div>
</html>