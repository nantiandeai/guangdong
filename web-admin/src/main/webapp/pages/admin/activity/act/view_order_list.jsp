<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% request.setAttribute("basePath", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath());%>
<% request.setAttribute("resourceid", request.getParameter("rsid")); %>
<%
    String path = request.getContextPath();
    request.setAttribute("nowSys", new java.util.Date());
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>活动管理-订单管理</title>
    <%@include file="/pages/comm/admin/header.jsp"%>
    <%--<link rel="stylesheet" href="<%=path%>/static/admin/Font-Awesome/css/font-awesome.min.css"/>--%>
    <%--<link rel="stylesheet" href="<%=path%>/static/admin/css/build.css"/>--%>
    <%--<script type="text/javascript" charset="utf-8"	src="<%=path%>/static/ueditor/ueditor.config.js"></script>--%>
    <%--<script type="text/javascript" charset="utf-8"	src="<%=path%>/static/ueditor/ueditor.all.min.js"></script>--%>
    <%--<script type="text/javascript" charset="utf-8"	src="<%=path%>/static/ueditor/lang/zh-cn/zh-cn.js"></script>--%>
</head>
<body>
    <table id="whgdg" title="活动管理-订单管理" class="easyui-datagrid"
           data-options="fit:true,
                                         striped:true,
                                          rownumbers:true,
                                          fitColumns:true,
                                          singleSelect:false,
                                          checkOnSelect:true,
                                          selectOnCheck:true,
                                          pagination:true,
                                          toolbar:'#whgdg-tb',
                                          url:'${basePath}/admin/activity/act/getActOrder?activityId=${act.id}'
         ">
        <thead>
        <tr>
            <th data-options="field:'actname', width:100">活动名</th>
            <th data-options="field:'starttime', width:100,formatter:WhgComm.FMTDate">活动开始时间</th>
            <th data-options="field:'endtime', width:100,formatter:WhgComm.FMTDate">活动结束时间</th>
            <th data-options="field:'playdate', width:100,formatter:WhgComm.FMTDate">预定活动时间</th>
            <th data-options="field:'playstime', width:100">时段开始</th>
            <th data-options="field:'playetime', width:100">时段结束</th> 
            <th data-options="field:'ordernumber', width:100">订单编号</th>
            <th data-options="field:'ordername', width:100">预订人姓名</th>
            <th data-options="field:'orderphoneno', width:100">预定人手机号</th>
            <th data-options="field:'ordersmsstate', width:80, formatter:getSmsSendStatus">短信发送状态</th>
            <%--<th data-options="field:'ordercreatetime', width:100, formatter:WhgComm.FMTDateTime">生成订单时间</th>--%>
            <th data-options="field:'ticketstatus', width:80, formatter:setTicketsStatus">取票状态</th>
            <th data-options="field:'_opt', width:160,formatter:WhgComm.FMTOpt,optDivId:'whgdg-opt'">操作</th>
        </tr>
        </thead>
    </table>

    <!-- 表格操作工具栏 -->
    <div id="whgdg-tb" style="display: none;">
        <div>
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-back" onclick="WhgComm.editDialogClose()">返 回</a>
        </div>
        <div style="padding-top: 5px">
            <input class= "easyui-textbox" name="ordernumber" data-options="width:120" prompt="订单编号"/>
            <input class= "easyui-textbox" name="orderphoneno" data-options="width:120" prompt="预订人手机号"/>
            <input class= "easyui-datebox" name="starttime" data-options="width:120" prompt="活动开始时间"/>
            <input class= "easyui-datebox" name="endtime" data-options="width:120" prompt="活动结束时间"/>
            <a class="easyui-linkbutton" iconCls="icon-search" onclick="WhgComm.search('#whgdg', '#whgdg-tb');">查 询</a>
       </div>
    </div>
    
    

    <div id="whgdg-opt" style="display: block;">
        <%--<shiro:hasPermission name="${resourceid}:resend"><a href="javascript:void(0)" class="easyui-linkbutton" plain="true" method="">重发订单短信</a></shiro:hasPermission>--%>
        <a href="javascript:void(0)" class="easyui-linkbutton" plain="true" method="showInfo">查看</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" plain="true" validFun="vfunSmsBut" method="againSendSms">重发订单短信</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" plain="true" validFun="vfunUnBut" method="cancelOrder">取消订单</a>
    </div>

    <!-- 编辑表单 -->
    <div id="whgwin-edit"></div>
    <!-- 编辑表单 END -->

    <script type="text/javascript">

        /**
         * 提供操作列的按钮
         * @param value
         * @param rowData
         * @param rowIndex
         * @returns {string}
         */
        /*function getRowBtn(value,rowData,rowIndex) {
            var html = "";
            if(rowData.ticketstatus == 1 && rowData.orderisvalid == 1 && rowData.endtime > rowData.nowDate){
            	 html += "<a href='javascript:void(0);' class='easyui-linkbutton' style='margin-right: 10px' onclick='againSendSms("+rowIndex+")' >重发订单短信</a>";
            }
            if(rowData.ticketstatus == 1 && rowData.orderisvalid == 1 && rowData.state != 1 ){
            	html += "<a href='javascript:void(0);' class='easyui-linkbutton' style='margin-right: 10px' onclick='cancelOrder("+rowIndex+")' >取消订单</a>";
            }
            html+= "<input type='hidden' rowNo='"+rowIndex+"' actid='"+rowData.actid+"' keyId='"+rowData.id+"' >";
            return html;

        }*/
        function vfunSmsBut(idx){
            var rowData = $("#whgdg").datagrid("getRows")[idx];
            return (rowData.ticketstatus == 1 && rowData.orderisvalid == 1 && rowData.endtime > rowData.nowDate);
        }
        function vfunUnBut(idx){
            var rowData = $("#whgdg").datagrid("getRows")[idx];
            return (rowData.ticketstatus == 1 && rowData.orderisvalid == 1 && rowData.state != 1 );
        }

        /**
         * 获取短信发送状态
         * @param ordersmsstate
         * @returns {*}
         */
        function getSmsSendStatus(ordersmsstate) {
            if(1 == ordersmsstate){
                return "未发送";
            }else if(2 == ordersmsstate){
                return "发送成功";
            }else if(3 == ordersmsstate){
                return "发送失败";
            }else {
                return "未知";
            }
        }

        /**
         * 获取取票状态
         * @param ticketstatus
         * @returns {*}
         */
        function setTicketsStatus(ticketstatus, row, index) {
            var nowSys = '${nowSys}';

            if(1 == ticketstatus){
                var now = new Date( Date.parse(nowSys) );
                var play = new Date(row.playdate);
                var ptime = /(\d{2}):(\d{2})/.exec(row.playetime);
                if (ptime){
                    var hh = String(ptime[1]);
                    var mm = String(ptime[2]);
                    play.setHours(Number(hh));
                    play.setMinutes(Number(mm));
                }

                return play>now?"未取票" : "已过期";
            }else if(2 == ticketstatus){
                return "已取票";
            }else if(3 == ticketstatus){
                return "已取消";
            }else {
                return "未知";
            }
        }
        
         /**
          * 重发订单短信
          * @param orderId
          * @returns {*}
          */
        function againSendSms(rowIndex) {
        	 //var id = $("tr[datagrid-row-index='"+rowIndex+"']").find("input[rowno='"+rowIndex+"']").attr("keyid");
             var rowData = $("#whgdg").datagrid("getRows")[rowIndex];
             var id = rowData.id;
   	         var params = {orderId: id};
	   	     $.messager.confirm("确认信息", "确定要给选中的项重新发送订单短信吗？", function(r){
	              if (r){
                  $.messager.progress();
            	  $.post('${basePath}/admin/activity/act/againSendSms', params, function(data){
        	             $("#whgdg").datagrid('reload');
        	             if (!data.success || data.success != "1"){
        	                 $.messager.alert("错误", data.errormsg||'操作失败', 'error');
        	             }
        	             $.messager.progress('close');
        	         }, 'json');
	              }
	          });
        }

        /**
         * 取消订单
         * @param orderId
         * @returns {*}
         */
        function cancelOrder(rowIndex) {
            //var id = $("tr[datagrid-row-index='"+rowIndex+"']").find("input[rowno='"+rowIndex+"']").attr("keyid");
            var rowData = $("#whgdg").datagrid("getRows")[rowIndex];
            var id = rowData.id;
            var params = {orderId: id};
            $.messager.confirm("确认信息", "确定取消选中的订单吗？", function(r){
                if (r){
                    $.messager.progress();
                    $.post('${basePath}/admin/activity/act/cancelOrder', params, function(data){
                        $("#whgdg").datagrid('reload');
                        if (!data.success || data.success != "1"){
                            $.messager.alert("错误", data.errormsg||'操作失败', 'error');
                        }
                        $.messager.progress('close');
                    }, 'json');
                }
            });
        }

        function showInfo(rowIndex){
            var rowData = $("#whgdg").datagrid("getRows")[rowIndex];
            WhgComm.editDialog( '${basePath}/admin/activity/act/view/order/info?orderid='+rowData.id );
        }
    </script>
</body>
</html>
