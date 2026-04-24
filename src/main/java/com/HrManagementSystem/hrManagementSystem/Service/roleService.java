package com.HrManagementSystem.hrManagementSystem.Service;

import com.HrManagementSystem.hrManagementSystem.DTO.RoleDTO;
import com.HrManagementSystem.hrManagementSystem.Entity.Permission;
import com.HrManagementSystem.hrManagementSystem.Entity.Role;
import com.HrManagementSystem.hrManagementSystem.Repository.PermissionRepository;
import com.HrManagementSystem.hrManagementSystem.Repository.RoleRepository;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Setter
public class roleService {
    private RoleRepository roleRepository;
    private PermissionRepository permissionRepository;
    public RoleDTO addRole(RoleDTO dto) {
        String roleName = dto.getRole_name().toUpperCase();
        if (roleRepository.existsByName(roleName)) {
            throw new RuntimeException("Role already exists");
        }
        Role role = new Role();
        role.setName(dto.getRole_name());
        role.setSystemRole(false);
        Set<Permission> permissions = dto.getPermissions().stream()
                .map(p -> permissionRepository.findByName(p.toUpperCase())
                        .orElseThrow(() -> new RuntimeException("Permission not found: " + p)))
                .collect(Collectors.toSet());

        role.setPermissions(permissions);

        roleRepository.save(role);
        return dto;
    }

    public RoleDTO updateRole(Long id,RoleDTO dto) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role Not Found"));
        role.setName(dto.getRole_name());
        roleRepository.save(role);
        return dto;
    }
    public List<RoleDTO> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(role -> new RoleDTO(
                        role.getName(),
                        role.isSystemRole(),
                        role.getPermissions()
                                .stream()
                                .map(Permission::getName)
                                .toList()
                ))
                .toList();
    }
    public RoleDTO getRoleById(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role Not Found"));
        return mapToDTO(role);
    }

    public void deleteRole(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        if (role.isSystemRole()) {
            throw new RuntimeException("System roles cannot be deleted");
        }
        roleRepository.deleteById(id);
    }

    private RoleDTO mapToDTO(Role role) {
        RoleDTO dto = new RoleDTO();
        dto.setRole_name(role.getName());
        dto.setSystemRole(role.isSystemRole());
        return dto;
    }
}
