package pe.edu.unprg.javaee.inventariolibros.controllers;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import pe.edu.unprg.javaee.inventariolibros.models.Author;
import pe.edu.unprg.javaee.inventariolibros.models.Book;
import pe.edu.unprg.javaee.inventariolibros.models.Genre;
import pe.edu.unprg.javaee.inventariolibros.models.Publisher;
import pe.edu.unprg.javaee.inventariolibros.exception.ServiceException;
import pe.edu.unprg.javaee.inventariolibros.services.IBookService;
import pe.edu.unprg.javaee.inventariolibros.services.factory.ServiceFactory;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.List;

@WebServlet(name = "bookServlet", urlPatterns = "/biblioteca/libros")
public class BookServlet extends HttpServlet {

    private static final String PATH = "/WEB-INF/views/books/index.jsp";
    private final Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
    private final IBookService bookService = ServiceFactory.getInstance().getBookService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        String action = request.getParameter("accion");
        if (action == null) {
            action = "index";
        }
        try {
            switch (action) {
                case "crear":
                    insertBookAction(request, response);
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
                    indexAction(request, response);
                    break;
            }
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }

    private void indexAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("cardTitle", "Listado de libros");
        RequestDispatcher dispatcher = request.getRequestDispatcher(PATH);
        dispatcher.forward(request, response);
    }

    private void insertBookAction(HttpServletRequest request, HttpServletResponse response) throws ServiceException, IOException {
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
            libro.setTitulo(titulo);
            libro.setPortada(portada);
            libro.setResenia(resenia);
            libro.setAnioEdicion(anioEdicion);
            libro.setNroPaginas(nroPaginas);
            libro.setEjemplares(ejemplares);
            libro.setPrecio(precio);
            libro.setAutor(new Author());
            libro.getAutor().setId(autor);
            libro.setEditorial(new Publisher());
            libro.getEditorial().setId(editorial);
            libro.setGenero(new Genre());
            libro.getGenero().setId(genero);
            boolean inserted = bookService.insert(libro);
            JsonObject json = new JsonObject();
            String message = null;
            if (inserted) {
                json.addProperty("status", "success");
                message = "El libro ha sido registrado con éxito";
            } else {
                json.addProperty("status", "error");
            }
            json.addProperty("message", message);
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            out.print(json);
            out.flush();
        }
    }

    private void moreDetailsBookAction(HttpServletRequest request, HttpServletResponse response) throws ServiceException, IOException {
        if (request.getParameter("id") != null) {
            int id = Integer.parseInt(request.getParameter("id"));
            Book found = bookService.findById(id);
            JsonObject json = new JsonObject();
            JsonElement result = null;
            if (found != null) {
                result = gson.toJsonTree(found);
                json.addProperty("status", "success");
            } else {
                json.addProperty("status", "error");
            }
            json.add("result", result);
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            out.print(json);
            out.flush();
        }
    }

    private void bookListAction(HttpServletResponse response) throws ServiceException, IOException {
        JsonObject json = new JsonObject();
        JsonArray data = null;
        List<Book> bookList = bookService.findAll();
        if (bookList != null) {
            json.addProperty("status", "success");
            Type typeBook = new TypeToken<List<Book>>(){}.getType();
            JsonElement result = gson.toJsonTree(bookList, typeBook);
            data = result.getAsJsonArray();
        } else {
            json.addProperty("status", "error");
        }
        json.add("result", data);
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(json);
        out.flush();
    }

    private void listActiveAuthorsAction(HttpServletRequest request, HttpServletResponse response) throws ServiceException, IOException {
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
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(json);
        out.flush();
    }

    private void listActivePublisherAction(HttpServletRequest request, HttpServletResponse response) throws ServiceException, IOException {
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
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(json);
        out.flush();
    }

    private void listActiveGenresAction(HttpServletRequest request, HttpServletResponse response) throws ServiceException, IOException {
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
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(json);
        out.flush();
    }

    private void disableBookAction(HttpServletRequest request, HttpServletResponse response) throws ServiceException, IOException {
        if (request.getParameter("id") != null) {
            int id = Integer.parseInt(request.getParameter("id"));
            boolean disabled = bookService.disableById(id);
            JsonObject json = new JsonObject();
            String message = null;
            if (disabled) {
                json.addProperty("status", "success");
                message = "El libro ha sido deshabilitado con éxito";
            } else {
                json.addProperty("status", "error");
            }
            json.addProperty("message", message);
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            out.print(json);
            out.flush();
        }
    }

}