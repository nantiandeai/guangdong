<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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

<script type="text/javascript">

/**
 * 日期格式化
 * format: yyyy-MM-dd hh:mm:ss.S / yyyy-MM-dd hh:mm:ss / yyyy-MM-dd hh:mm / yyyy-MM-dd
 */
function _dateFormat(val, format){
	 return (new Date(val)).Format(format)
}

/**
 * 上传附件
 */
/*function uploadFiles(actbmid){
	if (actbmid){
		var _href = getFullUrl("sign/evt/step2/"+actbmid);
		window.location = _href;
	}
}*/
 
/**
 * 取消报名
 */
/*function cancelEnroll(actbmid,type){
	rongAlertDialog({
		'title'        :  "温馨提示",
		'desc'         :  "您确定取消报名吗？",
		'closeBtn'     :  false,
		'icoType'      :  2
	}, function(){
		var _url = getFullUrl('/center/myevent/removeMyenroll');
		$.ajax({
			type:'post',
			url : _url,
			data : {id : actbmid },
			success : function(data) {
				if (data.success == "0") {
					closeDialog();
					rongDialog({
						title: '取消成功',
						type: true
					})
				} else {
					rongDialog({
						title: '操作失败',
						type: false
					})
				}
				_loadData(type);
			}
		})
		
	});
} */
 
/**数据加载 
 * @param type 0-报名中;1-审核中;2-已报名
 */
function _loadData(type, page, rows) {
	 var page = page || 1;//分页加载第一页 
	 var rows = rows || 3;//每页5条记录
	 //导航条件
	var  myType = $("li[switchTabLi='ok'][class='active']").attr("tab-type");
	var _url = getFullUrl('/center/getMyActList?type='+myType);
	 //请求服务器数据
	 $.ajax({
	 	type: "POST",
	 	url: _url+'&page='+page+'&rows='+rows,
	 	success: function(data){
	 		var dataRows = data.rows;
	 		var total = data.total || 0;
	 		//加载页面数据
	 		if(total > 0){
                showMyActOrderList(dataRows,myType);
	 		}else{
                showMyActOrderList([],myType);
	 		}
	 		
	 		//加载分页工具栏
	 		genPagging("artPagging", page, rows, total, function(page, rows){_loadData(myType, page, rows);});
	 	}
	 });
}

function showMyActOrderList(dataRows,type) {
    $("ul[id='myDataArea']").children("li").remove();
    if(0 == dataRows.length){
        $('.sysmsg').show();
	    return;
	}
    $('.sysmsg').hide();
	$.each(dataRows,function (index,value) {
		var myCardLi = $("<li class='item'>");
		var orderCont = $("<div class='orderCont clearfix'>");
        orderCont.append("<div class='orderTime float-left'>预定时间：<span>"+_dateFormat(value.ordercreatetime,"yyyy-MM-dd hh:mm:ss") +"</span></div>");
        orderCont.appendTo(myCardLi);
        var infoCont = $("<div class='infoCont'>");
        if(1 == value.ticketstatus){
        	orderCont.append('<div class="orderType">未出票</div>');
		}else if(2 == value.ticketstatus){
			orderCont.append('<div class="orderType">已取票</div>');
		}else if(3 == value.ticketstatus){
			orderCont.append('<div class="orderType">已取消</div>');
		}
        infoCont.append("<h2><a href='${basePath}/agdwhhd/activityinfo?actvid="+value.activityid+"' target='_blank'>" + value.name + "</a></h2>");
        infoCont.append("<p >地址 :<span>" + value.address +"</span></p>");
        infoCont.append("<p >手机 :<span>" + value.orderphoneno + "</span></p>");
        infoCont.append("<p class='tickets'>取票码 :<span>" + value.ordernumber + "</span></p>");
        infoCont.append("<p>座位号 :<span>" + value.seatCode + "</span></p>");
        infoCont.append("<p class='time'>活动时间 :<span>" +_dateFormat(value.playdate,"yyyy-MM-dd")  + " "+value.playstime+"-"+value.playetime+"</span></p>");
        if (value.ticketstatus != 3 && value.state != 1 && value.dAffore < value.strTime){
        	infoCont.append('<div><a class="orderKick" href="javascript:void(0)" onclick="cancelOrder('+value.id+')">取消预订</a></div>');
        }
        infoCont.appendTo(myCardLi);
        $("ul[id='myDataArea']").append(myCardLi);
    })
 	//设置导航高度
    $('.leftPanel').css('minHeight', $('.rightPanel').outerHeight());
}

/**
 * 取消订单
 */
