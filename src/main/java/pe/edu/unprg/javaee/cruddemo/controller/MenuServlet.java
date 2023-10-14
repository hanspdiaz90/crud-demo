package pe.edu.unprg.javaee.cruddemo.controller;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import pe.edu.unprg.javaee.cruddemo.model.Menu;
import pe.edu.unprg.javaee.cruddemo.model.Module;
import pe.edu.unprg.javaee.cruddemo.service.MenuService;
import pe.edu.unprg.javaee.cruddemo.service.impl.MenuServiceImpl;
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

@WebServlet(name = "menuServlet", urlPatterns = "/admincrud/menu-navegacion")
public class MenuServlet extends HttpServlet {

    private final MenuService menuService = new MenuServiceImpl();
    private static final String VIEW_TEMPLATE_PATH = "/WEB-INF/views/nav-menu/index.jsp";
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
            case "findActiveModules":
                this.findActiveModulesAction(response);
                break;
            case "findMenusWithRoute":
                this.findActiveModulesAction(response);
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
        request.setAttribute("cardTitle", "Listado de menú");
        request.setAttribute("formTitle", "Añadir nuevo menú");
        RequestDispatcher dispatcher = request.getRequestDispatcher(VIEW_TEMPLATE_PATH);
        dispatcher.forward(request, response);
    }

    private void createAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getParameter("title") != null &&
                request.getParameter("icon") != null &&
                request.getParameter("module") != null) {
            String title = request.getParameter("title");
            String icon = request.getParameter("icon");
            String route = !request.getParameter("route").isEmpty() ? request.getParameter("route") : null;
            Integer moduleId = Integer.parseInt(request.getParameter("module"));
            Integer parentId = request.getParameter("parent") != null ? Integer.parseInt(request.getParameter("parent")) : null;
            Menu savedMenu = new Menu();
            savedMenu.setTitle(title);
            savedMenu.setIcon(icon);
            savedMenu.setRoute(route);
            savedMenu.setModule(new Module());
            savedMenu.getModule().setModuleId(moduleId);
            savedMenu.setParentId(parentId);
            boolean created = this.menuService.createMenu(savedMenu);
            JsonObject json = new JsonObject();
            String message = null;
            if (created) {
                message = "El menú ha sido registrado con éxito";
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
        /*
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
            boolean created = moduleService.editModule(updatedModule);
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
         */
    }

    private void findByIdAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getParameter("menuId") != null) {
            int moduleId = Integer.parseInt(request.getParameter("menuId"));
            Menu foundMenu = this.menuService.findByMenuId(moduleId);
            JsonObject json = new JsonObject();
            JsonElement result = null;
            if (foundMenu != null) {
                Type menuType = new TypeToken<Menu>(){}.getType();
                result = this.gson.toJsonTree(foundMenu, menuType);
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
        List<Menu> menuList = this.menuService.findAll();
        if (menuList != null) {
            Type menuType = new TypeToken<List<Menu>>(){}.getType();
            JsonElement result = this.gson.toJsonTree(menuList, menuType);
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

    private void findActiveModulesAction(HttpServletResponse response) throws IOException {
        JsonObject json = new JsonObject();
        JsonArray data = null;
        List<Module> activeModules = this.menuService.findActiveModules();
        if (activeModules != null) {
            Type moduleType = new TypeToken<List<Module>>(){}.getType();
            JsonElement result = this.gson.toJsonTree(activeModules, moduleType);
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
        if (request.getParameter("menuId") != null) {
            int moduleId = Integer.parseInt(request.getParameter("menuId"));
            boolean disabled = this.menuService.disableByMenuId(moduleId);
            JsonObject json = new JsonObject();
            String message = null;
            if (disabled) {
                message = "El menú ha sido desactivado con éxito";
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