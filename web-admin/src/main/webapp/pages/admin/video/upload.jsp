<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath(); request.setAttribute("basePath", basePath); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>上传图片</title>
<link rel="stylesheet" type="text/css" href="${basePath}/pages/admin/video/style.css"/>
<script type="text/javascript">var basePath = '${basePath}'; var _userDir = '${dir}';</script>
</head>
<body>
	<form name=theform>
		<div id="container">
			<a id="selectfiles" href="javascript:void(0);" class='btn'>选择音视频文件</a>
			<a id="postfiles" href="javascript:void(0);" class='btn'>开始上传</a>
			<div style="margin: 5px;">先点击&nbsp;<label style="font-weight: bold; color: blue;">“选择音视频文件”</label>&nbsp;按钮选择本地音视频文件，后点击&nbsp;<label style="font-weight: bold; color: blue;">“开始上传”</label>&nbsp;按钮上传视频到服务器。视频支持<label style="font-weight: bold;">mp3,mp4,ogg,webm,mpg,flv,ogv,oga</label>格式类型文件。建议使用<label style="font-weight: bold; color:red;">mp4</label>格式视频文件。</div>
		</div>
		<hr>
		
		<div style="display: none;">
			<input type="radio" name="myradio" value="local_name" checked="checked"/> 上传文件名字保持本地文件名字
			<input type="radio" name="myradio" value="random_name" /> 上传文件名字是随机文件名, 后缀保留
		</div>
		
		<h4>您所选择的文件列表：</h4>
		<div id="ossfile">你的浏览器不支持flash,Silverlight或者HTML5！</div>
		
		<pre id="console" style="display: none;"></pre>
	</form>	
</body>
<script type="text/javascript" src="${basePath}/pages/admin/video/lib/plupload-2.1.2/js/plupload.full.min.js"></script>
<script type="text/javascript" src="${basePath}/pages/admin/video/upload.js"></script>
</html>