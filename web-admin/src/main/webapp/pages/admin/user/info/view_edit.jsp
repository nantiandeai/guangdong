<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<% request.setAttribute("basePath", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath());%>
<% request.setAttribute("resourceid", request.getParameter("rsid")); %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>会员管理-编辑会员资料</title>
    <%@include file="/pages/comm/admin/header.jsp"%>
    <link rel="stylesheet" href="${basePath}/static/admin/css/bootstrap.css"/>
    <link rel="stylesheet" href="${basePath}/static/admin/Font-Awesome/css/font-awesome.min.css"/>
    <link rel="stylesheet" href="${basePath}/static/admin/css/build.css"/>

    <script type="text/javascript" src="${basePath}/static/plupload/lib/plupload-2.1.2/js/plupload.full.min.js"></script>
    <script type="text/javascript" src="${basePath}/static/plupload/upload-img.js"></script>

    <script type="text/javascript" src="${basePath}/static/admin/js/whgtoolmodule.js"></script>

    <script type="text/javascript">
        /** 性别  */
        function getSexData() {
            return [{"id":"0", "text":"女"}, {"id":"1", "text":"男"}];
        }

        /** 是否实名 */
        function getRealnameData() {
            return [{"id":"0", "text":"待完善"}, {"id":"1", "text":"已认证"}, {"id":"2", "text":"认证失败"}, {"id":"3", "text":"待认证"}];
        }

        /** 审核状态 */
        function getCheckData() {
            return [{"id":"1", "text":"审核通过"}, {"id":"2", "text":"审核不通过"}];
        }
    </script>
</head>
<body>


<form id="whgff" class="whgff" method="post">
    <c:choose>
        <c:when test="${param.onlyshow == '1'}">
            <h2>会员管理-查看会员资料</h2>
        </c:when>
        <c:otherwise>
            <h2>会员管理-编辑会员资料</h2>
        </c:otherwise>
    </c:choose>

    <input type="hidden" name="onlyshow" value="${param.onlyshow}" >

    <div class="whgff-row">
        <div class="whgff-row-label">昵称：</div>
        <div class="whgff-row-input"><input class="easyui-textbox" name="nickname" value="${whuser.nickname}" style="width:300px; height:32px" data-options="required:true, validType:'length[3,30]'"></div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label">手机号码：</div>
        <div class="whgff-row-input"><input class="easyui-textbox" name="phone" value="${whuser.phone}" style="width:300px; height:32px" data-options="required:true, validType:'length[3,30]'"></div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label">邮箱：</div>
        <div class="whgff-row-input"><input class="easyui-textbox" name="email" value="${whuser.email}" style="width:300px; height:32px" data-options="required:true, validType:'length[3,30]'"></div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label">性别：</div>
        <div class="whgff-row-input">
            <div class="radio radio-primary whg-js-data" name="sex" value="${whuser.sex}" js-data="getSexData">
                <%--<input type="radio" name="radio" value="1" id="r-1">
                <label for="r-1">
                    图书馆
                </label>--%>
            </div>
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label">出生日期：</div>
        <div class="whgff-row-input">
            <input type="text" class="easyui-datebox" style="width:300px;height: 32px;" name="birthday" required="required" value="<fmt:formatDate value="${whuser.birthday}" pattern="yyyy-MM-dd"/>" data-options="prompt:'请选择'"/>
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label">籍贯：</div>
        <div class="whgff-row-input">
            <input class="easyui-textbox" name="origo" value="${whuser.origo}" style="width:300px; height:32px" data-options="required:true, validType:'length[3,30]'">
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label">民族：</div>
        <div class="whgff-row-input">
            <input class="easyui-textbox" name="nation" value="${whuser.nation}" style="width:300px; height:32px" data-options="required:true, validType:'length[3,30]'">
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label">通讯地址：</div>
        <div class="whgff-row-input">
            <input class="easyui-textbox" name="address" value="${whuser.address}" style="width:300px; height:32px" data-options="required:true, validType:'length[3,30]'">
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label">是否实名：</div>
        <div class="whgff-row-input">
            <div class="radio radio-primary whg-js-data" name="isrealname" value="${whuser.isrealname}" js-data="getRealnameData"></div>
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label">实名姓名：</div>
        <div class="whgff-row-input">
            <input class="easyui-textbox" name="name" value="${whuser.name}" style="width:300px; height:32px" data-options="required:true, validType:'length[3,30]'">
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label">身份证号码：</div>
        <div class="whgff-row-input">
            <input class="easyui-textbox" name="idcard" value="${whuser.idcard}" style="width:300px; height:32px" data-options="required:true, validType:'length[3,30]'">
        </div>
    </div>

    <%--<div class="whgff-row">
        <div class="whgff-row-label"><i>*</i>身份证手持：</div>
        <div class="whgff-row-input">
            <input type="hidden" id="cult_picture1" name="idcardface" value="${cult.idcardface}" >
            <div class="whgff-row-input-imgview" id="previewImg1"></div>
        </div>
    </div>--%>

    <div class="whgff-row">
        <div class="whgff-row-label"><i>*</i>身份证正面：</div>
        <div class="whgff-row-input">
            <input type="hidden" id="cult_picture2" name="idcardface" value="${whuser.idcardface}" >
            <div class="whgff-row-input-imgview" style="background: none; width: 450px;">
                <img src="${imgServerAddr}${whuser.idcardface}" style="width: 450px; height: 225px;" alt="身份证正面">
            </div>
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label"><i>*</i>身份证背面：</div>
        <div class="whgff-row-input">
            <input type="hidden" id="cult_picture3" name="idcardback" value="${whuser.idcardback}" >
            <div class="whgff-row-input-imgview" style="background: none; width: 450px;">
                <img src="${imgServerAddr}${whuser.idcardback}" style="width: 450px; height: 225px;" alt="身份证背面">
            </div>
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
        //双击大图
        $('.whgff-row-input-imgview').on('click', function () {
            var imgAddr = $(this).find('img').attr('src');
            var id = "imgShowBigImg_dialog";
            if( $('#'+id).size() > 0 ){
                $('#'+id).dialog('destroy');
                $('#'+id).remove();
            }
            $('<div id="'+id+'" style="overflow:hidden"></div>').appendTo($("body"));
            $('#'+id).dialog({
                title: '查看原图',
                closable: true,
                border: false,
                modal: true,
                content: '<img src="'+imgAddr+'">'
            });
            if( $('#'+id).parent('div').height() > $(window).height() ){
                $('#'+id).parent('div').css('top', '30px');
                $('div.window-shadow').css('top', '30px');
            }
        });

        //图片初始化
        WhgUploadImg.init({basePath: '${basePath}', uploadBtnId: 'imgUploadBtn1', hiddenFieldId: 'cult_picture1', previewImgId: 'previewImg1'});
        WhgUploadImg.init({basePath: '${basePath}', uploadBtnId: 'imgUploadBtn2', hiddenFieldId: 'cult_picture2', previewImgId: 'previewImg2', needCut:false});
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
            $('#whgff datebox').attr('readonly', 'readonly');
        }
    });
</script>
<!-- script END -->
</body>
</html>