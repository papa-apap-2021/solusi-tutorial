package apap.tutorial.pergipergi.repository;

import apap.tutorial.pergipergi.model.TourGuideModel;
import apap.tutorial.pergipergi.model.TravelAgensiModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TourGuideDb extends JpaRepository<TourGuideModel,Long> {
    Optional<TourGuideModel> findByNoTourGuide(Long noTourGuide);
}
