package com.zhuravlov.repairagency.controller.Util;

import com.zhuravlov.repairagency.entity.RepairFormEntity;
import com.zhuravlov.repairagency.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Component
@Slf4j
public class ControllerUtil {
    public String getUserName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public ModelAndView getModelAndViewForList(String basePath, @PathVariable("pageNo") int pageNo, Page<RepairFormEntity> page) {
        List<RepairFormEntity> repairFormList = page.getContent();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("repairFormUserList");
        return this.getModelAndViewAttributesForFormList(basePath, pageNo, page, repairFormList, modelAndView);
    }


    public ModelAndView getModelAndViewAttributesForFormList(String basePath, @PathVariable("pageNo") int pageNo, Page<RepairFormEntity> page, List<RepairFormEntity> repairFormList, ModelAndView modelAndView) {
        modelAndView.addObject("basePath", basePath);
        modelAndView.addObject("currentPage", pageNo);
        modelAndView.addObject("totalPages", page.getTotalPages());
        modelAndView.addObject("totalItems", page.getTotalElements());

        modelAndView.addObject("repairForms", repairFormList);
        log.info("--User:" + getUserName() + " Loaded FormListSize:" + repairFormList.size());
        return modelAndView;
    }


    public ModelAndView getModelAndViewAttributesForUserList(String basePath, @PathVariable("pageNo") int pageNo, Page<UserEntity> page, List<UserEntity> userEntityList, ModelAndView modelAndView) {
        modelAndView.addObject("basePath", basePath);
        modelAndView.addObject("currentPage", pageNo);
        modelAndView.addObject("totalPages", page.getTotalPages());
        modelAndView.addObject("totalItems", page.getTotalElements());

        modelAndView.addObject("users", userEntityList);
        log.info("--User:" + getUserName() + " Loaded UsersListSize:" + userEntityList.size());
        return modelAndView;
    }

}
