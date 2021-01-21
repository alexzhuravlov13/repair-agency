package com.zhuravlov.repairagency.controller;


import com.zhuravlov.repairagency.entity.UserEntity;
import com.zhuravlov.repairagency.service.SecurityService;
import com.zhuravlov.repairagency.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    @Autowired
    UserService userService;

    @Autowired
    private SecurityService securityService;


    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new UserEntity());

        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm") UserEntity userEntity, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userService.addUser(userEntity);

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (error != null) {
            model.addAttribute("error", "Invalid username or password");
        }

        if (logout != null) {
            model.addAttribute("message", "Your have been logged out successfully");
        }

        return "login";
    }
}
