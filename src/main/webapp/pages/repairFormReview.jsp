<%@ taglib prefix="springForm" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="jstlC" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="springform" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://example.com/functions" prefix="f" %>
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
    <springform:form method="POST" action="/repairs/saveReview" modelAttribute="repairFormAttribute"
                     class="mx-auto p-5 m-3" style="width: 50%; background-color: #eee;">
        <h2><spring:message code="repairFormView.name"/></h2>
        <springForm:hidden path="id"/>

        <div class="row mb-3">
            <div class="col-xs-15">
                <strong><spring:message
                        code="repairForm.Created"/></strong>: ${f:formatLocalDateTime(repairFormAttribute.creationDate, 'dd.MM.yyyy HH:mm')}
            </div>
        </div>
        <springForm:hidden path="creationDate"/>

        <springForm:hidden path="author"/>

        <div class="row mb-3">
            <div class="col-xs-15">
                <strong><spring:message code="repairForm.car"/></strong>: ${repairFormAttribute.car}
            </div>
        </div>
        <springForm:hidden path="car"/>

        <div class="row mb-3">
            <div class="col-xs-15">
                <strong><spring:message
                        code="repairForm.ShortDescription"/></strong>: ${repairFormAttribute.shortDescription}
            </div>
        </div>
        <springForm:hidden path="shortDescription"/>

        <div class="row mb-3">
            <div class="col-xs-15">
                <strong><spring:message code="repairForm.description"/></strong>: ${repairFormAttribute.description}
            </div>
        </div>
        <springForm:hidden path="description"/>

        <springForm:hidden path="repairman"/>
        <springForm:hidden path="status"/>

        <springForm:hidden path="price"/>
        <springForm:hidden path="lastModifiedDate"/>

        <spring:message code="repairForm.review" var="reviewPh"/>
        <div class="row mb-3 ${status.error ? 'has-error' : ''}">
            <div class="col-xs-15">
                <strong><spring:message code="repairForm.review"/></strong>
                <springForm:input type="text" path="feedback" class="form-control"/>
                <springForm:errors path="feedback"/>
            </div>
        </div>


        <spring:message code="repairFormView.ok" var="ok"/>
        <div class="row mb-3">
            <div class="col text-center">
                <button type="submit"
                        class="btn btn-success"
                        onclick="location.href='/repairs/list'"><spring:message code="userEdit.save"/>
                </button>
            </div>
        </div>

    </springform:form>
</div>
<jsp:include page="navbottom.jsp"/>
</body>
</html>

