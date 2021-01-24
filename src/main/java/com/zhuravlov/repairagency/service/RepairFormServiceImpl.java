package com.zhuravlov.repairagency.service;

import com.zhuravlov.repairagency.entity.RepairFormEntity;
import com.zhuravlov.repairagency.repository.RepairFormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class RepairFormServiceImpl implements RepairFormService {

    @Autowired
    private RepairFormRepository repository;

    @Override
    public List<RepairFormEntity> findUserRepairForms(int id) {
        return repository.findRepairFormEntityByAuthor_userId(id);
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
}