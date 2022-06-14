package pe.edu.unprg.javaee.inventariolibros.utils;

import java.sql.Date;
import java.time.LocalDate;

public class JdbcUtils {

    private JdbcUtils() {}

    public static Date getSQLDate(LocalDate localDate) {
        return Date.valueOf(localDate);
    }

    public static LocalDate getLocalDate(Date sqlDate) {
        return sqlDate.toLocalDate();
    }

}