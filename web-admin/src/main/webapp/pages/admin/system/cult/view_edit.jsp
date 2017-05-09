<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% request.setAttribute("basePath", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath());%>
<% request.setAttribute("resourceid", request.getParameter("rsid")); %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>分馆管理-编辑分馆资料</title>
    <%@include file="/pages/comm/admin/header.jsp"%>
    <link rel="stylesheet" href="${basePath}/static/admin/css/bootstrap.css"/>
    <link rel="stylesheet" href="${basePath}/static/admin/Font-Awesome/css/font-awesome.min.css"/>
    <link rel="stylesheet" href="${basePath}/static/admin/css/build.css"/>
    <script type="text/javascript" src="${basePath}/static/plupload/lib/plupload-2.1.2/js/plupload.full.min.js"></script>
    <script type="text/javascript" src="${basePath}/static/plupload/upload-img.js"></script>
</head>
<body>


<form id="whgff" class="whgff" method="post">
    <c:choose>
        <c:when test="${param.onlyshow == '1'}">
            <h2>分馆管理-查看分馆资料</h2>
        </c:when>
        <c:otherwise>
            <h2>分馆管理-编辑分馆资料</h2>
        </c:otherwise>
    </c:choose>

    <input type="hidden" name="id" value="${cult.id}" >
    <input type="hidden" name="onlyshow" value="${param.onlyshow}" >

    <div class="whgff-row">
        <div class="whgff-row-label"><i>*</i>名称：</div>
        <div class="whgff-row-input"><input class="easyui-textbox" name="name" value="${cult.name}" style="width:300px; height:32px" data-options="required:true, validType:'length[3,30]'"></div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label"><i>*</i>LOGO：</div>
        <div class="whgff-row-input">
            <input type="hidden" id="cult_picture3" name="logopicture" value="${cult.logopicture}" >
            <div class="whgff-row-input-imgview" id="previewImg3" style="height: 70px;"></div>
            <div class="whgff-row-input-imgfile">
                <i><a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" id="imgUploadBtn3">选择图片</a></i>
                <i>图片格式为jpg、png、gif，建议图片尺寸 280*60，大小为2MB以内</i>
            </div>
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label"><i>*</i>图片：</div>
        <div class="whgff-row-input">
            <input type="hidden" id="cult_picture1" name="picture" value="${cult.picture}" >
            <div class="whgff-row-input-imgview" id="previewImg1" style="height: 200px;"></div>
            <div class="whgff-row-input-imgfile">
                <i><a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" id="imgUploadBtn1">选择图片</a></i>
                <i>图片格式为jpg、png、gif，建议图片尺寸 750*500，大小为2MB以内</i>
            </div>
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label"><i>*</i>背景图片：</div>
        <div class="whgff-row-input">
            <input type="hidden" id="cult_picture2" name="bgpicture"value="${cult.bgpicture}" >
            <div class="whgff-row-input-imgview" id="previewImg2"></div>
            <div class="whgff-row-input-imgfile">
                <i><a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" id="imgUploadBtn2">选择图片</a></i>
                <i>图片格式为jpg、png、gif，建议图片尺寸 1920*1080，大小为2MB以内</i>
            </div>
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label"><i>*</i>区${area}域：</div>
        <div class="whgff-row-input">
            <input class="easyui-combobox" name="area" style="width:300px; height:32px" value="${cult.area}"  data-options="prompt:'请选择所属区域', value:'${cult.area}',required:true, valueField:'id', textField:'text', data:WhgComm.getAreaType()">
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label"><i>*</i>联系人：</div>
        <div class="whgff-row-input"><input class="easyui-textbox" name="contact" value="${cult.contact}" style="width:300px; height:32px" data-options="prompt:'请输入联系人姓名',required:true,validType:['length[2,20]']"></div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label"><i>*</i>联系方式：</div>
        <div class="whgff-row-input"><input class="easyui-textbox" name="contactnum" value="${cult.contactnum}" style="width:300px; height:32px" data-options="prompt:'请输入联系人手机', required:true, validType:'isPhone'"></div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label"><i>*</i>文化馆地址：</div>
        <div class="whgff-row-input">
            <input class="easyui-textbox" name="address" value="${cult.address}" style="width:300px; height:32px" data-options="prompt:'请输入文化馆地址', required:true, validType:['length[2,35]']">
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label">文化馆站点：</div>
        <div class="whgff-row-input">
            <input class="easyui-textbox" name="siteurl" value="${cult.siteurl}" style="width:300px; height:32px" data-options="prompt:'文化馆站点地址，如:http://yoursite.com/', validType:['url', 'length[2,512]']">
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label"><i>*</i>简介：</div>
        <div class="whgff-row-input">
            <input class="easyui-textbox" name="introduction" id="introduction" value="${cult.introduction}" style="width:550px; height:250px" data-options="required:true, multiline:true, validType:['length[1,500]', 'isText']">
        </div>
    </div>
