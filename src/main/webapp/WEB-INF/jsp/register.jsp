<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<t:page>
    <div class="container h-100">
        <div class="row h-100 justify-content-center align-items-center">
            <div class="tab-content">
                <div class="tab-pane active in" id="login">
                    <form:form class="form-horizontal" modelAttribute="user" action="register" method="POST">
                        <fieldset>
                            <legend>Registration</legend>
                            <label class="control-label" for="username">Name</label>
                            <input type="text" id="username" name="name" value="${fn:escapeXml(param.name)}" class="form-control" />
                            <small class="form-text text-danger"><form:errors path="name"/></small>
                            <label class="control-label" for="surname">Surname</label>
                            <input type="text" id="surname" name="surname" value="${fn:escapeXml(param.surname)}" class="form-control" />
                            <small class="form-text text-danger"><form:errors path="surname"/></small>
                            <label class="control-label" for="login">Login</label>
                            <input type="text" id="login" name="login" value="${fn:escapeXml(param.login)}" class="form-control"/>
                            <small class="form-text text-danger"><form:errors path="login"/></small>
                            <label class="control-label" for="password">Password</label>
                            <input type="password" name="password" value="${fn:escapeXml(param.password)}" class="form-control"/>
                            <small class="form-text text-danger"><form:errors path="password"/></small>
                            <button class="btn btn-primary" style="margin-top: 5px">Register</button>
                        </fieldset>
                    </form:form>
                    <c:if test="${not empty success}">
                        <div class="alert alert-success my-2">
                            ${success}
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</t:page>