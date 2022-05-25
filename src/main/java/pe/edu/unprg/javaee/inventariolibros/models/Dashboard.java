package pe.edu.unprg.javaee.inventariolibros.models;

public class Dashboard {

    private int totalAutores;
    private int totalEditoriales;
    private int totalGeneros;
    private int totalLibros;

    public Dashboard() {
    }

    public Dashboard(int totalAutores, int totalEditoriales, int totalGeneros, int totalLibros) {
        this.totalAutores = totalAutores;
        this.totalEditoriales = totalEditoriales;
        this.totalGeneros = totalGeneros;
        this.totalLibros = totalLibros;
    }

    public int getTotalAutores() {
        return totalAutores;
    }

    public void setTotalAutores(int totalAutores) {
        this.totalAutores = totalAutores;
    }

    public int getTotalEditoriales() {
        return totalEditoriales;
    }

    public void setTotalEditoriales(int totalEditoriales) {
        this.totalEditoriales = totalEditoriales;
    }

    public int getTotalGeneros() {
        return totalGeneros;
    }

    public void setTotalGeneros(int totalGeneros) {
        this.totalGeneros = totalGeneros;
    }

    public int getTotalLibros() {
        return totalLibros;
    }

    public void setTotalLibros(int totalLibros) {
        this.totalLibros = totalLibros;
    }

}