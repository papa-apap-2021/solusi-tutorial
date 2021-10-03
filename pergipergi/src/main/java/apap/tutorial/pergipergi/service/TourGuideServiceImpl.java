package apap.tutorial.pergipergi.service;

import apap.tutorial.pergipergi.model.TourGuideModel;
import apap.tutorial.pergipergi.repository.TourGuideDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class TourGuideServiceImpl implements TourGuideService{

    @Autowired
    TourGuideDb tourGuideDb;

    @Override
    public void addTourGuide(TourGuideModel tourGuide) {
        tourGuideDb.save(tourGuide);
    }

    @Override
    public TourGuideModel getTourGuideByNoTourGuide(Long noTourGuide) {
        Optional<TourGuideModel> guide = tourGuideDb.findByNoTourGuide(noTourGuide);
        return guide.orElse(null);
    }

    @Override
    public void updateTourGuide(TourGuideModel tourGuide) {
        tourGuideDb.save(tourGuide);
    }

    @Override
    public void deleteTourGuide(TourGuideModel tourGuide) {
        tourGuideDb.delete(tourGuide);
    }
}
