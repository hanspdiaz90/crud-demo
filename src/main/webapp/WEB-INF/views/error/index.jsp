<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="tm" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="loggedUser" value="${sessionScope.loggedUser}"/>
<%
    response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader ("Expires", 0);
%>
<c:if test="${empty loggedUser}">
    <c:redirect url="/"></c:redirect>
</c:if>
<tm:template tittle="DemoCRUD | Error 404">
    <jsp:attribute name="head">
        <jsp:include page="/WEB-INF/partials/_head.jsp"/>
    </jsp:attribute>
    <jsp:attribute name="breadcrumb">
        <div class="container-fluid">
            <div class="row">
                <div class="col-12">
                    <ol class="breadcrumb float-sm-left">
                        <li class="breadcrumb-item"><a href="${contextPath}/admincrud/dashboard">Inicio</a></li>
                        <li class="breadcrumb-item active">Error Página 404</li>
                    </ol>
                </div>
            </div>
        </div>
        <!-- /.container-fluid -->
    </jsp:attribute>
    <jsp:attribute name="content">
        <div class="error-page">
            <h2 class="headline text-warning"> 404</h2>
            <div class="error-content">
                <h3><i class="fas fa-exclamation-triangle text-warning"></i> Oops! Página no encontrada.</h3>
                <p>
                    No pudimos encontrar la página que estabas buscando.
                    Mientras tanto, puedes volver al <a href="${contextPath}/admincrud/dashboard">panel de inicio</a> o intentar usar el formulario de búsqueda.
                </p>
                <form class="search-form">
                    <div class="input-group">
                        <input type="text" name="search" class="form-control" placeholder="Buscar...">
                        <div class="input-group-append">
                            <button type="submit" name="submit" class="btn btn-warning"><i class="fas fa-search"></i></button>
                        </div>
                    </div>
                    <!-- /.input-group -->
                </form>
            </div>
            <!-- /.error-content -->
        </div>
        <!-- /.error-page -->
    </jsp:attribute>
    <jsp:attribute name="javascript">
        <jsp:include page="/WEB-INF/partials/_javascript.jsp"/>
        <script src="${contextPath}/assets/js/nav-menu/recursive-menu.js"></script>
    </jsp:attribute>
</tm:template>