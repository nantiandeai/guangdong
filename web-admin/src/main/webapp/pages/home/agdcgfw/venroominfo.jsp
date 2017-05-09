<%@ page import="com.creatoo.hn.utils.WhConstance" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    Object user = request.getSession().getAttribute(WhConstance.SESS_USER_KEY);
    if (user!=null){
        request.setAttribute("isSessUser", true);
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <%@include file="/pages/comm/agdhead.jsp"%>
    <title>${room.title }</title>
    <link href="${basePath }/static/assets/css/field/fieldOrder.css" rel="stylesheet">
    <link href="${basePath }/static/assets/js/plugins/oiplayer-master/css/oiplayer.css" rel="stylesheet">

    <script src="${basePath }/static/assets/js/plugins/oiplayer-master/plugins/flowplayer-3.2.6.min.js"></script>
    <script src="${basePath }/static/assets/js/plugins/oiplayer-master/js/jquery.oiplayer.js"></script>

    <script src="${basePath }/static/assets/js/field/fieldOrder.js"></script>

    <script src="${basePath }/static/common/js/whg.maps.js"></script>

    <script type="text/javascript" src="${basePath }/pages/home/agdcgfw/roomOrderTool.js"></script>

    <script>
        var orderTool = new RoomOrderTool({
            basePath: '${basePath}',
            roomId: '${room.id}',
            isSessUser: '${isSessUser}'
        });
        //扩展initWeekList,接管后续处理
        var tempInitWeekList = initWeekList;
        initWeekList = function(day){
            var roomtimeCount = '${roomtimeCount}';
            if (roomtimeCount != '0'){
                var beginDay = new Date('${beginDay}');
                var endDay = new Date('${endDay}');
                if (endDay < day) {
                    day.setDate(day.getDate()-7);
                }
                if (day <= beginDay){
                    day = beginDay;
                    $(".week-prev").on("click.prev", function(){
                        rongDialog({ type : false, title : "没有更多了", time : 1*1000 })
                    })
                }else{
                    $(".week-prev").off("click.prev");
                }
            }

            tempInitWeekList(day);
            if (endDay <= day) {
                $(".week-next").on("click.next", function(){
                    rongDialog({ type : false, title : "没有更多了", time : 1*1000 })
                })
            }else{
                $(".week-next").off("click.next");
            }
            orderTool.initWeekDay(day);
        };

        $(function () {
            $(".js-fmt-text").each(function () {
                var v = $(this).attr("js-val");
                var fn = $(this).attr('js-fn');
                var text = WhgComm[fn].call(WhgComm, v);
                $(this).text(text);
            });

            var roomtimeCount = '${roomtimeCount}';
            if (roomtimeCount == '0'){
                $(".orderPayCont").hide();
            }else{
                $(".orderPayCont").show();
            }
        })
    </script>
</head>
<body class="oiplayer-example">

<!-- 公共头部开始 -->
<%@include file="/pages/comm/agdtopsmall.jsp"%>
<!-- 公共头部结束-END -->

<div id="header-fix">
    <div class="header-nav-bg">
        <div class="header-nav">
            <div class="logo-small">
                <a href="${basePath }"><img src="${basePath }/static/assets/img/public/logoSmall.png"></a>
            </div>
            <ul>
                <li class="active last"><a href="${basePath }/agdcgfw/index">场馆服务</a></li>
            </ul>
        </div>
    </div>
</div>

<div class="public-crumbs">
    <span><a href="${basePath }">首页</a></span><span>></span>
    <span><a href="${basePath }/agdcgfw/index">场馆服务</a></span><span>></span>
    <span><a href="${basePath}/agdcgfw/venueinfo?venid=${venue.id}">${venue.title}</a></span><span>></span>
    <span>${room.title }</span>
</div>

<!--主体开始-->
<div class="venue-main clearfix">
    <div class="venue-img"> <img src="${imgServerAddr}${whg:getImg750_500(room.imgurl)}" width="420" height="285"> </div>
    <div class="venue-info">
        <div class="public-fav"><a href="javascript:void(0)" class="shoucang" reftyp="${enumtyperoom}" refid="${room.id }" id="collection"></a></div>
        <h1>${room.title }</h1>
        <p class="clearfix"><i class="public-s-ico s-ico-15"></i><span class="tt">可容纳人数 :</span><span class="desc">${room.sizepeople}人</span></p>
        <p class="clearfix"><i class="public-s-ico s-ico-11"></i><span class="tt">面积 :</span><span class="desc">${room.sizearea}㎡</span></p>
        <p class="clearfix"><i class="public-s-ico s-ico-17"></i><span class="tt">电话 :</span><span class="desc">${venue.phone}</span></p>
        <p class="clearfix"><i class="public-s-ico s-ico-10"></i><span class="tt">地址 :</span><span class="desc">${venue.address} ${room.location} <a href="javascript:void(0)" onclick="WhgMap.openMap('${venue.address}', '${venue.longitude}', '${venue.latitude}')">[查看地图]</a></span></p>
        <c:if test="${not empty room.facility}">
        <p class="clearfix"><i class="public-s-ico s-ico-19"></i><span class="tt">设施 :</span><span class="desc">
            <%--<em>音乐</em><em>演唱会</em><em>会议</em>--%>
            <c:forEach var="item" items="${room.facility }">
                <em class="js-fmt-text" js-fn="FMTSBType" js-val="${item }"></em>
            </c:forEach>
        </span></p>
        </c:if>
    </div>
</div>
<div class="main clearfix">
    <div class="orderPayCont">
        <div class="dateCont clearfix">
            <!--<div class="wbg"></div>-->
            <div class="weekCont clearfix">
                <div class="week-btn week-prev"></div>
                <div class="week-groups">
                    <ul>
                    </ul>
                </div>
                <div class="week-btn week-next"></div>
            </div>
            <!--<div class="dateChange">
              <div  id="dateCont" class="dateTable"></div>
            </div>-->
        </div>
        <div class="tableListCont">
            <table>
                <tbody>
                </tbody>
            </table>
        </div>
        <div class="orderPayBg"><div class="orderPay"> <a href="javascript:void(0)">立即预订</a> </div></div>
    </div>

    <div class="main-info-bg main-info-no-padding main-info-bgColorW">
        <div class="main-info-container clearfix">
            <div class="teacherCont"></div>
            <div class="public-left-main">
                <div class="public-info-step">
                    <h3><span>场地简介</span></h3>
                    <div class="info">
                        ${room.summary}
                    </div>
                    <h3><span>场地描述</span></h3>
                    <div class="info">
                        ${room.description}
                    </div>
                    <%--<h3><span>申请条件</span></h3>
                    <div class="info">
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1. 筑造和栖居是人类建立和定义空间的基本。栖居的重要性不容置疑，人和家居空间的关系也并不止于理论范畴：家居空间的功能性；人（用户）在家庭场景内的感官互动、私密性和展示性的平衡；私人领域与公共领域的定义和分界等等，都从人对家居空间的讨论和思考中衍生而来家是营造情感、审美、兴趣、修养的空间，也是建立个体和群体间感情关系的空间 <br><br>
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2. 家以一个最基本的社会组成单位的形态与所处时代互视和对话；在飞速的城市化进程中，家居缓慢了生活的物化节奏；本展共有十二位来自世界不同区域的艺术家创作关于“家”的作品（部分艺术家作品首次在中国展出），艺术家或将家居环境中的庸常之物重构为独特的视觉景观。<br><br>
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3. 都从人对家居空间的讨论和思考中衍生而来家是营造情感、审美、兴趣、修养的空间，也是建立个体和群体间感情关系的空间。家以一个最基本的社会组成单位的形态与所处时代互视和对话；在飞速的城市化进程中，家居缓慢了生活的物化节奏；本展共有十二位来自世界不同区域的艺术家创作关于“家”的作品（部分艺术家作品首次在中国展出）。
                    </div>--%>

                    <c:if test="${not empty roomImgList or not empty roomVideoList or not empty roomAudioList}">
                        <div class="site clearfix">
                            <ul class="tab clearfix">
                                <c:if test="${not empty roomImgList}">
                                    <li>场地图片</li>
                                </c:if>
                                <c:if test="${not empty roomVideoList}">
                                    <li>场地视频</li>
                                </c:if>
                                <c:if test="${not empty roomAudioList}">
                                    <li>场地音频</li>
                                </c:if>
                            </ul>
                            <script>
                                $(function () {
                                    $("ul.tab li:eq(0)").click();
                                })
                            </script>
                            <c:if test="${not empty roomImgList}">
                                <div class="list1 on">
                                    <div class="demo-list list-video clearfix">
                                        <c:forEach var="item" items="${roomImgList}" varStatus="vs">
                                            <a ${vs.count==3? 'class="last"':''} href="javascript:void(0)" onClick="show_img(this,{url:'${imgServerAddr}${item.enturl}'})">
                                                <div class="img1">
                                                    <img src="${imgServerAddr}${whg:getImg300_200(item.enturl)}" height="170">
                                                    <span>${item.entname}</span>
                                                </div>
                                            </a>
                                        </c:forEach>
                                    </div>
                                </div>
                            </c:if>

                            <c:if test="${not empty roomVideoList}">
                                <div class="list1">
                                    <div class="demo-list list-video clearfix">
                                        <c:forEach var="item" items="${roomVideoList}" varStatus="vs">
                                            <a ${vs.count==3? 'class="last"':''} href="javascript:void(0)" onClick="show_vedio(this,{url:'${item.enturl}'})">
                                                <div class="mask"></div>
                                                <div class="video1">
                                                    <img src="${imgServerAddr}${whg:getImg300_200(item.deourl)}" height="170">
                                                    <span>${item.entname}</span>
                                                </div>
                                            </a>
                                        </c:forEach>
                                    </div>
                                </div>
                            </c:if>

                            <c:if test="${not empty roomAudioList}">
                                <div class="list1">
                                    <div class="demo-list list-mp3 clearfix">
                                        <c:forEach var="item" items="${roomAudioList}" varStatus="vs">
                                            <a ${vs.count==3? 'class="last"':''} href="javascript:void(0)" onClick="show_vedio(this,{url:'${item.enturl}'})">
                                                <div class="mask"></div>
                                                <div class="mp31">
                                                    <span>${item.entname}</span>
                                                </div>
                                            </a>
                                        </c:forEach>
                                    </div>
                                </div>
                            </c:if>

                        </div>
                    </c:if>

                    <div class="public-share">
                        <span class="btn qq"><a href="javascript:void(0);" class="fxqq"></a></span>
                        <span class="btn weixin"><a a href="javascript:void(0);" class="fxweix"></a></span>
                        <span class="btn weibo"><a href="javascript:void(0)" class="fxweibo" target="_blank"></a></span>
                        <span class="btn dianzan">
                            <em>0</em>
                            <a href="javascript:void(0)" class="dianzan" reftyp="${enumtyperoom}" refid="${room.id }" id="good"></a>
                        </span>
                    </div>
                </div>

                <!-- 动态包含评论 -->
                <jsp:include page="/pages/comm/agdcomment.jsp" flush="true">
                    <jsp:param name="reftype" value="7"/>
                    <jsp:param name="refid" value="${room.id }"/>
                </jsp:include>
                <!-- 动态包含评论-END -->
            </div>

            <div class="public-right-main">
                <c:if test="${not empty nextroomlist}">
                <div class="public-other-notice margin-bottom">
                    <h2>相关场地</h2>
                    <c:forEach var="item" items="${nextroomlist}" varStatus="vs">
                        <div class="item last clearfix">
                            <div class="right-img">
                                <a href="${basePath}/agdcgfw/venroominfo?roomid=${item.id}"><img src="${imgServerAddr}${whg:getImg300_200(item.imgurl)}" width="130" height="90"></a>
                            </div>
                            <div class="right-detail">
                                <a href="${basePath}/agdcgfw/venroominfo?roomid=${item.id}"><h3>${item.title}</h3></a>
                                <p class="time">面积：<span>${item.sizearea}</span>㎡</p>
                                <p class="time">容纳人数：<span>${item.sizepeople}</span>人</p>
                            </div>
                        </div>
                    </c:forEach>

                    <%--<c:if test="${empty nextroomlist}">--%>
                        <%--<div class="public-no-message"></div>--%>
                    <%--</c:if>--%>

                </div>
                </c:if>

                <div class="public-other-notice">
                    <h2>推荐场馆</h2>
                    <c:forEach var="item" items="${venlist}">
                        <div class="item clearfix">
                            <div class="right-img">
                                <a href="${basePath}/agdcgfw/venueinfo?venid=${item.id}"><img src="${imgServerAddr}${whg:getImg300_200(item.imgurl)}" width="130" height="90"></a>
                            </div>
                            <div class="right-detail">
                                <a href="${basePath}/agdcgfw/venueinfo?venid=${item.id}"><h3>${item.title}</h3></a>
                                <p class="time"></p>
                            </div>
                        </div>
                    </c:forEach>

                    <c:if test="${empty venlist}">
                        <div class="public-no-message"></div>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</div>
<!--主体结束-->


<!--公共主底部开始-->
<%@include file="/pages/comm/agdfooter.jsp"%>
<!--公共主底部结束-END-->

</body>
</html>
