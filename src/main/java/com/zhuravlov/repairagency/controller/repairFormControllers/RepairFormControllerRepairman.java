package com.zhuravlov.repairagency.controller.repairFormControllers;

import com.zhuravlov.repairagency.controller.Util.ControllerUtil;
import com.zhuravlov.repairagency.model.entity.RepairFormEntity;
import com.zhuravlov.repairagency.model.entity.Status;
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

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/repairs/repairman")
@PreAuthorize("hasAuthority('ROLE_REPAIRMAN')")
@Slf4j
public class RepairFormControllerRepairman {
    String userName = "";

    @Autowired
    private ControllerUtil controllerUtil;

    @Autowired
    private RepairFormService repairFormService;

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public ModelAndView getRepairmanForms(Model model) {
        userName = controllerUtil.getUserName();
        log.info("--User:" + userName + " entered /repairman/list endpoint");
        return findRepairmanFormsPaginated(1, "creationDate", "desc");
    }

    @GetMapping("/list/page/{pageNo}")
    public ModelAndView findRepairmanFormsPaginated(@PathVariable(value = "pageNo") int pageNo,
                                                    @RequestParam("sortField") String sortField,
                                                    @RequestParam("sortDir") String sortDir) {
        log.info("--User:" + userName + " entered /repairman/list/page/" + pageNo + " endpoint");
        int pageSize = 10;

        int repairmanId = getIdFromDbByAuthentication();
        String basePath = "/repairs/repairman/list";

        Page<RepairFormEntity> page =
                repairFormService.findRepairmanForms(repairmanId, pageNo, pageSize, sortField, sortDir);
        log.info("Loaded: " + page.getContent().toString());
        return getModelAndView(pageNo, sortField, sortDir, basePath, page, controllerUtil);
    }

    @PreAuthorize("hasAuthority('ROLE_REPAIRMAN')")
    @GetMapping("/edit/{repairFormId}")
    public String editRepairForm(Model model, @PathVariable String repairFormId) {
        log.info("--User:" + userName + " entered /repairman/edit/" + repairFormId + " endpoint");

        List<Status> statuses = Arrays.asList(Status.IN_PROGRESS, Status.READY);

        RepairFormEntity repairForm =
                repairFormService.getRepairForm(Integer.parseInt(repairFormId));

        model.addAttribute("statuses", statuses);
        model.addAttribute("repairFormAttribute", repairForm);
        return "repairFormRepairman";
    }

    @PostMapping("/editRepairForm")
    public String saveEditedRepairForm(Model model, @ModelAttribute("repairFormAttribute")
    @Validated RepairFormEntity repairFormEntity, BindingResult bindingResult) {
        log.info("--User:" + userName + " entered /repairman/editRepairForm/ endpoint");
        if (bindingResult.hasErrors()) {
            model.addAttribute("repairFormAttribute", repairFormEntity);
            return "redirect:/repairs/edit/" + repairFormEntity.getId() + "?error";
        }

        repairFormEntity.setLastModifiedDate(LocalDateTime.now());
        repairFormService.addRepairForm(repairFormEntity);
        log.info("--User:" + userName + " edited repair form id:" + repairFormEntity.getId());
        return "redirect:/repairs/repairman/list";

    }

    private ModelAndView getModelAndView(@PathVariable("pageNo") int pageNo, @RequestParam("sortField") String sortField, @RequestParam("sortDir") String sortDir, String basePath, Page<RepairFormEntity> page, ControllerUtil controllerUtil) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndViewAddPaginatedAtributes(sortField, sortDir, modelAndView);
        return controllerUtil.
                getModelAndViewAttributesForFormList(basePath, pageNo, page, page.getContent(), modelAndView);
    }

    private void modelAndViewAddPaginatedAtributes(@RequestParam("sortField") String sortField, @RequestParam("sortDir") String sortDir, ModelAndView modelAndView) {
        modelAndView.setViewName("repairFormUserList");
        modelAndView.addObject("sortField", sortField);
        modelAndView.addObject("sortDir", sortDir);
        modelAndView.addObject("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
    }

    private int getIdFromDbByAuthentication() {
        if (userName.isEmpty()) {
            userName = controllerUtil.getUserName();
        }
        return userService.findByUsername(userName).getUserId();
    }


}
