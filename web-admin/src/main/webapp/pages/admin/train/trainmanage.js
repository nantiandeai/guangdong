/**
 * 新的管理培训的js
 */
var winform = new WhuiWinForm("#train_edit",{height:250});
var winform1 = new WhuiWinForm("#check_edit",{height:250});
/**
 * 设置富文本不可编辑
 */
function setDisabled() {
    UE.getEditor('traeditor1').setDisabled('fullscreen');
    UE.getEditor('catalog1').setDisabled('fullscreen');
}
/**
 * 当不需要报名时隐藏输入框
 */
function forbidden() {
	if ($("#traisenrol").combobox('getValue') == "0") {
		$("#traenroldesc").textbox('disable');
		$("#traenrolstime").datetimebox('disable');
		$("#traenroletime").datetimebox('disable');
		$("#traenrollimit").numberspinner('disable');
		$("#traisenrolqr").combobox('disable');
		$("#traisnotic").combobox('disable');
		$("#ismoney").combobox('disable');
		
		$("#traisrealname").combobox('disable');
		$("#traisfulldata").combobox('disable');
		$("#traisonlyone").combobox('disable');
		$("#traisattach").combobox('disable');
		
		$("#tracanperson").combobox('disable');
		$("#trapersonfile_up").filebox('disable');
		$("#tracanteam").combobox('disable');
		$("#trateamfile_up").filebox('disable');
	}else{
		$("#traenroldesc").textbox('enable');
		$("#traenrolstime").datetimebox('enable');
		$("#traenroletime").datetimebox('enable');
		$("#traenrollimit").numberspinner('enable');
		$("#traisenrolqr").combobox('enable');
		$("#traisnotic").combobox('enable');
		$("#ismoney").combobox('enable');
		$("#traisrealname").combobox('enable');
		$("#traisfulldata").combobox('enable');
		$("#traisonlyone").combobox('enable');
		$("#traisattach").combobox('enable');
		
		$("#tracanperson").combobox('enable');
		$("#trapersonfile_up").filebox('enable');
		$("#tracanteam").combobox('enable');
		$("#trateamfile_up").filebox('enable');
		
		
	}
}
//是否需要上传附件
function isFJ(){
	if ($("#traisattach").combobox('getValue') == "0") {
		$("#trapersonfile_up").filebox('disable');
		$("#trateamfile_up").filebox('disable');
	}else {
		
		$("#trapersonfile_up").filebox('enable');
		$("#trapersonfile_up").filebox({required:true});
		$("#trateamfile_up").filebox('enable');
		$("#trateamfile_up").filebox({required:true});
	}
}
//是否需要上传附件(修改)
function _isFJ(){
	if ($("#traisattach").combobox('getValue') == "0") {
		$("#trapersonfile_up").filebox('disable');
		$("#trateamfile_up").filebox('disable');
	}else {
		$("#trapersonfile_up").filebox('enable');
		$("#trateamfile_up").filebox('enable');
		$("#trapersonfile_up").filebox({required:false});
		$("#trateamfile_up").filebox({required:false});
	}
}

//是否需要个人报名
function forbide() {
	if ($("#tracanperson").combobox('getValue') == "0") {
		$("#trapersonfile_up").filebox('disable');
	}else{
		$("#trapersonfile_up").filebox('enable');
		if ($("#traisattach").combobox('getValue') == "1") {
			$("#trapersonfile_up").filebox({required:true});
		}
	}
}
//是否需要团队报名
function forbide1() {
	if ($("#tracanteam").combobox('getValue') == "0"){
		$("#trateamfile_up").filebox('disable');
	}else{
		$("#tracanteam").filebox('enable');
		if ($("#traisattach").combobox('getValue') == "1") {
			$("#trateamfile_up").filebox({required:true});
		}
	}
}

/**
 * 添加培训
 */
