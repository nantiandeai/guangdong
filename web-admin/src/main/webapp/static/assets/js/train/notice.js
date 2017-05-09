$(document).ready(function(e) {
    $('.main-noticeList-container ul').delegate('li','mouseover',function(){
		$(this).addClass('active');
	});
	$('.main-noticeList-container ul').delegate('li','mouseout',function(){
		$(this).removeClass('active');
	});
})