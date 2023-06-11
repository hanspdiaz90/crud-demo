package pe.edu.unprg.javaee.cruddemo.controller;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import pe.edu.unprg.javaee.cruddemo.model.MenuPermission;
import pe.edu.unprg.javaee.cruddemo.service.MenuPermissionService;
import pe.edu.unprg.javaee.cruddemo.service.impl.MenuPermissionServiceImpl;
import pe.edu.unprg.javaee.cruddemo.utils.JSONResponse;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

@WebServlet(name = "menuPermissionServlet", urlPatterns = "/admincrud/menu-permisos")
public class MenuPermissionServlet extends HttpServlet {

    private final MenuPermissionService menuPermissionService = new MenuPermissionServiceImpl();
    private final Gson gson = new GsonBuilder()
            .serializeNulls()
            .setPrettyPrinting()
            .create();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("accion") == null ? "index" : request.getParameter("accion");
        switch (action) {
            case "listarMenuNavegacion":
                listNavigationMenuByRoleAction(request, response);
                break;
        }
    }

    private void listNavigationMenuByRoleAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonObject json = new JsonObject();
        JsonArray data = null;
        Integer roleId = Integer.parseInt(request.getParameter("roleId"));
        List<MenuPermission> menus = menuPermissionService.findAllMenuPermissionByRole(roleId);
        if (menus != null) {
            Type menuPermissionType = new TypeToken<List<MenuPermission>>(){}.getType();
            JsonElement result = this.gson.toJsonTree(menus, menuPermissionType);
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

}