function _addTrain(index){
	var row = WHdg.getRowData(index);
	UE.getEditor('traeditor').setContent("");
	UE.getEditor('catalog').setContent("");
	winform.openWin();
	winform.setWinTitle("添加培训");
	clearCombobox();
	$("#ff").form('clear');
	
	//相关控制开始
	$("#traisenrol").combobox({
		onChange:forbidden
	});
	$("#traisattach").combobox({
		onChange:isFJ
	});//相关控制结束
	$("#tracanperson").combobox({
		onChange:forbide
	});
	$("#tracanteam").combobox({
		onChange:forbide1
	});
	
	_showImg(row);
	
	var frm = winform.getWinBody().find('form').form({
		url : getFullUrl('/admin/trainManage/saveTrain'),
		onSubmit : function(param){
			 //得到当前的输入值
			 var a = $('#trasdate').datetimebox('getValue');
			 var b = $('#traedate').datetimebox('getValue');
			 var s = $('#traenrolstime').datetimebox('getValue');
			 var e = $('#traenroletime').datetimebox('getValue');
			 //转换时间格式
			 var d_a = new Date(a); 
			 var d_b = new Date(b);
			 var stime_ = new Date(s);
			 var etime_ = new Date(e); 
			 //时间比较
			 if(d_a >= d_b){
				 $.messager.alert("提示", "开始时间应小于结束时间!");
				 winform.oneClick(function(){ frm.submit(); });
				 return false;
				 
			 }
			 if(stime_ >= etime_){
				 $.messager.alert("提示", "报名开始时间应小于报名结束时间!");
				 winform.oneClick(function(){ frm.submit(); });
				 return false;
				 
			 }
			 if(stime_ >= d_b){
				 $.messager.alert("提示", "报名开始时间应小于培训结束时间!");
				 winform.oneClick(function(){ frm.submit(); });
				 return false;
				 
			 }
			 if(etime_ >= d_b){
				 $.messager.alert("提示", "报名结束时间应小于培训结束时间!");
				 winform.oneClick(function(){ frm.submit(); });
				 return false;
			 }
			 //团队或者个人必须选择一个
			 if ($("#traisenrol").combobox('getValue') == 1) {
			 var gr = $("#tracanperson").combobox('getValue');
			 var td = $("#tracanteam").combobox('getValue');
			 if (gr == 0 && td == 0) {
				$.messager.alert("提示", "必须选择是个人或者团队报名!");
				winform.oneClick(function(){ frm.submit(); });
				return false;
				
			}
			}
			
			//设置富文本的内容
			$('#tradetail').val(UE.getEditor('traeditor').getContent());
			$('#tracatalog').val(UE.getEditor('catalog').getContent());
            var _is = $(this).form('enableValidation').form('validate');
            if (!_is) {
            	winform.oneClick(function(){ frm.submit(); });
			}
            return _is;
        },
		success : function(data) {
			var Json = $.parseJSON(data);
			if(Json && Json.success == "0"){
				$('#train').datagrid('reload');
				$.messager.alert('提示', '操作成功!');
				winform.closeWin();
			   }else{
				   $.messager.alert('提示', '操作失败!');
				   winform.oneClick(function(){ frm.submit(); });
			   }
		}
	});
	
	frm.form("clear");
	
	winform.oneClick(function(){ frm.submit(); });
 }


function clearCombobox(){
	$("#trakeys").combobox("clear");
	$("#tratags").combobox("clear");
}
/**
 * 修改培训批次
 */
