<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<%@include file="/pages/comm/agdhead.jsp"%>
<title>广东省文化馆-培训驿站</title>
<link href="${basePath }/static/assets/css/train/train.css" rel="stylesheet">
<script src="${basePath }/static/assets/js/train/train.js"></script>
<script type="text/javascript">
/** 设置点赞数量  */
function loadFabulous(reftyp, refid, obj){
	//判断是否点亮
	$.ajax({
		type : "POST",
		url : '${basePath}/comm/isLightenGood',	
		data : {reftyp : reftyp, refid : refid},
		success : function(data){
			obj.text(data.num);
		}
		
	});
}/** 设置点亮效果 -END*/

function showList(dataRows, event){
	
	var list = '';
	for(var i=0; i<dataRows.length && i<2; i++){
		list += '<div class="img "> <img src="${imgServerAddr}'+dataRows[i].trainimg+'" width="305" height="200"> </div>';
	}
	event.html(list);
	event.find(".img:eq(1)").addClass("last");
}



$(function(){
	//
	$('.fabulous-btn > span').each(function(e){
		var reftype = $(this).attr('reftype'); 
		var refid = $(this).attr('refid'); 
		loadFabulous(reftype, refid, $(this));
	});
	
	//
	$("ul.nav").on("click", "li.data_opt", function(){
		var idx = $(this).attr("idx");
		//alert(idx);
		var _imgCont =  $(".imgCont:eq("+idx+")");
		var teacherid = _imgCont.attr("tracherid");
		$.ajax({
			type: "POST",
			url: '${basePath}/agdpxyz/selTrainPic',
			data: {teacherid:teacherid},
			success: function(data){
				//alert(JSON.stringify(data));
				var dataRows = data;
				showList(dataRows, _imgCont);
			}
		});
		
	})
	$("ul.nav li.data_opt:eq(0)").click();
});
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
            	<li class="active"><a href="${basePath }/agdpxyz/index">培训驿站</a></li>
            	<li><a href="${basePath }/agdpxyz/notice">培训公告</a></li>
                <li><a href="${basePath }/agdpxyz/news">培训资讯</a></li>
                <li><a href="${basePath }/agdpxyz/teacher">培训师资</a></li>
                <li><a href="${basePath }/agdpxyz/trainlist">在线报名</a></li>
                <li class="last"><a href="${basePath }/agdpxyz/vod">在线点播</a></li>
                <%-- <li class="last"><a href="${basePath }/agdpxyz/resources">培训资源库</a></li> --%>
            </ul>
        </div>
    </div>
</div>
<!--公共主头部开始-END-->

<!--广告开始-->
		<div class="public_ad" style="background:url(${imgServerAddr}${whg:getImg750_500(adv.picture )}) no-repeat 50% 50%;"></div>
<!--广告结束--> 

<!--主体开始-->
<!-- 最新动态 -->
<div class="main-info-bg main-info-bgColorW">
	<div class="main-info-container main-info-title">
		<center>
			<h2><span class="line-l"></span><span class="tt">最新动态</span><span class="line-r"></span></h2>
		</center>
	  <div class="news-cont clearfix">
		   <div class="img">
			<ul class="basic">
		     	 <c:choose>
					<c:when test="${not empty ympz }"> 
			 			<c:forEach items="${ympz }" var="item" end="2">
		    				<li>
		        				<div class="mask"> <a href="${basePath }/agdpxyz/newsinfo?id=${item.cfgshowid}">${item.cfgshowtitle}</a> </div>
		        				<a href="${basePath }/agdpxyz/newsinfo?id=${item.cfgshowid}"><img src="${basePath }/${item.cfgshowpic}" height="370" width="480"/> </a>
		    				</li>
		  				</c:forEach>
		  			</c:when>
					<c:otherwise>
							<li>
					            <div class="mask">
					                <a href="javascript:void(0)">2016年度中国歌剧舞剧的院舞2</a>
					            </div>
		            			<a href="javascript:void(0)"><img src="${basePath }/static/assets/img/img_demo/activityindex1.jpg"></a>
		    				</li>
					</c:otherwise>
				</c:choose> 
		     </ul>
		     <div class="btn-top btn-top-prev"></div>
		     <div class="btn-top btn-top-next"></div>
		</div>
		
		<div class="newsGroups">
			<ul class="nav tab_list clearfix">
			    <li class="active">培训公告</li>
			    <li>培训资讯</li>
		  	</ul>
		  	<div class="tab_content newsList active">
		    	<ul>
				    <c:forEach items="${pxgg}" var="item" end="5">
				    	<li><i></i><a href="${basePath }/agdpxyz/noticeinfo?id=${item.clnfid}">${item.clnftltle}</a><em><fmt:formatDate value="${item.clnfcrttime}" pattern="yyyy-MM-dd "/></em></li>
				    </c:forEach>
		   		</ul>
		   		<div class="more"> <a href="${basePath }/agdpxyz/notice" class="public-more">MORE</a> </div>
		 	</div>
		 	<div class="tab_content newsList">
		   		<ul>
				   	<c:forEach items="${pxzx}" var="item" end="5">
				   		<li><i></i><a href="${basePath }/agdpxyz/newsinfo?id=${item.clnfid}">${item.clnftltle}</a><em><fmt:formatDate value="${item.clnfcrttime}" pattern="yyyy-MM-dd "/></em></li>
				   	</c:forEach>
		   		</ul>
		   		<div class="more"> <a href="${basePath }/agdpxyz/news" class="public-more">MORE</a> </div>
		    </div>
		 </div>
	  </div>
	</div>
