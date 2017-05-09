/**
 * 获得关键字|标签|分类|文化品牌|状态|文化馆六类数据
 * author: wangxinlin
 * version: 1-201703
 */
WhgSysData = (function(){
    //是否初始
    var _init_type,_init_key,_init_tag,_init_brand, _init_cult;

    //获取分类数据
    var _typeData = {};
    function _getTypeData(type) {
        if(!_init_type){
            $.ajax({
                async: false,
                cache: false,
                url: getFullUrl("/comm/type/srchList"),
                success: function(data){
                    _init_type = true;
                    if(data && data.success == "1" && typeof data["data"] != "undefined" ){
                        if($.isArray(data.data)){
                            for(var i=0; i<(data.data).length; i++){
                                var __data = (data.data)[i];
                                var __id = __data.id;
                                var __name = __data.name;
                                var __type = __data.type+"";
                                if(typeof _typeData[__type] == 'undefined'){
                                    _typeData[__type] = [];
                                }
                                _typeData[__type].push({"id":__id, "text":__name});
                            }
                        }
                    }
                }
            });
        }
        return (typeof _typeData[type] != 'undefined') ? _typeData[type] : [];
    }

    //获取标签数据
    var _keyData = {};
    function _getKeyData(type) {
        if(!_init_key){
            $.ajax({
                async: false,
                cache: false,
                url: getFullUrl("/comm/key/srchList"),
                success: function(data){
                    _init_key = true;
                    if(data && data.success == "1" && typeof data["data"] != "undefined" ){
                        if($.isArray(data.data)){
                            for(var i=0; i<(data.data).length; i++){
                                var __data = (data.data)[i];
                                var __id = __data.id;
                                var __name = __data.name;
                                var __type = __data.type+"";
                                if(typeof _keyData[__type] == 'undefined'){
                                    _keyData[__type] = [];
                                }
                                _keyData[__type].push({"id":__id, "text":__name});
                            }
                        }
                    }
                }
            });
        }
        return (typeof _keyData[type] != 'undefined') ? _keyData[type] : [];
    }

    //获取关键字数据
    var _tagData = {};
    function _getTagData(type) {
        if(!_init_tag){
            $.ajax({
                async: false,
                cache: false,
                url: getFullUrl("/comm/tag/srchList"),
                success: function(data){
                    _init_tag = true;
                    if(data && data.success == "1" && typeof data["data"] != "undefined" ){
                        if($.isArray(data.data)){
                            for(var i=0; i<(data.data).length; i++){
                                var __data = (data.data)[i];
                                var __id = __data.id;
                                var __name = __data.name;
                                var __type = __data.type+"";
                                if(typeof _tagData[__type] == 'undefined'){
                                    _tagData[__type] = [];
                                }
                                _tagData[__type].push({"id":__id, "text":__name});
                            }
                        }
                    }
                }
            });
        }
        return (typeof _tagData[type] != 'undefined') ? _tagData[type] : [];
    }

    //获取状态数据
    var _stateDate = {};
    function _getStateData(type) {
        if(typeof _stateDate[type] == 'undefined'){
            _stateDate[type] = [];//[{"id":"", "text":"全部"}];
            $.ajax({
                async: false,
                cache: false,
                url: getFullUrl("/comm/state/srchList/"+type),
                success: function(data){
                    if(data && data.success == "1" && typeof data["data"] != "undefined" ){
                        if($.isArray(data.data)){
                            for(var i=0; i<(data.data).length; i++){
                                var __data = (data.data)[i];
                                _stateDate[type].push({"id":__data.value, "text":__data.name});
                            }
                        }
                    }
                }
            });
        }
        return _stateDate[type];
    }

    //获取品牌数据
    var _brandData = [];
    function _getBrandData() {
        if(!_init_brand){
            $.ajax({
                async: false,
                cache: false,
                url: getFullUrl("/comm/brand/srchList/"),
                success: function(data){
                    _init_brand = true;
                    if(data && data.success == "1" && typeof data["data"] != "undefined" ){
                        if($.isArray(data.data)){
                            for(var i=0; i<(data.data).length; i++){
                                var __data = (data.data)[i];
                                _brandData.push({"id":__data.id, "text":__data.name});
                            }
                        }
                    }
                }
            });
        }
        return _brandData;
    }

    //获取文化馆数据
    var _cultData = [];
    function _getCultData() {
        if(!_init_cult){
            $.ajax({
                async: false,
                cache: false,
                url: getFullUrl("/comm/cult/srchList/"),
                success: function(data){
                    _init_cult = true;
                    _cultData.push( {"id":"TOP", "text":"总馆"} );
                    if(data && data.success == "1" && typeof data["data"] != "undefined" ){
                        if($.isArray(data.data)){
                            for(var i=0; i<(data.data).length; i++){
                                var __data = (data.data)[i];
                                _cultData.push({"id":__data.id, "text":__data.name});
                            }
                        }
                    }
                }
            });
        }
        return _cultData;
    }

    return {
        /**
         * 根据ID获取Text
         * @param val ID
         * @param map Text
         */
        FMT: function (val, data) {
            if(typeof data != 'undefined' && $.isArray(data)){
                for(var i=0; i<data.length; i++){
                    if(data[i].id == val){
                        val = data[i].text;
                    }
                }
            }
            return val;
        }
        
        /**
         * 获取分类数据
         * @param type
         */
        ,getTypeData: function (type) {
            return _getTypeData(type);
        }

        /**
         * 获标签数据
         * @param type
         */
        ,getTagData: function (type) {
            return _getTagData(type);
        }

        /**
         * 获取关键字数据
         * @param type
         */
        ,getKeyData: function (type) {
            return _getKeyData(type);
        }

        /**
         * 获取状态数据
         * @param type
         */
        ,getStateData: function (type) {
            return _getStateData(type);
        }

        /**
         * 获取文化品牌数据
         */
        ,getBrandData: function () {
            return _getBrandData();
        }

        /**
         * 获取文化馆数据
         */
        ,getCultData: function () {
            return _getCultData();
        }
    }
})();

