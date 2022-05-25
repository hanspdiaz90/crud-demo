package pe.edu.unprg.javaee.inventariolibros.models;

public class Author {

    private int id;
    private String nombres;
    private String apellidos;
    private String ciudad;
    private boolean activo;

    public Author() {
    }

    public Author(String nombres, String apellidos, String ciudad) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.ciudad = ciudad;
        this.activo = true;
    }

    public Author(int id, String nombres, String apellidos, String ciudad, boolean activo) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.ciudad = ciudad;
        this.activo = activo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

}