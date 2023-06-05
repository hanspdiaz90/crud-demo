package pe.edu.unprg.javaee.cruddemo.model;

import lombok.Data;

@Data
public class Module {

    private Integer moduleId;
    private String title;
    private String description;
    private boolean active;

}