<%@ taglib prefix="springForm" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="jstlC" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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
    <div class="mx-auto p-5 m-3" style="width: 50%; background-color: #eee;">
        <h2><spring:message code="repairFormView.name"/></h2>

        <div class="row mb-3">
            <div class="col-xs-15">
                <strong><spring:message
                        code="repairForm.Author"/></strong>: ${repairForm.author.firstName} ${repairForm.author.lastName}
            </div>
        </div>

        <div class="row mb-3">
            <div class="col-xs-15">
                <strong><spring:message code="repairForm.Created"/></strong>: ${f:formatLocalDateTime(repairForm.creationDate, 'dd.MM.yyyy HH:mm')}
            </div>
        </div>

        <div class="row mb-3">
            <div class="col-xs-15">
                <strong><spring:message code="repairForm.car"/></strong>: ${repairForm.car}
            </div>
        </div>

        <div class="row mb-3">
            <div class="col-xs-15">
                <strong><spring:message code="repairForm.ShortDescription"/></strong>: ${repairForm.shortDescription}
            </div>
        </div>

        <div class="row mb-3">
            <div class="col-xs-15">
                <strong><spring:message code="repairForm.description"/></strong>: ${repairForm.description}
            </div>
        </div>

        <div class="row mb-3">
            <div class="col-xs-15">
                <strong><spring:message code="repairForm.Status"/></strong>: ${repairForm.status}
            </div>
        </div>

        <div class="row mb-3">
            <div class="col-xs-15">
                <strong><spring:message code="repairForm.price"/></strong>: ${repairForm.price}
            </div>
        </div>

        <div class="row mb-3">
            <div class="col-xs-15">
                <strong><spring:message code="repairForm.review"/></strong>: ${repairForm.feedback}
            </div>
        </div>

        <spring:message code="repairFormView.ok" var="ok"/>
        <div class="row mb-3">
            <div class="col text-center">
                <button type="submit"
                        class="btn btn-success"
                        onclick="location.href='/repairs/list'">${ok}
                </button>
            </div>
        </div>

    </div>
</div>
<jsp:include page="navbottom.jsp"/>
</body>
</html>