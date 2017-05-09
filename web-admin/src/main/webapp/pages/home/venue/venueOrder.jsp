<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>广东省文化馆-分馆</title>
<%@include file="/pages/comm/comm_head.jsp"%>
<%@include file="/pages/comm/baidu_map.jsp"%>

<link href="./static/assets/css/venue/venueOrder.css" rel="stylesheet">
<script type="text/javascript" src="./pages/home/venue/wktool.js"></script>
<script type="text/javascript">
$(function(){
	var venid = "${venue.venid}";
	var lastDayNum = "${lastDayNum}";
	var uid = "${sessionUser.id}";
	wktool.init(venid, lastDayNum, uid);
	
	//重置initWeekList
	var _initWeekList = initWeekList;
	var newCall = function(onToday){
		var today = onToday.Format("yyyy-MM-dd");
		_initWeekList.call(this, onToday);
		wktool.load(today);
	}
	initWeekList = newCall;
	
	$(".orderPay a").on("click", function(){
		wktool.sendBked();
	})
	
	$("[address]").on("click", function(){
		rongDetailDialog("#rongpanel", this);
		var addr = $(this).attr("address");
		MapTool.showMap4addr("showMap", addr );
	})
})
</script>
</head>
<body>

<div id="rongpanel" style="z-index:99999; position:absolute;display: none">
<div style="height:20px; text-align: right;">
<p class="js__p_close">关闭</p>
</div>
<div id="showMap" style="width:600px;height:480px;"></div>
</div>

<!-- 公共头部开始 -->
<%@include file="/pages/comm/szheader.jsp"%>
<!-- 公共头部结束 -->

<!--广告开始-->
<div class="venue-ad" style="background:url(./static/assets/img/venue/bg.jpg) no-repeat 50% 50% #ddd">
	<c:if test="${not empty venueAdv.cfgadvpic }">
		<img alt="" src="${venueAdv.cfgadvpic }" height="250">
	</c:if>
</div>
<!--广告结束-->
<div class="public-crumbs"> <span><a href="${basePath }/">首页</a></span><span>></span><span><a href="./venue/index">场馆预定</a></span><span>></span><span>${venue.venname }</span> </div>
<!--主体开始-->
<div class="venue-main clearfix">
  <div class="venue-img"> <img src="${venue.venpic }" width="420" height="285"> </div>
  <div class="venue-info">
    <h1>${venue.venname }</h1>
    <p>区域 : <span>${venue.venarearef }</span></p>
    <p>类型 : <span>${venue.ventyperef }</span></p>
    <p>电话 : <span>${venue.vencontactnum }</span></p>
    <p>地址 : <span>${venue.venaddr }
    	<c:if test="${not empty venue.venaddr }">
    		<a href="javascript:void(0)" address="${venue.venaddr }">[查看地图]</a>
    	</c:if>
    </span></p>
    <p>用途 : <span>
    	<c:forEach var="item" items="${venscopelist }">
    		<em>${item.name }</em>
    	</c:forEach>
    <!-- <em>比赛</em><em>演唱会</em><em>会议</em> -->
    </span></p>
    
  </div>
