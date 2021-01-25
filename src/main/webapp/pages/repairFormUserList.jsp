<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!doctype html>
<html lang="en">

<head>
    <title><spring:message code="repairForm.title"/></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css"
          rel="stylesheet">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>
<jsp:include page="navbar.jsp"/>
<div class="container mt-3">
    <h3><spring:message code="repairForm.title"/></h3>
    <table class="table">
        <thead>
        <tr>
            <th scope="col">Id</th>
            <th scope="col"><spring:message code="repairForm.Created"/></th>
            <th scope="col"><spring:message code="repairForm.car"/></th>
            <th scope="col"><spring:message code="repairForm.ShortDescription"/></th>
            <th scope="col"><spring:message code="repairForm.Status"/></th>
            <th scope="col"><spring:message code="users.Action"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="repairForm" items="${repairForms}">
            <tr>
                <td> ${repairForm.id} </td>
                <td> ${repairForm.creationDate.toLocalDate()} ${repairForm.creationDate.toLocalTime()}</td>
                <td> ${repairForm.car} </td>
                <td> ${repairForm.shortDescription} </td>
                <td> ${repairForm.status} </td>
                <td>
                    <button type="button" class="btn btn-outline-info"
                            onclick="location.href='/repairs/view/${repairForm.id}'">
                        <spring:message code="repairForm.view"/></button>
                </td>
            </tr>
        </c:forEach>
    </table>
    <button type="button" class="btn btn-primary"
            onclick="location.href='/repairs/add'">
        <spring:message code="repairForm.add"/></button>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.min.js"></script>
</body>
</html>