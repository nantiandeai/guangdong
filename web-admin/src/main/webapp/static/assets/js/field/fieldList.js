/**
 * Created by Zengrong on 2016/10/28 0017.
 */
$(document).ready(function(e) {
	$('.masterRow ul').delegate('li a','click',function(){
		$(this).parent().addClass("active").siblings().removeClass('active');
	}); 
	$('.masterRow ul').delegate('.showAdr a','click',function(){
		$('.adrCont').stop(1,0).fadeIn(500);
	});
	$('.masterRow ul').delegate('.closeAdr a','click',function(){
		$('.adrCont').stop(1,0).fadeOut(0);
	});
})