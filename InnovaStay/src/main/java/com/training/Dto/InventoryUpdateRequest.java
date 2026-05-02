package com.training.Dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class InventoryUpdateRequest {
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal surgeFactor;
    private Boolean closed;
}
