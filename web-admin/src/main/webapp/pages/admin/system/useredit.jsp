<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath();
	request.setAttribute("basePath", basePath);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/pages/comm/header.jsp"%>
<base href="${basePath}/" />
<title>Insert title here</title>
<script type="text/javascript">
/**
 * 返回
 */
function go_back() {
	window.location.href = '${basePath}/admin/user';
}
/**
 * 添加用户信息
 */
$("document").ready(function() {

	$("#addBtn").on("click", function() {
		//alert(123);
		var param = $("form").serialize();
		$.ajax({
			type : "POST",
			url : getFullUrl( './admin/addUser'),
			data : param,
			success : function(data){
				if (data=="success") {
					go_back();
				}
			}
		});
	});

});
</script>
</head>
<body>
	<div class="container-fluid">
		<div class="row">
			<div class="commTt">添加用户信息</div>
		</div>
		<div class="formContainer">
			<form class="form-horizontal" role="form">
				<div class="form-group">
					<label for="inputText" class="col-md-2 control-label">姓名</label>
					<div class="col-md-10">
						<input type="text" class="form-control input-lg" id="inputName"
							placeholder="name" name="name" value="${user.name}">
					</div>
				</div>
				<div class="form-group">
					<label for="inputText" class="col-md-2 control-label">昵称</label>
					<div class="col-md-10">
						<input type="text" class="form-control input-lg" id="nickname"
							placeholder="nickname" name="nickname" value="${user.nickname}">
					</div>
				</div>
				<div class="form-group">
					<label for="inputText" class="col-md-2 control-label">电话</label>
					<div class="col-md-10">
						<input type="text" class="form-control input-lg" id="inputPhone"
							placeholder="phone" name="phone" value="${user.phone}">
					</div>
				</div>
				<div class="form-group">
					<label for="inputEmail3" class="col-md-2 control-label">邮箱</label>
					<div class="col-md-10">
						<input type="email" class="form-control input-lg" id="inputEmail"
							placeholder="Email" name="email" value="${user.email}">
					</div>
				</div>
				<div class="form-group">
					<div class="col-md-offset-2 col-md-10">
						<button type="button" id="addBtn" class="btn btn-warning">确定</button>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<button type="submit" class="btn btn-default"
							onclick="history.go(-1);">返回</button>
					</div>
				</div>
			</form>
		</div>
	</div>

</body>
</html>