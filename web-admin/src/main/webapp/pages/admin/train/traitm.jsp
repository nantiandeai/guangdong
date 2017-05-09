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
<script type="text/javascript" src="${basePath }/pages/admin/train/traitm.js"></script>
</head>
<body class="easyui-layout">
<!-- 
		<div id="tt" class="easyui-tabs" style="width:500px;height:250px;" fit="true">   
		    <div title="培训管理" style="display:none;" >    -->
		        <!-- 培训管理页面 -->
		        <table id="trainitm"></table>
		        
		        <!-- 查询栏 -->
				<div id="traitmTool">
					<div>
						<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="btn" onclick="_addTrain()">添加</a>
					</div>
					<div>
						培训模板:
						<input class="easyui-combobox" name="traid" data-options="valueField:'traid',textField:'title'" url='${basePath}/admin/train/getAllTitle'/> 
						开始时间:
						<input class="easyui-datebox" name="sdate_s" data-options=""/>结束时间：<input class="easyui-datebox" name="sdate_e" data-options=""/>
						<a href="#" class="easyui-linkbutton search" iconCls="icon-search">查询</a>
					</div>
				</div>
		        
		        
				<!-- 操作按钮 -->
				<div id="trainitmOPT" class="none">
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" method="editTrain">修改</a> 
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" method="delTrain">删除</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" method="sendCheck">送审</a>
			 		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" method="addzy">添加资源</a>   
					<a href="#" class="easyui-linkbutton" data-options="plain:true" method="tokecheng">课程表</a>
				</div> 
	 <!--  培训管理dialog -->
		 
		 <div id="ff" class="none" style="display: none" data-options=" fit:true">
			<form method="post">
				<input type="hidden" name="traitmid" value="">
				<input type="hidden" id="enroldesc" name="enroldesc" value="" />
				<div class="main">
					<div class="row">
		    			<div>培训模板:</div>
		    			<div>
		    			<input class="easyui-combobox" id="xx_traid" name="traid" style="height: 35px; width: 100%" data-options="valueField:'traid',textField:'title', onChange:_setTitle" url='${basePath}/admin/train/getAllTitle' /> 
		    			</div>
		    		</div>
		    		<div class="row">
		    			<div>培训名称:</div>
		    			<div>
		    			<input class="easyui-textbox" id="tratitle" name="tratitle" style="height: 35px; width: 100%">
		    			</div>
		    		</div>
		    		<div class="row">
		    			<div>开始日期:</div>
		    			<div><input id="sdate" name="sdate" type="text" class="easyui-datetimebox" required="required" style="height: 35px; width: 100%"></input></div>
		    		</div>
		    		<div class="row">
		    			<div>结束日期:</div>
		    			<div><input id="edate" name="edate" type="text" class="easyui-datetimebox" required="required" style="height: 35px; width: 100%"></input></div>
		    		</div>
		    		<div class="row">
		    			<div>是否需要报名:</div>
		    			<div>
		    				<select class="easyui-combobox combobox_isenroldata"
								name="isenrol" style="height: 35px; width: 100%"
								data-options="editable:false,required:true">
								<option value="1">是</option>
								<option value="0">否</option>
							</select>	
		    			</div>
		    		</div>
		    		
		    		<div class="row">
		    			<div>报名开始时间:</div>
		    			<div>
		    				<input id="stime" name="enrolstime" type="text" class="easyui-datetimebox"  style="height: 35px; width: 100%"></input>
		    			</div>
		    		</div>
		    		<div class="row">
		    			<div>报名结束时间:</div>
		    			<div>
		    				<input id="etime" name="enroletime" type="text" class="easyui-datetimebox"  style="height: 35px; width: 100%"></input>
		    			</div>
		    		</div>
		    		<div class="row">
		    			<div>报名人数限制:</div>
		    			<div>
		    				<input name="enrollimit" class="easyui-numberspinner"   data-options="min:1,max:1000,editable:true" style="height: 35px; width: 100%"></input>  
		    			</div>
		    		</div>
		    		<div class="row">
		    			<div>报名是否需要审核:</div>
		    			<div>
							<select class="easyui-combobox combobox_isenroldata"
								name="isenrolqr" style="height: 35px; width: 100%"
								data-options="editable:false">
								<option value="0">不需要</option>
								<option value="1">需要</option>
							</select>
		    			</div>
		    		 </div>
		    		 <div class="row">
		    			<div>是否需要面试通知:</div>
		    			<div>
							<select class="easyui-combobox combobox_isenroldata"
								name="isnotic" style="height: 35px; width: 100%"
								data-options="editable:false">
								<option value="1">是</option>
								<option value="0">否</option>
							</select>
		    			</div>
		    		 </div>
		    		 <div class="row">
		    			<div>报名介绍:</div>
		    			<div>
		    				<script id="traeditor" type="text/plain" style="width:100%; height:300px;"></script>
			    <!--   		<input class="easyui-textbox" data-options="multiline:true" id="enroldesc" name="enroldesc" style="width: 90%; height: 180px">   -->
		    			</div>
		    		</div>
		    		<div class="row">
		    			<div>课程简介:</div>
		    			<div>
			     			<input class="easyui-textbox" data-options="validType:'length[0,30]',multiline:true "  id="trasummary" name="trasummary" style="width: 100%; height: 180px">   
		    			</div>
		    		</div>
				</div>
			</form>
		</div>	
		
		
		<!--培训资源dialog  -->
		<div id="addzy">
			<iframe  style="width:100%; height:100%"></iframe>
		</div>
		
		<!-- 课程表dialog -->
		<div id="dd">
			<iframe  style="width:100%; height:100%"></iframe>
		</div>
		
		
		
</body>
</html>