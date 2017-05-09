<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <%@include file="/pages/comm/agdhead.jsp"%>
    <title>广东省文化馆-场馆</title>
    <link href="${basePath }/static/assets/css/field/fieldList.css" rel="stylesheet">
    <script src="${basePath }/static/assets/js/field/fieldList.js"></script>

    <script src="${basePath }/static/common/js/whg.maps.js"></script>

    <script type="text/javascript">

        var tools = (function(){
            //查询条件层选择器
            var queryDiv = "div.categoryChange";
            //列表项模板HTML
            var jsItemDomeLi = '';
            //列表项父UL
            var targetUL = '';

            function init(){
                //数据项模板初始处理
                var jsDome = $(".js-item-dome-li");
                jsItemDomeLi = jsDome.prop("outerHTML");
                targetUL = jsDome.parents("ul");

                //处理点击类型的查询条件
                $(queryDiv).find("a[pname]").off("click.pname").on("click.pname", onPnameClick);

                //查询点击
                $("#btn_sub").on('click', function(e){
                    e.preventDefault();
                    loadData();
                });

                //回车事件
                $("body").keydown(function() {
                    var isFocus = $("#title").is(":focus");
                    if (event.keyCode == "13" && isFocus) {
                        loadData();
                    }
                });
            }
            
            function onPnameClick(){
                var _li = $(this).parents("li");
                if (_li.length){
                    //显隐动画之后再查数据
                    setTimeout(function(){loadData()}, 500);
                }else{
                    $(this).parents("span.item").addClass("active").siblings().removeClass("active");
                    loadData();
                }
            }

            function getQueryParams(){
                var params = {};
                //选中项
                $(queryDiv).find(".active:visible").each(function(){
                    var paramA = $(this).find('a');
                    var name = paramA.attr('pname');
                    var value = paramA.attr('pvalue');
                    if (name && value){
                        params[name] = value;
                    }
                });
                //输入项
                var title = $(queryDiv).find("#title").val();
                if (title){
                    params.title = title;
                }

                return params;
            }

            function loadData(page, rows){
                var params = getQueryParams();
                params.page = page||1;
                params.rows = rows||10;
                $.ajax({
                    url: '${basePath}/agdcgfw/venlist',
                    type: "POST",
                    data: params,
                    success : function(data){
                        showData(data);
                        genPagging('paging', params.page, params.rows, data.total||0, loadData);
                        if(data.total == 0){
                            $(targetUL).find(".public-no-message").show();
                        }else{
                            $(targetUL).find(".public-no-message").hide();
                        }
                    }
                });

            }

            function showData(data){
                //显示数据项
                targetUL.children("li").remove();
                for (var i in data.rows){
                    var row = data.rows[i];
                    var item = $(jsItemDomeLi);
                    targetUL.append(item);
                    item.removeClass("js-item-dome-li").show();

                    item.find(".img a, .info h2 a").attr("href", "${basePath}/agdcgfw/venueinfo?venid="+row.id);
                    item.find(".img img").attr("src", WhgComm.getImg750_500('${imgServerAddr}'+row.imgurl) );
                    item.find(".info h2 a").text(row.title);
                    item.find(".info p.adr span.desc-text").text(row.address);
                    item.find(".info p.cate span.desc-text").text( WhgComm.FMTVenueType(row.etype) );
                    item.find(".info p.desc span.desc-text").text(row.summary);

                    //item.find(".info p.type").html('<span class="d">比赛</span>');
                    var etagP = item.find(".info p.type");
                    var etags = row.etag || '';
                    etags = etags.split(/\s*,\s*/);
                    for(var i in etags){
                        if (!etags[i]) continue;
                        etagP.append('<span class="d">'+WhgComm.FMTVenueTag(etags[i])+'</span>')
                    }
                    var mapParams = {address:row.address,longitude:row.longitude, latitude:row.latitude };
                    item.find(".info p.adr a").off("click.maps").on("click.maps",mapParams,function(evt){
                        WhgMap.openMap(evt.data.address, evt.data.longitude, evt.data.latitude);
                    })
                }
                //处理分页

            }

            return {
                init : init,
                loadData: loadData
            }
        })();

        $(function () {
            tools.init();
            tools.loadData();
        })
    </script>
