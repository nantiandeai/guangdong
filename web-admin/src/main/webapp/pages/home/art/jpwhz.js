/**
 * 精品文化展js
 */
$(function(){
	//导航
	$('#artMainPageNav>li a').each(function(idx){
		$(this).unbind('click.sz').bind('click.sz', function(e){
			var _class = $(this).parent('li').attr('class');
			if(_class != 'active'){
//				$(this).parents('ul').find('li').removeAttr('class');
//				$(this).parent('li').attr('class', 'active');
//				
//				//加载默认数据...
//				loadArtContent();
			}else{
				e.preventDefault();
			}
		});
	});//导航结束
	
	//艺术分类
	$('#artTypeNav > li > a').each(function(idx){
		$(this).unbind('click.sz').bind('click.sz', function(e){
			e.preventDefault();
			
			var _class = $(this).parent('li').attr('class');
			if(_class != 'active'){
				$(this).parents('ul').find('li').removeAttr('class');
				$(this).parent('li').attr('class', 'active');
				
				//加载默认数据...
				loadArtContent();
			}
			
		});
	});//艺术分类结束
	
	//搜索
	$('#artSearch').unbind('click.sz').bind('click.sz', function(e){
		e.preventDefault();
		var _tVal = $(this).val();
		
	});
	
	//回车事件
	$("body").keydown(function() {
		var isFocus = $("#artSearch input").is(":focus");
		var _val = $("#artSearch input").val();
        if (event.keyCode == "13" && isFocus) {
        	//加载默认数据...
        	loadArtContent();
        }
    });
	
	//加载服务器内容:精品文化展/个人作品展/艺术团队
	loadArtContent();
});


/**
 * 加载艺术广场主内容区的元素:精品文化展/个人作品展/艺术团队
 */
function loadArtContent(page, rows){
	var page = page || 1;//分页加载第一页 
	var rows = rows || 10;//每页10条记录
	
	//导航条件
	var _url = getFullUrl('/art/srchjpwhz?p=1');
	
	//艺术分类
	var arttyp = '';
	$('#artTypeNav > li > a').each(function(idx){
		if($(this).parent('li').hasClass('active')){
			arttyp = $(this).attr('arttyp');
		}
	});
	
	//搜索条件
	var condition = $("#artSearch input").val();
	
	//url
	if(arttyp != ''){
		_url += '&arttyp='+arttyp;
	}
	if(condition != ''){
		_url += '&title='+encodeURIComponent(condition);
	}
	
	//请求服务器数据
	$.ajax({
		type: "POST",
		url: _url+'&page='+page+'&rows='+rows,
		success: function(data){
			var dataRows = data.rows;
			var total = data.total || 0;
			
			//加载页面数据
			if(total > 0){
				showList(dataRows);
			}else{
				showList([]);
			}
			
			//加载分页工具栏
			genPagging('artPagging', page, rows, total, loadArtContent);
		}
	});
}

/**
 * 生成列表数据
 * @param dataList
 * @returns
 */
function showList(dataRows){
	var _html = '';
	for(var i=0; i<dataRows.length; i++){
		_html += '<li class="clearFix">';
		_html += '	<div class="img">';
		_html += '    	<a href="'+getFullUrl('/art/jpwhzlist?id='+dataRows[i].exhid)+'" style="background:url('+getFullUrl(dataRows[i].exhpic)+') no-repeat 50% 50%">';
		_html += '        	<div class="mask"></div>';
		_html += '        </a>';
		_html += '    </div>';
		_html += '    <div class="info">';
		_html += '    	<h2><a href="'+getFullUrl('/art/jpwhzlist?id='+dataRows[i].exhid)+'">'+dataRows[i].exhtitle+'</a></h2>';
		_html += '        <p class="time">时间：'+dateFMT(dataRows[i].exhstime)+' - '+dateFMT(dataRows[i].exhetime)+'</p>';
		_html += '        <p class="desc">'+dataRows[i].exhdesc+'</p>'
		_html += '        <a class="float-right more-btn" href="'+getFullUrl('/art/jpwhzlist?id='+dataRows[i].exhid)+'">查看详情</a>';
		_html += '    </div>';
		_html += '</li>';
	}
	$('#artContent').html(_html);
}