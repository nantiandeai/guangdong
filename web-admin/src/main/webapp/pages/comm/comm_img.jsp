<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath(); 
request.setAttribute("basePath", basePath);
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Basic Window - jQuery EasyUI Demo</title>
	
	<script>
		$(function(){
			$('#openA').on('click', function(e){
				openImgCut('art',900,300, function(url){
					alert('url:'+url);
				});
				return false;
			});
		});
	</script>
</head>
<body>
	<!--  -->
	<%@include file="/pages/comm/header.jsp"%>
	<div style="margin:20px 0;">
		<a href="javascript:void(0)" class="easyui-linkbutton" id="openA">Open</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="$('#window').window('close')">Close</a>
		<input type="hidden" id="hid" name="hid" value="" />
	</div>
	<!--
	<div id="window" class="easyui-window" title="Window Layout" data-options="iconCls:'icon-save'" style="width:1200px;height:400px;padding:5px;" closed="true">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'east',split:true" style="width:100px"></div>
			<form id="form" name="form" action="<%=request.getContextPath()%>/UploadDemo/cutimg" class="form-horizontal" method="post" enctype="multipart/form-data">
		<div class="modal-body text-center">
			<div class="zxx_main_con">
				<div class="zxx_test_list">
					<input class="photo-file" type="file" name="imgFile" id="fcupload" onchange="readURL(this)" />
					<img alt="" src="" id="cutimg" /> 
					<input type="hidden" id="x" name="x" /> 
					<input type="hidden" id="y" name="y" /> 
					<input type="hidden" id="w" name="w" /> 
					<input type="hidden" id="h" name="h" />
					<input type="hidden" id="hh" name="hh" value=""/> 
					<input type="hidden" id="ww" name="ww" value="" />
					<input type="hidden" id="type" name="type" value="" />
					<input type="hidden" id="fileId" name="fileId" value="" />
				</div>
			</div>
		</div>
	</form>
			<div data-options="region:'south',border:false" style="text-align:right;padding:5px 0 0;">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)" onclick="uploadCutImage()" style="width:80px">Ok</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="$('#window').window('close')" style="width:80px">Cancel</a>
			</div>
		</div>
	</div>
	-->
	<div id="test"></div>
</body>
</html>