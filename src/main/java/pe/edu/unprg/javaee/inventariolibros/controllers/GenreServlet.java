package pe.edu.unprg.javaee.inventariolibros.controllers;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import pe.edu.unprg.javaee.inventariolibros.entities.Genre;
import pe.edu.unprg.javaee.inventariolibros.exception.ServiceException;
import pe.edu.unprg.javaee.inventariolibros.services.IGenreService;
import pe.edu.unprg.javaee.inventariolibros.services.factory.ServiceFactory;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "generoServlet", urlPatterns = "/biblioteca/generos")
public class GenreServlet extends HttpServlet {

    private static final String PATH_GENEROS = "/WEB-INF/views/genres/index.jsp";
    private final Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
    private final IGenreService generoService = ServiceFactory.getInstance().getGenreService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = request.getParameter("accion");
        if (accion == null) {
            accion = "index";
        }
        try {
            switch (accion) {
                case "registrar":
                    insertGenreAction(request, response);
                    break;
                case "actualizar":
                    System.out.println("Próximo a implementarse...");
                    break;
                case "buscar":
                    searchGenreByIdAction(request, response);
                    break;
                case "desactivar":
                    deactivateGenreAction(request, response);
                    break;
                case "listar":
                    genreListAction(response);
                    break;
                default:
                    indexAction(request, response);
                    break;
            }
        } catch (IOException ex) {
            throw new ServletException(ex);
        }
    }

    private void indexAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(PATH_GENEROS);
        dispatcher.forward(request, response);
    }

    private void insertGenreAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
        boolean ok = false;
        String message = null;
        JsonObject jsonResponse = new JsonObject();
        try {
            String nombre = request.getParameter("nombre");
            if (nombre.isEmpty()) {
                message = "No se pudo registrar los datos del género literario";
            } else {
                Genre genero = new Genre(nombre);
                boolean exito = generoService.insert(genero);
                if (exito) {
                    ok = true;
                    message = "Los datos del género literario se registraron con éxito";
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

    private void searchGenreByIdAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
        boolean ok = false;
        JsonElement result = null;
        JsonObject jsonResponse = new JsonObject();
        try {
            String id = request.getParameter("id");
            Genre genero = generoService.findById(Integer.parseInt(id));
            if (genero != null) {
                ok = true;
                result = gson.toJsonTree(genero);
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

    private void genreListAction(HttpServletResponse response) throws IOException {
        boolean ok = false;
        JsonArray result = null;
        JsonObject jsonResponse = new JsonObject();
        try {
            List<Genre> listaGeneros = generoService.findAll();
            if (listaGeneros != null) {
                ok = true;
                JsonElement items = gson.toJsonTree(listaGeneros, new TypeToken<List<Genre>>(){}.getType());
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

    private void deactivateGenreAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
        boolean ok = false;
        String message = null;
        JsonObject jsonResponse = new JsonObject();
        try {
            String id = request.getParameter("id");
            boolean exito = generoService.deactivateById(Integer.parseInt(id));
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