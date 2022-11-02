package pe.edu.unprg.javaee.inventariolibros.utils;

import java.time.LocalDate;

public class JdbcUtils {

    private JdbcUtils() {}
    public static java.sql.Date toSQLDate(LocalDate localDate) {
        return java.sql.Date.valueOf(localDate);
    }
    public static LocalDate toLocalDate(java.sql.Date sqlDate) {
        return sqlDate.toLocalDate();
    }

}