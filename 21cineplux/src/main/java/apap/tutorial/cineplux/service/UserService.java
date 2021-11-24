package apap.tutorial.cineplux.service;

import apap.tutorial.cineplux.model.UserModel;

import java.util.List;

public interface UserService {
    UserModel addUser(UserModel user);
    String encrypt(String password);
    List<UserModel> getListUser();
    UserModel getUserByUsername(String username);
    void deleteUser(UserModel user);
    boolean validasiPassword(UserModel user,String password);
    UserModel updatePassword(UserModel user, String password);
}
