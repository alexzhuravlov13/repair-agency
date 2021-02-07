package com.zhuravlov.repairagency.model.DTO;

import com.zhuravlov.repairagency.model.entity.Status;
import lombok.Data;

@Data
public class FilterDto {
    private Integer masterId;
    private Status status;
}
