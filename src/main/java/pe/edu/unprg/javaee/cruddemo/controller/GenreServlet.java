package pe.edu.unprg.javaee.cruddemo.controller;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import pe.edu.unprg.javaee.cruddemo.model.Genre;
import pe.edu.unprg.javaee.cruddemo.service.GenreService;
import pe.edu.unprg.javaee.cruddemo.service.impl.GenreServiceImpl;
import pe.edu.unprg.javaee.cruddemo.utils.JSONResponse;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

@WebServlet(name = "genreServlet", urlPatterns = "/admincrud/generos")
public class GenreServlet extends HttpServlet {

    private final GenreService genreService = new GenreServiceImpl();
    private static final String VIEW_TEMPLATE_PATH = "/WEB-INF/views/genres/index.jsp";
    private final Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action") == null ? "index" : request.getParameter("action");
        switch (action) {
            case "findById":
                findByIdAction(request, response);
                break;
            case "findAll":
                findAllAction(response);
                break;
            default:
                mainAction(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action") == null ? "index" : request.getParameter("action");
        switch (action) {
            case "create":
                createAction(request, response);
                break;
            case "update":
                updateAction(request, response);
                break;
            case "disableById":
                disableByIdAction(request, response);
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

    private void createAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getParameter("name") != null) {
            String name = request.getParameter("name");
            Genre savedGenre = new Genre();
            savedGenre.setName(name);
            boolean created = genreService.createGenre(savedGenre);
            JsonObject json = new JsonObject();
            String message = null;
            if (created) {
                message = "El género literario ha sido registrado con éxito";
                json.addProperty("success", true);
                json.addProperty("status", "success");
            } else {
                json.addProperty("success", false);
                json.addProperty("status", "failure");
            }
            json.addProperty("message", message);
            JSONResponse.writeFromServlet(response, json);
        }
    }

    private void updateAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getParameter("genreId") != null &&
                request.getParameter("name") != null) {
            Integer genreId = Integer.parseInt(request.getParameter("genreId"));
            String name = request.getParameter("name");
            boolean active = request.getParameter("isActive") != null && request.getParameter("isActive").equals("on");
            Genre updatedGenre = new Genre();
            updatedGenre.setGenreId(genreId);
            updatedGenre.setName(name);
            updatedGenre.setActive(active);
            boolean updated = genreService.editGenre(updatedGenre);
            JsonObject json = new JsonObject();
            String message = null;
            if (updated) {
                message = "Se actualizaron los datos del género literario con éxito";
                json.addProperty("success", true);
                json.addProperty("status", "success");
            } else {
                json.addProperty("success", false);
                json.addProperty("status", "failure");
            }
            json.addProperty("message", message);
            JSONResponse.writeFromServlet(response, json);
        }
    }

    private void findByIdAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getParameter("genreId") != null) {
            int genreId = Integer.parseInt(request.getParameter("genreId"));
            Genre foundGenre = genreService.findByGenreId(genreId);
            JsonObject json = new JsonObject();
            JsonElement result = null;
            if (foundGenre != null) {
                Type genreType = new TypeToken<Genre>(){}.getType();
                result = this.gson.toJsonTree(foundGenre, genreType);
                json.addProperty("success", true);
                json.addProperty("status", "success");
            } else {
                json.addProperty("success", false);
                json.addProperty("status", "failure");
            }
            json.add("result", result);
            JSONResponse.writeFromServlet(response, json);
        }
    }

    private void findAllAction(HttpServletResponse response) throws IOException {
        JsonObject json = new JsonObject();
        JsonArray data = null;
        List<Genre> genreList = genreService.findAll();
        if (genreList != null) {
            Type genreType = new TypeToken<List<Genre>>(){}.getType();
            JsonElement result = this.gson.toJsonTree(genreList, genreType);
            data = result.getAsJsonArray();
            json.addProperty("success", true);
            json.addProperty("status", "success");
        } else {
            json.addProperty("success", false);
            json.addProperty("status", "failure");
        }
        json.add("result", data);
        JSONResponse.writeFromServlet(response, json);
    }

    private void disableByIdAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getParameter("genreId") != null) {
            int genreId = Integer.parseInt(request.getParameter("genreId"));
            boolean disabled = genreService.disableByGenreId(genreId);
            JsonObject json = new JsonObject();
            String message = null;
            if (disabled) {
                message = "El género literario ha sido deshabilitado con éxito";
                json.addProperty("success", true);
                json.addProperty("status", "success");
            } else {
                json.addProperty("success", false);
                json.addProperty("status", "failure");
            }
            json.addProperty("message", message);
            JSONResponse.writeFromServlet(response, json);
        }
    }

}