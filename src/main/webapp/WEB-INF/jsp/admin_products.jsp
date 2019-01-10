<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<t:page>
    <div class="container">
    <c:if test="${not empty error}">
          <div class="alert alert-danger my-2" role="alert">
                 ${error}
          </div>
    </c:if>
        <table class="table table-bordered">
            <thead>
            <tr>
                <th scope="col">
                    <button type="button" class="btn btn-primary" data-toggle="modal"
                            data-target="#addProductModal">
                        <i class="fas fa-plus"></i>
                    </button>
                </th>
                <th scope="col">Name</th>
                <th scope="col">Brand</th>
                <th scope="col">Price</th>
                <th scope="col">Category</th>
                <th scope="col"></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${productsPage.getContent()}" var="product" varStatus="var">
                <tr>
                    <th scope="row">${var.index + 1}</th>
                    <td>${product.name}</td>
                    <td>${product.brand}</td>
                    <td>${product.price} $</td>
                    <td>${product.category.name}</td>
                    <td>
                        <button id="productUpdateModal" type="button" class="btn btn-success"
                                data-id="${product.id}" data-category="${product.category.id}"
                                data-brand="${product.brand}" data-price="${product.price}"
                                data-name="${product.name}" data-toggle="modal" data-target="#editProductModal">
                            <i class="far fa-edit"> Edit</i>
                        </button>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <c:if test="${!productsPage.getContent().isEmpty()}">
            <nav aria-label="Page navigation">
                <ul class="pagination justify-content-center">
                    <c:if test="${productsPage.getNumber() > 1}">
                        <li class="page-item">
                            <a class="page-link" href="/admin/products?size=${param.size}&page=1" aria-label="Previous">
                                <span aria-hidden="true">&laquo;</span>
                            </a>
                        </li>
                    </c:if>
                    <c:if test="${productsPage.getNumber() > 0}">
                        <li class="page-item">
                            <a class="page-link" href="/admin/products?size=${param.size}&page=${param.page - 1}"
                               aria-label="Previous">
                                <span aria-hidden="true">&lsaquo;</span>
                            </a>
                        </li>
                    </c:if>
                    <li class="page-item"><a class="page-link" href="#">${productsPage.getNumber() + 1}</a></li>
                    <c:if test="${productsPage.getTotalPages() - productsPage.getNumber() > 1}">
                        <li class="page-item">
                            <a class="page-link" href="/admin/products?size=${param.size}&page=${param.page + 1}"
                               aria-label="Previous">
                                <span aria-hidden="true">&rsaquo;</span>
                            </a>
                        </li>
                    </c:if>
                    <c:if test="${productsPage.getTotalPages() - productsPage.getNumber() > 2}">
                        <li class="page-item">
                            <a class="page-link"
                               href="/admin/products?size=${param.size}&page=${productsPage.getTotalPages()}"
                               aria-label="Previous">
                                <span aria-hidden="true">&raquo;</span>
                            </a>
                        </li>
                    </c:if>
                    <li class="page-item mx-2">
                        <div class="btn-group">
                            <button type="button" class="btn btn-light" disabled>Number of records</button>
                            <button type="button" class="btn btn-light dropdown-toggle dropdown-toggle-split"
                                    data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                <span class="sr-only">Toggle Dropdown</span>
                            </button>
                            <div class="dropdown-menu">
                                <c:forEach var="i" begin="10" end="100" step="10">
                                    <a class="dropdown-item" href="/admin/products?size=${i}&page=1">${i}</a>
                                </c:forEach>
                            </div>
                        </div>
                    </li>
                </ul>
            </nav>
        </c:if>
    </div>
    <div class="modal fade" id="addProductModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle"
         aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <form action="/product" method="POST" enctype="multipart/form-data">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLongTitle">Add product</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <div class="form-group">
                            <label>Name</label>
                            <input type="text" class="form-control" name="name" required>
                        </div>
                        <div class="form-group">
                            <label>Brand</label>
                            <input type="text" class="form-control" name="brand" required>
                        </div>
                        <div class="form-group">
                            <label>Price</label>
                            <input type="number" step="0.01" min="0" class="form-control" name="price" required>
                        </div>
                        <div class="form-group">
                            <label for="exampleFormControlSelect2">Category</label>
                            <select class="form-control" id="category" name="category" required>
                                <c:forEach items="${categories}" var="category">
                                    <option value="${category.id}" }>${category.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="image">Image</label>
                            <input type="file" class="form-control-file" name="image" required>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                        <button type="submit" class="btn btn-primary">Save</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <div class="modal fade" id="editProductModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle"
         aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <form:form method="PUT" id="productEditForm" enctype="multipart/form-data">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLongTitle">Edit product</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <div class="form-group">
                            <label>Name</label>
                            <input type="text" class="form-control" name="name" id="name" required>
                        </div>
                        <div class="form-group">
                            <label>Brand</label>
                            <input type="text" class="form-control" name="brand" id="brand" required>
                        </div>
                        <div class="form-group">
                            <label>Price</label>
                            <input type="number" step="0.01" min="0" class="form-control" name="price" id="price" required>
                        </div>
                        <div class="form-group" id="category">
                            <label for="exampleFormControlSelect2">Category</label>
                            <select class="form-control" name="category" required>
                                <c:forEach items="${categories}" var="category">
                                    <option value="${category.id}">${category.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="image">Image</label>
                            <input type="file" class="form-control-file" name="image" required>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                        <button type="submit" class="btn btn-primary">Save changes</button>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</t:page>