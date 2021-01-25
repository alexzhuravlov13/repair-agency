package com.zhuravlov.repairagency.entity.converter;

import com.zhuravlov.repairagency.entity.DTO.RepairFormDto;
import com.zhuravlov.repairagency.entity.RepairFormEntity;
import com.zhuravlov.repairagency.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public class RepairFormConverter {

    @Autowired
    private UserService userService;

    public RepairFormEntity getFormFromDto(RepairFormDto repairFormDto) {
        RepairFormEntity repairForm = new RepairFormEntity();
        repairForm.setAuthor(userService.getUser(repairFormDto.getAuthorId()));
        repairForm.setCreationDate(repairFormDto.getCreationDate());
        repairForm.setDescription(repairFormDto.getDescription());
        return repairForm;
    }
}
