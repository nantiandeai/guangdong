var winform = new WhuiWinForm("#tra_edit",{height:600});
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
function forbide() {
	if ($("#tracanperson").combobox('getValue') == "0") {
		$("#trapersonfile_up").filebox('disable');
	}else{
		$("#trapersonfile_up").filebox('enable');
	}
}
function forbide1() {
	if ($("#tracanteam").combobox('getValue') == "0"){
		$("#trateamfile_up").filebox('disable');
	}else{
		$("#trateamfile_up").filebox('enable');
	}
}
/*修改是改变文件框**/
function editperFJ() {
	if ($("#traisattach").combobox('getValue') == "0") {
		$("#tracanperson_up").filebox('disable');
		$("#trapersonfile_up").filebox({required:false});
	}else{
		if ($("#tracanperson").combobox('getValue') == "0"){
			$("#trapersonfile_up").filebox('disable');
			$("#trapersonfile_up").filebox({required:false});
		}else{
			$("#tracanperson_up").filebox('enable');
			$("#trapersonfile_up").filebox({required:true});
		}
	}
}
function _editteamFJ() {
	if ($("#traisattach").combobox('getValue') == "0")
	{
		$("#trateamfile_up").filebox('disable');
		$("#trateamfile_up").filebox({required:false});
	}else{
		if ($("#tracanteam").combobox('getValue') == "0"){
			$("#trateamfile_up").filebox('disable');
			$("#trateamfile_up").filebox({required:false});
		}else{
			$("#trateamfile_up").filebox('enable');
			$("#trateamfile_up").filebox({required:true});
		}
	} 
	
}/*修改是改变文件框 end**/

//是否需要上传附件
function isFJ(){
	if ($("#traisattach").combobox('getValue') == "0") {
		$("#trapersonfile_up").filebox('disable');
		$("#trateamfile_up").filebox('disable');
	}else {
		$("#trapersonfile_up").filebox('disable');
		$("#trateamfile_up").filebox('disable');
		$("#trapersonfile_up").filebox({required:true});
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
		/*$("#trapersonfile_up").filebox({required:false});
		$("#trateamfile_up").filebox({required:false});*/
	}
}
 /** 添加培训模板 */
function addTra(){
	UE.getEditor('traeditor').setContent("");
	UE.getEditor('catalog').setContent("");
	winform.openWin();
	winform.setWinTitle("添加培训模板");
	clearCombobox();
	$("#ff").form('clear');
	//当报名按钮值输入时改变其他框的状态
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
		onChange:isFJ
	});//控制
	//移除修改时显示的图片以及文件
	$(".img_div").remove();
	$(".img_div1").remove();
	document.getElementById('fileName').innerHTML = "";	
	document.getElementById('fileName_').innerHTML = "";
	//
	var frm = winform.getWinBody().find('form').form({
		url : getFullUrl('/admin/trainMould/save'),
		
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
			var issub = $(this).form('enableValidation').form('validate');
			if (!issub) {
				winform.oneClick(function(){ frm.submit(); });
			}
            return issub;
        },
		success : function(data) {
			var Json = $.parseJSON(data);
 			if(Json && Json.success == '0'){
				$.messager.alert('提示', '操作成功!');
				$('#traintpl').datagrid('reload');
				winform.closeWin();
			}else{
				$.messager.alert('提示', '操作失败！');
				winform.oneClick(function(){ frm.submit(); });
				//alert(JSON.stringify(data));
			}
		}
	});
	frm.form("clear");
	winform.oneClick(function(){ frm.submit(); });
//	winform.setFoolterBut({onClick:function(){
//		frm.submit();
//	}});
}

