package com.zhuravlov.repairagency.repository;

import com.zhuravlov.repairagency.entity.RepairFormEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepairFormRepository extends JpaRepository<RepairFormEntity, Integer> {

    Page<RepairFormEntity> findByAuthor_userId(int id, Pageable pageable);

    Page<RepairFormEntity> findByRepairmanId(int id, Pageable pageable);

}
