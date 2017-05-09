<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- 公共点评的页面片段 -->
<div class="public-comment">
	<h3>评论</h3>
	<div class="public-comment-list" id="szwhgCommentContainer">
		<%--<div class="item">
        	<p class="author">文独青年 :<span>10小时前</span></p>
            <p class="msg">已经上了三周课程了，对粤剧有了新的认识，学习到了非常多的传统文化，也明白了粤剧的精髓和魅力所在，  我会努力继续的是学习跟进下去，老师也非常厉害，在专业。</p>
	         <div class="reMsg">
	          <p class="author">管理员A :</p>
	          <p class="msg">已经上了三周课程了，对粤剧有了新的认识，学习到了非常多的传统文化，也明白了粤剧的精髓和魅力所在，  我会努力继续的是学习跟进下去，老师也非常厉害，在专业。</p>
	        </div>
        </div>
        <div class="item">
            	<p class="author">文独青年 :<span>10小时前</span></p>
                <p class="msg">已经上了三周课程了，对粤剧有了新的认识，学习到了非常多的传统文化，也明白了粤剧的精髓和魅力所在，  我会努力继续的是学习跟进下去，老师也非常厉害，在专业。</p>
             <div class="reMsg">
              <p class="author">管理员A :</p>
              <p class="msg">已经上了三周课程了，对粤剧有了新的认识，学习到了非常多的传统文化，也明白了粤剧的精髓和魅力所在，  我会努力继续的是学习跟进下去，老师也非常厉害，在专业。</p>
            </div>
		</div>--%>
		<div class="getMsg">
			<textarea id="szwhgCommentContent"></textarea>
			<%--<textarea onpropertychange="if(value.length>100) value=value.substr(0,100)" class="smallArea" cols="60" name="txta" rows="8"></textarea>--%>
		</div>
		<div class="postMsg clearfix">
			<span><a href="javascript:void(0)" id="szwhgCommentBtn">点评</a></span>
		</div>
	</div>
</div>
<script type="text/javascript">
/** 加载点评数据 */
function loadComment(){
	var reftyp = '${param.reftype }';
	var refid = '${param.refid }';
	
	//请求后台数据
	$.ajax({
		type: "POST",
		url: '${basePath}/srchcomment?reftyp='+reftyp+'&refid='+refid,
		success: function(data){
			//删除原先的评论
			$('#szwhgCommentContainer > .item').remove();
			
			var rmids = "";
			var _html = '';
			if($.isArray(data)){
				$.each(data, function(i, d){
					rmids += d.rmid+",";
					_html += '<div class="item" refrmid="'+d.rmid+'">';
					_html += '	<p class="author">'+(d.nickname || '')+' :<span>'+new Date(d.rmdate).Format('yyyy-MM-dd hh:mm:ss')+'</span></p>';
					_html += '	<p class="msg">'+(d.rmcontent||"&nbsp;")+'</p>';
					_html += '</div>';
				});
			}
			$('#szwhgCommentContainer').prepend(_html);
			
			loadhuifu(rmids);
		}
	});
}/** 加载点评数据 -END*/

//加载回复
function loadhuifu(rmids){
	if (!rmids) return;
	$.getJSON('${basePath}/srchcommentHuifu', {rmids: rmids}, function(data){
		if (!data || data.length<1) return;
		for(var i in data){
			var d = data[i];
			var refrmid = d.rmrefid;
			var _itm = $('#szwhgCommentContainer > .item[refrmid="'+refrmid+'"]');
			if (!_itm.length) continue;
			
			var _rediv = $('<div class="reMsg"></div>');
			_rediv.append('<p class="author">'+(d.account || "")+' :</p>');
			_rediv.append('<p class="msg">'+(d.rmcontent||"&nbsp;")+'</p>');
			
			_itm.append(_rediv);
		}
	})
	
}



/** 点评 */
function doComment(e){
	e.preventDefault();
	
	//点评/实体类型/实体id
	var content = $('#szwhgCommentContent').val();
	var str = (content.replace(/\w/g,"")).length;
	var abcnum = content.length-str;
	var total = str+abcnum;
	if(total > 250){
		wxlDialog({
			title: '字数超过250个',
			type: false
		});
		return false;
	}

	var reftyp = '${param.reftype }';
	var refid = '${param.refid }';
	var rmurl = window.location.href;
	var rmtitle = document.title;
	
	//未输入内容
	if(content == ''){
		wxlDialog({
			title: '请输入点评内容',
			type: false
		});
		return;
	}
	
	//保存
	if(content && reftyp && refid){
		$.ajax({
			type: "POST",
			url: basePath+'/addcomment',
			data: {'rmcontent':content, 'rmtitle':rmtitle, 'rmurl':rmurl, 'rmreftyp':reftyp, 'rmrefid':refid, 'rmtyp':'0', 'rmstate':'0'},
			success: function(data){
				if(data.success == '0'){
					wxlDialog({
						title: '点评成功，审核通过后显示内容',
						type: true
					});
					//成功，重新加载点评
					loadComment();
					$('#szwhgCommentContent').val('');
				}else if(data.success == '1'){
					//失败
				}else if(data.success == '2'){
					//未登录，先登录, 弹出登录 会话框
					//window.location.href = basePath+'/login?preurl='+encodeURIComponent(window.location.href); 
					window.location.href = '${basePath}/login'; 
				}
			}
		});
	}
}/** 点评-END */

/** 限制字数 */
//$('#szwhgCommentContent').on('keydown', function(e){
//	if(this.value.length >= 5){
//		e.returnValue = false;
//		return false;
//	}
//});

/** 给点评按钮添加事件 */
$('#szwhgCommentBtn').on('click', doComment);

/** 默认进页面时加载评论 */
loadComment();
</script>