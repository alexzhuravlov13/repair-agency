package com.zhuravlov.repairagency.repository;

;
import com.zhuravlov.repairagency.entity.RoleEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RoleRepository extends CrudRepository<RoleEntity, Integer> {

    @Override
    <S extends RoleEntity> S save(S entity);

    @Override
    List<RoleEntity> findAll();

    @Query("from RoleEntity where name = :name")
    RoleEntity findByName(@Param("name") String name);

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
