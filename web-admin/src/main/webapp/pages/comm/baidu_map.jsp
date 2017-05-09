<%@page import="com.creatoo.hn.utils.WhConstance"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
//百度Map ak 配置
String CONFIG_BAIDU_MAP_AKEY = WhConstance.getSysProperty("BAIDU_MAP_AKEY", "");
//默认显示地
String CONFIG_DEFAUT_MAP_ADDRESS = WhConstance.getSysProperty("DEFAUT_MAP_ADDRESS", "广东省");
%>

<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=<%=CONFIG_BAIDU_MAP_AKEY%>"></script>

<script type="text/javascript">

var MapTool = (function(){
	var map;
	var default_address = "<%=CONFIG_DEFAUT_MAP_ADDRESS%>";
	
	function initMap(mpid){
		// 百度地图API功能
		map = new BMap.Map(mpid);    // 创建Map实例
		//map.setCurrentCity("长沙");          // 设置地图显示的城市 此项是必须设置的
		//map.centerAndZoom(new BMap.Point(116.404, 39.915), 16);  // 初始化地图,设置中心点坐标和地图级别
		map.addControl(new BMap.MapTypeControl());   //添加地图类型控件
		map.addControl(new BMap.NavigationControl());
		map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放
	}
	
	function showMap4addr(mpid, address, xpoint, ypoint){
		initMap(mpid);
		var xpoint = xpoint ||0;
		var ypoint = ypoint ||0;
		var address = address || default_address;
		// 创建地址解析器实例
		var myGeo = new BMap.Geocoder();
		// 将地址解析结果显示在地图上,并调整地图视野
		var point = new BMap.Point(xpoint, ypoint);
		myGeo.getPoint(address, function(point){
			if (point) {
				map.centerAndZoom(point, 16);
				map.addOverlay(new BMap.Marker(point));
			}else{
				alert("解析地址失败");
				//rongDialog({ type : false, title : "解析地址失败", time : 1*1000 });
			}
		});
	}
	
	function showMap(mpid, xpoint, ypoint){
		initMap(mpid);
		var xpoint = parseFloat( xpoint ||0 );
		var ypoint = parseFloat( ypoint ||0 );
		map.centerAndZoom(new BMap.Point(xpoint, ypoint), 16);
	}
	
	
	return {
		showMap: showMap,
		showMap4addr: showMap4addr
	}
})()

</script>