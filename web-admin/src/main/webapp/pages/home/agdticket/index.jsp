<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% request.setAttribute("basePath", request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()); %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>取票-广东省文化馆</title>
    <meta name="renderer" content="webkit"/>
    <meta http-equiv="X-UA-Compatible" Content="IE=Edge,chrome=1"/>
    <meta name="robots" content="noindex, nofollow">
    <link rel="stylesheet" type="text/css" href="${basePath}/static/ticket/css/ticket/reset.css"/>
    <link rel="stylesheet" type="text/css" href="${basePath}/static/ticket/css/ticket/index-dg.css"/>
    <link rel="stylesheet" type="text/css" href="${basePath}/static/ticket/css/ticket/index.css"/>
    <link rel="stylesheet" type="text/css" href="${basePath}/static/ticket/css/ticket/main-index.css"/>
    <link rel="stylesheet" type="text/css" href="${basePath}/static/ticket/css/ui-dialog.css"/>
    <link rel="stylesheet" href="${basePath}/static/ticket/css/ticket/wide.css" media="screen and (min-width: 1281px)" />
    <!--[if lte IE 8]>
    <link rel="stylesheet" type="text/css" href="${basePath}/static/ticket/css/ticket/IE.css"/>
    <![endif]-->
    <style>
        .htitle {
            font-size: 44px;
        }

        .num-board {
            width: 760px;
            height: 382px;
            position: relative;
            margin: 30px 0 30px 0;
        }

        .num-board ul {
            width: 760px;
            height: 400px;
            position: relative;
        }

        .showImgs{
            padding-top:10px;
            display:block;
        }
        .hideImgs{
            padding-top:10px;
            display:block;
        }
        .num-board ul li.key1{position: absolute;left: 0px;top:0px;}
        .num-board ul li.key2{position: absolute;left: 190px;top:0px;}
        .num-board ul li.key3{position: absolute;left: 380px;top:0px;}
        .num-board ul li.key4{position: absolute;left: 0px;top:100px;}
        .num-board ul li.key5{position: absolute;left: 190px;top:100px;}
        .num-board ul li.key6{position: absolute;left: 380px;top:100px;}
        .num-board ul li.key7{position: absolute;left: 0px;top:200px;}
        .num-board ul li.key8{position: absolute;left: 190px;top:200px;}
        .num-board ul li.key9{position: absolute;left: 380px;top:200px;}
        .num-board ul li.key10{position: absolute;left: 0px;top:300px;}
        .num-board ul li.key11{position: absolute;left: 570px;top:0px;}
        .num-board ul li.key12{position: absolute;left: 570px;top:100px;
            height: 180px;
            background: #fff;
            /*background-image: -moz-linear-gradient( 90deg, rgb(230,230,230) 0%, rgb(255,255,255) 100%);*/
            /*background-image: -webkit-linear-gradient( 90deg, rgb(230,230,230) 0%, rgb(255,255,255) 100%);*/
            /*background-image: -ms-linear-gradient( 90deg, rgb(230,230,230) 0%, rgb(255,255,255) 100%);*/
        }
        .num-board li, .num-board .btn-del , .num-board .btn-clean{
            width: 170px;
            display: block;
            height: 80px;
            line-height: 80px;
            margin: 0 18px 18px 0;
            background: #fff;
            /*background-image: -moz-linear-gradient( 90deg, rgb(230,230,230) 0%, rgb(255,255,255) 100%);*/
            /*background-image: -webkit-linear-gradient( 90deg, rgb(230,230,230) 0%, rgb(255,255,255) 100%);*/
            /*background-image: -ms-linear-gradient( 90deg, rgb(230,230,230) 0%, rgb(255,255,255) 100%);*/
            border-radius: 5px;
            -moz-border-radius: 5px;
            -webkit-border-radius: 5px;
            color: #333;
            text-align: center;
            cursor: pointer;
            border:solid 1px #d3d3d3;
        }

        .num-board .key {
            font-size: 48px;
            font-family: "微软雅黑";
        }

        .num-board .btn {
            float: right;
            height: 80px;
        }
        .num-board .btn-del {
            height: 80px;
            line-height: 80px;
            font-size: 40px;
            letter-spacing: 8px;
            border: 0;
        }
        .num-board .btn-clean {
            height: 180px;
            line-height: 180px;
            font-size: 40px;
            letter-spacing: 8px;
            border: 0;
            width: 170px;
        }
        .num-board .btn-del:hover,.num-board .btn-clean:hover{
            color: #fff;
            background: #979797;

        }
        .num-board li:hover,.num-board li:active,.num-board li:visited {
            /*text-shadow: 0 1px 0 rgba(255, 255, 255, 0.3);*/
            /*text-decoration: none;*/
            /*background-color: #eeeeee;*/
            /*border-color: #cfcfcf;*/
            color: #fff;
            -webkit-transition-duration: 0s;
            transition-duration: 0s;
            /*-webkit-box-shadow: inset 0 1px 3px rgba(0, 0, 0, 0.2);*/
            /*box-shadow: inset 0 1px 3px rgba(0, 0, 0, 0.2);*/
            background: #979797;

        }

        .btn-submit {
            display: block;
            width: 400px;
            height: 100px;
            line-height: 100px;
            background: #f58636;
            margin: 70px auto 0;
            text-align: center;
            color: #ffffff;
            font-size: 40px;
            border: 0;
            border-radius: 4px;
            -moz-border-radius: 4px;
            -webkit-border-radius: 4px;
            cursor: pointer;
            letter-spacing: 6px;
        }

        .get-ticket input[type=submit] {
            margin-top: -300px;
            margin-left: 585px;
            border-radius: 7px;
        }

        .get-ticket input[type=text] {
            width: 550px;
            height: 80px;
            padding: 0 0 0 15px;
            line-height: 80px;
            color: #666;
            margin: 0;
            float: left;
            border: solid 1px #d3d3d3;
            border-radius: 5px;
            -webkit-border-radius: 5px;
            -moz-border-radius: 5px;
            background: #fff;
            /*background-image: -moz-linear-gradient( 90deg, rgb(230,230,230) 0%, rgb(255,255,255) 100%);*/
            /*background-image: -webkit-linear-gradient( 90deg, rgb(230,230,230) 0%, rgb(255,255,255) 100%);*/
            /*background-image: -ms-linear-gradient( 90deg, rgb(230,230,230) 0%, rgb(255,255,255) 100%);*/
        }

        .get-ticket {
            width: 760px;
            float: left;
            margin-left: 40px;
        }
    </style>
    <script type="text/javascript" src="${basePath}/static/ticket/js/jquery.min.js"></script>
    <script type="text/javascript" src="${basePath}/static/ticket/js/jquery.qrcode.min.js"></script>
    <script type="text/javascript" src="${basePath}/static/ticket/js/base-jd.js"></script>
    <script type="text/javascript" src="${basePath}/static/ticket/js/keyboard.js"></script>
    <script type="text/javascript" src="${basePath}/static/ticket/js/LodopFuncs.js"></script>
    <script type="text/javascript" src="${basePath}/static/ticket/js/dialog-min.js"></script>


    <object id="LODOP" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width=0 height=0>
        <embed id="LODOP_EM" type="application/x-print-lodop" width=0 height=0></embed>
    </object>

    <style type="text/css" id="yangshi">

        .piao {
            width: 100%;
            margin: 0 auto;
        }

        .piao table {
            font-size: 16px;
            border: 1px solid blue;
        }

    </style>

    <script type="text/javascript">
        var seat ;
        var piaoInfo;
        $(function () {
            setIframeHeight();
            $(".ticket-content .book-ticket").hide();
            //   $("#orderValidateCode").focus();
            $(".ticket-top").on("click", ".menu a", function () {
                $(".ticket-top a").removeClass();

                var $this = $(this);
                $this.addClass("curr");
                var id = $this.attr("id");
                if ("orderTicket" == id) {
                    $("#book-iframe").attr("src", "${basePath}/frontPrint/frontActivityListTerminal.do");
                    $(".ticket-content>div").eq($this.index()).show();
                }
                else {
                    $(".ticket-content>div").eq($this.index()).show();
                    $("#orderValidateCode").focus();
                }

                $(".ticket-content>div").eq($this.index()).siblings().hide();

            });


            $(".get-ticket-btn").click(function () {
                var orderValidateCode = $("#orderValidateCode").val();
                if (orderValidateCode == null || orderValidateCode == '') {
                    showTicketMsg('提示信息', "请输入取票码");
                    return false;
                }
                $('#doPrint').attr('disabled', true);
                $.ajax({
                    type: "post",
                    url: "${basePath}/agdticket/print?orderValidateCode=" + orderValidateCode,
                    dataType: "json",
                    contentType: "application/json; charset=utf-8",
                    cache: false,//缓存不存在此页面
                    async: false,//同步请求
                    success: function (data) {
                        if (data.status == "1") {
                            //      showTicketMsg('取票信息有误', '对不起，您所定的票码有误，请检查后再次输入');
                            showTicketMsg('提示信息', data.data);
                            $('#doPrint').removeAttr('disabled');
                        } else {
                            var html = data.data;
                            seat = data.seat;
                            piaoInfo=data.piaoInfo;
                            if ('activity' == data.type) {
                                $("#printPiao").html("");
                                $("#printPiao").append(html);
                                var d = dialog({
                                    width: 400,
                                    top: '55%',
                                    left: '50%',
                                    content: '<strong style="font-size:16px;">正在出票</strong><br><span id="secondNum">3</span>秒后将自动消失',
                                    fixed: true,
                                    lock: true
                                });
                                d.showModal();
                                var sseat=seat.split(",");
                                //strFormHtml = strBodyStyle + "<body>" + printPiao+ "</body>";
                                var ids = 'qrcodeImg';
                                for(var i=0;sseat.length>i;i++){
                                    if(sseat[i]!=""){
                                        createCodeImage(ids,sseat[i]); //生成二维码
                                    }
                                }

                                var num = 3;
                                var timer1 = setInterval(function () {
                                    if (num == 0) {
                                        hideDialog3();
                                    }
                                    num--;
                                    $("#secondNum").text(num);
                                }, 1000);

                                var timer2 = setTimeout(hideDialog3, 3000);

                                function hideDialog3() {
                                    clearInterval(timer1);
                                    clearTimeout(timer2);
                                    d.close().remove();
                                    printAct();
                                }

                                $('#doPrint').removeAttr('disabled');//
                            }
                            if ('venue' == data.type) {
                                $("#venue_block").html("");
                                $("#venue_block").append(html);
                                var ids = 'qrcodeImg2';
                                createCodeImage(ids,""); //生成二维码
                                var d2 = dialog({
                                    width: 400,
                                    top: '55%',
                                    left: '50%',
                                    content: '<strong style="font-size:16px;">正在出票</strong><br><span id="secondNum2">3</span>秒后将自动消失',
                                    fixed: true,
                                    lock: true
                                });
                                d2.showModal();

                                var num = 3;
                                var timer3 = setInterval(function () {
                                    if (num == 0) {
                                        hideDialog4();
                                    }
                                    num--;
                                    $("#secondNum2").text(num);
                                }, 1000);
                                var timer4 = setTimeout(hideDialog4, 3000);
                                function hideDialog4() {
                                    clearInterval(timer3);
                                    clearTimeout(timer4);
                                    d2.close().remove();
                                    printVenue();
                                }

                                $('#doPrint').removeAttr('disabled');//
                            }
                        }
                    }
                });
            });
        });
        $(window).resize(function () {
            setIframeHeight();
        });

        /*设置iframe的高度*/
        function setIframeHeight() {
            var winH = $(window).height();
            var ifr = $("#book-iframe");
            var topH = $(".ticket-top").outerHeight();
            ifr.css("height", winH - topH);
//            $(".ticket-content").css("height", winH - topH);

            var layerBox = $("#ticket-layer");
            var layerTop = parseInt(($(window).height() - layerBox.height()) / 2);
            layerBox.css({"top": layerTop});
        }
        function showTicketMsg(title, msg) {
            var $body = $('body');
            var ticketBg = $('<div></div>').addClass("ticket-body-bg").appendTo($body);
            var html = '<div id="ticket-layer">' +
                '<div class="layer-info">' +
                '<div class="txt">' +
                '<h3>' + title + '</h3>' +
                '<p>' + msg + '</p></div>' +
                '<a class="btn-confirm">确定</a>' +
                '</div></div>';
            var layerBox = $(html).appendTo($body);
            var layerTop = parseInt(($(window).height() - layerBox.height()) / 2);
            layerBox.css({"top": layerTop});
            $(".btn-confirm").click(function () {
                $("#ticket-layer").remove();
                $(".ticket-body-bg").remove();
            });
        }
        function createCodeImage(ids,code) {

            //生成二维码图片
            var cardno = $("#"+ids+code).attr("data-val");
            if(code!=""){
                cardno=cardno+"****"+code;//原取票码+座位号  标识每张票里的二维码
            }
            $("#"+ids+code).html("");
            $("#"+ids+code).qrcode({
                render: "table",
                width: "140",
                height: "140",
                text: cardno
            });
        }

        //数字键盘
        $(function () {
            var cursorPos = 0;
            var cardNum = $("#orderValidateCode");
            var cardNumDom = cardNum.get(0);
            var cardNumVal = "";
            var cursorPos;
            $(".num-board").on("click", ".key", function () {
                var $this = $(this);
                var keyNum = keyboard.trim($this.html());
                cardNumVal = cardNum.val();
                var cardNumArr = cardNumVal.split("");
                cardNumArr.splice(cursorPos, 0, keyNum);
                cardNumVal = cardNumArr.join("");
                if (cardNumVal.length < 17) {
                    cardNum.val(cardNumVal);
                } else {
                    return false;
                }
                cursorPos++;
                keyboard.setCursorPosition(cardNum.get(0), cursorPos);
            });

            $("#btn-del").on("click", function () {
                var $this = $(this);
                cursorPos = keyboard.getPositionForInput(document.getElementById("orderValidateCode"));
                cardNumVal = cardNum.val();
                if (cursorPos > 0) {
                    var cardNumArr = cardNumVal.split("");
                    cardNumArr.splice(cursorPos - 1, 1);
                    cardNumVal = cardNumArr.join("");
                    cardNum.val(cardNumVal);
                    cursorPos--;
                    keyboard.setCursorPosition(cardNum.get(0), cursorPos);
                } else {
                    keyboard.setCursorPosition(cardNum.get(0), 0);
                }
            });
            $("#btn-clean").on("click", function () {
                $("#orderValidateCode").val("");
            });
            $("#orderValidateCode").on("click", function () {
                cursorPos = keyboard.getPositionForInput(document.getElementById("orderValidateCode"));
            });
        });

    </script>
