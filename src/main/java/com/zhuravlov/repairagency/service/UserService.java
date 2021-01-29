package com.zhuravlov.repairagency.service;


import com.zhuravlov.repairagency.entity.RepairFormEntity;
import com.zhuravlov.repairagency.entity.UserEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {

    void addUser(UserEntity userEntity);

    void saveAll(List<UserEntity> users);

    Page<UserEntity> findAllPaginated(int pageNo, int pageSize);

    UserEntity getUser(int id);

    void updateUser(UserEntity userEntity);

    void deleteUser(int id);

    UserEntity findByUsername(String email);

    List<UserEntity> findUsersByRole(String role);
}
