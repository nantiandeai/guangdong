<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css" href="${basePath }/static/admin/easyui/themes/bootstrap/easyui.css">
<link rel="stylesheet" type="text/css" href="${basePath }/static/admin/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${basePath }/static/admin/easyui/themes/color.css">
<script type="text/javascript" src="${basePath }/static/admin/easyui/jquery.min.js" ></script>
<script type="text/javascript" src="${basePath }/static/admin/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${basePath }/static/admin/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">var basePath = '${basePath}'; var imgServerAddr = '${imgServerAddr}';</script>
<script type="text/javascript" src="${basePath }/static/admin/js/common.js"></script>
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

    .datagrid-toolbar, .datagrid-pager{
        padding:10px 0px 10px 10px
    }

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



    /** 表单for wangxl */
    form.whgff{
        margin: 0 auto;
    }

    form.whgff h2{
       font-size:24px;
        padding-left:150px;
        margin-bottom:25px;
        margin-top:30px;
    }

    form.whgff > .whgff-row{
        padding: 10px 0;
        overflow: hidden;
    }
    form.whgff > .whgff-row > .whgff-row-label{
        float: left;
        width: 10%;
        text-align: right;
        font-weight:normal;
        font-size: 12px;
        line-height:24px;
     }
    form.whgff > .whgff-row > .whgff-row-label i{
        font-size:18px;
        display: inline-block;
        margin-right:5px;
        color:#ff0000;
        font-style:normal;
        vertical-align: middle;
        margin-top:5px;
    }
    form.whgff > .whgff-row > .whgff-row-input{
        float: left;
        width: 90%;
        overflow: hidden;
    }
    form.whgff > .whgff-row > .whgff-row-input > .whgff-row-input-imgview{
        border: 1px #ddd solid;
        width: 300px;
        height: 225px;
        border-radius: 5px;
        overflow: hidden;
        background:url("${basePath}/static/admin/img/no-img.png") no-repeat 50% 50% #f0f0f0;
    }
    form.whgff > .whgff-row > .whgff-row-input > .whgff-row-input-imgfile{
        margin-left: 320px;
        margin-top: -48px;
    }

    .whgff-row-input .radio label,.whgff-row-input .checkbox label{ display: inline-block; margin-right:30px; font-size:12px;}
    .whgff-row-input .radio, .whgff-row-input .checkbox{ margin:4px 0px 10px 0px}
    .whgff-row-input-imgfile i{color:#999; font-size:12px;  display: block; margin-top:10px; font-style: normal}
    .whgd-gtb-btn{margin-bottom:10px;}
    .body_add{background-color:#f5f5f5; border:1px #ddd solid;}
    .whgff-row-map{
        float:left;
        padding-left:113px;
        margin-top:20px;
        position:relative;
    }
    .whgff-row-map .seat-ul-div{
        max-width:650px;
        overflow-x:scroll;
    }
    .whgff-row-map .help-cont{
        position:absolute;
        width:200px;
        height:150px;
        right:-260px;
        top:49px;
    }
    .whgff-row-map .seat-row-control{
        position:absolute;
        width:30px;
        top:50px;
        right:-30px;
        font-size:12px;
    }
    .whgff-row-map .seat-row-control p{
        cursor: pointer;
    }
    .whgff-row-map .seat-column-control{
        padding-left:40px;
        height:30px;
        font-size:12px;
    }
    .whgff-row-map .seat-column-control p{
        height:30px;
        line-height:30px;
        width:75px;
        float:left;
        margin-right:5px;
        cursor: pointer;
    }
    .whgff-row-map .seat-row-control p,.whgff-row-map .seat-column-control p{
        color:#fff;
        background-color:#6da82e;
        text-align: center;
    }
    .whgff-row-map .led-cont{
        padding-left:40px;
    }
    .whgff-row-map .led-bg{
        height: 30px;
        width: 60%;
        margin:0px auto;
        background-color:#aaa;
        margin-bottom:20px;
        text-align: center;
        color:#fff;
        line-height:30px;
    }
    .whgff-row-map .help-row-number{
        position:absolute;
        width:55px;
        left:60px;
        top:49px;
        font-size:12px;
    }
    .whgff-row-map .help-cont p,.whgff-row-map .help-row-number p,.whgff-row-map .seat-row-control p{
        margin-bottom:5px;
        height:30px;
        line-height:30px;
    }
    .whgff-row-map .help-cont span{
        margin-right:10px;
        vertical-align: middle;
        display:inline-block;
        width:30px;
        height:30px;
    }
    .whgff-row-map ul {
        height:30px;
        margin-bottom:5px;
    }
    .whgff-row-map ul li{
        height:30px;
        width:75px;
        float:left;
        list-style: none;
        margin-right:5px;
        color:#fff;
        line-height:30px;
        text-align: center;
    }
    .whgff-row-map ul li input{
        height: 30px;
        width:75px;
        background-color:#fff;
        border:1px #999 solid;
        text-align: center;
        line-height:28px;
        color:#000;
    }
    .whgff-row-map ul .type-0,.whgff-row-map .help-cont .type-0{
        background-color:#6da82e;
    }
    .whgff-row-map ul .type-1,.whgff-row-map .help-cont .type-1{
        background-color:#aaa;
    }
    .whgff-row-map ul .type-2,.whgff-row-map .help-cont .type-2{
        background-color:#d82f2f;
    }
    .whgff-row-map ul .active,.whgff-row-map .help-cont .active{
        background-color: #f48331 ;
    }
    .seat-operation{
        margin-top:20px;
        margin-left:38px;
    }
    .whg-week-cont{
        border-left:1px #eee solid;
        margin-top:10px;
    }
    .whg-week-column{
        width:192px;
        display: inline-block;
        vertical-align: top;
        border:1px #eee solid;
        border-left:0px;
        background-color:#fff;
        padding:10px 15px;
        position:relative;
    }
    .whg-week-cont .disabled{
        background-color:#eee;
    }
    .whg-week-column dl{
        margin-bottom:5px;
    }
    .whg-week-column dl dt{
        font-weight:normal;
        text-indent: 50px;
        margin-bottom:5px;

    }
    .whg-week-cont .disabled dt{
        color:#999;
    }

    .whg-week-column dl dd{
        margin-bottom:5px;
    }
    .whg-week-column dl dd .del{
        display: none;
        width:20px;
        height:20px;
        background:url("${basePath}/static/admin/img/del.png") no-repeat 50% 50%;
        vertical-align: middle;
    }
    .whg-week-column dl dd:hover .del{
        display: inline-block;
        cursor: pointer;
    }

    .whg-week-column .add{
        width:16px;
        height:16px;
        background:url("${basePath}/static/admin/img/add.png") no-repeat 50% 50%;
        margin-left:60px;
        cursor:pointer;
    }
    .whg-week-cont .disabled .add{
        cursor:default;
        background:none;
    }
    .whg-week-timeinput{
        width:60px;
    }
    .validaMessage{
        line-height:22px;
        padding:2px 5px;
        background-color:#ffffcc;
        border:#cc9933 1px solid;
        text-align: center;
        width:600px;
        margin-top:10px;
        -webkit-border-radius:5px;
        -moz-border-radius:5px;
        border-radius:5px;
    }
</style>