package apap.tutorial.pergipergi.controller;

import apap.tutorial.pergipergi.model.DestinasiModel;
import apap.tutorial.pergipergi.model.UserModel;
import apap.tutorial.pergipergi.service.DestinasiService;
import apap.tutorial.pergipergi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DestinasiController {

    @Qualifier("destinasiServiceImpl")
    @Autowired
    DestinasiService destinasiService;

    @Autowired
    private UserService userService;

    @GetMapping("/destinasi/add")
    public String addDestinasiForm(Model model){
        model.addAttribute("destinasi", new DestinasiModel());
        return "form-add-destinasi";
    }

    @PostMapping("/destinasi/add")
    public String addDestinasiSubmit(
            @ModelAttribute DestinasiModel destinasi,
            Model model
    ){
        destinasiService.addDestinasi(destinasi);
        model.addAttribute("noDestinasi", destinasi.getNoDestinasi());
        return "add-destinasi";
    }

    @GetMapping("/destinasi/viewall")
    public String viewAllDestinasi(
            Model model, Authentication auth
    ){
        UserModel user = userService.getUserByUsername(auth.getName());
        String role = user.getRole().getRole();
        model.addAttribute("role", role);
        model.addAttribute("listDestinasi", destinasiService.getListDestinasi());
        return "viewall-destinasi";
    }
}