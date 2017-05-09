$(document).ready(function(e) {
    $('.registration .con ul li').delegate('a','mouseover',function(){
		$(this).find('.mask').stop(1,0).fadeIn(600);
	});
	$('.registration .con ul li').delegate('a','mouseout',function(){
		$(this).find('.mask').stop(1,0).fadeOut(600);
	});

    // $('.masterRow ul').delegate('li a','click',function(){
    //     $(this).parent().addClass("active").siblings().removeClass('active');
    // });
    // $('.masterRow ul').delegate('.showAdr','click',function(){
    //     $('.adrCont').stop(1,0).fadeIn(500);
    // });
    // $('.masterRow ul').delegate('.closeAdr','click',function(){
    //     $('.adrCont').stop(1,0).fadeOut(0);
    // });
})