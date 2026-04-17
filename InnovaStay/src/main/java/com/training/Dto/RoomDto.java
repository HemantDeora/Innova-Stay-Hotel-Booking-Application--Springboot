package com.training.Dto;

import lombok.Data;

@Data
public class RoomDto {
    private Long id;
    private String type;
    private Double basePrice;
    private String[] photos;
    private String[] amenities;
    private Integer totalCount;
    private Integer capacity;
}
