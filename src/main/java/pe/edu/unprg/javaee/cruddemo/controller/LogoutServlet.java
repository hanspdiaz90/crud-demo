package pe.edu.unprg.javaee.cruddemo.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import pe.edu.unprg.javaee.cruddemo.model.User;
import pe.edu.unprg.javaee.cruddemo.service.UserService;
import pe.edu.unprg.javaee.cruddemo.service.impl.UserServiceImpl;
import pe.edu.unprg.javaee.cruddemo.utils.JSONResponse;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "logoutServlet", urlPatterns = "/admincrud/logout")
public class LogoutServlet extends HttpServlet {

    private final UserService userService = new UserServiceImpl();
    private static final String VIEW_TEMPLATE_PATH = "/WEB-INF/views/login/index.jsp";

    private final Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.loginUserAction(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.loginUserAction(request, response);
    }

    private void loginUserAction(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        JsonObject json = new JsonObject();
        if ((request.getParameter("email") != null && !request.getParameter("email").isEmpty()) &&
                (request.getParameter("password") != null && !request.getParameter("password").isEmpty() )) {

            String email = request.getParameter("email");
            String password = request.getParameter("password");

            User foundUser = userService.authenticateUser(email, password);
            if (foundUser != null) {
                //String[] roles = {"admin", "editor", "guest"};
                HttpSession session = request.getSession();
//                String role = "";
//                switch (foundUser.getUserRole()) {
//                    case ADMIN:
//                        role = "admin";
//                        break;
//                    case EDITOR:
//                        role = "editor";
//                        break;
//                    case GUEST:
//                        role = "guest";
//                        break;
//                }
//                session.setAttribute(role, foundUser);
//                request.setAttribute("username", foundUser.getEmail());
//                request.getSession().setAttribute("username", foundUser.getEmail());

                json.addProperty("message", "success");
                //RequestDispatcher dispatcher = request.getRequestDispatcher(VIEW_TEMPLATE_PATH);
//                RequestDispatcher dispatcher = request.getRequestDispatcher("/biblioteca/dashboard");
                session.setAttribute("username", foundUser.getEmail());
//                dispatcher.forward(request, response);
                JSONResponse.writeFromServlet(response, json);

            } else {
                json.addProperty("message", "El e-mail y/o contraseña son incorrectos");
                JSONResponse.writeFromServlet(response, json);
            }
        } else {
            json.addProperty("message", "El email y/o contraseña no pueden estar vacíos");
            JSONResponse.writeFromServlet(response, json);
        }
    }

}