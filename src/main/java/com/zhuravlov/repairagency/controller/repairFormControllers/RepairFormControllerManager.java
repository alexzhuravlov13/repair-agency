package com.zhuravlov.repairagency.controller.repairFormControllers;

import com.zhuravlov.repairagency.controller.Util.ControllerUtil;
import com.zhuravlov.repairagency.entity.RepairFormEntity;
import com.zhuravlov.repairagency.service.RepairFormService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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

    @GetMapping("/list")
    public ModelAndView getAllRepairForms(Model model) {
        userName = controllerUtil.getUserName();
        log.info("--User:" + userName + " entered /manager/list endpoint");
        return findAllRepairsPaginated(1);
    }

    @GetMapping("/list/page/{pageNo}")
    public ModelAndView findAllRepairsPaginated(@PathVariable(value = "pageNo") int pageNo) {
        log.info("--User:" + userName + " entered /manager/list/page/" + pageNo + " endpoint");
        int pageSize = 10;

        Page<RepairFormEntity> page = repairFormService.findAllPaginated(pageNo, pageSize);
        String basePath = "/repairs/manager/list";
        return controllerUtil.getModelAndViewForList(basePath, pageNo, page);
    }
}
