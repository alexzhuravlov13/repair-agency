package com.zhuravlov.repairagency.model.builder;

import com.zhuravlov.repairagency.model.DTO.RepairFormDto;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;

public class RepairFormDtoBuilder {
    private RepairFormDto repairFormDto;

    public RepairFormDtoBuilder() {
        this.repairFormDto = new RepairFormDto();
    }

    public RepairFormDtoBuilder setCreationDate(LocalDateTime creationDate) {
        this.repairFormDto.setCreationDate(creationDate);
        return this;
    }

    public RepairFormDtoBuilder setAuthorId(int authorId) {
        this.repairFormDto.setAuthorId(authorId);
        return this;
    }

    public RepairFormDtoBuilder setCar(@NonNull String car) {
        this.repairFormDto.setCar(car);
        return this;
    }

    public RepairFormDtoBuilder setShortDescription(@NonNull String shortDescription) {
        this.repairFormDto.setShortDescription(shortDescription);
        return this;
    }

    public RepairFormDtoBuilder setDescription(@NonNull String description) {
        this.repairFormDto.setDescription(description);
        return this;
    }

    public RepairFormDto build() {
        return repairFormDto;
    }


}
