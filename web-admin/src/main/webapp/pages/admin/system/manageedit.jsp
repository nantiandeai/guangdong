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
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<%@include file="/pages/comm/header.jsp"%>
<base href="${basePath}/" />
<script>
/**
 * 返回
 */
function go_back() {
	window.location.href = './admin/manage';
}

$("document").ready(
function() {
			var id = "${id}";
			//alert(id);
			if (id == "") {
				$("input[name='radioStatus']:eq(0)").attr("checked",
						"checked");
				//添加管理员信息
				$("#addBtn").on("click", function() {
					var param = $("form").serialize();
					$.ajax({
						type : "POST",
						url : getFullUrl('./admin/addManage'),
						data : param,
						success : function(data) {
							if (data == "success") {
								go_back();
							}
						}
					});
				});
			} else {
				var st = "${manage.status}";
				//alert("==" + st);
				$("input[name='radioStatus'][value='" + st + "']").attr(
						"checked", "checked");
				//修改管理员信息
				$("#addBtn").on("click", function() {
					var param = $("form").serialize();
					$.ajax({
						type : "POST",
						url : getFullUrl('./admin/modifyManage?id=')+id,
						data : param,
						success : function(data) {
							if (data == "success") {
								go_back();
							}
						}
					});

				});
			}

		});
</script>
</head>
<body>
	<div class="container-fluid">
		<div class="row">
			<div class="commTt">${title}</div>
		</div>
		<div class="formContainer">
			<form class="form-horizontal" role="form">
				<div class="form-group">
					<label for="inputText" class="col-md-2 control-label">姓名</label>
					<div class="col-md-10">
						<input type="text" class="form-control input-lg" id="inputName"
							placeholder="name" name="name" value="${manage.name}">
					</div>
				</div>
				<div class="form-group">
					<label for="inputPassword3" class="col-md-2 control-label">密码</label>
					<div class="col-md-10">
						<input type="password" name="password"
							class="form-control input-lg" id="inputPassword"
							placeholder="Password" value="${manage.password}">
					</div>
				</div>
				<div class="form-group">
					<label for="inputText" class="col-md-2 control-label">昵称</label>
					<div class="col-md-10">
						<input type="text" name="nickname" class="form-control input-lg"
							id="nickname" placeholder="nickname" value="${manage.nickname}">
					</div>
				</div>
				<div class="form-group">
					<label for="inputText" class="col-md-2 control-label">电话</label>
					<div class="col-md-10">
						<input type="text" name="phone" class="form-control input-lg"
							id="inputPhone" placeholder="phone" value="${manage.phone}">
					</div>
				</div>
				<div class="form-group">
					<label for="inputEmail3" class="col-md-2 control-label">邮箱</label>
					<div class="col-md-10">
						<input type="email" name="email" class="form-control input-lg"
							id="inputEmail" placeholder="Email" value="${manage.email}">
					</div>
				</div>
				<div class="form-group">
					<label for="inputPassword3" id="intputStatus"
						class="col-md-2 control-label">状态</label>
					<div class="col-md-10">
						<div class="radio">
							<label> <input type="radio" name="radioStatus"
								id="optionsRadios1" value="1"> 启用
							</label> <label> <input type="radio" name="radioStatus"
								id="optionsRadios2" value="0"> 禁用
							</label>
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