<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<% request.setAttribute("basePath", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath());%>
<% request.setAttribute("resourceid", request.getParameter("rsid")); %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <c:choose>
        <c:when test="${not empty id and not empty targetShow}">
            <c:set var="pageTitle" value="查看培训师资"></c:set>
        </c:when>
        <c:when test="${not empty id}">
            <c:set var="pageTitle" value="编辑培训师资"></c:set>
        </c:when>
        <c:otherwise>
            <c:set var="pageTitle" value="添加培训师资"></c:set>
        </c:otherwise>
    </c:choose>
    <title>${pageTitle}</title>
    <%@include file="/pages/comm/admin/header.jsp"%>

    <link rel="stylesheet" href="${basePath}/static/admin/css/bootstrap.css"/>
    <link rel="stylesheet" href="${basePath}/static/admin/Font-Awesome/css/font-awesome.min.css"/>
    <link rel="stylesheet" href="${basePath}/static/admin/css/build.css"/>

    <script src="${basePath}/static/ueditor/ueditor.config.js"></script>
    <script src="${basePath}/static/ueditor/ueditor.all.min.js"></script>
    <script src="${basePath}/static/ueditor/lang/zh-cn/zh-cn.js"></script>

    <script src="${basePath}/static/admin/js/whgtoolmodule.js"></script>

    <!-- 图片上传相关 -->
    <script type="text/javascript" src="${basePath}/static/plupload/lib/plupload-2.1.2/js/plupload.full.min.js"></script>
    <script type="text/javascript" src="${basePath}/static/plupload/upload-img.js"></script>
    <!-- 图片上传相关-END -->

    <style>
        .slider-h{margin-left: 10px}
    </style>
</head>
<body>
<form id="whgff" method="post" class="whgff">
    <h2>${pageTitle}</h2>

    <div class="whgff-row">
        <div class="whgff-row-label"><label style="color: red">*</label>老师名称：</div>
        <div class="whgff-row-input">
            <input class="easyui-textbox" name="teachername" value="${tea.teachername}" style="width:500px; height:32px"
                   data-options="required:true,validType:['length[1,60]'],prompt:'请输入老师名称'">
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label"><i>*</i>图片：</div>
        <div class="whgff-row-input">
            <input type="hidden" id="cult_picture1" name="teacherpic" value="${tea.teacherpic}">
            <div class="whgff-row-input-imgview" id="previewImg1"></div>
            <div class="whgff-row-input-imgfile">
                <i><a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" id="imgUploadBtn1">选择图片</a></i>
                <i>图片格式为jpg、png、gif，建议图片尺寸 750*500，大小为2MB以内</i>
            </div>
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label"><label style="color: red">*</label>艺术类型：</div>
        <div class="whgff-row-input">
            <div class="checkbox checkbox-primary whg-js-data" value="${tea.teacherarttyp}" name="teacherarttyp" js-data="WhgComm.getArtType"></div>
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label"><label style="color: red">*</label>专长类型：</div>
        <div class="whgff-row-input">
            <div class="checkbox checkbox-primary whg-js-data" value="${tea.teachertype}" name="teachertype" js-data="WhgComm.getTEAType"></div>
            <%--<div class="radio radio-primary whg-js-data" name="teachertype" value="${tea.teachertype}" js-data="WhgComm.getTEAType"></div>--%>
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label"><label style="color: red">*</label>区域：</div>
        <div class="whgff-row-input">
            <div class="radio radio-primary whg-js-data" name="teacherarea" value="${tea.teacherarea}" js-data="WhgComm.getAreaType"></div>
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label"><label style="color: red">*</label>注册日期：</div>
        <div class="whgff-row-input">
            <input class="easyui-datetimebox" name="teacherregtime" value="<fmt:formatDate value="${tea.teacherregtime}" pattern="yyyy-MM-dd HH:mm:ss"/>" style="width:200px; height:32px" data-options="required:true">
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label"><label style="color: red">*</label>老师简介：</div>
        <div class="whgff-row-input">
            <input class="easyui-textbox" name="teacherintroduce" value="${tea.teacherintroduce}" style="width:500px; height:100px" data-options="multiline:true, required:true,validType:['length[1,400]'],prompt:'请填写老师简介'">
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label"><label style="color: red"></label>课程说明：</div>
        <div class="whgff-row-input">
            <input class="easyui-textbox" name="teachercourse" value="${tea.teachercourse}" style="width:500px; height:100px" data-options="multiline:true, validType:['length[1,200]'],prompt:'请填写课程说明'">
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label"><label style="color: red"></label>专长介绍：</div>
        <div class="whgff-row-input">
            <input class="easyui-textbox" name="teacherexpdesc" value="${tea.teacherexpdesc}" style="width:500px; height:100px" data-options="multiline:true, validType:['length[1,400]'],prompt:'请填写专长介绍'">
        </div>
    </div>
