<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@tag description="Page template" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>eShop</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
          integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"
            integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"
            integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy"
            crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.4.2/css/all.css"
          integrity="sha384-/rXc/GQVaYpyDdyxK+ecHPVYJSN9bmVFBvjA/9eOB+pb3F2w2N6fc5qB9Ew5yIns" crossorigin="anonymous">
    <link href="<c:url value="/css/login_styles.css" />" rel="stylesheet" type="text/css">
    <link href="<c:url value="/css/sidebar.css" />" rel="stylesheet" type="text/css">
    <link href="<c:url value="/css/toast.css" />" rel="stylesheet" type="text/css">
    <script src="<c:url value="/js/modalController.js"/>"></script>
    <script src="<c:url value="/js/productController.js"/>"></script>
</head>
<body class="homepage">
<div id="wrapper" class="toggled">
    <div id="sidebar-wrapper">
        <ul class="sidebar-nav">
            <li class="sidebar-brand">
                <a href="/">
                    eShop
                </a>
            </li>
            <sec:authorize access="!isAuthenticated()">
                <li>
                    <a href="/login"><i class="fas fa-sign-in-alt"></i> Log in</a>
                </li>
                <li>
                    <a href="/register"><i class="fas fa-user-plus"></i> Sign up</a>
                </li>
            </sec:authorize>
            <sec:authorize access="isAuthenticated()">
                <sec:authorize access="hasAuthority('ADMIN')">
                    <li>
                        <a href="#admin" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle"><i
                                class="fas fa-toolbox"></i> Admin panel</a>
                        <ul class="collapse list-unstyled" id="admin">
                            <li>
                                <a href="/admin/users?page=1&size=10">Users</a>
                            </li>
                            <li>
                                <a href="/admin/products?page=1&size=10">Products</a>
                            </li>
                        </ul>
                    </li>
                </sec:authorize>
                <li>
                    <a href="/category"><i class="fas fa-th-list"></i> Categories</a>
                </li>
                <li>
                    <a href="/cart"><i class="fas fa-shopping-cart"></i> Cart</a>
                </li>
                <li>
                    <a href="/order/details"><i class="fas fa-money-check"></i></i> My orders</a>
                </li>
                <li>
                    <a href="/user"><i class="far fa-user"></i></i> Profile</a>
                </li>
                <li>
                    <a href="/user/password"><i class="fas fa-key"></i></i> Change password</a>
                </li>
                <li>
                    <a href="/logout"><i class="fas fa-sign-out-alt"></i> Log out</a>
                </li>
            </sec:authorize>

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
