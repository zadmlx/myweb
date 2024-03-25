package individual.me.repository;

import individual.me.pojo.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserRepository {

    User getUserByUsername(@Param("username") String username);
    User getUserById(@Param("id") int id);
    void updateUser(@Param("user") User user);

    void deleteUserById(@Param("id") int id);

    void insertUser(@Param("user") User user);

    User loadUserByPhone(@Param("phone")String phone);
}
