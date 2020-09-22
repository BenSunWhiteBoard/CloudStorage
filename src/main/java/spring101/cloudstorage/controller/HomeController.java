package spring101.cloudstorage.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import spring101.cloudstorage.service.*;

@Controller
@RequestMapping("/home")
public class HomeController {

    private UserService userService;
    private FileService fileService;
    private CredentialService credentialService;
    private NoteService noteService;
    private EncryptionService encryptionService;

    public HomeController(UserService userService, FileService fileService, CredentialService credentialService, EncryptionService encryptionService, NoteService noteService) {
        this.userService = userService;
        this.fileService = fileService;
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
        this.noteService = noteService;
    }

    @GetMapping("")
    public String getPage(Authentication authentication, Model model){
        try {
            String curUsername = authentication.getName();
            Integer curUserId = userService.getCurUserId(curUsername);
            model.addAttribute("uploadedFiles", fileService.selectAll(curUserId));
            model.addAttribute("uploadedCredentials", credentialService.getAllCredentials(curUserId));
            model.addAttribute("uploadedNotes", noteService.getAllNotes(curUserId));
        } catch (Exception e) {
            return "redirect:result?uploadError=Please log in first!";
        }
        return "home";
    }

    @ModelAttribute("encryptionService")
    public EncryptionService encryptionService(){
        return this.encryptionService;
    }

}