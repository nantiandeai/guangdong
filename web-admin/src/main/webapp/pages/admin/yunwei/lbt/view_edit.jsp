<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% request.setAttribute("basePath", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath());%>
<% request.setAttribute("resourceid", request.getParameter("rsid")); %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>轮播图配置-编辑轮播图</title>
    <%@include file="/pages/comm/admin/header.jsp"%>
    <link rel="stylesheet" href="${basePath}/static/admin/css/bootstrap.css"/>
    <link rel="stylesheet" href="${basePath}/static/admin/Font-Awesome/css/font-awesome.min.css"/>
    <link rel="stylesheet" href="${basePath}/static/admin/css/build.css"/>

    <!-- 图片上传相关 -->
    <script type="text/javascript" src="${basePath}/static/plupload/lib/plupload-2.1.2/js/plupload.full.min.js"></script>
    <script type="text/javascript" src="${basePath}/static/plupload/upload-img.js"></script>
    <!-- 图片上传相关-END -->
</head>
<body>

<h2>轮播图配置-编辑轮播图</h2>

<form id="whgff" method="post" class="whgff">
    <input type="hidden" name="type" value="${clazz}">
    <input type="hidden" name="id" id="id" value="${id}"/>
    <div class="whgff-row">
        <div class="whgff-row-label"><label style="color: red"></label>名称：</div>
        <div class="whgff-row-input"><input class="easyui-textbox" id="name" name="name" value="${lbt.name}" style="width:600px; height:32px" data-options="required:true , validType:'length[0,30]'"></div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label"><i>*</i>图片：</div>
        <div class="whgff-row-input">
            <input type="hidden" id="cult_picture1" name="picture" value="${lbt.picture}">
            <div class="whgff-row-input-imgview" id="previewImg1"></div>
            <div class="whgff-row-input-imgfile">
                <i><a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" id="imgUploadBtn1">选择图片</a></i>
                <c:choose>
                    <c:when test="${clazz == '1'}">
                        <i>图片格式为jpg、png、gif，建议图片尺寸 1920*430，大小为2MB以内</i>
                    </c:when>
                    <c:when test="${clazz == '3'}">
                        <i>图片格式为jpg、png、gif，建议图片尺寸 600*320，大小为2MB以内</i>
                    </c:when>
                    <c:when test="${clazz == '6'}">
                        <i>图片格式为jpg、png、gif，大小为2MB以内</i>
                    </c:when>
                    <c:when test="${clazz == '7'}">
                        <i>图片格式为jpg、png、gif，建议图片尺寸 480*370，大小为2MB以内</i>
                    </c:when>
                    <c:otherwise>
                        <i>图片格式为jpg、png、gif，建议图片尺寸 1920*500，大小为2MB以内</i>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
    <div class="whgff-row">
        <div class="whgff-row-label">URL：</div>
        <div class="whgff-row-input">
            <input class="easyui-textbox" id="url" name="url" value="${lbt.url}" style="width:600px; height:32px" data-options="required:false,validType:'url',prompt:'请输入连接地址,如：http://yoursite.com/'">
        </div>
    </div>


</form>
<div id="whgwin-add-btn" class="whgff-but" style="width: 400px; margin:20px 0px 50px 350px">
    <div style="display: inline-block; margin: 0 auto">
        <a href="#" class="easyui-linkbutton" iconCls="icon-save" id="btn" type="submit">保 存</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="WhgComm.editDialogClose()">返 回</a>
    </div>
</div>

<script>

    $(function () {
        var lbt_type = '${clazz}';
        if( lbt_type == '1' ){
            WhgUploadImg.init({basePath: '${basePath}', uploadBtnId: 'imgUploadBtn1', hiddenFieldId: 'cult_picture1', previewImgId: 'previewImg1', needCut:false, cutWidth:1920, cutHeight:430});
        }else if(lbt_type == '3'){
            WhgUploadImg.init({basePath: '${basePath}', uploadBtnId: 'imgUploadBtn1', hiddenFieldId: 'cult_picture1', previewImgId: 'previewImg1', needCut:true, cutWidth:600, cutHeight:320});
        }else{
            WhgUploadImg.init({basePath: '${basePath}', uploadBtnId: 'imgUploadBtn1', hiddenFieldId: 'cult_picture1', previewImgId: 'previewImg1', needCut:false});
        }

        //初始化表单
        $('#whgff').form({
            novalidate: true,//去掉验证
            url: '${basePath}/admin/yunwei/lbt/edit',
            onSubmit: function () {
                var _valid = $(this).form('enableValidation').form('validate');
                if($("#cult_picture1").val() == ""){
                    _valid = false;
                    $.messager.alert('提示', '请选图片');
                }
                if (_valid) {
                    $.messager.progress();
                } else {
                    $('#btn').off('click').one('click', function () {
                        $('#whgff').submit();
                    });
                }
                return _valid;
            },
            success: function (data) {
                $.messager.progress('close');

                var Json = $.parseJSON(data);
                if (Json.success == "1") {
                    window.parent.$('#whgdg').datagrid('reload');
                    WhgComm.editDialogClose();
                } else {
                    $.messager.alert("提示", Json.errormsg);
                }

            }
        });
        //$('#whgff').form('disableValidation');

        //
        $('#btn').off('click').one('click', function () {
            $('#whgff').submit();
        });
    });



</script>

</body>
</html>