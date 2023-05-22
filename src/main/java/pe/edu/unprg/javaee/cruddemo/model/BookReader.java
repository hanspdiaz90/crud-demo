package pe.edu.unprg.javaee.cruddemo.model;

import lombok.Data;
import pe.edu.unprg.javaee.cruddemo.utils.enums.BookReaderType;
import pe.edu.unprg.javaee.cruddemo.utils.enums.Gender;

@Data
public class BookReader {

    private Integer userId;
    private String nic;
    private String firstName;
    private String paternalSurname;
    private String maternalSurname;
    private String email;
    BookReaderType readerType;
    Gender gender;
    private String cellphone;
    private String phone;
    private boolean active;

}