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
    <table id="whgdg" class="easyui-datagrid" title="预定时段管理-${roomtitle}" style="display: none;"
           toolbar="#tb" pagination=true pageSize=30 pageList="[10,20,30,50,100,200]" loadMsg="数据加载中，请稍候。。。"
           data-options="fit:true, striped:true, rownumbers:true, fitColumns:true, singleSelect:false, checkOnSelect:true, selectOnCheck:true,
           url:'${basePath}/admin/venue/roomtime/srchList4p?roomid=${roomid}'">
        <thead>
            <tr>
                <th data-options="width: 30, checkbox: true, field:'checkbox' ">全选</th>
                <th data-options="width:100, sortable:false, field:'roomid', formatter:function(){return '${roomtitle}';} ">活动室</th>
                <th data-options="width:100, sortable: true, field:'timeday', formatter:WhgComm.FMTDate ">日期</th>
                <th data-options="width:100, sortable: true, field:'timestart', formatter:WhgComm.FMTTime ">时段开始</th>
                <th data-options="width:100, sortable: true, field:'timeend', formatter:WhgComm.FMTTime ">时段结束</th>
                <th data-options="width:100, sortable: true, field:'state', formatter:timeState ">状态</th>
                <th data-options="width:200, field:'id', fixed:true, formatter:WhgComm.FMTOpt, optDivId:'whgdg-opt'">操作</th>
            </tr>
        </thead>
    </table>

    <div id="tb" style="display: none;">
        <div>
            <a class="easyui-linkbutton" iconCls="icon-back" onclick="WhgComm.editDialogClose()">返回</a>
            <a class="easyui-linkbutton" iconCls="icon-add" onclick="whgListTool.add()">添加预定时段</a>
            <a class="easyui-linkbutton" iconCls="icon-edit" onclick="whgListTool.rowsOpen()">批量开放预定</a>
            <a class="easyui-linkbutton" iconCls="icon-edit" onclick="whgListTool.rowsClose()">批量取消预定</a>
        </div>

        <div style="padding-top: 5px">
            <select class="easyui-combobox" name="state" prompt="请选择状态" panelHeight="auto" limitToList="true"
                    data-options="width:150, value:'', valueField:'id', textField:'text', data:[{id:'1',text:'开放预定'},{id:'0',text:'不开放预定'}]">
            </select>
            <input class= "easyui-datebox" name="startDay" data-options="width:120"/>
            至 <input class= "easyui-datebox" name="endDay" data-options="width:120"/>
            <a class="easyui-linkbutton" iconCls="icon-search" onclick="WhgComm.search('#whgdg', '#tb');">查 询</a>
        </div>
    </div>


    <!-- 操作按钮 -->
    <div id="whgdg-opt" style="display: none;">
        <a plain="true" validKey="state" validVal="0" method="whgListTool.edit">编辑</a>
        <a plain="true" validKey="state" validVal="0" method="whgListTool.rowOpen">开放预定</a>
        <a plain="true" validKey="state" validVal="1" method="whgListTool.rowClose">取消预定</a>
    </div>
    <!-- 操作按钮-END -->

    <!-- 编辑表单 -->
    <div id="whgwin-edit"></div>
    <!-- 编辑表单 END -->

