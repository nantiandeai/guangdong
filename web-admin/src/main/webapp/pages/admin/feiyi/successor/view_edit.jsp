<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<% request.setAttribute("basePath", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath());%>
<% request.setAttribute("resourceid", request.getParameter("rsid")); %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>名录项目管理-编辑</title>
    <link rel="stylesheet" href="${basePath}/static/admin/css/bootstrap.css"/>
    <link rel="stylesheet" href="${basePath}/static/admin/Font-Awesome/css/font-awesome.min.css"/>
    <link rel="stylesheet" href="${basePath}/static/admin/css/build.css"/>
    <!-- 富文本相关 -->
    <script type="text/javascript" charset="utf-8" src="${basePath}/static/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="${basePath}/static/ueditor/ueditor.all.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="${basePath}/static/ueditor/lang/zh-cn/zh-cn.js"></script>
    <!-- 富文本相关-END -->
    <%@include file="/pages/comm/admin/header.jsp"%>
    <script type="text/javascript" src="${basePath }/static/admin/js/common_img.js"></script>
    <!-- 图片上传相关 -->
    <script type="text/javascript" src="${basePath}/static/plupload/lib/plupload-2.1.2/js/plupload.full.min.js"></script>
    <script type="text/javascript" src="${basePath}/static/plupload/upload-img.js"></script>
    <!-- 图片上传相关-END -->
</head>
<body>
<h2>编辑名录</h2>
<form id="whgff" class="whgff" method="post" enctype="multipart/form-data" >
    <input type="hidden" name="mlproid" value="${mlproid}">
    <input type="hidden" name="suorid" value="${suorid}">
    <div class="whgff-row">
        <div class="whgff-row-label"><label style="color: red">*</label>传承人姓名：</div>
        <div class="whgff-row-input"><input class="easyui-textbox" name="suorname" value="${sc.suorname}" style="width:600px; height:32px" data-options="required:true, validType:'length[0,30]'"></div>
    </div>
    <div class="whgff-row">
        <div class="whgff-row-label"><label style="color: red">*</label>传承人类型：</div>
        <div class="whgff-row-input"><input class="easyui-combobox " name="suortype" value="${sc.suortype}"
                                            style="height:35px;width: 600px;"
                                            data-options="editable:false, valueField:'id',textField:'text',prompt:'传承人类型',data:WhgSysData.getTypeData('8'),required:true"/></div>
    </div>
    <div class="whgff-row">
        <div class="whgff-row-label"><label style="color: red">*</label>传承人批次：</div>
        <div class="whgff-row-input"><input class="easyui-combobox" name="suoritem" value="${sc.suoritem}" style="height:35px;width: 600px"
                                            data-options="editable:false,required:true, valueField:'id',textField:'text',prompt:'传承人批次', data:WhgSysData.getTypeData('9')"/></div>
    </div>
    <div class="whgff-row">
        <div class="whgff-row-label"><label style="color: red">*</label>传承人级别：</div>
        <div class="whgff-row-input"><input class="easyui-combobox" name="suorlevel" value="${sc.suorlevel}" style="height:35px;width: 600px"
                                            data-options="editable:false,required:true, valueField:'id',textField:'text', prompt:'传承人级别',data:WhgSysData.getTypeData('10')"/></div>
    </div>
    <div class="whgff-row">
        <div class="whgff-row-label"><label style="color: red">*</label>传承人区域：</div>
        <div class="whgff-row-input"><input class="easyui-combobox" name="suorqy" value="${sc.suorqy}" style="height:35px;width: 600px"
                                            data-options="editable:false,required:true, valueField:'id',textField:'text',prompt:'传承人区域', data:WhgSysData.getTypeData('6')"/></div>
    </div>
    <div class="whgff-row">
        <div class="whgff-row-label"><label style="color: red"></label>名录项目：</div>
        <div class="whgff-row-input"><input class="easyui-combobox" name="mlproid" value="${sc.proid}" style="height:35px;width: 600px"
                                            data-options="editable:false,required:false,prompt:'请选择名录项目',valueField:'mlproid',textField:'mlproshortitel',url:'${basePath}/admin/feiyi/selMingLu'"/></div>
    </div>
    <div class="whgff-row">
        <div class="whgff-row-label"><i>*</i>传承人图片：</div>
        <div class="whgff-row-input">
            <input type="hidden" id="cult_picture1" name="suorpic" value="${sc.suorpic}">
            <div class="whgff-row-input-imgview" id="previewImg1"></div>
            <div class="whgff-row-input-imgfile">
                <i><a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" id="imgUploadBtn1">选择图片</a></i>
                <i>图片格式为jpg、png、gif，建议图片尺寸 750*500，大小为2MB以内</i>
            </div>
        </div>
    </div>
    <div class="whgff-row">
        <div class="whgff-row-label"><i>*</i>传承人介绍：</div>
        <div class="whgff-row-input">
            <input class="easyui-textbox" name="suorjs" value="${sc.suorjs}" multiline="true" style="width:600px;height: 200px;"
                   data-options="required:true,validType:['length[0,800]']">
        </div>
    </div>
    <div class="whgff-row">
        <div class="whgff-row-label"><i></i>传承人成就：</div>
        <div class="whgff-row-input">
            <script id="catalog" type="text/plain" style="width:600px; height:200px;"></script>
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
    /**
     * 处理富文本框
     */
    var ueConfig = {
        scaleEnabled: false,
        autoFloatEnabled: false,
        elementPathEnabled:false,
        readonly: '${targetShow}'? true: false//富文本编辑器设为只读
    };
    var ue_catalog = UE.getEditor('catalog', ueConfig);
    ue_catalog.ready(function () {
        ue_catalog.setContent('${sc.suorachv}')
    });

    //查看时的处理
    $(function () {
        var targetShow = '${targetShow}';

        if (targetShow){
            //取消表单验证
            $("#whgff").form("disableValidation");
            //组件设为只读
            $('.easyui-textbox').textbox('readonly');
            $('.easyui-combobox').combobox('readonly');

            //不显示提交 button
            $('#whgwin-add-btn-save').hide();

        }
    });
    /**
     * 传承人编辑表单js
     */
    $(function () {
        WhgUploadImg.init({basePath: '${basePath}', uploadBtnId: 'imgUploadBtn1', hiddenFieldId: 'cult_picture1', previewImgId: 'previewImg1'});
        WhgUploadImg.init({basePath: '${basePath}', uploadBtnId: 'imgUploadBtn2', hiddenFieldId: 'cult_picture2', previewImgId: 'previewImg2', needCut:false});

        $('#whgff').form({
            novalidate: true,
            url: "${basePath}/admin/feiyi/addOrEditsuccessor",
            onSubmit : function(param) {
                var _valid = $(this).form('enableValidation').form('validate');
                if(_valid) {
//                    var isUEvalid = validateUE();
                    param.suorachv = ue_catalog.getContent();
                    $.messager.progress();

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
                    $.messager.alert('提示', '操作失败！', 'error');
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
