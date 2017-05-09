/**
 * 页面加载完后处理
 */
$(function(){
	//判断是否有绑定标识
	if($(".unBind").length>0){
		$.ajax({
			type : "POST",
			url : getFullUrl('./center/commBind'),
			success : function(data){
				if(data.s1 == "1"){
					//点亮手机绑定
					$("#setPhone").removeClass('unBind').addClass('Bind');
					jQuery('#setPhone').tipso('update', 'content', "已绑定手机");
				}
				if(data.s2 == "2"){
					//点亮邮箱绑定
					$("#setEmail").removeClass('unBind').addClass('Bind');
					jQuery('#setEmail').tipso('update', 'content', "已绑定邮箱");
				}
				if(data.s3 == "3"){
					//点亮实名绑定
					$("#setIsRealName").removeClass('unBind').addClass('Bind');
					jQuery('#setIsRealName').tipso('update', 'content', "已实名认证");
				}
			}
		});
	}
});