package apap.tutorial.emsidi.service;

import apap.tutorial.emsidi.model.CabangModel;
import apap.tutorial.emsidi.rest.CabangDetail;
import reactor.core.publisher.Mono;

import java.util.List;

public interface CabangRestService {
    CabangModel createCabang(CabangModel cabang);
    List<CabangModel> retrieveListCabang();
    CabangModel getCabangByNoCabang(Long noCabang);
    CabangModel updateCabang(Long noCabang, CabangModel cabangUpdate);
    void deleteCabang(Long noCabang);
    Mono<String> getStatus(Long noCabang);
    Mono<CabangDetail> postStatus();
}
