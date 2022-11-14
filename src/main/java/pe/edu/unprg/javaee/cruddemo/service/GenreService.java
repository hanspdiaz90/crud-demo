package pe.edu.unprg.javaee.cruddemo.service;

import pe.edu.unprg.javaee.cruddemo.model.Genre;

import java.util.List;

public interface GenreService {

    boolean createGenre(Genre genre);
    boolean editGenre(Genre genre);
    Genre findByGenreId(int genreId);
    List<Genre> findAll();
    boolean disableByGenreId(int genreId);

}
