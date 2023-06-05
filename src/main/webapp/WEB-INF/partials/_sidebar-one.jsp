<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="loggedUser" value="${sessionScope.loggedUser}"/>
<c:set var="navUser" value="${sessionScope.navUser}"/>
<aside class="main-sidebar sidebar-dark-primary elevation-4">
    <!-- Brand Logo -->
    <a href="${contextPath}/admincrud/dashboard" class="brand-link">
        <img src="${contextPath}/assets/dist/img/AdminLTELogo.png" alt="AdminCRUD Logo"
             class="brand-image img-circle elevation-3" style="opacity: .8">
        <span class="brand-text font-weight-light">AdminCRUD</span>
    </a>
    <!-- Sidebar -->
    <div class="sidebar">
        <!-- Sidebar user (optional) -->
        <div class="user-panel mt-3 pb-3 mb-3 d-flex">
            <div class="image">
                <img src="${contextPath}/assets/dist/img/user2-160x160.jpg" class="img-circle elevation-2"
                     alt="User Image">
            </div>
            <div class="info">
                <a href="#" class="d-block"><c:out value="${loggedUser.username}"/></a>
            </div>
        </div>
        <c:if test="${fn:length(navUser) > 0}">
            <!-- Sidebar Menu -->
            <nav class="mt-2">
                <ul class="nav nav-pills nav-sidebar flex-column" data-widget="treeview" role="menu"
                    data-accordion="false">
                    <!-- Add icons to the links using the .nav-icon class
                         with font-awesome or any other icon font library -->
                    <c:forEach var="nav" items="${navUser}">
                        <li class="nav-header">${"MÓDULO ".concat(nav.module)}</li>
                        <li class="nav-item">
                            <c:choose>
                                <c:when test="${fn:length(nav.children) == 0}">
                                    <a href="${not empty nav.route ? contextPath.concat(nav.route) : '#'}" class="nav-link">
                                        <i class="${nav.icon} nav-icon"></i>
                                        <p>${nav.title}</p>
                                    </a>
                                </c:when>
                                <c:otherwise>
                                    <a href="#" class="nav-link">
                                        <i class="nav-icon ${nav.icon}"></i>
                                        <p>${nav.title}
                                            <i class="fas fa-angle-left right"></i>
                                            <span class="badge badge-info right">${fn:length(nav.children)}</span>
                                        </p>
                                    </a>
                                    <ul class="nav nav-treeview">
                                        <c:forEach var="level" items="${nav.children}">
                                            <li class="nav-item">
                                                <c:choose>
                                                    <c:when test="${fn:length(level.children) == 0}">
                                                        <a href="${not empty level.route ? contextPath.concat(level.route) : '#'}" class="nav-link">
                                                            <i class="${level.icon} nav-icon"></i>
                                                            <p>${level.title}</p>
                                                        </a>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <a href="#" class="nav-link">
                                                            <i class="${level.icon} nav-icon"></i>
                                                            <p>${level.title}
                                                                <i class="fas fa-angle-left right"></i>
                                                                <span class="badge badge-info right">${fn:length(level.children)}</span>
                                                            </p>
                                                        </a>
                                                        <ul class="nav nav-treeview">
                                                            <c:forEach var="sublevel" items="${level.children}">
                                                                <li class="nav-item">
                                                                    <a href="${contextPath.concat(sublevel.route)}" class="nav-link">
                                                                        <i class="${sublevel.icon} nav-icon"></i>
                                                                        <p>${sublevel.title}</p>
                                                                    </a>
                                                                </li>
                                                            </c:forEach>
                                                        </ul>
                                                    </c:otherwise>
                                                </c:choose>
                                            </li>
                                        </c:forEach>
                                    </ul>
                                </c:otherwise>
                            </c:choose>
                        </li>
                    </c:forEach>
                </ul>
            </nav>
            <!-- /.sidebar-menu -->
        </c:if>
    </div>
    <!-- /.sidebar -->
</aside>