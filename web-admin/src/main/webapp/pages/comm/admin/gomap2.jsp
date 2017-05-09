<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setAttribute("basePath", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath());%>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no, width=device-width">
    <title>鼠标拾取地图坐标</title>
    <%@include file="/pages/comm/admin/header.jsp"%>
    <style>
        html, body {
            margin: 0;
            height: 100%;
            width: 100%;
            position: absolute;
        }

        #container {
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            bottom: 0;
            width: 100%;
            height: 100%;
        }

        .button-group {
            position: absolute;
            bottom: 20px;
            right: 20px;
            font-size: 12px;
            padding: 10px;
        }

        .button-group .button {
            height: 28px;
            line-height: 28px;
            background-color: #0D9BF2;
            color: #FFF;
            border: 0;
            outline: none;
            padding-left: 5px;
            padding-right: 5px;
            border-radius: 3px;
            margin-bottom: 4px;
            cursor: pointer;
        }
        .button-group .inputtext {
            height: 26px;
            line-height: 26px;
            border: 1px;
            outline: none;
            padding-left: 5px;
            padding-right: 5px;
            border-radius: 3px;
            margin-bottom: 4px;
            cursor: pointer;
        }
        #tip {
            background-color: #fff;
            padding-left: 10px;
            padding-right: 10px;
            position: absolute;
            font-size: 12px;
            right: 10px;
            top: 20px;
            border-radius: 3px;
            border: 1px solid #ccc;
            line-height: 30px;
        }
        .amap-info-content {
            font-size: 12px;
        }

        #myPageTop {
            position: absolute;
            top: 5px;
            right: 10px;
            background: #fff none repeat scroll 0 0;
            border: 1px solid #ccc;
            margin: 10px auto;
            padding:6px;
            font-family: "Microsoft Yahei", "微软雅黑", "Pinghei";
            font-size: 14px;
        }
        #myPageTop label {
            margin: 0 20px 0 0;
            color: #666666;
            font-weight: normal;
        }
        #myPageTop .column1 input {
            width: 250px;
        }
        #myPageTop .column2 input {
            width: 120px;
        }
        #myPageTop .column2{
            padding-left: 25px;
        }
    </style>
    <script type="text/javascript" src="http://webapi.amap.com/maps?v=1.3&key=39da37a97677fa883d5f0c5000176163&callback=whgMapInit"></script>
</head>
<body>
<div id="container"></div>

<script type="text/javascript">
    //全局对象
    var whgMap;//地图对象
    var placeSearch;//地址查询
    var autocomplete;//输入提示

    /**
     * 地图初始方法
     */
    function whgMapInit() {
        //显示地图
        var isxy = false;
        if($.isNumeric('${param.x}') && $.isNumeric('${param.x}')){
            isxy = true;
            whgMap = new AMap.Map("container", {
                resizeEnable: true,
                zoom: 20,
                center: [${param.x}, ${param.y}],
                keyboardEnable: false
            });
        }else{
            whgMap = new AMap.Map("container", {
                resizeEnable: true,
                zoom: 20,
                keyboardEnable: false
            });
        }
        AMap.plugin(['AMap.Geocoder', 'AMap.PlaceSearch'], function(){
            var geocoder = new AMap.Geocoder({});
            var marker = new AMap.Marker({
                map:whgMap,
                bubble:true
            })
            //构造查询类
            placeSearch = new AMap.PlaceSearch({
                map: whgMap
            });
        });

        //根据地址查询
        var tipinput = '${param.addr}';
        if(!isxy && tipinput != ''){
            placeSearch.search(tipinput, function(status, result) {
                if(status == 'complete' && result.info == 'OK' && result.poiList.pois.length > 0){
                    whgMap.setZoom(20);
                    whgMap.setCenter( (result.poiList.pois)[0].location);
                }
            });
        }
    }
</script>
</body>
</html>