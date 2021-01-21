package com.zhuravlov.repairagency.service;


import com.zhuravlov.repairagency.entity.UserEntity;

import java.util.List;

public interface UserService {

    void addUser(UserEntity userEntity);

    List<UserEntity> getUsers();

    UserEntity getUser(int id);

    void updateUser(UserEntity userEntity);

    void deleteUser(int id);

    UserEntity findByUsername(String email);
}
