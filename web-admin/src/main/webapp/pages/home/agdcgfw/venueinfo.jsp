<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <%@include file="/pages/comm/agdhead.jsp"%>
    <title>${venue.title }</title>
    <link href="${basePath }/static/assets/css/field/fieldOrder.css" rel="stylesheet">
    <%--<link href="${basePath }/static/assets/js/plugins/oiplayer-master/css/oiplayer.css" rel="stylesheet">--%>
    <script src="${basePath }/static/assets/js/plugins/laydate.dev.js"></script>
    <script src="${basePath }/static/assets/js/field/fieldOrder.js"></script>

    <%--<script src="${basePath }/static/assets/js/plugins/oiplayer-master/plugins/flowplayer-3.2.6.min.js"></script>
    <script src="${basePath }/static/assets/js/plugins/oiplayer-master/js/jquery.oiplayer.js"></script>--%>

    <%--<script type="text/javascript" src="${basePath }/pages/home/agdcgfw/wktool.js"></script>--%>
    <%--<script type="text/javascript" src="${basePath }/pages/home/agdcgfw/venueinfo.js"></script>--%>

    <script src="${basePath }/static/common/js/whg.maps.js"></script>

    <script>
        $(function () {
            $(".js-fmt-text").each(function () {
                var v = $(this).attr("js-val");
                var fn = $(this).attr('js-fn');
                var text = WhgComm[fn].call(WhgComm, v);
                $(this).text(text);
            })
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
	<span>${venue.title }</span>
</div>

<!--主体开始-->
<div class="venue-main clearfix">
  <div class="venue-img"> <img src="${imgServerAddr}${whg:getImg750_500(venue.imgurl)}" width="420px" height="285px"> </div>
  <div class="venue-info">
  	<div class="public-fav"><a href="javascript:void(0)" class="shoucang" reftyp="${enumtypevenue}" refid="${venue.id }" id="collection"></a></div>
    <h1>${venue.title }</h1>
    <p class="clearfix"><i class="public-s-ico s-ico-11"></i><span class="tt">区域 :</span><span class="desc js-fmt-text" js-fn="FMTAreaType" js-val="${venue.area }"></span></p>
    <p class="clearfix"><i class="public-s-ico s-ico-12"></i><span class="tt">类型 :</span><span class="desc js-fmt-text" js-fn="FMTVenueType" js-val="${venue.etype }"></span></p>
    <p class="clearfix"><i class="public-s-ico s-ico-17"></i><span class="tt">电话 :</span><span class="desc">${venue.phone }</span></p>
    <p class="clearfix"><i class="public-s-ico s-ico-10"></i><span class="tt">地址 :</span><span class="desc">${venue.address }
    	<%--<c:if test="${not empty venue.venaddr }">
    		<a href="javascript:void(0)" address="${venue.venaddr }">[查看地图]</a>
    	</c:if>--%>
    </span></p>
      <c:if test="${not empty venue.etag }">
          <p class="clearfix"><i class="public-s-ico s-ico-13"></i><span class="tt">用途 :</span><span class="desc">
            <%--<em>比赛</em><em>演唱会</em><em>会议</em>--%>
            <c:forEach var="item" items="${venue.etag }">
                <em class="js-fmt-text" js-fn="FMTVenueTag" js-val="${item }"></em>
            </c:forEach>
        </span></p>
      </c:if>
  </div>
</div>

<div class="main clearfix">
    <div class="main-info-bg main-info-no-padding main-info-bgColorW">
        <div class="main-info-container clearfix">
            <div class="public-left-main">
                <c:if test="${not empty roomlist}">
                <div class="public-info-step">
                    <h3> <span>选择场地</span><em>(<i class="red">${fn:length(roomlist)}</i>)</em>
                        <div class="pre-right"> <i class="kx-arrow kx-arrow-left"> <em></em> <span></span> </i> </div>
                        <div class="pre-left"> <i class="kx-arrow kx-arrow-right"> <em></em> <span></span> </i> </div>
                    </h3>
                    <div class="info">
                        <div class="eventliebiao">
                            <ul class="clearfix">
                                <c:forEach var="item" items="${roomlist}" varStatus="vs">
                                    <li class="${vs.first? 'active':''}">
                                        <a href="${basePath}/agdcgfw/venroominfo?roomid=${item.id}">
                                        <img src="${imgServerAddr}${whg:getImg300_200(item.imgurl)}" width="185" height="123">
                                        <em>
                                            ${item.title}
                                        </em>
                                        </a>
                                    </li>
                                </c:forEach>

                            </ul>
                        </div>
                    </div>
                </div>
                </c:if>
                <div class="public-info-step">
                    <h3><span>场馆简介</span></h3>
                    <div class="info">
                        ${venue.summary}
                    </div>
                    <h3><span>场馆描述</span></h3>
                    <div class="info">
                        ${venue.description}
                    </div>
                    <c:if test="${not empty venue.facilitydesc}">
                    <h3><span>设施描述</span></h3>
                    <div class="info">
                        ${venue.facilitydesc}
                    </div>
                    </c:if>
                    <%--<h3><span>申请条件</span></h3>
                    <div class="info">
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1. 筑造和栖居是人类建立和定义空间的基本。栖居的重要性不容置疑，人和家居空间的关系也并不止于理论范畴：家居空间的功能性；人（用户）在家庭场景内的感官互动、私密性和展示性的平衡；私人领域与公共领域的定义和分界等等，都从人对家居空间的讨论和思考中衍生而来家是营造情感、审美、兴趣、修养的空间，也是建立个体和群体间感情关系的空间 <br><br>
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2. 家以一个最基本的社会组成单位的形态与所处时代互视和对话；在飞速的城市化进程中，家居缓慢了生活的物化节奏；本展共有十二位来自世界不同区域的艺术家创作关于“家”的作品（部分艺术家作品首次在中国展出），艺术家或将家居环境中的庸常之物重构为独特的视觉景观。<br><br>
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3. 都从人对家居空间的讨论和思考中衍生而来家是营造情感、审美、兴趣、修养的空间，也是建立个体和群体间感情关系的空间。家以一个最基本的社会组成单位的形态与所处时代互视和对话；在飞速的城市化进程中，家居缓慢了生活的物化节奏；本展共有十二位来自世界不同区域的艺术家创作关于“家”的作品（部分艺术家作品首次在中国展出）。
                    </div>--%>

                    <c:if test="${not empty venImgList or not empty venVideoList or not empty venAudioList or not empty doc}">
                    <div class="site clearfix">
                        <ul class="tab clearfix">
                            <c:if test="${not empty venImgList}">
                                <li>场馆图片</li>
                            </c:if>
                            <c:if test="${not empty venVideoList}">
                                <li>场馆视频</li>
                            </c:if>
                            <c:if test="${not empty venAudioList}">
                                <li>场馆音频</li>
                            </c:if>
                            <c:if test="${not empty doc}">
                                <li>场馆文件</li>
                            </c:if>
                        </ul>
                        <script>
                            $(function () {
                                $("ul.tab li:eq(0)").click();
                            })
                        </script>

                        <c:if test="${not empty venImgList}">
                            <div class="list1 on">
                                <div class="demo-list list-video clearfix">
                                    <c:forEach var="item" items="${venImgList}" varStatus="vs">
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

                        <c:if test="${not empty venVideoList}">
                            <div class="list1">
                                <div class="demo-list list-video clearfix">
                                    <c:forEach var="item" items="${venVideoList}" varStatus="vs">
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

                        <c:if test="${not empty venAudioList}">
                            <div class="list1">
                                <div class="demo-list list-mp3 clearfix">
                                    <c:forEach var="item" items="${venAudioList}" varStatus="vs">
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
                        <!-- 下载 -->
                        <c:if test="${not empty doc }">
                            <div class="list1">
                                <div class="file-download-cont">
                                    <ul>
                                        <c:forEach items="${doc}" var="loadlists" varStatus="s">
                                            <li>
                                                <a href="${basePath }/whtools/downFile?filePath=${loadlists.enturl}"><i></i>${loadlists.entname}</a>
                                            </li>
                                        </c:forEach>
                                    </ul>
                                </div>
                            </div>
                        </c:if>
                        <!-- 下载 -->

                    </div>
                    </c:if>

                    <div class="public-share">
                        <span class="btn qq"><a href="javascript:void(0);" class="fxqq"></a></span>
                        <span class="btn weixin"><a a href="javascript:void(0);" class="fxweix"></a></span>
                        <span class="btn weibo"><a href="javascript:void(0)" class="fxweibo" target="_blank"></a></span>
                        <span class="btn dianzan">
                            <em>0</em>
                            <a href="javascript:void(0)" class="dianzan" reftyp="${enumtypevenue}" refid="${venue.id }" id="good"></a>
                        </span>
                    </div>
                </div>

                <!-- 动态包含评论 -->
                <jsp:include page="/pages/comm/agdcomment.jsp" flush="true">
                    <jsp:param name="reftype" value="6"/>
                    <jsp:param name="refid" value="${venue.id }"/>
                </jsp:include>
                <!-- 动态包含评论-END -->

            </div>

            <div class="public-right-main">
                <div class="public-other-notice margin-bottom padding-bottom-0">
                    <div class="map" id="maps_ven_target" style="width: 300px; height: 222px;">
                    </div>
                    <script>
                        $(function () {
                            WhgMap.showMap('maps_ven_target', '${venue.address}', '${venue.longitude}', '${venue.latitude}')
                        })
                    </script>
                </div>
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