package tmbe.service;

import tmbe.model.User;

import java.util.List;

public interface UserService {
    User readUserData(String token);

    List<User> readAllUserData();

    void deleteUser(String token);
}
