package individual.me.repository;

import individual.me.pojo.LoginUser;
import individual.me.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserRepository {

    @Select("select * from user where user.username = #{username}")
    User getUserByUsername(@Param("username") String username);
    User getUserById(@Param("id") int id);

    void updateUser(@Param("user") User user);

    void deleteUserById(@Param("id") int id);

    @Insert("insert into user values(null,#{user.username},#{user.password},null,null,sysdate())")
    void insertUser(@Param("user") LoginUser user);
}
