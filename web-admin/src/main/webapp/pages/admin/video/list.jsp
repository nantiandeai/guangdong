<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<% request.setAttribute("basePath", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath());%>
<% request.setAttribute("resourceid", request.getParameter("rsid")); %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>音视频管理</title>
	<%@include file="/pages/comm/admin/header.jsp"%>
	<link rel="stylesheet" type="text/css" href="${basePath}/pages/admin/video/style.css"/>
	<script type="text/javascript" src="${basePath }/static/common/js/easyui.wh.tools.js"></script>
	<script>
        /** 增加校验规则  */
        $.extend($.fn.validatebox.defaults.rules, {
            isDir: {
                validator: function(value, param){
                    if(value != ''){
                        var rex = /^[\u0800-\u9FA5\uF900-\uFA2D\uAC00-\uD7FFa-zA-Z0-9]{1}[\u0800-\u9FA5\uF900-\uFA2D\uAC00-\uD7FFa-zA-Z0-9\._\-]{0,253}$/;
                        if(rex.test(value)){
                            return true;
                        }else{
                            $.fn.validatebox.defaults.rules.isDir.message = "文件夹命名不规范";
                            return false;
                        }

                    }
                    return true;
                },
                message: ''
            }
        });/** 增加校验规则 -END */


        /** 打开添加数字资源表单 */
        function _goAdd(){
            //目录名称
            var dir = $('#whgdg-tb').find('input[name="dir"]').val();
            //打开表单
            $('#drscWin').find('iframe').attr('src', '${basePath}/admin/video/upload?dir='+encodeURIComponent(dir)+'&t='+new Date().getTime());
            $('#drscWin').dialog('open');

        }/** 打开添加数字资源表单-END */

        /** 删除数字资源 */
        function doDel(index){
            var row = $('#whgdg').datagrid('getRows')[index];
            $.messager.confirm('确认对话框', '确定要删除吗？', function(r){
                if (r){
                    $.ajax({
                        type: "POST",
                        url: getFullUrl('/admin/video/del'),
                        data: {ids : row.key},
                        success: function(Json){
                            if(Json && Json.success == '0'){
                                $('#whgdg').datagrid('reload');
                            } else {
                                $.messager.alert('提示', '操作失败:'+Json.errmsg+'！', 'error');
                            }
                        }
                    });
                }
            });
        }/** 删除数字资源-END */

        /** 修改数字资源状态 */
        function _updState(){
            var drscids = '';
            var rows = $("#whgdg").datagrid("getChecked");
            if(rows.length < 1){
                $.messager.alert('警告', '请选择要操作的数据！', 'warning');return;
            }
            var _spt = '';
            for(var i=0; i<rows.length; i++){
                drscids += _spt+rows[i].key;
                _spt = ',';
            }

            $.messager.confirm('确认对话框', '确定要批量删除这些视频吗？', function(r){
                if (r){
                    $.ajax({
                        type: "POST",
                        url: getFullUrl('/admin/video/del'),
                        data: {ids : drscids},
                        success: function(Json){
                            if(Json && Json.success == '0'){
                                $('#whgdg').datagrid('reload');
                            } else {
                                $.messager.alert('提示', '操作失败:'+Json.errmsg+'！', 'error');
                            }
                        }
                    });
                }
            });
        }/** 修改数字资源状态-END */

        /** 审批数字资源 */
        function _doBatchDel(index){
            var row = $('#whgdg').datagrid('getRows')[index];
            _updState(0, 2, row.drscid);
        }/** 审批数字资源-END */

        /** 取消审批数字资源 */
        function doUnCheck(index){
            var row = $('#whgdg').datagrid('getRows')[index];
            _updState(2, 0, row.drscid);
        }/** 取消审批数字资源-END */

        /** 发布数字资源 */
        function doRelease(index){
            var row = $('#whgdg').datagrid('getRows')[index];
            _updState(2, 3, row.drscid);
        }/** 发布数字资源-END */

        /** 取消发布数字资源 */
        function doUnRelease(index){
            var row = $('#whgdg').datagrid('getRows')[index];
            _updState(3, 2, row.drscid);
        }/** 取消发布数字资源-END */

        /** 创建子目录 */
        function _cdir(){
            //打开表单
            winform.setWinTitle("创建子目录");
            winform.openWin();
            winform.getWinFooter().find("a:eq(0)").show();

            //初始与清除表单
            $('#ff').form({
                novalidate: true,
                url : getFullUrl('/admin/video/createDir'),
                onSubmit : function(param) {
                    param.pdir = $('#whgdg-tb').find('input[name="dir"]').val();
                    //param.dir = $('#ff').find('input[name="dirx"]').textbox('getValue');
                    var _valid = $(this).form('enableValidation').form('validate')
                    if(_valid){
                        $.messager.progress();
                    }
                    return _valid;
                },
                success : function(data) {
                    $.messager.progress('close');
                    var Json = eval('('+data+')');
                    if(Json && Json.success == '0'){
                        $('#whgdg').datagrid('reload');
                        winform.closeWin();
                    } else {
                        $.messager.alert('提示', '操作失败！', 'error');
                    }
                }
            });

            //注册提交事件
            winform.setFoolterBut({onClick:function(){
                $('#ff').submit();
            }});

            //清除值
            window.setTimeout(function(){
                $('#cd_dir').textbox('clear');
            },100);
        }/** 创建子目录-END */

        /** 查看目录内容 */
        function doLS(index){
            var row = $('#whgdg').datagrid('getRows')[index];
            var dir = row.key;
            var idx = dir.indexOf("/");
            if(dir == '.'){
                dir = 'root';
            }
            $('#whgdg-tb').find('input[name="dir"]').val(dir);
            $('#whgdg-tb').find('.search').click();
        }

        /** 返回上级目录 */
        function doLSP(index){
            var row = $('#whgdg').datagrid('getRows')[index];
            var dir = row.key;
            var idx = dir.indexOf("/");
            if(idx > -1){
                dir = dir.substring(0,idx-1);
            }else{
                dir = "root";
            }
            $('#whgdg-tb').find('input[name="dir"]').val(dir);
            $('#whgdg-tb').find('.search').click();
        }

        /** 验证是否显示查看目录文件按钮 */
        function _doValidPre(index){
            var row = $('#whgdg').datagrid('getRows')[index];
            if(row.etag == 'predir'){
                return true;
            }else{
                return false;
            }
        }/** 验证是否显示查看目录文件按钮-END */

        /** 验证是否显示查看目录文件按钮 */
        function _doValidPre2(index){
            return !_doValidPre(index);
        }/** 验证是否显示查看目录文件按钮-END */

        /** 验证是否显示查看目录文件按钮 */
        function _doValidNext(index){
            var row = $('#whgdg').datagrid('getRows')[index];
            if(row.etag == 'dir'){
                return true;
            }else{
                return false;
            }
        }/** 验证是否显示查看目录文件按钮-END */
	</script>
