<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<% request.setAttribute("basePath", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath());%>
<% request.setAttribute("resourceid", request.getParameter("rsid")); %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">

    <%@include file="/pages/comm/admin/header.jsp"%>
    
     <c:choose>
        <c:when test="${type eq 'editList'}">
            <c:set var="pageTitle" value="活动编辑列表"></c:set>
        </c:when>
        <c:when test="${type eq 'check'}">
            <c:set var="pageTitle" value="活动审核列表"></c:set>
        </c:when>
        <c:when test="${type eq 'publish'}">
            <c:set var="pageTitle" value="活动发布列表"></c:set>
        </c:when>
        <c:when test="${type eq 'del'}">
            <c:set var="pageTitle" value="回收站"></c:set>
        </c:when>
        <c:otherwise>
            <c:set var="pageTitle" value="活动信息"></c:set>
        </c:otherwise>
    </c:choose>
    <title>${pageTitle}</title>
    
    <script type="text/javascript" charset="utf-8" src="${basePath}/static/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="${basePath}/static/ueditor/ueditor.all.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="${basePath}/static/ueditor/lang/zh-cn/zh-cn.js"></script>
</head>
<body>

<!-- 表格 -->
<table id="whgdg" title="${pageTitle}" class="easyui-datagrid" style="display: none"
       data-options="fit:true, striped:true, rownumbers:true, fitColumns:false, singleSelect:true, checkOnSelect:true, selectOnCheck:true, pagination:true, toolbar:'#whgdg-tb', url:'${basePath}/admin/activity/act/srchList4p?__pageType=${type}'">
    <thead>
    <tr>
        <th data-options="field:'name', sortable: true<%--,width:80--%>">名称</th>
        <th data-options="field:'starttime', <%--width:80,--%>sortable: true, formatter:WhgComm.FMTDate ">开始时间</th>
        <th data-options="field:'endtime', <%--width:80,--%>sortable: true, formatter:WhgComm.FMTDate ">结束时间</th>
        <th data-options="field:'telphone'<%--, width:80--%>">联系手机</th>
        <th data-options="field:'address'<%--, width:120--%>">地址</th>
         <th data-options="field:'statemdfdate',sortable: true,<%-- width:80,--%> formatter:WhgComm.FMTDateTime ">操作时间</th>
        <th data-options="field:'state', <%--width:60,--%> formatter:WhgComm.FMTBizState">状态</th>
        <th data-options="field:'_opt', <%--width:${type eq 'publish'?'750':'430'}, --%>fixed:true, formatter:WhgComm.FMTOpt, optDivId:'whgdg-opt'">操作</th>
    </tr>
    </thead>
</table>
<!-- 表格 END -->

<!-- 表格操作工具栏 -->
<div id="whgdg-tb" style="display: none;">
    <div class="whgd-gtb-btn">
        <shiro:hasPermission name="${resourceid}:add"><a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="doAdd();">添加活动</a></shiro:hasPermission>
    </div>
    <div class="whgdg-tb-srch">
        <input class="easyui-textbox" style="width: 200px;" name="name" data-options="prompt:'请输入活动名称', validType:'length[1,32]'" />
        <input class="easyui-combobox" style="width: 200px;" name="state" panelHeight="auto" limitToList="true" data-options="prompt:'请选择状态', value:'', valueField:'id', textField:'text', data:WhgComm.getBizState()"/>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="WhgComm.search('#whgdg', '#whgdg-tb');">查 询</a>
    </div>
</div>
<!-- 表格操作工具栏-END -->

