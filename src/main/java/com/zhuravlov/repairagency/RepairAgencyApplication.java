package com.zhuravlov.repairagency;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RepairAgencyApplication {


    public static void main(String[] args) {
        SpringApplication.run(RepairAgencyApplication.class, args);
    }

    //TODO:check when reading entity and modified it creationDate

    //TODO:spring exception handler
    //TODO:service returns entity (for tests), optionals or exceptions
    //TODO:refactor controllers, move business logic to services

    //TODO:transaction when reading and writing an object (using builder) or jpql for updating the fields
    //TODO:lastModified
}
