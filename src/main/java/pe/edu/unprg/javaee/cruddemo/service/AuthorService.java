package pe.edu.unprg.javaee.cruddemo.service;

import pe.edu.unprg.javaee.cruddemo.model.Author;

import java.util.List;

public interface AuthorService {

    boolean createAuthor(Author author);
    boolean editAuthor(Author author);
    Author findByAuthorId(int authorId);
    List<Author> findAll();
    boolean disableByAuthorId(int authorId);
//    boolean changeStatusById(int id) throws ServiceException;

}