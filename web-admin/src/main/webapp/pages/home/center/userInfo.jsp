<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath(); request.setAttribute("basePath", basePath); %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
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
<script src="${basePath }/static/assets/js/public/jquery-1.11.0.min.js"></script>
<script src="${basePath }/static/assets/js/public/jquery-migrate-1.0.0.js"></script>
<!--[if lt IE 9] >
<script src="${basePath }/static/assets/js/plugins/ie/IE9.js"></script> 
<! [endif]]-->
<script type="text/javascript">
var preurl = "${preurl}";
</script>
</head>
<body>

<!-- 公共头部 -->
<%@include file="/pages/comm/agdtop.jsp"%>
<!-- 公共头部END -->

<!-- 公共绑定 -->
<%@include file="/pages/comm/comm_center.jsp"%>
<!-- 公共绑定END -->

<div class="main clearfix">

	<div class="leftPanel">
		<ul>
			<!--用户中心导航-->
			<%@include file="/pages/comm/ucnav.jsp"%>
			<!--用户中心导航END-->
		</ul>
	</div>
  
	<div class="rightPanel">
		<ul class="commBtn clearfix">
			<li class="active">基本信息</li>
		</ul>
		<form id="inputForm">
			<!-- 基本信息 -->
			<input type="hidden" name="id" value="${id }">
			<div class="formContainer">
				<dl class="clearfix">
					<dt class="float-left">昵称</dt>
					<dd class="float-left">
						<input class="in-txt" sex="${sex}" job="${job}" name="nickname" id="nickname" value="${nickname}" placeholder="添加个性昵称"> 
						<em></em>
					</dd>
				</dl>
				<dl class="clearfix">
					<dt class="float-left">性别</dt>
					<dd class="float-left">
						<div class=""><!-- checked="checked"  -->
							<input type="radio" value="1" name="sex" id="r1"> <span>男</span>
							<input type="radio" value="0" name="sex" id="r2"> <span>女</span>
						</div>
					</dd>
				</dl>
				<dl class="clearfix">
					<dt class="float-left">民族</dt>
					<dd class="float-left">
						<div class="box float-left">
							<input class="in-txt" type="text" name="nation" id="nation" value="${nation}" placeholder="请输入民族"  maxlength="20" >
							<em></em>
						</div>
					</dd>
				</dl>
				<dl class="clearfix">
					<dt class="float-left">籍贯</dt>
					<dd class="float-left">
						<div class="box float-left">
							<input class="in-txt" type="text" name="origo" id="origo" value="${origo}" placeholder="所属籍贯" maxlength="20">
							<em></em>
						</div>
					</dd>
				</dl>
				<dl class="clearfix">
					<dt class="float-left">出生日期</dt>
					<dd class="float-left">
						<div class="box float-left">
							<input class="in-txt dateChange" name="birthday" id="birthday" value="<fmt:formatDate value="${birthday}" pattern="yyyy-MM-dd"/>" placeholder="请选择" required="required">
							<em></em>
						</div>
					</dd>
				</dl>
				<dl class="clearfix">
					<dt class="float-left">QQ号</dt>
					<dd class="float-left">
						<input class="in-txt" type="text" name="qq" id="qq" value="${qq}" data="${id }" placeholder="输入QQ号码" maxlength="13"> <em></em>
						<em></em>
					</dd>
				</dl>
				<dl class="clearfix">
					<dt class="float-left">微信号</dt>
					<dd class="float-left">
						<input class="in-txt" type="text" name="wx" id="wx" value="${wx}" placeholder="输入微信号" maxlength="30"> <em></em>
						<em></em>
					</dd>
				</dl>
				<dl class="clearfix">
					<dt class="float-left">职业</dt>
					<dd class="float-left">
						<div class="box float-left">
							<select name="job" id="job">
								<option value="">请选择</option>
								<option <c:if test="${job == '政府机关'}">selected="selected"</c:if> value="政府机关">政府机关</option>
								<option <c:if test="${job == '互联网'}">selected="selected"</c:if> value="互联网">互联网</option>
								<option <c:if test="${job == '电子商务'}">selected="selected"</c:if> value="电子商务">电子商务</option>
								<option <c:if test="${job == '信息传媒'}">selected="selected"</c:if> value="信息传媒">信息传媒</option>
								<option <c:if test="${job == '通信'}">selected="selected"</c:if> value="通信">通信</option>
								<option <c:if test="${job == '金融'}">selected="selected"</c:if> value="金融">金融</option>
								<option <c:if test="${job == '学生'}">selected="selected"</c:if> value="学生">学生</option>
								<option <c:if test="${job == '其它'}">selected="selected"</c:if> value="其它">其它</option>
							</select>
							<em></em>
						</div>
					</dd>
				</dl>
				<dl class="clearfix">
					<dt class="float-left">工作单位</dt>
					<dd class="float-left">
						<div class="box float-left">
							<input class="in-txt" type="text" name="company" id="company" value="${company}" placeholder="目前就职的单位" maxlength="150">
							<em></em>
						</div>
					</dd>
				</dl>
				<dl class="clearfix">
					<dt class="float-left">手机号码</dt>
					<dd class="float-left">
						<div class="box float-left">
							<input class="in-txt" type="text" name="phone" id="phone" value="${phone}" placeholder="联系电话" maxlength="11">
							<em></em>
						</div>
					</dd>
				</dl>
				<dl class="clearfix" style="display: none;">
					<dt class="float-left">邮箱账号</dt>
					<dd class="float-left">
						<div class="box float-left">
							<input class="in-txt" type="text" name="email" id="email" value="${email}" placeholder="邮箱地址" maxlength="120">
							<em></em>
						</div>
					</dd>
				</dl>
				<dl class="clearfix">
					<dt class="float-left">通讯地址</dt>
					<dd class="float-left">
						<div class="box float-left">
							<input class="in-txt" type="text" name="address" id="address" value="${address}" placeholder="通讯地址" maxlength="150">
							<em></em>
						</div>
					</dd>
				</dl>
				<dl class="clearfix">
					<dt class="float-left">个人简历</dt>
					<dd class="float-left clearfix">
						<textarea class="in-txt" name="resume" id="resume" maxlength="300">${resume}</textarea>
						<em></em>
					</dd>
				</dl>
				<dl class="clearfix">
					<dt class="float-left">从事文艺活动简介</dt>
					<dd class="float-left clearfix">
						<textarea class="in-txt" name="actbrief" id="actbrief" maxlength="300">${actbrief}</textarea>
						<em></em>
					</dd>
				</dl>
				<dl class="clearfix">
					<dt class="float-left">&nbsp;</dt>
					<dd class="float-left">
						<div class="goNext float-left" id="modify">修 改</div>
					</dd>
				</dl>
			</div>
			<!-- 基本信息END -->
		</form>
	</div>
</div>

<!--底部-->
<%@include file="/pages/comm/agdfooter.jsp"%>
<!--底部END-->

<!-- core public JavaScript -->
<script src="${basePath }/static/assets/js/public/formUI.js"></script>
<script src="${basePath }/static/assets/js/plugins/tipso/tipso.js"></script><script src="${basePath }/static/assets/js/public/rong-dialog.js"></script>
<script src="${basePath }/static/assets/js/userCenter/public.js"></script>
<script src="${basePath }/static/assets/js/plugins/laydate.dev.min.js"></script>
<script src="${basePath }/pages/home/center/js/userInfo.js"></script>
<script src="${basePath }/static/assets/js/public/comm.js"></script>
</body>
</html>
