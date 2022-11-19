package pe.edu.unprg.javaee.cruddemo.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "dashboardServlet", urlPatterns = "/biblioteca/dashboard")
public class DashboardServlet extends HttpServlet {

    private static final String VIEW_TEMPLATE_PATH = "/WEB-INF/views/dashboard/index.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.mainAction(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.mainAction(request, response);
    }

    private void mainAction(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(VIEW_TEMPLATE_PATH);
        dispatcher.forward(request, response);
    }

}