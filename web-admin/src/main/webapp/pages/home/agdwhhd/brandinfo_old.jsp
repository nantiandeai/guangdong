<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%request.setAttribute("now", new Date()); %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<%@include file="/pages/comm/agdhead.jsp"%>
<title>广东省文化馆-文化活动-${brand.bratitle }</title>
<link href="${basePath }/static/assets/css/activity/specialDetail.css" rel="stylesheet">
<script src="${basePath }/static/assets/js/plugins/sidebar/stickySidebar.js"></script>
<script src="${basePath }/static/assets/js/activity/specialDetail.js"></script>

<link href="${basePath }/static/assets/js/plugins/oiplayer-master/css/oiplayer.css" rel="stylesheet">
<!-- core public JavaScript -->
<script src="${basePath }/static/assets/js/plugins/oiplayer-master/plugins/flowplayer-3.2.6.min.js" type="text/javascript"></script>
<script src="${basePath }/static/assets/js/plugins/oiplayer-master/js/jquery.oiplayer.js"></script>

<script type="text/javascript">
$(function() {
	//取消视频弹出标题
	$('.js__p_vedio_start').off("click");
	
	$(".loadDetail").bind('click',function(){ 
		var bracactid = $(this).attr("bracactid");
		$(this).siblings().removeClass("active");
		$(this).addClass("active");
		
		$(".actdetail div:eq(0)").html("<i class='public-s-ico s-ico-8'></i>发布时间：<span>--</span>");
		$(".actdetail div:eq(1)").html("<i class='public-s-ico s-ico-9'></i>活动时间：<span>00:00-00:00</span>");
		$(".actdetail div:eq(2)").html("<i class='public-s-ico s-ico-17'></i>活动电话：<span>000-0000000</span>");
		$(".actdetail div:eq(3)").html("<i class='public-s-ico s-ico-10'></i>活动地址：<span>--</span>");
		$(".lookdetail").attr("href","javascript:void(0)");
		
		$.ajax({
			type:"POST",		
			data:{bracactid:bracactid},
			url:getFullUrl("/agdwhhd/selectactDetail"),
			success : function(data){
				if(data.length > 0){
					var data = data[0];
					$(".actdetail div:eq(0)").html("<i class='public-s-ico s-ico-8'></i>发布时间：<span>"+dateFMT(data.actvopttime)+"</span>");
					$(".actdetail div:eq(1)").html("<i class='public-s-ico s-ico-9'></i>活动时间：<span>"+new Date(data.bracstime).Format("hh:mm")+"-"+new Date(data.bracedate).Format("hh:mm")+"</span>");
					$(".actdetail div:eq(2)").html("<i class='public-s-ico s-ico-17'></i>活动电话：<span>"+data.bractelephone+"</span>");
					$(".actdetail div:eq(3)").html("<i class='public-s-ico s-ico-10'></i>活动地址：<span>"+data.bracaddr+"</span>");
					$(".lookdetail").attr("href","${basePath }/agdwhhd/activityinfo?actvid="+data.bracactid);
				}else{
					$(".actdetail div:eq(0)").html("<i class='public-s-ico s-ico-8'></i>发布时间：<span>--</span>");
					$(".actdetail div:eq(1)").html("<i class='public-s-ico s-ico-9'></i>活动时间：<span>00:00-00:00</span>");
					$(".actdetail div:eq(2)").html("<i class='public-s-ico s-ico-10'></i>活动电话：<span>000-0000000</span>");
					$(".actdetail div:eq(3)").html("<i class='public-s-ico s-ico-17'></i>活动地址：<span>--</span>");
					$(".lookdetail").attr("href","javascript:void(0)");
				}
				 
			}
		});
	})
	
	if($(".nowlist:eq(0)").size() == 0){
		$(".loadDetail:eq(0)").click();
	}else{
		$(".nowlist:eq(0)").click();
	}
	
	$(".source li:eq(0)").addClass("active");
	var _html = $(".source li:eq(0)").html();
	$(".sourceinfo").children("div:eq(2)").addClass("on");
	
	//alert($(".source li").size());
	if($(".source li").size() == 0){
		$(".source").remove();
	}

})

