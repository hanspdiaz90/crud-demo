package pe.edu.unprg.javaee.inventariolibros.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import pe.edu.unprg.javaee.inventariolibros.model.User;
import pe.edu.unprg.javaee.inventariolibros.service.UserService;
import pe.edu.unprg.javaee.inventariolibros.service.impl.UserServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "loginServlet", urlPatterns = "/biblioteca/login")
public class LoginServlet extends HttpServlet {

    private final UserService userService = new UserServiceImpl();
    private static final String VIEW_TEMPLATE_PATH = "/WEB-INF/views/dashboard/index.jsp";

    private final Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("accion") != null) {
            String action = request.getParameter("accion");
            if (action.equalsIgnoreCase("iniciarSesion")) {
                loginUserAction(request, response);
            }
        }
    }

    private void loginUserAction(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (request.getParameter("email") != null &&
                request.getParameter("password") != null) {
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            JsonObject json = new JsonObject();
            if (!email.isEmpty() && !password.isEmpty()) {
                User userInfo = userService.authenticateUser(email, password);
                if (userInfo != null) {
                    HttpSession session = request.getSession();
                    String role = "";
                    switch (userInfo.getRole()) {
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
                    session.setAttribute(role, userInfo);
                    request.setAttribute("username", userInfo.getEmail());
//                    RequestDispatcher dispatcher = request.getRequestDispatcher(VIEW_TEMPLATE_PATH);
//                    dispatcher.forward(request, response);
                    PrintWriter out = response.getWriter();
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    out.print("success");
                    out.flush();
                    response.sendRedirect(request.getContextPath() + "/biblioteca/dashboard");
                } else {
                    json.addProperty("message", "El e-mail y/o contraseña son incorrectos");
//                    json.addProperty("message", "Incorrect username or password.");
                    PrintWriter out = response.getWriter();
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    out.print(json);
                    out.flush();
                    RequestDispatcher dispatcher = request.getRequestDispatcher(VIEW_TEMPLATE_PATH);
                    dispatcher.include(request, response);
                }
            } else {
                json.addProperty("message", "El email y/o contraseña no pueden estar vacíos");
                PrintWriter out = response.getWriter();
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                out.print(json);
                out.flush();
                RequestDispatcher dispatcher = request.getRequestDispatcher(VIEW_TEMPLATE_PATH);
                dispatcher.include(request, response);
            }

        }
    }

}