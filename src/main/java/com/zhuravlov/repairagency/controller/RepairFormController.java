package com.zhuravlov.repairagency.controller;

import com.zhuravlov.repairagency.entity.DTO.RepairFormDto;
import com.zhuravlov.repairagency.entity.RepairFormEntity;
import com.zhuravlov.repairagency.entity.converter.RepairFormConverter;
import com.zhuravlov.repairagency.service.RepairFormService;
import com.zhuravlov.repairagency.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/repairs")
@Slf4j
public class RepairFormController {

    @Autowired
    private RepairFormService repairFormService;

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET, value = "/list")
    public ModelAndView getRepairFormList() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        int id = userService.findByUsername(userName).getUserId();

        List<RepairFormEntity> usersRepairForms = repairFormService.findUserRepairForms(id);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("repairForms", usersRepairForms);
        modelAndView.setViewName("repaiFormUserList");
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/add")
    public String addTicketForm(Model model, @RequestParam("userId") int userId) {
        RepairFormDto repairFormDto = new RepairFormDto();
        repairFormDto.setAuthorId(userId);
        model.addAttribute("repairFormAttribute", repairFormDto);
        return "repairFormAdd";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/addTicket")
    public String addTicket(Model model, @ModelAttribute("ticketAttribute")
    @Validated RepairFormDto repairFormDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "repairFormAdd";
        }
        RepairFormConverter repairFormConverter = new RepairFormConverter();
        RepairFormEntity formFromDto = repairFormConverter.getFormFromDto(repairFormDto);
        repairFormService.addRepairForm(formFromDto);
        return "redirect:repaiFormUserList";
    }


    @RequestMapping(method = RequestMethod.GET, value = "/{repairFormId}")
    @GetMapping("/view/{repairFormId}")
    public ModelAndView showTicket(@PathVariable String repairFormId) {
        RepairFormEntity repairForm = repairFormService.getRepairForm(Integer.parseInt(repairFormId));

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("repairForm", repairForm);
        modelAndView.setViewName("repairFormView");

        return modelAndView;
    }
}
