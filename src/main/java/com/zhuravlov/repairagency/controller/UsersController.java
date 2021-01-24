package com.zhuravlov.repairagency.controller;

import com.zhuravlov.repairagency.entity.RoleEntity;
import com.zhuravlov.repairagency.entity.UserEntity;
import com.zhuravlov.repairagency.repository.RoleRepository;
import com.zhuravlov.repairagency.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Slf4j

@Controller
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/add")
    public String addUserForm(Model model) {
        model.addAttribute("userAttribute", new UserEntity());
        return "userAdd";
    }

    @GetMapping("/addUser")
    public String addUser(@ModelAttribute("userAttribute") @Validated UserEntity userEntity, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "userAdd";
        } else {
            userService.addUser(userEntity);
            return "redirect:/users/list";
        }
    }

    @GetMapping("/list")
    public ModelAndView getUsersList() {
        List<UserEntity> usersList = userService.getUsers();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("users", usersList);
        modelAndView.setViewName("usersList");
        return modelAndView;
    }

    @GetMapping("/edit")
    public String editUserForm(Model model, @RequestParam("userId") int userId) {
        model.addAttribute("userAttribute", userService.getUser(userId));
        List<RoleEntity> roleEntityList = roleRepository.findAll();
        model.addAttribute("roles", roleEntityList);
        return "userEdit";
    }

    @PostMapping("/editUser")
    public String updateUser(@ModelAttribute("userAttribute") @Validated UserEntity userEntity) {
        UserEntity userFromDb = userService.findByUsername(userEntity.getEmail());
        userFromDb.setEmail(userEntity.getEmail());
        userFromDb.setFirstName(userEntity.getFirstName());
        userFromDb.setLastName(userEntity.getLastName());
        userFromDb.setRoles(userEntity.getRoles());
        userService.updateUser(userFromDb);
        return "redirect:/users/list";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/deleteUser")
    public String deleteUser(@RequestParam("userId") int userId) {
        userService.deleteUser(userId);
        return "redirect:/users/list";
    }
}
