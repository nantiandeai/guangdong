<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<% request.setAttribute("basePath", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath());%>
<% request.setAttribute("resourceid", request.getParameter("rsid")); %>
<% String path = request.getContextPath();%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>馆办团队管理-修改馆办团队</title>
    <%@include file="/pages/comm/admin/header.jsp"%>

    <!-- 编辑表单 -->
    <link rel="stylesheet" href="${basePath}/static/admin/css/bootstrap.css"/>
    <link rel="stylesheet" href="${basePath}/static/admin/Font-Awesome/css/font-awesome.min.css"/>
    <link rel="stylesheet" href="${basePath}/static/admin/css/build.css"/>
    
    <script type="text/javascript" charset="utf-8"	src="<%=path%>/static/ueditor/ueditor.config.js"></script>
	<script type="text/javascript" charset="utf-8"	src="<%=path%>/static/ueditor/ueditor.all.min.js"></script>
	<script type="text/javascript" charset="utf-8"	src="<%=path%>/static/ueditor/lang/zh-cn/zh-cn.js"></script>
    <!-- 编辑表单-END -->
    
    <!-- 图片上传相关 -->
    <script type="text/javascript" src="${basePath}/static/plupload/lib/plupload-2.1.2/js/plupload.full.min.js"></script>
    <script type="text/javascript" src="${basePath}/static/plupload/upload-img.js"></script>
    <!-- 图片上传相关-END -->
    <script src="${basePath}/static/admin/js/whgtoolmodule.js"></script>
</head>
<body>

<form id="whgff" class="whgff" method="post">

    <h2>馆办团队管理-修改馆办团队</h2>
	 <input type="hidden" name="troupeid" value="${team.troupeid}" >
	 <input type="hidden" name="onlyshow" value="${param.onlyshow}" >
    <div class="whgff-row">
        <div class="whgff-row-label"><i>*</i>艺术类型：</div>
        <div class="whgff-row-input">
        	<select class="easyui-combobox" name="troupearttyp" style="width:500px; height:32px"
                    data-options="editable:false, required:true, valueField:'id', textField:'text', value:'${team.troupearttyp}', data:WhgComm.getArtType(),required:true"></select>
        </div>
    </div>
    
    <div class="whgff-row">
		<div class="whgff-row-label"><i>*</i>团队名称：</div>
		<div class="whgff-row-input">
			<input id="troupename" class="easyui-textbox" name="troupename" value="${team.troupename }" style="width:500px;height:32px;" data-options="required:true, validType:'length[1,60]'"/>
		</div>
	</div>
    
    <div class="whgff-row">
        <div class="whgff-row-label"><i>*</i>图片：</div>
        <div class="whgff-row-input">
            <input type="hidden" id="troupepic_pic" name="troupepic" value="${team.troupepic}" >
            <div class="whgff-row-input-imgview" id="previewImg1""></div>
            <div class="whgff-row-input-imgfile">
                <i><a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" id="imgUploadBtn1">选择图片</a></i>
                <i>图片格式为jpg、png、gif，建议图片尺寸 280*60，大小为2MB以内</i>
            </div>
        </div>
    </div>

	<div class="whgff-row">
		<div class="whgff-row-label"><i>*</i>成立时间：</div>
		<div class="whgff-row-input">
			<input class="easyui-datetimebox" id="trouperegtime" name="trouperegtime" value="<fmt:formatDate value='${team.trouperegtime}' pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>"  style="width:500px;height:32px;" data-options="editable:false, required:true"/>
		</div>
	</div>
	
	<div class="whgff-row">
		<div class="whgff-row-label"><i>*</i>成员数：</div>
		<div class="whgff-row-input">
			<input class="easyui-numberspinner" id="troupernum" name="troupernum" value="${team.troupernum }" data-options="increment:1, required:true, min:2, max:999" style="width:500px;height:32px;">
		</div>
	</div>

	<div class="whgff-row">
		<div class="whgff-row-label"><i>*</i>联系地址：</div>
		<div class="whgff-row-input">
			<input class="easyui-textbox" id="troupeaddr" name="troupeaddr" value="${team.troupeaddr }" data-options="validType:'length[0,150]',multiline:true " style="width:500px;height:32px;">
		</div>
	</div>
	
	<div class="whgff-row">
		<div class="whgff-row-label">简介：</div>
		<div class="whgff-row-input">
			<input class="easyui-textbox" id="troupedesc" name="troupedesc" value="${team.troupedesc }" data-options="validType:'length[0,400]', multiline:true" style="width:500px;height:150px;">
		</div>
	</div>
	
	<div class="whgff-row">
		<div class="whgff-row-label">
			详细介绍：
		</div>
		<div class="whgff-row-input">
			<script id="troupecontent" name="troupecontent"  type="text/plain" style="width: 700px; height: 250px;" >${team.troupecontent}</script>
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
      //图片初始化
      WhgUploadImg.init({basePath: '${basePath}', uploadBtnId: 'imgUploadBtn1', hiddenFieldId: 'troupepic_pic', previewImgId: 'previewImg1'});
      //初始化富文本
		var ueConfig = {
	        scaleEnabled: false,
	        autoFloatEnabled: false
	    };
		UE.getEditor('troupecontent', ueConfig);
        $('#whgff').form({
            novalidate: true,
            url : getFullUrl('/admin/arts/uptroupe'),
            onSubmit : function(param) {
                //alert('onSubmit');
                var _valid = $(this).form('enableValidation').form('validate');
                //图片必填
                if(_valid){
                    if($('#troupepic').val() == ""){
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