package com.zhuravlov.repairagency.service;

import com.zhuravlov.repairagency.entity.RepairFormEntity;
import com.zhuravlov.repairagency.repository.RepairFormRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
public class RepairFormServiceImpl implements RepairFormService {

    @Autowired
    private RepairFormRepository repository;

    @Override
    public List<RepairFormEntity> findUserRepairForms(int id) {
        List<RepairFormEntity> repairFormsList = repository.findRepairFormEntityByAuthor_userId(id);
        log.info("Loaded from db: " + repairFormsList.toString());
        return repairFormsList;
    }

    @Override
    public List<RepairFormEntity> findRepairmansForms(int id) {
        return repository.findRepairFormEntityByRepairmanId(id);
    }

    @Override
    public void addRepairForm(RepairFormEntity repairForm) {
        repository.save(repairForm);
    }

    @Override
    public RepairFormEntity getRepairForm(int id) {
        Optional<RepairFormEntity> repairFormOptional = repository.findById(id);
        if (repairFormOptional.isEmpty()) {
            throw new NoSuchElementException("Repair form not found");
        }
        return repairFormOptional.get();
    }

    @Override
    public List<RepairFormEntity> getRepairForms() {
        return repository.findAll();
    }
}
