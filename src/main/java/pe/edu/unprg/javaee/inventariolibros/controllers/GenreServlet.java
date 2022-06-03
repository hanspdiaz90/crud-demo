package pe.edu.unprg.javaee.inventariolibros.controllers;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import pe.edu.unprg.javaee.inventariolibros.models.Genre;
import pe.edu.unprg.javaee.inventariolibros.exception.ServiceException;
import pe.edu.unprg.javaee.inventariolibros.services.IGenreService;
import pe.edu.unprg.javaee.inventariolibros.services.factory.ServiceFactory;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.List;

@WebServlet(name = "genreServlet", urlPatterns = "/biblioteca/generos")
public class GenreServlet extends HttpServlet {

    private static final String PATH = "/WEB-INF/views/genres/index.jsp";
    private final Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
    private final IGenreService genreService = ServiceFactory.getInstance().getGenreService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        String action = request.getParameter("accion");
        if (action == null) {
            action = "index";
        }
        try {
            switch (action) {
                case "crear":
                    insertGenreAction(request, response);
                    break;
                case "editar":
                    System.out.println("Próximo a implementarse...");
                    break;
                case "verDetalles":
                    moreDetailsGenreAction(request, response);
                    break;
                case "deshabilitar":
                    disableGenreAction(request, response);
                    break;
                case "listar":
                    genreListAction(response);
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
        request.setAttribute("cardTitle", "Listado de géneros literarios");
        RequestDispatcher dispatcher = request.getRequestDispatcher(PATH);
        dispatcher.forward(request, response);
    }

    private void insertGenreAction(HttpServletRequest request, HttpServletResponse response) throws ServiceException, IOException {
        if (request.getParameter("nombre") != null) {
            String nombre = request.getParameter("nombre");
            Genre genre = new Genre();
            genre.setNombre(nombre);
            boolean inserted = genreService.insert(genre);
            JsonObject json = new JsonObject();
            String message = null;
            if (inserted) {
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

    private void moreDetailsGenreAction(HttpServletRequest request, HttpServletResponse response) throws ServiceException, IOException {
        if (request.getParameter("id") != null) {
            int id = Integer.parseInt(request.getParameter("id"));
            Genre found = genreService.findById(id);
            JsonObject json = new JsonObject();
            JsonElement result = null;
            if (found != null) {
                result = gson.toJsonTree(found);
                json.addProperty("status", "success");
            } else {
                json.addProperty("status", "error");
            }
            json.add("result", result);
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            out.print(json);
            out.flush();
        }
    }

    private void genreListAction(HttpServletResponse response) throws ServiceException, IOException {
        JsonObject json = new JsonObject();
        JsonArray data = null;
        List<Genre> genreList = genreService.findAll();
        if (genreList != null) {
            json.addProperty("status", "success");
            Type typeGenre = new TypeToken<List<Genre>>(){}.getType();
            JsonElement result = gson.toJsonTree(genreList, typeGenre);
            data = result.getAsJsonArray();
        } else {
            json.addProperty("status", "error");
        }
        json.add("result", data);
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(json);
        out.flush();
    }

    private void disableGenreAction(HttpServletRequest request, HttpServletResponse response) throws ServiceException, IOException {
        if (request.getParameter("id") != null) {
            int id = Integer.parseInt(request.getParameter("id"));
            boolean disable = genreService.disableById(id);
            JsonObject json = new JsonObject();
            String message = null;
            if (disable) {
                json.addProperty("status", "success");
                message = "El género literario ha sido deshabilitado con éxito";
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