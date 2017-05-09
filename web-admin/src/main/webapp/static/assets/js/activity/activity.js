/**
 * Created by LENCON on 2016/11/11.
 */
$(document).ready(function(e) {
    $(".tab ul li").click(function () {
        $(this).addClass("active").siblings().removeClass('active');
        $(".detail").eq($(".tab ul li").index(this)).addClass("on").siblings().removeClass('on');
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
	
	$('.news-cont .img').sly({
		itemNav: "basic",
		easing: "easeOutExpo",
		nextPage: ".btn-top-prev",
		prevPage: ".btn-top-next",
		horizontal: 1,
		touchDragging: 1
  	});
})