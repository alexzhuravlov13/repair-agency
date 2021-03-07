package com.zhuravlov.repairagency.controller;

import com.zhuravlov.repairagency.controller.Util.ControllerUtil;
import com.zhuravlov.repairagency.model.DTO.UserAmountDto;
import com.zhuravlov.repairagency.model.entity.RoleEntity;
import com.zhuravlov.repairagency.model.entity.UserEntity;
import com.zhuravlov.repairagency.repository.RoleRepository;
import com.zhuravlov.repairagency.unit.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @GetMapping("/list")
    public ModelAndView getUsersList() {
        log.info("--User:" + userName + " entered /users/list endpoint");
        userName = controllerUtil.getUserName();
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
        log.info("Loaded: " + page.getContent().toString());
        return controllerUtil.getModelAndViewAttributesForUserList(basePath, pageNo, page, userList, modelAndView);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/edit")
    public String editUserForm(Model model, @RequestParam("userId") int userId) {
        log.info("--User:" + userName + " entered /users/edit" + userId + " endpoint");
        model.addAttribute("userAttribute", userService.getUser(userId));
        List<RoleEntity> roleEntityList = roleRepository.findAll();
        model.addAttribute("roles", roleEntityList);
        return "userEdit";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/editUser")
    public String updateUser(@ModelAttribute("userAttribute") @Validated UserEntity userEntity) {
        log.info("--User:" + userName + " entered /users/editUser endpoint");
        userService.updateUser(userEntity);
        log.info("--User:" + userName + " edited user id:" + userEntity.getUserId());
        return "redirect:/users/list";
    }

    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    @GetMapping("/changeAmount")
    public String changeAmountUserForm(Model model, @RequestParam("userId") int userId) {
        log.info("--User:" + userName + " entered /users/changeAmount/" + userId + " endpoint");
        model.addAttribute("userAmountDto", new UserAmountDto(userId));
        return "userChangeAmount";
    }

    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    @PostMapping("/changeAmount")
    public String changeUserAmount(@ModelAttribute("userAttribute") UserAmountDto userAmountDto) {
        log.info("--User:" + userName + " entered /users/changeAmount endpoint");
        userService.changeAmount(userAmountDto.getUserId(), userAmountDto.getAmount());
        log.info("--User:" + userName + " change amount of user "
                + userAmountDto.getUserId() + " by " + userAmountDto.getAmount());
        return "redirect:/users/list";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping(method = RequestMethod.GET, value = "/deleteUser")
    public String deleteUser(@RequestParam("userId") int userId) {
        log.info("--User:" + userName + " entered /users/deleteUser endpoint");
        userService.deleteUser(userId);
        return "redirect:/users/list";
    }
}
