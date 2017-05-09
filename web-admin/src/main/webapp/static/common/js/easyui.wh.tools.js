/**
 * 定义可通用的方法
 * @returns
 */
function WhuiBase(){
	if ( typeof WhuiBase.__initialization__ != "undefined") return;
	WhuiBase.__initialization__ = true;
	//设置实例属性
	WhuiBase.prototype.setAttr = function(key, val){
		this[key] = val;
	}
	//取得实例属性
	WhuiBase.prototype.getAttr = function(key){
		return this[key];
	}
}

/**
 * DataGrid 定义处理
 * @param config
 * @returns
 */
function WhuiDataGrid(datagridJQ, config){
	this.datagridJQ = datagridJQ || ".easyui-datagrid";
	this.cfg = {
			border:false,
        	fit:true,
        	fitColumns:true,
        	rownumbers:true,
        	singleSelect:true,
        	pagination: true,
            pageSize : 30,
            pageList : [10,20,30,50,100]
	};
	$.extend(this.cfg, config||{});
	
	if ( typeof WhuiDataGrid.__initialization__ != "undefined") return;
	WhuiDataGrid.__initialization__ = true;

	
	//显示数据表格
	WhuiDataGrid.prototype.init = function(options){
		var options = options || {};
		$.extend(this.cfg, options);
		$(this.datagridJQ).datagrid( this.cfg );

		this.initSearchBut();
	}
	
	//返回数据表格引用
	WhuiDataGrid.prototype.getDataGrid = function(){return $(this.datagridJQ) }

	WhuiDataGrid.prototype.initSearchBut = function(){
		var tb = this.cfg.toolbar;
		if (!tb) return;
		var searchBut = $(tb).find('.search');
		if (searchBut.size() == 0) return;
		searchBut.off("click");
		var me = this;
		searchBut.on("click", function(){ me.searchLoad() });
	}
	
	//按查询工具条加数据
	WhuiDataGrid.prototype.searchLoad = function(url,toolbarJQ){ 
		var tbjq = toolbarJQ || this.cfg.toolbar;
		var url = url || this.cfg.url;
		
		var params = {};
		if (tbjq){
			var tb = $(tbjq);
			tb.find("[name]").each(function(){
				var value = $(this).val();
				var name = $(this).attr("name");
				params[name] = value;
			});
		}
        
		this.cfg.queryParams = this.cfg.queryParams ||{};
        $.extend(this.cfg.queryParams, params);

        this.getDataGrid().datagrid({
            url : url,
            queryParams : this.cfg.queryParams
        });
	}

	WhuiDataGrid.prototype.getRowData = function(idx){
		if (!idx.match("\d+")) return "";
		var rows = this.getDataGrid().datagrid("getRows");
		if (rows.length <= idx) return "";
		return rows[idx];
	}

	WhuiDataGrid.prototype.optFMT = function(val, rowData, index){
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

			var method = $(this).attr("method") || "alert";
			if(_show){
				$(this).attr("onClick", method+"("+index+");");
			}else{
				$(this).removeAttr("onClick");
			}

		});
		return optDiv.html();
	}
}
//继承自定义基础类
WhuiDataGrid.prototype = new WhuiBase();


/**
 * 弹出框表单
 * @param winJQ
 * @param config
 * @constructor
 *
例1
var test2 = new WhuiWinForm("#test2");
 test2.init();
 test2.setFormStyleTemp({lableAlign:'left'});
 test2.openWin();
<div class="none" id="test2">
 <form id="te" method="get">
	 <div class="main">
		 <div class="row">
			 <div>test4</div>
			 <div><input class="easyui-textbox"  style="height:35px;width:80%"/></div>
		 </div>
	 </div>
 </form>
 </div>

 例2
 var test = new WhuiWinForm();
 test.init();
 test.setFormInfo("#test","tr");
 test.setWinTitle('abc');
 test.setFoolterBut({text:'abcde',onClick:function(){alert('3311693033')}});

 <div class="none" id="test">
 <form id="test1frm" method="post">
	 <table>
		 <tr>
			 <td>test1</td>
			 <td><input class="easyui-combobox" name="actcalss" style="height:35px;width:80%"
			 data-options="editable:false,required:true, valueField:'typid',textField:'typname',url:'./comm/whtyp?type=0'"/></td>
		 </tr>
	 </table>
 </form>
 </div>
 *
 */
