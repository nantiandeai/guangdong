<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<% request.setAttribute("basePath", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath());%>
<% request.setAttribute("resourceid", request.getParameter("rsid")); %>
<% String path = request.getContextPath();%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>活动管理-发布活动</title>
    <%@include file="/pages/comm/admin/header.jsp"%>
	<link rel="stylesheet" href="${basePath}/static/admin/css/bootstrap.css"/>
	<link rel="stylesheet" href="${basePath}/static/admin/Font-Awesome/css/font-awesome.min.css"/>
	<link rel="stylesheet" href="${basePath}/static/admin/css/build.css"/>
	<script type="text/javascript" charset="utf-8"	src="<%=path%>/static/ueditor/ueditor.config.js"></script>
	<script type="text/javascript" charset="utf-8"	src="<%=path%>/static/ueditor/ueditor.all.min.js"></script>
	<script type="text/javascript" charset="utf-8"	src="<%=path%>/static/ueditor/lang/zh-cn/zh-cn.js"></script>

	<script src="${basePath}/static/admin/js/whgtoolmodule.js"></script>
	
	<script type="text/javascript" src="${basePath}/static/plupload/lib/plupload-2.1.2/js/plupload.full.min.js"></script>
	<script type="text/javascript" src="${basePath}/static/plupload/upload-img.js"></script>
	
	<script type="text/javascript" src="${basePath}/static/plupload/upload-file.js"></script>
	
    <script type="text/javascript" src="${basePath}/static/common/js/whg.maps.js"></script>
    
    <script src="${basePath}/static/admin/js/whgmodule-venseatmaps.js"></script>
    <script>
        $.extend($.fn.validatebox.defaults.rules, {
            bmEndTime: {
                validator: function(value, param){
                    var sdVal = $('#'+param[0]).datetimebox('getValue');
                    var d1 = WhgComm.parseDateTime(sdVal);//$.fn.datebox.defaults.parser(sdVal);
                    var d2 = WhgComm.parseDateTime(value);//$.fn.datebox.defaults.parser(value);
                    return d2.getTime()>d1.getTime();
                },
                message: '活动报名结束时间必须大于活动报名开始时间.'
            },
            hdEndDate: {
                validator: function(value, param){
                    var sdVal = $('#'+param[0]).datebox('getValue');
                    var d1 = WhgComm.parseDate(sdVal);//$.fn.datebox.defaults.parser(sdVal);
                    var d2 = WhgComm.parseDate(value);//$.fn.datebox.defaults.parser(value);
                    return d2.getTime()>=d1.getTime() || sdVal == value;
                },
                message: '活动结束日期必须大于活动开始日期.'
            }
        })
    </script>
