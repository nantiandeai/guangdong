/**
 * 页面加载完处理
 */
$(document).ready(function(){
	//判断是否有已绑定标识
	if($(".err").length>0){
		$.ajax({
			type : "POST",
			url : getFullUrl('./center/isBind'),
			success : function(data){
				if(data.s1 == "1"){
					//设置为手机已绑定
					//$("#isBindPhone").html("已设置");
					$("#isBindPhone").removeClass('err').addClass('success');
					
				}
				if(data.s2 == "2"){
					//设置为邮箱已绑定
					//$("#isBindEmail").html("已设置");
					$("#isBindEmail").removeClass('err').addClass('success');

				}
				if(data.s3 == "3"){
					//密码已设置
					$("#isSet").html("已设置");
					$("#setPwd").removeClass('err').addClass('success');
				}

				if(data.s4 == "4" || data.s4 == "1"){
					//是否已实名
					$("#isReal").html("已实名");
					$("#setReal").removeClass('err').addClass('success');
				}else if(data.s4 == "2"){
                    $("#isReal").html("实名审核未通过");
                    $("#setReal").removeClass('success').addClass('err');
				}else if(data.s4 == "3"){
                    $("#isReal").html("实名审核中");
                    $("#setReal").removeClass('err').addClass('success');
                }
			}
		})
	}
});