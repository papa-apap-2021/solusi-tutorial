package apap.tutorial.pergipergi.service;

import apap.tutorial.pergipergi.model.TourGuideModel;

public interface TourGuideService {

    void addTourGuide(TourGuideModel tourGuide);
    TourGuideModel getTourGuideByNoTourGuide(Long noTourGuide);
    void updateTourGuide(TourGuideModel tourGuide);
    void deleteTourGuide(TourGuideModel tourGuide);
}
