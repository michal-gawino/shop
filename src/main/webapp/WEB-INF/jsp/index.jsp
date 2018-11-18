<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:page>
    <div class="container">
        <div class="row">
            <h3>You are logged in as ${user.name} ${user.surname}.<h3>
        </div>
    </div>
</t:page>