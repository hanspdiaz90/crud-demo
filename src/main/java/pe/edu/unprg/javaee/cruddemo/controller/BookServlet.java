package pe.edu.unprg.javaee.cruddemo.controller;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import pe.edu.unprg.javaee.cruddemo.model.Author;
import pe.edu.unprg.javaee.cruddemo.model.Book;
import pe.edu.unprg.javaee.cruddemo.model.Genre;
import pe.edu.unprg.javaee.cruddemo.model.Publisher;
import pe.edu.unprg.javaee.cruddemo.service.BookService;
import pe.edu.unprg.javaee.cruddemo.service.impl.BookServiceImpl;
import pe.edu.unprg.javaee.cruddemo.utils.JSONResponse;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

@WebServlet(name = "bookServlet", urlPatterns = "/admincrud/libros")
public class BookServlet extends HttpServlet {

    private final BookService bookService = new BookServiceImpl();
    private static final String VIEW_TEMPLATE_PATH = "/WEB-INF/views/books/index.jsp";
    private final Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action") == null ? "index" : request.getParameter("action");
        switch (action) {
            case "findById":
                this.findByIdAction(request, response);
                break;
            case "findAll":
                this.findAllAction(response);
                break;
            case "findActiveAuthors":
                this.findActiveAuthorsAction(request, response);
                break;
            case "findActivePublishers":
                this.findActivePublishersAction(request, response);
                break;
            case "findActiveGenres":
                this.findActiveGenresAction(request, response);
                break;
            default:
                this.mainAction(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action") == null ? "index" : request.getParameter("action");
        switch (action) {
            case "create":
                this.createAction(request, response);
                break;
            case "update":
                this.updateAction(request, response);
                break;
            case "disableById":
                this.disableByIdAction(request, response);
                break;
            default:
                this.mainAction(request, response);
                break;
        }
    }

    private void mainAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("cardTitle", "Listado de libros");
        RequestDispatcher dispatcher = request.getRequestDispatcher(VIEW_TEMPLATE_PATH);
        dispatcher.forward(request, response);
    }

