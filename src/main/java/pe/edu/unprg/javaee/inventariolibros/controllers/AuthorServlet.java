package pe.edu.unprg.javaee.inventariolibros.controllers;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import pe.edu.unprg.javaee.inventariolibros.entities.Author;
import pe.edu.unprg.javaee.inventariolibros.exception.ServiceException;
import pe.edu.unprg.javaee.inventariolibros.services.IAuthorService;
import pe.edu.unprg.javaee.inventariolibros.services.factory.ServiceFactory;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "autorServlet", urlPatterns = "/biblioteca/autores")
public class AuthorServlet extends HttpServlet {

    private static final String PATH_AUTORES = "/WEB-INF/views/authors/index.jsp";
    private final Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
    private final IAuthorService authorService = ServiceFactory.getInstance().getAuthorService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("accion");
        if (action == null) {
            action = "index";
        }
        try {
            switch (action) {
                case "registrar":
                    insertAuthorAction(request, response);
                    break;
                case "actualizar":
                    System.out.println("Próximo a implementarse...");
                    break;
                case "buscar":
                    searchAuthonByIdAction(request, response);
                    break;
                case "cambiarEstado":
                    changeAuthorStatusAction(request, response);
                    break;
                case "listar":
                    authorsListAction(response);
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
        RequestDispatcher dispatcher = request.getRequestDispatcher(PATH_AUTORES);
        dispatcher.forward(request, response);
    }

    private void insertAuthorAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
        boolean ok = false;
        String message = null;
        JsonObject jsonResponse = new JsonObject();
        try {
            String nombres = request.getParameter("nombres");
            String apellidos = request.getParameter("apellidos");
            String ciudad = request.getParameter("ciudad");
            if (nombres.isEmpty() || apellidos.isEmpty() || ciudad.isEmpty()) {
                message = "No se registraron los datos del autor";
            } else {
                Author author = new Author(nombres, apellidos, ciudad);
                boolean success = authorService.insert(author);
                if (success) {
                    ok = true;
                    message = "Los datos del autor se registraron con éxito";
                }
            }
            jsonResponse.addProperty("ok", ok);
            jsonResponse.addProperty("message", message);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(jsonResponse.toString());
        } catch (ServiceException ex) {
            response.setContentType("text/html");
            response.getWriter().print(ex.getMessage());
        }
    }

    private void searchAuthonByIdAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
        boolean ok = false;
        JsonElement data = null;
        JsonObject jsonResponse = new JsonObject();
        try {
            String id = request.getParameter("id");
            Author autor = authorService.findById(Integer.parseInt(id));
            if (autor != null) {
                ok = true;
                data = gson.toJsonTree(autor);
            }
            jsonResponse.addProperty("ok", ok);
            jsonResponse.add("data", data);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(jsonResponse.toString());
        } catch (ServiceException ex) {
            response.setContentType("text/html");
            response.getWriter().print(ex.getMessage());
        }
    }

    private void authorsListAction(HttpServletResponse response) throws IOException {
        boolean ok = false;
        JsonArray result = null;
        JsonObject jsonResponse = new JsonObject();
        try {
            List<Author> listaAutores = authorService.findAll();
            if (listaAutores != null) {
                ok = true;
                JsonElement items = gson.toJsonTree(listaAutores, new TypeToken<List<Author>>(){}.getType());
                result = items.getAsJsonArray();
            }
            jsonResponse.addProperty("ok", ok);
            jsonResponse.add("result", result);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(jsonResponse.toString());
        } catch (ServiceException ex) {
            response.setContentType("text/html");
            response.getWriter().print(ex.getMessage());
        }
    }

    private void changeAuthorStatusAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
        boolean ok = false;
        String message = null;
        JsonObject jsonResponse = new JsonObject();
        try {
            String id = request.getParameter("id");
            boolean exito = authorService.changeStatusById(Integer.parseInt(id));
            if (exito) {
                ok = true;
                message = "La operación se realizó con éxito";
            }
            jsonResponse.addProperty("ok", ok);
            jsonResponse.addProperty("message", message);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(jsonResponse.toString());
        } catch (ServiceException ex) {
            response.setContentType("text/html");
            response.getWriter().print(ex.getMessage());
        }
    }

}