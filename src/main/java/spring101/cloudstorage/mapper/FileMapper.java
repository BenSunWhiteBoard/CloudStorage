package spring101.cloudstorage.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import spring101.cloudstorage.model.File;

import java.util.ArrayList;

@Repository
@Mapper
public interface FileMapper {

    @Insert("INSERT INTO FILES(filename, contenttype, filesize, userId, filedata) " +
            "VALUES(#{filename},#{contenttype},#{filesize},#{userId},#{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    Integer insert(File file);

    @Select("SELECT * FROM FILES WHERE fileId=#{fileId} AND userId=#{userId}")
    File selectById(Integer fileId, Integer userId);

    @Select("SELECT * FROM FILES WHERE userId=#{userId}")
    ArrayList<File> selectAll(Integer userId);

    @Delete("DELETE FROM FILES WHERE fileId=#{fileId} AND userId=#{userId}")
    void delete(Integer fileId, Integer userId);


}
