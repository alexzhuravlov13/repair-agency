package com.zhuravlov.repairagency.config;

import com.zhuravlov.repairagency.entity.RepairFormEntity;
import com.zhuravlov.repairagency.entity.Status;
import com.zhuravlov.repairagency.entity.UserEntity;
import com.zhuravlov.repairagency.repository.RoleRepository;
import com.zhuravlov.repairagency.service.RepairFormService;
import com.zhuravlov.repairagency.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;

@Slf4j
@Component
public class InsertAdminAndRoles implements InitializingBean {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RepairFormService repairFormService;

    @Override
    public void afterPropertiesSet() throws Exception {

        initRoles();

        initUsers();

        initRepairForms();

    }

    private void initRepairForms() {
        RepairFormEntity repairFormEntity1 = new RepairFormEntity();
        repairFormEntity1.setCar("Subaru Impreza WRX Blue");
        repairFormEntity1.setShortDescription("4th cylinder");
        repairFormEntity1.setCreationDate(LocalDateTime.now());
        repairFormEntity1.setLastModifiedDate(LocalDateTime.now());
        repairFormEntity1.setAuthor(userService.getUser(1));
        repairFormEntity1.setDescription("Subariku pora na kapitalku");
        repairFormEntity1.setStatus(Status.NEW);


        repairFormService.addRepairForm(repairFormEntity1);

        RepairFormEntity repairFormEntity2 = new RepairFormEntity();
        repairFormEntity2.setCar("Mitsubishi evo 8 red");
        repairFormEntity2.setShortDescription("4wd not drifting");
        repairFormEntity2.setCreationDate(LocalDateTime.now());
        repairFormEntity2.setLastModifiedDate(LocalDateTime.now());
        repairFormEntity2.setAuthor(userService.getUser(3));
        repairFormEntity2.setDescription("change engine to 16rik");
        repairFormEntity2.setStatus(Status.NEW);


        repairFormService.addRepairForm(repairFormEntity2);
    }

    private void initUsers() {
        UserEntity admin =
                new UserEntity(
                        "Alex",
                        "Zhuravlov",
                        "admin@gmail.com",
                        "111111");

        UserEntity byUsername = userService.findByUsername("admin@gmail.com");

        if (byUsername == null) {
            userService.addUser(admin);
        }

        byUsername = userService.findByUsername("admin@gmail.com");

        byUsername.setRoles(new HashSet<>(Arrays.asList(
                roleRepository.findByName("ROLE_USER"),
                roleRepository.findByName("ROLE_ADMIN"))));

        userService.updateUser(byUsername);

        UserEntity manager =
                new UserEntity(
                        "Manager",
                        "Managerovich",
                        "manager@gmail.com",
                        "111111");

        UserEntity managerbyUsername = userService.findByUsername("manager@gmail.com");

        if (managerbyUsername == null) {
            userService.addUser(manager);
        }

        managerbyUsername = userService.findByUsername("manager@gmail.com");

        managerbyUsername.setRoles(new HashSet<>(Arrays.asList(
                roleRepository.findByName("ROLE_USER"),
                roleRepository.findByName("ROLE_MANAGER"))));

        userService.updateUser(managerbyUsername);

        UserEntity user =
                new UserEntity(
                        "User",
                        "Userovich",
                        "User@gmail.com",
                        "111111");

        UserEntity userbyUsername = userService.findByUsername("user@gmail.com");

        if (userbyUsername == null) {
            userService.addUser(user);
        }

        userbyUsername = userService.findByUsername("user@gmail.com");

        userbyUsername.setRoles(new HashSet<>(Arrays.asList(
                roleRepository.findByName("ROLE_USER"))));

        userService.updateUser(userbyUsername);
    }

    private void initRoles() {
        roleRepository.createRoleIfNotFound("ROLE_ADMIN");
        roleRepository.createRoleIfNotFound("ROLE_USER");
        roleRepository.createRoleIfNotFound("ROLE_MANAGER");
        roleRepository.createRoleIfNotFound("ROLE_REPAIRMAN");
    }
}
