<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath(); request.setAttribute("basePath", basePath); 
request.setAttribute("now", new Date());
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
<script src="${basePath }/static/assets/js/plugins/tipso/tipso.js"></script>
<script src="${basePath }/static/assets/js/public/rong-dialog.js"></script>
<script>
    var basePath = "${basePath}";
    var temp;
    var tempContent;
    var targetUL;
    /** 页面加载完成事件 */
    $(function(){
        //默认加载报名中的数据
		temp = $(".item");
		tempContent = temp.prop("outerHTML");
        targetUL = temp.parents("ul");
        _loadData('1',null,1,10);
    });
	/**
	 * @param type 0-报名中;1-审核中;2-已报名
	 */
	function _loadData(type, input, page, rows){
        $(input).addClass("active").siblings().removeClass('active');
		 var page = page || 1;//分页加载第一页
		 var rows = rows || 10;//每页10条记录
		 var _url = basePath+"/center/myenroll/srchmyenroll?type="+type;
		 //请求服务器数据
		 $.ajax({
			type: "POST",
			url: _url+'&page='+page+'&rows='+rows,
			success: function(data){
				var dataRows = data.rows;
				var total = data.total || 0;
				//加载页面数据
				if(total > 0){
					showList(dataRows, type);
				}else{
					showList([], type);
				}
				//加载分页工具栏
				genPagging('artPagging', page, rows, total, function(page, rows){
                    _loadData(type, input, page, rows);
				});
			}
		 });
	}

	/**
	 * 生成列表数据
	 * @param dataList
	 * @returns
	 */
	function showList(dataRows, type){
        //移除原有数据
        targetUL.children("li").remove();
        $('div.sysmsg').hide();
		if(dataRows.length > 0){
			for(var i=0; i<dataRows.length; i++){
                var row = dataRows[i];
                var item = $(tempContent);
                item.find(".showtime").text(new Date(row.crttime).Format("yyyy-MM-dd hh:mm:ss"));
                item.find(".orderType").text(getStateDesc(row.state));
                item.find(".title").text(row.title);
				item.find(".title").attr("href",basePath+'/agdpxyz/traininfo?traid='+row.trainid);
				/*item.find(".title").off("click").one('click',function () {
					window.location.href=basePath+"/agdpxyz/traininfo?traid="+row.trainid;
				})*/
                item.find(".address").text(row.address);
                item.find(".crttime").text(new Date(row.crttime).Format("yyyy-MM-dd hh:mm:ss"));
                item.find(".starttime").text(new Date(row.starttime).Format("yyyy-MM-dd hh:mm:ss"));
                item.find(".orderid").text(row.orderid);
                if(row.state == 1 && type == 1){
                    item.find(".docancel").css("display","");
                    item.find(".docancel").attr("enrolId",row.id);
				}
				if(row.state == 2 ){
					item.find(".docancel").css("display","");
					item.find(".docancel").text("删除报名")
					item.find(".docancel").attr("enrolId",row.id);
					item.find(".docancel").attr("state",row.state);
				}
                item.removeClass("js-item-dome-li").show();
                targetUL.append(item);
			}
		}else{
            $('div.sysmsg').show();
		}
		//数据加载完成后添加取事件
        $('.docancel').on('click', function(){
            var id = $(this).attr("enrolId");
			var state = $(this).attr("state");
			if(state == 2){
				rongAlertDialog({ title: "提示信息", desc : "您确定删除该培训报名吗？", closeBtn : false, icoType : 1 }, function(){
					var _url = basePath+"/center/myenroll/delmyenroll?";
					var params = new Object();
					params.id = id;
					params.state = state;
					$.post(_url, params, function(result){
						if (result.success == "0"){
							_loadData(type,null,1,10);
						}else{
							rongDialog({ type : true, title : "操作失败,请联系管理员", time : 3*1000 });
						}
					}, "json");
				});
			}else{
				rongAlertDialog({ title: "提示信息", desc : "您确定取消该培训报名吗？", closeBtn : false, icoType : 1 }, function(){
					var _url = basePath+"/center/myenroll/delmyenroll?";
					var params = new Object();
					params.id = id;
					$.post(_url, params, function(result){
						if (result.success == "0"){
							_loadData(type,null,1,10);
						}else{
							rongDialog({ type : true, title : "操作失败,请联系管理员", time : 3*1000 });
						}
					}, "json");
				});
			}
        });


        //设置导航高度
        $('.leftPanel').css('minHeight', $('.rightPanel').outerHeight());
	}
	function getStateDesc(state){
	    if(state == 1){
	        return "报名申请"
		}else if(state == 2){
            return "取消报名";
		}else if(state == 3){
            return "审核失败";
        }else if(state == 4){
			return "等待面试";
        }else if(state == 5){
            return "面试不通过";
        }else{
            return "报名成功";
		}
	}
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
			<li aaa="111"class="active" onclick="_loadData('1',this)"><a href="javascript:void(0)" >待参加</a></li>
			<li  aaa="222" onclick="_loadData('0',this)"><a href="javascript:void(0)">已结束</a></li>
		</ul>
	    <div class="sysmsg" style="display:none;">
	       <div class="ad"></div>
	       <p>暂无报名中的课程</p>
	    </div>
		<ul class="group clearfix">
			<li class="item" style="display: none">
				<div class="orderCont clearfix">
					<div class="orderTime float-left">预定时间：<span class="showtime"></span></div>
					<div class="orderType"></div>
				</div>
				<div class="msgInfoCont">
					<p>培训内容 :<a class="title"></a></p>
					<p>培训地址 :<span class="address"></span></p>
					<%--<p>报名日期 :<span class="crttime"></span></p>--%>
					<p>开课日期 :<span class="starttime"></span></p>
					<p>订单号:<span class="orderid"></span></p>
					<a class="docancel orderKick js__p_orderKick_start" href="javascript:void(0)" style="display: none">取消报名</a>
				</div>
			</li>
		</ul>
    	<!-- 分页 -->
    	<div class="green-black" id="artPagging">
		</div>
	</div>
</div>

<!--底部开始-->
<%@include file="/pages/comm/agdfooter.jsp"%>
<!--底部结束-->
</body>
</html>
