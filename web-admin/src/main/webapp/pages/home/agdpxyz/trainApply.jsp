<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <%@include file="/pages/comm/agdhead.jsp"%>
    <title>广东省文化馆-培训驿站-在线报名-${train.title}</title>
    <meta charset="utf-8">
    <meta http-equiv=”X-UA-Compatible” content=”IE=edge,chrome=1″ />
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="keywords" content="">
    <meta name="author" content="">
    <link href="${basePath}/static/assets/css/public/reset.css" rel="stylesheet">
    <link href="${basePath}/static/assets/css/activity/activityDetaiil.css" rel="stylesheet">
    <link href="${basePath}/static/assets/js/plugins/oiplayer-master/css/oiplayer.css" rel="stylesheet">
    <link href="${basePath}/static/assets/css/train/train.css" rel="stylesheet">
    <link href="${basePath}/static/assets/js/public/skin/WdatePicker.css " rel="stylesheet">
    <!-- core public JavaScript -->
    <script src="${basePath}/static/assets/js/public/jquery-1.11.0.min.js"></script>
    <script src="${basePath}/static/assets/js/public/jquery-migrate-1.0.0.js"></script>
    <script src="${basePath}/static/assets/js/plugins/oiplayer-master/plugins/flowplayer-3.2.6.min.js" type="text/javascript"></script>
    <script src="${basePath}/static/assets/js/plugins/oiplayer-master/js/jquery.oiplayer.js"></script>
    <script src="${basePath}/static/assets/js/public/rong-dialog.js"></script>
    <script src="${basePath}/static/assets/js/plugins/roll/jquery.sly.min.js"></script>
    <script src="${basePath}/static/assets/js/plugins/sidebar/stickySidebar.js"></script>
    <script src="${basePath}/static/assets/js/public/WdatePicker.js"></script>
    <script src="${basePath}/static/assets/js/activity/activityDetail.js"></script>
    <script src="${basePath}/static/assets/js/public/public.js"></script>
    <script type="text/javascript">
        var basePath = "${basePath}";
        var tools = (function(trainId){
            var trainId = trainId;
            //处理步骤显示
            function setStepNav(stepNum){
                if (stepNum == '2'){
                    $("ul.crumbs2").removeClass("crumbs2-1st").addClass("crumbs2-2nd");
                }
                var midx = parseInt(stepNum) -1;
                $(".main:eq("+midx+")").show();
            }
            function isItemOK($item){
                var $em = $item.siblings("em.msg");
                $em.text("");
                var name = $item.parents("dl").find("dt").text();
                var value = $item.val();
                if (!value) {
                    $em.text(name+" 不能为空");
                    return false;
                }
                if ($item.attr("regexp")){
                    var patt = new RegExp( $item.attr("regexp") );
                    value = $.trim(value);
                    var tes = patt.test(value);
                    if (!tes){
                        $em.text(name+" 格式不正确");
                        return false;
                    }
                }
                //去空格后回填数据
                $item.val(value);
                return true;
            }
            //处理报名资料提交
            function goNext(){
                var isAllItemOK = true;
                $(".main:eq(0) :input").each(function(){
                    var isOK = isItemOK($(this));
                    if (!isOK){
                        isAllItemOK = false;
                    }
                });
                if (!isAllItemOK){
                    return false;
                }
                var sarr = $(".main:eq(0) :input").serializeArray();
                var params = new Object();
                for(var i in sarr){
                    var d = sarr[i];
                    params[d.name] = d.value;
                }
                params.traid = trainId;
                $("#btnSubmit").css("display","none");
                $("#hiddenbtnSubmit").css("display","");
                $.post(basePath+"/agdpxyz/saveTrainEnrol.do", params, function(resultCode){
                    if (resultCode == "0"){
                        window.location.href=basePath+"/agdpxyz/toTrainApplySuccess.do?trainId="+trainId;
                    }else{
                        showErrorCode(resultCode);
                    }
                }, "json");
                $("#hiddenbtnSubmit").css("display","none");
                $("#btnSubmit").css("display","");
            }
            function showErrorCode(resultCode){
                if ("001" == resultCode) {
                    var loginUrl = basePath+"/login";
                    rongAlertDialog({ title: "提示信息", desc : "请登录后再进行操作", closeBtn : false, icoType : 1 }, function(){
                        window.location.href = loginUrl;
                    });
                } else if ("100" == resultCode) {
                    rongDialog({ type : true, title : "培训已下架,请选择其他培训", time : 3*1000 });
                }else if ("101" == resultCode) {
                    rongDialog({ type : true, title : "培训报名已结束,请选择其他培训", time : 3*1000 });
                }else if ("102" == resultCode) {
                    rongDialog({ type : true, title : "培训报名名额已满,请选择其他培训", time : 3*1000 });
                }else if ("103" == resultCode) {
                    rongDialog({ type : true, title : "您已报名了该培训,请不要重复报名", time : 3*1000 });
                }else if ("104" == resultCode) {
                    rongDialog({ type : true, title : "您还未进行实名制验证,请先实名制", time : 3*1000 });
                }else if ("107" == resultCode) {
                    rongDialog({ type : true, title : "您选择的培训年龄段不符合", time : 3*1000 });
                }else if ("106" == resultCode) {
                    rongDialog({ type : true, title : "您已经报名了两场培训，不可以再报名", time : 3*1000 });
                }
                else{
                    rongDialog({ type : true, title : "保存失败,请联系管理员", time : 3*1000 });
                }
            }
            function init(){
                //步骤处理
                var stepNum = "${stepNum}";
                setStepNav(stepNum);
                //失去焦点验证事件
                $(".main:eq(0) :input").on("blur", function(){
                    isItemOK($(this));
                })
                //点击提交事件
                $(".main:eq(0) .goNext a").on("click", function () {
                    goNext();
                })
            }

            return {
                init: init
            }
        })("${train.id}");

        $(function () {
            $('.cardType:eq(0)').click();
            tools.init();
            //日历控件
            $("#wdate_id").on("click", function(){
                WdatePicker({
                    dateFmt:'yyyy-MM-dd',
                    doubleCalendar:true,
                    minDate:'1900-01-01',
                    maxDate:'2050-12-31',
                    isShowClear:true,
                    isShowOK:true,
                    isShowToday:false})
            })
        })
    </script>
