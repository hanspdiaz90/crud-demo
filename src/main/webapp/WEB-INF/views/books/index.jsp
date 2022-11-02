<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="mt" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<mt:template _tittle="AdminCRUD | Editoriales">
    <jsp:attribute name="_head">
        <jsp:include page="../../partials/_head.jsp"/>
        <!-- Select2 -->
        <link rel="stylesheet" href="${path}/assets/plugins/select2/css/select2.min.css">
        <link rel="stylesheet" href="${path}/assets/plugins/select2-bootstrap4-theme/select2-bootstrap4.min.css">
        <!-- SweetAlert2 -->
        <link rel="stylesheet" href="${path}/assets/plugins/sweetalert2/sweetalert2.min.css">
        <!-- DataTables -->
        <link rel="stylesheet" href="${path}/assets/plugins/datatables-bs4/css/dataTables.bootstrap4.min.css">
        <link rel="stylesheet" href="${path}/assets/plugins/datatables-responsive/css/responsive.bootstrap4.min.css">
    </jsp:attribute>
    <jsp:attribute name="_breadcrumb">
        <div class="container-fluid">
            <div class="row mb-2">
                <div class="col-sm-6">
                    <h1>Libros</h1>
                </div>
                <div class="col-sm-6">
                    <ol class="breadcrumb float-sm-right">
                        <li class="breadcrumb-item"><a href="${path}/biblioteca/dashboard">Inicio</a></li>
                        <li class="breadcrumb-item active">Libros</li>
                    </ol>
                </div>
            </div>
        </div>
        <!-- /.container-fluid -->
    </jsp:attribute>
    <jsp:attribute name="_content">
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
                                        data-toggle="modal" data-target="#bookAddModal"
                                        data-tooltip="tooltip" data-placement="left" title="Añadir book">
                                    <i class="fas fa-plus"></i>
                                </button>
                            </div>
                        </div>
                        <!-- /.card-header -->
                        <!-- Card Body -->
                        <div class="card-body">
                            <jsp:include page="booksDataTable.jsp"/>
                        </div>
                        <!-- /.card-body -->
                    </div>
                    <!-- /.card -->
                </div>
            </div>
        </div>
        <!-- /.container-fluid -->
        <!-- #addBookModal -->
        <jsp:include page="bookAddModal.jsp"/>
        <!-- /.modal (Add New Modal)-->
        <!-- #viewBookModal -->
        <jsp:include page="bookViewModal.jsp"/>
        <!-- /.modal (View Details Modal)-->
    </jsp:attribute>
    <jsp:attribute name="_scripts">
        <jsp:include page="../../partials/_scripts.jsp"/>
        <!-- Select2 -->
        <script src="${path}/assets/plugins/select2/js/select2.min.js"></script>
        <!-- jQuery Validation -->
        <script src="${path}/assets/plugins/jquery-validation/jquery.validate.min.js"></script>
        <script src="${path}/assets/plugins/jquery-validation/localization/messages_es_PE.min.js"></script>
        <!-- SweetAlert2 -->
        <script src="${path}/assets/plugins/sweetalert2/sweetalert2.min.js"></script>
        <!-- DataTables  & Plugins -->
        <script src="${path}/assets/plugins/datatables/jquery.dataTables.min.js"></script>
        <script src="${path}/assets/plugins/datatables-bs4/js/dataTables.bootstrap4.min.js"></script>
        <script src="${path}/assets/plugins/datatables-responsive/js/dataTables.responsive.min.js"></script>
        <script src="${path}/assets/plugins/datatables-responsive/js/responsive.bootstrap4.min.js"></script>
        <!-- CustomJS -->
        <script src="${path}/assets/js/default-datatable.js"></script>
        <script src="${path}/assets/js/default-validation-bs4.js"></script>
        <script src="${path}/assets/js/books/app.js"></script>
    </jsp:attribute>
</mt:template>