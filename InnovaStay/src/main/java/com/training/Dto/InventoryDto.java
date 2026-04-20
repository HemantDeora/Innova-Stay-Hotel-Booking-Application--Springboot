package com.training.Dto;

import lombok.Data;
import java.math.BigInteger;
import java.time.LocalDate;

@Data
public class InventoryDto {
    private Long id;
    private LocalDate date;
    private Integer bookedCount;
    private Integer totalCount;
    private BigInteger surgeFactor;
    private BigInteger price;
    private String city;
    private Boolean closed;
}