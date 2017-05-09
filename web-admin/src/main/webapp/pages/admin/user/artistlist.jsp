<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + request.getContextPath();
    request.setAttribute("basePath", basePath);
%>
<%request.setAttribute("resourceid", request.getParameter("rsid"));%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>艺术家列表</title>
    <base href="${basePath }/">
    <%@include file="/pages/comm/header.jsp"%>

    <script type="application/javascript">
/**
 * 批量审核
 */
function artAllcheck(){
	var rows={};
	rows = $("#table_grid").datagrid("getSelections");
	if (rows == "" || rows == null) {
		$.messager.alert('提示', '操作失败,没有选择要操作的记录');
		return;
	}
	var artistids = _split = "";//id1,id2,id3
	for (var i = 0; i<rows.length; i++){
		artistids += _split+rows[i].artistid;
		_split = ",";
	}
	//alert(JSON.stringify(artistids));
	$.messager.confirm('确认对话框', '您确认将所选择的进行发布吗？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/user/gochcek'),
				data: {artistid : artistids,fromstate:0, tostate:2},
				success: function(data){
					if(data.success == '0'){
						$('#table_grid').datagrid('reload');
					   }else{
						   $.messager.alert('提示', '操作失败。原因：'+data.errmsg, 'error');
					   }
				}
			});
		}
	});
	
} 
/**
 * 批量取消审核
 */
function artcheck(){
	var rows={};
	rows = $("#table_grid").datagrid("getSelections");
	if (rows == "" || rows == null) {
		$.messager.alert('提示', '操作失败,没有选择要操作的记录');
		return;
	}
	var artistids = _split = "";//id1,id2,id3
	for (var i = 0; i<rows.length; i++){
		artistids += _split+rows[i].artistid;
		_split = ",";
	}
	//alert(JSON.stringify(artistids));
	$.messager.confirm('确认对话框', '您确认将所选择的进行发布吗？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/user/gochcek'),
				data: {artistid : artistids,fromstate:2, tostate:0},
				success: function(data){
					if(data.success == '0'){
						$('#table_grid').datagrid('reload');
					   }else{
						   $.messager.alert('提示', '操作失败。原因：'+data.errmsg, 'error');
					   }
				}
			});
		}
	});
	
} 
/**
 * 批量发布
 */
function artGocheck(){
	var rows={};
	rows = $("#table_grid").datagrid("getSelections");
	if (rows == "" || rows == null) {
		$.messager.alert('提示', '操作失败,没有选择要操作的记录');
		return;
	}
	var artistids = _split = "";//id1,id2,id3
	for (var i = 0; i<rows.length; i++){
		artistids += _split+rows[i].artistid;
		_split = ",";
	}
	//alert(JSON.stringify(artistids));
	$.messager.confirm('确认对话框', '您确认将所选择的进行发布吗？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/user/gochcek'),
				data: {artistid : artistids,fromstate:2, tostate:3},
				success: function(data){
					if(data.success == '0'){
						$('#table_grid').datagrid('reload');
					   }else{
						   $.messager.alert('提示', '操作失败。原因：'+data.errmsg, 'error');
					   }
				}
			});
		}
	});
	
}
/**
 * 批量取消发布
 */