</head>
<body>
<!--
<object id="jatoolsPrinter" classid="CLSID:B43D3361-D075-4BE2-87FE-057188254255"
    codebase="jatoolsPrinter.cab#version=5,7,0,0" width="0" height="0">
</object>
    -->
<!-- <div class="ticket-top">
    <div class="menu">

    </div>
</div> -->
<div class="header clearfix">
    <div class="container">
        <div class="logo">
            <img src="${basePath}/static/ticket/img/print/logo.png" width="255" height="119">
        </div>
        <%--<div class="nav">--%>
        <%--<a href="javascript:void(0)" class="active">取票</a>--%>
        <%--<a href="javascript:void(0)">订票</a>--%>
        <%--</div>--%>
        <%--<div class="user">--%>
        <%--<a href="javascript:void(0)">登录</a>--%>
        <%--</div>--%>
    </div>
</div>
<div class="ticket-content clearfix">
    <div class="get-ticket">
        <%--<img src="${basePath}/STATIC/img/print/ptlogoqp.png" style="width:111px;height:98px;margin:0px auto 32px;">--%>
        <%--<h1><span class="htitle">东莞市数字文化馆欢迎您</span></h1>--%>
        <!-- <span id="msg" style="color:red;font-size:18px;"></span> -->
        <div class="qpm clearfix"><span>取票码：</span><input type="text" id="orderValidateCode"/></div>
        <script type="text/javascript">
            var text = document.getElementById("orderValidateCode");
            text.onkeyup = function () {
                this.value = this.value.replace(/\D/g, '');
                if (text.value.length >= 16) {
                    var v = text.value;
                    $("#orderValidateCode").val(v.substring(0, 16));
                }
            }
        </script>
        <div class="num-board clear">
            <ul>
                <li class="key key1">1</li>
                <li class="key key2">2</li>
                <li class="key key3">3</li>
                <li class="key key4">4</li>
                <li class="key key5">5</li>
                <li class="key key6">6</li>
                <li class="key key7">7</li>
                <li class="key key8">8</li>
                <li class="key key9">9</li>
                <li class="key key10">0</li>
                <li class="btn key11"><input type="button" class="btn-del" id="btn-del" value="删除"/></li>
                <li class="btn key12"><input type="button" class="btn-clean" id="btn-clean" value="清空"/></li>
            </ul>
            <button type="submit" class="get-ticket-btn" id="doPrint">确定取票</button>
        </div>
        <!-- <input type="submit" value="确定&#13;&#10;取票" class="get-ticket-btn" />  -->
    </div>
    <div class="right-page">
        <img src="${basePath}/static/ticket/img/print/right-bg.png" width="418" height="496">
    </div>
    <%--<div class="msg"><i></i>如需帮助，请联系工作人员</div>--%>
