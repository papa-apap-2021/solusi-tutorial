package apap.tutorial.emsidi.service;

import apap.tutorial.emsidi.model.MenuModel;
import apap.tutorial.emsidi.repository.MenuDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService{
    @Autowired
    MenuDb menuDb;

    @Override
    public List<MenuModel> getListMenu() {
        return menuDb.findAll();
    }

    @Override
    public void addMenu(MenuModel menu){
        menuDb.save(menu);
    }
}
