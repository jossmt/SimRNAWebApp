<%--
  Created by IntelliJ IDEA.
  User: jossmillertodd
  Date: 31/07/2018
  Time: 19:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<link href="<c:url value="/resources/font-awesome/css/font-awesome.css"/>" rel="stylesheet">

<html>
<head>
    <title>Title</title>
</head>
<body>

<tiles:insertAttribute name="header"/>
<tiles:insertAttribute name="body"/>
</body>
<div id="clearer"></div>
<%--<tiles:insertAttribute name="footer"/>--%>
</html>