</div>
<!-- 最新动态  END-->

<!-- 热门培训  -->
<div class="main-info-bg">
  <div class="main-info-container main-info-title">
  
    <center>
      <h2><span class="line-l"></span><span class="tt">热门培训</span><span class="line-r"></span></h2>
    </center>
    
    <div class="hotTrain">
      	<ul class="nav train-nav-cont">
	      <c:if test="${not empty ys }">
	      	<c:forEach items="${ys}" var="item" varStatus="sta">
	      		<li class="hg"><span><a href="${basePath}/agdpxyz/trainlist?typid=${item.id}">${item.name }</a></span></li>
	      	</c:forEach>
	      </c:if>
      	</ul>
		<div class="hotTrainGroupsCont">
			<div class="hotTrainGroups">
				<ul class="clearfix">
				  	<c:if test="${not empty whTrain}">
						<c:forEach items="${whTrain}" var="px" varStatus="state" end="2">
							<li ${state.index eq 2 ? 'class=\"last\"' :''}>
								<div class="mask"> <a href="${basePath }/agdpxyz/traininfo?traid=${px.id}">${px.title}</a> </div>
								<a href="${basePath }/agdpxyz/traininfo?traid=${px.id}"><img src="${imgServerAddr}${whg:getImg750_500(px.trainimg)}" width="320px" height="460px"></a>
							</li>
						</c:forEach>
					</c:if>
				</ul>
			<div class="more"> <a href="${basePath }/agdpxyz/trainlist" class="public-more">MORE</a> </div>
			</div>
		</div>
    </div>
    
  </div>
</div>
<!-- 热门培训   END-->

<!-- 在线点播   -->
<div class="main-info-bg main-info-bgColorW">
	 <div class="main-info-container main-info-title">
	   <center>
	     <h2><span class="line-l"></span><span class="tt">在线点播</span><span class="line-r"></span></h2>
	   </center>
	   <div class="trainVedio">
	     <div class="vedio-btn-cont">
	       <div class="vedio-prev"></div>
	       <div class="vedio-next"></div>
	     </div>
	     <div class="vedio-groups">
	       <div class="group">
	         <ul class="forceCentered">
	         	<c:choose>
	  		 		<c:when test="${not empty zxdb }"> 
	        			<c:forEach items="${zxdb}" var="db" varStatus="states"> 
	        				<c:if test="${(states.index % 2) eq 0 }">
	            				<li>
	        				</c:if>
						            <div class="item">
						              <div class="mask"><a href="${basePath }/agdpxyz/vodinfo?drscid=${db.drscid}"></a></div>
						              <a href="${basePath }/agdpxyz/vodinfo?drscid=${db.drscid}"><img src="${imgServerAddr}${db.drscpic}"></a>
						              <div class="video-title">
					                   	 <h3>${db.drsctitle}</h3>
					                  </div>
							        </div>
	             	
				            <c:if  test="${(states.index % 2) eq 1 or states.last}">
				            	</li>
				        	</c:if>
	            
	           			</c:forEach>
	           		</c:when>
	  		 		<c:otherwise>
						<li>
							<div class="item">
								<div class="mask"><a href="javascript:void(0)"></a></div>
							    <a href="javascript:void(0)"><img src="${basePath }/static/assets/img/img_demo/trainVedio1.jpg"></a>
								<div class="video-title">
									<h3>钢琴成人培训班第23期</h3>
								</div>
							</div>
							<div class="item">
								<div class="mask"><a href="javascript:void(0)"></a></div>
							  	<a href="javascript:void(0)"><img src="${basePath }/static/assets/img/img_demo/trainVedio1.jpg"></a>
								<div class="video-title">
									<h3>钢琴成人培训班第23期</h3>
								</div>
							 </div>
						</li>
			           	<li>
			             	<div class="item">
			               		<div class="mask"><a href="javascript:void(0)"></a></div>
			               		<a href="javascript:void(0)"><img src="${basePath }/static/assets/img/img_demo/trainVedio1.jpg"></a>
				               	<div class="video-title">
				                 	<h3>汉调音乐剧</h3>
				               	</div>
			             	</div>
			             	<div class="item">
			               		<div class="mask"><a href="javascript:void(0)"></a></div>
			               		<a href="javascript:void(0)"><img src="${basePath }/static/assets/img/img_demo/trainVedio1.jpg"></a>
					            <div class="video-title">
					            	<h3>2016暑假成人 - 钢琴成人培训班第23期</h3>
					            </div>
			             	</div>
			           	</li>
			           	<li>
			             	<div class="item">
				               	<div class="mask"><a href="javascript:void(0)"></a></div>
				               	<a href="javascript:void(0)"><img src="${basePath }/static/assets/img/img_demo/trainVedio1.jpg"></a>
			               		<div class="video-title">
			                 		<h3>钢琴成人培训班第23期</h3>
			               		</div>
			             	</div>
			             	<div class="item">
			               		<div class="mask"><a href="javascript:void(0)"></a></div>
			               		<a href="javascript:void(0)"><img src="${basePath }/static/assets/img/img_demo/trainVedio1.jpg"></a>
				               	<div class="video-title">
				                	<h3>钢琴成人培训班第23期</h3>
				               	</div>
			             	</div>
			           	</li>
	  		 		</c:otherwise>
		 		</c:choose> 
	         </ul>
	         
	       </div>
	     </div>
	     <div class="more"> <a href="${basePath }/agdpxyz/vod" class="public-more">MORE</a> </div>
	   </div>
	 </div>
