package com.zhuravlov.repairagency.service;


import com.zhuravlov.repairagency.entity.UserEntity;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

public interface UserService {

    UserEntity addUser(UserEntity userEntity);

    List<UserEntity> saveAll(List<UserEntity> users);

    Page<UserEntity> findAllPaginated(int pageNo, int pageSize);

    UserEntity getUser(int id);

    UserEntity updateUser(UserEntity userEntity);

    boolean deleteUser(int id);

    UserEntity findByUsername(String email);

    List<UserEntity> findUsersByRole(String role);

    boolean changeAmount(int userId, BigDecimal amount);
}
