$(document).ready(function(e) {


    $("#checkbox1").on("click",function(){
        var Ischecbox = $(this).find("input[type=checkbox]");
        if(Ischecbox.is(':checked')) {
            Ischecbox.attr("checked", false);
            $("#submit-order").removeClass("red");
            $(this).find("label").hide();
        }else {
            Ischecbox.attr("checked", true);
            $("#submit-order").addClass("red");
            $(this).find("label").show();
        }
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
	$(".tab li").click(function() {
        $(this).addClass("active").siblings().removeClass('active');
        $(".list1").eq($(".tab li").index(this)).addClass("on").siblings().removeClass('on');
    })

    $('.dateChange').on('click',
        function() {
            laydate({
                elem: '#dateCont'
            });
        });
})