$(document).ready(function(e) {
    $('.vedio-groups ul li').delegate('.item','mouseover',function(){
		$(this).find('.mask').stop(1,0).fadeIn(600);
	});
	$('.vedio-groups ul li').delegate('.item','mouseout',function(){
		$(this).find('.mask').stop(1,0).fadeOut(600);
	});
	$('.hotTrain .hotTrainGroups ul').delegate('li','mouseover',function(){
		$(this).stop(1,0).animate({marginTop:'0px'},300);
		$(this).find('.mask').stop(1,0).fadeIn(600);
	});
	$('.hotTrain .hotTrainGroups ul').delegate('li','mouseout',function(){
		$(this).stop(1,0).animate({marginTop:'20px'},300);
		$(this).find('.mask').stop(1,0).fadeOut(600);
	});
	$('.group').sly({
		itemNav: "centered",
		easing: "easeOutExpo",
		prevPage: ".vedio-next",
		nextPage: ".vedio-prev",
		horizontal: 1,
		touchDragging: 1
	});
	
	$('.train-nav-cont').delegate('li','click',function(){
		$(this).addClass("active").siblings().removeClass('active');
	});
	
	$('.newsGroups ul').delegate('li','click',function(){
		$(this).addClass("active").siblings().removeClass('active');
		$(".tab_content").eq($(".newsGroups ul li").index(this)).addClass("active").siblings().removeClass('active');
	});
	
	$('.resource').sly({
		itemNav: "centered",
		easing: "easeOutExpo",
		nextPage: ".arrow-right",
		horizontal: 1
	});
	
	$('.resource ul').delegate('li','mouseover',function(){
		$(this).find('.mask').stop(1,0).animate({left:'0px'},600);
	});
	
	$('.resource ul').delegate('li','mouseout',function(){
		$(this).find('.mask').stop(1,0).animate({left:'-590px'},600);
	});
	
	$('.news-cont .img').sly({
		itemNav: "basic",
		easing: "easeOutExpo",
		nextPage: ".btn-top-prev",
		prevPage: ".btn-top-next",
		horizontal: 1,
		touchDragging: 1
  	});
	
})