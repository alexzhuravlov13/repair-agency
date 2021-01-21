package com.zhuravlov.repairagency.repository;

import com.zhuravlov.repairagency.entity.RoleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RoleRepository extends CrudRepository<RoleEntity, Integer> {

    @Override
    List<RoleEntity> findAll();

    RoleEntity findByName(String name);

    @Transactional
    default RoleEntity createRoleIfNotFound(String name) {
        RoleEntity roleEntity = findByName(name);
        if (roleEntity == null) {
            roleEntity = new RoleEntity(name);
            save(roleEntity);
        }
        return roleEntity;
    }
}
