package com.zhuravlov.repairagency.repository;

import com.zhuravlov.repairagency.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    UserEntity findByEmail(String email);

    List<UserEntity> findByRoles_name(String name);

    @Modifying
    @Query("UPDATE UserEntity u SET u.amount = ?2 WHERE u.userId = ?1")
    void changeUserAmount(@Param(value = "userId") int userId, @Param(value = "amount") BigDecimal amount);

}
