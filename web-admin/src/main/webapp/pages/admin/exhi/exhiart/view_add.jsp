<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<% request.setAttribute("basePath", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath());%>
<% request.setAttribute("resourceid", request.getParameter("rsid")); %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>数字展览管理——添加展览作品</title>
    <link rel="stylesheet" href="${basePath}/static/admin/css/bootstrap.css"/>
    <link rel="stylesheet" href="${basePath}/static/admin/Font-Awesome/css/font-awesome.min.css"/>
    <link rel="stylesheet" href="${basePath}/static/admin/css/build.css"/>

    <script type="text/javascript" charset="utf-8" src="${basePath}/static/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="${basePath}/static/ueditor/ueditor.all.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="${basePath}/static/ueditor/lang/zh-cn/zh-cn.js"></script>
    <%@include file="/pages/comm/admin/header.jsp"%>
    <!-- 图片上传相关 -->
    <script type="text/javascript" src="${basePath}/static/plupload/lib/plupload-2.1.2/js/plupload.full.min.js"></script>
    <script type="text/javascript" src="${basePath}/static/plupload/upload-img.js"></script>
    <!-- 图片上传相关-END -->
</head>
<body>



<form id="whgff" class="whgff" method="post">
    <h2>数字展览管理——添加展览作品</h2>
    <input type="hidden" name="artexhid" value="${exhid}">
    <div class="whgff-row">
        <div class="whgff-row-label"><label style="color: red">*</label>标题：</div>
        <div class="whgff-row-input"><input class="easyui-textbox" name="arttitle" style="width:600px; height:32px" data-options="required:true, validType:'length[1,30]'"></div>
    </div>
    <div class="whgff-row">
        <div class="whgff-row-label"><label style="color: red">*</label>责任者：</div>
        <div class="whgff-row-input"><input class="easyui-textbox" name="artauthor" style="width:600px; height:32px" data-options="required:true, validType:'length[1,8]'"></div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label"><label style="color: red"></label>标签：</div>
        <div class="whgff-row-input"><input class="easyui-combobox " name="arttags"
                                            style="height:32px;width: 600px;"
                                            data-options="editable:false, valueField:'id',textField:'text',data:WhgComm.getZxTag(),required:false"/></div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label"><label style="color: red">*</label>关键字：</div>
        <div class="whgff-row-input"><input class="easyui-combobox " name="artkeys"
                                            style="height:32px;width: 600px;"
                                            data-options="editable:true, valueField:'id',textField:'text',data:WhgComm.getZxKey(),required:true"/></div>
    </div>


    <div class="whgff-row">
        <div class="whgff-row-label"><label style="color: red">*</label>创建时间：</div>
        <div class="whgff-row-input">
            <input type="text" class="easyui-datetimebox" style="width:300px;height: 32px;" name="creTime"
                   data-options="required:true,editable:false,prompt:'请选择'"/>
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label"><i>*</i>作品图片：</div>
        <div class="whgff-row-input">
            <input type="hidden" id="cult_picture1" name="artpic" >
            <div class="whgff-row-input-imgview" id="previewImg1"></div>
            <div class="whgff-row-input-imgfile">
                <i><a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" id="imgUploadBtn1">选择图片</a></i>
                <i>图片格式为jpg、png、gif，建议图片尺寸 750*500，大小为2MB以内</i>
            </div>
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label"><i>*</i>作者介绍：</div>
        <div class="whgff-row-input">
            <input class="easyui-textbox" name="artartistdesc" multiline="true" style="width:600px;height: 200px;"
                   data-options="required:true,validType:['length[0,1500]']">
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label"><i>*</i>作品介绍：</div>
        <div class="whgff-row-input">
            <script id="catalog" type="text/plain" style="width:600px; height:250px;"></script>
        </div>
    </div>

</form>


<div id="whgwin-add-btn" class="whgff-but" style="width: 400px; margin:20px 0px 50px 350px">
    <div style="display: inline-block; margin: 0 auto">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" id="whgwin-add-btn-save">保 存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" onclick="WhgComm.editDialogClose()">返  回</a>
    </div>
</div>

<!-- script -->
<script type="text/javascript">
    var ueConfig = {
        scaleEnabled: false,
        autoFloatEnabled: false,
    };
    var ue_catalog = UE.getEditor('catalog', ueConfig);

    $(function () {
        WhgUploadImg.init({basePath: '${basePath}', uploadBtnId: 'imgUploadBtn1', hiddenFieldId: 'cult_picture1', previewImgId: 'previewImg1'});

        $('#whgff').form({
            novalidate: true,
            url: "${basePath}/admin/exhi/exhiart/add",
            onSubmit : function(param) {
                var _valid = $(this).form('enableValidation').form('validate')
                if(_valid) {
                    //图片必填
                    if($('#cult_picture1').val() == ""){
                        _valid = false;
                        $.messager.alert('提示', '请选择作品图片');
                    }else if(!isUEvalid) {
                        var isUEvalid = validateUE();
                        if (isUEvalid) {
                            param.artcontent = ue_catalog.getContent();
                            $.messager.progress();
                        } else {
                            _valid = false;
                        }
                    }
                }

                if (!_valid){
                    $.messager.progress('close');
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
                    $.messager.alert('提示', '操作失败'+Json.errormsg+'！', 'error');
                    $('#whgwin-add-btn-save').off('click').one('click', function () { $('#whgff').submit(); });
                }
            }
        });
        //注册提交事件
        $('#whgwin-add-btn-save').off('click').one('click', function () { $('#whgff').submit(); });
    });

    function validateUE(){
        if (!ue_catalog.hasContents()) {
            $.messager.alert("错误", '作品介绍不能为空', 'error');
            return false;
        }
        return true;
    }

</script>
<!-- script END -->
</body>
</html>
