package apap.tutorial.cineplux.service;

import apap.tutorial.cineplux.model.BioskopModel;

import java.util.List;

public interface BioskopService {
  void addBioskop(BioskopModel bioskop);
  void updateBioskop(BioskopModel bioskop);
  void deleteBioskop(BioskopModel bioskop);
  String checkError(BioskopModel bioskop);
  List<BioskopModel> getBioskopList();
  BioskopModel getBioskopByNoBioskop(Long noBioskop);
}
