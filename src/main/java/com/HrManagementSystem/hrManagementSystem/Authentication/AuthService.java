package com.HrManagementSystem.hrManagementSystem.Authentication;

import com.HrManagementSystem.hrManagementSystem.DTO.AdminDTO;
import com.HrManagementSystem.hrManagementSystem.DTO.LoginDTO;
import com.HrManagementSystem.hrManagementSystem.DTO.companyRegistrationDTO;
import com.HrManagementSystem.hrManagementSystem.DTO.employeeRequestDTO;
import com.HrManagementSystem.hrManagementSystem.Entity.Company;
import com.HrManagementSystem.hrManagementSystem.Entity.Employee;
import com.HrManagementSystem.hrManagementSystem.Entity.Role;
import com.HrManagementSystem.hrManagementSystem.Repository.PermissionRepository;
import com.HrManagementSystem.hrManagementSystem.Repository.RoleRepository;
import com.HrManagementSystem.hrManagementSystem.Repository.companyRepository;
import com.HrManagementSystem.hrManagementSystem.Repository.employeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthService {

    @Autowired
    private employeeRepository employeeRepo;

    @Autowired
    private companyRepository companyRepo;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepo;

    public boolean isFirstUser() {
        return employeeRepo.count() == 0;
    }

    public String registerCompany(companyRegistrationDTO request) {
        if (companyRepo.count() > 0) {
            return "Company already exists. Please login.";
        }
        Role role = roleRepo.findByName("ADMIN")
                .orElseThrow(() -> new RuntimeException("Role not found"));

        Company company = new Company();
        company.setCompanyName(request.getCompanyName());
        Company savedCompany = companyRepo.save(company);

        AdminDTO dto =request.getAdmin();

        // ✅ Create Admin User

        Employee admin = new Employee();
        admin.setFirstName(dto.getFirstName());
        admin.setLastName(dto.getLastName());
        admin.setUserName(dto.getWorkEmail());
        admin.setPassword(passwordEncoder.encode(dto.getPassword()));
        admin.setWorkEmail(dto.getWorkEmail());
        Role adminRole = roleRepo.findByName("ADMIN")
                .orElseThrow(() -> new RuntimeException("Admin role not found"));

        admin.setRole(adminRole);
        admin.setCompany(savedCompany);


        admin.setActive(true);

        employeeRepo.save(admin);

        return "Company and Admin created successfully";
    }

    public String login(LoginDTO dto) {

        Employee emp = employeeRepo
                .findByUserName(dto.getUsernameOrEmail());

        if(emp==null){
            emp = employeeRepo
                    .findByWorkEmail(dto.getUsernameOrEmail())
                    .orElseThrow(() -> new RuntimeException("User not found"));
      }

        // ✅ Password validation
        if (!passwordEncoder.matches(dto.getPassword(), emp.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        // ✅ Generate JWT with role + permissions
        return jwtUtil.generateToken(emp);
    }
}