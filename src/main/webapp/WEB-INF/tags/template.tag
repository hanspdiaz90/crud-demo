<%@ tag description="Default page template" pageEncoding="UTF-8" language="java" %>
<%@ attribute name="tittle" required="true" rtexprvalue="true" %>
<%@ attribute name="head" fragment="true" %>
<%@ attribute name="breadcrumb" fragment="true" %>
<%@ attribute name="content" fragment="true" %>
<%@ attribute name="javascript" fragment="true" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <jsp:invoke fragment="head"/>
        <title>${tittle}</title>
    </head>
    <body class="hold-transition sidebar-mini">
        <!-- Site wrapper -->
        <div class="wrapper">
            <!-- Preloader -->
            <jsp:include page="/WEB-INF/partials/_preloader.jsp"/>
            <!-- /.preloader -->
            <!-- Navbar -->
            <jsp:include page="/WEB-INF/partials/_navbar.jsp"/>
            <!-- /.navbar -->
            <!-- Main Sidebar Container -->
            <jsp:include page="/WEB-INF/partials/_sidebar.jsp"/>
            <!-- /.main-sidebar -->
            <!-- Content Wrapper. Contains page content -->
            <div class="content-wrapper">
                <!-- Content Header (Page header) -->
                <section class="content-header">
                    <jsp:invoke fragment="breadcrumb"/>
                </section>
                <!-- /.content-header -->
                <!-- Main content -->
                <section class="content">
                    <jsp:invoke fragment="content"/>
                </section>
                <!-- /.content -->
            </div>
            <!-- /.content-wrapper -->
            <!-- Main Footer -->
            <jsp:include page="/WEB-INF/partials/_footer.jsp"/>
            <!-- /.main-footer -->
            <!-- Control Sidebar -->
            <aside class="control-sidebar control-sidebar-dark">
                <!-- Control sidebar content goes here -->
            </aside>
            <!-- /.control-sidebar -->
        </div>
        <!-- ./wrapper -->
        <jsp:invoke fragment="javascript"/>
    </body>
</html>