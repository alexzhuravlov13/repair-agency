<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sp" uri="http://www.springframework.org/tags" %>

<html>

<head>
    <title>Users list</title>
    <link href="/webjars/bootstrap/4.4.1/css/bootstrap.min.css" rel="stylesheet">
</head>

<body>
<span><sp:message code="changeLocale"/></span>:
<select id="locales">
    <option value=""></option>
    <option value="en"><sp:message code="locales.en"/></option>
    <option value="ru"><sp:message code="locales.ru"/></option>
</select>

<a href="/home">home</a>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js">
</script>
<script type="text/javascript">
    $(document).ready(function () {
        $("#locales").change(function () {
            var selectedOption = $('#locales').val();
            if (selectedOption != '') {
                window.location.replace('?lang=' + selectedOption);
            }
        });
    });
</script>
</body>
</html>