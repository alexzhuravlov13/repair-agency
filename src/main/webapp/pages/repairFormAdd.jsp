<%@ taglib prefix="springForm" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="jstlC" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title><spring:message code="repairFormAdd.h"/></title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css"
          rel="stylesheet">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>

<body>
<jsp:include page="navbar.jsp"/>
<div class="container">
    <springForm:form method="POST" action="addRepairForm" modelAttribute="repairFormAttribute"
                     class="mx-auto p-5 m-3" style="width: 50%; background-color: #eee;">
        <h2><spring:message code="repairFormAdd.h"/></h2>

        <springForm:hidden path="creationDate"/>
        <springForm:hidden path="authorId"/>

        <spring:bind path="car">
            <spring:message code="repairFormAdd.car" var="carPh"/>
            <div class="row mb-3 ${status.error ? 'has-error' : ''}">
                <div class="col-xs-15">
                    <springForm:input type="text" path="car" class="form-control"
                                      placeholder="${carPh}"
                                      autofocus="true"/>
                    <springForm:errors path="car"/>
                </div>
            </div>
        </spring:bind>


        <spring:bind path="shortDescription">
            <spring:message code="repairFormAdd.shortDescription" var="shortDescriptionPh"/>
            <div class="row mb-3 ${status.error ? 'has-error' : ''}">
                <div class="col-xs-15">
                    <springForm:input type="text" path="shortDescription" class="form-control"
                                      placeholder="${shortDescriptionPh}"
                                      autofocus="true"/>
                    <springForm:errors path="shortDescription"/>
                </div>
            </div>
        </spring:bind>

        <spring:bind path="description">
            <spring:message code="repairFormAdd.description" var="descriptionPh"/>
            <div class="row mb-3 ${status.error ? 'has-error' : ''}">
                <div class="col-xs-15">
                    <springForm:input type="text" path="description" class="form-control"
                                      placeholder="${descriptionPh}"
                                      autofocus="true"/>
                    <springForm:errors path="description"/>
                </div>
            </div>
        </spring:bind>


        <spring:message code="repairFormAdd.save" var="save"/>
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