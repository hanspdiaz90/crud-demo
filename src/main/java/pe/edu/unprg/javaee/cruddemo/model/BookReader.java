package pe.edu.unprg.javaee.cruddemo.model;

import lombok.Data;
import pe.edu.unprg.javaee.cruddemo.utils.enums.ReaderType;
import pe.edu.unprg.javaee.cruddemo.utils.enums.ReaderGender;

@Data
public class BookReader {

    private int userId;
    private String dni;
    private String firstName;
    private String paternalSurname;
    private String maternalSurname;
    private String email;
    ReaderType readerType;
    ReaderGender readerGender;
    private String cellphone;
    private String phone;
    private boolean active;

}