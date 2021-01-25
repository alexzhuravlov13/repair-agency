package com.zhuravlov.repairagency.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

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

    LocalDateTime creationDate;

    @OneToOne
    @JoinColumn(name = "users")
    UserEntity author;

    String car;

    String shortDescription;

    String description;

    int repairmanId;

    @Enumerated(EnumType.STRING)
    Status status;

    LocalDateTime lastModifiedDate;

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
