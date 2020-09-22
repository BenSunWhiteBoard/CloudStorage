package spring101.cloudstorage.service;

import org.attoparser.dom.INestableNode;
import org.springframework.stereotype.Service;
import spring101.cloudstorage.mapper.NoteMapper;
import spring101.cloudstorage.model.Note;

import java.util.ArrayList;

@Service
public class NoteService {

    private NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public ArrayList<Note> getAllNotes(Integer userid) {
        return noteMapper.selectAll(userid);
    }

    public Integer uploadNote(Note note) {
        return noteMapper.insert(note);
    }

    public Integer editNote(Note note) {
        return noteMapper.update(note);
    }

    public boolean deleteNote(Integer userid, Integer noteid) {
        if (noteMapper.selectById(userid, noteid) != null) {
            noteMapper.delete(userid, noteid);
            return true;
        }else{
            return false;
        }
    }
}
