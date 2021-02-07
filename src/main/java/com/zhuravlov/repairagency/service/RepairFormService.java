package com.zhuravlov.repairagency.service;

import com.zhuravlov.repairagency.model.DTO.FilterDto;
import com.zhuravlov.repairagency.model.RepairFormSpecs;
import com.zhuravlov.repairagency.model.entity.RepairFormEntity;
import com.zhuravlov.repairagency.model.entity.Status;
import com.zhuravlov.repairagency.model.exception.RepairFormNotFoundException;
import com.zhuravlov.repairagency.repository.RepairFormRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class RepairFormService {

    @Autowired
    private RepairFormRepository repository;


    public Page<RepairFormEntity> findAllPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Pageable pageable = getPageable(pageNo, pageSize, sortField, sortDirection);
        return repository.findAll(pageable);
    }


    public Page<RepairFormEntity> findUserRepairFormsPaginated(int id, int pageNo, int pageSize, String sortField, String sortDirection) {
        Pageable pageable = getPageable(pageNo, pageSize, sortField, sortDirection);
        return repository.findByAuthor_userId(id, pageable);
    }


    public Page<RepairFormEntity> findRepairmanForms(int id, int pageNo, int pageSize, String sortField, String sortDirection) {
        Pageable pageable = getPageable(pageNo, pageSize, sortField, sortDirection);
        return repository.findByRepairman_UserId(id, pageable);
    }


    public RepairFormEntity addRepairForm(RepairFormEntity repairForm) {
        repository.save(repairForm);
        return repairForm;
    }

    @Transactional
    public RepairFormEntity updateRepairForm(RepairFormEntity repairFormEntity) {
        repository.save(repairFormEntity);
        return repairFormEntity;
    }


    public RepairFormEntity getRepairForm(int id) {
        Optional<RepairFormEntity> repairFormOptional = repository.findById(id);
        if (repairFormOptional.isEmpty()) {
            throw new RepairFormNotFoundException();
        }
        return repairFormOptional.get();
    }


    public List<RepairFormEntity> saveAll(List<RepairFormEntity> repairForms) {
        repository.saveAll(repairForms);
        return repairForms;
    }


    public Page<RepairFormEntity> findByStatus(Status status, int pageNo, int pageSize, String sortField, String sortDirection) {
        Pageable pageable = getPageable(pageNo, pageSize, sortField, sortDirection);
        Page<RepairFormEntity> byStatus = repository.findByStatus(status, pageable);
        if (byStatus.isEmpty()) {
            throw new RepairFormNotFoundException();
        }
        return byStatus;
    }


    public List<RepairFormEntity> findAll() {
        List<RepairFormEntity> all = repository.findAll();
        if (all.isEmpty()) {
            throw new RepairFormNotFoundException();
        }
        return all;
    }

    public List<Status> findAllStatuses() {
        return Arrays.asList(Status.values());
    }

    public Page<RepairFormEntity> findFiltered(Integer masterId, Status status, int pageNo, int pageSize, String sortField, String sortDir) {
        Pageable pageable = getPageable(pageNo, pageSize, sortField, sortDir);

        Specification<RepairFormEntity> masterSpec = RepairFormSpecs.masterEquals(masterId);
        Specification<RepairFormEntity> statusSpec = RepairFormSpecs.statusEquals(status);

        Specification<RepairFormEntity> repairFormEntitySpecification = Specification.where(masterSpec).and(statusSpec);

        return repository.findAll(repairFormEntitySpecification, pageable);
    }

/*
    public Page<RepairFormEntity> findFiltered(FilterDto filterRequest, int pageNo, int pageSize, String sortField, String sortDir) {
        log.info(filterRequest.toString());
        Pageable pageable = getPageable(pageNo, pageSize, sortField, sortDir);

        String masterId = filterRequest.getMasterId();
        String status = filterRequest.getStatus();

        boolean statusIsNotPresent = status == null || status.isEmpty();
        boolean masterIdIsNotPresent = masterId == null || masterId.isEmpty();

        Page<RepairFormEntity> page;

        if (masterIdIsNotPresent && !statusIsNotPresent) {
            page = repository.findByStatus(Status.valueOf(status), pageable);
        } else if (statusIsNotPresent && !masterIdIsNotPresent) {
            page = repository.findByRepairman_UserId(Integer.parseInt(masterId), pageable);
        } else if (!statusIsNotPresent) {
            page = repository.findByRepairman_UserIdAndStatus(Integer.parseInt(masterId), Status.valueOf(status), pageable);
        } else {
            page = findAllPaginated(pageNo, pageSize, sortField, sortDir);
        }
        if (page == null) {
            throw new RepairFormNotFoundException("Repairs form not found");
        }

        return page;
    }*/

    private Pageable getPageable(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection
                .equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        return PageRequest.of(pageNo - 1, pageSize, sort);
    }
}
