<%@ taglib prefix="springForm" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="jstlC" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title><spring:message code="userEdit.h"/></title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css"
          rel="stylesheet">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>

<body>
<jsp:include page="navbar.jsp"/>
<div class="container">
    <springForm:form method="POST" action="changeAmount" modelAttribute="userAmountDto"
                     class="mx-auto p-5 m-3" style="width: 50%; background-color: #eee;">
        <h2><spring:message code="userEdit.changeAmount"/></h2>

        <springForm:hidden path="userId"/>

        <div class="col-xs-15">
            <strong><spring:message code="users.Amount"/></strong>: ${userAmountDto.amount}
        </div>

        <spring:bind path="amount">
            <div class="row mb-3 ${status.error ? 'has-error' : ''}">
                <div class="col-xs-15">
                    <strong><spring:message code="userEdit.changeAmount"/></strong>
                </div>
                <div class="col-xs-15">
                    <springForm:input type="number" min="0" value="0" step=".01" path="amount" class="form-control"
                                      placeholder="${amountPh}"/>
                    <springForm:errors path="amount"/>
                </div>
            </div>
        </spring:bind>


        <spring:message code="userEdit.save" var="save"/>
        <div class="row mb-3">
            <div class="col text-center">
                <button type="submit" class="btn btn-success">${save}</button>
            </div>
        </div>

    </springForm:form>
</div>
<jsp:include page="navbottom.jsp"/>
</body>
</html>