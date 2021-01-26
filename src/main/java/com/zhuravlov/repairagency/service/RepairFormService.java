package com.zhuravlov.repairagency.service;

import com.zhuravlov.repairagency.entity.RepairFormEntity;

import java.util.List;

public interface RepairFormService {
    List<RepairFormEntity> findUserRepairForms(int id);

    List<RepairFormEntity> findRepairmansForms(int id);


    void addRepairForm(RepairFormEntity formFromDto);

    RepairFormEntity getRepairForm(int id);

    List<RepairFormEntity> getRepairForms();
}
