package pe.edu.unprg.javaee.inventariolibros.controllers;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "dashboardServlet", urlPatterns = "/biblioteca/dashboard")
//@WebServlet(name = "dashboardServlet", urlPatterns = {"", "/biblioteca/dashboard"})
public class DashboardServlet extends HttpServlet {

    private static final String PATH_DASHBOARD = "/WEB-INF/views/dashboard/index.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(PATH_DASHBOARD);
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
