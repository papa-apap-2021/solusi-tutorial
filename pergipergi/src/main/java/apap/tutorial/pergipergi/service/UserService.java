package apap.tutorial.pergipergi.service;

import apap.tutorial.pergipergi.model.UserModel;

import java.util.List;

public interface UserService {
    UserModel addUser(UserModel user);
    String encrypt(String password);

    //Latihan 1
    List<UserModel> getListUser();

    //Latihan 2
    UserModel getUserByUsername(String username);
    void deleteUser(UserModel user);

    //Latihan 4
    boolean validasiPassword(UserModel user,String password);
    UserModel updatePassword(UserModel user, String password);
}
