package pe.edu.unprg.javaee.inventariolibros.dao.query;

public class PublisherQuery {

    private PublisherQuery() {}

    public static final String SP_INSERT = "call sp_insertar_editorial(?, ?, ?)";
    public static final String SP_UPDATE = "call sp_actualizar_editorial(?, ?, ?, ?, ?)";
    public static final String SP_FIND_BY_ID = "call sp_buscar_editorial(?)";
    public static final String SP_FIND_ALL = "call sp_listar_todos_editoriales()";
    public static final String SP_DEACTIVATE_BY_ID = "call sp_desactivar_editorial(?)";

}