<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}" />
<!-- jQuery -->
<script src="${path}/assets/plugins/jquery/jquery.min.js"></script>
<!-- Bootstrap 4 -->
<script src="${path}/assets/plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
<!-- overlayScrollbars -->
<%--<script src="../assets/plugins/overlayScrollbars/js/jquery.overlayScrollbars.min.js"></script>--%>
<!-- AdminLTE App -->
<script src="${path}/assets/dist/js/adminlte.js"></script>
<script>
    $(function () {
        let url = window.location;
        // console.log(url.href);
        // console.log(url.pathname);
        // Alternar la barra lateral de navegación
        $("ul.nav-sidebar a").filter(toggleNavSidebar);
        // Alternar vista de árbol de navegación
        $("ul.nav-treeview a").filter(toggleNavTreeview);
        function toggleNavSidebar() {
            if (this.href == url.href) return $(this).addClass("active");
        }
        function toggleNavTreeview() {
            if (this.href == url.href) return $(this).parentsUntil(".nav-sidebar > .nav-treeview").addClass("menu-open").prev("a").addClass("active");
        }
    });
</script>