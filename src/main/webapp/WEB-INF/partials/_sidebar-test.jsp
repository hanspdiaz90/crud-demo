<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="loggedUser" value="${sessionScope.loggedUser}"/>
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
            <a href="#" class="d-block"><c:out value="${loggedUser.username}"/></a>
        </div>
    </div>
    <c:if test="${fn:length(navUser) > 0}">
        <!-- Sidebar Menu -->
        <nav class="mt-2">
            <ul class="nav nav-pills nav-sidebar flex-column" data-widget="treeview" role="menu" data-accordion="false">
                <!-- Add icons to the links using the .nav-icon class with font-awesome or any other icon font library -->
                <c:forEach var="nav" items="${navUser}">
                    <li class="nav-header">${"MÓDULO ".concat(nav.menu.module.title)}</li>
                    <c:choose>
                        <c:when test="${fn:length(nav.children) == 0}">
                            <li class="nav-item">
                                <a href="${not empty nav.menu.route ? contextPath.concat(nav.menu.route) : '#'}" class="nav-link">
                                    <i class="${nav.menu.icon} nav-icon"></i>
                                    <p>${nav.menu.title}</p>
                                </a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="nav-item">
                                <a href="#" class="nav-link">
                                    <i class="nav-icon ${nav.menu.icon}"></i>
                                    <p>${nav.menu.title}
                                        <i class="fas fa-angle-left right"></i>
                                        <span class="badge badge-info right">${fn:length(nav.children)}</span>
                                    </p>
                                </a>
                                <ul class="nav nav-treeview">
                                    <c:forEach var="level" items="${nav.children}">
                                        <c:choose>
                                            <c:when test="${fn:length(level.children) == 0}">
                                                <li class="nav-item">
                                                    <a href="${not empty level.menu.route ? contextPath.concat(level.menu.route) : '#'}" class="nav-link">
                                                        <i class="${level.menu.icon} nav-icon"></i>
                                                        <p>${level.menu.title}</p>
                                                    </a>
                                                </li>
                                            </c:when>
                                            <c:otherwise>
                                                <li class="nav-item">
                                                    <a href="#" class="nav-link">
                                                        <i class="${level.menu.icon} nav-icon"></i>
                                                        <p>${level.menu.title}
                                                            <i class="fas fa-angle-left right"></i>
                                                            <span class="badge badge-info right">${fn:length(level.children)}</span>
                                                        </p>
                                                    </a>
                                                    <ul class="nav nav-treeview">
                                                        <c:forEach var="sublevel" items="${level.children}">
                                                            <li class="nav-item">
                                                                <a href="${contextPath.concat(sublevel.menu.route)}" class="nav-link">
                                                                    <i class="${sublevel.menu.icon} nav-icon"></i>
                                                                    <p>${sublevel.menu.title}</p>
                                                                </a>
                                                            </li>
                                                        </c:forEach>
                                                    </ul>
                                                </li>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </ul>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </ul>
        </nav>
        <!-- /.sidebar-menu -->
    </c:if>
</div>
<!-- /.sidebar -->