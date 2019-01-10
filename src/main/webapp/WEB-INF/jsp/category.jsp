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
        <div class="row">
            <c:forEach items="${categories}" var="category">
                <div class="col-sm-4">
                    <div class="card my-1">
                        <a href="/category/${category.id}/products">
                            <img class="card-img-top"
                                 src="data:image/jpg;base64,<c:out value='${fileManager.getBase64Image(fileManager.getCategoryImagePath(category))}'/>"/>
                        </a>
                        <div class="card-body">
                            <h5 class="card-title">${category.name}</h5>
                            <c:if test="${user.role eq 'ADMIN'}">
                                <div class="row">
                                    <div class="col-sm-3">
                                        <button type="button" id="categoryUpdateModal" data-toggle="modal"
                                                data-target="#editCategoryModal" data-name="${category.name}"
                                                data-id="${category.id}" class="btn btn-primary btn-sm">Edit
                                        </button>

                                    </div>
                                </div>
                            </c:if>
                        </div>
                    </div>
                </div>
            </c:forEach>
            <c:if test="${user.role eq 'ADMIN'}">
                <div class="col-sm-4">
                    <div class="card my-1">
                        <img class="card-img-top" src="https://via.placeholder.com/300x300?text=No+image"/>
                        <div class="card-body">
                            <h5 class="card-title"></h5>
                            <button id="userUpdateModal" type="button" class="btn btn-sm btn-outline-secondary"
                                    data-toggle="modal" data-target="#addCategory">
                                <i class="fas fa-plus"></i>
                                Add category
                            </button>
                        </div>
                    </div>
                </div>
            </c:if>
        </div>
    </div>

    <div class="modal fade" id="addCategory" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle"
         aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <form action="/category" method="POST" enctype="multipart/form-data">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLongTitle">Add category</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <div class="form-group">
                            <label>Name</label>
                            <input type="text" class="form-control" name="name">
                        </div>
                        <div class="form-group">
                            <label for="image">Image</label>
                            <input type="file" class="form-control-file" id="image" name="image">
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

    <div class="modal fade" id="editCategoryModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle"
         aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <form:form method="PUT" id="categoryEditForm" enctype="multipart/form-data">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLongTitle">Edit category</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <div class="form-group">
                            <label>Name</label>
                            <input type="text" class="form-control" name="name" id="name">
                        </div>
                        <div class="form-group">
                            <label for="image">Image</label>
                            <input type="file" class="form-control-file" name="image" id="image">
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