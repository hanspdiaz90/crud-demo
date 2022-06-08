package pe.edu.unprg.javaee.inventariolibros.services.factory;

import pe.edu.unprg.javaee.inventariolibros.services.*;
import pe.edu.unprg.javaee.inventariolibros.services.impl.*;

public class ServiceFactory {

    private static ServiceFactory instance = null;

    private final IUserService userService = new UserServiceImpl();
    private final IAuthorService authorService = new AuthorServiceImpl();
    private final IBookService bookService = new BookServiceImpl();
    private final IGenreService genreService = new GenreServiceImpl();
    private final IPublisherService publisherService = new PublisherServiceImpl();

    private ServiceFactory() {}

    public static ServiceFactory getInstance() {
        if (instance == null) {
            instance = new ServiceFactory();
        }
        return instance;
    }

    public IUserService getUserService() { return userService; }

    public IAuthorService getAuthorService() {
        return authorService;
    }

    public IBookService getBookService() {
        return bookService;
    }

    public IGenreService getGenreService() {
        return genreService;
    }

    public IPublisherService getPublisherService() {
        return publisherService;
    }

}