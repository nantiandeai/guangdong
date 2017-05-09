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
<link rel="shortcut icon" href="${basePath }/favicon.ico" />
<title>广东省文化馆-用户中心</title>
<link href="${basePath }/static/assets/css/public/reset.css" rel="stylesheet">
<link href="${basePath }/static/assets/css/userCenter/userCenter.css" rel="stylesheet">
<!--[if lt IE 9] >
<script src="${basePath }/static/assets/js/plugins/ie/IE9.js"></script> 
<! [endif]]-->
<script src="${basePath }/static/assets/js/public/jquery-1.11.0.min.js"></script> 
<script src="${basePath }/static/assets/js/public/jquery-migrate-1.0.0.js"></script>
<script src="${basePath }/pages/comm/agdcomm.js"></script>
<%--<script src="${basePath }/static/assets/js/public/comm.js"></script>--%>

<script type="text/javascript">
/** 数据加载 */
function loadData(page, rows){
	page = page || 1;
	rows = rows || 5;

    var cmreftyp = $(".rightPanel ul.commBtn li.active").attr("cmreftyp");

	$.ajax({
		type: 'post',
		url: '${basePath }/center/col/loadcoll',
		data:{page:page,rows:rows, cmreftyp: cmreftyp},
		success: function(data){
			var total=data.total;
			var cmdata=data.rows;
			showData(cmdata);
			//加载分页工具栏
			genPagging('artPagging', page, rows, total, loadData);
		}
	});
}


/** 展示数据 */
function showData(data){
	var _html = '';
	if(data.length > 0){
		for(var i=0 ;i<data.length; i++){
			var row = data[i];                                                            
	_html+='<li class="item">                                                             '
	_html+='	<a class="trash js__p_orderKick_start" cmid='+row.cmid+' href="javascript:void(0)"></a>     '
	_html+=' 	<div class="orderCont clearfix">                                          '
	_html+='  	    <div class="orderTime float-left">'+datetimeFMT(row.cmdate)+'                    '
	_html+='  	    </div>                                                                '
	_html+='     </div>                                                                   '
	_html+='	    <div class="msgInfoCont">                                             '
	_html+='   	    <h2>                                                                  '
	_html+='     		 <a href="'+row.cmurl+'" target="_blank">'+row.cmtitle+'          '
	_html+='    		  </a>                                                            '
	_html+='     	</h2>                                                                 '
	_html+='     </div>                                                                   '
	_html+='</li>                                                                         '
		}
		$('.sysmsg').hide();
		$('#dataUL').html(_html);
	}else{
		$('.sysmsg').show();
		$('#dataUL').html('');
	}
	//弹出 删除框
	$('.js__p_orderKick_start').on('click',function(){
		var cmid=$(this).attr("cmid");
		rongAlertDialog({
			'title'        :  "温馨提示",
			'desc'         :  "您确定删除此收藏吗？",
			'closeBtn'     :  false,
			'icoType'      :  2
		},function(){
			$.ajax({
				type:'post',
				url : '${basePath }/center/removeColle',
				data : {
					cmid : cmid
				},
				success : function(data) {
					if (data.success) {
						loadData();
						
						closeDialog();
						rongDialog({
							title: '删除成功',
							type: true
						})
					} else {
						closeDialog();
						rongDialog({
							title: '删除失败',
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

/** 頁面加載*/
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
    		   <li class="active" cmreftyp="2"><a href="javascript:void(0)">场馆收藏</a></li>
    		   <li cmreftyp="3"><a href="javascript:void(0)">场地收藏</a></li>
    		   <li cmreftyp="4"><a href="javascript:void(0)">活动收藏</a></li>
    		   <li cmreftyp="5"><a href="javascript:void(0)">培训收藏</a></li>
   	    </ul>
    	    
    	 <!-- 无数据是显示开始 -->
   		<div class="sysmsg">
    		  <div class="ad">
        	  </div>
      		  <p>暂无收藏
      		  </p>
  	    </div>
  	    <!-- 无数据是显示结束 -->
  	  
  	   	 <!-- 数据显示开始 -->  	
  		 <ul class="group clearfix" id="dataUL">
   		 </ul>
     	 <!-- 数据显示结束 -->
    
	    <!-- 分页开始 -->
	    <div class="green-black" id="artPagging">
	    <!-- 分页結束-->
	    </div>
    
	</div>
</div>

<!--底部开始-->
<%@include file="/pages/comm/agdfooter.jsp"%>
<!--底部结束-->


<!-- core public JavaScript --> 
<script src="${basePath }/static/assets/js/public/rong-dialog.js"></script>
<script src="${basePath }/static/assets/js/plugins/tipso/tipso.js"></script> 
<script src="${basePath }/static/assets/js/userCenter/public.js"></script> 
</body>
</html>
