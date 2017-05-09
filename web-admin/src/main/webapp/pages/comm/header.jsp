<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="${basePath }/static/bootstrap/css/bootstrap.min.css"></link>
<link rel="stylesheet" href="${basePath }/static/admin/easyui/themes/bootstrap/easyui.css"></link>
<link rel="stylesheet" href="${basePath }/static/admin/easyui/themes/icon.css"></link>
<link rel="stylesheet" href="${basePath }/static/common/jcrop/css/jquery.Jcrop.css"/>
<script type="text/javascript" src="${basePath }/static/admin/easyui/jquery.min.js" ></script>
<script type="text/javascript" src="${basePath }/static/admin/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${basePath }/static/admin/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">var basePath = '${basePath}'; var imgServerAddr = '${imgServerAddr}';</script>
<script type="text/javascript" src="${basePath }/static/admin/js/common.js"></script>
<script type="text/javascript" src="${basePath }/static/admin/js/common_img.js"></script>
<script type="text/javascript" src="${basePath }/static/common/js/easyui.wh.tools.js"></script>
<script type="text/javascript" src="${basePath }/static/common/jcrop/js/jquery.Jcrop.js"></script>
<script type="text/javascript" src="${basePath }/static/common/js/whg.sys.base.data.js"></script>


<style type="text/css">
    .panel-title{ color:#333; font-size:14px;}
    .grid-control-group {padding: 10px; background: #fafafa;}
    .window, .window-shadow{
        -webkit-border-radius:0px;
        -moz-border-radius:0px;
        border-radius:0px;
    }
    .datagrid-header td, .datagrid-body td, .datagrid-footer td{
        padding-top:6px;
        padding-bottom:6px;
    }
    .datagrid-row-selected{
        background-color:#e5e5e5;
        color:#000;
    }
    /*.datagrid-row-selected .l-btn{
        color:#fff;
    }
    .datagrid-row-selected .l-btn-plain{
        background-color:#307cb8
    }
    .datagrid-row-selected .l-btn-plain:hover{
        background-color:#3f92d2;
        border-color:#3f92d2;
    }*/
    .panel-title{
        padding:10px 15px;
        height:auto;
    }
    input[type=radio], input[type=checkbox] {
        margin: 2px 5px 0 0;
        margin-top: 1px \9;
        line-height: normal;
        vertical-align: top;
    }
    .textbox{
        margin-right:6px;
    }
    .datagrid-header, .datagrid-toolbar, .datagrid-pager, .datagrid-footer-inner{
        padding:10px 5px 10px 0px
    }
</style>