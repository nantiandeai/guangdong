/**
 * 专题转栏
 * Created by lj on 2016/11/1.
 */

$(function(){
	//选择事件
	$(".page1-title ul li").each(function(idx){
		
		$(this).on('click', function(e){
			//colid
			var colid = $(this).attr('colid');
			
			//样式
			$(this).addClass("active").siblings().removeClass('active');
			
			//
			//$('.page-right > .page1 > .page-con > div:eq('+idx+')').show().siblings().hide();
			
			//选择
			loadData(colid, idx);
		});
		
	});
	//绑定事件跳转页面
	//绑定事件跳转页面
	$(".page-left ul li").click(function(){
		var url = $(this).attr("url");
		window.location.href = url;
	});
	
	//第一个元素
	currentLoad(columnIdx);
	
	$('.page-right > .page1').show();
});

function currentLoad(idx){
	//隐藏其他样式
	$(".page1-title ul li:eq("+idx+")").addClass('active').siblings().removeClass('active');
	
	//隐藏其他显示层
	//$('.page-right > .page1 > .page1-con > div:eq('+idx+')').show().siblings().hide();
	
	//子栏目id
	var colid = $(".page1-title ul li:eq("+idx+")").attr('colid');//$(".page1-title ul li").first().attr('colid');

	loadData(colid, idx);
	
}


function loadData(colid, idx){
	//分页加载第一页 
	var page = page || 1;
	//每页10条记录
	var rows = rows || 9;
	//url
	var url = getFullUrl('/xuanchuan/gowhdt?colid='+colid);
	$.ajax({
		type: "POST",
		url: url+'&page='+page+'&rows='+rows,
		success: function(data){
			var dataRows = data.rows;
			var total = data.total || 0;
			//加载页面数据
			if(total > 0){
				showList(dataRows, $('.page-right > .page1 > .page-con > div:gt(0)').find('ul'));
			}else{
				showList([]);
			}
			
			//加载分页工具栏
			genPagging('artPagging', page, rows, total, loadData);
		}
	});
	
}

/**
 * 生成列表数据总馆资讯
 * @param dataList
 * @returns
 */
function showList(dataRows, el){
	var _html = '';
	for(var i=0; i<dataRows.length; i++){
		 _html += ' <li class="clearfix">';
		 _html += ' <a href="'+getFullUrl('/xuanchuan/xqinfo?clnfid='+dataRows[i].clnfid)+'"><div class="left" style="background: url('+getFullUrl(dataRows[i].clnfpic)+')"></div></a>';
		 _html += ' <div class="right">';
		 _html += ' <a href="'+getFullUrl('/xuanchuan/xqinfo?clnfid='+dataRows[i].clnfid)+'"><h3>'+dataRows[i].clnftltle+'</h3></a>';
		 _html += '  <p>'+dataRows[i].clnfintroduce+'</p>';
		 _html += '<div class="time"><span>发布时间：'+new Date(dataRows[i].clnfcrttime).Format("yyyy-MM-dd")+'</span><a href="'+getFullUrl('/xuanchuan/xqinfo?clnfid='+dataRows[i].clnfid)+'">查看更多></a></spqn></div>';
		 _html += '</div>';
		 _html += '</li>';
	}
	el.html(_html);
}