</head>
<body class="oiplayer-example">
<!--公共主头部开始-->
<%@include file="/pages/comm/agdtopsmall.jsp"%>
<!--公共主头部结束-->
<div class="main-info-bg bg-color">
    <ul class="crumbs crumbs-1 clearfix">
        <li class="step-1">1. 填写培训报名资料<em class="arrow"></em></li>
        <li class="step-2 last">2. 等待管理员审核<em class="arrow"></em></li>
    </ul>
    <div class="main train-main container-wrapper">

        <dl class="clearfix">
            <dt>培训名称：</dt>
            <dd class="demoName">${train.title}</dd>
        </dl>

        <dl class="clearfix">
            <dt>姓名：</dt>
            <dd><input type="text" name="realname" value="${not empty user.name ? user.name : ""}" placeholder="请输入真实姓名" maxlength="50"><em class="msg"></em></dd>
        </dl>

        <dl class="clearfix">
            <dt>出生年月日：</dt>
            <dd>
                <input type="text" id="wdate_id" name="enrolBirthdayStr" placeholder="请选择出生年月日" value="<fmt:formatDate value="${not empty user.birthday? user.birthday :''}" pattern="yyyy-MM-dd" />" readonly="">
                <em class="msg"></em>
            </dd>
        </dl>
        <dl class="clearfix">
            <dt>性别：</dt>
            <dd>
                <label><input type="radio" name="sex" value="1" ${empty user.sex ? "checked=''" :""} ${not empty user.sex and user.sex == 1 ? "checked=''":""} class="ckbox">男</label>
                <label><input type="radio" name="sex" value="2" ${not empty user.sex and user.sex == 0 ? "checked=''":""} class="ckbox">女</label>
            </dd>
        </dl>

        <dl class="clearfix">
            <dt>证件类型：</dt>
            <dd>
                <label><input type="radio" name="crettype" checked="" class="ckbox cardType" value="1">身份证</label>
                <label><input type="radio" name="crettype" class="ckbox cardType" value="0">其它有效证件(大陆境外)</label>
                <script>
                    $('.cardType').click(function(){
                        if($(this).val()==0){
                            $('#cardId2017').attr({
                                regexp:(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$),
                                placeholder:"请输入身份证号码"
                            });

                        }else{
                            $('#cardId2017').removeAttr("regexp");
                            $('#cardId2017').attr({
                                placeholder:"请输入证件号",
                                value:""
                            })
                        }
                    });
                </script>
            </dd>
        </dl>
        <dl class="clearfix">
            <dt>证件号：</dt>
            <dd><input type="text" id="cardId2017" name="cardno" value="${not empty user.idcard ? user.idcard :""}" placeholder="请输入身份证号码" maxlength="30" regexp="(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)"><em class="msg"></em></dd>
        </dl>

        <dl class="clearfix">
            <dt>手机联系电话：</dt>
            <dd><input type="text" name="contactphone" value="${not empty user.phone ?user.phone :"" }" placeholder="请输入手机号码" maxlength="20" regexp="^\d{11}$"><em class="msg"></em></dd>
        </dl>

        <div class="goNext" id="submitDiv">
            <a id="btnSubmit" class="submit" href="javascript:void(0)">确认订单</a>
            <a id="hiddenbtnSubmit" style="display: none" class="open" href="javascript:void(0)">确认订单</a>
        </div>
    </div>
</div>
<!--公共主底部开始-->
<%@include file="/pages/comm/agdfooter.jsp"%>
<!--公共主底部结束-END-->
<a class="to-top"></a>
</body>
</html>
