/**
 * Created by Zengrong on 2016/11/03 0017.
 */
$(document).ready(function(e) {
    initWeekList(new Date());
    $('.dateChange').on('click',
        function() {
            laydate({
                elem: '#dateCont'
            });
        });
    $('.week-next').on('click',
        function() {
            initWeekList(nextWeek());
        });
    $('.week-prev').on('click',
        function() {
            initWeekList(prevWeek());
        });
    $('.week-groups ul').delegate('li', 'click',
        function() {
            $(this).addClass('active').siblings().removeClass('active');
        });
    $('.place i').mouseover(function(){
        $(this).children('.infoMianCont').stop(0,1).show();
    });
    $('.place i').mouseout(function(){
        $(this).children('.infoMianCont').stop(0,1).fadeOut(300);
    });

    $(".tab li").click(function() {
        $(this).addClass("active").siblings().removeClass('active');
        $(".list1").eq($(".tab li").index(this)).addClass("on").siblings().removeClass('on');
    })

    $('.eventliebiao').sly({
        itemNav: "smart",
        easing: "easeOutExpo",
        prevPage: ".pre-left",
        nextPage: ".pre-right",
        horizontal: 1,
        touchDragging: 1,
        dragContent: 1
    });

})

function initWeekList(onToday) {

    var today = new Array('星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六');
    var htmlVal = "";
    var weekDay = onToday ? onToday: new Date();
    var year =  weekDay.getFullYear();
    var month = weekDay.getMonth();
    var date = weekDay.getDate();
    for (i = 0; i < 7; i++) {
        weekDay.setYear(year);
        weekDay.setMonth(month);
        weekDay.setDate(date);
        thisYear = weekDay.getFullYear();
        thisMonth = weekDay.getMonth()+1;
        thisDay = weekDay.getDate();
        var result = today[weekDay.getDay()];
        var active = i == 0 ? 'class="active"': '';
        //alert(today[weekDay.getDay()]+'-'+parseInt(weekDay.getDay()) );
        htmlVal += '<li ' + active + '>' + '<h4>' + result + '</h4>' + '<p>' + thisYear + '-' + thisMonth + '-' + thisDay + '</p>' + '<div class="day-border"></div>' + '</li>';
        date++;
    }
    $('.week-groups ul').html(htmlVal);
}

function nextWeek() {
    var thisDate = $('.week-groups ul li p:first').html();
    var str = thisDate.split('-');
    var date = new Date(str[0],str[1]-1,str[2]);
    date.setDate(date.getDate() + 7);
    return date;

}

function prevWeek() {
    var thisDate = $('.week-groups ul li p:first').html();
    var str = thisDate.split('-');
    var date = new Date(str[0],str[1]-1,str[2]);
    date.setDate(date.getDate() - 7);
    return date;
}