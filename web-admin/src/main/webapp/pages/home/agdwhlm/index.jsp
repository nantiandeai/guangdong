<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <%@ include file="/pages/comm/agdhead.jsp" %>
  <title>广东省文化馆-场馆服务-地方分馆</title>
  <link href="${basePath }/static/assets/css/union/unionIndex.css" rel="stylesheet">
  <script type="text/javascript" src="${basePath }/static/assets/js/union/unionIndex.js" charset="UTF-8"></script>
  <script type="text/javascript">
      /** page onload event */
      $(function(){
          var areaid = '${param.area}';

          //加载分页工具栏
          genPagging('whgPagging', ${page}, ${pageSize}, ${total}, function(page, rows){
              if(areaid != ''){
                window.location.href = '${basePath}/agdwhlm/index?page='+page+'&rows='+rows+"&area=${param.area}";
              }else{
                window.location.href = '${basePath}/agdwhlm/index?page='+page+'&rows='+rows;
              }
          });

          //区域样式
          if(areaid == ''){
              $('.adrList > span:eq(0)').addClass("active");
          }else{
              $('.adrList a').each(function (idx) {
                  if( $(this).attr("areaid") == areaid ){
                      $(this).parent("span").addClass("active");
                  }
              });
          }

          //区域注册事件
          $('.adrList a').on('click', function (event) {
              event.preventDefault();
              var areaid = $(this).attr("areaid");
              if(areaid != ''){
                  window.location.href = '${basePath}/agdwhlm/index?area='+areaid;
              }else{
                  window.location.href = '${basePath}/agdwhlm/index';
              }
          })
      });
  </script>
</head>
<body>
<!-- 头部开始 -->
<%@ include file="/pages/comm/agdtopsmall.jsp" %>
<!-- 头部结束-END -->

<!--公共主头部开始-->
<div id="header-fix">
  <div class="header-nav-bg">
    <div class="header-nav">
      <div class="logo-small">
        <a href="${basePath }/home"><img src="${basePath }/static/assets/img/public/logoSmall.png"></a>
      </div>
      <ul>
        <li class="active last"><a href="javascript:void(0)">文化馆联盟</a></li>
      </ul>
    </div>
  </div>
</div>
<!--公共主头部结束-->

<!--主体开始-->
<div id="content">

  <!--  搜索区 -->
  <div class="categoryChange">
    <div class="row clearfix">
      <div class="title">区域</div>
      <div class="adrList">
        <span class="item"><a href="javascript:void(0)" areaid="">全部</a></span>
        <c:if test="${areaList != null && fn:length(areaList) > 0}">
          <c:forEach items="${areaList}" var="row" varStatus="s">
            <span class="item"><a href="javascript:void(0)" areaid="${row.id}">${row.name}</a></span>
          </c:forEach>
        </c:if>
      </div>
    </div>
  </div>
  <!--  搜索区END -->

  <div class="topicslist-list container">
    <div class="con">
      <!-- 无数据 -->
      <c:if test="${cultList == null || fn:length(cultList) < 1}">
        <div class="public-no-message"></div>
      </c:if>
      <!-- 无数据END -->



      <!-- 文化馆数据 -->
      <c:if test="${cultList != null || fn:length(cultList) > 0}">
      <ul class="clearfix">
        <c:forEach items="${cultList}" var="row" varStatus="s">
          <c:choose>
              <c:when test="${not empty row.siteurl}"><c:set var="whgurl" value="${row.siteurl}"/></c:when>
              <c:otherwise><c:set var="whgurl" value="${basePath }/agdwhlm/info/${row.id}"/></c:otherwise>
          </c:choose>
          <li>
              <a href="${whgurl}" target="_blank">
                <div class="img">
                  <img src="${imgServerAddr}${row.picture}" style="width: 300px; height: 190px;">
                  <div class="mask"></div>
                </div>
              </a>
              <div class="detail">
                    <a href="${whgurl}" target="_blank"><h2>${row.name}</h2></a>
                    <p>${row.introduction}</p>
                    <a class="enter-zt" href="${whgurl}" target="_blank">进入文化馆</a>
              </div>
          </li>
        </c:forEach>
      </ul>
      </c:if>
      <!-- 文化馆数据END -->

      <!-- 分页 -->
      <div class="green-black" id="whgPagging"></div>
      <!-- 分页END -->
    </div>
  </div>
</div>
<!--主体结束-->

<!--公共主底部开始-->
<%@ include file="/pages/comm/agdfooter.jsp" %>
<!--公共主底部结束-END-->
</body>
</html>