/**
 * Created by rbg on 2017/3/27.
 */

;(function($){

    $(function(){
       $(".whgmodule-venseatmaps").each(function(){
           $(this).whgVenseatmaps();
       });
    });

    $.fn.whgVenseatmaps = function(options){
        if (typeof options == 'string'){
            var args = Array.prototype.slice.call(arguments, 1);
            return methods[options].apply(this, args);
        }

        options = options || {};
        return this.each(function(){
            var settings = $(this).data("whgVenseatmaps");
            if (settings){
                $.extend(settings.options, options);
            }else{
                var psoptions = paresOptions(this);
                $(this).data('whgVenseatmaps', {
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
            type: 'use',    //use: 开关预定，edit:设置坐位，show:查看
            rowNum: 0,
            colNum: 0,
            onInit: function(){}
        };
        var jq = $(target);
        var whgoptions = $.trim(jq.attr('whgmodule-options'));
        if (whgoptions){
            if (whgoptions.substring(0, 1) != '{'){
                whgoptions = '{' + whgoptions + '}';
            }
            var dataOptions = (new Function('return '+whgoptions))();
            // psOptions.rowNum = dataOptions.rowNum || 0;
            // psOptions.colNum = dataOptions.colNum || 0;
            // psOptions.onInit = dataOptions.onInit;
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
        if (options.rowNum && options.colNum){
            methods.setSeatSize.call(jq, options.rowNum, options.colNum);
        }

        if (options.onInit && typeof options.onInit == 'function'){
            options.onInit.call(jq);
        }

        methods.setModuleType.call(jq, options.type);

        if (options.type != 'show')
        _onSeatEvent(jq);
    }

    //初始检测定时器
    var _isInitSizeTimeOut;

    /**
     * 方法处理定义
     * @type {{}}
     */
    var methods = {
        options: function(){
            var jq = this;
            if (jq.length && $(jq.get(0)).data("whgVenseatmaps")){
                return $(jq.get(0)).data("whgVenseatmaps").options || {};
            }else{
                return {};
            }
        },

        setSeatSize: function(numrow, numcol){
            var jq = this;
            numrow = parseInt(numrow);
            numcol = parseInt(numcol);
            if (isNaN(numrow) || isNaN(numcol)) return jq;

            _resetMainDiv(jq, numrow, numcol);

            var options = methods.options.call(jq);
            options.rowNum = numrow;
            options.colNum = numcol;

            methods.setModuleType.call(jq, options.type);
            return jq;
        },

        setValue: function(rows){
            var isInitSize = this.data("isInitSize");

            if (_isInitSizeTimeOut){
                clearTimeout(_isInitSizeTimeOut);
            }
            if (!isInitSize){
                var jq = this;
                _isInitSizeTimeOut = setTimeout(function(){
                    methods.setValue.call(jq, rows);
                }, 500);
                return;
            }

            var options = methods.options.call(this);
            var rowNum = options.rowNum;
            var colNum = options.colNum;

            rows = rows || [];
            for(var k in rows){
                /*var seat = rows[k];
                var r = parseInt(seat.numrow);
                var c = parseInt(seat.numcol);
                if (r >= rowNum || c >= colNum){
                    continue;
                };

                _showData2Seat(this, r, c, seat);*/

                var seatRow = rows[k];
                for(var i in seatRow){
                    var seat = seatRow[i];
                    var r = parseInt(seat.numrow);
                    var c = parseInt(seat.numcol);
                    if (r >= rowNum || c >= colNum){
                        continue;
                    };

                    _showData2Seat(this, r, c, seat);
                }
            }
        },

        getValue: function(){
            var options = methods.options.call(this);
            var seats = this.find("ul");
            var value = [];
            seats.each(function(ulidx){
                var numrow = ulidx;
                var seatRow = [];
                $(this).find("li").each(function(liidx){
                    var seat = {};
                    seat.numrow = numrow;
                    seat.numcol = liidx;
                    var _li = $(this);
                    seat.type = _li.attr('class').indexOf('type-0')>-1? 1 : 0;
                    if (seat.type == 1){
                        seat.numreal = _li.text();
                    }else{
                        seat.numreal = '';
                    }
                    if (options.type != 'edit' && seat.type == 1){
                        seat.open = _li.attr('class').indexOf('type-2')>-1? 0 : 1;
                    }

                    //value.push(seat);
                    seatRow.push(seat);
                })
                value.push(seatRow);
            });
            return value;
        },

        setModuleType: function(type){
            var seat_operation = this.find('.seat-operation');

            if (type == 'edit'){
                seat_operation.find('.js-button-setseat, .js-button-setnotseat').show();
                seat_operation.find('.js-button-setopen, .js-button-setnotopen').hide();
                this.find("li.type-9").removeClass('type-9').addClass('type-1');
            }else{
                this.find("li.type-1").removeClass('type-1').addClass('type-9');
            }
            if (type == 'use'){
                seat_operation.find('.js-button-setseat, .js-button-setnotseat').hide();
                seat_operation.find('.js-button-setopen, .js-button-setnotopen').show();
            }
            if (type == 'show'){
                seat_operation.find('.js-button-setseat, .js-button-setnotseat').hide();
                seat_operation.find('.js-button-setopen, .js-button-setnotopen').hide();
            }
        }
    };

    function _getNotseatClass(jq){
        var options = methods.options.call(jq);
        var type = options.type;
        if (type == 'edit'){
            return 'type-1';
        }else{
            return 'type-9';
        }
    }

    function _showData2Seat(jq, r, c, seat){
        var type = seat.type;
        //var seatTypeCls = type==0? 'type-1' : 'type-0';
        var notseatCls = _getNotseatClass(jq);
        var seatTypeCls = type==0? notseatCls : 'type-0';
        jq.find("ul:eq("+r+") li:eq("+c+")")
            .removeClass("type-1 type-0").removeClass(notseatCls)
            .addClass(seatTypeCls)
            .text(seat.numreal).data("__seatNumIdx", seat.numreal);

        if(typeof seat.open!='undefined' && 0 == seat.open && 1 == seat.type){
            jq.find("ul:eq("+r+") li:eq("+c+")").addClass("type-2");
        }
    }

    function setSelectSeat(jq, type){
        var actli = jq.find("li.active");
        //var seatTypeCls = type==0? 'type-1' : 'type-0';
        var notseatCls = _getNotseatClass(jq);
        var seatTypeCls = type==0? notseatCls : 'type-0';
        actli.each(function(){
            var seatText = type==0? '' : $(this).data("__seatNumIdx");
            $(this).removeClass("type-1 type-0").removeClass(notseatCls).removeClass('active')
                .addClass(seatTypeCls)
                .text(seatText);
        })
    }

    function setSelectSeatOpen(jq, open){
        var actli = jq.find("li.active");
        actli.each(function(){
            if (open == 0){
                $(this).addClass('type-2').removeClass('active');
            }else{
                $(this).removeClass('type-2 active');
            }
        });
    }

    /**
     * 指定行列大小的坐位展示样式
     * @param jq
     * @param numrow
     * @param numcol
     * @private
     */
    function _resetMainDiv(jq, numrow, numcol){
        jq.empty();
        if (numrow<1 || numcol<1){
            return;
        }
        //取消标记设置行列完成
        jq.data("isInitSize", false);

        jq.append('<div class="led-cont"><div class="led-bg">舞台</div></div>');
        //添加状态说明块
        var helpCont = $('<div class="help-cont"></div>');
        jq.append(helpCont);
        helpCont.append('<p><span class="active"></span>当前可操作项</p>');
        helpCont.append('<p><span class="type-0"></span>有效座位</p>');
        helpCont.append('<p><span class="type-1"></span>无效座位</p>');
        helpCont.append('<p><span class="type-2"></span>不可预订</p>');
        //排数Div
        var helpRowNumber = $('<div class="help-row-number"></div>');
        jq.append(helpRowNumber);
        //ULDiv
        var ulDivParent = $('<div class="seat-ul-div"></div>');
        jq.append(ulDivParent);
        var ulDiv = $('<div class="seat-ul-appdiv"></div>');
        ulDivParent.append(ulDiv);
        ulDiv.css('width', 80*(parseInt(numcol))+40);
        var jqWidthNum = parseInt(numcol)>8?8:parseInt(numcol);
        jq.css('width', 80*(jqWidthNum)+140);

        /*for(var i=0; i<numrow; i++){
            //添加第几排
            helpRowNumber.append('<p>第'+(i+1)+'排</p>');
            //添加行UL
            var rowUL = $('<ul></ul>');
            ulDiv.append(rowUL);

            for(var ic=0; ic<numcol; ic++){
                //添加列LI
                var seatNum = (i+1)+"排"+(ic+1)+"座";
                var colLI = $('<li class="type-0">'+seatNum+'</li>');
                colLI.data("__seatNumIdx", seatNum);
                rowUL.append(colLI);
            }
        }*/

        var uls = "";
        var numbers = "";
        for(var i=0; i<numrow; i++){
            numbers += '<p>第'+(i+1)+'排</p>';
            uls += '<ul></ul>';
        }
        helpRowNumber.append(numbers);
        ulDiv.append(uls);

        function _setli(){
            setTimeout(function(){
                ulDiv.find("ul").each(function(i){
                    $(this).find('li').each(function(ic){
                        var seatNum = (i+1)+"排"+(ic+1)+"座";
                        $(this).data("__seatNumIdx", seatNum);
                        $(this).text(seatNum);
                        return true;
                    });
                    return true;
                });

                //标记设置行列完成
                jq.data("isInitSize", true);
            }, 1);
        }

        function _addli(){
            setTimeout( function(){
                var lis = "";
                for(var ic=0; ic<numcol; ic++){
                    lis += '<li class="type-0"></li>';
                };
                ulDiv.find("ul").append(lis);
                _setli();
            }, 1);
        }

        _addli();

        //行全选
        var seatRowControl = $('<div class="seat-row-control"></div>');
        ulDiv.append(seatRowControl);
        var rowcts = "";
        for(var i=0; i<numrow; i++){
            rowcts += '<p>全选</p>'
        }
        seatRowControl.append(rowcts);

        //列全选
        var seatColumnControl = $('<div class="seat-column-control"></div>');
        ulDiv.append(seatColumnControl);
        var colcts = '';
        for(var ic=0; ic<numcol; ic++){
            colcts += '<p>全选</p>';
        }
        seatColumnControl.append(colcts);

        //操作项
        var seatOperation = $('<div class="seat-operation"></div>');
        jq.append(seatOperation);
        seatOperation.append(' <a class="easyui-linkbutton js-button-setseat">设置成坐位</a> ');
        seatOperation.append(' <a class="easyui-linkbutton js-button-setnotseat">设置成非坐位</a> ');
        seatOperation.append(' <a class="easyui-linkbutton js-button-setopen">设置可预定</a> ');
        seatOperation.append(' <a class="easyui-linkbutton js-button-setnotopen">设置不可预定</a> ');
        $.parser.parse(seatOperation);
    }

    /**
     * 添加事件处理交互
     * @param jq
     * @private
     */
    function _onSeatEvent(jq){
        //全选行
        jq.on('click', '.seat-row-control p', function(){
            var rowIdx = $(this).index();
            var activeNum = jq.find("ul:eq("+rowIdx+") li.active").length;
            if (activeNum > 0){
                jq.find("ul:eq("+rowIdx+") li.active").removeClass('active');
            }else{
                jq.find("ul:eq("+rowIdx+") li:not(.type-9)").addClass('active');
            }

        });
        //全选列
        jq.on('click', '.seat-column-control p', function(){
            var colIdx = $(this).index();
            var activeNum = jq.find('ul').find('li:eq('+colIdx+').active').length;
            if (activeNum > 0){
                jq.find('ul').find('li:eq('+colIdx+').active').removeClass('active');
            }else{
                jq.find('ul').find('li:eq('+colIdx+'):not(.type-9)').addClass('active');
            }
        });
        //格点击
        jq.on('click', 'li:not(.type-9)', function(){
            $(this).toggleClass('active');
        });

        //格双击
        jq.on('dblclick', 'li:not(.type-9,.type-1)', function(){
            var text = $(this).text() || $(this).data("__seatNumIdx");
            $(this).empty();
            var inp = $('<input type="text" maxlength="10"/>');
            $(this).append(inp);
            inp.val(text);
            inp.focus();
            var _li = $(this);
            inp.on('blur', function(){
                var v = $(this).val() || text;
                var count = 0;//jq.find("li:not(.type-9,.type-1):contains('"+v+"')");
                jq.find("li:not(.type-9,.type-1)").each(function(){
                    if ($(this).text() == v){
                        count = 1;
                        return false;
                    }
                });

                if (count > 0){
                    $.messager.alert("提示","输入了重复的座位编号","error");
                    v = $(_li).data("__seatNumIdx");
                }
                _li.text(v);
                $(_li).data("__seatNumIdx", v);
                $(_li).removeClass('active');
            })
        });

        //设置坐位点击
        jq.on('click', 'a.js-button-setseat', function(){
            setSelectSeat(jq, 1);
        });

        //设置非坐位点击
        jq.on('click', 'a.js-button-setnotseat', function(){
            setSelectSeat(jq, 0);
        });

        //设置可预定点击
        jq.on('click', 'a.js-button-setopen', function(){
            setSelectSeatOpen(jq, 1);
        });

        //设置不可预定点击
        jq.on('click', 'a.js-button-setnotopen', function(){
            setSelectSeatOpen(jq, 0);
        });
    }


})(jQuery);
