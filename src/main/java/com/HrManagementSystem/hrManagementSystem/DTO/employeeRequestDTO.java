package com.HrManagementSystem.hrManagementSystem.DTO;

import com.HrManagementSystem.hrManagementSystem.Entity.Role;
import io.swagger.v3.oas.annotations.media.Schema;
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
public class employeeRequestDTO {
    //personal Details
    @Schema(description = "First Name of employee", example = "Hemanth")
    private String firstName;
    @Schema(description = "Last Name of employee", example = "Pelluru")
    private String lastName;
    private String phoneNo;
    private String userName;
    private String personalEmail;
    private String gender;
    private LocalDate dateOfBirth;
    private String maritalStatus;
    private String timeZone;
    private String employmentType;
    private List<Long> managerId;

    private RoleDTO roleDTO;

    private String password;

    //Job Details
    private LocalDate dateOfJoining;
    private String jobCategory;
    @Schema(description = "Working email of an employee", example = "hemanth@gmail.com")
    private String workEmail;



}