</script>

</head>
<body class="oiplayer-example">
<!--公共主头部开始-->
<div id="header">
    <div class="main clearfix">
        <div class="gatalogMain">
            <div class="return">
                <a href="${basePath }/agdwhhd/index" class="btn">
                    <i class="kx-arrow kx-arrow-right">
                        <em></em>
                        <span></span>
                    </i>
                  	  返回文化馆主站
                </a>
            </div>
        </div>
    </div>
</div>
<!--公共主头部结束-->

<!--主体开始-->
<div class="special-bg" style="background:url(${basePath }/static/assets/img/activity/special-bg.jpg) no-repeat;">
     <div class="special-main">
        <div class="banner">
            <h1>${brand.bratitle }</h1>
            <p>${brand.braintroduce }</p>
        </div>
       <div class="special-head">
            <div class="special-head-left"><img src="${basePath }/${brand.brapic}" width="380" height="240" onerror="showDefaultIMG(this, '${basePath }/static/assets/img/img_demo/1.jpg')"></div>
            <div class="special-head-right clearfix">
                <div class="head-father">
                    <div class="head-con on">
                        <h1>${brand.bratitle}</h1>
                        <div class="detail actdetail">
                            <div class="time"><i class="public-s-ico s-ico-8"></i>发布时间：<span></span></div>
                            <div class="time1"><i class="public-s-ico s-ico-9"></i>活动时间：<span></span></div>
                            <div><i class="public-s-ico s-ico-17"></i>活动电话：<span></span></div>
                            <div><i class="public-s-ico s-ico-10"></i>活动地址：<span></span></div>
                        </div>
                        <a class="lookdetail" href="javascript:void(0)">查看活动详情</a>
                    </div>
                </div>
                <div class="head-con2">
                    <ul>
                  
                    	<c:if test="${listba !=null}">
	                    	<c:forEach items="${listba}" var="item" varStatus="idx">
		                        <li class="clearfix loadDetail ${(not empty item.bracstime and item.bracstime gt now or idx.last)?'nowlist':'' }" bracactid="${item.bracactid}">
		                            <div class="time"><fmt:formatDate value="${item.bracstime}" pattern="yyyy-MM-dd"/></div><div class="list"><i></i>第${idx.index+1}期</div>
		                        </li>
	                       </c:forEach>
                       </c:if>
                  
                   		<c:if test="${empty listba}">
		                 <li class="clearfix" style="display:none">
		                    <div class="time"></div><div class="list"><i></i>第n期</div>
		                 </li>
		                 </c:if>
                    </ul>
                </div>
            </div>
        </div>
  
        <div class="special-content clearfix">
            <div class="con-left clearfix sourceinfo">
                <div class="site">
                    <div class="site-head">现场直击</div>
                    <div class="detail">
                        <p>${brand.bradetail } </p>
                    </div>
                </div>
                
                 <div class="public-share">
                    <span class="btn qq"><a href="javascript:void(0);" class="fxqq"></a></span>
                    <span class="btn weixin"><a href="javascript:void(0)" target="_blank" class="fxweix"></a></span>
                    <span class="btn weibo"><a href="javascript:void(0)" target="_blank"  class="fxweibo"></a></span>
                	<span class="btn dianzan">
	                	<em>129</em>
	                	<a href="javascript:void(0)" class="dianzan" reftyp="2016103100000001" refid="${brand.braid }" id="good"></a>
	                    </a>
                	</span>
                </div>
                
                <ul class="tab clearfix oiplayer-example source">
	                <c:if test="${not empty souretp}">
	                    <li class="active">活动图片</li>
	                </c:if>
	                <c:if test="${not empty souresp}">
	                    <li>视频精选</li>
	                </c:if>    
	                <c:if test="${not empty ypsoure}">
	                    <li>音频精选</li>
	                </c:if>
                </ul>
                
               <c:if test="${not empty souretp}">
	                <div class="list1">
	                    <div class="demo-list list-video clearfix">
	                       <c:forEach items="${souretp}" var="item" varStatus="vs">
	                          <a ${vs.count%3==0? 'class="last"':''} href="javascript:void(0)" onClick="show_img(this,{url:'${basePath }/${item.enturl}'})">
	                              <div class="img1 ${vs.count%3==0? 'last':''}">
	                                  <img src="${basePath }/${item.enturl}" onerror="showDefaultIMG(this, '${basePath }/static/assets/img/img_demo/1.jpg')">
	                                  <span>${item.entname }</span>
	                              </div>
	                          </a>
	                       </c:forEach>
	                     </div>
	                </div>
                </c:if>
                
               <c:if test="${not empty souresp}">
	               <div class="list1">
	                 <div class="demo-list list-video clearfix">
	                         <c:forEach items="${souresp}" var="item" varStatus="vs">
	                          <a ${vs.count%3==0? 'class="last"':''} href="javascript:void(0)" onClick="show_vedio(this,{url:'${basePath }/${item.enturl}'})">
	                              <div class="mask"></div>
	                              <div class="video1">
	                                  <img src="${basePath }/${item.deourl}" width="252" height="170" onerror="showDefaultIMG(this, '${basePath }/static/assets/img/img_demo/1.jpg')">
	                                  <span>${item.entname }</span>
	                              </div>
	                          </a>
	                         </c:forEach>
	                 	 </div>
	            	</div>
            	</c:if>
            	
           	 	<c:if test="${not empty ypsoure}">
	           	  <div class="list1">
	                   <div class="demo-list list-mp3 clearfix">
	                    <c:forEach items="${ypsoure}" var="item" varStatus="vs">
	                       <a ${vs.count%3==0? 'class="last"':''} href="javascript:void(0)" onClick="show_vedio(this,{url:'${basePath}/${item.enturl}'})">
	                       	  <div class="mask"></div>
	                           <div class="mp31">
	                               <span>${item.entname }</span>
	                           </div>
	                       </a>
	                    </c:forEach>
	                   </div>
	               </div>
               </c:if>
          </div> 
             
         <div class="con-right event public-right-main">
             <div class="public-other-notice info1">
                <h2>往期活动</h2>
                     <c:choose>
                    	<c:when test="${not empty wqact}">
                    		<c:forEach items="${wqact}" var="item">
			                     <div class="item clearfix">
			                        <div class="right-img">
			                            <a href="${basePath }/agdwhhd/activityinfo?actvid=${item.actvid}"> <img src="${basePath }/${item.actvbigpic}" onerror="showDefaultIMG(this, '${basePath }/static/assets/img/img_demo/1.jpg')"></a>
			                        </div>
			                        <div class="right-detail">
			                            <a href="${basePath }/agdwhhd/activityinfo?actvid=${item.actvid}"><h3>${item.actvshorttitle}</h3></a>
			                            <p class="time">2015-10-20</p>
			                        </div>
	                    		 </div>
                    		</c:forEach>
	                    </c:when>
	                    <c:otherwise>
	                    	<div class='public-no-message'></div>
	                    </c:otherwise>
	                </c:choose>
             </div>
             <div class="info1 infofix">
                	<h2>活动资讯</h2>
	                <div class="detail">
	                  	<c:choose>
		                  	<c:when test="${not empty listzx}">
			                    <c:forEach items="${listzx}" var="item">
			                        <a href="${basePath }/agdwhhd/newsinfo?id=${item.clnfid}">${item.clnftltle}</a>
			                    </c:forEach>
		                   	</c:when>
		                   	<c:otherwise>
		                   		<div class='public-no-message'></div>
		                   	</c:otherwise>
	               		</c:choose>
	                 </div>
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