package pe.edu.unprg.javaee.inventariolibros.dao;

import pe.edu.unprg.javaee.inventariolibros.exception.DAOException;

public interface ILoginDAO {

    boolean validate() throws DAOException;
}
