package pe.edu.unprg.javaee.cruddemo.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
Este servlet sólo sirve en caso de no existir configuración inicial en el web.xml
y se está cargando inicialmente el index.jsp
 */

@WebServlet(name = "indexServlet", urlPatterns = "/admincrud/index")
public class IndexServlet extends HttpServlet {

    private static final String VIEW_TEMPLATE_PATH = "/WEB-INF/views/login/index.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.mainAction(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.mainAction(request, response);
    }

    private void mainAction(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(VIEW_TEMPLATE_PATH);
        dispatcher.forward(request, response);
    }

}