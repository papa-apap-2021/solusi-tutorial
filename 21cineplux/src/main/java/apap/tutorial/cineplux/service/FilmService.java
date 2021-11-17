package apap.tutorial.cineplux.service;

import apap.tutorial.cineplux.model.FilmModel;

import java.util.List;

public interface FilmService {
    void addFilm(FilmModel film);
    List<FilmModel> getListFilm();
}
