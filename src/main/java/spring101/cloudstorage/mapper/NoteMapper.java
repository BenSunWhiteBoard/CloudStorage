package spring101.cloudstorage.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import spring101.cloudstorage.model.Note;

import java.util.ArrayList;

@Repository
@Mapper
public interface NoteMapper {

    @Insert("INSERT INTO NOTES(notetitle,notedescription,userid) " +
            "VALUES(#{notetitle},#{notedescription},#{userid})")
    @Options(useGeneratedKeys = true,keyProperty = "noteid")
    Integer insert(Note note);

    @Select("SELECT * FROM NOTES WHERE userid=#{userid}")
    ArrayList<Note> selectAll(Integer userid);

    @Select("SELECT * FROM NOTES WHERE userid=#{userid} and noteid=#{noteid}")
    Note selectById(Integer userid,Integer noteid);

    @Update("UPDATE NOTES SET notetitle=#{notetitle},notedescription=#{notedescription} WHERE userid=#{userid} and noteid=#{noteid}")
    Integer update(Note note);

    @Delete("DELETE FROM NOTES WHERE userid=#{userid} and noteid=#{noteid}")
    void delete(Integer userid,Integer noteid);

}