</head>
<body>

	<input id="supplement" type="hidden" value="0">
	<form id="whgff" method="post" class="whgff">
		<h2>活动管理</h2>
		
		<div class="whgff-row">
			<div class="whgff-row-label"><i>*</i>活动名称：</div>
			<div class="whgff-row-input">
				<input class="easyui-textbox" name="name"
					style="width: 500px; height: 32px" data-options="required:true,validType:['length[1,60]']" />
			</div>
		</div>
		
		<div class="whgff-row">
		<div class="whgff-row-label"><i>*</i>活动简介：</div>
		<div class="whgff-row-input">
			<input class="easyui-textbox" name="actsummary"  style="width:500px; height:32px" data-options="required:true,validType:['length[1,60]']"></div>
		</div>
	
		<div class="whgff-row">
			<div class="whgff-row-label">
				<label style="color: red">*</label>上传封面：
			</div>
			<div class="whgff-row-input">
	            <input type="hidden" id="act_imgurl1" name="imgurl" data-options="required:true">
	            <div class="whgff-row-input-imgview" id="previewImg1"></div>
	            <div class="whgff-row-input-imgfile" id="imgUrl_pic">
	                <i><a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" id="imgUploadBtn1">选择图片</a></i>
	                <i>图片格式为jpg、png、gif，建议图片尺寸 750*500，大小为2MB以内</i>
	            </div>
	        </div>
		</div>
		
		 <div class="whgff-row">
			 <div class="whgff-row-label">
				<label style="color: red">*</label>区域：
			 </div>
			 <div class="whgff-row-input">
	            <div class="radio radio-primary whg-js-data"  name="areaid"  js-data="WhgComm.getAreaType"></div>
			</div>

		</div>
		
		<div class="whgff-row">
	        <div class="whgff-row-label"><label style="color: red">*</label>所属文化馆：</div>
	        <div class="whgff-row-input">
	            <select class="easyui-combobox" name="cultid" id="cultid" style="width:500px; height:32px"
	                    data-options="editable:false, required:true, valueField:'id', textField:'text',  data:WhgComm.getCult(),prompt:'请选择文化馆'"></select>
	        </div>
    	</div>

        <div class="whgff-row">
            <div class="whgff-row-label">文化品牌：</div>
            <div class="whgff-row-input">
               <%--<div class="checkbox checkbox-primary whg-js-data" name="ebrand"
					js-data="WhgComm.getBrand" >
				</div>--%>
				<div class="whgff-row-input">
					<div class="radio radio-primary whg-js-data"  name="ebrand"  js-data="WhgComm.getBrand"></div>
				</div>
            </div>
        </div>
	
		<div class="whgff-row">
	       <div class="whgff-row-label">场馆：</div>
	       <div class="whgff-row-input">
	       	<input class="easyui-combobox" name="venueid" id="venueid" panelHeight="auto" style="width:500px; height:32px"
                   data-options="editable:true,limitToList:true,
                   valueField:'id', textField:'title', url:'${basePath}/admin/venue/srchList?state=6&delstate=0',
                   onChange : changeVen,
                   onSelect:function(rec){
                       $('#roomid').combobox('clear');
                       $('#roomid').combobox('reload', '${basePath}/admin/venue/room/srchList?state=6&delstate=0&&venid=' + rec.id);},
                       loadFilter : function (data) {
                        if (data && data instanceof Array) {
                            //data.splice(0, 0, {id: '', title: '　'});　
                        }
                        return data;
                    }"/>
	   		</div>
		</div>

		<div class="whgff-row">
			<div class="whgff-row-label">活动室：</div>
			<div class="whgff-row-input">
				<input class="easyui-combobox" name="roomid" style="width:500px; height:32px" id="roomid"
					   data-options="editable:true,limitToList:true,valueField:'id', textField:'title',
                    onSelect:roomSelected,
                    loadFilter : function (data) {
                        if (data && data instanceof Array) {
                            //data.splice(0, 0, {id: '', title: '　'});　
                        }
                        return data;
                    }"/>
			</div>
		</div>
		
		<div class="whgff-row">
			<div class="whgff-row-label">主办方：</div>
			<div class="whgff-row-input">
				<input class="easyui-textbox" name="host"
					style="width: 500px; height: 32px" data-options="required:false,validType:['length[0,20]']" />
			</div>
		</div>
		<div class="whgff-row">
			<div class="whgff-row-label">承办单位：</div>
			<div class="whgff-row-input">
				<input class="easyui-textbox" name="organizer"
					style="width: 500px; height: 32px" data-options="required:false,validType:['length[0,20]']" />
			</div>
		</div>
		
		<div class="whgff-row">
			<div class="whgff-row-label">协办单位：</div>
			<div class="whgff-row-input">
				<input class="easyui-textbox" name="coorganizer"
					style="width: 500px; height: 32px" data-options="required:false,validType:['length[0,20]']" />
			</div>
		</div>
			
		<div class="whgff-row">	
			<div class="whgff-row-label">演出单位：</div>
			<div class="whgff-row-input">
				<input class="easyui-textbox" name="performed"
					style="width: 500px; height: 32px" data-options="required:false,validType:['length[0,20]']" />
			</div>
		</div>
		
		<div class="whgff-row">
			<div class="whgff-row-label">主讲人：</div>
			<div class="whgff-row-input">
				<input class="easyui-textbox" name="speaker"
					style="width: 500px; height: 32px" data-options="required:false,validType:['length[0,20]']" />
			</div>
		</div>

		<div class="whgff-row">
			<div class="whgff-row-label">
				<label style="color: red">*</label>活动分类：
			</div>
			<div class="whgff-row-input" data-check="true" target="etype" err-msg="请至少选择一个分类">
				<div class="checkbox checkbox-primary whg-js-data" name="etype"
					js-data="WhgComm.getActivityType" >
				</div>
			</div>
		</div>
	
		<div class="whgff-row">
			<div class="whgff-row-label"><i>*</i>艺术分类：</div>
			<div class="whgff-row-input" data-check="true" target="arttype" err-msg="请至少选择一个艺术分类">
	           <div class="checkbox checkbox-primary whg-js-data" name="arttype" js-data="WhgComm.getArtType">
	       	</div>
		</div>
		</div>

        <div class="whgff-row">
            <div class="whgff-row-label">标签：</div>
            <div class="whgff-row-input" data-check="false" target="etag" err-msg="请至少选择一个标签">
                <div class="checkbox checkbox-primary whg-js-data" name="etag" js-data="WhgComm.getActivityTag"></div>
            </div>
        </div>
		
		<div class="whgff-row">
			<div class="whgff-row-label">关键字：</div>
			<div class="whgff-row-input" data-check="false" target="ekey" err-msg="请至少选择一个关键字">
		    	<%--<div class="checkbox checkbox-primary whg-js-data" name="ekey" js-data="WhgComm.getActivityKey"></div>--%>
				<input class="easyui-combobox" name="ekey" style="width:500px; height:32px" validType="notQuotes" data-options="multiple:true,editable:true,valueField:'text',textField:'text', data:WhgComm.getActivityKey(),prompt:'请填写关键字'"/>
				<span>（如需手动输入，请用英文逗号隔开！）</span>
			</div>
		</div>

		<div class="whgff-row">
			<div class="whgff-row-label">
				<label style="color: red">*</label>活动报名时间：
			</div>
			<div class="whgff-row-input">
				<input type="text" class="easyui-datetimebox enterstrtime"style="width: 240px; height: 32px;" id="enterstrtime" name="enterstrtime" required="required" data-options="editable:false,required:true,prompt:'请选择'"/> ~
				<input type="text" class="easyui-datetimebox enterendtime"style="width: 240px; height: 32px;" id="enterendtime" name="enterendtime" required="required" data-options="editable:false,required:true,prompt:'请选择',validType:'bmEndTime[\'enterstrtime\']'" />
			</div>
		</div>

		<div class="whgff-row">
			<div class="whgff-row-label">
				<label style="color: red">*</label>活动日期：
			</div>
			<div class="whgff-row-input">
				<input type="text" class="easyui-datebox"style="width: 240px; height: 32px;" id="starttime" name="starttime" required="required" data-options="editable:false,required:true,prompt:'请选择'" /> ~
				<input type="text" class="easyui-datebox"style="width: 240px; height: 32px;" id="endtime" name="endtime" required="required" data-options="editable:false,required:true,prompt:'请选择',validType:'hdEndDate[\'starttime\']'" />
			</div>
		</div>

		<div class="whgff-row">
			<div class="whgff-row-label">
				<label id="timeStarSpan" style="color: red">*</label>场次模板：
			</div>
			<div class="whgff-row-input">
				<div id="free-time-set">
					<div id="put-ticket-list" style="width: 800px;">
						<div class="ticket-item activityTimeLabel1">
							<input class="easyui-timespinner" style="width: 100px; height: 32px;" value="08:00" id="playstrtime" name="playstrtime" data-options="required:true"/>-
							<input class="easyui-timespinner" style="width: 100px; height: 32px;" value="10:00" id="playendtime" name="playendtime" data-options="required:true"/>
							<a href="javascript:void(0)" id="add" >添加</a>
							<label style="color: red;margin-left: 10px;display: none;">时间填写不正确</label>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="whgff-row">
			<div class="whgff-row-label">
				<label style="color: red">*</label>地址：
			</div>
			<div class="whgff-row-input">
				<input class="easyui-textbox" name="address" id="address" style="width: 500px; height: 32px" data-options="required:true,validType:['length[0,60]']">
			</div>
		</div>
		<div class="whgff-row">
			<div class="whgff-row-label">
				<label style="color: red">*</label>坐标：
			</div>
			<div class="whgff-row-input">
				<input class="easyui-numberbox" name="actlon" id="actlon" style="width: 150px; height: 32px" data-options="required:true, precision:6,readonly:true,prompt:'X轴'"> 
				<input class="easyui-numberbox" name="actlat" id="actlat" style="width: 150px; height: 32px" data-options="required:true, precision:6,readonly:true,prompt:'Y轴'"> 
				<a class="easyui-linkbutton whg-maps" map-addr="#address" vm-x="actlon" vm-y="actlat" id="getXYPointBtn" text="选择坐标"></a>
			</div>
		</div>
	
		<div class="whgff-row">
			<div class="whgff-row-label"><label style="color: red">*</label>联系电话：</div>
			<div class="whgff-row-input">
				<input class="easyui-textbox" name="telphone" style="width: 500px; height: 32px" data-options="prompt:'请输入联系人手机', required:true, validType:'isPhone'">
			</div>
		</div>
		
		<div class="whgff-row">
			<div class="whgff-row-label">
				<label style="color: red">*</label>是否消耗积分：
			</div>
			<div class="whgff-row-input">
		    	<div class="radio radio-primary whg-js-data" name="integral" id="integral"  onclick="isIntegral()" 
                 js-data='[{"id":"1","text":"默认"},{"id":"2","text":"积分"}]'>

            </div>
             <span id="integralCount" style="display:none">该活动需要消耗积分<input class="easyui-numberspinner" name="integralnum" value="50" id="integralnum" style="width: 50px; height: 25px" data-options="required:false,min:0,max:999">分 </span> 
			</div>
		</div>
		
		<div class="whgff-row">
			<div class="whgff-row-label">
				<label style="color: red">*</label>是否收费：
			</div>
			<div class="whgff-row-input">
		    	<div class="radio radio-primary whg-js-data" name="hasfees" id="hasfees"  
                 js-data='[{"id":"0","text":"否"},{"id":"1","text":"是"}]'>
            </div>
			</div>
		</div>
		
		
		<div class="whgff-row">
			<div class="whgff-row-label">
				<label style="color: red">*</label>活动类型：
			</div>
			<div class="whgff-row-input">
		    	<div class="radio radio-primary whg-js-data" name="sellticket"  id="sellticket"  onclick="isCheck()"
                 js-data='[{"id":"1","text":"不可预定"},{"id":"2","text":"自由入座"},{"id":"3","text":"在线选座"}]'>
					<input id="sellticketSelection" type="hidden" value="1">
				</div>
				<span id="maxBuySeatCount" style="display:none">每人每场最多购票<input class="easyui-numberspinner" name="seats" value="3" id="seats" style="width: 50px; height: 25px" data-options="required:false,min:0,max:99">张 </span>
				<span id="maxSoldSeatCount" style="display:none">每场次最多售票<input class="easyui-numberspinner" name="ticketnum" value="1000" id="ticketnum"  style="width:100px; height: 25px" data-options="required:false,min:0,max:999999">张 </span>
			</div>
		</div>
		
		<div class="whgff-row" style="display: none" id="onLineSeat">
			<div class="whgff-row-map test-seatmaps">
			</div>
		</div>
		
		<div class="whgff-row">
			<div class="whgff-row-label">
				<label style="color: red">*</label>活动描述：
			</div>
			<div class="whgff-row-input">
				<script id="remark" name="remark"  type="text/plain"	style="width: 700px; height: 300px;" data-options="required:true"></script>
				<label style="color: red;margin-left: 10px;display: none;" id="remark_lable">活动描述不允许为空</label>
			</div>
		</div>
		
		<%--<div class="whgff-row">--%>
			<%--<div class="whgff-row-label">--%>
				<%--上传附件：--%>
			<%--</div>--%>
			<%--<div class="whgff-row-input">--%>
				<%--<input  id="act_filepath1" name="filepath" data-options="required:true" style="width:600px;height:32px;" readonly="readonly">--%>
				<%--<!-- <input id="filepath"  class="easyui-filebox" name="filepath" style="width:800px;height:32px;" data-options="buttonText:'选择文件', accept:'xls,doc,txt,pdf,jpg,png'"> -->--%>
				<%--<div class="whgff-row-input" id="filepath">--%>
	                <%--<i><a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" id="fileUploadBtn1">选择文件</a></i>--%>
	                <%--<i>附件格式为doc,docx,xls,zip,xlsx,建议文件大小为10MB以内</i>--%>
	            <%--</div>--%>
			<%--</div>--%>
		<%--</div>--%>

		<div class="whgff-row">
			<div class="whgff-row-label">上传附件：</div>
			<div class="whgff-row-input">
				<input type="hidden" id="whg_file_upload" name="filepath">
				<div class="whgff-row-input-fileview" id="whg_file_pload_view"></div>
				<div class="whgff-row-input-filefile">
					<i><a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" id="fileUploadBtn2">选择附件</a></i>
					<i>附件格式为doc,docx,xls,xlsx,zip,pdf,建议文件大小为10MB以内</i>
				</div>
			</div>
		</div>
	</form>
	
	<div class="whgff-but" style="width: 400px; margin: 10px auto;">
		<a class="easyui-linkbutton whgff-but-submit" iconCls="icon-ok" id="whgwin-add-btn-save">提 交</a> 
		<a class="easyui-linkbutton whgff-but-clear" iconCls="icon-no" id="whgff-but-clear">清 空</a>
	</div>
