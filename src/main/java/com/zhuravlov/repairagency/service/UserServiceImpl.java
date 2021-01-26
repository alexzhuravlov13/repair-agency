package com.zhuravlov.repairagency.service;


import com.zhuravlov.repairagency.entity.UserEntity;
import com.zhuravlov.repairagency.repository.RoleRepository;
import com.zhuravlov.repairagency.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void addUser(UserEntity userEntity) {
        userEntity.setPassword(bCryptPasswordEncoder.encode(userEntity.getPassword()));
        userEntity.setRoles(new HashSet<>(Arrays.asList(roleRepository.findByName("ROLE_USER"))));

        userRepository.save(userEntity);
    }

    @Transactional
    @Override
    public List<UserEntity> getUsers() {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public UserEntity getUser(int id) {
        Optional<UserEntity> userByIdOptional = userRepository.findById(id);
        if (userByIdOptional.isEmpty()) {
            throw new UsernameNotFoundException("User id:" + id + " not found");
        }
        return userByIdOptional.get();
    }

    @Override
    public void updateUser(UserEntity userEntity) {
        userRepository.save(userEntity);
    }

    @Override
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserEntity findByUsername(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserEntity> findUsersByRole(String role) {
        return userRepository.findByRoles_name(role);
    }


}
