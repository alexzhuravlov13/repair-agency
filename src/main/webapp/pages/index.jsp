<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>

<head>
    <title>//:TODO</title>
    <link href="${pageContext.request.contextPath}/webjars/bootstrap/4.4.1/css/bootstrap.min.css" rel="stylesheet">
</head>

<body>
<jsp:include page="navbar.jsp"/>
<p><h5>Welcome, ${pageContext.request.userPrincipal.name}</h5>


<script src="${pageContext.request.contextPath}/webjars/jquery/3.4.1/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/webjars/bootstrap/4.4.1/js/bootstrap.min.js"></script>
</body>
</html>