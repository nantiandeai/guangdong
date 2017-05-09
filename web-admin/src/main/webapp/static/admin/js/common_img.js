/**
 * 图片裁剪
 */

/**
 * 图片裁剪通用入口
 * 参数：type:图片类型 w:图片宽度 h:图片高度 fn:图片路径ID(返回图片路径地址)
 */
var imgurl ;
function openImgCut(type, w, h, fn){

	var _winId = "wh_imgcut_2017";
	
	var contentStr = //'<div id="'+_winId+'" class="easyui-window" title="Window Layout" data-options="iconCls:\'icon-save\'" style="width:1200px;height:400px;padding:5px;" closed="true">'+
	//'		<div class="easyui-layout" data-options="fit:true">'+
	//'			<div data-options="region:\'east\',split:true" style="width:100px"></div>'+
	'			<form id="form" name="form" class="form-horizontal" method="post" enctype="multipart/form-data">'+
	'		<div class="modal-body text-center">'+
	'			<div class="zxx_main_con">'+
	'				<div class="zxx_test_list">'+
	'					<input class="photo-file" type="file" name="imgFile" id="fcupload" onchange="readURL(this)" />'+
	'					<img alt="" src="" id="cutimg" /> '+
	'					<input type="hidden" id="x" name="x" /> '+
	'					<input type="hidden" id="y" name="y" /> '+
	'					<input type="hidden" id="w" name="w" /> '+
	'					<input type="hidden" id="h" name="h" />'+
	'					<input type="hidden" id="hh" name="hh" value=""/> '+
	'					<input type="hidden" id="ww" name="ww" value="" />'+
	'					<input type="hidden" id="type" name="type" value="" />'+
	'					<input type="hidden" id="fileId" name="fileId" value="" />'+
	'				</div>'+
	'			</div>'+
	'		</div>'+
	'	</form>';
	//'		</div>';
	//'	</div>';
	
	contentStr = 
	'<div class="easyui-layout" data-options="fit:true">                                                                                                                    '+
	'    <div data-options="region:\'center\', border:false" style="padding:10px;">                                                                                                         '+
			contentStr+
	'    </div>                                                                                                                                                             '+
	'    <div data-options="region:\'south\',border:false" style="text-align:right;padding:5px 0 0;padding-right:10px;padding-bottom:5px">                                                                         '+
	'        <a class="easyui-linkbutton" data-options="iconCls:\'icon-ok\'" href="javascript:void(0)" onclick="uploadCutImage();" style="width:80px">Ok</a>             '+
	'        <a class="easyui-linkbutton" data-options="iconCls:\'icon-cancel\'" href="javascript:void(0)" onclick="javascript:alert(\'cancel\')" style="width:80px">Cancel</a> '+
	'    </div>                                                                                                                                                             '+
	'</div>                                                                                                                                                                 '+
	'';
	
	
	if($('#'+_winId).size() > 0){
		$('#'+_winId).remove();
	}
	var win = $('<div id="'+_winId+'"></div>');
	$('body').append(win) ;
	
	//$("#type").val(type) ;
	
	$('#'+_winId).window({
		title: '图片裁剪',
		maximized: true,
		collapsible: false,
		minimizable: false,
		maximizable: false,
		content: contentStr,
		footer: '',
		onOpen: function(){
			win.find('#fcupload').data('width', w).data('height', h).data('fn', fn).data('imgtype',type);
		}
	});
}

/**
 * 裁剪处理
 * @param input
 * @returns
 */
function readURL(input) {
	var _width = $(input).data('width');
	var _height = $(input).data('height');
	//var _fn = $(input).data('fn');
	var _type = $(input).data('imgtype');
	$("#type").val(_type);
	var api = null;
	var boundx;
	var boundy;
	var x2 = $("#ww").val() ;
	var y2 = $("#hh").val() ;
	if (input.files && input.files[0]) {
		var reader = new FileReader();
		reader.readAsDataURL(input.files[0]);
		reader.onload = function(e) {
			$('#cutimg').removeAttr('src');
			$('#cutimg').attr('src', e.target.result);
			//$pimg.removeAttr('src');
			//$pimg.attr('src', e.target.result);

			api = $.Jcrop('#cutimg', {
			    setSelect: [0, 0, _width, _height],
			    //aspectRatio: 1,
			    allowResize: false,
			    allowSelect: false,
			    onSelect: updateCoords,
			    onChange:updateCoords
			});
			var bounds = api.getBounds();
			boundx = bounds[0];
			boundy = bounds[1];
			//$preview.appendTo(api.ui.holder);
		};
		if (api != undefined) {
			api.destroy();
		}
	}
}
/**
 * 给隐藏域赋值（坐标值，传到后台处理）
 * @param obj
 * @returns
 */
function updateCoords(obj) {
	$("#x").val(obj.x);
	$("#y").val(obj.y);
	$("#w").val(obj.w);
	$("#h").val(obj.h);
}
/**
 * 上传裁剪图片
 * @returns
 */

function uploadCutImage(){
    var form = new FormData(document.getElementById("form"));
    $(".jcrop-holder").hide();
    var urlPath = getFullUrl('/imgcut/uploadCutImage');
    $.ajax({
        url:urlPath,//'http://localhost:8080/szwhgsg/imgcut/uploadCutImage',
        type:"post",
        data:form,
        processData:false,
        contentType:false,
        dataType:'json',
        success:function(data){
        	var hiddenID = $("#fileId").val() ;
        	$("#"+hiddenID).val(data.imgpath);
        	imgurl = data.imgpath ;
        	var _fn = $('#fcupload').data('fn');
            $('#'+_fn).val(imgurl);
            $('#wh_imgcut_2017').window('close');
        },
        error:function(e){
            alert("错误！！");
        }
    });
}

function imgUrlPath(){
	return imgurl ;
}
