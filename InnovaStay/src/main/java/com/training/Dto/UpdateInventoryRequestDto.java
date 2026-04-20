package com.training.Dto;

import lombok.Data;
import java.math.BigInteger;

@Data
public class UpdateInventoryRequestDto {
    private BigInteger surgeFactor;
    private BigInteger price;
    private Boolean closed;
}