function cancelOrder(orderId){
	 rongAlertDialog({
         'title'        :  "温馨提示",
         'desc'         :  "您确定取消预定吗？",
         'closeBtn'     :  false,
         'icoType'      :  2
     },function(e){
         $.ajax({
             type: "POST",
             url: getFullUrl('/center/cancelActOrder'),
             data: {orderId : orderId},
             success: function(data){
                 if (data.success) {
                     closeDialog();
                     rongDialog({ title: '取消成功', type: true })
                 }else {
                     rongDialog({ title: '操作失败', type: false })
                 }
				 var currentPage = $("#artPagging").find(".current").text();
                 _loadData(null, currentPage);
             }
         });
     })
}


 /**数据显示 */
 /*function showList(dataRows,type) {
 	var _html="";
 	if(dataRows.length>0){
 		for(var i=0;i<dataRows.length;i++){
 			var row = dataRows[i];
 			var now = new Date();
 			//var timeObj = _time(row.actvitmsdate,row.actvitmedate);

 			//审核失败
 			var checkerror =row.actshstate == '3' ? ' err-border' : '';
 			if(type == '2'){
 				checkerror = 'sus-border';
 			}

 			//审核的报名
 			var checkHtml = '';
 			if(type == '1' ){
 				if(row.actshstate == '0'){//审核中
 					if(row.actbmtype == '1'){
 		 				teamHtml ='<div class="orderType">团队报名</div>';
 		 				checkHtml = '<div class="orderType">审核中</div>'+teamHtml;
 		 			}else{
 		 				checkHtml = '<div class="orderType">审核中</div>';
 		 			}

 				}else if(row.actshstate == '3'){//审核失败
 					if(row.actbmtype == '1'){
 		 				teamHtml ='<div class="orderType">团队报名</div>';
 		 				checkHtml = '<div class="orderType error">审核失败<div class="err-msg none"><i></i>'+row.actbmstatemsg+'</div></div>'+teamHtml;
 		 			}else{
 		 				checkHtml = '<div class="orderType error">审核失败<div class="err-msg none"><i></i>'+row.actbmstatemsg+'</div></div>';
 		 			}

 				}
 			}
 			if(type == '2' && row.actvisenrol == '1'){
 				if(row.actbmtype == '1'){
 				checkHtml ='<div class="orderType">团队报名</div>';
 				}
 			}
 			_html+='<li class="item '+checkerror+'">                                                      '
 		    _html+='  <div class="orderCont clearfix">                                                   '
 		    _html+='    <div class="orderTime">'+_dateFormat(row.actbmtime,"yyyy-MM-dd hh:mm:ss")+'</div>'
 		    _html+='    <div class="orderNum">活动号 : <span>'+row.actid+'</span></div>            		 '+checkHtml;
 		    _html+='  </div>                                                                             '
 		    _html+='  <div class="msgInfoCont">                                                          '

 		    if(now > row.actvitmedate){
 		    _html += '		<div class="m-end"></div>';
 		    }

 		    _html+='    <h2><a href="${basePath}/agdwhhd/activityinfo?actvid='+row.actid+'" target="_blank">'+row.actvshorttitle+'</a></h2>'
 		    _html+='    <p>活动时间 :<span>'+_dateFormat(row.actvitmsdate,"yyyy/MM/dd hh:mm")+'</span></p>      '
 		    _html+='    <p>地址 :<span>'+row.actvaddress+'</span></p>                    				   '
 		   	_html+='	<p>数量 :<span>'+row.actbmcount+'</span>张</p>'

 		    if(type == 0 && row.actbmisb == 0 && row.actbmstate == 0){
 		    _html+='<a class="uploadFiles" href="javascript:void(0)" actbmid="'+row.actbmid+'">上传报名表</a>							'
 		    }

 		   	if(type == 0 || type == 1 || type ==2 && row.actshstate == 3){
 		   	 _html+='    <a class="orderKick js__p_orderKick_start" href="javascript:void(0)" actbmid="'+row.actbmid+'">取消报名</a> 	 '
 		   	}

 		    _html+='  </div>'
 		    _html+='</li>'
 			}
 			$('.bmshuju').html(_html).show();
 			$('.sysmsg').hide();
 			$('.bmshuju a.uploadFiles').unbind('click.wxl').bind('click.wxl', function(e){
 				e.preventDefault();
 				uploadFiles($(this).attr('actbmid'));
 			});

 			$('.bmshuju a.orderKick').unbind('click.wxl').bind('click.wxl', function(e){
 				e.preventDefault();
 				cancelEnroll($(this).attr('actbmid'),type);
 			});
 		}else{
 			$('.bmshuju').html('').hide();
 			$('.sysmsg').show();
 		}

 }*/

/**页面加载*/
$(function() {
	//报名中 审核中 已报名  点击事件-加载对应的报名数据
    /**
     $('ul.commBtn a').unbind('click.wxl').bind('click.wxl', function(e){
		e.preventDefault();

		$('ul.commBtn li').removeClass('active');
		$(this).parent('li').addClass('active');
		_loadData($(this).attr('whtype'));
	});

     _loadData("0");
     */


	$("a[switchTabA='ok']").on("click",function () {
	    $("li[switchTabLi='ok']").removeClass("active");
	    $(this).parent("li").addClass("active");
        _loadData(1);
    })
    _loadData(1);
})

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
	  	<!--选项卡列表 开始 -->
	  	<ul class="commBtn clearfix">
	      	<li class="active" switchTabLi="ok" tab-type="1"><a href="javascript:void(0);" whtype="0" switchTabA="ok">待参加</a></li>
	      	<li  switchTabLi="ok" tab-type="2"><a href="javascript:void(0);" whtype="2" switchTabA="ok">已结束</a></li>
	    </ul>
	    <!--选项卡列表 结束 -->
    
	    <!--无记录显示 开始 -->
	    <div class="sysmsg">
	        	<div class="ad"></div>
	            <p>暂无您正在参与的活动报名信息</p>
	    </div>
	    <!--无记录显示 结束 -->
    	<!--有数据 报名列表 starts -->
    	<ul class="group clearfix" id="myDataArea">

		</ul>
    	<!--有数据 报名列表 end -->

    	<!-- 分页  starts-->
    	<div class="green-black" id="artPagging"></div>
    	<!-- 分页 end -->	
	</div>
</div>

<!--底部开始-->
<%@include file="/pages/comm/agdfooter.jsp"%>
<!--底部结束-->

<!-- core public JavaScript --> 
<script src="${basePath }/static/assets/js/plugins/tipso/tipso.js"></script> 
<script src="${basePath }/static/assets/js/userCenter/public.js"></script> 
<script src="${basePath }/static/assets/js/public/rong-dialog.js"></script>
<script src="${basePath }/static/assets/js/userCenter/activity.js"></script>
</body>
</html>
