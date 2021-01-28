package com.zhuravlov.repairagency.config;

import com.zhuravlov.repairagency.entity.RepairFormEntity;
import com.zhuravlov.repairagency.entity.Status;
import com.zhuravlov.repairagency.entity.UserEntity;
import com.zhuravlov.repairagency.entity.builder.RepairFormBuilder;
import com.zhuravlov.repairagency.repository.RoleRepository;
import com.zhuravlov.repairagency.service.RepairFormService;
import com.zhuravlov.repairagency.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

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
        List<RepairFormEntity> repairFormEntityList = new ArrayList<>();

        int masterId = userService.findByUsername("master1@gmail.com").getUserId();

        for (int i = 1; i < 13; i++) {
            RepairFormEntity repairFormEntity1 =
                    builderForm("Car" + i + " Blue",
                            "ShortDescription" + i, 1,
                            "Description" + i,
                            Status.NEW).build();
            repairFormEntityList.add(repairFormEntity1);

            RepairFormEntity repairFormEntity2 =
                    builderForm("Car" + i + " Red",
                            "ShortDescription" + i, 3,
                            "Problem" + i,
                            Status.READY, masterId).build();
            repairFormEntityList.add(repairFormEntity2);
        }

        repairFormService.saveAll(repairFormEntityList);

    }

    private RepairFormBuilder builderForm(String car, String shortDescription, int authorId, String description, Status status) {
        RepairFormBuilder builder = new RepairFormBuilder()
                .setCar(car)
                .setShortDescription(shortDescription)
                .setCreationDate(LocalDateTime.now())
                .setLastModifiedDate(LocalDateTime.now())
                .setAuthor(userService.getUser(authorId))
                .setDescription(description)
                .setStatus(status);

        return builder;
    }

    private RepairFormBuilder builderForm(String car, String shortDescription, int authorId, String description, Status status, int repairmanId) {
        RepairFormBuilder builder = builderForm(car, shortDescription, authorId, description, status);

        if (repairmanId > 0) {
            builder.setRepairmanId(repairmanId);
        }

        return builder;
    }


    private void initUsers() {
        initUser("Alex", "Zhuravlov", "admin@gmail.com", "ROLE_ADMIN");

        initUser("Manager", "Managerovich", "manager@gmail.com", "ROLE_MANAGER");

        initUser("User", "Userovich", "User@gmail.com", "user@gmail.com", "ROLE_USER");

        initUser("Repairman1", "Master", "master1@gmail.com", "master1@gmail.com", "ROLE_REPAIRMAN");

        initUser("Repairman2", "Master2", "master2@gmail.com", "master2@gmail.com", "ROLE_REPAIRMAN");
    }

    private void initUser(String alex, String zhuravlov, String s, String role_admin) {
        UserEntity admin =
                new UserEntity(
                        alex,
                        zhuravlov,
                        s,
                        "111111");

        UserEntity byUsername = userService.findByUsername(s);

        if (byUsername == null) {
            userService.addUser(admin);
        }

        byUsername = userService.findByUsername(s);

        byUsername.setRoles(new HashSet<>(Arrays.asList(
                roleRepository.findByName("ROLE_USER"),
                roleRepository.findByName(role_admin))));

        userService.updateUser(byUsername);
    }

    private void initUser(String user2, String userovich, String s, String s2, String role_user) {
        UserEntity user =
                new UserEntity(
                        user2,
                        userovich,
                        s,
                        "111111");

        UserEntity userbyUsername = userService.findByUsername(s2);

        if (userbyUsername == null) {
            userService.addUser(user);
        }

        userbyUsername = userService.findByUsername(s2);

        userbyUsername.setRoles(new HashSet<>(Arrays.asList(
                roleRepository.findByName(role_user))));

        userService.updateUser(userbyUsername);
    }

    private void initRoles() {
        roleRepository.createRoleIfNotFound("ROLE_ADMIN");
        roleRepository.createRoleIfNotFound("ROLE_USER");
        roleRepository.createRoleIfNotFound("ROLE_MANAGER");
        roleRepository.createRoleIfNotFound("ROLE_REPAIRMAN");
    }
}