<script>
	//定义座位变量
	var rowNum = 0;
	var colNum = 0;
	var seatjson = '';
	$(function() {
		
		
		var seatmap = $("div.whgff-row-map");
		
		//图片初始化
        var whgImg = WhgUploadImg.init({basePath: '${basePath}', uploadBtnId: 'imgUploadBtn1', hiddenFieldId: 'act_imgurl1', previewImgId: 'previewImg1'});
		//文件上传初始化
        <%--WhgUploadFile.init({basePath: '${basePath}', uploadBtnId: 'fileUploadBtn1', hiddenFieldId: 'act_filepath1'});--%>
       var whgFile = WhgUploadFile.init({basePath: '${basePath}', uploadBtnId: 'fileUploadBtn2', hiddenFieldId: 'whg_file_upload',previewFileId:'whg_file_pload_view'});

       //根据地址取坐标
        WhgMap.init({basePath:'${basePath}', addrFieldId:'address', xpointFieldId:'actlon', ypointFieldId:'actlat', getPointBtnId:'getXYPointBtn'});
		
		//初始化富文本
	    var ueConfig = {
	        scaleEnabled: false,
	        autoFloatEnabled: false,
            elementPathEnabled:false
	    };
		var remark = UE.getEditor('remark', ueConfig);

		//清空
		 $("#whgff-but-clear").on('click', function(){
			$('#whgff').form('clear');
			whgImg.clear();
			whgFile.clear();
	        //第一个单选又点上
	        $('#whgff').find("div.radio").find(':radio:eq(0)').click();
	        remark.setContent('');
	    });

		//提交事件
		function submitFun() {
            if(!supplement()){
                return;
            }
            if(!checkTime()){
                return;
            }
            if(!validateUE()){
                return;
            }
            if(!validateTicket()){
                return;
            }
            if(!validateIntegral()){
                return;
            } 
            $('#whgff').submit();
		}
		
		//初始化表单
		$('#whgff').form({
			novalidate : true,
			url : getFullUrl('/admin/activity/act/add'),
			onSubmit : function(param) {
				//坐位相关值处理 START
                var seatsValue = seatmap.whgVenseatmaps('getValue');
                param.seatjson = JSON.stringify(seatsValue);
                var list = $("#put-ticket-list").children("div.activityTimeLabel1");
                var activityTimeList = [];
                $.each(list,function(index,value){
                    var playstrtime = $(value).find("input[name='playstrtime']").val();
                    var playendtime = $(value).find("input[name='playendtime']").val();
                    var item = {};
                    item.playstrtime = playstrtime;
					item.playendtime = playendtime;
                    activityTimeList.push(item);
				});
                param.activityTimeList = JSON.stringify(activityTimeList);
              	//坐位相关值处理End
				var _valid = $(this).form('enableValidation').form('validate');
				if (_valid) {
					$.messager.progress();
				} else {
					//失败时再注册提交事件
					$('#whgwin-add-btn-save').off('click').one('click', submitFun);
				}
				return _valid;
			},
			success : function(data) {
				$.messager.progress('close');
				var Json = eval('(' + data + ')');
				if (Json && Json.success == '1') {
                    //window.parent.$("a[title='活动编辑列表']").click();
					$(this).form("disableValidation");
					//清空信息
					$('#whgff').form('clear');
					whgImg.clear();
					whgFile.clear();
					//第一个单选又点上
					$('#whgff').find("div.radio").find(':radio:eq(0)').click();
					remark.setContent('');

					$.messager.show({
						title:'提示消息',
						msg:'活动信息提交成功',
						showType:'slide',
						timeout:1000,
						width: 300,
						height: 200
					});
				} else {
					$.messager.alert('提示', '操作失败'+Json.errormsg+'！', 'error');
					$('#whgwin-add-btn-save').off('click').one('click', submitFun);
				}
			}
		});

		function supplement() {
		    $("div[data-check='true']").children("label").remove();
		    var list = $("div[data-check='true']");
		    for(var i = 0;i < list.length;i++){
                var target = $(list[i]).attr("target");
				var bCheck = false;
				var checkBoxs = $("div[name='"+target+"']").children(":checkbox");
				for(var cur = 0;cur < checkBoxs.length;cur++){
				    var check  = $(checkBoxs[cur]).is(':checked');
				    if(check){
                        bCheck = true;
                        break;
					}
				}
				if(!bCheck){
                    $(list[i]).append("<label style='color: red'>"+$(list[i]).attr("err-msg")+"</label>");
                    return false;
				}
			}
			return true;
        }

		//注册提交事件
		$('#whgwin-add-btn-save').on('click', submitFun);

		 //处理UE项的验证
         function validateUE(){
            var picture1 = $("#act_imgurl1").val();
            if (!picture1){
                $.messager.alert("错误", '图片不能为空', 'error');
                return false;
            }

            if (!remark.hasContents()){
                $.messager.alert("错误", '活动描述不能为空', 'error');
                return false;
            }
            return true;
        } 
		
		 //验证积分数
		function validateIntegral(){
			var val=$('input:radio[name="integral"]:checked').val();
			if(val==1){
				return true;
			}
			if(val == 2){
				var integralNum = $('#integralnum').numberspinner('getValue');
				if(integralNum == null || integralNum == ""){
					$.messager.alert("提示", '积分数必填！', 'error');
					return false;
				}
			}
			return true;
		}	 
		//验证在线活动售票数
	 	function validateTicket(){
            var val=$('input:radio[name="sellticket"]:checked').val();
            if(val == '1'){//不可预定不验证
                return true;
			}

			var seats = $('#seats').numberspinner('getValue');  
			var ticketNum = $('#ticketnum').numberspinner('getValue');
			if(val == '2'){
				if(seats == '' || ticketNum == ''){
					$.messager.alert("提示", '每人每场次购票数和每场售票数必填', 'error');
					return false;
				}else if(Number(seats) > Number(ticketNum)){
					$.messager.alert("错误", '每人每场次购票不能大于每场次售票', 'error');
					return false;
				}
			}
			if(val == '3'){
				if(seats == ''){
					$.messager.alert("提示", '每人每场次购票数必填', 'error');
					return false;
				}
			}
			return true;
		}
		
        $('#add').click(function(){
            var $div = $("<div class='ticket-item activityTimeLabel1' style='margin-top: 5px;'>");
            var $playstrtime = $("<input class='easyui-timespinner' style='width: 100px; height: 32px;' value='08:00' name='playstrtime'>");
            var $playendtime = $("<input class='easyui-timespinner' style='width: 100px; height: 32px;' value='10:00' name='playendtime'>");
            var $add = $("<a href='javascript:void(0)' id='cancel' style='margin-left:7px; '></a>");
            $add.html("取消");
            $add.on("click",function(){
                $(this).parent("div.activityTimeLabel1").remove();
            });
            $div.append($playstrtime);
            $div.append("- ");
            $div.append($playendtime);
            $div.append($add);
            $div.append("<label style='color: red;margin-left: 10px;display: none'>时间段填写不正确，不能与前时间段重合</label>");
            $div.appendTo("#put-ticket-list");
            $.parser.parse("#put-ticket-list");
        });
    });
	
	//是否消耗积分
	function isIntegral(){
		var val=$('input:radio[name="integral"]:checked').val();
		if(val == 2){
			$("#integralCount").show();
		}
		if(val == 1){
			$("#integralCount").hide();
		}
	}
	
	//是否在线售票
	function isCheck(){
		var val=$('input:radio[name="sellticket"]:checked').val();
		var valueBeforeSelect = $("#sellticketSelection").val();
		if(1 == val){
			$("#maxBuySeatCount").hide();
			$("#maxSoldSeatCount").hide();
            $("#onLineSeat").hide();
            $("#sellticketSelection").val(val);
		}
		if(2 == val){
            $("#maxBuySeatCount").show();
            $("#maxSoldSeatCount").show();
            $("#onLineSeat").hide();
            $("#sellticketSelection").val(val);
		}
		if(3 == val){
            if(rowNum == 0 || rowNum == undefined){
            	$.messager.alert("错误", '请选择有座位的活动室!', 'error');
                $("input:radio[name='sellticket'][value='" + valueBeforeSelect + "']").prop("checked", "checked");
                return;
            }
            $("#maxBuySeatCount").show();
            $("#maxSoldSeatCount").hide();
            $('.test-seatmaps').whgVenseatmaps({type: 'use'})
                .whgVenseatmaps('setSeatSize', rowNum,colNum)
                .whgVenseatmaps('setValue', JSON.parse(seatjson));
            $("#onLineSeat").show();
            $("#sellticketSelection").val(val);
		}

	}
	

    function checkTime() {
        var timeList = $("#put-ticket-list").children("div.activityTimeLabel1");
        var prePlaystrtime = false;
        for(var i = 0;i < timeList.length;i++){
            var item = timeList[i];
            var playstrtime = $(item).find("input[name='playstrtime']").val();
            var playendtime = $(item).find("input[name='playendtime']").val();
            if(2 == compareTime(playstrtime,playendtime,prePlaystrtime)){
                $(item).find("label").hide();
            }else{
                $(item).find("label").show();
                return false;
            }
            prePlaystrtime = playendtime;//记录前一个结束时间
        }
        return true;
    }

	function roomSelected(rec){
		//活动室onSelect事件时，隐藏在线选座
		$("#onLineSeat").css("display","none");
		rowNum = rec.seatrows;
		colNum = rec.seatcols;
		seatjson = rec.seatjson;
		//console.info(seatjson);
	}

    function compareTime(time1,time2, preTime1) {
	    if(!preTime1){
            preTime1 = time1;
        }
        var time10 = "2010-01-01 " + preTime1 + ":00";
        var time11 = "2010-01-01 " + time1 + ":30";
        var time22 = "2010-01-01 " + time2 + ":30";
        var date0 = WhgComm.parseDateTime(time10);//new Date(time11);
        var date1 = WhgComm.parseDateTime(time11);//new Date(time11);
        var date2 = WhgComm.parseDateTime(time22);//new Date(time22);
        if(date2 > date1 && date1 > date0){
            return 2;
        }else{
            return 1;
        }
    }
    
    //场馆改变，将场馆所对应的坐标与地址带出来
    function changeVen(){ 
		/* var venueid = $("#venueid").val(); */
		var venueid = $('#venueid').combobox('getValue'); 
		if(venueid !=null && venueid != ''){
			var url = getFullUrl('/admin/activity/act/changeVen?venueId='+venueid);
			$.ajax({  
		           url: url,  
		           type: "POST",  
		           success : function(data){  
		        	   if (!data) return;
		        	   $("#address").textbox('setValue', data.address);
		        	   $('#actlon').numberspinner('setValue', data.longitude);  
		        	   $('#actlat').numberspinner('setValue', data.latitude);
		           }, error : function() {
		                 alert("error");  
		           }  
		        });
		}
    }

</script>

</body>
</html>
