/**
 * zengrong 
   zrongs@vip.qq.com
   create 20161022
 */

function show_time(id,date){
	var id = id;
	var date  = date;
    var time_start = new Date().getTime(); //设定当前时间
    var time_end =  new Date(date).getTime(); //设定目标时间
    // 计算时间差 
    var time_distance = time_end - time_start; 
    // 天
    var int_day = Math.floor(time_distance/86400000) 
    time_distance -= int_day * 86400000; 
    // 时
    var int_hour = Math.floor(time_distance/3600000) 
    time_distance -= int_hour * 3600000; 
    // 分
    var int_minute = Math.floor(time_distance/60000) 
    time_distance -= int_minute * 60000; 
    // 秒  
    if(int_day < 10){ 
        int_day = "0" + int_day; 
    } 
    if(int_hour < 10){ 
        int_hour = "0" + int_hour; 
    } 
    if(int_minute < 10){ 
        int_minute = "0" + int_minute; 
    }
    $(id+" #time_d").html(int_day); 
    $(id+" #time_h").html(int_hour); 
    $(id+" #time_m").html(int_minute); 
}