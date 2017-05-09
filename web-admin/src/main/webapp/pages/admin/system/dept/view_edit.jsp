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
</head>
<body>

<c:choose>
    <c:when test="${param.onlyshow == '1'}">
        <h3>分馆管理-查看分馆资料</h3>
    </c:when>
    <c:otherwise>
        <h3>分馆管理-编辑分馆资料</h3>
    </c:otherwise>
</c:choose>

<form id="whgff" method="post" style="width: 800px;">
    <input type="hidden" name="id" value="${cult.id}" >
    <input type="hidden" name="onlyshow" value="${param.onlyshow}" >

    <div class="whgff-row">
        <div class="whgff-row-label"><label style="color: red">*</label>名称：</div>
        <div class="whgff-row-input"><input class="easyui-textbox" name="name" value="${cult.name}" style="width:300px; height:32px" data-options="required:true, validType:'length[3,30]'"></div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label"><label style="color: red">*</label>图片：</div>
        <div class="whgff-row-input">
            <input type="hidden" name="picture" value="${cult.picture}" >
            <div class="whgff-row-input-imgview"></div>
            <div class="whgff-row-input-imgfile">
                <a href="#" class="easyui-linkbutton" iconCls="icon-save" onclick="">选择图片</a>
            </div>
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label"><label style="color: red">*</label>背景图片：</div>
        <div class="whgff-row-input">
            <input type="hidden" name="bgpicture" value="${cult.bgpicture}" >
            <div class="whgff-row-input-imgview"></div>
            <div class="whgff-row-input-imgfile">
                <a href="#" class="easyui-linkbutton" iconCls="icon-save" onclick="">选择图片</a>
            </div>
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label"><label style="color: red">*</label>联系人：</div>
        <div class="whgff-row-input"><input class="easyui-textbox" name="contact" value="${cult.contact}" style="width:300px; height:32px" data-options="prompt:'请输入联系人姓名',required:true,validType:['length[2,20]']"></div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label"><label style="color: red">*</label>联系方式：</div>
        <div class="whgff-row-input"><input class="easyui-textbox" name="contactnum" value="${cult.contactnum}" style="width:300px; height:32px" data-options="prompt:'请输入联系人手机', required:true, validType:'isPhone'"></div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label"><label style="color: red">*</label>简介：</div>
        <div class="whgff-row-input">
            <textarea style="width: 400px" rows="15" name="introduction">${cult.introduction}</textarea>
        </div>
    </div>
</form>
<div id="whgwin-edit-btn" style="text-align: center;">
    <div style="display: inline-block; margin: 0 auto">
        <c:if test="${param.onlyshow != '1'}">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" id="whgwin-edit-btn-save">保 存</a>
        </c:if>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" onclick="WhgComm.editDialogClose()">返  回</a>
    </div>
</div>

<!-- script -->
<script type="text/javascript">
$(function () {
    $('#whgff').form({
        novalidate: true,
        url : getFullUrl('/admin/system/cult/edit'),
        onSubmit : function(param) {
            //alert('onSubmit');
            var _valid = $(this).form('enableValidation').form('validate')
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
                window.parent.$('#whgdg').datagrid('reload');
                WhgComm.editDialogClose();
            } else {
                $.messager.alert('提示', '操作失败:'+Json.errormsg+'!', 'error');
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
