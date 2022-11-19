package pe.edu.unprg.javaee.cruddemo.controller;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import pe.edu.unprg.javaee.cruddemo.model.Genre;
import pe.edu.unprg.javaee.cruddemo.service.GenreService;
import pe.edu.unprg.javaee.cruddemo.service.impl.GenreServiceImpl;
import pe.edu.unprg.javaee.cruddemo.utils.JSONResponse;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

@WebServlet(name = "genreServlet", urlPatterns = "/biblioteca/generos")
public class GenreServlet extends HttpServlet {

    private final GenreService genreService = new GenreServiceImpl();
    private static final String VIEW_TEMPLATE_PATH = "/WEB-INF/views/genres/index.jsp";
    private final Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("accion") == null ? "index" : request.getParameter("accion");
        switch (action) {
            case "crear":
                createGenreAction(request, response);
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
                mainAction(request, response);
                break;
        }
    }

    private void mainAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("cardTitle", "Listado de géneros literarios");
        RequestDispatcher dispatcher = request.getRequestDispatcher(VIEW_TEMPLATE_PATH);
        dispatcher.forward(request, response);
    }

    private void createGenreAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getParameter("name") != null) {
            String name = request.getParameter("name");
            Genre genre = new Genre();
            genre.setName(name);
            boolean created = genreService.createGenre(genre);
            JsonObject json = new JsonObject();
            String message = null;
            if (created) {
                message = "El género literario ha sido registrado con éxito";
                json.addProperty("status", "success");
            } else {
                json.addProperty("status", "error");
            }
            json.addProperty("message", message);
            JSONResponse.writeFromServlet(response, json);
        }
    }

    private void moreDetailsGenreAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getParameter("genreId") != null) {
            int genreId = Integer.parseInt(request.getParameter("genreId"));
            Genre foundGenre = genreService.findByGenreId(genreId);
            JsonObject json = new JsonObject();
            JsonElement result = null;
            if (foundGenre != null) {
                result = this.gson.toJsonTree(foundGenre);
                json.addProperty("status", "success");
            } else {
                json.addProperty("status", "error");
            }
            json.add("result", result);
            JSONResponse.writeFromServlet(response, json);
        }
    }

    private void genreListAction(HttpServletResponse response) throws IOException {
        JsonObject json = new JsonObject();
        JsonArray data = null;
        List<Genre> genreList = genreService.findAll();
        if (genreList != null) {
            Type genreType = new TypeToken<List<Genre>>(){}.getType();
            JsonElement result = this.gson.toJsonTree(genreList, genreType);
            data = result.getAsJsonArray();
            json.addProperty("status", "success");
        } else {
            json.addProperty("status", "error");
        }
        json.add("result", data);
        JSONResponse.writeFromServlet(response, json);
    }

    private void disableGenreAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getParameter("genreId") != null) {
            int genreId = Integer.parseInt(request.getParameter("genreId"));
            boolean disabled = genreService.disableByGenreId(genreId);
            JsonObject json = new JsonObject();
            String message = null;
            if (disabled) {
                message = "El género literario ha sido deshabilitado con éxito";
                json.addProperty("status", "success");
            } else {
                json.addProperty("status", "error");
            }
            json.addProperty("message", message);
            JSONResponse.writeFromServlet(response, json);
        }
    }

}