<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!doctype html>
<html lang="en">

<head>
    <title>Users list</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css"
          rel="stylesheet">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <span class="navbar-brand">Bugtracker</span>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <a class="nav-link" href="/">Home <span class="sr-only">(current)</span></a>
            </li>
            <li class="nav-item active">
                <sec:authorize access="hasRole('ROLE_ADMIN') or hasRole('ROLE_BOSS')">
                    <a class="nav-link" href="${pageContext.request.contextPath}/users/list">Users</a>
                </sec:authorize>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/tickets/list?userId=">Tickets</a>
            </li>
        </ul>
        <c:url value="/logout?${_csrf.parameterName}=${_csrf.token}" var="logoutUrl"/>
        <form:form class="form-inline my-2 my-lg-0" action="${logoutUrl}" method="POST" enctype="multipart/form-data">
            <span class="navbar-text mr-sm-2">${pageContext.request.userPrincipal.name}</span>
            <button type="Logout" class="btn btn-outline-danger">Log out</button>
        </form:form>
    </div>
</nav>

<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.min.js"></script>
</body>
</html>