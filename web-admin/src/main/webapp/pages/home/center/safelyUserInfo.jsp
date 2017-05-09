<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath(); request.setAttribute("basePath", basePath); %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv=”X-UA-Compatible” content=”IE=edge,chrome=1″ />
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="keywords" content="">
<meta name="author" content="">
<title>广东省文化馆-用户中心</title>
<link href="${basePath }/static/assets/css/public/reset.css" rel="stylesheet">
<link href="${basePath }/static/assets/css/userCenter/userCenter.css" rel="stylesheet">
<script src="${basePath }/static/assets/js/public/comm.js"></script>
<!--[if lt IE 9] >
<script src="${basePath }/static/assets/js/plugins/ie/IE9.js"></script> 
<! [endif]]-->
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
    	<ul>
            <!--用户中心导航开始-->
			<%@include file="/pages/comm/ucnav.jsp"%>
			<!--用户中心导航结束-->
        </ul>
    </div>
    <div class="rightPanel">
        <div class="crumbs clearfix">
        	<div class="tt">用户详情资料</div>
            <div class="goBack"><a href="javascript:history.go(-1);"></a></div>
        </div>
        <dl class="clearfix">
          <dt class="float-left">昵称</dt>
          <dd class="float-left">
            <input class="in-txt" placeholder="添加个性昵称">
            <em></em>
          </dd>
        </dl>
        <dl class="clearfix">
          <dt class="float-left">性别</dt>
          <dd class="float-left">
            <div class="radio radio-success">
              <input type="radio"  value="option1" name="radioSingle1" checked id="r1">
              <label for="r1">男</label>
              <input type="radio"  value="option2" name="radioSingle1" id="r2">
              <label for="r2">女</label>
            </div>
          </dd>
        </dl>
        <dl class="clearfix">
          <dt class="float-left">职业</dt>
          <dd class="float-left">
            <div class="box float-left">
                      <select>
                        <option value="">请选择</option>
                          <option value="政府机关">政府机关</option>
                          <option value="互联网">互联网</option>
                          <option value="电子商务">电子商务</option>
                          <option value="信息传媒">信息传媒</option>
                          <option value="通信">通信</option>
                          <option value="金融">金融</option>
                          <option value="学生">学生</option>
                          <option value="其它">其它</option>
                        </select>
            </div>
            <em></em>
          </dd>
        </dl>
        <dl class="clearfix">
          <dt class="float-left">出生日期</dt>
          <dd class="float-left">
            <div class="box float-left">
                <input class="in-txt datePicker" placeholder="请选择">
            </div>
            <em></em>
          </dd>
        </dl>
        <dl class="clearfix">
          <dt class="float-left">QQ号</dt>
          <dd class="float-left">
            <input class="in-txt"  type="txt" placeholder="输入QQ号码">
            <em></em>
          </dd>
        </dl>
        <dl class="clearfix">
          <dt class="float-left">微信号</dt>
          <dd class="float-left">
            <input class="in-txt"  type="txt" placeholder="输入微信号">
            <em></em>
          </dd>
        </dl>
        <dl class="clearfix">
          <dt class="float-left">&nbsp;</dt>
          <dd class="float-left">
            <div class="goNext float-left">修 改</div>
          </dd>
        </dl>
    </div>
</div>

<!--底部开始-->
<%@include file="/pages/comm/agdfooter.jsp"%>
<!--底部结束-->

<div class="md-overlay"></div>

<!-- core public JavaScript --> 
<script src="${basePath }/static/assets/js/public/jquery-1.11.0.min.js"></script> 
<script src="${basePath }/static/assets/js/public/formUI.js"></script> 
<script src="${basePath }/static/assets/js/plugins/tipso.js"></script>
<script src="${basePath }/static/assets/js/public/jquery-migrate-1.0.0.js"></script> 
<script src="${basePath }/static/assets/js/public/rong-dialog.js"></script>
<script src="${basePath }/static/assets/js/userCenter/public.js"></script>
<script src="${basePath }/static/assets/js/userCenter/safely-userInfo.js"></script>
</body>
</html>
