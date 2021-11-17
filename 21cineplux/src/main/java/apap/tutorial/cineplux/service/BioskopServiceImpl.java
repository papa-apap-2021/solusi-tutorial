package apap.tutorial.cineplux.service;

import apap.tutorial.cineplux.model.BioskopModel;
import apap.tutorial.cineplux.model.FilmModel;
import apap.tutorial.cineplux.repository.BioskopDB;
import apap.tutorial.cineplux.repository.FilmDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BioskopServiceImpl implements BioskopService {

    @Autowired
    BioskopDB bioskopDB;

    @Autowired
    FilmDB filmDB;

    @Override
    public void addBioskop(BioskopModel bioskop) {

        for (FilmModel film :
                bioskop.getListFilm()) {
            FilmModel filmModel = filmDB.getById(film.getNoFilm());
            if (filmModel.getListBioskop() == null) {
                filmModel.setListBioskop(new ArrayList<>());
            }
            filmModel.getListBioskop().add(bioskop);
        }
        bioskopDB.save(bioskop);
    }

    @Override
    public void updateBioskop(BioskopModel bioskop) {
        bioskopDB.save(bioskop);
    }

    @Override
    public List<BioskopModel> getBioskopList() {
        return bioskopDB.findAll();
    }

    @Override
    public BioskopModel getBioskopByNoBioskop(Long noBioskop) {
        Optional<BioskopModel> bioskop = bioskopDB.findByNoBioskop(noBioskop);
        if (bioskop.isPresent()) {
            return bioskop.get();
        }
        return null;
    }

    @Override
    public void deleteBioskop(BioskopModel bioskop) {
        if (checkError(bioskop) == null) {
            bioskopDB.delete(bioskop);
        }
    }

    @Override
    public String checkError(BioskopModel bioskop) {
        if (LocalTime.now().isAfter(bioskop.getWaktuBuka()) && LocalTime.now().isBefore(bioskop.getWaktuTutup())) {
            return "Bioskop sedang buka";
        }
        if (bioskop.getListPenjaga().size() > 0) {
            return "Bioskop memiliki penjaga";
        }
        return null;
    }
}
