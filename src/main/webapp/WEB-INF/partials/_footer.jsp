<%@ page import="java.time.LocalDate" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="anioActual" value="${LocalDate.now().getYear()}"/>
<footer class="main-footer">
    <strong>Copyright &copy; ${anioActual} <a href="https://adminlte.io">AdminLTE.io</a>.</strong>
    All rights reserved.
    <div class="float-right d-none d-sm-inline-block">
        <b>Versión</b> 3.2.0
    </div>
</footer>