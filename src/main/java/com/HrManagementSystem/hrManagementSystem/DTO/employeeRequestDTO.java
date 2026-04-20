package com.HrManagementSystem.hrManagementSystem.DTO;

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
    private String firstName;
    private String LastName;
    private String phoneNo;
    private String userName;
    private String personalEmail;
    private String gender;
    private LocalDate dateOfBirth;
    private String maritalStatus;
    private String timeZone;
    private String employmentType;
    private List<Long> managerId;

    //Job Details
    private LocalDate dateOfJoining;
    private String jobCategory;
    private String workEmail;



}
