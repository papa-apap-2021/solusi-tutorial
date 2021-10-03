package apap.tutorial.pergipergi.service;

import apap.tutorial.pergipergi.model.TravelAgensiModel;

import java.util.List;

public interface TravelAgensiService {
    void addAgensi(TravelAgensiModel travelAgensi);
    List<TravelAgensiModel> getListAgensi();
    TravelAgensiModel getAgensiByNoAgensi(Long noAgensi);
    TravelAgensiModel updateAgensi(TravelAgensiModel travelAgensi);
    void deleteAgensi(TravelAgensiModel travelAgensi);
}