</div>
<div class="main clearfix">
  <div class="orderPayCont">
    <div class="dateCont clearfix">
      <div class="wbg"></div>
      <div class="weekCont clearfix">
        <div class="week-btn week-prev"></div>
        <div class="week-groups">
          <ul>
          </ul>
        </div>
        <div class="week-btn week-next"></div>
      </div>
      <div class="dateChange">
        <div  id="dateCont" class="dateTable"></div>
      </div>
    </div>
    <div class="tableListCont">
      <table width="1158">
        <tbody>
        	<c:forEach items="${venueTimes }" var="item">
        		 <tr>
		            <td class="place" width="170" vtid="${item.vtid }">${item.vtstime} - ${item.vtetime }</td>
		            <td class="choose"></td>
		            <td class="choose"></td>
		            <td class="choose"></td>
		            <td class="choose"></td>
		            <td class="choose"></td>
		            <td class="choose"></td>
		            <td class="choose"></td>
		            <td class="last">&nbsp;</td>
		          </tr>
        	</c:forEach>
          <!-- 
          <tr>
            <td class="place">18:00-20:00</td>
            <td class="choosed"><span><i></i>&nbsp;</span></td>
            <td class="choose"><a href="javascript:void(0)">可选</a></td>
            <td class="choose"><a href="javascript:void(0)">可选</a></td>
            <td class="choose">时髦妈妈舞蹈队舞蹈队活动</td>
            <td class="choose"><a href="javascript:void(0)">可选</a></td>
            <td class="choose"><a href="javascript:void(0)">可选</a></td>
            <td class="choose">时髦妈妈舞蹈队舞蹈队活动</td>
            <td class="last">&nbsp;</td>
          </tr> 
          -->
        </tbody>
      </table>
    </div>
    <div class="orderPay"> <a href="javascript:void(0)">立即预订</a> </div>
  </div>
  <div class="main-left">
    <div class="info-cont">
      <h2>场馆描述</h2>
      <div class="info"> 
      ${venue.venintroduce1 }
      </div>
    </div>
    <div class="info-cont">
      <h2>设施描述</h2>
      <div class="info">
      ${venue.venintroduce2 }
      </div>
    </div>
    <div class="info-cont">
      <h2>申请条件</h2>
      <div class="info">
      ${venue.vencondition }
      </div>
    </div>
  </div>
  <div class="main-right">
    <div class="event">
      <h2><i class="h-line"></i>相关动态</h2>
      <ul>
      	<c:forEach items="${srclist }" var="item" end="4">
	        <li> <a href="javascript:void(0)"> <img src="${item.enturl }"> <span>${item.entname }</span> </a> </li>
      	</c:forEach>
        <!-- <li> <a href="javascript:void(0)"> <img src="./static/assets/img/special/s2.jpg"> <span>广东省市合唱节开赛 第二期</span> </a> </li>
        <li> <a href="javascript:void(0)"> <img src="./static/assets/img/special/s3.jpg"> <span>广东省市合唱节开赛 第三期</span> </a> </li>
        <li> <a href="javascript:void(0)"> <img src="./static/assets/img/special/s4.jpg"> <span>广东省市合唱节开赛 第四期</span> </a> </li> -->
      </ul>
    </div>
  </div>
  <div class="main-left">
    <div class="info-cont">
      <h2>我要评论</h2>
      <div class="kcdetail-left">
        <div class="kc-dp">
          <div class="dianp-list">
          	<ul id="pinglunNRC" reftyp="2016101400000053" refid="${venue.venid }">  </ul>
            <!-- <ul>
              <li>
                <div class="dp-content">
                  <div class="xyname"> 文独青年： </div>
                  <div class="pl-neirong"> 已经上了三周课程了，对粤剧有了新的认识，学习到了非常多的传统文化，也明白了粤剧的精髓和魅力所在，我会努力继续的学习跟进下去，老师也非常厉害，在专业。 </div>
                  <div class="pl-shijian"> <span>10小时</span>
                    <div class="huifu"> <a href=""> <span class="xingxi"></span>回复 </a> <a href=""> <span class="xx"></span>25 </a> </div>
                  </div>
                </div>
              </li>
              <li>
                <div class="dp-content">
                  <div class="xyname"> 文独青年： </div>
                  <div class="pl-neirong"> 已经上了三周课程了，对粤剧有了新的认识，学习到了非常多的传统文化，也明白了粤剧的精髓和魅力所在，我会努力继续的学习跟进下去，老师也非常厉害，在专业。 </div>
                  <div class="pl-shijian"> <span>10小时</span>
                    <div class="huifu"> <a href=""> <span class="xingxi"></span>回复 </a> <a href=""> <span class="xx"></span>25 </a> </div>
                  </div>
                </div>
              </li>
              <li>
                <div class="dp-content">
                  <div class="xyname"> 文独青年： </div>
                  <div class="pl-neirong"> 已经上了三周课程了，对粤剧有了新的认识，学习到了非常多的传统文化，也明白了粤剧的精髓和魅力所在，我会努力继续的学习跟进下去，老师也非常厉害，在专业。 </div>
                  <div class="pl-shijian"> <span>10小时</span>
                    <div class="huifu"> <a href=""> <span class="xingxi"></span>回复 </a> <a href=""> <span class="xx"></span>25 </a> </div>
                  </div>
                </div>
              </li>
            </ul> -->
          </div>
          <div class="input-dianp">
            <textarea placeholder="对此场馆进行点评..."></textarea>
            <div class="dianp-xuanx clearfix"> <a href="javascript:void(0);" class="submit-dianp a-button">点 评</a> </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>

<!--主体结束--> 

<!--底部开始-->
<%@include file="/pages/comm/szfooter.jsp"%>
<!--底部结束-->
<!-- core public JavaScript --> 
<script src="./static/assets/js/plugins/laydate.dev.js"></script> 
<script src="./static/assets/js/venue/venueOrder.js"></script>
</body>
</html>
