package com.zhuravlov.repairagency.entity.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class RepairFormDto {

    @CreatedDate
    LocalDateTime creationDate;

    int authorId;

    String description;

}
