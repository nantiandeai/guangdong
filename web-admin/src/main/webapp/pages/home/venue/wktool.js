var wktool = (function(){
	//场馆ID
	var venid = "";
	//basepath
	var basePath = $("base").attr("href");
	//一次加多少天之后的预订
	var loadDayNum = 7;
	//当前日期多少天后不可定
	var selectLastDayNum = 10;
	//table
	var wktable = ".tableListCont table";
	//显示天的UL
	var wkdayul = ".week-groups ul";
	
	//当前时间
	var nowDate = new Date().Format("yyyy-MM-dd hh:mm:ss");
	function getNowDate(){
		$.post("./whtools/now", function(data){
			nowDate = data;
		});
		return nowDate;
	}
	//会话ID
	var nowUserId
	function isUser(ck){
		//超时清理
		/*setTimeout( function(){nowUserId = null; isUser()}, 1000*60*10 );
		if (nowUserId) return;*/
		$.post("./whtools/homeUser", function(data){
			//alert("test="+JSON.stringify(data));
			if (data.user){
				nowUserId = data.user.id;
			}
			if (!data.user && ck){ //ck=true, 变为会话验证并跳转
				rongDialog({ type : false, title : "请登录后再进行操作", time : 3*1000, url:basePath+"/login" });
			}
		}, "json")
	}
	
	//取天所对应的要操作的td单元索引
	function getTdIdx4day(day){
		var days = day.split(/\D+/);
		day = days[0]+"-"+parseInt(days[1])+"-"+parseInt(days[2]); //月日单数值处理
		var dayIdx = $("li p:contains('"+day+"')", wkdayul).parents("li").index();
		return (dayIdx !=-1)? dayIdx+1 : dayIdx;
	}
	//当前7天的日期数组值
	function getShowDays(){
		var showDays = new Array();
		$("li p", wkdayul).each(function(){
			var days = $(this).text().split(/\D+/);
			var day = new Date(days[0], days[1]-1, days[2]).Format("yyyy-MM-dd"); //月日双数值处理
			showDays.push( day );
		})
		return showDays;
	}
	//取时间段对应的要操作的tr行索引
	function getTrIdx4time(timeStr){
		var timeIdx = $("tr td.place:contains('"+timeStr+"')", wktable).parents("tr").index();
		return timeIdx;
	}
	
	//初始单元格显示
	var tdA = '<a href="javascript:void(0)">可选</a>';
	var tdSp = '<span><i></i>&nbsp;</span>';
	function initTd(){
		$("tr td:not(.place,.last)", wktable).html(tdA);
		initTd4Date();
	}
	//初始单元格显示不同时间的可选限制
	function initTd4Date(){
		//当前天之前的不可以选
		var now = getNowDate();
		locktd4Time(now);
		nowarr = now.split(/\D+/, 3);
		var nday = nowarr.join("-");
		locktd(nday);
		//若有限制之后天不可选的，之后天不可以选
		if (selectLastDayNum > 0){
			var ldate = new Date(nowarr[0], nowarr[1]-1, nowarr[2]);
			ldate.setDate(ldate.getDate()+ parseInt(selectLastDayNum) );
			var lday = ldate.Format("yyyy-MM-dd");
			locktd(lday, 1);
		}
	}
	//处理当天的单元格时段锁定
	function locktd4Time(now){
		var nowdt = now.split(/\s+/);
		var nowday = nowdt[0].split(/\D+/, 3).join("-");
		var nowtime = nowdt[1].split(/\D+/, 2).join(":");
		tdidx = getTdIdx4day(nowday);
		if (tdidx == -1) return;
		$("tr", wktable).each(function(){
			var trtime = $(this).find("td:eq(0)").text();
			var _stime = trtime.split(" - ")[0];
			if (_stime <= nowtime){
				var tagtd = $(this).find("td:eq("+tdidx+")");
				tagtd.html("-");
				//tagtd.removeClass("choose choosed");
			}
		})
	}
	//按天处理不可选单元格锁定
	function locktd(day, last){
		var showDays = getShowDays();
		for(var i in showDays){
			if (last && day > showDays[i]){
				continue;
			}
			if (!last && day <= showDays[i]){
				continue;
			}
			
			tdidx = getTdIdx4day(showDays[i]);
			if (tdidx == -1) continue;
			$("tr", wktable).find("td:eq("+tdidx+")").each(function(){
				var onlyA = $(this).find("a:only-child");
				if (onlyA.length){
					$(this).html("-");
					//$(this).removeClass("choose choosed");
				}
			})
		}
	}
	
	
	//按预定记录数据取对应单元行列索引
	function _getRowDataIdx(row){
		var vebday = row.vebday;
		var day = vebday.replace(/\D+/, "-");
		var btime = row.vebstime;
		var etime = row.vebetime;
		//定位场馆列索引
		var dayIdx = getTdIdx4day(day);
		//定位场馆行索引
		var timeStr = btime+" - "+etime;
		var timeIdx = getTrIdx4time(timeStr);
		
		return {dayIdx : dayIdx, timeIdx: timeIdx};
	}
	
	//推入有效的已预定信息
	function pushBkedDay(row){
		var idxs = _getRowDataIdx(row);
		var timeIdx = idxs.timeIdx;
		var dayIdx = idxs.dayIdx;
		if (timeIdx == -1 || dayIdx ==-1) return;
		var title = row.tratitle || row.acttitle ||"已被预订";
		$("tr:eq("+timeIdx+")", wktable).find("td:eq("+dayIdx+")").text( title );
	}
	
	//推入用户已定信息为已选定，不可取消选定
	function pushUserBkedDay(row){ 
		var idxs = _getRowDataIdx(row); 
		var timeIdx = idxs.timeIdx;
		var dayIdx = idxs.dayIdx;
		if (timeIdx == -1 || dayIdx ==-1) return;
		
		var tagtd = $("tr:eq("+timeIdx+")", wktable).find("td:eq("+dayIdx+")");
		tagtd.removeClass("choose").addClass("choosed");
		var _tdSp = $(tdSp).addClass("notClick");
		tagtd.html(_tdSp);
	}
	
	//推入已选预定
	function checkSelect(){
		var showDays = getShowDays();
		for(var i in showDays){
			var day = showDays[i];
			tdidx = getTdIdx4day(showDays[i]);
			if (tdidx == -1) continue;
			$("tr", wktable).find("td:eq("+tdidx+")").each(function(){
				var timestr = $(this).siblings(":first").text();
				var qpkey = day+"|"+timestr;
				
				var onlyA = $(this).find("a:only-child");
				if (onlyA.length){
					if (queryParams[qpkey]){
						$(this).removeClass("choose").addClass("choosed");
						$(this).html(tdSp);
					}else{
						$(this).removeClass("choosed").addClass("choose");
					}
				}
			})
		}
	}
	
	//个人未审核的预定提取
	function pushBkedList(today){
		//没有会话ID时不做处理
		if (!nowUserId) return;
		//没有指定天参数，取第一个显示列表的天
		var day = today? today : getShowDays()[0];
		var params = new Object();
	 	params.venid = venid;
	 	params.uid = nowUserId;
	 	params.daynum = loadDayNum;
	 	params.bday = day;
	 	$.post("./venue/order/bkednock", params, function(data){
	 		if (data){
	 			for(var i in data){
	 				pushUserBkedDay(data[i]);
	 			}
	 		}
	 	}, "json");
	}
	
	//加载指定时间七天之内的场馆预订数据
	function load(today){
	 	var params = new Object();
	 	params.venid = venid;
	 	params.daynum = loadDayNum;
	 	params.bday = today;
		$.post("./venue/order/bked", params, function(data){
			initTd();
			if (data){
				for(var i in data){
					pushBkedDay(data[i]);
				}
			}
			checkSelect();
			pushBkedList(today);
		}, "json");
	}
	
	//点击时的处理,记录参数
	var queryParams = new Object();
	function tdclick(event, isadd){
		isUser(true);
		//天
		var tdIdx = $(event).parents("td").index();
		var days = getShowDays();
		var day = days[tdIdx-1];
		//时间段
		var time_td = $(event).parents("td").siblings(":first");
		var timestr = time_td.text();
		var vebtid = time_td.attr("vtid");
		
		//参数对象天时段Key，方便增删选项和定位已选
		var qpkey = day +"|"+ timestr;
		if (!isadd){
			delete queryParams[qpkey];
		} else{
			var times = timestr.split(/\s*\-\s*/);
			var param = new Object();
			param.vebvenid = venid;
			param.vebday = day;
			param.vebstime = times[0];
			param.vebetime = times[1];
			param.vebtid = vebtid;
			
			queryParams[qpkey] = param;
		}
	}
	
	//提交预定
	function sendBked(){
		isUser(true);
		//alert(JSON.stringify( queryParams ));
		var params = new Array();
		for(var k in queryParams){
			params.push(queryParams[k]);
			delete queryParams[k];
		}
		
		if (params.length == 0){
			rongDialog({ type : false, title : "没有预订项被选中", time : 3*1000 });
			return;
		}
		//alert(JSON.stringify( params ));
		$.post("./venue/order/addbked", {bkedArrayStr:JSON.stringify( params )}, sendSuccess, "json").done(function(){
			var day = getShowDays()[0];
			load(day);
		});
	}
	function sendSuccess(d){
		if (d.success){
			rongAlertDialog({ title: "提示信息", desc : "场馆预订成功<br/></br>查看我的预订", closeBtn : false, icoType : 1 }, function(){
				window.location = basePath;
			});
		}else{
			rongDialog({ type : false, title : "预定失败，请重新操作", time : 3*1000 });
		}
	}
	
	
	function init(_venid, lastDayNum, _uid){
		//初始参数
		venid = _venid;
		selectLastDayNum = lastDayNum;
		nowUserId = _uid;
		
		//加载一次系统当前时间
		getNowDate();
		//加载一次会话ID
		isUser();
		
		//定义点击事件预定和取消
		$(wktable).delegate("tr td:not(.place,.last) a", "click", function(event){
			tdclick(event.target, true);
			$(this).parents("td").removeClass("choose").addClass("choosed");
			$(this).parents("td").html(tdSp);
		})
		$(wktable).delegate("tr td:not(.place,.last) span:not(.notClick)", "click", function(event){
			tdclick(event.target, false);
			$(this).parents("td").removeClass("choosed").addClass("choose");
			$(this).parents("td").html(tdA);
		})
	}
	
	return {
		load: load,
		sendBked: sendBked,
		init: init
	}
})();