function Gocheck(){
	var rows={};
	rows = $("#table_grid").datagrid("getSelections");
	if (rows == "" || rows == null) {
		$.messager.alert('提示', '操作失败,没有选择要操作的记录');
		return;
	}
	var artistids = _split = "";//id1,id2,id3
	for (var i = 0; i<rows.length; i++){
		artistids += _split+rows[i].artistid;
		_split = ",";
	}
	//alert(JSON.stringify(artistids));
	$.messager.confirm('确认对话框', '您确认将所选择的进行发布吗？', function(r){
		if (r){
			$.ajax({
				type: "POST",
				url: getFullUrl('/admin/user/gochcek'),
				data: {artistid : artistids,fromstate:3, tostate:2},
				success: function(data){
					if(data.success == '0'){
						$('#table_grid').datagrid('reload');
					   }else{
						   $.messager.alert('提示', '操作失败。原因：'+data.errmsg, 'error');
					   }
				}
			});
		}
	});
	
}
var action = (function () {
    var editWin = new WhuiWinForm("#edit_win");
    var isInitEditWin = false;
    var _url = "./admin/user/addOrEditArtist";

    function initEditWin(){
        if (!isInitEditWin){
            editWin.init();
        }
        isInitEditWin = true;
    }


    function setFormData(frm, row){
        frm.form("load", row);
/*         var imgDiv = '<div class="row img_div">'
                +'<div></div>'
                +'<div><img width="200" height="150"> </div> '
                +'</div>';

        if (row.artistpic){
            var imgrow = $("[name$='artistpic']").parents(".row");
            imgrow.after(imgDiv);
            imgrow.next(".img_div").find("div img").attr("src",row.artistpic);
        } */
    }

    function clearForm(frm){
        frm.form('disableValidation').form("clear");
        $(".img_div").remove();
    }

    function addArtist(){
        initEditWin();
        editWin.setWinTitle("添加艺术家");
        editWin.openWin();
        var frm = editWin.getWinBody().find("form");
        clearForm(frm);
        
        $('#file_artistpic_img').removeAttr('src').parents('.row').hide();
        $('#file_artistpic').filebox('enable');
		$('#artistarttyp').combobox('readonly',false);
		$('#artistname').textbox('readonly',false);
		$('#artisttype').textbox('readonly',false);
		$('#artistregtime').datetimebox('readonly',false);
		$('#artistdesc').textbox('readonly',false);
		editWin.getWinFooter().find("a:eq(0)").show();
        frm.form({
            url:_url,
            onSubmit : function(param){
            	param.artiststate = 0;
            	param.artistidx = '';
            	param.artistghp = 0;
            	param.artistuid = 0;
            	//得到当前的输入值
				 var a = $('#artistregtime').datetimebox('getValue');
				 //转换时间格式
				 var d_a = new Date(a);
				 var myDate=new Date();
				 //时间比较
				 if(d_a >= myDate.getTime()){
					 $.messager.alert("提示", "时间设置错误,注册时间必须小于当前时间");
					 return false;
				 }
                return $(this).form('enableValidation').form('validate');
            },
            success : function(data){
                var json = $.parseJSON(data);
                if (json.success){
                    $.messager.alert("提示","添加艺术家成功");
                    $("#table_grid").datagrid("reload");
                    editWin.closeWin();
                }else{
                    $.messager.alert("失败了", json.msg);
                }
            }
        });
        editWin.setFoolterBut({onClick:function(){ frm.submit() } })
    }

    function editArtist(idx){
        var rows = $("#table_grid").datagrid("getRows");
        var row = rows[idx];
        initEditWin();
        editWin.setWinTitle("编辑艺术家");
        editWin.openWin();

        var frm = editWin.getWinBody().find("form");
        clearForm(frm);
        row.artistregtime = datetimeFMT(row.artistregtime);
        if (row.artistpic && row.artistpic != '' ){
        	$('#file_artistpic_img').attr('src', getFullUrl(row.artistpic)).parents('.row').show();
        }else{
        	$('#file_artistpic_img').parents('.row').hide();
        }
    	$('#file_artistpic').filebox('enable');
		$('#artistarttyp').combobox('readonly',false);
		$('#artistname').textbox('readonly',false);
		$('#artisttype').textbox('readonly',false);
		$('#artistregtime').datetimebox('readonly',false);
		$('#artistdesc').textbox('readonly',false);
        setFormData(frm, row);
        editWin.getWinFooter().find("a:eq(0)").show();
        frm.form({
            url:_url,
            onSubmit : function(param){
                param.artistid = row.artistid;
                param.artistpic = row.artistpic;
              //得到当前的输入值
				 var a = $('#artistregtime').datetimebox('getValue');
				 //转换时间格式
				 var d_a = new Date(a);
				 var myDate=new Date();
				 //时间比较
				 if(d_a >= myDate.getTime()){
					 $.messager.alert("提示", "时间设置错误,注册时间必须小于当前时间");
					 return false;
				 }
                return $(this).form('enableValidation').form('validate');
            },
            success : function(data){
                var json = $.parseJSON(data);
                if (json.success){
                    $.messager.alert("提示","编辑艺术家成功");
                    $("#table_grid").datagrid("reload");
                    editWin.closeWin();
                }else{
                    $.messager.alert("失败了", json.msg);
                }
            }
        });
        editWin.setFoolterBut({onClick:function(){ frm.submit() } })
    }
    
    function seeArtist(idx){
        var rows = $("#table_grid").datagrid("getRows");
        var row = rows[idx];
        initEditWin();
        editWin.setWinTitle("查看艺术家");
        editWin.openWin();

        var frm = editWin.getWinBody().find("form");
        clearForm(frm);
        row.artistregtime = datetimeFMT(row.artistregtime);
        if (row.artistpic && row.artistpic != '' ){
        	$('#file_artistpic_img').attr('src', getFullUrl(row.artistpic)).parents('.row').show();
        }else{
        	$('#file_artistpic_img').parents('.row').hide();
        }
		$('#file_artistpic').filebox('disable');
		$('#artistarttyp').combobox('readonly',true);
		$('#artistname').textbox('readonly',true);
		$('#artisttype').textbox('readonly',true);
		$('#artistregtime').datetimebox('readonly',true);
		$('#artistdesc').textbox('readonly',true);
		editWin.getWinFooter().find("a:eq(0)").hide();
        setFormData(frm, row);
        
        frm.form({
            url:_url,
            onSubmit : function(param){
                param.artistid = row.artistid;
                param.artistpic = row.artistpic;
                return $(this).form('enableValidation').form('validate');
            },
            success : function(data){
                var json = $.parseJSON(data);
                if (json.success){
                    $.messager.alert("提示","编辑艺术家成功");
                    $("#table_grid").datagrid("reload");
                    editWin.closeWin();
                }else{
                    $.messager.alert("失败了", json.msg);
                }
            }
        });
        editWin.setFoolterBut({onClick:function(){ frm.submit() } })
    }

    function removeArtist(idx){
        var rows = $("#table_grid").datagrid("getRows");
        var row = rows[idx];
        $.messager.confirm('确认对话框', '您想要删除此记录吗？', function(r) {
            if (r) {
                $.ajax({
                    url : './admin/user/removeArtist',
                    data : { artistid : row.artistid },
                    success : function(data) {
                        if (data.success) {
                            $.messager.alert("提示", "删除成功！");
                            $("#table_grid").datagrid("reload");
                        } else {
                            $.messager.alert("提示", "删除该记录失败！");
                        }
                    }
                })
            }
        });
    }
    
    function stateOpt(artistid, artiststate){
    	$.ajax({
    		url : "./admin/user/updateState",
    		data : {artistid: artistid, artiststate:artiststate},
    		success:function(data){
    			if (data=="success"){
                    $.messager.alert("提示","操作成功");
                    $("#table_grid").datagrid("reload");
                }else{
                    $.messager.alert("失败了", "操作失败！");
                }
    		}
    	})
    }
    
    function checkArt(idx){
    	 var rows = $("#table_grid").datagrid("getRows");
         var row = rows[idx];
         
         $.messager.confirm('确认对话框', '确定要通过审核？', function(r) {
             if (r) {
            	 stateOpt(row.artistid, 2);
             }
         })
    }
    
    function uncheckArt(idx){
   	 var rows = $("#table_grid").datagrid("getRows");
        var row = rows[idx];
        
        $.messager.confirm('确认对话框', '确定要取消审核？', function(r) {
            if (r) {
           	 	stateOpt(row.artistid, 0);
            }
        })
   }
    function ucheckArt(idx){
      	 var rows = $("#table_grid").datagrid("getRows");
           var row = rows[idx];
           
           $.messager.confirm('确认对话框', '确定要发布？', function(r) {
               if (r) {
              	 	stateOpt(row.artistid, 3);
               }
           })
      }
    function ncheckArt(idx){
   	 var rows = $("#table_grid").datagrid("getRows");
        var row = rows[idx];
        //if (row.artistghp==0) {
        	$.messager.confirm('确认对话框', '确定要取消发布？', function(r) {
                if (r) {
               	 stateOpt(row.artistid, 2);
                }
            })
		//}else {
			// $.messager.alert("提示", "上首页标识为否才能取消发布！");
		//}
        
   }

    return {
        addArtist: addArtist,
        editArtist: editArtist,
        removeArtist: removeArtist,
        checkArt : checkArt,
        uncheckArt : uncheckArt,
        ucheckArt: ucheckArt,
        seeArtist:seeArtist,
        ncheckArt: ncheckArt
    }
})();
/**
 *上首页
 */
 function goindex(index){
		var row = WHdg.getRowData(index);
		editWinform_.openWin();
	    editWinform_.setWinTitle("设置上首页");
	    $('#artistghp').combobox({
			onChange: function(newVal, oldVal){
				if(newVal == '0'){
					$('#artistidx').numberspinner('enable');
				}else {
					$('#artistidx').numberspinner('disable');
				}
			}
		});
	    var frm = $('#ff_').form({
			url : getFullUrl("/admin/user/goarts"),
			onSubmit : function(param) {
				param.artistid = row.artistid;
				return $(this).form('enableValidation').form('validate');
			},
			success : function(data) {
				data = eval('('+data+')');
				if(data && data.success == "0"){
					$('#table_grid').datagrid('reload');
					$.messager.alert('提示', '操作成功!');
					editWinform_.closeWin();
				}else{
					   $.messager.alert('提示', '操作失败!');
				 }
			}
		});
	    editWinform_.setFoolterBut({onClick:function(){
			frm.submit();
		}});
} 
var editWinform_ = new WhuiWinForm("#edit_win_",{width:500,height:250});
$(function(){

    //var gridtool = new WhuiDataGrid("#table_grid");

    var options = {
            title : "艺术家列表",
            url : "./admin/user/loadArtistList",
            toolbar : "#tb",
            sortName: 'artistid',
    		sortOrder: 'desc',
    		rownumbers:true,
    		singleSelect:false,
            columns : [[
				{field:'ck',checkbox:true},        
                {field:'artistname',title:'艺术家名称',width:150},
                {field:'artistarttyp',title:'艺术类型',width:150, formatter: arttypFMT},
                {field:'artisttype',title:'艺术家类型',width:100, sortable:true},
                {field:'artistpic',title:'艺术家图片',width:80,formatter:imgFMT},
                //{field:'artistdesc',title:'艺术家简介',width:80},
                {field:'artistregtime',title:'注册时间',width:150, sortable:true, formatter:datetimeFMT},
                //{field:'artistghp',title:'上首页标识',width:100,formatter: yesNoFMT,sortable:true},
                //{field:'artistidx',title:'上首页排序',width:100 ,sortable:true},
                {field:'artiststate',title:'状态',width:60, sortable:true, formatter:traStateFMT},
                {field:'opt',title:'操作', fixed:true, formatter: WHdg.optFMT, optDivId:'checklistOPT'}
            ]]
    }
    //gridtool.init(options);
    WHdg.init('table_grid', 'tb', options);
    editWinform_.init();
    $("#tb a:first").linkbutton({
        iconCls:'icon-add',
        text : '添加',
        //size : 'large',
        //plain:true,
        onClick: function(){ action.addArtist() }
    })
})

    </script>
