package pe.edu.unprg.javaee.inventariolibros.controller;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import pe.edu.unprg.javaee.inventariolibros.model.Author;
import pe.edu.unprg.javaee.inventariolibros.model.Book;
import pe.edu.unprg.javaee.inventariolibros.model.Genre;
import pe.edu.unprg.javaee.inventariolibros.model.Publisher;
import pe.edu.unprg.javaee.inventariolibros.service.BookService;
import pe.edu.unprg.javaee.inventariolibros.service.impl.BookServiceImpl;
import pe.edu.unprg.javaee.inventariolibros.utils.JSONResponse;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

@WebServlet(name = "bookServlet", urlPatterns = "/biblioteca/libros")
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
                request.getParameter("titulo") != null &&
                request.getParameter("portada") != null &&
                request.getParameter("resenia") != null &&
                request.getParameter("anioEdicion") != null &&
                request.getParameter("nroPaginas") != null &&
                request.getParameter("ejemplares") != null &&
                request.getParameter("precio") != null &&
                request.getParameter("autor") != null &&
                request.getParameter("editorial") != null &&
                request.getParameter("genero") != null) {
            String isbn = request.getParameter("isbn");
            String titulo = request.getParameter("titulo");
            String portada = request.getParameter("portada");
            String resenia = request.getParameter("resenia");
            int anioEdicion = Integer.parseInt(request.getParameter("anioEdicion"));
            int nroPaginas = Integer.parseInt(request.getParameter("nroPaginas"));
            int ejemplares = Integer.parseInt(request.getParameter("ejemplares"));
            double precio = Double.parseDouble(request.getParameter("precio"));
            int autor = Integer.parseInt(request.getParameter("autor"));
            int editorial = Integer.parseInt(request.getParameter("editorial"));
            int genero = Integer.parseInt(request.getParameter("genero"));
            Book libro = new Book();
            libro.setIsbn(isbn);
            libro.setTitle(titulo);
            libro.setCoverImage(portada);
            libro.setReview(resenia);
            libro.setYearEdition(anioEdicion);
            libro.setNumberPages(nroPaginas);
            libro.setCopies(ejemplares);
            libro.setPrice(precio);
            libro.setAuthor(new Author());
            libro.getAuthor().setAuthorId(autor);
            libro.setPublisher(new Publisher());
            libro.getPublisher().setPublisherId(editorial);
            libro.setGenre(new Genre());
            libro.getGenre().setGenreId(genero);
            boolean inserted = bookService.createBook(libro);
            JsonObject json = new JsonObject();
            String message = null;
            if (inserted) {
                json.addProperty("status", "success");
                message = "El libro ha sido registrado con éxito";
            } else {
                json.addProperty("status", "error");
            }
            json.addProperty("message", message);
            JSONResponse.writeFromServlet(response, json);
        }
    }

    private void moreDetailsBookAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getParameter("bookId") != null) {
            int bookId = Integer.parseInt(request.getParameter("bookId"));
            Book found = bookService.findByBookId(bookId);
            JsonObject json = new JsonObject();
            JsonElement result = null;
            if (found != null) {
                result = gson.toJsonTree(found);
                json.addProperty("status", "success");
            } else {
                json.addProperty("status", "error");
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
            json.addProperty("status", "success");
            Type bookListType = new TypeToken<List<Book>>(){}.getType();
            JsonElement result = gson.toJsonTree(bookList, bookListType);
            data = result.getAsJsonArray();
        } else {
            json.addProperty("status", "error");
        }
        json.add("result", data);
        JSONResponse.writeFromServlet(response, json);
    }

    private void listActiveAuthorsAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonObject json = new JsonObject();
        JsonArray data = null;
        String filter = request.getParameter("filtro");
        List<Author> activeAuthors = filter != null ? bookService.findActiveAuthors(filter) : bookService.findActiveAuthors("");
        if (activeAuthors != null) {
            json.addProperty("status", "success");
            Type typeAuthor = new TypeToken<List<Author>>(){}.getType();
            JsonElement result = gson.toJsonTree(activeAuthors, typeAuthor);
            data = result.getAsJsonArray();
        } else {
            json.addProperty("status", "error");
        }
        json.add("result", data);
        JSONResponse.writeFromServlet(response, json);
    }

    private void listActivePublisherAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonObject json = new JsonObject();
        JsonArray data = null;
        String filter = request.getParameter("filtro");
        List<Publisher> activePublishers = filter != null ? bookService.findActivePublishers(filter) : bookService.findActivePublishers("");
        if (activePublishers != null) {
            json.addProperty("status", "success");
            Type typePublisher = new TypeToken<List<Publisher>>(){}.getType();
            JsonElement result = gson.toJsonTree(activePublishers, typePublisher);
            data = result.getAsJsonArray();
        } else {
            json.addProperty("status", "error");
        }
        json.add("result", data);
        JSONResponse.writeFromServlet(response, json);
    }

    private void listActiveGenresAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonObject json = new JsonObject();
        JsonArray data = null;
        String filter = request.getParameter("filtro");
        List<Genre> activeGenres = filter != null ? bookService.findActiveGenres(filter) : bookService.findActiveGenres("");
        if (activeGenres != null) {
            json.addProperty("status", "success");
            Type typeGenre = new TypeToken<List<Genre>>(){}.getType();
            JsonElement result = gson.toJsonTree(activeGenres, typeGenre);
            data = result.getAsJsonArray();
        } else {
            json.addProperty("status", "error");
        }
        json.add("result", data);
        JSONResponse.writeFromServlet(response, json);
    }

    private void disableBookAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getParameter("id") != null) {
            int id = Integer.parseInt(request.getParameter("id"));
            boolean disabled = bookService.disableByBookId(id);
            JsonObject json = new JsonObject();
            String message = null;
            if (disabled) {
                json.addProperty("status", "success");
                message = "El libro ha sido deshabilitado con éxito";
            } else {
                json.addProperty("status", "error");
            }
            json.addProperty("message", message);
            JSONResponse.writeFromServlet(response, json);
        }
    }

}