package pe.edu.unprg.javaee.inventariolibros.service;

import pe.edu.unprg.javaee.inventariolibros.model.Author;

import java.util.List;

public interface AuthorService {

    boolean createAuthor(Author author);
    boolean editAuthor(Author author);
    Author findByAuthorId(int authorId);
    List<Author> findAll();
    boolean disableByAuthorId(int authorId);
//    boolean changeStatusById(int id) throws ServiceException;

}