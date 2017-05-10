<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<% request.setAttribute("now", new Date()); %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<%@include file="/pages/comm/agdhead.jsp"%>
<title>${actdetail.name}</title>
<link href="${basePath }/static/assets/css/activity/activityDetaiil.css" rel="stylesheet">
<link href="${basePath }/static/assets/js/plugins/oiplayer-master/css/oiplayer.css" rel="stylesheet">

<script src="${basePath }/static/assets/js/plugins/sidebar/stickySidebar.js"></script>
<script src="${basePath }/static/assets/js/activity/activityDetail.js"></script>
<script src="${basePath }/static/assets/js/plugins/oiplayer-master/plugins/flowplayer-3.2.6.min.js"></script>
<script src="${basePath }/static/assets/js/plugins/oiplayer-master/js/jquery.oiplayer.js"></script>

<script type="text/javascript">
$(function(){
	$(".source li:eq(0)").addClass("active");
	var _html = $(".source li:eq(0)").html();
	$(".sourceinfo").children("div:eq(0)").addClass("on");
	
	if($(".nowlist:eq(0)").size() == 0){
		$(".actvitm:eq(0)").click();
	}else{
		$(".nowlist:eq(0)").click();
	}
	
	if($(".source li").size() == 0){
		$(".source").remove();
	}
	
	 $(".js-fmt-text").each(function () {
         var v = $(this).attr("js-val");
         var fn = $(this).attr('js-fn');
         var text = WhgComm[fn].call(WhgComm, v);
         $(this).text(text);
     })
     
     var sellticket = "${actdetail.sellticket }";
     if (sellticket == 1) {
         $("#goNext").css("display", "none");
     }
     
     var endTime = "${actdetail.endtime }";
     var nowDate = new Date();
     endTime = dateFMT(endTime);
     var nowTime = dateFMT(nowDate);
     if(endTime < nowTime ){
     	 $("#goNext").html('<a >活动已结束</a>').addClass("no-editable");
     } 
})


</script>

</head>
<body class="oiplayer-example">
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
				<li class="active"><a href="${basePath }/agdwhhd/activitylist">活动预约</a></li>
				 <li><a href="${basePath }/agdwhhd/notice">活动公告</a></li>
				<li><a href="${basePath }/agdwhhd/news">活动资讯</a></li>
				<li class="last"><a href="${basePath }/agdwhhd/brandlist">品牌活动</a></li>
			</ul>
		</div>
	</div>
</div>
<!--公共主头部结束-END-->

