package com.zhuravlov.repairagency.unit.service;

import com.zhuravlov.repairagency.model.entity.RepairFormEntity;
import com.zhuravlov.repairagency.model.entity.Status;
import com.zhuravlov.repairagency.repository.RepairFormRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RepairFormServiceTest {
    private final String SORT_FIElD_CREATION_DATE = "creationDate";
    private final String SORT_DIR_DESC = "desc";
    private final int PAGE_NO_ONE = 1;
    private final int PAGE_SIZE_TEN = 10;
    @InjectMocks
    private RepairFormService service;
    @Mock
    private RepairFormRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testFindAllPaginated() {
        Page<RepairFormEntity> entities = mock(Page.class);
        when(repository.findAll(any(Pageable.class))).thenReturn(entities);
        assertEquals(entities, service.findAllPaginated(PAGE_NO_ONE, PAGE_SIZE_TEN, SORT_FIElD_CREATION_DATE, SORT_DIR_DESC));
    }

    @Test
    void testFindUserRepairFormsPaginated() {
        Page<RepairFormEntity> entities = mock(Page.class);
        when(repository.findByAuthor_UserId(anyInt(), any(Pageable.class))).thenReturn(entities);
        Page<RepairFormEntity> repairmanForms = service.findUserRepairFormsPaginated(1, PAGE_NO_ONE, PAGE_SIZE_TEN, SORT_FIElD_CREATION_DATE, SORT_DIR_DESC);
        assertEquals(entities, repairmanForms);
    }

    @Test
    void testFindRepairmanForms() {
        Page<RepairFormEntity> entities = mock(Page.class);
        when(repository.findByRepairman_UserId(anyInt(), any(Pageable.class))).thenReturn(entities);
        Page<RepairFormEntity> repairmanForms = service.findRepairmanForms(1, PAGE_NO_ONE, PAGE_SIZE_TEN, SORT_FIElD_CREATION_DATE, SORT_DIR_DESC);
        assertEquals(entities, repairmanForms);
    }

    @Test
    void testAddRepairForm() {
        RepairFormEntity entity = mock(RepairFormEntity.class);
        when(repository.save(any(RepairFormEntity.class))).thenReturn(entity);
        RepairFormEntity repairFormEntity = service.addRepairForm(entity);
        assertEquals(entity, repairFormEntity);
    }

    @Test
    void testUpdateRepairForm() {
        RepairFormEntity entity = mock(RepairFormEntity.class);
        when(repository.save(any(RepairFormEntity.class))).thenReturn(entity);
        RepairFormEntity repairFormEntity = service.updateRepairForm(entity);
        assertEquals(entity, repairFormEntity);
    }

    @Test
    void testGetRepairForm() {
        RepairFormEntity entity = mock(RepairFormEntity.class);
        when(repository.findById(anyInt())).thenReturn(Optional.of(entity));
        RepairFormEntity repairForm = service.getRepairForm(1);
        assertEquals(entity, repairForm);
    }

    @Test
    void testSaveAll() {
        List<RepairFormEntity> list = mock(List.class);
        when(repository.saveAll(any())).thenReturn(list);
        List<RepairFormEntity> repairFormEntities = service.saveAll(list);
        assertEquals(list, repairFormEntities);

    }

    @Test
    void testFindFiltered() {
        Page<RepairFormEntity> entities = mock(Page.class);
        Specification<RepairFormEntity> specification = mock(Specification.class);
        when(repository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(entities);
        Page<RepairFormEntity> filtered =
                service.findFiltered(1, Status.NEW, PAGE_NO_ONE, PAGE_SIZE_TEN, SORT_FIElD_CREATION_DATE, SORT_DIR_DESC);
        assertEquals(entities, filtered);
    }
}