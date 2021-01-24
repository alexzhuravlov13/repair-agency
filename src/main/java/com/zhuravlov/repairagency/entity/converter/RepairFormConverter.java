package com.zhuravlov.repairagency.entity.converter;

import com.zhuravlov.repairagency.entity.DTO.RepairFormDto;
import com.zhuravlov.repairagency.entity.RepairFormEntity;

public class RepairFormConverter {

    public static RepairFormEntity getFormFromDto(RepairFormDto repairFormDto) {
        RepairFormEntity repairForm = new RepairFormEntity();
        repairForm.setAuthorId(repairFormDto.getAuthorId());
        repairForm.setCreationDate(repairFormDto.getCreationDate());
        repairForm.setDescription(repairFormDto.getDescription());
        return repairForm;
    }
}
