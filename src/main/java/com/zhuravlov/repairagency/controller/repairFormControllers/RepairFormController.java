package com.zhuravlov.repairagency.controller.repairFormControllers;

import com.zhuravlov.repairagency.controller.Util.ControllerUtil;
import com.zhuravlov.repairagency.model.DTO.RepairFormDto;
import com.zhuravlov.repairagency.model.builder.RepairFormDtoBuilder;
import com.zhuravlov.repairagency.model.converter.RepairFormConverter;
import com.zhuravlov.repairagency.model.entity.RepairFormEntity;
import com.zhuravlov.repairagency.model.entity.Status;
import com.zhuravlov.repairagency.service.RepairFormService;
import com.zhuravlov.repairagency.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    String userName = "";

    int pageSize = 10;

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
        int userId = getIdFromDbByAuthentication();

        Page<RepairFormEntity> page = repairFormService.findUserRepairFormsPaginated(userId, pageNo, pageSize, sortField, sortDir);
        List<RepairFormEntity> repairFormList = page.getContent();

        int id = getIdFromDbByAuthentication();

        String amount = String.valueOf(userService.getUser(id).getAmount());
        String basePath = "/repairs/list";

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("statusReady", Status.READY);

        paginatedListModelAddAttributes(sortField, sortDir, amount, modelAndView);
        log.info("Loaded: " + page.getContent().toString());

        return controllerUtil.
                getModelAndViewAttributesForFormList(basePath, pageNo, page, repairFormList, modelAndView);
    }

    @GetMapping("/add")
    public String addRepairForm(Model model) {
        log.info("--User:" + userName + " entered /add endpoint");
        RepairFormDto repairFormDto = new RepairFormDtoBuilder()
                .setAuthorId(getIdFromDbByAuthentication())
                .setCreationDate(LocalDateTime.now())
                .build();

        model.addAttribute("repairFormAttribute", repairFormDto);
        return "repairFormAdd";
    }

    @PostMapping("/addRepairForm")
    public String saveRepairForm(Model model, @ModelAttribute("repairFormAttribute")
    @Validated RepairFormDto repairFormDto, BindingResult bindingResult) {
        log.info("--User:" + userName + " entered /addRepairForm endpoint");

        if (bindingResult.hasErrors()) {
            model.addAttribute("repairFormAttribute", repairFormDto);
            return "repairFormAdd";
        }

        RepairFormEntity formFromDto = repairFormConverter.getFormFromDto(repairFormDto, Status.NEW);
        repairFormService.addRepairForm(formFromDto);
        log.info("--User:" + userName + " add new repairForm" + formFromDto.toString());

        return "redirect:/repairs/list";
    }


    @GetMapping("/view/{repairFormId}")
    public ModelAndView showTicket(@PathVariable String repairFormId) {
        RepairFormEntity repairForm = repairFormService.getRepairForm(Integer.parseInt(repairFormId));
        log.info("--User:" + userName + "entered /view/" + repairFormId + " endpoint");

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("repairForm", repairForm);
        modelAndView.setViewName("repairFormView");
        return modelAndView;
    }

    @GetMapping("/review/{repairFormId}")
    public String reviewForm(Model model, @PathVariable String repairFormId) {
        log.info("--User:" + userName + "entered /review/" + repairFormId + " endpoint");
        RepairFormEntity repairForm = repairFormService.getRepairForm(Integer.parseInt(repairFormId));
        model.addAttribute("repairFormAttribute", repairForm);
        return "repairFormReview";
    }

    @PostMapping("/saveReview")
    public String reviewSave(@ModelAttribute("repairFormAttribute")
                             @Validated RepairFormEntity repairFormEntity) {
        log.info("--User:" + userName + "entered /saveReview endpoint");
        repairFormService.updateRepairForm(repairFormEntity);
        log.info("--User:" + userName + " edited repair form id:" + repairFormEntity.getId());
        return "redirect:/repairs/list";
    }


    private int getIdFromDbByAuthentication() {
        if (userName.isEmpty()) {
            userName = controllerUtil.getUserName();
        }
        return userService.findByUsername(userName).getUserId();
    }

    private void paginatedListModelAddAttributes(@RequestParam("sortField") String sortField, @RequestParam("sortDir") String sortDir, String amount, ModelAndView modelAndView) {
        modelAndView.setViewName("repairFormUserList");
        modelAndView.addObject("amount", amount);
        modelAndView.addObject("sortField", sortField);
        modelAndView.addObject("sortDir", sortDir);
        modelAndView.addObject("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
    }


}
