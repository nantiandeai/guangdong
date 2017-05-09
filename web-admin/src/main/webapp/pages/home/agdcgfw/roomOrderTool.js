/**
 * Created by rbg on 2017/4/6.
 */


function RoomOrderTool(config){
    this.settings = $.extend(true, {}, config ||{});

    this.nowDate = new Date();

    this.weekDiv= '.orderPayCont';

    this.trDome = '<tr>\
        <td class="choose"></td>\
        <td class="choose"></td>\
        <td class="choose"></td>\
        <td class="choose"></td>\
        <td class="choose"></td>\
        <td class="choose"></td>\
        <td class="choose"></td>\
    </tr>';


}

RoomOrderTool.prototype = {
    
    initWeekDay: function (day) {
        //week Ul
        this.weekUl = $(this.weekDiv).find('.week-groups ul');
        //table
        this.timesTable = $(this.weekDiv).find('.tableListCont table');

        //按日期取7天内的预定信息数据，用以输出预定操作表格
        this.loadWeekRoomTimes();

        this.setItemEvent();
        this.setButtonEvent();
    },

    setButtonEvent: function(){
        if (this.__setButtonEvent) return;
        this.__setButtonEvent = true;

        var button = $(this.weekDiv).find(".orderPayBg a");
        button.attr('href', 'javascript:void(0)');
        button.off("click.roomOrderTool");
        var that = this;
        if (this.settings.isSessUser){
            button.text("立即预订");
            button.on("click.roomOrderTool", function () {
                that.buttonSubmit();
            })
        }else{
            button.text("请登录");
            button.attr('href', this.settings.basePath+"/login");
        }
    },

    buttonSubmit: function(){
        if (!this.selectValue){
            rongAlertDialog({ title: "提示信息", desc: '请选择要预订的时间', closeBtn: true, icoType : 1 });
            return;
        }
        var that = this;
        $.ajax({
            url : this.settings.basePath+'/agdcgfw//checkRoomOrder',
            data : {roomtimeid: that.selectValue.id},
            dataType : "json",
            success : function(data){
                if (data.success){
                    window.location.href = that.settings.basePath+'/agdcgfw/roomOrder1?roomtimeid='+that.selectValue.id;
                }else {
                    rongAlertDialog({ title: '错误信息', desc: data.errmsg, closeBtn: true, closeIco:false, icoType:3});
                }
            }
        });
        //var roomtime = JSON.stringify(this.selectValue);
        //window.location.href = this.settings.basePath+'/agdcgfw/roomOrder1?roomtimeid='+this.selectValue.id;
    },

    setItemEvent: function(){
        if (this.__setItemEvent) return;
        this.__setItemEvent = true;

        var that = this;
        //处理选择
        this.timesTable.on("click", "td.choose:not(.gray) a", function(){
            var td = $(this).parents("td");
            var data = td.data('jsData.roomTime');
            that.selectValue = data;

            that.viewselectItem();
        });
    },

    viewselectItem: function(){
        this.timesTable.find("td.choosed:not(.gray) span").each(function(){
            var td = $(this).parents("td");
            td.removeClass().addClass('choose').html('<a href="javascript:void(0)">'+$(this).text()+'</a>');
        });

        if (!this.selectValue) return;
        var that = this;
        this.timesTable.find("td.choose:not(.gray) a").each(function(){
            var td = $(this).parents("td");
            var data = td.data('jsData.roomTime');
            if (data.id == that.selectValue.id){
                td.removeClass().addClass('choosed').html('<i></i><span>'+$(this).text()+'</span>');
            }
        });
    },
    
    loadWeekRoomTimes: function () {
        var bday = this.getWeekDayBegin();
        var eday = this.getWeekDayEnd();
        //console.info(bday, eday);
        var that = this;
        $.ajax({
            url: that.settings.basePath+'/agdcgfw/loadRoomTimes',
            data: {bday: bday, eday: eday, roomid: that.settings.roomId},
            type: 'post',
            dateType: 'json',
            success: function(data){
                that.nowDate = new Date(data.nowDate);

                that.viewRoomTimes(data);

                that.viewselectItem();
            }
        })
    },

    viewRoomTimes: function(data){
        var tbody = this.timesTable.find("tbody");
        tbody.empty();

        //预订时段对象
        var viewTimes = this.parseTimes2view(data.roomTimes);
        //成功的预订
        var roomOrderOK = this.parseOrders2view(data.roomOrderOK);
        //当前用户的申请
        var roomOrderUser = null;
        if (data.roomOrderUser){
            roomOrderUser = this.parseOrders2view(data.roomOrderUser);
        }
        //console.info(viewTimes, roomOrderOK, roomOrderUser);

        //装入行列框架
        for(var i=0; i<viewTimes.maxDayTimesNum; i++){
            tbody.append(this.trDome);
        }

        //装入初始的预定时段数据
        var that = this;
        this.weekUl.find("li").each(function(i){
            //对应星期的日期
            var weekday = that.getWeekDay(i);
            //对应的数据
            var daytimes = viewTimes[weekday];
            //对应的 列 td
            var dayTds = that.timesTable.find("tr").find("td:eq("+i+")");
            //没有数据时
            if (!daytimes){
                dayTds.removeClass("choose").addClass("choosed").text('-');
                return true;
            }
            //处理数据显示
            dayTds.each(function (i) {
                //空的
                if (i>=daytimes.length || daytimes[i].state==0){
                    $(this).removeClass("choose").addClass("choosed").text('-');
                    return true;
                }

                var showText = daytimes[i].timestart+' - '+daytimes[i].timeend;

                //小于当前时间的变灰
                var anow = that.isGtNowDate(daytimes[i]);
                if (!anow){
                    $(this).removeClass("choose").addClass("choosed gray").html(showText);
                    return true;
                }

                var key = daytimes[i].timeday+''+daytimes[i].timestart+''+daytimes[i].timeend;
                key = key.replace(/:/g, '-');
                //成功的预订变灰
                if (roomOrderOK && roomOrderOK[key]){
                    $(this).removeClass("choose").addClass("choosed gray").html(showText);
                    return true;
                }
                //当前用户订的变灰打标记
                if (roomOrderUser && roomOrderUser[key]){
                    $(this).removeClass('choose').addClass('choosed  gray').html('<i></i><span>'+showText+'</span>');
                    return true;
                }

                //可以操作的
                $(this).html('<a href="javascript:void(0)">'+showText+'</a>');
                $(this).data('jsData.roomTime', daytimes[i]);
            })
        })

    },

    parseTimes2view : function(data){
        var times = {};
        times.maxDayTimesNum = 0;
        for(var i in data){
            var row = data[i];
            if (row.state == 0) continue;

            var timeday = new Date(row.timeday).Format("yyyy-MM-dd");
            var timestart = new Date(row.timestart).Format("hh:mm");
            var timeend = new Date(row.timeend).Format("hh:mm");

            times[timeday] = times[timeday]? times[timeday] : [];
            times[timeday].push({
                id: row.id,
                roomid: row.roomid,
                state: row.state,
                timeday: timeday,
                timestart: timestart,
                timeend: timeend
            });

            times.maxDayTimesNum = times.maxDayTimesNum<times[timeday].length? times[timeday].length: times.maxDayTimesNum;
        }
        return times;
    },

    parseOrders2view: function (data) {
        var times = {};
        for(var i in data){
            var row = data[i];

            var timeday = new Date(row.timeday).Format("yyyy-MM-dd");
            var timestart = new Date(row.timestart).Format("hh:mm");
            var timeend = new Date(row.timeend).Format("hh:mm");

            var key = timeday+''+timestart+''+timeend;
            key = key.replace(/:/g, '-');
            times[key] = {
                roomid: row.roomid,
                state: row.state,
                timeday: timeday,
                timestart: timestart,
                timeend: timeend
            };
        }
        return times;
    },


    isGtNowDate: function(item){
        var now = this.nowDate;
        // var stimeStr = item.timeday + " " + item.timestart;
        // var stime = new Date( Date.parse(stimeStr.replace("-", '/')) );
        var stime = this.myParseDay(item.timeday, item.timestart);
        return stime.getTime() > now.getTime();
    },

    getWeekDayBegin: function(){
        var daystr = this.weekUl.find("li:first p").text();
        // var day = new Date(Date.parse(daystr.replace("-", '/')));
        var day = this.myParseDay(daystr);
        return day.Format("yyyy-MM-dd");
    },
    getWeekDayEnd: function(){
        var daystr = this.weekUl.find("li:last p").text();
        // var day = new Date(Date.parse(daystr.replace("-", '/')));
        day = this.myParseDay(daystr);
        return day.Format("yyyy-MM-dd");
    },
    getWeekDay: function (idx) {
        var daystr = this.weekUl.find("li:eq("+idx+") p").text();
        // var day = new Date(Date.parse(daystr.replace("-", '/')));
        day = this.myParseDay(daystr);
        return day.Format("yyyy-MM-dd");
    },

    myParseDay: function(daystr, timestr){
        var _day = new Date();

        var dstrs = /(\d{4})-(\d{1,2})-(\d{1,2})/.exec(daystr);
        if (dstrs){
            _day.setFullYear( dstrs[1] );
            _day.setMonth(parseInt(dstrs[2])-1);
            _day.setDate( dstrs[3] );
        }
        if (timestr){
            var timestrs = /(\d{2}):(\d{2})/.exec(timestr);
            if (timestrs){
                _day.setHours(timestrs[1]);
                _day.setMinutes(timestrs[2]);
                _day.setSeconds(0);
            }
        }else{
            _day.setHours(0);
            _day.setMinutes(0);
            _day.setSeconds(0);
        }
        return _day;
    }


};
