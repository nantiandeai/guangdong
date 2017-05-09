/**
 * Created by LENCON on 2016/11/11.
 */
$(document).ready(function () {
  $('.science-cont').delegate('.item','mouseover',function(){
  	$(this).css({"background-color":"#f5f5f5"});
  });
  $('.science-cont').delegate('.item','mouseout',function(){
  	$(this).css({"background":"none"});
  })
});