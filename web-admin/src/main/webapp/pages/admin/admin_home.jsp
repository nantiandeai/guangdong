<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
    request.setAttribute("basePath", basePath);
%>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>数字文化馆后台管理系统</title>
    <%@include file="/pages/comm/admin/header.jsp"%>
    <script src="${basePath}/static/admin/js/highcharts.src.js"></script>
    <%--<script src="${basePath}/static/admin/js/highcharts.3d.js"></script>--%>
    <script src="${basePath}/static/admin/js/countUp.min.js"></script>
    <link href="${basePath }/static/admin/css/admin.css" rel="stylesheet">
    <style>
        /*#data-sys-1, #sliders {
            min-width: 310px;
            max-width: 1200px;
            margin: 0 auto;
        }
        #data-sys-1 {
            height: 400px;
        }*/
    </style>
</head>
<%--style="background:url(${basePath}/static/admin/img/demo.jpg) no-repeat 50% 50%"--%>
<body>
<div class="home-main">
    <div class="top-msg-cont" data-sing-type="true" style="display: none">8</div>
    <ul class="data-list-cont">
        <li>
            <p id="d-n-0">0</p>
            <h2>会员总数</h2>
        </li>
        <li>
            <p id="d-n-1">0</p>
            <h2>培训总数</h2>
        </li>
        <li>
            <p id="d-n-2">0</p>
            <h2>活动总数</h2>
        </li>
        <li class="last">
            <p id="d-n-3">0</p>
            <h2>场馆总数</h2>
        </li>
    </ul>
    <div class="data-info-cont-row">
        <div class="leftCont">
            <div id="data-sys-1"></div>
        </div>
        <div class="rightCont">
            <div id="data-sys-2"></div>
        </div>
    </div>
    <div class="data-info-cont-row">
        <div class="leftCont">
            <div id="data-sys-3"></div>
        </div>
        <div class="rightCont">
            <div id="data-sys-4"></div>
        </div>
    </div>
    <div class="data-info-cont-row">
        <div id="data-sys-5"></div>
        <%--<div class="leftCont">--%>
            <%--<div id="data-sys-5"></div>--%>
        <%--</div>--%>
    </div>
    <div class="data-info-cont-row">
        <div id="data-sys-6"></div>
        <%--<div class="leftCont">--%>
        <%--<div id="data-sys-5"></div>--%>
        <%--</div>--%>
    </div>
</div>

