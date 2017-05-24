<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<% request.setAttribute("basePath", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath());%>
<% request.setAttribute("resourceid", request.getParameter("rsid")); %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <%@include file="/pages/comm/admin/header.jsp"%>
    <title>栏目内容管理</title>
</head>
<body class="easyui-layout">
    <div id='infDGS' data-options="region:'west',title:'栏目页面',split:true" style="width:200px;">
        <ul id="pageTree"></ul>
    </div>
    <div style="display:none" data-options="region:'center',title:'',iconCls:'icon-ok'">
        <table id="whgdg" class="easyui-datagrid" title="${pageTitle}" style="display: none;"
               toolbar="#tb" pagination=true pageSize=30 pageList="[10,20,30,50,100,200]" loadMsg="数据加载中，请稍候。。。"
               data-options="fit:true, striped:true, rownumbers:true, fitColumns:true, singleSelect:false, checkOnSelect:true, selectOnCheck:true">
            <thead>
            <tr>
                <th data-options="width: 30, checkbox: true, field:'checkbox' ">全选</th>
                <th data-options="width:100, sortable:false, field:'clnftype', formatter:tool.shopType ">所属栏目</th>
                <th data-options="width:100, sortable:false, field:'clnftltle' ">标题</th>
                <th data-options="width:100, sortable:false, field:'clnfstata', formatter:traStateFMT ">状态</th>
                <th data-options=" field:'id', fixed:true, formatter:WhgComm.FMTOpt, optDivId:'whgdg-opt'">操作</th>
            </tr>
            </thead>
        </table>
    </div>

    <div id="tb" style="display: none;">
        <div>
            <shiro:hasPermission name="${resourceid}:add">
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" size="small" plain="true" onclick="whgListTool.add();">添加</a>
            </shiro:hasPermission>
            <shiro:hasPermission name="${resourceid}:checkon">
                <a href="javascript:void(0)" class="easyui-linkbutton" size="small" plain="true"  onclick="whgListTool.doallup();">批量审核</a>
            </shiro:hasPermission>
            <shiro:hasPermission name="${resourceid}:checkoff">
                <a href="javascript:void(0)" class="easyui-linkbutton" size="small" plain="true" onclick="whgListTool.doupall();" >批量取消审核</a>
            </shiro:hasPermission>
            <shiro:hasPermission name="${resourceid}:publish">
                <a href="javascript:void(0)" class="easyui-linkbutton" size="small" plain="true" onclick="whgListTool.toallup();" >批量发布</a>
            </shiro:hasPermission>
            <shiro:hasPermission name="${resourceid}:publishoff">
                <a href="javascript:void(0)" class="easyui-linkbutton" size="small" plain="true" onclick="whgListTool.toupall();" >批量取消发布</a>
            </shiro:hasPermission>
        </div>
        <div style="padding-top: 5px">
            <input class="easyui-textbox" style="width: 200px; height:28px" name="clnftltle" data-options="prompt:'请输入栏目内容标题'" />

            <a class="easyui-linkbutton" iconCls="icon-search" onclick="WhgComm.search('#whgdg', '#tb');">查 询</a>
        </div>

    </div>


    <!-- 操作按钮 -->
    <div id="whgdg-opt" style="display: none;">

        <shiro:hasPermission name="${resourceid}:view"><a href="javascript:void(0)" class="easyui-linkbutton uploadzy" data-options="plain:true" method="whgListTool.addzl">资源管理</a></shiro:hasPermission>
        <shiro:hasPermission name="${resourceid}:view"><a href="javascript:void(0)" class="easyui-linkbutton uploadFile" data-options="plain:true" method="whgListTool.addzy">上传资料</a></shiro:hasPermission>
        <shiro:hasPermission name="${resourceid}:view"><a href="javascript:void(0)" validKey="clnfstata" validVal="0,1,2,3" method="whgListTool.view">查看</a></shiro:hasPermission>
        <shiro:hasPermission name="${resourceid}:edit"><a href="javascript:void(0)" validKey="clnfstata" validVal="0,1" method="whgListTool.edit">编辑</a></shiro:hasPermission>
        <shiro:hasPermission name="${resourceid}:del"><a href="javascript:void(0)" validKey="clnfstata" validVal="0,1" method="whgListTool.delinfo">删除</a></shiro:hasPermission>
        <shiro:hasPermission name="${resourceid}:checkon"><a href="javascript:void(0)" validKey="clnfstata" validVal="0,1" method="whgListTool.checkinfo">审核</a></shiro:hasPermission>
        <shiro:hasPermission name="${resourceid}:checkoff"><a href="javascript:void(0)" validKey="clnfstata" validVal="2" method="whgListTool.uncheckinfo">取消审核</a></shiro:hasPermission>
        <shiro:hasPermission name="${resourceid}:publish"><a href="javascript:void(0)" validKey="clnfstata" validVal="2" method="whgListTool.pubinfo">发布</a></shiro:hasPermission>
        <shiro:hasPermission name="${resourceid}:publishoff"><a href="javascript:void(0)" validKey="clnfstata" validVal="3" method="whgListTool.unpubinfo">取消发布</a></shiro:hasPermission>
        <shiro:hasPermission name="${resourceid}:upindex"><a href="javascript:void(0)" class="easyui-linkbutton" validFun="_upindexon" plain="true" method="whgListTool.upindex">上首页</a></shiro:hasPermission>
        <shiro:hasPermission name="${resourceid}:upindexoff"><a href="javascript:void(0)" class="easyui-linkbutton" validFun="_upindexoff" plain="true" method="whgListTool.noupindex">取消上首页</a></shiro:hasPermission>
        <shiro:hasPermission name="${resourceid}:order"><a href="javascript:void(0)" validKey="clnfstata" validVal="3" method="whgListTool.goindex">排序</a></shiro:hasPermission>
        <shiro:hasPermission name="${resourceid}:edit"><a href="javascript:void(0)" validFun="whgListTool.top" method="whgListTool.toTop">置顶</a></shiro:hasPermission>
        <shiro:hasPermission name="${resourceid}:edit"><a href="javascript:void(0)" validKey="totop" validVal="1" method="whgListTool.cancelToTop">取消置顶</a></shiro:hasPermission>

    </div>
    <!-- 操作按钮-END -->

    <!-- 编辑表单 -->
    <div id="whgwin-edit"></div>
    <!-- 编辑表单 END -->

