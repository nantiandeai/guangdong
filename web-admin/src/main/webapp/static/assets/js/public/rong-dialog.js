//jquery rongDialog 1.0
//create by zengrong (zrongs@vip.qq.com)
//绝对局中
function positionCenter(className) {
    className.css({
        position: 'fixed',
        left: ($(window).width() - className.outerWidth()) / 2,
        top: ($(window).height() - className.outerHeight()) / 2,
        zIndex: 15777271
    })
}

/*
	    >> rongDialog参数说明 <<
		type         ------   true/false   默认为成功
		title        ------   txt          显示的文本内容
		showTime     ------   number       动画过渡时间
		time         ------   number       动画停留时间
		url          ------   url          跳转链接
*/
function rongDialog(option, event) {
    $("body").find(".r_dialog").remove();
    $("body").find(".r_mask").remove();
    $("body").prepend("<div class=\"r_dialog\"><i></i><p></p></div><div class=\"r_mask\"></div>");
    var ico = option['type'] == true ? "s": "e";
    var title = option['title'];
    var time = option['time'] ? option['time'] : 1000;
    var showTime = option['showTime'] ? option['showTime'] : 300;
    var dialog = $('.r_dialog');
    var mask = $('.r_mask');
    var url = option['url'];
    positionCenter(dialog);
    dialog.children("i").addClass(ico);
    dialog.children("p").text(title);
    dialog.fadeIn(showTime);
    mask.on('click',
    function() {
        mask.fadeOut(0);
        dialog.fadeOut(showTime,
        function() {
            if (url) {
                window.location.href = url;
            }
        });

    });
    setTimeout(function() {
        mask.fadeOut(0);
        dialog.fadeOut(showTime,
        function() {
            if (url) {
                window.location.href = url;
            }
        });
    },
    time);
}

/*
	    >> rongLoading  参数说明 <<
		title        ------   txt          显示的文本内容
		showTime     ------   number       动画过渡时间
		time         ------   number       动画停留时间
		clear        ------   true/false   是否保留动画   默认false
		url          ------   url          跳转链接
*/
function rongLoading(option, event) {
    $("body").find(".r_dialog").remove();
    $("body").find(".r_mask").remove();
    $("body").prepend("<div class=\"r_dialog\"><i class=\"l\"></i><p></p></div><div class=\"r_mask\"></div>");
    var title = option['title'];
    var time = option['time'] ? option['time'] : 1000;
    var showTime = option['showTime'] ? option['showTime'] : 300;
    var dialog = $('.r_dialog');
    var mask = $('.r_mask');
    var type = option['clear'] ? option['clear'] : "false";
    var url = option['url'];
    positionCenter(dialog);
    dialog.children("p").text(title);
    dialog.fadeIn(showTime);
    if (type == true) {
        if (url) {
            setTimeout(function() {
                mask.fadeOut(0);
                dialog.fadeOut(showTime,
                function() {
                    window.location.href = url;
                });
            },
            time);
        } else {
            window.location.href = url;
        }
    } else {
        if (url) {
            window.location.href = url;
        }
    }
}

/*
	    >> rongAlertDialog  参数说明 <<
		title        ------   txt          显示的标题内容
		desc         ------   txt          描述信息
		closeBtn     ------   true/false   是否添加确定关闭按钮   默认为添加
		icoType      ------   1,2,3        1感叹号 2问号  3叉叉
		closeIco     ------   ture/false   是否关闭提示图标      默认为false 显示图标 true
*/
function rongAlertDialog(option, fn, event) {
    var okBtn = "";
    var coloeBtn = "";
    var ico = (option['icoType'] > 0 && option['icoType'] <= 3) ? option['icoType'] : "1";
    var desc = option['desc'] ? "<span>" + option['desc'] + "</span>": "";
    var closeDialog = option['closeBtn'];
    var closeIco = !option['closeIco'] ? "": "none";
    if (!closeDialog) {
        okBtn = "<div class=\"p_btn goNext float-left\"> <a href=\"javascript:void(0)\" class=\"js__p_ok\">确定</a> </div>";
        coloeBtn = "<div class=\"p_btn goBack float-left\"> <a href=\"javascript:void(0)\" class=\"js__p_close\">取消</a> </div>";
    }
    $("body").prepend("<div class=\"popup_mask\"></div><div class=\"popup js__slide_top clearfix\"><a href=\"javascript:void(0)\" class=\"p_close js__p_close\" title=\"关闭\"></a><div class=\"p_content\"><p>" + option['title'] + "</p></div><div class=\"p_main\"><div class=\"p_ico p_ico_" + ico + " " + closeIco + "\"></div>" + desc + "</div>" + okBtn + coloeBtn + "</div>");
    var popupCont = $('.popup');
    var showTime = option['showTime'] ? option['showTime'] : 300;
    var mask = $('.popup_mask');
    positionCenter(popupCont);
    mask.fadeIn(showTime);
    popupCont.fadeIn(showTime);
    $(".js__p_ok").on("click", fn);
    $(".js__p_close,.js__p_ok").on("click",
    function() {
        popupCont.fadeOut(showTime);
        mask.fadeOut(showTime);
        setTimeout(function() {
            popupCont.remove();
        },
        showTime);
        mask.remove();
    });
}

/*
	    >> closeDialog  参数说明 <<
*/

function closeDialog(event) {
    var popupCont = $('.popup');
    var mask = $('.popup_mask');
    popupCont.fadeOut(300);
    mask.fadeOut(300);
}

