<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<% request.setAttribute("basePath", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath());%>
<% request.setAttribute("resourceid", request.getParameter("rsid")); %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <c:choose>
        <c:when test="${not empty id and not empty targetShow}">
            <c:set var="pageTitle" value="场馆管理-查看场馆"></c:set>
        </c:when>
        <c:when test="${not empty id}">
            <c:set var="pageTitle" value="场馆管理-编辑场馆"></c:set>
        </c:when>
        <c:otherwise>
            <c:set var="pageTitle" value="场馆管理-添加场馆"></c:set>
        </c:otherwise>
    </c:choose>
    <title>${pageTitle}</title>

    <%@include file="/pages/comm/admin/header.jsp" %>
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

    <!-- 根据地址取坐标 -->
    <script type="text/javascript" src="${basePath}/static/common/js/whg.maps.js"></script>

</head>
<body class="body_add">
<form id="whgff" method="post" class="whgff">
    <h2>${pageTitle}</h2>
    <div class="whgff-row">
        <div class="whgff-row-label"><i>*</i>名称：</div>
        <div class="whgff-row-input">
            <input class="easyui-textbox" name="title" value="${whgVen.title}" style="width:600px; height:32px"
                   data-options="required:true,validType:['length[1,60]'], prompt:'请输入场馆名称'">
        </div>
    </div>

    <%--<div class="whgff-row">
        <div class="whgff-row-label"><i>*</i>场馆级别：</div>
        <div class="whgff-row-input" style="height:60px;padding-left:10px;">
            <input class="easyui-slider" name="level" value="${whgVen.level}" style="width:300px;"
                   data-options="showTip:true,rule:['1','2','3','4','5'],max:5,min:1">
        </div>
    </div>--%>

    <div class="whgff-row">
        <div class="whgff-row-label"><i>*</i>图片：</div>
        <div class="whgff-row-input">
            <input type="hidden" id="cult_picture1" name="imgurl" value="${whgVen.imgurl}">
            <div class="whgff-row-input-imgview" id="previewImg1"></div>
            <div class="whgff-row-input-imgfile">
                <i><a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" id="imgUploadBtn1">选择图片</a></i>
                <i>图片格式为jpg、png、gif，建议图片尺寸 750*500，大小为2MB以内</i>
            </div>
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label">建馆时间：</div>
        <div class="whgff-row-input">
            <input type="text" class="easyui-datebox" style="width:300px;height: 32px;" name="datebuild_str"
                   value="<fmt:formatDate value='${whgVen.datebuild}' pattern="yyyy-MM-dd"></fmt:formatDate>" data-options="editable:false,prompt:'请选择'"/>
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label"><label style="color: red">*</label>所属文化馆：</div>
        <div class="whgff-row-input">
            <select class="easyui-combobox" name="cultid" style="width:500px; height:32px"
                    data-options="editable:false, required:true, valueField:'id', textField:'text', value:'${whgVen.cultid}', data:WhgComm.getCult(),prompt:'请选择文化馆'"></select>
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label"><i>*</i>场馆分类：</div>
        <div class="whgff-row-input">
            <div class="radio radio-primary whg-js-data"
                 name="etype" value="${whgVen.etype}" js-data="WhgComm.getVenueType">
            </div>
        </div>
    </div>
    <%--<div class="whgff-row">
        <div class="whgff-row-label">艺术分类：</div>
        <div class="whgff-row-input">
            <div class="checkbox checkbox-primary whg-js-data"
                 name="arttype" value="${whgVen.arttype}" js-data="WhgComm.getArtType">
            </div>
        </div>
    </div>--%>
    <div class="whgff-row">
        <div class="whgff-row-label">场馆标签：</div>
        <div class="whgff-row-input">
            <div class="checkbox checkbox-primary whg-js-data"
                 name="etag" value="${whgVen.etag}" js-data="WhgComm.getVenueTag">
            </div>
        </div>
    </div>
    <div class="whgff-row">
        <div class="whgff-row-label">场馆关键字：</div>
        <div class="whgff-row-input">
            <%--<div class="checkbox checkbox-primary whg-js-data"
                 name="ekey" value="${whgVen.ekey}" js-data="WhgComm.getVenueKey">
            </div>--%>
            <select class="easyui-combobox" name="ekey" style="width:600px; height:32px"
                   data-options="multiple:true, valueField:'text', textField:'text', value:'${whgVen.ekey}', data:WhgComm.getVenueKey()" ></select>
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label"><i>*</i>区域：</div>
        <div class="whgff-row-input">
            <div class="radio radio-primary whg-js-data" name="area" value="${whgVen.area}"
                 js-data="WhgComm.getAreaType">
            </div>
        </div>
    </div>
    <div class="whgff-row">
        <div class="whgff-row-label"><i>*</i>地址：</div>
        <div class="whgff-row-input">
            <input class="easyui-textbox" id="address" name="address" value="${whgVen.address}" style="width:600px; height:32px"
                   data-options="required:true,validType:['length[1,60]'], prompt:'请输入详细地址'">
        </div>
    </div>
    <div class="whgff-row">
        <div class="whgff-row-label"><i>*</i>坐标：</div>
        <div class="whgff-row-input">
            <input class="easyui-numberbox" id="longitude" name="longitude" value="${whgVen.longitude}"
                   style="width:100px; height:32px" data-options="required:true, precision:6,readonly:true,prompt:'X轴'">
            -
            <input class="easyui-numberbox" id="latitude" name="latitude" value="${whgVen.latitude}" style="width:100px; height:32px"
                   data-options="required:true, precision:6,readonly:true,prompt:'Y轴'">
            <a class="easyui-linkbutton" id="getXYPointBtn" text="选择坐标"></a>
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label"><i>*</i>联系人：</div>
        <div class="whgff-row-input">
            <input class="easyui-textbox" name="contacts" value="${whgVen.contacts}" style="width:300px; height:32px"
                   data-options="required:true,validType:['length[1,20]'], prompt:'请输入联系人姓名'">
        </div>
    </div>
    <div class="whgff-row">
        <div class="whgff-row-label"><i>*</i>联系电话：</div>
        <div class="whgff-row-input">
            <input class="easyui-textbox" name="phone" value="${whgVen.phone}" style="width:300px; height:32px"
                   data-options="required:true,validType:['length[1,20]', 'isPhone'], prompt:'请输入联系人电话号码'">
        </div>
    </div>
    <div class="whgff-row">
        <div class="whgff-row-label"><i>*</i>场馆简介：</div>
        <div class="whgff-row-input">
            <input class="easyui-textbox" name="summary" value="${whgVen.summary}" multiline="true" style="width:600px;height: 100px;"
                   data-options="required:true,validType:['length[1,400]']">
        </div>
    </div>
    <div class="whgff-row">
        <div class="whgff-row-label"><i>*</i>场馆描述：</div>
        <div class="whgff-row-input">
            <script id="description" name="description" type="text/plain" style="width:600px; height:200px;"></script>
        </div>
    </div>
    <div class="whgff-row">
        <div class="whgff-row-label">场馆设施描述：</div>
        <div class="whgff-row-input">
            <script id="facilitydesc" name="facilitydesc" type="text/plain" style="width:600px; height:200px;"></script>
        </div>
    </div>