    private void createAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getParameter("title") != null &&
                request.getParameter("isbn") != null &&
                request.getParameter("yearEdition") != null &&
                request.getParameter("numberPages") != null &&
                request.getParameter("author") != null &&
                request.getParameter("publisher") != null &&
                request.getParameter("genre") != null &&
                request.getParameter("price") != null &&
                request.getParameter("coverImage") != null &&
                request.getParameter("review") != null) {
            String title = request.getParameter("title");
            String isbn = request.getParameter("isbn");
            Integer yearEdition = Integer.parseInt(request.getParameter("yearEdition"));
            Integer numberPages = Integer.parseInt(request.getParameter("numberPages"));
            Integer authorId = Integer.parseInt(request.getParameter("author"));
            Integer publisherId = Integer.parseInt(request.getParameter("publisher"));
            Integer genreId = Integer.parseInt(request.getParameter("genre"));
            Double price = Double.parseDouble(request.getParameter("price"));
            String coverImage = request.getParameter("coverImage");
            String review = request.getParameter("review");
            Book savedBook = new Book();
            savedBook.setTitle(title);
            savedBook.setIsbn(isbn);
            savedBook.setYearEdition(yearEdition);
            savedBook.setNumberPages(numberPages);
            savedBook.setAuthor(new Author());
            savedBook.getAuthor().setAuthorId(authorId);
            savedBook.setPublisher(new Publisher());
            savedBook.getPublisher().setPublisherId(publisherId);
            savedBook.setGenre(new Genre());
            savedBook.getGenre().setGenreId(genreId);
            savedBook.setPrice(price);
            savedBook.setCoverImage(coverImage);
            savedBook.setReview(review);
            boolean created = this.bookService.createBook(savedBook);
            JsonObject json = new JsonObject();
            String message = null;
            if (created) {
                message = "El libro ha sido registrado con éxito";
                json.addProperty("success", true);
                json.addProperty("status", "success");
            } else {
                json.addProperty("success", false);
                json.addProperty("status", "failure");
            }
            json.addProperty("message", message);
            JSONResponse.writeFromServlet(response, json);
        }
    }

    private void updateAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getParameter("bookId") != null &&
                request.getParameter("title") != null &&
                request.getParameter("isbn") != null &&
                request.getParameter("yearEdition") != null &&
                request.getParameter("numberPages") != null &&
                request.getParameter("author") != null &&
                request.getParameter("publisher") != null &&
                request.getParameter("genre") != null &&
                request.getParameter("price") != null &&
                request.getParameter("coverImage") != null &&
                request.getParameter("review") != null) {
            Integer bookId = Integer.parseInt(request.getParameter("bookId"));
            String title = request.getParameter("title");
            String isbn = request.getParameter("isbn");
            Integer yearEdition = Integer.parseInt(request.getParameter("yearEdition"));
            Integer numberPages = Integer.parseInt(request.getParameter("numberPages"));
            Integer authorId = Integer.parseInt(request.getParameter("author"));
            Integer publisherId = Integer.parseInt(request.getParameter("publisher"));
            Integer genreId = Integer.parseInt(request.getParameter("genre"));
            Double price = Double.parseDouble(request.getParameter("price"));
            String coverImage = request.getParameter("coverImage");
            String review = request.getParameter("review");
            boolean active = request.getParameter("isActive") != null && request.getParameter("isActive").equals("on");
            Book updatedBook = new Book();
            updatedBook.setBookId(bookId);
            updatedBook.setTitle(title);
            updatedBook.setIsbn(isbn);
            updatedBook.setYearEdition(yearEdition);
            updatedBook.setNumberPages(numberPages);
            updatedBook.setAuthor(new Author());
            updatedBook.getAuthor().setAuthorId(authorId);
            updatedBook.setPublisher(new Publisher());
            updatedBook.getPublisher().setPublisherId(publisherId);
            updatedBook.setGenre(new Genre());
            updatedBook.getGenre().setGenreId(genreId);
            updatedBook.setPrice(price);
            updatedBook.setCoverImage(coverImage);
            updatedBook.setReview(review);
            updatedBook.setActive(active);
            boolean updated = this.bookService.editBook(updatedBook);
            JsonObject json = new JsonObject();
            String message = null;
            if (updated) {
                message = "Se actualizaron los datos del libro con éxito";
                json.addProperty("success", true);
                json.addProperty("status", "success");
            } else {
                json.addProperty("success", false);
                json.addProperty("status", "failure");
            }
            json.addProperty("message", message);
            JSONResponse.writeFromServlet(response, json);
        }
    }

    private void findByIdAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getParameter("bookId") != null) {
            Integer bookId = Integer.parseInt(request.getParameter("bookId"));
            Book foundBook = this.bookService.findByBookId(bookId);
            JsonObject json = new JsonObject();
            JsonElement result = null;
            if (foundBook != null) {
                Type bookType = new TypeToken<Book>(){}.getType();
                result = this.gson.toJsonTree(foundBook, bookType);
                json.addProperty("success", true);
                json.addProperty("status", "success");
            } else {
                json.addProperty("success", false);
                json.addProperty("status", "failure");
            }
            json.add("result", result);
            JSONResponse.writeFromServlet(response, json);
        }
    }

    private void findAllAction(HttpServletResponse response) throws IOException {
        JsonObject json = new JsonObject();
        JsonArray data = null;
        List<Book> bookList = this.bookService.findAll();
        if (bookList != null) {
            Type bookType = new TypeToken<List<Book>>(){}.getType();
            JsonElement result = this.gson.toJsonTree(bookList, bookType);
            data = result.getAsJsonArray();
            json.addProperty("success", true);
            json.addProperty("status", "success");
        } else {
            json.addProperty("success", false);
            json.addProperty("status", "failure");
        }
        json.add("result", data);
        JSONResponse.writeFromServlet(response, json);
    }

    private void findActiveAuthorsAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonObject json = new JsonObject();
        JsonArray data = null;
        String filter = request.getParameter("filter");
        List<Author> activeAuthors = this.bookService.findActiveAuthors(filter);
        if (activeAuthors != null) {
            Type authorType = new TypeToken<List<Author>>(){}.getType();
            JsonElement result = this.gson.toJsonTree(activeAuthors, authorType);
            data = result.getAsJsonArray();
            json.addProperty("success", true);
            json.addProperty("status", "success");
        } else {
            json.addProperty("success", false);
            json.addProperty("status", "failure");
        }
        json.add("result", data);
        JSONResponse.writeFromServlet(response, json);
    }

    private void findActivePublishersAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonObject json = new JsonObject();
        JsonArray data = null;
        String filter = request.getParameter("filter");
        List<Publisher> activePublishers = this.bookService.findActivePublishers(filter);
        if (activePublishers != null) {
            Type publisherType = new TypeToken<List<Publisher>>(){}.getType();
            JsonElement result = this.gson.toJsonTree(activePublishers, publisherType);
            data = result.getAsJsonArray();
            json.addProperty("success", true);
            json.addProperty("status", "success");
        } else {
            json.addProperty("success", false);
            json.addProperty("status", "failure");
        }
        json.add("result", data);
        JSONResponse.writeFromServlet(response, json);
    }

    private void findActiveGenresAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonObject json = new JsonObject();
        JsonArray data = null;
        String filter = request.getParameter("filter");
        List<Genre> activeGenres = this.bookService.findActiveGenres(filter);
        if (activeGenres != null) {
            Type genreType = new TypeToken<List<Genre>>(){}.getType();
            JsonElement result = this.gson.toJsonTree(activeGenres, genreType);
            data = result.getAsJsonArray();
            json.addProperty("success", true);
            json.addProperty("status", "success");
        } else {
            json.addProperty("success", false);
            json.addProperty("status", "failure");
        }
        json.add("result", data);
        JSONResponse.writeFromServlet(response, json);
    }

    private void disableByIdAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getParameter("bookId") != null) {
            Integer bookId = Integer.parseInt(request.getParameter("bookId"));
            boolean disabled = this.bookService.disableByBookId(bookId);
            JsonObject json = new JsonObject();
            String message = null;
            if (disabled) {
                message = "El libro ha sido desactivado con éxito";
                json.addProperty("success", true);
                json.addProperty("status", "success");
            } else {
                json.addProperty("success", false);
                json.addProperty("status", "failure");
            }
            json.addProperty("message", message);
            JSONResponse.writeFromServlet(response, json);
        }
    }

}