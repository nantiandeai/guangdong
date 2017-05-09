<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath(); request.setAttribute("basePath", basePath); %>
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
<!--[if lt IE 9] >
<script src="${basePath }/static/assets/js/plugins/ie/IE9.js"></script> 
<! [endif]]-->
<script src="${basePath }/static/assets/js/public/jquery-1.11.0.min.js"></script> 
<script src="${basePath }/static/assets/js/public/jquery-migrate-1.0.0.js"></script> 
<script src="${basePath }/static/assets/js/public/comm.js"></script>
<script>
var basePath = '${basePath}';
var preurl = '${preurl}';
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
        	<li class="active">安全设置</li>
        </ul>
        <div class="safe-row safe-row-1 clearfix">
        	<a href="${basePath }/center/safely-pwd"></a>
        	<div class="ico err"  id="setPwd"></div>
            <div class="safeTt">设置登录密码</div>
            <div class="safeState" id="isSet">未设置</div>
            <div class="setUp"></div>
        </div>
        <div class="safe-row safe-row-2 clearfix">
        	<a href="${basePath }/center/safely-phone"></a>
        	<div class="ico err" id="isBindPhone"></div>
            <div class="safeTt">绑定手机</div>
            <div class="safeState" value="${phone}">
            <c:choose>
         			<c:when test="${not empty phone}">
     			    	<script type="text/javascript">
	      					var phone = "${phone}";
	      					document.write( numFMT(phone) );
         				</script>	
         			</c:when>
         			<c:otherwise>
 						未设置
         			</c:otherwise> 
           	</c:choose>
            </div>
            <div class="setUp"></div>
        </div>

        <!-- 取消用Email注册，这里也取消 -->
        <%--<div class="safe-row safe-row-3 clearfix">
        	<a href="${basePath }/center/safely-email"></a>
        	<div class="ico err" id="isBindEmail"></div>
            <div class="safeTt">绑定邮箱</div>
            <div class="safeState" value="${email}">
            <c:choose>
         			<c:when test="${not empty email}">
     			    	<script type="text/javascript">
	      					 var email = "${email}";
	      					document.write( email.replace(/(.{2}).+(.{2}@.+)/g,"$1****$2"));
         				</script>	
         			</c:when>
         			<c:otherwise>
 						未设置
         			</c:otherwise> 
           	</c:choose>
            </div>
            <div class="setUp"></div>
        </div>--%>


        <div class="safe-row safe-row-4 clearfix">
        	<a href="${basePath }/center/safely-userReal"></a>
        	<div class="ico err" id="setReal"></div>
            <div class="safeTt">实名认证</div>
            <div class="safeState" id="isReal">未实名</div>
            <div class="setUp"></div>
        </div>
    </div>
</div>

<!--底部开始-->
<%@include file="/pages/comm/agdfooter.jsp"%>
<!--底部结束-->

<div class="md-overlay"></div>
<!-- core public JavaScript --> 
<script src="${basePath }/static/assets/js/plugins/tipso/tipso.js"></script>
<script src="${basePath }/static/assets/js/userCenter/public.js"></script>
<script src="${basePath }/static/assets/js/userCenter/message.js"></script>
<script src="${basePath }/pages/home/center/js/safely.js"></script>
</body>
</html>