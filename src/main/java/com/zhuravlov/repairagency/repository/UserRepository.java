package com.zhuravlov.repairagency.repository;

import com.zhuravlov.repairagency.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    UserEntity findByEmail(String email);

    List<UserEntity> findByRoles_name(String name);
}
