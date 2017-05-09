<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<% request.setAttribute("basePath", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath());%>
<% request.setAttribute("resourceid", request.getParameter("rsid")); %>
<% String path = request.getContextPath();%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>评论管理-回复</title>
    <%@include file="/pages/comm/admin/header.jsp"%>
    <link rel="stylesheet" href="${basePath}/static/admin/Font-Awesome/css/font-awesome.min.css"/>
    <link rel="stylesheet" href="${basePath}/static/admin/css/build.css"/>
</head>
<body>

<form id="whgff" class="whgff" method="post">
    <h2>${commentInfo.rmtitle }</h2>
    <div class="whgff-row">
        <div class="whgff-row-label">评论人：</div>
        <div class="whgff-row-input">
            <input class="easyui-textbox" name="host" value="${comentUser.nickname }"
                   style="width: 500px; height: 32px" data-options="required:false,readonly:true" />
        </div>
    </div>
    <%--<div class="whgff-row">--%>
        <%--<div class="whgff-row-label">评论内容：</div>--%>
        <%--<div class="whgff-row-input">--%>
            <%--<input class="easyui-textbox" name="host" value="${commentInfo.rmcontent }"--%>
                   <%--style="width: 500px; height: 32px" data-options="required:false, readonly:true" />--%>
        <%--</div>--%>
    <%--</div>--%>
    <div class="whgff-row">
        <div class="whgff-row-label"><i>*</i>评论内容：</div>
        <div class="whgff-row-input">
            <input class="easyui-textbox" name="host" value="${commentInfo.rmcontent}" style="width:500px; height:100px" data-options="required:false,multiline:true,readonly:true">
        </div>
    </div>
    <div class="whgff-row">
        <div class="whgff-row-label">评论时间：</div>
        <div class="whgff-row-input">
            <input class="easyui-textbox" name="host" value="<fmt:formatDate value="${commentInfo.rmdate }" pattern="yyyy-MM-dd HH:mm:ss"/>"
                   style="width: 500px; height: 32px" data-options="required:false,readonly:true" />
        </div>
    </div>
    <div class="whgff-row">
        <div class="whgff-row-label">回复类型：</div>
        <div class="whgff-row-input">
            <%--<input class="easyui-textbox" name="host" value="<fmt:formatDate value="${commentInfo.rmdate }" pattern="yyyy-MM-dd HH:mm:ss"/>"--%>
                   <%--style="width: 500px; height: 32px" data-options="required:false,readonly:true" />--%>
            <select  class="easyui-combobox" name="rmpltype" style="width: 200px">
            <option value="0">内部回复</option>
            <option value="1">公开回复</option>
            </select>
        </div>
    </div>
    <%--<div class="whgff-row">--%>
        <%--<div class="whgff-row-label">回复信息：</div>--%>
        <%--<div class="whgff-row-input">--%>
            <%--<input class="easyui-textbox"  name="rmcontent" data-options="required:true,validType:'length[0,400]'"--%>
                   <%--style="width:500px;height:80px;">--%>
        <%--</div>--%>
    <%--</div>--%>
    <div class="whgff-row">
        <div class="whgff-row-label"><i>*</i>回复信息：</div>
        <div class="whgff-row-input">
            <input class="easyui-textbox" name="rmcontent" style="width:500px; height:100px" data-options="required:true, multiline:true, validType:['length[1,400]', 'isText']">
        </div>
    </div>
</form>

<div id="whgwin-edit-btn" class="whgff-but" style="width: 400px; margin:20px 0px 50px 350px">
    <a class="easyui-linkbutton whgff-but-submit" iconCls="icon-ok">提 交</a>
    <a href="javascript:void(0)" class="easyui-linkbutton whgff-but-clear" iconCls="icon-undo" onclick="WhgComm.editDialogClose()">返 回</a>
</div>
<script>
    var frm;
    var buts;
    $(function(){
        var id = '${commentInfo.rmid}';
        frm = $("#whgff");
        buts = $("div.whgff-but");
        oneSubmit();
        //定义表单提交
        var url = '${basePath}/admin/yunwei/comment/addReply';
        frm.form({
            url: url,
            novalidate: true,
            onSubmit: function (param) {
               param.rmid = id;
                $(this).form("enableValidation");
                var isValid = $(this).form('validate');
                if (!isValid){
                    $.messager.progress('close');
                    oneSubmit();
                }
                return isValid;
            },
            success: function (data) {
                $.messager.progress('close');
                data = $.parseJSON(data);
                if(data.success == 1){
                    window.parent.$('#whgdg').datagrid('reload');
                    WhgComm.editDialogClose();
                }else{
                    $.messager.alert("错误", data.errormsg||'操作失败', 'error');
                    return;
                }

            }
        })
    });
    //处理重复点击提交
    function oneSubmit(){
        buts.find("a.whgff-but-submit").off('click').one('click', submitForm);
    }
    //处理表单提交
    function submitForm(){
        $.messager.progress();
        frm.submit();
    }
</script>
</body>
</html>