</head>
<body>

<!-- 列表 -->
<table id="table_grid" class="easyui-datagrid" data-options=""></table>

<div id="checklistOPT" style="display: none;">
    <shiro:hasPermission name="${resourceid}:view"><a href="javascript:void(0)" validKey="artiststate" validVal="0,1,2,3" method="action.seeArtist">查看</a></shiro:hasPermission>
    <shiro:hasPermission name="${resourceid}:edit"><a href="javascript:void(0)" validKey="artiststate" validVal="0,1" method="action.editArtist">编辑</a></shiro:hasPermission>
    <shiro:hasPermission name="${resourceid}:del"><a href="javascript:void(0)" validKey="artiststate" validVal="0,1" method="action.removeArtist">删除</a></shiro:hasPermission>
    <shiro:hasPermission name="${resourceid}:checkon"><a href="javascript:void(0)" validKey="artiststate" validVal="0,1" method="action.checkArt">审核</a></shiro:hasPermission>
    <shiro:hasPermission name="${resourceid}:checkoff"><a href="javascript:void(0)" validKey="artiststate" validVal="2" method="action.uncheckArt">取消审核</a></shiro:hasPermission>
    <shiro:hasPermission name="${resourceid}:publish"><a href="javascript:void(0)" validKey="artiststate" validVal="2" method="action.ucheckArt">发布</a></shiro:hasPermission>
    <shiro:hasPermission name="${resourceid}:publishoff"><a href="javascript:void(0)" validKey="artiststate" validVal="3" method="action.ncheckArt">取消发布</a></shiro:hasPermission>
    <!--<shiro:hasPermission name="${resourceid}:order"><a href="javascript:void(0)" validKey="artiststate" validVal="3" method="goindex">排序</a></shiro:hasPermission>-->
