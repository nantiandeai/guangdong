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
<div id="myPageTop">
    <table>
        <tr>
            <td>
                <label>请输入地址：</label>
            </td>
            <td class="column2">
                <label>鼠标左键在地图上单击获取坐标：</label>
            </td>
        </tr>
        <tr>
            <td class="column1">
                <input type="text" placeholder="请输入地址" id="tipinput" value="${param.addr}">
            </td>
            <td class="column2">
                x坐标：<input type="text" id="lnglatX" value="${param.x}">&nbsp;&nbsp;
                y坐标：<input type="text" id="lnglatY" value="${param.y}"> &nbsp;&nbsp;&nbsp;&nbsp;
                <a href="javascript:void(0)" class="easyui-linkbutton" id="getAddrPointBtn">确&nbsp;&nbsp;&nbsp;&nbsp;定</a>
            </td>
        </tr>
    </table>
</div>

<!-- 需要返写值的三个元素 -->
<input type="hidden" id="addrField" value="${param.fa}">
<input type="hidden" id="xpointField" value="${param.fb}">
<input type="hidden" id="ypointField" value="${param.fc}">
<input type="hidden" id="dialogId" value="${param.da}" >

<script type="text/javascript">
    //全局对象
    var whgMap;//地图对象
    var placeSearch;//地址查询
    var autocomplete;//输入提示
    var searchOK = false;//查询成功

    /**
     * 地图初始方法
     */
    function whgMapInit() {
        //显示地图
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


        //加载插件 工具条/自动完成
        AMap.plugin(['AMap.ToolBar', 'AMap.Autocomplete', 'AMap.PlaceSearch'],
            function () {
                //工具条
                whgMap.addControl(new AMap.ToolBar());

                //构造查询类
                placeSearch = new AMap.PlaceSearch({
                    map: whgMap
                });

                var marker = new AMap.Marker({
                    map:whgMap,
                    bubble:true
                })

                //关键字搜索
                autocomplete = new AMap.Autocomplete({
                    input: "tipinput"
                });
                AMap.event.addListener(autocomplete, "select", function (e) {
                    if (e.poi && e.poi.location) {
                        whgMap.setZoom(20);
                        whgMap.setCenter(e.poi.location);
                    }
                });
            }
        );

        //为地图注册click事件获取鼠标点击出的经纬度坐标
        whgMap.on('click', function(e) {
            document.getElementById("lnglatX").value = e.lnglat.getLng();
            document.getElementById("lnglatY").value = e.lnglat.getLat();
        });

        //如果地址-按地址定位
        var tipinput = document.getElementById("tipinput").value;
        if(tipinput != null){
            placeSearch.search(tipinput, function(status, result) {
                if(status == 'complete' && result.info == 'OK' && result.poiList.pois.length > 0){
                    searchOK = true;
                    whgMap.setZoom(20);
                    whgMap.setCenter( (result.poiList.pois)[0].location);
                }
            });
        }


    }

    //确定时，获取坐标值
    $('#getAddrPointBtn').on('click', function () {
        var lnglatX = document.getElementById("lnglatX").value;
        var lnglatY = document.getElementById("lnglatY").value;
        var tipinput = document.getElementById("tipinput").value;
        if(lnglatX != '' && lnglatY != ''){
            var addrFieldId = $('#addrField').val();
            var xpointField = $('#xpointField').val();
            var ypointField = $('#ypointField').val();
            var dialogId = $('#dialogId').val();

            window.parent.$('#'+addrFieldId).textbox('setValue', tipinput);
            window.parent.$('#'+xpointField).numberbox('setValue', lnglatX);
            window.parent.$('#'+ypointField).numberbox('setValue', lnglatY);
            window.parent.$('#'+dialogId).dialog('close');
        }else{
            $.messager.alert('提示', '请输入有效的地址，然后用鼠标点击地图中的位置获取坐标！');
        }
    });

    window.setTimeout(function () {
        if(!searchOK){
            var tipinput = document.getElementById("tipinput").value;
            if(tipinput != null){
                placeSearch.search(tipinput, function(status, result) {
                    if(status == 'complete' && result.info == 'OK' && result.poiList.pois.length > 0){
                        searchOK = true;
                        whgMap.setZoom(20);
                        whgMap.setCenter( (result.poiList.pois)[0].location);
                    }
                });
            }
        }
    }, 1000);
</script>
</body>
</html>