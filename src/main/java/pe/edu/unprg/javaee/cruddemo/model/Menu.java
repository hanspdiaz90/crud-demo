package pe.edu.unprg.javaee.cruddemo.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Menu {

    private int menuId;
    private String title;
    private String module;
    private String icon;
    private String route;
    private int parentId;
    private int sortOrder;
    private boolean active;
    private List<Menu> children = new ArrayList<>();

}