/**
 * 控制端公共方法
 * @type {{optFMT, stateFMT, delStateFMT, bmStateFMT, bizStateFMT, artTypeFMT, venueTypeFMT, roomTypeFMT, activityTypeFMT, trainTypeFMT, venueTagFMT, roomTagFMT, activityTagFMT, trainTagFMT, zxTagFMT, venueKeyFMT, roomKeyFMT, activityKeyFMT, trainKeyFMT, zxKeyFMT}}
 */
WhgComm = (function(){
    //打开Dialog的ID
    var __WhgDialog4EditId = 'WhgDialog4Edit';

    return {
        search: function (dgId, tbId) {
            var dgId = dgId || '#whgdg';
            if (!dgId.match(/^[#\.]/)){dgId = "#"+dgId;}
            var tbId = tbId || 'whgdg-tb-srch';
            if (!tbId.match(/^[#\.]/)){tbId = "#"+tbId;}

            var dgObj = $(dgId);
            var tbObj = $(tbId);

            var _queryParams = dgObj.datagrid('options').queryParams || {};
            tbObj.find('input[name]').each(function(){
                var propName = $(this).attr('name');
                if($(this).val() != ''){
                    _queryParams[$(this).attr('name')] = $(this).val();
                }else{
                    delete _queryParams[propName];
                }
            });
            dgObj.datagrid({
                url: dgObj.datagrid('options').url,
                queryParams: _queryParams
            });
        }

        ,FMTOpt: function(val, rowData, index){
            var optDivId = this.optDivId;
            if (!optDivId.match(/^[#\.]/)){
                optDivId = "#"+optDivId;
            }
            var optDiv = $(optDivId);
            if (optDiv.size() == 0) return "";

            //按钮是否显示控制
            optDiv.find('a').each(function(){
                var _show = true;

                //根据validKey控制显示
                var validKey = $(this).attr("validKey");
                $(this).linkbutton({ plain: true });
                if(typeof validKey != "undefined"){
                    var validVal = $(this).attr("validVal");
                    var vvl = validVal.split(/,\s*/);
                    $(this).linkbutton('disable');
                    _show = false;
                    for(var k in vvl){
                        var v = vvl[k];
                        if (rowData[validKey] == v){
                            $(this).linkbutton('enable');
                            _show = true;
                            break;
                        }
                    }
                }

                //根据验证函数判断是否显示按钮
                var validFun = $(this).attr("validFun");
                if(typeof validFun != "undefined"){
                    _show = eval(validFun+'('+index+')');
                    if(_show){
                        $(this).linkbutton('enable');
                    }else{
                        $(this).linkbutton('disable');
                    }
                }

                //设置属性
                var prop = $(this).attr("prop");
                var propVal = '';
                if(typeof prop != "undefined"){
                    propVal = rowData[prop];
                }

                //注册点击事件
                var method = $(this).attr("method");
                if(_show && method){
                    $(this).attr("onClick", method+"("+index+", '"+propVal+"');");
                }else{
                    $(this).removeAttr("onClick");
                }
            });
            return optDiv.html();
        }

        ,FMTTreeGridOpt: function(val, rowData, index){
            index = rowData.id;//treeGrid，没有index,只有rowData对象
            var optDivId = this.optDivId;
            if (!optDivId.match(/^[#\.]/)){
                optDivId = "#"+optDivId;
            }
            var optDiv = $(optDivId);
            if (optDiv.size() == 0) return "";

            //按钮是否显示控制
            optDiv.find('a').each(function(){
                var _show = true;

                //根据validKey控制显示
                var validKey = $(this).attr("validKey");
                $(this).linkbutton({ plain: true });
                if(typeof validKey != "undefined"){
                    var validVal = $(this).attr("validVal");
                    var vvl = validVal.split(/,\s*/);
                    $(this).linkbutton('disable');
                    _show = false;
                    for(var k in vvl){
                        var v = vvl[k];
                        if (rowData[validKey] == v){
                            $(this).linkbutton('enable');
                            _show = true;
                            break;
                        }
                    }
                }

                //根据验证函数判断是否显示按钮
                var validFun = $(this).attr("validFun");
                if(typeof validFun != "undefined"){
                    _show = eval(validFun+'('+index+')');
                    if(_show){
                        $(this).linkbutton('enable');
                    }else{
                        $(this).linkbutton('disable');
                    }
                }

                //设置属性
                var prop = $(this).attr("prop");
                var propVal = '';
                if(typeof prop != "undefined"){
                    propVal = rowData[prop];
                }

                //注册点击事件
                var method = $(this).attr("method");
                if(_show && method){
                    $(this).attr("onClick", method+"("+index+", '"+propVal+"');");
                }else{
                    $(this).removeAttr("onClick");
                }
            });
            return optDiv.html();
        }
        
        ,FMTImg: function (val) {
            if(!val) return val;
            return '<img src="'+WhgComm.getImgServerAddr()+val+'" style="width:60px; height:40px;"/>';
        }

        ,getImg750_500: function (imgAddr) {
            if(imgAddr && imgAddr.length > 10){
                var idx = imgAddr.lastIndexOf(".");
                if(idx !== -1){
                    imgAddr = imgAddr.substring(0, idx)+"_750_500"+imgAddr.substring(idx);
                }
            }
            return imgAddr;
        }

        ,getImg300_200: function (imgAddr) {
            if(imgAddr && imgAddr.length > 10){
                var idx = imgAddr.lastIndexOf(".");
                if(idx !== -1){
                    imgAddr = imgAddr.substring(0, idx)+"_300_200"+imgAddr.substring(idx);
                }
            }
            return imgAddr;
        }

        ,getImg740_555: function (imgAddr) {
            if(imgAddr && imgAddr.length > 10){
                var idx = imgAddr.lastIndexOf(".");
                if(idx !== -1){
                    imgAddr = imgAddr.substring(0, idx)+"_740_555"+imgAddr.substring(idx);
                }
            }
            return imgAddr;
        }

        ,FMTDate: function (val) {
            if(!val) return val;
            return new Date(val).Format("yyyy-MM-dd");
        }

        ,FMTDateTime: function (val) {
            if(!val) return val;
            return new Date(val).Format("yyyy-MM-dd hh:mm:ss");
        }

        ,FMTTime: function (val) {
            if(isNaN(val)) return val;
            return new Date(val).Format("hh:mm:ss");
        }

        ,FMTState: function (val) {
            return WhgSysData.FMT(val, WhgComm.getState());
        }

        ,getState: function () {
            return WhgSysData.getStateData("EnumState");
        }

        ,FMTDelState: function(val){
            return WhgSysData.FMT(val, WhgComm.getDelState());
        }

        ,getDelState: function () {
            return WhgSysData.getStateData("EnumDelState");
        }

        ,FMTBmState: function (val) {
            return WhgSysData.FMT(val, WhgComm.getBmState());
        }

        ,getBmState: function () {
            return WhgSysData.getStateData("EnumBMState");
        }

        ,FMTBizState: function (val,idx) {
            if(1 == idx.delstate){
                return "已回收";
            }
            return WhgSysData.FMT(val, WhgComm.getBizState());
        }

        ,getBizState: function () {
            return WhgSysData.getStateData("EnumBizState");
        }
        
        ,FMTBrand: function(val){
        	return WhgSysData.FMT(val, WhgComm.getBrand());
        }
        
        ,getBrand: function(){
        	return WhgSysData.getBrandData();
        }

        ,FMTCult: function (val) {
            return WhgSysData.FMT(val, WhgComm.getCult());
        }

        ,getCult: function () {
            return WhgSysData.getCultData();
        }

        ,FMTArtType: function (val) {
            return WhgSysData.FMT(val, WhgComm.getArtType());
        }

        ,getArtType: function () {
            return WhgSysData.getTypeData("1");//1代表艺术分类
        }

        ,FMTVenueType: function (val) {
            return WhgSysData.FMT(val, WhgComm.getVenueType());
        }

        ,getVenueType: function () {
            return WhgSysData.getTypeData("2");//2代表场馆分类
        }

        ,FMTRoomType: function (val) {
            return WhgSysData.FMT(val, WhgComm.getRoomType());
        }

        ,getRoomType: function () {
            return WhgSysData.getTypeData("3");//3代表活动室分类
        }

        ,FMTActivityType: function(val){
            return WhgSysData.FMT(val, WhgComm.getActivityType());
        }

        ,getActivityType: function () {
            return WhgSysData.getTypeData("4");//4代表活动分类
        }

        ,FMTTrainType: function(val){
            return WhgSysData.FMT(val, WhgComm.getTrainType());
        }

        ,getTrainType: function () {
            return WhgSysData.getTypeData("5");//5代表培训分类
        }

        ,FMTAreaType: function(val){
            return WhgSysData.FMT(val, WhgComm.getAreaType());
        }

        ,getAreaType: function () {
            return WhgSysData.getTypeData("6");//6代表区域分类
        }

        ,FMTSBType: function(val){
            return WhgSysData.FMT(val, WhgComm.getSBType());
        }

        ,getSBType: function () {
            return WhgSysData.getTypeData("7");//7代表活动室设备分类
        }
        ,FMTTEAType: function(val){
            return WhgSysData.FMT(val, WhgComm.getTEAType());
        }
        ,getTEAType: function () {
            return WhgSysData.getTypeData("11");//11代表老师专长分类
        }
        ,FMTZYFLType: function(val){
            return WhgSysData.FMT(val, WhgComm.getZYFLType());
        }
        ,getZYFLType: function () {
            return WhgSysData.getTypeData("14");//14代表资源分类
        }
        ,FMTCULType: function(val){
            return WhgSysData.FMT(val, WhgComm.getCULType());
        }
        ,getCULType: function () {
            return WhgSysData.getTypeData("15");//15文化展类型分类
        }
        ,FMTVenueTag: function (val) {
            return WhgSysData.FMT(val, WhgComm.getVenueTag());
        }

        ,getVenueTag: function () {
            return WhgSysData.getTagData("1");//1代表场馆标签
        }

        ,FMTRoomTag: function (val) {
            return WhgSysData.FMT(val, WhgComm.getRoomTag());
        }

        ,getRoomTag: function () {
            return WhgSysData.getTagData("2");//2代表活动室标签
        }

        ,FMTActivityTag: function(val){
            return WhgSysData.FMT(val, WhgComm.getActivityTag());
        }

        ,getActivityTag: function () {
            return WhgSysData.getTagData("3");//3代表活动标签
        }

        ,FMTTrainTag: function(val){
            return WhgSysData.FMT(val, WhgComm.getTrainTag());
        }

        ,getTrainTag: function () {
            return WhgSysData.getTagData("4");//4代表培训标签
        }

        ,FMTZxTag: function(val){
            return WhgSysData.FMT(val, WhgComm.getZxTag());
        }

        ,getZxTag: function () {
            return WhgSysData.getTagData("5");//5代表资讯标签
        }

        ,FMTVenueKey: function (val) {
            return WhgSysData.FMT(val, WhgComm.getVenueKey());
        }

        ,getVenueKey: function () {
            return WhgSysData.getKeyData("1");
        }

        ,FMTRoomKey: function (val) {
            return WhgSysData.FMT(val, WhgComm.getRoomKey());
        }

        ,getRoomKey: function () {
            return WhgSysData.getKeyData("2");
        }

        ,FMTActivityKey: function(val){
            return WhgSysData.FMT(val, WhgComm.getActivityKey());
        }

        ,getActivityKey: function () {
            return WhgSysData.getKeyData("3");
        }

        ,FMTTrainKey: function(val){
            return WhgSysData.FMT(val, WhgComm.getTrainKey());
        }

        ,getTrainKey: function () {
            return WhgSysData.getKeyData("4");
        }

        ,FMTZxKey: function(val){
            return WhgSysData.FMT(val, WhgComm.getZxKey());
        }

        ,getZxKey: function () {
            return WhgSysData.getKeyData("5");
        }

        /**
         * 列表页编辑时打开的新页面
         * @param url
         */
        ,editDialog: function (url) {
            var id = __WhgDialog4EditId;
            if( $('#'+id).size() > 0 ){
                $('#'+id).dialog('destroy');
                $('#'+id).remove();
            }
            $('<div id="'+id+'" style="overflow:hidden"></div>').appendTo($("body"));
            var _iframe = '<iframe scrolling="auto" frameborder="0"  src="' + url+ '" style="width:100%;height:100%;"></iframe>';
            $('#'+id).dialog({
                title: '',
                closable: true,
                maximized: true,
                border: false,
                modal: true,
                content: _iframe
            });
        }

        /**
         * 使用WhgComm.editDialog打开编辑里面，用此方法可以关闭打开的Dialog，模拟返回操作
         */
        ,editDialogClose: function () {
            window.parent.$('#'+__WhgDialog4EditId).dialog('close');
        }

        /**
         * 图片服务器地址
         * @returns {string}
         */
        ,getImgServerAddr: function () {
            if(typeof window.imgServerAddr != 'undefined'){
                return imgServerAddr;
            }
            return '';
        }

        /**
         * 将字符串yyyy-MM-dd转换成Date对象
         * @param dateVal yyyy-MM-dd字符串
         */
        ,parseDate: function (dateVal) {
            var d = new Date();
            try{
                dateVal += " 00:00:00";
                d = new Date(Date.parse(dateVal.replace(/-/g, "/")));
            }catch (e){
            }
            return d;
        }

        /**
         * 将字符串yyyy-MM-dd HH:mm:ss转换成Date对象
         * @param dateTimeVal yyyy-MM-dd HH:mm:ss字符串
         */
        ,parseDateTime: function (dateTimeVal) {
            var d = new Date();
            try{
                d = new Date(Date.parse(dateTimeVal.replace(/-/g, "/")));
            }catch (e){
            }
            return d;
        }
    }
})();