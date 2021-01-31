<%@ taglib prefix="springForm" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="jstlC" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title><spring:message code="error"/></title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css"
          rel="stylesheet">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>

<body>
    <jsp:include page="locale.jsp"/>
    <div class="container">
        <h3><spring:message code="error"/></h3>
        <h5><spring:message code="error.feedback"/></h5>
        <a href="/"><spring:message code="error.back"/></a>
    </div>
    <jsp:include page="navbottom.jsp"/>
</body>
</html>