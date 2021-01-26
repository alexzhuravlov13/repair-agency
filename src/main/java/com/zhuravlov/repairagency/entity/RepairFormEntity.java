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
    int id;

    LocalDateTime creationDate;

    @OneToOne
    @JoinColumn(name = "author_id")
    UserEntity author;

    String car;

    String shortDescription;

    String description;

    int repairmanId;

    @Enumerated(EnumType.STRING)
    Status status;

    LocalDateTime lastModifiedDate;

    @Min(0)
    BigDecimal price = BigDecimal.valueOf(0);

    @Override
    public String toString() {
        return "RepairFormEntity{" +
                "id=" + id +
                ", creationDate=" + creationDate +
                ", authorId=" + author.getUserId() +
                ", shortDescription='" + shortDescription + '\'' +
                ", repairmanId=" + repairmanId +
                ", status=" + status.toString() +
                ", lastModifiedDate=" + lastModifiedDate +
                ", price=" + price +
                '}';
    }
}
