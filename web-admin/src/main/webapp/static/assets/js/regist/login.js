/*---------------------------------------------------------------------
	* Filename:         login.js
	* Description:      login
	* Version:          1.0.1 (2016-10-20)
	* Website:          hn.sun3d.com
	* Author:           JackyZeng (zrongs@vip.qq.com)
---------------------------------------------------------------------*/


$(document).ready(function() {
	
	$('.input-row').click(function(){
		$(this).addClass('active');
	});
	
	$('.input-row').mouseover(function(){
		$(this).addClass('active');
	});
	
	$('.input-row').mouseout(function(){
		$(this).removeClass('active');
	});
})