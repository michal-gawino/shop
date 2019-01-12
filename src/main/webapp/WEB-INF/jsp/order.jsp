<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>


<sec:authentication var="user" property="principal.user" />

<t:page>
    <div class="container">
        <div class="row">
            <form:form modelAttribute="orderDetails" action="/order" method="POST">
                <div class="form-row">
                    <div class="col-md-4 mb-3">
                        <label for="validationDefault01">First name</label>
                        <input type="text" class="form-control" value="${user.name}" disabled>
                    </div>
                    <div class="col-md-4 mb-3">
                        <label for="validationDefault02">Surname</label>
                        <input type="text" class="form-control" value="${user.surname}" disabled>
                    </div>
                    <div class="col-md-4 mb-3">
                        <label for="validationDefault02">Country</label>
                        <input type="text" class="form-control" placeholder="Country" name="country" value=${param.country}>
                        <small class="form-text text-danger"><form:errors path="country"/></small>
                    </div>
                </div>
                <div class="form-row">
                    <div class="col-md-5 mb-3">
                        <label for="validationDefault03">City</label>
                        <input type="text" class="form-control" name="city" placeholder="City" value=${param.city}>
                        <small class="form-text text-danger"><form:errors path="city"/></small>
                    </div>
                    <div class="col-md-4 mb-3">
                        <label for="validationDefault04">Street</label>
                        <input type="text" class="form-control" name="street" placeholder="Street" value=${param.street}>
                        <small class="form-text text-danger"><form:errors path="street"/></small>
                    </div>
                    <div class="col-md-3 mb-3">
                        <label for="validationDefault05">Postal code</label>
                        <input type="text" class="form-control" name="postalCode" placeholder="Postal code" value=${param.postalCode}>
                        <small class="form-text text-danger"><form:errors path="postalCode"/></small>
                    </div>
                </div>
                <div class="form-row">
                    <div class="col-md-4">
                        <label>Credit card</label>
                        <input type="text" class="form-control" name="creditCardNumber" placeholder="Credit card number" value=${param.creditCardNumber}>
                        <small class="form-text text-danger"><form:errors path="creditCardNumber"/></small>
                    </div>
                </div>
                <a href="/cart" class="btn btn-secondary btn-md my-1 active" role="button">Back</a>
                <button class="btn btn-primary btn-md mx-1 my-1" type="submit">Confirm</button>
                <h3 class="float-right"> Total cart value : <span class="badge badge-success"><fmt:formatNumber
                        type="number" maxFractionDigits="2" value="${sessionScope.cart.getTotalCartValue()}"/> $</span>
                </h3>
            </form:form>
        </div>
    </div>
</t:page>