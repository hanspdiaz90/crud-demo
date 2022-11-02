package pe.edu.unprg.javaee.inventariolibros.service.impl;

import pe.edu.unprg.javaee.inventariolibros.dao.AuthorDAO;
import pe.edu.unprg.javaee.inventariolibros.dao.impl.AuthorDAOImpl;
import pe.edu.unprg.javaee.inventariolibros.model.Author;
import pe.edu.unprg.javaee.inventariolibros.exception.DAOException;
import pe.edu.unprg.javaee.inventariolibros.service.AuthorService;

import java.util.List;
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDAO authorDAO = new AuthorDAOImpl();

    @Override
    public boolean createAuthor(Author author) {
        boolean result;
        try {
            result =  authorDAO.createAuthor(author);
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public boolean editAuthor(Author author) {
        boolean result;
        try {
            result =  authorDAO.editAuthor(author);
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public Author findByAuthorId(int authorId) {
        Author result;
        try {
            result = authorDAO.findByAuthorId(authorId);
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public List<Author> findAll() {
        List<Author> result;
        try {
            result = authorDAO.findAll();
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public boolean disableByAuthorId(int authorId) {
        boolean result;
        try {
            result = authorDAO.disableByAuthorId(authorId);
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

//    @Override
//    public boolean changeStatusById(int id) throws ServiceException {
//        boolean result = false;
//        try {
//            result = authorDAO.changeStatusById(id);
//        } catch (DAOException e) {
//            throw new ServiceException(e);
//        }
//        return result;
//    }

}