</div>

<!--<div class="ticket-body-bg"></div>
<div id="ticket-layer">
    <div class="layer-info">
        <div class="txt">
            <h3>取票信息有误</h3>
            <p>对不起，您所定的票码有误，请检查后再次输入</p>
        </div>
        <a class="btn-confirm">确定</a>
    </div>
</div>-->

<!-- 活动打印 -->
<div class="piao" id="printPiao" style="display: none;"></div>
<!-- 场馆打印 -->
<div id="venue_block" style="display: none;"></div>
<script language="javascript" type="text/javascript">
    var LODOP; //声明为全局变量
    function printAct() {
        try{
            LODOP = getLodop(document.getElementById('LODOP'), document.getElementById('LODOP_EM'));
            var id = 'qrcodeImg';
            var printPiao=document.getElementById("printPiao").innerHTML;
            var strBodyStyle = "<style>" + document.getElementById("yangshi").innerHTML + "</style>";
            if(piaoInfo!=""){
                var str= piaoInfo.split(",");
                var sseat=seat.split(",");
                for(var i=0;str.length>i;i++){
                    var strFormHtml;
                    if(str[i]!=""){
                        if(printPiao.indexOf("[piaoInfo]")!=-1){
                            strFormHtml = strBodyStyle + "<body>" + printPiao.replace("[piaoInfo]",str[i])+ "</body>";
                            LODOP.PRINT_INIT("");
                            LODOP.SET_PRINT_PAGESIZE(3, 800, 280, "");
                            LODOP.SET_PRINT_STYLEA(1, "ItemType", 0);
                            LODOP.SET_PRINT_STYLEA(1, "FontName", "微软雅黑");
                            LODOP.SET_PRINT_STYLEA(1, "FontSize", 8);
                            if(strFormHtml.indexOf("[qrcodeImgdiv]")!=-1){
                                strFormHtml=strFormHtml.replace("[qrcodeImgdiv]",document.getElementById(id+sseat[i]).innerHTML);
                            }
                            LODOP.ADD_PRINT_HTM(0, 0, "99%", "100%", strFormHtml);
                            LODOP.PRINT();
                        }
                    }
                }
                $('#doPrint').removeAttr('disabled');
            }
            //LODOP.PREVIEW();
            $("#orderValidateCode").val("");
            piaoInfo="";
            seat="";
        } catch (e){
        }

        $('#doPrint').removeAttr('disabled');
    }


    function printVenue() {
        try{
            LODOP = getLodop(document.getElementById('LODOP'), document.getElementById('LODOP_EM'));
            LODOP.PRINT_INIT("");
            //     var strFormHtml = strBodyStyle + "<body>" + document.getElementById("venue_block").innerHTML + "</body>";
            LODOP.SET_PRINT_PAGESIZE(3, 800, 0, "");
            LODOP.SET_PRINT_STYLEA(1, "ItemType", 0);
            LODOP.SET_PRINT_STYLEA(1, "FontName", "微软雅黑");
            LODOP.SET_PRINT_STYLEA(1, "FontSize", 8);
            LODOP.ADD_PRINT_HTM(0, 0, "99%", "100%", document.getElementById("venue_block").innerHTML);
    //		LODOP.PREVIEW();
            LODOP.PRINT();
            $("#orderValidateCode").val('');
        }catch (e){
        }
        $('#doPrint').removeAttr('disabled');
    }
</script>

</body>
</html>