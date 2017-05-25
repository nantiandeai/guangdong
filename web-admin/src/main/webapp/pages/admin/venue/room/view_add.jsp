<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<% request.setAttribute("basePath", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath());%>
<% request.setAttribute("resourceid", request.getParameter("rsid")); %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <c:choose>
        <c:when test="${not empty id and not empty targetShow}">
            <c:set var="pageTitle" value="活动室管理-查看活动室"></c:set>
        </c:when>
        <c:when test="${not empty id}">
            <c:set var="pageTitle" value="活动室管理-编辑活动室"></c:set>
        </c:when>
        <c:otherwise>
            <c:set var="pageTitle" value="活动室管理-添加活动室"></c:set>
        </c:otherwise>
    </c:choose>
    <title>${pageTitle}</title>

    <%@include file="/pages/comm/admin/header.jsp" %>
    <link rel="stylesheet" href="${basePath}/static/admin/css/bootstrap.css"/>
    <link rel="stylesheet" href="${basePath}/static/admin/Font-Awesome/css/font-awesome.min.css"/>
    <link rel="stylesheet" href="${basePath}/static/admin/css/build.css"/>

    <script src="${basePath}/static/ueditor/ueditor.config.js"></script>
    <script src="${basePath}/static/ueditor/ueditor.all.min.js"></script>
    <script src="${basePath}/static/ueditor/lang/zh-cn/zh-cn.js"></script>

    <script src="${basePath}/static/admin/js/whgtoolmodule.js"></script>
    <script src="${basePath}/static/admin/js/whgmodule-weektimes.js"></script>
    <script src="${basePath}/static/admin/js/whgmodule-venseatmaps.js"></script>

    <!-- 图片上传相关 -->
    <script type="text/javascript" src="${basePath}/static/plupload/lib/plupload-2.1.2/js/plupload.full.min.js"></script>
    <script type="text/javascript" src="${basePath}/static/plupload/upload-img.js"></script>
    <!-- 图片上传相关-END -->

