<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<t:page>
    <div class="container">
        <div class="row">
            <c:choose>
                <c:when test="${orders.isEmpty()}">
                    <h2>You have no orders placed</h2>
                </c:when>
                <c:otherwise>
                    <div class="accordion" id="accordionExample">
                        <c:forEach items="${orders}" var="order">
                            <div class="card">
                                <div class="card-header" id="headingOne">
                                    <h5 class="mb-0">
                                        <button class="btn btn-link" type="button" data-toggle="collapse"
                                                data-target="#order${order.id}" aria-expanded="true">
                                            <fmt:parseDate value="${order.creationDate}" var="parsedDate"
                                                           pattern="yyyy-MM-dd'T'HH:mm:ss.SSS"/>
                                            <fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${parsedDate}"/>
                                            ${orderDate} ${order.orderDetails.country} ${order.orderDetails.city}
                                            ${order.orderDetails.street} ${order.orderDetails.postalCode}
                                        </button>
                                    </h5>
                                </div>

                                <div id="order${order.id}" class="collapse" aria-labelledby="headingOne" data-parent="#accordionExample">
                                    <div class="card-body">
                                        <ul class="list-unstyled">
                                            <c:forEach items="${order.products}" var="product">
                                                <li class="media my-3 my-1">
                                                    <img class="img-thumbnail" width="250" height="250"
                                                         src="data:image/jpg;base64,<c:out value='${fileManager.getBase64Image(fileManager.getProductImagePath(product))}'/>">
                                                    <div class="media-body mx-1">
                                                        <h5>${product.brand}</h5>
                                                        ${product.name}
                                                    </div>
                                                </li>
                                            </c:forEach>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</t:page>