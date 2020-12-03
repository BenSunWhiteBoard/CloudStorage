package spring101.cloudstorage.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import spring101.cloudstorage.model.Credential;
import spring101.cloudstorage.model.User;
import spring101.cloudstorage.service.CredentialService;
import spring101.cloudstorage.service.EncryptionService;
import spring101.cloudstorage.service.UserService;

import java.security.SecureRandom;
import java.util.Base64;

@Controller
@RequestMapping("/credential")
public class CredentialController {

    private UserService userService;
    private CredentialService credentialService;


    public CredentialController(UserService userService, CredentialService credentialService) {
        this.userService = userService;
        this.credentialService = credentialService;
    }

//    @GetMapping("")
//    public String getPage(Authentication authentication, Model model){
//        String curUsername = authentication.getName();
//        Integer curUserId = userService.getCurUserId(curUsername);
//        model.addAttribute("uploadedCredentials",credentialService.getAllCredentials(curUserId));
//        return "home";
//    }

    @PostMapping("uploadOrEdit")
    public String uploadCredential(Authentication authentication, Credential credential){
        String curUsername = authentication.getName();
        Integer curUserId = userService.getCurUserId(curUsername);
        //general the key for encryption and decryption
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        if(credential.getCredentialid()==null){//upload
            Integer newCredentialId = credentialService.uploadCredential(new Credential(null,credential.getUrl(),credential.getUsername(),encodedKey,credential.getPassword(),curUserId));
            if(newCredentialId!=null){
                return "redirect:/result?success";
            }else{
                return "redirect:/result?inputError";
            }
        }else{//edit
            credential.setKey(encodedKey);
            credential.setUserid(curUserId);
            Integer rowChanged = credentialService.updateCredential(credential);
            if(rowChanged!=0){
                return "redirect:/result?success";
            }else{
                return "redirect:/result?inputError";
            }
        }



    }

    @DeleteMapping("delete/{credentialid}")
    public String deleteCredential(@PathVariable Integer credentialid,Authentication authentication){
        String curUsername = authentication.getName();
        Integer curUserId = userService.getCurUserId(curUsername);
        if(credentialService.deleteCredential(curUserId,credentialid)){
            return "redirect:/result?success";
        }else{
            return "redirect:/result?uploadError=credential not exists";
        }
    }

}
