package com.zhuravlov.repairagency.controller;

import com.zhuravlov.repairagency.model.entity.UserEntity;
import com.zhuravlov.repairagency.service.UserService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class RegistrationController {

    @Autowired
    UserService userService;

    @GetMapping("/registration")
    public String registration(Model model, @RequestParam(required = false) String error) {
        model.addAttribute("userForm", new UserEntity());
        if (error != null) {
            model.addAttribute("error", "Email already registered");
        }
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm") @Valid UserEntity userEntity, BindingResult bindingResult, String error) {

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        try {
            userService.addUser(userEntity);
        } catch (Exception e) {
            return "redirect:registration?error";
        }

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
