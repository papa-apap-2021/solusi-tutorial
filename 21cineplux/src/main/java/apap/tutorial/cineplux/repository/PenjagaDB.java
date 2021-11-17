package apap.tutorial.cineplux.repository;

import apap.tutorial.cineplux.model.PenjagaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PenjagaDB extends JpaRepository<PenjagaModel, Long> {
    Optional<PenjagaModel> findByNoPenjaga(Long noPenjaga);
}
