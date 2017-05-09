<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="${basePath }/pages/comm/comm_center.js"></script>
<script>
var basePath = '${basePath}';
</script>
<div class="userHeader clearfix">
	<div class="header">
    	<div class="userInfo">
        	<h2>Hi,${sessionUser.nickname}</h2>
            <div class="userClass">
		         <span class="ico i-1 unBind tipso_top" id="setPhone" title="未绑定手机"><a href="${basePath }/center/safely-phone"></a></span> 
		         <%--<span class="ico i-2 unBind tipso_top" id="setEmail" title="未绑定电子邮箱"><a href="${basePath }/center/safely-email"></a></span> --%>
		         <span class="ico i-3 unBind tipso_top" id="setIsRealName" title="未绑实名认证"><a href="${basePath }/center/safely-userReal"></a></span> 
		        <!--  <span class="ico i-4 unBind tipso_top" title="未绑定团队账号">
		         <a href="#"></a></span>
		         <span class="ico i-5 unBind tipso_top" title="未认证艺术资质"><a href="#"></a></span>
		         <span class="ico i-6 unBind tipso_top" title="未认证企业资质"><a href="#"></a></span> -->
            </div>
        </div>
    </div>
</div>