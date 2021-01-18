<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>

<head>
   <title>Users list</title>
   <link href="/webjars/bootstrap/4.4.1/css/bootstrap.min.css" rel="stylesheet">
</head>

<body>
<jsp:include page="navbar.jsp"/>
        <p><h5>Welcome, ${pageContext.request.userPrincipal.name}</h5>


	<script src="/webjars/jquery/3.4.1/jquery.min.js"></script>
	<script src="/webjars/bootstrap/4.4.1/js/bootstrap.min.js"></script>
</body>
</html>