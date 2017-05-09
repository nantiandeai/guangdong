$(document).ready(function(e) {
    $('.teacherList ul').delegate('li','mouseover',function(){
		$(this).addClass('active');
	});
	$('.teacherList ul').delegate('li','mouseout',function(){
		$(this).removeClass('active');
	});
})