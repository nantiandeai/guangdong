<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<% String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath(); request.setAttribute("basePath", basePath); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<base href="${basePath }/">
<%@include file="/pages/comm/header.jsp"%>
<script>
function go_back(){
	window.location.href = './admin/selAllkey';
}
/*
 * 添加和修改
 */
$(function(){
	var id="${id}";
	if (id == "") {
		
	
	$("#btn").on("click",function(){
		
			var name = $("#cc").combobox('getText');
			var type = $("#cc1").combobox('getText');
			var idx = $("#idx").textbox('getValue');
		$.ajax({
			type: "post",
			url: "./admin/addKey",
			data: {name:name,type:type,idx:idx},
			success: function(data){
				if (data.success) {
					   go_back();
				} else {
					
				}	  
			}
		})
	})
	}else{
		$("#btn").on("click",function(){
			
			var name = $("#cc").combobox('getText');
			var type = $("#cc1").combobox('getText');
			var idx = $("#idx").textbox('getValue');
		$.ajax({
			type: "post",
			url: "./admin/updateKey",
			data: {id:id,name:name,type:type,idx:idx},
			success: function(data){
				if (data.success) {
					
					   go_back();
				} else {
					
					}	  
				}
			})
		})
	}
})

</script>
</head>
<body>

	<div class="container-fluid">
		<div class="row">
			<div class="commTt">${title }</div>
		</div>
		<div class="formContainer">
			<form class="form-horizontal" role="form">
				<div class="form-group">
					<label for="inputEmail3" class="col-md-2 control-label">关键字名字</label>
					<div class="col-md-10">
					<!--  	<input type="email" class="form-control input-lg" id="inputEmail3"
							placeholder="Email">-->
					<select id="cc" class="easyui-combobox" name="dept1" style="width:400px; height:50px">   
					    <option value="name">${whKey.name == null?"a":whKey.name}</option>   
					    <option >b</option>   
					    <option >c</option>   
					    <option >d</option>   
					    <option >e</option>   
					</select>
					</div>
				</div>
				<div class="form-group">
					<label for="inputPassword3" class="col-md-2 control-label">关键字类型</label>
					<div class="col-md-10">
					<!--  	<input type="password" class="form-control input-lg"
							id="inputPassword3" placeholder="Password"> -->
					<select id="cc1" class="easyui-combobox" name="dept2" style="width:400px; height:50px">   
					    <option value="type">${whKey.type == null?"a":whKey.type}</option>   
					    <option >比赛</option>   
					    <option >bitem3</option>   
					    <option >ditem4</option>   
					    <option >eitem5</option>   
					</select>
					</div>
				</div>
				
				<div class="form-group">
					<label for="inputPassword3" class="col-md-2 control-label">关键字序号</label>
					<div class="col-md-10">
					<!--  	<input type="password" class="form-control input-lg"
							id="inputPassword3" placeholder="Password"> -->
					<input id="idx" class="easyui-textbox" value="${whKey.idx}"  style="width:400px; height:50px">
					
					</div>
				</div>
				<div class="form-group">
					<div class="col-md-offset-2 col-md-10">
						<button type="button" class="btn btn-warning"  id="btn">提交</button>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<button type="button" class="btn btn-default" onclick="history.go(-1);">返回</button>
					</div>
				</div>
			</form>
		</div>
	</div>

</body>
</html>