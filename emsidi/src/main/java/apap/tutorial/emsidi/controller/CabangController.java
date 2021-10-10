package apap.tutorial.emsidi.controller;

import apap.tutorial.emsidi.model.CabangModel;
import apap.tutorial.emsidi.model.MenuModel;
import apap.tutorial.emsidi.model.PegawaiModel;
import apap.tutorial.emsidi.service.CabangService;
import apap.tutorial.emsidi.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

@SuppressWarnings("ALL")
@Controller
public class CabangController {

    @Qualifier("cabangServiceImpl")
    @Autowired
    private CabangService cabangService;

    @Qualifier("menuServiceImpl")
    @Autowired
    private MenuService menuService;

    @GetMapping("/cabang/add")
    public String addCabangForm(Model model) {
        CabangModel cabang = new CabangModel();
        List<MenuModel> listMenu = menuService.getListMenu();
        List<MenuModel> listMenuNew = new ArrayList<MenuModel>();

        cabang.setListMenu(listMenuNew);
        cabang.getListMenu().add(new MenuModel());

        model.addAttribute("cabang", cabang);
        model.addAttribute("listMenuExisting", listMenu);
        return "form-add-cabang";
    }

    @PostMapping(value = "/cabang/add", params = {"save"})
    public String addCabangSubmit(
            @ModelAttribute CabangModel cabang,
            Model model
    ) {
        if(cabang.getListMenu() == null){
            cabang.setListMenu(new ArrayList<>());
        }
        cabangService.addCabang(cabang);
        model.addAttribute("noCabang", cabang.getNoCabang());
        return "add-cabang";
    }

    @PostMapping(value = "/cabang/add", params = {"addRow"})
    public String addRowCabangMultiple(
            @ModelAttribute CabangModel cabang,
            Model model
    ) {
        if(cabang.getListMenu() == null || cabang.getListMenu().size() == 0){
            cabang.setListMenu(new ArrayList<>());
        }
        cabang.getListMenu().add(new MenuModel());
        List<MenuModel> listMenu = menuService.getListMenu();

        model.addAttribute("cabang", cabang);
        model.addAttribute("listMenuExisting", listMenu);
        return "form-add-cabang";
    }

    @PostMapping(value = "/cabang/add", params = {"deleteRow"})
    public String deleteRowCabangMultiple(
            @ModelAttribute CabangModel cabang,
            @RequestParam("deleteRow") Integer row,
            Model model
    ) {
        final Integer rowId = Integer.valueOf(row);
        cabang.getListMenu().remove(rowId.intValue());
        
        List<MenuModel> listMenu = menuService.getListMenu();

        model.addAttribute("cabang", cabang);
        model.addAttribute("listMenuExisting", listMenu);
        return "form-add-cabang";
    }

    @GetMapping("/cabang/viewall")
    public String listCabang(Model model) {
        List<CabangModel> listCabang = cabangService.getCabangList();
        model.addAttribute("listCabang", listCabang);
        return "viewall-cabang";
    }

    // Latihan 1: Terurut berdasarkan nama cabang
    @GetMapping("/cabang/viewAll")
    public String listCabangTerurut(Model model) {
        List<CabangModel> listCabang = cabangService.getCabangListSorted();
        model.addAttribute("listCabang", listCabang);
        return "viewall-cabang";
    }


    @GetMapping("/cabang/view")
    public String viewDetailCabang(
            @RequestParam(value = "noCabang") Long noCabang,
            Model model
    ) {
        CabangModel cabang = cabangService.getCabangByNoCabang(noCabang);
        List<PegawaiModel> listPegawai = cabang.getListPegawai();

        model.addAttribute("cabang", cabang);
        model.addAttribute("listPegawai", listPegawai);

        return "view-cabang";
    }

    @GetMapping("/cabang/update/{noCabang}")
    public String updateCabangForm(
            @PathVariable Long noCabang,
            Model model
    ) {
        CabangModel cabang = cabangService.getCabangByNoCabang(noCabang);
        model.addAttribute("cabang", cabang);
        return "form-update-cabang";
    }

    @PostMapping("/cabang/update")
    public String updateCabangSubmit(
            @ModelAttribute CabangModel cabang,
            Model model
    ) {
        cabangService.updateCabang(cabang);
        model.addAttribute("noCabang", cabang.getNoCabang());
        return "update-cabang";
    }

    // Latihan 4: Delete Cabang saat tidak memiliki pegawai dan sedang tutup
    @GetMapping("/cabang/delete/{noCabang}")
    public String deleteCabang(@PathVariable Long noCabang, Model model) {
        LocalTime now = LocalTime.now();
        CabangModel cabang = cabangService.getCabangByNoCabang(noCabang);
        if ((now.isBefore(cabang.getWaktuBuka()) || now.isAfter(cabang.getWaktuTutup()))
                && cabang.getListPegawai().isEmpty()) {
            cabangService.removeCabang(cabang);
            model.addAttribute("noCabang", cabang.getNoCabang());
            return "delete-cabang";
        }
        return "error-cannot-delete";
    }

}
