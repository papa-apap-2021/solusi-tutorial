package apap.tutorial.pergipergi.repository;

import apap.tutorial.pergipergi.model.TravelAgensiModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TravelAgensiDb extends JpaRepository<TravelAgensiModel,Long> {
    Optional<TravelAgensiModel> findByNoAgensi(Long noAgensi);
}
