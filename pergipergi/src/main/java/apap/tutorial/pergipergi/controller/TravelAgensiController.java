package apap.tutorial.pergipergi.controller;

import apap.tutorial.pergipergi.model.DestinasiModel;
import apap.tutorial.pergipergi.model.TourGuideModel;
import apap.tutorial.pergipergi.model.TravelAgensiModel;
import apap.tutorial.pergipergi.service.DestinasiService;
import apap.tutorial.pergipergi.service.TravelAgensiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TravelAgensiController {

    @Qualifier("travelAgensiServiceImpl")
    @Autowired
    private TravelAgensiService travelAgensiService;

    @Qualifier("destinasiServiceImpl")
    @Autowired
    private DestinasiService destinasiService;

    @GetMapping("/agensi/add")
    public String addAgensiFormPage(Model model){
        TravelAgensiModel agensi = new TravelAgensiModel();
        List<DestinasiModel> listDestinasi = destinasiService.getListDestinasi();
        List<DestinasiModel> listDestinasiNew = new ArrayList<DestinasiModel>();

        agensi.setListDestinasi(listDestinasiNew);
        agensi.getListDestinasi().add(new DestinasiModel());

        model.addAttribute("agensi", agensi);
        model.addAttribute("listDestinasiExisting", listDestinasi);
        return "form-add-agensi";
    }

    @PostMapping(value = "/agensi/add", params = {"save"})
    public String addAgensiSubmit(
            @ModelAttribute TravelAgensiModel agensi,
            Model model
    ) {
        if (agensi.getListDestinasi() == null) {
            agensi.setListDestinasi(new ArrayList<>());
        }
        travelAgensiService.addAgensi(agensi);
        model.addAttribute("noAgensi", agensi.getNoAgensi());
        return "add-agensi";
    }

    @PostMapping(value = "/agensi/add", params = {"addRow"})
    private String addRowDestinasiMultiple(
            @ModelAttribute TravelAgensiModel agensi,
            Model model
    ) {
        if (agensi.getListDestinasi() == null || agensi.getListDestinasi().size() == 0) {
            agensi.setListDestinasi(new ArrayList<>());
        }
        agensi.getListDestinasi().add(new DestinasiModel());
        List<DestinasiModel> listDestinasi = destinasiService.getListDestinasi();

        model.addAttribute("agensi", agensi);
        model.addAttribute("listDestinasiExisting", listDestinasi);

        return "form-add-agensi";
    }

    @PostMapping(value = "/agensi/add", params = {"deleteRow"})
    private String deleteRowDestinasiMultiple(
            @ModelAttribute TravelAgensiModel agensi,
            @RequestParam("deleteRow") Integer row,
            Model model
    ) {
        final Integer rowId = Integer.valueOf(row);
        agensi.getListDestinasi().remove(rowId.intValue());

        List<DestinasiModel> listDestinasi = destinasiService.getListDestinasi();

        model.addAttribute("agensi", agensi);
        model.addAttribute("listDestinasiExisting", listDestinasi);

        return "form-add-agensi";
    }

    @GetMapping("/agensi/viewall")
    public String listAgensi(Model model){
        List<TravelAgensiModel> listAgensi = travelAgensiService.getListAgensi();
        model.addAttribute("listAgensi", listAgensi);
        return "viewall-agensi";
    }

    @GetMapping("/agensi/view")
    public String viewDetailAgensiPage(
            @RequestParam(value = "noAgensi") Long noAgensi,
            Model model
    ){
        TravelAgensiModel agensi = travelAgensiService.getAgensiByNoAgensi(noAgensi);
        List<TourGuideModel> listTourGuide = agensi.getListTourGuide();

        model.addAttribute("agensi", agensi);
        model.addAttribute("listTourGuide", listTourGuide);
        model.addAttribute("listDestinasi", agensi.getListDestinasi());

        return "view-agensi";
    }

    @GetMapping("/agensi/update/{noAgensi}")
    public String updateAgensiFormPage(
            @PathVariable Long noAgensi,
            Model model
    ){
        TravelAgensiModel agensi = travelAgensiService.getAgensiByNoAgensi(noAgensi);
        model.addAttribute("agensi", agensi);
        return "form-update-agensi";
    }

    @PostMapping("/agensi/update")
    public String updateAgensiSubmitPage(
            @ModelAttribute TravelAgensiModel agensi,
            Model model
    ){
        TravelAgensiModel updatedAgensi = travelAgensiService.updateAgensi(agensi);
        model.addAttribute("noAgensi", updatedAgensi.getNoAgensi());
        return "update-agensi";
    }

    @GetMapping("/agensi/delete/{noAgensi}")
    public String deleteAgensi(@PathVariable Long noAgensi, Model model) {
        LocalTime now = LocalTime.now();
        TravelAgensiModel agensi = travelAgensiService.getAgensiByNoAgensi(noAgensi);
        if ((now.isBefore(agensi.getWaktuBuka()) || now.isAfter(agensi.getWaktuTutup()))
                && agensi.getListTourGuide().isEmpty()) {
            travelAgensiService.deleteAgensi(agensi);
            model.addAttribute("noAgensi", agensi.getNoAgensi());
            return "delete-agensi";
        }
        return "error-notfound";
    }
}