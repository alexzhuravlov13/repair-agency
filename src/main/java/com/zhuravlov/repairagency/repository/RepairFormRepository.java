package com.zhuravlov.repairagency.repository;

import com.zhuravlov.repairagency.model.entity.RepairFormEntity;
import com.zhuravlov.repairagency.model.entity.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepairFormRepository extends JpaRepository<RepairFormEntity, Integer> {

    Page<RepairFormEntity> findByAuthor_userId(int id, Pageable pageable);

    Page<RepairFormEntity> findByRepairmanId(int id, Pageable pageable);

    Page<RepairFormEntity> findByStatus(Status status, Pageable pageable);

    Page<RepairFormEntity> findByRepairmanIdAndStatus(int id, Status status, Pageable pageable);

}