function WhuiWinForm(winJQ, config){
	this.windowJQ = winJQ || $("<div></div>");

	this.cfg = {
		collapsible : false,
		minimizable : false,
		maximizable : true,
		modal : true,
		width : '70%',
		height : '60%',
		footer : '<div class="whui_winform_footer" style="padding:10px 0px;"><center><a href="javascript:void(0)"></a>&nbsp;&nbsp;<a href="javascript:void(0)"></a></center></div>'
	};
	$.extend(this.cfg, config||{});
	
	if ( typeof WhuiWinForm.__initialization__ != "undefined") return;
	WhuiWinForm.__initialization__ = true;

	WhuiWinForm.__id__ = 0;

	//默认的初始处理
	WhuiWinForm.prototype.init = function(){
		if (!this.isinit){
			this.initWin();
		}
		this.isinit = true;
	}

	//构建win组件
	WhuiWinForm.prototype.initWin = function(options){
		var options = options||{};
		$.extend(this.cfg, options);

		//加一个特定的样式名,用于处理同页面表单样式各自生效
		WhuiWinForm.__id__++;
		this._bodycls = "whui_winbody_cls"+WhuiWinForm.__id__;
		this.cfg.bodyCls = this._bodycls;

		//解析为弹出框
		var win = this.getWin().window( this.cfg );

		//初始下边的按钮
		if( $(this.cfg.footer).attr('class')=="whui_winform_footer"){
			this.setFoolterBut();
			var me = this;
			this.setFoolterBut({
				text :"取消",
				iconCls:"",
				width:60,
				onClick:function(){ me.closeWin(); }
			}, 1);
		}

		//装入样式
		this.setFormStyleTemp();
		//关闭掉
		win.window("close");
	}

	//默认的表格样式
	WhuiWinForm.prototype.styleTempForm = '<style type="text/css">'
		+'bodycls .main{width:formWidth; margin:mainTop auto}'
		+'bodycls .row{}'
		+'bodycls .row>div{padding: 5px; }'
		+'bodycls .row>div:nth-child(1){width:lableWidth; height:lableHeight; line-height:lableHeight; text-align: lableAlign;float:left; }'
		+'bodycls .row>div:nth-child(2){margin-left: lableWidth}'
		+'bodycls .cls{clear: both;}'
		+'</style>';
	//调整表单样式模板
	WhuiWinForm.prototype.setFormStyleTemp = function(options){
		var _options = {
			mainTop:'30px',
			formWidth : '60%',
			lableWidth : '150px',
			lableHeight : '35px',
			lableAlign : 'right'
		};
		$.extend(_options, options||{});
		var styleInfo = this.styleTempForm;
		styleInfo = styleInfo.replace(/bodycls/g, this._bodycls?'.'+this._bodycls : "");
		styleInfo = styleInfo.replace(/mainTop/g, _options.mainTop);
		styleInfo = styleInfo.replace(/formWidth/g, _options.formWidth);
		styleInfo = styleInfo.replace(/lableWidth/g, _options.lableWidth);
		styleInfo = styleInfo.replace(/lableHeight/g, _options.lableHeight);
		styleInfo = styleInfo.replace(/lableAlign/g, _options.lableAlign);

		this.getWin().find("style").remove();
		this.getWin().append(styleInfo);
	}
	//清除表单样式模板
	WhuiWinForm.prototype.createFormStyleTemp = function(){
		this.getWin().find("style").remove();
	}

	//定议footer按钮
	WhuiWinForm.prototype.setFoolterBut = function(options, idx){
		var alist = this.getWinFooter().find("a");
		if (alist.size()<1) return;
		var _options = {
			text :"提交",
			iconCls:"icon-ok",
			width: 80
			//,onClick:function(){ alert("foolterBut click...") }
		}
		$.extend(_options, options || {});

		var idx = idx || 0;
		$(alist[idx]).linkbutton( _options );
	}
	
	//给footer按钮一次点击事件
	WhuiWinForm.prototype.oneClick = function(fun, data, idx){
		var alist = this.getWinFooter().find("a");
		if (alist.size()<1) return;
		var idx = idx || 0;
		$(alist[idx]).off("click");
		$(alist[idx]).one("click", data||{}, fun);
	}

	//设置面板标题
	WhuiWinForm.prototype.setWinTitle = function(title){
		this.getWin().window("setTitle", title);
		this.winTitle = title;
	}
	
	//取面板标题
	WhuiWinForm.prototype.getWinTitle = function(){
		return this.winTitle;
	}

	//设置表单内容,用于将table定义的表单装入弹框
	WhuiWinForm.prototype.setFormInfo = function(divJq, rowJq, labelIdx, putIdx){
		if (!divJq) return;
		var mainHtm = $(divJq);
		if (mainHtm.size()==0) return;

		//提表单项集
		var formRows = [];
		if (rowJq){
			formRows = $(rowJq, mainHtm);
		}else{
			formRows = $(divJq).children();
		}
		if (formRows.size() == 0) return;

		var main = $("<div class='main'></div>");
		var labelIdx = labelIdx || 0;
		var putIdx = putIdx || 1;
		for(var k=0;k< formRows.length; k++){
			var tr = $(formRows[k]);
			var tds = tr.children();
			if (tds.size() <2) continue;

			var row = $("<div class='row'><div></div> <div></div></div>");
			var label = $(tds.get(labelIdx)).html();
			var put = $(tds.get(putIdx)).children();
			row.find("div:eq(0)").html(label);
			row.find("div:eq(1)").append(put);

			main.append(row);
		}

		var winbody = this.getWin().window("body");

		//提form标签
		var _form = mainHtm.find("form:eq(0)");
		if (_form.size() ==1){
			_form.empty();
			_form.append(main);
			winbody.append(_form);
		}else{
			winbody.append(main);
		}
	}

	//获取Win引用
	WhuiWinForm.prototype.getWin = function(){
		return $(this.windowJQ);
	}
	//获取win面板的footer引用
	WhuiWinForm.prototype.getWinFooter = function(){
		return this.getWin().window('footer');
	}
	//获取win面板的body引用
	WhuiWinForm.prototype.getWinBody = function(){
		return this.getWin().window('body');
	}
	//关闭面板
	WhuiWinForm.prototype.closeWin = function(){
		this.getWin().window("close");
	}
	//打开面板
	WhuiWinForm.prototype.openWin = function(){
		this.getWin().window("open");
		this.getWin().window("resize");
	}
}
//继承自定义基础类
WhuiWinForm.prototype = new WhuiBase();