$(document).ready(function(e) {
    $('.img-list').sly({
        itemNav: "smart",
        easing: "easeOutExpo",
        prevPage: ".pre-left1",
        nextPage: ".pre-right1",
        horizontal: 1,
        touchDragging: 1,
        dragContent: 1
    });
    $(".tab li").click(function() {
        $(this).addClass("active").siblings().removeClass('active');
        $(".list1").eq($(".tab li").index(this)).addClass("on").siblings().removeClass('on');
    })
})