</form>

<div id="whgwin-edit-btn" class="whgff-but" style="width: 400px; margin:20px 0px 50px 350px">
    <c:if test="${param.onlyshow != '1'}">
    <a href="javascript:void(0)" class="easyui-linkbutton whgff-but-submit" iconCls="icon-ok" id="whgwin-edit-btn-save">提 交</a>
    </c:if>
    <a href="javascript:void(0)" class="easyui-linkbutton whgff-but-clear" iconCls="icon-undo" onclick="WhgComm.editDialogClose()">返 回</a>
</div>

<!-- script -->
<script type="text/javascript">
    $(function () {
        //图片初始化
        WhgUploadImg.init({basePath: '${basePath}', uploadBtnId: 'imgUploadBtn1', hiddenFieldId: 'cult_picture1', previewImgId: 'previewImg1'});
        WhgUploadImg.init({basePath: '${basePath}', uploadBtnId: 'imgUploadBtn2', hiddenFieldId: 'cult_picture2', previewImgId: 'previewImg2', needCut:false, cutWidth:1920 });
        WhgUploadImg.init({basePath: '${basePath}', uploadBtnId: 'imgUploadBtn3', hiddenFieldId: 'cult_picture3', previewImgId: 'previewImg3', needCut:true, cutWidth:280, cutHeight:60});

        $('#whgff').form({
            novalidate: true,
            url : getFullUrl('/admin/system/cult/edit'),
            onSubmit : function(param) {
                //alert('onSubmit');
                var _valid = $(this).form('enableValidation').form('validate');
                //图片必填
                if(_valid){
                    if($('#cult_picture3').val() == ""){
                        _valid = false;
                        $.messager.alert('提示', '请选择文化馆的LOGO图片');
                    }else if($('#cult_picture1').val() == ""){
                        _valid = false;
                        $.messager.alert('提示', '请选择文化馆的封面图片');
                    }else if($('#cult_picture2').val() == ""){
                        _valid = false;
                        $.messager.alert('提示', '请选择文化馆的主页背景图片');
                    }
                }
                if(_valid){
                    $.messager.progress();
                }else{
                    //失败时再注册提交事件
                    $('#whgwin-edit-btn-save').off('click').one('click', function () { $('#whgff').submit(); });
                }
                return _valid;
            },
            success : function(data) {
                $.messager.progress('close');
                var Json = eval('('+data+')');
                if(Json && Json.success == '1'){
                    if($('#whgff input[name="onlyshow"]').val() != "1") {
                        window.parent.$('#whgdg').datagrid('reload');
                    }
                    WhgComm.editDialogClose();
                } else {
                    $.messager.alert('提示', '操作失败: '+Json.errormsg+'!', 'error');
                    $('#whgwin-edit-btn-save').off('click').one('click', function () { $('#whgff').submit(); });
                }
            }
        });
        //注册提交事件
        $('#whgwin-edit-btn-save').off('click').one('click', function () { $('#whgff').submit(); });

        //查看状态下表单元素不能编辑
        if($('#whgff input[name="onlyshow"]').val() == "1"){
            $('#whgff input').attr('readonly', 'readonly');
            $('#whgff textarea').attr('readonly', 'readonly');
        }
    });
</script>
<!-- script END -->
</body>
</html>