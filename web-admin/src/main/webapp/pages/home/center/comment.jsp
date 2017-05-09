<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath(); request.setAttribute("basePath", basePath); %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge"> 
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="keywords" content="">
<meta name="author" content="">
<title>广东省文化馆-用户中心</title>
<link href="${basePath }/static/assets/css/public/reset.css" rel="stylesheet">
<link href="${basePath }/static/assets/css/userCenter/userCenter.css" rel="stylesheet">
<script src="${basePath }/static/assets/js/public/jquery-1.11.0.min.js"></script> 
<script src="${basePath }/static/assets/js/public/jquery-migrate-1.0.0.js"></script>
<script src="${basePath }/pages/comm/agdcomm.js"></script>
<%--<script src="${basePath }/static/assets/js/public/comm.js"></script>--%>
<!--[if lt IE 9] >
<script src="${basePath }/static/assets/js/plugins/ie/IE9.js"></script> 
<! [endif]]-->
<!-- core public JavaScript --> 

<script>
/** 数据加载 */
function loadData(page, rows){
	page = page || 1;
	rows = rows || 5;

	var rmreftyp = $(".rightPanel ul.commBtn li.active").attr("rmreftyp");

	$.ajax({
		type: 'post',
		url: '${basePath }/center/loadcomLoad',
		data:{rmuid:"${sessionUser.id}",rmreftyp:rmreftyp,page:page,rows:rows},
		success: function(data){
			//
			var commList = data.commList;
			var commRetryMap = data.commRetryMap;
			var total = data.total;
			showData(commList, commRetryMap);
			
			//加载分页工具栏
			genPagging('artPagging', page, rows, total, loadData);
		}
	});
}

/** 展示数据 */
function showData(data, commentReMap){
	var _html = '';
	if(data.length > 0){
		for(var i=0 ;i<data.length; i++){
			var row = data[i];
			_html += '<li class="item">';
			_html += '	<a id="deletepl" class="trash js__p_orderKick_start" rmid='+row.rmid+' href="javascript:void(0);"></a>';
			_html += '	<div class="orderCont clearfix">';
			_html += '		<div class="orderTime float-left">'+datetimeFMT(row.rmdate)+'</div>';
			_html += '	</div>';
			_html += '	<div class="msgInfoCont">';
			_html += '		<h2>';
			_html += '			<a href="'+row.rmurl+'" target="_blank">'+row.rmtitle+'</a>';
			_html += '		</h2>';
			_html += '		<div class="myComm">';
			_html += '			<i></i>';
			_html += '			<p class="comm">';
			//_html += '				<span>'+numFMT(row.phone)+' :</span>'+row.rmcontent;
			_html += '				<span></span>'+row.rmcontent;
			_html += '			</p>';
			if(commentReMap && typeof commentReMap[row.rmid] != "undefined"){
			_html += '			<p class="reComm">';
			_html += '				<span>'+(commentReMap[row.rmid].account||"")+' :</span>'+commentReMap[row.rmid].rmcontent;
			_html += '			</p>';
			}
			
			_html += '		</div>';
			_html += '	</div>';
			_html += '</li>';
		}
		$('.sysmsg').hide();
		$('#dataUL').html(_html);
	}else{
		$('.sysmsg').show();
		$('#dataUL').html('');
	}
	//弹出 删除框
	$('.js__p_orderKick_start').on('click',function(){
		var rmid=$(this).attr("rmid");
		rongAlertDialog({
			'title'        :  "温馨提示",
			'desc'         :  "您确定删除此评论吗？",
			'closeBtn'     :  false,
			'icoType'      :  2
		},function(){
			$.ajax({
				type:'post',
				url : '${basePath }/center/removeContent',
				data : {
					rmid : rmid
				},
				success : function(data) {
					if (data=="success") {
						loadData();
						
						closeDialog();
						rongDialog({
						title: '删除成功',
						type: true
						})
					} else {
						rongDialog({
						title: '删除成功',
						type: false
						})
					}
				}
			})
		});
	});
	
	//设置导航高度
	$('.leftPanel').css('minHeight', $('.rightPanel').outerHeight());
}

/**
 * phone 格式化
 */
function numFMT(val, rowData, index){
	if(!val) return val;
	var reg = /(\d{3})\d{4}(\d{4})/;
	return val.replace(reg,"$1****$2");;
}

/**页面加载*/
$(function(){
	$(".rightPanel ul.commBtn li").on("click", function () {
		$(this).addClass("active").siblings().removeClass("active");
		loadData();
	});
	loadData();
});
</script>
</head>
<body>

<!-- 公共头部开始 -->
<%@include file="/pages/comm/agdtop.jsp"%>
<!-- 公共头部结束 -->

<!-- 公共绑定开始 -->
<%@include file="/pages/comm/comm_center.jsp"%>
<!-- 公共绑定结束 -->

<div class="main clearfix">
	<div class="leftPanel">
		<ul id="uull">
			<!--用户中心导航开始-->
			<%@include file="/pages/comm/ucnav.jsp"%>
			<!--用户中心导航结束-->
		</ul>
	</div>
	
	<div class="rightPanel">
		<ul class="commBtn clearfix">
			<li class="active" rmreftyp="1"><a href="javascript:void(0)">活动</a></li>
			<li rmreftyp="2"><a href="javascript:void(0)">培训</a></li>
			<li rmreftyp="6"><a href="javascript:void(0)">场馆</a></li>
			<li rmreftyp="7"><a href="javascript:void(0)">场地</a></li>
			<li rmreftyp="3"><a href="javascript:void(0)">培训师资</a></li>
			<li rmreftyp="4"><a href="javascript:void(0)">培训点播</a></li>
			<li rmreftyp="5"><a href="javascript:void(0)">志愿培训</a></li>
		</ul>
		
		<!-- 无数据是显示开始 -->
		<div class="sysmsg" style="display:none">
			<div class="ad"></div>
			<p>暂无点评</p>
		</div>
		<!-- 无数据是显示结束 -->
		
		<ul class="group clearfix" id="dataUL">
		
		</ul>
		<!-- 分页开始 -->
		<div class="green-black" id="artPagging"></div>
		<!-- 分页结束 -->
	</div>
</div>

<!--底部开始-->
<%@include file="/pages/comm/agdfooter.jsp"%>
<!--底部结束-->
<script src="${basePath }/static/assets/js/public/rong-dialog.js"></script>
<script src="${basePath }/static/assets/js/plugins/tipso/tipso.js"></script> 
<script src="${basePath }/static/assets/js/userCenter/public.js"></script> 
</body>
</html>
