package pe.edu.unprg.javaee.inventariolibros.services.factory;

import pe.edu.unprg.javaee.inventariolibros.services.IAuthorService;
import pe.edu.unprg.javaee.inventariolibros.services.IBookService;
import pe.edu.unprg.javaee.inventariolibros.services.IGenreService;
import pe.edu.unprg.javaee.inventariolibros.services.IPublisherService;
import pe.edu.unprg.javaee.inventariolibros.services.impl.AuthorServiceImpl;
import pe.edu.unprg.javaee.inventariolibros.services.impl.BookServiceImpl;
import pe.edu.unprg.javaee.inventariolibros.services.impl.GenreServiceImpl;
import pe.edu.unprg.javaee.inventariolibros.services.impl.PublisherServiceImpl;

public class ServiceFactory {

    private static ServiceFactory instance = null;

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