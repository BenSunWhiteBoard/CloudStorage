package spring101.cloudstorage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import spring101.cloudstorage.model.User;
import spring101.cloudstorage.service.UserService;

@Controller
public class SignupController {

    private UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/signup")
    public String getPage() {
        return "signup";
    }

    @PostMapping("/signup")
    public String getPage(@ModelAttribute User user, Model model) {

        String signupError = null;

        if (userService.isUserExist(user.getUsername())) {
            signupError = "username already exist!";
        }

        if (signupError == null){
            Integer newUserId = userService.createUser(user);
            if (newUserId < 0) {
                signupError = "signed up failed";
            }
        }

        if (signupError == null){
            model.addAttribute("signupSuccess",true);
        }else{
            model.addAttribute("signupError",signupError);
        }

        return "signup";
    }

}