function editTrain(index) {
	var row = WHdg.getRowData(index);
	if (row.trastate != "0") {
		$.messager.alert("提示", "不能符合修改要求！");
		return;
	}
	//alert(JSON.stringify(row.enroldesc));r
	winform.openWin();
	winform.setWinTitle("修改培训");
	clearCombobox();
	//当报名按钮值输入时改变其他框的状态
	if (row.traisenrol == "0") {
		$("#traenroldesc").textbox('disable');
		$("#traenrolstime").datetimebox('disable');
		$("#traenroletime").datetimebox('disable');
		$("#traenrollimit").numberspinner('disable');
		$("#traisenrolqr").combobox('disable');
		$("#traisnotic").combobox('disable');
		$("#ismoney").combobox('disable');
		
		$("#traisrealname").combobox('disable');
		$("#traisfulldata").combobox('disable');
		$("#traisonlyone").combobox('disable');
		$("#traisattach").combobox('disable');
		
		$("#tracanperson").combobox('disable');
		$("#trapersonfile_up").filebox('disable');
		$("#tracanteam").combobox('disable');
		$("#trateamfile_up").filebox('disable');
	}
	if (row.traisattach == "0") {
		$("#trapersonfile_up").filebox('disable');
		$("#trateamfile_up").filebox('disable');
	}
	$("#traisenrol").combobox({
		onChange:forbidden
	});
	$("#tracanperson").combobox({
		onChange:forbide
	});
	$("#tracanteam").combobox({
		onChange:forbide1
	});
	$("#traisattach").combobox({
		onChange:_isFJ
	});
	//
	
	//显示富文本的值
	UE.getEditor('traeditor').setContent(row.tradetail);
	UE.getEditor('catalog').setContent(row.tracatalog);
	_showImg(row);
	var frm = winform.getWinBody().find('form').form({
		url : getFullUrl('/admin/trainManage/saveTrain'),
		onSubmit : function(param) {
			 //得到当前的输入值
			 var a = $('#trasdate').datetimebox('getValue');
			 var b = $('#traedate').datetimebox('getValue');
			 var s = $('#traenrolstime').datetimebox('getValue');
			 var e = $('#traenroletime').datetimebox('getValue');
			 //转换时间格式
			 var d_a = new Date(a); 
			 var d_b = new Date(b);
			 var stime_ = new Date(s);
			 var etime_ = new Date(e); 
			 //时间比较
			 if(d_a >= d_b){
				 $.messager.alert("提示", "开始时间应小于结束时间!");
				 winform.oneClick(function(){ frm.submit(); });
				 return false;
			 }
			 if(stime_ >= etime_){
				 $.messager.alert("提示", "报名开始时间应小于报名结束时间!");
				 winform.oneClick(function(){ frm.submit(); });
				 return false;
			 }
			 if(stime_ >= d_b){
				 $.messager.alert("提示", "报名开始时间应小于培训结束时间!");
				 winform.oneClick(function(){ frm.submit(); });
				 return false;
			 }
			 if(etime_ >= d_b){
				 $.messager.alert("提示", "报名结束时间应小于培训结束时间!");
				 winform.oneClick(function(){ frm.submit(); });
				 return false;
			 }
			 //团队或者个人必须选择一个
			 if ($("#traisenrol").combobox('getValue') == 1) {
			 var gr = $("#tracanperson").combobox('getValue');
			 var td = $("#tracanteam").combobox('getValue');
			 if (gr == 0 && td == 0) {
				$.messager.alert("提示", "必须选择是个人或者团队报名!");
				winform.oneClick(function(){ frm.submit(); });
				return false;
			}
			}
			//设置富文本的内容
			$('#tradetail').val(UE.getEditor('traeditor').getContent());
			$('#tracatalog').val(UE.getEditor('catalog').getContent());
			var isSubmit = $(this).form('enableValidation').form('validate');
			if (!isSubmit) {
				winform.oneClick(function(){ frm.submit(); });
			}
			return isSubmit;
		},
		success : function(data) {
			//alert(JSON.stringify(data));
			var Json = $.parseJSON(data);
	 			if(Json && Json.success == '0'){
	 			$.messager.alert('提示', '操作成功！');
				$('#train').datagrid('reload');
				
				winform.closeWin();
			} else {
				$.messager.alert('提示', '修改失败,操作无效！');
				winform.oneClick(function(){ frm.submit(); });
			}
		}
	});
	//格式化日期
	var _data = $.extend({}, row,
			{
		trasdate : datetimeFMT(row.trasdate),
		traedate : datetimeFMT(row.traedate),
		traenrolstime : datetimeFMT(row.traenrolstime),	
		traenroletime : datetimeFMT(row.traenroletime),
	});
	
	frm.form("clear");
	frm.form("load", _data);
	/*winform.setFoolterBut({onClick:function(){
		frm.submit();
			}});*/
	winform.oneClick(function(){ frm.submit(); });
	UE.getEditor('traeditor');
}
 
 /**删除培训
 */
