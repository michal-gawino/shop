<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:page>
    <div class="container h-100">
        <div class="row h-100 justify-content-center align-items-center">
            <div class="tab-content">
                <div class="tab-pane active in" id="login">
                    <form:form class="form-horizontal" action="register" method="POST">
                        <fieldset>
                            <legend>Registration</legend>
                            <label class="control-label" for="username">Name</label>
                            <input type="text" id="username" name="name" value="${fn:escapeXml(param.name)}"
                                   class="form-control" required="required"/>
                            <label class="control-label" for="surname">Surname</label>
                            <input type="text" id="surname" name="surname" value="${fn:escapeXml(param.surname)}"
                                   class="form-control" required="required"/>
                            <label class="control-label" for="login">Login</label>
                            <input type="text" id="login" name="login" class="form-control" required="required"/>
                            <label class="control-label" for="password">Password</label>
                            <input type="password" id="password" name="password" class="form-control" required
                                   pattern="^.{6,}" title="Password should contain at least 6 characters"/>
                            <button class="btn btn-primary" style="margin-top: 5px">Register</button>
                        </fieldset>
                    </form:form>
                    <c:if test="${not empty user_exists}">
                        <div class="alert alert-danger">
                            ${user_exists}
                        </div>
                    </c:if>
                    <c:if test="${not empty success}">
                        <div class="alert alert-success">
                            ${success}
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</t:page>