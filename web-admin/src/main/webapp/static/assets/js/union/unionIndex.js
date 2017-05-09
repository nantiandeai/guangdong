/**
 * Created by LENCON on 2016/11/11.
 */
$(document).ready(function(e) {
    $(".tab ul li").click(function () {
        $(this).addClass("active").siblings().removeClass('active');
        $(".detail").eq($(".tab ul li").index(this)).addClass("on").siblings().removeClass('on');
    })
    $('.list-detail ul li').delegate('.img','mouseover',function(){
        var imgHeight = $(this).height();
        var tHeight = $(this).find('.text').height();
        tHeight = imgHeight-tHeight-50;
        //console.log(tHeight);
        $(this).find('.detail').stop(1,0).animate({top:tHeight+"px"},600);
    });
    $('.list-detail ul li').delegate('.img','mouseout',function(){
        $(this).find('.detail').stop(1,0).animate({top:'218px'},600);
    });
    $('.list-detail').sly({
        itemNav: "basic",
        prevPage: ".next",
        nextPage: ".prev",
        horizontal: 1,
        touchDragging: 1
    });
})