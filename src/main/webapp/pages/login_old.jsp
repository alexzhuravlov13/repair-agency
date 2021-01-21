<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Log in with your account</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css"
          rel="stylesheet">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>

<body>
<div class="container">
    <form:form method="POST" action="${contextPath}/login" class="form-signin">
        <h2 class="form-heading">Log in</h2>
        <div class="form-group ">
            <label>
                <input name="username" type="text" class="form-control" placeholder="Username"
                       autofocus="autofocus"/>
            </label>
            <label>
                <input name="password" type="password" class="form-control" placeholder="Password"/>
            </label>
        </div>

        <div class="form-group">
            <div class="col-xs-15">
                <div>
                    <!-- Check for login error -->
                    <c:if test="${param.error != null}">
                        <div class="alert alert-danger col-xs-offset-1 col-xs-10">
                            <span>${error}</span>
                        </div>
                    </c:if>
                    <!-- Check for logout -->
                    <c:if test="${param.logout != null}">
                        <div class="alert alert-success col-xs-offset-1 col-xs-10">
                            <span>${message}</span>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>

        <div class="form-group">
            <button type="submit" class="btn btn-primary btn-block">Log In</button>
            <h4 class="text-center"><a href="${contextPath}/registration">Create an account</a></h4>
        </div>


    </form:form>
</div>

<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.min.js"></script>
</body>
</html>