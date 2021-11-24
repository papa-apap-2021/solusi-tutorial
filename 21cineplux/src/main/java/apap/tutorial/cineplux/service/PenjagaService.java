package apap.tutorial.cineplux.service;

import apap.tutorial.cineplux.model.PenjagaModel;

public interface PenjagaService {
    void addPenjaga(PenjagaModel penjaga);
    PenjagaModel getPenjagaByNoPenjaga(Long noPenjaga);
    void updatePenjaga(PenjagaModel penjaga);
    int deletePenjaga(PenjagaModel penjaga);
}
