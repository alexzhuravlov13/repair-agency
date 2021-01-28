package com.zhuravlov.repairagency.service;

import com.zhuravlov.repairagency.entity.RepairFormEntity;
import com.zhuravlov.repairagency.repository.RepairFormRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public Page<RepairFormEntity> findAllPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return repository.findAll(pageable);
    }

    @Override
    public Page<RepairFormEntity> findUserRepairFormsPaginated(int id, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return repository.findByAuthor_userId(id, pageable);
    }

    @Override
    public Page<RepairFormEntity> findRepairmanForms(int id, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return repository.findByRepairmanId(id, pageable);
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
    public void saveAll(List<RepairFormEntity> repairForms) {
        repository.saveAll(repairForms);
    }
}
