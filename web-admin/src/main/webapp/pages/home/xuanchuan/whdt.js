
$(function(){
	//选择事件
	$(".page1-title ul li").each(function(idx){
		
		$(this).on('click', function(e){
			//colid
			var colid = $(this).attr('colid');
			//样式
			$(this).addClass("active").siblings().removeClass('active');
			
			
			//隐藏
			$('.page-right > .page1 > .page1-con > div:eq('+idx+')').show().siblings().hide();
			//选择
			loadData(colid, idx);
		});
	});
	
	//绑定事件跳转页面
	$(".page-left ul li").click(function(){
		var url = $(this).attr("url");
		window.location.href = url;
	});
	
	//第一个元素
	currentLoad(columnIdx);
	
	//
	$('.page-right > .page1').show();
});

function currentLoad(idx){
	//隐藏其他样式
	$(".page1-title ul li:eq("+idx+")").addClass('active').siblings().removeClass('active');
	
	//隐藏其他显示层
	$('.page-right > .page1 > .page1-con > div:eq('+idx+')').show().siblings().hide();
	
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
				if (idx != 0) {
					showLista(dataRows, $('.page-right > .page1 > .page1-con > div:eq('+idx+')').find('ul'));
				}else{
					showList(dataRows, $('.page-right > .page1 > .page1-con > div:eq('+idx+')').find('ul'));
				}
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
		_html += '<li class="clearfix">';
		_html += '<div class="time"><span>'+new Date(dataRows[i].clnfcrttime).Format("dd")+'</span><p>'+new Date(dataRows[i].clnfcrttime).Format("yyyy-MM")+'</p></div>';
		_html += '<div class="title">';
		_html += '<a href="'+getFullUrl('/xuanchuan/xqinfo?clnfid='+dataRows[i].clnfid)+'"><h2>'+dataRows[i].clnftltle+'</h2></a>';
		_html += '<div class="detail"> 来源：<span>'+dataRows[i].clnfsource+'</span>&nbsp;&nbsp;&nbsp;&nbsp;作者：<span>'+dataRows[i].clnfauthor+'</span>';
		_html += '</div>';
		_html += '</div>';
		_html += '</li>';
	}
	el.html(_html);
}
/**
 * 生成列表数据分馆资讯
 * @param dataList
 * @returns
 */
function showLista(dataRows, el){
	var _html = '';
	for(var i=0; i<dataRows.length; i++){
	    _html += '<li class="clearfix">';
	    _html += '<div class="title clearfix">';
	    _html += '<a href="'+getFullUrl('/xuanchuan/xqinfo?clnfid='+dataRows[i].clnfid)+'"><h2><i></i>'+dataRows[i].clnftltle+'</h2><span>'+new Date(dataRows[i].clnfcrttime).Format("yyyy-MM-dd")+'</span></a>';
	    _html += '</div>';
	    _html += '</li>';
	}
	el.html(_html);
}


