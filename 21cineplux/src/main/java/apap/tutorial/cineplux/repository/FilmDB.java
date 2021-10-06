package apap.tutorial.cineplux.repository;

import apap.tutorial.cineplux.model.FilmModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FilmDB extends JpaRepository<FilmModel, Long> {
  Optional<FilmModel> findByNamaFilm(String nama);
}
