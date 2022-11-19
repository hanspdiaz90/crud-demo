<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!-- jQuery -->
<script src="${contextPath}/assets/plugins/jquery/jquery.min.js"></script>
<!-- Bootstrap 4 -->
<script src="${contextPath}/assets/plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
<!-- overlayScrollbars -->
<%--<script src="../assets/plugins/overlayScrollbars/js/jquery.overlayScrollbars.min.js"></script>--%>
<!-- AdminLTE App -->
<script src="${contextPath}/assets/dist/js/adminlte.js"></script>
<!-- CustomJS -->
<script>
    let contextPath = "${contextPath}";
    $(function () {
        let url = window.location;
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