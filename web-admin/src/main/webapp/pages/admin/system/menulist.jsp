<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath(); request.setAttribute("basePath", basePath); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>后台管理菜单列表</title>
<base href="${basePath }/">
<%@include file="/pages/comm/header.jsp"%>

<%--<script type="application/javascript" src="./static/common/js/easyui.wh.tools.js"></script>--%>

<script type="text/javascript">
	/** 生成菜单支持操作的值  */
	function getOPTSVal(){
		var rtnVal = [];//id text
		var type = $('.cbb_type').combobox('getValue');
		if(type == '1'){
			var vals = $('#optlist').combobox('getValues');
			var text = $('#optlist').combobox('getText');
			if(vals.length > 0){
				var txts = text.split(',');
				for(var i=0; i<vals.length; i++){
					rtnVal.push({"id":vals[i], "text":txts[i]});
				}
			}
		}
		if(rtnVal.length > 0){
			rtnVal = JSON.stringify(rtnVal);
		}else{
			rtnVal = false;
		}
		return rtnVal;
	}

	function MenuListAction(){
		this.treegridJQ = "#tt";
		this.idxWin = $("<div></div>");
		
		if (typeof MenuListAction.__initialization__ != "undefined") return;
		MenuListAction.__initialization__ = true;
		
		MenuListAction.prototype.init = function(){
			var me = this;
			this.treegrid = $(this.treegridJQ).treegrid({
				url : './admin/loadMenus?type=off',
				toolbar : [
			           {
			        	   text:"添加菜单项",
			        	   size:'large',
			        	   iconCls:'icon-add',
			        	   handler : function(){ 
			        		   //window.location="./admin/menuedit";
							   me.addMenuInfo();
			        	   }
			           },
			           {
			        	   text:"刷 新",
			        	   size:'large',
			        	   iconCls:'icon-reload',
			        	   handler : function(){ 
			        		   me.treegrid.treegrid("reload");
			        	   }
			           },
			           {
			        	   text:"全部折叠",
			        	   size:'large',
			        	   iconCls:'icon-undo',
			        	   handler : function(){ 
			        		   me.treegrid.treegrid("collapseAll");
			        	   }
			           },
			           {
			        	   text:"全部展开",
			        	   size:'large',
			        	   iconCls:'icon-redo',
			        	   handler : function(){ 
			        		   me.treegrid.treegrid("expandAll");
			        	   }
			           }
		           ]
			})

			//处理菜单类型改变
			me.cbb_type = $(".cbb_type").combobox({
				onChange : function(v,olv){
					me.typeChange(v);
				}
			});
		}
		
		MenuListAction.prototype.setMenuIdx = function(id){
			var me = this;
			me.treegrid.treegrid("select",id);
			var row = me.treegrid.treegrid('getSelected');

			winform.openWin();

			var _form = winform.getWinBody().find("form").form({
				url : "./admin/editMenuItem",
				onSubmit : function(param){
					param.id = row.id;
					return $(this).form('enableValidation').form('validate');
				},
				success : function(data){
					data = eval("("+data+")");
					if (data == "success"){
						$.messager.alert("提示","设置排序成功");
						me.treegrid.treegrid("reload");
						winform.closeWin();
					}else{
						$.messager.alert("提示","设置排序失败");
					}
				}
			});

			var data = {idx:row.idx};
			_form.form("load", data);

			winform.setFoolterBut({onClick:function(){
				_form.submit();
			}})
			
			/*var win = $("#idx_win").window({
				collapsible : false,
                minimizable : false,
                maximizable : false,
                modal : true,
				width : 300,
				height : 180,
				title : "设置排序"
			});
			
			var _form = win.find("form").form({
                url : "./admin/editMenuItem",
                onSubmit : function(param){ param.id = row.id},
                success : function(data){
                    if (data == "success"){
                    	$.messager.alert("提示","设置排序成功");
                    	me.treegrid.treegrid("reload");
                    	win.window("close");
                    }else{
                    	$.messager.alert("提示","设置排序失败");
                    }
                }
            });
			var data = {idx:row.idx};
			_form.form("load", data);
			win.find(".winSubmit").off("click");
			win.find(".winSubmit").on("click",function(){ _form.submit() });*/
		}
		
		MenuListAction.prototype.removeMenu = function(id){
			var me = this;
			me.treegrid.treegrid("select",id);
			var row = me.treegrid.treegrid('getSelected');
			
			if(row.children.length>0){
				$.messager.alert("提示", "选中的节点有子节点，请先删除子节点");
				return;
			}
			$.messager.confirm('确认提示','确定要删除选定的菜单记录吗？',
	            function(yn){
	                if(!yn) return false;
	                $.ajax({
	                	url : "./admin/removeMenuItem",
	                	data :{id : row.id},
	                	type : "post",
	                	success:function(data){
	                		if (data == "success"){
	                			me.treegrid.treegrid("reload");
	                		}else{
	                			$.messager.alert("提示","删除菜单项失败");
	                		}
	                	}
	                });
				}
			);
		}


		MenuListAction.prototype.typeChange = function(type){
			var me = this;

			me._setItemOnOff(type);

			if (me.parentList){
				me._setParentMenu(type);
			}else{
				$.getJSON("./admin/loadParentList", function(data){
					me.parentList = data || [];
					me._setParentMenu(type);
				})
			}
			
			if(type == '1'){
				$('#optlist').parents('.row').show();
				
				var title = editWinform.getWinTitle();
				if(title.indexOf('添加') > -1){
					$('#optlist').combobox('setValues', ['view', 'add', 'edit', 'del']);
				}else{
					$('#optlist').combobox('clear');
				}
			}else{
				var title = editWinform.getWinTitle();
				if(title.indexOf('添加') > -1){
					$('#optlist').combobox('clear');
				}
				$('#optlist').parents('.row').hide();
			}
		}
		MenuListAction.prototype._setParentMenu = function(type){
			var me = this;

			var loaddata = [];

			//处理菜单组的无父菜单选项
			if (type==0){
				loaddata.push({id:"", text:"无"});
			}
			for(var i in me.parentList){
				var data = me.parentList[i];
				//处理菜单组不能设到第三级
				if (type==0 && data.parent) continue;
				//处理自己不能做自己的上级菜单
				if (me.notSelectPID && me.notSelectPID == data.id) continue;
				//var _text = data.parent? "-->"+data.text : data.text;
				//loaddata.push({id:data.id, "text":_text, parent:data.parent});
				loaddata.push({id:data.id, "text":data.text, parent:data.parent});
			}

			//$(".cbb_parent").combobox("loadData", loaddata);
			//if (me.selectPID) $(".cbb_parent").combobox("setValue", me.selectPID);
			$(".cbb_parent").combotree("loadData", me._toTreeList(loaddata));
			if (me.selectPID) $(".cbb_parent").combotree("setValue", me.selectPID);
		}
		MenuListAction.prototype._toTreeList = function(data){
			var treedata = new Array();
			for(var i in data){
				if (!data[i].parent || data[i].parent==0){
					var _d = data[i];
					for (var j in data){
						var _cd = data[j];
						if (_cd.parent && _cd.parent == _d.id){
							_d.children = _d.children ? _d.children : new Array();
							_d.children.push(_cd);
						}
					}
					treedata.push(_d);
				}
			}
			return treedata;
		}
		MenuListAction.prototype._setItemOnOff = function(type){
			var _href = $("input[name='href']").parents("div.row");
			var _iconcls = $("input[name='iconcls']").parents("div.row");
			if (type == 0){
				_href.hide();
				_iconcls.show();
			}else{
				_href.show();
				_iconcls.hide();
			}
		}

		MenuListAction.prototype.addMenuInfo = function(){
			var me = this;

			editWinform.openWin();
			editWinform.setWinTitle("添加菜单");
			var _form = editWinform.getWinBody().find("form").form({
				url : "./admin/addMenuItem",
				onSubmit : function(param){
					var opts = getOPTSVal();
					if(opts){
						param.opts = opts;
					}
					return $(this).form('enableValidation').form('validate');
				},
				success : function(data){
					data = eval("("+data+")");
					if (data == "success"){
						$.messager.alert("提示","添加菜单成功");
						me.treegrid.treegrid("reload");
						editWinform.closeWin();
						me.parentList = '';
					}else{
						$.messager.alert("提示","添加菜单失败");
					}
				}
			});
			_form.form('disableValidation').form("clear");
			me.notSelectPID = "";
			me.selectPID = "";
			me.cbb_type.combobox("clear");
			me.cbb_type.combobox("setValue", 0);
			me.typeChange( me.cbb_type.combobox("getValue") );

			editWinform.setFoolterBut({onClick:function(){
				_form.submit();
			}})
			
		}

		MenuListAction.prototype.editMenuInfo = function(id){
			var me = this;
			me.treegrid.treegrid("select",id);
			var row = me.treegrid.treegrid('getSelected');

			editWinform.openWin();
			editWinform.setWinTitle("编辑菜单");
			me.notSelectPID = row.id;
			me.selectPID = row.parent;

			var _form = editWinform.getWinBody().find("form").form({
				url : "./admin/editMenuItem",
				onSubmit : function(param){
					param.id = row.id;
					param.opts = getOPTSVal() || '[]';
					return $(this).form('enableValidation').form('validate');
				},
				success : function(data){
					data = eval("("+data+")");

					if (data == "success"){
						$.messager.alert("提示","编辑菜单成功");
						me.treegrid.treegrid("reload");
						editWinform.closeWin();
						me.parentList = '';
					}else{
						$.messager.alert("提示","编辑菜单失败");
					}
				}
			});
			$('#optlist').combobox('clear');
			me.cbb_type.combobox("clear");
			_form.form("clear");
			me.typeChange(row.type);
			_form.form("load", row);
			
			$('#optlist').combobox('clear');
			//菜单项或者菜单 组
			var __type = $(".cbb_type").combobox('getValue');
			if(__type == '1'){
				$('#optlist').parents('.row').show();
				var _optlist = [];
				if(row.opts){
					var optArr = jQuery.parseJSON(row.opts);
					if($.isArray(optArr)){
						for(var i=0; i<optArr.length; i++){
							_optlist.push(optArr[i].id);
						}
					}
					if(_optlist.length > 0){
						$('#optlist').combobox('clear');
						$('#optlist').combobox('setValues', _optlist);
					}
				}
			}else{
				$('#optlist').parents('.row').hide();
			}


			editWinform.setFoolterBut({onClick:function(){
				_form.submit();
			}})
		}
	}
	
	var menuAct = new MenuListAction();

	var winform = new WhuiWinForm("#idx_win",{width:350,height:150});
	var editWinform = new WhuiWinForm("#edit_win");
	$(function(){
		winform.init();
		winform.setWinTitle("设置菜单排序");
		winform.setFormStyleTemp({
			mainTop:'8px',
			formWidth : '90%',
			lableWidth : '80px'
		});

		editWinform.init();
		//editWinform.openWin();

		menuAct.init();
	})
	
	function fieldfmt_type(v,r,i){
		return (v==0)? "分类菜单" : "操作菜单";
	}
	function fieldfmt_opt(v,r,i){
		var col_opt = $("#col_opt");
		
		var alist = col_opt.find("a");
		//$(alist[0]).attr("href", "./admin/menuedit?id="+v);
		$(alist[0]).attr("onClick", "menuAct.editMenuInfo('"+v+"')");
		$(alist[1]).attr("onClick", "menuAct.setMenuIdx('"+v+"')");
		if (r.children && r.children.length==0){
			$(alist[2]).show();
			$(alist[2]).attr("onClick", "menuAct.removeMenu('"+v+"')");
		}else{
			$(alist[2]).hide();
		}
		return col_opt.html();
	}
