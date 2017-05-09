<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath(); request.setAttribute("basePath", basePath); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<%@include file="/pages/comm/header.jsp"%>
<link rel="stylesheet" href="${basePath }/static/fullcalendar/fullcalendar.min.css"></link>
<link rel="stylesheet" href="${basePath }/static/fullcalendar/fullcalendar.print.css" media='print'></link>
<script type="text/javascript" src="${basePath }/static/fullcalendar/lib/moment.min.js" ></script>
<script type="text/javascript" src="${basePath }/static/fullcalendar/fullcalendar.min.js" ></script>
<script type="text/javascript" src="${basePath }/static/fullcalendar/locale/zh-cn.js" ></script>
<script>
/**培训的开始和结束日期*/
var tra_s_date = '${tra_s_date}';//'2016-10-10';
var tra_e_date = '${tra_e_date}';//'2016-11-28';
var traitmid = '${traid}';

/** START-文档加载结束后处理逻辑 */
$(document).ready(function(){
	/** START-dialog Init */
	$('#dlg').dialog({
		title: '添加课程表',
		buttons: [{
			text:'提交',
			iconCls:'icon-ok',
			handler:function(){
				$('#ff').form('submit', {
					url:'${basePath }/admin/train/kcadd',
					success:function(data){
						var data = eval('(' + data + ')');
						if(data && data.success == '0'){
							$('#dlg').dialog('close');
							$('#calendar').fullCalendar("refetchEvents");
						}else{
							$.messager.alert('提示', '操作失败。原因：'+data.errmsg, 'error');
						}
					}
				});
			}
		},{
			text:'删除',
			iconCls:'icon-remove',
			handler:function(){
				//课时标识
				var timeid = $('#timeid').val();
				if(timeid != ''){
					$.ajax({
						url:'${basePath }/admin/train/kcdel',
						data: {"timeid": timeid},
						success: function(data){
							if(data && data.success == '0'){
								$('#dlg').dialog('close');
								$('#calendar').fullCalendar("refetchEvents");//刷新日历显示
							}else{
								$.messager.alert('提示', '操作失败。原因：'+data.errmsg, 'error');
							}
						}
					});
				}else{
					$('#dlg').dialog('close');
				}
			}
		}]
	}).dialog('close');/** END-dialog Init */
	
	/** START-fullCalendar Init */
	$('#calendar').fullCalendar({
		titleFormat: 'YYYY年MM月-课程表设置',		
		/**自定义两个按钮*/
		customButtons: {
			myprev: {text:'<', click:function(){
				$('#calendar').fullCalendar( 'prev' );
			}},
			mynext: {text:'>', click:function(){
				$('#calendar').fullCalendar( 'next' );
			}}
		},
		/**日历头部定义*/
		header: {
			left: 'prev,next today',
			center: 'title',
			right: 'month,basicWeek,basicDay'
		},
		firstDay:0,
		defaultDate: tra_s_date,/*培训开始时间*/
		dayClick: function(date, jsEvent, view) {
			/*添加课时*/
			$('#ff').form('clear');
			
			//验证是否在tra_s_date和tra_e_date之间
			if(date < Date.parse(tra_s_date) || date >= Date.parse(tra_e_date)){
				$.messager.alert('提示','只能在'+tra_s_date+'-'+tra_e_date+'之间设置课时！');
				return;
			}
			
			//设置初始值
			var curDate = $.fullCalendar.moment(date).format('YYYY-MM-DD');//date.format('YYYY-MM-DD');
			var curDate_s = curDate+" 10:30";
			var curDate_e = curDate+" 11:30";
			$('#stime').datetimespinner('setValue', curDate_s);
			$('#etime').datetimespinner('setValue', curDate_e);
			$('#tradate').val(curDate);
			$('#traitmid').val(traitmid);
			
			//弹出编辑框
			$('#dlg').dialog('open');
		},
		eventClick: function(event, jsEvent, view) {
			//编辑课时
			$('#ff').form('clear');
			
			//设置初始值
			var curDate = $.fullCalendar.moment(event.start).format('YYYY-MM-DD');//date.format('YYYY-MM-DD');
			var curDate_s = $.fullCalendar.moment(event.start).format('YYYY-MM-DD HH:mm:ss');
			var curDate_e = $.fullCalendar.moment(event.end).format('YYYY-MM-DD HH:mm:ss');
			$('#stime').datetimespinner('setValue', curDate_s);
			$('#etime').datetimespinner('setValue', curDate_e);
			$('#tradate').val(curDate);
			$('#timeid').val(event.id);
			$('#dtitle').textbox('setValue', event.title);
			$('#traitmid').val(traitmid);
			
			//弹出编辑框
			$('#dlg').dialog('open');
		},
		events: function(start, end, timezone, callback) {
			/** 从后台加载指定时间内的课时信息 */
			var _start = start.format('YYYY-MM-DD HH:mm:ss');
			var _end = end.format('YYYY-MM-DD HH:mm:ss');
			$.ajax({
		    	url: '${basePath }/admin/train/kclist',
		    	data: {start: _start, end: _end, traitmid: traitmid},
		    	success: function(data){
		    		var events = [];
		    		if($.isArray(data)){
		    			$.each(data, function(idx, row){
		    				events.push({
		    					title: row.title,
		    					start: row.start,
		    					end: row.end,
		    					id: row.timeid
		    				});
		    			});
		    		} 
	                callback(events);
		    	}
		    });
		}
	});/**END-fullCalendar Init */
		
});/**END-文档加载结束后处理逻辑 */
</script>
<style>

	body {
		margin: 40px 10px;
		padding: 0;
		font-family: "Lucida Grande",Helvetica,Arial,Verdana,sans-serif;
		font-size: 14px;
	}

	#calendar {
		max-width: 900px;
		margin: 0 auto;
	}

</style>
</head>
<body>
	<div id="calendar"></div>
	
	<!-- 编辑窗口 -->
	<div id="dlg" style="width:400px;height:200px;padding:10px" data-options="modal:true">
			<center>
			    <form id="ff" method="post">
			    	<input type="hidden" id="tradate" name="tradate"/>
			    	<input type="hidden" id="timeid" name="timeid"/>
			    	<input type="hidden" id="traitmid" name="traitmid"/>
			    	<table cellpadding="5">
			    		<tr>
			    			<td>标题:</td>
			    			<td><input class="easyui-textbox" type="text" id="dtitle" name="dtitle" data-options="required:true, validType:['length[1,10]']" style="width:180px;"></input></td>
			    		</tr>
			    		<tr>
			    			<td>开始时间:</td>
			    			<td><input class="easyui-datetimespinner" id="stime" name="stime" data-options="required:true, selections:[[11,13],[14,16]]" value="2016-10-11 17:23" style="width:180px;"></input></td>
			    		</tr>
			    		<tr>
			    			<td>结束时间:</td>
			    			<td><input class="easyui-datetimespinner" id="etime" name="etime" data-options="required:true, selections:[[11,13],[14,16]]" value="2016-10-11 17:23" style="width:180px;"></input></td>
			    		</tr>
			    	</table>
			    </form>
			</center>
	</div>
</body>
</html>