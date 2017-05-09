$(document).ready(function(e) {
	$('.dropify').dropify({
			messages: {
				'default': '点击或拖拽文件到这里',
				'replace': '',
				'remove': '重新选择',
				'error': '对不起，你上传的文件太大了'
			}
	});
	
	$('.cardA').on('click',function(){
		rongDetailDialog('.js__cardA_popup');
	});
	
	$('.cardB').on('click',function(){
		rongDetailDialog('.js__cardB_popup');
	});
})