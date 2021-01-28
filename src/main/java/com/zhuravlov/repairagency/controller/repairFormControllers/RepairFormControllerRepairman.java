package com.zhuravlov.repairagency.controller.repairFormControllers;

import com.zhuravlov.repairagency.controller.Util.ControllerUtil;
import com.zhuravlov.repairagency.entity.RepairFormEntity;
import com.zhuravlov.repairagency.service.RepairFormService;
import com.zhuravlov.repairagency.service.UserService;
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
@RequestMapping("/repairs")
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

    @GetMapping("/repairman/list")
    public ModelAndView getRepairmanForms(Model model) {
        userName = controllerUtil.getUserName();
        log.info("--User:" + userName + " entered /manager/list endpoint");
        return findRepairmanFormsPaginated(1);
    }

    @GetMapping("/repairman/list/page/{pageNo}")
    public ModelAndView findRepairmanFormsPaginated(@PathVariable(value = "pageNo") int pageNo) {
        log.info("--User:" + userName + " entered /manager/list/page/" + pageNo + " endpoint");
        int pageSize = 10;

        int repairmanId = getIdFromDbByAuthentication();
        String basePath = "/repairs/repairman/list";

        Page<RepairFormEntity> page =
                repairFormService.findRepairmanForms(repairmanId, pageNo, pageSize);
        return controllerUtil.getModelAndViewForList(basePath, pageNo, page);
    }

    private int getIdFromDbByAuthentication() {
        if (userName.isEmpty()) {
            userName = controllerUtil.getUserName();
        }
        return userService.findByUsername(userName).getUserId();
    }


}
