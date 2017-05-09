$.ajaxSetup({
	dataType: "json",
	global: false
});

/** basePath */
if(typeof basePath == "undefined"){
	if($("base").size() > 0){
		var url = $("base").attr("href");
		if(url.substring( url.length-1 ) == "/"){
			url = url.substring(0, url.length-1);
		}
		window.basePath = url;
	}else{
		//获取页面完整地址
		var url = window.location.href;
        var idx = url.length;
        if(url.indexOf("/index") > -1){
        	idx = url.indexOf("/index");
        }else if(url.indexOf("/xuanchuan") > -1){
        	idx = url.indexOf("/xuanchuan");
        }else if(url.indexOf("/art") > -1){
        	idx = url.indexOf("/art");
        }else if(url.indexOf("/event") > -1){
        	idx = url.indexOf("/event");
        }else if(url.indexOf("/gypx") > -1){
        	idx = url.indexOf("/gypx");
        }else if(url.indexOf("/zhiyuan") > -1){
        	idx = url.indexOf("/zhiyuan");
        }else if(url.indexOf("/fenguan") > -1){
        	idx = url.indexOf("/fenguan");
        }else if(url.indexOf("/center") > -1){
        	idx = url.indexOf("/center");
        }
        
		window.basePath = url.substring(0,idx);
	}
}

/** 设置全局变量和函数，用于获取正常的请求URL */
function getFullUrl(url){
	if(url && url.length > 1){
		if(url.substring(0, 2) == "./"){
			url = url.substring(1);
		}else if(url.substring(0, 1) != "/"){
			url = "/"+url;
		}
	}
	return basePath+url;
}

/**
 * 生成分页工具栏
 * @param divID 分页工具栏的父元素
 * @param page 当前页
 * @param pageSize 每页大小
 * @param total 总行数
 */
function genPagging(divID, page, pageSize, total, _Fun){
	if(total > 0 && total > pageSize){//大于一页
		var totalPage = Math.ceil(total / pageSize);//总页数
		
		var _html = '';
		
		//前5页
		var _lastCnt = 0;
		var _prePage = parseInt(page);
		for(var i=1; i<6; i++){
			_prePage = page-i;
			if(_prePage > 0){
				var __url = _prePage;//_url+'&page='+_prePage;
				_html = '<a href="'+__url+'">'+_prePage+'</a>'+_html;
			}else{
				_lastCnt = 5+1-i;
				break;
			}
		}
		if(_prePage > 1){
			_html = '...'+_html;
		}
		if(page == 1){
			//var __url = page-1;//_url+'&page=1';
			//_html = '<span class="pre" page="'+__url+'"></span>'+_html;
		}else{
			var __url = parseInt(page)-1;//_url+'&page=1';
			_html = '<span class="disabled prev" href="'+__url+'">&nbsp;</span>'+_html;
		}
		
		//当前页
		_html += '<span class="current">'+page+'</span>';
		
		//后5页
		var len = 6+_lastCnt;
		
		
		var nextPage = parseInt(page);
		for(var i=1; i<len; i++){
			nextPage = parseInt(page)+i;
			if(nextPage <= totalPage){
				var __url = nextPage;//_url+'&page='+nextPage;
				_html += '<a href="'+__url+'">'+nextPage+'</a>';
			}
		}
		if(nextPage < totalPage){
			_html += '...';
		}
		if(page == totalPage){
			//_html += '<span class="next" page="'+__url+'">&nbsp;</span>';
		}else{
			var __url = parseInt(page)+1;//_url+'&page='+totalPage;
			_html += '<span class="disabled next" href="'+__url+'">&nbsp;</span>';
		}
		
		//输出到界面
		$('#'+divID).html(_html);
		
		//注册事件
		$('#'+divID+' a').bind('click', function(e){
			e.preventDefault();
			var curpage = $(this).attr('href');
			if($.isFunction(_Fun)){
				_Fun(curpage, pageSize);
			}
		});
		$('#'+divID+' span.disabled').bind('click', function(e){
			e.preventDefault();
			var curpage = $(this).attr('href');
			if($.isFunction(_Fun)){
				_Fun(curpage, pageSize);
			}
		});
	}else{
		$('#'+divID).html('');
	}
}

