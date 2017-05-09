<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<% request.setAttribute("basePath", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath());%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>栏目内容管理</title>

    <%@include file="/pages/comm/admin/header.jsp" %>
    <link rel="stylesheet" href="${basePath}/static/admin/css/bootstrap.css"/>
    <link rel="stylesheet" href="${basePath}/static/admin/Font-Awesome/css/font-awesome.min.css"/>
    <link rel="stylesheet" href="${basePath}/static/admin/css/build.css"/>

    <script src="${basePath}/static/ueditor/ueditor.config.js"></script>
    <script src="${basePath}/static/ueditor/ueditor.all.min.js"></script>
    <script src="${basePath}/static/ueditor/lang/zh-cn/zh-cn.js"></script>

    <script src="${basePath}/static/admin/js/whgtoolmodule.js"></script>

</head>
<body class="body_add">
<form id="whgff" method="post" class="whgff">
    <h2>资讯公告管理</h2>

    <div class="whgff-row">
        <div class="whgff-row-label"><i>*</i>类型：</div>
        <%--<select id="cc" class="easyui-combobox" name="type" style="width:300px; height:32px" data-options="editable:false,required:true," prompt='请选择类型'>
            <option value="" >请选择类型</option>
            <option value="1">资讯</option>
            <option value="2">公告</option>
        </select>--%>
        <div class="whgff-row-input">
            <div class="radio radio-primary whg-js-data" value="${type}" name="type" js-data='[{"id":"1","text":"资讯"}]'></div>
        </div>
    </div>

    <div class="whgff-row" refField="clnftltle">
        <div class="whgff-row-label">标题：</div>
        <div class="whgff-row-input">
            <input class="easyui-textbox" name="clnftltle" style="width:600px; height:32px"
                   data-options="required:true,validType:['length[1,60]'], prompt:'请输入标题'">
        </div>
    </div>

    <div class="whgff-row" refField="clnfsource">
        <div class="whgff-row-label">来源：</div>
        <div class="whgff-row-input">
            <input class="easyui-textbox" name="clnfsource" style="width:600px; height:32px"
                   data-options="required:true,validType:['length[1,60]'], prompt:'请输入来源'">
        </div>
    </div>

    <div class="whgff-row" refField="clnfauthor">
        <div class="whgff-row-label">作者：</div>
        <div class="whgff-row-input">
            <input class="easyui-textbox" name="clnfauthor" style="width:600px; height:32px"
                   data-options="required:true,validType:['length[1,60]'], prompt:'请输入作者'">
        </div>
    </div>

    <div class="whgff-row" refField="clnfkey">
        <div class="whgff-row-label">关键字：</div>
        <div class="whgff-row-input">

            <input id="clnfkey" class="easyui-combobox" name="clnfkey" multiple="true" style="width: 600px;height:32px;" data-options="required:true,prompt:'请输入关键字', panelHeight:'auto',editable:true, valueField:'text',textField:'text', data: WhgComm.getZxKey(), multiple:true"/>
        </div>
    </div>
    </div>

    <div class="whgff-row" refField="clnfcrttime">
        <div class="whgff-row-label">创立时间：</div>
        <div class="whgff-row-input">
            <input type="text" class="easyui-datetimebox" style="width:300px;height: 32px;" name="clnfcrttime" data-options="editable:false,prompt:'请选择'"/>
        </div>
    </div>

    <div class="whgff-row" refField="clnfintroduce">
        <div class="whgff-row-label">简介：</div>
        <div class="whgff-row-input">
            <input class="easyui-textbox" name="clnfintroduce" multiline="true" style="width:600px;height: 100px;"
                   data-options="required:false,validType:['length[1,400]']">
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label">详细介绍：</div>
        <div class="whgff-row-input">
            <script id="clnfdetail" name="clnfdetail" type="text/plain" style="width:600px; height:200px;"></script>
        </div>
    </div>

</form>

<div class="whgff-but" style="width: 400px; margin:20px 0px 50px 350px">
    <a class="easyui-linkbutton whgff-but-submit" iconCls="icon-ok">提 交</a>
    <a class="easyui-linkbutton whgff-but-clear" iconCls="icon-undo" onclick="WhgComm.editDialogClose()">返 回</a>
</div>

<script>
    //处理UE
    var ueConfig = {
        scaleEnabled: false,
        autoFloatEnabled: false,
    };

    $(function(){
        var ue_description = UE.getEditor('clnfdetail', ueConfig);

        var frm = $("#whgff");
        var buts = $("div.whgff-but");

        //表单提交处理
        var url = "${basePath}/admin/info/add";
        frm.form({
            url: url,
            novalidate: true,
            onSubmit: function (param) {
                param.entityid = ${entityid};
                param.entity = ${entity};
                param.clnfdetail = ue_description.getContent();
                $(this).form("enableValidation");
                var isValid = $(this).form('validate');
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
                    $.messager.alert("错误", data.msg||'操作失败', 'error');
                    return;
                }
                window.parent.$('#whgdg').datagrid('reload');
                WhgComm.editDialogClose();
            }
        });

        //处理表单提交
        function submitForm(){
            $.messager.progress();
            frm.submit();
        }

        //处理重复点击提交
        function oneSubmit(){
            buts.find("a.whgff-but-submit").off('click').one('click', submitForm);
        }
        oneSubmit();

    });
</script>

</body>
</html>
