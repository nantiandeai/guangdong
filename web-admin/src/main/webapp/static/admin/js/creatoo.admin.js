$(document).ready(function (e) {
    btnPosition();
    $('.subBtn').fadeOut(0);
    $('.right-main').layout('hidden', 'west');
    $('.addBtn').on('click',
        function () {
            close3Panel()
        });
    $('.subBtn').on('click',
        function () {
            open3Panel()
        });

    $('.right-3-panel .s-a').on('click',
        function () {
            $(this).parent().addClass("active").siblings("li").removeClass("active");
        });

    $('.inside .s-a').on('click',
        function() {
            //$(this).parents("li").removeClass("active");
            $('.inside li').removeClass("active");
            $(this).parent().addClass("active").siblings("li").removeClass("active");
        });

    $('.parent .p-a').on('click',
        function(){
            if($(this).parent().hasClass("active")){
                $(this).parent().removeClass("active");
                $('.inside').stop(1, 0).slideUp(400);
            }else{
                //$('.inside li').removeClass("active");
                $(this).parent().addClass("active").siblings("li").removeClass("active");
                $('.inside').stop(1, 0).slideUp(400);
                $(this).next().stop(1, 0).slideDown(600);
            }
            setTimeout(initScoll,600);

        });
    //缩小放大一级菜单
    var rightMain = $('.right-main');
    var centerMain = $('.center-main');
    var tipsoMain = $('.outer').find('a');
    $('.zoom').toggle(
        function () {
            tipsoMain.addClass('tipso_right');
            $('.inside').find('a').addClass('insideIco');
            $('.tipso_right').tipso({
                position: 'right',
                animationIn: 'fadeIn',
                background: 'rgb(44,61,74)',
                speed: 0
            });
            $('.outer').addClass('delTxt');
            $(this).parent().panel('resize', {
                width: 50,
            });
            $(this).nextAll().css('width', '50');
            if (rightMain.hasClass('openPanel')) {
                rightMain.layout('panel', 'center').panel('resize', {width: rightMain.width() - 50});
            } else {
                rightMain.layout('panel', 'center').panel('resize', {width: rightMain.width() + 130});
            }
            rightMain.width(rightMain.width() + 130);
            rightMain.parents('div').width(rightMain.width());
            rightMain.parents('.layout-panel-center').css("left", 50);
        },
        function () {
            $('.inside').find('a').removeClass('insideIco');
            $('.tipso_right').tipso('destroy');
            $('.outer').removeClass('delTxt');
            $(this).parent().panel('resize', {
                width: 180,
            });
            $(this).nextAll().css('width', '180');
            if (rightMain.hasClass('openPanel')) {
                rightMain.layout('panel', 'center').panel('resize', {width: rightMain.width() - 310});
            } else {
                rightMain.layout('panel', 'center').panel('resize', {width: rightMain.width() - 130});
            }
            rightMain.width(rightMain.width() - 130);
            rightMain.parents('div').width(rightMain.width());
            rightMain.parents('.layout-panel-center').css("left", 180);
        }
    );
    /*$('.userName').on('mouseover', function () {
        $(this).stop(1,0).animate({bottom: '-50'}, 400);
        $(this).next().stop(1,0).animate({top: '-0'}, 400);
    });
    $('.userSys').on('mouseout', function () {
        $(this).stop(1,0).animate({top: '-50'}, 400);
        $(this).prev('div').stop(1,0).animate({bottom: '0'}, 400);
    });*/
})

$(window).resize(function () {
    initScoll();
    btnPosition();
});

//设置控制按钮的位置
function btnPosition() {
    var bodyHeight = $(window).height(),
        wrapHeight = $(".layoutBtn").height(),
        adminTop = $('#adminTop').height();
    posTop = (bodyHeight - wrapHeight - adminTop) / 2;
    $(".layoutBtn").css({
        "top": posTop + "px"
    });
}


//菜单滑动条显示或隐藏
function initScoll(){
    var bar = $(".leftPanel .bar");
    bar.css({
        height:"auto"
    });
    setTimeout(function(){
        if(bar.height() > ($(".leftPanel").height()-30)){
            bar.height($(".leftPanel").height()-30);
            bar.addClass("scoll_in");
        }else{
            bar.removeClass("scoll_in");
        }
    },100);
}

//打开三级菜单面板
function open3Panel() {
    $('.subBtn').fadeOut(0);
    $('.right-main').layout('show', 'west');
}

//关闭三级菜单面板
function close3Panel() {
    $('.subBtn').fadeIn(0);
    $('.right-main').layout('hidden', 'west');
}

//关闭所有二三级清除active
function closeAllPanel(){
    $('.parent ul li,.parent ul li ul li').removeClass('active');
}
$.extend($.fn.layout.methods, {
    /**
     * 面板是否存在和可见
     * @param {Object} jq
     * @param {Object} params
     */
    isVisible: function (jq, params) {
        var panels = $.data(jq[0], 'layout').panels;
        var pp = panels[params];
        if (!pp) {
            return false;
        }
        if (pp.length) {
            return pp.panel('panel').is(':visible');
        } else {
            return false;
        }
    },
    /**
     * 隐藏除某个region，center除外。
     * @param {Object} jq
     * @param {Object} params
     */
    hidden: function (jq, params) {
        $('#cc').removeClass('openPanel');
        return jq.each(function () {
            var opts = $.data(this, 'layout').options;
            var panels = $.data(this, 'layout').panels;
            if (!opts.regionState) {
                opts.regionState = {};
            }
            var region = params;

            function hide(dom, region, doResize) {
                var first = region.substring(0, 1);
                var others = region.substring(1);
                var expand = 'expand' + first.toUpperCase() + others;
                if (panels[expand]) {
                    if ($(dom).layout('isVisible', expand)) {
                        opts.regionState[region] = 1;
                        panels[expand].panel('close');
                    } else if ($(dom).layout('isVisible', region)) {
                        opts.regionState[region] = 0;
                        panels[region].panel('close');
                    }
                } else {
                    panels[region].panel('close');
                }
                if (doResize) {
                    $(dom).layout('resize');
                }
            };
            if (region.toLowerCase() == 'all') {
                hide(this, 'east', false);
                hide(this, 'north', false);
                hide(this, 'west', false);
                hide(this, 'south', true);
            } else {
                hide(this, region, true);
            }
        });
    },
    /**
     * 显示某个region，center除外。
     * @param {Object} jq
     * @param {Object} params
     */
    show: function (jq, params) {
        $('#cc').addClass('openPanel');
        return jq.each(function () {
            var opts = $.data(this, 'layout').options;
            var panels = $.data(this, 'layout').panels;
            var region = params;

            function show(dom, region, doResize) {
                var first = region.substring(0, 1);
                var others = region.substring(1);
                var expand = 'expand' + first.toUpperCase() + others;
                if (panels[expand]) {
                    if (!$(dom).layout('isVisible', expand)) {
                        if (!$(dom).layout('isVisible', region)) {
                            if (opts.regionState[region] == 1) {
                                panels[expand].panel('open');
                            } else {
                                panels[region].panel('open');
                            }
                        }
                    }
                } else {
                    panels[region].panel('open');
                }
                if (doResize) {
                    $(dom).layout('resize');
                }
            };
            if (region.toLowerCase() == 'all') {
                show(this, 'east', false);
                show(this, 'north', false);
                show(this, 'west', false);
                show(this, 'south', true);
            } else {
                show(this, region, true);
            }
        });
    }
});