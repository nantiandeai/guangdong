var winform = new WhuiWinForm("#tra_edit",{height:600});
/**
 * whtra script
 */
 /** 添加培训模板 */
 function addTra(){
	 UE.getEditor('traeditor').setContent("");
	 UE.getEditor('catalog').setContent("");
	winform.openWin();
	winform.setWinTitle("添加培训模板");
	$(".img_div").remove();
	$("#ff").form('clear');
	var frm = winform.getWinBody().find('form').form({
		url : getFullUrl('/admin/traintpl/savetratpl'),
		
		onSubmit : function(param){
			//设置富文本的内容
			$('#tradesc').val(UE.getEditor('traeditor').getContent());
			$('#tracatalog').val(UE.getEditor('catalog').getContent());
            return $(this).form('enableValidation').form('validate');
        },
		success : function(data) {
			var Json = $.parseJSON(data);
 			if(Json && Json.success == '0'){
				$.messager.alert('提示', '操作成功!');
				$('#tra').datagrid('reload');
				winform.closeWin();
			}else{
				$.messager.alert('提示', '操作失败,此模板正在被使用！');
				//alert(JSON.stringify(data));
			}
		}
	});
	frm.form("clear");
	winform.setFoolterBut({onClick:function(){
		frm.submit();
	}});
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
	//显示富文本的值
	UE.getEditor('traeditor').setContent(row.tradesc);
	UE.getEditor('catalog').setContent(row.tracatalog);
	_showImg(row);
	var frm = winform.getWinBody().find('form').form({
		url : getFullUrl('/admin/traintpl/savetratpl'),
		onSubmit : function(param) {
			//设置富文本的内容
			$('#tradesc').val(UE.getEditor('traeditor').getContent());
			$('#tracatalog').val(UE.getEditor('catalog').getContent());
			return $(this).form('enableValidation').form('validate');
		},
		success : function(data) {
			//alert(JSON.stringify(data));
			var Json = $.parseJSON(data);
	 		if(Json && Json.success == '0'){
	 			$.messager.alert('提示', '操作成功！');
				$('#tra').datagrid('reload');
				
				winform.closeWin();
			} else {
				$.messager.alert('提示', '修改失败,操作无效！');
			}
		}
	});
	
	frm.form("clear");
	frm.form("load", row);
	winform.setFoolterBut({onClick:function(){
		frm.submit();
	}});
	//UE.getEditor('traeditor');
}
//处理编辑时显示图片
	function _showImg(data){
		$(".img_div").remove();
		
		var imgDiv = '<div class="row img_div">'
			+'<div></div>'
			+'<div><img width="200" height="150"> </div> '
			+'</div>';
		
		if (data.trapic){
			var imgrow = $("[name$='trapic_up']").parents(".row");
			imgrow.after(imgDiv);
			imgrow.next(".img_div").find("div img").attr("src",getFullUrl(data.trapic));	
		}
		if (data.trapic1){
			var imgrow = $("[name$='trapic_home']").parents(".row");
			imgrow.after(imgDiv);
			imgrow.next(".img_div").find("div img").attr("src",getFullUrl(data.trapic1));	
		}
		if (data.trapic2){
			var imgrow = $("[name$='trapic_de']").parents(".row");
			imgrow.after(imgDiv);
			imgrow.next(".img_div").find("div img").attr("src",getFullUrl(data.trapic2));	
		}
		if (data.userfile){
			var imgrow = $("[name$='userfile']").parents(".row");
			imgrow.after(imgDiv);
			imgrow.next(".img_div").find("div img").attr("src",getFullUrl(data.userfile));	
		}
		if (data.groupfile){
			var imgrow = $("[name$='groupfile']").parents(".row");
			imgrow.after(imgDiv);
			imgrow.next(".img_div").find("div img").attr("src",getFullUrl(data.groupfile));	
		}
	}
/**
 * 删除培训模板
 * @param index 表格列索引
 * @returns
 */
function _delTrain(index){
	var row = WHdg2.getRowData(index);
	var traid = row.traid;
	var traitmid = row.traitmid;
	$.messager.confirm('确认对话框', '您确认要删除吗？', function(r){
		if (r){
			$.ajax({
			   type: "POST",
			   url: getFullUrl("/admin/traintpl/deltratpl"),
			   data: {traid : traid },
			   success: function(data){
				   if(data && data.success == '0'){
					   $("#tra").datagrid('reload');
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
		url: getFullUrl('/admin/traintpl/sreachTraintpl'),
		columns: [[
		    {field:'title',title:'培训模板', sortable:true},
			{field:'tratyp',title:'培训类型', sortable:true, formatter: tratypFMT},
			{field:'arttyp',title:'艺术类型', sortable:true, formatter: arttypFMT},
			{field:'area',title:'区域', sortable:true, formatter: areaFMT},
			
			{field:'trapic',title:'封面图片', sortable:true, formatter:imgFMT},
			{field:'trapic1',title:'首页图片', sortable:true, formatter:imgFMT},
			{field:'trapic2',title:'明细页图片', sortable:true, formatter:imgFMT},
			{field:'teacher',title:'授课老师'},
			//{field:'teacherid',title:'授课老师标识'},    
			{field:'isrealname',title:'必须实名', sortable:true, formatter:yesNoFMT},    
			{field:'isfulldata',title:'必须完善资料', sortable:true, formatter:yesNoFMT},
			{field:'isonlyone',title:'1人1项限制', sortable:true, formatter:yesNoFMT},    
			{field:'islogincomment',title:'须登录点评', sortable:true, formatter:yesNoFMT},
			{field:'isattach',title:'需上传附件', sortable:true, formatter:yesNoFMT},
			{field:'opt', title:'操作', formatter: WHdg2.optFMT, optDivId:'tra_opt'}
		]]
	};
	
	//初始表格
	WHdg2.init('tra', 'tra_tb', options);
	//初始弹出框
	winform.init();
	
	//初始富文本
	UE.getEditor('traeditor');
	UE.getEditor('catalog');
	
	/** 2.注册点击添加培训模板事件 */
//	$('#btn_addtra').unbind('click.add').bind('click.add', function(e){
//		window.location.href = getFullUrl('/admin/traintpl/gopage');
//	});/** END-2.注册点击添加培训模板事件 */
	
	
});/** END页面加载完成后的动作 */