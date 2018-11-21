<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:page>
    <div class="container">
        <div class="row justify-content-center align-items-center">
            <form:form action="/user/password" method="POST">
                <div class="form-row">
                    <div class="form-group">
                        <label>Current password</label>
                        <input type="password" class="form-control" name="currentPassword" required>
                    </div>
                </div>
                <div class="form-row">
                    <div class="form-group">
                        <label>New password</label>
                        <input type="password" class="form-control" name="newPassword" required>
                    </div>
                </div>
                <div class="form-row">
                    <div class="form-group">
                        <label>Repeat new password</label>
                        <input type="password" class="form-control" name="newPasswordRepeat" required>
                    </div>
                </div>
                <button type="submit" class="btn btn-primary float-right">Change password</button>
            </form:form>
        </div>
        <c:if test="${not empty success}">
            <div class="alert alert-success my-2" role="alert">
                ${success}
            </div>
        </c:if>
        <c:if test="${not empty error}">
            <div class="alert alert-danger my-2" role="alert">
                ${error}
            </div>
        </c:if>
    </div>
</t:page>