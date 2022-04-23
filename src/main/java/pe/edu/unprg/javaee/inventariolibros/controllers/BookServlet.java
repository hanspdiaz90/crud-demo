package pe.edu.unprg.javaee.inventariolibros.controllers;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import pe.edu.unprg.javaee.inventariolibros.entities.Author;
import pe.edu.unprg.javaee.inventariolibros.entities.Book;
import pe.edu.unprg.javaee.inventariolibros.entities.Genre;
import pe.edu.unprg.javaee.inventariolibros.entities.Publisher;
import pe.edu.unprg.javaee.inventariolibros.exception.ServiceException;
import pe.edu.unprg.javaee.inventariolibros.services.IBookService;
import pe.edu.unprg.javaee.inventariolibros.services.factory.ServiceFactory;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "libroServlet", urlPatterns = "/biblioteca/libros")
public class BookServlet extends HttpServlet {

    private static final String PATH_LIBROS = "/WEB-INF/views/books/index.jsp";
    private final Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
    private final IBookService libroService = ServiceFactory.getInstance().getBookService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = request.getParameter("accion");
        if (accion == null) {
            accion = "index";
        }
        try {
            switch (accion) {
                case "registrar":
                    insertBookAction(request, response);
                    break;
                case "actualizar":
                    System.out.println("Próximo a implementarse...");
                    break;
                case "buscar":
                    searchBookByIdAction(request, response);
                    break;
                case "desactivar":
                    deactivateBookAction(request, response);
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
        } catch (IOException ex) {
            throw new ServletException(ex);
        }
    }

    private void indexAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(PATH_LIBROS);
        dispatcher.forward(request, response);
    }

    private void insertBookAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
        boolean ok = false;
        String message = null;
        JsonObject jsonResponse = new JsonObject();
        try {
            String isbn = request.getParameter("isbn");
            String titulo = request.getParameter("titulo");
            String descripcion = request.getParameter("descripcion");
            String existencias = request.getParameter("existencias");
            String precio = request.getParameter("precio");
            String autor = request.getParameter("autor");
            String editorial = request.getParameter("editorial");
            String genero = request.getParameter("genero");
            if (isbn.isEmpty() || titulo.isEmpty() || descripcion.isEmpty()) {
                message = "No se registraron los datos del editorial";
            } else {
                Book libro = new Book(
                        isbn,
                        titulo,
                        descripcion,
                        Integer.parseInt(existencias),
                        Double.parseDouble(precio),
                        new Author(),
                        new Publisher(),
                        new Genre());
                libro.getAutor().setId(Integer.parseInt(autor));
                libro.getEditorial().setId(Integer.parseInt(editorial));
                libro.getGenero().setId(Integer.parseInt(genero));
                boolean exito = libroService.insert(libro);
                if (exito) {
                    ok = true;
                    message = "Los datos del libro se registraron con éxito";
                }
            }
            jsonResponse.addProperty("ok", ok);
            jsonResponse.addProperty("message", message);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(jsonResponse.toString());
        } catch (ServiceException ex) {
            response.setContentType("text/html");
            response.getWriter().print(ex.getMessage());
        }
    }

    private void searchBookByIdAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
        boolean ok = false;
        JsonElement data = null;
        JsonObject jsonResponse = new JsonObject();
        try {
            String id = request.getParameter("id");
            Book libro = libroService.findById(Integer.parseInt(id));
            if (libro != null) {
                ok = true;
                data = gson.toJsonTree(libro);
            }
            jsonResponse.addProperty("ok", ok);
            jsonResponse.add("data", data);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(jsonResponse.toString());
        } catch (ServiceException ex) {
            response.setContentType("text/html");
            response.getWriter().print(ex.getMessage());
        }
    }

    private void bookListAction(HttpServletResponse response) throws IOException {
        boolean ok = false;
        JsonArray result = null;
        JsonObject jsonResponse = new JsonObject();
        try {
            List<Book> listaLibros = libroService.findAllBooks();
            if (listaLibros != null) {
                ok = true;
                JsonElement items = gson.toJsonTree(listaLibros, new TypeToken<List<Book>>(){}.getType());
                result = items.getAsJsonArray();
            }
            jsonResponse.addProperty("ok", ok);
            jsonResponse.add("result", result);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(jsonResponse.toString());
        } catch (ServiceException ex) {
            ex.printStackTrace();
            response.setContentType("text/html");
            response.getWriter().print(ex.getMessage());
        }
    }

    private void listActiveAuthorsAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
        boolean ok = false;
        JsonArray result = null;
        JsonObject jsonResponse = new JsonObject();
        try {
            String filtro = request.getParameter("filtro");
            List<Author> listaAutores = filtro != null ? libroService.findActiveAuthors(filtro) : libroService.findActiveAuthors("");
            if (listaAutores != null) {
                ok = true;
                JsonElement items = gson.toJsonTree(listaAutores, new TypeToken<List<Author>>(){}.getType());
                result = items.getAsJsonArray();
            }
            jsonResponse.addProperty("ok", ok);
            jsonResponse.add("result", result);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(jsonResponse.toString());
        } catch (ServiceException ex) {
            response.setContentType("text/html");
            response.getWriter().print(ex.getMessage());
        }
    }

    private void listActivePublisherAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
        boolean ok = false;
        JsonArray result = null;
        JsonObject jsonResponse = new JsonObject();
        try {
            String filtro = request.getParameter("filtro");
            List<Publisher> listaEditoriales = filtro != null ? libroService.findActivePublishers(filtro) : libroService.findActivePublishers("");
            if (listaEditoriales != null) {
                ok = true;
                JsonElement items = gson.toJsonTree(listaEditoriales, new TypeToken<List<Publisher>>(){}.getType());
                result = items.getAsJsonArray();
            }
            jsonResponse.addProperty("ok", ok);
            jsonResponse.add("result", result);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(jsonResponse.toString());
        } catch (ServiceException ex) {
            response.setContentType("text/html");
            response.getWriter().print(ex.getMessage());
        }
    }

    private void listActiveGenresAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
        boolean ok = false;
        JsonArray result = null;
        JsonObject jsonResponse = new JsonObject();
        try {
            String filtro = request.getParameter("filtro");
            List<Genre> listaGeneros = filtro != null ? libroService.findActiveGenres(filtro) : libroService.findActiveGenres("");
            if (listaGeneros != null) {
                ok = true;
                JsonElement items = gson.toJsonTree(listaGeneros, new TypeToken<List<Genre>>(){}.getType());
                result = items.getAsJsonArray();
            }
            jsonResponse.addProperty("ok", ok);
            jsonResponse.add("result", result);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(jsonResponse.toString());
        } catch (ServiceException ex) {
            response.setContentType("text/html");
            response.getWriter().print(ex.getMessage());
        }
    }

    private void deactivateBookAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
        boolean ok = false;
        String message = null;
        JsonObject jsonResponse = new JsonObject();
        try {
            String id = request.getParameter("id");
            boolean exito = libroService.deactivateById(Integer.parseInt(id));
            if (exito) {
                ok = true;
                message = "La operación se realizó con éxito";
            }
            jsonResponse.addProperty("ok", ok);
            jsonResponse.addProperty("message", message);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(jsonResponse.toString());
        } catch (ServiceException ex) {
            response.setContentType("text/html");
            response.getWriter().print(ex.getMessage());
        }
    }

}