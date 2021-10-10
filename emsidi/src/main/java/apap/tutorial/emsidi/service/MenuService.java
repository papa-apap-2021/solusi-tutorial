package apap.tutorial.emsidi.service;

import apap.tutorial.emsidi.model.MenuModel;

import java.util.List;

public interface MenuService {
    List<MenuModel> getListMenu();
    void addMenu(MenuModel menu);
}