<script>
    function timeState(v){
        return v==1? '开放预定' : '不开放预定';
    }

    var whgListTool = new Gridopts();

    function myParseDay(daystr, timestr){
        var _day = new Date();

        var dstrs = /(\d{4})-(\d{1,2})-(\d{1,2})/.exec(daystr);
        if (dstrs){
            _day.setFullYear( dstrs[1] );
            _day.setMonth(parseInt(dstrs[2])-1);
            _day.setDate( dstrs[3] );
        }
        if (timestr){
            var timestrs = /(\d{2}):(\d{2})/.exec(timestr);
            if (timestrs){
                _day.setHours(timestrs[1]);
                _day.setMinutes(timestrs[2]);
                _day.setSeconds(0);
            }
        }else{
            _day.setHours(0);
            _day.setMinutes(0);
            _day.setSeconds(0);
        }
        return _day;
    }

    $.extend($.fn.validatebox.defaults.rules, {
        afterDate: {
            validator: function(value, param){
                var preValue = $(param[0]).datebox('getValue');
                if (!preValue) return true;

                var currDate = myParseDay(value);//new Date( Date.parse(value) );
                var preDate = myParseDay(preValue);//new Date( Date.parse(preValue) );
                var lastDate = myParseDay(preValue);//new Date( Date.parse(preValue) );
                lastDate.setDate(lastDate.getDate()+30);
                return currDate >= preDate && currDate <= lastDate;
            },
            message: '请选择前一个日期之后30天内的日期'
        },
        startDate: {
            validator: function(value, param){
                //return Date.parse(value) >= Date.parse(param[0]);
                var vd = myParseDay(value);
                var pd = myParseDay(param[0]);
                return vd >= pd;
            },
            message: '日期不能小于{0}'
        },

        afterTime: {
            validator: function(value, param){
                var preValue = $(param[0]).timespinner('getValue');
                if (!preValue) return true;

                function toMMs(v){
                    var tms = /^(\d{2}):(\d{2})/.exec(v);
                    if (!tms) return 0;
                    var hh = String(tms[1]);
                    var mm = String(tms[2]);
                    return Number(hh) * 60 + Number(mm);
                }
                var pretm = toMMs(preValue);
                var tm = toMMs(value);
                return tm > pretm;
            },
            message: '结束时间需大于开始时间'
        }
    });

    /**
     * 单行操作开启
     * @param idx
     */
    Gridopts.prototype.rowOpen = function(idx){
        var row = this.__getGridRow(idx);
        var mmx = this;
        $.messager.confirm("确认信息", "确定开放预定选中的项吗？", function(r){
            if (r){
                mmx.__updateStateSend(row.id, "0", 1);
            }
        })
    };
    /**
     * 单行操作关闭
     * @param idx
     */
    Gridopts.prototype.rowClose = function(idx){
        var row = this.__getGridRow(idx);
        var mmx = this;
        $.messager.confirm("确认信息", "确定取消预定选中的项吗？", function(r){
            if (r){
                mmx.__updateStateSend(row.id, "1", 0);
            }
        })
    };

    /**
     * 批量开启
     */
    Gridopts.prototype.rowsOpen = function(){
        var rowids = this.__getGridSelectIds();
        if (rowids.length<1){
            $.messager.alert("提示信息", "请选择要操作的选项", 'info');
            return;
        }
        var mmx = this;
        $.messager.confirm("确认信息", "确定开放预定选中的项吗？", function(r){
            if (r){
                mmx.__updateStateSend(rowids, "0", 1);
            }
        })
    };

    /**
     * 批量关闭
     * @param idx
     */
    Gridopts.prototype.rowsClose = function(idx){
        var rowids = this.__getGridSelectIds();
        if (rowids.length<1){
            $.messager.alert("提示信息", "请选择要操作的选项", 'info');
            return;
        }
        var mmx = this;
        $.messager.confirm("确认信息", "确定取消预定选中的项吗？", function(r){
            if (r){
                mmx.__updateStateSend(rowids, "1", 0);
            }
        })
    };

    /**
     * 添加预订时段记录
     */
    Gridopts.prototype.add = function(){
        var roomid = '${roomid}';
        var mmx = this;
        var addDialog =$('<div></div>').dialog({
            title: '添加预定时段',
            width: 400,
            height: 200,
            cache: false,
            modal: true,
            content: '<div class="dialog-content-add" style="padding: 30px 30px;"></div>',
            buttons: [{
                text:'确认',
                iconCls: 'icon-ok',
                handler:function(){
                    $.messager.progress();
                    var valid = true;
                    addDialog.find(".easyui-datebox").each(function () {
                        valid = $(this).datebox('isValid');
                        return valid;
                    });

                    if (!valid) {
                        $.messager.progress('close');
                        return;
                    }
                    var sday = addDialog.find(".easyui-datebox:eq(0)").datebox('getValue');
                    var eday = addDialog.find(".easyui-datebox:eq(1)").datebox('getValue');
                    $.ajax({
                        url: mmx.modeUrl+'/add',
                        data: {roomid: roomid, startDay: sday, endDay: eday},
                        type: 'post',
                        dataType: 'json',
                        success: function(data){
                            mmx.__ajaxSuccess(data);
                            addDialog.dialog('close');
                            addDialog.remove()
                        },
                        error: function(xhr, ts, e){ mmx.__ajaxError(xhr, ts, e) }
                    });
                }
            },{
                text:'关闭',
                iconCls: 'icon-no',
                handler:function(){ addDialog.dialog('close'); addDialog.remove()}
            }]
        });

        addDialog.find('.dialog-content-add').empty();
        addDialog.find('.dialog-content-add').append('\
        从：<input class= "easyui-datebox valid-statrDay" data-options="width:120,required:true"/>\
        至 <input class= "easyui-datebox" validType="afterDate[\'.valid-statrDay\']" data-options="width:120,required:true"/>\
        ');

        $.messager.progress();
        $.getJSON('${basePath}/admin/venue/roomtime/ajaxAddStartDay?t='+(new Date()).getTime(), {roomid: roomid}, function(data){
            $.messager.progress('close');
            var sday = new Date(data);
            var eday = new Date(data);
            eday.setDate(sday.getDate()+6);
            addDialog.find(".easyui-datebox:eq(0)").attr('validType', "startDate['"+WhgComm.FMTDate(sday)+"']");
            $.parser.parse(addDialog);
            addDialog.find(".easyui-datebox:eq(0)").datebox('setValue', WhgComm.FMTDate(sday));
            addDialog.find(".easyui-datebox:eq(1)").datebox('setValue', WhgComm.FMTDate(eday));
        });
    };

    /**
     * 编辑预定时段记录
     * @param idx
     */
    Gridopts.prototype.edit = function(idx){
        var row = this.__getGridRow(idx);
        var roomid = '${roomid}';
        var id = row.id;
        var timeDay = WhgComm.FMTDate(row.timeday);

        var mmx = this;
        var optDialog =$('<div></div>').dialog({
            title: '编辑预定时段:'+timeDay,
            width: 400,
            height: 200,
            cache: false,
            modal: true,
            content: '<div class="dialog-content-add" style="padding: 30px 30px;"></div>',
            buttons: [{
                text:'确认',
                iconCls: 'icon-ok',
                handler:function(){
                    $.messager.progress();
                    var valid = true;
                    optDialog.find(".easyui-timespinner").each(function () {
                        valid = $(this).timespinner('isValid');
                        return valid;
                    });

                    if (!valid) {
                        $.messager.progress('close');
                        return;
                    }
                    var timeStart = optDialog.find(".easyui-timespinner:eq(0)").timespinner('getValue');
                    var timeEnd = optDialog.find(".easyui-timespinner:eq(1)").timespinner('getValue');
                    $.ajax({
                        url: mmx.modeUrl+'/edit',
                        data: {roomid: roomid, id: id, timeStart: timeStart, timeEnd: timeEnd},
                        type: 'post',
                        dataType: 'json',
                        success: function(data){
                            mmx.__ajaxSuccess(data);
                            optDialog.dialog('close');
                            optDialog.remove();
                        },
                        error: function(xhr, ts, e){ mmx.__ajaxError(xhr, ts, e) }
                    });
                }
            },{
                text:'关闭',
                iconCls: 'icon-no',
                handler:function(){ optDialog.dialog('close');optDialog.remove();}
            }]
        });

        optDialog.find(".dialog-content-add").append('\
            时段开始：<input class="easyui-timespinner valid-befortime" data-options="width:80,required:true"/>\
            时段结束：<input class="easyui-timespinner" validType="afterTime[\'.valid-befortime\']" data-options="width:80,required:true"/>\
        ');
        $.parser.parse(optDialog);
        optDialog.find(".easyui-timespinner:eq(0)").timespinner('setValue', WhgComm.FMTTime(row.timestart));
        optDialog.find(".easyui-timespinner:eq(1)").timespinner('setValue', WhgComm.FMTTime(row.timeend));
    };

</script>
</body>
</html>