<!-- 操作按钮 -->
<div id="whgdg-opt" style="display: none;">
    <shiro:hasPermission name="${resourceid}:view"><a href="javascript:void(0)" class="easyui-linkbutton" plain="true" method="doSee">查看</a></shiro:hasPermission>
    <shiro:hasPermission name="${resourceid}:edit"><a href="javascript:void(0)" class="easyui-linkbutton" plain="true" validKey="state" validVal="1,9,2,4" method="doEdit">编辑</a></shiro:hasPermission>
    <shiro:hasPermission name="${resourceid}:ccmanager"><a href="javascript:void(0)" class="easyui-linkbutton" plain="true" validKey="state" validVal="1,9,2,4,6" method="doUpdateScreenings">场次管理</a></shiro:hasPermission>
    <shiro:hasPermission name="${resourceid}:publish"> <a href="javascript:void(0)" class="easyui-linkbutton" plain="true" validKey="state" validVal="2,4" method="doPublish">发布</a></shiro:hasPermission>
    <shiro:hasPermission name="${resourceid}:publishoff"><a href="javascript:void(0)" class="easyui-linkbutton" plain="true" validKey="state" validVal="6"  method="publishoff">撤销发布</a></shiro:hasPermission>
    <shiro:hasPermission name="${resourceid}:checkon"> <a href="javascript:void(0)" class="easyui-linkbutton" plain="true" validKey="state" validVal="9" method="doCheckOn">审核通过</a></shiro:hasPermission>
    <shiro:hasPermission name="${resourceid}:back"><a href="javascript:void(0)" class="easyui-linkbutton" plain="true" validKey="state" validVal="2,9"  method="reBackEdit">打回</a></shiro:hasPermission>
    <shiro:hasPermission name="${resourceid}:edit"><a href="javascript:void(0)" class="easyui-linkbutton" plain="true" validKey="state" validVal="1,9,2,4,6" method="doResource">资源管理</a></shiro:hasPermission>
    <shiro:hasPermission name="${resourceid}:info"><a href="javascript:void(0)" class="easyui-linkbutton" plain="true" method="info">资讯公告管理</a></shiro:hasPermission>
    <shiro:hasPermission name="${resourceid}:order"><a href="javascript:void(0)" class="easyui-linkbutton" plain="true" validKey="state" validVal="1,9,2,4,6" method="doOrder">订单管理</a></shiro:hasPermission>
    <shiro:hasPermission name="${resourceid}:checkgo"> <a href="javascript:void(0)" class="easyui-linkbutton" plain="true" validKey="state" validVal="1,9,2,4" method="doCheckgo">送审</a></shiro:hasPermission>
    <shiro:hasPermission name="${resourceid}:upindex"><a href="javascript:void(0)" class="easyui-linkbutton" validFun="_upindexon" plain="true" method="upindex">上首页</a></shiro:hasPermission>
    <shiro:hasPermission name="${resourceid}:upindexoff"><a href="javascript:void(0)" class="easyui-linkbutton" validFun="_upindexoff" plain="true" method="noupindex">取消上首页</a></shiro:hasPermission>
    <shiro:hasPermission name="${resourceid}:recommend"> <a href="javascript:void(0)" class="easyui-linkbutton" plain="true" validFun="validRecommendOn" method="doCommend">推荐</a></shiro:hasPermission>
    <shiro:hasPermission name="${resourceid}:recommendoff"> <a href="javascript:void(0)" class="easyui-linkbutton" plain="true" validFun="validRecommendOff" method="commendOff">取消推荐</a></shiro:hasPermission>
    <shiro:hasPermission name="${resourceid}:undel"> <a href="javascript:void(0)" class="easyui-linkbutton" plain="true" validKey="delstate" validVal="1" method="reBack">还原</a></shiro:hasPermission>
    <c:choose>
        <c:when test="${type eq 'del'}">
             <shiro:hasPermission name="${resourceid}:del"> <a href="javascript:void(0)" class="easyui-linkbutton" plain="true" validKey="delstate" validVal="1" method="doDel">删除</a></shiro:hasPermission>
        </c:when>
        <c:otherwise>
            <shiro:hasPermission name="${resourceid}:del"> <a href="javascript:void(0)" class="easyui-linkbutton" plain="true" validKey="state" validVal="1,2,4,6,9" method="doDel">回收</a></shiro:hasPermission>
        </c:otherwise>
    </c:choose>
    
   
