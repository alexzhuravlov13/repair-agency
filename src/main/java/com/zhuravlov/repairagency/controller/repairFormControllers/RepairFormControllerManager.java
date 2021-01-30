package com.zhuravlov.repairagency.controller.repairFormControllers;

import com.zhuravlov.repairagency.controller.Util.ControllerUtil;
import com.zhuravlov.repairagency.entity.DTO.FilterDto;
import com.zhuravlov.repairagency.entity.RepairFormEntity;
import com.zhuravlov.repairagency.entity.Status;
import com.zhuravlov.repairagency.entity.UserEntity;
import com.zhuravlov.repairagency.service.RepairFormService;
import com.zhuravlov.repairagency.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@PreAuthorize("hasAuthority('ROLE_MANAGER')")
@RequestMapping("/repairs/manager")
@Slf4j
public class RepairFormControllerManager {
    String userName = "";

    @Autowired
    private ControllerUtil controllerUtil;

    @Autowired
    private RepairFormService repairFormService;

    @Autowired
    private UserService userService;

    private List<Status> allStatuses;

    private List<UserEntity> allMasters;

    private FilterDto filterRequest;

    private void initLists() {
        allMasters = userService.findUsersByRole("ROLE_REPAIRMAN");
        allStatuses = repairFormService.findAllStatuses();
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
        return "redirect:/repairs/manager/list";
    }

    @GetMapping("/list/clear")
    public String clearFilters() {
        filterRequest = null;
        return "redirect:/repairs/manager/list";
    }

    @GetMapping("/list/page/{pageNo}")
    public ModelAndView findAllRepairsPaginated(@PathVariable(value = "pageNo") int pageNo,
                                                @RequestParam("sortField") String sortField,
                                                @RequestParam("sortDir") String sortDir) {
        log.info("--User:" + userName + " entered /manager/list/page/" + pageNo + " endpoint");

        int pageSize = 10;

        Page<RepairFormEntity> page;

        if (filterRequest != null) {
            page = repairFormService.findFiltered(filterRequest, pageNo, pageSize, sortField, sortDir);
        } else {
            page = repairFormService.findAllPaginated(pageNo, pageSize, sortField, sortDir);
        }
        String basePath = "/repairs/manager/list";

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("repairFormUserList");
        modelAndView.addObject("filterDto", new FilterDto());
        modelAndView.addObject("masters", allMasters);
        modelAndView.addObject("statuses", allStatuses);
        modelAndView.addObject("sortField", sortField);
        modelAndView.addObject("sortDir", sortDir);
        modelAndView.addObject("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        return controllerUtil.
                getModelAndViewAttributesForFormList(basePath, pageNo, page, page.getContent(), modelAndView);
    }

    @GetMapping("/list/filter/page/{pageNo}")
    public ModelAndView findFiltered(@PathVariable(value = "pageNo") int pageNo,
                                     @RequestParam("filterField") String filterField,
                                     @RequestParam("sortField") String sortField,
                                     @RequestParam("sortDir") String sortDir) {
        log.info("--User:" + userName + " entered /manager/list/page/" + pageNo + " endpoint");
        int pageSize = 10;
        Page<RepairFormEntity> page = getPagesByFilterField(pageNo, filterField, sortField, sortDir, pageSize);

        String basePath = "/repairs/manager/list";

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("repairFormUserList");
        modelAndView.addObject("sortField", sortField);
        modelAndView.addObject("sortDir", sortDir);
        modelAndView.addObject("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        return controllerUtil.
                getModelAndViewAttributesForFormList(basePath, pageNo, page, page.getContent(), modelAndView);
    }

    private Page<RepairFormEntity> getPagesByFilterField(@PathVariable("pageNo") int pageNo, @RequestParam("filterField") String filterField, @RequestParam("sortField") String sortField, @RequestParam("sortDir") String sortDir, int pageSize) {
        Page<RepairFormEntity> page;
        if (filterField.toLowerCase().equals("master")) {
            page = repairFormService.findRepairmanForms(Integer.parseInt(filterField), pageNo, pageSize, sortField, sortDir);
        } else if (filterField.toLowerCase().equals("status")) {
            page = repairFormService.findByStatus(Status.valueOf(filterField), pageNo, pageSize, sortField, sortDir);
        } else page = null;
        return page;
    }
}
