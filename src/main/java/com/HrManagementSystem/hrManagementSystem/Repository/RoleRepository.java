package com.HrManagementSystem.hrManagementSystem.Repository;

import com.HrManagementSystem.hrManagementSystem.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(String name);
}
