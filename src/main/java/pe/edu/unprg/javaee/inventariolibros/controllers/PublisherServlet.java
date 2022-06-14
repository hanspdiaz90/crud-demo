package pe.edu.unprg.javaee.inventariolibros.controllers;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import pe.edu.unprg.javaee.inventariolibros.models.Publisher;
import pe.edu.unprg.javaee.inventariolibros.exception.ServiceException;
import pe.edu.unprg.javaee.inventariolibros.services.IPublisherService;
import pe.edu.unprg.javaee.inventariolibros.services.factory.ServiceFactory;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.List;

@WebServlet(name = "publisherServlet", urlPatterns = "/biblioteca/editoriales")
public class PublisherServlet extends HttpServlet {

    private static final String VIEW_TEMPLATE_PATH = "/WEB-INF/views/publishers/index.jsp";
    private final Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
    private final IPublisherService publisherService = ServiceFactory.getInstance().getPublisherService();

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
                    insertPublisherAction(request, response);
                    break;
                case "editar":
                    System.out.println("Próximo a implementarse =)");
                    break;
                case "verDetalles":
                    moreDetailsPublisherAction(request, response);
                    break;
                case "deshabilitar":
                    disablePublisherAction(request, response);
                    break;
                case "listar":
                    publishersListAction(response);
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
        request.setAttribute("cardTitle", "Listado de editoriales");
        RequestDispatcher dispatcher = request.getRequestDispatcher(VIEW_TEMPLATE_PATH);
        dispatcher.forward(request, response);
    }

    private void insertPublisherAction(HttpServletRequest request, HttpServletResponse response) throws ServiceException, IOException {
        if (request.getParameter("nombre") != null &&
                request.getParameter("direccion") != null &&
                request.getParameter("email") != null &&
                request.getParameter("paginaWeb") != null &&
                request.getParameter("nroTelefono") != null) {
            String nombre = request.getParameter("nombre");
            String direccion = request.getParameter("direccion");
            String email = request.getParameter("email");
            String paginaWeb = request.getParameter("paginaWeb");
            String nroTelefono = request.getParameter("nroTelefono");
            Publisher publisher = new Publisher();
            publisher.setNombre(nombre);
            publisher.setDireccion(direccion);
            publisher.setEmail(email);
            publisher.setPaginaWeb(paginaWeb);
            publisher.setNroTelefono(nroTelefono);
            boolean inserted = publisherService.insert(publisher);
            JsonObject json = new JsonObject();
            String message = null;
            if (inserted) {
                json.addProperty("status", "success");
                message = "La editorial ha sido registrado con éxito";
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

    private void moreDetailsPublisherAction(HttpServletRequest request, HttpServletResponse response) throws ServiceException, IOException {
        if (request.getParameter("id") != null) {
            int id = Integer.parseInt(request.getParameter("id"));
            Publisher found = publisherService.findById(id);
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

    private void publishersListAction(HttpServletResponse response) throws ServiceException, IOException {
        JsonObject json = new JsonObject();
        JsonArray data = null;
        List<Publisher> publisherList = publisherService.findAll();
        if (publisherList != null) {
            json.addProperty("status", "success");
            Type publisherListType = new TypeToken<List<Publisher>>(){}.getType();
            JsonElement result = gson.toJsonTree(publisherList, publisherListType);
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

    private void disablePublisherAction(HttpServletRequest request, HttpServletResponse response) throws ServiceException, IOException {
        if (request.getParameter("id") != null) {
            int id = Integer.parseInt(request.getParameter("id"));
            boolean disable = publisherService.disableById(id);
            JsonObject json = new JsonObject();
            String message = null;
            if (disable) {
                json.addProperty("status", "success");
                message = "La editorial ha sido deshabilitado con éxito";
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