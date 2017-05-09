$(document).ready(function(e) {

    $(".tab li").click(function() {
        $(this).addClass("active").siblings().removeClass('active');
        $(".list1").eq($(".tab li").index(this)).addClass("on").siblings().removeClass('on');
    })

    $(".head-con2 ul li").click(function() {
        $(".head-con").eq($(".head-con2 ul li").index(this)).addClass("on").siblings().removeClass('on');
    })

    $('.head-con2').sly({
        itemNav: "smart",
        dragContent: 1,
        startAt: 3,
        elasticBounds: 1
    });
	
	/*$('.infofix').stickySidebar({
			headerSelector: '.con-left',
		    contentSelector: '.con-left',
			footerSelector: '.footer-main',
			footerThreshold: 30
	});*/
})