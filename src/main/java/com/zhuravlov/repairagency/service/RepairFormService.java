package com.zhuravlov.repairagency.service;

import com.zhuravlov.repairagency.entity.RepairFormEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RepairFormService {

    Page<RepairFormEntity> findAllPaginated(int pageNo, int pageSize);

    Page<RepairFormEntity> findUserRepairFormsPaginated(int id, int pageNo, int pageSize);

    Page<RepairFormEntity> findRepairmanForms(int id, int pageNo, int pageSize);

    void addRepairForm(RepairFormEntity formFromDto);

    RepairFormEntity getRepairForm(int id);

    void saveAll(List<RepairFormEntity> repairForms);
}
