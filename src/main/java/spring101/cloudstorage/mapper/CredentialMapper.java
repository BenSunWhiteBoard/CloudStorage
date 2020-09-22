package spring101.cloudstorage.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import spring101.cloudstorage.model.Credential;

import java.util.ArrayList;

@Repository
@Mapper
public interface CredentialMapper {
    @Insert("INSERT INTO CREDENTIALS(url,username,key,password,userid) " +
            "VALUES(#{url},#{username},#{key},#{password},#{userid}) ")
    @Options(keyProperty = "credentialid",useGeneratedKeys = true)
    Integer insert(Credential credential);

    @Select("SELECT * FROM CREDENTIALS WHERE userid=#{userid}")
    ArrayList<Credential> selectAll(Integer userid);

    @Select("SELECT * FROM CREDENTIALS WHERE userid=#{userid} and credentialid=#{credentialid}")
    Credential selectById(Integer userid, Integer credentialid);

    @Update("UPDATE CREDENTIALS SET url=#{url},username=#{username},key=#{key},password=#{password} WHERE userid=#{userid} and credentialid=#{credentialid}")
    Integer update(Credential updatedCredential);

    @Delete("DELETE FROM CREDENTIALS WHERE userid=#{userid} and credentialid=#{credentialid}")
    void delete(Integer userid, Integer credentialid);

}
