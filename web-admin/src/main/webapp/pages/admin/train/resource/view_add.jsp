<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<% request.setAttribute("basePath", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath());%>
<% request.setAttribute("resourceid", request.getParameter("rsid")); %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>添加资源</title>
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

<form id="whgff" class="whgff" method="post">

    <h2>添加资源</h2>
    <input type="hidden" name="reftype" value="${reftype}">
    <div class="whgff-row">
        <div class="whgff-row-label"><i>*</i>资源类型：</div>
        <select id="cc" class="easyui-combobox" name="enttype" panelHeight="auto" style="width:600px; height:32px" data-options="editable:true, limitToList:true,required:true,prompt:'请选择资源类型',value:'1'">
            <option value="1">图片</option>
            <option value="2">视频</option>
            <option value="3">音频</option>
        </select>
    </div>
    <div class="whgff-row">
        <div class="whgff-row-label"><i>*</i>资源名称：</div>
        <div class="whgff-row-input"><input class="easyui-textbox" name="entname" style="width:600px; height:32px" data-options="required:true, validType:'length[1,60]'"></div>
    </div>

    <div class="whgff-row video_wrap" style="display: none">
        <div class="whgff-row-label"><i>*</i>资源地址：</div>
        <div class="whgff-row-input">
            <input class="easyui-combobox" name="entdir" id="video_entdir" style="height:35px;width:190px" data-options="prompt:'请选择目录',editable:true,limitToList:true,valueField:'text',textField:'text',url:'${basePath}/admin/video/srchPagging?srchDir=1'"/>
            <input class="easyui-combobox" name="enturl" id="video_enturl" style="height:35px;width:400px" data-options="prompt:'请选择音视频',editable:true,limitToList:true,valueField:'addr',textField:'key'"/>
        </div>
    </div>

    <div class="whgff-row video_wrap" style="display: none">
        <div class="whgff-row-label"><i></i>视频/音频时长：</div>
        <div class="whgff-row-input"><input class="easyui-timespinner" name="enttimes" style="width:300px; height:32px" data-options="showSeconds:true" prompt="请选择资源时长"></div>
    </div>

    <div class="whgff-row picture_warp">
        <div class="whgff-row-label"><i>*</i>资源图片：</div>
        <div class="whgff-row-input">
            <input type="hidden" id="cult_picture2" name="penturl" >
            <div class="whgff-row-input-imgview" id="previewImg2"></div>
            <div class="whgff-row-input-imgfile">
                <i><a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" id="imgUploadBtn2">选择图片</a></i>
                <i>图片格式为jpg、png、gif，大小为2MB以内</i>
            </div>
        </div>
    </div>

    <div class="whgff-row video_wrap" id="spfm" style="display: none">
        <div class="whgff-row-label"><i></i>视频封面图片：</div>
        <div class="whgff-row-input">
            <input type="hidden" id="cult_picture1" name="deourl" >
            <div class="whgff-row-input-imgview" id="previewImg1"></div>
            <div class="whgff-row-input-imgfile">
                <i><a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" id="imgUploadBtn1">选择图片</a></i>
                <i>图片格式为jpg、png、gif，建议图片尺寸 750*500，大小为2MB以内</i>
            </div>
        </div>
    </div>

</form>

<div id="whgwin-add-btn" class="whgff-but" style="width: 400px; margin:20px 0px 50px 350px">
    <a href="javascript:void(0)" class="easyui-linkbutton whgff-but-submit" iconCls="icon-ok" id="whgwin-add-btn-save">提 交</a>
    <a href="javascript:void(0)" class="easyui-linkbutton whgff-but-clear" iconCls="icon-undo" onclick="WhgComm.editDialogClose()">返 回</a>
</div>

<!-- script -->
<script type="text/javascript">

    $(function () {
        //选择资源目录后级连资源地址
        $('#video_entdir').combobox({
            onChange: function (newv, oldv) {
                $('#video_enturl').combobox({
                    valueField:'addr',textField:'key',url:'${basePath}/admin/video/srchPagging?srchFile=1&dir='+encodeURIComponent(newv)
                });
            }
        });

        //根据下拉框的值进行显示隐藏操作
        $('#cc').combobox({
            onChange: function(newv,oldv) {
                if(newv == 1){//图片
                    $(".picture_warp").show();
                    $(".video_wrap").hide();
                }else if(newv == 2){//视频
                    $(".video_wrap").show();
                    $(".picture_warp").hide();
                }else if (newv == 3){//音频
                    $(".video_wrap").show();
                    $("#spfm").hide();
                    $(".picture_warp").hide();
                }
            }
        });

        //初始图片上传
        WhgUploadImg.init({basePath: '${basePath}', uploadBtnId: 'imgUploadBtn1', hiddenFieldId: 'cult_picture1', previewImgId: 'previewImg1',needCut:true});
        WhgUploadImg.init({basePath: '${basePath}', uploadBtnId: 'imgUploadBtn2', hiddenFieldId: 'cult_picture2', previewImgId: 'previewImg2',needCut:false});

        //初始表单
        $('#whgff').form({
            novalidate: true,
            url : getFullUrl('/admin/train/resource/add?id=${id}'),
            onSubmit : function(param) {
                var _valid = $(this).form('enableValidation').form('validate');
                if($("[name=enttype]").val() == ""){
                    _valid = false;
                    $.messager.alert('提示', '请选择资源类型');
                }else if($("[name=enttype]").val() != 1 && $("[name = enturl]").val() == ""){
                    _valid = false;
                    $.messager.alert('提示', '请选择资源地址');
                }else if($("[name=enttype]").val() == 1 && $("[name = penturl]").val() == ""){
                    _valid = false;
                    $.messager.alert('提示', '请选择资源图片');
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
                    $.messager.alert('提示', '操作失败！', 'error');
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