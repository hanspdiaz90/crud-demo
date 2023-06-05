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

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
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
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("accion") == null ? "index" : request.getParameter("accion");
        switch (action) {
            case "crear":
                createBookAction(request, response);
                break;
            case "editar":
                System.out.println("Próximo a implementarse...");
                break;
            case "verDetalles":
                moreDetailsBookAction(request, response);
                break;
            case "deshabilitar":
                disableBookAction(request, response);
                break;
            case "listar":
                bookListAction(response);
                break;
            case "listarAutoresActivos":
                listActiveAuthorsAction(request, response);
                break;
            case "listarEditorialesActivos":
                listActivePublisherAction(request, response);
                break;
            case "listarGenerosActivos":
                listActiveGenresAction(request, response);
                break;
            default:
                mainAction(request, response);
                break;
        }
    }

    private void mainAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("cardTitle", "Listado de libros");
        RequestDispatcher dispatcher = request.getRequestDispatcher(VIEW_TEMPLATE_PATH);
        dispatcher.forward(request, response);
    }

    private void createBookAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getParameter("isbn") != null &&
                request.getParameter("title") != null &&
                request.getParameter("coverImage") != null &&
                request.getParameter("review") != null &&
                request.getParameter("yearEdition") != null &&
                request.getParameter("numberPages") != null &&
                request.getParameter("price") != null &&
                request.getParameter("author") != null &&
                request.getParameter("publisher") != null &&
                request.getParameter("genre") != null) {
            String isbn = request.getParameter("isbn");
            String title = request.getParameter("title");
            String coverImage = request.getParameter("coverImage");
            String review = request.getParameter("review");
            int yearEdition = Integer.parseInt(request.getParameter("yearEdition"));
            int numberPages = Integer.parseInt(request.getParameter("numberPages"));
            double price = Double.parseDouble(request.getParameter("price"));
            int authorId = Integer.parseInt(request.getParameter("author"));
            int publisherId = Integer.parseInt(request.getParameter("publisher"));
            int genreId = Integer.parseInt(request.getParameter("genre"));
            Book savedBook = new Book();
            savedBook.setIsbn(isbn);
            savedBook.setTitle(title);
            savedBook.setCoverImage(coverImage);
            savedBook.setReview(review);
            savedBook.setYearEdition(yearEdition);
            savedBook.setNumberPages(numberPages);
            savedBook.setPrice(price);
            savedBook.setAuthor(new Author());
            savedBook.getAuthor().setAuthorId(authorId);
            savedBook.setPublisher(new Publisher());
            savedBook.getPublisher().setPublisherId(publisherId);
            savedBook.setGenre(new Genre());
            savedBook.getGenre().setGenreId(genreId);
            boolean created = bookService.createBook(savedBook);
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

    private void moreDetailsBookAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getParameter("bookId") != null) {
            int bookId = Integer.parseInt(request.getParameter("bookId"));
            Book foundBook = bookService.findByBookId(bookId);
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

    private void bookListAction(HttpServletResponse response) throws IOException {
        JsonObject json = new JsonObject();
        JsonArray data = null;
        List<Book> bookList = bookService.findAll();
        if (bookList != null) {
            Type bookType = new TypeToken<List<Book>>(){}.getType();
            JsonElement result = gson.toJsonTree(bookList, bookType);
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

    private void listActiveAuthorsAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonObject json = new JsonObject();
        JsonArray data = null;
        String filter = request.getParameter("filter");
        List<Author> activeAuthors = filter != null ? bookService.findActiveAuthors(filter) : bookService.findActiveAuthors("");
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

    private void listActivePublisherAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonObject json = new JsonObject();
        JsonArray data = null;
        String filter = request.getParameter("filter");
        List<Publisher> activePublishers = filter != null ? bookService.findActivePublishers(filter) : bookService.findActivePublishers("");
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

    private void listActiveGenresAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonObject json = new JsonObject();
        JsonArray data = null;
        String filter = request.getParameter("filter");
        List<Genre> activeGenres = filter != null ? bookService.findActiveGenres(filter) : bookService.findActiveGenres("");
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

    private void disableBookAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getParameter("bookId") != null) {
            int bookId = Integer.parseInt(request.getParameter("bookId"));
            boolean disabled = bookService.disableByBookId(bookId);
            JsonObject json = new JsonObject();
            String message = null;
            if (disabled) {
                message = "El libro ha sido deshabilitado con éxito";
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