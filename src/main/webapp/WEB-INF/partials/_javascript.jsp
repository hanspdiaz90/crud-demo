<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<script>
    let contextPath = "${contextPath}";
    let roleId = ${loggedUser.role.roleId};
    let url = window.location;
</script>
<!-- jQuery -->
<script src="${contextPath}/assets/plugins/jquery/jquery.min.js"></script>
<!-- Bootstrap 4 -->
<script src="${contextPath}/assets/plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
<!-- overlayScrollbars -->
<script src="${contextPath}/assets/plugins/overlayScrollbars/js/jquery.overlayScrollbars.min.js"></script>
<!-- AdminLTE App -->
<script src="${contextPath}/assets/dist/js/adminlte.js"></script>