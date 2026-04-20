package com.HrManagementSystem.hrManagementSystem.Entity;

import jakarta.persistence.*;
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
@Entity
@Table(name ="Employees")
public class Employee {
    @Id
    @Column(name = "employee_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //personal Details
    private String firstName;
    private String lastName;
    private String phoneNo;
    private String userName;
    private String personalEmail;
    private String gender;
    private LocalDate dateOfBirth;
    private String maritalStatus;
    private String timeZone;
    private String employmentType;

    //Job Details
    private LocalDate dateOfJoining;
    private String jobCategory;
    private String workEmail;

    //Self Reference
    @ElementCollection
    @CollectionTable(name = "employee_managers", joinColumns = @JoinColumn(name = "employee_id"))
    @Column(name="manager_id")
    private List<Long> manager_id;

    //Status of an employee
    private boolean isActive = true;
}
