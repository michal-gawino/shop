<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>


<t:page>
    <div class="container">
        <div class="row">
            <form action="/order" method="POST">
                <div class="form-row">
                    <div class="col-md-4 mb-3">
                        <label for="validationDefault01">First name</label>
                        <input type="text" class="form-control" value="${sessionScope.user.name}" disabled>
                    </div>
                    <div class="col-md-4 mb-3">
                        <label for="validationDefault02">Surname</label>
                        <input type="text" class="form-control" value="${sessionScope.user.surname}" disabled>
                    </div>
                    <div class="col-md-4 mb-3">
                        <label for="validationDefault02">Country</label>
                        <input type="text" class="form-control" placeholder="Country" name="country">
                    </div>
                </div>
                <div class="form-row">
                    <div class="col-md-5 mb-3">
                        <label for="validationDefault03">City</label>
                        <input type="text" class="form-control" name="city" placeholder="City">
                    </div>
                    <div class="col-md-4 mb-3">
                        <label for="validationDefault04">Street</label>
                        <input type="text" class="form-control" name="street" placeholder="Street">
                    </div>
                    <div class="col-md-3 mb-3">
                        <label for="validationDefault05">Postal code</label>
                        <input type="text" class="form-control" name="postalCode" placeholder="Postal code">
                    </div>
                </div>
                <div class="form-row">
                    <div class="col-md-4">
                        <label>Credit card</label>
                        <input type="text" class="form-control" name="creditCardNumber" placeholder="Credit card number">
                    </div>
                </div>
                <a href="/cart" class="btn btn-secondary btn-md my-1 active" role="button">Back</a>
                <button class="btn btn-primary btn-md mx-1 my-1" type="submit">Confirm</button>
                <h3 class="float-right"> Total cart value : <span class="badge badge-success"><fmt:formatNumber
                        type="number" maxFractionDigits="2" value="${sessionScope.cart.getTotalCartValue()}"/> $</span>
                </h3>

            </form>
        </div>
    </div>
</t:page>