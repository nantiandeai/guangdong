<!--新的培训管理的页面  -->


<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath(); request.setAttribute("basePath", basePath); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<%@include file="/pages/comm/header.jsp"%>
<script type="text/javascript" charset="utf-8" src="${basePath}/static/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${basePath}/static/ueditor/ueditor.all.min.js"></script>
<script type="text/javascript" charset="utf-8" src="${basePath}/static/ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript" src="${basePath }/pages/admin/train/tra.js"></script>
</head>
<body class="easyui-layout">
		<!-- 培训模板表格 -->
		<table id="tra"></table>
		<!-- END-培训模板表格 -->
		
		<!-- 培训模板表格操作按钮 -->
		<div id="tra_tb" style="height:auto">
			<div>
				<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="btn_addtra" onclick="addTra()">添加</a>
			</div>
			<div>
				培训类型:
				<input class="easyui-combobox" name="tratyp" data-options="valueField:'typid',textField:'typname',url:'${basePath}/comm/whtyp?type=2'"/>
				艺术类型: 
				<input class="easyui-combobox" name="arttyp" data-options="valueField:'typid',textField:'typname',url:'${basePath}/comm/whtyp?type=0'"/>
				适合年龄: 
				<input class="easyui-combobox" name="agelevel" data-options="valueField:'typid',textField:'typname',url:'${basePath}/comm/whtyp?type=3'"/>
				课程级别: 
				<input class="easyui-combobox" name="tralevel" data-options="valueField:'typid',textField:'typname',url:'${basePath}/comm/whtyp?type=4'"/>
				<br>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
				标题:
				<input class="easyui-textbox" name="title" data-options="validType:'length[1,30]'"/>
				<a href="#" class="easyui-linkbutton search" iconCls="icon-search">查询</a>
			</div>
		</div>
		<!-- END-培训模板表格操作按钮 -->
	
		<!-- 操作按钮 -->
		<div id="tra_opt" class="none">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" method="_updTrain">修改</a> 
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" method="_delTrain">删除</a>
		</div>
		
		
		<!-- 培训模板弹出框  -->
		 <div id="tra_edit" class="none" style="display: none" data-options=" fit:true">
			 <form id="ff" method="post" enctype="multipart/form-data" action="${basePath}/admin/traintpl/savetratpl">
				<input type="hidden" id="traid" name="traid" value="" />
		    	<input type="hidden" id="teacherid" name="teacherid" value="123" />
		    	<input type="hidden" id="tradesc" name="tradesc" value="" />
		    	<input type="hidden" id="tracatalog" name="tracatalog" value="" />
		    	<input type="hidden" id="trapic" name="trapic" value=""/>
				<input type="hidden" id="trapic1" name="trapic1" value=""/>
				<input type="hidden" id="trapic2" name="trapic2" value=""/>
				<input type="hidden" id="userfile" name="userfile" value=""/>
				<input type="hidden" id="groupfile" name="groupfile" value=""/>
				
				<div class="main">
					<div class="row">
		    			<div>培训类型:</div>
		    			<div>
		    				<input id="tratyp" class="easyui-combobox" name="tratyp" style="width:100%;height:32px;" data-options="required:true, valueField:'typid',textField:'typname',url:'${basePath}/comm/whtyp?type=2'"/>
		    			</div>
		    		</div>
		    		<div class="row">
		    			<div>艺术类型:</div>
		    			<div>
		    				<input id="arttyp" class="easyui-combobox" name="arttyp" style="width:100%;height:32px;" data-options="required:true, valueField:'typid',textField:'typname',url:'${basePath}/comm/whtyp?type=0'"/>
		    			</div>
		    		</div>
		    		<div class="row">
		    			<div>培训模板:</div>
		    			<div>
							<input id="title" class="easyui-textbox" name="title" style="width:100%;height:32px;" data-options="required:true, validType:'length[1,30]'"/>
		    			</div>
		    		 </div>
		    		<div class="row">
		    			<div>区域:</div>
		    			<div>
		    				<input class="easyui-combobox" name="area" style="width:100%;height:32px" 
									data-options="editable:false,required:true, valueField:'typid',textField:'typname',url:'${basePath}/comm/whtyp?type=8'"/>
		    			</div>
		    		</div>
		    		<div class="row">
		    			<div>封面图片:</div>
		    			<div>
							<input  class="easyui-filebox" name="trapic_up" style="width:100%;height:32px;">
		    			</div>
		    		 </div>
		    		 <div class="row">
		    			<div>适合年龄:</div>
			    		<div>
							<input id="agelevel" class="easyui-combobox" name="agelevel" style="width:100%;height:32px;" data-options="required:true, valueField:'typid',textField:'typname',url:'${basePath}/comm/whtyp?type=3'"/>
			    		</div>
		    		 </div>
		    		<div class="row">
		    			<div>课程级别:</div>
		    			<div>
		    				<input id="tralevel" class="easyui-combobox" name="tralevel" style="width:100%;height:32px;" data-options="required:true, valueField:'typid',textField:'typname',url:'${basePath}/comm/whtyp?type=4'"/>
		    			</div>
		    		</div>
		    		<div class="row">
		    			<div>标签:</div>
		    			<div>
							<input id="tratags" class="easyui-combobox" name="tratags" multiple="true" style="width:100%;height:32px;" data-options=" valueField:'id',textField:'name',url:'${basePath}/comm/whtag?type=2016101400000020', multiple:true"/>
		    			</div>
		    		 </div>
		    		 <div class="row">
		    			<div>关键字:</div>
			    		<div>
							<input id="trakeys" class="easyui-combobox" name="trakeys" multiple="true" style="width:100%;height:32px;" data-options=" valueField:'id',textField:'name',url:'${basePath}/comm/whkey?type=2016101400000023', multiple:true"/>
			    		</div>
		    		 </div>
		    		 <div class="row">
		    			<div>授课老师:</div>
			    		<div>
							<input id="teacher" class="easyui-textbox" name="teacher" style="width:100%;height:32px;" data-options="required:true, validType:'length[1,30]'"/>
			    		</div>
		    		 </div>
		    		<div class="row">
		    			<div>老师介绍:</div>
		    			<div>
		    				<input id="teacherdesc" class="easyui-textbox" name="teacherdesc" style="width:100%;height:100px;" data-options="validType:'length[0,500]', multiline:true"/>
		    			</div>
		    		</div>
		    		<div class="row">
		    			<div>必须实名:</div>
		    			<div>
							<select class="easyui-combobox radio" name="isrealname" style="width:100%;height:32px;">
			    					<option value="1">是</option>
			    					<option value="0">否</option>
							</select>
		    			</div>
		    		 </div>
		    		 <div class="row">
		    			<div>必须完善资料:</div>
			    		<div>
							<select class="easyui-combobox radio" name="isfulldata" style="width:100%;height:32px;">
			    					<option value="1">是</option>
			    					<option value="0">否</option>
							</select>
			    		</div>
		    		 </div>
		    		 <div class="row">
		    			<div>1人1项限制:</div>
			    		<div>
							<select class="easyui-combobox radio" name="isonlyone" style="width:100%;height:32px;">
			    					<option value="1">是</option>
			    					<option value="0">否</option>
							</select>
			    		</div>
		    		 </div>
		    		<div class="row">
		    			<div>须登录点评:</div>
		    			<div>
		    				<select class="easyui-combobox radio" name="islogincomment" style="width:100%;height:32px;">
			    					<option value="1">是</option>
			    					<option value="0">否</option>
							</select>
		    			</div>
		    		</div>
		    		<div class="row">
		    			<div>需上传附件:</div>
		    			<div>
							<select class="easyui-combobox radio" id="isattach" name="isattach" style="width:100%;height:32px;">
			    					<option value="1">是</option>
			    					<option value="0">否</option>
							</select>
		    			</div>
		    		 </div>
		    		 <div class="row">
		    			<div>个人附件模板:</div>
			    		<div>
							<input id="userfile" class="easyui-filebox" id="userfile" name="userfile_up" style="width:100%;height:32px;">
			    		</div>
		    		 </div>
		    		 <div class="row">
		    			<div>团队附件模板:</div>
			    		<div>
							<input id="groupfile" class="easyui-filebox" id="groupfile" name="groupfile_up" style="width:100%;height:32px;">
			    		</div>
		    		 </div>
		    		<div class="row">
		    			<div>详细地址：</div>
		    			<div>
		    				<input class="easyui-textbox" id="address" name="address" style="width:100%;height:32px;">
		    			</div>
		    		</div>
		    		<div class="row">
		    			<div>首页图片：</div>
		    			<div>
							<input id="trapic1" class="easyui-filebox" name="trapic_home" style="width:100%;height:32px;">
		    			</div>
		    		 </div>
		    		 <div class="row">
		    			<div>详细页图片：</div>
			    		<div>
							<input id="trapic2" class="easyui-filebox" name="trapic_de" style="width:100%;height:32px;">
			    		</div>
		    		 </div>
		    		 <div class="row">
		    			<div>培训大纲：</div>
			    		<div>
							<script id="catalog" type="text/plain" style="width:100%; height:300px;"></script>
			    		</div>
		    		 </div>
		    		<div class="row">
		    			<div>培训详情:</div>
		    			<div>
		    				<script id="traeditor" type="text/plain" style="width:100%; height:300px;"></script>
		    			</div>
		    		</div>
				</div>
			</form>
		</div>	
</body>
</html>