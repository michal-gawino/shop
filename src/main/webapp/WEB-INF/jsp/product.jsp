<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:page>
   <div class="container">
    <div class="row">
    <c:choose>
       <c:when test="${products.isEmpty()}">
       <h2>Category has no products</h2>
        </c:when>
       <c:otherwise>
        <c:forEach items="${products}" var="product">
                <div class="col-sm-4">
                       <div class="card my-1">
                          <img class="card-img-top" src="data:image/jpg;base64,<c:out value='${fileManager.getBase64Image(fileManager.getProductImagePath(product))}'/>" />
                         <div class="card-body">
                           <h5 class="card-title">${product.brand}</h5>
                            <p class="card-text">${product.name}</p>
                            <form method="POST" action="/cart/${product.id}">
                             <div class="btn-group" role="group">
                               <button type="button" class="btn btn-secondary btn-success">Add to cart</button>
                               <button type="button" class="btn btn-secondary btn-dark">${product.price} $</button>
                             </div>
                             </form>
                         </div>
                       </div>
                     </div>
           </c:forEach>
       </c:otherwise>
       </c:choose>
    </div>
</div>
</t:page>