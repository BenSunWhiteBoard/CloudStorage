package spring101.cloudstorage.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import spring101.cloudstorage.model.Note;
import spring101.cloudstorage.service.NoteService;
import spring101.cloudstorage.service.UserService;

@Controller
@RequestMapping("/note")
public class NoteController {

    private NoteService noteService;
    private UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @PostMapping("uploadOrEdit")
    public String uploadOrEdit(Authentication authentication, Note note){
        String curUsername = authentication.getName();
        Integer curUserid = userService.getCurUserId(curUsername);
        if(note.getNoteid()==null){//upload
            Integer newid = noteService.uploadNote(new Note(null,note.getNotetitle(), note.getNotedescription(), curUserid));
            if(newid != null){
                return "redirect:/result?success";
            }else{
                return "redirect:/result?inputError";
            }
        }else{//edit
            note.setUserid(curUserid);
            Integer rowChanged = noteService.editNote(note);
            if(rowChanged != 0){
                return "redirect:/result?success";
            }else{
                return "redirect:/result?inputError";
            }
        }
    }

    @GetMapping("delete/{noteId}")
    public String delete(Authentication authentication,@PathVariable Integer noteId){
        String curUsername = authentication.getName();
        Integer curUserid = userService.getCurUserId(curUsername);
        if(noteService.deleteNote(curUserid,noteId)){
            return "redirect:/result?success";
        }else{
            return "redirect:/result?inputError";
        }
    }
}
