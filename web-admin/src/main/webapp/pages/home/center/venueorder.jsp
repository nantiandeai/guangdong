<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>广东省文化馆-用户中心</title>
    <%@include file="/pages/comm/agdhead.jsp"%>
    <link href="${basePath }/static/assets/css/userCenter/userCenter.css" rel="stylesheet">
    <script src="${basePath }/static/assets/js/plugins/tipso/tipso.js"></script>
    <script src="${basePath }/static/assets/js/userCenter/public.js"></script>
    <script src="${basePath }/static/assets/js/userCenter/activity.js"></script>

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
      <li class="active"><a href="javascript:void(0)" nowtype="now">进行中</a></li>
      <li><a href="javascript:void(0)" nowtype="old">已结束</a></li>
      <%--<li><a href="javascript:void(0)" vebstate="2">未通过</a></li>--%>
    </ul>
    
    <div class="sysmsg" style="display: none;">
      <div class="ad"></div>
      <p>暂无您正在参与的预订信息</p>
    </div> 
    
    <ul class="group clearfix" id="connet">
        <li class="item js-item-dome-li" style="display: none;">
            <div class="orderCont clearfix">
                <div class="orderType">state</div>
                <div class="orderTime float-left">预订时间：<span>2016-10-08 14:00</span></div>
            </div>
            <div class="infoCont">
                <h2><a target="_blank">{{title}}</a></h2>
                <p >场馆地址 :<span>{{address}}</span></p>
                <p >场馆电话 :<span>{{phone}}</span></p>
                <p class="tickets">订单号/取票码 :<span>{{orderid}}</span></p>
                <p class="timeday">预订场次 :<span>2016-10-08 14:00-15:30</span></p>
                <p >预定人 :<span>{{ordercontact}}</span></p>
                <p class="ordercontactphone">预定人电话 :<span></span></p>
                <p class="ticketstatus">票务状态 :<span></span></p>
                <p class="ordersummary">拒绝理由 :<span>{{ordersummary}}</span></p>

                <p class="time"></p>
                <a class="orderKick" href="javascript:void(0)">取消预订</a>
            </div>
        </li>
    </ul>

    <div class="green-black" id="paging"> 
    </div>
    
  </div>
</div>

<!--底部开始-->
<%@include file="/pages/comm/agdfooter.jsp"%>
<!--底部结束-->

<script>
    $(function () {
        venOrder.init();

        venOrder.loadData();
    });
    
    var venOrder = (function () {
        var panel = '.rightPanel';
        var dataUL, dataLI, queryParams={page:1, rows:5};

        var state = {
            "0" : "申请中",
            "1" : "已取消",
            "2" : "审核拒绝",
            "3" : "审核通过"
        };

        function init() {
            var temp = $('li.js-item-dome-li');
            dataUL = temp.parents("ul");
            dataLI = temp.removeClass('js-item-dome-li').prop("outerHTML");

            $(panel).children('ul.commBtn').on('click', 'a[nowtype]', function () {
                $(this).parents('li').addClass('active').siblings().removeClass();
                loadData();
            })
        }

        function showData(data){
            //显示数据项
            dataUL.children("li").remove();
            for (var i in data.rows){
                var row = data.rows[i];

                var _li = dataLI;
                var pas = _li.match(/\{\{.+\}\}/gm);
                if (pas){
                    for(var j=0; j<pas.length; j++){
                        var pa = pas[j];
                        var k = String(pa).replace(/[\{\}]/g, '');
                        _li = _li.replace(pa, row[k]||'');
                    }
                }

                var item = $(_li);
                dataUL.append(item);

                var orderCont = item.find(".orderCont");
                orderCont.find(".orderTime span").text(new Date(row.crtdate).Format("yyyy-MM-dd hh:mm"));
                orderCont.find(".orderType").text(state[row.state]);

                var infoCont = item.find(".infoCont");
                infoCont.find("h2 a").attr("href","${basePath}/agdcgfw/venroominfo?roomid="+row.roomid);
                var timeStr = new Date(row.timeday).Format("yyyy-MM-dd")+' '+new Date(row.timestart).Format("hh:mm")+'-'+new Date(row.timeend).Format("hh:mm")
                infoCont.find(".timeday span").text(timeStr);
                infoCont.find(".ticketstatus span").text( (row.ticketstatus==2?"已取票":"未取票") +' '+(row.ticketcheckstate==2?"已验票":"未验票") );

                var ordercontactphone = row.ordercontactphone;
                var phonearr =  /^(\d{3})(\d*)(\d{4})$/.exec(ordercontactphone);
                if (phonearr){
                    ordercontactphone = phonearr[1]+"****"+phonearr[3];
                }

                infoCont.find(".ordercontactphone span").text(ordercontactphone);

                if (row.ordersummary && row.state==2){
                    infoCont.find(".ordersummary").show();
                }else{
                    infoCont.find(".ordersummary").hide();
                }

                infoCont.find("a.orderKick").hide().off("click");
                if (row.state==1){
                    infoCont.find("a.orderKick").text("删除").show().on("click", {orderid: row.id}, delOrder)
                }
                if (row.state==0 && row.hasfees != 1){
                    infoCont.find("a.orderKick").text("取消预订").show().on("click", {orderid: row.id}, unOrder)
                }

                item.show();
            }
        }

        function getQueryParams(){
            var nowtype = $(panel).find('ul.commBtn li.active:eq(0) a[nowtype]').attr("nowtype");
            queryParams.nowtype = nowtype||'now';
            return queryParams;
        }

        function loadData(page, rows){
            var params = getQueryParams();
            params.page = page|| params.page;
            params.rows = rows|| params.rows;

            $.ajax({
                url: '${basePath}/center/findVenueOrder',
                type: "POST",
                data: params,
                success : function(data){
                    showData(data);
                    genPagging('paging', params.page, params.rows, data.total||0, loadData);
                    if(data.total == 0){
                        $(panel).find(".sysmsg").show();
                    }else{
                        $(panel).find(".sysmsg").hide();
                    }
                    //设置导航高度
                    $('.leftPanel').css('minHeight', $('.rightPanel').outerHeight());
                }
            });

        }

        function unOrder(event){
            var id = event.data.orderid;
            rongAlertDialog({
                'title'        :  "温馨提示",
                'desc'         :  "您确定取消预定吗？",
                'closeBtn'     :  false,
                'icoType'      :  2
            },function(e){
                $.ajax({
                    type: "POST",
                    url: '${basePath}/center/unOrder',
                    data: {orderid : id},
                    success: function(data){
                        if (data.success) {
                            closeDialog();
                            rongDialog({ title: '取消成功', type: true })
                        }else {
                            rongDialog({ title: '操作失败', type: false })
                        }
                        loadData();
                    }
                });
            })
        }

        function delOrder(event){
            var id = event.data.orderid;
            rongAlertDialog({
                'title'        :  "温馨提示",
                'desc'         :  "您确定删除预定吗？",
                'closeBtn'     :  false,
                'icoType'      :  2
            },function(e){
                $.ajax({
                    type: "POST",
                    url: '${basePath}/center/delOrder',
                    data: {orderid : id},
                    success: function(data){
                        if (data.success) {
                            closeDialog();
                            rongDialog({ title: '删除成功', type: true })
                        }else {
                            rongDialog({ title: '操作失败', type: false })
                        }
                        loadData();
                    }
                });
            })
        }
        
        return {
            init: init,
            loadData: loadData
        }
    })();
</script>

</body>
</html>