</form>

<div class="whgff-but" style="width: 400px; margin:20px 0px 50px 350px">
    <a class="easyui-linkbutton whgff-but-submit" iconCls="icon-ok">提 交</a>
    <a class="easyui-linkbutton whgff-but-clear" iconCls="icon-no">清 空</a>
</div>

<script>

    $(function () {
        //图片初始化
        var __WhgUploadImg = WhgUploadImg.init({basePath: '${basePath}', uploadBtnId: 'imgUploadBtn1', hiddenFieldId: 'cult_picture1', previewImgId: 'previewImg1'});

        var buts = $("div.whgff-but");
        var id = '${id}';
        var targetShow = '${targetShow}';
        var frm = $("#whgff");
        //添加时开启清除 ，其它为返回
        if (!id){
            buts.find("a.whgff-but-clear").on('click', function(){
                frm.form('disableValidation');
                frm.form('clear');
                frm.find("div.radio").find(':radio:eq(0)').click();
                __WhgUploadImg.clear();
            });
        }else{
            //处理返回
            buts.find("a.whgff-but-clear").linkbutton({
                text: '返 回',
                iconCls: 'icon-undo',
                onClick: function(){
                    if (!targetShow){
                        window.parent.$('#whgdg').datagrid('reload');
                    }
                    WhgComm.editDialogClose();
                }
            });
        }


        //查看时的处理
        if (targetShow){
            //取消表单验证
            frm.form("disableValidation");

            //组件设为只读
            $('.easyui-textbox').textbox('readonly');
            $('.easyui-combobox').combobox('readonly');
            $('.easyui-datetimebox').combobox('readonly');

            $("#imgUploadBtn1").hide();
            //处理选项点击不生效
            frm.find("input[type='checkbox'], input[type='radio']").on('click', function(){return false});

            //不显示提交 button
            buts.find("a.whgff-but-submit").hide();
            return;
        }

        //定义表单提交
        var url = id ? "${basePath}/admin/train/tea/edit" : "${basePath}/admin/train/tea/add";
        frm.form({
            url: url,
            novalidate: true,
            onSubmit: function (param) {
                if (id){
                    param.teacherid = id;
                }
                $(this).form("enableValidation");
                var isValid = $(this).form('validate');
                if(isValid){
                    //图片不能为空
                    var picture1 = $("#cult_picture1").val();
                    if (!picture1){
                        $.messager.alert("错误", '图片不能为空！', 'error');
                        isValid = false;
                    }
                    //艺术分类不能为空
                    var arttype = $("#whgff").find("input[name='teacherarttyp']:checked").val();
                    if (!arttype){
                        $.messager.alert("错误", '艺术类型不能为空！', 'error');
                        isValid = false;
                    }
                }
                if (!isValid){
                    $.messager.progress('close');
                    buts.find("a.whgff-but-submit").off('click').one('click', function () {
                        frm.submit();
                    });
                }
                return isValid;

            },
            success : function (data) {
                data = $.parseJSON(data);
                $.messager.progress('close');
                buts.find("a.whgff-but-submit").off('click').one('click', function () {
                    frm.submit();
                });
                if (!data.success || data.success != "1"){
                    $.messager.alert("错误", data.errormsg||'操作失败', 'error');
                    return;
                }
                if (!id){
                    $(this).form("disableValidation");
                    buts.find("a.whgff-but-clear").click();
                    $.messager.show({
                        title:'提示消息',
                        msg:'培训师资信息提交成功',
                        showType:'slide',
                        timeout:1000,
                        width: 300,
                        height: 200
                    });
                }else{
                    WhgComm.editDialogClose();
                    window.parent.$('#whgdg').datagrid('reload');
                }
            }
        });
        buts.find("a.whgff-but-submit").off('click').one('click', function () {
            frm.submit();
        });

    });


</script>

</body>
</html>
