
var winform = new WhuiWinForm("#ff",{height:250});

/**
 * 返回按钮
 * @returns
 */
function back(){
 	$("#dlg").dialog('close');
 	$("#resdlg").dialog('close');
}

/**
 * 
 * @param newVal 新值
 * @param oldVal 老值
 */
function _setTitle(newVal, oldVal){
	var text = $("#xx_traid").combobox('getText');
	$("#tratitle").textbox('setValue', text);
}

/**
 * 添加培训
 */
function _addTrain(index){
	UE.getEditor('traeditor').setContent("");
	winform.openWin();
	winform.setWinTitle("添加培训");
	var row = WHdg.getRowData(index);
	var tratitle = row.title;
	var traid = row.traid;
	var frm = winform.getWinBody().find('form').form({
		url : getFullUrl('/admin/train/addTraitm'),
		onSubmit : function(param){
			 //得到当前的输入值
			 var a = $('#sdate').datetimebox('getValue');
			 var b = $('#edate').datetimebox('getValue');
			 var s = $('#stime').datetimebox('getValue');
			 var e = $('#etime').datetimebox('getValue');
			 //转换时间格式
			 var d_a = new Date(a); 
			 var d_b = new Date(b);
			 var stime_ = new Date(s);
			 var etime_ = new Date(e); 
			 //时间比较
			 if(d_a >= d_b){
				 $.messager.alert("提示", "开始时间应小于结束时间!");
				 return flase;
			 }
			 if(stime_ >= etime_){
				 $.messager.alert("提示", "报名开始时间应小于报名结束时间!");
				 return flase;
			 }
			//富文本的值赋给报名介绍
			$('#enroldesc').val(UE.getEditor('traeditor').getContent());
            return $(this).form('enableValidation').form('validate');
        },
		success : function(data) {
			var Json = $.parseJSON(data);
			if(Json && Json.success == "0"){
				$('#trainitm').datagrid('reload');
				$.messager.alert('提示', '操作成功!');
				winform.closeWin();
			   }else{
				   $.messager.alert('提示', '操作失败!');
			   }
		}
	});
	frm.form("clear");
	winform.setFoolterBut({onClick:function(){
		frm.submit();
	}});
 }
 
/**
 * 修改培训批次
 */
function editTrain(index) {
	var row = WHdg.getRowData(index);
	if (row.state != "0") {
		$.messager.alert("提示", "不能符合修改要求！");
		return;
	}
	//alert(JSON.stringify(row.enroldesc));
	winform.openWin();
	winform.setWinTitle("修改培训");
	//显示富文本的值
	UE.getEditor('traeditor').setContent(row.enroldesc);
	
	var frm = winform.getWinBody().find('form').form({
		url : getFullUrl('/admin/train/updateTraitm'),
		onSubmit : function(param) {
			$('#enroldesc').val(UE.getEditor('traeditor').getContent());
			return $(this).form('enableValidation').form('validate');
		},
		success : function(data) {
			//alert(JSON.stringify(data));
			var Json = $.parseJSON(data);
	 			if(Json && Json.success == '0'){
	 			$.messager.alert('提示', '操作成功！');
				$('#trainitm').datagrid('reload');
				
				winform.closeWin();
			} else {
				$.messager.alert('提示', '修改失败,操作无效！');
			}
		}
	});
	//格式化日期
	var _data = $.extend({}, row,
			{
		sdate : datetimeFMT(row.sdate),
		edate : datetimeFMT(row.edate),
		enrolstime : datetimeFMT(row.enrolstime),	
		enroletime : datetimeFMT(row.enroletime),
	});
	frm.form("clear");
	frm.form("load", _data);
	winform.setFoolterBut({onClick:function(){
		frm.submit();
			}});
	UE.getEditor('traeditor');
}
 
 /**删除培训
 */
function delTrain(index){
	var row = WHdg.getRowData(index);
	var traitmid = row.traitmid;
	$.messager.confirm('确认对话框', '您确认要删除吗？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/train/delTrain'),
				data: {traitmid : traitmid},
				success: function(data){
				//	alert(JSON.stringify(data));
					if(data.success == '0'){
						$('#trainitm').datagrid('reload');
					   }else{
						   $.messager.alert('提示', '操作失败。原因：'+data.errmsg, 'error');
					   }
				}
			});
		}
	});
}
 

 	
/**
 * 送审
 */
function sendCheck(index){
	var row = WHdg.getRowData(index);
	var traitmid = row.traitmid;
	var state = row.state;
	$.messager.confirm('确认对话框', '确定送去审核？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/train/checkTraitm'),
				data: {traitmid : traitmid,state : state},
				success: function(msg){
				$('#trainitm').datagrid('reload');
				}
			});
		}
	}); 
}

/**
 * 初始培训批次表格
 */
$(function(){
	//定义表格参数
	var options = {
			title: '培训管理',	
			url: getFullUrl('/admin/train/sreachAllTrain'),
			queryParams: {stateArray:"0"},
			columns: [[
			{field:'title',title:'培训模板'},
	        {field:'tratitle',title:'培训名称'},
	        {field:'sdate',title:'开始时间',formatter :datetimeFMT},    
	        {field:'edate',title:'结束时间',formatter :datetimeFMT},
	        {field:'isenrol',title:'是否需要报名',formatter : yesNoFMT}, 
	        {field:'enrolstime',title:'报名开始时间',formatter :datetimeFMT},    
	        {field:'enroletime',title:'报名结束时间',formatter :datetimeFMT},  
	        {field:'isenrolqr',title:'报名是否需要审核',formatter : yesNoFMT}, 
	       
	        {field:'enrollimit',title:'报名人数限制'}, 
	        {field:'isnotic',title:'是否需要面试通知',formatter : yesNoFMT},
	        {field:'state',title:'状态',formatter :traStateFMT},
	        {field:'opt', title:'操作', formatter: WHdg.optFMT, optDivId:'trainitmOPT'}
			]]
	};
	//初始表格
	WHdg.init('trainitm', 'traitmTool', options);
	//初始弹出框
	winform.init();
	
	//初始富文本
	UE.getEditor('traeditor');
});


/**
 * 课程表
 * @param index
 * @returns
 */
function tokecheng(index){
	var rows = $('#trainitm').datagrid('getRows');
	var row = rows[index];//WHdg2.getRowData(index);
	var traitmid = row.traitmid;
	var sdate = row.sdate;
	var edate = row.edate;
	$('#dd iframe').attr('src', getFullUrl('/admin/train/kecheng?sdate='+sdate+'&edate='+edate+'&traitmid='+traitmid+'&type='));
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
	var rows = $('#trainitm').datagrid('getRows');
	var row = rows[index];//WHdg2.getRowData(index);
	var traitmid = row.traitmid;
	
	$('#addzy iframe').attr('src', getFullUrl("/admin/ent/entsrc?reftype=2016101400000051&refid="+traitmid));
	$('#addzy').dialog({    
	    title: '添加资源',  
	    modal:true,
	    maximizable: true,
	    maximized: true,
	    width: 400,
	    height: 200   
	}); 
}


	