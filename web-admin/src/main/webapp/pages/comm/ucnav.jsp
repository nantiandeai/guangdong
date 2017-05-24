<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% 
	String reqPath = request.getRequestURI(); 
	reqPath = reqPath.substring(reqPath.lastIndexOf("/")+1);
	reqPath = reqPath.substring(0, reqPath.lastIndexOf(".jsp"));
	reqPath = "center/"+reqPath;
	request.setAttribute("reqPath", reqPath); 
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script src="${basePath }/pages/comm/agdtop.js"></script>
<%-- <link href="${basePath }/static/assets/css/userCenter/userCenter.css" rel="stylesheet"> --%>
<%-- <script src="${basePath }/static/assets/js/userCenter/public.js"></script> --%>

<script type="text/javascript">

function removeAlert(evt){
	if (!evt.attr("reftype")) return;
	var reftype = evt.attr("reftype");
	$.ajax({
		type : "POST",
		url : '${basePath}/center/delAlert',
		data : {reftype : reftype},
		success : function(data){
			//console.log("test==>"+reftype+" "+JSON.stringify(data));
			if(data.success == "0"){
				evt.parents("li").find("i").remove();
			}
		}
	});
}

/**
 * 页面加载后
 */
$(function(){ 
	//显示提示消息
	$.ajax({
		type : "POST",
		url : '${basePath}/center/alerts',
		success : function(data){
			if(data.success == "0"){
				var altlist = data.alertList;
				//alert($("li a[reftype='1']").html()+"==="+altlist[0].reftype);
				if (!altlist) return;
				for(var i in altlist){
					//console.log("test==>add");
					$("li a[reftype='"+altlist[i].reftype+"']").parents("li").append("<i></i>");
					//alert($("li a[reftype='1']").html());
				}
			}
		}
	}).done(function(){
		$("#uull li.active").each(function(){
			var _actA = $(this).find("a[reftype]");
			//console.log("test==>del");
			removeAlert(_actA);
		})
	});
	//去除提示消息
	$("#uull li a").off('click').on('click',function(){
		removeAlert($(this));
	});
	
});

</script>

<%--<li<c:if test="${reqPath == 'center/userCenter' }"> class="active"</c:if>><a href="${basePath }/center/userCenter"><em class="ico i-9"></em>账户概况</a></li>--%>
<li<c:if test="${reqPath == 'center/userInfo'   }"> class="active"</c:if>><a href="${basePath }/center/userInfo"><em class="ico i-1"></em>基本信息</a></li>
<!-- <li><a href="order.html"><em class="ico i-2"></em>我的订单</a></li> -->
<li<c:if test="${reqPath == 'center/activity'   }"> class="active"</c:if>><a href="${basePath }/center/activity" refuid="refuid" reftype="1"><em class="ico i-6"></em>我的活动</a></li>
<li<c:if test="${reqPath == 'center/curriculum' }"> class="active"</c:if>><a href="${basePath }/center/curriculum" refuid="refuid" reftype="2"><em class="ico i-3"></em>我的课程</a></li>
<li<c:if test="${reqPath == 'center/venueorder' }"> class="active"</c:if>><a href="${basePath }/center/myVenue" refuid="refuid" reftype="3"><em class="ico i-1"></em>我的场馆</a></li>
<li<c:if test="${reqPath == 'center/favorite'   }"> class="active"</c:if>><a href="${basePath }/center/favorite" refuid="refuid" reftype="4"><em class="ico i-8"></em>我的收藏</a></li>
<li<c:if test="${reqPath == 'center/comment'    }"> class="active"</c:if>><a href="${basePath }/center/comment" refuid="refuid" reftype="5"><em class="ico i-7"></em>我的点评</a></li>
<!-- <li><a href="message.html"><em class="ico i-4"></em>我的消息</a></li> -->
<li 
	<c:if test="${(reqPath == 'center/safely') 
	or (reqPath eq 'center/safelyUserReal')
	or (reqPath eq 'center/safelyPwd')
	or (reqPath eq 'center/safelyPhone')
	or (reqPath eq 'center/safelyEmail')    }"> class="active"</c:if>
>
	<a href="${basePath }/center/safely" refuid="refuid" reftype="6"><em class="ico i-5"></em>安全设置</a>
</li>
<!--
<li <c:if test="${fn:startsWith(reqPath, 'center/help')}"> class="active"</c:if>><a href="${basePath }/center/help"><em class="ico i-9"></em>帮助中心</a></li>
-->