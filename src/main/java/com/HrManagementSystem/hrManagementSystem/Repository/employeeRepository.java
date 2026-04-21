package com.HrManagementSystem.hrManagementSystem.Repository;

import com.HrManagementSystem.hrManagementSystem.Entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static org.hibernate.grammars.hql.HqlParser.AS;
@Repository
public interface employeeRepository extends JpaRepository<Employee,Long> {
//    @Query("SELECT e FROM Employee e WHERE e.isActive = true AND " +
//            "(CAST(e.id AS string) LIKE %:keyword% OR " +
//            "LOWER(e.firstName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
//            "LOWER(e.lastName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
//            "LOWER(CONCAT(e.firstName, ' ', e.lastName)) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
//            "LOWER(e.userName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
//            "LOWER(e.workEmail) LIKE LOWER(CONCAT('%', :keyword, '%')))")

    Page<Employee> findByIsActiveTrue(Pageable pageable);
    Page<Employee> findByFirstNameContaining(String keyword, Pageable pageable);


    Optional<Employee> findByUserNameOrWorkEmail(String userName, String workEmail);
}
