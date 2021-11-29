package apap.tutorial.emsidi.service;

import apap.tutorial.emsidi.model.UserModel;
import apap.tutorial.emsidi.repository.UserDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDb userDb;

    @Override
    public UserModel addUser(UserModel user) {
        String pass = encrypt(user.getPassword());
        user.setPassword(pass);
        return userDb.save(user);
    }

    @Override
    public String encrypt(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        return hashedPassword;
    }

    // Latihan 1
    @Override
    public List<UserModel> getListUser() {
        return userDb.findAll();
    }

    // Latihan 2
    @Override
    public UserModel getUserByUsername(String username) {
        return userDb.findByUsername(username);
    }

    @Override
    public void deleteUser(UserModel user) {
        userDb.delete(user);
    }

    // Latihan 4 & 5
    @Override
    public boolean validasiPassword(UserModel user, String password) {
        return new BCryptPasswordEncoder().matches(password, user.getPassword());
    }

    @Override
    public UserModel updatePassword(UserModel user, String password) {
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        return userDb.save(user);
    }
}
