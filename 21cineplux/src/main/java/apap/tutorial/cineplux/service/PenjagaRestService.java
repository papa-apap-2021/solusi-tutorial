package apap.tutorial.cineplux.service;

import apap.tutorial.cineplux.model.PenjagaModel;

import java.util.List;

public interface PenjagaRestService {
    PenjagaModel createPenjaga(PenjagaModel penjaga);
    List<PenjagaModel> retrieveListPenjaga();
    PenjagaModel getPenjagaByNoPenjaga(Long noPenjaga);
    PenjagaModel updatePenjaga(Long noPenjaga, PenjagaModel penjagaModel);
    void deletePenjaga(Long noPenjaga);
    PenjagaModel predictAge(Long noPenjaga);
}
