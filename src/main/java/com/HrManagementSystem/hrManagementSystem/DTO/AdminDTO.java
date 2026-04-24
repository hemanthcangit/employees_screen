package com.HrManagementSystem.hrManagementSystem.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminDTO {
    private String firstName;
    private String lastName;
    private String workEmail;
    private String password;
}
