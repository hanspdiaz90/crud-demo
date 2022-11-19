package pe.edu.unprg.javaee.cruddemo.controller;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import pe.edu.unprg.javaee.cruddemo.model.Publisher;
import pe.edu.unprg.javaee.cruddemo.service.PublisherService;
import pe.edu.unprg.javaee.cruddemo.service.impl.PublisherServiceImpl;
import pe.edu.unprg.javaee.cruddemo.utils.JSONResponse;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

@WebServlet(name = "publisherServlet", urlPatterns = "/biblioteca/editoriales")
public class PublisherServlet extends HttpServlet {

    private final PublisherService publisherService = new PublisherServiceImpl();
    private static final String VIEW_TEMPLATE_PATH = "/WEB-INF/views/publishers/index.jsp";
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
                createPublisherAction(request, response);
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
                mainAction(request, response);
                break;
        }
    }

    private void mainAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("cardTitle", "Listado de editoriales");
        RequestDispatcher dispatcher = request.getRequestDispatcher(VIEW_TEMPLATE_PATH);
        dispatcher.forward(request, response);
    }

    private void createPublisherAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getParameter("name") != null &&
                request.getParameter("address") != null &&
                request.getParameter("email") != null &&
                request.getParameter("webSite") != null &&
                request.getParameter("phone") != null &&
                request.getParameter("cellphone") != null) {
            String name = request.getParameter("name");
            String address = request.getParameter("address");
            String email = request.getParameter("email");
            String webSite = request.getParameter("webSite");
            String phone = request.getParameter("phone");
            String cellphone = request.getParameter("cellphone");
            Publisher savedPublisher = new Publisher();
            savedPublisher.setName(name);
            savedPublisher.setAddress(address);
            savedPublisher.setEmail(email);
            savedPublisher.setWebSite(webSite);
            savedPublisher.setPhone(phone);
            savedPublisher.setCellphone(cellphone);
            boolean created = publisherService.createPublisher(savedPublisher);
            JsonObject json = new JsonObject();
            String message = null;
            if (created) {
                message = "La editorial ha sido registrado con éxito";
                json.addProperty("status", "success");
            } else {
                json.addProperty("status", "error");
            }
            json.addProperty("message", message);
            JSONResponse.writeFromServlet(response, json);
        }
    }

    private void moreDetailsPublisherAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getParameter("publisherId") != null) {
            int publisherId = Integer.parseInt(request.getParameter("publisherId"));
            Publisher foundPublisher = publisherService.findByPublisherId(publisherId);
            JsonObject json = new JsonObject();
            JsonElement result = null;
            if (foundPublisher != null) {
                result = this.gson.toJsonTree(foundPublisher);
                json.addProperty("status", "success");
            } else {
                json.addProperty("status", "error");
            }
            json.add("result", result);
            JSONResponse.writeFromServlet(response, json);
        }
    }

    private void publishersListAction(HttpServletResponse response) throws IOException {
        JsonObject json = new JsonObject();
        JsonArray data = null;
        List<Publisher> publishersList = publisherService.findAll();
        if (publishersList != null) {
            Type publisherType = new TypeToken<List<Publisher>>(){}.getType();
            JsonElement result = this.gson.toJsonTree(publishersList, publisherType);
            data = result.getAsJsonArray();
            json.addProperty("status", "success");
        } else {
            json.addProperty("status", "error");
        }
        json.add("result", data);
        JSONResponse.writeFromServlet(response, json);
    }

    private void disablePublisherAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getParameter("publisherId") != null) {
            int publisherId = Integer.parseInt(request.getParameter("publisherId"));
            boolean disabled = publisherService.disableByPublisherId(publisherId);
            JsonObject json = new JsonObject();
            String message = null;
            if (disabled) {
                message = "La editorial ha sido deshabilitado con éxito";
                json.addProperty("status", "success");
            } else {
                json.addProperty("status", "error");
            }
            json.addProperty("message", message);
            JSONResponse.writeFromServlet(response, json);
        }
    }

}