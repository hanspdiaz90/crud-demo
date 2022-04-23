package pe.edu.unprg.javaee.inventariolibros.dao.factory;

import pe.edu.unprg.javaee.inventariolibros.dao.IAuthorDAO;
import pe.edu.unprg.javaee.inventariolibros.dao.IBookDAO;
import pe.edu.unprg.javaee.inventariolibros.dao.IGenreDAO;
import pe.edu.unprg.javaee.inventariolibros.dao.IPublisherDAO;
import pe.edu.unprg.javaee.inventariolibros.dao.impl.AuthorDAOImpl;
import pe.edu.unprg.javaee.inventariolibros.dao.impl.BookDAOImpl;
import pe.edu.unprg.javaee.inventariolibros.dao.impl.GenreDAOImpl;
import pe.edu.unprg.javaee.inventariolibros.dao.impl.PublisherDAOImpl;

public class DAOFactory {

    private static DAOFactory instance = null;

    private final IAuthorDAO authorDAO = new AuthorDAOImpl();
    private final IBookDAO bookDAO = new BookDAOImpl();
    private final IGenreDAO genreDAO = new GenreDAOImpl();
    private final IPublisherDAO publisherDAO = new PublisherDAOImpl();

    private DAOFactory() {}

    public static DAOFactory getInstance() {
        if (instance == null) {
            instance = new DAOFactory();
        }
        return instance;
    }

    public IAuthorDAO getAuthorDAO() {
        return authorDAO;
    }

    public IBookDAO getBookDAO() {
        return bookDAO;
    }

    public IGenreDAO getGenreDAO() {
        return genreDAO;
    }

    public IPublisherDAO getPublisherDAO() {
        return publisherDAO;
    }

}