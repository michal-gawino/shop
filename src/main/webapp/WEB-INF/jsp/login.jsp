<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:page>
    <div class="container h-100">
        <div class="row h-100 justify-content-center align-items-center">
            <div class="col-sm-6 col-md-4 col-md-offset-4">
                <div class="account-wall">
                    <center><img src="<c:url value="/images/login.png" />" style="height: auto;width: 100px" ></center>
                    <form class="form-signin" action="login" method="POST">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <input name="username" type="text" class="form-control" placeholder="login">
                        <input name="password" type="password" class="form-control" placeholder="password">
                        <button class="btn btn-primary btn-block" type="submit">Log in</button>
                    </form>
                    <c:if test="${not empty param.error}">
                        <div class="alert alert-danger">
                            Invalid credentials
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</t:page>