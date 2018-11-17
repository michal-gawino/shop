<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<t:page>
   <div class="container">
    <div class="row">
    <c:choose>
       <c:when test="${sessionScope.cart.products.isEmpty()}">
       <h2>Your cart is empty</h2>
        </c:when>
       <c:otherwise>
        <c:forEach items="${sessionScope.cart.products}" var="product">
                <div class="col-sm-4">
                       <div class="card my-1">
                          <img class="card-img-top" src="data:image/jpg;base64,<c:out value='${fileManager.getBase64Image(fileManager.getProductImagePath(product))}'/>" />
                         <div class="card-body">
                           <h5 class="card-title">${product.brand}</h5>
                            <p class="card-text">${product.name}</p>
                            <form:form method="DELETE" action="/cart/${product.id}">
                             <div class="btn-group" role="group">
                               <button type="submit" class="btn btn-secondary btn-danger">Remove from cart</button>
                               <button type="button" class="btn btn-secondary btn-dark" disabled>${product.price} $</button>
                             </div>
                             </form:form>
                         </div>
                       </div>
                     </div>
           </c:forEach>
       </c:otherwise>
       </c:choose>
    </div>
</div>
</t:page>