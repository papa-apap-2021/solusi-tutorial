package apap.tutorial.pergipergi.controller;
import apap.tutorial.pergipergi.model.TourGuideModel;
import apap.tutorial.pergipergi.model.TravelAgensiModel;
import apap.tutorial.pergipergi.service.TourGuideService;
import apap.tutorial.pergipergi.service.TravelAgensiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalTime;

@Controller
public class TourGuideController {

    @Qualifier("tourGuideServiceImpl")
    @Autowired
    private TourGuideService tourGuideService;

    @Qualifier("travelAgensiServiceImpl")
    @Autowired
    private TravelAgensiService travelAgensiService;

    @GetMapping("/tour-guide/add/{noAgensi}")
    public String addTourGuideFormPage(@PathVariable Long noAgensi, Model model){
        TourGuideModel guide = new TourGuideModel();
        TravelAgensiModel agensi = travelAgensiService.getAgensiByNoAgensi(noAgensi);
        guide.setAgensi(agensi);
        model.addAttribute("guide", guide);
        return "form-add-tour-guide";
    }

    @PostMapping("/tour-guide/add")
    public String addTourGuideSubmitPage(
            @ModelAttribute TourGuideModel tourGuide,
            Model model
    ){
        tourGuideService.addTourGuide(tourGuide);
        model.addAttribute("noTourGuide", tourGuide.getNoTourGuide());
        return "add-tour-guide";
    }

    @GetMapping("/tour-guide/update/{noTourGuide}")
    public String updateTourGuideForm(@PathVariable Long noTourGuide, Model model){
        TourGuideModel guide = tourGuideService.getTourGuideByNoTourGuide(noTourGuide);
        if (guide == null){
            return "error-notfound";
        }
        model.addAttribute("guide", guide);
        return "form-update-tour-guide";
    }

    @PostMapping("/tour-guide/update")
    public String updateTourGuideSubmit(
            @ModelAttribute TourGuideModel guide,
            Model model
    ){
        LocalTime now = LocalTime.now();
        TravelAgensiModel agensi = guide.getAgensi();
        if (now.isBefore(agensi.getWaktuBuka()) || now.isAfter(agensi.getWaktuTutup())){
            tourGuideService.updateTourGuide(guide);
            model.addAttribute("namaTourGuide", guide.getNamaTourGuide());
            model.addAttribute("noTourGuide", guide.getNoTourGuide());
            return "update-tour-guide";
        }
        return "error-notfound";
    }

    @GetMapping("/tour-guide/delete/{noTourGuide}")
    public String deleteTourGuideForm(@PathVariable Long noTourGuide, Model model){
        LocalTime now = LocalTime.now();
        TourGuideModel guide = tourGuideService.getTourGuideByNoTourGuide(noTourGuide);
        TravelAgensiModel agensi = guide.getAgensi();
        if (now.isBefore(agensi.getWaktuBuka()) || now.isAfter(agensi.getWaktuTutup())){
            tourGuideService.deleteTourGuide(guide);
            model.addAttribute("namaTourGuide", guide.getNamaTourGuide());
            model.addAttribute("noTourGuide", guide.getNoTourGuide());
            return "delete-tour-guide";
        }
        return "error-notfound";
    }

    @PostMapping("/tour-guide/delete")
    public String deleteTourGuideSubmit(
            @ModelAttribute TravelAgensiModel agensi,
            Model model
    ){
        if (travelAgensiService.isClosed(agensi.getWaktuBuka(), agensi.getWaktuTutup())) {
            for (TourGuideModel tourGuide :
                    agensi.getListTourGuide()) {
                tourGuideService.deleteTourGuide(tourGuide);
            }
            model.addAttribute("noAgensi", agensi.getNoAgensi());
            return "delete-tour-guide";
        }
        return "error-notfound";
    }

}
