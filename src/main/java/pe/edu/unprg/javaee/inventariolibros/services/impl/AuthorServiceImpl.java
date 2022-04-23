package pe.edu.unprg.javaee.inventariolibros.services.impl;

import pe.edu.unprg.javaee.inventariolibros.dao.IAuthorDAO;
import pe.edu.unprg.javaee.inventariolibros.dao.factory.DAOFactory;
import pe.edu.unprg.javaee.inventariolibros.entities.Author;
import pe.edu.unprg.javaee.inventariolibros.exception.DAOException;
import pe.edu.unprg.javaee.inventariolibros.exception.ServiceException;
import pe.edu.unprg.javaee.inventariolibros.services.IAuthorService;

import java.util.List;

public class AuthorServiceImpl implements IAuthorService {

    private final IAuthorDAO autorDAO = DAOFactory.getInstance().getAuthorDAO();

    @Override
    public boolean insert(Author autor) throws ServiceException {
        boolean resultado = false;
        try {
            resultado =  autorDAO.insert(autor);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return resultado;
    }

    @Override
    public List<Author> findAll() throws ServiceException {
        List<Author> listaAutores = null;
        try {
            listaAutores = autorDAO.findAll();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return listaAutores;
    }

    @Override
    public boolean update(Author autor) throws ServiceException {
        boolean resultado = false;
        try {
            resultado =  autorDAO.update(autor);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return resultado;
    }

    @Override
    public Author findById(int id) throws ServiceException {
        Author autor = null;
        try {
            autor = autorDAO.findById(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return autor;
    }

    @Override
    public boolean changeStatusById(int id) throws ServiceException {
        boolean resultado = false;
        try {
            resultado = autorDAO.changeStatusById(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return resultado;
    }

}