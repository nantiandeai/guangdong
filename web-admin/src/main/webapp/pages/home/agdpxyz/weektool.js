
//计算周
var  WeekTool = (function(){
	function getDate(dateStr){
		var dtarr = dateStr.split(/\D+/);
		var d = new Date();
		if (dtarr.length >=3){
			dtarr[1] = dtarr[1]-1;
			dtarr.length = 3;
			d.setFullYear.apply(d, dtarr);
		}
		return d;
	}
	
	function getWeekNum(sdate, edate, now){
		var _sdate = getDate(sdate);
		var _edate = getDate(edate);
		var _now = getDate(now);
		//总天数
		var _dayCount = (_edate.getTime()-_sdate.getTime())/(24*60*60*1000);
		//当前天数
		var _nowCount = (_now.getTime()-_sdate.getTime())/(24*60*60*1000);
		if (_nowCount <0) _nowCount = 0;
		if (_nowCount > _dayCount) _nowCount = _dayCount;
		
		//百分比
		var per = Math.floor( (_nowCount/_dayCount)*100 );
		//总周数
		var weekNum = Math.floor( _dayCount%7>0? _dayCount/7+1 : _dayCount/7 );
		//进度周
		var nowWeekNum = Math.floor( _nowCount%7>0? _nowCount/7+1 : _nowCount/7 );

		var ref = new Object();
		ref.per = per;
		ref.weekNum = weekNum;
		ref.nowWeekNum = nowWeekNum;
		return ref;
	}
	
	
	
	
	return {
		getWeekNum: getWeekNum
	}
})()

