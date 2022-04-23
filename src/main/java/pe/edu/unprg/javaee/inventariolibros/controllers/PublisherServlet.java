package pe.edu.unprg.javaee.inventariolibros.controllers;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import pe.edu.unprg.javaee.inventariolibros.entities.Publisher;
import pe.edu.unprg.javaee.inventariolibros.exception.ServiceException;
import pe.edu.unprg.javaee.inventariolibros.services.IPublisherService;
import pe.edu.unprg.javaee.inventariolibros.services.factory.ServiceFactory;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "editorialServlet", urlPatterns = "/biblioteca/editoriales")
public class PublisherServlet extends HttpServlet {

    private static final String PATH_EDITORIALES = "/WEB-INF/views/publishers/index.jsp";
    private final Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
    private final IPublisherService editorialService = ServiceFactory.getInstance().getPublisherService();

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
                    insertPublisherAction(request, response);
                    break;
                case "actualizar":
                    System.out.println("Próximo a implementarse...");
                    break;
                case "buscar":
                    searchPublisherByIdAction(request, response);
                    break;
                case "desactivar":
                    deactivatePublisherAction(request, response);
                    break;
                case "listar":
                    publishersListAction(response);
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
        RequestDispatcher dispatcher = request.getRequestDispatcher(PATH_EDITORIALES);
        dispatcher.forward(request, response);
    }

    private void insertPublisherAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
        boolean ok = false;
        String message = null;
        JsonObject jsonResponse = new JsonObject();
        try {
            String nombre = request.getParameter("nombre");
            String email = request.getParameter("email");
            String telefono = request.getParameter("telefono");
            if (nombre.isEmpty() || email.isEmpty() || telefono.isEmpty()) {
                message = "No se registraron los datos del editorial";
            } else {
                Publisher editorial = new Publisher(nombre, email, telefono);
                boolean exito = editorialService.insert(editorial);
                if (exito) {
                    ok = true;
                    message = "Los datos de la editorial se registraron con éxito";
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

    private void searchPublisherByIdAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
        boolean ok = false;
        JsonElement result = null;
        JsonObject jsonResponse = new JsonObject();
        try {
            String id = request.getParameter("id");
            Publisher editorial = editorialService.findById(Integer.parseInt(id));
            if (editorial != null) {
                ok = true;
                result = gson.toJsonTree(editorial);
            }
            jsonResponse.addProperty("ok", ok);
            jsonResponse.add("result", result);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(jsonResponse.toString());
        } catch (Exception ex) {
            response.setContentType("text/html");
            response.getWriter().print(ex.getMessage());
        }
    }

    private void publishersListAction(HttpServletResponse response) throws IOException {
        boolean ok = false;
        JsonArray result = null;
        JsonObject jsonResponse = new JsonObject();
        try {
            List<Publisher> listaEditoriales = editorialService.findAll();
            if (listaEditoriales != null) {
                ok = true;
                JsonElement items = gson.toJsonTree(listaEditoriales, new TypeToken<List<Publisher>>(){}.getType());
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

    private void deactivatePublisherAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
        boolean ok = false;
        String message = null;
        JsonObject jsonResponse = new JsonObject();
        try {
            String id = request.getParameter("id");
            boolean exito = editorialService.deactivateById(Integer.parseInt(id));
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