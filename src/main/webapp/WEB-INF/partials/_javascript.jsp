<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!-- jQuery -->
<script src="${contextPath}/assets/plugins/jquery/jquery.min.js"></script>
<!-- Bootstrap 4 -->
<script src="${contextPath}/assets/plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
<!-- overlayScrollbars -->
<script src="${contextPath}/assets/plugins/overlayScrollbars/js/jquery.overlayScrollbars.min.js"></script>
<!-- AdminLTE App -->
<script src="${contextPath}/assets/dist/js/adminlte.js"></script>
<!-- CustomJS -->
<script>
    let contextPath = "${contextPath}";
    $(function () {

        getNavigationMenu(${loggedUser.role.roleId});

        let url = window.location;

        function getNavigationMenu(sessionRoleId) {
            let url = contextPath + "/admincrud/menu-permisos?accion=listarMenuNavegacion";
            $.ajax({
                url: url,
                method: "GET",
                data: {roleId: sessionRoleId},
                dataType: "JSON",
                success: function (response) {
                    if (response.success) {
                        let menuData = response.result;
                        let menuView = $(".main-sidebar .sidebar ul");
                        parseRecursiveMenu(menuView, menuData);
                        // Alternar la barra lateral de navegación
                        $("ul.nav-sidebar a").filter(toggleNavSidebar);
                        // Alternar vista de árbol de navegación
                        $("ul.nav-treeview a").filter(toggleNavTreeview);
                    }
                }
            });
        }

        //let li = $(ul).append("<li class='nav-header'>" + "MÓDULO ".concat(menu[i].menu.module.title) + "</li>");
        function parseRecursiveMenu(ul, menu) {
            for (let i = 0; i < menu.length; i++) {
                if (menu[i].children.length > 0) {
                    let li = $(ul).append("<li class='nav-item'>" +
                        "<a href='#' class='nav-link'>" +
                        "<i class='nav-icon " + menu[i].menu.icon + "'></i>" +
                        "<p>" + menu[i].menu.title +
                        "<i class='fas fa-angle-left right'></i>" +
                        "<span class='badge badge-info right'>" + menu[i].children.length + "</span>" +
                        "</p>" +
                        "</a>" +
                        "<ul class='nav nav-treeview' id='submenu-" + menu[i].menu.title.toLowerCase() + "'>" +
                        "</ul>" +
                        "</li>");
                    let subMenuView = $("#submenu-" + menu[i].menu.title.toLowerCase());
                    parseRecursiveMenu(subMenuView, menu[i].children);
                } else {
                    let li = $(ul).append("<li class='nav-item'>" +
                        "<a href='" + (menu[i].menu.route != null ? contextPath.concat(menu[i].menu.route) : '#') + "' class='nav-link'>" +
                        "<i class='" + menu[i].menu.icon + " nav-icon'></i>" +
                        "<p>" + menu[i].menu.title + "</p>" +
                        "</a>" +
                        "</li>");
                }
            }
        }

        function toggleNavSidebar() {
            if (this.href == url.href) return $(this).addClass("active");
        }

        function toggleNavTreeview() {
            if (this.href == url.href) return $(this).parentsUntil(".nav-sidebar > .nav-treeview").addClass("menu-open").prev("a").addClass("active");
        }

    });

</script>