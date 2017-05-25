//jquery index 1.0
//create by zengrong (zrongs@vip.qq.com)

$(document).ready(function(e) {
    var dom = $(".banner-cont");
    var li_width = [];
    $("li", dom).each(function (idx) {
        var _img = $(this).get(0).style.background;
        //_img = _img.substring(5, _img.length-2);
        var imgarr = /.*url\((.*)\).*/.exec(_img);
        if (imgarr){
            _img = String(imgarr[1]);
            _img = _img.replace(/\"/g,"");
        }

        var _text = $('a', this).attr('title');
        li_width[idx] = {};
        li_width[idx].img = _img;
        li_width[idx].text = _text;
    });

    var count = dom.find("li").length;
    count = 100/count+"%";
    dom.sly({
        itemNav: "forceCentered",
        easing: "easeOutExpo",
        pagesBar: ".pages",
        pageBuilder:function (dom) {
            return "<li style="+"width:"+count+">" +
                    "<div class="+"page-content"+">"+
                "<img src="+li_width[dom].img+">" +
                "<div class="+"msg"+">"+
                "<div class="+"number"+">0"+(dom+1)+"</div>"+
                "<div class="+"text"+">"+li_width[dom].text+"</div>"+
                "</div>"+
                "<div class="+"mask"+">"+
                "</div>"+
                "</li>";
        },
        prev: ".big-b-prev",
        next: ".big-b-next",
        cycleBy: 'items',
        horizontal: 1,
        touchDragging: 1
    });

    $(".tab ul li").click(function () {
        $(this).addClass("active").siblings().removeClass('active');
        $(".detail").eq($(".tab ul li").index(this)).addClass("on").siblings().removeClass('on');
    })

    $('.union-cont').sly({
        itemNav: "basic",
        easing: "easeOutExpo",
        pagesBar: ".u-nav",
        pageBuilder:function (dom) {
            return "<span></span>";
        },
        horizontal: 1,
        touchDragging: 1
    });

    $('.information-banner').sly({
        itemNav: "forceCentered",
        easing: "easeOutExpo",
        prevPage: ".left-arrow",
        nextPage: ".right-arrow",
        horizontal: 1,
        scrollBy: 1

    });
});