function clearCombobox(){
	$("#trakeys").combobox("clear");
	$("#tratags").combobox("clear");
}
 /**
  * 修改培训模板
  * @param index 表格列索引
  * @returns
  */
 function _updTrain(index){
 	var row = WHdg2.getRowData(index);
 	var traid = row.traid;
	winform.openWin();
	winform.setWinTitle("修改培训模板");
	clearCombobox();
	//当报名按钮值输入时改变其他框的状态
	if (row.traisenrol == "0") {
		$("#traenroldesc").textbox('disable');
		$("#traenrolstime").datetimebox('disable');
		$("#traenroletime").datetimebox('disable');
		$("#traenrollimit").numberspinner('disable');
		$("#traisenrolqr").combobox('disable');
		$("#traisnotic").combobox('disable');
		
		$("#traisrealname").combobox('disable');
		$("#traisfulldata").combobox('disable');
		$("#traisonlyone").combobox('disable');
		$("#traisattach").combobox('disable');
		
		$("#tracanperson").combobox('disable');
		$("#trapersonfile_up").filebox('disable');
		$("#tracanteam").combobox('disable');
		$("#trateamfile_up").filebox('disable');
	}
	//当报名按钮值输入时改变其他框的状态
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
	if (!row.trapersonfile) {
		$("#tracanperson").combobox({
			onChange:editperFJ
		});
	}
	if (!row.trateamfile) {
		$("#tracanperson").combobox({
			onChange:_editteamFJ
		});
		//$("#trateamfile_up").filebox({required:true});
	}
	//显示富文本的值
	UE.getEditor('traeditor').setContent(row.tradetail);
	UE.getEditor('catalog').setContent(row.tracatalog);
	
	_showImg(row);
	var frm = winform.getWinBody().find('form').form({
		url : getFullUrl('/admin/trainMould/save'),
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
			var issub = $(this).form('enableValidation').form('validate');
			if (!issub) {
				winform.oneClick(function(){ frm.submit(); });
			}
			return issub;
		},
		success : function(data) {
			//alert(JSON.stringify(data));
			var Json = $.parseJSON(data);
	 		if(Json && Json.success == '0'){
	 			$.messager.alert('提示', '操作成功！');
				$('#traintpl').datagrid('reload');
				
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
	winform.oneClick(function(){ frm.submit(); });
//	winform.setFoolterBut({onClick:function(){
//		frm.submit();
//	}});
	//UE.getEditor('traeditor');
	
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
		document.getElementById('fileName').innerHTML="<span style='color:Blue'>" +data.trapersonfile+ "</span>";
	}
	if (data.trateamfile) {
		document.getElementById('fileName_').innerHTML="<span style='color:Blue'>" +data.trateamfile+"</span>";
	}
}
/**
 * 删除培训模板
 * @param index 表格列索引
 * @returns
 */
function _delTrain(index){
	var row = WHdg2.getRowData(index);
	var tratplid = row.tratplid;
	$.messager.confirm('确认对话框', '您确认要删除吗？', function(r){
		if (r){
			$.ajax({
			   type: "POST",
			   url: getFullUrl("/admin/trainMould/deltraintpl"),
			   data: {tratplid : tratplid },
			   success: function(data){
				   if(data.success == '0'){
					   $("#traintpl").datagrid('reload');
				   }else{
					   $.messager.alert('提示', '操作失败。原因：'+data.errmsg, 'error');
				   }
				 //  alert(JSON.stringify(success));
			   }
			});
		}
	});
}

/** 页面加载完成后的动作 */
$(function(){
	/** 1.初始表格tra */
	//定义表格参数
	var options = {
		title: '培训模板',			
		url: getFullUrl('/admin/trainMould/findTraintpl'),
		columns: [[
		    {field:'tratitle',title:'课程标题',width:80, sortable:true},
			{field:'tratyp',title:'培训类型',width:80, sortable:true, formatter: tratypFMT},
			{field:'traarttyp',title:'艺术类型',width:80, sortable:true, formatter: arttypFMT},
			{field:'traarea',title:'区域',width:80, sortable:true, formatter: areaFMT},
			{field:'traagelevel',title:'适合年龄',width:80, sortable:true, formatter:agelevelFMT},    
			{field:'tralevel',title:'课程级别',width:80, sortable:true, formatter:tralevelFMT},
			
			{field:'trapic',title:'列表图',width:80, sortable:true, formatter:imgFMT},
			{field:'trabigpic',title:'详细页图片',width:80, sortable:true, formatter:imgFMT},
			{field:'trasdate',title:'开始日期',width:150,formatter :datetimeFMT, sortable:true},
			{field:'traedate',title:'结束日期',width:150,formatter :datetimeFMT, sortable:true},
			   
			
			{field:'trateacher',title:'授课老师名称',width:80,},
		//	{field:'traaddress',title:'详细地址',width:150,}, 
			{field:'opt', title:'操作', formatter: WHdg2.optFMT, optDivId:'traintpl_opt'}
		]]
	};
	
	//初始表格
	WHdg2.init('traintpl', 'traintpl_tb', options);
	//初始弹出框
	winform.init();
	
	//初始富文本
	UE.getEditor('traeditor');
	UE.getEditor('catalog');
	
	//清除文件框的值
	$('#btn_pic').bind('click', function(){  
		$("#trapic_up").filebox('clear');
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
	
	//设置老师介绍
	 $("#trateacherid").combobox({
		 onSelect: function(r){
			 //alert(JSON.stringify(r));
			 var trateacherdesc = r.teacherintroduce;
			 $("#trateacherdesc").textbox('setValue',trateacherdesc);
			 $("#trateacher").val(r.teachername);
		 }
	 })
	
});/** END页面加载完成后的动作 */