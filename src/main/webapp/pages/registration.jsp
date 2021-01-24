<%@ taglib prefix="springForm" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="jstlC" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>Log in with your account</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css"
          rel="stylesheet">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>

<body>
<jsp:include page="locale.jsp"/>
<div class="container">
    <springForm:form method="POST" modelAttribute="userForm"
                     class="mx-auto p-5 m-3" style="width: 50%; background-color: #eee;">
        <h2><spring:message code="registration.createAccount"/></h2>
        <div class="row mb-3">
            <div class="col-xs-15">
                <div>
                    <!-- Check for login error -->
                    <jstlC:if test="${param.error != null}">
                        <div class="alert alert-danger col-xs-offset-1 col-xs-10">
                            <span><spring:message code="registration.emailRegistered"/></span>
                        </div>
                    </jstlC:if>
                </div>
            </div>
        </div>
        <spring:bind path="email">
            <spring:message code="registration.Email" var="emailPh"/>
            <div class="row mb-3 ${status.error ? 'has-error' : ''}">
                <div class="col-xs-15">
                    <springForm:input type="email" path="email" class="form-control"
                                      placeholder="${emailPh}"
                                      autofocus="true"/>
                    <springForm:errors path="email"/>
                </div>
            </div>
        </spring:bind>

        <spring:bind path="firstName">
            <spring:message code="registration.FirstName" var="firstNamePh"/>
            <div class="row mb-3 ${status.error ? 'has-error' : ''}">
                <div class="col-xs-15">
                    <springForm:input type="text" path="firstName" class="form-control"
                                      placeholder="${firstNamePh}"
                                      autofocus="true"/>
                    <springForm:errors path="firstName"/>
                </div>
            </div>
        </spring:bind>

        <spring:bind path="lastName">
            <spring:message code="registration.LastName" var="lastNamePh"/>
            <div class="row mb-3 ${status.error ? 'has-error' : ''}">
                <div class="col-xs-15">
                    <springForm:input type="text" path="lastName" class="form-control"
                                      placeholder="${lastNamePh}"
                                      autofocus="true"/>
                    <springForm:errors path="lastName"/>
                </div>
            </div>
        </spring:bind>

        <spring:bind path="password">
            <spring:message code="registration.Password" var="passwordPh"/>
            <div class="row mb-3 ${status.error ? 'has-error' : ''}">
                <div class="col-xs-15">
                    <springForm:input type="password" path="password" class="form-control"
                                      placeholder="${passwordPh}"/>
                    <springForm:errors path="password"/>
                </div>
            </div>
        </spring:bind>

        <spring:message code="registration.Register" var="register"/>
        <div class="row mb-3">
            <div class="col text-center">
                <button type="submit" class="btn btn-primary">${register}</button>
            </div>
        </div>

        <spring:message code="registration.AlreadyRegistered" var="alreadyRegistered"/>
        <div class="row mb-3">
            <h4 class="text-center"><a href="/login">${alreadyRegistered}</a></h4>
        </div>
    </springForm:form>
</div>
</body>
</html>