//jquery index 1.0
//create by zengrong (zrongs@vip.qq.com)

$(document).ready(function(e) {
	$('.search-cont').mouseover(function(){
		$(this).removeClass('search-mask');
	});
	$('.search-cont').mouseout(function(){
		$(this).addClass('search-mask');
	});
});