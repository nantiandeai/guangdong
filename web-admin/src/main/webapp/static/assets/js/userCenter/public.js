$(document).ready(function(e) {
	$('.tipso_top').tipso({
			  position: 'top',
			  animationIn: 'fadeIn',
			  background: 'rgb(50,50,50)'
	});
	$('.leftPanel').css('minHeight',getLeftHeight());
	$('.rightPanel .item').mouseover(function(){
		$(this).addClass('active');
	});
	$('.rightPanel .item').mouseout(function(){
		$(this).removeClass('active');
	});
		$('.error').mouseover(function(){
		$(this).children('.err-msg').stop(0,1).fadeIn();
	});
	$('.error').mouseout(function(){
		$(this).children('.err-msg').stop(0,1).fadeOut();
	});
});
$(window).resize(function() {
	$('.leftPanel').css('minHeight',getLeftHeight());
});

function getLeftHeight(){
	var l_h = 600;
	if($('.rightPanel').outerHeight() < l_h){
		return l_h;
	}else{
		return $('.rightPanel').outerHeight();
	}
}