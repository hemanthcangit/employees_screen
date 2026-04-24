package com.HrManagementSystem.hrManagementSystem.DTO;

import com.HrManagementSystem.hrManagementSystem.Entity.Permission;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {
    private String role_name;
    private boolean systemRole;
    private List<String> permissions;
}
