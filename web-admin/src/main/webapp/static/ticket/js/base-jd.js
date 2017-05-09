/**
 * Created by minh on 2015/8/11.
 */
$(function(){
    /*文本框提示*/
    $(".input-text").focus(function(){
        var $this = $(this);
        if($this.val() == $this.attr("data-val")){
            $this.val("").css({"color": "#666666"});
        }
    });
    $(".input-text").blur(function(){
        var $this = $(this);
        if($this.val() == "" || $this.val() == null || $this.val() == $this.attr("data-val")){
            $this.val($this.attr("data-val")).removeAttr("style");
        }
    });
    /*首页网上书房*/
    $(function(){
        $(".in-online .img").on({
            "mouseenter": function(){
                var $this = $(this);
                var enter = $this.find(".enter");
                enter.animate({"left": "0"});
            },
            "mouseleave": function(){
                var $this = $(this);
                var enter = $this.find(".enter");
                enter.animate({"left": "156px"});
            }
        }, ".link")
    });
    /*分页*/
    $("#page").on("click", ".pageNum", function(){
        var $this = $(this);
        $this.siblings(".list").toggle();
        $this.toggleClass("on");
        return false;
    });
    $("#page").on("click", ".list a", function(){
        var $this = $(this);
        $("#page .list").hide();
        $("#page .pageNum em").text($this.text());
        return false;
    });
    $(document).click(function(){
        $("#page .pageNum").removeClass("on");
        $("#page .list").hide();
    });
    /*a标签点击当前样式*/
    $(".menu-box").on("click", "a", function(){
        var $this = $(this);
        $this.addClass("curr").siblings().removeClass("curr");
    });
    /*详情页为您推荐 换一批*/
    var top = 0;
    $("#change").on("click", function(){
        var list = $(this).parent().siblings(".recommend-list");
        var listUl = list.find("ul");
        var listHeight = list.height();
        var listUlHeight = listUl.height();
        top = top - listHeight;
        if(listUlHeight - Math.abs(top) < listHeight){
            top = 0;
        }
        listUl.animate({"marginTop": top});
    });
    /*场馆详情 选择活动室*/
    /*$("#venues-table").on("click", ".default", function(){
        var $this = $(this);
        if($this.hasClass("available")){
            $this.removeClass("available").addClass("selected");
        }else if($this.hasClass("selected")){
            $this.removeClass("selected").addClass("available");
        }
    });*/
    /*数字展馆列表*/
    $(".digital-list .txt").hover(function(){
        $(this).addClass("open").stop().animate({"top": 0});
    }, function(){
        $(this).removeClass("open").stop().animate({"top": "235px"});
    });
    /*网上书房*/
    $(".book-room-cont .slider .txt").hover(function(){
        $(this).addClass("open").stop().animate({"top": 0});
    }, function(){
        $(this).removeClass("open").stop().animate({"top": "190px"});
    });
    /*成功页面满屏显示*/
    setFullScreen();
    /*场次选择*/
    $(".cate .caption").each(function(){
        var thisVal = $(this).parent().find("option:selected").text();
        if(thisVal != "请选择所属团体") $(this).removeClass("default");
        $(this).text(thisVal);
    });
    $(".room-part1 .cate").on("change", "select", function(){
        var selectVal =  $(this).find("option:selected").text();
        var textDom = $(this).parent().find(".caption");
        if(selectVal == "请选择所属团体"){
            textDom.addClass("default");
        }else {
            textDom.removeClass("default")
        }
        textDom.text(selectVal);
    })

});
/*改变窗口大小*/
$(window).resize(function(){
    /*成功页面满屏显示*/
  //  setFullScreen();
    setUserFullScreen();
    setPopupTop("login_pop_layer");
});

