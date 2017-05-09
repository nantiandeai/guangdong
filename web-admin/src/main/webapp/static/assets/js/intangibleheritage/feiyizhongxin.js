$(document).ready(function(e) {
    $('.active-list .con ul li').delegate('a','mouseover',function(){
        $(this).find('.mask').stop(0,1).fadeIn(600);
    });
    $('.active-list .con ul li').delegate('a','mouseout',function(){
        $(this).find('.mask').stop(0,1).fadeOut(600);
    });
    $('.newsGroups ul').delegate('li','click',function(){
        $(this).addClass("active").siblings().removeClass('active');
        $(".tab_content").eq($(".newsGroups ul li").index(this)).addClass("active").siblings().removeClass('active');
    });
    $('.label ul').delegate('li','click',function(){
        $(this).addClass("active").siblings().removeClass('active');
        //$(".tab_content").eq($(".newsGroups ul li").index(this)).addClass("active").siblings().removeClass('active');
    });
    /*$('.module2-content').sly({
        itemNav: "smart",
        easing: "easeOutExpo",
        horizontal: 1,
        touchDragging: 1,
        dragContent: 1,
        scrollBy: 1,
        scrollBar:".scrollbar",
        cycleBy: 'items',
        cycleInterval: 1000,
        speed: 400,
    })*/
	
	$('.news-cont .img').sly({
		itemNav: "basic",
		easing: "easeOutExpo",
		nextPage: ".btn-top-prev",
		prevPage: ".btn-top-next",
		horizontal: 1,
		touchDragging: 1
  	});
	
})