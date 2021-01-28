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
    <h3><spring:message code="users.Amount"/>: ${amount}</h3>
    <h3><spring:message code="repairForm.title"/></h3>

    <button type="button" class="btn btn-primary m-3"
            onclick="location.href='/repairs/add'">
        <spring:message code="repairForm.add"/></button>

    <table class="table table-striped table-light">
        <thead>
        <tr>
            <th scope="col">#</th>
            <th scope="col"><spring:message code="repairForm.Created"/></th>
            <th scope="col"><spring:message code="repairForm.Author"/></th>
            <th scope="col"><spring:message code="repairForm.car"/></th>
            <th scope="col"><spring:message code="repairForm.ShortDescription"/></th>
            <th scope="col"><spring:message code="repairForm.Status"/></th>
            <th scope="col"><spring:message code="repairForm.price"/></th>
            <th scope="col"><spring:message code="users.Action"/></th>
            <sec:authorize access="hasRole('ROLE_MANAGER') or hasRole('ROLE_REPAIRMAN')">
                <th scope="col"><spring:message code="users.Action"/></th>
            </sec:authorize>
        </tr>
        </thead>
        <tbody>

        <c:forEach var="repairForm" items="${repairForms}" varStatus="i">
            <tr>
                <c:choose>
                    <c:when test="${currentPage != 1}">
                        <td>${i.index+1+(currentPage-1)*10}</td>
                    </c:when>
                    <c:otherwise>
                        <td>${i.index+1}</td>
                    </c:otherwise>
                </c:choose>

                <td>${repairForm.creationDate.toLocalDate()}</td>
                <td>${repairForm.author.firstName} ${repairForm.author.lastName}</td>
                <td>${repairForm.car} </td>
                <td>${repairForm.shortDescription} </td>
                <td>${repairForm.status} </td>
                <td>${repairForm.price} </td>
                <td>
                    <button type="button" class="btn btn-outline-info"
                            onclick="location.href='/repairs/view/${repairForm.id}'">
                        <spring:message code="repairForm.view"/></button>

                    <c:if test="${repairForm.status eq statusReady}">
                        <button type="button" class="btn btn-outline-primary"
                                onclick="location.href='/repairs/review/${repairForm.id}'">
                            <spring:message code="repairForm.review"/></button>
                    </c:if>
                </td>
                <sec:authorize access="hasRole('ROLE_MANAGER') or hasRole('ROLE_REPAIRMAN')">
                    <td>
                        <button type="button" class="btn btn-outline-info"
                                onclick="location.href='/repairs/edit/${repairForm.id}'">
                            <spring:message code="repairForm.edit"/></button>
                    </td>
                </sec:authorize>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <nav aria-label="...">
        <ul class="pagination">
            <c:if test="${currentPage != 1}">
                <li class="page-item item">
                    <a class="page-link" href="${basePath}/page/${currentPage - 1}" tabindex="-1"><spring:message
                            code="pagination.previous"/></a>
                </li>
            </c:if>
            <c:forEach begin="1" end="${totalPages}" var="i">
                <c:choose>
                    <c:when test="${currentPage eq i}">
                        <li class="page-item active">
                            <a class="page-link">${i}</a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="page-item"><a class="page-link" href="${basePath}/page/${i}">${i}</a></li>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
            <c:if test="${currentPage lt totalPages}">
                <li class="page-item">
                    <a class="page-link" href="${basePath}/page/${currentPage + 1}"><spring:message
                            code="pagination.next"/></a>
                </li>
            </c:if>
        </ul>
    </nav>


</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.min.js"></script>
</body>
</html>