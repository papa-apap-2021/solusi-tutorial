package apap.tutorial.emsidi.service;

import apap.tutorial.emsidi.model.UserModel;

import java.util.List;

public interface UserService {
    UserModel addUser(UserModel user);
    public String encrypt(String password);

    // Latihan 1
    List<UserModel> getListUser();

    // Latihan 2
    UserModel getUserByUsername(String username);
    void deleteUser(UserModel user);

    // Latihan 4 & 5
    boolean validasiPassword(UserModel user,String password);
    UserModel updatePassword(UserModel user, String password);
}
