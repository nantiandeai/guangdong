<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<%@include file="/pages/comm/agdhead.jsp"%>
<title>广东省文化馆</title>
<link href="${basePath }/static/assets/css/sign/sign.css" rel="stylesheet">

<link href="${basePath }/static/common/webuploader/webuploader.css" rel="stylesheet">
<script src="${basePath }/static/common/webuploader/webuploader.js"></script>

</head>
<body>

<!-- 公共头部开始 -->
<%@include file="/pages/comm/agdtop.jsp"%>
<!-- 公共头部结束 -->

<c:choose>
	<c:when test="${mark eq 'event' }">
		<h1 class="sign-title">《${event.actvtitle }》活动报名</h1>
	</c:when>
	
	<c:otherwise>
		<h1 class="sign-title">《${train.tratitle }》培训报名</h1>
	</c:otherwise>
</c:choose>

<ul class="crumbs crumbs-2nd clearfix">
  <li class="step-1">1. 选择报名方式<em class="arrow"></em></li>
  <li class="step-2">2. 上传报名资料<em class="arrow"></em></li>
  <li class="step-3 last">3. 报名成功</li>
</ul>
<div class="main">
  <div class="main-cont">
  	<c:if test="${train.traisattach eq 1 or event.actvisattach eq 1}">
	<div class="download-btn">
		<!-- 按分类放置不同的模板文件 -->
		<c:choose>
			<c:when test="${traenr.enrtype eq 0 }">
				<c:set var="tempfile"  value="${train.trapersonfile }"/> 
			</c:when>
			<c:when test="${traenr.enrtype eq 1 }">
				<c:set var="tempfile"  value="${train.trateamfile }"/> 
			</c:when>
			<c:when test="${actbm.actbmtype eq 0 }">
				<c:set var="tempfile"  value="${event.actvpersonfile }"/> 
			</c:when>
			<c:when test="${actbm.actbmtype eq 1 }">
				<c:set var="tempfile"  value="${event.actvteamfile }"/> 
			</c:when>
		</c:choose>
		
    	<span><a href="javascript:void(0)" onClick="downTemp('${tempfile}')"></a></span>
        <p>下载报名申请模板文件</p>
    </div>
  	</c:if>
  	
	
	<div class="selectBtn">
		<span>本次报名共需要提供${fileCount }个文件</span>
       	<a href="javascript:void(0)" id="open_but">选择要上传的文件</a>
    </div>
    <div class="rongUploadTt clearfix">
    	<div class="tt1">文件名</div>
        <div class="tt2">上传进度</div>
        <div class="tt3">状态</div>
        <div class="tt4">操作</div>
    </div> 
    <div class="rongUploadCont" id="file_list">
    <center>请选择要上传的文件</center>
    <!-- <ul class="rongUpload">
    	<li class="clearfix">
        	<div class="tt">cjk.mp4</div>
            <div class="loadingLineCont">
            	<div class="loadingLine" style=" width:40%"></div>
            </div>
            <div class="loadingInfo">上传中</div>
            <div class="loadingDel"><a href="javascript:void(0);"></a></div>
        </li>
    </ul> -->
    </div>
    <div class="controlCont clearfix">
        <div class="controlContBtn">
        	<a href="javascript:void(0)" id="upfile_but">开始上传</a>
        </div>
    </div>
    
    <div class="typeMain">
        <div class="download-cont">
        <a href="javascript:void(0)" class="goNext next none">下一步</a>
        </div>
    </div>
  </div>
</div>

<!--底部开始-->
<%@include file="/pages/comm/agdfooter.jsp"%>
<!--底部结束-->

<!-- core public JavaScript --> 
<script src="${basePath }/static/assets/js/plugins/tipso/tipso.js"></script>
<script src="${basePath }/static/assets/js/sign/sign.js"></script>
<script type="text/javascript">