</script>

</head>
<body>

<table id="tt" class="easyui-treegrid"    
        data-options="fit:true,idField:'id',
			treeField:'text',
			fitColumns:true,
			title:'菜单信息列表'">   
    <thead>   
        <tr>   
            <th data-options="field:'text',width:100">菜单文本</th>   
            <th data-options="field:'type',width:50,formatter:fieldfmt_type">类型</th>   
            <th data-options="field:'href',width:100">菜单连接</th>   
            <th data-options="field:'iconcls',width:80">图标样式</th>   
            <th data-options="field:'idx',width:40">排序</th>   
            <th data-options="field:'id',width:80,formatter:fieldfmt_opt">操作</th>   
        </tr>   
    </thead>   
</table>

<div id="col_opt" style="display: none">
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,width:40">编辑</a> 
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true">设置排序值</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,width:40">删除</a>
</div>

<div id="edit_win" style="display: none">
	<form method="post" class="easyui-form" data-options="novalidate:true">
		<div class="main">
			<div class="row">
				<div><label>菜单类型:</label></div>
				<div>
					<select class="easyui-combobox cbb_type" name="type" style="width:90%;height:35px" data-options="editable:false, required:true, panelHeight:'auto'">
						<option value="0">菜单组</option>
						<option value="1">菜单项</option>
					</select>
				</div>
			</div>
			<div class="row">
				<div><label>上级菜单:</label></div>
				<div>
					<select class="easyui-combotree cbb_parent" name="parent" style="width:90%;height:35px"
					data-options="editable:false, required:true, panelHeight:'auto', valueField : 'id', textField : 'text'">
					</select>
				</div>
			</div>
			<div class="row">
				<div><label>菜单名称:</label></div>
				<div>
					<input class="easyui-textbox" name="text" style="width:90%;height:35px"
					data-options="required:true"/>
				</div>
			</div>
			<div class="row">
				<div><label>菜单连接:</label></div>
				<div>
					<%--<input class="easyui-textbox" name="href" style="width:90%;height:35px" />--%>
					<input class="easyui-combobox" name="href" style="width:90%;height:35px"
					data-options="url:'static/common/data/menus.json',method:'get',
					valueField:'value',textField:'value',groupField:'group' ">
				</div>
			</div>
			<div class="row">
				<div><label>菜单样式:</label></div>
				<div>
					<input class="easyui-textbox" name="iconcls" style="width:90%;height:35px" />
				</div>
			</div>
			<div class="row">
				<div><label>支持操作:</label></div>
				<div>
					<select id="optlist" class="easyui-combobox" data-options="multiple:true" name="optlist" style="width:90%;height:35px">   
					    <option value="view">浏览</option>   
					    <option value="add">添加</option>   
					    <option value="edit">编辑</option>   
					    <option value="del">删除</option>   
					    <option value="on">启用</option>   
					    <option value="off">停用</option>  
					    <option value="checkgo">送审</option> 
					    <option value="checkon">审核通过</option>   
					    <option value="checkoff">审核打回</option>   
					    <option value="publish">发布</option>   
					    <option value="publishoff">取消发布</option>
					    <option value="huifu">回复</option>
					    <option value="order">排序</option>
					    <option value="reset">密码重置</option>
					</select> 
				</div>
			</div>
		</div>
	</form>
</div>

<div id="idx_win" style="display: none">
	<form method="post">
		<div class="main">
			<div class="row">
				<div><label>排序：</label></div>
				<div>
					<input name="idx" class="easyui-numberbox" style="width:90%;height:35px" required="true"/>
				</div>
			</div>
		</div>
	</form>

	<%--<div class="easyui-layout" data-options="fit:true,border:false">
		<div data-options="region:'center'" style="padding: 10px;">
			<form method="post">
				<table style="width: 100%; margin: 25px auto">
					<tr>
						<td style="text-align: right">排序：</td>
						<td><input name="idx" class="easyui-numberbox" style="width:100%" /></td>
					</tr>
				</table>
			</form>
		</div>
		<div data-options="region:'south',border:false,height:40" style="text-align: center; padding: 5px;">
			<a class="easyui-linkbutton winSubmit" data-options="iconCls:'icon-ok'" href="javascript:void(0)">提交</a>
		</div>
	</div>--%>
</div>

</body>
</html>