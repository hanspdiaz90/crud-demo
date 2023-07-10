package pe.edu.unprg.javaee.cruddemo.controller;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import pe.edu.unprg.javaee.cruddemo.model.Author;
import pe.edu.unprg.javaee.cruddemo.service.AuthorService;
import pe.edu.unprg.javaee.cruddemo.service.impl.AuthorServiceImpl;
import pe.edu.unprg.javaee.cruddemo.utils.JSONResponse;
import pe.edu.unprg.javaee.cruddemo.utils.LocalDateTypeAdapter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet(name = "authorServlet", urlPatterns = "/admincrud/autores")
public class AuthorServlet extends HttpServlet {

    private final AuthorService authorService = new AuthorServiceImpl();
    private static final String VIEW_TEMPLATE_PATH = "/WEB-INF/views/authors/index.jsp";
    private final Gson gson = new GsonBuilder()
            .serializeNulls()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
            .create();

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

    private void mainAction(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setAttribute("cardTitle", "Listado de autores");
        RequestDispatcher dispatcher = request.getRequestDispatcher(VIEW_TEMPLATE_PATH);
        dispatcher.forward(request, response);
    }

    private void createAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getParameter("firstName") != null &&
                request.getParameter("lastName") != null &&
                request.getParameter("city") != null &&
                request.getParameter("dob") != null) {
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String city = request.getParameter("city");
            String dob = request.getParameter("dob");
            Author savedAuthor = new Author();
            savedAuthor.setFirstName(firstName);
            savedAuthor.setLastName(lastName);
            savedAuthor.setCity(city);
            savedAuthor.setDob(LocalDate.parse(dob, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            boolean created = authorService.createAuthor(savedAuthor);
            JsonObject json = new JsonObject();
            String message = null;
            if (created) {
                message = "El autor ha sido registrado con éxito";
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
        if (request.getParameter("authorId") != null &&
                request.getParameter("firstName") != null &&
                request.getParameter("lastName") != null &&
                request.getParameter("city") != null &&
                request.getParameter("dob") != null &&
                request.getParameter("active") != null) {
            Integer authorId = Integer.parseInt(request.getParameter("authorId"));
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String city = request.getParameter("city");
            String dob = request.getParameter("dob");
            boolean active = !request.getParameter("active").isEmpty();
            Author updatedAuthor = new Author();
            updatedAuthor.setAuthorId(authorId);
            updatedAuthor.setFirstName(firstName);
            updatedAuthor.setLastName(lastName);
            updatedAuthor.setCity(city);
            updatedAuthor.setDob(LocalDate.parse(dob, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            updatedAuthor.setActive(active);
            boolean updated = authorService.editAuthor(updatedAuthor);
            JsonObject json = new JsonObject();
            String message = null;
            if (updated) {
                message = "Se actualizaron los datos del autor con éxito";
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
        if (request.getParameter("authorId") != null) {
            int authorId = Integer.parseInt(request.getParameter("authorId"));
            Author foundAuthor = authorService.findByAuthorId(authorId);
            JsonObject json = new JsonObject();
            JsonElement result = null;
            if (foundAuthor != null) {
                Type authorType = new TypeToken<Author>(){}.getType();
                result = this.gson.toJsonTree(foundAuthor, authorType);
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
        List<Author> authorsList = authorService.findAll();
        if (authorsList != null) {
            Type authorType = new TypeToken<List<Author>>(){}.getType();
            JsonElement result = this.gson.toJsonTree(authorsList, authorType);
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
        if (request.getParameter("authorId") != null) {
            int authorId = Integer.parseInt(request.getParameter("authorId"));
            boolean disabled = authorService.disableByAuthorId(authorId);
            JsonObject json = new JsonObject();
            String message = null;
            if (disabled) {
                message = "El autor ha sido deshabilitado con éxito";
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

//    private void changeAuthorStatusAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        boolean ok = false;
//        String message = null;
//        JsonObject jsonResponse = new JsonObject();
//        try {
//            if (request.getParameter("id") != null) {
//                int id = Integer.parseInt(request.getParameter("id"));
//                boolean success = authorService.changeStatusById(id);
//                if (success) {
//                    ok = true;
//                    message = "La operación se realizó con éxito";
//                }
//            }
//            jsonResponse.addProperty("status", ok ? "success" : "error");
//            jsonResponse.addProperty("message", message);
//            response.setContentType("application/json");
//            response.setCharacterEncoding("UTF-8");
//            response.getWriter().print(jsonResponse.toString());
//        } catch (ServiceException ex) {
//            response.setContentType("text/html");
//            response.getWriter().print(ex.getMessage());
//        }
//    }

}