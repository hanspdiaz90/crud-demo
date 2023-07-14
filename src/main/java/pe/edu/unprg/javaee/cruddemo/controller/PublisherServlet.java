package pe.edu.unprg.javaee.cruddemo.controller;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import pe.edu.unprg.javaee.cruddemo.model.Publisher;
import pe.edu.unprg.javaee.cruddemo.service.PublisherService;
import pe.edu.unprg.javaee.cruddemo.service.impl.PublisherServiceImpl;
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

@WebServlet(name = "publisherServlet", urlPatterns = "/admincrud/editoriales")
public class PublisherServlet extends HttpServlet {

    private final PublisherService publisherService = new PublisherServiceImpl();
    private static final String VIEW_TEMPLATE_PATH = "/WEB-INF/views/publishers/index.jsp";
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
        request.setAttribute("cardTitle", "Listado de editoriales");
        RequestDispatcher dispatcher = request.getRequestDispatcher(VIEW_TEMPLATE_PATH);
        dispatcher.forward(request, response);
    }

    private void createAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getParameter("name") != null &&
                request.getParameter("email") != null &&
                request.getParameter("address") != null) {
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String address = request.getParameter("address");
            String phone = !request.getParameter("phone").isEmpty() ? request.getParameter("phone") : null;
            String cellphone = !request.getParameter("cellphone").isEmpty() ? request.getParameter("cellphone") : null;
            String webSite = !request.getParameter("webSite").isEmpty() ? request.getParameter("webSite") : null;
            Publisher savedPublisher = new Publisher();
            savedPublisher.setName(name);
            savedPublisher.setEmail(email);
            savedPublisher.setAddress(address);
            savedPublisher.setPhone(phone);
            savedPublisher.setCellphone(cellphone);
            savedPublisher.setWebSite(webSite);
            boolean created = publisherService.createPublisher(savedPublisher);
            JsonObject json = new JsonObject();
            String message = null;
            if (created) {
                message = "La editorial ha sido registrado con éxito";
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
        if (request.getParameter("publisherId") != null &&
                request.getParameter("name") != null &&
                request.getParameter("email") != null &&
                request.getParameter("address") != null) {
            Integer publisherId = Integer.parseInt(request.getParameter("publisherId"));
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String address = request.getParameter("address");
            String phone = !request.getParameter("phone").isEmpty() ? request.getParameter("phone") : null;
            String cellphone = !request.getParameter("cellphone").isEmpty() ? request.getParameter("cellphone") : null;
            String webSite = !request.getParameter("webSite").isEmpty() ? request.getParameter("webSite") : null;
            boolean active = request.getParameter("isActive") != null && request.getParameter("isActive").equals("on");
            Publisher updatePublisher = new Publisher();
            updatePublisher.setPublisherId(publisherId);
            updatePublisher.setName(name);
            updatePublisher.setEmail(email);
            updatePublisher.setAddress(address);
            updatePublisher.setPhone(phone);
            updatePublisher.setCellphone(cellphone);
            updatePublisher.setWebSite(webSite);
            updatePublisher.setActive(active);
            boolean updated = publisherService.editPublisher(updatePublisher);
            JsonObject json = new JsonObject();
            String message = null;
            if (updated) {
                message = "Se actualizaron los datos de la editorial con éxito";
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
        if (request.getParameter("publisherId") != null) {
            int publisherId = Integer.parseInt(request.getParameter("publisherId"));
            Publisher foundPublisher = publisherService.findByPublisherId(publisherId);
            JsonObject json = new JsonObject();
            JsonElement result = null;
            if (foundPublisher != null) {
                Type publisherType = new TypeToken<Publisher>(){}.getType();
                result = this.gson.toJsonTree(foundPublisher, publisherType);
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
        List<Publisher> publishersList = publisherService.findAll();
        if (publishersList != null) {
            Type publisherType = new TypeToken<List<Publisher>>(){}.getType();
            JsonElement result = this.gson.toJsonTree(publishersList, publisherType);
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
        if (request.getParameter("publisherId") != null) {
            int publisherId = Integer.parseInt(request.getParameter("publisherId"));
            boolean disabled = publisherService.disableByPublisherId(publisherId);
            JsonObject json = new JsonObject();
            String message = null;
            if (disabled) {
                message = "La editorial ha sido deshabilitado con éxito";
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