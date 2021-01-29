package com.zhuravlov.repairagency.service;

import com.zhuravlov.repairagency.entity.DTO.FilterDto;
import com.zhuravlov.repairagency.entity.RepairFormEntity;
import com.zhuravlov.repairagency.entity.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RepairFormService {


    Page<RepairFormEntity> findAllPaginated(int pageNo, int pageSize, String sortField, String sortDirection);

    Page<RepairFormEntity> findUserRepairFormsPaginated(int id, int pageNo, int pageSize, String sortField, String sortDirection);

    Page<RepairFormEntity> findRepairmanForms(int id, int pageNo, int pageSize, String sortField, String sortDirection);

    void addRepairForm(RepairFormEntity formFromDto);

    RepairFormEntity getRepairForm(int id);

    void saveAll(List<RepairFormEntity> repairForms);

    Page<RepairFormEntity> findByStatus(Status status, int pageNo, int pageSize, String sortField, String sortDirection);

    List<RepairFormEntity> findAll();

    List<Status> findAllStatuses();

    Page<RepairFormEntity> findFiltered(FilterDto filterRequest, int pageNo, int pageSize, String sortField, String sortDir);
}