<script type="text/javascript">

    //系统消息提示声音
    function singOpen(){
        if($(".top-msg-cont").attr("data-sing-type")){
            if($(".top-msg-cont").html() != "" && parseInt($(".top-msg-cont").html()) > 0){
                var audioElement = document.createElement('audio');
                audioElement.setAttribute('src', '${basePath }/static/admin/img/ring.mp3');
                audioElement.setAttribute('autoplay', 'autoplay');
                $.get();
                //IE8不支持addEventListener
                if(navigator.appVersion.match(/8./i)!="8."){
                    audioElement.addEventListener("load", function() {
                        audioElement.play();
                    }, true);
                }
            }else{
                $(".top-msg-cont").hide();
            }
        }
    }

    $(function () {
        // Make monochrome colors and set them as default for all pies
        Highcharts.getOptions().plotOptions.pie.colors = (function () {
            var colors = [],
                    base = Highcharts.getOptions().colors[0],
                    i;
            for (i = 0; i < 10; i += 1) {
                // Start out with a darkened base color (negative brighten), and end
                // up with a much brighter color
                colors.push(Highcharts.Color(base).brighten((i - 3) / 30).get());
            }
            return colors;
        }());

        //图型列表
        var xArr1 = ['文艺范诗歌剧场《随黄公望游富春山》', '我们和“星星”在一起', '大型民族歌舞剧《康定情歌》', '“魔指爷爷的古典万花筒”2017钢琴巡演', '东莞市文化志愿者大舞台', '新中式·探索——冯璐服饰艺术展', '久石让&宫崎骏经典动漫作品视听音乐会', '2017年东城新春粤剧黄金周活动', '“我们在黄旗山下”东城街道社区合唱比赛', '东莞市文化志愿者大舞台第119期'];
        var xArr2 = [];
        var yArr1 = [103, 100, 90, 88, 84, 80, 50, 45, 44, 36];
        for (var c in xArr1) {
            if (xArr1[c].length > 5) {
                xArr2.push(xArr1[c].substr(0, 5) + '..')
            } else {
                xArr2.push(xArr1[c])
            }
        }

        var chart = new Highcharts.Chart({
            chart: {
                renderTo: 'data-sys-2',
                type: 'column',
                options3d: {
                    enabled: true,
                    alpha: 10,
                    beta: 25,
                    depth: 70,
                    viewDistance: 25
                }
            },
            title: {
                text: '广东省热门活动统计TOP10'
            },
            plotOptions: {
                column: {
                    depth: 25
                }
            },
            xAxis: {
                categories: xArr2
            },
            series: [{
                data: yArr1
            }]
        });

        //图型列表
        var xArr1 = ['文艺范诗歌剧场《随黄公望游富春山》', '我们和“星星”在一起', '大型民族歌舞剧《康定情歌》', '“魔指爷爷的古典万花筒”2017钢琴巡演', '东莞市文化志愿者大舞台', '新中式·探索——冯璐服饰艺术展', '久石让&宫崎骏经典动漫作品视听音乐会', '2017年东城新春粤剧黄金周活动', '“我们在黄旗山下”东城街道社区合唱比赛', '东莞市文化志愿者大舞台第119期'];
        var yArr1 = [70, 60, 45, 23, 21, 20, 19, 18, 17, 15];
        var xArr2=[];
        for (var i in xArr1){
            if(xArr1[i].length>5){
                xArr2.push(xArr1[i].substr(0,5)+"..");
            }else{
                xArr2.push(xArr1[i]);
            }
        }
        var chart = new Highcharts.Chart({
            chart: {
                renderTo: 'data-sys-4',
                type: 'column',
                options3d: {
                    enabled: true,
                    alpha: 10,
                    beta: 25,
                    depth: 70,
                    viewDistance: 25
                }
            },
            title: {
                text: '广东省热门培训统计TOP10'
            },
            plotOptions: {
                column: {
                    depth: 25
                }
            },
            xAxis: {
                categories: xArr2
            },
            series: [{
                data:yArr1
            }]
        });

        //培训根据文化馆分类ajax
        $.ajax({
            url: '${basePath}/admin/traGroupByCult',
            success: function (data) {
                var xArr = [], yArr = [];
                for (var i in data) {
                    xArr.push(data[i].name);
                    yArr.push(parseInt(data[i].cnt));
                }
                var chart = new Highcharts.Chart({
                    chart: {
                        renderTo: 'data-sys-5',
                        type: 'column',
                        options3d: {
                            enabled: true,
                            alpha: 0,
                            beta: 0,
                            depth: 20,
                            viewDistance: 25
                        }
                    },
                    title: {
                        text: '培训根据文化馆统计'
                    },
                    plotOptions: {
                        column: {
                            depth: 25
                        }
                    },
                    xAxis: {
                        categories: xArr
                    },
                    series: [{
                        data: yArr
                    }]

                });
            }
        });

        //培训根据艺术类型分类ajax
        $.ajax({
            url : '${basePath}/admin/traGroupByArt',
            success: function (data) {
                var xArr = [], yArr = [];
                for (var i in data) {
                    xArr.push(data[i].name);
                    yArr.push(parseInt(data[i].cnt));
                }
                var chart = new Highcharts.Chart({
                    chart: {
                        renderTo: 'data-sys-6',
                        type: 'column',
                        options3d: {
                            enabled: true,
                            alpha: 0,
                            beta: 0,
                            depth: 20,
                            viewDistance: 25
                        }
                    },
                    title: {
                        text: '培训根据艺术类型统计'
                    },
                    plotOptions: {
                        column: {
                            depth: 25
                        }
                    },
                    xAxis: {
                        categories: xArr
                    },
                    series: [{
                        data: yArr
                    }]

                });
            }
        });

        //广东省活动数量统计饼图ajax
        $.ajax({
            url : '${basePath}/admin/actGroupByArt',
            success: function (data) {
                var chartArr = [];
                for (var i in data) {
                    chartArr.push({name: data[i].name, y: parseInt(data[i].cnt)});
                }
                Highcharts.chart('data-sys-1', {
                    chart: {
                        plotBackgroundColor: null,
                        plotBorderWidth: null,
                        plotShadow: false,
                        type: 'pie'
                    },
                    title: {
                        text: '广东省活动数量统计'
                    },
                    tooltip: {
                        pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
                    },
                    plotOptions: {
                        pie: {
                            allowPointSelect: true,
                            cursor: 'pointer',
                            dataLabels: {
                                enabled: true,
                                format: '<b>{point.name}</b>: {point.percentage:.1f} %',
                                style: {
                                    color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                                }
                            }
                        }
                    },
                    series: [{
                        name: 'Brands',
                        colorByPoint: true,
                        data:chartArr
                    }]
                });
            }
        });

        //广东省培训数量统计饼图ajax
        $.ajax({
            url : '${basePath}/admin/traGroupByArt',
            success: function (data) {
                var chartArr = [];
                for (var i in data) {
                    chartArr.push({name: data[i].name, y: parseInt(data[i].cnt)});
                }
                Highcharts.chart('data-sys-3', {
                    chart: {
                        plotBackgroundColor: null,
                        plotBorderWidth: null,
                        plotShadow: false,
                        type: 'pie'
                    },
                    title: {
                        text: '广东省培训数量统计'
                    },
                    tooltip: {
                        pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
                    },
                    plotOptions: {
                        pie: {
                            allowPointSelect: true,
                            cursor: 'pointer',
                            dataLabels: {
                                enabled: true,
                                format: '<b>{point.name}</b>: {point.percentage:.1f} %',
                                style: {
                                    color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                                }
                            }
                        }
                    },
                    series: [{
                        name: 'Brands',
                        colorByPoint: true,
                        data:chartArr
                    }]
                });
            }
        });

        //首页后台会员总数，培训总数，活动总数，场馆总数ajax
        $.ajax({
            url : '${basePath}/admin/srchList',
            success: function (data) {
                start(data[0]);
            }
        });

    });

    //数字加载
    var options = {
        useEasing : true,
        useGrouping : true,
        separator : ',',
        decimal : '.',
        prefix : '',
        suffix : ''
    };

    function start(obj) {
        var data_1 = new CountUp("d-n-0", 0, obj.memberTotal, 0, 2.5, options);
        var data_2 = new CountUp("d-n-1", 0, obj.traTotal, 0, 2.5, options);
        var data_3 = new CountUp("d-n-2", 0, obj.actTotal, 0, 2.5, options);
        var data_4 = new CountUp("d-n-3", 0, obj.venTotal, 0, 2.5, options);

        data_1.start();
        data_2.start();
        data_3.start();
        data_4.start();
        //singOpen();
    }

</script>
</body>
</html>
