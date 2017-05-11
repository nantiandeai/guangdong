<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<% request.setAttribute("basePath", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath());%>
<% request.setAttribute("resourceid", request.getParameter("rsid")); %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <c:choose>
        <c:when test="${not empty id and not empty targetShow}">
            <c:set var="pageTitle" value="培训驿站-查看培训"></c:set>
        </c:when>
        <c:when test="${not empty id}">
            <c:set var="pageTitle" value="培训驿站-编辑培训"></c:set>
        </c:when>
        <c:otherwise>
            <c:set var="pageTitle" value="培训驿站-添加培训"></c:set>
        </c:otherwise>
    </c:choose>
    <title>${pageTitle}</title>
    <%@include file="/pages/comm/admin/header.jsp"%>

    <link rel="stylesheet" href="${basePath}/static/admin/css/bootstrap.css"/>
    <link rel="stylesheet" href="${basePath}/static/admin/Font-Awesome/css/font-awesome.min.css"/>
    <link rel="stylesheet" href="${basePath}/static/admin/css/build.css"/>

    <script src="${basePath}/static/ueditor/ueditor.config.js"></script>
    <script src="${basePath}/static/ueditor/ueditor.all.min.js"></script>
    <script src="${basePath}/static/ueditor/lang/zh-cn/zh-cn.js"></script>

    <script src="${basePath}/static/admin/js/whgtoolmodule.js"></script>

    <!-- 图片上传相关 -->
    <script type="text/javascript" src="${basePath}/static/plupload/lib/plupload-2.1.2/js/plupload.full.min.js"></script>
    <script type="text/javascript" src="${basePath}/static/plupload/upload-img.js"></script>
    <!-- 图片上传相关-END -->

    <!-- 根据地址取坐标 -->
    <script type="text/javascript" src="${basePath}/static/common/js/whg.maps.js"></script>

    <style>
        .slider-h{margin-left: 10px}
    </style>
    <script>
        IDUtil = (function(){
            var __id = 100;

            function _getId(){
                return __id++;
            }

            return {
                getId: function(){
                    return _getId();
                }
            }
        })();

        $.extend($.fn.validatebox.defaults.rules, {
            traTime: {
                validator: function(value, param){
                    var _valid = false;
                    var _type = param[0];//0-starttime 1-endtime
                    var _refid = param[1];//0-starttime 1-endtime
                    var _stime = $('#starttime').datebox('getValue');
                    var _etime = $('#endtime').datebox('getValue');
                    var _ptime = false;

                    if(_type === 0){//0-starttime
                        var pDiv = $('#'+_refid).parents('div[name="multiContent"]');
                        var preDiv = pDiv.prev();
                        if(preDiv.is('[name="multiContent"]')){
                            var preEndTimeEl = preDiv.find('._endtime');
                            _ptime = preEndTimeEl.datetimebox('getValue');
                        }
                    }else if(_type === 1){//1-endtime
                        _ptime = $('#'+_refid).datetimebox('getValue');
                    }

                    //value in _stime,_etime
                    //value > _ptime
                    var t_value = WhgComm.parseDateTime(value);
                    var t__stime = WhgComm.parseDate(_stime);
                    var t__etime = WhgComm.parseDateTime(_etime+" 23:59:59");

                    if( t_value.getTime() > t__stime.getTime() && t_value.getTime() < t__etime.getTime() ){
                        if(_ptime){
                            var t_ptime = WhgComm.parseDateTime(_ptime);
                            if(t_value.getTime() > t_ptime.getTime()){
                                _valid = true;
                            }
                        }else{
                            _valid = true;
                        }
                    }
                    return _valid;
                },
                message: '培训课时必须在培训周期之内，结束时间大于开始时间，各培训课时段不能重合。'
            }
        });
    </script>

