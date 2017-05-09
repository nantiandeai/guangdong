<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<link href="${basePath }/static/assets/js/plugins/oiplayer-master/css/oiplayer.css" rel="stylesheet">
<script src="${basePath }/static/assets/js/plugins/oiplayer-master/plugins/flowplayer-3.2.6.min.js" type="text/javascript"></script>
<script src="${basePath }/static/assets/js/plugins/oiplayer-master/js/jquery.oiplayer.js" type="text/javascript"></script>

<script type="text/javascript">
$(function(){
	//自动注册音视频播放
	var basePath = $("base").attr("href");
	$("body").delegate(".opt_videoshow", "click", {basePath:"${basePath }"}, function(event){
		var me = $(this);
		var url = me.attr("vsurl");
		event.data.url = url;
		show_vedio(this, event.data);
	})
});
</script>