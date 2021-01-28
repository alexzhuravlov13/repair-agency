package com.zhuravlov.repairagency.controller;

import com.zhuravlov.repairagency.controller.Util.ControllerUtil;
import com.zhuravlov.repairagency.entity.RoleEntity;
import com.zhuravlov.repairagency.entity.UserEntity;
import com.zhuravlov.repairagency.repository.RoleRepository;
import com.zhuravlov.repairagency.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Slf4j

@Controller
@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MANAGER')")
@RequestMapping("/users")
public class UsersController {
    String userName;

    @Autowired
    private ControllerUtil controllerUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


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
    public ModelAndView getUsersList(Model model) {
        userName = controllerUtil.getUserName();
        log.info("--User:" + userName + " entered /users/list endpoint");
        return getUsersListPaginated(1);
    }

    @GetMapping("/list/page/{pageNo}")
    public ModelAndView getUsersListPaginated(@PathVariable(value = "pageNo") int pageNo) {
        log.info("--User:" + userName + " entered /users/list/page/" + pageNo + " endpoint");
        int pageSize = 10;

        Page<UserEntity> page = userService.findAllPaginated(pageNo, pageSize);
        List<UserEntity> userList = page.getContent();

        String basePath = "/users/list";

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("usersList");
        return controllerUtil.getModelAndViewAttributesForUserList(basePath, pageNo, page, userList, modelAndView);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/edit")
    public String editUserForm(Model model, @RequestParam("userId") int userId) {
        model.addAttribute("userAttribute", userService.getUser(userId));
        List<RoleEntity> roleEntityList = roleRepository.findAll();
        model.addAttribute("roles", roleEntityList);
        return "userEdit";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/editUser")
    public String updateUser(@ModelAttribute("userAttribute") @Validated UserEntity userEntity) {
        UserEntity userFromDb = userService.findByUsername(userEntity.getEmail());
        userFromDb.setEmail(userEntity.getEmail());
        userFromDb.setPassword(bCryptPasswordEncoder.encode(userEntity.getPassword()));
        userFromDb.setFirstName(userEntity.getFirstName());
        userFromDb.setLastName(userEntity.getLastName());
        userFromDb.setRoles(userEntity.getRoles());
        userService.updateUser(userFromDb);
        return "redirect:/users/list";
    }

    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    @GetMapping("/changeAmount")
    public String changeAmountUserForm(Model model, @RequestParam("userId") int userId) {
        model.addAttribute("userAttribute", userService.getUser(userId));
        return "userChangeAmount";
    }

    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    @PostMapping("/changeAmount")
    public String changeUserAmount(@ModelAttribute("userAttribute") @Validated UserEntity userEntity) {
        UserEntity userFromDb = userService.findByUsername(userEntity.getEmail());
        userFromDb.setAmount(userFromDb.getAmount().add(userEntity.getAmount()));
        userService.updateUser(userFromDb);
        return "redirect:/users/list";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping(method = RequestMethod.GET, value = "/deleteUser")
    public String deleteUser(@RequestParam("userId") int userId) {
        userService.deleteUser(userId);
        return "redirect:/users/list";
    }
}