</head>
<body>
<form id="whgff" method="post" class="whgff">
    <h2>${pageTitle}</h2>

    <div class="whgff-row">
        <div class="whgff-row-label"><label style="color: red">*</label>标题：</div>
        <div class="whgff-row-input">
            <input class="easyui-textbox" name="title" value="${whgTra.title}" style="width:500px; height:32px"
                   data-options="required:true,validType:['length[1,60]'],prompt:'请输入标题'">
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label"><i>*</i>图片：</div>
        <div class="whgff-row-input">
            <input type="hidden" id="cult_picture1" name="trainimg" value="${whgTra.trainimg}">
            <div class="whgff-row-input-imgview" id="previewImg1"></div>
            <div class="whgff-row-input-imgfile">
                <i><a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" id="imgUploadBtn1">选择图片</a></i>
                <i>图片格式为jpg、png、gif，建议图片尺寸 750*500，大小为2MB以内</i>
            </div>
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label"><label style="color: red">*</label>所属单位：</div>
        <div class="whgff-row-input">
            <select class="easyui-combobox" name="cultid" style="width:500px; height:32px"
                    data-options="editable:false, required:true, valueField:'id', textField:'text', value:'${whgTra.cultid}', data:WhgComm.getCult(),prompt:'请选择所属单位'"></select>
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label"><label style="color: red"></label>所在场馆：</div>
        <div class="whgff-row-input">
            <input class="easyui-combobox" name="venue" style="width:500px; height:32px" value="${whgTra.venue}"
                    data-options="editable:true, multiple:false,limitToList:true, mode:'remote', valueField:'id', textField:'title', url:'${basePath}/admin/venue/srchList?state=6&delstate=0',
                    prompt:'请选择所属场馆',
                     onChange:function(venue){
                        $('#venroom').combobox({
                            valueField:'id', //值字段
                            textField:'title', //显示的字段
                            url:'${basePath}/admin/venue/room/srchList?state=6&delstate=0&venid='+venue,
                            panelHeight:'auto',
                            editable:false,
                            value:''
                        });
                    }
                    ">
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label"><label style="color: red"></label>所在活动室：</div>
        <div class="whgff-row-input">
            <input class="easyui-combobox" id="venroom" name="venroom" value="${whgTra.venroom}" style="width:500px; height:32px"
                    data-options="editable:true, multiple:false,limitToList:true, valueField:'id', textField:'title', url:'${basePath}/admin/venue/room/srchList?state=6&delstate=0&venid=${whgTra.venue}',
                        prompt:'请选择活动室',
                        panelHeight:'auto',
                        editable:false
                    ">
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label"><label style="color: red"></label>文化品牌：</div>
        <div class="whgff-row-input">
            <select class="easyui-combobox" name="ebrand" style="width:500px; height:32px"
                    data-options="editable:false,multiple:true, valueField:'id', textField:'text', value:'${whgTra.ebrand}', data:WhgComm.getBrand(),prompt:'请选择文化品牌'">
            </select>
        </div>
    </div>
    <div class="whgff-row">
        <div class="whgff-row-label"><label style="color: red"></label>联系电话：</div>
        <div class="whgff-row-input">
            <input class="easyui-textbox" name="phone" value="${whgTra.phone}" style="width:500px; height:32px" data-options="validType:'isPhone[\'traphone\']',prompt:'请填写联系电话'">
        </div>
    </div>
    <div class="whgff-row">
        <div class="whgff-row-label"><label style="color: red">*</label>区域：</div>
        <div class="whgff-row-input">
            <div class="radio radio-primary whg-js-data" name="area" value="${whgTra.area}"
                 js-data="WhgComm.getAreaType">
            </div>
            <%--<select class="easyui-combobox" name="area" style="width:500px; height:32px"
                    data-options="editable:false, required:true, valueField:'id', textField:'text', value:'${whgTra.area}', data:WhgComm.getAreaType(),prompt:'请选择区域'"></select>--%>
        </div>
    </div>





    <div class="whgff-row">
        <div class="whgff-row-label"><label style="color: red">*</label>艺术分类：</div>
        <div class="whgff-row-input">
            <div class="checkbox checkbox-primary whg-js-data" value="${whgTra.arttype}" name="arttype" js-data="WhgComm.getArtType"></div>
        </div>
    </div>
    <div class="whgff-row">
        <div class="whgff-row-label"><label style="color: red"></label>标签：</div>
        <div class="whgff-row-input">
            <div class="checkbox checkbox-primary whg-js-data" value="${whgTra.etag}" name="etag" js-data="WhgComm.getTrainTag"></div>
        </div>
    </div>
    <div class="whgff-row">
        <div class="whgff-row-label"><label style="color: red"></label>关键字：</div>
        <div class="whgff-row-input">
            <%--<div class="checkbox checkbox-primary whg-js-data" value="${whgTra.ekey}" name="ekey" js-data="WhgComm.getTrainKey"></div>--%>
            <input class="easyui-combobox" name="ekey" value="${whgTra.ekey}" style="width:500px; height:32px" data-options="multiple:true,editable:true,valueField:'text',textField:'text', data:WhgComm.getTrainKey(),prompt:'请填写关键字'"/>
            <span>（如需手动输入，请用英文逗号隔开！）</span>
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label"><label style="color: red">*</label>地址：</div>
        <div class="whgff-row-input">
            <input class="easyui-textbox" id="address" name="address" value="${whgTra.address}" style="width:500px; height:32px" data-options="required:true,validType:['length[1,200]'],prompt:'请填写地址'">
        </div>
    </div>
    <div class="whgff-row">
        <div class="whgff-row-label"><label style="color: red">*</label>坐标：</div>
        <div class="whgff-row-input">
            <input class="easyui-numberbox" id="longitude" name="longitude" value="${whgTra.longitude}" style="width:100px; height:32px" data-options="required:false,precision:6,readonly:true,prompt:'X轴'">
            <input class="easyui-numberbox" id="latitude" name="latitude" value="${whgTra.latitude}" style="width:100px; height:32px" data-options="required:false,precision:6,readonly:true,prompt:'y轴'">
            <a class="easyui-linkbutton whg-maps" map-addr="#address" vm-x="#longitude" vm-y="#latitude" id="getXYPointBtn" text="选择坐标"></a>
        </div>
    </div>
    <div class="whgff-row">
        <div class="whgff-row-label"><label style="color: red"></label>培训老师：</div>
        <div class="whgff-row-input">
            <input class="easyui-combobox" name="teacherid" value="${whgTra.teacherid}" style="width:500px; height:32px" data-options="multiple:true,editable:true,valueField:'teacherid',textField:'teachername',url:'${basePath}/admin/trainManage/findTeacher'"/>
            <span>（如需手动输入，请用英文逗号隔开！）</span>
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label"><label style="color: red">*</label>报名人数上限：</div>
        <div class="whgff-row-input">
            <input class="easyui-numberspinner" name="maxnumber" value="${whgTra.maxnumber}" style="width:500px; height:32px" data-options="min:0,required:true,prompt:'请输入人数'">
        </div>
    </div>
    <div class="whgff-row">
        <div class="whgff-row-label"><label style="color: red">*</label>基础报名人数：</div>
        <div class="whgff-row-input">
            <input class="easyui-numberspinner" name="basicenrollnumber" value="${whgTra.basicenrollnumber}" style="width:500px; height:32px" data-options="min:0,required:true,prompt:'请输入人数'">
        </div>
    </div>
    <div class="whgff-row">
        <div class="whgff-row-label"><label style="color: red">*</label>是否显示最大报名人数：</div>
        <div class="whgff-row-input">
            <div class="radio radio-primary whg-js-data" value="${whgTra.isshowmaxnumber}" name="isshowmaxnumber" js-data='[{"id":"0","text":"否"},{"id":"1","text":"是"}]'></div>
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label"><label style="color: red">*</label>培训分类：</div>
        <div class="whgff-row-input">
            <div class="radio radio-primary whg-js-data" value="${whgTra.etype}" name="etype" js-data="WhgComm.getTrainType"></div>
        </div>
    </div>
    <div class="whgff-row">
        <div class="whgff-row-label"><label style="color: red">*</label>是否收费：</div>
        <div class="whgff-row-input">
            <div class="radio radio-primary whg-js-data" value="${whgTra.ismoney}" name="ismoney" js-data='[{"id":"0","text":"否"},{"id":"1","text":"是"}]'></div>
        </div>
    </div>
    <div class="whgff-row">
        <div class="whgff-row-label"><label style="color: red"></label>适合年龄：</div>
        <div class="whgff-row-input">
            <input class="easyui-numberspinner" id="age1" name="age1" value="${age1}" style="width:80px;" data-options="min:1,max:100, editable:true">&nbsp;&nbsp;&nbsp;至&nbsp;&nbsp;&nbsp;
            <input class="easyui-numberspinner" id="age2" name="age2" value="${age2}" style="width:80px;" data-options="min:1,max:100, editable:true, validType:'_validage[\'age1\']'">岁
        </div>
    </div>
    <div class="whgff-row">
        <div class="whgff-row-label"><label style="color: red">*</label>实名制：</div>
        <div class="whgff-row-input">
            <div class="radio radio-primary whg-js-data" value="${whgTra.isrealname}" name="isrealname" js-data='[{"id":"0","text":"否"},{"id":"1","text":"是"}]'></div>
        </div>
    </div>
    <div class="whgff-row">
        <div class="whgff-row-label"><label style="color: red">*</label>学期制：</div>
        <div class="whgff-row-input">
            <div class="radio radio-primary whg-js-data" value="${whgTra.isterm}"  name="isterm" js-data='[{"id":"0","text":"否"},{"id":"1","text":"是"}]'></div>
        </div>
    </div>
    <div class="whgff-row">
        <div class="whgff-row-label"><label style="color: red">*</label>录取规则：</div>
        <div class="whgff-row-input">
            <div class="radio radio-primary whg-js-data" value="${whgTra.isbasicclass}"  name="isbasicclass" js-data='[{"id":"0","text":"需要面试"},{"id":"1","text":"需人工录取"},{"id":"2","text":"即报即得"}]'></div>
        </div>
    </div>
    <div class="whgff-row">
        <div class="whgff-row-label"><label style="color: red">*</label>培训场次：</div>
        <div class="whgff-row-input">
            <div class="radio radio-primary whg-js-data" value="${whgTra.ismultisite}"  name="ismultisite" js-data='[{"id":"0","text":"单场"},{"id":"1","text":"多场"},{"id":"2","text":"固定场"}]'></div>
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label"><label style="color: red">*</label>报名时间周期：</div>
        <div class="whgff-row-input">
            <input class="easyui-datetimebox enrollstarttime" id="enrollstarttime" name="enrollstarttime" value="<fmt:formatDate value="${whgTra.enrollstarttime}" pattern="yyyy-MM-dd HH:mm:ss"/>" style="width:200px; height:32px" data-options="required:true"/>至
            <input class="easyui-datetimebox enrollendtime" id="enrollendtime" name="enrollendtime" value="<fmt:formatDate value="${whgTra.enrollendtime}" pattern="yyyy-MM-dd HH:mm:ss"/>" style="width:200px; height:32px" data-options="required:true, validType:'bmEndTime[\'enrollstarttime\']'"/>
        </div>
    </div>
    <div class="whgff-row train">
        <div class="whgff-row-label "><label style="color: red">*</label>培训时间周期：</div>
        <div class="whgff-row-input">
            <input class="easyui-datebox starttime" id="starttime" name="starttime" value="<fmt:formatDate value="${whgTra.starttime}" pattern="yyyy-MM-dd"/>" style="width:200px; height:32px" data-options="required:true, validType:'traEndTime[0,\'enrollendtime\']'" />至
            <input class="easyui-datebox endtime" id="endtime" name="endtime" value="<fmt:formatDate value="${whgTra.endtime}" pattern="yyyy-MM-dd"/>" style="width:200px; height:32px" data-options="required:true, validType:'traEndTime[1,\'starttime\']'"/>
        </div>
    </div>

    <!--添加时单场的培训上课时间-->
    <div class="whgff-row single" >
        <div class="whgff-row-label"><label style="color: red">*</label>培训上课时间：</div>
        <div class="whgff-row-input">
            <input class="easyui-datetimebox sin_starttime" id="sin_starttime" name="sin_starttime" style="width:200px; height:32px" data-options="validType:'sinTime[\'enrollendtime\']'"/>至
            <input class="easyui-datetimebox sin_endtime" id="sin_endtime" name="sin_endtime" style="width:200px; height:32px" data-options="validType:'sinTime[\'sin_starttime\']'"/>
        </div>
    </div>


    <!--添加时多场的培训上课时间-->
    <c:if test="${empty course}">
        <div class="whgff-row multi" name="multiContent">
            <div class="whgff-row-label"><label style="color: red">*</label>培训上课时间：</div>
            <div class="whgff-row-input">
                <input class="easyui-datetimebox _starttime" name="_starttime" id="_starttime_1"  style="width:200px; height:32px" data-options="required:true,validType:'traTime[0, \'_starttime_1\']'"/>至
                <input class="easyui-datetimebox _endtime" name="_endtime" id="_starttime_2" style="width:200px; height:32px" data-options="required:true,validType:'traTime[1,\'_starttime_1\']'"/>
                <a href="javascript:void(0)" class="timeico add">添加</a>
               <%-- <a href="javascript:void(0)" class="timeico del">删除</a>--%>
            </div>
        </div>
    </c:if>


    <div class="whgff-row fixed">
        <div class="whgff-row-label"><label style="color: red">*</label>固定周几：</div>
        <div class="whgff-row-input">
            <div class="checkbox checkbox-primary whg-js-data"  name="fixedweek" value="${whgTra.fixedweek}" js-data='[{"id":"1","text":"周一"},{"id":"2","text":"周二"},{"id":"3","text":"周三"},{"id":"4","text":"周四"},{"id":"5","text":"周五"},{"id":"6","text":"周六"},{"id":"7","text":"周日"}]'> </div>
        </div>
    </div>

    <div class="whgff-row fixed">
        <div class="whgff-row-label"><label style="color: red">*</label>固定上课时间：</div>
        <div class="whgff-row-input">
            <input class="easyui-timespinner _coursestarttime" name="fixedstarttime" value="<fmt:formatDate value="${whgTra.fixedstarttime}" pattern="HH:mm"/>"  style="width:200px; height:32px"/>至
            <input class="easyui-timespinner _courseendtime" name="fixedendtime" value="<fmt:formatDate value="${whgTra.fixedendtime}" pattern="HH:mm"/>" style="width:200px; height:32px" />
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label"><label style="color: red">*</label>培训详情：</div>
        <div class="whgff-row-input">
            <script id="detail" name="detail" type="text/plain" style="width:600px; height:300px;"></script>
        </div>
    </div>
    <div class="whgff-row">
        <div class="whgff-row-label"><label style="color: red"></label>培训大纲：</div>
        <div class="whgff-row-input">
            <script id="catalog" name="catalog" type="text/plain" style="width:600px; height:300px;"></script>
        </div>
    </div>
    <div class="whgff-row">
        <div class="whgff-row-label"><label style="color: red"></label>老师介绍：</div>
        <div class="whgff-row-input">
            <script id="teacherIntro" name="teacherIntro" type="text/plain" style="width:600px; height:300px;"></script>
        </div>
    </div>