</form>

<div class="whgff-but" style="width: 400px; margin:20px 0px 50px 350px">
    <a class="easyui-linkbutton whgff-but-submit" iconCls="icon-ok">提 交</a>
    <a class="easyui-linkbutton whgff-but-clear" iconCls="icon-no">清 空</a>
</div>

<script>


    //处理UE
    var ueConfig = {
        scaleEnabled: false,
        autoFloatEnabled: false,
        elementPathEnabled:false,
        readonly: '${targetShow}'? true: false
    };
    var ue_description = UE.getEditor('description', ueConfig);
    var ue_facilitydesc = UE.getEditor('facilitydesc', ueConfig);
    ue_description.ready(function(){  ue_description.setContent('${whgVen.description}') });
    ue_facilitydesc.ready(function(){  ue_facilitydesc.setContent('${whgVen.facilitydesc}') });


    $(function(){

        //根据地址取坐标
        WhgMap.init({basePath:'${basePath}', addrFieldId:'address', xpointFieldId:'longitude', ypointFieldId:'latitude', getPointBtnId:'getXYPointBtn'});


        var id = '${id}';
        var targetShow = '${targetShow}';
        var frm = $("#whgff");
        var buts = $("div.whgff-but");

        var whgImg = WhgUploadImg.init({basePath: '${basePath}', uploadBtnId: 'imgUploadBtn1', hiddenFieldId: 'cult_picture1', previewImgId: 'previewImg1'});

        //添加时开启清除 ，其它为返回
        if (!id){
            buts.find("a.whgff-but-clear").on('click', function(){
                frm.form('clear');
                whgImg.clear();

                //第一个单选又点上
                frm.find("div.radio").find(':radio:eq(0)').click();
                ue_description.setContent('');
                ue_facilitydesc.setContent('');
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
            $('.easyui-datebox').combobox('readonly');
            //处理选项点击不生效
            frm.find("input[type='checkbox']").on('click', function(){return false});
            frm.find("input[type='radio']").attr('disabled', true);
            $("#getXYPointBtn").hide();
            $("#imgUploadBtn1").hide();

            $('.easyui-slider').slider({disabled:true});

            //不显示提交 button
            buts.find("a.whgff-but-submit").hide();
            return;
        }

        //定义表单提交
        var url = id ? "${basePath}/admin/venue/edit" : "${basePath}/admin/venue/add";
        frm.form({
            url: url,
            novalidate: true,
            onSubmit: function (param) {
                if (id){
                    param.id = id;
                }

                $(this).form("enableValidation");
                var isValid = $(this).form('validate');

                if (isValid){
                    isValid = validateUE();
                }

                if (!isValid){
                    $.messager.progress('close');
                    oneSubmit();
                }
                return isValid;
            },
            success: function (data) {
                data = $.parseJSON(data);
                $.messager.progress('close');
                oneSubmit();
                if (!data.success || data.success != "1"){
                    $.messager.alert("错误", data.errormsg||'操作失败', 'error');
                    return;
                }
                if (!id){
                    $(this).form("disableValidation");

                    buts.find("a.whgff-but-clear").click();

                    $.messager.show({
                        title:'提示消息',
                        msg:'场馆信息提交成功',
                        showType:'slide',
                        timeout:1000,
                        width: 300,
                        height: 200
                    });
                }else{
                    window.parent.$('#whgdg').datagrid('reload');
                    WhgComm.editDialogClose();
                }
            }
        });

        //处理重复点击提交
        function oneSubmit(){
            buts.find("a.whgff-but-submit").off('click').one('click', submitForm);
        }
        oneSubmit();

        //处理表单提交
        function submitForm(){
            $.messager.progress();
            frm.submit();
        }

        //处理UE项的验证
        function validateUE(){
            var picture1 = $("#cult_picture1").val();
            if (!picture1){
                $.messager.alert("错误", '图片不能为空', 'error');
                return false;
            }

            if (!ue_description.hasContents()){
                $.messager.alert("错误", '场馆描述不能为空', 'error');
                return false;
            }
//            if (!ue_facilitydesc.hasContents()){
//                $.messager.alert("错误", '场馆设施描述不能为空', 'error');
//                return false;
//            }
            return true;
        }
    });

</script>

</body>
</html>
