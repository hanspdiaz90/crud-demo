package pe.edu.unprg.javaee.cruddemo.controller;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import pe.edu.unprg.javaee.cruddemo.model.Module;
import pe.edu.unprg.javaee.cruddemo.service.ModuleService;
import pe.edu.unprg.javaee.cruddemo.service.impl.ModuleServiceImpl;
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

@WebServlet(name = "moduleServlet", urlPatterns = "/admincrud/modulos")
public class ModuleServlet extends HttpServlet {

    private final ModuleService moduleService = new ModuleServiceImpl();
    private static final String VIEW_TEMPLATE_PATH = "/WEB-INF/views/modules/index.jsp";
    private final Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action") == null ? "index" : request.getParameter("action");
        switch (action) {
            case "findById":
                this.findByIdAction(request, response);
                break;
            case "findAll":
                this.findAllAction(response);
                break;
            default:
                this.mainAction(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action") == null ? "index" : request.getParameter("action");
        switch (action) {
            case "create":
                this.createAction(request, response);
                break;
            case "update":
                this.updateAction(request, response);
                break;
            case "disableById":
                this.disableByIdAction(request, response);
                break;
            default:
                this.mainAction(request, response);
                break;
        }
    }

    private void mainAction(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setAttribute("cardTitle", "Listado de módulos");
        request.setAttribute("formTitle", "Añadir nuevo módulo");
        RequestDispatcher dispatcher = request.getRequestDispatcher(VIEW_TEMPLATE_PATH);
        dispatcher.forward(request, response);
    }

    private void createAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getParameter("title") != null) {
            String title = request.getParameter("title");
            String description = !request.getParameter("description").isEmpty() ? request.getParameter("description") : null;
            Module savedModule = new Module();
            savedModule.setTitle(title);
            savedModule.setDescription(description);
            boolean created = this.moduleService.createModule(savedModule);
            JsonObject json = new JsonObject();
            String message = null;
            if (created) {
                message = "El módulo ha sido registrado con éxito";
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
        if (request.getParameter("moduleId") != null &&
                request.getParameter("title") != null) {
            Integer moduleId = Integer.parseInt(request.getParameter("moduleId"));
            String title = request.getParameter("title");
            String description = !request.getParameter("description").isEmpty() ? request.getParameter("description") : null;
            boolean active = request.getParameter("isActive") != null && request.getParameter("isActive").equals("on");
            Module updatedModule = new Module();
            updatedModule.setModuleId(moduleId);
            updatedModule.setTitle(title);
            updatedModule.setDescription(description);
            updatedModule.setActive(active);
            boolean created = this.moduleService.editModule(updatedModule);
            JsonObject json = new JsonObject();
            String message = null;
            if (created) {
                message = "Se actualizaron los datos del módulo con éxito";
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
        if (request.getParameter("moduleId") != null) {
            Integer moduleId = Integer.parseInt(request.getParameter("moduleId"));
            Module foundModule = this.moduleService.findByModuleId(moduleId);
            JsonObject json = new JsonObject();
            JsonElement result = null;
            if (foundModule != null) {
                Type moduleType = new TypeToken<Module>(){}.getType();
                result = this.gson.toJsonTree(foundModule, moduleType);
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
        List<Module> modulesList = this.moduleService.findAll();
        if (modulesList != null) {
            Type moduleType = new TypeToken<List<Module>>(){}.getType();
            JsonElement result = this.gson.toJsonTree(modulesList, moduleType);
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
        if (request.getParameter("moduleId") != null) {
            Integer moduleId = Integer.parseInt(request.getParameter("moduleId"));
            boolean disabled = this.moduleService.disableByModuleId(moduleId);
            JsonObject json = new JsonObject();
            String message = null;
            if (disabled) {
                message = "El módulo ha sido desactivado con éxito";
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