<script>
    function _upindexon(idx){
        var row = $("#whgdg").datagrid("getRows")[idx];
        return row.clnfstata == 3 && row.upindex == 0;
    }
    function _upindexoff(idx){
        var row = $("#whgdg").datagrid("getRows")[idx];
        return row.clnfstata == 3 && row.upindex == 1;
    }

    $(function () {
        tool.init();
    });

    var tool = (function(){
        var tree, grid;
        var nowColid, nowColidTitle;

        function init(){
            tree = $(pageTree).tree({
                url: '${basePath}/admin/shop/selecol' ,
                onLoadSuccess: function(node, data){
                    //默认选中省馆介绍
                    var node = tree.tree('find', '2016111900000006');
                    if(node){
                        tree.tree('select', node.target);
                    }
                },
                onSelect: treeOnselect,
                onBeforeSelect: treeOnbeforeSelect
            });
        }

        function treeOnselect(node){
            //缓存相关的栏目ID
            nowColid = node.colid;
            nowColidTitle = node.coltitle;
            //初始表格数据
            grid = $("#whgdg").datagrid({
                url : '${basePath}/admin/shop/seleinfo',
                onBeforeLoad: function(param){
                    param.clnftype = nowColid;
                    if (!param.clnftype) return false;
                }
            });
        }

        function treeOnbeforeSelect(node){
            //是否是一级节点
            var isLeaf = node.colpid != '0';
            //如果是一级栏目，不能操作资讯
            if(!isLeaf || node.colid == '2016111900000005'){
                $('#tb > div > a.easyui-linkbutton').linkbutton('disable');
            }else{
                $('#tb > div > a.easyui-linkbutton').linkbutton('enable');
            }
            //隐藏不需要的上传和资源的栏目
            if (node.colid == '2016111900000006' || node.colid == '2016111900000010' || node.colid == '2016111900000011') {
                $('#whgdg-opt >.uploadzy').hide();
                $('#whgdg-opt >.uploadFile').hide();
            }else {
                $('#whgdg-opt >.uploadzy').show();
                $('#whgdg-opt >.uploadFile').show();
            }
        }

        function shopType(val){
            return tree.tree('find', val).text;
        }

        function getNowColid(){
            return nowColid;
        }

        return {
            init: init,
            shopType: shopType,
            getNowColid: getNowColid
        }
    })();



    var whgListTool = {
        __getGridRow: function(idx){
            return $('#whgdg').datagrid("getRows")[idx];
        },

        __ajaxError: function(xhr, ts, e){
            $.messager.progress('close');
            $.messager.alert("提示信息", "操作处理发生错误", 'error');
            $.error("ajax error info : "+ts);
        },

        //资源管理
        addzl: function (idx) {
            var row = this.__getGridRow(idx);
            WhgComm.editDialog('${basePath}/admin/train/resource/view/list?reftype=11&id=' + row.clnfid);
        },

        //上传
        addzy: function (idx){
            var row = this.__getGridRow(idx);
            var id = row.clnfid;
            WhgComm.editDialog('${basePath}/admin/shop/uploads/view/list?rsid=${resourceid}&refid='+id);
        },

        top: function (idx){
            var row = $("#whgdg").datagrid("getRows")[idx];
            return row.clnfstata == 3 && row.totop == 0 ;
        },

        //添加
        add: function(){
            var nowColid = tool.getNowColid();
            WhgComm.editDialog('${basePath}/admin/shop/view/add?clnftype='+nowColid);
        },

        //编辑
        edit: function(idx){
            var row = this.__getGridRow(idx);
            WhgComm.editDialog('${basePath}/admin/shop/view/add?id='+row.clnfid);
        },

        //查看
        view: function(idx){
            var row = this.__getGridRow(idx);
            WhgComm.editDialog('${basePath}/admin/shop/view/add?targetShow=1&id='+row.clnfid);
        },

        //删除
        delinfo: function(idx){
            var row = this.__getGridRow(idx);
            var id = row.clnfid;
            var pic = row.clnfpic;
            var bigpic = row.clnfbigpic;

            var that = this;
            $.messager.confirm('确认对话框', '您确认要删除吗？', function(r){
                if (r){
                    $.messager.progress();
                    $.ajax({
                        url: '${basePath}/admin/shop/delinfo',
                        data: {clnfid : id, clnfpic:pic,clnfbigpic : bigpic},
                        type: 'post',
                        dataType: 'json',
                        error: function(xhr, ts, e){ that.__ajaxError(xhr, ts, e) },
                        success: function(data){
                            $('#whgdg').datagrid('reload');
                            $.messager.progress('close');
                            $.messager.alert("提示", "操作成功");
                        }
                    });
                }
            });
        },

        //排序
        goindex: function(idx){
            var row = this.__getGridRow(idx);

            var that = this;
            var optDialog =$('<div></div>').dialog({
                title: '设置排序',
                width: 400,
                height: 200,
                cache: false,
                modal: true,
                content: '<div class="dialog-content-add" style="padding: 30px 30px;"></div>',
                onOpen: function(){
                    var content = $(this).find('.dialog-content-add');
                    content.append('排序：<input class="easyui-numberspinner" name="clnfidx" data-options="increment:1, required:true,min:1,editable:true"/>');
                    $.parser.parse(content);
                    content.find(".easyui-numberspinner:eq(0)").numberspinner('setValue', row.clnfidx || 1);
                },
                buttons: [{
                    text:'确认',
                    iconCls: 'icon-ok',
                    handler:function(){
                        $.messager.progress();
                        var valid = optDialog.find(".easyui-numberspinner:eq(0)").numberspinner('isValid');
                        if (!valid) {
                            $.messager.progress('close');
                            return;
                        }
                        var clnfidx = optDialog.find(".easyui-numberspinner:eq(0)").numberspinner('getValue');
                        $.ajax({
                            url: '${basePath}/admin/shop/goinfo',
                            data: {clnfid : row.clnfid, clnfidx:clnfidx},
                            type: 'post',
                            dataType: 'json',
                            error: function(xhr, ts, e){ that.__ajaxError(xhr, ts, e) },
                            success: function(data){
                                $.messager.progress('close');
                                if(data && data.success == "0"){
                                    $('#whgdg').datagrid('reload');
                                    $.messager.alert('提示', '操作成功!');
                                    optDialog.dialog('close');
                                    optDialog.remove();
                                }else{
                                    $.messager.alert('提示', '操作失败!');
                                }
                            }
                        });

                    }
                },{
                    text:'取消',
                    iconCls: 'icon-no',
                    handler:function(){ optDialog.dialog('close');optDialog.remove();}
                }]
            });
        },

        _oneSetState: function(id, state){
            var that = this;
            $.messager.progress();
            $.ajax({
                url : "${basePath}/admin/shop/checkinfo",
                data : {clnfid:id, clnfstata:state},
                type: 'post',
                dataType: 'json',
                error: function(xhr, ts, e){ that.__ajaxError(xhr, ts, e) },
                success: function(data){
                    $.messager.progress('close');
                    if (data=="success"){
                        $('#whgdg').datagrid('reload');
                    }else{
                        $.messager.alert("失败了", "操作失败！");
                    }
                }
            })
        },
        /**
         * 上首页提交
         */
        __upindex:function(ids, formupindex, toupindex) {
            $.messager.progress();
            var params = {ids: ids, formupindex: formupindex, toupindex: toupindex};
            $.post('${basePath}/admin/shop/upindex', params, function(data){
                $("#whgdg").datagrid('reload');
                if (!data.success || data.success != "1"){
                    $.messager.alert("错误", data.errormsg||'操作失败', 'error');
                }
                $.messager.progress('close');
            }, 'json');
        },

        /**
         * 上首页
         * @param idx
         */
        upindex:function(idx){
            var row = this.__getGridRow(idx);
            var that = this;
            $.messager.confirm("确认信息", "确定要将选中的项推上首页吗？", function(r){
                if (r){
                    that.__upindex(row.clnfid, 0, 1);
                }
            })
        },
        /**
         * 取消上首页
         * @param idx
         */
        noupindex: function(idx){
            var row = this.__getGridRow(idx);
            var that = this;
            $.messager.confirm("确认信息", "确定要将选中的项取消推上首页吗？", function(r){
                if (r){
                    that.__upindex(row.clnfid, 1, 0);
                }
            })
        },


        //审核
        checkinfo: function(idx){
            var row = this.__getGridRow(idx);
            var id = row.clnfid;
            var that = this;
            $.messager.confirm('确认对话框', '确定要通过审核吗？', function(r) {
                if (r) {
                    that._oneSetState(id, 2);
                }
            })
        },

        //取消审核
        uncheckinfo: function(idx){
            var row = this.__getGridRow(idx);
            var id = row.clnfid;
            var that = this;
            $.messager.confirm('确认对话框', '确定要打回重审吗？', function(r) {
                if (r) {
                    that._oneSetState(id, 0);
                }
            })
        },

        //发布
        pubinfo: function(idx){
            var row = this.__getGridRow(idx);
            var id = row.clnfid;
            var that = this;
            $.messager.confirm('确认对话框', '确定要发布吗？', function(r) {
                if (r) {
                    that._oneSetState(id, 3);
                }
            })
        },

        //取消发布
        unpubinfo: function(idx){
            var row = this.__getGridRow(idx);
            var id = row.clnfid;
            var that = this;
            $.messager.confirm('确认对话框', '确定要取消发布？', function(r) {
                if (r) {
                    that._oneSetState(id, 2);
                }
            })
        },


        _getSelectIds: function(oldstart){
            var rows={};
            rows = $('#whgdg').datagrid("getSelections");
            if (rows == "" || rows == null) {
                $.messager.alert('提示', '请选择要操作的记录');
                return false;
            }
            var clnfids = _split = "";//id1,id2,id3
            for (var i = 0; i<rows.length; i++){
                if (rows[i].clnfstata == oldstart){
                    clnfids += _split+rows[i].clnfid;
                    _split = ",";
                }
            }
            if (!clnfids){
                $.messager.alert('提示', '没有匹配当前操作的选择记录');
                return false;
            }
            return clnfids;
        },

        _batchSend: function(data){
            var that = this;
            $.ajax({
                url: '${basePath}/admin/shop/checkeinfos',
                data: data,
                type: 'post',
                dataType: 'json',
                error: function(xhr, ts, e){ that.__ajaxError(xhr, ts, e) },
                success: function(data){
                    if(data.success == '0'){
                        $('#whgdg').datagrid('reload');
                    }else{
                        $.messager.alert('提示', '操作失败。原因：'+data.errmsg, 'error');
                    }
                }
            });
        },

        //处理批量审核
        doallup: function(){
            var clnfids = this._getSelectIds(0);
            if (!clnfids){ return; }
            var that = this;
            $.messager.confirm('确认对话框', '您确认将所选择的批量审核吗？', function(r){
                if (r){
                    var data = {clnfid : clnfids,fromstate:0, tostate:2};
                    that._batchSend(data);
                }
            });
        },

        //批量取消审核
        doupall: function(){
            var clnfids = this._getSelectIds(2);
            if (!clnfids){ return; }
            var that = this;
            $.messager.confirm('确认对话框', '您确认将所选择的批量审核吗？', function(r){
                if (r){
                    var data = {clnfid : clnfids,fromstate:2, tostate:0};
                    that._batchSend(data);
                }
            });
        },

        //批量发布
        toallup: function(){
        var clnfids = this._getSelectIds(2);
            if (!clnfids){ return; }
            var that = this;
            $.messager.confirm('确认对话框', '您确认将所选择的进行批量发布吗？', function(r){
                if (r){
                    var data = {clnfid : clnfids,fromstate:2, tostate:3};
                    that._batchSend(data);
                }
            });
        },

        //批量取消发布
        toupall: function(){
            var clnfids = this._getSelectIds(3);
            if (!clnfids){ return; }
            var that = this;
            $.messager.confirm('确认对话框', '您确认将所选择的进行批量取消发布吗？', function(r){
                if (r){
                    var data = {clnfid : clnfids,fromstate:3, tostate:2};
                    that._batchSend(data);
                }
            });
        },

        //置顶
        toTop: function(idx){
            var row = this.__getGridRow(idx);
            var id = row.clnfid;
            var clnftype = row.clnftype;
            var that = this;
            $.messager.confirm('确认对话框', '确定要置顶吗？', function(r) {
                if (r) {
                    that._toTop(id, 1,clnftype);
                }
            })
        },

        //取消置顶
        cancelToTop: function(idx){
            var row = this.__getGridRow(idx);
            var id = row.clnfid;
            var clnftype = row.clnftype;
            var that = this;
            $.messager.confirm('确认对话框', '确定要取消置顶吗？', function(r) {
                if (r) {
                    that._toTop(id, 0,clnftype);
                }
            })
        },


        _toTop: function(id, state,clnftype){
            var that = this;
            $.messager.progress();
            $.ajax({
                url : "${basePath}/admin/shop/toTop",
                data : {clnfid:id, totop:state,clnftype:clnftype},
                type: 'post',
                dataType: 'json',
                error: function(xhr, ts, e){ that.__ajaxError(xhr, ts, e) },
                success: function(data){
                    $.messager.progress('close');
                    if (data=="success"){
                        $('#whgdg').datagrid('reload');
                    }else{
                        $.messager.alert("失败了", "操作失败！");
                    }
                }
            })
        },

    };


</script>
</body>
</html>
