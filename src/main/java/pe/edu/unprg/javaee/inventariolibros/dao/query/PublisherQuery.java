package pe.edu.unprg.javaee.inventariolibros.dao.query;

public class PublisherQuery {

    private PublisherQuery() {}

    public static final String SP_INSERT_PUBLISHER = "call sp_insertar_editorial(?, ?, ?)";
    public static final String SP_UPDATE_PUBLISHER = "call sp_actualizar_editorial(?, ?, ?, ?, ?)";
    public static final String SP_FIND_PUBLISHER_BY_ID = "call sp_buscar_editorial(?)";
    public static final String SP_FIND_ALL_PUBLISHER = "call sp_listar_todos_editoriales()";
    public static final String SP_DEACTIVATE_PUBLISHER_BY_ID = "call sp_desactivar_editorial(?)";

}