package com.HrManagementSystem.hrManagementSystem;

import com.HrManagementSystem.hrManagementSystem.Entity.Permission;
import com.HrManagementSystem.hrManagementSystem.Entity.Role;
import com.HrManagementSystem.hrManagementSystem.Repository.PermissionRepository;
import com.HrManagementSystem.hrManagementSystem.Repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RbacInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    @Override
    public void run(String... args) {
        createPermissions();
        createRoles();
    }

    // ============================
    // CREATE PERMISSIONS
    // ============================
    private void createPermissions() {

        Map<String, String> permissionsMap = Map.of(
                "VIEW_EMPLOYEES", "View Employees screen",
                "CREATE_EMPLOYEE", "Create Employee",
                "VIEW_SUBORDINATES", "View Subordinates",
                "EDIT_SUBORDINATES", "Edit Subordinates",
                "MANAGE_ALL_EMPLOYEES", "Full access to all employees",
                "INACTIVE_EMPLOYEES", "View inactive employees",
                "DELETE_EMPLOYEES", "Delete employees"
        );

        permissionsMap.forEach((name, desc) -> {
            if (!permissionRepository.existsByName(name)) {
                Permission permission = new Permission();
                permission.setName(name);
                permissionRepository.save(permission);
            }
        });

        System.out.println("Permissions initialized");
    }

    // ============================
    // CREATE ROLES + ASSIGN PERMISSIONS
    // ============================
    private void createRoles() {

        // Safety check
        if (permissionRepository.count() == 0) {
            throw new RuntimeException("Permissions must be created first");
        }

        Map<String, Permission> perms = permissionRepository.findAll()
                .stream()
                .collect(Collectors.toMap(Permission::getName, p -> p));

        // ADMIN
        Role admin = createRoleIfNotExists("ADMIN");
        if (isEmpty(admin)) {
            admin.setPermissions(Set.of(
                    getPermission(perms, "VIEW_EMPLOYEES"),
                    getPermission(perms, "CREATE_EMPLOYEE"),
                    getPermission(perms, "VIEW_SUBORDINATES"),
                    getPermission(perms, "EDIT_SUBORDINATES"),
                    getPermission(perms, "MANAGE_ALL_EMPLOYEES"),
                    getPermission(perms, "INACTIVE_EMPLOYEES"),
                    getPermission(perms, "DELETE_EMPLOYEES")
            ));
        }

        // MANAGER
        Role manager = createRoleIfNotExists("MANAGER");
        if (isEmpty(manager)) {
            manager.setPermissions(Set.of(
                    getPermission(perms, "VIEW_EMPLOYEES"),
                    getPermission(perms, "CREATE_EMPLOYEE"),
                    getPermission(perms, "VIEW_SUBORDINATES"),
                    getPermission(perms, "EDIT_SUBORDINATES")
            ));
        }

        // USER
        Role user = createRoleIfNotExists("USER");
        if (isEmpty(user)) {
            user.setPermissions(Set.of(
                    getPermission(perms, "VIEW_EMPLOYEES")
            ));
        }

        roleRepository.saveAll(List.of(admin, manager, user));

        System.out.println("Roles initialized");
    }

    // ============================
    // HELPER METHODS
    // ============================

    private Role createRoleIfNotExists(String name) {
        return roleRepository.findByName(name)
                .orElseGet(() -> {
                    Role role = new Role();
                    role.setName(name);
                    role.setSystemRole(true);
                    return roleRepository.save(role);
                });
    }

    private Permission getPermission(Map<String, Permission> perms, String name) {
        Permission permission = perms.get(name);
        if (permission == null) {
            throw new RuntimeException("Permission not found: " + name);
        }
        return permission;
    }

    private boolean isEmpty(Role role) {
        return role.getPermissions() == null || role.getPermissions().isEmpty();
    }
}