<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8"/>
    <meta name="renderer" content="webkit">
    <meta name="keywords" content="Web3DMol, protein, structure, visualization, WebGL">
    <meta name="description"
          content="Web3DMol is a web application focusing on protein structure visualization in modern web browsers.">
    <meta name="author" content="MaoXiang Shi">
    <title>Web3DMol - interactive protein structure visualization using WebGL</title>
    <style type="text/css">
        html, body, div, img, input, button, textarea, select {
            margin: 0;
            padding: 0;
            border: 0;
            outline: none;
        }

        html {
            width: 100%;
            height: 100%;
            overflow: hidden;
        }

        body {
            width: 100%;
            height: 100%;
            overflow: hidden;
            background-color: #000000;
        }

        #app {
            width: 100%;
            height: 100%;
        }
    </style>
</head>

<body>
<div id="app"></div>
<script type="text/javascript" src="<spring:url value="/resources/js/web3dmol.js?20180723.js"/>"></script>
<script type="text/javascript">
    'ActiveXObject' in window ? alert('Sorry! But IE is not support WebGL until Edge.\nPlease use Edge or other browsers.\n( We recommend Google Chrome )') : void(0);
        w3m.api.init('app', '1mbs');

    <%--function loadpdb() {--%>
        <%--w3m.pdb("${data}");--%>
    <%--}--%>
</script>
</body>

</html>