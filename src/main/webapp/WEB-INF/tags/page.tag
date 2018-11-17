<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@tag description="Page template" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <title>eShop</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
		<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
		<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
		<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.4.2/css/all.css" integrity="sha384-/rXc/GQVaYpyDdyxK+ecHPVYJSN9bmVFBvjA/9eOB+pb3F2w2N6fc5qB9Ew5yIns" crossorigin="anonymous">
        <link href="<c:url value="/css/login_styles.css"  />" rel="stylesheet" type="text/css">
        <link href="<c:url value="/css/sidebar.css"  />" rel="stylesheet" type="text/css">
        <script src="<c:url value="/js/modalController.js"/>"></script>
    </head>
    <body class="homepage">
    <div id="wrapper" class="toggled">
        <div id="sidebar-wrapper">
            <ul class="sidebar-nav">
                <li class="sidebar-brand">
                    <a href="">
                        eShop
                    </a>
                </li>
                <c:choose>
                    <c:when test="${empty sessionScope.user}">
                <li>
                    <a href="/login"><i class="fas fa-sign-in-alt"></i> Log in</a>
                </li>
                <li>
                    <a href="/register"><i class="fas fa-user-plus"></i> Sign up</a>
                </li>
                    </c:when>
                    <c:otherwise>
                <c:if test="${user.role eq 'ADMIN'}">
                 <li>
                       <a href="#admin" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle"><i class="fas fa-toolbox"></i> Admin panel</a>
                                <ul class="collapse list-unstyled" id="admin">
                                    <li>
                                        <a href="/admin/users">Users</a>
                                    </li>
                                    <li>
                                        <a href="/admin/products">Products</a>
                                    </li>
                                </ul>
                            </li>
                </c:if>
                <li>
                    <a href="/cart"><i class="fas fa-shopping-cart"></i>  ${cart.products.size()} My Cart</a>
                </li>
                <li>
                    <a href="/category"><i class="fas fa-th-list"></i> Categories</a>
                </li>
                <li>
                    <a href="/logout"><i class="fas fa-sign-out-alt"></i> Log out</a>
                </li>
                    </c:otherwise>
                </c:choose>

            </ul>
        </div>
        <div id="page-content-wrapper">
            <div class="container-fluid">
               <section>
                   <jsp:doBody></jsp:doBody>
               </section>
            </div>
        </div>
    </div>
    </body>
</html>
