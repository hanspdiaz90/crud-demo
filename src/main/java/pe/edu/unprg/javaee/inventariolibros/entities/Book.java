package pe.edu.unprg.javaee.inventariolibros.entities;

public class Book {

    private int id;
    private String isbn;
    private String titulo;
    private String descripcion;
    private int existencias;
    private double precio;
    private Author autor;
    private Publisher editorial;
    private Genre genero;
    private boolean activo;

    public Book() {
    }

    public Book(String isbn, String titulo, String descripcion, int existencias, double precio, Author autor, Publisher editorial, Genre genero) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.existencias = existencias;
        this.precio = precio;
        this.autor = autor;
        this.editorial = editorial;
        this.genero = genero;
        this.activo = true;
    }

    public Book(int id, String isbn, String titulo, String descripcion, int existencias, double precio, Author autor, Publisher editorial, Genre genero, boolean activo) {
        this.id = id;
        this.isbn = isbn;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.existencias = existencias;
        this.precio = precio;
        this.autor = autor;
        this.editorial = editorial;
        this.genero = genero;
        this.activo = activo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getISBN() {
        return isbn;
    }

    public void setISBN(String isbn) {
        this.isbn = isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getExistencias() {
        return existencias;
    }

    public void setExistencias(int existencias) {
        this.existencias = existencias;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Author getAutor() {
        return autor;
    }

    public void setAutor(Author autor) {
        this.autor = autor;
    }

    public Publisher getEditorial() {
        return editorial;
    }

    public void setEditorial(Publisher editorial) {
        this.editorial = editorial;
    }

    public Genre getGenero() {
        return genero;
    }

    public void setGenero(Genre genero) {
        this.genero = genero;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

}