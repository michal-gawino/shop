<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<sec:authentication var="user" property="principal.user" />

<t:page>
    <div class="container">
        <div class="row">
            <h3>Welcome ${user.name} ${user.surname}<h3>
        </div>
    </div>
</t:page>