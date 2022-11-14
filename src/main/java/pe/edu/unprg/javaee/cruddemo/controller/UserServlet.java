package pe.edu.unprg.javaee.cruddemo.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import pe.edu.unprg.javaee.cruddemo.service.UserService;
import pe.edu.unprg.javaee.cruddemo.service.impl.UserServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "userServlet", urlPatterns = "/biblioteca/usuarios")
public class UserServlet extends HttpServlet {

    private final UserService userService = new UserServiceImpl();
    private static final String VIEW_TEMPLATE_PATH = "/WEB-INF/jsp/dashboard/index.jsp";

    private final Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        String action = request.getParameter("accion");
        if (action == null) {
            action = "index";
        }
        try {
            switch (action) {
                case "editar":
                    System.out.println("Próximo a implementarse...");
                    break;
                case "verDetalles":
                    System.out.println("Próximo a implementarse...");
                    break;
                default:
                    indexAction(request, response);
                    break;
            }
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }

    private void indexAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(VIEW_TEMPLATE_PATH);
        dispatcher.forward(request, response);
    }

    private void insertUserAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getParameter("nombre") != null) {
//            String nombre = request.getParameter("nombre");
//            Genre genre = new Genre();
//            genre.setNombre(nombre);
//            boolean inserted = genreService.insert(genre);
            JsonObject json = new JsonObject();
            String message = null;
            if (true) {
                json.addProperty("status", "success");
                message = "El género literario ha sido registrado con éxito";
            } else {
                json.addProperty("status", "error");
            }
            json.addProperty("message", message);
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            out.print(json);
            out.flush();
        }
    }

}