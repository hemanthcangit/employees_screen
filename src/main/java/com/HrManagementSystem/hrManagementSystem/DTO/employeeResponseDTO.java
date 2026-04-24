package com.HrManagementSystem.hrManagementSystem.DTO;

import com.HrManagementSystem.hrManagementSystem.Entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class employeeResponseDTO {
    private Long employeeId;
    private String fullName;
    private String userName;
    private String workEmail;
    private List<Long> managers;
    private LocalDate dateOfJoining;
    private String phoneNo;
    private Role role;
}
