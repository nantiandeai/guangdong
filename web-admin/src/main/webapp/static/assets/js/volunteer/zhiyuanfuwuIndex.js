$(document).ready(function(e) {
    $(".tab ul li").click(function () {
        $(this).addClass("active").siblings().removeClass('active');
        $(".newsGroups .detail").eq($(".tab ul li").index(this)).addClass("on").siblings().removeClass('on');
		
    })
    $('.event-right-list').sly({
        itemNav: "smart",
        easing: "easeOutExpo",
        prevPage: ".arrow-left",
        nextPage: ".arrow-right",
        horizontal: 1,
        touchDragging: 1,
        dragContent: 1
    });

    $('.fengcai-row').delegate('.content','mouseover',function(){
        $(this).find('.mask').stop(1,0).slideDown(600);
    });
    $('.fengcai-row').delegate('.content','mouseout',function(){
        $(this).find('.mask').stop(1,0).slideUp(600);
    });
	
	$('.news-cont .img').sly({
		itemNav: "basic",
		easing: "easeOutExpo",
		nextPage: ".btn-top-prev",
		prevPage: ".btn-top-next",
		horizontal: 1,
		touchDragging: 1
  	});

})