/**
 * Created by LENCON on 2016/11/11.
 */
$(document).ready(function () {
   fixHeader();
   $('.header-nav ul').delegate('.add-nav','mouseover',function(){
   		$(this).find('.nav-more').stop(1,0).slideDown(200);
   });
   $('.header-nav ul').delegate('.add-nav','mouseout',function(){
   		$(this).find('.nav-more').stop(1,0).slideUp(200);
   });
   $('.to-top').toTop({
        autohide: true, 
        offset: 420,   
        speed: 500,    
        right: 30,    
        bottom: 143    
   });
});

/*----固定导航----*/
function fixHeader(){
   $('#header-fix').addClass('td-abs')
	  $('.td-abs').each( function() {  
			var stickyTop = $('.td-abs').offset().top;
			var headerMenu = function(){
				var scrollTop = $(window).scrollTop(); 
				$('#header-sm').css({'marginBottom':'100px'})
				if ('undefined' != typeof(document.body.style.maxHeight)) { 
					if (scrollTop > stickyTop){ 
						$('.td-abs').css({ 'position': 'fixed', 'top':0 ,'z-index':99999,'borderTop':'5px','borderTopColor':'#332c2b','borderTopStyle':'solid',}).addClass('dg-fix');
					} else {
						$('.td-abs').css({ 'position': 'absolute', 'top':stickyTop,'z-index':10,'borderTopWidth':'0px'}).removeClass('dg-fix'); 
					}
				}
			};
			headerMenu();
			$(window).scroll(function() {
				headerMenu();
		});
	});
}

!
function(o) {
    o.fn.toTop = function(t) {
        var e = this,
        i = o(window),
        s = o("html, body"),
        n = o.extend({
            autohide: !0,
            offset: 420,
            speed: 500,
            right: 15,
            bottom: 30
        },
        t);
        e.css({
            position: "fixed",
            right: n.right,
            bottom: n.bottom,
            cursor: "pointer"
        }),
        n.autohide && e.css("display", "none"),
        e.click(function() {
            s.animate({
                scrollTop: 0
            },
            n.speed)
        }),
        i.scroll(function() {
            var o = i.scrollTop();
            n.autohide && (o > n.offset ? e.fadeIn(n.speed) : e.fadeOut(n.speed))
        })
    }
} (jQuery);