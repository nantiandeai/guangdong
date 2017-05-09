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
	var _url = getFullUrl('/art/srchszzy?p=1');
	
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
		_url += '&drscarttyp='+arttyp;
	}
	if(condition != ''){
		_url += '&drsctitle='+encodeURIComponent(condition);
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
	//<a href="javascript:void(0)" class="js__p_vedio_start opt_videoshow" vsurl="${item.enturl}">
	for(var i=0; i<dataRows.length; i++){
        _html += '<li>';
	    _html += '    <a href="javascript:void(0);" class="opt_videoshow" vsurl="'+getFullUrl(dataRows[i].drscpath)+'" style="background-image:url('+getFullUrl(dataRows[i].drscpic)+')">';
	    _html += '        <div class="mask">';
	    _html += '            <img src="'+getFullUrl('/static/assets/img/art/video.png')+'">';
	    _html += '        </div>';
	    _html += '    </a>';
	    _html += '    <a href="javascript:void(0);">';
	    _html += '        <div class="detail">';
	    _html += '            <h2>'+dataRows[i].drsctitle+'</h2>';
	    _html += '            <span>来源：'+dataRows[i].drscfrom+'</span>';
	    _html += '        </div>';
	    _html += '    </a>';
	    _html += '</li>';
	}
	$('#artContent').html(_html);
}