package apap.tutorial.pergipergi.service;

import apap.tutorial.pergipergi.model.DestinasiModel;

import java.util.List;

public interface DestinasiService {
    void addDestinasi(DestinasiModel destinasi);
    List<DestinasiModel> getListDestinasi();
}