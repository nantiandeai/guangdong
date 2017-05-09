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
    <title>广东省文化馆-用户后台</title>
    <link href="${basePath }/static/assets/css/public/reset.css" rel="stylesheet">
    <link href="${basePath }/static/assets/css/libraryService/branchSer.css" rel="stylesheet">
    <!--[if lt IE 9] >
    <script src="../assets/js/plugins/ie/IE9.js"></script>
    <! [endif]]-->
    
	<!-- core public JavaScript -->
	<script src="${basePath }/static/assets/js/public/jquery-1.11.0.min.js"></script>
	<script src="${basePath }/static/assets/js/public/jquery-migrate-1.0.0.js"></script>
</head>
<body>

<!-- 公共头部开始 -->
<%@include file="/pages/comm/szheader.jsp"%>
<!-- 公共头部结束 -->

<!--广告开始-->
<div class="art-ad" style="background:url(${basePath }/static/assets/img/libraryService/banne-ser.jpg) no-repeat 50% 50%"></div>
<!--广告结束-->

<!--第O层开始-->
<div class="index-main index-hr">
    <div class="index-row">
        <div class="public-title">
            <h1>分馆资讯</h1>
            <p>RECENT NEWS</p>
            <hr>
        </div>
        <div class="pecent-list clearfix">
            <ul>
                <li class="clearfix">
                    <div class="con-left">
                        <img src="${basePath }/static/assets/img/demoImg/perent-1.jpg">
                    </div>
                    <div class="con-right">
                        <h2><span></span>长安分馆</h2>
                        <p>根据长安镇“一改两区三提升”及新型城镇化工作战略部署要求，为进一步创新长安镇公共文化服务水平和品质，着力提高公共文化服务效能，依托长安“文化综合强”强大文化资源，通过数字文化技术手段及信息化管理，整合镇宣传文体局、长安影像中心、长安体育公园、上沙社区、长安电视台等服务设施倾力打造广东省市具有引领地位的广东省镇街数字文化分馆。</p>
                        <a href="javascript:void(0)">点击进入</a>
                    </div>
                </li>
                <li class="clearfix">
                    <div class="con-left">
                        <img src="${basePath }/static/assets/img/demoImg/perent-2.jpg">
                    </div>
                    <div class="con-right">
                        <h2><span></span>东城分馆</h2>
                        <p>东城展览馆位于东城文化中心剧院后面，交通十分便利。东城展览馆是由原来的舞蹈排练场和羽毛球场改建而成总投资达110多万。展览馆地板采用柚木配大理石，再以柔和适当的白色灯光配中灰色的毛毯展墙，搭配大胆而协调。150平方米的活动展板，可以随意组合想要的展览格局，适合举办各种类型的展览和小型雅集活动</p>
                        <a href="javascript:void(0)">点击进入</a>
                    </div>
                </li>
                <li class="clearfix">
                    <div class="con-left">
                        <img src="${basePath }/static/assets/img/demoImg/perent-3.jpg">
                    </div>
                    <div class="con-right">
                        <h2><span></span>常平分馆</h2>
                        <p>常平文化中心位于镇中心区域，包括文化中心大楼，大剧院及文化广场，文化中心大楼总建筑面积为21312多平方米，建筑总高度近40米，占地面积4174平方米，共十层，地下设有200车位的车库。文化中心大剧院主业务：文艺交流、演出、庆典、礼仪活动策划/会议、展览、演出场所租赁/舞台灯光音响工程。</p>
                            <a href="javascript:void(0)">点击进入</a>
                    </div>
                </li>
                <li class="clearfix">
                    <div class="con-left">
                        <img src="${basePath }/static/assets/img/demoImg/perent-4.jpg">
                    </div>
                    <div class="con-right">
                        <h2><span></span>清溪分馆</h2>
                        <p>清溪文广中心占地面积49600平方米，总建筑面积为36400平方米，包含清溪文化广场、清溪数字文化馆（数字体验馆、信息展示平台、中央课堂、麒麟舞及客家山歌互动）、清溪麒麟博物馆、图书馆等版块，是清溪镇的主要文化阵地。</p>
                        <a href="javascript:void(0)">点击进入</a>
                    </div>
                </li>
                <li class="clearfix">
                    <div class="con-left">
                        <img src="${basePath }/static/assets/img/demoImg/perent-5.jpg">
                    </div>
                    <div class="con-right">
                        <h2><span></span>茶山分馆</h2>
                        <p>根据长安镇“一改两区三提升”及新型城镇化工作战略部署要求，为进一步创新长安镇公共文化服务水平和品质，着力提高公共文化服务效能，依托长安“文化综合强”强大文化资源，通过数字文化技术手段及信息化管理，整合镇宣传文体局、长安影像中心、长安体育公园、上沙社区、长安电视台等服务设施倾力打造广东省市具有引领地位的广东省镇街数字文化分馆。</p>
                        <a href="javascript:void(0)">点击进入</a>
                    </div>
                </li>
                <li class="clearfix">
                    <div class="con-left">
                        <img src="${basePath }/static/assets/img/demoImg/perent-6.jpg">
                    </div>
                    <div class="con-right">
                        <h2><span></span>横沥分馆</h2>
                        <p>根据长安镇“一改两区三提升”及新型城镇化工作战略部署要求，为进一步创新长安镇公共文化服务水平和品质，着力提高公共文化服务效能，依托长安“文化综合强”强大文化资源，通过数字文化技术手段及信息化管理，整合镇宣传文体局、长安影像中心、长安体育公园、上沙社区、长安电视台等服务设施倾力打造广东省市具有引领地位的广东省镇街数字文化分馆。</p>
                        <a href="javascript:void(0)">点击进入</a>
                    </div>
                </li>
            </ul>
        </div>
    </div>
</div>
<!--第O层结束-->

<!--底部开始-->
<%@include file="/pages/comm/szfooter.jsp"%>
<!--底部结束--> 

</body>
</html>