/**
 * 馆务
 * Created by lj on 2016/11/4.
 */
$(function(){
	//选择事件
	$(".page1-title ul li").each(function(idx){
		
		$(this).on('click', function(e){
			//colid
			var colid = $(this).attr('colid');
			
			//加载当前选中的项的样式
			$(this).addClass("active").siblings().removeClass('active');
			//隐藏其他显示层
		   	$('.page-right >.page1>.page1-con> div:eq('+idx+')').show().siblings().hide();
			
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
	$(".page1-title ul li:eq("+idx+")").addClass('active').siblings().removeClass('active');
	
	$('.page-right > .page1 > .page1-con > div:eq('+idx+')').show().siblings().hide();
	
	var colid = $(".page1-title ul li:eq("+idx+")").attr('colid');

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
					showList(dataRows, 	$('.page-right >.page1>.page1-con>div:eq('+idx+')'));
				}else if(idx == 1){
					
					showLista(dataRows, $('.page-right >.page1>.page1-con>div:eq('+idx+')').find('ul'));
				}
				else if(idx == 2){
					showListb(dataRows, $('.page-right >.page1>.page1-con>div:eq('+idx+')').find('ul'));
				}else if(idx == 3 ){
					showListc(dataRows, $('.page-right >.page1>.page1-con>div:eq('+idx+')').find('ul'));
				}else{
					showListd(dataRows, $('.page-right >.page1>.page1-con>div:eq('+idx+')').find('ul'));
				}
			}else {
					showList([]);
				}
				
			//加载分页工具栏
			genPagging('artPagging', page, rows, total, loadData);
		}
	});
}
/**
 * 文化馆
 * @param dataList
 * @returns
 */
function showList(dataRows, el){
	var _html = '';
	for(var i=0; i<dataRows.length; i++){
		_html += '<div class="img" style="background-image: url('+getFullUrl(dataRows[i].clnfbigpic)+')"></div>';
		_html += '<div class="content">';
		_html += '<h1><i></i>文化馆概况：'+dataRows[i].clnftltle+'</h1>';
		_html += '<p>'+dataRows[i].clnfdetail+'</p>';
		_html += '</div>';
	}
	el.html(_html);
}
/**
 * 领导分工
 * @param dataList
 * @returns
 */
function showLista(dataRows, el){
	var _html = '';
	for(var i=0; i<dataRows.length; i++){
		_html += '<li class="clearfix">';
		_html += '<a href="javascript:void(0)"><div class="img" style="background-image: url('+getFullUrl(dataRows[i].clnfpic)+')"><span></span></div></a>';
		_html += '<div class="detail">';
		_html += '<h2>'+dataRows[i].clnftltle+'</h2>';
		_html += '<span>'+dataRows[i].clnfsource+'</span>';
		_html += '<hr>';
		_html += '<p>'+dataRows[i].clnfintroduce+'</p>';
		_html +=  '<p>'+dataRows[i].clnfsource+'</p>';
		_html += '<p>'+dataRows[i].clnfdetail+'</p>';
		_html += '</div>';
		_html += '</li>';
	}
	el.html(_html);
}
/**
 * 部门职能
 * @param dataList
 * @returns
 */
function showListb(dataRows, el){
	var _html = '';
	for(var i=0; i<dataRows.length; i++){
		_html += '	<li class="clearfix">';
		_html += ' <a href="javascript:void(0)"><div class="img" style="background-image: url('+getFullUrl(dataRows[i].clnfpic)+')"></div></a>';
		_html += ' <div class="detail">';
		_html += '  <h2>'+dataRows[i].clnftltle+'</h2>';
		_html += '  <span>部门负责人：'+dataRows[i].clnfsource+'</span>';
		_html += ' <hr>';
		_html += '  <p>'+dataRows[i].clnfintroduce+'</p>';
		_html += ' </div>';
		_html += ' </li>';
	}
	el.html(_html);
}
/**
 * 领导任免
 * @param dataList
 * @returns
 */
function showListc(dataRows, el){
	var _html = '';
	for(var i=0; i<dataRows.length; i++){
		_html += '<li class="clearfix">';
		_html += '<div class="title clearfix">';
		_html += '<a href="'+getFullUrl('/xuanchuan/xqinfo?clnfid='+dataRows[i].clnfid)+'"><h2>'+dataRows[i].clnftltle+'</h2>';
		_html += '	<span>'+new Date(dataRows[i].clnfcrttime).Format("yyyy-MM-dd")+'</span></a>';
		_html += '</div>';
		_html += '<div class="detail">';
		_html += '	来源：<span>'+dataRows[i].clnfsource+'</span>';
		_html += '	</div>';
		_html += '	</li>';
	}
	el.html(_html);
}
/**
 * 公开考录
 * @param dataList
 * @returns
 */
function showListd(dataRows, el){
	var _html = '';
	for(var i=0; i<dataRows.length; i++){
		_html += '<li class="clearfix">';
		_html += '<div class="title clearfix">';
		_html += '<a href="'+getFullUrl('/xuanchuan/xqinfo?clnfid='+dataRows[i].clnfid)+'"><h2>';
		_html += '<i></i>'+dataRows[i].clnftltle+'';
		_html += '</h2>';
		_html += '<span>'+new Date(dataRows[i].clnfcrttime).Format("yyyy-MM-dd")+'</span></a>';
		_html += '</div>';
	}
	el.html(_html);
}