/** 加载点评内容 */
function loadComment(ulId){
	//实体类型/实体id
	var reftyp = $('#'+ulId).attr('reftyp');
	var refid = $('#'+ulId).attr('refid');
	
	//请求后台数据
	$.ajax({
		type: "POST",
		url: getFullUrl('/srchcomment?reftyp='+reftyp+'&refid='+refid),
		success: function(data){
			var _html = '';
			
			if($.isArray(data)){
				$.each(data, function(i, d){
					_html += '<li>';
	                _html += '    <div class="xylogo"> <span></span> </div>';
	                _html += '    <div class="dp-content">';
	                _html += '        <div class="xyname">'+(d.nickname || '')+'</div>';
	                _html += '        <div class="pl-neirong">'+d.rmcontent+'</div>';
	                _html += '        <div class="pl-shijian">';
	                _html += '            <span>'+new Date(d.rmdate).Format('yyyy-MM-dd hh:mm:ss')+'</span>';
	                _html += '            <div class="huifu">';
	                //_html += '                <a href=""> <span class="xingxi"></span>回复 </a>';
	                //_html += '                <a href=""> <span class="xx"></span>25 </a>';
	                _html += '            </div>';
	                _html += '        </div>';
	                _html += '    </div>';
	                _html += '</li>';
				});
			}
			
			$('#'+ulId).html(_html);
		}
	});
}


/**
 对Date的扩展，将 Date 转化为指定格式的String
 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符， 
 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) 
 例子： 
 (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423 
 (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18 
 */
