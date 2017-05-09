<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<%@include file="/pages/comm/agdhead.jsp"%>
<title>广东省文化馆</title>
	<link href="${basePath }/static/assets/css/index/index.css" rel="stylesheet">
	<link href="${basePath }/static/assets/css/index/search.css" rel="stylesheet">
	<script src="${basePath }/static/assets/js/index/index.js" type="text/javascript"></script>
	<script src="${basePath }/static/assets/js/plugins/roll/jquery.sly.min.js" type="text/javascript"></script>
	<script src="${basePath }/static/assets/js/index/search.js" type="text/javascript"></script>
	<script type="text/javascript">
		function srchContent(_srchKey, page, rows){
			var page = page || 1;//分页加载第一页
			var rows = rows || 10;//每页10条记录

			//搜索条件
			var srchkey = _srchKey || "";
			$(".search-info h1 span:first").text(srchkey);

			if(srchkey){
				//请求服务器数据
				$.ajax({
					type: "POST",
					url: getFullUrl('/globalsrchcontent'),
					data: {page:page, rows:rows, srchkey:srchkey},
					success: function(data){
						var dataRows = data.rows;
						var total = data.total || 0;

						//设置查询到条数
						$(".search-info h1 span:eq(1)").text(total);

						//加载页面数据
						showList(dataRows);

						//加载分页工具栏
						genPagging('artPagging', page, rows, total, function(page, rows){
							srchContent(srchkey, page, rows);
						});
					}
				});
			}else{
				showList([]);
			}
		}

		/**
		 * 生成列表数据
		 * @param dataList
		 * @returns
		 */
		function showList(dataRows){
			var srchkey = $(".search-info h1 span:first").text();
			var reger = new RegExp('(('+srchkey+')+)',"gm");

			var _html = '';
			if($.isArray(dataRows) && dataRows.length > 0){
				for(var i=0; i<dataRows.length; i++){
					var _href = getUrl(dataRows[i].id, dataRows[i].type);
					var _title = dataRows[i].title;
					_title = _title.replace(reger, '<span>$1</span>');
					var _type = dataRows[i].srchtype;

					_html += '<li><a href="'+getFullUrl(_href)+'">'+_title+'</a><em>- '+_type+'</em></li>';
				}
				$('#srchContent').show();
				$('.public-no-message').hide();
			}else{
				$('#srchContent').hide();
				$('.public-no-message').show();
			}
			$('#srchContent').html(_html);
		}

		function getUrl(id, type){
			var url = '';
			if(type && type == '1'){//活动
				url = '/agdwhhd/activityinfo?actvid='+id;
			}else if(type && type == '2'){//培训
				url = '/agdpxyz/traininfo?traid='+id;
			}else if(type && type == '3'){//场馆
				url = '/agdcgfw/venueinfo?venid='+id;
			}else if(type && type == '4'){//活动资讯
				url = '/agdwhhd/newsinfo?id='+id;
			}else if(type && type == '5'){//文化品牌
				url = '/agdwhhd/brandinfo?braid='+id;
			}else if(type && type == '6'){//文化馆
				url = '/agdwhlm/info/'+id;
			}else if(type && type == '7'){//资讯公告
				url = '/agdzxdt/noticeinfo?id='+id;
			}else if(type && type == '8'){//资讯工作动态
				url = '/agdzxdt/workinginfo?id='+id;
			}else if(type && type == '9'){//培训公告
				url = '/agdpxyz/noticeinfo?id='+id;
			}else if(type && type == '10'){//培训资讯
				url = '/agdpxyz/newsinfo?id='+id;
			}else if(type && type == '11'){//非遗公告
				url = '/agdfyzg/noticeinfo?id='+id;
			}else if(type && type == '12'){//志愿资讯
				url = '/agdzyfw/newsinfo?id='+id;
			}else if(type && type == '13'){//在线点播
				url = '/agdpxyz/vodinfo?drscid='+id;
			}
			return url;
		}

		$(function(){

			//搜索框处理
			$('.search-bg .search-cont .search-button').unbind('click.sz').bind('click.sz', function(e){
				e.preventDefault();
				var srchkey = $('.search-bg .search-cont .search-input').val();
				if(srchkey){
					srchContent(srchkey);
				}
			});

			//回车事件
			$('.search-bg .search-cont .search-input').off("keydown").on("keydown", function (event) {
				if (event.keyCode==13){
					$(this).siblings(".search-button").click();
				}
			})

			//分页查询
			var srchkey = $(".search-bg .search-input").val();
			srchContent(srchkey);
		});
	</script>
</head>
<body>

<!-- 公共头部开始 -->
<%@include file="/pages/comm/agdtop.jsp"%>
<!-- 公共头部结束-END -->

<div class="search-bg">
	<div class="search-border">
		<div class="search-cont search-mask">
			<input class="search-input" value="${srchkey}" placeholder="搜索你喜欢的">
			<button class="search-button"></button>
		</div>
	</div>
</div>

<div class="main-info-bg main-info-bgColorW">
	<div class="main-info-container search-info">
		<h1>平台为您找到相关“<span> </span>”结果约 <span>0</span> 个</h1>
		<ul id="srchContent">
		</ul>

		<!-- 无搜索内容显示层 -->
		<div class="public-no-message none"></div>
		<!-- 无搜索内容显示层-END -->

		<div class="green-black" id="artPagging">
		</div>
	</div>
</div>

<!--公共主底部开始-->
<%@include file="/pages/comm/agdfooter.jsp"%>
<!--公共主底部结束-END-->
</body>
</html>