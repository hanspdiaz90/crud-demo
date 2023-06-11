package pe.edu.unprg.javaee.cruddemo.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "dashboardServlet", urlPatterns = "/admincrud/dashboard")
public class DashboardServlet extends HttpServlet {

    //private final MenuPermissionService menuPermissionService = new MenuPermissionServiceImpl();
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
        /*
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("loggedUser");
        List<MenuPermission> menus = menuPermissionService.findAllMenuPermissionByRole(user.getRole().getRoleId());
        request.setAttribute("navUser", menus);
        */
        RequestDispatcher dispatcher = request.getRequestDispatcher(VIEW_TEMPLATE_PATH);
        dispatcher.forward(request, response);
    }

}