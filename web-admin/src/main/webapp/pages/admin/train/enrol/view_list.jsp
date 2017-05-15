<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<% request.setAttribute("basePath", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath());%>
<% request.setAttribute("resourceid", request.getParameter("rsid")); %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">

    <%@include file="/pages/comm/admin/header.jsp"%>
</head>
<body>
<!-- 表格 -->
<div id="tt" class="easyui-tabs" style="width:500px;height:250px;" fit="true">
    <div title="${isbasicclass == 0 ? "面试通知列表":"录取列表"}"  style="overflow:auto;padding:5px;display:none;">
        <table id="whgdg" title="${pageTitle}" class="easyui-datagrid" style="display: none"
               data-options="fit:true, striped:true, rownumbers:true, fitColumns:true, singleSelect:false, checkOnSelect:true, selectOnCheck:true, pagination:true, toolbar:'#whgdg-tb', url:'${basePath}/admin/train/enrol/srchList4p?traid=${id}&type=${isbasicclass}&tab=0'">
            <thead>
            <tr>
                <th data-options="width: 30, checkbox: true, field:'checkbox' ">全选</th>
                <th data-options="field:'orderid', width:160">订单号</th>
                <th data-options="field:'realname', width:160">报名人</th>
                <th data-options="field:'cardno', width:160">身份证号码</th>
                <th data-options="field:'contactphone', width:160">手机号码</th>
                <th data-options="field:'birthday', width:160, formatter:WhgComm.FMTDate">出生日期</th>
                <th data-options="field:'crttime', width:160, formatter:WhgComm.FMTDateTime">报名时间</th>
                <th data-options="field:'state', width:160, formatter:WhgComm.FMTBmState" >状态</th>
                <th data-options="field:'_opt', width:160, formatter:WhgComm.FMTOpt, optDivId:'whgdg-opt'">操作</th>
            </tr>
            </thead>
        </table>
    </div>
    <c:if test="${isbasicclass == 0}">
        <div title="录取列表" style="overflow:auto;padding:5px;display:none;" >
            <table id="whgdg1" title="${pageTitle}" class="easyui-datagrid" style="display: none"
                   data-options="fit:true, striped:true, rownumbers:true, fitColumns:true, singleSelect:false, checkOnSelect:true, selectOnCheck:true, pagination:true, toolbar:'#whgdg-tb1', url:'${basePath}/admin/train/enrol/srchList4p?traid=${id}&type=${isbasicclass}&tab=1'">
                <thead>
                <tr>
                    <th data-options="width: 30, checkbox: true, field:'checkbox' ">全选</th>
                    <th data-options="field:'orderid', width:160">订单号</th>
                    <th data-options="field:'realname', width:160">报名人</th>
                    <th data-options="field:'cardno', width:160">身份证号码</th>
                    <th data-options="field:'contactphone', width:160">手机号码</th>
                    <th data-options="field:'birthday', width:160, formatter:WhgComm.FMTDate">出生日期</th>
                    <th data-options="field:'crttime', width:160, formatter:WhgComm.FMTDateTime">报名时间</th>
                    <th data-options="field:'state', width:160, formatter:WhgComm.FMTBmState" >状态</th>
                    <th data-options="field:'_opt', width:160, formatter:WhgComm.FMTOpt, optDivId:'whgdg-opt1'">操作</th>
                </tr>
                </thead>
            </table>
        </div>
    </c:if>
</div>

<!-- 表格 END -->

