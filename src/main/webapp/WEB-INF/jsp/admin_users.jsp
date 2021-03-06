<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>


<t:page>
    <div class="container">
        <c:if test="${not empty user_exists}">
            <div class="alert alert-danger">
                ${user_exists}
            </div>
        </c:if>
        <form:form action="/user" method="DELETE">
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th scope="col">
                        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#addUserModal">
                            <i class="fas fa-plus"></i>
                        </button>
                    </th>
                    <th scope="col">Login</th>
                    <th scope="col">Name</th>
                    <th scope="col">Surname</th>
                    <th scope="col">
                        <c:if test="${!users.isEmpty()}">
                            <button type="submit" data-toggle="tooltip" title="Remove selected users"
                                    class="btn btn-danger">
                                <i class="far fa-trash-alt"></i>
                            </button>
                        </c:if>
                    </th>
                    <th scope="col"></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${usersPage.getContent()}" var="u" varStatus="var">
                    <tr>
                        <th scope="row">${var.index + 1}</th>
                        <td>${u.login}</td>
                        <td>${u.name}</td>
                        <td>${u.surname}</td>
                        <td>
                            <div class="form-check">
                                <input class="form-check-input" type="checkbox" value="${u.id}" name="usersToDelete">
                            </div>
                        </td>
                        <td>
                            <button id="userUpdateModal" type="button" class="btn btn-success" data-id="${u.id}"
                                    data-name="${u.name}" data-surname="${u.surname}" data-login="${u.login}"
                                    data-toggle="modal" data-target="#editUserModal">
                                <i class="far fa-edit"> Edit</i>
                            </button>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </form:form>
        <c:if test="${!usersPage.getContent().isEmpty()}">
            <nav aria-label="Page navigation">
                <ul class="pagination justify-content-center">
                    <c:if test="${usersPage.getNumber() > 1}">
                        <li class="page-item">
                            <a class="page-link" href="/admin/users?size=${param.size}&page=1" aria-label="Previous">
                                <span aria-hidden="true">&laquo;</span>
                            </a>
                        </li>
                    </c:if>
                    <c:if test="${usersPage.getNumber() > 0}">
                        <li class="page-item">
                            <a class="page-link" href="/admin/users?size=${param.size}&page=${param.page - 1}"
                               aria-label="Previous">
                                <span aria-hidden="true">&lsaquo;</span>
                            </a>
                        </li>
                    </c:if>
                    <li class="page-item"><a class="page-link" href="#">${usersPage.getNumber() + 1}</a></li>
                    <c:if test="${usersPage.getTotalPages() - usersPage.getNumber() > 1}">
                        <li class="page-item">
                            <a class="page-link" href="/admin/users?size=${param.size}&page=${param.page + 1}"
                               aria-label="Previous">
                                <span aria-hidden="true">&rsaquo;</span>
                            </a>
                        </li>
                    </c:if>
                    <c:if test="${usersPage.getTotalPages() - usersPage.getNumber() > 2}">
                        <li class="page-item">
                            <a class="page-link"
                               href="/admin/users?size=${param.size}&page=${usersPage.getTotalPages()}"
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
                                    <a class="dropdown-item" href="/admin/users?size=${i}&page=1">${i}</a>
                                </c:forEach>
                            </div>
                        </div>
                    </li>
                </ul>
            </nav>
        </c:if>
    </div>
    <div class="modal fade" id="addUserModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle"
         aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <form:form modelAttribute="u" action="/user"  method="POST">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLongTitle">Add user</h5>
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
                            <label>Surname</label>
                            <input type="text" class="form-control" name="surname" required>
                        </div>
                        <div class="form-group">
                            <label>Login</label>
                            <input type="text" class="form-control" name="login" required="required" pattern="[A-Za-z0-9#$%&*()]{6,}"
                            title="Login should contain at least 6 characters. Allowed special characters: # $ % & * ( )">
                        </div>
                        <div class="form-group">
                            <label>Password</label>
                            <input type="password" class="form-control" name="password" required="required" pattern="[A-Za-z0-9#$%&*()]{6,}"
                            title="Password should contain at least 6 characters. Allowed special characters: # $ % & * ( )">
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                        <button type="submit" class="btn btn-primary">Save</button>
                    </div>
                </form:form>
            </div>
        </div>
    </div>

    <div class="modal fade" id="editUserModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle"
         aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <form:form method="PUT" id="editForm" modelAttribute="newUser">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLongTitle">Edit user</h5>
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
                            <label>Surname</label>
                            <input type="text" class="form-control" name="surname" id="surname" required>
                        </div>
                        <div class="form-group">
                            <label>Login</label>
                            <input type="text" class="form-control" name="login" id="login" pattern="[A-Za-z0-9#$%&*()]{6,}"
                             title="Login should contain at least 6 characters. Allowed special characters: # $ % & * ( )">
                        </div>
                        <div class="form-group">
                            <label>Password</label>
                            <input type="password" class="form-control" name="password" pattern="[A-Za-z0-9#$%&*()]{6,}"
                            title="Password should contain at least 6 characters. Allowed special characters: # $ % & * ( )">
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