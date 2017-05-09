<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
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
    <script src="${basePath}/static/admin/js/whgtoolmodule.js"></script>
    <script type="text/javascript" src="${basePath}/static/common/js/whg.maps.js"></script>
    <script src="${basePath}/static/admin/js/whgmodule-venseatmaps.js"></script>
</head>
<body>


<form id="whgff" class="whgff" method="post">
    <h2>活动管理-查看活动</h2>

    <input type="hidden" name="id" value="${act.id}" >
    <input type="hidden" name="onlyshow" value="${param.onlyshow}" >

    <div class="whgff-row">
        <div class="whgff-row-label">名称：</div>
        <div class="whgff-row-input"><input class="easyui-textbox" name="name" value="${act.name}" style="width:500px; height:32px" data-options="required:true, validType:'length[3,30]'"></div>
    </div>
    <div class="whgff-row">
		<div class="whgff-row-label">活动简介：</div>
			<input class="easyui-textbox" name="actsummary" value="${act.actsummary}"  style="width:500px; height:32px" data-options="required:true,validType:['length[1,400]']">
	</div>
    <div class="whgff-row">
        <div class="whgff-row-label">活动封面：</div>
        <div class="whgff-row-input">
            <input type="hidden" id="act_imgurl1" name="imgurl" value="${act.imgurl}" >
            <div class="whgff-row-input-imgview" id="previewImg1"></div>
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label">
            	区域：
        </div>
        <div class="whgff-row-input">
            <div class="radio radio-primary whg-js-data" name="areaid"  value="${act.areaid}"  js-data="WhgComm.getAreaType"></div>
        </div>
    </div>
    
   	<div class="whgff-row">
       <div class="whgff-row-label">所属文化馆：</div>
       <div class="whgff-row-input">
           <select class="easyui-combobox" name="cultid" style="width:500px; height:32px"
                   data-options="editable:false, required:true, valueField:'id', textField:'text', value:'${act.cultid}', data:WhgComm.getCult(),prompt:'请选择文化馆'"></select>
       </div>
    </div> 

    <div class="whgff-row">
        <div class="whgff-row-label">场馆：</div>
        <div class="whgff-row-input">
            <input class="easyui-combobox" name="venueid" id="venueid" panelHeight="auto" limitToList="true" style="width:500px; height:32px" onchange="choseRomm()"
                   data-options="required:false, editable:false,multiple:false, mode:'remote',
                  valueField:'id', textField:'title',
                  url:'${basePath}/admin/venue/srchList?state=6&delstate=0',
                  onLoadSuccess: function(){$(this).combobox('setValue','${act.venueid}');},
                  prompt:'请选择所属场馆',
                  onSelect:function(rec){
                      $('[comboname=roomid]').combobox('clear');
                      $('[comboname=roomid]').combobox('reload', '${basePath}/admin/venue/room/srchList?state=6&delstate=0&&venid=' + rec.id);} "/>
        </div>
        <script type="text/javascript">
            $("#venueid").attr('readonly','readonly');
        </script>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label">活动：</div>
        <div class="whgff-row-input">
            <input class="easyui-combobox" name="roomid" style="width:500px; height:32px" id="roomid"
                   data-options="editable:false,
                   multiple:false,
                   valueField:'id',
                   textField:'title',
                   mode:'remote',
                   onLoadSuccess: function(){$(this).combobox('setValue','${act.roomid}');},
                   prompt:'请选择所属活动室'"/>
        </div>
        <script type="text/javascript">
            $("#roomid").attr('readonly','readonly');
        </script>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label">品牌：</div>
        <div class="whgff-row-input">
            <div class="checkbox checkbox-primary whg-js-data"  name="ebrand"  value="${act.ebrand}" js-data="WhgComm.getBrand"></div>
        </div>
        <script type="text/javascript">
            $("#ebrand").attr("readonly","readonly");
        </script>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label">主办方：</div>
        <div class="whgff-row-input">
            <input class="easyui-textbox" name="host" value="${act.host }"
                   style="width: 500px; height: 32px" data-options="required:false" />
        </div>
    </div>
    <div class="whgff-row">
        <div class="whgff-row-label">承办单位：</div>
        <div class="whgff-row-input">
            <input class="easyui-textbox" name="organizer" value="${act.organizer }"
                   style="width: 500px; height: 32px" data-options="required:false" />
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label">协办单位：</div>
        <div class="whgff-row-input">
            <input class="easyui-textbox" name="coorganizer" value="${act.coorganizer }"
                   style="width: 500px; height: 32px" data-options="required:false" />
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label">演出单位：</div>
        <div class="whgff-row-input">
            <input class="easyui-textbox" name="performed" value="${act.performed }"
                   style="width: 500px; height: 32px" data-options="required:false" />
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label">主讲人：</div>
        <div class="whgff-row-input">
            <input class="easyui-textbox" name="speaker" value="${act.speaker }"
                   style="width: 500px; height: 32px" data-options="required:false" />
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label">
            分类：
        </div>
        <div class="whgff-row-input">
            <div class="checkbox checkbox-primary whg-js-data"
                 name="etype"
                 value="${act.etype}"
                 js-data="WhgComm.getActivityType"
                 data-options="onLoadSuccess:function(){$(this).att('readonly','readonly')}"></div>
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label">
            标签：
        </div>
        <div class="whgff-row-input">
            <div class="checkbox checkbox-primary whg-js-data"name="etag" value="${act.etag}" js-data="WhgComm.getActivityTag"></div>
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label">
            	艺术分类：
        </div>
        <div class="whgff-row-input">
            <div class="checkbox checkbox-primary whg-js-data" name="arttype" value="${act.arttype}" js-data="WhgComm.getArtType"></div>
        </div>
    </div>
    
    <div class="whgff-row">
		<div class="whgff-row-label">
			关键字：
		</div>
		<div class="whgff-row-input">
	    	<div class="checkbox checkbox-primary whg-js-data" name="ekey" value="${act.ekey}" js-data="WhgComm.getActivityKey"></div>
		</div>
	</div>

    <div class="whgff-row">
		<div class="whgff-row-label">
			活动报名时间：
		</div>
		<div class="whgff-row-input">
			<input type="text" class="easyui-datetimebox"style="width: 240px; height: 32px;" name="enterstrtime"  value="<fmt:formatDate value='${act.enterstrtime}' pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>" required="required" data-options="prompt:'请选择'" /> ~ 
			<input type="text" class="easyui-datetimebox"style="width: 240px; height: 32px;" name="enterendtime"  value="<fmt:formatDate value='${act.enterendtime}' pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>" required="required" data-options="prompt:'请选择'" />
		</div>
	</div>

   
	<div class="whgff-row">
		<div class="whgff-row-label">
			活动日期：
		</div>
		<div class="whgff-row-input">
			<input type="text" class="easyui-datebox" style="width: 240px; height: 32px;" name="starttime" required="required" value="<fmt:formatDate value='${act.starttime}' pattern="yyyy-MM-dd"></fmt:formatDate>"  data-options="editable:false,prompt:'请选择'" /> ~ 
			<input type="text" class="easyui-datebox" style="width: 240px; height: 32px;" name="endtime"  required="required" value="<fmt:formatDate value='${act.endtime}' pattern="yyyy-MM-dd"></fmt:formatDate>"  data-options="editable:false,prompt:'请选择'" />
		</div>
	</div>


    <div class="whgff-row">
        <div class="whgff-row-label">
            	地址：
        </div>
        <div class="whgff-row-input">
            <input class="easyui-textbox" name="address" id="address" value="${act.address}" style="width: 500px; height: 32px" data-options="required:true">
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label">
            	坐标：
        </div>
        <div class="whgff-row-input">
            <input class="easyui-numberbox" name="actlon" id="actlon" value="${act.actlon}" style="width: 100px; height: 32px" data-options="required:false, precision:6,readonly:true,prompt:'X轴'">
            <input class="easyui-numberbox" name="actlat" id="actlat" value="${act.actlat}"  style="width: 100px; height: 32px" data-options="required:false, precision:6,readonly:true,prompt:'Y轴'">
            <a class="easyui-linkbutton whg-maps" map-addr="#address" vm-x="actlon" vm-y="actlat" id="getXYPointBtn" text="选择坐标"></a>
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label">活动电话：</div>
        <div class="whgff-row-input"><input class="easyui-textbox" name="telphone" value="${act.telphone}" style="width:300px; height:32px" ></div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label">
            	是否消耗积分：
        </div>
        <div class="whgff-row-input">
            <div class="radio radio-primary whg-js-data" name="integral" value="${act.integral }"
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
        <div class="whgff-row-label">
            	在线售票：
        </div>
        <div class="whgff-row-input">
            <div class="radio radio-primary whg-js-data" name="sellticket" value="${act.sellticket }"
                 js-data='[{"id":"1","text":"不可预定"},{"id":"2","text":"自由入座"},{"id":"3","text":"在线选座"}]'>
            </div>
            <span id="maxBuySeatCount" style="display:none">每人每场最多购票<input class="easyui-textbox" name="seats" style="width: 50px; height: 25px" disabled="disabled">张 </span>
            <span id="maxSoldSeatCount" style="display:none">每场次最多售票<input class="easyui-textbox" name="ticketnum" style="width: 50px; height: 25px" disabled="disabled">张 </span>
        </div>
    </div>
    <div class="whgff-row" style="display: none" id="onLineSeat">
        <div class="whgff-row-map test-seatmaps">
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label">
            	活动描述：
        </div>
        <div class="whgff-row-input">
            <script id="remark" name="remark" type="text/plain" style="width: 600px; height: 300px;">${act.remark}</script>
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
    $(function () {
        //图片初始化
        WhgUploadImg.init({basePath: '${basePath}', uploadBtnId: 'imgUploadBtn1', hiddenFieldId: 'act_imgurl1', previewImgId: 'previewImg1'});

        //根据地址取坐标
        WhgMap.init({basePath:'${basePath}', addrFieldId:'address', xpointFieldId:'actlon', ypointFieldId:'actlat', getPointBtnId:'getXYPointBtn'});

        //初始化富文本
        var ue_remark =  UE.getEditor('remark');

        //查看状态下表单元素不能编辑
        if($('#whgff input[name="onlyshow"]').val() == "1"){
            $('#whgff input').attr('readonly', 'readonly');
            $('#whgff textarea').attr('readonly', 'readonly');
        }
        setSeat();
        isIntegral();
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

    function  setSeat() {
        var sellticket = ${act.sellticket};
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
            var myWhgSeat = JSON.parse('${whgSeat}');

            //console.info(myWhgSeat);
             $('.test-seatmaps').whgVenseatmaps({type: 'show'})
             .whgVenseatmaps('setSeatSize', myWhgSeat.rowNum,myWhgSeat.colNum)
             .whgVenseatmaps('setValue', myWhgSeat.mySeatMap);
            $("#onLineSeat").show();
            return;
        }
    }
</script>
<!-- script END -->
</body>
</html>