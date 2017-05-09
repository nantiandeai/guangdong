$(document).ready(function(e) {

    $(".tab li").click(function() {
        $(this).addClass("active").siblings().removeClass('active');
        $(".list1").eq($(".tab li").index(this)).addClass("on").siblings().removeClass('on');
    })
    $(".tab li").eq(0).trigger("click");
})