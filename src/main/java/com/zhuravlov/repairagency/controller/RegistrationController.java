package com.zhuravlov.repairagency.controller;

import com.zhuravlov.repairagency.model.entity.UserEntity;
import com.zhuravlov.repairagency.service.UserService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class RegistrationController {

    @Autowired
    UserService userService;

    @GetMapping("/registration")
    public String registration(Model model, @RequestParam(required = false) String error) {
        log.info("--User" + " entered /registration endpoint");
        model.addAttribute("userForm", new UserEntity());
        if (error != null) {
            model.addAttribute("error", "Email already registered");
        }
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm") @Valid UserEntity userEntity, BindingResult bindingResult, String error) {
        log.info("--User" + " entered /registration endpoint");
        if (bindingResult.hasErrors()) {
            log.info("Registration canceled for" + userEntity.toString());
            log.info("Binding result" + bindingResult.toString());
            return "registration";
        }

        try {
            userService.addUser(userEntity);
        } catch (Exception e) {
            log.info("Registration canceled for" + userEntity.toString());
            log.info("Exception:" + e.getMessage());
            return "redirect:registration?error";
        }
        log.info("Registration success for" + userEntity.toString());
        return "redirect:/login";

    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        log.info("--User" + " entered /login endpoint");
        if (error != null) {
            model.addAttribute("error", "Invalid username or password");
            log.info("--Error login invalid username or password");
        }

        if (logout != null) {
            log.info("--User" + " successfully logout");
            model.addAttribute("message", "Your have been logged out successfully");
        }
        return "login";
    }
}