<!-- 表格操作工具栏 -->
<div id="whgdg-tb" style="display: none;">
    <div class="whgd-gtb-btn">
    <h2>总报名数：${count} 人&nbsp; &nbsp; &nbsp; &nbsp; 有效报名数： ${goodCount}人&nbsp; &nbsp; &nbsp; &nbsp;未处理的报名： ${unCheckCount} 人</h2>
    </div>
    <div class="whgdg-tb-srch">
        <input class="easyui-textbox" style="width: 200px;" name="contactphone" data-options="prompt:'请输入手机号码', validType:'length[1,32]'" />
        <select class="easyui-combobox" name="state" prompt="请选择状态" panelHeight="auto" limitToList="true"
                data-options="width:120, value:'', valueField:'id', textField:'text', data:WhgComm.getBmState()">
        </select>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" onclick="WhgComm.editDialogClose();">返 回</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="WhgComm.search('#whgdg', '#whgdg-tb');">查 询</a>
        <a href="javascript:void(0)" class="easyui-linkbutton checkon" iconCls="icon-add" onclick="allcheckon();">批量审核</a>
        <%--<a href="${basePath}/admin/train/enrol/exportExcel" class="easyui-linkbutton checkon" iconCls="icon-add">导出</a>--%>
        <c:if test="${isbasicclass == 1}">
            <a href="javascript:void(0)" class="easyui-linkbutton " iconCls="icon-add" onclick="ramEnroll();">随机录取</a>
        </c:if>
    </div>
</div>
<!-- 表格操作工具栏-END -->
<!-- 表格操作工具栏 -->
<div id="whgdg-tb1" style="display: none;">
    <div class="whgd-gtb-btn">
    <h2>面试总人数：${viewCount} 人&nbsp; &nbsp; &nbsp; &nbsp;成功录取人数：${passCount} 人</h2>

    </div>
    <div class="whgdg-tb-srch">
        <input class="easyui-textbox" style="width: 200px;" name="contactphone" data-options="prompt:'请输入手机号码', validType:'length[1,32]'" />
        <select class="easyui-combobox" name="state" prompt="请选择状态" panelHeight="auto" limitToList="true"
                data-options="width:120, value:'', valueField:'id', textField:'text', data:WhgComm.getBmState()">
        </select>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" onclick="WhgComm.editDialogClose();">返 回</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="WhgComm.search('#whgdg1', '#whgdg-tb1');">查 询</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="allpublishon();">批量面试</a>
    </div>
</div>
<!-- 表格操作工具栏-END -->

<!-- 操作按钮 -->
<div id="whgdg-opt" style="display: none;">
    <a href="javascript:void(0)" class="easyui-linkbutton" plain="true" validKey="state" validVal="1" method="checkon">审核</a>
    <c:if test="${isbasicclass == 2}">
        <a href="javascript:void(0)" class="easyui-linkbutton" plain="true" validFun="_valid" method="cancel">取消</a>
    </c:if>
</div>
<!-- 操作按钮-END -->

<!-- 操作按钮 -->
<div id="whgdg-opt1" style="display: none;">
    <a href="javascript:void(0)" class="easyui-linkbutton" plain="true" validKey="state" validVal="4" method="publishon">面试</a>
</div>
<!-- 操作按钮-END -->

<!-- 添加表单 -->
<div id="whgwin-add" style="display: none">
    <form id="whgff" class="whgff" method="post">
        <div class="whgff-row">
            <div class="whgff-row-label _check" style="width: 30%"><i>*</i>审核是否通过：</div>
            <div class="whgff-row-input" style="width: 70%">
                <select class="easyui-combobox _state" id="_state" name="_state" style="height: 35px; width: 90%" data-options="editable:false,required:true">
                    <option value="0">否</option>
                    <option value="1">是</option>
                </select>
            </div>
        </div>
        <div class="whgff-row">
            <div class="whgff-row-label" style="width: 30%"><i></i>操作原因：</div>
            <div class="whgff-row-input" style="width: 70%"><input class="easyui-textbox" name="statedesc" style="width:90%; height:35px"></div>
        </div>
        <div class="whgff-row date">
            <div class="whgff-row-label" style="width: 30%"><label style="color: red">*</label>面试时间：</div>
            <div class="whgff-row-input" style="width: 70%">
                <input class="easyui-datetimebox viewtime" name="viewtime" style="width:90%; height:35px"/>
            </div>
        </div>
        <div class="whgff-row address">
            <div class="whgff-row-label" style="width: 30%"><label style="color: red">*</label>面试地点：</div>
            <div class="whgff-row-input" style="width: 70%">
                <input class="easyui-textbox viewaddress" name="viewaddress" style="width:90%; height:35px"/>
            </div>
        </div>
    </form>
</div>
<div id="whgwin-add-btn" style="text-align: center; display: none">
    <div style="display: inline-block; margin: 0 auto">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" id="btn" >确 定</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#whgwin-add').dialog('close')">关 闭</a>
    </div>
