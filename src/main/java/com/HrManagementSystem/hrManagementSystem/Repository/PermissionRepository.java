package com.HrManagementSystem.hrManagementSystem.Repository;

import com.HrManagementSystem.hrManagementSystem.Entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, Integer> {
    Optional<Permission> findByName(String name);
}
