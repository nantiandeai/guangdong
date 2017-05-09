/*---------------------------------------------------------------------
	* Filename:         sign.js
	* Description:      sign
	* Version:          1.0.0 (2016-10-25)
	* Website:          hn.sun3d.com
	* Author:           JackyZeng (zrongs@vip.qq.com)
---------------------------------------------------------------------*/
$(document).ready(function() {
	var actionTime = 800;
	$('.individual a').click(function(){
		$t = $('.individual');
		$(this).fadeOut(actionTime);
		$t.siblings().fadeOut(actionTime);
		setTimeout(function(){
			$t.animate({left:'315px',top:'-10px'},actionTime/2);
			$('.reLoad').fadeIn(actionTime);
		},actionTime);
		setTimeout(function(){
			$('.next1').fadeIn(actionTime);
		},actionTime*2);
	});
	
	$('.teamReg a').click(function(){
		$t = $('.teamReg');
		$(this).fadeOut(actionTime);
		$t.siblings().fadeOut(actionTime);
		setTimeout(function(){
			$t.animate({left:'310px',top:'-10px'},actionTime);
			$('.reLoad').fadeIn(actionTime);
		},actionTime);
		setTimeout(function(){
			$('.next2').fadeIn(actionTime);
		},actionTime*2);
		
	});
	$('.tipso_top').tipso({
			  position: 'top',
			  animationIn: 'fadeIn',
			  background: 'rgb(50,50,50)'
	});
})