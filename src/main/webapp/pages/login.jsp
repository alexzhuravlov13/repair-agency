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

    <springForm:form method="POST" action="/login"
                     class="mx-auto p-3 m-3" style="width: 50%; background-color: #eee;">
        <h2>Log in</h2>
        <div class="row mb-3">
            <label for="inputEmail3" class="col-sm-2 col-form-label">Email</label>
            <div class="col-sm-10">
                <input name="email" type="email" class="form-control" id="inputEmail3">
            </div>
        </div>
        <div class="row mb-3">
            <label for="inputPassword3" class="col-sm-2 col-form-label">Password</label>
            <div class="col-sm-10">
                <input name="password" type="password" class="form-control" id="inputPassword3">
            </div>
        </div>
        <div class="row mb-3">
            <div class="col-xs-15">
                <div>
                    <!-- Check for login error -->
                    <jstlC:if test="${param.error != null}">
                        <div class="alert alert-danger col-xs-offset-1 col-xs-10">
                            <span>${error}</span>
                        </div>
                    </jstlC:if>
                    <!-- Check for logout -->
                    <jstlC:if test="${param.logout != null}">
                        <div class="alert alert-success col-xs-offset-1 col-xs-10">
                            <span>${message}</span>
                        </div>
                    </jstlC:if>
                </div>
            </div>
        </div>
        <div class="row mb-3">
            <div class="col text-center">
                <button type="submit" class="btn btn-primary">Sign in</button>
            </div>
        </div>
        <div class="row mb-3">
            <h4 class="text-center"><a href="/registration">Create an account</a></h4>
        </div>
    </springForm:form>
</div>
</body>
</html>