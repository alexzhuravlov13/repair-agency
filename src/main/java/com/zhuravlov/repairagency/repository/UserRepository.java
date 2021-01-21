package com.zhuravlov.repairagency.repository;

import com.zhuravlov.repairagency.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Integer> {

    @Override
    List<UserEntity> findAll();

    UserEntity findByEmail(String email);
}