</head>
<body class="body_add">
<form id="whgff" method="post" class="whgff">
    <h2>${pageTitle}</h2>
    <div class="whgff-row">
        <div class="whgff-row-label"><i>*</i>名称：</div>
        <div class="whgff-row-input">
            <input class="easyui-textbox" name="title" value="${whgVenRoom.title}" style="width:600px; height:32px"
                   data-options="required:true,validType:['length[1,10]'], prompt:'请输入活动室名称'">
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label"><i>*</i>图片：</div>
        <div class="whgff-row-input">
            <input type="hidden" id="cult_picture1" name="imgurl" value="${whgVenRoom.imgurl}">
            <div class="whgff-row-input-imgview" id="previewImg1"></div>
            <div class="whgff-row-input-imgfile">
                <i><a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" id="imgUploadBtn1">选择图片</a></i>
                <i>图片格式为jpg、png、gif，建议图片尺寸 750*500，大小为2MB以内</i>
            </div>
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label"><i>*</i>活动室分类：</div>
        <div class="whgff-row-input">
            <div class="radio radio-primary whg-js-data" name="etype" value="${whgVenRoom.etype}"
                 js-data="WhgComm.getRoomType">
            </div>
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label"><i>*</i>是否收费：</div>
        <div class="whgff-row-input">
            <div class="radio radio-primary whg-js-data" name="hasfees" value="${whgVenRoom.hasfees}"
                 js-data='[{"id":"0","text":"不收费"},{"id":"1","text":"收费"}]'>
            </div>
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label">活动室标签：</div>
        <div class="whgff-row-input">
            <div class="checkbox checkbox-primary whg-js-data" name="etag" value="${whgVenRoom.etag}"
                 js-data="WhgComm.getRoomTag">
            </div>
        </div>
    </div>
    <div class="whgff-row">
        <div class="whgff-row-label">活动室关键字：</div>
        <div class="whgff-row-input">
            <%--<div class="checkbox checkbox-primary whg-js-data" name="ekey" value="${whgVenRoom.ekey}"
                 js-data="WhgComm.getRoomKey">
            </div>--%>
            <select class="easyui-combobox" id="ekey" style="width:600px; height:32px" validType="notQuotes"
                    data-options="multiple:true, valueField:'text', textField:'text', data:WhgComm.getRoomKey()" ></select>
                (多于2个关键字之间用半角逗号分隔)
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label">活动室设施：</div>
        <div class="whgff-row-input">
            <div class="checkbox checkbox-primary whg-js-data" name="facility" value="${whgVenRoom.facility}"
                 js-data="WhgComm.getSBType" >
            </div>
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label">开放时间：</div>
        <div class="whgff-row-input">
            <div class="checkbox checkbox-primary whg-js-data" name="openweek" value="${whgVenRoom.openweek}"
                 js-data='[{"id":"2","text":"周一"},{"id":"3","text":"周二"},{"id":"4","text":"周三"},{"id":"5","text":"周四"},{"id":"6","text":"周五"},{"id":"7","text":"周六"},{"id":"1","text":"周日"}]'>
            </div>

            <div class="whg-week-cont whgmodule-weektimes" whgmodule-options="{refWeekCheckbox:'input[name=\'openweek\']'}">
            </div>
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label"><i>*</i>所属场馆：</div>
        <div class="whgff-row-input">
            <input class="easyui-combobox" name="venid" panelHeight="auto" limitToList="true" style="width:600px; height:32px"
                   data-options="required:true, editable:false,multiple:false, mode:'remote', panelMaxHeight:300,
                   valueField:'id', textField:'title', url:'${basePath}/admin/venue/srchList?state=6&delstate=0',
                   onLoadSuccess: function(){$(this).combobox('setValue','${whgVenRoom.venid}')},
                   prompt:'请选择所属场馆'">
        </div>
    </div>
    <div class="whgff-row">
        <div class="whgff-row-label">座位模板：</div>
        <div class="whgff-row-input">
            <input class="easyui-numberbox" name="seatrows" value="${whgVenRoom.seatrows}" style="width:100px; height:32px"
                   data-options="required:false,min:0,max:99, prompt:'行数'">
            -
            <input class="easyui-numberbox" name="seatcols" value="${whgVenRoom.seatcols}" style="width:100px; height:32px"
                   data-options="required:false,min:0,max:99, prompt:'列数'">
            <a class="easyui-linkbutton js-test" text="设置行列数"></a>
        </div>
        <div class="whgff-row-map whgmodule-venseatmaps"
             whgmodule-options="{
             type: '${targetShow}'?'show':'edit',
             rowNum:'${whgVenRoom.seatrows}',
             colNum:'${whgVenRoom.seatcols}',
             onInit:initSeatValue}"></div>
    </div>
    <div class="whgff-row">
        <div class="whgff-row-label"><i>*</i>地址：</div>
        <div class="whgff-row-input">
            <input class="easyui-textbox" name="location" value="${whgVenRoom.location}" style="width:600px; height:32px"
                   data-options="required:true,validType:['length[1,60]'], prompt:'请输入位置信息，如：1栋108室'">
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label"><i>*</i>面积(m<sup>2</sup>)：</div>
        <div class="whgff-row-input">
            <input class="easyui-numberbox" name="sizearea" value="${whgVenRoom.sizearea}" style="width:300px; height:32px"
                   data-options="required:true,precision:3,min:0,max:1000000, prompt:'请输入面积'">
        </div>
    </div>
    <div class="whgff-row">
        <div class="whgff-row-label"><i>*</i>可容人数：</div>
        <div class="whgff-row-input">
            <input class="easyui-numberbox" name="sizepeople" value="${whgVenRoom.sizepeople}" style="width:300px; height:32px"
                   data-options="required:true,min:0,max:1000000, prompt:'请输入可容人数'">
        </div>
    </div>

    <div class="whgff-row">
        <div class="whgff-row-label"><i>*</i>场地简介：</div>
        <div class="whgff-row-input">
            <input class="easyui-textbox" name="summary" value="${whgVenRoom.summary}" multiline="true" style="width:600px;height: 100px;"
                   data-options="required:true,validType:['length[1,400]']">
        </div>
    </div>
    <div class="whgff-row">
        <div class="whgff-row-label"><i>*</i>场地描述：</div>
        <div class="whgff-row-input">
            <script id="description" name="description" type="text/plain" style="width:600px; height:200px;"></script>
        </div>
    </div>

</form>

<div class="whgff-but" style="width: 400px; margin:20px 0px 50px 350px">
    <a class="easyui-linkbutton whgff-but-submit" iconCls="icon-ok">提 交</a>
    <a class="easyui-linkbutton whgff-but-clear" iconCls="icon-no">清 空</a>
</div>