function delTrain(index){
	var row = WHdg.getRowData(index);
	var traid = row.traid;
	var stime = row.trasdate;
	var etime = row.traedate;
	var _stime = new Date(stime); 
	var _etime = new Date(etime); 
	var now =  new Date();
	/*if (_stime < now && _etime > now) {
		 $.messager.alert('提示', '培训正在进行，不能删除！');
		 return;
	}else{*/
		$.messager.confirm('确认对话框', '您确认要删除吗？', function(r){
			if (r){
				$.ajax({
					type: "POST",
					url: getFullUrl('/admin/trainManage/deltrain'),
					data: {traid : traid},
					success: function(data){
					//	alert(JSON.stringify(data));
						if(data.success == '0'){
							$('#train').datagrid('reload');
						   }else{
							   $.messager.alert('提示', '操作失败。原因：'+data.errmsg, 'error');
						   }
					}
				});
			}
		});
	/*}*/
}
 
/**
 * 判断是否能够进行送审
 */
function sendCheck(index){
	var row = WHdg.getRowData(index);
	var traid = row.traid;
	var trastate = row.trastate;
	$.ajax({
		type : "post",
		url : getFullUrl('/admin/trainManage/isCheck'),
		data : {traid : traid},
		success :  function(data){
			//alert(JSON.stringify(data));
			if (data == 0 || data == null) {
				$.messager.alert("提示","没有设置课程表，不能送审！");
				return ;
			}else{
				var trastate = row.trastate;
				$.messager.confirm('确认对话框', '您确认将所选择的送去审核吗？', function(r){
					if (r){
						$.ajax({
							type: "POST",
							url: getFullUrl('/admin/trainManage/passTrain'),
							data: {traid : traid,trastate: trastate},
							success: function(data){
								if(data.success == '0'){
									$('#train').datagrid('reload');
								   }else{
									   $.messager.alert('提示', '操作失败。原因：'+data.errmsg, 'error');
								   }
							}
						});
					}
				});
				
			}
		}
	});
}
 	


//处理编辑时显示图片
function _showImg(data){
	$(".img_div").remove();
	$(".img_div1").remove();
	document.getElementById('fileName').innerHTML = "";	
	document.getElementById('fileName_').innerHTML = "";
	
	var imgDiv = '<div class="row img_div">'
		+'<div></div>'
		+'<div><img width="200" height="150"> </div> '
		+'</div>';
	var imgDiv1 = '<div class="row img_div1">'
		+'<div></div>'
		+'<div><img width="200" height="150"> </div> '
		+'</div>';
		
	if (data.trapic){
		var imgrow = $("[name$='trapic_up']").parents(".row");
		imgrow.after(imgDiv);
		imgrow.next(".img_div").find("div img").attr("src",getFullUrl(data.trapic));	
	}
	if (data.trabigpic){
		var imgrow = $("[name$='trabigpic_up']").parents(".row");
		imgrow.after(imgDiv1);
		imgrow.next(".img_div1").find("div img").attr("src",getFullUrl(data.trabigpic));	
	}
	if (data.trapersonfile) {
		document.getElementById('fileName').innerHTML = "<span style='color:Blue'>" +data.trapersonfile+ "</span>";
	}
	if (data.trateamfile) {
		document.getElementById('fileName_').innerHTML = "<span style='color:Blue'>" +data.trateamfile+"</span>";
	}
	if (data.trapersonfile) {
		document.getElementById('trapersonfile_up1').innerHTML = "<span style='color:Blue'>" +data.trapersonfile+ "</span>";
	}
	if (data.trateamfile) {
		document.getElementById('trateamfile_up1').innerHTML = "<span style='color:Blue'>" +data.trateamfile+"</span>";
	}
	 
}

