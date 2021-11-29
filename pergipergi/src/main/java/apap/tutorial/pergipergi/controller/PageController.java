package apap.tutorial.pergipergi.controller;

import apap.tutorial.pergipergi.model.UserModel;
import apap.tutorial.pergipergi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
    //latihan 1 dan 2
    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String home(Model model, Authentication auth){
        //Latihan 1 dan 2
        UserModel user = userService.getUserByUsername(auth.getName());
        String role = user.getRole().getRole();
        model.addAttribute("role", role);
        return "home";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }
}
