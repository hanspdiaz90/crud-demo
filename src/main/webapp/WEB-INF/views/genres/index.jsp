<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="tm" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<%
    response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);
    if (session.getAttribute("loggedUser") == null) {
        response.sendRedirect(request.getContextPath() + "/");
    }
%>
<tm:template tittle="DemoCRUD | Géneros">
    <jsp:attribute name="head">
        <jsp:include page="/WEB-INF/partials/_head.jsp"/>
        <!-- SweetAlert2 -->
        <link rel="stylesheet" href="${contextPath}/assets/plugins/sweetalert2/sweetalert2.min.css">
        <!-- DataTables -->
        <link rel="stylesheet" href="${contextPath}/assets/plugins/datatables-bs4/css/dataTables.bootstrap4.min.css">
        <link rel="stylesheet" href="${contextPath}/assets/plugins/datatables-responsive/css/responsive.bootstrap4.min.css">
    </jsp:attribute>
    <jsp:attribute name="breadcrumb">
        <div class="container-fluid">
            <div class="row mb-2">
                <div class="col-sm-6">
                    <h1>Géneros Literarios</h1>
                </div>
                <div class="col-sm-6">
                    <ol class="breadcrumb float-sm-right">
                        <li class="breadcrumb-item"><a href="${contextPath}/admincrud/dashboard">Inicio</a></li>
                        <li class="breadcrumb-item active">Géneros Literarios</li>
                    </ol>
                </div>
            </div>
        </div>
        <!-- /.container-fluid -->
    </jsp:attribute>
    <jsp:attribute name="content">
        <div class="container-fluid">
            <div class="row">
                <div class="col-12">
                    <!-- Card -->
                    <div class="card card-outline card-primary">
                        <!-- Card Header -->
                        <div class="card-header">
                            <div class="d-flex align-items-center justify-content-between">
                                <h3 class="card-title">
                                    <i class="fas fa-clipboard-list"></i> ${cardTitle}
                                </h3>
                                <button type="button" class="btn btn-primary btn-sm"
                                        data-toggle="modal" data-target="#addEditModal"
                                        data-tooltip="tooltip" data-placement="left" title="Añadir género literario" id="btnNew">
                                    <i class="fas fa-plus"></i>
                                </button>
                            </div>
                        </div>
                        <!-- /.card-header -->
                        <!-- Card Body -->
                        <div class="card-body">
                            <jsp:include page="dataTable.jsp"/>
                        </div>
                        <!-- /.card-body -->
                    </div>
                    <!-- /.card -->
                </div>
            </div>
        </div>
        <!-- /.container-fluid -->
        <!-- #addEditModal -->
        <jsp:include page="addEditModal.jsp"/>
        <!-- /.modal (Add Edit Modal)-->
        <!-- #viewDetailModal -->
        <jsp:include page="viewDetailModal.jsp"/>
        <!-- /.modal (View Details Modal)-->
    </jsp:attribute>
    <jsp:attribute name="javascript">
        <jsp:include page="/WEB-INF/partials/_javascript.jsp"/>
        <!-- jQuery Validation -->
        <script src="${contextPath}/assets/plugins/jquery-validation/jquery.validate.min.js"></script>
        <script src="${contextPath}/assets/plugins/jquery-validation/localization/messages_es_PE.min.js"></script>
        <!-- SweetAlert2 -->
        <script src="${contextPath}/assets/plugins/sweetalert2/sweetalert2.min.js"></script>
        <!-- DataTables  & Plugins -->
        <script src="${contextPath}/assets/plugins/datatables/jquery.dataTables.min.js"></script>
        <script src="${contextPath}/assets/plugins/datatables-bs4/js/dataTables.bootstrap4.min.js"></script>
        <script src="${contextPath}/assets/plugins/datatables-responsive/js/dataTables.responsive.min.js"></script>
        <script src="${contextPath}/assets/plugins/datatables-responsive/js/responsive.bootstrap4.min.js"></script>
        <!-- CustomJS -->
        <script src="${contextPath}/assets/js/default-datatable.js"></script>
        <script src="${contextPath}/assets/js/default-validation-bs4.js"></script>
        <script src="${contextPath}/assets/js/nav-menu/recursive-menu.js"></script>
        <script src="${contextPath}/assets/js/genres/app.js"></script>
    </jsp:attribute>
</tm:template>