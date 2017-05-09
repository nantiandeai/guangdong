<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<% String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath(); request.setAttribute("basePath", basePath); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<%@include file="/pages/comm/header.jsp"%>
<!-- UEditor -->
<script type="text/javascript" charset="utf-8" src="${basePath}/static/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${basePath}/static/ueditor/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="${basePath}/static/ueditor/lang/zh-cn/zh-cn.js"></script>
<script>
/** 添加资讯 */
function doadd(){
	$("#zxform").form("submit", {
		url : '${basePath}/admin/addzx',		
		success: function(data){
			alert(JSON.stringify(data));
		}
	});
}

/** 编辑资讯 */
function doedit(){
	$("#zxform").form("submit", {
		url : '${basePath}/admin/editzx',		
		success: function(data){
			alert(JSON.stringify(data));
		}
	});
}


$(function(){
	//实例化编辑器
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
    var ue = UE.getEditor('zxeditor');
});
</script>
</head>
<body>
	<div class="container-fluid">
		<div class="row">
			<div class="commTt">${zxtitle }<button type="submit" class="btn btn-default class="col-md-offset-11 col-md-1"" onclick="history.go(-1);">返回</button></div>
		</div>
		
		<div class="formContainer">
			<div class="row">
				<form class="form-horizontal" role="form" id="zxform" enctype="multipart/form-data" action="${basePath}/admin/addzx" method="post">
					<input type="hidden" name="id" value="${zxinfo.id }" />
					<div class="form-group">
						<label for="type" class="col-md-2 control-label">分类:</label>
						<div class="col-md-8">
							<input type="text" class="form-control input-lg" name="type" value="" placeholder="资讯分类" readonly/>
						</div>
					</div>
					<div class="form-group">
						<label for="shorttitle" class="col-md-2 control-label">短标题:</label>
						<div class="col-md-8">
							<input type="text" class="form-control input-lg" name="shorttitle" value="${zxinfo.shorttitle }" placeholder="">
						</div>
					</div>
					<div class="form-group">
						<label for="title" class="col-md-2 control-label">长标题:</label>
						<div class="col-md-8">
							<input type="text" class="form-control input-lg" name="title" value="${zxinfo.title }" placeholder="">
						</div>
					</div>
					<div class="form-group">
						<label for="inputPassword3" class="col-md-2 control-label">图片:</label>
						<div class="col-md-8">
							<input type="file" name="zximg">
						</div>
					</div>
					<div class="form-group">
						<label for="author" class="col-md-2 control-label">作者:</label>
						<div class="col-md-8">
							<input type="text" class="form-control input-lg" name="author" value="${zxinfo.author}" placeholder="">
						</div>
					</div>
					<div class="form-group">
						<label for="editor" class="col-md-2 control-label">小编:</label>
						<div class="col-md-8">
							<input type="text" class="form-control input-lg" name="editor" value="${zxinfo.editor }" placeholder="">
						</div>
					</div>
					<div class="form-group">
						<label for="fromchannel" class="col-md-2 control-label">来源:</label>
						<div class="col-md-8">
							<input type="text" class="form-control input-lg" name="fromchannel" value="${zxinfo.fromchannel }" placeholder="">
						</div>
					</div>
					<div class="form-group">
						<label for="inputPassword3" class="col-md-2 control-label">标签:</label>
						<div class="col-md-8">
							<c:forEach items="${zxtags}" varStatus="i" var="item" >
							<button type="button" class="btn btn-default btn-sm">${item.name }</button>
							</c:forEach> 
						</div>
					</div>
					<div class="form-group">
						<label for="inputPassword3" class="col-md-2 control-label">关键字:</label>
						<div class="col-md-8">
							<c:forEach items="${zxkeys}" varStatus="i" var="item" >
							<button type="button" class="btn btn-default btn-sm">${item.name }</button>
							</c:forEach> 
						</div>
					</div>
					<div class="form-group">
						<label for="inputPassword3" class="col-md-2 control-label">描述:</label>
						<div class="col-md-8">
							<textarea class="form-control" rows="3" name="memo">${zxinfo.memo}</textarea>
						</div>
					</div>
					
					<div class="form-group">
						<label for="creator" class="col-md-2 control-label">创建者:</label>
						<div class="col-md-8">
							<input type="text" class="form-control input-lg" name="creator" value="${zxinfo.creator}" placeholder="">
						</div>
					</div>
					<div class="form-group">
						<label for="inputPassword3" class="col-md-2 control-label">内容:</label>
						<div class="col-md-8">
							 <script id="zxeditor" type="text/plain" style="width:100%;height:300px;"></script>
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-offset-2 col-md-8">
							<button type="button" class="btn btn-warning" onclick="do${method}();">提交</button>
							&nbsp;&nbsp;&nbsp;&nbsp;
							<button type="button" class="btn btn-default" onclick="history.go(-1);">返回</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>

</body>
</html>