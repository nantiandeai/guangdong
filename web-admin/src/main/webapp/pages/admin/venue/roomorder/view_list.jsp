<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<% request.setAttribute("basePath", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath());%>
<% request.setAttribute("resourceid", request.getParameter("rsid")); %>
<% request.setAttribute("resourceopts", request.getParameter("opts")); %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <%@include file="/pages/comm/admin/header.jsp"%>
    <title>活动室时段管理</title>
    <script src="${basePath}/static/admin/js/whgmodule-gridopts.js"></script>
</head>
<body>
    <table id="whgdg" class="easyui-datagrid" title="预定管理-${roomtitle}" style="display: none;"
           toolbar="#tb" pagination=true pageSize=30 pageList="[10,20,30,50,100,200]" loadMsg="数据加载中，请稍候。。。"
           data-options="fit:true, striped:true, rownumbers:true, fitColumns:true, singleSelect:false, checkOnSelect:true, selectOnCheck:true,
           url:'${basePath}/admin/venue/roomorder/srchList4p?roomid=${roomid}'">
        <thead>
            <tr>
                <th data-options="width: 30, checkbox: true, field:'checkbox' ">全选</th>
                <th data-options="width:100, sortable:false, field:'orderid'">订单号</th>
                <th data-options="width:100, sortable:false, field:'roomid', formatter:function(){return '${roomtitle}';} ">活动室</th>
                <th data-options="width:100, sortable: true, field:'timeday', formatter:WhgComm.FMTDate ">日期</th>
                <th data-options="width:100, sortable: true, field:'timestart', formatter:WhgComm.FMTTime ">时段开始</th>
                <th data-options="width:100, sortable: true, field:'timeend', formatter:WhgComm.FMTTime ">时段结束</th>
                <th data-options="width:100, sortable: true, field:'ordercontact'">联系人</th>
                <th data-options="width:100, sortable: true, field:'ordercontactphone' ">联系电话</th>
                <th data-options="width:100, sortable: true, field:'state', formatter:orderState ">状态</th>
                <th data-options="width:200, field:'id', fixed:true, formatter:WhgComm.FMTOpt, optDivId:'whgdg-opt'">操作</th>
            </tr>
        </thead>
    </table>

    <div id="tb" style="display: none;">
        <div>
            <a class="easyui-linkbutton" iconCls="icon-back" onclick="WhgComm.editDialogClose()">返回</a>
        </div>

        <div style="padding-top: 5px">
            <select class="easyui-combobox" name="state" prompt="请选择状态" panelHeight="auto" limitToList="true"
                    data-options="width:150, value:'', valueField:'id', textField:'text',
                    data:[{id:'0',text:'申请预定'},
                    {id:'1',text:'取消申请'},
                    {id:'2',text:'审核拒绝'},
                    {id:'3',text:'预定成功'}]">
            </select>
            <input class= "easyui-datebox" name="startDay" data-options="width:120" prompt="开始日期"/>
            至 <input class= "easyui-datebox" name="endDay" data-options="width:120" prompt="结束日期"/>
            <input class= "easyui-textbox" name="ordercontactphone" data-options="width:120" prompt="联系电话"/>
            <a class="easyui-linkbutton" iconCls="icon-search" onclick="WhgComm.search('#whgdg', '#tb');">查 询</a>
        </div>
    </div>


    <!-- 操作按钮 -->
    <div id="whgdg-opt" style="display: none;">
        <a plain="true" method="whgListTool.view">查看</a>
        <a plain="true" validKey="state" validVal="0" method="whgListTool.yesOrder">审核通过</a>
        <a plain="true" validKey="state" validVal="0,3" method="whgListTool.noOrder">审核拒绝</a>
    </div>
    <!-- 操作按钮-END -->

    <!-- 编辑表单 -->
    <div id="whgwin-edit"></div>
    <!-- 编辑表单 END -->

<script>
    function orderState(v){
        switch (v){
            case 0 : return '申请预定';
            case 1 : return '取消申请';
            case 2 : return '审核拒绝';
            case 3 : return '预定成功';
            default: return v;
        }
    }

    var whgListTool = new Gridopts();

    Gridopts.prototype.yesOrder = function(idx){
        var row = this.__getGridRow(idx);
        var mmx = this;
        $.messager.confirm("确认信息", "确定通过选中的项的申请吗？", function(r){
            if (r){
                $.messager.progress();
                var params = {id: row.id, formstates: '0', tostate: 3};
                $.ajax({
                    url: mmx.modeUrl+'/updstate',
                    data: params,
                    type: 'post',
                    dataType: 'json',
                    success: function(data){ mmx.__ajaxSuccess(data) },
                    error: function(xhr, ts, e){ mmx.__ajaxError(xhr, ts, e) }
                });
            }
        })
    };

    Gridopts.prototype.noOrder = function(idx){
        var row = this.__getGridRow(idx);
        var mmx = this;

        var addDialog =$('<div></div>').dialog({
            title: '审核拒绝预订申请',
            width: 500,
            height: 300,
            cache: false,
            modal: true,
            content: '<div class="dialog-content-add" style="padding: 30px 30px;"></div>',
            buttons: [{
                text:'确认',
                iconCls: 'icon-ok',
                handler:function(){
                    $.messager.progress();
                    var _textbox = addDialog.find(".easyui-textbox");
                    if (!_textbox.textbox('isValid')) {
                        $.messager.progress('close');
                        return;
                    }

                    var ordersummary = _textbox.textbox('getValue');
                    var params = {id: row.id, formstates: '0,3', tostate: 2, ordersummary: ordersummary};
                    $.ajax({
                        url: mmx.modeUrl+'/updstate',
                        data: params,
                        type: 'post',
                        dataType: 'json',
                        success: function(data){
                            mmx.__ajaxSuccess(data);
                            addDialog.dialog('close');
                        },
                        error: function(xhr, ts, e){ mmx.__ajaxError(xhr, ts, e) }
                    });
                }
            },{
                text:'关闭',
                iconCls: 'icon-no',
                handler:function(){ addDialog.dialog('close')}
            }]
        });

        addDialog.find('.dialog-content-add').append('\
        拒绝原因：<input class= "easyui-textbox" validType="length[0,100]"\
        data-options="width:350,height:120,multiline:true,required:false,\
        value:\'非常遗憾，我们不能通过您的预订申请\'"/>\
        ');

        $.parser.parse(addDialog);
    };



</script>
</body>
</html>
