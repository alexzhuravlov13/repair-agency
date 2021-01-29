package com.zhuravlov.repairagency.entity.DTO;

import com.zhuravlov.repairagency.entity.Status;
import lombok.Data;

@Data
public class FilterDto {
    private String masterId;
    private String status;
}
