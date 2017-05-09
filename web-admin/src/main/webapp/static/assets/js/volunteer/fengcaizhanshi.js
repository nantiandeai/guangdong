$(document).ready(function(e) {
    $('.active-list .con ul li').delegate('a','mouseover',function(){
        $(this).find('.mask').stop(0,1).fadeIn(600);
    });
    $('.active-list .con ul li').delegate('a','mouseout',function(){
        $(this).find('.mask').stop(0,1).fadeOut(600);
    });
})