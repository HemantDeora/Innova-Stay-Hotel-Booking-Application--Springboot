package com.logic.DTO;

import com.logic.entity.enums.Gender;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SignUpRequestDTO {
    private String email;
    private String password;
    private String name;

}
