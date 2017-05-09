<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% request.setAttribute("basePath", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath());%>
<% request.setAttribute("resourceid", request.getParameter("rsid")); %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>添加上传资源</title>
    <%@include file="/pages/comm/admin/header.jsp" %>
    <link rel="stylesheet" href="${basePath}/static/admin/css/bootstrap.css"/>
    <link rel="stylesheet" href="${basePath}/static/admin/Font-Awesome/css/font-awesome.min.css"/>
    <link rel="stylesheet" href="${basePath}/static/admin/css/build.css"/>
    <script type="text/javascript" charset="utf-8" src="${basePath}/static/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="${basePath}/static/ueditor/ueditor.all.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="${basePath}/static/ueditor/lang/zh-cn/zh-cn.js"></script>
    <script type="text/javascript" src="${basePath}/static/plupload/lib/plupload-2.1.2/js/plupload.full.min.js"></script>
    <script type="text/javascript" src="${basePath}/static/plupload/upload-singlefile.js"></script>
</head>
<body>

<form id="whgff" method="post" class="whgff" enctype="multipart/form-data">

    <h2>添加上传资源</h2>

    <div class="whgff-row">
        <div class="whgff-row-label"><label style="color: red">*</label>资源标题：</div>
        <div class="whgff-row-input"><input class="easyui-textbox" name="upname" style="width:600px; height:32px" data-options="required:true, validType:'length[0,30]'"></div>
    </div>

    <%--<div class="whgff-row">--%>
        <%--<div class="whgff-row-label">资源地址：</div>--%>
        <%--<div class="whgff-row-input">--%>
            <%--<input class="easyui-filebox" name="uplink_up" id="uplink_up" style="width: 600px;height:35px" data-options="required: true, validType:'isFile[\'uplink_up\', 20485760]', buttonText:'选择文件'"/>--%>

        <%--</div>--%>
    <%--</div>--%>

    <div class="whgff-row">
        <div class="whgff-row-label">
            资源地址：
        </div>
        <div class="whgff-row-input">
            <input id="act_filepath1" name="uplink" data-options="required:true" style="width:600px;height:32px;"
                   readonly="readonly">
            <div class="whgff-row-input" id="filepath">
                <i><a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" id="fileUploadBtn1">选择文件</a></i>
                <i>附件格式为doc,docx,xls,zip,xlsx,pdf建议文件大小为100MB以内</i>
            </div>
        </div>
    </div>

    <div id="whgwin-add"></div>
    <div id="whgwin-add-btn" class="whgff-but" style="width: 400px; margin:20px 0px 50px 350px">
        <div style="display: inline-block; margin: 0 auto">
            <a href="#" class="easyui-linkbutton" iconCls="icon-save" id="whgwin-add-btn-save" type="submit">保 存</a>
            <a href="javascript:void(0)" class="easyui-linkbutton whgff-but-clear" iconCls="icon-undo" onclick="WhgComm.editDialogClose()" data-options="size:'small'">返 回</a>
        </div>
    </div>
</form>

<script>
    $(function () {
        <%--WhgUploadFile.init({basePath: '${basePath}', uploadBtnId: 'fileUploadBtn2', hiddenFieldId: 'whg_file_upload',previewFileId:'whg_file_pload_view'});--%>
        WhgUploadFile.init({basePath: '${basePath}', uploadBtnId: 'fileUploadBtn1', hiddenFieldId: 'act_filepath1'});
        $('#whgff').form({
            novalidate: true,
            url: '${basePath}/admin/shop/adduploda',
            onSubmit : function(param) {
                param.uptype ='${refid}';
                param.upstate = 0;
                var _valid = $(this).form('enableValidation').form('validate')
                if (!_valid){
                    $.messager.progress('close');
                    $('#whgwin-add-btn-save').off('click').one('click', function () { $('#whgff').submit(); });
                }
                return _valid;
            },
            success : function(data) {
                $.messager.progress('close');
                var Json = eval('('+data+')');
                if(Json && Json.success == '0'){
                    window.parent.$('#whgdg').datagrid('reload');
                    WhgComm.editDialogClose();
                } else {
                    $.messager.alert('提示', '操作失败！', 'error');
                    $('#whgwin-add-btn-save').off('click').one('click', function () { $('#whgff').submit(); });
                }
            },

        });
        //注册提交事件
        $('#whgwin-add-btn-save').off('click').one('click', function () { $('#whgff').submit(); });
    });

</script>

</body>
</html>