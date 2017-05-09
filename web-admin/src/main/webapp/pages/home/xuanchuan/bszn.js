/**
 * Created by lj on 2016/11/4.
 */
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
				if (idx == 0) {
					
					showList(dataRows, $('.page-right > .page1 > .page1-con >.page-con> div:gt(0)').find('ul'));
					
				}else{
					showLista(dataRows, $('.page-right > .page1 > .page1-con >.page-con> div:gt(0)').find('ul'));
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
 * 业务指南
 * @param dataList
 * @returns
 */
function showList(dataRows, el){
	var _html = '';
	
	for(var i=0; i<dataRows.length; i++){
		_html += '<li class="active">';
		_html += '<div class="list">';
		_html += '<div class="time"><i></i>'+new Date(dataRows[i].clnfcrttime).Format("yyyy-MM")+'</div>';
		_html += '<a href="'+getFullUrl('/xuanchuan/xqinfo?clnfid='+dataRows[i].clnfid)+'"><h3>'+dataRows[i].clnftltle+'</h3></a>';
		_html += '<p>'+dataRows[i].clnfintroduce+'</p>';
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
		_html += '<li class="active">';
		_html += '<div class="list">';
		_html += '<div class="time"><i></i>'+new Date(dataRows[i].clnfcrttime).Format("yyyy-MM")+'</div>';
		_html += '<a href="'+getFullUrl('/xuanchuan/xqinfo?clnfid='+dataRows[i].clnfid)+'"><h3>'+dataRows[i].clnftltle+'</h3></a>';
		_html += '<p>'+dataRows[i].clnfintroduce+'</p>';
		_html += '</div>';
		_html += '</li>';
	}
	el.html(_html);
}


