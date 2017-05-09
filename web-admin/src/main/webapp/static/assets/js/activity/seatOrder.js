/*$(document).ready(function(e) {
    var map  = JSON.parse(staMap);

    var mapType  =JSON.parse(typeMap);

    var option = {
        map     :    map,      //座位地图
        mapType :    mapType,  //座位号和状态
        seatMax :    seatMax         //票数
    }
    initSeat(option);
})*/

//新建场地
function initSeat(option) {
    var html = '<div class="led-bg"></div><div class="row-num"><ul></ul></div><div class="seat-item-container"></div>';
    $('#seat').append(html);
   // $('#seat-on-message').prepend('<span class="seat-list msg">您尚未选座</span>');
    analytical_map(option);
    analytical_map_type(option);
    $('.available').on('click',
        function(event) {
            var seatNo = $(this).attr('data-no').split('-');
            if ($(this).attr('data-type') != 'Y') return;
            if (!$(this).hasClass('selected')) {
                if ($('#seat .seat-item-container ul').children('.selected').length >= (option.seatMax-option.seatSizeUser)) {
                    rongDialog({
                        title: '您只能订购' + option.seatMax + '张'
                    });
                } else {
                    $('#seat-on-message').prepend('<span class="seat-list no-' + $(this).attr('data-no') + '">'  + seatNo[1] + '</span>');
                    $(this).addClass('selected');
                    selectedTickedRefNum(true);
                }
            } else {
                $('#seat-on-message .no-' + $(this).attr('data-no')).remove();
                $(this).removeClass('selected');
                selectedTickedRefNum(false);
            }
            if ($('#seat-on-message').children('span').length > 1) {
                $('#seat-on-message .msg').remove()
            }
           /* else {
                $('#seat-on-message').prepend('<span class="seat-list msg">您尚未选座</span>');
            }*/
        });
}

function selectedTickedRefNum(isadd){
    var num = $("#overNum").text();
    num = parseInt(num);
    $("#overNum").text( isadd? num-1: num+1 );
    var userNumText = $("#ticketNum_").text();
    userNumText = (/(\d+)\s*\/\s*(\d+)/).exec(userNumText);
    if (userNumText){
        userNum = parseInt(userNumText[1]);
        userNum = isadd? userNum+1: userNum-1;
        $("#ticketNum_").text(userNum+" / "+userNumText[2]);
    }
}

//获得场地宽度让它是否可滚动
function initSeatWidth(liNum) {
    var seatWidth = liNum * 36;
    if (liNum > 23) {
        $('#seat').parents(".seat").css({
            "overflow-y": "scroll"
        });
    }
    $('#seat').width(seatWidth + 50);
    if($('#seat').width()< 900){
        $('.dialog-signUP-con .seat .led-bg').width(seatWidth+110);
    }else{
        $('.seat-item-container').css({
            'padding-top':'45px'
        })
    }
}

//解析座位信息
function analytical_map(option) {
    var mapList = option.map;
    var html = "";
    var html2 = "";
    var seatMapCount = 0;
    var rowCount = mapList.length;
    for (var i = 0; i < rowCount; i++) {
        html += "<ul>";
        html2 += "<li>" + (i + 1) + "</li>";
        for (var j = 0; j < mapList[i].length; j++) {
            var className = mapList[i][j] == "3" ? "del": "available";
            html += "<li class='" + className + "'></li>";
            seatMapCount++;
        }
        html += "</ul>";
    }
    $('#seat').find('.row-num ul').append(html2);
    $('#seat').find('.seat-item-container').append(html);
    //渲染座位场地DIV的宽度
    seatMapCount = seatMapCount / rowCount;
    initSeatWidth(seatMapCount);
}

//解析座位状态
function analytical_map_type(option) {
    var mapItemType = option.mapType;
    for (var i = 0; i < mapItemType.length; i++) {
        //排数
        var rowNum = i + 1;
        for (var j = 0; j < mapItemType[i].length; j++) {
            var mapItem = mapItemType[i][j].split('-');
            var dataNo = rowNum + '-' + mapItem[0];
            var className = "";
            var $li = $('.seat-item-container ul:eq(' + i + ') li:eq(' + j + ')');
            //加Y 表示可预定
            if($li.hasClass('del')){
                $li.attr('data-type', 'N');
            }else{
                $li.attr('data-type', 'Y');
            }
            if (mapItem[1] == '3') {
                className = "unavailable";
                //加N  表示已经被占
                $li.attr('data-type', 'N');
            } else if (mapItem[1] == '2') {
                className = "unavailable";
               // className = "unavailable";
                $li.attr('data-type', 'N');
            }
            if (className != "") {
                $li.addClass(className);
            }
            $li.attr('data-no', dataNo);
        }
    }
}