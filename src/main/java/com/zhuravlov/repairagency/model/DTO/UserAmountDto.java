package com.zhuravlov.repairagency.model.DTO;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserAmountDto {
    private int userId;
    private BigDecimal amount;

    public UserAmountDto(int userId) {
        this.userId = userId;
        this.amount = new BigDecimal(0);
    }
}