/**
 * 一键审核
 */
function allCheck(){
	var rows={};
	rows = $("#train").datagrid("getSelections");
	if (rows == "" || rows == null) {
		$.messager.alert('提示', '请选择要操作的数据！');
		return;
	}
	var traids = _split = "";//id1,id2,id3
	for (var i = 0; i<rows.length; i++){
		traids += _split+rows[i].traid;
		_split = ",";
	}
	//alert(JSON.stringify(traids));
	$.messager.confirm('确认对话框', '您确认将所选择的送去审核吗？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/trainManage/checkTrain'),
				data: {traid : traids,fromstate: 0, tostate:1,iss : "yes"},
				success: function(data){
					if(data.success=="success"){
						$.messager.alert("提示", data.msg);
						$('#train').datagrid('reload');
					   }else{
						   $.messager.alert('提示', '操作失败。原因：'+data.err, 'error');
					   }
				}
			});
		}
	});
}

/**
 * 课程表
 * @param index
 * @returns
 */
function tokecheng(index){
	var rows = $('#train').datagrid('getRows');
	var row = rows[index];//WHdg2.getRowData(index);
	var traid = row.traid;
	//datetimeFMT(row.trasdate),
	var sdate = datetimeFMT(row.trasdate);
	var edate = datetimeFMT(row.traedate);
	$('#dd iframe').attr('src', getFullUrl('/admin/train/kecheng?tra_s_date='+sdate+'&tra_e_date='+edate+'&traid='+traid+'&type='));
	$('#dd').dialog({    
	    title: '课程表',  
	    modal:true,
	    maximizable: true,
	    maximized: true,
	    width: 400,
	    height: 200   
	}); 
}

/**
 * 添加资源
 * @param index
 * @returns
 */
function addzy(index){
	var rows = $('#train').datagrid('getRows');
	var row = rows[index];//WHdg2.getRowData(index);
	var traid = row.traid;
	
	$('#addzy iframe').attr('src', getFullUrl("/admin/ent/entsrc?reftype=2016101400000051&refid="+traid+"&canEdit=true"));
	$('#addzy').dialog({    
	    title: '添加资源',  
	    modal:true,
	    maximizable: true,
	    maximized: true,
	    width: 400,
	    height: 200   
	}); 
}
/**
 * 导入模板
 * @returns
 */
