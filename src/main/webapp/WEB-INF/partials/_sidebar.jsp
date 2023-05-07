<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<aside class="main-sidebar sidebar-dark-primary elevation-4">
    <!-- Brand Logo -->
    <a href="${contextPath}/admincrud/dashboard" class="brand-link">
        <img src="${contextPath}/assets/dist/img/AdminLTELogo.png" alt="AdminCRUD Logo" class="brand-image img-circle elevation-3" style="opacity: .8">
        <span class="brand-text font-weight-light">AdminCRUD</span>
    </a>
    <!-- Sidebar -->
    <div class="sidebar">
        <!-- Sidebar user (optional) -->
        <div class="user-panel mt-3 pb-3 mb-3 d-flex">
            <div class="image">
                <img src="${contextPath}/assets/dist/img/user2-160x160.jpg" class="img-circle elevation-2" alt="User Image">
            </div>
            <div class="info">
                <a href="#" class="d-block"><c:out value="${sessionScope.username}"/></a>
            </div>
        </div>
        <!-- Sidebar Menu -->
        <nav class="mt-2">
            <ul class="nav nav-pills nav-sidebar nav-child-indent flex-column" data-widget="treeview" role="menu" data-accordion="false">
                <!-- Add icons to the links using the .nav-icon class
                     with font-awesome or any other icon font library -->
<%--                <li class="nav-header">DASHBOARD</li>--%>
                <li class="nav-item">
                    <a href="${contextPath}/admincrud/dashboard" class="nav-link">
                        <i class="fas fa-chart-area"></i>
                        <p>Escritorio</p>
                    </a>
                </li>
<%--                <li class="nav-header">MANTENEDORES</li>--%>
                <li class="nav-item">
                    <a href="#" class="nav-link">
                        <i class="nav-icon fas fa-toolbox"></i>
                        <p>
                            Mantenedores
                            <i class="fas fa-angle-left right"></i>
                            <span class="badge badge-info right">4</span>
                        </p>
                    </a>
                    <ul class="nav nav-treeview">
                        <li class="nav-item">
                            <a href="${contextPath}/admincrud/autores" class="nav-link">
                                <i class="far fa-circle nav-icon"></i>
                                <p>Autores</p>
                            </a>
                        </li>
                        <li class="nav-item">
                            <a href="${contextPath}/admincrud/editoriales" class="nav-link">
                                <i class="far fa-circle nav-icon"></i>
                                <p>Editoriales</p>
                            </a>
                        </li>
                        <li class="nav-item">
                            <a href="${contextPath}/admincrud/generos" class="nav-link">
                                <i class="far fa-circle nav-icon"></i>
                                <p>Géneros Literarios</p>
                            </a>
                        </li>
                        <li class="nav-item">
                            <a href="${contextPath}/admincrud/libros" class="nav-link">
                                <i class="far fa-circle nav-icon"></i>
                                <p>Libros</p>
                            </a>
                        </li>
                    </ul>
                </li>
<%--                <li class="nav-header">ALTA DE TABLAS MAESTRAS</li>--%>
<%--                <li class="nav-item">--%>
<%--                    <a href="#" class="nav-link">--%>
<%--                        <i class="nav-icon fas fa-toolbox"></i>--%>
<%--                        <p>--%>
<%--                            Alta de tablas maestras--%>
<%--                            <i class="fas fa-angle-left right"></i>--%>
<%--                            <span class="badge badge-info right">4</span>--%>
<%--                        </p>--%>
<%--                    </a>--%>
<%--                    <ul class="nav nav-treeview">--%>
<%--                        <li class="nav-item">--%>
<%--                            <a href="${path}/biblioteca/autores" class="nav-link">--%>
<%--                                <i class="far fa-circle nav-icon"></i>--%>
<%--                                <p>Autores</p>--%>
<%--                            </a>--%>
<%--                        </li>--%>
<%--                        <li class="nav-item">--%>
<%--                            <a href="${path}/biblioteca/libros" class="nav-link">--%>
<%--                                <i class="far fa-circle nav-icon"></i>--%>
<%--                                <p>Libros</p>--%>
<%--                            </a>--%>
<%--                        </li>--%>
<%--                        <li class="nav-item">--%>
<%--                            <a href="${path}/biblioteca/editoriales" class="nav-link">--%>
<%--                                <i class="far fa-circle nav-icon"></i>--%>
<%--                                <p>Editoriales</p>--%>
<%--                            </a>--%>
<%--                        </li>--%>
<%--                        <li class="nav-item">--%>
<%--                            <a href="${path}/biblioteca/generos" class="nav-link">--%>
<%--                                <i class="far fa-circle nav-icon"></i>--%>
<%--                                <p>Generos</p>--%>
<%--                            </a>--%>
<%--                        </li>--%>
<%--                    </ul>--%>
<%--                </li>--%>
            </ul>
        </nav>
        <!-- /.sidebar-menu -->
    </div>
    <!-- /.sidebar -->
</aside>