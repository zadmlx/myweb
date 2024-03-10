package individual.me.service;

import individual.me.pojo.user.User;

public interface UserService {
    User getUserByUsername(String username);

    User getUserById(int id);

    void updateUser( User user);

    void deleteUserById(int id);

    void insertUser(User user);
}
