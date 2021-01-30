package com.zhuravlov.repairagency.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "repair_requests")
@Data
@NoArgsConstructor
public class RepairFormEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDateTime creationDate;

    @OneToOne
    @JoinColumn(name = "author_id")
    private UserEntity author;

    private String car;

    private String shortDescription;

    private String description;

    private String feedback;

    private int repairmanId;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime lastModifiedDate;

    @Min(0)
    private BigDecimal price = BigDecimal.valueOf(0);
}
