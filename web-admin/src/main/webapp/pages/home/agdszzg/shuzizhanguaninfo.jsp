<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <%@include file="/pages/comm/agdhead.jsp"%>
    <title>广东省数字文化馆-数字展馆</title>
    <link href="${basePath}/static/assets/css/resource/shuzizhanguan.css" rel="stylesheet">
    <script src="${basePath}/static/assets/js/plugins/roll/jquery.sly.min.js" type="text/javascript"></script>
    <script src="${basePath}/static/assets/js/science/science.js"></script>
    <script src="${basePath}/static/assets/js/resource/shuzizhanguan.js"></script>
    <script src="${basePath}/static/assets/js/plugins/zoomin/zoomin.js"></script>
    <script src="${basePath}/static/assets/js/public/rong-dialog.js"></script>

</head>
<body style="background-color:#f5f5f5">
<!--公共主头部开始-->
<%@include file="/pages/comm/agdtopsmall.jsp"%>

<div id="header-fix">
    <div class="header-nav-bg">
        <div class="header-nav">
            <div class="logo-small"> <a href="${basePath}/home"><img src="${basePath}/static/assets/img/public/logoSmall.png"></a> </div>
            <ul>
                <li class="last"><a href="${basePath }/agdszzg/index">数字展馆</a></li>
                <%--<li class="active"><a href="${basePath }/agdszzg/list">赛事活动</a></li>--%>
            </ul>
        </div>
    </div>
</div>
<!--公共主头部结束-->

<!--主体开始-->
<div class="sshd-main" style="margin-top:140px;margin-bottom:60px;">
    <h1>${whgExh.exhtitle}</h1>
    <p class="other-info">
        <span>时间：<fmt:formatDate value="${whgExh.exhstime}" pattern="yyyy-MM-dd"/> </span>
        <%--<span>来源：广东省文化馆</span>--%>
        <span>主办：${whgExh.organizer}</span>
    </p>
    <div class="ss-img">
        <img src="${imgServerAddr}/${whg:getImg750_500(whgExh.exhpic)}">
    </div>
    <div class="nav-ul">
        <span class="active">活动介绍</span>
        <span>展览展示</span>
    </div>
    <div>
        <div class="detail">
            ${whgExh.exhdesc}
        </div>
        <div class="detail none">
            <ul class="clearfix" id="imgContent">
                <c:if test="${not empty whgExhArt}">
                    <c:forEach items="${whgExhArt}" var="row" varStatus="s" begin="0" end="11">
                        <li ${s.count%4==0?"class='last'":''}>
                            <div class="img">
                                <a href="javascript:void(0)" onclick="show_img(this,{url:'${imgServerAddr}/${row.artpic}',zoomin:'ture',title:'我是标题'})">
                                    <img src="${imgServerAddr}/${row.artpic}" class="image">
                                </a>
                            </div>
                            <h2>${row.arttitle}</h2>
                            <p>责任者：${row.artauthor}</p>
                            <p>时间：<fmt:formatDate value="${row.artcrttime}" pattern="yyyy-MM-dd"/></p>
                            <div class="info1">描述：${row.artcontent}</div>
                        </li>
                    </c:forEach>
                </c:if>

            </ul>
        </div>
    </div>
    <script type="text/javascript">
//        var imgContent = document.getElementById("imgContent");
//        var aImgs = imgContent.getElementsByTagName("img");
//        var wrapRate = $("#imgContent .img").eq(0).outerWidth()/$("#imgContent .img").eq(0).outerHeight();
//        console.log(wrapRate);
//        for (var i=0;i<aImgs.length;i++) {
//            var fn = function (i) {
//                var naturalWidth = aImgs[i].naturalWidth;
//                var naturalHeight = aImgs[i].naturalHeight;
//                var naturalRate = naturalWidth/naturalHeight;
//                console.log(naturalWidth,naturalHeight,naturalRate);
//                if (naturalRate - wrapRate > 0) { //图片宽大些
//                    $(aImgs[i]).css("width","276px")
//                } else {
//                    $(aImgs[i]).css("height","207px")
//                }
//            }(i);
//        }

        $(".nav-ul span").on("click", function () {
            $(this).addClass("active").siblings().removeClass("active");
            $(".detail").eq($(".nav-ul span").index(this)).removeClass("none").siblings().addClass('none');
        })
    </script>
</div>

<!--主体结束-->

<!--底部开始-->
<%@include file="/pages/comm/agdfooter.jsp"%>
<!--底部结束-->
<a class="to-top"></a>
</body>
</html>