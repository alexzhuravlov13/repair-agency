package com.zhuravlov.repairagency.model.converter;

import com.zhuravlov.repairagency.model.DTO.RepairFormDto;
import com.zhuravlov.repairagency.model.entity.RepairFormEntity;
import com.zhuravlov.repairagency.model.entity.Status;
import com.zhuravlov.repairagency.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RepairFormConverter {

    @Autowired
    private UserService userService;

    public RepairFormEntity getFormFromDto(RepairFormDto repairFormDto) {
        RepairFormEntity repairForm = new RepairFormEntity();
        repairForm.setAuthor(userService.getUser(repairFormDto.getAuthorId()));
        repairForm.setShortDescription(repairFormDto.getShortDescription());
        repairForm.setCar(repairFormDto.getCar());
        repairForm.setCreationDate(repairFormDto.getCreationDate());
        repairForm.setDescription(repairFormDto.getDescription());
        return repairForm;
    }

    public RepairFormEntity getFormFromDto(RepairFormDto repairFormDto, Status status) {
        RepairFormEntity repairForm = new RepairFormEntity();
        repairForm.setAuthor(userService.getUser(repairFormDto.getAuthorId()));
        repairForm.setShortDescription(repairFormDto.getShortDescription());
        repairForm.setCar(repairFormDto.getCar());
        repairForm.setStatus(status);
        repairForm.setCreationDate(repairFormDto.getCreationDate());
        repairForm.setDescription(repairFormDto.getDescription());
        return repairForm;
    }
}
