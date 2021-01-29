package com.zhuravlov.repairagency.service;

import com.zhuravlov.repairagency.entity.DTO.FilterDto;
import com.zhuravlov.repairagency.entity.RepairFormEntity;
import com.zhuravlov.repairagency.entity.Status;
import com.zhuravlov.repairagency.repository.RepairFormRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
public class RepairFormServiceImpl implements RepairFormService {

    @Autowired
    private RepairFormRepository repository;

    @Override
    public Page<RepairFormEntity> findAllPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Pageable pageable = getPageable(pageNo, pageSize, sortField, sortDirection);
        return repository.findAll(pageable);
    }

    @Override
    public Page<RepairFormEntity> findUserRepairFormsPaginated(int id, int pageNo, int pageSize, String sortField, String sortDirection) {
        Pageable pageable = getPageable(pageNo, pageSize, sortField, sortDirection);
        return repository.findByAuthor_userId(id, pageable);
    }

    @Override
    public Page<RepairFormEntity> findRepairmanForms(int id, int pageNo, int pageSize, String sortField, String sortDirection) {
        Pageable pageable = getPageable(pageNo, pageSize, sortField, sortDirection);
        return repository.findByRepairmanId(id, pageable);
    }

    private Pageable getPageable(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection
                .equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        return PageRequest.of(pageNo - 1, pageSize, sort);
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

    @Override
    public Page<RepairFormEntity> findByStatus(Status status, int pageNo, int pageSize, String sortField, String sortDirection) {
        Pageable pageable = getPageable(pageNo, pageSize, sortField, sortDirection);
        return repository.findByStatus(status, pageable);
    }

    @Override
    public List<RepairFormEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Status> findAllStatuses() {
        return Arrays.asList(Status.values());
    }

    @Override
    public Page<RepairFormEntity> findFiltered(FilterDto filterRequest, int pageNo, int pageSize, String sortField, String sortDir) {
        log.info(filterRequest.toString());
        Pageable pageable = getPageable(pageNo, pageSize, sortField, sortDir);

        String masterId = filterRequest.getMasterId();
        String status = filterRequest.getStatus();

        boolean statusIsNotPresent = status == null || status.isEmpty();
        boolean masterIdIsNotPresent = masterId == null || masterId.isEmpty();

        if (masterIdIsNotPresent && !statusIsNotPresent) {
            return repository.findByStatus(Status.valueOf(status), pageable);
        } else if (statusIsNotPresent && !masterIdIsNotPresent) {
            return repository.findByRepairmanId(Integer.parseInt(masterId), pageable);
        } else if (!statusIsNotPresent) {
            return repository.findByRepairmanIdAndStatus(Integer.parseInt(masterId), Status.valueOf(status), pageable);
        }
        return findAllPaginated(pageNo, pageSize, sortField, sortDir);
    }
}
