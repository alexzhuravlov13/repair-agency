package com.zhuravlov.repairagency.controller.repairFormControllers;

import com.zhuravlov.repairagency.controller.Util.ControllerUtil;
import com.zhuravlov.repairagency.entity.DTO.RepairFormDto;
import com.zhuravlov.repairagency.entity.RepairFormEntity;
import com.zhuravlov.repairagency.entity.RoleEntity;
import com.zhuravlov.repairagency.entity.Status;
import com.zhuravlov.repairagency.entity.UserEntity;
import com.zhuravlov.repairagency.entity.converter.RepairFormConverter;
import com.zhuravlov.repairagency.service.RepairFormService;
import com.zhuravlov.repairagency.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/repairs")
@Slf4j
public class RepairFormController {
    String userName = "";
    private boolean isManager;
    @Autowired
    private ControllerUtil controllerUtil;

    @Autowired
    private RepairFormService repairFormService;

    @Autowired
    private UserService userService;

    @Autowired
    private RepairFormConverter repairFormConverter;

    @GetMapping("/list")
    public ModelAndView getRepairFormList(Model model) {
        userName = controllerUtil.getUserName();
        log.info("--User:" + userName + " entered /list endpoint");
        return findUsersRepairsPaginated(1, "creationDate", "desc");
    }

    @GetMapping("/list/page/{pageNo}")
    public ModelAndView findUsersRepairsPaginated(
            @PathVariable(value = "pageNo") int pageNo,
            @RequestParam("sortField") String sortField,
            @RequestParam("sortDir") String sortDir) {
        log.info("--User:" + userName + " entered /list/page/" + pageNo + " endpoint");
        int pageSize = 10;
        int userId = getIdFromDbByAuthentication();

        Page<RepairFormEntity> page = repairFormService.findUserRepairFormsPaginated(userId, pageNo, pageSize, sortField, sortDir);
        List<RepairFormEntity> repairFormList = page.getContent();

        int id = getIdFromDbByAuthentication();

        String amount = String.valueOf(userService.getUser(id).getAmount());
        String basePath = "/repairs/list";

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("repairFormUserList");
        modelAndView.addObject("amount", amount);
        modelAndView.addObject("sortField", sortField);
        modelAndView.addObject("sortDir", sortDir);
        modelAndView.addObject("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        return controllerUtil.
                getModelAndViewAttributesForFormList(basePath, pageNo, page, repairFormList, modelAndView);
    }

    @GetMapping("/add")
    public String addRepairForm(Model model) {
        //TODO:make a builder
        RepairFormDto repairFormDto = new RepairFormDto(
                LocalDateTime.now(),
                getIdFromDbByAuthentication());
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

    @PreAuthorize("hasAuthority('ROLE_MANAGER') or hasAuthority('ROLE_REPAIRMAN')")
    @GetMapping("/edit/{repairFormId}")
    public String editRepairForm(Model model, @PathVariable String repairFormId) {

        int idFromDbByAuthentication = getIdFromDbByAuthentication();
        UserEntity user = userService.getUser(idFromDbByAuthentication);
        Set<RoleEntity> userRoles = user.getRoles();

        List<Status> statuses = null;
        isManager = false;

        if (userRoles.contains(new RoleEntity("ROLE_MANAGER"))) {
            isManager = true;
            statuses = Arrays.asList(Status.CANCELED, Status.PAID, Status.WAITING_FOR_PAYMENT);
        } else if (userRoles.contains(new RoleEntity("ROLE_REPAIRMAN"))) {
            statuses = Arrays.asList(Status.IN_PROGRESS, Status.READY);
        }

        RepairFormEntity repairForm = repairFormService.getRepairForm(Integer.parseInt(repairFormId));

        List<UserEntity> repairmans = userService.findUsersByRole("ROLE_REPAIRMAN");

        model.addAttribute("statuses", statuses);
        model.addAttribute("repairFormAttribute", repairForm);
        model.addAttribute("repairmans", repairmans);
        if (isManager) {
            return "repairFormEdit";
        }
        return "repairFormRepairman";
    }

    @PostMapping("/editRepairForm")
    public String saveEditedRepairForm(Model model, @ModelAttribute("repairFormAttribute")
    @Validated RepairFormEntity repairFormEntity, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("repairFormAttribute", repairFormEntity);
            return "repairFormEdit";
        }
        if (repairFormEntity.getStatus().equals(Status.PAID)) {
            BigDecimal newAmount = repairFormEntity.getAuthor().getAmount().subtract(repairFormEntity.getPrice());
            if (newAmount.compareTo(BigDecimal.ZERO) < 0) {
                //Todo:add message
                //TODO: user account service transactional
                return "repairFormEdit";
            }
            repairFormEntity.getAuthor().setAmount(newAmount);
        }
        repairFormEntity.setLastModifiedDate(LocalDateTime.now());
        repairFormService.addRepairForm(repairFormEntity);
        if (isManager) {
            return "redirect:/repairs/manager/list";
        }
        return "redirect:/repairs/repairman/list";

    }

    @GetMapping("/review/{repairFormId}")
    public String reviewForm(Model model, @PathVariable String repairFormId) {
        RepairFormEntity repairForm = repairFormService.getRepairForm(Integer.parseInt(repairFormId));
        model.addAttribute("repairFormAttribute", repairForm);
        return "repairFormReview";
    }

    @PostMapping("/saveReview")
    public String reviewSave(Model model, @ModelAttribute("repairFormAttribute")
    @Validated RepairFormEntity repairFormEntity) {
        RepairFormEntity repairForm = repairFormService.getRepairForm(repairFormEntity.getId());
        repairForm.setFeedback(repairFormEntity.getFeedback());
        repairFormService.addRepairForm(repairForm);
        return "redirect:/repairs/list";
    }

    private int getIdFromDbByAuthentication() {
        if (userName.isEmpty()) {
            userName = controllerUtil.getUserName();
        }
        return userService.findByUsername(userName).getUserId();
    }


}
