<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<%@include file="/pages/comm/agdhead.jsp"%>
<link href="${basePath }/static/assets/css/museum/museum.css" rel="stylesheet">
<title>广东省文化馆-馆务公开-意见反馈</title>
<script type="text/javascript" src="${basePath }/pages/home/agdgwgk/fankuai.js"></script>
</head>
<!-- 公共头部开始 -->
<%@include file="/pages/comm/agdtopsmall.jsp"%>
<!-- 公共头部结束-END -->

<!-- 二级栏目 -->
<div id="header-fix">
    <div class="header-nav-bg">
        <div class="header-nav">
            <div class="logo-small">
                <a href="${basePath }/home"><img src="${basePath }/static/assets/img/public/logoSmall.png"></a>
            </div>
            <ul>
                <li><a href="${basePath }/agdgwgk/index">省馆介绍</a></li>
                <li><a href="${basePath }/agdgwgk/jigou">组织机构</a></li>
                <li><a href="${basePath }/agdgwgk/fagui">政策法规</a></li>
                <li><a href="${basePath }/agdgwgk/zhinan">业务指南</a></li>
                <li><a href="${basePath }/agdgwgk/tuandui">馆办团队</a></li>
                <li class="last active"><a href="${basePath }/agdgwgk/fankui">意见反馈</a></li>
            </ul>
        </div>
    </div>
</div>
<!-- 二级栏目-END -->

<!--主体开始-->
<div class="main-info-bg main-info-no-padding main-info-bgColorW">

    <div class="main-info-container">
        <div class="page-banner page-banner-yijian">
            <img src="${basePath }/static/assets/img/museum/yijianfankui.jpg">
        </div>
        <div class="page-con page5-con4">
         <form method="post" id="ff">
            <div class="form-table">
                <ul>
                    <li class="clearfix">
                        <div class="wenzi">意见反馈</div>
                        <div class="input1">
                            <textarea type="text" name="feeddesc" id="feeddesc" placeholder="留下您的意见 我们提供更好的服务..." maxlength="360"></textarea>
                        </div>
                    </li>
                    <li class="clearfix">
                        <div class="wenzi">姓名</div>
                        <div class="input1">
                            <input type="text" name="feedname" placeholder="请输入联系人姓名" maxlength="20">
                        </div>
                    </li>
                    <li class="clearfix">
                        <div class="wenzi">单位</div>
                        <div class="input1">
                            <input type="text"name="feedcom" placeholder="请输入贵公司联系地址" maxlength="40" >
                        </div>
                    </li>
                    <li class="clearfix">
                        <div class="wenzi">联系电话</div>
                        <div class="input1">
                            <input type="text" name="feedphone" placeholder="请输入联系人电话" maxlength="11">
                        </div>
                    </li>
                    <li class="clearfix">
                        <div class="wenzi">电子邮箱</div>
                        <div class="input1">
                            <input type="text" name="feedmail" placeholder="请输入正确的邮箱地址" maxlength="30">
                        </div>
                    </li>
                    <li class="clearfix">
                        <div class="wenzi">验证码</div>
                        <div class="input1">
                            <input class="vfcode" name="feedyanzhen" type="text" placeholder="请输入验证码">
                            <span><img id="yanzhen" src="${basePath }/authImage?t=1" alt="验证码" ></span>
                        </div>
                    </li>
                    <li class="clearfix">
                        <div class="wenzi"></div>
                        <div class="input1">
                            <input class="btn" type="button" value="提交" onclick="addyj();">
                        </div>
                    </li>

                </ul>
            </div>
          </form>
        </div>
    </div>
</div>
<!--主体结束-->

<!--公共主底部开始-->
<%@include file="/pages/comm/agdfooter.jsp"%>
<!--公共主底部结束-END-->
<body>
</body>
</html>