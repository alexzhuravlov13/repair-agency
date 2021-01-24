package com.zhuravlov.repairagency.repository;

import com.zhuravlov.repairagency.entity.RepairFormEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepairFormRepository extends JpaRepository<RepairFormEntity, Integer> {

    List<RepairFormEntity> findRepairFormEntityByAuthor_userId(int id);

}
