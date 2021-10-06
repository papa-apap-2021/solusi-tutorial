package apap.tutorial.cineplux.controller;

import apap.tutorial.cineplux.model.FilmModel;
import apap.tutorial.cineplux.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FilmController {

    @Qualifier("filmServiceImpl")
    @Autowired
    FilmService filmService;

    @GetMapping("/film/add")
    public String addFilmForm(Model model){
        model.addAttribute("film", new FilmModel());
        return "form-add-film";
    }

    @PostMapping("/film/add")
    public String addFilmSubmit(
            @ModelAttribute FilmModel film,
            Model model
    ){
        filmService.addFilm(film);
        model.addAttribute("noFilm", film.getNoFilm());
        return "add-film";
    }

    @GetMapping("/film/viewall")
    public String viewAllFilm(
        Model model
    ){
        model.addAttribute("listFilm", filmService.getListFilm());
        return "viewall-film";
    }
}
