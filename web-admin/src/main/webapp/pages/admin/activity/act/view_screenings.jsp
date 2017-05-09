<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% request.setAttribute("basePath", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath());%>
<% request.setAttribute("resourceid", request.getParameter("rsid")); %>
<% String path = request.getContextPath();%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>活动管理-场次列表</title>
    <%@include file="/pages/comm/admin/header.jsp"%>
    <link rel="stylesheet" href="<%=path%>/static/admin/Font-Awesome/css/font-awesome.min.css"/>
    <link rel="stylesheet" href="<%=path%>/static/admin/css/build.css"/>
    <script type="text/javascript" charset="utf-8"	src="<%=path%>/static/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8"	src="<%=path%>/static/ueditor/ueditor.all.min.js"></script>
    <script type="text/javascript" charset="utf-8"	src="<%=path%>/static/ueditor/lang/zh-cn/zh-cn.js"></script>
</head>
<body>

    <table id="whgdg" title="活动管理-场次列表(${act.name})" class="easyui-datagrid"
           data-options="fit:true,
                                         striped:true,
                                         rownumbers:true,
                                         fitColumns:true,
                                         singleSelect:false,
                                         checkOnSelect:true,
                                         selectOnCheck:true,
                                         pagination:true,
                                         toolbar:'#whgdg-tb',
                                         url:'${basePath}/admin/activity/act/getScreenings?activityId=${act.id}'
     ">
        <thead>
            <tr>
                <th data-options="field:'playdate', width:160,formatter:WhgComm.FMTDate">日期</th>
                <th data-options="field:'playstime', width:160">开始时间</th>
                <th data-options="field:'playetime', width:160">结束时间</th>
                <th data-options="field:'seats', width:160">场次座位总数</th>
                <th data-options="field:'state', width:160, formatter:setStatus">状态</th>
                <th data-options="field:'_opt', width:160,formatter:getRowBtn">操作</th>
            </tr>
        </thead>
    </table>

    <!-- 表格操作工具栏 -->
    <div id="whgdg-tb" style="display: none;">
        <div class="whgd-gtb-btn">
        	<c:if test="${act.state != 6 }">
        		<a href="javascript:void(0)" class="easyui-linkbutton whgff-but-clear" iconCls="icon-ok" onclick="addScreenings()">新 增</a>
        	</c:if>
            <a href="javascript:void(0)" class="easyui-linkbutton whgff-but-clear" iconCls="icon-undo" onclick="WhgComm.editDialogClose()">返 回</a>
        </div>
    </div>
    <!-- 表格操作工具栏-END -->

    <div id="dd">
        <form  id="whgff" class="whgff" method="post">
        </form>

    </div>



    <script type="text/javascript">
    	var actId = '${act.id}';
    	var state = '${act.state}';
        function setStatus(state) {
            if(0 == state){
                return "非正常";
            }else {
                return "正常";
            }
        }
        
        function addScreenings(rowIndex) {
            var actid = actId;
            var seats = '${seats}';
            $("#whgff").children().remove();
            createActId(actid);
            createPlaydate_('');
            createTime('08:00','12:00');
            createSeats(seats);
            createState();
            $("#dd").dialog(
                {
                    title:'场次时间新增',
                    width:400,
                    height:300,
                    close:false,
                    modal:true,
                    buttons:[
                        {
                            text:'提交',
                            handler:function () {
                                if(2 != compareTime($('#playstime').timespinner('getValue'),$('#playetime').timespinner('getValue'))){
                                	$("#time_check").show();
                    	            return false;
                                }if(Number($("#seats").textbox('getValue')) > Number(seats)){
                                	$.messager.alert("错误", '新增座位数不允许大于活动场次总座位数!', 'error');
                                	return false;
                                } 
                                $.post("/admin/activity/act/addScreenings?actId="+actid,$("#whgff").serialize(),function (data) {
                                    $("#dd").dialog('close');
                                    if(1 == data.success){
                                        $("#whgdg").datagrid('reload');
                                    }
                                },"json");
                            }
                        },
                        {
                            text:'取消',
                            handler:function () {
                                $("#dd").dialog('close');
                            }
                        }
                    ]
                }
            );
        }
        

        function updateScreenings(rowIndex) {
            /**
             * 获取
             * @type {*}
             */
            var id = $("tr[datagrid-row-index='"+rowIndex+"']").find("input[rowno='"+rowIndex+"']").attr("keyid");
            var actid = $("tr[datagrid-row-index='"+rowIndex+"']").find("input[rowno='"+rowIndex+"']").attr("actid");
            var playdate = $("tr[datagrid-row-index='"+rowIndex+"']").find("td[field='playdate']").find("div").html();
            var playstime = $("tr[datagrid-row-index='"+rowIndex+"']").find("td[field='playstime']").find("div").html();
            var playetime = $("tr[datagrid-row-index='"+rowIndex+"']").find("td[field='playetime']").find("div").html();
            var seats = $("tr[datagrid-row-index='"+rowIndex+"']").find("td[field='seats']").find("div").html();
            $("#whgff").children().remove();
            createId(id);
            createActId(actid);
            createPlaydate(playdate);
            createTime(playstime,playetime);
            createSeats(seats);
            createState();
            $("#dd").dialog(
                {
                    title:'场次时间修改',
                    width:400,
                    height:300,
                    close:false,
                    modal:true,
                    buttons:[
                        {
                            text:'提交',
                            handler:function () {
                                if(2 != compareTime($('#playstime').timespinner('getValue'),$('#playetime').timespinner('getValue'))){
                                	$("#time_check").show();
                    	            return false;
                                } 
                                $.post("/admin/activity/act/updateScreenings",$("#whgff").serialize(),function (data) {
                                    $("#dd").dialog('close');
                                    if(1 == data.success){
                                        $("#whgdg").datagrid('reload');
                                    }else{
                                    	$.messager.alert("错误", data.errormsg, 'error');
                    					return false;
                                    }
                                },"json");
                            }
                        },
                        {
                            text:'取消',
                            handler:function () {
                                $("#dd").dialog('close');
                            }
                        }
                    ]
                }
            );
        }

        
        
        function compareTime(time1,time2) {
            var strTime = time1.replace(":","");
            var endTime = time2.replace(":","");
            if(Number(strTime) > Number(endTime)){
                return 1;
            }else if(Number(strTime) < Number(endTime)){
                return 2;
            }else{
                return 0;
            }
        }

        function createId(id) {
            var object = $("<input type='hidden' id='id' name='id'>");
            object.val(id);
            object.appendTo("#whgff");
        }

        function createActId(actid) {
            var object = $("<input type='hidden' id='actid' name='actid'>");
            object.val(actid);
            object.appendTo("#whgff");
        }
        
        function createState() {
            var object = $("<input type='hidden' id='state' name='state'>");
            object.val(1);
            object.appendTo("#whgff");
        }

        function createPlaydate(playdate) {
            var object = $("<div class='whgff-row'>");
            var subObject1 = $("<div class='whgff-row-label' style='width: 25% !important;'>");
            subObject1.html("日期：");
            subObject1.appendTo(object);
            var subObject2 = $('<div class="whgff-row-input" style="width: 70% !important;">');
            subObject2.append('<input class="easyui-datebox" id="playdate" name="playdate" style="width:200px; height:32px" data-options="validType:length[3,30]" value="'+playdate+'" readonly="readonly">');
            subObject2.appendTo(object);
            object.appendTo("#whgff");
            $.parser.parse("#whgff");
        }
        
        function createPlaydate_(playdate) {
            var object = $("<div class='whgff-row'>");
            var subObject1 = $("<div class='whgff-row-label' style='width: 25% !important;'>");
            subObject1.html("日期：");
            subObject1.appendTo(object);
            var subObject2 = $('<div class="whgff-row-input" style="width: 70% !important;">');
            subObject2.append('<input class="easyui-datebox" id="playdate" name="playdate" style="width:200px; height:32px" data-options="validType:length[3,30],required:true"  value="'+playdate+'" >');
            subObject2.appendTo(object);
            object.appendTo("#whgff");
            $.parser.parse("#whgff");
        }

        function createTime(playstime,playetime) {
            var object = $("<div class='whgff-row'>");
            var subObject1 = $("<div class='whgff-row-label' style='width: 25% !important;'>");
            subObject1.html("活动时间：");
            subObject1.appendTo(object);
            var subObject2 = $('<div class="whgff-row-input" style="width: 70% !important;">');
            subObject2.append('<input class="easyui-timespinner" style="width: 100px; height: 32px;" id="playstime"  name="playstime" value="'+playstime+'"/>');
            subObject2.append("-");
            subObject2.append('<input class="easyui-timespinner" style="width: 100px; height: 32px;"  id="playetime" name="playetime" value="'+playetime+'"/><label style="color: red;margin-left: 10px;display: none;" id="time_check">时间填写不正确</label>');
            subObject2.appendTo(object);
            object.appendTo("#whgff");
            $.parser.parse("#whgff");
        }

        function createSeats(seats) {
            var object = $("<div class='whgff-row'>");
            var subObject1 = $("<div class='whgff-row-label' style='width: 25% !important;'>");
            subObject1.html("场次座位数：");
            subObject1.appendTo(object);
            var subObject2 = $('<div class="whgff-row-input" style="width: 70% !important;">');
            subObject2.append('<input class="easyui-textbox" id="seats" name="seats" style="width:200px; height:32px" data-options="validType:\'length[1,5]\', required:true"  value="'+seats+'">');
            subObject2.appendTo(object);
            object.appendTo("#whgff");
            $.parser.parse("#whgff");
        }

        function getRowBtn(value,rowData,rowIndex) {
            var html = "";
            if(state != 6){
            	 html += "<a href='javascript:void(0);' class='easyui-linkbutton' style='margin-right: 10px' onclick='updateScreenings("+rowIndex+");' >修改</a>";
                 if(1 == rowData.state){
                     html += "<a href='javascript:void(0);' class='easyui-linkbutton' style='margin-right: 10px' onclick='updateScreeningsStatus("+rowIndex+",0)'>取消</a>";
                 }else{
                     html += "<a href='javascript:void(0);' class='easyui-linkbutton' style='margin-right: 10px' onclick='updateScreeningsStatus("+rowIndex+",1)'>恢复</a>";
                 }
                 html+= "<input type='hidden' rowNo='"+rowIndex+"' actid='"+rowData.actid+"' keyId='"+rowData.id+"' >";
            }
            return html;
        }
        
        function updateScreeningsStatus(rowIndex,status) {
            var id = $("tr[datagrid-row-index='"+rowIndex+"']").find("input[rowno='"+rowIndex+"']").attr("keyid");
            var actid = $("tr[datagrid-row-index='"+rowIndex+"']").find("input[rowno='"+rowIndex+"']").attr("actid");
            var playdate = $("tr[datagrid-row-index='"+rowIndex+"']").find("td[field='playdate']").find("div").html();
            var playstime = $("tr[datagrid-row-index='"+rowIndex+"']").find("td[field='playstime']").find("div").html();
            var playetime = $("tr[datagrid-row-index='"+rowIndex+"']").find("td[field='playetime']").find("div").html();
            var seats = $("tr[datagrid-row-index='"+rowIndex+"']").find("td[field='seats']").find("div").html();
            var param = "";
            param += "id=" + id;
            param += "&actid=" + actid;
            param += "&playdate=" + playdate;
            param += "&playstime=" + playstime;
            param += "&playetime=" + playetime;
            param += "&seats=" + seats;
            param += "&state=" + status ;
            $.getJSON("/admin/activity/act/updateScreenings?" + param,function (data) {
                if(1 == data.success){
                    $("#whgdg").datagrid('reload');
                }else{
                	$.messager.alert("错误", data.errormsg, 'error');
					return false;
                }
            })
        }
    </script>
</body>
</html>
