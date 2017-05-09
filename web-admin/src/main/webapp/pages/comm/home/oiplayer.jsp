<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <%@include file="/pages/comm/agdhead.jsp"%>
    <title></title>
</head>
<body class="oiplayer-example">
    <video width="700" height="354" poster="${imgUrl}" controls oncontextmenu="return false" autoplay="autoplay">
        <source type="${type}" src="${url}" />
    </video>

<script>
    $(function () {
        $('body.oiplayer-example').oiplayer({
            server: '${basePath}',
            jar: '/static/assets/js/plugins/oiplayer-master/plugins/cortado-ovt-stripped-0.6.0.jar',
            flash: '/static/assets/js/plugins/oiplayer-master/plugins/flowplayer-3.2.7.swf',
            controls: 'top',
            log: 'info'
        });
    });
</script>
</body>
</html>
