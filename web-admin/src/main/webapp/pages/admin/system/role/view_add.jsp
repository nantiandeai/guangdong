<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<% request.setAttribute("basePath", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath());%>
<% request.setAttribute("resourceid", request.getParameter("rsid")); %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>岗位管理-添加岗位</title>
    <%@include file="/pages/comm/admin/header.jsp"%>
    <link rel="stylesheet" href="${basePath}/static/admin/css/bootstrap.css"/>
    <link rel="stylesheet" href="${basePath}/static/admin/Font-Awesome/css/font-awesome.min.css"/>
    <link rel="stylesheet" href="${basePath}/static/admin/css/build.css"/>
</head>
<body>


<form id="whgff" class="whgff" method="post">

    <h2>岗位管理-添加岗位</h2>

    <div class="whgff-row">
        <div class="whgff-row-label"><i>*</i>名称：</div>
        <div class="whgff-row-input"><input class="easyui-textbox" name="name" style="width:300px; height:32px" data-options="prompt:'请输入角色名称', required:true, validType:'length[3,30]'"></div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label"><i>*</i>权限：</div>
        <div class="whgff-row-input">
            <a href="javascript:void(0)" class="easyui-linkbutton" style="width:100px;height:32px;" id="chioceAll" >全&nbsp;&nbsp;选</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" style="width:100px;height:32px;" id="unchioceAll">取&nbsp;&nbsp;消</a>
            <table id="tdg_menu" style="width:80%"></table>
        </div>
    </div>

</form>

<div id="whgwin-add-btn" class="whgff-but" style="width: 400px; margin:20px 0px 50px 350px">
    <a href="javascript:void(0)" class="easyui-linkbutton whgff-but-submit" iconCls="icon-save" id="whgwin-add-btn-save">保 存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton whgff-but-clear" iconCls="icon-undo" onclick="WhgComm.editDialogClose()">返  回</a>
</div>

<!-- script -->
<script type="text/javascript">
    var optMap = eval('(${optMap})');

    /** 格式化权限 操作 */
    function pmsFMT(val, data, idx){
        var html = "";
        if(val){
            var pmsArr = val.split(',');
            if(pmsArr.length > 0){
                html += '<label><input type="checkbox" name="pmsAll" value="0" id="pmsAll-'+data.id+'" onchange="chiocePMS(\''+data.id+'\');" />全选</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
                for(var i=0; i<pmsArr.length; i++){
                    var pms = pmsArr[i];
                    var pmsid = $.trim(pms);
                    var pmstext = optMap[pmsid];

                    html += '<label><input type="checkbox" rcid="'+data.id+'" name="pms" id="'+data.id+'-'+pmsid+'" value="'+data.id+':'+pmsid+'" />'+pmstext+'</label>&nbsp;&nbsp;';
                }
            }

        }
        return html;
    }

    /** 单个资源全选 */
    function chiocePMS(rcid){
        if( $('#pmsAll-'+rcid).prop("checked") ){
            $('#pmsAll-'+rcid).parents('div').find("input[type='checkbox'][id^='"+rcid+"']").prop("checked", true);
        }else{
            $('#pmsAll-'+rcid).parents('div').find("input[type='checkbox'][id^='"+rcid+"']").prop("checked", false);
        }
    }

    /** 所有资源全选与取消 */
    function chioceAll(checked){
        if(checked){
            $("input[type='checkbox'][name='pms']").prop('checked', true);
            $("input[type='checkbox'][name='pmsAll']").prop('checked', true);
        }else{
            $("input[type='checkbox'][name='pms']").prop('checked', false);
            $("input[type='checkbox'][name='pmsAll']").prop('checked', false);
        }
    }

    function pmsOnChange(){
        $("input[type='checkbox'][name='pms']").change(function(){
            if( $(this).prop('checked') ){
                $(this).parent('label').css('color', 'red');
            }else{
                $(this).parent('label').css('color', '');
            }
        });
    }

    $(function () {
        //初始权限编辑表格
        $('#tdg_menu').treegrid({
            url: '${basePath}/admin/system/role/srchMenuTree',
            idField: 'id',
            treeField: 'text',
            fitColumns: true,
            singleSelect: true,
            columns:[[
                {field:'text', title:'权限资源', width:100},
                {field:'opts', title:'支持操作', width:300, formatter: pmsFMT}
            ]],
            onLoadSuccess: function (row, data) {
                //全选与取消
                $('#chioceAll').show().off('click.wxl').on('click.wxl', function(){
                    chioceAll(true);
                });
                $('#unchioceAll').show().off('click.wxl').on('click.wxl', function(){
                    chioceAll(false);
                });
            }
        });


        //表单
        $('#whgff').form({
            novalidate: true,
            url : getFullUrl('/admin/system/role/add'),
            onSubmit : function(param) {
                //alert('onSubmit');
                var _valid = $(this).form('enableValidation').form('validate')
                if(_valid){
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
                    $.messager.alert('提示', '操作失败:'+Json.errormsg+'！', 'error');
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
