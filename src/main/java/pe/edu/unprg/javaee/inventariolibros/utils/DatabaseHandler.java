package pe.edu.unprg.javaee.inventariolibros.utils;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseHandler {

    private static DatabaseHandler instance = null;
    private static DataSource dataSource = null;
    private static final String RESOURCE = "/database/settings.properties";

    private DatabaseHandler() {
        try {
            if (dataSource == null) {
                InputStream fis = DatabaseHandler.class.getClassLoader().getResourceAsStream(RESOURCE);
                Properties props = new Properties();
                props.load(fis);
                BasicDataSource bds = new BasicDataSource();
                bds.setDriverClassName(props.getProperty("datasource.driverClassName"));
                bds.setUsername(props.getProperty("datasource.username"));
                bds.setPassword(props.getProperty("datasource.password"));
                bds.setUrl(props.getProperty("datasource.url"));
                bds.setMinIdle(5);
                bds.setMaxIdle(20);
                bds.setMaxWaitMillis(10000);
                dataSource = bds;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static DatabaseHandler getInstance() {
        if (instance == null) {
            instance = new DatabaseHandler();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

}