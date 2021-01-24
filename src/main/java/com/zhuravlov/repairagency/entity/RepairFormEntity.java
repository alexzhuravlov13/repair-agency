package com.zhuravlov.repairagency.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "repair_requests")
@Data
@NoArgsConstructor
public class RepairFormEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @CreatedDate
    LocalDateTime creationDate;

    @OneToOne
    @JoinColumn(name = "users")
    UserEntity author;

    String description;

    int repairmanId;

    @OneToOne
    @JoinColumn(name = "statuses")
    StatusEntity status;

    @LastModifiedDate
    LocalDateTime lastModifiedDate;

    BigDecimal price = BigDecimal.valueOf(0);

}
