/**
 * Created by LENCON on 2016/11/11.
 */
$(document).ready(function () {
   $('.bottomImgList .more').on('click',function(){
	   $(this).next('ul').css({'height':'auto'});
	   $(this).fadeOut(0);
   })
});