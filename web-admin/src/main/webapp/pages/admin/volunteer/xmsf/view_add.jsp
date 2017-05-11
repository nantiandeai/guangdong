<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<% request.setAttribute("basePath", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath());%>
<% request.setAttribute("resourceid", request.getParameter("rsid")); %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>添加项目示范</title>
    <%@include file="/pages/comm/admin/header.jsp"%>
    <!-- 编辑表单 -->
    <link rel="stylesheet" href="${basePath}/static/admin/css/bootstrap.css"/>
    <link rel="stylesheet" href="${basePath}/static/admin/Font-Awesome/css/font-awesome.min.css"/>
    <link rel="stylesheet" href="${basePath}/static/admin/css/build.css"/>
    <!-- 富文本-->
    <script src="${basePath}/static/ueditor/ueditor.config.js"></script>
    <script src="${basePath}/static/ueditor/ueditor.all.min.js"></script>
    <script src="${basePath}/static/ueditor/lang/zh-cn/zh-cn.js"></script>
    <!-- 图片上传相关 -->
    <script type="text/javascript" src="${basePath}/static/plupload/lib/plupload-2.1.2/js/plupload.full.min.js"></script>
    <script type="text/javascript" src="${basePath}/static/plupload/upload-img.js"></script>
</head>
<body>

<form id="whgff" class="whgff" method="post">

    <h2>添加项目示范</h2>

    <div class="whgff-row">
        <div class="whgff-row-label"><i>*</i>标题：</div>
        <div class="whgff-row-input">
            <input class="easyui-textbox" id="zyfcxmtitle" name="zyfcxmtitle" style="width:500px;height:35px;" data-options="required:true, validType:'length[1,60]'"/>
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label"><i>*</i>短标题：</div>
        <div class="whgff-row-input">
            <input class="easyui-textbox" id="zyfcxmshorttitle" name="zyfcxmshorttitle" style="width:500px;height:35px;" data-options="required:true, validType:'length[1,30]'"/>
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label"><i>*</i>服务地区：</div>
        <div class="whgff-row-input">
            <input class="easyui-textbox" id="zyfcxmscope" name="zyfcxmscope" style="width:500px;height:35px;" data-options="required:true, validType:'length[1,30]'"/>
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label"><i>*</i>实施单位：</div>
        <div class="whgff-row-input">
            <input class="easyui-textbox" id="zyfcxmssdw" name="zyfcxmssdw" style="width: 300px;;height:35px;" data-options="required:true, validType:'length[1,30]'"/>
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label"><i>*</i>实施时间：</div>
        <div class="whgff-row-input">
            <input class="easyui-datetimebox" id="zyfcxmsstime" name="zyfcxmsstime" style="width: 300px;;height:35px;" data-options="editable:false, required:true" />
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label"><i>*</i>列表图片：</div>
        <div class="whgff-row-input">
            <input type="hidden" id="hide_picture1" name="zyfcxmpic" >
            <div class="whgff-row-input-imgview" id="previewImg1" style="width:300px; height:190px;"></div>
            <div class="whgff-row-input-imgfile">
                <i><a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" id="imgUploadBtn1">选择图片</a></i>
                <i>图片格式为jpg、png、gif，建议图片尺寸 380*240，大小为2MB以内</i>
            </div>
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label"><i>*</i>详情页图：</div>
        <div class="whgff-row-input">
            <input type="hidden" id="hide_picture2" name="zyfcxmbigpic" >
            <div class="whgff-row-input-imgview" id="previewImg2" style="width:300px; height: 185px;"></div>
            <div class="whgff-row-input-imgfile">
                <i><a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" id="imgUploadBtn2">选择图片</a></i>
                <i>图片格式为jpg、png、gif，建议图片尺寸 400*250，大小为2MB以内</i>
            </div>
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label">志愿者数量：</div>
        <div class="whgff-row-input">
            <input class="easyui-numberspinner" id="zyfcxmpnum" name="zyfcxmpnum" data-options="min:1" style="width:500px;height:35px;" />
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label">服务人数量：</div>
        <div class="whgff-row-input">
            <input class="easyui-numberspinner" id="zyfcxmanum" name="zyfcxmanum" data-options="min:1" style="width:500px;height:35px;" />
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label"><i>*</i>项目实施内容：</div>
        <div class="whgff-row-input">
            <script id="traeditor" name="zyfcxmcontent" type="text/plain" style="width:650px; height:300px;"></script>
        </div>
    </div>
</form>

<div id="whgwin-add-btn" class="whgff-but" style="width: 400px; margin:20px 0px 50px 350px">
    <a href="javascript:void(0)" class="easyui-linkbutton whgff-but-submit" iconCls="icon-ok" id="whgwin-add-btn-save">提 交</a>
    <a href="javascript:void(0)" class="easyui-linkbutton whgff-but-clear" iconCls="icon-undo" onclick="WhgComm.editDialogClose()">返 回</a>
</div>

<!-- script -->
<script type="text/javascript">
    var mydescUE;
    $(function () {
        //UEditor
        mydescUE = UE.getEditor('traeditor', {scaleEnabled: false, autoFloatEnabled: false,elementPathEnabled:false, readonly:false});

        //图片初始化
        WhgUploadImg.init({basePath: '${basePath}', uploadBtnId: 'imgUploadBtn1', hiddenFieldId: 'hide_picture1', previewImgId: 'previewImg1', needCut:true, cutWidth:380, cutHeight:240});
        WhgUploadImg.init({basePath: '${basePath}', uploadBtnId: 'imgUploadBtn2', hiddenFieldId: 'hide_picture2', previewImgId: 'previewImg2', needCut:true, cutWidth:400, cutHeight:250});

        $('#whgff').form({
            novalidate: true,
            url : getFullUrl('/admin/volun/addpro'),
            onSubmit : function(param) {
                param.zyfcxmstate = 0;
                //alert('onSubmit');
                var _valid = $(this).form('enableValidation').form('validate');
                //图片必填
                if(_valid){
                    if($('#hide_picture1').val() == ""){
                        _valid = false;
                        $.messager.alert('提示', '请选择列表图片');
                    }else if($('#hide_picture2').val() == ""){
                        _valid = false;
                        $.messager.alert('提示', '请选择详情页图片');
                    }else if (!mydescUE.hasContents()){
                        _valid = false;
                        $.messager.alert("提示", '项目实施内容不能为空', 'error');
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
                if(Json && Json.success == '0'){
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