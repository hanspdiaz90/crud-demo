<%@ tag description="Default page template" pageEncoding="UTF-8" language="java" %>
<%@ attribute name="_tittle" required="true" rtexprvalue="true" %>
<%@ attribute name="_head" fragment="true" %>
<%@ attribute name="_breadcrumb" fragment="true" %>
<%@ attribute name="_content" fragment="true" %>
<%@ attribute name="_scripts" fragment="true" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <jsp:invoke fragment="_head"/>
        <title>${_tittle}</title>
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
                    <jsp:invoke fragment="_breadcrumb"/>
                </section>
                <!-- /.content-header -->
                <!-- Content -->
                <section class="content">
                    <jsp:invoke fragment="_content"/>
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
        <jsp:invoke fragment="_scripts"/>
    </body>
</html>