</head>
<body>

<!-- 表格 -->
<table id="whgdg" title="音视频管理" class="easyui-datagrid" style="display: none"
	   data-options="fit:true, striped:true, rownumbers:true, fitColumns:true, singleSelect:false, checkOnSelect:true, selectOnCheck:true, pagination:false, toolbar:'#whgdg-tb',queryParams: {dir:'root'}, url:'${basePath}/admin/video/srchPagging'">
	<thead>
	<tr>
		<th data-options="field:'ck', checkbox:true"></th>
		<th data-options="field:'etag', width:80, formatter:function(val,row,index){return 'dir'==val || 'predir' == val ? '目录':'文件';}">类型</th>
		<th data-options="field:'key', width:160">名称</th>
		<th data-options="field:'size', width:100">大小</th>
		<th data-options="field:'lastmodified', width:100, formatter:WhgComm.FMTDateTime">上传时间</th>
		<th data-options="field:'_opt', formatter:WhgComm.FMTOpt, optDivId:'whgdg-opt'">操作</th>
	</tr>
	</thead>
</table>
<!-- 表格 END -->

<!-- 表格操作工具栏 -->
<div id="whgdg-tb" style="display: none;">
	<div class="whgd-gtb-btn">
		<shiro:hasPermission name="${resourceid}:add"> <a size="small" class="easyui-linkbutton" data-options="iconCls:'icon-add'" id="btn_add">上传视频</a></shiro:hasPermission>
		<shiro:hasPermission name="${resourceid}:del"> <a size="small" class="easyui-linkbutton" data-options="iconCls:'icon-remove'" id="btn_bdel" >批量删除</a></shiro:hasPermission>
		<shiro:hasPermission name="${resourceid}:add"> <a size="small" class="easyui-linkbutton" data-options="iconCls:'icon-edit'" id="btn_cdir" >新建目录</a></shiro:hasPermission>
		<shiro:hasPermission name="${resourceid}:view"><a size="small" class="easyui-linkbutton" data-options="iconCls:'icon-reload'" id="btn_refresh">刷新</a></shiro:hasPermission>
	</div>
	<div class="whgdg-tb-srch">
		<input type="hidden" name="dir" value="root" />
		<input class="easyui-textbox" name="keyname" style="width:300px"  data-options="prompt:'请输入音视频名称进行查询',validType:'length[1,30]'"/>
		<a href="javascript:void(0)" class="easyui-linkbutton search" iconCls="icon-search" onclick="WhgComm.search('#whgdg', '#whgdg-tb');">查 询</a>
	</div>
