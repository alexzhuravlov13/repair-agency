package com.zhuravlov.repairagency.unit.service;


import com.zhuravlov.repairagency.model.DTO.MastersAndStatusesDto;
import com.zhuravlov.repairagency.model.entity.Status;
import com.zhuravlov.repairagency.model.entity.UserEntity;
import com.zhuravlov.repairagency.repository.RoleRepository;
import com.zhuravlov.repairagency.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserEntity addUser(UserEntity userEntity) {
        userEntity.setPassword(bCryptPasswordEncoder.encode(userEntity.getPassword()));
        userEntity.setRoles(new HashSet<>(Collections.singletonList(roleRepository.findByName("ROLE_USER"))));
        return userRepository.save(userEntity);
    }

    public List<UserEntity> saveAll(List<UserEntity> users) {
        userRepository.saveAll(users);
        return users;
    }

    public Page<UserEntity> findAllPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return userRepository.findAll(pageable);
    }


    public UserEntity getUser(int id) {
        Optional<UserEntity> userByIdOptional = userRepository.findById(id);
        return userByIdOptional
                .orElseThrow(() -> new UsernameNotFoundException("User id:" + id + " not found"));
    }

    public UserEntity updateUser(UserEntity userEntity) {
        userRepository.save(userEntity);
        return userEntity;
    }

    public boolean deleteUser(int id) {
        userRepository.deleteById(id);
        return userRepository.findById(id).isEmpty();
    }

    public UserEntity findByUsername(String email) {
        return userRepository.findByEmail(email);
    }

    public List<UserEntity> findUsersByRole(String role) {
        return userRepository.findByRoles_name(role);
    }

    public boolean changeAmount(int userId, BigDecimal amount) {
        userRepository.changeUserAmount(userId, amount);
        Optional<UserEntity> byId = userRepository.findById(userId);
        return byId.map(userEntity -> userEntity.getAmount().equals(amount)).orElse(false);
    }

    @Transactional
    public MastersAndStatusesDto getMastersAndStatuses() {
        MastersAndStatusesDto lists = new MastersAndStatusesDto();
        List<UserEntity> allMasters = userRepository.findByRoles_name("ROLE_REPAIRMAN");
        List<Status> statuses = Arrays.asList(Status.values());
        lists.setMasters(allMasters);
        lists.setStatuses(statuses);
        return lists;
    }

}