</div>

<!-- 列表工具条 -->
<div id="tb" class="grid-control-group">
    <div>
        <shiro:hasPermission name="${resourceid}:add"><a href="javascript:void(0)" >添加</a></shiro:hasPermission>
        <shiro:hasPermission name="${resourceid}:checkon"><a href="javascript:void(0)"  class="easyui-linkbutton " onclick="artAllcheck();">批量审核</a></shiro:hasPermission>
		<shiro:hasPermission name="${resourceid}:checkoff"><a href="javascript:void(0)"  class="easyui-linkbutton " onclick="artcheck();" >批量取消审核</a></shiro:hasPermission>
		<shiro:hasPermission name="${resourceid}:publish"><a href="javascript:void(0)"  class="easyui-linkbutton " onclick="artGocheck();" >批量发布</a></shiro:hasPermission>
		<shiro:hasPermission name="${resourceid}:publishoff"><a href="javascript:void(0)"  class="easyui-linkbutton " onclick="Gocheck();" >批量取消发布</a></shiro:hasPermission>
    </div>
    <div style="margin-top:5px">
            艺术类型: 
		<input class="easyui-combobox" name="artistarttyp" data-options="valueField:'typid',textField:'typname',url:'${basePath}/comm/whtyp?type=0'"/>
            艺术家名:
        <input class="easyui-textbox" name="artistname" />
               状态:
	    <select class="easyui-combobox radio" name="artiststate">
	    			<option value="">全部</option>
 					<option value="0">未审核</option>
 					<option value="2">已审核</option>
 					<option value="3">已发布</option>
		</select>
        <a href="javascript:void(0)" class="easyui-linkbutton search" iconCls="icon-search">查询</a>
        
    </div>
