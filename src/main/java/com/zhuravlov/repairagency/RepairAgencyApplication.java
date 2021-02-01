package com.zhuravlov.repairagency;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RepairAgencyApplication {


    public static void main(String[] args) {
        SpringApplication.run(RepairAgencyApplication.class, args);
    }

    //TODO:validation of forms
    //TODO:Objects.equals() where we compare objects and check for a null
    //TODO:several queries in one transaction (in service)
}
