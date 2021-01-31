package com.zhuravlov.repairagency.model.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class RepairFormDto {

    @CreatedDate
    private LocalDateTime creationDate;

    private int authorId;

    @NonNull
    private String car;

    @NonNull
    private String shortDescription;

    @NonNull
    private String description;
}
