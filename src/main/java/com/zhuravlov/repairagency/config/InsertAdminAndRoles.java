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

        roleRepository.createRoleIfNotFound("ROLE_ADMIN");
        roleRepository.createRoleIfNotFound("ROLE_USER");
        roleRepository.createRoleIfNotFound("ROLE_MANAGER");
        roleRepository.createRoleIfNotFound("ROLE_REPAIRMAN");

        UserEntity admin =
                new UserEntity(
                        "Alex",
                        "Zhuravlov",
                        "alexzhuravlov13@gmail.com",
                        "111111");

        UserEntity byUsername = userService.findByUsername("alexzhuravlov13@gmail.com");

        if (byUsername == null) {
            userService.addUser(admin);
        }

        byUsername = userService.findByUsername("alexzhuravlov13@gmail.com");

        byUsername.setRoles(new HashSet<>(Arrays.asList(
                roleRepository.findByName("ROLE_USER"),
                roleRepository.findByName("ROLE_ADMIN"))));

        userService.updateUser(byUsername);

        RepairFormEntity repairFormEntity = new RepairFormEntity();
        repairFormEntity.setCar("Subaru Impreza WRX Blue");
        repairFormEntity.setShortDescription("4th cylinder");
        repairFormEntity.setCreationDate(LocalDateTime.now());
        repairFormEntity.setLastModifiedDate(LocalDateTime.now());
        repairFormEntity.setAuthor(userService.getUser(1));
        repairFormEntity.setDescription("Subariku pora na kapitalku");
        repairFormEntity.setStatus(Status.NEW);


        repairFormService.addRepairForm(repairFormEntity);

    }
}