<!--主体开始-->
<div class="special-bg">
    <div class="activity-main">
        <div class="public-crumbs">
            <span><a href="${basePath }">首页</a></span><span>></span>
            <%--<span><a href="${basePath }/agdwhhd/index">文化活动</a></span><span>></span>--%>
            <span><a href="${basePath }/agdwhhd/activitylist">活动预约</a></span><span>></span>
            <span>${actdetail.name}</span>
        </div>
        <div class="special-head">
            <div class="special-head-left">
            	<img alt="" src="${imgServerAddr}${whg:getImg750_500(actdetail.imgurl) }" style="width:380px;height: 240px">
            </div>
            <div class="special-head-right clearfix">
            
            <!-- 收藏 -->
            <div class="public-fav"><a reftyp="${enumtypeAct}" id="collection" refid="${actdetail.id }" class="shoucang"></a></div>
               
                <div class="head-father">
                    <div class="head-con on">
                        <h1>${actdetail.name}</h1>
                        <div class="detail">
                            <div class="time"><i class="public-s-ico s-ico-8"></i>发布时间：<span><fmt:formatDate value="${actdetail.statemdfdate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="time1 clearfix"><i class="public-s-ico s-ico-9"></i><span>活动时间：</span><fmt:formatDate value="${actdetail.starttime}" pattern="yyyy-MM-dd "/>~
                           	<fmt:formatDate value="${actdetail.endtime}" pattern="yyyy-MM-dd "/>
                           </div>
                           <div class="tel"><i class="public-s-ico s-ico-17"></i>活动电话：<span>${actdetail.telphone}</span></div>
                           <input type=hidden name="sellticket" id="sellticket" value="${actdetail.sellticket }">
                        </div>
                        <div class="detail detail1">
                            <div class="time">
                                <i class="public-s-ico s-ico-19"></i>活动标签：
                                <span>
	                                <c:forEach var="item" items="${actdetail.etag }">
							    		<span class="label"><a href="javascript:void (0)"><em class="js-fmt-text" js-fn="FMTActivityTag" js-val="${item }"></em></a></span>
							    	</c:forEach>
						    	</span>
                            </div>
                        </div>
                        <c:if test = "${ not empty sessionUser }">
                        	<div class="goNext" id="goNext">
			                  <a href="#" onclick="checkActState()" >立即报名</a>
			                </div>
                        </c:if>
                        <c:if test = "${empty sessionUser }">
                        	<div class="goNext" id="goNext">
			                   <a href="#" id="toLogin" onclick="checkLogin()" >立即报名</a> 
			                </div>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
        <div class="special-content clearfix">
            <div class="con-left clearfix">
            	<div class="public-info-step">
		        	<h3><span>活动详情</span></h3>
		            <div class="info">${actdetail.remark}</div>
					<!-- 下载 -->
					<c:if test="${not empty loadlist}">
						<div class="file-download-cont">
							<ul>
								<c:forEach items="${loadlist}" var="cc" varStatus="s">
									<li>
										<a href="${basePath }/whtools/downFile?filePath=${cc}"><i></i>${whg:getFileName(cc)}</a>

									</li>
								</c:forEach>
							</ul>
						</div>
					</c:if>
              	</div>
              	<!--分享 -->
              	<div class="public-share">
                     <span class="btn qq"><a href="javascript:void(0);" class="fxqq"></a></span>
                     <span class="btn weixin"><a a href="javascript:void(0);" class="fxweix"></a></span>
                     <span class="btn weibo"><a href="javascript:void(0)" class="fxweibo" target="_blank"></a></span>
                     <span class="btn dianzan">
                         <em>0</em>
                         <a href="javascript:void(0)" class="dianzan" reftyp="${enumtypeAct}" refid="${actdetail.id }" id="good"></a>
                     </span>
                 </div>
    		<c:if test="${not empty tsource or not empty tsource or not empty ssource}">
	           <div class="site clearfix sourceinfo">
                  <ul class="tab clearfix source">
                      <li class="active">活动图片</li>
                      <li>活动视频</li>
                      <li>活动音频</li>
                  </ul>
                  <c:if test="${not empty tsource}">
	                  <div class="list1">
	                      <div class="demo-list list-video clearfix">
	                      <c:forEach items="${tsource}" var="item" varStatus="s">
	                          <a ${s.count%3 == 0?'class="last"':'' } href="javascript:void(0)" onClick="show_img(this,{url:'${imgServerAddr}${item.enturl}'})">
	                              <div class="img1">
	                                  <img src="${imgServerAddr}${whg:getImg300_200(item.enturl)}"  width="252" height="170"/>
	                                  <span>${item.entname }</span>
	                              </div>
	                          </a>
	                       </c:forEach>
	                      </div>
	                  </div>
                  </c:if>
                  <c:if test="${empty tsource}">
                  	 <div class="list1">
                  	 </div>
                  </c:if>
                  <c:if test="${not empty ssource}">
	                  <div class="list1">
	                      <div class="demo-list list-video clearfix">
	                       <c:forEach items="${ssource}" var="item" varStatus="s">
	                          <a ${s.count%3 == 0?'class="last"':'' } href="javascript:void(0)" onClick="show_vedio(this,{url:'${item.enturl}'})">
	                              <div class="mask"></div>
	                              <div class="video1">
	                                  <img src="${imgServerAddr}${whg:getImg300_200(item.deourl)}" width="252" height="150"/>
	                                  <span>${item.entname }</span>
	                              </div>
	                          </a>
	                         </c:forEach>
	                      </div>
	                  </div>
                  </c:if>
                  <c:if test="${empty ssource}">
                  	 <div class="list1">
                  	 </div>
                  </c:if>
                  <c:if test="${not empty ysource}">
	                  <div class="list1">
	                      <div class="demo-list list-mp3 clearfix">
	                       <c:forEach items="${ysource}" var="item" varStatus="s">
	                          <a ${s.count%3 == 0?'class="last"':'' } href="javascript:void(0)" onClick="show_vedio(this,{url:'${item.enturl}'})">
	                          	  <div class="mask"></div>
	                              <div class="mp31">
	                                  <span>${item.entname }</span>
	                              </div>
	                          </a>
	                       </c:forEach>
	                      </div>
	                  </div>
                  </c:if>
                  <c:if test="${empty ysource}">
                  	 <div class="list1">
                  	 </div>
                  </c:if>
              </div>
			</c:if>
				<!-- 动态包含评论 -->
				<jsp:include page="/pages/comm/agdcomment.jsp" flush="true">
					<jsp:param name="reftype" value="1"/>
					<jsp:param name="refid" value="${actdetail.id }"/>
				</jsp:include>
				<!-- 动态包含评论-END -->

            </div>
            <div class="public-right-main">
                <div class="public-other-notice">
                    <h2>推荐活动</h2>
                      <c:choose>
                    	<c:when test="${not empty acttj}">
		                     <c:forEach items="${acttj}" var="item">
	                   			<div class="item clearfix">
			                        <div class="right-img">
			                            <a href="${basePath }/agdwhhd/activityinfo?actvid=${item.id}"><img src="${imgServerAddr}${ item.imgurl}" width="130" height="90" onerror="showDefaultIMG(this, '${basePath }/static/assets/img/img_demo/1.jpg')"></a>
			                        </div>
			                        <div class="right-detail">
			                            <a href="${basePath }/agdwhhd/activityinfo?actvid=${item.id}"><h3>${item.name}</h3></a>
			                            <p class="time"><fmt:formatDate value="${item.starttime}" pattern="yyyy-MM-dd"/></p>
			                        </div>
			                    </div>
		                     </c:forEach>
	                    </c:when>
	                    <c:otherwise>
	                    	<div class='public-no-message'></div>
	                    </c:otherwise>
	                </c:choose>
                </div>

                <c:if test="${not empty wh_zx_colinfo_ref}">
                <div class="public-other-notice" style="margin-top: 20px;">
                    <h2>相关咨讯</h2>
                        <ul>
                            <c:forEach items="${wh_zx_colinfo_ref }" var="row" varStatus="s">
                                <li><a href="${basePath }/agdwhhd/newsinfo?id=${row.clnfid}">${row.clnftltle }</a><p class="time"><fmt:formatDate value="${row.clnfcrttime}" pattern="yyyy-MM-dd" /></p></li>
                            </c:forEach>
                        </ul>
                </div>
                </c:if>
            </div>
        </div>
    </div>
</div>
<!--主体结束-->
<!--公共主底部开始-->
<%@include file="/pages/comm/agdfooter.jsp"%>
<!--公共主底部结束-END-->
</body>
<script>
$(function(){
	var sellticket = "${actdetail.sellticket }";
	if(sellticket == 1){
		$("#goNext").css("display","none");
	}
})

function checkLogin(){
	rongDialog({ type : false, title : "登录之后才能预定！", time : 3*1000 });
	$('#toLogin').attr('href','${basePath }/login');
}

function checkActState(){
	var actId = '${actdetail.id}';
	 $.ajax({
         url : '${basePath }/agdwhhd/checkActPublish',
         data : {actId: actId},
         dataType : "json",
         success : function(data){
             if (data.success == 1){
                 window.location.href = '${basePath }/agdwhhd/actBaoMing?actvid='+actId;
             }else {
                 rongDialog({ type : false, title : data.errormsg, time : 3*1000 });
             }
         }
     });
}
</script>
</html>

