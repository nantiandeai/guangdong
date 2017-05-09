/**
 * 根据导航栏目下标索引设置当前栏目。
 */
function currentNav(){
	$('#whgnav > li > a').each(function(idx){
		if(false && (idx == 1 || idx ==3 || idx ==4 || idx ==6 || idx ==7)){
			$(this).bind('click.sz', function(e){
				e.preventDefault();return;
			});
		}else{
			var __href = $(this).attr('href');
			var idx = __href.indexOf(basePath+"/");
			
			var iscurMode = false;
			if(idx > -1){
				var __mode = __href.substring(idx+basePath.length+1);
				if(__mode != '' && __mode.indexOf('/') > -1){
					__mode = __mode.substring(0, __mode.indexOf('/'));
				}
				if(__mode == __nav){
					iscurMode = true;
				}
			}
			if(iscurMode){
				$(this).parents('li').attr('class', 'active');	
			}else{
				$(this).parents('li').removeAttr('class');
			}
			$(this).unbind('click.sz');
		}
	});
}

/**
 * phone 手机格式化
 */
function numFMT(val, rowData, index){
	if(!val) return val;
	var reg = /(\d{3})\d{4}(\d{4})/;
	return val.replace(reg,"$1****$2");;
}

/** 页面加载完成后的处理 */
$(function(){
	//设置当前栏目
	currentNav();
	
	/**
	 * 个人用户中心
	 */
	$("#userName").click(function(){
		var id=$("#userName").attr("data");
		//alert(id);
		window.location.href=basePath+"/center/userCenter?id="+id;
	});
	
});