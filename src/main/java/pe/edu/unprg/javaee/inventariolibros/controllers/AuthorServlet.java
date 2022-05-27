package pe.edu.unprg.javaee.inventariolibros.controllers;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import pe.edu.unprg.javaee.inventariolibros.models.Author;
import pe.edu.unprg.javaee.inventariolibros.exception.ServiceException;
import pe.edu.unprg.javaee.inventariolibros.services.IAuthorService;
import pe.edu.unprg.javaee.inventariolibros.services.factory.ServiceFactory;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.List;

@WebServlet(name = "authorServlet", urlPatterns = "/biblioteca/autores")
public class AuthorServlet extends HttpServlet {

    private static final String PATH_AUTORES = "/WEB-INF/views/authors/index.jsp";
    private final Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
    private final IAuthorService authorService = ServiceFactory.getInstance().getAuthorService();

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
                    insertAuthorAction(request, response);
                    break;
                case "editar":
                    System.out.println("Próximo a implementarse =)");
                    break;
                case "verDetalles":
                    moreDetailsAuthonAction(request, response);
                    break;
                case "deshabilitar":
                    disableAuthorAction(request, response);
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

    private void indexAction(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setAttribute("cardTitle", "Listado de autores");
        RequestDispatcher dispatcher = request.getRequestDispatcher(PATH_AUTORES);
        dispatcher.forward(request, response);
    }

    private void insertAuthorAction(HttpServletRequest request, HttpServletResponse response) throws ServiceException, IOException {
        if (request.getParameter("nombres") != null &&
                request.getParameter("apellidos") != null &&
                request.getParameter("ciudad") != null) {
            String nombres = request.getParameter("nombres");
            String apellidos = request.getParameter("apellidos");
            String ciudad = request.getParameter("ciudad");
            Author author = new Author();
            author.setNombres(nombres);
            author.setApellidos(apellidos);
            author.setCiudad(ciudad);
            author.setActivo(true);
            boolean inserted = authorService.insert(author);
            JsonObject json = new JsonObject();
            String message = null;
            if (inserted) {
                json.addProperty("status", "success");
                message = "El autor ha sido registrado con éxito";
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

    private void moreDetailsAuthonAction(HttpServletRequest request, HttpServletResponse response) throws ServiceException, IOException {
        if (request.getParameter("id") != null) {
            int id = Integer.parseInt(request.getParameter("id"));
            Author found = authorService.findById(id);
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

    private void authorsListAction(HttpServletResponse response) throws ServiceException, IOException {
        JsonObject json = new JsonObject();
        JsonArray data = null;
        List<Author> authorList = authorService.findAll();
        if (authorList != null) {
            json.addProperty("status", "success");
            Type typeAuthor = new TypeToken<List<Author>>(){}.getType();
            JsonElement result = gson.toJsonTree(authorList, typeAuthor);
            data = result.getAsJsonArray();
        } else {
            json.addProperty("status", "error");
        }
        json.add("result", data);
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(json.toString());
        out.flush();
    }

    private void disableAuthorAction(HttpServletRequest request, HttpServletResponse response) throws ServiceException, IOException {
        if (request.getParameter("id") != null) {
            int id = Integer.parseInt(request.getParameter("id"));
            boolean disabled = authorService.disableById(id);
            JsonObject json = new JsonObject();
            String message = null;
            if (disabled) {
                json.addProperty("status", "success");
                message = "El autor ha sido deshabilitado con éxito";
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