</div>
<!-- 添加表单 END -->

<script>


    /**显示取消功能*/
    function _valid(idx){
        var isbasic = ${isbasicclass};
        //alert(isbasic);
        var row =  $("#whgdg").datagrid("getRows")[idx];
        return row.state == '6' && isbasic == '2';
    }

    /**取消*/
    function cancel(idx){
        var row = $("#whgdg").datagrid("getRows")[idx];
        $.messager.confirm("确认信息", "确定要取消选中的项吗？", function(r){
            if (r){
                $.messager.progress();
                var params = {ids: row.id, fromstate: 6, tostate: 2};
                $.post('${basePath}/admin/train/enrol/updstate', params, function(data){
                    $("#whgdg").datagrid('reload');
                    if (!data.success || data.success != "1"){
                        $.messager.alert("错误", data.errormsg||'操作失败', 'error');
                    }
                    $.messager.progress('close');
                }, 'json');
            }
        })
    }
    /**
     * 随机录取
     * */
    function ramEnroll(idx) {
        var rows = $("#whgdg").datagrid("getRows");
        var ids = _split = "";//id1,id2,id3
        for (var i = 0; i<rows.length; i++){
            ids += _split+rows[i].id;
            _split = ",";
        }
        $.messager.confirm("确认信息", "确定要进行随机录取吗？", function(r){
            if (r){
                $.messager.progress();
                var params = {ids: ids, fromstate: 1, tostate: 6};
                $.post('${basePath}/admin/train/enrol/ramEnroll', params, function(data){
                    $("#whgdg").datagrid('reload');
                    if (!data.success || data.success != "1"){
                        $.messager.alert("错误", data.errormsg||'操作失败', 'error');
                    }
                    $.messager.progress('close');
                }, 'json');
            }
        })
    }
    /**
     * 通过面试
     * @param idx
     */
    function publishon(idx){
        $(".date").css("display","none");
        $(".address").css("display","none");
        $(".viewtime").datetimebox({required:false});
        $(".viewaddress").textbox({required:false});
        $('#whgwin-add').dialog({
            title: '录取通知',
            cache: false,
            modal: true,
            width: '600',
            height: '240',
            maximizable: true,
            resizable: true,
            buttons: '#whgwin-add-btn',
            onOpen: function () {
                _change();
                $(".easyui-combobox").combobox('clear');
                $("._check").text("是否录取");
                var row = $("#whgdg1").datagrid("getRows")[idx];
                var formstate;
                var tostate;
                $('#whgff').form({
                    url : '${basePath}/admin/train/enrol/updstate',
                    onSubmit : function(param) {
                        var state = $("._state").combobox("getValue");
                        if(state == 0){
                            formstate = 4;
                            tostate = 5;
                        }
                        if(state == 1){
                            formstate = 4;
                            tostate = 6;
                        }
                        param.ids = row.id;
                        param.fromstate = formstate;
                        param.tostate = tostate;
                        var isValid = $(this).form('enableValidation').form('validate');
                        if(isValid){
                            $.messager.progress();
                        }else{
                            $("#btn").off("click").one("click",function () { $('#whgff').submit(); });
                        }
                        return isValid;
                    },
                    success : function(data) {
                        $.messager.progress('close');
                        var Json = $.parseJSON(data);
                        if(Json.success == "1"){
                            $('#whgdg').datagrid('reload');
                            $('#whgdg1').datagrid('reload');

                            $('#whgwin-add').dialog('close');
                        }else{
                            $.messager.alert("提示", Json.errormsg);
                            $("#btn").off("click").one("click",function () { $('#whgff').submit(); });
                        }

                    }
                });
                $('#whgff').form("clear");
                $("#btn").off("click").one("click",function () { $('#whgff').submit(); });
            }
        });
    }


    /**
     * 通过审核发送面试通知
     * @param idx
     */
    function checkon(idx){
        $(".date").css("display","none");
        $(".address").css("display","none");

        var isbasicclass = ${isbasicclass};
        $('#whgwin-add').dialog({
            title: '报名审核',
            cache: false,
            modal: true,
            width: '600',
            height: '330',
            maximizable: true,
            resizable: true,
            buttons: '#whgwin-add-btn',
            onOpen: function () {
                if(isbasicclass == 0){
                    $("#_state").combobox({
                        onChange:function(){
                            var state = $("._state").combobox("getValue");
                            if(state == 0){
                                $(".date").css("display","none");
                                $(".address").css("display","none");
                                $(".viewtime").datetimebox({required:false});
                                $(".viewaddress").textbox({required:false});
                            }
                            if(state == 1){
                                $(".date").css("display","");
                                $(".address").css("display","");
                                $(".viewtime").datetimebox({required:true});
                                $(".viewaddress").textbox({required:true});
                            }
                        }
                    });
                }

                $(".easyui-combobox").combobox('clear');
                var row = $("#whgdg").datagrid("getRows")[idx];
                var formstate;
                var tostate;
                $('#whgff').form({
                    url : '${basePath}/admin/train/enrol/updstate',
                    onSubmit : function(param) {
                        var state = $("._state").combobox("getValue");

                        if(state == 0){
                            if(isbasicclass == 1){
                                formstate = 1;
                                tostate = 3;
                            }else{
                                formstate = 1;
                                tostate = 3;
                            }
                        }
                        if(state == 1){
                            if(isbasicclass == 1){
                                formstate = 1;
                                tostate = 6;
                            }else{
                                formstate = 1;
                                tostate = 4;
                            }
                        }

                        param.ids = row.id;
                        param.fromstate = formstate;
                        param.tostate = tostate;
                        var isValid = $(this).form('enableValidation').form('validate');
                        if(isValid){
                            $.messager.progress();
                        }else{
                            $("#btn").off("click").one("click",function () { $('#whgff').submit(); });
                        }
                        return isValid;
                    },
                    success : function(data) {
                        $.messager.progress('close');
                        var Json = $.parseJSON(data);
                        if(Json.success == "1"){
                            $('#whgdg').datagrid('reload');
                            $('#whgdg1').datagrid('reload');
                            $('#whgwin-add').dialog('close');
                        }else{
                            $.messager.alert("提示", Json.errormsg);
                            $('#whgwin-add').dialog('close');
                            $("#btn").off("click").one("click",function () { $('#whgff').submit(); });
                        }

                    }
                });
                $('#whgff').form("clear");
                $("#btn").off("click").one("click",function () { $('#whgff').submit(); });
            }
        });

    }


    /**
     * 批量通过审核发送面试通知
     * @param idx
     */
    function allcheckon(idx){
        $(".date").css("display","none");
        $(".address").css("display","none");
        var isbasicclass = ${isbasicclass};
        var rows = $("#whgdg").datagrid("getSelections");

        if(rows == ""){
            $.messager.alert("提示", "请选择要操作的数据！");
            return ;
        }
        $('#whgwin-add').dialog({
            title: '报名审核',
            cache: false,
            modal: true,
            width: '600',
            height: '330',
            maximizable: true,
            resizable: true,
            buttons: '#whgwin-add-btn',
            onOpen: function () {
                if(isbasicclass == 0){
                    $("#_state").combobox({
                        onChange:function(){
                            var state = $("._state").combobox("getValue");
                            if(state == 0){
                                $(".date").css("display","none");
                                $(".address").css("display","none");
                                $(".viewtime").datetimebox({required:false});
                                $(".viewaddress").textbox({required:false});
                            }
                            if(state == 1){
                                $(".date").css("display","");
                                $(".address").css("display","");
                                $(".viewtime").datetimebox({required:true});
                                $(".viewaddress").textbox({required:true});
                            }
                        }
                    });
                }

                var state = $("._state").combobox("getValue");

                var ids = _split = "";//id1,id2,id3
                for (var i = 0; i<rows.length; i++){
                    ids += _split+rows[i].id;
                    _split = ",";
                }
                var formstate;
                var tostate;
                $('#whgff').form({
                    url : '${basePath}/admin/train/enrol/updstate',
                    onSubmit : function(param) {
                        var state = $("._state").combobox("getValue");
                        if(state == 0){
                            if(isbasicclass == 1){
                                formstate = 1;
                                tostate = 3;
                            }else{
                                formstate = 1;
                                tostate = 3;
                            }
                        }
                        if(state == 1){
                            if(isbasicclass == 1){
                                formstate = 1;
                                tostate = 6;
                            }else{
                                formstate = 1;
                                tostate = 4;
                            }

                        }
                        param.ids = ids;
                        param.fromstate = formstate;
                        param.tostate = tostate;
                        var isValid = $(this).form('enableValidation').form('validate');
                        if(isValid){
                            $.messager.progress();
                        }else{
                            $('#whgwin-add').dialog('close');
                            $("#btn").off("click").one("click",function () { $('#whgff').submit(); });
                        }
                        return isValid
                    },
                    success : function(data) {
                        $.messager.progress('close');
                        var Json = $.parseJSON(data);
                        if(Json.success == "1"){
                            $('#whgdg').datagrid('reload');
                            $('#whgdg1').datagrid('reload');
                            $('#whgwin-add').dialog('close');
                        }else{
                            $.messager.alert("提示", Json.errormsg);
                            $('#whgwin-add').dialog('close');
                            $("#btn").off("click").one("click",function () { $('#whgff').submit(); });
                        }

                    }
                });
                $('#whgff').form("clear");
                $("#btn").off("click").one("click",function () { $('#whgff').submit(); });
            }
        });
    }


    /**
     * 批量通过面试
     * @param idx
     */
    function allpublishon(idx){
        $(".date").css("display","none");
        $(".address").css("display","none");
        $(".viewtime").datetimebox({required:false});
        $(".viewaddress").textbox({required:false});
        var rows = $("#whgdg1").datagrid("getSelections");
        if(rows == ""){
            $.messager.alert("提示", "请选择要操作的数据！");
            return;
        }
        $('#whgwin-add').dialog({
            title: '录取通知',
            cache: false,
            modal: true,
            width: '600',
            height: '240',
            maximizable: true,
            resizable: true,
            buttons: '#whgwin-add-btn',
            onOpen: function () {
                _change();
                $("._check").text("是否录取");

                var ids = _split = "";
                for (var i = 0; i<rows.length; i++){
                    ids += _split+rows[i].id;
                    _split = ",";
                }
                var formstate;
                var tostate;
                $('#whgff').form({
                    url : '${basePath}/admin/train/enrol/updstate',
                    onSubmit : function(param) {
                        var state = $("._state").combobox("getValue");
                        if(state == 0){
                            formstate = 4;
                            tostate = 5;
                        }
                        if(state == 1){
                            formstate = 4;
                            tostate = 6;
                        }
                        param.ids = ids;
                        param.fromstate = formstate;
                        param.tostate = tostate;
                        var isValid = $(this).form('enableValidation').form('validate');
                        if(isValid){
                            $.messager.progress();
                        }else{
                            $("#btn").off("click").one("click",function () { $('#whgff').submit(); });
                        }
                        return isValid
                    },
                    success : function(data) {
                        $.messager.progress('close');
                        var Json = $.parseJSON(data);
                        if(Json.success == "1"){
                            $('#whgdg').datagrid('reload');
                            $('#whgdg1').datagrid('reload');
                            $('#whgwin-add').dialog('close');
                        }else{
                            $.messager.alert("提示", Json.errormsg);
                            $('#whgwin-add').dialog('close');
                            $("#btn").off("click").one("click",function () { $('#whgff').submit(); });
                        }

                    }
                });

                $('#whgff').form("clear");
                $("#btn").off("click").one("click",function () { $('#whgff').submit(); });
            }
        });
    }


    /**
     * 如果即报即得隐藏批量按钮
     */
    $(function () {
        var type = ${isbasicclass};
        if(type == 2){
            $(".checkon").css("display","none");
            $(".checkoff").css("display","none");
        }
    })

    function _change(){
        $("#_state").combobox({
            onChange:function(){
                $(".date").css("display","none");
                $(".address").css("display","none");
                $(".viewtime").datetimebox({required:false});
                $(".viewaddress").textbox({required:false});
            }
        });
    }

</script>
</body>
</html>