</form>

<div class="whgff-but" style="width: 400px; margin:20px 0px 50px 350px">
    <a class="easyui-linkbutton whgff-but-submit" iconCls="icon-ok">提 交</a>
    <a class="easyui-linkbutton whgff-but-clear" iconCls="icon-no">清 空</a>
</div>

<script>
    $.extend($.fn.validatebox.defaults.rules, {
        bmEndTime: {
            validator: function(value, param){
                //alert(value);
                var sdVal = $('#'+param[0]).datetimebox('getValue');
                var d1 = WhgComm.parseDateTime(sdVal);
                var d2 = WhgComm.parseDateTime(value);
                return d2.getTime()>d1.getTime();
            },
            message: '培训报名结束时间必须大于培训报名开始时间.'
        },
        traEndTime: {
            validator: function(value, param){
                var _type = param[0];
                var _refid = param[1];

                    if(_type === 0){
                        var sdVal = $("#enrollendtime").datetimebox('getValue');
                        var d1 = WhgComm.parseDateTime(sdVal);
                        var d2 = WhgComm.parseDateTime(value+" 23:59:59");
                        return d2.getTime() > d1.getTime();
                    }else if(_type === 1){
                        var sdVal = $('#'+param[1]).datebox('getValue');
                        var d1 = WhgComm.parseDateTime(sdVal);
                        var d2 = WhgComm.parseDateTime(value+" 23:59:59");
                        return d2.getTime() > d1.getTime();
                    }

            },
            message: '培训时间必须晚于培训报名时间.'
        },
        _validage: {
            validator: function(value, param){
                var val = $('#'+param[0]).numberspinner('getValue');
                return parseInt(value) > parseInt(val);
            },
            message: '培训适合年龄后面的必须大于前面的.'
        },
        sinTime:{
            validator: function(value, param){
                var sdVal = $('#'+param[0]).datetimebox('getValue');
                var d1 = WhgComm.parseDateTime(sdVal);
                var d2 = WhgComm.parseDateTime(value);
                //var _enrollendtime = WhgComm.parseDateTime($("#enrollendtime").datetimebox('getValue'));
                return d2.getTime()>=d1.getTime();
            },
            message: '培训时间必须晚于培训报名时间.'
        }

    });

    //显示培训时间(0单场,1多场,2固定场)
    function showTrainTime(isMulti){
        var id = '${id}';
        var state = '${state}';
        //添加的时候
        if(!id){
            //单场
            if(0 == isMulti){
                $(".single").css("display","");
              //  $(".single_edit").css("display","none");

               // $(".sin_starttime_edit").datetimebox("destroy");
               // $(".sin_endtime_edit").datetimebox("destroy");

                $(".sin_starttime").datetimebox({required:true});
                $(".sin_endtime").datetimebox({required:true});

                $(".multi").css("display","none");
             //   $(".multi_edit").css("display","none");
                $("._starttime").datetimebox({required:false});
                $("._endtime").datetimebox({required:false});
                $(".fixed").css("display","none");
                $("._coursestarttime").timespinner({required:false});
                $("._courseendtime").timespinner({required:false});
                $(".train").css("display","none");
                $(".starttime").datebox({required:false});
                $(".endtime").datebox({required:false});
            }
            //多场
            if(1 == isMulti){
                $(".single").css("display","none");
             //   $(".single_edit").css("display","none");
                $(".sin_starttime").datetimebox({required:false});
                $(".sin_endtime").datetimebox({required:false});

                $(".multi").css("display","");
                $("._starttime").datetimebox({required:true});
                $("._endtime").datetimebox({required:true});

            //    $(".multi_edit").css("display","");
              //  $(".starttime_edit").datetimebox("destroy");
              //  $(".endtime_edit").datetimebox("destroy");

                $(".fixed").css("display","none");
                $("._coursestarttime").timespinner({required:false});
                $("._courseendtime").timespinner({required:false});
                $(".train").css("display","");
                $(".starttime").datebox({required:true});
                $(".endtime").datebox({required:true});
            }
            //固定场
            if(2 == isMulti){
                $(".single").css("display","none");
               // $(".single_edit").css("display","none");
                $(".sin_starttime").datetimebox({required:false});
                $(".sin_endtime").datetimebox({required:false});

                $(".multi").css("display","none");
               // $(".multi_edit").css("display","none");
                $("._starttime").datetimebox({required:false});
                $("._endtime").datetimebox({required:false});
                $(".fixed").css("display","");
                $("._coursestarttime").timespinner({required:true});
                $("._courseendtime").timespinner({required:true});
                $(".train").css("display","");
                $(".starttime").datebox({required:true});
                $(".endtime").datebox({required:true});
            }
        }

    }



    //处理UE
    var ueConfig = {
        scaleEnabled:true,
        autoFloatEnabled: false,
        elementPathEnabled:false,
        readonly: '${targetShow}'? true: false
    };
    var teacherIntro = UE.getEditor('teacherIntro', ueConfig);
    var catalog = UE.getEditor('catalog', ueConfig);
    var detail = UE.getEditor('detail', ueConfig);
    //UE 设置值
    teacherIntro.ready(function(){  teacherIntro.setContent('${whgTra.teacherdesc}') });
    catalog.ready(function(){  catalog.setContent('${whgTra.outline}') });
    detail.ready(function(){  detail.setContent('${whgTra.coursedesc}') });



    $(function () {
        //根据地址取坐标
        WhgMap.init({basePath:'${basePath}', addrFieldId:'address', xpointFieldId:'longitude', ypointFieldId:'latitude', getPointBtnId:'getXYPointBtn'});
        //图片初始化
        var __WhgUploadImg = WhgUploadImg.init({basePath: '${basePath}', uploadBtnId: 'imgUploadBtn1', hiddenFieldId: 'cult_picture1', previewImgId: 'previewImg1'});

        //添加一行课程添加的表单
        $(document).on('click','.add',function(){
            var trs = $("#whgff").find("div[name='multiContent']");
            var length = $(trs).length;
            var content = "";
            if(length >= 1){
                for(var i=0;i<length;i++){
                    if(i<1){
                        if(!id) {

                            var _sid = IDUtil.getId();
                            var _eid = IDUtil.getId();
                            var addHtmlContent = '<div class="whgff-row-label"><label style="color: red">*</label>培训上课时间：</div>'
                                    + '<div class="whgff-row-input">'
                                    + '<input class="easyui-datetimebox _starttime" name="_starttime" id="_starttime_' + _sid + '" style="width:200px; height:32px" data-options="required:true, validType:\'traTime[0, \\\'_starttime_' + _sid + '\\\']\'">至'
                                    + '&nbsp;<input class="easyui-datetimebox _endtime" name="_endtime" id="_endtime_' + _eid + '" style="width:200px; height:32px" data-options="required:true, validType:\'traTime[1, \\\'_starttime_' + _sid + '\\\']\'" >'
                                    + '&nbsp;<a href="javascript:void(0)" class="timeico add">添加</a>'
                                    + '&nbsp;&nbsp;<a href="javascript:void(0)" class="timeico del">删除</a>'
                                    + '</div>';
                        }else{
                            var _sid = IDUtil.getId(); var _eid = IDUtil.getId();
                            var addHtmlContent = '<div class="whgff-row-label"><label style="color: red">*</label>培训上课时间：</div>'
                                    +'<div class="whgff-row-input">'
                                    +'<input class="easyui-datetimebox starttime_edit" name="_starttime" id="_starttime_'+_sid+'" style="width:200px; height:32px" data-options="required:true, validType:\'edittraTime[0, \\\'_starttime_'+_sid+'\\\']\'">至'
                                    +'&nbsp;<input class="easyui-datetimebox endtime_edit" name="_endtime" id="_endtime_'+_eid+'" style="width:200px; height:32px" data-options="required:true, validType:\'edittraTime[1, \\\'_starttime_'+_sid+'\\\']\'" >'
                                    +'&nbsp;<a href="javascript:void(0)" class="timeico add">添加</a>'
                                    +'&nbsp;&nbsp;<a href="javascript:void(0)" class="timeico del">删除</a>'
                                    +'</div>';
                        }
                        content += '<div class="whgff-row multi js-temp-addnow" name="multiContent">' + addHtmlContent + '</div>';
                    }
                }
                $($(trs)[length-1]).after(content);
                $.parser.parse("div.js-temp-addnow[name='multiContent']")
            }
            //找到添加的行清除原有内容
            $("#whgff").find("div.js-temp-addnow[name='multiContent']").each(function (i){
                $(this).find('input').val("");
                $(this).attr("name","multiContent");
                $(this).removeClass("js-temp-addnow");
                //$(this).find(".easyui-datetimebox").datetimebox({});
            });
        }) //添加一行课程添加的表单  END

        //删除一行
        $(document).on('click','.del',function(){
            var length = $("#whgff").find("div[name='multiContent']").length;
            if(1 == length){
                $.messager.alert("提示","最少保留一次培训日期！");
                return
            }
            //删除当前行,上一行
            var curTr = $(this).parent().parent();
            $(curTr).remove();
        })  //删除一行 END

        //
        var isMulti = $('input[name="ismultisite"]:checked').val();
        showTrainTime(isMulti);

        //单场培训、多场、固定场
        $('.whg-js-data').on('click','input[name="ismultisite"]',function(){
            var ck = this.checked;
            var value = $(this).val();
            if(ck){
                showTrainTime(value);
            }else{
                showTrainTime(0);
            }
        });

        var buts = $("div.whgff-but");
        var id = '${id}';
        var targetShow = '${targetShow}';
        var frm = $("#whgff");
        //添加时开启清除 ，其它为返回
        if (!id){
            buts.find("a.whgff-but-clear").on('click', function(){
                frm.form('disableValidation');
                frm.form('clear');
                frm.find("div.radio").find(':radio:eq(0)').click();
                teacherIntro.setContent('');
                catalog.setContent('');
                detail.setContent('');
                //
                __WhgUploadImg.clear();
            });
        }else{
            //处理返回
            buts.find("a.whgff-but-clear").linkbutton({
                text: '返 回',
                iconCls: 'icon-undo',
                onClick: function(){
                    if (!targetShow){
                        window.parent.$('#whgdg').datagrid('reload');
                    }
                    WhgComm.editDialogClose();
                }
            });
        }


        //查看时的处理
        if (targetShow){
            //取消表单验证
            frm.form("disableValidation");

            //组件设为只读
            $('.easyui-textbox').textbox('readonly');
            $('.easyui-combobox').combobox('readonly');
            $('.easyui-datebox').combobox('readonly');
            $('.easyui-numberspinner').numberspinner('readonly');
            $('.easyui-datetimebox').combobox('readonly');
            $("#getXYPointBtn").hide();
            $("#imgUploadBtn1").hide();
            //处理选项点击不生效
            frm.find("input[type='checkbox'], input[type='radio']").on('click', function(){return false});

            //不显示提交 button
            buts.find("a.whgff-but-submit").hide();
            return;
        }

        //定义表单提交
        var url = id ? "${basePath}/admin/train/edit" : "${basePath}/admin/train/add";
        frm.form({
            url: url,
            novalidate: true,
            onSubmit: function (param) {
                if (id){
                    param.id = id;
                }
                $(this).form("enableValidation");
                var isValid = $(this).form('validate');
                if (isValid){
                    //富文本验证
                    var isUEvalid = validateUE();
                    if (isUEvalid){
                        param.teacherdesc = teacherIntro.getContent();
                        param.outline = catalog.getContent();
                        param.coursedesc = detail.getContent();
                    }else{
                        isValid = false;
                    }//富文本验证 --END

                    //报名人数
                    var _basicenrollnumber = $("#whgff").find("input[name='basicenrollnumber']").val();
                    var _maxnumber = $("#whgff").find("input[name='maxnumber']").val();
                    if(parseInt(_basicenrollnumber) > parseInt(_maxnumber)){
                        $.messager.alert("错误", '基础报名人数必须小于报名人数限制！', 'error');
                        isValid = false;
                    }

                    //固定班周几验证
                    if(parseInt($('input[name="ismultisite"]:checked').val()) == 2){

                        var week = $('input[name="fixedweek"]:checked').val();
                        if(!week){
                            $.messager.alert("错误", '固定周几不能为空！', 'error');
                            isValid = false;
                        }

                        var starthour = $("._coursestarttime").timespinner('getHours');
                        var startmin = $("._coursestarttime").timespinner('getMinutes');
                        var endhour = $("._courseendtime").timespinner('getHours');
                        var endmin = $("._courseendtime").timespinner('getMinutes');
                        var _a = starthour*60+startmin;
                        var _b = endhour*60+endmin;

                        if(_a > _b){
                            $.messager.alert("错误", '固定上课开始时间不能晚于结束时间！', 'error');
                            isValid = false;
                        }

                    }//固定班周几验证 --END

                }
                if (!isValid){
                    $.messager.progress('close');
                    buts.find("a.whgff-but-submit").off('click').one('click', function () {
                        frm.submit();
                    });
                }

                return isValid;

            },
            success : function (data) {
                data = $.parseJSON(data);
                $.messager.progress('close');
                buts.find("a.whgff-but-submit").off('click').one('click', function () {
                    frm.submit();
                });
                if (!data.success || data.success != "1"){
                    $.messager.alert("错误", data.errormsg||'操作失败', 'error');
                    return;
                }
                if (!id){
                    $(this).form("disableValidation");
                    buts.find("a.whgff-but-clear").click();
                    /*$(this).form('clear');
                    frm.find("div.radio").find(':radio:eq(0)').click();
                    teacherIntro.setContent('');
                    catalog.setContent('');
                    detail.setContent('');*/
                    $.messager.show({
                        title:'提示消息',
                        msg:'培训信息提交成功',
                        showType:'slide',
                        timeout:1000,
                        width: 300,
                        height: 200
                    });
                }else{
                    WhgComm.editDialogClose();
                    window.parent.$('#whgdg').datagrid('reload');
                }
            }
        });
        buts.find("a.whgff-but-submit").off('click').one('click', function () {
            frm.submit();
        });

        //处理UE项的验证
        function validateUE(){
            //图片不能为空
            var picture1 = $("#cult_picture1").val();
            if (!picture1){
                $.messager.alert("错误", '图片不能为空！', 'error');
                return false;
            }
            //艺术分类不能为空
            var arttype = $("#whgff").find("input[name='arttype']:checked").val();
            if (!arttype){
                $.messager.alert("错误", '艺术分类不能为空！', 'error');
                return false;
            }
            //课程详情不能为空
            if (!detail.hasContents()){
                $.messager.alert("错误", '培训详情不能为空！', 'error');
                return false;
            }
            return true;
        }
    });


</script>

</body>
</html>