var upFile = (function(){
	var filelist = $("#file_list");
	var upBut = $("#upfile_but");
	
	var state = "pending";
	var uploader = "";
	
	var eventObj = new Object();
	
	eventObj.fileDequeued = function( file ){
		var ulList = filelist.find("ul.rongUpload");
		ulList.find("#"+file.id).remove();
		if (ulList.find("li").length==0){
			ulList.remove();
			filelist.append('<center>请选择要上传的文件</center>');
		}
	}
	
	eventObj.fileQueued = function( file ) { 
		filelist.find("center").remove();
		var ulList = filelist.find("ul.rongUpload");
		if (ulList.size() == 0){
			filelist.append('<ul class="rongUpload"></ul>');
			ulList = filelist.find("ul.rongUpload");
		}
		var temp = "";
		temp+='<li class="clearfix" id="'+file.id+'">'
		temp+='	<div class="tt">' + file.name + '</div>'
		temp+='	<div class="loadingLineCont">'
		temp+='		<div class="loadingLine"></div>'
		temp+='	</div>'
		temp+='	<div class="loadingInfo state">等待上传...</div>'
		temp+='	<div class="loadingDel"><a href="javascript:void(0);" ></a></div>'
		temp+='</li>';
		var _tp = $(temp);
		_tp.find(".loadingDel>a").on("click", {file:file},function(event){
			uploader.removeFile(event.data.file, true);
		});
		ulList.append(_tp);
	};
	
	eventObj.uploadProgress = function( file, percentage ) {
	    var $li = $( '#'+file.id ), $percent = $li.find('.loadingLine');
	    $li.find('.state').text('上传中');
	    $percent.css( 'width', percentage * 100 + '%' );
	    //$percent.html(percentage * 100 + '%');
	}
	eventObj.uploadSuccess = function( file ) {
	    $( '#'+file.id ).find('.state').text('已上传');
	    $( '#'+file.id ).find('.loadingDel a').fadeOut();
	};
	eventObj.uploadError = function( file ) {
	    $( '#'+file.id ).find('.state').text('上传出错');
	};
	eventObj.uploadComplete = function( file ) {
	    //$( '#'+file.id ).find('.progress').fadeOut();
		//$( '#'+file.id ).find('.loadingDel a').fadeOut();
	};
	eventObj.all = function( type ) {
        if ( type === 'startUpload' ) {
            state = 'uploading';
        } else if ( type === 'stopUpload' ) {
            state = 'paused';
        } else if ( type === 'uploadFinished' ) {
            state = 'done';
        }
        if ( state === 'uploading' ) {
        	upBut.text('暂停上传');
        } else {
        	upBut.text('开始上传');
        }
    };
    
	
	function init(options){
		var _options = {
			//文件数限制
			fileNumLimit : 1,
		    // swf文件路径
		    swf: '${basePath}/static/common/webuploader/Uploader.swf',
		    // 文件接收服务端。
		    server: '',
		    // 选择文件的按钮。可选。
		    // 内部根据当前运行是创建，可能是input元素，也可能是flash.
		    pick: '#open_but',
		    // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
		    resize: false	
		};
		$.extend(_options, options || {});
		uploader = WebUploader.create(_options);
		
		for(var k in eventObj){
			onevent(k, eventObj[k]);
		}
		
		upBut.on( 'click', function() {
	        if ( state === 'uploading' ) {
	            uploader.stop();
	        } else {
	            uploader.upload();
	        }
	    });
	}
	
	function onevent(name, fun){
		if (uploader){
			uploader.on(name, fun);
		}
	}
	
	function getUploader(){
		return uploader;
	}
	
	return {
		init: init,
		onevent: onevent,
		getUploader: getUploader
	}
})()

var mark = "${mark}";
$(function(){
	//处理错误时跳到首页去
	var error = "${error}";
	if (error){
		rongDialog({
			type : false,
			title : error,
			time : 3*1000,
			url : "${basePath}"
		});
		return ;
	}
	
	//处理已有上传的访问跳到第三步去
	var enrisb = (mark && mark=="event")? "${event.actbmisb}": "${traenr.enrisb}";
	var gotoURL = (mark && mark=="event")? "${basePath}/sign/evt/step3/${actbm.actbmid}" : "${basePath}/sign/step3/${traenr.enrid}"
	if (enrisb && enrisb==1){
		rongDialog({
			type : false,
			title : "此报名已操作过上传资料了",
			time : 3*1000,
			url : gotoURL //"${basePath}/sign/step3/${traenr.enrid}"
		});
		return ;
	}
	
	//启动上传初始
	var params = new Object();
	params.mark = mark;
	params.enrid = mark=="event" ? "${actbm.actbmid}" : "${traenr.enrid}";
	upFile.init({
		fileNumLimit : "${fileCount }",
		server : "${basePath}/sign/upenrfiles",
		formData : params, //{enrid: "${traenr.enrid}"},
		fileVal : "files",
		multiple: true,
		fileSingleSizeLimit: 1024*1024*5,
		accept:{
				title: 'Files', 
				extensions: 'doc,docx,xls,xlsx,zip', 
				mimeTypes: 'application/msword'
		            +',application/vnd.openxmlformats-officedocument.wordprocessingml.document'
		            +',application/vnd.ms-excel'
		            +',application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
			}
			//[{ title: 'Images', extensions: 'gif,jpg,jpeg,bmp,png', mimeTypes: 'image/*' }]
	});
	
	upFile.onevent("uploadFinished", upFinished);
	upFile.onevent("uploadAccept", function(obj, ret){
		//alert(JSON.stringify(ret))
		return ret.success;
	});

})

//处理上传所有文件后
function upFinished(){
	var allCount = upFile.getUploader().getFiles().length;
	var completeCount = upFile.getUploader().getFiles("complete").length;
	var errCount = upFile.getUploader().getFiles("error").length;
	
	if (allCount == 0){
		rongDialog({ type : false, title : "请选择上传文件", time : 3*1000 });
		return;
	}
	
	var params = new Object();
	params.mark = mark;
	params.enrid = mark=="event" ? "${actbm.actbmid}" : "${traenr.enrid}";
	if (completeCount == "${fileCount }"){
		//点亮下一步
		$(".goNext").fadeIn();
		$(".goNext").off("click");
		$(".goNext").one("click", function(){
			//打包
			$.post("${basePath}/sign/optupfiles", params);
			//跳到3
			t3url = (mark && mark=="event")? "/sign/evt/step3/${actbm.actbmid}": "/sign/step3/${traenr.enrid}";
			window.location = getFullUrl(t3url);
		})
	}
	
	if (errCount>0){
		rongDialog({ type : false, title : "有 "+errCount+" 个文件失败", time : 3*1000 });
		return;
	}
}

//处理模板文件下载
function downTemp(fpath){
	if (fpath){
		var url = "./whtools/downFile?filePath="+fpath;
		var _href = getFullUrl(url);
		window.location = _href;
	}
}

</script>
</body>
</html>
