/**
 * 管理控制系统对应的公共函数与方法
 */
/**
 * 定义可通用的方法
 * @returns
 */
/** 设置全局变量和函数，用于获取正常的请求URL */
$.ajaxSetup({
	dataType: "json",
	global: false
});

/**
 * 根据URL获取全局URL
 */
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

/** 全局-表格界面构造方法 */
/** 表格实例化 */
(function($){
	$.WHDG = function(){}
	
	$.extend($.WHDG.prototype, {
		init: function(dgid, dgtbid, options){
			this.__dgid = dgid;
			this.__tbid = dgtbid;
			this.__queryParams = options.queryParams || {};
			
			var tOptions = $.extend({
				fit: true,
				rownumbers: true,
				fitColumns: true,
				singleSelect: true,
				pagination: true,
			    pageSize: 30,
			    pageList: [10,20,30,40,50]
			}, options);
			
			//定义了工具栏
			if($.type(this.__tbid) === "string" && this.__tbid != ""){
				var tOptions = $.extend(tOptions, {
					toolbar: '#'+this.__tbid
				});
			}
			
			//构造表格
			$('#'+this.__dgid).datagrid(tOptions);
			
			//查询处理
			this.bindSearch();
		},
		bindSearch: function(){
			var dgObj = $('#'+this.__dgid);
			var tbObj = $('#'+this.__tbid);
			var __queryParams = this.__queryParams;
			
			tbObj.find('.search').unbind('click.search').bind('click.search', function(e){
				e.preventDefault();
				
				__queryParams = $.extend(dgObj.datagrid('options').queryParams || {}, __queryParams);
				
				var _queryParams = {};
				tbObj.find('input[name]').each(function(){
					var propName = $(this).attr('name');
					if($(this).val() != ''){
						_queryParams[$(this).attr('name')] = $(this).val();
					}else{
						delete __queryParams[propName];
					}
				});
				
				dgObj.datagrid({
					url: dgObj.datagrid('options').url,
					queryParams: $.extend(__queryParams, _queryParams)
				}); 
			});
		},
		getRowData: function(index){
			var row = {};
			var rows = $('#'+this.__dgid).datagrid('getRows');
			if($.isArray(rows) && rows.length >= index){
				row = rows[index];
			}
			return row;
		},
		optFMT: function(val, rowData, index){
			var optDivId = this.optDivId;
			if (!optDivId.match(/^[#\.]/)){
				optDivId = "#"+optDivId;
			}
			var optDiv = $(optDivId);
			if (optDiv.size() == 0) return "";
			optDiv.find('a').each(function(){
				var _show = true;
				var validKey = $(this).attr("validKey");
				$(this).linkbutton({
					plain:true
				});
				if(typeof validKey != "undefined"){
					var validVal = $(this).attr("validVal");
					var vvl = validVal.split(/,\s*/);
					$(this).linkbutton('disable');
					_show = false;
					for(var k in vvl){
						var v = vvl[k];
						if (rowData[validKey] == v){
							$(this).linkbutton('enable');
							_show = true;
							break;
						}
					}
				}
				
				//根据验证函数判断是否显示按钮
				var validFun = $(this).attr("validFun");
				if(typeof validFun != "undefined"){
					_show = eval(validFun+'('+index+')');
					if(_show){
						$(this).linkbutton('enable');
					}else{
						$(this).linkbutton('disable');
					}
				}
				
				//设置属性
				var prop = $(this).attr("prop");
				var propVal = '';
				if(typeof prop != "undefined"){
					propVal = rowData[prop];
				}
				
				
				//
				var method = $(this).attr("method");
				if(_show && method){
					$(this).attr("onClick", method+"("+index+", '"+propVal+"');");
				}else{
					$(this).removeAttr("onClick");
				}

			});
			return optDiv.html();
		}
	});
})(jQuery);
var WHdg = new $.WHDG();
var WHdg2 = new $.WHDG();//如果有双表格

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

/**
 * 根据类型和标识取类型说明
 * @param type 分类类型:wh_typ.type
 * @param typid 分类标识:wh_typ.typid
 * @returns
 */
function sys_Whtyp(type, typid){
	return WHTYP.sys_Whtyp(type, typid);
}


/**
 * ------------------------datagrid formatter函数------------------------------
 */
/**
 * yes or no的格式化
 * @param val 单元格的值
 * @param rowData 表格行数据
 * @param index 表格行索引
 * @returns yes or no的格式化
 */
function yesNoFMT(val, rowData, index){
	return val == "1" ? '是' : '否';
}

/**
 * 启用停用的格式化
 * @param val 单元格的值
 * @param rowData 表格行数据
 * @param index 表格行索引
 * @returns yes or no的格式化
 */
function onOffFMT(val, rowDate, index){
	return val == "1" ? '启用' : '停用';
}
/**
 * 是否可预订的格式化
 * @param val 单元格的值
 * @param rowData 表格行数据
 * @param index 表格行索引
 * @returns yes or no的格式化
 */
function ydFMT(val, rowDate, index){
	return val == "1" ? '可预订' : '不可预定';
}


/**
 * 报名列表格式化
 * @param val
 * @param rowData
 * @param index
 * @returns
 */
function enrolllistFMT(val, rowData, index){
	switch(val){
		case "0" : return "否";
		case "1" : return "是";
		case "2" : return "不需要";
		default : return val;
	}
}
function eStateFMT(val, rowData, index){
	switch(val){
		case "0" : return "未审核";
		case "1" : return "已审核";
		case "2" : return "不需要";
		case "3" : return "不通过";
		default : return val;
	}
}
function typeFMT(val, rowData, index){
	return val == "1" ? '团队' : '个人';
}
/**
 * 审核是否有效
 * @param val
 * @param rowData
 * @param index
 * @returns
 */
function youxiaoFMT(val, rowData, index){
	return val == "1" ? '有效' : '无效';
}
/**
 * 培训状态的格式化
 * @param val 单元格的值
 * @param rowData 表格行数据
 * @param index 表格行索引
 * @returns 培训状态的格式化
 */
function traStateFMT(val, rowData, index){
	switch(val){
		case 0 : return "已编辑";
		case 1 : return "待审核";
		case 2 : return "已审核";
		case 3 : return "已发布";
		default : return val;
	}
}
/**
 * 场馆预定状态的格式化
 * @param val 单元格的值
 * @param rowData 表格行数据
 * @param index 表格行索引
 * @returns 培训状态的格式化
 */
function desStateFMT(val, rowData, index){
	switch(val){
	case 0 : return "未审核";
	case 1 : return "通过";
	case 2 : return "不通过";
	default : return val;
	}
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
 * 艺术类型
 * @param val 单元格的值
 * @param rowData 表格行数据
 * @param index 表格行索引
 * @returns 艺术类型
 */
function arttypFMT(val, rowData, index){
	return sys_Whtyp("0", val);
}

/**
 * 活动类型
 * @param val 单元格的值
 * @param rowData 表格行数据
 * @param index 表格行索引
 * @returns 艺术类型
 */
function acttypFMT(val, rowData, index){
	return sys_Whtyp("1", val);
}


/**
 * 培训类型
 * @param val 单元格的值
 * @param rowData 表格行数据
 * @param index 表格行索引
 * @returns 培训类型
 */
function tratypFMT(val, rowData, index){
	return sys_Whtyp("2", val);
}

/**
 * 适合年龄
 * @param val 单元格的值
 * @param rowData 表格行数据
 * @param index 表格行索引
 * @returns 适合年龄
 */
function agelevelFMT(val, rowData, index){
	return sys_Whtyp("3", val);
}

/**
 * 课程级别
 * @param val 单元格的值
 * @param rowData 表格行数据
 * @param index 表格行索引
 * @returns 课程级别
 */
function tralevelFMT(val, rowData, index){
	return sys_Whtyp("4", val);
}

/**
 * 资讯
 * @param val 单元格的值
 * @param rowData 表格行数据
 * @param index 表格行索引
 * @returns 资讯
 */
function zxtypeFMT(val, rowData, index){
	return sys_Whtyp("5", val);
}

/**
 * 标签
 * @param val 单元格的值
 * @param rowData 表格行数据
 * @param index 表格行索引
 * @returns 标签
 */
function tagFMT(val, rowData, index){
	return sys_Whtyp("6", val);
}

/**
 * 关键字
 * @param val 单元格的值
 * @param rowData 表格行数据
 * @param index 表格行索引
 * @returns 关键字
 */
function keyFMT(val, rowData, index){
	return sys_Whtyp("7", val);
}

/**
 * 区域
 * @param val 单元格的值
 * @param rowData 表格行数据
 * @param index 表格行索引
 * @returns 区域
 */
function areaFMT(val, rowData, index){
	return sys_Whtyp("8", val);
}
/**
 * 实体
 * @param val 单元格的值
 * @param rowData 表格行数据
 * @param index 表格行索引
 * @returns 实体
 */
function shitiFMT(val, rowData, index){
	return sys_Whtyp("9", val);
}
/**
 * 资源
 * @param val 单元格的值
 * @param rowData 表格行数据
 * @param index 表格行索引
 * @returns 资源
 */
function entFMT(val, rowData, index){
	return sys_Whtyp("10", val);
}
/**
 * 志愿培训
 * @param val 单元格的值
 * @param rowData 表格行数据
 * @param index 表格行索引
 * @returns 资源
 */
function zypxFMT(val, rowData, index){
	return sys_Whtyp("19", val);
}

/**
 * 图片格式化
 * @param val 单元格的值
 * @param rowData 表格行数据
 * @param index 表格行索引
 * @returns 资源
 */
function imgFMT(val, rowData, index){
	if(val && val != ''){
		return '<img src="'+imgServerAddr+''+val+'" style="height:50px;"/>';
	}else{
		return '';
	}
}



/** ----------------------------filebox验证器----------------------------------------------- */
$.extend($.fn.validatebox.defaults.rules, {
	isPhone: {
		validator: function(value, param){
			if(value != ''){
				var rex = /^1[3-8]+\d{9}$/;
				var rex2 = /^((0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;
				if(rex.test(value) || rex2.test(value)){
					return true;
				}else{
					$.fn.validatebox.defaults.rules.isPhone.message ="请输入正确的联系方式, 如：13688888888.";
					return false;
				}
				
			}
			return true;
		},    
        message: ''
	},
	isEmail: {
		validator: function(value, param){
			if(value != ''){ 
				var rex=/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
				if(rex.test(value)){
					return true;
				}else{
					$.fn.validatebox.defaults.rules.isEmail.message ="请输入正确的邮箱号码, 邮箱号码格式如：****@**.**.";
					return false;
				}
				
			}
			return true;
		},    
		message: ''
	},
    isIMG: {    
        validator: function(value, param){ 
        	//fileBox的name属性
        	var fileId = param[0];
        	
        	//参数为限制大小,KB单位
        	var size = param[1] || 104857600;//1M
        		
        	//获上传的文件
        	var fileObj = $('#'+fileId).parent().find('input[name="'+fileId+'"]')[0];
        	if(fileObj && fileObj.files && fileObj.files.length > 0){
	        	var file = fileObj.files[0];
	        	if(file == null){
	        		return true;
	        	}
	        	
	        	//获取文件名称
	            var fileName = file.name;
	            
	            //获取文件类型名称
	            var allowArr = ['jpg','jpeg', 'png', 'JPG','JPEG', 'PNG'];
	            var file_typename = fileName.substring(fileName.lastIndexOf('.')+1, fileName.length);
	            if($.inArray(file_typename, allowArr) < 0){
	            	//错误消息
	            	$.fn.validatebox.defaults.rules.isIMG.message ="请选择正确的图片格式(jpg/jpeg/png).";
	            	return false;
	            }
	            
	            //如果大小不对
	            var fileSize = (file.size/1024).toFixed(2);
	            if(fileSize > size){
	            	if(size >= 1048576){
	            		var tsize = (size/1024).toFixed(2);
	            		$.fn.validatebox.defaults.rules.isIMG.message ="请选择正确的图片格式(jpg/jpeg/png), 大小限制在"+tsize+"M以内.";
	            	}else{
	            		$.fn.validatebox.defaults.rules.isIMG.message ="请选择正确的图片格式(jpg/jpeg/png), 大小限制在"+size+"KB以内.";
	            	}
	            	return false;
	            }
        	}
        	
            return true;    
        },    
        message: ''   
    },
    isVideo: {
    	validator: function(value, param){
    		//fileBox的name属性
        	var fileId = param[0];
        	
        	//参数为限制大小,KB单位
        	var size = param[1] || 524288000;//500M
        		
        	//获上传的文件
        	var fileObj = $('#'+fileId).parent().find('input[name="'+fileId+'"]')[0];
        	if(fileObj && fileObj.files && fileObj.files.length > 0){
	        	var file = fileObj.files[0];
	        	if(file == null){
	        		return true;
	        	}
	        	
	        	//获取文件名称
	            var fileName = file.name;
	            
	            //获取文件类型名称
	            var allowArr = ['mp4','ogg', 'webm', 'MP4','OGG', 'WEBM'];
	            var file_typename = fileName.substring(fileName.lastIndexOf('.')+1, fileName.length);
	            if($.inArray(file_typename, allowArr) < 0){
	            	//错误消息
	            	$.fn.validatebox.defaults.rules.isVideo.message ="请选择正确的文件格式(mp4/ogg/webm).";
	            	return false;
	            }
	            
	            //如果大小不对
	            var fileSize = (file.size/1024).toFixed(2);
	            if(fileSize > size){
	            	if(size >= 1048576){
	            		var tsize = (size/1024).toFixed(2);
	            		$.fn.validatebox.defaults.rules.isVideo.message ="请选择正确的文件格式(mp4/ogg/webm), 大小限制在"+tsize+"M以内.";
	            	}else{
	            		$.fn.validatebox.defaults.rules.isVideo.message ="请选择正确的文件格式(mp4/ogg/webm), 大小限制在"+size+"KB以内.";
	            	}
	            	return false;
	            }
        	}
        	
        	return true;
    	},    
        message: ''
    },
    isAudio: {
    	validator: function(value, param){   
    		//fileBox的name属性
        	var fileId = param[0];
        	
        	//参数为限制大小,KB单位
        	var size = param[1] || 104857600;//100M
        		
        	//获上传的文件
        	var fileObj = $('#'+fileId).parent().find('input[name="'+fileId+'"]')[0];
        	if(fileObj && fileObj.files && fileObj.files.length > 0){
	        	var file = fileObj.files[0];
	        	if(file == null){
	        		return true;
	        	}
	        	
	        	//获取文件名称
	            var fileName = file.name;
	            
	            //获取文件类型名称
	            var allowArr = ['mp3','MP3'];
	            var file_typename = fileName.substring(fileName.lastIndexOf('.')+1, fileName.length);
	            if($.inArray(file_typename, allowArr) < 0){
	            	//错误消息
	            	$.fn.validatebox.defaults.rules.isAudio.message ="请选择正确的文件格式(mp3).";
	            	return false;
	            }
	            
	            //如果大小不对
	            var fileSize = (file.size/1024).toFixed(2);
	            if(fileSize > size){
	            	if(size >= 1048576){
	            		var tsize = (size/1024).toFixed(2);
	            		$.fn.validatebox.defaults.rules.isAudio.message ="请选择正确的文件格式(mp3), 大小限制在"+tsize+"M以内.";
	            	}else{
	            		$.fn.validatebox.defaults.rules.isAudio.message ="请选择正确的文件格式(mp3), 大小限制在"+size+"KB以内.";
	            	}
	            	return false;
	            }
        	}
        	
        	return true;   
        },    
        message: ''  
    },
    isFile: {
    	validator: function(value, param){   
    		//accept:'application/msword,application/vnd.ms-excel,aplication/zip'
    		//fileBox的name属性
        	var fileId = param[0];
        	
        	//参数为限制大小,KB单位
        	var size = param[1] || 104857600;//100M
        		
        	//获上传的文件
        	var fileObj = $('#'+fileId).parent().find('input[name="'+fileId+'"]')[0];
        	if(fileObj && fileObj.files && fileObj.files.length > 0){
	        	var file = fileObj.files[0];
	        	if(file == null){
	        		return true;
	        	}
	        	
	        	//获取文件名称
	            var fileName = file.name;
	            
	            //获取文件类型名称
	            var allowArr = ['zip', 'xls', 'xlsx', 'doc', 'docx','pdf','rar','ppt','pptx','PPTX','PPT','RAR', 'ZIP', 'XLS', 'XLSX', 'DOC', 'DOCX', 'PDF'];
	            var file_typename = fileName.substring(fileName.lastIndexOf('.')+1, fileName.length);
	            if($.inArray(file_typename, allowArr) < 0){
	            	//错误消息
	            	$.fn.validatebox.defaults.rules.isFile.message ="请选择正确的文件格式(execl/word/zip/rar/ppt/pdf).";
	            	return false;
	            }
	            
	            //如果大小不对
	            var fileSize = (file.size/1024).toFixed(2);
	            if(fileSize > size){
	            	if(size >= 1048576){
	            		var tsize = (size/1024).toFixed(2);
	            		$.fn.validatebox.defaults.rules.isFile.message ="请选择正确的文件格式(execl/word/zip/rar/ppt/pdf), 大小限制在"+tsize+"M以内.";
	            	}else{
	            		$.fn.validatebox.defaults.rules.isFile.message ="请选择正确的文件格式(execl/word/zip/rar/ppt/pdf), 大小限制在"+size+"KB以内.";
	            	}
	            	return false;
	            }
        	}
        	
        	return true;   
    	},
    	message: ''
    }

    ,isText: {
        validator: function (value, param) {
			if(/<[^>]*>/.test(value)){
                $.fn.validatebox.defaults.rules.isText.message ="文本中不能包含<或>符号.";
				return false;
			}
			return true;
        }, message: ''
    }
});