/**
 * 处理内部预定场馆时段
 * Created by qxk on 2016/12/30.
 */

function VenueDatebked(config){
    this.config = {};
    $.extend(this.config, config||{});

    if (typeof VenueDatebked.__initialization__ != 'undefined') return;
    VenueDatebked.__initialization__ = true;

    /**
     * 初始配置处理
     * @param config
     */
    VenueDatebked.prototype.init = function (config) {
        $.extend(this.config, config||{});
    }

    /**
     * 处理点击行接入
     * @param idx
     */
    VenueDatebked.prototype.show = function (idx) {
        var grid = this.config.grid;
        if (!grid){
            $.messager.alert("错误信息","没有指定表格对象","error");
            return;
        }

        //获取选中项的场馆信息
        //this.data_venue_row = grid.getRowData(idx);

        //弹出面板
        //this.showWin();

        //获取日期选项行信息
        var dateRow = grid.getRowData(idx);
        //场馆ID
        this.data_venid = dateRow.vendpid;
        //场馆名称
        this.data_venName = dateRow.venname;
        //日期段ID
        this.data_vendid = dateRow.vendid;

        //日期段对应的时段集
        var me = this;
        var url = me.config.basePath+"/admin/ven/loadVenTimeList?vendid="+this.data_vendid;
        $.getJSON(url, function (data) {
            me.data_times = data;
            me.showWin();
        })

        //处理日期段选择天的选项数据
        this.data_vendate2day = new Array();
        var patt = new RegExp(/(\d{4})\D(\d{2})\D(\d{2})/);
        var sdateP = patt.exec(dateRow.vendsdate);
        var edateP = patt.exec(dateRow.vendedate);
        var sd, ed;
        if (!sdateP){
            sd = new Date(dateRow.vendsdate);
        }else{
            sd = new Date();
            sd.setFullYear(parseInt(sdateP[1]), parseInt(sdateP[2])-1, parseInt(sdateP[3]));
        }
        if (!edateP){
            ed = new Date(dateRow.vendedate);
        }else{
            ed = new Date();
            ed.setFullYear(parseInt(edateP[1]), parseInt(edateP[2])-1, parseInt(edateP[3]));
        }

        var _sdlong = sd.getTime();
        while ( _sdlong <= ed.getTime() ) {
            var showDay = new Date(_sdlong).Format("yyyy-MM-dd");
            _sdlong += (1000 * 60 * 60 * 24);
            var item = new Object();
            item.value = showDay;
            item.text = showDay;
            me.data_vendate2day.push(item);
        }
    }

    /**
     * 处理面板设置
     */
    VenueDatebked.prototype.showWin = function () {
        var me = this;
        if (!this.temp_win){
            this.temp_win = '<div class="easyui-window"></div>';
        }

        $(this.temp_win).window({
            collapsible : false,
            minimizable : false,
            maximizable : true,
            modal : true,
            shadow : true,
            constrain : true,
            maximized: true,
            width: '70%',
            height: '60%',
            title : me.data_venName+' - 场馆内定处理', //me.data_venue_row.venname+' - 场馆内定处理',
            onOpen : function(){
                me.$_window = $(this);
                me.initWinBody();
            }
        });

    }

    /**
     * 处理win 弹出层body
     */
    VenueDatebked.prototype.initWinBody = function(){
        var me = this;
        //装入日期选项和时段显示层
        var winbody = this.$_window.window("body");

        if (!winbody.find("div.winlayout").length){
            winbody.append('<div class="winlayout easyui-layout" data-options="fit:true">' +
                '<div data-options="region:center"><table class="wintable"></table></div>' +
                '</div>');
        }
        if (!winbody.find("div#wintabletoolbar").length){
            winbody.append('<div id="wintabletoolbar" style="display: none;">' +
                '<lable>场馆日期选项：</lable>' +
                '<input class="datelist easyui-combobox">' +
                '</div>')
        }

        this.v_table = winbody.find("table.wintable");

        this.v_table.datagrid({
            columns: [[
                {field: 'time', title: '时段', align:'center', width:100},
                {field: 'state', title: '状态', width:100, formatter:function(v){
                    if (v==0) return "可内定";
                    else if (v==1) return "已被内定";
                    else if (v==2) return "已被预定";
                }},
                {field: 'option', title: '操作', width:100, formatter:function (v, r, i) {
                    if (r.state==2) return "";
                    if (r.state==0) return '<a class="addItem" idx="'+i+'">内定</a>';
                    if (r.state==1) return '<a class="delItem" idx="'+i+'">取消</a>';
                }}
            ]],
            fitColumns : true,
            singleSelect : true,
            toolbar : '#wintabletoolbar',
            onLoadSuccess : function(d){
                winbody.find("a.addItem, a.delItem").linkbutton({plain:true});
            }
        });


        this.v_toolbar = winbody.find("#wintabletoolbar .datelist");
        this.v_toolbar.combobox({
            data : me.data_vendate2day,
            onSelect : function (record) {
                me.data_selectDate = record.value;
                me.loadTable();
            },
            onLoadSuccess : function (d) {
                if (d.length){
                    $(this).combobox("select", d[0].value);
                }
            }
        });

        winbody.on("click", ".addItem", function(evt){
            var idx = $(this).attr("idx");
            var rows = me.v_table.datagrid("getRows");
            var row = rows[idx];
            //alert(JSON.stringify(row));

            $.messager.confirm('确认对话框', '您确认将所选择的时段设置为内部使用吗？', function(r){
                if (r) {
                    //发送数据
                    $.post(me.config.basePath+"/admin/ven/addVenSystimeBked", row.param, function (data) {
                        if (!data.success){
                            $.messager.alert("提示", "操作失败");
                        }
                        me.loadTable();
                    })
                }
            });
        });

        winbody.on("click", ".delItem", function(evt){
            var idx = $(this).attr("idx");
            var rows = me.v_table.datagrid("getRows");
            var row = rows[idx];
            //alert(JSON.stringify(row));
            $.messager.confirm('确认对话框', '您确认取消内部使用所选择的时段吗？', function(r){
                if (r) {
                    //发送数据
                    $.post(me.config.basePath+"/admin/ven/delVenSystimeBked", row.param, function (data) {
                        if (data.success){
                            if (!data.success){
                                $.messager.alert("提示", "操作失败");
                            }
                            me.loadTable();
                        }
                    })
                }
            });
        });


        /*if (!winbody.find("div.main").length){
            winbody.append('<div class="main" style="margin: 10px auto; width: 80%;"></div>');
        }

        if (!winbody.find(".main .datelist").length){
            var datelist = $('<div style="height: 40px;line-height: 40px;padding: 0 5px; background-color: #b3b3b3">' +
                '<lable>场馆日期选项：</lable><input class="datelist">' +
                '</div>');
            winbody.find(".main").append(datelist);
        }

        if (!winbody.find(".main .timelist").length){
            this.$_win_timelist = $('<div class="timelist" style="padding: 10px 5px">' +
                '<table style="width: 100%">' +
                '</table>' +
                '</div>');
            winbody.find(".main").append(this.$_win_timelist);
        }

        //加载日期选项数据
        var me = this;
        var url = this.config.basePath+"/admin/ven/loadVenDateList?venid="+this.data_venue_row.venid;
        this.$_win_datalist = winbody.find(".main .datelist").combobox({
            url : url,
            valueField : 'vendid',
            textField : 'vendsdate',
            height: 35,
            width: 200,
            onSelect: function (recode) {
                var url = me.config.basePath+"/admin/ven/loadVenTimeList?vendid="+recode.vendid;
                $.getJSON(url, function (data) {
                    me.initWinTime(recode, data);
                })
            }
        })*/
    }

    VenueDatebked.prototype.loadTable = function(){
        var me = this;
        var url2 = me.config.basePath+"/admin/ven/loadVenTimeBkedList?vendid="+me.data_vendid;
        $.getJSON(url2, function (data) {
            me.tableDataFilter(data);
        })
    }

    VenueDatebked.prototype.tableDataFilter = function (data) {
        var me = this;
        //alert(JSON.stringify(data));
        var _data = new Array();
        for(var i in me.data_times){
            var d = me.data_times[i];

            var row = new Object();

            var _attr = new Object();
            _attr.vebday = me.data_selectDate;
            _attr.vebstime = d.vtstime;
            _attr.vebetime = d.vtetime;
            _attr.vebtid = d.vtid;
            _attr.vebvenid = me.data_venid;

            row.param = _attr;
            row.time = d.vtstime+'-'+d.vtetime;

            row.state = 0;
            for (var j in data){
                var d = data[j];
                //console.info(d);
                if (row.param.vebtid != d.vebtid){
                    continue;
                }
                var vebday = new Date(d.vebday).Format("yyyy-MM-dd");
                if (row.param.vebstime == d.vebstime && row.param.vebetime == d.vebetime && row.param.vebday == vebday){
                    if (d.vebuid.match(/admin/)){
                        row.state = 1;
                    }else{
                        row.state = 2;
                    }
                    break;
                }
            }

            _data.push(row);
        }

        this.v_table.datagrid("loadData", _data);

    }

    /**
     * 加载 可操作的时段数据
     */
    /*VenueDatebked.prototype.initWinTime = function (vdate, times) {
        var $table = this.$_win_timelist.find("table").empty();
        //组装时间段集
        var ths = $('<tr class="tth" style="background-color: #b3b3b3"></tr>');
        ths.append('<th width="200" height="30">日期</th>');
        //var temp_thtd = '<td></td>';
        for(var i in times){
            /!*var d = times[i];
             var thtd = $(temp_thtd);
             thtd.text(d.vtstime+'-'+d.vtetime);
             ths.append(thtd);*!/
            ths.append('<th>时段</th>');
        }
        $table.append(ths);

        //组装日期段集
        var patt = new RegExp(/(\d{4})\D(\d{2})\D(\d{2})/);
        var sdateP = patt.exec(vdate.vendsdate);
        var edateP = patt.exec(vdate.vendedate);
        var sd = new Date();
        sd.setFullYear(parseInt(sdateP[1]), parseInt(sdateP[2])-1, parseInt(sdateP[3]));
        var ed = new Date();
        ed.setFullYear(parseInt(edateP[1]), parseInt(edateP[2])-1, parseInt(edateP[3]));

        var me = this;
        var _sdlong = sd.getTime();
        while ( _sdlong <= ed.getTime() ){
            var showDay = new Date(_sdlong).Format("yyyy-MM-dd");
            _sdlong += (1000*60*60*24);

            var tr = $('<tr></tr>');
            tr.append('<td>'+showDay+'</td>');

            for(var i in times){
                var d = times[i];
                var _td = $('<td class="addtimeItem">--</td>');

                var _attr = new Object();
                _attr.vebday = showDay;
                _attr.vebstime = d.vtstime;
                _attr.vebetime = d.vtetime;
                _attr.vebtid = d.vtid;
                _attr.vebvenid = this.data_venue_row.venid;

                _td.data("param", _attr);
                _td.text(d.vtstime+'-'+d.vtetime);
                tr.append(_td);
            }

            $table.append(tr);
        }
        
        $table.on("click", "td.addtimeItem", function (evt) {
            me.timeItemClick(evt);
        })
        $table.on("click", "td.deltimeItem", function (evt) {
            me.untimeItemClick(evt);
        })

        var url2 = me.config.basePath+"/admin/ven/loadVenTimeBkedList?vendid="+vdate.vendid;
        $.getJSON(url2, function (data) {
            me.initWinTimeBked(vdate, data);
        })
    }*/

    /*VenueDatebked.prototype.initWinTimeBked = function (vdate, bkeds) {
        var itemTds = this.$_win_timelist.find("table td.addtimeItem");
        itemTds.each(function () {
            var td = $(this);
            var param = td.data("param");
            //console.info( "====>");
            //console.info( param);
            for (var i in bkeds){
                var d = bkeds[i];
                //console.info(d);
                if (param.vebtid != d.vebtid){
                    continue;
                }

                if (param.vebstime == d.vebstime && param.vebetime == d.vebetime && param.vebday == d.vebday){
                    if (d.vebuid.match(/admin/)){
                        td.removeClass("addtimeItem").addClass("deltimeItem");
                        var _txt = td.text();
                        td.text(_txt+"(已内定)");
                    }else{
                        td.removeClass("addtimeItem");
                        var _txt = td.text();
                        td.text(_txt+"(已被预定)");
                    }
                    return true;
                }
            }
        })
    }*/

    /*VenueDatebked.prototype.timeItemClick = function (evt) {
    	var param = $(evt.target).data("param");
    	var base = this.config.basePath;
    	$.messager.confirm('确认对话框', '您确认将所选择的时段设置为内部使用吗？', function(r){
    		if (r) {
    	        //console.info(param);
    	        //发送数据
    	        $.post(base+"/admin/ven/addVenSystimeBked", param, function (data) {
    	            if (data.success){
    	                $(evt.target).removeClass("addtimeItem").addClass("deltimeItem");
    	                var _txt = $(evt.target).text();
    	                $(evt.target).text(_txt+"(已内定)");
    	            }
    	        })
			}
    	});
    }
    VenueDatebked.prototype.untimeItemClick = function (evt) {
    	var param = $(evt.target).data("param");
    	var base = this.config.basePath;
    	$.messager.confirm('确认对话框', '您确认取消内部使用所选择的时段吗？', function(r){
    		if (r) {
		        //发送数据
		        $.post(base+"/admin/ven/delVenSystimeBked", param, function (data) {
		            if (data.success){
		                $(evt.target).removeClass("deltimeItem").addClass("addtimeItem");
		                var _txt = $(evt.target).text();
		                _txt = _txt.replace('(已内定)', '');
		                $(evt.target).text(_txt);
		            }
		        })
    		}
    	});
    }*/
}
