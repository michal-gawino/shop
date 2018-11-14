<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:page>
   <div class="container">
    <div class="row">
    <c:forEach items="${products}" var="product">
         <div class="col-sm-4">
                <div class="card my-1">
                  <a href="/category/${category.id}/products">
                    <img class="card-img-top" src="data:image/jpg;base64,<c:out value='${fileManager.getBase64CategoryImage(category)}'/>" />
                   </a>
                  <div class="card-body">
                    <h5 class="card-title">${product.brand}</h5>
                     <p class="card-text">${product.name}</p>
                     <a href="/cart/add_product" class="btn btn-success">Add to cart</a> ${product.price}
                  </div>
                </div>
              </div>
    </c:forEach>
    </div>
</div>
</t:page>