/*加入收藏*/
function addFavorite2() {
    var url = window.location;
    var title = document.title;
    var ua = navigator.userAgent.toLowerCase();
    if (ua.indexOf("360se") > -1) {
        alert("由于360浏览器功能限制，请按 Ctrl+D 手动收藏！");
    }
    else if (ua.indexOf("msie 8") > -1) {
        window.external.AddToFavoritesBar(url, title); //IE8
    }
    else if (document.all) {
        try{
            window.external.addFavorite(url, title);
        }catch(e){
            alert('您的浏览器不支持,请按 Ctrl+D 手动收藏!');
        }
    }
    else if (window.sidebar) {
        window.sidebar.addPanel(title, url, "");
    }
    else {
        alert('您的浏览器不支持,请按 Ctrl+D 手动收藏!');
    }
}


/*打开详情页*/
function showDetail(url){
    $("html").css({overflow:"hidden"});
    var $body = $('body');
    if(window.top!=window.self && (window.parent.location.href.indexOf("get-tickets.html")==-1 && window.parent.location.href.indexOf("collect_info.jsp")==-1)){  /*如果有父窗口时，在当前iframe里面直接打开页面*/
        location.href = url;
    }else {
        var bodyBg = $('<div id="body_bg" style="display: none;"><img src="../STATIC/image/loading.gif"></div>').appendTo($body);
        $("#body_bg").fadeIn();
        var layerDiv = $('<div id="layer_bg"><a class="close-btn" id="close-detail-btn"></a></div>').css({"display": "none"}).appendTo($body);
        layerDiv.append('<div id="pop_layer"><iframe style="background-color:transparent;" allowtransparency="true" id="show_detail" frameborder="0" scrolling="yes" width="100%" height="100%" ></iframe></div>');

        iframeLoad(url,"show_detail");
    }
    /*关闭详情窗口*/
    $("#layer_bg").on("click", "#close-detail-btn", function(){
        $("#body_bg").remove();
        $("#layer_bg").remove();
        $("html").css({overflow:"auto"});
    });
}
/*打开弹出窗*/
function showPopLayer(url){
    $("html").css({overflow:"hidden"});
    var $body = $('body');
    var bodyBg = $('<div id="login_body_bg" style="display: none;"><img src="../STATIC/image/loading.gif"></div>').appendTo($body);
    var layerDiv = $('<div id="login_layer_bg"><a class="close-btn" id="close-login-btn"></a></div>').css({"display":"none"}).appendTo($body);
    if(url.indexOf("userLogin.do")!=-1){
        $("#login_body_bg").addClass("opacity50").show();
    }else{
        $("#login_body_bg").fadeIn();
    }
    layerDiv.append('<div id="login_pop_layer" style="width: 600px; position: absolute;"><iframe style="background-color:transparent;" allowtransparency="true" id="show_login" frameborder="0" scrolling="no" width="100%" height="100%" ></iframe></div>');

    iframeLoad(url,"show_login");

    /*关闭详情窗口*/
    $("#login_layer_bg").on("click", "#close-login-btn", function(){
        $("#login_body_bg").remove();
        $("#login_layer_bg").remove();
        $("html").css({overflow:"auto"});
    });
}

/*打开终端机登录弹出窗*/
function showLoginPopLayer(url){
    $("html").css({overflow:"hidden"});
    var $body = $('body');
    var bodyBg = $('<div id="login_body_bg" style="display: none;"><img src="../STATIC/image/loading.gif"></div>').appendTo($body);
    var layerDiv = $('<div id="login_layer_bg"><a class="close-btn log-close-btn" id="close-login-btn" style="margin-top: -250px;"></a></div>').css({"display":"none"}).appendTo($body);
    if(url.indexOf("userLogin.do")!=-1){
        $("#login_body_bg").addClass("opacity50").show();
    }else{
        $("#login_body_bg").fadeIn();
    }
    layerDiv.append('<div id="login_pop_layer" style="width: 1000px; position: absolute;"><iframe style="background-color:transparent;" allowtransparency="true" id="show_login" frameborder="0" scrolling="no" width="100%" height="100%" ></iframe></div>');

    iframeLoad(url,"show_login");

    /*关闭详情窗口*/
    $("#login_layer_bg").on("click", "#close-login-btn", function(){
        $("#login_body_bg").remove();
        $("#login_layer_bg").remove();
        $("html").css({overflow:"auto"});
    });
}



