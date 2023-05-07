package pe.edu.unprg.javaee.cruddemo.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import pe.edu.unprg.javaee.cruddemo.model.User;
import pe.edu.unprg.javaee.cruddemo.service.UserService;
import pe.edu.unprg.javaee.cruddemo.service.impl.UserServiceImpl;
import pe.edu.unprg.javaee.cruddemo.utils.JSONResponse;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

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
                json.addProperty("success", true);
                json.addProperty("status", "success");
                json.addProperty("url", "/biblioteca/dashboard");
                session.setAttribute("username", foundUser.getEmail());
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