function addTraintpl(){
	//
//	var tplId = $('#tplid').combobox('getValue');
	var tplId = $('#enrtraid').combogrid('getValue');
	//
	if (tplId == "") {
		$.messager.alert('提示','请选择模板！');
		return;
	}
	$.ajax({
		url: getFullUrl('/admin/trainManage/getTratpl'),
		type: 'post',
		data: {"tratplid": tplId},
		success: function(data){
			delete data.traid;
			$('#ff').form('load',{
				tratyp : data.tratyp,
				traagelevel : data.traagelevel,
				tralevel :data.tralevel,
				traaddress : data.traaddress|| "",
				traarttyp : data.traarttyp|| "",
				traarea : data.traarea|| "",
				tratitle : data.tratitle|| "",
				trashorttitle : data.trashorttitle|| "",
				tratags : data.tratags || "",
				trakeys : data.trakeys || "",
				trateacherid : data.trateacherid|| "",
				trateacherdesc : data.trateacherdesc|| "",
				traislogincomment : data.traislogincomment,
				traisenrol :data.traisenrol,
				traenroldesc :data.traenroldesc|| "",
				traenrollimit : data.traenrollimit|| "",
				traisenrolqr : data.traisenrolqr,
				traisnotic : data.traisnotic,
				traisrealname : data.traisrealname,
				traisfulldata : data.traisfulldata,
				traisonlyone: data.traisonlyone,
				traisattach : data.traisattach,
				tracanperson : data.tracanperson,
				ismoney : data.ismoney,
				tracanteam : data.tracanteam,
				traintroduce : data.traintroduce|| "",
				trasdate : datetimeFMT(data.trasdate)|| "",
				traedate : datetimeFMT(data.traedate)|| "",
				traenrolstime : datetimeFMT(data.traenrolstime)|| "",	
				traenroletime : datetimeFMT(data.traenroletime)|| "",
				trapic : data.trapic|| "",
				trabigpic : data.trabigpic|| "",
				trapersonfile : data.trapersonfile || "",
				trateamfile : data.trateamfile || "",
				traphone : data.traphone,
				tracontact : data.tracontact,
			});
			_showImg(data);
			
			//显示富文本的值
			UE.getEditor('traeditor').setContent(data.tradetail);
			UE.getEditor('catalog').setContent(data.tracatalog);
			//当报名按钮值输入时改变其他框的状态
			if (data.traisenrol == "0") {
				$("#traenroldesc").textbox('disable');
				$("#traenrolstime").datetimebox('disable');
				$("#traenroletime").datetimebox('disable');
				$("#traenrollimit").numberspinner('disable');
				$("#traisenrolqr").combobox('disable');
				$("#traisnotic").combobox('disable');
				$("#ismoney").combobox('disable');
				
				$("#traisrealname").combobox('disable');
				$("#traisfulldata").combobox('disable');
				$("#traisonlyone").combobox('disable');
				$("#traisattach").combobox('disable');
				
				$("#tracanperson").combobox('disable');
				$("#trapersonfile_up").filebox('disable');
				$("#tracanteam").combobox('disable');
				$("#trateamfile_up").filebox('disable');
			}
			if (data.traisattach == 1) {
				$("#trapersonfile_up").filebox('enable');
				$("#trateamfile_up").filebox('enable');
				$("#trapersonfile_up").filebox({required:false});
				$("#trateamfile_up").filebox({required:false});
			}
		}
		 
	});
	
}
/**
 * 查看详情
 */
 function look(index){
	var row = WHdg.getRowData(index);
	winform1.openWin();
	winform1.setWinTitle("查看详情");
	winform1.getWinFooter().find("a:eq(0)").hide();
	setDisabled();
	//显示富文本的值
	UE.getEditor('traeditor1').setContent(row.tradetail);
	UE.getEditor('catalog1').setContent(row.tracatalog);
	var imgDiv = '<div class="row img_div">'
		+'<div></div>'
		+'<div><img width="200" height="150"> </div> '
		+'</div>';
	var imgDiv1 = '<div class="row img_div1">'
		+'<div></div>'
		+'<div><img width="200" height="150"> </div> '
		+'</div>';
		
	_showImg(row);
	
	var frm = winform1.getWinBody().find('form').form({
	});
	//格式化日期
	var _data = $.extend({}, row,{
		trasdate : datetimeFMT(row.trasdate),
		traedate : datetimeFMT(row.traedate),
		traenrolstime : datetimeFMT(row.traenrolstime),	
		traenroletime : datetimeFMT(row.traenroletime),
	});
	frm.form("clear");
	frm.form("load", _data);
	$('#train').datagrid('reload');
}
 /**
  * 初始培训批次表格
  */
 $(function(){
 	//定义表格参数
	var options = {
			title: '培训管理',	
			url: getFullUrl('/admin/trainManage/selectTrain'),
			queryParams: {stateArray:"0"},
			rownumbers:true,
			singleSelect:false,
			columns: [[
			{field:'ck',checkbox:true},
			{field:'tratitle',title:'课程标题',width:80, sortable:true},
			{field:'tratyp',title:'培训类型',width:80, sortable:true, formatter: tratypFMT},
			{field:'traarttyp',title:'艺术类型',width:80, sortable:true, formatter: arttypFMT},
		//	{field:'traarea',title:'区域',width:80, sortable:true, formatter: areaFMT},
			{field:'traagelevel',title:'适合年龄',width:80, sortable:true, formatter:agelevelFMT},    
			{field:'tralevel',title:'课程级别',width:80, sortable:true, formatter:tralevelFMT},
			{field:'trapic',title:'列表图',width:80, sortable:true, formatter:imgFMT},
			{field:'trabigpic',title:'详细页图片',width:80, sortable:true, formatter:imgFMT},
			{field:'trasdate',title:'开始日期',width:200,formatter :datetimeFMT,sortable:true},
			{field:'traedate',title:'结束日期',width:200,formatter :datetimeFMT, sortable:true},
			   
			
			{field:'trateacher',title:'授课老师名称',width:80},
		//	{field:'traaddress',title:'详细地址',width:80}, 
	        {field:'trastate',title:'状态',width:80,formatter :traStateFMT},
	        {field:'opt', title:'操作', formatter: WHdg.optFMT, optDivId:'trainOPT'}
			]]
	};

	//初始表格
	WHdg.init('train', 'trainTool', options);
	
	//初始弹出框
	winform.init();
	winform1.init();
	
	//初始富文本
	UE.getEditor('traeditor');
	UE.getEditor('catalog');
	//
	UE.getEditor('traeditor1');
	UE.getEditor('catalog1');
	
	
	//清除文件框的值
	$('#btn_pic').bind('click', function(){  
		$("#trapic_").filebox('clear');
    });    
	$('#btn_pic1').bind('click', function(){  
		$("#trabigpic_up").filebox('clear');
	});    
	$('#btn_pic2').bind('click', function(){  
		$("#trapersonfile_up").filebox('clear');
	});    
	$('#btn_pic3').bind('click', function(){  
		$("#trateamfile_up").filebox('clear');
	});    
	
	//导入模板的数据表格
	var option = {
			delay: 800,    
		    mode: 'remote',
			title: '培训模板',			
			url: getFullUrl('/admin/trainMould/findTraintpl'),
			idField: 'tratplid',    
		    textField: 'tratitle',
			pagination: true,
		    pageSize: 10,
		    pageList: [10,20,50],
			columns: [[
			    {field:'tratitle',title:'课程标题',width:80, sortable:true},
				{field:'tratyp',title:'培训类型',width:80, sortable:true, formatter: tratypFMT},
				{field:'traarttyp',title:'艺术类型',width:80, sortable:true, formatter: arttypFMT},
				{field:'traarea',title:'区域',width:80, sortable:true, formatter: areaFMT},
				{field:'traagelevel',title:'适合年龄',width:80, sortable:true, formatter:agelevelFMT},    
				{field:'tralevel',title:'课程级别',width:80, sortable:true, formatter:tralevelFMT},
				
//				{field:'trapic',title:'列表图',width:80, sortable:true, formatter:imgFMT},
//				{field:'trabigpic',title:'详细页图片',width:80, sortable:true, formatter:imgFMT},
				{field:'trasdate',title:'开始日期',width:150,formatter :datetimeFMT, sortable:true},
				{field:'traedate',title:'结束日期',width:150,formatter :datetimeFMT, sortable:true},
				   
				
				{field:'trateacher',title:'授课老师名称',width:80}
			//	{field:'traaddress',title:'详细地址',width:150,}, 
			]],
			toolbar: "#testdiv"
		};
	 $('#enrtraid').combogrid(option); 
	 
	 $("#testdiv a.search").on("click", function(){
		 var _data = $("#testdiv").find("[name]").serializeArray();
		 var params = new Object();
		 for(var k in _data){
			 var ent = _data[k];
			 if (ent.value)
			 params[ent.name] = ent.value;
		 }
		 
		 var grid = $('#enrtraid').combogrid("grid");
		 grid.datagrid({
			 url: getFullUrl('/admin/trainMould/findTraintpl'),
			 queryParams: params
		 });
	 })

	//设置老师介绍
	 $("#trateacherid").combobox({
		 onSelect: function(r){
			 //alert(JSON.stringify(r));
			 var trateacherdesc = r.teacherintroduce;
			 $("#trateacherdesc").textbox('setValue',trateacherdesc);
			 $("#trateacher").val(r.teachername);
			 
		 }
	 })
 });
 //
	 