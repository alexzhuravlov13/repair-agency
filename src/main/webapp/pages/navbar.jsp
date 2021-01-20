<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>

<head>
   <title>Users list</title>
   <link href="/webjars/bootstrap/4.4.1/css/bootstrap.min.css" rel="stylesheet">
</head>
<body><nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <span class="navbar-brand">Bugtracker</span>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
          <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
              <a class="nav-link" href="/">Home <span class="sr-only">(current)</span></a>
            </li>
            <li class="nav-item active">
            <sec:authorize access="hasRole('ROLE_ADMIN') or hasRole('ROLE_BOSS')">
                    <a class="nav-link" href="/users/list">Users</a>
              </sec:authorize>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="/tickets/list?userId=">Tickets</a>
            </li>
            </ul>
            <c:url value="/logout?${_csrf.parameterName}=${_csrf.token}" var="logoutUrl"/>
            <form:form class="form-inline my-2 my-lg-0" action="${logoutUrl}" method="POST" enctype="multipart/form-data">
                <span class="navbar-text mr-sm-2">${pageContext.request.userPrincipal.name}</span>
            <button type="Logout" class="btn btn-outline-danger">Log out</button>
            </form:form>
        </div>
      </nav>

	<script src="/webjars/jquery/3.4.1/jquery.min.js"></script>
	<script src="/webjars/bootstrap/4.4.1/js/bootstrap.min.js"></script>
</body>
</html>