Date.prototype.Format = function (fmt) { //author: meizz 
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "h+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}



/**
 * 日期格式化
 * @param val 单元格的值
 * @param rowData 表格行数据
 * @param index 表格行索引
 * @returns 日期格式化
 */
function dateFMT(val, rowData, index){
	if(!val) return val;
	return new Date(val).Format("yyyy-MM-dd");
}

/**
 * 时间格式化
 * @param val 单元格的值
 * @param rowData 表格行数据
 * @param index 表格行索引
 * @returns 时间格式化
 */
function datetimeFMT(val, rowData, index){
	if(!val) return val;
	return new Date(val).Format("yyyy-MM-dd hh:mm:ss");
}

/**
 * WHComm对象提供一些公共的方法
 */
var WHComm = (function($){
	return {
		/**
		 * 计算两个日期的天数
		 * sDate: 开始日期
		 * eDate: 结束日期
		 */
		d_getDay: function(sDate, eDate){
			return parseInt((Math.abs(eDate - sDate)) / 86400000);
		}
		
		/**
		 * 结束日期是否大于开始日期:true:是, false:否.
		 * sDate: 开始日期
		 * eDate: 结束日期 
		 */
		,d_eGTs: function(sDate, eDate){
			return eDate>sDate;
		}
		
		
	};
})($);


/**
 * 获取系统所有类型定义
 * @returns
 */
var WHTYP = (function(){
	var _inited = false;//是否初始化
	var _alltyp = [];
	var _typData = [];// {type1:[wh_typ1, wh_typ2]; type2:[wh_typ1, wh_typ2]}
	
	/** 根据类型和标识取类型说明 */
	function _sys_Whtyp(type, typid){
		if(!typid){
			return '';
		}
		
		if(!_inited){
			_init();
		}
		var __key = typid;
		
		if(type && type=='zxcolumn'){
			__key = type+'_'+__key;
		}
		return _alltyp[__key] || __key;
	}
	
	function parseChild(_children, map){
		
		for(var i=0; i<_children.length; i++){
			var row = _children[i];
			var _key = row.typid;
			var _val = row.typname;
			var _type = row.type+'_'+_key;
			var __children = row.children;
			
			if(__children && $.isArray(__children)){
				parseChild(__children, map);
			}
			
			map[_key] = _val;
			map[_type] = _val;
		}
	}
	
	/** 查所有类型 */
	function _init(){
		_inited = true;
		$.ajax({
			async: false,
			url: getFullUrl("/comm/whtyp"),
			success: function(data){
				if($.isArray(data)){
					var _map = {};
					for(var i=0; i<data.length; i++){
						var row = data[i];
						var _key = row.typid;
						var _val = row.typname;
						var _type = row.type+'_'+_key;
						var _children = row.children;
						if(_children && $.isArray(_children)){
							parseChild(_children, _map);
						}
						
						_map[_key] = _val;
						_map[_type] = _val;
						
						var _type = row.type;
						if(_type in _typData){
							_typData[_type].push(row);
						}else{
							_typData[_type] = [];
							_typData[_type].push(row);
						}
					}
					_alltyp = _map;
				}
			}
		});
	}
	
	/** 查所type指定的类型 */
	function _getTypeData(type){
		if(!_inited){
			_init();
		}
		return _typData[type];
	}
	
	return {
		sys_Whtyp: function(type, typid){
			return _sys_Whtyp(type, typid);
		},
		sys_Whtyp_Data: function(type){
			return _getTypeData(type);
		}
	};
})();

/** 弹出层 */
function positionCenter(className){
	className.css({
        position: 'fixed',
        left: ($(window).width() - className.outerWidth()) / 2,
        top: ($(window).height() - className.outerHeight()) / 2,
        zIndex: 16777271
    })
}
function wxlDialog(option,event) {
	$("div").remove(".r_dialog");
	$("div").remove(".r_mask");
	$("body").prepend("<div class=\"r_dialog\"><i></i><p></p></div><div class=\"r_mask\"></div>");
    var ico = option['type'] == true ? "s": "e";
    var title = option['title'];
	var time = option['time'] ? option['time'] : 1000;
	var showTime = option['showTime'] ? option['showTime'] : 300;
    var dialog = $('.r_dialog');
	var mask = $('.r_mask');
	var url = option['url'];
	positionCenter(dialog);
	dialog.children("i").addClass(ico);
	dialog.children("p").text(title);
    dialog.fadeIn(showTime);
	mask.on('click',function(){
		mask.fadeOut(0);
		dialog.fadeOut(showTime,function(){
			if(url){
				window.location.href = url;
			}
		});
		
	});
	setTimeout(function(){
		mask.fadeOut(0);
		dialog.fadeOut(showTime,function(){
			if(url){
				window.location.href = url;
			}
		});
	},time);
}

/**
 * 页面加载完成后处理
 */
$(function(){
	/**
	 * 判断是否点亮收藏 (收藏是要登录的)
	 */
	//1.判断是否有收藏按钮
	if($("#collection").length>0){
		//2.取必要属性
		var reftyp = $("#collection").attr("reftyp");
		var refid = $("#collection").attr("refid");
		//3.ajax判断是否有点亮 server()
        $.ajax({
        	type : "POST",
        	url : getFullUrl('./comm/isLightenColle'),
        	data : {reftyp : reftyp, refid : refid},
        	success : function(data){
        		//3.1判断收藏状态及是否登录
        	    if(data.success == "2"){
        	    	//未登录
        		}else if(data.success == "0"){
        			//未收藏（设置变暗样式）
        			$("#collection").removeClass("shoucangclick").addClass("shoucang");
        			$("#collection").parents("div.public-fav").removeClass("fav-active");
        		}else if(data.success == "1"){
        			//已收藏（设置点亮样式）	
        			$("#collection").removeClass("shoucang").addClass("shoucangclick");
        			$("#collection").parents("div.public-fav").addClass("fav-active");
        		}else{
        			//alert("点亮收藏失败");
        		}
        	}
        })
	}
	
	/**
	 * 收藏
	 */
	//0.收藏按钮设置点击事件
	$("#collection").unbind('click.dzl').bind('click.dzl',function(e){
		e.preventDefault();
		//1.取必要属性(包含是否已收藏) //reftyp refid iscolle
		var reftype = $("#collection").attr("reftyp");
		var refid = $("#collection").attr("refid");
	    var isColle = $("#collection").hasClass('shoucangclick');
	    var refurl = window.location.href;
	    var reftitle = document.title;
	    
	    //判断是否已点亮收藏
	    if(isColle){
			//2.1已点亮：  2-没sess; 1-success; 0-fial
			$.ajax({
				type : "POST",
				url : getFullUrl('./comm/removeColle'), // 删除收藏
				data : {reftyp : reftyp, refid : refid},
				success : function(data){
					if(data.success == "2"){
						 //未登录
						//alert("请登录");
					    window.location.href = basePath+'/login?preurl='+encodeURIComponent(window.location.href); 
					}else if(data.success == "1"){
						//alert("取消收藏成功");
						//置灰收藏按钮
						$("#collection").removeClass("shoucangclick").addClass("shoucang");
						$("#collection").parents("div.public-fav").removeClass("fav-active");
					}else if(data.success == "0"){
						//alert("取消收藏失败");
					}else{
						//alert("3:删除操作失败");
					}
				 }
			});
	    }else{
			//2.2未点亮:   2-没sess; 1-success; 0-fial
			$.ajax({
			    type : "POST",
			    url : getFullUrl('./comm/addColle'), // 添加收藏
			    data : {cmreftyp : reftyp, cmrefid : refid, cmurl:refurl, cmtitle:reftitle},
			    success : function(data){
			    	if(data.success == "2"){
			    		//未登录
			    		//alert("请登录");
			    	    window.location.href = basePath+'/login?preurl='+encodeURIComponent(window.location.href); 
			    	}else if(data.success == "1"){
			    		//alert("收藏成功");
			    		//并点亮收藏样式
			    		$("#collection").removeClass("shoucang").addClass("shoucangclick");
			    		$("#collection").parents("div.public-fav").addClass("fav-active");
			    	}else if(data.success == "0"){
			    		//收藏失败
			    		//alert("收藏失败");
				    }else{
				    	//alert("3:添加收藏操作失败");
				    }
			    }
			});
		}
	})


	/**
	 * 点亮点赞（不需要登录）
	 */
	//判断是否有点赞按钮
	if($("#good").length>0){
		//取其必要属性
		var reftyp = $("#good").attr("reftyp");
		var refid = $("#good").attr("refid");
		//判断是否点亮
		$.ajax({
			type : "POST",
			url : getFullUrl('./comm/isLightenGood'),	
			data : {reftyp : reftyp, refid : refid},
			success : function(data){
				$("#good").prev("em").text(data.num||0);
				//console.log("dianzan===>"+data.num);
				if(data.success == "0"){
					//未点赞（置灰点赞样式）
        			$("#good").removeClass("dianzanclick").addClass("dianzan");
				}else if(data.success == "1"){
					//已点赞（点亮点赞样式）
        			$("#good").removeClass("dianzan").addClass("dianzanclick");
				}
			}
			
		});
		
	}
	
	/**
	 * 点赞
	 */
	//点赞按钮点击事件
	$("#good").unbind('click.dzl').bind('click.dzl',function(e){
		e.preventDefault();
		//获取属性  reftyp refid isGood
		var reftyp = $("#good").attr("reftyp");
		var refid = $("#good").attr("refid");
		var isGood = $("#good").hasClass("dianzanclick");
		//判断是否已点赞
		if(!isGood){
			//未点赞
			$.ajax({
				type : "POST",
				url : getFullUrl('./comm/addGood'),	//添加点赞记录
				data: {cmreftyp : reftyp, cmrefid : refid},
				success : function(data){
					if(data.success == "0"){
						//alert("点赞成功");
	        			$("#good").removeClass("dianzan").addClass("dianzanclick");
	        			$("#good").prev("em").text(data.num||0);
					}else{
						//alert("点赞失败");
					}
				}
			});
		}
	});
	
	/**
	 * 加载点评内容  
	 * UL的ID必须为pinglunNRC
	 * UL的reftyp为培训/活动/艺术作品的类型
	 * UL的refid为培训/活动/艺术作品的ID
	 */
	if($('#pinglunNRC').size() == 1){
		loadComment('pinglunNRC');
		
		
		/** 提交点评 */
		$('.submit-dianp').unbind('click.wxl').bind('click.wxl', function(e){
			e.preventDefault();
			
			//点评/实体类型/实体id
			var contentEl = $(this).parents('.input-dianp').find('textarea');
			var content = contentEl.val();
			var reftyp = $('#pinglunNRC').attr('reftyp');
			var refid = $('#pinglunNRC').attr('refid');
			var rmurl = window.location.href;
			var rmtitle = document.title;
			
			if(content == ''){
				wxlDialog({
					title: '请输入点评内容',
					type: false
				});
			}
		
			//保存
			if(content && reftyp && refid){
				$.ajax({
					type: "POST",
					url: basePath+'/addcomment',
					data: {'rmcontent':content, 'rmtitle':rmtitle, 'rmurl':rmurl, 'rmreftyp':reftyp, 'rmrefid':refid, 'rmtyp':'0', 'rmstate':'1'},
					success: function(data){
						if(data.success == '0'){
							//成功，重新加载点评
							loadComment('pinglunNRC');
							contentEl.val('');
						}else if(data.success == '1'){
							//失败
						}else if(data.success == '2'){
							//未登录，先登录, 弹出登录 会话框
							window.location.href = basePath+'/login?preurl='+encodeURIComponent(window.location.href); 
						}
					}
				});
			}
		});
	}
	
	
	/**
	 * 分享微博 -直接使用连接，需要用到appkey
	 * http://service.weibo.com/share/share.php?title=&url=&source=bookmark&appkey=2992571369&pic=&ralateUid=
	 * 
	 * 分享到QQ空间
	 * http://sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_onekey?url=&title=&pics=&summary=
	 */
	$('a.fxweibo').each(function(i){
		var _url = escape(window.location.href);
		//var _url = encodeURIComponent('http://hn.creatoo.cn/');
		var _title = document.title;
		$(this).removeClass('wxldisabled').attr('target', '_blank').attr('href', 'http://service.weibo.com/share/share.php?title='+_title+'&url='+_url+'&source=bookmark&appkey=2992571369&pic=&ralateUid=');
	});
	$('a.fxqq').each(function(i){
		var _url = encodeURIComponent(window.location.href);
		//var _url = encodeURIComponent('http://hn.creatoo.cn/');
		var _title = document.title;
		$(this).removeClass('wxldisabled').attr('target', '_blank').attr('href', 'http://sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_onekey?url='+_url+'&title='+_title+'&pics=&summary=');
	});
//	if($('a.fxweibo').size() > 0 ){
//		$.getScript('http://v2.jiathis.com/code/jia.js', function(){
//			$('a.fxweibo').each(function(i){
//				$(this).attr('class', 'jiathis_button_tsina '+$(this).attr('class'));
//			});
//			$('a.fxqq').each(function(i){
//				$(this).attr('class', 'jiathis_button_qzone '+$(this).attr('class'));
//			});
//		});
//	}
	
	/**
	 * 分享到微信朋友圈
	 */
	if($('a.fxweix').size() > 0 ){
		$.getScript('http://v3.jiathis.com/code/jia.js?uid=1', function(){
			$('a.fxweix').each(function(i){
				$(this).attr('class', 'jiathis_button_weixin '+$(this).attr('class'));
			});
		});
	}
});



/** 显示默认的图片 */
function showDefaultIMG(obj, src){
	$(obj).attr('src', src);
}
