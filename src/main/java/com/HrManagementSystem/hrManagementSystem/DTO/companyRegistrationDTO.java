package com.HrManagementSystem.hrManagementSystem.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class companyRegistrationDTO {
    // Company details
    private String companyName;

    // Admin user details
    private employeeRequestDTO admin;
}
