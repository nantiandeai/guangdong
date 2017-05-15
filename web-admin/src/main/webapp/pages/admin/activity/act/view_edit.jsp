<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<% request.setAttribute("basePath", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath());%>
<% request.setAttribute("resourceid", request.getParameter("rsid")); %>
<% String path = request.getContextPath();%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>活动管理-编辑活动资料</title>
    <%@include file="/pages/comm/admin/header.jsp"%>
    <link rel="stylesheet" href="${basePath}/static/admin/css/bootstrap.css"/>
    <link rel="stylesheet" href="${basePath}/static/admin/Font-Awesome/css/font-awesome.min.css"/>
    <link rel="stylesheet" href="${basePath}/static/admin/css/build.css"/>
    
    <script type="text/javascript" charset="utf-8"	src="<%=path%>/static/ueditor/ueditor.config.js"></script>
	<script type="text/javascript" charset="utf-8"	src="<%=path%>/static/ueditor/ueditor.all.min.js"></script>
	<script type="text/javascript" charset="utf-8"	src="<%=path%>/static/ueditor/lang/zh-cn/zh-cn.js"></script>
    
    <script type="text/javascript" src="${basePath}/static/plupload/lib/plupload-2.1.2/js/plupload.full.min.js"></script>
    <script type="text/javascript" src="${basePath}/static/plupload/upload-img.js"></script>
    <script type="text/javascript" src="${basePath}/static/plupload/upload-file.js"></script>
    <script src="${basePath}/static/admin/js/whgtoolmodule.js"></script>
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


