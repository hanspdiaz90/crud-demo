package pe.edu.unprg.javaee.inventariolibros.model;

import lombok.Data;

@Data
public class Publisher {

    private int publisherId;
    private String publisher;
    private String address;
    private String email;
    private String webSite;
    private String phone;
    private String cellphone;
    private boolean active;

}