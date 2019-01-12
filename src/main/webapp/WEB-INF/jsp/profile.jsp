<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<sec:authentication var="user" property="principal.user" />

<t:page>
    <div class="container">
        <div class="row justify-content-center align-items-center">
            <form:form action="/user/${sessionScope.user.id}" method="PUT">
                <div class="form-row">
                    <div class="form-group">
                        <label>Name</label>
                        <input type="text" class="form-control" name="name" value="${user.name}" pattern="^\S*$"
                        title="Name can't contain whitespaces">
                    </div>
                </div>
                <div class="form-row">
                    <div class="form-group">
                        <label>Surname</label>
                        <input type="text" class="form-control" name="surname" value="${user.surname}" pattern="^\S*$"
                        title="Surname can't contain whitespaces">
                    </div>
                </div>
                <button type="submit" class="btn btn-primary float-right">Save changes</button>
            </form:form>
        </div>
        <c:if test="${not empty success}">
            <div class="alert alert-success my-2" role="alert">
                ${success}
            </div>
        </c:if>
    </div>
</t:page>