package apap.tutorial.cineplux.service;

import apap.tutorial.cineplux.model.BioskopModel;
import apap.tutorial.cineplux.rest.BioskopDetail;
import reactor.core.publisher.Mono;

import java.util.List;

public interface BioskopRestService {
    BioskopModel createBioskop(BioskopModel bioskop);
    List<BioskopModel> retrieveListBioskop();
    BioskopModel getBioskopByNoBioskop(Long noBioskop);
    BioskopModel updateBioskop(Long noBioskop, BioskopModel bioskopUpdate);
    void deleteBioskop(Long noBioskop);
    Mono<String> getStatus(Long noBioskop);
    Mono<BioskopDetail> postStatus();
}