</div>
<!-- 操作按钮-END -->

<!--发布设置发布时间表单 -->
<div id="whgwin-add" style="display: none">
    <form id="whgff" class="whgff" method="post">
        <div class="whgff-row">
            <div class="whgff-row-label _check" style="width: 30%"><i>*</i>发布时间：</div>
            <div class="whgff-row-input" style="width: 70%">
                <input class="easyui-datetimebox statemdfdate" name="statemdfdate" style="width:200px; height:32px" data-options="required:true">
            </div>
        </div>
    </form>
</div>
<div id="whgwin-add-btn" style="text-align: center; display: none">
    <div style="display: inline-block; margin: 0 auto">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" id="btn" >确 定</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#whgwin-add').dialog('close')">关 闭</a>
    </div>
</div>
<!-- 发布设置发布时间表单 END -->

<!-- script -->
<script type="text/javascript">
    function _upindexon(idx){
        var row = $("#whgdg").datagrid("getRows")[idx];
        return row.state == 6 && row.upindex == 0;
    }
    function _upindexoff(idx){
        var row = $("#whgdg").datagrid("getRows")[idx];
        return row.state == 6 && row.upindex == 1;
    }

	function validRecommendOn(idx){
		var is = false;
		var curRow = $('#whgdg').datagrid('getRows')[idx];
		if(curRow.state == '6' && curRow.isrecommend != '1'){
			is = true;
		}
		return is;
	}
	
	function validRecommendOff(idx){
		var is = false;
		var curRow = $('#whgdg').datagrid('getRows')[idx];
		if(curRow.state == '6' && curRow.isrecommend == '1'){
			is = true;
		}
		return is;
	}

    /**
     * 添加活动
     */
    function doAdd() {
        //WhgComm.editDialog('${basePath}/admin/activity/act/view/add');
        window.parent.$("a[title='添加活动']").click();
    }

    /**
     * 编辑信息
     * @param idx 行下标
     */
    function doEdit(idx) {
        var curRow = $('#whgdg').datagrid('getRows')[idx];
        WhgComm.editDialog('${basePath}/admin/activity/act/view/edit?id='+curRow.id);
    }

    function doUpdateScreenings(idx) {
        var curRow = $('#whgdg').datagrid('getRows')[idx];
        WhgComm.editDialog('${basePath}/admin/activity/act/view/screenings?id='+curRow.id);
    }

    /**
     * 查看资料
     * @param idx 行下标
     */
    function doSee(idx) {
        var curRow = $('#whgdg').datagrid('getRows')[idx];
        WhgComm.editDialog('${basePath}/admin/activity/act/view/edit?id='+curRow.id+"&onlyshow=1");
    }
    
    /**
     * 送审 [1,5]->9
     * @param idx
     */
    function doCheckgo(idx){
        var row = $("#whgdg").datagrid("getRows")[idx];
        $.messager.confirm("确认信息", "确定要送审选中的项吗？", function(r){
            if (r){
                __updStateSend(row.id, "1", 9);
            }
        })
    }
     
     /**
      * 审通过 [9]->2
      * @param idx
      */
     function doCheckOn(idx){
         var row = $("#whgdg").datagrid("getRows")[idx];
         $.messager.confirm("确认信息", "确定要审核选中的项吗？", function(r){
             if (r){
                 __updStateSend(row.id, 9, 2);
             }
         })
     }
      
      /**
       * 审不通过 [9]->1
       * @param idx
       */
      function doCheckOff(idx){
          var row = $("#whgdg").datagrid("getRows")[idx];
          $.messager.confirm("确认信息", "确定要打回选中的项吗？", function(r){
              if (r){
                  __updStateSend(row.id, 9, 1);
              }
          })
      }

    /**
     * 上首页
     * @param idx
     */
    function upindex(idx){
        var row = $("#whgdg").datagrid("getRows")[idx];
        $.messager.confirm("确认信息", "确定要将选中的项推上首页吗？", function(r){
            if (r){
                __upindex(row.id, 0, 1);
            }
        })
    }
    /**
     * 取消上首页
     * @param idx
     */
    function noupindex(idx){
        var row = $("#whgdg").datagrid("getRows")[idx];
        $.messager.confirm("确认信息", "确定要将选中的项取消推上首页吗？", function(r){
            if (r){
                __upindex(row.id, 1, 0);
            }
        })
    }
    /**
     * 上首页提交
     */
    function __upindex(ids, formupindex, toupindex) {
        $.messager.progress();
        var params = {ids: ids, formupindex: formupindex, toupindex: toupindex};
        $.post('${basePath}/admin/activity/act/upindex', params, function(data){
            $("#whgdg").datagrid('reload');
            if (!data.success || data.success != "1"){
                $.messager.alert("错误", data.errormsg||'操作失败', 'error');
            }
            $.messager.progress('close');
        }, 'json');
    }

    /**
     * 发布 [2,4]->6
     * @param idx
     */
    function doPublish(idx){
        var row = $("#whgdg").datagrid("getRows")[idx];
        $('#whgwin-add').dialog({
            title: '设置发布时间',
            cache: false,
            modal: true,
            width: '400',
            height: '150',
            maximizable: true,
            resizable: true,
            buttons: '#whgwin-add-btn',
            onOpen: function () {
                $(".statemdfdate").datetimebox('setValue',new Date().Format('yyyy-MM-dd hh:mm:ss'));
                $('#whgff').form({
                    url : '${basePath}/admin/activity/act/updstate',
                    onSubmit : function(param) {
                        param.ids = row.id;
                        if(2 == row.state){
                            param.fromState = 2;
                        }else if(4 == row.state){
                            param.fromState = 4;
                        }
                        //param.fromState = "2,4";
                        param.toState = 6;
                        var isValid = $(this).form('enableValidation').form('validate');
                        if(isValid){
                            $.messager.progress();
                        }else{
                            $("#btn").off("click").one("click",function () { $('#whgff').submit(); });
                        }
                        return isValid;
                    },
                    success : function(data) {
                        $.messager.progress('close');
                        var Json = $.parseJSON(data);
                        if(Json.success == "1"){
                            $('#whgdg').datagrid('reload');
                            $('#whgwin-add').dialog('close');
                        }else{
                            $.messager.alert("提示", data.errorMsg);
                        }
                    }
                });
                $("#btn").off("click").one("click",function () { $('#whgff').submit(); });
            }
        })
        /*$.messager.confirm("确认信息", "确定要发布选中的项吗？", function(r){
            if (r){
                if(2 == row.state){
                    __updStateSend(row.id, 2, 6);
                }else if(4 == row.state){
                    __updStateSend(row.id, 4, 6);
                }
            }
        })*/
    }

       /**
        * 取消发布 [6]->4
        * @param idx
        */
       function publishoff(idx){
           var row = $("#whgdg").datagrid("getRows")[idx];
           $.messager.confirm("确认信息", "确定要取消发布选中的项吗？", function(r){
               if (r){
                   __updStateSend(row.id, 6, 4);
               }
           })
       }
        
        /**
         * 打回编辑 [6]->1
         * @param idx
         */
        function reBackEdit(idx){
            var row = $("#whgdg").datagrid("getRows")[idx];
            var fromState = row.state;
            $.messager.confirm("确认信息", "确定要打回编辑选中的项吗？", function(r){
                if (r){
                    __updStateSend(row.id, fromState, 1);
                }
            })
        }
         
        
     
     /**
     * 状态变更（送审、审核、发布、推荐...操作状态变更）
     * @param idx
     */
    function __updStateSend(ids, fromState, toState){
         $.messager.progress();
         var params = {ids: ids, fromState: fromState, toState: toState};
         $.post('${basePath}/admin/activity/act/updstate', params, function(data){
             $("#whgdg").datagrid('reload');
             if (!data.success || data.success != "1"){
                 $.messager.alert("错误", data.errormsg||'操作失败', 'error');
             }
             $.messager.progress('close');
         }, 'json');
     }

    /**
     * 删除
     * @param idx
     */
    function doDel(idx) {
        var curRow = $('#whgdg').datagrid('getRows')[idx];
        $.messager.confirm('提示', '您确定要删除此记录吗？', function(r){
            if (r){
                $.ajax({
                    type: "POST",
                    cache: false,
                    url: getFullUrl('/admin/activity/act/del'),
                    data: {ids : curRow.id,delStatus:1},
                    success: function(Json){
                        if(Json && Json.success == '1'){
                            $('#whgdg').datagrid('reload');
                        } else {
                            $.messager.alert('提示', '操作失败:'+Json.errormsg+'！', 'error');
                        }
                    }
                });
            }
        });
    }
    
    /**
     * 还原
     * @param idx
     */
    function reBack(idx) {
        var curRow = $('#whgdg').datagrid('getRows')[idx];
        $.messager.confirm('提示', '您确定要还原此记录吗？', function(r){
            if (r){
                $.ajax({
                    type: "POST",
                    cache: false,
                    url: getFullUrl('/admin/activity/act/reBack'),
                    data: {ids : curRow.id,delStatus:0},
                    success: function(Json){
                        if(Json && Json.success == '1'){
                            $('#whgdg').datagrid('reload');
                        } else {
                            $.messager.alert('提示', '操作失败:'+Json.errormsg+'！', 'error');
                        }
                    }
                });
            }
        });
    }

    /**
    * 资源管理
    * */
    function doResource(idx) {
        var curRow = $('#whgdg').datagrid('getRows')[idx];
        WhgComm.editDialog('${basePath}/admin/train/resource/view/list?reftype=2&id=' + curRow.id);
    }

    /**
     * 订单管理
     * */
    function doOrder(idx) {
        var curRow = $('#whgdg').datagrid('getRows')[idx];
        WhgComm.editDialog('${basePath}/admin/activity/act/view/orderList?reftype=2&id=' + curRow.id);
    }
    
    /**
     * 推荐状态变更
     * 0-1
     * @param idx
     */
    function _updCommend(ids, fromState, toState){
    	$.messager.progress();
        var params = {ids: ids, fromState: fromState, toState: toState};
        $.post('${basePath}/admin/activity/act/updCommend', params, function(data){
            $("#whgdg").datagrid('reload');
            if (!data.success || data.success != "1"){
                $.messager.alert("错误", data.errormsg||'操作失败', 'error');
            }
            $.messager.progress('close');
        }, 'json');
    }
    
    /**
     * 推荐状态变更 [0]->1
     * @param idx
     */
    function doCommend(idx){
        var row = $("#whgdg").datagrid("getRows")[idx];
        $.messager.confirm("确认信息", "确定要推荐选中的项吗？", function(r){
            if (r){
            	_updCommend(row.id, 0, 1);
            }
        })
    }
     /**
      * 推荐状态变更 [1]->0
      * @param idx
      */
     function commendOff(idx){
         var row = $("#whgdg").datagrid("getRows")[idx];
         $.messager.confirm("确认信息", "确定要取消推荐选中的项吗？", function(r){
             if (r){
             	_updCommend(row.id, 1, 0);
             }
         })
     }

    /**
     * 资讯公告
     * @param idx
     */
    function info(idx) {
        var row = $("#whgdg").datagrid("getRows")[idx];
        WhgComm.editDialog('${basePath}/admin/info/view/list?entityid='+row.id+'&entity=2');
    }
</script>
<!-- script END -->
</body>
</html>