/*
	    >> rongDetailDialog  参数说明 <<
		dId          ------   txt          要打开的窗口ID
*/
function rongDetailDialog(dId, option, a, event) {
    $("body").prepend("<div class=\"popup_mask\"></div>");
	var popupCont = $(dId);
    var mask = $('.popup_mask');
    positionCenter(popupCont);
	var zoomin = '';
	if(option){
		zoomin = option['zoomin'] ? 'sampleimage' : '';
	}
	if(zoomin != ''){
		$(window).bind("wheel",function(event){
			event.preventDefault();
			return false;
		});
		$('html').css({'overflow':'hidden'});
		$('html,body').animate({scrollTop:0},0);
		$('.img_cont img').addClass(zoomin);
		$(".sampleimage").zoomio();
	}
    var li = $(a).parents('li');
    $('.img-next').on("click",function(){
        li.next('li').find("a").click();
    });
    $('.img-prev').on("click",function(){
        li.prev('li').find("a").click();
    });
    mask.fadeIn(300);
    popupCont.fadeIn(300);
    $('.js__p_close').on("click",
    function() {
        popupCont.fadeOut(0);
        mask.fadeOut(0);
        mask.remove();
		$('html').css({'overflow':'auto'});
        $("body").find("#vedio_cont").remove();
		$("#zoomiocontainer").css({visibility:'hidden'});
		$(window).unbind("wheel");
    });
}
/*
	>> rongDetailDialog  参数说明 <<
		url          ------   txt         视频链接
		type         ------   txt         视频格式  video/webm   video/ogg   video/mp4
*/
function show_vedio(a, option) {
	var url = option['url'];
    //console.info(url);
    if (typeof basePath !='undefined'){
        var __url = url.replace(basePath, '');
        //console.info(__url);
        if (!__url.match(/^(\.)?\/upload/)){
            url = __url.replace(/^(\.)?\//, '');
        }
    }
    //console.info(url);

    var _bspath = (typeof basePath !='undefined')? basePath+"/static/" : "../../";

    var result = /\.[^\.]+$/.exec(url);
    
    var imgUrl = result == '.mp3' ? _bspath+'assets/img/public/musicBg.png': _bspath+"assets/img/public/vedioBg.jpg";
    var type = option['type'] ? option['type'] : "video/mp4";
    $("body").find("#vedio_cont").remove();
    //var ems = '<div id="vedio_cont" class="vedioFixCont">' + '<a href=\"javascript:void(0)\" class=\"p_close js__p_close\" title=\"关闭\"></a>' + '<div id="clientcaps"></div>' + '<div class="oip_media" >' + '<video width="700" height="354" poster="' + imgUrl + '" controls oncontextmenu="return false" autoplay="autoplay">' + '<source type="' + type + '" src="' + url + '" />' + '</video>' + '</div>' + '</div>';
    var _src = basePath+"/oiplayer/show?imgUrl="+imgUrl+"&type="+type+"&url="+url;
    var ems = '<div id="vedio_cont" class="vedioFixCont">' +
        '<a href=\"javascript:void(0)\" class=\"p_close js__p_close\" title=\"关闭\"></a>' +
        '<div id="clientcaps"></div>' +
        '<div class="oip_media" >' +
        // '<video width="700" height="354" poster="' + imgUrl + '" controls oncontextmenu="return false" autoplay="autoplay">' +
        // '<source type="' + type + '" src="' + url + '" />' +

            '<iframe src="'+_src+'" scrolling="no" frameborder="0" height="354" width="700"></iframe>'+
        '</video>' +
        '</div>' +
        '</div>';
    $("body").append(ems);

    rongDetailDialog("#vedio_cont");
    /*$('body.oiplayer-example').oiplayer({
        server: _bspath,
        jar: 'assets/js/plugins/oiplayer-master/plugins/cortado-ovt-stripped-0.6.0.jar',
        flash: 'assets/js/plugins/oiplayer-master/plugins/flowplayer-3.2.7.swf',
        controls: 'top',
        log: 'info'
    });*/
}

/*
	>> rongDetailDialog  参数说明 <<
		url          ------   txt         视频链接
		type         ------   txt         视频格式  video/webm   video/ogg   video/mp4
*/
function show_img(a, option) {
	var url = option['url'];
    $("body").find("#img_cont").remove();
    var ems = '<div id="img_cont" ><div class="imgFixCont">' + '<a href=\"javascript:void(0)\" class=\"p_close js__p_close\" title=\"关闭\"></a>' + '<div class="img_cont" >' + '<img src="'+url+'">' + '</div>' + '</div><div class="page-bar"><span class="img-prev"><i class="iconfont icon-back"></i></span><span class="img-next"><i class="iconfont icon-more"></i></span></div></div>';    $("body").append(ems);
    setTimeout(function(){
        if($('#img_cont').find('img').height()> $(window).height()){
            $('#img_cont').find('img').css({"height":($(window).height()-120)+'px'});
        }
        //超过窗口宽度  自适应
        if($('#img_cont').find('img').width()> $(window).width()){
            $('#img_cont').find('img').css({"width":($(window).width()-80)+'px'});
        }
        imgHeight = $('#img_cont').find('img').height();
        imgWidth = $('#img_cont').find('img').width();
        $('#img_cont').find('.imgFixCont').css({width:imgWidth+'px',height:imgHeight+'px'});
    },100);
    setTimeout(function(){rongDetailDialog("#img_cont",option,a);},200);
}