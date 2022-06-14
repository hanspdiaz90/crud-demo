package pe.edu.unprg.javaee.inventariolibros.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import pe.edu.unprg.javaee.inventariolibros.exception.ServiceException;
import pe.edu.unprg.javaee.inventariolibros.models.User;
import pe.edu.unprg.javaee.inventariolibros.services.IUserService;
import pe.edu.unprg.javaee.inventariolibros.services.factory.ServiceFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "loginServlet", urlPatterns = "/biblioteca/login")
public class LoginServlet extends HttpServlet {

    private static final String VIEW_TEMPLATE_PATH = "/WEB-INF/views/dashboard/index.jsp";

    private final Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();

    private final IUserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("accion") != null) {
            String action = request.getParameter("accion");
            if (action.equalsIgnoreCase("iniciarSesion")) {
                try {
                    loginUserAction(request, response);
                } catch (ServiceException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void loginUserAction(HttpServletRequest request, HttpServletResponse response) throws ServiceException, IOException, ServletException {
        if (request.getParameter("email") != null &&
                request.getParameter("password") != null) {
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            JsonObject json = new JsonObject();
            if (!email.isEmpty() && !password.isEmpty()) {
                User userInfo = userService.authenticate(email, password);
                if (userInfo != null) {
//                    HttpSession session = request.getSession(true);
                    String role = "";
                    switch (userInfo.getRol()) {
                        case ADMIN:
                            role = "admin";
                            break;
                        case EDITOR:
                            role = "editor";
                            break;
                        case GUEST:
                            role = "guest";
                            break;
                    }
//                    session.setAttribute(role, userInfo);
                    request.setAttribute("username", userInfo.getEmail());
                    RequestDispatcher dispatcher = request.getRequestDispatcher(VIEW_TEMPLATE_PATH);
                    dispatcher.forward(request, response);
                    PrintWriter out = response.getWriter();
                    response.setContentType("text/html");
                    response.setCharacterEncoding("UTF-8");
                    out.print("success");
//                    out.flush();
//                    response.sendRedirect(request.getContextPath() + viewTemplate);
                } else {
                    json.addProperty("message", "El e-mail y/o contraseña son incorrectos");
                    PrintWriter out = response.getWriter();
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    out.print(json);
                    out.flush();
                }
            } else {
                json.addProperty("message", "El email y/o contraseña no pueden estar vacíos");
                PrintWriter out = response.getWriter();
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                out.print(json);
                out.flush();
            }

        }
    }

}