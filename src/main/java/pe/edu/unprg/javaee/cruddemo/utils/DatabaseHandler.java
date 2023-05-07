package pe.edu.unprg.javaee.cruddemo.utils;

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
    private static final String DATABASE_RESOURCE = "setting/database-cloud.properties";

    private DatabaseHandler() {
        try {
            if (dataSource == null) {
                //InputStream resource = Thread.currentThread().getContextClassLoader().getResourceAsStream(DATABASE_RESOURCE);
                //InputStream resource = new FileInputStream("resources/" + DATABASE_RESOURCE);
                InputStream resource = this.getClass().getClassLoader().getResourceAsStream(DATABASE_RESOURCE);
                Properties props = new Properties();
                props.load(resource);
                BasicDataSource bds = new BasicDataSource();
                bds.setDriverClassName(props.getProperty("db.driverClassName"));
                bds.setUsername(props.getProperty("db.username"));
                bds.setPassword(props.getProperty("db.password"));
                bds.setUrl(props.getProperty("db.url"));
                bds.setMinIdle(5);
                bds.setMaxIdle(12);
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