<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- 公共点赞片段 -->
<div class="fabulous-btn"><span></span><i><a href="javascript:void(0);"></a></i></div>
<script type="text/javascript">
/** 设置点赞数量  */
function loadFabulous(){
	var reftyp = '${param.reftype }';
	var refid = '${param.refid }';
	
	//判断是否点亮
	$.ajax({
		type : "POST",
		url : '${basePath}/comm/isLightenGood',	
		data : {reftyp : reftyp, refid : refid},
		success : function(data){
			if(data.success == "1"){
				$('div.fabulous-btn > i > a').attr('isGood', 'true');
			}else{
				$('div.fabulous-btn > i > a').attr('isGood', 'false');
			}
			$('div.fabulous-btn > span').text(data.num);
		}
		
	});
}/** 设置点亮效果 -END*/

/** 点赞 */
function doFabulous(e){
	e.preventDefault();
	
	//获取属性  reftyp refid isGood
	var reftyp = '${param.reftype }';
	var refid = '${param.refid }';
	var isGood = $('div.fabulous-btn > i > a').attr('isGood') == 'true';
	
	if(isGood){
		wxlDialog({
			title: '您已经点赞,谢谢参与',
			type: false
		});
		return;
	}
	
	//判断是否已点赞
	if(!isGood){
		//未点赞
		$.ajax({
			type : "POST",
			url : '${basePath}/comm/addGood',	//添加点赞记录
			data: {cmreftyp : reftyp, cmrefid : refid},
			success : function(data){
				if(data.success == "0"){
					//alert("点赞成功");
        			//$("#good").removeClass("dianzan").addClass("dianzanclick");
        			loadFabulous();
				}else{
					//alert("点赞失败");
				}
			}
		});
	}
}/** 点赞-END */

/** 给点评按钮添加事件 */
$('div.fabulous-btn > i > a').on('click', doFabulous);

/** 默认进页面时加载评论 */
loadFabulous();
</script>