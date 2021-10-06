package apap.tutorial.cineplux.controller;

import apap.tutorial.cineplux.model.BioskopModel;
import apap.tutorial.cineplux.model.FilmModel;
import apap.tutorial.cineplux.model.PenjagaModel;
import apap.tutorial.cineplux.service.BioskopService;
import apap.tutorial.cineplux.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BioskopController {

    @Qualifier("bioskopServiceImpl")
    @Autowired
    private BioskopService bioskopService;

    @Qualifier("filmServiceImpl")
    @Autowired
    private FilmService filmService;

    @GetMapping("/bioskop/add")
    public String addBioskopForm(Model model) {
        BioskopModel bioskop = new BioskopModel();
        List<FilmModel> listFilm = filmService.getListFilm();
        List<FilmModel> listFilmNew = new ArrayList<FilmModel>();

        bioskop.setListFilm(listFilmNew);
        bioskop.getListFilm().add(new FilmModel());

        model.addAttribute("bioskop", bioskop);
        model.addAttribute("listFilmExisting", listFilm);
        return "form-add-bioskop";
    }

    @PostMapping(value = "/bioskop/add", params = {"save"})
    public String addBioskopSubmit(@ModelAttribute BioskopModel bioskop, Model model) {
        if (bioskop.getListFilm() == null) {
            bioskop.setListFilm(new ArrayList<>());
        }
        bioskopService.addBioskop(bioskop);
        model.addAttribute("noBioskop", bioskop.getNoBioskop());
        return "add-bioskop";
    }

    @PostMapping(value = "/bioskop/add", params = {"addRow"})
    private String addRowFilmMultiple(@ModelAttribute BioskopModel bioskop, Model model) {
        if (bioskop.getListFilm() == null || bioskop.getListFilm().size() == 0) {
            bioskop.setListFilm(new ArrayList<>());
        }
        bioskop.getListFilm().add(new FilmModel());
        List<FilmModel> listFilm = filmService.getListFilm();

        model.addAttribute("bioskop", bioskop);
        model.addAttribute("listFilmExisting", listFilm);

        return "form-add-bioskop";
    }

    @PostMapping(value = "/bioskop/add", params = {"deleteRow"})
    private String deleteRowFilmMultiple(@ModelAttribute BioskopModel bioskop, @RequestParam("deleteRow") Integer row,
                                         Model model) {
        final Integer rowId = Integer.valueOf(row);
        bioskop.getListFilm().remove(rowId.intValue());

        List<FilmModel> listFilm = filmService.getListFilm();

        model.addAttribute("bioskop", bioskop);
        model.addAttribute("listFilmExisting", listFilm);

        return "form-add-bioskop";
    }

    @GetMapping("/bioskop/viewall")
    public String listBioskop(Model model) {
        List<BioskopModel> listBioskop = bioskopService.getBioskopList();
        model.addAttribute("listBioskop", listBioskop);
        return "viewall-bioskop";
    }

    @GetMapping("/bioskop/view")
    public String viewDetailBioskop(@RequestParam(value = "noBioskop") Long noBioskop, Model model) {
        BioskopModel bioskop = bioskopService.getBioskopByNoBioskop(noBioskop);
        List<PenjagaModel> listPenjaga = bioskop.getListPenjaga();

        model.addAttribute("bioskop", bioskop);
        model.addAttribute("listFilm", bioskop.getListFilm());
        model.addAttribute("listPenjaga", listPenjaga);

        return "view-bioskop";
    }

    @GetMapping("/bioskop/view/{noBioskop}")
    public String viewDetailBioskopPath(
            @PathVariable Long noBioskop,
            Model model) {
        BioskopModel bioskop = bioskopService.getBioskopByNoBioskop(noBioskop);
        List<PenjagaModel> listPenjaga = bioskop.getListPenjaga();

        model.addAttribute("bioskop", bioskop);
        model.addAttribute("listFilm", bioskop.getListFilm());
        model.addAttribute("listPenjaga", listPenjaga);

        return "view-bioskop";
    }

    @GetMapping("/bioskop/update/{noBioskop}")
    public String updateBioskopForm(@PathVariable Long noBioskop, Model model) {
        BioskopModel bioskop = bioskopService.getBioskopByNoBioskop(noBioskop);
        model.addAttribute("bioskop", bioskop);
        return "form-update-bioskop";
    }

    @PostMapping("/bioskop/update")
    public String updateBioskopSubmit(@ModelAttribute BioskopModel bioskop, Model model) {
        if (bioskop.getListFilm() == null) {
            bioskop.setListFilm(new ArrayList<>());
        }
        bioskopService.updateBioskop(bioskop);
        model.addAttribute("noBioskop", bioskop.getNoBioskop());
        return "update-bioskop";
    }

    @GetMapping("/bioskop/delete/{noBioskop}")
    public String deleteBioskop(@PathVariable Long noBioskop, Model model) {
        BioskopModel bioskop = bioskopService.getBioskopByNoBioskop(noBioskop);
        bioskopService.deleteBioskop(bioskop);
        model.addAttribute("bioskop", bioskop);
        model.addAttribute("errorMessage", bioskopService.checkError(bioskop));
        return "delete-bioskop";
    }

}