</div>
<!-- 在线点播 END   -->

<!-- 培训师资 -->
<div class="main-info-bg">
	<div class="main-info-container main-info-title clearfix">
	    <center>
	      <h2><span class="line-l"></span><span class="tt">培训师资</span><span class="line-r"></span></h2>
	    </center>
    
    	<!-- 培训师资-->
	    <div class="teacherGroups">
			<ul class="teacher-info">
				<c:forEach items="${teacher}" var="item" end="2">
					<li>
						<div class="imgCont clearfix" tracherid="${item.teacherid}">
						</div>
						<div class="info">
							<h4>
								<span>${item.teachername}</span>
								<!-- <em>女神</em> -->
							</h4>
							<p>${item.teacherintroduce}</p>
							<div class="fabulous">
								<div class="fabulous-btn" > <span reftype="2016112300000001" refid="${item.teacherid }"></span> <i> <a href="javascript:void(0);"></a></i></div>
							</div>
						</div>
						<div class="more">
							<a href="${basePath }/agdpxyz/teacherinfo?id=${item.teacherid}" class="public-more">VIEW DETAILS</a>
						</div>
					</li>
				</c:forEach>
			</ul>
		</div>
		<!-- 培训师资-END -->
		
		<!-- 培训师资右边图片区 -->
	    <div class="teacher-nav-cont">
	    	<ul class="nav teacher-nav">
		      	<c:set var="tempStr" value=""></c:set>
		     	<c:forEach items="${teacher}" var="item" end="2">
		     		<c:set var="tempStr" value="${tempStr += item.teacherpic },"></c:set>
		      	</c:forEach>
		        <script type="text/javascript">
		        	var imgUrlNameStr = "${tempStr}";
					var imgUrlName = imgUrlNameStr.split(",");//['trainTeacher1.jpg','trainTeacher2.jpg','trainTeacher3.jpg'];
					
		        	$('.teacherGroups').sly({
						easing: "easeOutExpo",
						pagesBar: ".teacher-nav",
						pageBuilder: function (index) {
							return '<li class="data_opt" idx="'+index+'"><div class="mask"></div><img src="${imgServerAddr}'+imgUrlName[index]+'" width="220" height="220"></li>';
						},
						horizontal: 0
					});
		        </script>
	      	</ul>
	    	<div class="more"> <a href="${basePath }/agdpxyz/teacher">MORE +</a> </div>
		</div>
		<!-- 培训师资右边图片区-END -->
	</div>
</div>
<!-- 培训师资-END -->

<!-- 培训资源库 -->
<div class="main-info-bg main-info-bgColorW">
  <div class="main-info-container main-info-title clearfix">
    <center>
      <h2><span class="line-l"></span><span class="tt">培训资源库</span><span class="line-r"></span></h2>
    </center>
    <div class="trainResource ">
      <div class="resource">
        <ul class="centered">
          <li>
            <div class="mask">
            <h4>文化资源库</h4>
            <a href="#">广义上的文化资源泛指人们从事一切与文化活动有关的生产和生活内容的总称，它以精神状态为主要存在形式；
狭义上的文化资源是指对人们能够产生直接和间接经济利益的精神文化内容。</a> </div>
            <a href="#"><img src="${basePath }/static/assets/img/train/zyk1.jpg"></a>
          </li>
        </ul>
      </div>
      <div class="arrow-right"></div>
    </div>
    <div class="more resource-more"> <a href="#" class="public-more">MORE</a> </div>
  </div>
</div>
<!--主体结束--> 

<!--公共主底部开始-->
<%@include file="/pages/comm/agdfooter.jsp"%>
<!--公共主底部结束-END-->
</body>
</html>