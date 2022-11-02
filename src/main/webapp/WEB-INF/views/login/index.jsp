<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>AdminLTE 3 | Log in</title>
        <!-- Google Font: Source Sans Pro -->
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700&display=fallback">
        <!-- Font Awesome -->
        <link rel="stylesheet" href="${path}/assets/plugins/fontawesome-free/css/all.min.css">
        <!-- icheck bootstrap -->
        <link rel="stylesheet" href="${path}/assets/plugins/icheck-bootstrap/icheck-bootstrap.min.css">
        <!-- Theme style -->
        <link rel="stylesheet" href="${path}/assets/dist/css/adminlte.min.css">
    </head>
    <body class="hold-transition login-page">
        <div class="login-box">
            <div class="login-logo">
                <a href="#"><b>Admin</b>LTE</a>
            </div>
            <!-- /.login-logo -->
            <div class="card">
                <div class="card-body login-card-body">

                    <p class="login-box-msg">Sign in to start your session</p>

<%--                    <c:if test="${!empty requestScope.message}">--%>
<%--                        <p class="alert-danger"><c:out value="${requestScope.message}"/></p>--%>
<%--                    </c:if>--%>

                    <form id="loginForm">
                        <div class="input-group mb-3">
                            <input type="email" id="txtEmail" class="form-control" name="email" placeholder="E-mail">
                            <div class="input-group-append">
                                <div class="input-group-text">
                                    <span class="fas fa-envelope"></span>
                                </div>
                            </div>
                        </div>
                        <div class="input-group mb-3">
                            <input type="password" id="txtPassword" class="form-control" name="password" placeholder="Contraseña">
                            <div class="input-group-append">
                                <div class="input-group-text">
                                    <span class="fas fa-lock"></span>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-8">
                                <div class="icheck-primary">
                                    <input type="checkbox" id="remember">
                                    <label for="remember">Remember Me</label>
                                </div>
                            </div>
                            <!-- /.col -->
                            <div class="col-4">
                                <button type="submit" id="btnLogin" class="btn btn-primary btn-block">Sign In</button>
                            </div>
                            <!-- /.col -->
                        </div>
                    </form>

                    <p class="mb-1">
                        <a href="${path}/biblioteca/dashboard">I forgot my password</a>
                    </p>
                    <p class="mb-0">
                        <a href="${path}/biblioteca/dashboard" class="text-center">Register a new membership</a>
                    </p>
                </div>
                <!-- /.login-card-body -->
            </div>
        </div>
        <!-- /.login-box -->
        <!-- jQuery -->
        <script src="${path}/assets/plugins/jquery/jquery.min.js"></script>
        <!-- Bootstrap 4 -->
        <script src="${path}/assets/plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
        <!-- jQuery Validation -->
        <script src="${path}/assets/plugins/jquery-validation/jquery.validate.min.js"></script>
        <script src="${path}/assets/plugins/jquery-validation/localization/messages_es_PE.min.js"></script>
        <!-- AdminLTE App -->
        <script src="${path}/assets/dist/js/adminlte.min.js"></script>
        <script src="${path}/assets/js/user/app.js"></script>
    </body>
</html>