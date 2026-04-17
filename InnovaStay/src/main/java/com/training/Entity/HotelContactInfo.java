package com.training.Entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import lombok.Data;


@Data
@Embeddable
public class HotelContactInfo {

    private String address;
    private String phoneNumber;
    private String email;
    private String location;
}
