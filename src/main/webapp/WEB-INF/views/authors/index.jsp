<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="tm" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<tm:template tittle="DemoCRUD | Autores">
    <jsp:attribute name="head">
        <jsp:include page="/WEB-INF/partials/_head.jsp"/>
        <!-- SweetAlert2 -->
        <link rel="stylesheet" href="${contextPath}/assets/plugins/sweetalert2/sweetalert2.min.css">
        <!-- Tempusdominus Bootstrap 4 -->
        <link rel="stylesheet" href="${contextPath}/assets/plugins/tempusdominus-bootstrap-4/css/tempusdominus-bootstrap-4.min.css">
        <!-- DataTables -->
        <link rel="stylesheet" href="${contextPath}/assets/plugins/datatables-bs4/css/dataTables.bootstrap4.min.css">
        <link rel="stylesheet" href="${contextPath}/assets/plugins/datatables-responsive/css/responsive.bootstrap4.min.css">
    </jsp:attribute>
    <jsp:attribute name="breadcrumb">
        <div class="container-fluid">
            <div class="row mb-2">
                <div class="col-sm-6">
                    <h1>Autores</h1>
                </div>
                <div class="col-sm-6">
                    <ol class="breadcrumb float-sm-right">
                        <li class="breadcrumb-item"><a href="${contextPath}/biblioteca/dashboard">Inicio</a></li>
                        <li class="breadcrumb-item active">Autores</li>
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
                                        data-toggle="modal" data-target="#authorAddModal"
                                        data-tooltip="tooltip" data-placement="left" title="Añadir author">
                                    <i class="fas fa-plus"></i>
                                </button>
                            </div>
                        </div>
                        <!-- /.card-header -->
                        <!-- Card Body -->
                        <div class="card-body">
                            <jsp:include page="authorsDataTable.jsp"/>
                        </div>
                        <!-- /.card-body -->
                    </div>
                    <!-- /.card -->
                </div>
            </div>
        </div>
        <!-- /.container-fluid -->
        <!-- #addAuthorModal -->
        <jsp:include page="authorAddModal.jsp"/>
        <!-- /.modal (Add Modal)-->
        <!-- #viewAuthorModal -->
        <jsp:include page="authorViewModal.jsp"/>
        <!-- /.modal (View Details Modal)-->
    </jsp:attribute>
    <jsp:attribute name="javascript">
        <jsp:include page="/WEB-INF/partials/_javascript.jsp"/>
        <!-- jQuery Validation -->
        <script src="${contextPath}/assets/plugins/jquery-validation/jquery.validate.min.js"></script>
        <script src="${contextPath}/assets/plugins/jquery-validation/localization/messages_es_PE.min.js"></script>
        <!-- Tempusdominus Bootstrap 4 -->
        <script src="${contextPath}/assets/plugins/moment/moment.min.js"></script>
        <script src="${contextPath}/assets/plugins/moment/locale/es.js"></script>
        <script src="${contextPath}/assets/plugins/tempusdominus-bootstrap-4/js/tempusdominus-bootstrap-4.min.js"></script>
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
        <script>
            $(function () {
                $("#dtDob").datetimepicker({
                    locale: "es",
                    format: "DD/MM/YYYY"
                    // defaultDate: moment()
                });
            });
        </script>
        <script src="${contextPath}/assets/js/authors/app.js"></script>
    </jsp:attribute>
</tm:template>