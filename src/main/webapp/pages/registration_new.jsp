<%@ taglib prefix="springForm" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="jstlC" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <springForm:form method="POST" modelAttribute="userForm"
                     class="mx-auto p-5 m-3" style="width: 50%; background-color: #eee;">
        <h2>Create your account</h2>

        <spring:bind path="email">
            <div class="row mb-3 ${status.error ? 'has-error' : ''}">
                <springForm:input type="email" path="email" class="form-control"
                                  placeholder="Email"
                                  autofocus="true"/>
                <springForm:errors path="email"/>
            </div>
        </spring:bind>

        <spring:bind path="firstName">
            <div class="row mb-3 ${status.error ? 'has-error' : ''}">
                <springForm:input type="text" path="firstName" class="form-control"
                                  placeholder="First Name"
                                  autofocus="true"/>
                <springForm:errors path="firstName"/>
            </div>
        </spring:bind>

        <spring:bind path="lastName">
            <div class="row mb-3 ${status.error ? 'has-error' : ''}">
                <springForm:input type="text" path="lastName" class="form-control"
                                  placeholder="Last Name"
                                  autofocus="true"/>
                <springForm:errors path="lastName"/>
            </div>
        </spring:bind>

        <spring:bind path="password">
            <div class="row mb-3 ${status.error ? 'has-error' : ''}">
                <springForm:input type="password" path="password" class="form-control"
                                  placeholder="Password"/>
                <springForm:errors path="password"/>
            </div>
        </spring:bind>

        <spring:bind path="passwordConfirm">
            <div class="row mb-3 ${status.error ? 'has-error' : ''}">
                <springForm:input type="password" path="passwordConfirm" class="form-control"
                                  placeholder="Confirm your password"/>
                <springForm:errors path="passwordConfirm"/>
            </div>
        </spring:bind>


        <div class="row mb-3">
            <div class="col text-center">
                <button type="submit" class="btn btn-primary">Register</button>
            </div>
        </div>

        <div class="row mb-3">
            <h4 class="text-center"><a href="/login">Already have an account?</a></h4>
        </div>
    </springForm:form>
</div>
</body>
</html>