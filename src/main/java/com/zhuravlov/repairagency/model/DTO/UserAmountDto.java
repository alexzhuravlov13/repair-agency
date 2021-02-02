package com.zhuravlov.repairagency.model.DTO;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

@Data
public class UserAmountDto {
    private int userId;

    @NotEmpty(message = "{repairForm.error}")
    private BigDecimal amount;

    public UserAmountDto(int userId) {
        this.userId = userId;
        this.amount = new BigDecimal(0);
    }
}
