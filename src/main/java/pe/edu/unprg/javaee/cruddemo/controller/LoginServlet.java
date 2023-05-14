package pe.edu.unprg.javaee.cruddemo.controller;

import com.google.gson.JsonObject;
import pe.edu.unprg.javaee.cruddemo.model.Menu;
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
import java.util.List;

@WebServlet(name = "loginServlet", urlPatterns = "/admincrud/login")
public class LoginServlet extends HttpServlet {

    private final UserService userService = new UserServiceImpl();

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
                (request.getParameter("password") != null && !request.getParameter("password").isEmpty())) {

            String email = request.getParameter("email");
            String password = request.getParameter("password");
            User foundUser = userService.authenticateUser(email, password);
            if (foundUser != null) {
                String role = foundUser.getRole().getRoleType().name();
                List<Menu> navs = userService.findNavMenuByRole(role);
                HttpSession session = request.getSession();
                String url = "/admincrud/dashboard";
                json.addProperty("success", true);
                json.addProperty("status", "success");
                json.addProperty("url", url);
                session.setAttribute("loggedUser", foundUser);
                session.setAttribute("navsUser", navs);
                JSONResponse.writeFromServlet(response, json);

            } else {
                json.addProperty("success", false);
                json.addProperty("status", "error");
                json.addProperty("message", "El usuario y/o contraseña son incorrectos");
                JSONResponse.writeFromServlet(response, json);
            }
        } else {
            json.addProperty("success", false);
            json.addProperty("status", "error");
            json.addProperty("message", "El usuario y/o contraseña no pueden estar vacíos");
            JSONResponse.writeFromServlet(response, json);
        }
    }

}