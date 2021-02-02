package com.zhuravlov.repairagency.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
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

    @NotEmpty(message = "{repairForm.error}")
    private String car;

    @NotEmpty(message = "{repairForm.error}")
    private String shortDescription;

    @NotEmpty(message = "{repairForm.error}")
    private String description;

    private String feedback;

    private int repairmanId;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime lastModifiedDate;

    @Min(0)
    private BigDecimal price = BigDecimal.valueOf(0);
}
