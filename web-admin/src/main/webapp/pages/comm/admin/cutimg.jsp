<%--
  Created by IntelliJ IDEA.
  User: wangxl
  Date: 2017/3/27
  Time: 11:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% request.setAttribute("basePath", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath());%>
<html>
<head>
    <title>图片裁剪</title>
    <link rel="stylesheet" href="${basePath}/static/common/jcrop/css/jquery.Jcrop.min.css">
    <%--<link rel="stylesheet" type="text/css" href="${basePath }/static/admin/easyui/themes/bootstrap/easyui.css">--%>
    <script type="text/javascript" src="${basePath }/static/admin/easyui/jquery.min.js" ></script>
    <script type="text/javascript" src="${basePath}/static/common/jcrop/js/jquery.Jcrop.min.js"></script>
    <%--<script type="text/javascript" src="${basePath }/static/admin/easyui/jquery.easyui.min.js"></script>--%>
</head>
<body>

<img id="whg_img_cut" src="${param.imgurl}"/>

<%--
<input class="easyui-slider" value="0"  style="width:300px" data-options="showTip:true, rule:[0,'|', 1,'|',2,'|',3], onChange:imgChange" />
--%>


<script type="text/javascript">
var JcropApi;
var cutw  = parseInt('${param.w}');//裁剪宽高
var cuth  = parseInt('${param.h}');//裁剪高度
var wh = parseInt('${param.wh}');//父窗口高度
var ww = parseInt('${param.ww}');//父窗口宽度

/*//
function imgChange(newV, oldV) {
    //alert(newV);
    var i = newV*100;
    JcropApi.setSelect([0, 0, cutw+i, cuth+i]);
}*/

$(function () {
    $('#whg_img_cut').Jcrop({
        allowSelect: false,
        allowResize: false,
        bgOpacity: 0.5,
        bgColor: 'white',
        boxWidth: ww-50,
        boxHeight: wh-140
    }, function () {
        JcropApi = this;
        JcropApi.setSelect([0, 0, cutw, cuth]);
        JcropApi.setOptions({
            bgFade: true
        });
        JcropApi.ui.selection.addClass('jcrop-selection');
    });
})
</script>
</body>
</html>
