package com.zhuravlov.repairagency.config;

import com.zhuravlov.repairagency.model.builder.RepairFormBuilder;
import com.zhuravlov.repairagency.model.builder.UserEntityBuilder;
import com.zhuravlov.repairagency.model.entity.RepairFormEntity;
import com.zhuravlov.repairagency.model.entity.RoleEntity;
import com.zhuravlov.repairagency.model.entity.Status;
import com.zhuravlov.repairagency.model.entity.UserEntity;
import com.zhuravlov.repairagency.repository.RoleRepository;
import com.zhuravlov.repairagency.unit.service.RepairFormService;
import com.zhuravlov.repairagency.unit.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
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

        for (int i = 1; i <10; i++) {
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

    private RepairFormBuilder builderForm(String car,
                                          String shortDescription,
                                          int authorId,
                                          String description,
                                          Status status) {
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
        UserEntity repairman = userService.getUser(repairmanId);

        if (repairmanId > 0) {
            builder.setRepairmanId(repairman);
        }

        return builder;
    }


    private void initUsers() {
        initUser("Alex", "Zhuravlov", "admin@gmail.com", "ROLE_ADMIN");

        initUser("Manager", "Managerovich", "manager@gmail.com", "ROLE_MANAGER");

        initUser("User", "Userovich", "User@gmail.com", "ROLE_USER");

        initUser("Repairman1", "Master", "master1@gmail.com", "ROLE_REPAIRMAN");

        initUser("Repairman2", "Master2", "master2@gmail.com", "ROLE_REPAIRMAN");
    }

    private void initUser(String firstName, String lastName, String email, String role) {

        UserEntity user = new UserEntityBuilder()
                .setFirstName(firstName)
                .setLastName(lastName)
                .setEmail(email)
                .setPassword("111111")
                .build();

        UserEntity byUsername = userService.findByUsername(email);

        if (byUsername == null) {
            userService.addUser(user);
        }

        byUsername = userService.findByUsername(email);
        String roleUser = "ROLE_USER";
        HashSet<RoleEntity> userRoles = new HashSet<>(Collections.singletonList(
                roleRepository.findByName(roleUser)));

        if (!role.equalsIgnoreCase(roleUser)) {
            userRoles.add(roleRepository.findByName(role));
        }
        byUsername.setRoles(userRoles);

        userService.updateUser(byUsername);
    }

    private void initRoles() {
        roleRepository.createRoleIfNotFound("ROLE_ADMIN");
        roleRepository.createRoleIfNotFound("ROLE_USER");
        roleRepository.createRoleIfNotFound("ROLE_MANAGER");
        roleRepository.createRoleIfNotFound("ROLE_REPAIRMAN");
    }
}
