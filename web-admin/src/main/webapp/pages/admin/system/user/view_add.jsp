<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<% request.setAttribute("basePath", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath());%>
<% request.setAttribute("resourceid", request.getParameter("rsid")); %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>管理员管理-添加管理员</title>
    <%@include file="/pages/comm/admin/header.jsp"%>
    <link rel="stylesheet" href="${basePath}/static/admin/css/bootstrap.css"/>
    <link rel="stylesheet" href="${basePath}/static/admin/Font-Awesome/css/font-awesome.min.css"/>
    <link rel="stylesheet" href="${basePath}/static/admin/css/build.css"/>

    <script type="text/javascript" src="${basePath }/static/common/js/jQuery.md5.js"></script>
    <script type="text/javascript" src="${basePath}/static/admin/js/whgtoolmodule.js"></script>
    <script type="text/javascript">
        $.extend($.fn.validatebox.defaults.rules, {
            isAccount: {
                validator: function (value, param) {
                    if(!/^\w{3,30}$/.test(value)){
                        $.fn.validatebox.defaults.rules.isAccount.message = "请输入3到30个字符长度的登录账号, 只允许字母数字和下划线";
                        return false;
                    }
                    return true;
                },
                message: ''
            }, myPassword: {
                validator: function (value, param) {
                    var p1 = $('#password1').passwordbox('getValue');
                    if (value != p1) {
                        $.fn.validatebox.defaults.rules.myPassword.message = "两次密码输入不一致.";
                        return false;
                    }
                    return true;
                },
                message: ''
            }
        });
    </script>
</head>
<body>


<form id="whgff" class="whgff" method="post">
    <h2>管理员管理-添加管理员</h2>
    <div class="whgff-row">
        <div class="whgff-row-label"><i>*</i>账号：</div>
        <div class="whgff-row-input"><input class="easyui-textbox" name="account" style="width:300px; height:32px" data-options="prompt:'请输入管理员账号',required:true, validType:['length[3,30]','isAccount']"></div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label"><i>*</i>密码：</div>
        <div class="whgff-row-input"><input class="easyui-passwordbox" id="password1" name="password1" style="width:300px; height:32px" data-options="prompt:'请输入管理员密码',required:true,validType:['length[2,20]']"></div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label"><i>*</i>确认密码：</div>
        <div class="whgff-row-input"><input class="easyui-passwordbox" id="password2" name="password2" style="width:300px; height:32px" data-options="prompt:'请输入管理员确认密码',required:true,validType:['length[2,20]','myPassword']"></div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label"><i>*</i>所属部门：</div>
        <div class="whgff-row-input">
            <input class="easyui-combotree" name="deptid" style="width:300px; height:32px" data-options="prompt:'请选择部门', value:'', required:true, loadFilter:myFilter, url:'${basePath}/admin/system/dept/srchList?state=1&delstate=0'">
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label">岗位：</div>
        <div class="whgff-row-input">
            <div class="checkbox checkbox-primary whg-js-data" name="roleids" value="" js-data="getRoleData"></div>
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label">权限分馆：</div>
        <div class="whgff-row-input">
            <div class="checkbox checkbox-primary whg-js-data" name="cultids" value="" js-data="getCultData"></div>
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label">所属分馆：</div>
        <div class="whgff-row-input">
            <div class="radio radio-primary whg-js-data" name="cultid" value="" js-data="getCultData"></div>
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label">手机号码：</div>
        <div class="whgff-row-input"><input class="easyui-textbox" name="contactnum" style="width:300px; height:32px" data-options="prompt:'请输入管理员手机号码', validType:'isPhone'"></div>
    </div>

</form>

<div id="whgwin-add-btn" class="whgff-but" style="width: 400px; margin:20px 0px 50px 350px">
    <a href="javascript:void(0)" class="easyui-linkbutton whgff-but-submit" iconCls="icon-save" id="whgwin-add-btn-save">保 存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton whgff-but-clear" iconCls="icon-undo" onclick="WhgComm.editDialogClose()">返  回</a>
</div>

<!-- script -->
<script type="text/javascript">
    /** 对部门数据过滤处理 */
    function myFilter(data){
        var rows = data.rows;
        var newRows = [];
        var lastNodeList = [];
        if(rows.length > 0){
            for(var i=0; i<rows.length; i++){
                var _id = rows[i].id;
                var _text = rows[i].name;
                var _node = {"id":_id, "text":_text};
                if(rows[i].pid){
                    while(lastNodeList.length > 0 && rows[i].pid != lastNodeList[lastNodeList.length-1]["id"]){
                        lastNodeList.splice(lastNodeList.length-1, 1);
                    }
                    if(lastNodeList.length > 0){
                        if(typeof lastNodeList[lastNodeList.length-1]["children"] == "undefined"){
                            lastNodeList[lastNodeList.length-1]["children"] = [];
                        }
                        lastNodeList[lastNodeList.length-1]["children"].push(_node);
                        lastNodeList.push(_node);
                    }else{
                        newRows.push(_node);
                        lastNodeList = [];
                        lastNodeList.push(_node);
                    }
                }else{
                    newRows.push(_node);
                    lastNodeList = [];
                    lastNodeList.push(_node);
                }
            }
        }
        return newRows;
    }

    /** 获角色数据 */
    function getRoleData(){
        var _data = [];
        $.ajax({
            url: '${basePath}/admin/system/role/srchList',
            cache: false,
            async: false,
            type: 'POST',
            data: {state: 1, delstate:0},
            success: function (data) {
                for(var i=0; i<data.length; i++){
                    _data.push( {"id":data[i].id, "text":data[i].name} );
                }
            }
        });
        return _data;
    }

    /** 获取权限分馆数据 */
    function getCultData() {
        var _data = [];
        $.ajax({
            url: '${basePath}/admin/system/cult/srchList',
            cache: false,
            async: false,
            type: 'POST',
            data: {state: 1, delstate:0},
            success: function (data) {
                _data.push( {"id":"TOP", "text":"总馆"} );
                for(var i=0; i<data.length; i++){
                    _data.push( {"id":data[i].id, "text":data[i].name} );
                }
            }
        });
        return _data;
    }

    /** 初始表单 */
    $(function () {
        $('#whgff').form({
            novalidate: true,
            url : getFullUrl('/admin/system/user/add'),
            onSubmit : function(param) {
                var _valid = $(this).form('enableValidation').form('validate');
                if(_valid){
                    var pwd = $("#password1").passwordbox('getValue');
                    param.password = $.md5(pwd); //加密
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
                    $.messager.alert('提示', '操作失败: '+Json.errormsg+'！', 'error');
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
