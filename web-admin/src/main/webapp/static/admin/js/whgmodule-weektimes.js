/**
 * Created by rbg on 2017/3/27.
 */

;(function($, window){


    var methods = {
        options: function(){
            var jq = this;
            if (jq.length && $(jq.get(0)).data("whgWeektimes")){
                return $(jq.get(0)).data("whgWeektimes").options || {};
            }else{
                return {};
            }
        },

        /**
         * 显隐相关的天时间设置块
         * @param wday
         * @param show
         */
        switchWeekDayConfig: function(wday, show){
            var wdayDiv = this.find('.weekdaydiv-'+wday);
            if (show){
                wdayDiv.show();
                if (! wdayDiv.find('dl dd').length){
                    _addDefaultTimeItems(wdayDiv);
                }

                _setWeekdayDlHeight(wdayDiv);
            }else{
                wdayDiv.hide();
            }
        },

        validaValue: function(){
            var isvalida = true;
            var validaMessage = this.find(".validaMessage");
            validaMessage.hide().text('');

            //所有显示的weekdaydiv
            this.find('.whg-week-column:visible').each(function(){
                //当前所有的time输入框
                var times = $(this).find('.easyui-timespinner');
                var weekday = $(this).attr('weekdayValue');
                var dayText = '';
                for(var i in __weekDayData){
                    if (__weekDayData[i]['id'] == weekday){
                        dayText = __weekDayData[i]['text'];
                        break;
                    }
                }
                times.each(function(i){
                    //是否有空的值
                    var time = $(this).timespinner('getValue');
                    if (! $.trim(time) ) {
                        isvalida = false;
                        validaMessage.show().text(dayText+' 时间设置不能为空');
                        return false;
                    }
                    //后一个是否比前一个时间大
                    if (i==0) return true;
                    var pre = $(times.get(i-1));
                    var preHours = pre.timespinner('getHours');
                    var hours = $(this).timespinner('getHours');
                    if (parseInt(preHours) > parseInt(hours)){
                        isvalida = false;
                        validaMessage.show().text(dayText+' 时间设置有小于前一个时间框的值');
                        return false;
                    }else if(parseInt(preHours) == parseInt(hours)){
                        var preMin = pre.timespinner('getMinutes');
                        var min = $(this).timespinner('getMinutes');
                        if (parseInt(preMin) > parseInt(min)){
                            isvalida = false;
                            validaMessage.show().text(dayText+' 时间设置有小于前一个时间框的值');
                            return false;
                        }
                    }
                });
                return isvalida;
            });

            return isvalida;
        },

        /**
         * 取设置的值
         * @returns {'1':[{timestart:'', timeend:''}]}
         */
        getValue: function(){
            //所有显示的weekdaydiv
            var value = {};
            this.find('.whg-week-column:visible').each(function(){
                //取weekday 标值
                var weekday = $(this).attr('weekdayValue');
                //取所有的dd集
                var dds = $(this).find('dd');
                if (! dds.length){ return true; }
                var times = [];
                dds.each(function(){
                    var stime = $(this).find('.easyui-timespinner:eq(0)').timespinner('getValue');
                    var etime = $(this).find('.easyui-timespinner:eq(1)').timespinner('getValue');
                    if (!stime || !etime){ return true; }
                    times.push({timestart: stime, timeend: etime});
                });

                if (times.length){
                    value[weekday.toString()] = times;
                }
            });

            return value;
        },

        setValue: function(data) {
            if (!this.data("whgWeektimes")){
                this.whgWeektimes();
            }

            data = data || {};
            for(var k in data){
                var wdayDiv = this.find('.weekdaydiv-'+k+':visible');
                wdayDiv.find('dl dd').remove();
                var dl = wdayDiv.find('dl');
                var times = data[k];
                for(var i in times){
                    var item = times[i];
                    var newItem = $(__weekDayTimeNewTemp);
                    dl.append(newItem);
                    $.parser.parse(newItem);
                    newItem.find('.easyui-timespinner:eq(0)').timespinner('setValue', item.timestart);
                    newItem.find('.easyui-timespinner:eq(1)').timespinner('setValue', item.timeend);
                }

                _setWeekdayDlHeight(wdayDiv);
            }
        },

        setIsShow: function(){
            this.find('.whg-week-column .add').hide();
            this.find('.whg-week-column .del').hide();
            this.find('.easyui-timespinner').timespinner('readonly');
            var refWeekCheckbox = this.whgWeektimes('options').refWeekCheckbox;
            $(refWeekCheckbox).off('click.refWeekCheckbox');
        },

        clear: function(){
            this.find(".validaMessage").hide();
            this.find('dd').remove();
            this.find('.whg-week-column:visible').hide();
        }
    };

    /**
     * 处理关联周一到周日多选框的值和点击
     * @param jq
     * @private
     */
    function _eventRefWeekCheck(jq){
        var refWeekCheckbox = jq.whgWeektimes('options').refWeekCheckbox;
        if (!refWeekCheckbox) return;

        //处理关联多选对应的项
        $(refWeekCheckbox).each(function(){
            jq.whgWeektimes('switchWeekDayConfig', $(this).val(), this.checked);
        });

        $(refWeekCheckbox).off('click.refWeekCheckbox');
        $(refWeekCheckbox).on('click.refWeekCheckbox', function(){
            var checked = this.checked;
            var wday = $(this).val();
            jq.whgWeektimes('switchWeekDayConfig', wday, checked);
        })
    }

    /**
     * 定义组件相关的操作事件
     * @param jq
     * @private
     */
    function _eventInit(jq){
        //处理添加天的时间项
        jq.on('click', '.whg-week-column .add', function(){
            var newItem = $(__weekDayTimeNewTemp);

            $(this).siblings('dl').append(newItem);
            $.parser.parse(newItem);

            _setWeekdayDlHeight($(this).parents('.whg-week-column'));
        });

        //处理删除除天的时间项
        jq.on('click', '.whg-week-column .del', function(){
            var dl = $(this).parents('dl');
            var refWeekCheckbox = jq.whgWeektimes('options').refWeekCheckbox;

            $(this).parents('dd').remove();
            _setWeekdayDlHeight(dl.parents('.whg-week-column'));

            if ((! dl.find('dd').length) && refWeekCheckbox){
                var weekday = dl.parents('.whg-week-column').attr('weekdayValue');
                $(refWeekCheckbox).each(function(){
                    if (this.value == weekday && this.checked){
                        $(this).click();
                    }
                });
            }
        })
    }


    $.fn.whgWeektimes = function(options){
        if (typeof options == 'string'){
            var args = Array.prototype.slice.call(arguments, 1);
            return methods[options].apply(this, args);
        }

        options = options || {};
        return this.each(function(){
            var settings = $(this).data("whgWeektimes");
            if (settings){
                $.extend(settings.options, options);
            }else{
                var psoptions = paresOptions(this);
                $(this).data('whgWeektimes', {
                    options: $.extend({}, psoptions, options)
                });
            }

            init(this);
        });
    };

    /**
     * 处理标签配置
     * @param target
     * @returns {{}}
     */
    function paresOptions(target){
        var psOptions = {
            refWeekCheckbox : ''
        };
        var jq = $(target);
        var whgoptions = $.trim(jq.attr('whgmodule-options'));
        if (whgoptions){
            if (whgoptions.substring(0, 1) != '{'){
                whgoptions = '{' + whgoptions + '}';
            }
            var dataOptions = (new Function('return '+whgoptions))();
            psOptions = $.extend({}, psOptions, dataOptions || {});
        }
        //console.info(psOptions)
        return psOptions;
    }

    /**
     * 初始DOM数据
     * @param target
     */
    function init(target){
        var jq = $(target);
        var options = methods.options.call(jq);

        //输出对应周的时段架构
        jq.empty();
        $.each(__weekDayData, function(i, weekday){
            var weekDayDiv = $(__weekDayDivTemp);
            jq.append(weekDayDiv);
            weekDayDiv.find('dt').text(weekday.text);
            weekDayDiv.addClass('weekdaydiv-'+weekday.id);
            weekDayDiv.attr("weekdayValue", weekday.id);

            _addDefaultTimeItems(weekDayDiv);
        });
        jq.append(__clearfixDivTemp);
        jq.append('<div class="validaMessage" style="display: none;"></div>');

        //装载关联的多选事件
        _eventRefWeekCheck(jq);
        //装载事件
        _eventInit(jq);
    }

    function _addDefaultTimeItems(weekdayJq){
        weekdayJq.find('dl dd').remove();
        weekdayJq.find('dl').append(__weekDayTimeDefaultTemp);
        $.parser.parse(weekdayJq);
    }

    function _setWeekdayDlHeight(wdayDiv){
        var dth = wdayDiv.find('dl dt').height();
        var ddcount = wdayDiv.find('dl dd').length;
        if (ddcount){
            var ddh = wdayDiv.find('dl dd:eq(0)').height();
            wdayDiv.find('dl').height( (ddh+5)*ddcount + dth);
        }else{
            wdayDiv.find('dl').height(dth);
        }
    }

    var __weekDayData = [
        {id:2, text: '星期一'},
        {id:3, text: '星期二'},
        {id:4, text: '星期三'},
        {id:5, text: '星期四'},
        {id:6, text: '星期五'},
        {id:7, text: '星期六'},
        {id:1, text: '星期日'}
    ];

    //周每天的时段DIV模型
    var __weekDayDivTemp = '<div class="whg-week-column" style="display: none;">\
            <dl>\
            <dt>星期一</dt>\
            </dl>\
            <div class="add"></div>\
            </div>';

    var __weekDayTimeDefaultTemp = '<dd><input style="width: 60px" class="easyui-timespinner whg-week-timeinput" value="08:00">- <input style="width: 60px" class="easyui-timespinner whg-week-timeinput" value="10:00"><span class="del"></span></dd>\
            <dd><input style="width: 60px" class="easyui-timespinner whg-week-timeinput" value="10:30">- <input style="width: 60px" class="easyui-timespinner whg-week-timeinput" value="12:00"><span class="del"></span></dd>\
            <dd><input style="width: 60px" class="easyui-timespinner whg-week-timeinput" value="14:00">- <input style="width: 60px" class="easyui-timespinner whg-week-timeinput" value="16:00"><span class="del"></span></dd>\
            <dd><input style="width: 60px" class="easyui-timespinner whg-week-timeinput" value="16:30">- <input style="width: 60px" class="easyui-timespinner whg-week-timeinput" value="18:00"><span class="del"></span></dd>\
            ';

    var __weekDayTimeNewTemp = '<dd><input style="width: 60px" class="easyui-timespinner whg-week-timeinput">- <input style="width: 60px" class="easyui-timespinner whg-week-timeinput"><span class="del"></span></dd>';

    //清理浮动模型
    var __clearfixDivTemp = '<div class="clearfix"></div>';


    $(function () {
        $(".whgmodule-weektimes").each(function(){
            $(this).whgWeektimes();
        });
    });

})(jQuery, window);