<form id="whgff" class="whgff" method="post">
	<h2>活动管理-编辑活动</h2>

    <input type="hidden" name="id" value="${act.id}" >
    <input type="hidden" name="onlyshow" value="${param.onlyshow}" >

    <div class="whgff-row">
        <div class="whgff-row-label"><i>*</i>活动名称：</div>
        <div class="whgff-row-input"><input class="easyui-textbox" name="name" value="${act.name}" style="width:500px; height:32px" data-options="required:true, validType:'length[1,60]'"></div>
    </div>

	<div class="whgff-row">
		<div class="whgff-row-label"><i>*</i>活动简介：</div>
		<div class="whgff-row-input">
			<input class="easyui-textbox" name="actsummary" value="${act.actsummary}"  style="width:500px; height:32px" data-options="required:true,validType:['length[1,60]']">
		</div>
	</div>
    <div class="whgff-row">
        <div class="whgff-row-label"><i>*</i>活动封面：</div>
        <div class="whgff-row-input">
            <input type="hidden" id="act_imgurl1" name="imgurl" value="${act.imgurl}" >
            <div class="whgff-row-input-imgview" id="previewImg1"></div>
			<div class="whgff-row-input-imgfile" id="divImgFile">
				<i><a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" id="imgUploadBtn1">选择图片</a></i>
				<i>图片格式为jpg、png、gif，建议图片尺寸 750*500，大小为2MB以内</i>
			</div>
        </div>
    </div>
    
    <div class="whgff-row">
		 <div class="whgff-row-label"><i>*</i>区域：</div>
		 <div class="whgff-row-input">
            <div class="radio radio-primary whg-js-data"  name="areaid" value="${act.areaid}" js-data="WhgComm.getAreaType"></div>
		</div>
	</div>
	
   	<div class="whgff-row">
        <div class="whgff-row-label"><i>*</i>所属文化馆：</div>
        <div class="whgff-row-input">
            <select class="easyui-combobox" name="cultid" style="width:500px; height:32px"
                    data-options="editable:false, required:true, valueField:'id', textField:'text', value:'${act.cultid}', data:WhgComm.getCult(),prompt:'请选择文化馆'"></select>
        </div>
    </div>

	<div class="whgff-row">
		<div class="whgff-row-label">文化品牌：</div>
		<div class="whgff-row-input">
			<%--<div class="checkbox checkbox-primary whg-js-data" name="ebrand" value="${act.ebrand}"
					js-data="WhgComm.getBrand" >
			</div>--%>
			<div class="whgff-row-input">
				<div class="radio radio-primary whg-js-data"  name="ebrand" value="${act.ebrand}"  js-data="WhgComm.getBrand"></div>
			</div>
	</div>
	</div>
    
    <div class="whgff-row">
       <div class="whgff-row-label">场馆：</div>
       <div class="whgff-row-input">
       	<input class="easyui-combobox" name="venueid" id="venueid" panelHeight="auto" style="width:500px; height:32px" onchange="choseRomm()"
                  data-options="required:false,editable:true,limitToList:true,
                  valueField:'id', textField:'title', url:'${basePath}/admin/venue/srchList?state=6&delstate=0',
                  onLoadSuccess: function(){$(this).combobox('setValue','${act.venueid}')},
                  prompt:'请选择所属场馆',onChange : changeVen,
                  onSelect:function(rec){
                      $('[comboname=roomid]').combobox('clear');
                      $('[comboname=roomid]').combobox('reload', '${basePath}/admin/venue/room/srchList?state=6&delstate=0&&venid=' + rec.id);} "/>
       </div>
    </div>

	<div class="whgff-row">
		<div class="whgff-row-label">活动室：</div>
		<div class="whgff-row-input">
			<input class="easyui-combobox" name="roomid" style="width:500px; height:32px" id="roomid"
				   data-options="editable:true,limitToList:true,valueField:'id',textField:'title',onLoadSuccess: function(){$(this).combobox('setValue','${act.roomid}')},
                   prompt:'请选择所属活动室',onSelect:roomSelected"/>
		</div>
	</div>

    
    <div class="whgff-row">
		<div class="whgff-row-label">主办方：</div>
		<div class="whgff-row-input">
			<input class="easyui-textbox" name="host" value="${act.host }"
				style="width: 500px; height: 32px" data-options="required:false,validType:['length[0,20]']" />
		</div>
	</div>
	<div class="whgff-row">
		<div class="whgff-row-label">承办单位：</div>
		<div class="whgff-row-input">
			<input class="easyui-textbox" name="organizer" value="${act.organizer }"
				style="width: 500px; height: 32px" data-options="required:false,validType:['length[0,20]']" />
		</div>
	</div>
	
	<div class="whgff-row">
		<div class="whgff-row-label">协办单位：</div>
		<div class="whgff-row-input">
			<input class="easyui-textbox" name="coorganizer" value="${act.coorganizer }"
				style="width: 500px; height: 32px" data-options="required:false,validType:['length[0,20]']" />
		</div>
	</div>
		
	<div class="whgff-row">	
		<div class="whgff-row-label">演出单位：</div>
		<div class="whgff-row-input">
			<input class="easyui-textbox" name="performed" value="${act.performed }"
				style="width: 500px; height: 32px" data-options="required:false,validType:['length[0,20]']" />
		</div>
	</div>
	
	<div class="whgff-row">
		<div class="whgff-row-label">主讲人：</div>
		<div class="whgff-row-input">
			<input class="easyui-textbox" name="speaker" value="${act.speaker }"
				style="width: 500px; height: 32px" data-options="required:false,validType:['length[0,20]']" />
		</div>
	</div>
    
    <div class="whgff-row">
		 <div class="whgff-row-label"><i>*</i>活动分类：</div>
		 <div class="whgff-row-input">
            <div class="checkbox checkbox-primary whg-js-data"
				 name="etype"
				 value="${act.etype}"
				 js-data="WhgComm.getActivityType"
				 data-options="onLoadSuccess:function(){$(this).att('readonly','readonly')}"></div>
		</div>
	</div>
		
	<div class="whgff-row">
		<div class="whgff-row-label"><i>*</i>艺术分类：</div>
		<div class="whgff-row-input">
           <div class="checkbox checkbox-primary whg-js-data" name="arttype" value="${act.arttype}" js-data="WhgComm.getArtType"></div>
		</div>
	</div>

	<div class="whgff-row">
		<div class="whgff-row-label">标签：</div>
		<div class="whgff-row-input">
			<div class="checkbox checkbox-primary whg-js-data"name="etag" value="${act.etag}" js-data="WhgComm.getActivityTag"></div>
		</div>
	</div>
	
	<div class="whgff-row">
		<div class="whgff-row-label">关键字：</div>
		<div class="whgff-row-input">
	    	<%--<div class="checkbox checkbox-primary whg-js-data" name="ekey" value="${act.ekey}" js-data="WhgComm.getActivityKey"></div>--%>
			<input class="easyui-combobox" name="ekey" style="width:500px; height:32px" validType="notQuotes" data-options="multiple:true,editable:true,value:'${fn:replace(fn:replace(act.ekey,'"',"''"),"'","\\'")}',valueField:'text',textField:'text', data:WhgComm.getActivityKey()"/>
			<span>（如需手动输入，请用英文逗号隔开！）</span>
		</div>
	</div>
	
	<div class="whgff-row">
		<div class="whgff-row-label"><i>*</i>活动报名时间：</div>
		<div class="whgff-row-input">
			<input type="text" class="easyui-datetimebox"style="width: 240px; height: 32px;" id="enterstrtime" name="enterstrtime"  value="<fmt:formatDate value='${act.enterstrtime}' pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>" data-options="editable:false,required:true,prompt:'请选择'" /> ~
			<input type="text" class="easyui-datetimebox"style="width: 240px; height: 32px;" id="enterendtime" name="enterendtime"  value="<fmt:formatDate value='${act.enterendtime}' pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>" data-options="editable:false,required:true,prompt:'请选择',validType:'bmEndTime[\'enterstrtime\']'" />
		</div>
	</div>
	
	<div class="whgff-row">
		<div class="whgff-row-label"><i>*</i>活动日期：</div>
		<div class="whgff-row-input">
			<input type="text" class="easyui-datebox" style="width: 240px; height: 32px;" id="starttime" name="starttime" required="required" value="<fmt:formatDate value='${act.starttime}' pattern="yyyy-MM-dd"></fmt:formatDate>"  data-options="editable:false,required:true,readonly:true,prompt:'请选择'" /> ~
			<input type="text" class="easyui-datebox" style="width: 240px; height: 32px;" id="endtime" name="endtime"  required="required" value="<fmt:formatDate value='${act.endtime}' pattern="yyyy-MM-dd"></fmt:formatDate>"  data-options="editable:false,prompt:'请选择',readonly:true,validType:'hdEndDate[\'starttime\']'" />
		</div>
	</div>
		
	<div class="whgff-row">
		<div class="whgff-row-label"><i>*</i>地址：</div>
		<div class="whgff-row-input">
			<input class="easyui-textbox" name="address" id="address" value="${act.address}" style="width: 500px; height: 32px" data-options="required:true,validType:['length[0,60]']">
		</div>
	</div>

	<div class="whgff-row">
		<div class="whgff-row-label"><i>*</i>坐标：</div>
		<div class="whgff-row-input">
			<input class="easyui-numberbox" name="actlon" id="actlon" value="${act.actlon}" style="width: 100px; height: 32px" data-options="required:false, precision:6,readonly:true,prompt:'X轴'"> 
			<input class="easyui-numberbox" name="actlat" id="actlat" value="${act.actlat}"  style="width: 100px; height: 32px" data-options="required:false, precision:6,readonly:true,prompt:'Y轴'"> 
			<a class="easyui-linkbutton whg-maps" map-addr="#address" vm-x="actlon" vm-y="actlat" id="getXYPointBtn" text="选择坐标"></a>
		</div>
	</div>

    <div class="whgff-row">
        <div class="whgff-row-label"><label style="color: red">*</label>联系电话：</div>
        <div class="whgff-row-input"><input class="easyui-textbox" name="telphone" value="${act.telphone}" style="width:300px; height:32px" data-options="prompt:'请输入联系人手机', required:true, validType:'isPhone'"></div>
    </div>
    
    <div class="whgff-row">
		<div class="whgff-row-label">
			<label style="color: red">*</label>是否消耗积分：
		</div>
		<div class="whgff-row-input">
	    	<div class="radio radio-primary whg-js-data" name="integral" id="integral" value="${act.integral }"  onclick="isIntegral()" 
                js-data='[{"id":"1","text":"默认"},{"id":"2","text":"积分"}]'>
           </div>
           <span id="integralCount" style="display:none">该活动需要消耗积分<input class="easyui-numberspinner" name="integralnum" value="${act.integralnum }" id="integralnum" style="width: 50px; height: 25px" data-options="required:false,min:0,max:999">分 </span>
		</div>
	</div>
	
	<div class="whgff-row">
		<div class="whgff-row-label">
			<label style="color: red">*</label>是否收费：
		</div>
		<div class="whgff-row-input">
	    	<div class="radio radio-primary whg-js-data" name="hasfees" id="hasfees"   value="${act.hasfees }"
                js-data='[{"id":"0","text":"否"},{"id":"1","text":"是"}]'>
           </div>
		</div>
	</div>
	
	<div class="whgff-row">
		<div class="whgff-row-label"><i>*</i>活动类型：</div>
		<div class="whgff-row-input">
	    	<div class="radio radio-primary whg-js-data" name="sellticket" id="sellticket" value="${act.sellticket }"  onclick="setSeat()" 
                js-data='[{"id":"1","text":"不可预定"},{"id":"2","text":"自由入座"},{"id":"3","text":"在线选座"}]'>
                <input id="sellticketSelection" type="hidden" value="1">
           </div>
			<span id="maxBuySeatCount" style="display:none">每人每场最多购票<input class="easyui-textbox" name="seats" id="seats" style="width: 50px; height: 25px" >张 </span>
			<span id="maxSoldSeatCount" style="display:none">每场次最多售票<input class="easyui-textbox" name="ticketnum" id="ticketnum" style="width: 50px; height: 25px">张 </span>
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
			<script id="remark" name="remark" id="remark" type="text/plain" style="width: 800px; height: 600px;">${act.remark}</script>
		</div>
	</div>
	
	<%--<div class="whgff-row">--%>
		<%--<div class="whgff-row-label">--%>
			<%--上传附件：--%>
		<%--</div>--%>
		<%--<div class="whgff-row-input">--%>
			<%--<input  id="act_filepath1" name="filepath" value="${act.filepath }" data-options="required:true" style="width:600px;height:32px;" readonly="readonly">--%>
			<%--<div class="whgff-row-input-file" id="filepath">--%>
                <%--<i><a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" id="fileUploadBtn1">选择文件</a></i>--%>
                <%--<i>附件格式为doc,docx,xls,zip,xlsx，建议图文件大小为10MB以内</i>--%>
            <%--</div>--%>
		<%--</div>--%>
	<%--</div>--%>
	<div class="whgff-row">
		<div class="whgff-row-label">上传附件：</div>
		<div class="whgff-row-input">
			<input type="hidden" id="whg_file_upload" name="filepath" value="${act.filepath}">
			<div class="whgff-row-input-fileview" id="whg_file_pload_view"></div>
			<div class="whgff-row-input-filefile">
				<i><a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" id="fileUploadBtn2">选择附件</a></i>
				<i>附件格式为doc,docx,xls,xlsx,zip,pdf,建议文件大小为10MB以内</i>
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

	var seatmap;
	//定义座位变量
	var rowNum = 0;
	var colNum = 0;
	var seatjson = '';
    $(function () {
    	var state = '${act.state}';
    	if(state == 6 || state == 4){
    		$("#venueid").combobox({disabled:true});
    		$("#roomid").combobox({disabled:true});
    		$('#enterstrtime').datetimebox({ disabled: true});
    		$('#enterendtime').datetimebox({ disabled: true});
    		$('#whgff').find("input[type='radio'][name='sellticket']").attr('disabled', true);
    		$('#whgff').find("input[type='radio'][name='integral']").attr('disabled', true);
    	}
    	
        //var seatmap = $("div.whgff-row-map");
        if (!seatmap) {
            seatmap = $('.test-seatmaps').whgVenseatmaps({type: 'use'});
        }
      	//图片初始化
        var whgImg = WhgUploadImg.init({basePath: '${basePath}', uploadBtnId: 'imgUploadBtn1', hiddenFieldId: 'act_imgurl1', previewImgId: 'previewImg1'});
      	//文件上传初始化
        <%--WhgUploadFile.init({basePath: '${basePath}', uploadBtnId: 'fileUploadBtn1', hiddenFieldId: 'act_filepath1'});--%>
		WhgUploadFile.init({basePath: '${basePath}', uploadBtnId: 'fileUploadBtn2', hiddenFieldId: 'whg_file_upload',previewFileId:'whg_file_pload_view'});

		//根据地址取坐标
        WhgMap.init({basePath:'${basePath}', addrFieldId:'address', xpointFieldId:'actlon', ypointFieldId:'actlat', getPointBtnId:'getXYPointBtn'});
		
		//初始化富文本
	    var ueConfig = {
	        scaleEnabled: false,
	        autoFloatEnabled: false,
            elementPathEnabled:false
	    };
		var remark = UE.getEditor('remark', ueConfig);
        
        
		//提交事件
		function submitFun() {
            if(!supplement()){
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
      	
        //初始化富文本
	   	var ue_remark =  UE.getEditor('remark');
        $('#whgff').form({
            novalidate: true,
            url : getFullUrl('/admin/activity/act/edit'),
            onSubmit : function(param) {
                var seatsValue = seatmap.whgVenseatmaps('getValue');
                param.seatjson = JSON.stringify(seatsValue);
                //在线选座，自由选座按钮恢复不可用
                $("input[name='sellticket']").attr('disabled', false);
                var _valid = $(this).form('enableValidation').form('validate')
                if(_valid){
                    $.messager.progress();
                }else{
                    //失败时再注册提交事件
                    $('#whgwin-edit-btn-save').off('click').one('click',submitFun);
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
                    $.messager.alert('提示', '操作失败:'+Json.errormsg+'!', 'error');
                    $('#whgwin-add-btn-save').off('click').one('click', submitFun);
                }
            }
        });
        //注册提交事件
        $('#whgwin-edit-btn-save').on('click', submitFun);
        var whgSeat = JSON.parse('${whgSeat}'); 
        
        setSeat(whgSeat);
        isIntegral();
    });
    
    function roomSelected(rec){
		//活动室onSelect事件时，隐藏在线选座
		$("#onLineSeat").css("display","none");
		rowNum = rec.seatrows;
		colNum = rec.seatcols;
		seatjson = rec.seatjson? JSON.parse(rec.seatjson) : [];
		//console.info(seatjson);
		var whgSeat = ''; 
		if(rec.id == '${act.roomid}' ){
			whgSeat = JSON.parse('${whgSeat}'); 
		}else{
			whgSeat = '';
		}
		
		setSeat(whgSeat);
	}

    function  setSeat(whgSeat) {
        var sellticket=$('input:radio[name="sellticket"]:checked').val();
		var valueBeforeSelect = $("#sellticketSelection").val();
        if("1" == sellticket){
            $("#maxBuySeatCount").hide();
            $("#maxSoldSeatCount").hide();
            $("#onLineSeat").hide();
            return;
        }
        if("2" == sellticket){
            var ticketnum = "${act.ticketnum}";
            var seats = "${act.seats}";
            ticketnum = ("" != ticketnum?ticketnum:0);
            seats = ("" != seats?seats:0);

            $("#maxBuySeatCount").find("input").val(seats);
            $("#maxSoldSeatCount").find("input").val(ticketnum);
            $("#maxBuySeatCount").show();
            $("#maxSoldSeatCount").show();
            $("#onLineSeat").hide();
            return;
        }
        if("3" == sellticket){
            $("#maxBuySeatCount").show();
			var seats = "${act.seats}";
			$("#maxBuySeatCount").find("input").val(seats);
            $("#maxSoldSeatCount").hide();
            var myWhgSeat = whgSeat; 
            if(myWhgSeat != null && myWhgSeat != ''){
            	rowNum = myWhgSeat.rowNum;
            	colNum = myWhgSeat.colNum;
            	seatjson = myWhgSeat.mySeatMap;
            }
            if(rowNum == 0 || rowNum == undefined){
            	
            	$.messager.alert("错误", '请选择有座位的活动室!', 'error');
                $("input:radio[name='sellticket'][value='" + valueBeforeSelect + "']").prop("checked", "checked");
                $("#maxBuySeatCount").hide();
                return;
            }

            if (!seatmap){
                seatmap = $('.test-seatmaps').whgVenseatmaps({type: 'use'})
            }
            seatmap.whgVenseatmaps('setSeatSize', rowNum,colNum)
                .whgVenseatmaps('setValue', seatjson);
            $("#onLineSeat").show();
            return;
        }
    }
    
    //场馆改变，将场馆所对应的坐标与地址带出来
    function changeVen(){ 
		/* var venueid = $("#venueid").val(); */
		var venueid = $('#venueid').combobox('getValue'); 
		var address = $("#address").textbox('getValue');
		if(venueid !=null && venueid != ''){
			var url = getFullUrl('/admin/activity/act/changeVen?venueId='+venueid);
			$.ajax({  
		           url: url,  
		           type: "POST",  
		           success : function(data){  
		        	   if (!data) return;
		        	   if(address == null || address == ''){
		        		   $("#address").textbox('setValue', data.address);
		        	   }
		        	   $('#actlon').numberspinner('setValue', data.longitude);  
		        	   $('#actlat').numberspinner('setValue', data.latitude);
		           },  
		            error : function() {    
		                 alert("error");  
		           }  
		        });
		}
    }
</script>
<!-- script END -->
</body>
</html>