</head>
<body>

<!-- 公共头部开始 -->
<%@include file="/pages/comm/agdtopsmall.jsp"%>
<!-- 公共头部结束-END -->

<!--公共主头部开始-->
<div id="header-fix">
    <div class="header-nav-bg">
        <div class="header-nav">
            <div class="logo-small">
                <a href="${basePath }/home"><img src="${basePath }/static/assets/img/public/logoSmall.png"></a>
            </div>
            <ul>
                <li class="active last"><a href="${basePath }/agdcgfw/index">场馆服务</a></li>
            </ul>
        </div>
    </div>
</div>
<!--公共主头部结束-END-->

<!--主体开始-->
<div class="venue-main">
    <div class="categoryChange">
        <div class="masterRow">
            <ul class="clearfix">
                <li class="active closeAdr"><a href="javascript:void(0)" pname="cultid" pvalue="TOP">省馆</a></li>
                <li class="showAdr"><a href="javascript:void(0)" pname="cultid" pvalue="">全省</a></li>
            </ul>
        </div>

        <div class="row clearfix adrCont none">
            <div class="title">区域</div>
            <div class="adrList">
                <span class="item active"><a href="javascript:void(0)" pname="area" pvalue="">全部</a></span>
            	<c:forEach items="${areas }" var="item">
                	<span class="item"><a href="javascript:void(0)" pname="area" pvalue="${item.id}">${item.name}</a></span>
                </c:forEach>
            </div>
        </div>
        
        <div class="row clearfix">
            <div class="title">类型</div>
            <div class="adrList">
                <span class="item active"><a href="javascript:void(0)" pname="etype" pvalue="">全部</a></span>
                <c:forEach items="${types }" var="item">
                    <span class="item"><a href="javascript:void(0)" pname="etype" pvalue="${item.id}">${item.name}</a></span>
                </c:forEach>
            </div>
        </div>
        
        <div class="row clearfix">
            <div class="title">标签</div>
            <div class="adrList">
                <span class="item active"><a href="javascript:void(0)" pname="etag" pvalue="">全部</a></span>
                <c:forEach items="${tags }" var="item">
                    <span class="item"><a href="javascript:void(0)" pname="etag" pvalue="${item.id}">${item.name}</a></span>
                </c:forEach>
            </div>
        </div>
        <div class="row clearfix">
           	<div class="title">状态</div>
            <div class="adrList adrList1">
                <span class="item active"><a href="javascript:void(0)" pname="destine" pvalue="">全部</a></span>
                <span class="item"><a href="javascript:void(0)" pname="destine" pvalue="1">可预定</a></span>
            </div> 
            
            <div class="searchCont">
                <input placeholder="搜点什么..." id="title">
                <button type="submit" id="btn_sub"></button>
            </div>
        </div>
        
    </div>
</div>
<div class="main clearfix">
    <div class="venue-main-left">
    	<ul class="group" id="connet">
            <div class="public-no-message none"></div>
            <li class="item clearfix js-item-dome-li" style="display: none;">
                <div class="img">
                    <a><img style="width: 280px; height: 190px;"></a>
                </div>
                <div class="info">
                    <h2><a>罗店镇社区文化活动中心</a></h2>
                    <div class="info-main">
                        <div class="row-1">
                            <p class="type clearfix"></p>
                            <p class="adr clearfix"><span class="tt">地址 :</span><span class="desc-text">广东市南城区宏北路南城社保分局、交通分局旁</span><a href="#">[查看地图]</a></p>
                            <p class="cate clearfix"><span class="tt">类型 :</span><span class="desc-text">其它</span></p>
                            <p class="desc clearfix"><span class="tt">描述 :</span><span class="desc-text">由藏书区、借阅区、咨询服务区、公共活动与辅助服务区、业务区、行政办公区、技术设备区和后勤保障区等几个功能部分组成</span></p>
                        </div>
                    </div>
                </div>
            </li>
        </ul>
        
        <div class="green-black" id="paging"> 
        </div>
        
    </div>
</div>
<!--主体结束-->

<!--公共主底部开始-->
<%@include file="/pages/comm/agdfooter.jsp"%>
<!--公共主底部结束-END-->
</body>
</html>