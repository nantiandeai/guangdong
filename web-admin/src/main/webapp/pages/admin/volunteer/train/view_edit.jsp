<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<% request.setAttribute("basePath", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath());%>
<% request.setAttribute("resourceid", request.getParameter("rsid")); %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>志愿服务管理-编辑志愿培训</title>
    <%@include file="/pages/comm/admin/header.jsp"%>

    <!-- 编辑表单 -->
    <link rel="stylesheet" href="${basePath}/static/admin/css/bootstrap.css"/>
    <link rel="stylesheet" href="${basePath}/static/admin/Font-Awesome/css/font-awesome.min.css"/>
    <link rel="stylesheet" href="${basePath}/static/admin/css/build.css"/>
    <!-- 编辑表单-END -->

    <!-- 图片上传相关 -->
    <script type="text/javascript" src="${basePath}/static/plupload/lib/plupload-2.1.2/js/plupload.full.min.js"></script>
    <script type="text/javascript" src="${basePath}/static/plupload/upload-img.js"></script>
    <!-- 图片上传相关-END -->
</head>
<body>

<form id="whgff" class="whgff" method="post">

    <h2>志愿服务管理-编辑志愿培训</h2>
	 <input type="hidden" name="zypxid" value="${train.zypxid}" >
	 <input type="hidden" name="onlyshow" value="${param.onlyshow}" >
    <div class="whgff-row">
        <div class="whgff-row-label"><i>*</i>培训类型：</div>
        <div class="whgff-row-input">
        	<select class="easyui-combobox" name="zypxtype" style="width:500px; height:32px"
                    data-options="editable:false, required:true, valueField:'id', textField:'text', value:'${train.zypxtype}', data:WhgSysData.getTypeData('12'),required:true"></select>
        </div>
    </div>
    
    <div class="whgff-row">
		<div class="whgff-row-label">关键字：</div>
		<div class="whgff-row-input">
			<select id="zypxkey" class="easyui-combobox" id="zypxkey" style="width:500px;height:32px;" validType="notQuotes" data-options="panelHeight:'auto', editable:true,valueField:'text',textField:'text',data: WhgComm.getTrainKey(), multiple:true"></select>
		</div>
	</div>
	
	<div class="whgff-row">
		<div class="whgff-row-label"><i>*</i>详情标题：</div>
		<div class="whgff-row-input">
			<input id="zypxtitle" class="easyui-textbox" name="zypxtitle" value="${train.zypxtitle}" style="width:500px;height:32px;" data-options="required:true, validType:['length[1,60]']"/>
		</div>
	</div>
	
	<div class="whgff-row">
		<div class="whgff-row-label"><i>*</i>列表短标题：</div>
		<div class="whgff-row-input">
			<input id="zypxshorttitle" class="easyui-textbox" name="zypxshorttitle" value="${train.zypxshorttitle}" style="width:500px;height:32px;" data-options="required:true, validType:['length[1,60]']"/>
		</div>
	</div>
	<div class="whgff-row">
		<div class="whgff-row-label"><i>*</i>来源：</div>
		<div class="whgff-row-input">
			<input id="zypxfrom" class="easyui-textbox" name="zypxfrom" value="${train.zypxfrom}" style="width:500px;height:32px;" data-options="required:true,validType:['length[1,60]']"/>
		</div>
	</div>

    <div class="whgff-row">
        <div class="whgff-row-label"><i>*</i>列表页图：</div>
        <div class="whgff-row-input">
            <input type="hidden" id="zypxpic_pic" name="zypxpic" value="${train.zypxpic}">
            <div class="whgff-row-input-imgview" id="previewImg1"></div>
            <div class="whgff-row-input-imgfile">
                <i><a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" id="imgUploadBtn1">选择图片</a></i>
                <i>图片格式为jpg、png、gif，建议图片尺寸 280*60，大小为2MB以内</i>
            </div>
        </div>
    </div>

	<div class="whgff-row">
		<div class="whgff-row-label"><i>*</i>视频地址：</div>
		<div class="whgff-row-input">
			<select class="easyui-combobox" id="zypxvideo" name="zypxvideo" style="width:500px;height:32px;" data-options="editable:false, required:true, valueField:'addr', value:'${train.zypxvideo}', textField:'key', url:'${basePath}/admin/video/srchPagging'"></select>
		</div>
	</div>
	
	<div class="whgff-row">
		<div class="whgff-row-label"><i></i>视频时长：</div>
		<div class="whgff-row-input">
			<input class="easyui-numberspinner" id="zypxvideolen" name="zypxvideolen" value="${train.zypxvideolen }" data-options="validType:['length[1,10]'],multiline:true " style="width:500px;height:32px;">
		</div>
	</div>

	<div class="whgff-row">
		<div class="whgff-row-label"><i>*</i>视频简介：</div>
		<div class="whgff-row-input">
			<input class="easyui-textbox" id="zypxcontent" name="zypxcontent" value="${train.zypxcontent }" data-options="validType:['length[1,200]'],multiline:true " style="width:500px;height:150px;">
		</div>
	</div>
</form>

<div id="whgwin-add-btn" class="whgff-but" style="width: 400px; margin:20px 0px 50px 350px">
	 <c:if test="${param.onlyshow != '1'}">
    	<a href="javascript:void(0)" class="easyui-linkbutton whgff-but-submit" iconCls="icon-ok" id="whgwin-add-btn-save">提 交</a>
    </c:if>
    <a href="javascript:void(0)" class="easyui-linkbutton whgff-but-clear" iconCls="icon-undo" onclick="WhgComm.editDialogClose()">返 回</a>
</div>

<!-- script -->
<script type="text/javascript">
    $(function () {
		$("#zypxkey").combobox("setValue","${train.zypxkey}");
        //图片初始化
        WhgUploadImg.init({basePath: '${basePath}', uploadBtnId: 'imgUploadBtn1', hiddenFieldId: 'zypxpic_pic', previewImgId: 'previewImg1'});

        $('#whgff').form({
            novalidate: true,
            url : getFullUrl('/admin/volun/edit'),
            onSubmit : function(param) {
                //alert('onSubmit');
                var _valid = $(this).form('enableValidation').form('validate');
                //图片必填
                if(_valid){
                    if($('#zypxpic_pic').val() == ""){
                        _valid = false;
                        $.messager.alert('提示', '请选择列表图片');
                    }
                }
                if(_valid){
                    $.messager.progress();
                }else{
                    //失败时再注册提交事件
                    $('#whgwin-add-btn-save').off('click').one('click', function () { $('#whgff').submit(); });
                }
				param.zypxkey = $("#zypxkey").combobox("getText");
                return _valid;
            },
            success : function(data) {
                $.messager.progress('close');
                var Json = eval('('+data+')');
                if(Json && Json.success == '1'){
                    window.parent.$('#whgdg').datagrid('reload');
                    WhgComm.editDialogClose();
                } else {
                    $.messager.alert('提示', '操作失败: '+Json.errormsg+'！', 'error');
                    $('#whgwin-add-btn-save').off('click').one('click', function () { $('#whgff').submit(); });
                }
            }
        });
        //注册提交事件
        $('#whgwin-add-btn-save').off('click').one('click', function () { $('#whgff').submit(); });
    });
</script>
<!-- script END -->
</body>
</html>