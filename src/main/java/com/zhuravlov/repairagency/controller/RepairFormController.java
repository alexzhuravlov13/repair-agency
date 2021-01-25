package com.zhuravlov.repairagency.controller;

import com.zhuravlov.repairagency.entity.DTO.RepairFormDto;
import com.zhuravlov.repairagency.entity.RepairFormEntity;
import com.zhuravlov.repairagency.entity.Status;
import com.zhuravlov.repairagency.entity.converter.RepairFormConverter;
import com.zhuravlov.repairagency.service.RepairFormService;
import com.zhuravlov.repairagency.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/repairs")
@Slf4j
public class RepairFormController {

    @Autowired
    private RepairFormService repairFormService;

    @Autowired
    private UserService userService;

    @Autowired
    private RepairFormConverter repairFormConverter;

    @GetMapping("/list")
    public ModelAndView getRepairFormList() {
        int id = getIdFromDbByAuthentication();

        List<RepairFormEntity> usersRepairForms = repairFormService.findUserRepairForms(id);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("repairForms", usersRepairForms);
        modelAndView.setViewName("repairFormUserList");
        return modelAndView;
    }

    private int getIdFromDbByAuthentication() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.findByUsername(userName).getUserId();
    }

    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    @GetMapping("/manager/list")
    public ModelAndView getAllRepairForms() {
        List<RepairFormEntity> usersRepairForms = repairFormService.getRepairForms();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("repairForms", usersRepairForms);
        modelAndView.setViewName("repairFormUserList");
        return modelAndView;
    }

    @GetMapping("/add")
    public String addRepairForm(Model model) {
        int userId = getIdFromDbByAuthentication();
        RepairFormDto repairFormDto = new RepairFormDto();
        repairFormDto.setAuthorId(userId);
        repairFormDto.setCreationDate(LocalDateTime.now());
        model.addAttribute("repairFormAttribute", repairFormDto);
        return "repairFormAdd";
    }

    @PostMapping("/addRepairForm")
    public String saveRepairForm(Model model, @ModelAttribute("repairFormAttribute")
    @Validated RepairFormDto repairFormDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("repairFormAttribute", repairFormDto);
            return "repairFormAdd";
        }
        RepairFormEntity formFromDto = repairFormConverter.getFormFromDto(repairFormDto);
        formFromDto.setStatus(Status.NEW);
        repairFormService.addRepairForm(formFromDto);
        return "redirect:/repairs/list";
    }


    @GetMapping("/view/{repairFormId}")
    public ModelAndView showTicket(@PathVariable String repairFormId) {
        RepairFormEntity repairForm = repairFormService.getRepairForm(Integer.parseInt(repairFormId));

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("repairForm", repairForm);
        modelAndView.setViewName("repairFormView");

        return modelAndView;
    }
}
