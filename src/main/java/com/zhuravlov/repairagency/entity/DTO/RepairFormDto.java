package com.zhuravlov.repairagency.entity.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class RepairFormDto {

    @CreatedDate
    LocalDateTime creationDate;

    int authorId;

    @NonNull
    String car;

    @NonNull
    String shortDescription;

    @NonNull
    String description;

    public RepairFormDto(LocalDateTime creationDate, int authorId) {
        this.creationDate = creationDate;
        this.authorId = authorId;
    }
}
