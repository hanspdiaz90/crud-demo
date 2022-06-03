package pe.edu.unprg.javaee.inventariolibros.dao.query;

public class PublisherQuery {

    private PublisherQuery() {}

    public static final String SP_INSERT_PUBLISHER = "call sp_insert_publisher(?, ?, ?, ?, ?)";
    public static final String SP_UPDATE_PUBLISHER = "call sp_update_publisher(?, ?, ?, ?, ?, ?, ?)";
    public static final String SP_FIND_PUBLISHER_BY_ID = "call sp_find_publisher_by_id(?)";
    public static final String SP_FIND_ALL_PUBLISHERS = "call sp_find_all_publishers()";
    public static final String SP_DISABLE_PUBLISHER_BY_ID = "call sp_disable_publisher_by_id(?)";

}