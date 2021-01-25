package com.zhuravlov.repairagency.entity.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.lang.NonNull;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
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

}