<script>

    function initSeatValue(){
        var seatjson = '${whgVenRoom.seatjson}';
        if (seatjson){
            $(this).whgVenseatmaps('setValue', JSON.parse(seatjson));
        }
    }

    //处理UE
    var ueConfig = {
        scaleEnabled: false,
        autoFloatEnabled: false,
        elementPathEnabled:false,
        readonly: '${targetShow}'? true: false
    };
    var ue_description = UE.getEditor('description', ueConfig);
    ue_description.ready(function(){  ue_description.setContent('${whgVenRoom.description}') });


    $(function(){
        $("#ekey").combobox('setValue', "${whgVenRoom.ekey}");

        var id = '${id}';
        var targetShow = '${targetShow}';
        var frm = $("#whgff");
        var buts = $("div.whgff-but");
        var seatmap = $("div.whgff-row-map");

        //时段设置
        var weektimejson = '${whgVenRoom.weektimejson}';
        if (weektimejson){
            $(".whgmodule-weektimes").whgWeektimes("setValue", JSON.parse(weektimejson));
        }


        var whgImg = WhgUploadImg.init({basePath: '${basePath}', uploadBtnId: 'imgUploadBtn1', hiddenFieldId: 'cult_picture1', previewImgId: 'previewImg1'});

        //添加时开启清除 ，其它为返回
        if (!id){
            buts.find("a.whgff-but-clear").on('click', function(){
                frm.form("disableValidation");
                frm.form('clear');
                whgImg.clear();
                //第一个单选又点上
                frm.find("div.radio").find(':radio:eq(0)').click();
                ue_description.setContent('');
                seatmap.whgVenseatmaps('setSeatSize', 0, 0);
                $(".whgmodule-weektimes").whgWeektimes("clear");
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
            $('.easyui-numberbox').combobox('readonly');
            $(".whgmodule-weektimes").whgWeektimes("setIsShow");
            //处理选项点击不生效
            frm.find("input[type='checkbox']").on('click', function(){return false});
            frm.find("input[type='radio']").attr('disabled', true);
            $("#imgUploadBtn1").hide();

            //不显示提交 button
            buts.find("a.whgff-but-submit").hide();
            return;
        }

        //处理行列设置
        $('.js-test').on("click", setRCNum);
        function setRCNum(){
            var rownum = $('input[name="seatrows"]').val();
            var colnum = $('input[name="seatcols"]').val();
            seatmap.whgVenseatmaps('setSeatSize', rownum, colnum);
        }

        //定义表单提交
        var url = id ? "${basePath}/admin/venue/room/edit" : "${basePath}/admin/venue/room/add";
        frm.form({
            url: url,
            novalidate: true,
            onSubmit: function (param) {
                if (id){
                    param.id = id;
                }

                //坐位相关值处理 START
                var seatsValue = seatmap.whgVenseatmaps('getValue');
                param.seatjson = JSON.stringify(seatsValue);
                //修正坐位行列参数
                var options= seatmap.whgVenseatmaps('options');
                $('input[name="seatrows"]').val(options.rowNum);
                $('input[name="seatcols"]').val(options.colNum);
                //坐位相关值处理 END

                $(this).form("enableValidation");
                var isValid = $(this).form('validate');

                //时段相关值处理 START
                var weekvalida = $(".whgmodule-weektimes").whgWeektimes("validaValue");
                if (weekvalida){
                    var weekTimes = $(".whgmodule-weektimes").whgWeektimes("getValue");
                    param.weektimejson = JSON.stringify(weekTimes);
                }else{
                    $("input[name='openweek']:checked:eq(0)").focus();
                    isValid = weekvalida;
                }
                //时段相关值处理 END

                if (isValid){
                    isValid = validateUE();
                }

                if (!isValid){
                    $.messager.progress('close');
                    oneSubmit();
                }

                param.ekey = $("#ekey").combobox('getText');
                return isValid;
            },
            success: function (data) {
                data = $.parseJSON(data);
                $.messager.progress('close');
                oneSubmit();
                if (!data.success || data.success != "1"){
                    $.messager.alert("错误", data.errormsg||'操作失败', 'error');
                    return;
                }
                if (!id){
                    buts.find("a.whgff-but-clear").click();
                    $.messager.show({
                        title:'提示消息',
                        msg:'活动室信息提交成功',
                        showType:'slide',
                        timeout:1000,
                        width: 300,
                        height: 200
                    });
                }else{
                    window.parent.$('#whgdg').datagrid('reload');
                    WhgComm.editDialogClose();
                }
            }
        });

        //处理重复点击提交
        function oneSubmit(){
            buts.find("a.whgff-but-submit").off('click').one('click', submitForm);
        }
        oneSubmit();

        //处理表单提交
        function submitForm(){
            $.messager.progress();
            frm.submit();
        }

        //处理UE项的验证
        function validateUE(){
            var picture1 = $("#cult_picture1").val();
            if (!picture1){
                $.messager.alert("错误", '图片不能为空', 'error');
                return false;
            }
            if (!ue_description.hasContents()){
                $.messager.alert("错误", '活动室描述不能为空', 'error');
                return false;
            }
            return true;
        }
    });

</script>

</body>
</html>
