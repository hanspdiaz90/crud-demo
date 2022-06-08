package pe.edu.unprg.javaee.inventariolibros.dao.factory;

import pe.edu.unprg.javaee.inventariolibros.dao.*;
import pe.edu.unprg.javaee.inventariolibros.dao.impl.*;

public class LibraryDAOFactory {

    private static LibraryDAOFactory instance = null;

    private final IUserDAO userDAO = new UserDAOImpl();
    private final IAuthorDAO authorDAO = new AuthorDAOImpl();
    private final IBookDAO bookDAO = new BookDAOImpl();
    private final IGenreDAO genreDAO = new GenreDAOImpl();
    private final IPublisherDAO publisherDAO = new PublisherDAOImpl();
    private final IDashboardDAO dashboardDAO = new DashboardDAOImpl();

    private LibraryDAOFactory() {}

    public static LibraryDAOFactory getInstance() {
        if (instance == null) {
            instance = new LibraryDAOFactory();
        }
        return instance;
    }

    public IUserDAO getUserDAO() { return userDAO; }

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

    public IDashboardDAO getDashboardDAO() { return dashboardDAO; }

}