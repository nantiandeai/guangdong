<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<% request.setAttribute("basePath", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath());%>
<% request.setAttribute("resourceid", request.getParameter("rsid")); %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>先进个人管理-编辑</title>
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
<h2>编辑先进个人</h2>
<form id="whgff" class="whgff" method="post" enctype="multipart/form-data" >
    <input type="hidden" name="zyfcgrid" value="${zyfcgrid}">
    <div class="whgff-row">
        <div class="whgff-row-label"><label style="color: red">*</label>详情标题：</div>
        <div class="whgff-row-input"><input class="easyui-textbox" name="zyfcgrtitle" value="${person.zyfcgrtitle}" style="width:600px; height:32px" data-options="required:true, validType:'length[0,60]'"></div>
    </div>
    <div class="whgff-row">
        <div class="whgff-row-label"><label style="color: red">*</label>列表短标题：</div>
        <div class="whgff-row-input"><input class="easyui-textbox" name="zyfcgrshorttitle" value="${person.zyfcgrshorttitle}" style="width:600px; height:32px" data-options="required:true, validType:'length[0,60]'"></div>
    </div>
    <div class="whgff-row">
        <div class="whgff-row-label"><label style="color: red"></label>文艺专长：</div>
        <div class="whgff-row-input"><input class="easyui-textbox" name="zyfcgrzc" value="${person.zyfcgrzc}" style="width:600px; height:32px" data-options="required:false, validType:'length[0,200]'"></div>
    </div>
    <div class="whgff-row">
        <div class="whgff-row-label"><label style="color: red"></label>工作单位：</div>
        <div class="whgff-row-input"><input class="easyui-textbox" name="zyfcgrworkaddr" value="${person.zyfcgrworkaddr}" style="width:600px; height:32px" data-options="required:false, validType:'length[0,200]'"></div>
    </div>
    <div class="whgff-row">
        <div class="whgff-row-label"><label style="color: red"></label>参与活动：</div>
        <div class="whgff-row-input"><input class="easyui-textbox" name="zyfcgrjoinact" value="${person.zyfcgrjoinact}" style="width:600px; height:32px" data-options="required:false, validType:'length[0,200]'"></div>
    </div>
    <div class="whgff-row">
        <div class="whgff-row-label"><label style="color: red"></label>加入时间：</div>
        <div class="whgff-row-input"><input id="zyfcgrjrtime" name="zyfcgrjrtime" value="${person.zyfcgrjrtime}" type="text" class="easyui-datetimebox" data-options="editable:false, required:false"  style="height: 35px; width: 600px"></div>
    </div>
    <div class="whgff-row">
        <div class="whgff-row-label"><label style="color: red">*</label>服务地区：</div>
        <div class="whgff-row-input"><input class="easyui-textbox" name="zyfcgrscope" value="${person.zyfcgrscope}" style="width:600px; height:32px" data-options="required:true, validType:'length[0,200]'"></div>
    </div>
    <div class="whgff-row">
        <div class="whgff-row-label"><label style="color: red">*</label>服务时间：</div>
        <div class="whgff-row-input"><input id="zyfcgrfwtime" name="zyfcgrfwtime" value="${person.zyfcgrfwtime}" type="text" class="easyui-datetimebox" data-options="editable:false, required:true"  style="height: 35px; width: 600px"></div>
    </div>
    <div class="whgff-row">
        <div class="whgff-row-label">服务时长：</div>
        <div class="whgff-row-input"><input id="zyfwhen"  name="zyfwhen" value="${person.zyfwhen}" class="easyui-numberspinner" style=" height: 35px; width: 600px " data-options="min:1,max:100000000,editable:true,required:false">  </div>
    </div>
    <div class="whgff-row">
        <div class="whgff-row-label"><i>*</i>列表页图：</div>
        <div class="whgff-row-input">
            <input type="hidden" id="cult_picture1" name="zyfcgrpic" value="${person.zyfcgrpic}">
            <div class="whgff-row-input-imgview" id="previewImg1"></div>
            <div class="whgff-row-input-imgfile">
                <i><a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" id="imgUploadBtn1">选择图片</a></i>
                <i>图片格式为jpg、png、gif，建议图片尺寸 380*240，大小为1MB以内</i>
            </div>
        </div>
    </div>
    <div class="whgff-row">
        <div class="whgff-row-label"><i>*</i>详情图片：</div>
        <div class="whgff-row-input">
            <input type="hidden" id="cult_picture2" name="zyfcgrbigpic" value="${person.zyfcgrbigpic}">
            <div class="whgff-row-input-imgview" id="previewImg2"></div>
            <div class="whgff-row-input-imgfile">
                <i><a href="#" class="easyui-linkbutton" iconCls="icon-save" id="imgUploadBtn2">选择图片</a></i>
                <i>图片格式为jpg、png、gif，建议图片尺寸 750*500，大小为1MB以内</i>
            </div>
        </div>
    </div>
    <div class="whgff-row">
        <div class="whgff-row-label"><i>*</i>事迹材料：</div>
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
        readonly: '${targetShow}'? true: false//富文本编辑器设为只读
    };
    var ue_catalog = UE.getEditor('catalog', ueConfig);
    ue_catalog.ready(function () {
        ue_catalog.setContent('${person.zyfcgrcontent}')
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
            $('.easyui-numberspinner').numberspinner('readonly');
            //不显示提交 button
            $('#whgwin-add-btn-save').hide();
        }
    });
    /**
     * 先进个人表单提交
     */
    $(function () {
        WhgUploadImg.init({basePath: '${basePath}', uploadBtnId: 'imgUploadBtn1', hiddenFieldId: 'cult_picture1', previewImgId: 'previewImg1', needCut:true,cutWidth:380, cutHeight:240});
        WhgUploadImg.init({basePath: '${basePath}', uploadBtnId: 'imgUploadBtn2', hiddenFieldId: 'cult_picture2', previewImgId: 'previewImg2', needCut:true,cutWidth:400, cutHeight:250});

        $('#whgff').form({
            novalidate: true,
            url: "${basePath}/admin/volun/addOrUpdatePerson",
            onSubmit : function(param) {
                var _valid = $(this).form('enableValidation').form('validate')
                if(_valid) {
                    var isUEvalid = validateUE();
                    if (isUEvalid) {
                        param.mlprodetail = ue_catalog.getContent();
                        $.messager.progress();
                    } else {
                        _valid = false;
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
                    $.messager.alert('提示', '操作失败！', 'error');
                }
            }
        });
        //注册提交事件
        $('#whgwin-add-btn-save').off('click').one('click', function () { $('#whgff').submit(); });
    });

    //富文本验证
    function validateUE(){
        if (!ue_catalog.hasContents()) {
            $.messager.alert("错误", '组织详情不能为空', 'error');
            return false;
        }
        return true;
    }

</script>
<!-- script END -->
</body>
</html>
