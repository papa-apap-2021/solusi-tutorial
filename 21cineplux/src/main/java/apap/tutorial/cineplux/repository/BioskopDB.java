package apap.tutorial.cineplux.repository;

import apap.tutorial.cineplux.model.BioskopModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BioskopDB extends JpaRepository<BioskopModel, Long> {
    Optional<BioskopModel> findByNoBioskop(Long noBioskop);
    List<BioskopModel> findAllByOrderByNamaBioskop();
}
