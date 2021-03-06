<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath(); request.setAttribute("basePath", basePath); %>
<%request.setAttribute("resourceid", request.getParameter("rsid"));%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<%@include file="/pages/comm/header.jsp"%>
<script type="text/javascript" charset="utf-8" src="${basePath}/static/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${basePath}/static/ueditor/ueditor.all.min.js"></script>
<script type="text/javascript" charset="utf-8" src="${basePath}/static/ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript" src="${basePath }/pages/admin/train/trainmanage.js"></script>
<script type="text/javascript">

$(function(){
	$("#trainTool").css('visibility','visible');
})
</script>
</head>
<body>
		<div id="testdiv" style="display:none">
			培训类型:
			<input class="easyui-combobox" name="tratyp" data-options="editable:false,valueField:'typid',textField:'typname',url:'${basePath}/comm/whtyp?type=2'"/>
			艺术类型: 
			<input class="easyui-combobox" name="traarttyp" data-options="editable:false,valueField:'typid',textField:'typname',url:'${basePath}/comm/whtyp?type=0'"/>
			<a id="search" class="easyui-linkbutton search" iconCls="icon-search">查询</a>
		</div>
		
		
		<!-- 培训管理页面 -->
		<table id="train"></table>
		
		<!-- 查询栏 -->
		<div id="trainTool" class="grid-control-group" style="display: none;">
			<div>
				<shiro:hasPermission name="${resourceid}:add"><a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" id="btn" onclick="_addTrain()">添加</a></shiro:hasPermission>
				<shiro:hasPermission name="${resourceid}:checkgo"><a href="javascript:void(0)" class="easyui-linkbutton" onclick="allCheck()">批量审核</a></shiro:hasPermission>
			</div>
			<div style="padding-top: 5px">
				培训类型:
				<input class="easyui-combobox" name="tratyp" data-options="width:150,valueField:'typid',textField:'typname',url:'${basePath}/comm/whtyp?type=2'"/>
				艺术类型: 
				<input class="easyui-combobox" name="traarttyp" data-options="width:100,valueField:'typid',textField:'typname',url:'${basePath}/comm/whtyp?type=0'"/>
				适合年龄: 
				<input class="easyui-combobox" name="traagelevel" data-options="width:100,valueField:'typid',textField:'typname',url:'${basePath}/comm/whtyp?type=3'"/>
				课程级别: 
				<input class="easyui-combobox" name="tralevel" data-options="width:100,valueField:'typid',textField:'typname',url:'${basePath}/comm/whtyp?type=4'"/>
			</div>	
			<div style="padding-top: 5px">
				培训标题:
				<input class="easyui-textbox" name="tratitle" data-options="width:150,validType:'length[1,30]'"/>
				开始时间:
				<input class="easyui-datebox" name="sdate_s" data-options=""/>到<input class="easyui-datebox" name="sdate_e" data-options=""/>
				<a href="javascript:void(0)" class="easyui-linkbutton search" iconCls="icon-search">查询</a>
			</div>
			
		</div>
		      
		<!-- 操作按钮 -->
		<div id="trainOPT" style="display:none">
			<shiro:hasPermission name="${resourceid}:edit"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" method="editTrain">修改</a></shiro:hasPermission> 
			<shiro:hasPermission name="${resourceid}:del"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" method="delTrain">删除</a></shiro:hasPermission>
			<shiro:hasPermission name="${resourceid}:checkgo"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" method="sendCheck">送审</a></shiro:hasPermission>
			<shiro:hasPermission name="${resourceid}:view"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true"  method="look">查看详情</a></shiro:hasPermission>
			<shiro:hasPermission name="${resourceid}:edit"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" method="addzy">资源管理</a></shiro:hasPermission>   
			<shiro:hasPermission name="${resourceid}:edit"><a href="#" class="easyui-linkbutton" data-options="plain:true" method="tokecheng">课程表</a></shiro:hasPermission>
		</div> 
				
	 <!--  培训管理dialog -->
		 
		 <div id="train_edit" class="none" style="display:none"  data-options=" fit:true" >
		    		
			 <form id="ff" method="post" enctype="multipart/form-data" >
				<!-- 隐藏域  -->
				<!-- <input type="hidden" id="tratplid" name="tratplid" value="" /> -->
				<input type="hidden" id="traid" name="traid" value="" />
		    	<input type="hidden" id="trateacher" name="trateacher" value="" /> 
		    	<input type="hidden" id="tradetail" name="tradetail" value="" />
		    	<input type="hidden" id="tracatalog" name="tracatalog" value="" />
		  		<input type="hidden" id="trapic" name="trapic" value=""/>
				<input type="hidden" id="trabigpic" name="trabigpic" value=""/>
				<input type="hidden" id="trapersonfile" name="trapersonfile" value=""/>
				<input type="hidden" id="trateamfile" name="trateamfile" value=""/>    	
				
				<div class="main">
					<div class="row">
		    			<div>选择模板:</div>
		    			<div>
		    				<%-- <input style="width:60%;height:32px;" class="easyui-combobox" name="tratplid" id="tplid" data-options="valueField:'tratplid', textField:'tratitle', url:'${basePath}/admin/trainManage/getTratplTitle'"/> --%>
		    				<input class="easyui-combogrid" id="enrtraid" name="tratplid" style="width:600px;height:32px" data-options="editable:false"/>  
		    				<a class="easyui-linkbutton" iconCls="icon-add" plain="true" id="btn" onclick="addTraintpl()">培训模板导入</a>
		    			</div>
		    		</div>
					<div class="row">
		    			<div>培训类型:</div>
		    			<div>
		    				<input id="tratyp" class="easyui-combobox" name="tratyp" style="width:100%;height:32px;" data-options="panelHeight:'auto', editable:false, required:true, valueField:'typid', textField:'typname',url:'${basePath}/comm/whtyp?type=2'"/>
		    			</div>
		    		</div>
		    		<div class="row">
		    			<div>艺术类型:</div>
		    			<div>
		    				<input id="traarttyp" class="easyui-combobox" name="traarttyp" style="width:100%;height:32px;" data-options="editable:false, required:true, valueField:'typid',textField:'typname',url:'${basePath}/comm/whtyp?type=0'"/>
		    			</div>
		    		</div>
		    		<div class="row">
		    			<div>课程标题:</div>
		    			<div>
							<input id="tratitle" class="easyui-textbox" name="tratitle" style="width:100%;height:32px;"  data-options="required:true, validType:'length[1,60]'"/>
		    			</div>
		    		 </div>
		    		<div class="row">
		    			<div>短标题:</div>
		    			<div>
							<input id="trashorttitle" class="easyui-textbox" name="trashorttitle" style="width:100%;height:32px;" data-options="required:true,validType:'length[1,60]'"/>
		    			</div>
		    		 </div>
		    		<div class="row">
		    			<div>区域:</div>
		    			<div>
		    				<input id="traarea" class="easyui-combobox" name="traarea" style="width:100%;height:32px" 
									data-options="panelHeight:'auto', editable:false, required:true, valueField:'typid',textField:'typname',url:'${basePath}/comm/whtyp?type=8'"/>
		    			</div>
		    		</div>
		    		<div class="row">
		    			<div>列表页图:</div>
		    			<div>
							<input id="trapic_up"  class="easyui-filebox" name="trapic_up" style="width:90%;height:32px;" data-options="validType:'isIMG[\'trapic_up\']', buttonText:'选择图片', accept:'image/jpeg,image/peg, image/png',prompt:'图片尺寸:380x240'">
							<a id="btn_pic" class="easyui-linkbutton" data-options="plain:true," iconCls="icon-clear" style="width:9%;height:32px;">清除</a>
		    			</div>
		    		 </div>
		    		 <div class="row">
		    			<div>详细页图片：</div>
			    		<div>
							<input id="trabigpic_up" class="easyui-filebox" name="trabigpic_up" editable="true" style="width:90%;height:32px;" data-options="validType:'isIMG[\'trabigpic_up\']', buttonText:'选择图片', accept:'image/jpeg,image/peg, image/png',prompt:'图片尺寸:428x270'">
			    			<a id="btn_pic1" class="easyui-linkbutton" data-options="plain:true," iconCls="icon-clear" style="width:9%;height:32px;">清除</a> 
			    		</div>
		    		 </div>
		    		 <div class="row">
		    			<div>培训开始日期:</div>
		    			<div><input id="trasdate" name="trasdate" type="text" class="easyui-datetimebox" data-options="editable:false, required:true"  style="height: 35px; width: 100%"></input></div>
		    		</div>
		    		<div class="row">
		    			<div>培训结束日期:</div>
		    			<div><input id="traedate" name="traedate" type="text" class="easyui-datetimebox" data-options="editable:false, required:true" style="height: 35px; width: 100%"></input></div>
		    		</div>
		    		 <div class="row">
		    			<div>适合年龄:</div>
			    		<div>
							<input id="traagelevel" class="easyui-combobox" name="traagelevel" style="width:100%;height:32px;" data-options="panelHeight:'auto',editable:false,required:true, valueField:'typid',textField:'typname',url:'${basePath}/comm/whtyp?type=3'"/>
			    		</div>
		    		 </div>
		    		<div class="row">
		    			<div>课程级别:</div>
		    			<div>
		    				<input id="tralevel" class="easyui-combobox" name="tralevel" style="width:100%;height:32px;" data-options="panelHeight:'auto',editable:false,required:true, valueField:'typid',textField:'typname',url:'${basePath}/comm/whtyp?type=4'"/>
		    			</div>
		    		</div>
		    		<div class="row">
		    			<div>详细地址：</div>
		    			<div>
		    				<input class="easyui-textbox" id="traaddress" name="traaddress" data-options="validType:'length[0,200]',multiline:true " style="width:100%;height:50px;">
		    			</div>
		    		</div>
		    		<div class="row">
		    			<div>标签:</div>
		    			<div>
							<input id="tratags" class="easyui-combobox" name="tratags" multiple="true" style="width:100%;height:32px;" data-options="panelHeight:'auto',editable:false, valueField:'id',textField:'name',url:'${basePath}/comm/whtag?type=2016101400000020', multiple:true"/>
		    			</div>
		    		 </div>
		    		 <div class="row">
		    			<div>关键字:</div>
			    		<div>
							<input id="trakeys" class="easyui-combobox" name="trakeys" multiple="true" style="width:100%;height:32px;" data-options="required:'true',missingMessage:'请用英文逗号分隔',panelHeight:'auto', valueField:'name',textField:'name',url:'${basePath}/comm/whkey?type=2016101400000023', multiple:true"/>
			    		</div>
		    		 </div>
		    		 <div class="row">
		    			<div>联系人：</div>
		    			<div>
		    				<input class="easyui-textbox" id="tracontact" name="tracontact" data-options="validType:'length[0,10]',multiline:true " style="width:100%;height:32px;">
		    			</div>
		    		</div>
		    		<div class="row">
		    			<div>联系电话：</div>
		    			<div>
		    				<input class="easyui-textbox" id="traphone" name="traphone" data-options="validType:'isPhone[\'traphone\']'" style="width:100%;height:32px;">
		    			</div>
		    		</div>
		    		 <div class="row">
		    			<div>授课老师:</div>
			    		<div>
							<input id="trateacherid" class="easyui-combobox" name="trateacherid" style="width:100%;height:32px;" data-options="required:true,valueField:'teacherid',textField:'teachername',url:'${basePath}/admin/trainManage/findTeacher'"/>
			    		</div>
		    		 </div>
		    		<div class="row">
		    			<div>老师介绍:</div>
		    			<div>
		    				<input id="trateacherdesc" class="easyui-textbox" name="trateacherdesc" style="width:100%;height:100px;" data-options="validType:'length[0,500]', multiline:true"/>
		    			</div>
		    		</div>
		    		<div class="row">
		    			<div>必需登录点评:</div>
		    			<div>
		    				<select class="easyui-combobox radio" name="traislogincomment" data-options="panelHeight:'auto',editable:false,required:true" style="width:100%;height:32px;">
			    					<option value="1">是</option>
			    					<option value="0">否</option>
							</select>
		    			</div>
		    		</div>
		    		<div class="row">
		    			<div>是否需要报名:</div>
		    			<div>
		    				<select id="traisenrol" class="easyui-combobox"
								name="traisenrol" style="height: 35px; width: 100%"
								data-options="panelHeight:'auto',editable:false,required:true">
								<option value="1">是</option>
								<option value="0">否</option>
							</select>	
		    			</div>
		    		</div>
		    		<div class="row">
		    			<div>报名介绍:</div>
		    			<div>
		    				<input id="traenroldesc" class="easyui-textbox" name="traenroldesc" style="width:100%;height:100px;" data-options="validType:'length[0,500]', multiline:true"/>
		    			</div>
		    		</div>
		    		<div class="row">
		    			<div>报名开始时间:</div>
		    			<div>
		    				<input id="traenrolstime" name="traenrolstime" type="text" data-options="editable:false,required:true" class="easyui-datetimebox"  style="height: 35px; width: 100%"></input>
		    			</div>
		    		</div>
		    		<div class="row">
		    			<div>报名结束时间:</div>
		    			<div>
		    				<input id="traenroletime" name="traenroletime" type="text" data-options="editable:false,required:true" class="easyui-datetimebox"  style="height: 35px; width: 100%"></input>
		    			</div>
		    		</div>
		    		<div class="row">
		    			<div>报名人数限制:</div>
		    			<div>
		    				<input id="traenrollimit" name="traenrollimit" class="easyui-numberspinner"   data-options="min:1,max:1000,editable:true,required:true" style="height: 35px; width: 100%"></input>  
		    			</div>
		    		</div>
		    		<div class="row">
		    			<div>是否需要收费:</div>
		    			<div>
							<select id="ismoney" class="easyui-combobox"
								name="ismoney" style="height: 35px; width: 100%"
								data-options="panelHeight:'auto',editable:false,required:true">
								<option value="0">否</option>
								<option value="1">是</option>
							</select>
		    			</div>
		    		 </div> 
		    		 <div class="row">
		    			<div>是否需要面试通知:</div>
		    			<div>
							<select id="traisnotic" class="easyui-combobox"
								name="traisnotic" style="height: 35px; width: 100%"
								data-options="panelHeight:'auto',editable:false,required:true">
								<option value="1">是</option>
								<option value="0">否</option>
							</select>
		    			</div>
		    		 </div>
		    		<div class="row">
		    			<div>必须实名报名:</div>
		    			<div>
							<select id="traisrealname" class="easyui-combobox" name="traisrealname" data-options="panelHeight:'auto',editable:false,required:true" style="width:100%;height:32px;">
			    					<option value="1">是</option>
			    					<option value="0">否</option>
							</select>
		    			</div>
		    		 </div>
		    		 <div class="row">
		    			<div>是否需要完善资料:</div>
			    		<div>
							<select id="traisfulldata" class="easyui-combobox" name="traisfulldata" data-options="panelHeight:'auto',editable:false,required:true" style="width:100%;height:32px;">
			    					<option value="1">是</option>
			    					<option value="0">否</option>
							</select>
			    		</div>
		    		 </div>
		    		 <!-- <div class="row">
		    			<div>1人1项限制:</div>
			    		<div>
							<select id="traisonlyone" class="easyui-combobox" name="traisonlyone" data-options="panelHeight:'auto',editable:false,required:true" style="width:100%;height:32px;">
			    					<option value="1">是</option>
			    					<option value="0">否</option>
							</select>
			    		</div>
		    		 </div> -->
		    		 <div class="row">
		    			<div>需上传附件:</div>
		    			<div>
							<select class="easyui-combobox" id="traisattach" name="traisattach" data-options="panelHeight:'auto',editable:false,required:true" style="width:100%;height:32px;">
			    					<option value="1">是</option>
			    					<option value="0">否</option>
							</select>
		    			</div>
		    		 </div>
		    		<div class="row">
		    			<div>允许个人报名:</div>
		    			<div>
		    				<select id="tracanperson" class="easyui-combobox" name="tracanperson" data-options="panelHeight:'auto',editable:false" style="width:100%;height:32px;">
			    					<option value="1">是</option>
			    					<option value="0">否</option>
							</select>
		    			</div>
		    		</div>
		    		
		    		<div class="row">
		    			<div>个人附件:</div>
			    		<div>
							<input class="easyui-filebox" id="trapersonfile_up" name="trapersonfile_up" style="width:90%;height:32px;" data-options="validType:'isFile[\'trapersonfile_up\']', buttonText:'选择文件', accept:'file/doc,file/docx, file/xls,file/xlsx,file/zip'">
			    			<a id="btn_pic2" class="easyui-linkbutton" data-options="plain:true," iconCls="icon-clear" style="width:9%;height:32px;">清除</a>
			    		</div>
		    		 </div>
		    		 <div class="row">
		    			<div></div>
			    		<div>
			    			<label id="fileName" /></a>
			    		</div>
		    		 </div>
		    		 <div class="row">
		    			<div>允许团队报名:</div>
		    			<div>
		    				<select id="tracanteam" class="easyui-combobox" name="tracanteam" data-options="panelHeight:'auto',editable:false" style="width:100%;height:32px;">
			    					<option value="1">是</option>
			    					<option value="0">否</option>
							</select>
		    			</div>
		    		</div>
		    		 <div class="row">
		    			<div>团队附件:</div>
			    		<div>
							<input class="easyui-filebox" id="trateamfile_up" name="trateamfile_up" style="width:90%;height:32px;" data-options="validType:'isFile[\'trateamfile_up\']', buttonText:'选择文件', accept:'file/doc,file/docx, file/xls,file/xlsx,file/zip'">
			    			<a id="btn_pic3" class="easyui-linkbutton" data-options="plain:true," iconCls="icon-clear" style="width:9%;height:32px;" onclick="clear()">清除</a>
			    		</div>
		    		 </div>
		    		 <div class="row">
		    			<div></div>
			    		<div>
			    			<a id="fileName_" /></a>
			    		</div>
		    		 </div>
		    		<div class="row">
		    			<div>课程简介：</div>
		    			<div>
		    				<input class="easyui-textbox" id="traintroduce" name="traintroduce" data-options="validType:'length[0,400]',multiline:true " style="width:100%;height:50px;">
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
		
		
		<!--培训资源dialog  -->
		<div id="addzy" style="display:none">
			<iframe  style="width:100%; height:100%;border:0px" ></iframe>
		</div>
		
		<!-- 课程表dialog -->
		<div id="dd" style="display:none">
			<iframe  style="width:100%; height:100%;border:0px" ></iframe>
		</div>
		
		<!-- 审核 -->
		<div id="check_edit" class="none" style="display: none" data-options=" fit:true">
			 <form id="check_ff" method="post" enctype="multipart/form-data" >
				<!-- 隐藏域  -->
				<input type="hidden" name="tratplid" value="" />
				<input type="hidden" name="traid" value="" />
		    	<input type="hidden" name="trateacherid" value="123" />
		    	<input type="hidden" name="tradetail" value="" />
		    	<input type="hidden" name="tracatalog" value="" />
		    	<input type="hidden" name="trapic" value=""/>
				<input type="hidden" name="trabigpic" value=""/>
				<input type="hidden" name="trapersonfile" value=""/>
				<input type="hidden" name="trateamfile" value=""/>
				<input type="hidden" name="trastate" value=""/>
				
				<div class="main">
					<div class="row">
		    			<div>培训类型:</div>
		    			<div>
		    				<input class="easyui-combobox" missingMessage="培训类型必须填写" name="tratyp" style="width:100%;height:32px;" data-options="readonly:true,required:true, valueField:'typid',textField:'typname',url:'${basePath}/comm/whtyp?type=2'"/>
		    			</div>
		    		</div>
		    		<div class="row">
		    			<div>艺术类型:</div>
		    			<div>
		    				<input class="easyui-combobox" name="traarttyp" style="width:100%;height:32px;" data-options="readonly:true,required:true, valueField:'typid',textField:'typname',url:'${basePath}/comm/whtyp?type=0'"/>
		    			</div>
		    		</div>
		    		<div class="row">
		    			<div>课程标题:</div>
		    			<div>
							<input class="easyui-textbox" name="tratitle" style="width:100%;height:32px;" data-options="readonly:true"/>
		    			</div>
		    		 </div>
		    		<div class="row">
		    			<div>短标题:</div>
		    			<div>
							<input class="easyui-textbox" name="trashorttitle" style="width:100%;height:32px;" data-options="readonly:true"/>
		    			</div>
		    		 </div>
		    		<div class="row">
		    			<div>区域:</div>
		    			<div>
		    				<input class="easyui-combobox" name="traarea" style="width:100%;height:32px" 
									data-options="readonly:true,editable:false,required:true, valueField:'typid',textField:'typname',url:'${basePath}/comm/whtyp?type=8'"/>
		    			</div>
		    		</div>
		    		<div class="row">
		    			<div>列表页图:</div>
		    			<div>
		    				<a name="trapic_up"></a>
		    			</div>
		    		 </div>
		    		 <div class="row">
		    			<div>详细页图片：</div>
			    		<div>
			    			<a name="trabigpic_up"></a>
			    		</div>
		    		 </div>
		    		 <div class="row">
		    			<div>培训开始日期:</div>
		    			<div><input name="trasdate" type="text" class="easyui-datetimebox" data-options="readonly:true" style="height: 35px; width: 100%"></input></div>
		    		</div>
		    		<div class="row">
		    			<div>培训结束日期:</div>
		    			<div><input name="traedate" type="text" class="easyui-datetimebox" data-options="readonly:true" style="height: 35px; width: 100%"></input></div>
		    		</div>
		    		 <div class="row">
		    			<div>适合年龄:</div>
			    		<div>
							<input class="easyui-combobox" name="traagelevel" style="width:100%;height:32px;" data-options="readonly:true,editable:false,required:true, valueField:'typid',textField:'typname',url:'${basePath}/comm/whtyp?type=3'"/>
			    		</div>
		    		 </div>
		    		<div class="row">
		    			<div>课程级别:</div>
		    			<div>
		    				<input class="easyui-combobox" name="tralevel" style="width:100%;height:32px;" data-options="readonly:true,editable:false,required:true, valueField:'typid',textField:'typname',url:'${basePath}/comm/whtyp?type=4'"/>
		    			</div>
		    		</div>
		    		<div class="row">
		    			<div>详细地址：</div>
		    			<div>
		    				<input class="easyui-textbox" name="traaddress" data-options="readonly:true" style="width:100%;height:50px;">
		    			</div>
		    		</div>
		    		<div class="row">
		    			<div>标签:</div>
		    			<div>
							<input class="easyui-combobox" name="tratags" multiple="true" style="width:100%;height:32px;" data-options="readonly:true, valueField:'id',textField:'name',url:'${basePath}/comm/whtag?type=2016101400000020', multiple:true"/>
		    			</div>
		    		 </div>
		    		 <div class="row">
		    			<div>关键字:</div>
			    		<div>
							<input class="easyui-combobox" name="trakeys" multiple="true" style="width:100%;height:32px;" data-options="readonly:true, valueField:'id',textField:'name',url:'${basePath}/comm/whkey?type=2016101400000023', multiple:true"/>
			    		</div>
		    		 </div>
		    		 <div class="row">
		    			<div>联系人：</div>
		    			<div>
		    				<input class="easyui-textbox" name="tracontact" data-options="validType:'length[0,10]',multiline:true " style="width:100%;height:32px;">
		    			</div>
		    		</div>
		    		<div class="row">
		    			<div>联系电话：</div>
		    			<div>
		    				<input class="easyui-textbox" name="traphone" data-options="validType:'isPhone[\'traphone\']'" style="width:100%;height:32px;">
		    			</div>
		    		</div>
		    		 <div class="row">
		    			<div>授课老师:</div>
			    		<div>
							<input class="easyui-textbox" name="trateacher" style="width:100%;height:32px;" data-options="readonly:true"/>
			    		</div>
		    		 </div>
		    		<div class="row">
		    			<div>老师介绍:</div>
		    			<div>
		    				<input class="easyui-textbox" name="trateacherdesc" style="width:100%;height:100px;" data-options="readonly:true"/>
		    			</div>
		    		</div>
		    		<div class="row">
		    			<div>必需登录点评:</div>
		    			<div>
		    				<select class="easyui-combobox radio" name="traislogincomment" data-options="readonly:true" style="width:100%;height:32px;">
			    					<option value="1">是</option>
			    					<option value="0">否</option>
							</select>
		    			</div>
		    		</div>
		    		<div class="row">
		    			<div>是否需要报名:</div>
		    			<div>
		    				<select class="easyui-combobox combobox_isenroldata"
								name="traisenrol" style="height: 35px; width: 100%"
								data-options="readonly:true">
								<option value="1">是</option>
								<option value="0">否</option>
							</select>	
		    			</div>
		    		</div>
		    		<div class="row">
		    			<div>报名介绍:</div>
		    			<div>
		    				<input class="easyui-textbox" name="traenroldesc" style="width:100%;height:100px;" data-options="readonly:true"/>
		    			</div>
		    		</div>
		    		<div class="row">
		    			<div>报名开始时间:</div>
		    			<div>
		    				<input name="traenrolstime" type="text" data-options="readonly:true" class="easyui-datetimebox"  style="height: 35px; width: 100%"></input>
		    			</div>
		    		</div>
		    		<div class="row">
		    			<div>报名结束时间:</div>
		    			<div>
		    				<input name="traenroletime" type="text" data-options="readonly:true" class="easyui-datetimebox"  style="height: 35px; width: 100%"></input>
		    			</div>
		    		</div>
		    		<div class="row">
		    			<div>报名人数限制:</div>
		    			<div>
		    				<input name="traenrollimit" class="easyui-numberspinner"   data-options="readonly:true" style="height: 35px; width: 100%"></input>  
		    			</div>
		    		</div>
		    		<!-- <div class="row">
		    			<div>报名是否需要审核:</div>
		    			<div>
							<select id="traisenrolqr" class="easyui-combobox"
								name="traisenrolqr" style="height: 35px; width: 100%"
								data-options="readonly:true">
								<option value="0">否</option>
								<option value="1">是</option>
							</select>
		    			</div>
		    		 </div> -->
		    		 <div class="row">
		    			<div>是否需要收费:</div>
		    			<div>
							<select class="easyui-combobox"
								name="ismoney" style="height: 35px; width: 100%"
								data-options="panelHeight:'auto',editable:false,required:true">
								<option value="0">否</option>
								<option value="1">是</option>
							</select>
		    			</div>
		    		 </div> 
		    		 <div class="row">
		    			<div>是否需要面试通知:</div>
		    			<div>
							<select class="easyui-combobox"
								name="traisnotic" style="height: 35px; width: 100%"
								data-options="readonly:true">
								<option value="1">是</option>
								<option value="0">否</option>
							</select>
		    			</div>
		    		 </div>
		    		<div class="row">
		    			<div>必须实名报名:</div>
		    			<div>
							<select class="easyui-combobox" name="traisrealname" data-options="readonly:true" style="width:100%;height:32px;">
			    					<option value="1">是</option>
			    					<option value="0">否</option>
							</select>
		    			</div>
		    		 </div>
		    		 <div class="row">
		    			<div>是否需要完善资料:</div>
			    		<div>
							<select class="easyui-combobox" name="traisfulldata" data-options="readonly:true" style="width:100%;height:32px;">
			    					<option value="1">是</option>
			    					<option value="0">否</option>
							</select>
			    		</div>
		    		 </div>
		    		<!--  <div class="row">
		    			<div>1人1项限制:</div>
			    		<div>
							<select id="traisonlyone" class="easyui-combobox" name="traisonlyone" data-options="readonly:true" style="width:100%;height:32px;">
			    					<option value="1">是</option>
			    					<option value="0">否</option>
							</select>
			    		</div>
		    		 </div> -->
		    		 <div class="row">
		    			<div>需上传附件:</div>
		    			<div>
							<select class="easyui-combobox" name="traisattach" data-options="readonly:true" style="width:100%;height:32px;">
			    					<option value="1">是</option>
			    					<option value="0">否</option>
							</select>
		    			</div>
		    		 </div>
		    		<div class="row">
		    			<div>允许个人报名:</div>
		    			<div>
		    				<select class="easyui-combobox" name="tracanperson" data-options="readonly:true" style="width:100%;height:32px;">
			    					<option value="1">是</option>
			    					<option value="0">否</option>
							</select>
		    			</div>
		    		</div>
		    		
		    		<div class="row">
		    			<div>个人附件:</div>
			    		<div>
			    			<a id="trapersonfile_up1"></a>
			    		</div>
		    		 </div>
		    		
		    		 <div class="row">
		    			<div>允许团队报名:</div>
		    			<div>
		    				<select class="easyui-combobox" name="tracanteam" data-options="readonly:true" style="width:100%;height:32px;">
			    					<option value="1">是</option>
			    					<option value="0">否</option>
							</select>
		    			</div>
		    		</div>
		    		 <div class="row">
		    			<div>团队附件:</div>
			    		<div>
			    			<a id="trateamfile_up1"></a>
			    		</div>
		    		 </div>
		    		<div class="row">
		    			<div>课程简介：</div>
		    			<div>
		    				<input class="easyui-textbox" name="traintroduce" data-options="readonly:true" style="width:100%;height:50px;">
		    			</div>
		    		</div>
		    		 <div class="row">
		    			<div>培训大纲：</div>
			    		<div>
							<script id="catalog1" type="text/plain" style="width:100%; height:300px;"></script>
			    		</div>
		    		 </div>
		    		<div class="row">
		    			<div>培训详情:</div>
		    			<div>
		    				<script id="traeditor1" type="text/plain" style="width:100%; height:300px;"></script>
		    			</div>
		    		</div>
				</div>
			</form>
		</div>	
		
</body>
</html>