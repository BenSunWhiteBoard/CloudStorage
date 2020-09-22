package spring101.cloudstorage.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import spring101.cloudstorage.model.User;

import java.util.ArrayList;

@Repository
@Mapper
public interface UserMapper {

    @Insert("INSERT INTO USERS(username,salt,password,firstname,lastname) " +
            "VALUES(#{username},#{salt},#{password},#{firstname},#{lastname})")
    @Options(keyProperty = "userid",useGeneratedKeys = true)
    Integer insert(User user);

    @Select("SELECT * FROM USERS")
    ArrayList<User> selectAll();

    @Select("SELECT * FROM USERS WHERE username = #{username}")
    User selectByName(String username);

}
