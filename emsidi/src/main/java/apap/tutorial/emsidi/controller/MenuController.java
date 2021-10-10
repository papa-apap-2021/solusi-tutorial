package apap.tutorial.emsidi.controller;

import apap.tutorial.emsidi.model.MenuModel;
import apap.tutorial.emsidi.service.CabangService;
import apap.tutorial.emsidi.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MenuController {
    @Qualifier("menuServiceImpl")
    @Autowired
    MenuService menuService;

    @GetMapping("/menu/add")
    public String addMenuForm(Model model){
        model.addAttribute("menu", new MenuModel());
        return "form-add-menu";
    }

    @PostMapping("/menu/add")
    public String addFilmSubmit(
        @ModelAttribute MenuModel menu,
        Model model
    ){
        menuService.addMenu(menu);
        model.addAttribute("noMenu", menu.getNoMenu());
        return "add-menu";
    }

    @GetMapping("/menu/viewall")
    public String viewAllMenu(
        Model model
    ){
        model.addAttribute("listMenu", menuService.getListMenu());
        return "viewall-menu";
    }

}
