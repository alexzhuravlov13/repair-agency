package com.zhuravlov.repairagency.model.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class RepairFormDto {

    @CreatedDate
    private LocalDateTime creationDate;

    private int authorId;

    @NotEmpty(message = "{repairForm.error}")
    private String car;

    @NotEmpty(message = "{repairForm.error}")
    private String shortDescription;

    @NotEmpty(message = "{repairForm.error}")
    private String description;
}