</div>

<!-- 编辑表单 -->
<div id="edit_win" style="display:none" data-options="maximized:true" >
    <form method="post" enctype="multipart/form-data" id="ff">
        <div class="main">
        	<div class="row">
                <div><label>艺术类型:</label></div>
                <div>
                    <input class="easyui-combobox" name="artistarttyp" id="artistarttyp" style="width:90%; height:35px" data-options="valueField:'typid',textField:'typname',url:'${basePath}/comm/whtyp?type=0'"/>
                </div>
            </div>
            <div class="row">
                <div><label>艺术家名称:</label></div>
                <div>
                    <input class="easyui-textbox" name="artistname" id="artistname" style="width:90%; height:35px" data-options="required:true, validType:'length[0,60]'"/>
                </div>
            </div>
            <div class="row">
                <div><label>艺术家类型:</label></div>
                <div>
                	<input class="easyui-textbox" name="artisttype" id="artisttype" style="width:90%; height:35px" data-options="required:true, validType:'length[0,30]'"/>
                </div>
            </div>
            <div class="row">
                <div><label>艺术家图片:</label></div>
                <div>
                    <input class="easyui-filebox" name="file_artistpic" id="file_artistpic" style="width:90%; height:35px"/>
                </div>
            </div>
            <div class="row">
                <div><label></label></div>
                <div>
                    <img id="file_artistpic_img" style="height:150px;"/>
                </div>
            </div>
            <div class="row">
                <div><label>注册时间:</label></div>
                <div>
                    <input id="artistregtime" name="artistregtime" type="text" style="width:90%;height:32px;"  class="easyui-datetimebox" required="required">
                </div>
            </div>
            <div class="row">
                <div><label>艺术家简介</label></div>
                <div>
                    <input class="easyui-textbox" name="artistdesc" id="artistdesc" style="width:90%; height:120px" data-options="validType:'length[0,400]',multiline:true"/>
                </div>
            </div>
        </div>
    </form>
</div>

  <div id="edit_win_" class="none">
		<form method="post" id="ff_" enctype="multipart/form-data">
		 <!-- 隐藏作用域 -->
		 <input type="hidden" name="artistghp"/>
			<div class="main">
				<div class="row">
					<div><label>上首页排序</label></div>
					<div>
						<input class="easyui-numberspinner" id="artistidx" name="artistidx" style="width:90%;height:35px" data-options="increment:1, required:true,min:1,max:999,editable:true"/>
					</div>
				</div>
		    </div>
	    </form>
   </div>

</body>
</html>
