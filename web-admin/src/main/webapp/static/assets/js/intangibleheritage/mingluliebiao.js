$(document).ready(function(e) {
    $('.active-list .con ul').delegate('li a','mouseover',function(){
        $(this).find('.mask').stop(0,1).fadeIn(600);
    });
    $('.active-list .con ul').delegate('li a','mouseout',function(){
        $(this).find('.mask').stop(0,1).fadeOut(600);
    });
    $('.newsGroups ul').delegate('li','click',function(){
        $(this).addClass("active").siblings().removeClass('active');
        $(".tab_content").eq($(".newsGroups ul li").index(this)).addClass("active").siblings().removeClass('active');
    });
    $(".tab li").click(function() {
        $(this).addClass("active").siblings().removeClass('active');
        $(".list1").eq($(".tab li").index(this)).addClass("on").siblings().removeClass('on');
    })
	/*
    $('.img-list').sly({
        itemNav: "smart",
        easing: "easeOutExpo",
        prevPage: ".pre-left1",
        nextPage: ".pre-right1",
        horizontal: 1,
        touchDragging: 1,
        dragContent: 1,
        scrollBy: 1
    });*/
})