</div>
<!-- 表格操作工具栏-END -->

<!-- 操作按钮 -->
<div id="whgdg-opt" style="display: none;">
	<shiro:hasPermission name="${resourceid}:del">  <a href="javascript:void(0)" class="easyui-linkbutton" plain="true" method="doDel" validFun="_doValidPre2">删除</a></shiro:hasPermission>
	<shiro:hasPermission name="${resourceid}:view"> <a href="javascript:void(0)" class="easyui-linkbutton" plain="true" method="doLSP" validFun="_doValidPre">返回上级</a></shiro:hasPermission>
	<shiro:hasPermission name="${resourceid}:view"> <a href="javascript:void(0)" class="easyui-linkbutton" plain="true" method="doLS" validFun="_doValidNext">查看下级目录</a></shiro:hasPermission>
</div>
<!-- 操作按钮-END -->

<!-- 编辑查看添加表单 -->
<div id="drscWin" style="display: none"><iframe width="100%" height="100%" frameborder="0"></iframe></div>
<!-- 编辑查看添加表单-END -->

<!-- 编辑查看添加表单 -->
<div id="createDir" style="display: none">
	<form id="ff" class="whgff" method="post">
		<input type="hidden" name="id"/>
		<div class="whgff-row">
			<div class="whgff-row-label" style="width: 25%"><i>*</i>目录名称：</div>
			<div class="whgff-row-input" style="width: 75%">
				<input class="easyui-textbox" id="cd_dir" name="dir" value="" style="width:80%;height:32px;" data-options="required:true, validType:['isDir','length[1,25]']"/>
			</div>
		</div>

		<div class="whgff-row">
			<div class="whgff-row-label" style="width: 25%">&nbsp;</div>
			<div class="whgff-row-input" style="width: 75%">
				<em>文件夹命名规范：</em>
				<ul>
					<li>» 1. 只能包含字母，数字，中文，下划线（_）和短横线（-）,小数点（.）</li>
					<li>» 2. 只能以字母、数字或者中文开头</li>
					<li>» 3. 文件夹的长度限制在1-100之间</li>
					<li>» 4. 子文件名总长度必须在1-1023之间</li>
				</ul>
			</div>
		</div>
	</form>
</div>
<!-- 编辑查看添加表单-END -->

<!-- script -->
<script type="text/javascript">
    /** 页面加载完成后事件处理 */
    var winform = new WhuiWinForm("#createDir", {height:300, width:600});

    //页面加载完成后处理
    $(function(){
        //注册添加事件
        $('#btn_add').on('click.wxl', _goAdd);
        $('#btn_bdel').on('click.wxl', function(e){e.preventDefault();_updState();});
        $('#btn_cdir').on('click.wxl', function(e){e.preventDefault();_cdir();});
        $('#btn_refresh').on('click.wxl', function(){
            $('#whgdg-tb').find('.search').click();
        });

        //初始上传窗口
        $('#drscWin').dialog({
            title: '上传音视频文件',
            width: '80%',
            height: '80%',
            minimizable: false,
            maximizable: false,
            collapsible: false,
            modal: true,
            closed: true,
            onClose: function(){
                $('#whgdg').datagrid('reload');
            },
            buttons: [
                {
                    iconCls: 'icon-clear',
                    text: '关闭',
                    handler: function(){
                        $('#drscWin').dialog('close');
                    }
                }
            ]
        });

        //窗口初始化
        winform.init();
    });
</script>
<!-- script END -->
</body>
</html>