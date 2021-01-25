package com.zhuravlov.repairagency.entity.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class RepairFormDto {

    @CreatedDate
    LocalDateTime creationDate;

    int authorId;

    String description;

}
