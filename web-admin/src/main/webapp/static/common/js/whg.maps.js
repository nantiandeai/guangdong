/**
 * Created by wangxl on 2017/3/28.
 */
WhgMap = (function () {
    /**
     * 初始根据地址取坐标组件
     * @param options
     * @private
     */
    function _initMap(options) {
        //对象参数
        this._options = $.extend({},{
            basePath: '',
            addrFieldId: 'addrFieldId',
            xpointFieldId: 'xpointFieldId',
            ypointFieldId: 'ypointFieldId',
            getPointBtnId: 'getPointBtnId'
        }, options);
    }

    /**
     * 绑定点击事件
     * @private
     */
    _initMap.prototype._bindClick = function () {
        //注册取坐标事件
        var thisObj = this;
        $('#'+this._options.getPointBtnId).off('click').on("click", function (e) {
            e.preventDefault();
            thisObj._click();
            return false;
        })
    }



    /**
     * 弹出取坐标页面
     * @private
     */
    _initMap.prototype._click = function () {
        var thisObj = this;
        var id_dialog = "whgDialog-map";
        var xval = $('#'+this._options.xpointFieldId).val();
        var yval = $('#'+this._options.ypointFieldId).val();
        var fafa = this._options.addrFieldId;
        var fbfb = this._options.xpointFieldId;
        var fcfc = this._options.ypointFieldId;
        var cut_img_url = this._options.basePath+"/comm/gomap?fa="+fafa+"&fb="+fbfb+"&fc="+fcfc+"&da="+id_dialog+"&x="+xval+"&y="+yval+"&addr="+encodeURIComponent($('#'+this._options.addrFieldId).val());
        if( $('#'+id_dialog).size() > 0 ){
            $('#'+id_dialog).dialog('destroy');
            $('#'+id_dialog).remove();
        }
        var dialogHTML = '<div id="'+id_dialog+'" style="overflow:hidden"></div>';
        $(dialogHTML).appendTo($("body"));
        var _iframe = '<iframe id="iframe_'+id_dialog+'" name="iframe_'+id_dialog+'" scrolling="auto" frameborder="0"  src="' + cut_img_url+ '" style="width:100%;height:100%;"></iframe>';

        var _height = $(window).height() < 680 ? $(window).height() -50 : 680;
        $('#'+id_dialog).dialog({
            title: '查询坐标',
            closable: true,
            width: '80%',
            height: ''+_height,
            border: false,
            modal: true,
            content: _iframe
        });
    }

    /**
     * 弹出层显示地图
     * @param addr 地址
     * @param x x坐标
     * @param y y坐标
     * @private
     */
    function _initOpenMap(addr, x, y) {
        _includeFile([basePath+'/static/admin/easyui/themes/bootstrap/easyui.css', basePath+'/static/admin/easyui/jquery.easyui.min.js']);
        window.setTimeout(function () {
            var id_dialog = "whgDialog-map-front";
            var cut_img_url = basePath+"/comm/gomap2?x="+x+"&y="+y+"&addr="+encodeURIComponent(addr);
            if( $('#'+id_dialog).size() > 0 ){
                $('#'+id_dialog).dialog('destroy');
                $('#'+id_dialog).remove();
            }
            var dialogHTML = '<div id="'+id_dialog+'" style="overflow:hidden"></div>';
            $(dialogHTML).appendTo($("body"));
            var _iframe = '<iframe id="iframe_'+id_dialog+'" name="iframe_'+id_dialog+'" scrolling="auto" frameborder="0"  src="' + cut_img_url+ '" style="width:100%;height:100%;"></iframe>';

            var _height = $(window).height() < 680 ? $(window).height() -50 : 680;
            $('#'+id_dialog).dialog({
                title: '查看地址',
                closable: true,
                width: '60%',
                height: ''+_height,
                border: false,
                modal: true,
                content: _iframe
            });
        }, 1000);
    }

    /**
     *
     * @param addr 地址
     * @param id 显示地图的class 此元素必须有以下三个属性: map_addr="" map_x="" map_y=""
     * @private
     */
    var __x;
    var __y;
    var __addr
    var __id;
    function _initShowMap(id, addr, x, y) {
        __x = x;
        __y = y;
        __addr = addr;
        __id = id;
        window.whgMapInit_dg = _showMap;
        _includeFile('http://webapi.amap.com/maps?v=1.3&key=39da37a97677fa883d5f0c5000176163&callback=whgMapInit_dg');
    }

    function _showMap() {
        //显示地图
        var isxy = false;
        if($.isNumeric(__x) && $.isNumeric(__y)){
            isxy = true;
            whgMap = new AMap.Map(__id, {
                resizeEnable: true,
                zoom: 20,
                center: [__x, __y],
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
        var tipinput = __addr;
        if(!isxy && tipinput != ''){
            placeSearch.search(tipinput, function(status, result) {
                if(status == 'complete' && result.info == 'OK' && result.poiList.pois.length > 0){
                    whgMap.setZoom(20);
                    whgMap.setCenter( (result.poiList.pois)[0].location);
                }
            });
        }
    }

    /**
     * 动态引入JS|CSS文件
     * @param file
     * @private
     */
    function _includeFile(file){
        var files = typeof file == "string" ? [file] : file;
        for (var i = 0; i < files.length; i++){
            var name = files[i].replace(/^\s|\s$/g, "");
            var att = name.split('.');
            var ext = att[att.length - 1].toLowerCase();
            var isCSS = ext == "css";
            var tag = isCSS ? "link" : "script";
            var attr = isCSS ? " type='text/css' rel='stylesheet' " : " language='javascript' type='text/javascript' ";
            var link = (isCSS ? "href" : "src") + "='" + name + "'";
            if ($(tag + "[" + link + "]").length == 0){
                $("<" + tag + attr + link + "></" + tag + ">").appendTo("head");
            }
        }
    }

    return {
        /**
         * 根据地址获取xy坐标
         * @param options
         * @returns {_initMap}
         */
        init: function (options) {
            var initMap = new _initMap(options);
                initMap._bindClick();
            return initMap;
        }

        /**
         * 根据位置坐标或者地址弹出层显示地图
         * 使用方式：<a class="enter-zt" href="javascript:void(0)" onclick="WhgMap.openMap('湖南省长沙市云栖谷小区', '112.984162', '28.194435')">查看地址</a>
         * @param addr 地址
         * @param x x坐标
         * @param y y坐标
         */
        ,openMap: function (addr, x, y) {
            _initOpenMap(addr, x, y);
        }

        /**
         *  根据位置坐标或者地址显示地图
         *  使用方式：WhgMap.showMap('showWhgMap', '湖南省长沙市云栖谷小区', '112.984162', '28.194435');
         * @param id 显示地图的DIV容器的ID
         * @param addr 地址
         * @param x x坐标
         * @param y y坐标
         */
        ,showMap: function (id, addr, x, y) {
            _initShowMap(id, addr, x, y);
        }
    }
})();
