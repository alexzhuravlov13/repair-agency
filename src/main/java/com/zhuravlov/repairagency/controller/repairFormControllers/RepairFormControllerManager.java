package com.zhuravlov.repairagency.controller.repairFormControllers;

import com.zhuravlov.repairagency.controller.Util.ControllerUtil;
import com.zhuravlov.repairagency.model.DTO.FilterDto;
import com.zhuravlov.repairagency.model.DTO.MastersAndStatusesDto;
import com.zhuravlov.repairagency.model.entity.RepairFormEntity;
import com.zhuravlov.repairagency.model.entity.Status;
import com.zhuravlov.repairagency.model.entity.UserEntity;
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

@Controller
@PreAuthorize("hasAuthority('ROLE_MANAGER')")
@RequestMapping("/repairs/manager")
@Slf4j
public class RepairFormControllerManager {
    String userName = "";

    int pageSize = 10;

    @Autowired
    private ControllerUtil controllerUtil;

    @Autowired
    private RepairFormService repairFormService;

    @Autowired
    private UserService userService;

    private List<Status> allStatuses;

    private List<UserEntity> allMasters;

    private FilterDto  filterRequest = new FilterDto();

    private void initLists() {
        MastersAndStatusesDto mastersAndStatuses = userService.getMastersAndStatuses();
        allMasters = mastersAndStatuses.getMasters();
        allStatuses = mastersAndStatuses.getStatuses();
    }

    @GetMapping("/list")
    public ModelAndView getAllRepairForms() {
        initLists();
        userName = controllerUtil.getUserName();
        log.info("--User:" + userName + " entered /manager/list endpoint");
        return findAllRepairsPaginated(1, "creationDate", "desc");
    }

    @PostMapping("/list")
    public String getAllRepairForms(@ModelAttribute("filterDto") FilterDto filterDto) {
        filterRequest = filterDto;
        log.info("--User:" + userName + " entered manager/list post endpoint");
        log.info("--Filters:" + filterDto);
        return "redirect:/repairs/manager/list";
    }

    @GetMapping("/list/clear")
    public String clearFilters() {
        filterRequest = new FilterDto();
        log.info("--User:" + userName + " entered manager/list/clear endpoint");
        return "redirect:/repairs/manager/list";
    }

    @GetMapping("/list/page/{pageNo}")
    public ModelAndView findAllRepairsPaginated(@PathVariable(value = "pageNo") int pageNo,
                                                @RequestParam("sortField") String sortField,
                                                @RequestParam("sortDir") String sortDir) {
        log.info("--User:" + userName + " entered /manager/list/page/" + pageNo + " endpoint");

        Page<RepairFormEntity> page
                = repairFormService.findFiltered(filterRequest.getMasterId(), filterRequest.getStatus(), pageNo, pageSize, sortField, sortDir);

        String basePath = "/repairs/manager/list";

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("filterDto", new FilterDto());
        modelAndView.addObject("masters", allMasters);
        modelAndView.addObject("statuses", allStatuses);
        return getModelAndView(pageNo, sortField, sortDir, page, basePath, modelAndView);
    }

    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    @GetMapping("/edit/{repairFormId}")
    public String editManagerRepairForm(Model model, @PathVariable String repairFormId) {
        log.info("--User:" + userName + " entered /manager/edit/" + repairFormId + " endpoint");

        List<Status> statuses = getManagerStatuses();

        RepairFormEntity repairForm = repairFormService.getRepairForm(Integer.parseInt(repairFormId));

        editFormAddAttributes(model, statuses, repairForm, allMasters);
        return "repairFormEdit";
    }

    private List<Status> getManagerStatuses() {
        return Arrays.asList(Status.CANCELED, Status.PAID, Status.WAITING_FOR_PAYMENT);
    }

    @PostMapping("/editRepairForm")
    public String saveEditedRepairForm(Model model, @ModelAttribute("repairFormAttribute")
    @Validated RepairFormEntity repairFormEntity, BindingResult bindingResult) {
        log.info("--User:" + userName + " entered /manager/editRepairForm endpoint");

        if (bindingResult.hasErrors()) {
            return addRepairFormAndReturnBackToEdit(model, repairFormEntity);
        }

        if (repairFormEntity.getStatus().equals(Status.PAID)) {

            BigDecimal newAmount =
                    repairFormEntity.getAuthor().getAmount().subtract(repairFormEntity.getPrice());

            if (notEnoughMoney(repairFormEntity, newAmount)) {
                return addRepairFormAndReturnBackToEdit(model, repairFormEntity);
            }
            repairFormEntity.getAuthor().setAmount(newAmount);

        }

        repairFormEntity.setLastModifiedDate(LocalDateTime.now());
        repairFormService.addRepairForm(repairFormEntity);
        log.info("--User:" + userName + " edited repair form id:" + repairFormEntity.getId());
        return "redirect:/repairs/manager/list";

    }

    private ModelAndView getModelAndView(@PathVariable("pageNo") int pageNo, @RequestParam("sortField") String sortField, @RequestParam("sortDir") String sortDir, Page<RepairFormEntity> page, String basePath, ModelAndView modelAndView) {
        modelAndView.setViewName("repairFormUserList");
        modelAndView.addObject("sortField", sortField);
        modelAndView.addObject("sortDir", sortDir);
        modelAndView.addObject("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        return controllerUtil.
                getModelAndViewAttributesForFormList(basePath, pageNo, page, page.getContent(), modelAndView);
    }


    private void editFormAddAttributes(Model model, List<Status> statuses, RepairFormEntity repairForm, List<UserEntity> repairmans) {
        model.addAttribute("statusReady", Status.READY);
        model.addAttribute("statuses", statuses);
        model.addAttribute("repairFormAttribute", repairForm);
        model.addAttribute("repairmans", repairmans);
    }

    private boolean notEnoughMoney(@Validated @ModelAttribute("repairFormAttribute") RepairFormEntity repairFormEntity, BigDecimal newAmount) {
        return newAmount.compareTo(BigDecimal.ZERO) < 0;
    }

    private String addRepairFormAndReturnBackToEdit(Model model, @Validated @ModelAttribute("repairFormAttribute") RepairFormEntity repairFormEntity) {
        model.addAttribute("repairFormAttribute", repairFormEntity);
        return "redirect:/repairs/manager/edit/" + repairFormEntity.getId() + "?error";
    }
}
