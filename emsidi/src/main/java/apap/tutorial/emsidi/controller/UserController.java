package apap.tutorial.emsidi.controller;

import apap.tutorial.emsidi.model.RoleModel;
import apap.tutorial.emsidi.model.UserModel;
import apap.tutorial.emsidi.service.RoleService;
import apap.tutorial.emsidi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    String hurufBesar = ".*[A-Z].*";
    String hurufKecil = ".*[a-z].*";
    String angka = ".*[0-9].*";
    String special = ".*[^A-Za-z0-9].*";

    @GetMapping(value = "/add")
    private String addUserFormPage(Model model) {
        UserModel user = new UserModel();
        List<RoleModel> listRole = roleService.findAll();
        model.addAttribute("user", user);
        model.addAttribute("listRole", listRole);
        return "form-add-user";
    }

    @PostMapping(value = "/add")
    private String addUserSubmit(@ModelAttribute UserModel user, Model model) {
        userService.addUser(user);
        model.addAttribute("user", user);
        return "redirect:/";
    }

    // Latihan 1
    @GetMapping("/viewall")
    public String viewAllUser(Model model) {
        List<UserModel> listUser = userService.getListUser();
        model.addAttribute("listUser", listUser);
        return "viewall-user";
    }

    // Latihan 2
    @PostMapping("/delete/{username}")
    public String deleteUser(@PathVariable String username, RedirectAttributes red) {
        UserModel user = userService.getUserByUsername(username);
        String pesan = "User dengan username " + username + " berhasil dihapus!";
        userService.deleteUser(user);
        red.addFlashAttribute("pesan", pesan);
        return "redirect:/";
    }

    // Latihan 4 dan 5
    @GetMapping("/ubahPassword")
    public String changePasswordFormPage(){
        return "form-update-password";
    }

    @PostMapping("/ubahPassword")
    @PreAuthorize("isAuthenticated()")
    public String changePasswordSubmit(Authentication auth,
                                       @RequestParam("passwordLama") String passwordLama,
                                       @RequestParam("passwordBaru") String passwordBaru,
                                       @RequestParam("konfirmasiPassword") String konfirmasiPassword,
                                       RedirectAttributes red){
        UserModel user = userService.getUserByUsername(auth.getName());
        if(!userService.validasiPassword(user, passwordLama)){
            red.addFlashAttribute("pesanError","Password Lama Salah, Harap Memasukkan Password yang Benar");
            return "redirect:/user/ubahPassword";
        }

        if(passwordBaru.length() < 8){
            red.addFlashAttribute("pesanError", "Password Harus Minimal 8 Karakter");
            return "redirect:/user/ubahPassword";
        }

        if(!passwordBaru.matches(hurufBesar) || !passwordBaru.matches(hurufKecil) || !passwordBaru.matches(angka) || !passwordBaru.matches(special)){
            red.addFlashAttribute("pesanError", "Password harus mengandung angka, huruf, dan simbol");
            return "redirect:/user/ubahPassword";
        }

        if(!passwordBaru.equals(konfirmasiPassword)){
            red.addFlashAttribute("pesanError", "Password Baru Tidak Sama dengan Konfirmasi Password");
            return "redirect:/user/ubahPassword";
        }

        userService.updatePassword(user, konfirmasiPassword);
        red.addFlashAttribute("pesan", "Password Berhasil Diubah");
        return "redirect:/";
    }
}