/*iframe 加载事件*/
function iframeLoad( url,ifrId){
    var oIframe = document.getElementById(ifrId);
    oIframe.onload = oIframe.onreadystatechange = function(){
        iframeLoadSuccess(ifrId);
    }
    oIframe.src = url;
}
/*判断iframe是否加载完成，加载完成之后iframe显示*/
function iframeLoadSuccess(ifrId){
    var oIframe = document.getElementById(ifrId);
    if (!oIframe.readyState || oIframe.readyState == "complete") {
        if(ifrId == "show_login"){
            $('#login_layer_bg').css('display', 'block');
            $("#login_body_bg").empty();
        }else {
            $('#layer_bg').css('display', 'block');
            $("#body_bg").empty();
        }
        oIframe.style.display = 'block';
        iframeHeight(ifrId);
    }
}
/*获取iframe的高度*/
function iframeHeight(ifrId){
    var winHeight = $(window).height();
    var oIframe = document.getElementById(ifrId);
    var subWeb = document.frames ? document.frames[ifrId].document : oIframe.contentDocument;
    if(oIframe != null && subWeb != null) {
        var iframeUrl = oIframe.src;
        if(iframeUrl.indexOf("userLogin.do")!=-1){
            oIframe.height = subWeb.body.clientHeight;
            setPopupTop("login_pop_layer", subWeb.body.clientHeight);
        }else{
            //oIframe.height = subWeb.body.scrollHeight;
            //oIframe.height = "100%";
            oIframe.height = winHeight;
        }
    }
}
function alertDialog(dialogTit, dialogcontent,status, fn){
    jAlert(dialogcontent, dialogTit, status, function(r) {
        if(r){
            if(fn){
                fn();
            }
        }
    });
}
function confirmDialog(dialogTit, dialogcontent, fn){
    jConfirm(dialogcontent, dialogTit, function(r) {
        if(r){
            if(fn){
                fn();
            }
        }
    });
}
/*父窗口页面跳转*/
function parentPageJump(url){
    //top.window.location.href = url;
    parent.window.location.href = url;
}
/*设置弹出框的位置*/
function setPopupTop(cId, idHeight){
    var obj = $('#'+cId);
    if(obj.length > 0) {
        obj.css('height', idHeight);
        var objTop = parseInt(($(window).height() - $(obj).height()) / 2);
        var objLeft = parseInt(($(window).width() - $(obj).width()) / 2);
        if (objTop > 0) {
            obj.css('top', objTop);
        } else {
            obj.css('top', '0px');
        }
        if (objLeft > 0) {
            obj.css('left', objLeft);
        } else {
            obj.css('left', '0px');
        }
    }
}
/*成功页面满屏显示*/
function setFullScreen(){
    if($("#register-content").length > 0) {
        var otherHeight = $(window).height() - 301;
        var regHeight = $("#register-content").height();
        if (otherHeight > regHeight) {
            $("#register-content").css({"height": otherHeight});
        } else {
            $("#register-content").removeAttr("style");
        }
    }
}
function setUserFullScreen(){
    if($("#content").length > 0) {
        $("#content").removeAttr("style");
        var otherHeight = $(window).height() - 231;
        var usercontHeight = $("#content").height();
        if (otherHeight > usercontHeight) {
            $("#content").css({"height": otherHeight});
        } else {
            $("#content").removeAttr("style");
        }
    }
}
/*地图滚动条*/
function setScrollBar(obj){
    var H = obj.height();
    var oMapMo=obj.find(".map-result");
    var oRollMap=obj.find(".scroll");
    var oRoll=obj.find(".scrollBar");
    var oUpMouse=obj.find(".map-list");
    var oMapList=oUpMouse.find("ul");
    var mapPd=obj.find(".search-bar");
    var wheelnum=0;

    $(".expCon").css({top:H/2});
    $("#map,.mapHeight").css({height:H});
    oUpMouse.css({height:H-mapPd.innerHeight()});
    oRollMap.css({height:H-mapPd.innerHeight()+22});

    if(oMapList.height()<oUpMouse.height()){
        oRollMap.hide();
        return false;
    }else{
        oRoll.css({height:oUpMouse.height()/oMapList.height()*oRollMap.height()})
    }
    var oRH=oRollMap.height()-oRoll.height();
    var xH=oUpMouse.height()-oMapList.height();
    if(!oRoll.get(0)) return
    oRoll.get(0).onmousedown=function(ev){
        var ev=ev||event;
        var disY=ev.clientY-this.offsetTop;
        if(oRoll.get(0).setCapture){
            oRoll.get(0).setCapture();
        }
        document.onmousemove=function(ev){
            var ev=ev||event;
            var T=ev.clientY-disY;
            wheelnum=T
            wheelmove(wheelnum);
        }
        document.onmouseup=function(ev){
            document.onmousemove=document.onmouseup=null;
            if(oRoll.get(0).releaseCapture){
                oRoll.get(0).releaseCapture();
            }
        }
        return false;
    }
    oMapMo.on({
        "DOMMouseScroll": function(){
            gunUp();
        },
        "mousewheel": function(){
            gunUp();
        }}
    );

    function gunUp(ev){
        var ev=ev||event;
        var b=true;
        if(ev.wheelDelta){
            b=ev.wheelDelta<0?true:false
        }else{
            b=ev.detail>0?true:false
        }
        if(b){

            wheelnum+=30;
            if(oRH<wheelnum){
                wheelnum=oRH;
            }

            wheelmove(wheelnum)
        }else{

            wheelnum-=30;
            if(wheelnum<0){
                wheelnum=0;
            }
            wheelmove(wheelnum)
        }
        if(ev.preventDefault)
        {
            ev.preventDefault();
        }
        return false;

    }
    function wheelmove(T){
        if(T<0){
            wheelnum=T=0;

        }else if(T>oRH){
            wheelnum=T=oRH;

        }

        oRoll.get(0).style.top=T+"px";
        var iScale=T/(oRollMap.height()-oRoll.height())
        oMapList.css({top:iScale*(xH)});
    }
}
/*预订页面 文本框默认值*/
$(function(){
    $(".tab1").on("blur input propertychange",'.input-text', function(){
        if($(this).val() == ''){
            $(this).parent().addClass("showPlaceholder");
        }else{
            $(this).parent().removeClass("showPlaceholder");
        }
    });
    $(".placeholder").on({
        click:function(){ $(this).parent().find(".input-text").focus();},
        dblclick:function(){ $(this).parent().find(".input-text").focus();}
    });

    /*IE8 IE7 IE6*/
    var userAgent = window.navigator.userAgent.toLowerCase();
    $.browser.msie10 = $.browser.msie && /msie 10\.0/i.test(userAgent);
    $.browser.msie9 = $.browser.msie && /msie 9\.0/i.test(userAgent);
    $.browser.msie8 = $.browser.msie && /msie 8\.0/i.test(userAgent);
    $.browser.msie7 = $.browser.msie && /msie 7\.0/i.test(userAgent);
    $.browser.msie6 = !$.browser.msie8 && !$.browser.msie7 && $.browser.msie && /msie 6\.0/i.test(userAgent);

    if($.browser.msie8 || $.browser.msie7 || $.browser.msie6){
        $(".tab1").on("focus",'.input-text', function(){
            $(this).parent().removeClass("showPlaceholder");
        });
    }
});
function dialogConfirm(title, content, fn){
    if(top.dialog){
        dialog = top.dialog;
    }
    var d = dialog({
        width:400,
        title:title,
        content:content,
        fixed: true,
        button:[{
            value: '确定',
            callback: function () {
                if(fn)  fn();
                //this.content('你同意了');
                //return false;
            },
            autofocus: true
        },{
            value: '取消'
        }]
    });
    d.showModal();
}

function dialogAlert(title, content, fn){
    if(top.dialog){
        dialog = top.dialog;
    }
    var d = dialog({
        width:400,
        title:title,
        content:content,
        fixed: true,
        okValue: '确 定',
        ok: function () {
            if(fn)  fn();
        }
    });
    d.showModal();
}
